using System;

namespace MusicScope.Core.Stereo;

/// <summary>
/// Precision Stereo Phase Correlation, Mid/Side balance, and Goniometer Analyzer.
/// Directly reflects the measurement and oscilloscope logic in MusicScope's StereoMeterControl.
/// </summary>
public sealed class StereoAnalyzer
{
    private double _sumL2;
    private double _sumR2;
    private double _sumLR;
    private double _sumMid2;
    private double _sumSide2;
    private long _sampleCount;

    // Rolling correlation smoothing
    private double _smoothedCorrelation = 1.0;
    private const double SmoothingFactor = 0.05;

    // 2D Density Histogram Grid (256x256) for Cumulative Stereo Phosphor Cloud
    public const int DensityGridSize = 256;
    private readonly int[,] _densityGrid = new int[DensityGridSize, DensityGridSize];

    // Live Phosphor Points Ring Buffer (5000 points, matching MusicScope's high-resolution trace)
    public const int LivePointCount = 5000;
    private readonly float[] _livePointsX = new float[LivePointCount];
    private readonly float[] _livePointsY = new float[LivePointCount];
    private int _livePointsCount;

    /// <summary>
    /// Current smoothed real-time correlation value [-1.0, +1.0].
    /// </summary>
    public double RealtimeCorrelation => _smoothedCorrelation;

    [System.Runtime.CompilerServices.MethodImpl(System.Runtime.CompilerServices.MethodImplOptions.AggressiveInlining)]
    private static float Compress(float d)
    {
        return d < 0f ? -MathF.Log10(5f * -d + 1f) : MathF.Log10(5f * d + 1f);
    }

    [System.Runtime.CompilerServices.MethodImpl(System.Runtime.CompilerServices.MethodImplOptions.AggressiveInlining)]
    private static double Compress(double d)
    {
        return d < 0.0 ? -Math.Log10(5.0 * -d + 1.0) : Math.Log10(5.0 * d + 1.0);
    }

    /// <summary>
    /// Processes interleaved stereo samples [L0, R0, L1, R1, ...].
    /// </summary>
    public void ProcessInterleaved(ReadOnlySpan<double> stereoSamples)
    {
        int frameCount = stereoSamples.Length / 2;
        if (frameCount == 0) return;

        double frameSumL2 = 0.0;
        double frameSumR2 = 0.0;
        double frameSumLR = 0.0;

        for (int i = 0; i < frameCount; i++)
        {
            double l = stereoSamples[i * 2];
            double r = stereoSamples[i * 2 + 1];

            frameSumL2 += l * l;
            frameSumR2 += r * r;
            frameSumLR += l * r;
        }

        double halfSumEnergy = 0.5 * (frameSumL2 + frameSumR2);
        _sumMid2 += halfSumEnergy + frameSumLR;
        _sumSide2 += halfSumEnergy - frameSumLR;

        _sumL2 += frameSumL2;
        _sumR2 += frameSumR2;
        _sumLR += frameSumLR;
        _sampleCount += frameCount;

        // Instantaneous frame correlation
        double denom = Math.Sqrt(frameSumL2 * frameSumR2);
        if (denom > 1e-9)
        {
            double instantCorr = Math.Clamp(frameSumLR / denom, -1.0, 1.0);
            _smoothedCorrelation = _smoothedCorrelation * (1.0 - SmoothingFactor) + instantCorr * SmoothingFactor;
        }

        // Accumulate 2D density cloud
        for (int k = 0; k < frameCount; k += 2)
        {
            double l = stereoSamples[k * 2];
            double r = stereoSamples[k * 2 + 1];

            double rotX = (r - l) * 0.7071067811865475;
            double rotY = -(l + r) * 0.7071067811865475;

            double compX = Compress(rotX);
            double compY = Compress(rotY);

            int gx = (int)(128.0 + compX * 140.0);
            int gy = (int)(128.0 + compY * 140.0);

            if ((uint)gx < DensityGridSize && (uint)gy < DensityGridSize)
            {
                _densityGrid[gx, gy]++;
            }
        }

        // Live points
        if (frameCount >= LivePointCount)
        {
            float step = (float)frameCount / LivePointCount;
            for (int k = 0; k < LivePointCount; k++)
            {
                int sampleIdx = (int)(k * step) * 2;
                float l = (float)stereoSamples[sampleIdx];
                float r = (float)stereoSamples[sampleIdx + 1];

                float rotX = (r - l) * 0.70710678f;
                float rotY = -(l + r) * 0.70710678f;

                _livePointsX[k] = Compress(rotX);
                _livePointsY[k] = Compress(rotY);
            }
            _livePointsCount = LivePointCount;
        }
    }

