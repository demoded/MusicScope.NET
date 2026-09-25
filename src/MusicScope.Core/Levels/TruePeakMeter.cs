using System;
using MusicScope.Core.DSP;

namespace MusicScope.Core.Levels;

/// <summary>
/// Bit-exact port of XiVideo MusicScope's LevelsModule.
/// Uses cascaded polyphase FIR interpolation filters to detect inter-sample True Peak
/// and calculate the running CREST Avg. and RMS levels.
/// </summary>
public sealed class TruePeakMeter
{
    private const int BlockSize = 2048;

    private readonly int _channelCount;
    private readonly double _sampleRate;

    // Polyphase filters ported from XiVideo MusicScope
    private readonly MusicScopePolyphaseFilter _filter0 = new(0); // 44.1k stage 1 (90 taps)
    private readonly MusicScopePolyphaseFilter _filter1 = new(1); // 44.1k stage 2 / 88.2k (54 taps)
    private readonly MusicScopePolyphaseFilter _filter2 = new(2); // 48k stage 1 (80 taps)
    private readonly MusicScopePolyphaseFilter _filter3 = new(3); // 48k stage 2 / 96k (52 taps)

    // Block buffers
    private readonly double[] _inBlockL = new double[BlockSize];
    private readonly double[] _inBlockR = new double[BlockSize];
    private readonly double[] _stage1L = new double[BlockSize * 2];
    private readonly double[] _stage1R = new double[BlockSize * 2];
    private int _inBlockPos;

    // Peaks and RMS
    private readonly double[] _samplePeakMax = new double[2];
    private readonly double[] _truePeakMax = new double[2];
    private readonly double[] _currentBlockPeak = new double[2];
    private readonly double[] _currentBlockRms = new double[2];

    // Track energy accumulators (matching DemuxUtils and LeadingZeros in LevelsModule.java)
    private double _demuxUtils;   // Left energy sum
    private double _leadingZeros; // Right energy sum
    private long _sampleInfo;     // Block count

    // CREST factor state (matching LevelsModule.java)
    private readonly double[] _crestRingBuffer = new double[8];
    private int _crestRingIndex;
    private int _crestWarmupCount;
    private double _crestSum;
    private long _crestCount = 1;
    private double _crestAvgDb;
    private double _currentInstantCrestDb;

    public double CurrentBlockPeakLeftDb => _currentBlockPeak[0] > 1e-6 ? 20.0 * Math.Log10(_currentBlockPeak[0]) : -100.0;
    public double CurrentBlockPeakRightDb => _channelCount > 1 && _currentBlockPeak[1] > 1e-6 ? 20.0 * Math.Log10(_currentBlockPeak[1]) : CurrentBlockPeakLeftDb;
    public double CurrentBlockRmsLeftDb => _currentBlockRms[0] > 1e-6 ? 20.0 * Math.Log10(_currentBlockRms[0]) : -100.0;
    public double CurrentBlockRmsRightDb => _channelCount > 1 && _currentBlockRms[1] > 1e-6 ? 20.0 * Math.Log10(_currentBlockRms[1]) : CurrentBlockRmsLeftDb;

    public double MaxTruePeakLeftDb => _truePeakMax[0] > 1e-6 ? 20.0 * Math.Log10(_truePeakMax[0]) : -100.0;
    public double MaxTruePeakRightDb => _channelCount > 1 && _truePeakMax[1] > 1e-6 ? 20.0 * Math.Log10(_truePeakMax[1]) : MaxTruePeakLeftDb;

    public double CrestAvgDb => _crestAvgDb;
    public double CurrentInstantCrestDb => _currentInstantCrestDb;

    public TruePeakMeter(int channelCount = 2, double sampleRate = 44100.0)
    {
        if (channelCount <= 0)
            throw new ArgumentOutOfRangeException(nameof(channelCount));

        _channelCount = channelCount;
        _sampleRate = sampleRate;

        Reset();
    }

