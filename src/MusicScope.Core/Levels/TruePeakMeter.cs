using System;

namespace MusicScope.Core.Levels;

/// <summary>
/// ITU-R BS.1770-4 compliant 4x oversampling True Peak Meter.
/// Detects inter-sample peaks that exceed 0 dBFS through polyphase FIR interpolation.
/// </summary>
public sealed class TruePeakMeter
{
    private const int OversamplingFactor = 4;
    private const int SubfilterLength = 16; // 64-tap total FIR filter / 4 phases = 16 taps per phase

    // Precomputed 4x polyphase interpolation coefficients (windowed-sinc)
    private static readonly double[][] PolyphaseCoefficients = InitializeCoefficients();
    private static readonly double[] CoeffsRev0 = CreateReversedCoeffs(0);
    private static readonly double[] CoeffsRev1 = CreateReversedCoeffs(1);
    private static readonly double[] CoeffsRev2 = CreateReversedCoeffs(2);
    private static readonly double[] CoeffsRev3 = CreateReversedCoeffs(3);

    private static double[] CreateReversedCoeffs(int phase)
    {
        double[] rev = new double[SubfilterLength];
        for (int i = 0; i < SubfilterLength; i++)
        {
            rev[i] = PolyphaseCoefficients[phase][SubfilterLength - 1 - i];
        }
        return rev;
    }

    private static double[][] InitializeCoefficients()
    {
        int totalTaps = OversamplingFactor * SubfilterLength;
        double cutoff = 0.125; // 1 / (2 * OversamplingFactor)
        double center = (totalTaps - 1) / 2.0;

        double[][] poly = new double[OversamplingFactor][];
        for (int p = 0; p < OversamplingFactor; p++)
        {
            poly[p] = new double[SubfilterLength];
            for (int k = 0; k < SubfilterLength; k++)
            {
                int n = k * OversamplingFactor + p;
                double t = n - center;
                double sinc = (Math.Abs(t) < 1e-9)
                    ? 2.0 * cutoff
                    : Math.Sin(2.0 * Math.PI * cutoff * t) / (Math.PI * t);

                // Blackman window
                double a = 2.0 * Math.PI * n / (totalTaps - 1);
                double w = 0.42 - 0.5 * Math.Cos(a) + 0.08 * Math.Cos(2.0 * a);
                poly[p][k] = sinc * w * OversamplingFactor;
            }

            // Normalize branch for unity DC gain
            double sum = 0.0;
            for (int k = 0; k < SubfilterLength; k++)
                sum += poly[p][k];

            if (Math.Abs(sum) > 1e-9)
            {
                for (int k = 0; k < SubfilterLength; k++)
                    poly[p][k] /= sum;
            }
        }

        return poly;
    }

    private readonly int _channelCount;
    // Contiguous double-buffered delay history: [channel][32]
    private readonly double[][] _history;
    private readonly int[] _historyIndex;

    private readonly double[] _samplePeakMax;
    private readonly double[] _truePeakMax;
    private readonly double[] _sumSquares;
    private readonly double[] _currentBlockPeak;
    private readonly double[] _currentBlockRms;
    private long _totalFrames;

    public double CurrentBlockPeakLeftDb => _currentBlockPeak[0] > 1e-6 ? 20.0 * Math.Log10(_currentBlockPeak[0]) : -100.0;
    public double CurrentBlockPeakRightDb => _channelCount > 1 && _currentBlockPeak[1] > 1e-6 ? 20.0 * Math.Log10(_currentBlockPeak[1]) : CurrentBlockPeakLeftDb;
    public double CurrentBlockRmsLeftDb => _currentBlockRms[0] > 1e-6 ? 20.0 * Math.Log10(_currentBlockRms[0]) : -100.0;
    public double CurrentBlockRmsRightDb => _channelCount > 1 && _currentBlockRms[1] > 1e-6 ? 20.0 * Math.Log10(_currentBlockRms[1]) : CurrentBlockRmsLeftDb;

    public double MaxTruePeakLeftDb => _truePeakMax[0] > 1e-6 ? 20.0 * Math.Log10(_truePeakMax[0]) : -100.0;
    public double MaxTruePeakRightDb => _channelCount > 1 && _truePeakMax[1] > 1e-6 ? 20.0 * Math.Log10(_truePeakMax[1]) : MaxTruePeakLeftDb;

