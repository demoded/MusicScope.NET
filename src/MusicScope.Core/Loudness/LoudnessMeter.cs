using System;
using System.Collections.Generic;
using System.Linq;
using MusicScope.Core.DSP;

namespace MusicScope.Core.Loudness;

/// <summary>
/// Professional EBU R128 / ITU-R BS.1770-4 Loudness Meter.
/// Accurately computes Integrated Loudness (LUFS), Momentary (400 ms), Short-term (3 s),
/// and Loudness Range (LRA) according to EBU Tech 3342.
/// </summary>
public sealed class LoudnessMeter
{
    private readonly double _sampleRate;
    private readonly int _channelCount;
    private readonly double[] _channelWeights;
    private readonly KWeightingFilter _filter;

    // Buffer parameters
    private readonly int _samplesPerBlock400ms;
    private readonly int _samplesPerStep100ms;
    private readonly int _blocksPerShortTerm3s = 30; // 3000 ms / 100 ms = 30 blocks

    // Rolling sub-blocks (4 sub-blocks of 100ms = 400ms momentary window)
    private readonly double[] _current100msEnergy;
    private readonly double[][] _subBlockMeanSquares;
    private int _subBlockRingIdx;
    private int _totalSubBlocksProcessed;
    private int _samplesSinceLastStep;
    private long _totalSamplesProcessed;
    private double _shortTermRunningSum;

    // Power history of 400ms blocks: each entry is weighted sum of mean square powers
    private readonly List<double> _blockPowers400ms = [];
    // Short-term (3s) loudness values in LUFS
    private readonly List<double> _shortTermLoudnessHistory = [];

    // Realtime metrics
    private double _currentMomentaryLufs = -70.0;
    private double _currentShortTermLufs = -70.0;
    private double _momentaryMax = -70.0;
    private double _shortTermMax = -70.0;
    // S-Mode Loudness Histogram (751 bins from -70.0 dB to +5.0 dB in 0.1 dB steps, matching MusicScope LoudnessModule)
    private readonly int[] _sModeHistogram = new int[751];
    private int _sModeMaxCount;

    // PLR (Peak-to-Loudness Ratio) matching XiVideo MusicScope LoudnessModule
    private const int PlrBlockSize = 2048;
    private int _plrBlockSampleCount;
    private double _plrBlockMaxPeak;
    private double _plrBlockEnergySum;
    private readonly double[] _plrPeakRing = new double[8];
    private readonly double[] _plrEnergyRing = new double[8];
    private int _plrPeakRingIdx;
    private int _plrEnergyRingIdx;
    private int _plrWarmup;
    private double _plrChunkInfo;
    private double _plrDemuxResT = 1.0;
    private double _currentInstantPlrDb;
    private double _plrAvgDb;

    public double CurrentMomentaryLufs => _currentMomentaryLufs;
    public double CurrentShortTermLufs => _currentShortTermLufs;
    public double MomentaryMax => _momentaryMax;
    public double ShortTermMax => _shortTermMax;
    public double CurrentInstantPlrDb => _currentInstantPlrDb;
    public double PlrAvgDb => _plrAvgDb;

    public LoudnessMeter(double sampleRate, int channelCount = 2, double[]? channelWeights = null)
    {
        if (sampleRate <= 0)
            throw new ArgumentOutOfRangeException(nameof(sampleRate));
        if (channelCount <= 0)
            throw new ArgumentOutOfRangeException(nameof(channelCount));

        _sampleRate = sampleRate;
        _channelCount = channelCount;
        _filter = new KWeightingFilter(sampleRate, channelCount);

        _samplesPerBlock400ms = (int)Math.Round(0.400 * sampleRate);
        _samplesPerStep100ms = (int)Math.Round(0.100 * sampleRate);

        _channelWeights = new double[channelCount];
        for (int i = 0; i < channelCount; i++)
        {
            if (channelWeights != null && i < channelWeights.Length)
                _channelWeights[i] = channelWeights[i];
            else
                _channelWeights[i] = 1.0; // Left, Right, Center default 1.0
        }

        _current100msEnergy = new double[channelCount];
        _subBlockMeanSquares = new double[channelCount][];
        for (int i = 0; i < channelCount; i++)
        {
            _subBlockMeanSquares[i] = new double[4];
        }
    }