    /// <summary>
    /// Processes interleaved float stereo samples.
    /// </summary>
    public void ProcessInterleaved(ReadOnlySpan<float> stereoSamples)
    {
        int frameCount = stereoSamples.Length / 2;
        if (frameCount == 0) return;

        double frameSumL2 = 0.0;
        double frameSumR2 = 0.0;
        double frameSumLR = 0.0;

        int i = 0;
        int unrolledEnd = frameCount - (frameCount & 3);
        for (; i < unrolledEnd; i += 4)
        {
            int idx = i << 1;
            double l0 = stereoSamples[idx];
            double r0 = stereoSamples[idx + 1];
            double l1 = stereoSamples[idx + 2];
            double r1 = stereoSamples[idx + 3];
            double l2 = stereoSamples[idx + 4];
            double r2 = stereoSamples[idx + 5];
            double l3 = stereoSamples[idx + 6];
            double r3 = stereoSamples[idx + 7];

            frameSumL2 += l0 * l0 + l1 * l1 + l2 * l2 + l3 * l3;
            frameSumR2 += r0 * r0 + r1 * r1 + r2 * r2 + r3 * r3;
            frameSumLR += l0 * r0 + l1 * r1 + l2 * r2 + l3 * r3;
        }

        for (; i < frameCount; i++)
        {
            int idx = i << 1;
            double l = stereoSamples[idx];
            double r = stereoSamples[idx + 1];

            frameSumL2 += l * l;
            frameSumR2 += r * r;
            frameSumLR += l * r;
        }

        double halfSumEnergy = 0.5 * (frameSumL2 + frameSumR2);
        _sumMid2 += halfSumEnergy + frameSumLR;
        _sumSide2 += halfSumEnergy - frameSumLR;

        _sumL2 += frameSumL2;
        _sumR2 += frameSumR2;
        _sumLR += frameSumLR;
        _sampleCount += frameCount;

        double denom = Math.Sqrt(frameSumL2 * frameSumR2);
        if (denom > 1e-9)
        {
            double instantCorr = Math.Clamp(frameSumLR / denom, -1.0, 1.0);
            _smoothedCorrelation = _smoothedCorrelation * (1.0 - SmoothingFactor) + instantCorr * SmoothingFactor;
        }

        // Accumulate 2D density cloud (sampling every 2nd frame)
        for (int k = 0; k < frameCount; k += 2)
        {
            float l = stereoSamples[k * 2];
            float r = stereoSamples[k * 2 + 1];

            float rotX = (r - l) * 0.70710678f;
            float rotY = -(l + r) * 0.70710678f;

            float compX = Compress(rotX);
            float compY = Compress(rotY);

            int gx = (int)(128f + compX * 140f);
            int gy = (int)(128f + compY * 140f);

            if ((uint)gx < DensityGridSize && (uint)gy < DensityGridSize)
            {
                _densityGrid[gx, gy]++;
            }
        }

        // Maintain 5000 live points for real-time oscilloscope phosphor trace
        if (frameCount >= LivePointCount)
        {
            float step = (float)frameCount / LivePointCount;
            for (int k = 0; k < LivePointCount; k++)
            {
                int sampleIdx = (int)(k * step) * 2;
                float l = stereoSamples[sampleIdx];
                float r = stereoSamples[sampleIdx + 1];

                float rotX = (r - l) * 0.70710678f;
                float rotY = -(l + r) * 0.70710678f;

                _livePointsX[k] = Compress(rotX);
                _livePointsY[k] = Compress(rotY);
            }
            _livePointsCount = LivePointCount;
        }
        else
        {
            int shift = Math.Min(_livePointsCount, LivePointCount - frameCount);
            if (shift > 0)
            {
                Array.Copy(_livePointsX, _livePointsCount - shift, _livePointsX, 0, shift);
                Array.Copy(_livePointsY, _livePointsCount - shift, _livePointsY, 0, shift);
            }
            else
            {
                shift = 0;
            }

            int toCopy = Math.Min(frameCount, LivePointCount - shift);
            for (int k = 0; k < toCopy; k++)
            {
                float l = stereoSamples[k * 2];
                float r = stereoSamples[k * 2 + 1];

                float rotX = (r - l) * 0.70710678f;
                float rotY = -(l + r) * 0.70710678f;

                _livePointsX[shift + k] = Compress(rotX);
                _livePointsY[shift + k] = Compress(rotY);
            }
            _livePointsCount = shift + toCopy;
        }
    }