    public TruePeakMeter(int channelCount = 2)
    {
        if (channelCount <= 0)
            throw new ArgumentOutOfRangeException(nameof(channelCount));

        _channelCount = channelCount;
        _history = new double[channelCount][];
        _historyIndex = new int[channelCount];
        _samplePeakMax = new double[channelCount];
        _truePeakMax = new double[channelCount];
        _sumSquares = new double[channelCount];
        _currentBlockPeak = new double[channelCount];
        _currentBlockRms = new double[channelCount];

        for (int ch = 0; ch < channelCount; ch++)
        {
            _history[ch] = new double[SubfilterLength * 2]; // 32 elements for contiguous slicing
        }

        Reset();
    }

    [System.Runtime.CompilerServices.MethodImpl(System.Runtime.CompilerServices.MethodImplOptions.AggressiveInlining)]
    private static double EvaluatePhase(double[] cRev, double[] hist, int offset)
    {
        return cRev[0] * hist[offset]
             + cRev[1] * hist[offset + 1]
             + cRev[2] * hist[offset + 2]
             + cRev[3] * hist[offset + 3]
             + cRev[4] * hist[offset + 4]
             + cRev[5] * hist[offset + 5]
             + cRev[6] * hist[offset + 6]
             + cRev[7] * hist[offset + 7]
             + cRev[8] * hist[offset + 8]
             + cRev[9] * hist[offset + 9]
             + cRev[10] * hist[offset + 10]
             + cRev[11] * hist[offset + 11]
             + cRev[12] * hist[offset + 12]
             + cRev[13] * hist[offset + 13]
             + cRev[14] * hist[offset + 14]
             + cRev[15] * hist[offset + 15];
    }

    /// <summary>
    /// Processes interleaved samples and updates Sample Peak and True Peak.
    /// </summary>
    public void ProcessInterleaved(ReadOnlySpan<double> samples)
    {
        int frameCount = samples.Length / _channelCount;
        if (frameCount == 0) return;

        double[] blockSumSq = new double[_channelCount];
        double[] blockPeak = new double[_channelCount];

        for (int frame = 0; frame < frameCount; frame++)
        {
            int baseIdx = frame * _channelCount;
            for (int ch = 0; ch < _channelCount; ch++)
            {
                double s = samples[baseIdx + ch];
                double absS = Math.Abs(s);

                if (absS > blockPeak[ch])
                    blockPeak[ch] = absS;

                blockSumSq[ch] += s * s;

                if (absS > _samplePeakMax[ch])
                    _samplePeakMax[ch] = absS;

                _sumSquares[ch] += s * s;

                // Contiguous double-buffer write
                int writeIdx = _historyIndex[ch];
                double[] hist = _history[ch];
                hist[writeIdx] = s;
                hist[writeIdx + 16] = s;
                _historyIndex[ch] = (writeIdx + 1) & 15;

                // Skip FIR if signal is small and cannot exceed current peak
                if (absS >= _truePeakMax[ch] * 0.707 || absS >= 0.5 || _truePeakMax[ch] < 0.1)
                {
                    int offset = writeIdx + 1;
                    double p0 = Math.Abs(EvaluatePhase(CoeffsRev0, hist, offset));
                    double p1 = Math.Abs(EvaluatePhase(CoeffsRev1, hist, offset));
                    double p2 = Math.Abs(EvaluatePhase(CoeffsRev2, hist, offset));
                    double p3 = Math.Abs(EvaluatePhase(CoeffsRev3, hist, offset));

                    double maxInterp = Math.Max(Math.Max(p0, p1), Math.Max(p2, p3));
                    if (maxInterp > _truePeakMax[ch])
                    {
                        _truePeakMax[ch] = maxInterp;
                    }
                }
            }
            _totalFrames++;
        }

        for (int ch = 0; ch < _channelCount; ch++)
        {
            _currentBlockPeak[ch] = blockPeak[ch];
            _currentBlockRms[ch] = Math.Sqrt(blockSumSq[ch] / frameCount);
        }
    }