    /// <summary>
    /// Processes interleaved audio samples (e.g. [L0, R0, L1, R1, ...]).
    /// </summary>
    public void ProcessInterleaved(ReadOnlySpan<double> samples)
    {
        int frameCount = samples.Length / _channelCount;
        if (_channelCount == 2)
        {
            double energy0 = _current100msEnergy[0];
            double energy1 = _current100msEnergy[1];
            int stepSamples = _samplesSinceLastStep;
            long totalSamples = _totalSamplesProcessed;
            int stepTarget = _samplesPerStep100ms;

            for (int frame = 0; frame < frameCount; frame++)
            {
                double rawL = samples[frame * 2];
                double rawR = samples[frame * 2 + 1];

                _filter.ProcessStereoSample(rawL, rawR, out double filteredL, out double filteredR);

                energy0 += filteredL * filteredL;
                energy1 += filteredR * filteredR;
                stepSamples++;
                totalSamples++;

                // PLR accumulation
                double absL = Math.Abs(filteredL);
                double absR = Math.Abs(filteredR);
                if (absL > _plrBlockMaxPeak) _plrBlockMaxPeak = absL;
                if (absR > _plrBlockMaxPeak) _plrBlockMaxPeak = absR;
                _plrBlockEnergySum += (filteredL * filteredL + filteredR * filteredR);
                _plrBlockSampleCount++;
                if (_plrBlockSampleCount >= PlrBlockSize)
                {
                    EvaluatePlrBlock();
                }

                if (stepSamples >= stepTarget)
                {
                    _current100msEnergy[0] = energy0;
                    _current100msEnergy[1] = energy1;
                    _samplesSinceLastStep = 0;
                    _totalSamplesProcessed = totalSamples;

                    EvaluateSubBlock();

                    energy0 = 0.0;
                    energy1 = 0.0;
                    stepSamples = 0;
                    totalSamples = _totalSamplesProcessed;
                }
            }

            _current100msEnergy[0] = energy0;
            _current100msEnergy[1] = energy1;
            _samplesSinceLastStep = stepSamples;
            _totalSamplesProcessed = totalSamples;
        }
        else
        {
            for (int frame = 0; frame < frameCount; frame++)
            {
                int baseIdx = frame * _channelCount;
                double frameEnergySum = 0.0;
                for (int ch = 0; ch < _channelCount; ch++)
                {
                    double raw = samples[baseIdx + ch];
                    double filtered = _filter.ProcessSample(ch, raw);
                    _current100msEnergy[ch] += filtered * filtered;

                    double abs = Math.Abs(filtered);
                    if (abs > _plrBlockMaxPeak) _plrBlockMaxPeak = abs;
                    frameEnergySum += filtered * filtered;
                }
                _plrBlockEnergySum += frameEnergySum;
                _plrBlockSampleCount++;
                if (_plrBlockSampleCount >= PlrBlockSize)
                {
                    EvaluatePlrBlock();
                }

                _samplesSinceLastStep++;
                _totalSamplesProcessed++;

                if (_samplesSinceLastStep >= _samplesPerStep100ms)
                {
                    _samplesSinceLastStep = 0;
                    EvaluateSubBlock();
                }
            }
        }
    }