    /// <summary>
    /// Generates normalized Goniometer / Vector Scope (X, Y) coordinates for visualization from live ring buffer.
    /// </summary>
    public void GenerateGoniometerPoints(Span<float> outX, Span<float> outY)
    {
        int copyCount = Math.Min(_livePointsCount, Math.Min(outX.Length, outY.Length));
        _livePointsX.AsSpan(0, copyCount).CopyTo(outX);
        _livePointsY.AsSpan(0, copyCount).CopyTo(outY);
    }

    /// <summary>
    /// Generates normalized Goniometer / Vector Scope (X, Y) coordinates for visualization from explicit buffer.
    /// </summary>
    public void GenerateGoniometerPoints(
        ReadOnlySpan<float> stereoSamples,
        Span<float> outX,
        Span<float> outY)
    {
        int count = Math.Min(stereoSamples.Length / 2, Math.Min(outX.Length, outY.Length));
        for (int i = 0; i < count; i++)
        {
            float l = stereoSamples[i * 2];
            float r = stereoSamples[i * 2 + 1];

            float rotX = (r - l) * 0.70710678f;
            float rotY = -(l + r) * 0.70710678f;

            outX[i] = Compress(rotX);
            outY[i] = Compress(rotY);
        }
    }

    /// <summary>
    /// Generates the cumulative 2D stereo phosphor density cloud (256x256 normalized intensity bytes).
    /// Direct port of XiVero MusicScope's StereoMeterControl.java FFT() normalization algorithm.
    /// </summary>
    public byte[] GenerateDensityCloud()
    {
        int maxCount = 1;
        for (int y = 0; y < DensityGridSize; y++)
        {
            for (int x = 0; x < DensityGridSize; x++)
            {
                // Ignore exact center crosshair where digital silence or DC accumulates (matching StereoMeterControl.java line 82)
                if (Math.Abs(x - 128) <= 1 && Math.Abs(y - 128) <= 1) continue;
                if (_densityGrid[x, y] > maxCount)
                    maxCount = _densityGrid[x, y];
            }
        }

        byte[] cloud = new byte[DensityGridSize * DensityGridSize];
        for (int y = 0; y < DensityGridSize; y++)
        {
            for (int x = 0; x < DensityGridSize; x++)
            {
                int count = _densityGrid[x, y];
                if (count > 0)
                {
                    double norm = 10000.0 * ((double)count / maxCount);
                    double val = 70.0 * Math.Log10(norm + 1.0);
                    cloud[y * DensityGridSize + x] = (byte)Math.Clamp((int)val, 0, 255);
                }
            }
        }
        return cloud;
    }

    /// <summary>
    /// Computes overall cumulative stereo metrics and final phosphor density cloud.
    /// </summary>
    public StereoResult CalculateResult()
    {
        if (_sampleCount == 0)
            return new StereoResult();

        double denom = Math.Sqrt(_sumL2 * _sumR2);
        double overallCorrelation = denom > 1e-9 ? Math.Clamp(_sumLR / denom, -1.0, 1.0) : 1.0;

        double rmsL = Math.Sqrt(_sumL2 / _sampleCount);
        double rmsR = Math.Sqrt(_sumR2 / _sampleCount);
        double balance = (rmsL + rmsR) > 1e-6 ? (rmsR - rmsL) / (rmsL + rmsR) : 0.0;

        double rmsMid = Math.Sqrt(_sumMid2 / _sampleCount);
        double rmsSide = Math.Sqrt(_sumSide2 / _sampleCount);

        double midDb = rmsMid > 1e-6 ? 20.0 * Math.Log10(rmsMid) : -100.0;
        double sideDb = rmsSide > 1e-6 ? 20.0 * Math.Log10(rmsSide) : -100.0;

        return new StereoResult
        {
            Correlation = Math.Round(overallCorrelation, 3),
            Balance = Math.Round(balance, 3),
            MidLevelDb = Math.Round(midDb, 2),
            SideLevelDb = Math.Round(sideDb, 2),
            DensityCloud = GenerateDensityCloud()
        };
    }

    /// <summary>
    /// Resets accumulators and density grid for a new measurement session.
    /// </summary>
    public void Reset()
    {
        _sumL2 = 0.0;
        _sumR2 = 0.0;
        _sumLR = 0.0;
        _sumMid2 = 0.0;
        _sumSide2 = 0.0;
        _sampleCount = 0;
        _smoothedCorrelation = 1.0;
        _livePointsCount = 0;
        Array.Clear(_densityGrid, 0, _densityGrid.Length);
    }
}