    /// <summary>
    /// Processes interleaved double samples.
    /// </summary>
    [System.Runtime.CompilerServices.MethodImpl(System.Runtime.CompilerServices.MethodImplOptions.AggressiveOptimization)]
    public void ProcessInterleaved(ReadOnlySpan<double> samples)
    {
        int totalFrames = samples.Length / _channelCount;
        int frameOffset = 0;

        if (_channelCount == 2)
        {
            double peak0 = _samplePeakMax[0];
            double peak1 = _samplePeakMax[1];

            while (frameOffset < totalFrames)
            {
                int framesToCopy = Math.Min(totalFrames - frameOffset, BlockSize - _inBlockPos);
                int baseSampleIdx = frameOffset << 1;

                for (int i = 0; i < framesToCopy; i++)
                {
                    int idx = baseSampleIdx + (i << 1);
                    double sL = samples[idx];
                    double sR = samples[idx + 1];

                    double absL = sL < 0.0 ? -sL : sL;
                    double absR = sR < 0.0 ? -sR : sR;
                    if (absL > peak0) peak0 = absL;
                    if (absR > peak1) peak1 = absR;

                    _inBlockL[_inBlockPos + i] = sL;
                    _inBlockR[_inBlockPos + i] = sR;
                }

                _inBlockPos += framesToCopy;
                frameOffset += framesToCopy;

                if (_inBlockPos == BlockSize)
                {
                    ProcessBlock(BlockSize);
                    _inBlockPos = 0;
                }
            }

            _samplePeakMax[0] = peak0;
            _samplePeakMax[1] = peak1;
        }
        else
        {
            double peak0 = _samplePeakMax[0];

            while (frameOffset < totalFrames)
            {
                int framesToCopy = Math.Min(totalFrames - frameOffset, BlockSize - _inBlockPos);
                int baseSampleIdx = frameOffset * _channelCount;

                for (int i = 0; i < framesToCopy; i++)
                {
                    int idx = baseSampleIdx + i * _channelCount;
                    double sL = samples[idx];
                    double absL = sL < 0.0 ? -sL : sL;
                    if (absL > peak0) peak0 = absL;

                    _inBlockL[_inBlockPos + i] = sL;
                    _inBlockR[_inBlockPos + i] = sL;
                }

                _inBlockPos += framesToCopy;
                frameOffset += framesToCopy;

                if (_inBlockPos == BlockSize)
                {
                    ProcessBlock(BlockSize);
                    _inBlockPos = 0;
                }
            }

            _samplePeakMax[0] = peak0;
            _samplePeakMax[1] = peak0;
        }
    }

    /// <summary>
    /// Processes interleaved float samples.
    /// </summary>
    [System.Runtime.CompilerServices.MethodImpl(System.Runtime.CompilerServices.MethodImplOptions.AggressiveOptimization)]
    public void ProcessInterleaved(ReadOnlySpan<float> samples)
    {
        int totalFrames = samples.Length / _channelCount;
        int frameOffset = 0;

        if (_channelCount == 2)
        {
            double peak0 = _samplePeakMax[0];
            double peak1 = _samplePeakMax[1];

            while (frameOffset < totalFrames)
            {
                int framesToCopy = Math.Min(totalFrames - frameOffset, BlockSize - _inBlockPos);
                int baseSampleIdx = frameOffset << 1;

                for (int i = 0; i < framesToCopy; i++)
                {
                    int idx = baseSampleIdx + (i << 1);
                    double sL = samples[idx];
                    double sR = samples[idx + 1];

                    double absL = sL < 0.0 ? -sL : sL;
                    double absR = sR < 0.0 ? -sR : sR;
                    if (absL > peak0) peak0 = absL;
                    if (absR > peak1) peak1 = absR;

                    _inBlockL[_inBlockPos + i] = sL;
                    _inBlockR[_inBlockPos + i] = sR;
                }

                _inBlockPos += framesToCopy;
                frameOffset += framesToCopy;

                if (_inBlockPos == BlockSize)
                {
                    ProcessBlock(BlockSize);
                    _inBlockPos = 0;
                }
            }

            _samplePeakMax[0] = peak0;
            _samplePeakMax[1] = peak1;
        }
        else
        {
            double peak0 = _samplePeakMax[0];

            while (frameOffset < totalFrames)
            {
                int framesToCopy = Math.Min(totalFrames - frameOffset, BlockSize - _inBlockPos);
                int baseSampleIdx = frameOffset * _channelCount;

                for (int i = 0; i < framesToCopy; i++)
                {
                    int idx = baseSampleIdx + i * _channelCount;
                    double sL = samples[idx];
                    double absL = sL < 0.0 ? -sL : sL;
                    if (absL > peak0) peak0 = absL;

                    _inBlockL[_inBlockPos + i] = sL;
                    _inBlockR[_inBlockPos + i] = sL;
                }

                _inBlockPos += framesToCopy;
                frameOffset += framesToCopy;

                if (_inBlockPos == BlockSize)
                {
                    ProcessBlock(BlockSize);
                    _inBlockPos = 0;
                }
            }

            _samplePeakMax[0] = peak0;
            _samplePeakMax[1] = peak0;
        }
    }