    /// <summary>
    /// Processes interleaved float audio samples.
    /// </summary>
    public void ProcessInterleaved(ReadOnlySpan<float> samples)
    {
        int frameCount = samples.Length / _channelCount;
        if (_channelCount == 2)
        {
            double energy0 = _current100msEnergy[0];
            double energy1 = _current100msEnergy[1];
            int stepSamples = _samplesSinceLastStep;
            long totalSamples = _totalSamplesProcessed;
            int stepTarget = _samplesPerStep100ms;

            for (int frame = 0; frame < frameCount; frame++)
            {
                float rawL = samples[frame * 2];
                float rawR = samples[frame * 2 + 1];

                _filter.ProcessStereoSample(rawL, rawR, out double filteredL, out double filteredR);

                energy0 += filteredL * filteredL;
                energy1 += filteredR * filteredR;
                stepSamples++;
                totalSamples++;

                // PLR accumulation
                double absL = Math.Abs(filteredL);
                double absR = Math.Abs(filteredR);
                if (absL > _plrBlockMaxPeak) _plrBlockMaxPeak = absL;
                if (absR > _plrBlockMaxPeak) _plrBlockMaxPeak = absR;
                _plrBlockEnergySum += (filteredL * filteredL + filteredR * filteredR);
                _plrBlockSampleCount++;
                if (_plrBlockSampleCount >= PlrBlockSize)
                {
                    EvaluatePlrBlock();
                }

                if (stepSamples >= stepTarget)
                {
                    _current100msEnergy[0] = energy0;
                    _current100msEnergy[1] = energy1;
                    _samplesSinceLastStep = 0;
                    _totalSamplesProcessed = totalSamples;

                    EvaluateSubBlock();

                    energy0 = 0.0;
                    energy1 = 0.0;
                    stepSamples = 0;
                    totalSamples = _totalSamplesProcessed;
                }
            }

            _current100msEnergy[0] = energy0;
            _current100msEnergy[1] = energy1;
            _samplesSinceLastStep = stepSamples;
            _totalSamplesProcessed = totalSamples;
        }
        else
        {
            for (int frame = 0; frame < frameCount; frame++)
            {
                int baseIdx = frame * _channelCount;
                double frameEnergySum = 0.0;
                for (int ch = 0; ch < _channelCount; ch++)
                {
                    double raw = samples[baseIdx + ch];
                    double filtered = _filter.ProcessSample(ch, raw);
                    _current100msEnergy[ch] += filtered * filtered;

                    double abs = Math.Abs(filtered);
                    if (abs > _plrBlockMaxPeak) _plrBlockMaxPeak = abs;
                    frameEnergySum += filtered * filtered;
                }
                _plrBlockEnergySum += frameEnergySum;
                _plrBlockSampleCount++;
                if (_plrBlockSampleCount >= PlrBlockSize)
                {
                    EvaluatePlrBlock();
                }

                _samplesSinceLastStep++;
                _totalSamplesProcessed++;

                if (_samplesSinceLastStep >= _samplesPerStep100ms)
                {
                    _samplesSinceLastStep = 0;
                    EvaluateSubBlock();
                }
            }
        }
    }

    private void EvaluateSubBlock()
    {
        for (int ch = 0; ch < _channelCount; ch++)
        {
            _subBlockMeanSquares[ch][_subBlockRingIdx] = _current100msEnergy[ch] / _samplesPerStep100ms;
            _current100msEnergy[ch] = 0.0;
        }

        _subBlockRingIdx = (_subBlockRingIdx + 1) & 3;
        _totalSubBlocksProcessed++;

        // Don't compute until we have 4 full sub-blocks (400ms)
        if (_totalSubBlocksProcessed < 4)
            return;

        double weightedSumOfPowers = 0.0;
        for (int ch = 0; ch < _channelCount; ch++)
        {
            double meanSquare = (_subBlockMeanSquares[ch][0] +
                                 _subBlockMeanSquares[ch][1] +
                                 _subBlockMeanSquares[ch][2] +
                                 _subBlockMeanSquares[ch][3]) * 0.25;
            weightedSumOfPowers += _channelWeights[ch] * meanSquare;
        }

        _blockPowers400ms.Add(weightedSumOfPowers);

        // Momentary Loudness (400 ms)
        _currentMomentaryLufs = weightedSumOfPowers > 0.0
            ? -0.691 + 10.0 * Math.Log10(weightedSumOfPowers)
            : -70.0;

        if (_currentMomentaryLufs > _momentaryMax)
            _momentaryMax = _currentMomentaryLufs;

        // Short-term Loudness (last 3 seconds = 30 steps of 100ms) with running sum
        _shortTermRunningSum += weightedSumOfPowers;
        if (_blockPowers400ms.Count >= _blocksPerShortTerm3s)
        {
            if (_blockPowers400ms.Count > _blocksPerShortTerm3s)
            {
                _shortTermRunningSum -= _blockPowers400ms[_blockPowers400ms.Count - 1 - _blocksPerShortTerm3s];
            }
            double shortTermAvgPower = _shortTermRunningSum / _blocksPerShortTerm3s;
            _currentShortTermLufs = shortTermAvgPower > 0.0
                ? -0.691 + 10.0 * Math.Log10(shortTermAvgPower)
                : -70.0;

            if (_currentShortTermLufs > _shortTermMax)
                _shortTermMax = _currentShortTermLufs;

            _shortTermLoudnessHistory.Add(_currentShortTermLufs);
        }
        else
        {
            double shortTermAvgPower = _shortTermRunningSum / _blockPowers400ms.Count;
            _currentShortTermLufs = shortTermAvgPower > 0.0
                ? -0.691 + 10.0 * Math.Log10(shortTermAvgPower)
                : -70.0;

            if (_currentShortTermLufs > _shortTermMax)
                _shortTermMax = _currentShortTermLufs;
        }

        // Accumulate S-Mode histogram after 3s warm-up (matching LoudnessModule.java AlacContextModel > 59)
        if (_blockPowers400ms.Count >= _blocksPerShortTerm3s &&
            _currentShortTermLufs >= -70.0 && _currentShortTermLufs <= 5.0)
        {
            int bin = (int)Math.Round(10.0 * (_currentShortTermLufs + 70.0));
            if ((uint)bin < (uint)_sModeHistogram.Length)
            {
                int count = ++_sModeHistogram[bin];
                if (count > _sModeMaxCount)
                {
                    _sModeMaxCount = count;
                }
            }
        }
    }