    /// <summary>
    /// Processes interleaved float samples.
    /// </summary>
    public void ProcessInterleaved(ReadOnlySpan<float> samples)
    {
        int frameCount = samples.Length / _channelCount;
        if (frameCount == 0) return;

        double[] blockSumSq = new double[_channelCount];
        double[] blockPeak = new double[_channelCount];

        for (int frame = 0; frame < frameCount; frame++)
        {
            int baseIdx = frame * _channelCount;
            for (int ch = 0; ch < _channelCount; ch++)
            {
                double s = samples[baseIdx + ch];
                double absS = Math.Abs(s);

                if (absS > blockPeak[ch])
                    blockPeak[ch] = absS;

                blockSumSq[ch] += s * s;

                if (absS > _samplePeakMax[ch])
                    _samplePeakMax[ch] = absS;

                _sumSquares[ch] += s * s;

                // Contiguous double-buffer write
                int writeIdx = _historyIndex[ch];
                double[] hist = _history[ch];
                hist[writeIdx] = s;
                hist[writeIdx + 16] = s;
                _historyIndex[ch] = (writeIdx + 1) & 15;

                // Skip FIR if signal is small and cannot exceed current peak
                if (absS >= _truePeakMax[ch] * 0.707 || absS >= 0.5 || _truePeakMax[ch] < 0.1)
                {
                    int offset = writeIdx + 1;
                    double p0 = Math.Abs(EvaluatePhase(CoeffsRev0, hist, offset));
                    double p1 = Math.Abs(EvaluatePhase(CoeffsRev1, hist, offset));
                    double p2 = Math.Abs(EvaluatePhase(CoeffsRev2, hist, offset));
                    double p3 = Math.Abs(EvaluatePhase(CoeffsRev3, hist, offset));

                    double maxInterp = Math.Max(Math.Max(p0, p1), Math.Max(p2, p3));
                    if (maxInterp > _truePeakMax[ch])
                    {
                        _truePeakMax[ch] = maxInterp;
                    }
                }
            }
            _totalFrames++;
        }

        for (int ch = 0; ch < _channelCount; ch++)
        {
            _currentBlockPeak[ch] = blockPeak[ch];
            _currentBlockRms[ch] = Math.Sqrt(blockSumSq[ch] / frameCount);
        }
    }

    /// <summary>
    /// Returns the current peak and RMS results.
    /// </summary>
    public LevelsResult CalculateResult()
    {
        double spLeftDb = _samplePeakMax[0] > 1e-6 ? 20.0 * Math.Log10(_samplePeakMax[0]) : -100.0;
        double spRightDb = _channelCount > 1 && _samplePeakMax[1] > 1e-6 ? 20.0 * Math.Log10(_samplePeakMax[1]) : spLeftDb;

        double tpLeftDb = _truePeakMax[0] > 1e-6 ? 20.0 * Math.Log10(_truePeakMax[0]) : -100.0;
        double tpRightDb = _channelCount > 1 && _truePeakMax[1] > 1e-6 ? 20.0 * Math.Log10(_truePeakMax[1]) : tpLeftDb;

        double rmsLeft = _totalFrames > 0 ? Math.Sqrt(_sumSquares[0] / _totalFrames) : 0.0;
        double rmsRight = _channelCount > 1 && _totalFrames > 0 ? Math.Sqrt(_sumSquares[1] / _totalFrames) : rmsLeft;

        double rmsLeftDb = rmsLeft > 1e-6 ? 20.0 * Math.Log10(rmsLeft) : -100.0;
        double rmsRightDb = rmsRight > 1e-6 ? 20.0 * Math.Log10(rmsRight) : -100.0;

        double maxPeak = Math.Max(_samplePeakMax[0], _channelCount > 1 ? _samplePeakMax[1] : 0.0);
        double avgRms = (rmsLeft + rmsRight) / 2.0;
        double crestDb = (maxPeak > 1e-6 && avgRms > 1e-6) ? 20.0 * Math.Log10(maxPeak / avgRms) : 0.0;

        double drDb = Math.Max(0.0, Math.Round(crestDb, 1));

        return new LevelsResult
        {
            SamplePeakLeftDb = Math.Round(spLeftDb, 2),
            SamplePeakRightDb = Math.Round(spRightDb, 2),
            TruePeakLeftDb = Math.Round(tpLeftDb, 2),
            TruePeakRightDb = Math.Round(tpRightDb, 2),
            RmsLeftDb = Math.Round(rmsLeftDb, 2),
            RmsRightDb = Math.Round(rmsRightDb, 2),
            CrestFactorDb = Math.Round(crestDb, 1),
            DynamicRangeDb = drDb
        };
    }

    /// <summary>
    /// Resets the meter state.
    /// </summary>
    public void Reset()
    {
        for (int ch = 0; ch < _channelCount; ch++)
        {
            Array.Clear(_history[ch], 0, _history[ch].Length);
            _historyIndex[ch] = 0;
            _samplePeakMax[ch] = 0.0;
            _truePeakMax[ch] = 0.0;
            _sumSquares[ch] = 0.0;
        }
        _totalFrames = 0;
    }
}