    /// <summary>
    /// Processes a block of samples through MusicScope's exact oversampling filter and CREST algorithm.
    /// </summary>
    [System.Runtime.CompilerServices.MethodImpl(System.Runtime.CompilerServices.MethodImplOptions.AggressiveOptimization)]
    private void ProcessBlock(int count)
    {
        if (count == 0) return;

        double d8, d7, d4, d3;

        if (Math.Abs(_sampleRate - 44100.0) < 100.0)
        {
            _filter0.Process(0, count, _inBlockL.AsSpan(0, count), _inBlockR.AsSpan(0, count), _stage1L, _stage1R);
            var stats = _filter1.ProcessAndAccumulate(0, count * 2, _stage1L.AsSpan(0, count * 2), _stage1R.AsSpan(0, count * 2));
            d8 = stats.MaxL;
            d7 = stats.MaxR;
            d4 = stats.SumSqL / (count * 4);
            d3 = stats.SumSqR / (count * 4);
        }
        else if (Math.Abs(_sampleRate - 48000.0) < 100.0)
        {
            _filter2.Process(0, count, _inBlockL.AsSpan(0, count), _inBlockR.AsSpan(0, count), _stage1L, _stage1R);
            var stats = _filter3.ProcessAndAccumulate(0, count * 2, _stage1L.AsSpan(0, count * 2), _stage1R.AsSpan(0, count * 2));
            d8 = stats.MaxL;
            d7 = stats.MaxR;
            d4 = stats.SumSqL / (count * 4);
            d3 = stats.SumSqR / (count * 4);
        }
        else if (Math.Abs(_sampleRate - 88200.0) < 100.0)
        {
            var stats = _filter1.ProcessAndAccumulate(1, count, _inBlockL.AsSpan(0, count), _inBlockR.AsSpan(0, count));
            d8 = stats.MaxL;
            d7 = stats.MaxR;
            d4 = stats.SumSqL / (count * 2);
            d3 = stats.SumSqR / (count * 2);
        }
        else if (Math.Abs(_sampleRate - 96000.0) < 100.0)
        {
            var stats = _filter3.ProcessAndAccumulate(1, count, _inBlockL.AsSpan(0, count), _inBlockR.AsSpan(0, count));
            d8 = stats.MaxL;
            d7 = stats.MaxR;
            d4 = stats.SumSqL / (count * 2);
            d3 = stats.SumSqR / (count * 2);
        }
        else
        {
            d8 = 0.0;
            d7 = 0.0;
            d4 = 0.0;
            d3 = 0.0;
            for (int n = 0; n < count; n++)
            {
                double d9 = _inBlockL[n];
                double d10 = _inBlockR[n];
                double abs9 = Math.Abs(d9);
                double abs10 = Math.Abs(d10);
                if (d8 < abs9) d8 = abs9;
                if (d7 < abs10) d7 = abs10;
                d4 += d9 * d9;
                d3 += d10 * d10;
            }
            d4 /= count;
            d3 /= count;
        }

        _demuxUtils += d4;
        _leadingZeros += d3;
        _sampleInfo++;

        if (d8 > _truePeakMax[0]) _truePeakMax[0] = d8;
        if (d7 > _truePeakMax[1]) _truePeakMax[1] = d7;

        _currentBlockPeak[0] = d8;
        _currentBlockPeak[1] = d7;
        _currentBlockRms[0] = Math.Sqrt(d4);
        _currentBlockRms[1] = Math.Sqrt(d3);

        // CREST calculation matching LevelsModule.java lines 268-288
        double d17 = d8;
        double d18 = d4;
        if (d17 < d7)
        {
            d17 = d7;
            d18 = d3;
        }

        double blockRms = Math.Sqrt(d18);
        double crestBlock = blockRms > 0.0 ? (d17 / blockRms) : 0.0;

        _crestRingBuffer[_crestRingIndex] = crestBlock;
        _crestRingIndex = (_crestRingIndex + 1) & 7;

        double d19 = 0.0;
        for (int k = 0; k < 8; k++)
        {
            d19 += _crestRingBuffer[k];
        }
        d19 /= 8.0;

        if (d19 > 0.001)
        {
            if (_crestWarmupCount < 8)
            {
                _crestWarmupCount++;
            }
            else
            {
                _crestSum += d19;
                _crestAvgDb = 20.0 * Math.Log10(_crestSum / _crestCount);
                _crestCount++;
            }
            _currentInstantCrestDb = 20.0 * Math.Log10(d19);
        }
        else
        {
            _currentInstantCrestDb = 0.0;
        }
    }