    private void EvaluatePlrBlock()
    {
        double d2 = _plrBlockMaxPeak;
        double d9 = _plrBlockEnergySum / PlrBlockSize;
        _plrBlockSampleCount = 0;
        _plrBlockMaxPeak = 0.0;
        _plrBlockEnergySum = 0.0;

        _plrPeakRing[_plrPeakRingIdx] = d2;
        _plrPeakRingIdx = (_plrPeakRingIdx + 1) & 7;

        double sumPeak = 0.0;
        for (int i = 0; i < 8; i++)
        {
            sumPeak += _plrPeakRing[i];
        }
        double avgPeak = sumPeak / 8.0;
        double d10 = avgPeak > 0.0 ? 20.0 * Math.Log10(avgPeak) - 0.691 : -60.0;
        if (d10 < -60.0) d10 = -60.0;

        _plrEnergyRing[_plrEnergyRingIdx] = d9;
        _plrEnergyRingIdx = (_plrEnergyRingIdx + 1) & 7;

        double sumEnergy = 0.0;
        for (int i = 0; i < 8; i++)
        {
            sumEnergy += _plrEnergyRing[i];
        }
        double avgEnergy = sumEnergy / 8.0;
        double d12 = avgEnergy > 0.0 ? 10.0 * Math.Log10(avgEnergy) - 0.691 : -90.0;
        if (d12 < -60.0) d12 = -60.0;

        double instantPlr = d10 - d12;
        if (instantPlr < 0.0) instantPlr = 0.0;

        if (_plrWarmup < 8)
        {
            _plrWarmup++;
            _currentInstantPlrDb = 0.0;
        }
        else
        {
            _currentInstantPlrDb = instantPlr;
            _plrChunkInfo += Math.Pow(10.0, instantPlr / 20.0);
            _plrDemuxResT += 1.0;
            _plrAvgDb = _plrChunkInfo > 0.0 ? 20.0 * Math.Log10(_plrChunkInfo / _plrDemuxResT) : 0.0;
        }
    }

