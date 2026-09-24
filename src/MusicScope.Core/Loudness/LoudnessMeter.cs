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

    // Rolling ring buffer for current block (400ms)
    private readonly double[][] _blockRingBuffer;
    private int _ringBufferPos;
    private int _samplesSinceLastStep;
    private long _totalSamplesProcessed;

    // Power history of 400ms blocks: each entry is weighted sum of mean square powers
    private readonly List<double> _blockPowers400ms = [];
    // Short-term (3s) loudness values in LUFS
    private readonly List<double> _shortTermLoudnessHistory = [];

    // Realtime metrics
    private double _currentMomentaryLufs = -70.0;
    private double _currentShortTermLufs = -70.0;
    private double _momentaryMax = -70.0;
    private double _shortTermMax = -70.0;

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

        _blockRingBuffer = new double[channelCount][];
        for (int i = 0; i < channelCount; i++)
        {
            _blockRingBuffer[i] = new double[_samplesPerBlock400ms];
        }
    }

    /// <summary>
    /// Processes interleaved audio samples (e.g. [L0, R0, L1, R1, ...]).
    /// </summary>
    public void ProcessInterleaved(ReadOnlySpan<double> samples)
    {
        int frameCount = samples.Length / _channelCount;
        for (int frame = 0; frame < frameCount; frame++)
        {
            int baseIdx = frame * _channelCount;
            for (int ch = 0; ch < _channelCount; ch++)
            {
                double raw = samples[baseIdx + ch];
                double filtered = _filter.ProcessSample(ch, raw);
                _blockRingBuffer[ch][_ringBufferPos] = filtered;
            }

            _ringBufferPos = (_ringBufferPos + 1) % _samplesPerBlock400ms;
            _samplesSinceLastStep++;
            _totalSamplesProcessed++;

            if (_samplesSinceLastStep >= _samplesPerStep100ms)
            {
                _samplesSinceLastStep = 0;
                EvaluateStep();
            }
        }
    }

    /// <summary>
    /// Processes interleaved float audio samples.
    /// </summary>
    public void ProcessInterleaved(ReadOnlySpan<float> samples)
    {
        int frameCount = samples.Length / _channelCount;
        for (int frame = 0; frame < frameCount; frame++)
        {
            int baseIdx = frame * _channelCount;
            for (int ch = 0; ch < _channelCount; ch++)
            {
                double raw = samples[baseIdx + ch];
                double filtered = _filter.ProcessSample(ch, raw);
                _blockRingBuffer[ch][_ringBufferPos] = filtered;
            }

            _ringBufferPos = (_ringBufferPos + 1) % _samplesPerBlock400ms;
            _samplesSinceLastStep++;
            _totalSamplesProcessed++;

            if (_samplesSinceLastStep >= _samplesPerStep100ms)
            {
                _samplesSinceLastStep = 0;
                EvaluateStep();
            }
        }
    }

    private void EvaluateStep()
    {
        // Don't compute until we have at least one full 400ms block
        if (_totalSamplesProcessed < _samplesPerBlock400ms)
            return;

        // Calculate mean square power over the 400ms buffer for each channel
        double weightedSumOfPowers = 0.0;
        for (int ch = 0; ch < _channelCount; ch++)
        {
            double chSumSq = 0.0;
            double[] chBuf = _blockRingBuffer[ch];
            for (int i = 0; i < _samplesPerBlock400ms; i++)
            {
                double s = chBuf[i];
                chSumSq += s * s;
            }
            double meanSquare = chSumSq / _samplesPerBlock400ms;
            weightedSumOfPowers += _channelWeights[ch] * meanSquare;
        }

        _blockPowers400ms.Add(weightedSumOfPowers);

        // Momentary Loudness (400 ms)
        _currentMomentaryLufs = weightedSumOfPowers > 0.0
            ? -0.691 + 10.0 * Math.Log10(weightedSumOfPowers)
            : -70.0;

        if (_currentMomentaryLufs > _momentaryMax)
            _momentaryMax = _currentMomentaryLufs;

        // Short-term Loudness (last 3 seconds = 30 steps of 100ms)
        if (_blockPowers400ms.Count >= _blocksPerShortTerm3s)
        {
            int startIdx = _blockPowers400ms.Count - _blocksPerShortTerm3s;
            double shortTermPowerSum = 0.0;
            for (int i = startIdx; i < _blockPowers400ms.Count; i++)
            {
                shortTermPowerSum += _blockPowers400ms[i];
            }
            double shortTermAvgPower = shortTermPowerSum / _blocksPerShortTerm3s;
            _currentShortTermLufs = shortTermAvgPower > 0.0
                ? -0.691 + 10.0 * Math.Log10(shortTermAvgPower)
                : -70.0;

            if (_currentShortTermLufs > _shortTermMax)
                _shortTermMax = _currentShortTermLufs;

            _shortTermLoudnessHistory.Add(_currentShortTermLufs);
        }
        else
        {
            _currentShortTermLufs = _currentMomentaryLufs;
            if (_currentShortTermLufs > _shortTermMax)
                _shortTermMax = _currentShortTermLufs;
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

        return new LoudnessResult
        {
            IntegratedLoudness = Math.Round(integratedLufs, 1),
            MomentaryMax = Math.Round(_momentaryMax, 1),
            ShortTermMax = Math.Round(_shortTermMax, 1),
            LoudnessRange = Math.Round(lra, 1),
            LraLow = Math.Round(lraLow, 1),
            LraHigh = Math.Round(lraHigh, 1)
        };
    }

    /// <summary>
    /// Resets all buffers and historical data for a new measurement.
    /// </summary>
    public void Reset()
    {
        _filter.Reset();
        _ringBufferPos = 0;
        _samplesSinceLastStep = 0;
        _totalSamplesProcessed = 0;
        _blockPowers400ms.Clear();
        _shortTermLoudnessHistory.Clear();
        _currentMomentaryLufs = -70.0;
        _currentShortTermLufs = -70.0;
        _momentaryMax = -70.0;
        _shortTermMax = -70.0;

        for (int ch = 0; ch < _channelCount; ch++)
        {
            Array.Clear(_blockRingBuffer[ch], 0, _blockRingBuffer[ch].Length);
        }
    }
}