    /// <summary>
    /// Returns the complete peak, true peak, RMS, and CREST measurement results.
    /// </summary>
    public LevelsResult CalculateResult()
    {
        if (_inBlockPos > 0)
        {
            ProcessBlock(_inBlockPos);
            _inBlockPos = 0;
        }

        double spLeftDb = _samplePeakMax[0] > 1e-6 ? 20.0 * Math.Log10(_samplePeakMax[0]) : -100.0;
        double spRightDb = _channelCount > 1 && _samplePeakMax[1] > 1e-6 ? 20.0 * Math.Log10(_samplePeakMax[1]) : spLeftDb;

        double tpLeftDb = _truePeakMax[0] > 1e-6 ? 20.0 * Math.Log10(_truePeakMax[0]) : -100.0;
        double tpRightDb = _channelCount > 1 && _truePeakMax[1] > 1e-6 ? 20.0 * Math.Log10(_truePeakMax[1]) : tpLeftDb;

        double rmsLeft = _sampleInfo > 0 ? Math.Sqrt(_demuxUtils / _sampleInfo) : 0.0;
        double rmsRight = _channelCount > 1 && _sampleInfo > 0 ? Math.Sqrt(_leadingZeros / _sampleInfo) : rmsLeft;

        double rmsLeftDb = rmsLeft > 1e-6 ? 20.0 * Math.Log10(rmsLeft) : -100.0;
        double rmsRightDb = rmsRight > 1e-6 ? 20.0 * Math.Log10(rmsRight) : -100.0;

        double crestDb = (_crestCount > 1) ? _crestAvgDb : 0.0;
        double drDb = Math.Max(0.0, Math.Round(crestDb, 1));

        return new LevelsResult
        {
            SamplePeakLeftDb = Math.Round(spLeftDb, 1),
            SamplePeakRightDb = Math.Round(spRightDb, 1),
            TruePeakLeftDb = Math.Round(tpLeftDb, 1),
            TruePeakRightDb = Math.Round(tpRightDb, 1),
            RmsLeftDb = Math.Round(rmsLeftDb, 1),
            RmsRightDb = Math.Round(rmsRightDb, 1),
            CrestFactorDb = Math.Round(crestDb, 1),
            DynamicRangeDb = drDb
        };
    }

    /// <summary>
    /// Resets the meter state.
    /// </summary>
    public void Reset()
    {
        _filter0.Reset();
        _filter1.Reset();
        _filter2.Reset();
        _filter3.Reset();
        _inBlockPos = 0;
        Array.Clear(_inBlockL);
        Array.Clear(_inBlockR);
        Array.Clear(_stage1L);
        Array.Clear(_stage1R);
        Array.Clear(_samplePeakMax);
        Array.Clear(_truePeakMax);
        Array.Clear(_currentBlockPeak);
        Array.Clear(_currentBlockRms);
        Array.Clear(_crestRingBuffer);
        _crestRingIndex = 0;
        _crestWarmupCount = 0;
        _crestSum = 0.0;
        _crestCount = 1;
        _crestAvgDb = 0.0;
        _currentInstantCrestDb = 0.0;
        _demuxUtils = 0.0;
        _leadingZeros = 0.0;
        _sampleInfo = 0;
    }
}