    /// <summary>
    /// Computes the complete Integrated Loudness and Loudness Range (LRA).
    /// </summary>
    public LoudnessResult CalculateResult()
    {
        if (_blockPowers400ms.Count == 0)
        {
            return new LoudnessResult();
        }

        // 1. Calculate Integrated Loudness with gating (ITU-R BS.1770-4)
        // Step A: Absolute threshold (-70 LKFS)
        List<double> absoluteGatedPowers = new(_blockPowers400ms.Count);
        foreach (double power in _blockPowers400ms)
        {
            double lkfs = power > 0.0 ? -0.691 + 10.0 * Math.Log10(power) : -100.0;
            if (lkfs >= -70.0)
            {
                absoluteGatedPowers.Add(power);
            }
        }

        double integratedLufs = -70.0;
        if (absoluteGatedPowers.Count > 0)
        {
            // Step B: Relative threshold (-10 LU below unweighted average of absolute gated blocks)
            double avgPowerAbsGated = absoluteGatedPowers.Average();
            double preliminaryLufs = -0.691 + 10.0 * Math.Log10(avgPowerAbsGated);
            double relativeThreshold = preliminaryLufs - 10.0;

            // Step C: Final gated calculation
            List<double> finalGatedPowers = new(absoluteGatedPowers.Count);
            foreach (double power in absoluteGatedPowers)
            {
                double lkfs = -0.691 + 10.0 * Math.Log10(power);
                if (lkfs >= relativeThreshold)
                {
                    finalGatedPowers.Add(power);
                }
            }

            if (finalGatedPowers.Count > 0)
            {
                double finalAvgPower = finalGatedPowers.Average();
                integratedLufs = -0.691 + 10.0 * Math.Log10(finalAvgPower);
            }
        }

        // 2. Calculate Loudness Range (LRA) according to EBU Tech 3342
        double lra = 0.0;
        double lraLow = -70.0;
        double lraHigh = -70.0;

        if (_shortTermLoudnessHistory.Count >= 10)
        {
            // Gate 1: Absolute threshold (-70 LUFS)
            var absGatedShortTerm = _shortTermLoudnessHistory
                .Where(v => v >= -70.0)
                .ToList();

            if (absGatedShortTerm.Count > 0)
            {
                // Gate 2: Relative threshold (-20 LU below mean of absolute-gated blocks)
                // Compute energy-weighted average of short-term values
                double meanPower = absGatedShortTerm
                    .Select(v => Math.Pow(10.0, (v + 0.691) / 10.0))
                    .Average();
                double preliminaryStLufs = -0.691 + 10.0 * Math.Log10(meanPower);
                double lraRelativeThreshold = preliminaryStLufs - 20.0;

                var lraGatedValues = absGatedShortTerm
                    .Where(v => v >= lraRelativeThreshold)
                    .OrderBy(v => v)
                    .ToList();

                if (lraGatedValues.Count >= 2)
                {
                    int idx10 = (int)Math.Round(0.10 * (lraGatedValues.Count - 1));
                    int idx95 = (int)Math.Round(0.95 * (lraGatedValues.Count - 1));
                    lraLow = lraGatedValues[idx10];
                    lraHigh = lraGatedValues[idx95];
                    lra = Math.Max(0.0, lraHigh - lraLow);
                }
            }
        }

        // Prefer exact histogram-based percentiles matching MusicScope LoudnessModule if histogram is populated
        int[] sModeCopy = new int[_sModeHistogram.Length];
        Array.Copy(_sModeHistogram, sModeCopy, sModeCopy.Length);
        CalculateLraFromHistogram(sModeCopy, out double hLow, out double hHigh, out double hLra);
        if (hLra > 0.0)
        {
            lra = hLra;
            lraLow = hLow;
            lraHigh = hHigh;
        }

        return new LoudnessResult
        {
            IntegratedLoudness = Math.Round(integratedLufs, 1),
            MomentaryMax = Math.Round(_momentaryMax, 1),
            ShortTermMax = Math.Round(_shortTermMax, 1),
            LoudnessRange = Math.Round(lra, 1),
            PlrAvgDb = Math.Round(_plrAvgDb, 1),
            LraLow = Math.Round(lraLow, 1),
            LraHigh = Math.Round(lraHigh, 1),
            SModeHistogram = sModeCopy,
            SModeMaxCount = _sModeMaxCount
        };
    }

    /// <summary>
    /// Returns a real-time copy of the S-Mode histogram and calculates current running LRA percentiles.
    /// Directly follows the algorithm in MusicScope LoudnessModule.java lines 147-177.
    /// </summary>
    public int[] GetHistogramSnapshot(out int maxCount, out double lraLow, out double lraHigh, out double lra)
    {
        int[] copy = new int[_sModeHistogram.Length];
        Array.Copy(_sModeHistogram, copy, copy.Length);
        maxCount = _sModeMaxCount;
        CalculateLraFromHistogram(copy, out lraLow, out lraHigh, out lra);
        return copy;
    }

    /// <summary>
    /// Computes LRA and percentiles from the 0.1 dB resolution histogram matching MusicScope LoudnessModule.java.
    /// </summary>
    public static void CalculateLraFromHistogram(
        ReadOnlySpan<int> histogram,
        out double lraLow,
        out double lraHigh,
        out double lra)
    {
        lraLow = -70.0;
        lraHigh = -70.0;
        lra = 0.0;

        // Step 1: Un-gated power sum
        double sumPower = 0.0;
        int totalCount = 0;
        for (int n = 0; n < histogram.Length; n++)
        {
            int count = histogram[n];
            if (count > 0)
            {
                double db = (double)n / 10.0 - 70.0;
                double power = Math.Pow(10.0, db / 10.0);
                totalCount += count;
                sumPower += count * power;
            }
        }

        if (totalCount < 2)
            return;

        double meanPower = sumPower / totalCount;
        double unGatedLufs = 10.0 * Math.Log10(meanPower);
        double relativeThreshold = unGatedLufs - 20.0;
        if (relativeThreshold < -70.0)
            relativeThreshold = -70.0;

        int startBin = Math.Clamp((int)Math.Round(10.0 * (relativeThreshold + 70.0)), 0, histogram.Length);

        // Step 2: Sum gated counts
        int gatedCount = 0;
        for (int n = startBin; n < histogram.Length; n++)
        {
            gatedCount += histogram[n];
        }

        if (gatedCount < 2)
            return;

        // Step 3: Find 10th and 95th percentiles (matching LoudnessModule.java lines 168-175)
        int cumCount = 0;
        int p10Bin = -1;
        int p95Bin = -1;
        for (int n = startBin; n < histogram.Length; n++)
        {
            cumCount += histogram[n];
            if (p10Bin == -1 && (double)cumCount / gatedCount > 0.10)
            {
                p10Bin = n;
            }
            if (p95Bin == -1 && (double)cumCount / gatedCount > 0.95)
            {
                p95Bin = n;
                break;
            }
        }

        if (p10Bin >= 0 && p95Bin >= 0)
        {
            lraLow = (double)p10Bin / 10.0 - 70.0;
            lraHigh = (double)p95Bin / 10.0 - 70.0;
            lra = Math.Max(0.0, lraHigh - lraLow);
        }
    }

    /// <summary>
    /// Resets all buffers and historical data for a new measurement.
    /// </summary>
    public void Reset()
    {
        _filter.Reset();
        _subBlockRingIdx = 0;
        _totalSubBlocksProcessed = 0;
        _samplesSinceLastStep = 0;
        _totalSamplesProcessed = 0;
        _shortTermRunningSum = 0.0;
        _blockPowers400ms.Clear();
        _shortTermLoudnessHistory.Clear();
        _currentMomentaryLufs = -70.0;
        _currentShortTermLufs = -70.0;
        _momentaryMax = -70.0;
        _shortTermMax = -70.0;
        Array.Clear(_sModeHistogram, 0, _sModeHistogram.Length);
        _sModeMaxCount = 0;

        _plrBlockSampleCount = 0;
        _plrBlockMaxPeak = 0.0;
        _plrBlockEnergySum = 0.0;
        Array.Clear(_plrPeakRing, 0, _plrPeakRing.Length);
        Array.Clear(_plrEnergyRing, 0, _plrEnergyRing.Length);
        _plrPeakRingIdx = 0;
        _plrEnergyRingIdx = 0;
        _plrWarmup = 0;
        _plrChunkInfo = 0.0;
        _plrDemuxResT = 1.0;
        _currentInstantPlrDb = 0.0;
        _plrAvgDb = 0.0;

        for (int ch = 0; ch < _channelCount; ch++)
        {
            _current100msEnergy[ch] = 0.0;
            Array.Clear(_subBlockMeanSquares[ch], 0, 4);
        }
    }
}
