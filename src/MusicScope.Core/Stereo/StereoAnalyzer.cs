using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Runtime.Intrinsics;

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

    // 2D Density Histogram Grid (256x256 flattened row-major [x * 256 + y] for cache-friendly updates)
    public const int DensityGridSize = 256;
    private readonly int[] _densityGrid = new int[DensityGridSize * DensityGridSize];

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
                _densityGrid[gx * DensityGridSize + gy]++;
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
    [MethodImpl(MethodImplOptions.AggressiveOptimization)]
    public void ProcessInterleaved(ReadOnlySpan<float> stereoSamples)
    {
        int frameCount = stereoSamples.Length / 2;
        if (frameCount == 0) return;

        double frameSumL2 = 0.0;
        double frameSumR2 = 0.0;
        double frameSumLR = 0.0;

        int i = 0;
        if (Vector256.IsHardwareAccelerated && frameCount >= 4)
        {
            ref float pSamples = ref MemoryMarshal.GetReference(stereoSamples);
            var vSumL2 = Vector256<double>.Zero;
            var vSumR2 = Vector256<double>.Zero;
            var vSumLR = Vector256<double>.Zero;
            var deinterleaveIdx = Vector256.Create(0, 2, 4, 6, 1, 3, 5, 7);

            int vecEnd = frameCount - (frameCount & 3);
            for (; i < vecEnd; i += 4)
            {
                var v = Vector256.LoadUnsafe(ref pSamples, (nuint)(i << 1));
                var deint = Vector256.Shuffle(v, deinterleaveIdx);
                var vl = Vector256.WidenLower(deint);
                var vr = Vector256.WidenUpper(deint);

                vSumL2 = Vector256.FusedMultiplyAdd(vl, vl, vSumL2);
                vSumR2 = Vector256.FusedMultiplyAdd(vr, vr, vSumR2);
                vSumLR = Vector256.FusedMultiplyAdd(vl, vr, vSumLR);
            }

            frameSumL2 = Vector256.Sum(vSumL2);
            frameSumR2 = Vector256.Sum(vSumR2);
            frameSumLR = Vector256.Sum(vSumLR);
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
        int[] grid = _densityGrid;
        int dIdx = 0;
        if (Vector256.IsHardwareAccelerated && frameCount >= 8)
        {
            ref float pSamples = ref MemoryMarshal.GetReference(stereoSamples);
            var pickEvenFrames = Vector256.Create(0, 4, 1, 5, 0, 4, 1, 5);
            var groupChannels = Vector256.Create(0, 1, 4, 5, 2, 3, 6, 7);
            var vInvSqrt2 = Vector128.Create(0.70710678f);
            var vNegInvSqrt2 = Vector128.Create(-0.70710678f);
            var vFive = Vector256.Create(5f);
            var vOne = Vector256.Create(1f);
            var vScale = Vector256.Create(140f);
            var vCenter = Vector256.Create(128f);
            var vSignMask = Vector256.Create(0x80000000u);
            var vAbsMask = Vector256.Create(0x7FFFFFFFu);
            var vMantMask = Vector256.Create(0x007FFFFF);
            var vExpOne = Vector256.Create(0x3F800000);
            var vExpBias = Vector256.Create(127);
            var vLog10_2 = Vector256.Create(0.3010299956639812f);
            var vTwoLog10_E = Vector256.Create(0.8685889638065037f); // 2 / ln(10)
            var vC3 = Vector256.Create(1f / 3f);
            var vC5 = Vector256.Create(1f / 5f);
            var vC7 = Vector256.Create(1f / 7f);
            var vC9 = Vector256.Create(1f / 9f);

            int vecEndK = frameCount - (frameCount & 7);
            for (; dIdx < vecEndK; dIdx += 8)
            {
                var v0 = Vector256.LoadUnsafe(ref pSamples, (nuint)(dIdx << 1));
                var v1 = Vector256.LoadUnsafe(ref pSamples, (nuint)((dIdx + 4) << 1));
                var s0 = Vector256.Shuffle(v0, pickEvenFrames).GetLower();
                var s1 = Vector256.Shuffle(v1, pickEvenFrames).GetLower();
                var grouped = Vector256.Shuffle(Vector256.Create(s0, s1), groupChannels);
                var vL = grouped.GetLower();
                var vR = grouped.GetUpper();

                var rotX128 = (vR - vL) * vInvSqrt2;
                var rotY128 = (vL + vR) * vNegInvSqrt2;
                var rot = Vector256.Create(rotX128, rotY128);

                var signBits = rot.AsUInt32() & vSignMask;
                var absRot = (rot.AsUInt32() & vAbsMask).AsSingle();
                var x = Vector256.FusedMultiplyAdd(vFive, absRot, vOne);

                var xBits = x.AsInt32();
                var exp = Vector256.ConvertToSingle((xBits >>> 23) - vExpBias);
                var m = ((xBits & vMantMask) | vExpOne).AsSingle();
                var u = (m - vOne) / (m + vOne);
                var u2 = u * u;
                var poly = Vector256.FusedMultiplyAdd(u2, vC9, vC7);
                poly = Vector256.FusedMultiplyAdd(u2, poly, vC5);
                poly = Vector256.FusedMultiplyAdd(u2, poly, vC3);
                poly = Vector256.FusedMultiplyAdd(u2, poly, vOne);
                var log10 = Vector256.FusedMultiplyAdd(exp, vLog10_2, u * vTwoLog10_E * poly);

                var comp = (log10.AsUInt32() | signBits).AsSingle();
                var coords = Vector256.ConvertToInt32(Vector256.FusedMultiplyAdd(comp, vScale, vCenter));

                for (int lane = 0; lane < 4; lane++)
                {
                    int gx = coords.GetElement(lane);
                    int gy = coords.GetElement(lane + 4);
                    if ((uint)gx < DensityGridSize && (uint)gy < DensityGridSize)
                    {
                        grid[gx * DensityGridSize + gy]++;
                    }
                }
            }
        }

        for (; dIdx < frameCount; dIdx += 2)
        {
            float l = stereoSamples[dIdx * 2];
            float r = stereoSamples[dIdx * 2 + 1];

            float rotX = (r - l) * 0.70710678f;
            float rotY = -(l + r) * 0.70710678f;

            float compX = Compress(rotX);
            float compY = Compress(rotY);

            int gx = (int)(128f + compX * 140f);
            int gy = (int)(128f + compY * 140f);

            if ((uint)gx < DensityGridSize && (uint)gy < DensityGridSize)
            {
                grid[gx * DensityGridSize + gy]++;
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
        int[] grid = _densityGrid;
        for (int x = 0; x < DensityGridSize; x++)
        {
            int colBase = x * DensityGridSize;
            for (int y = 0; y < DensityGridSize; y++)
            {
                // Ignore exact center crosshair where digital silence or DC accumulates (matching StereoMeterControl.java line 82)
                if (Math.Abs(x - 128) <= 1 && Math.Abs(y - 128) <= 1) continue;
                int count = grid[colBase + y];
                if (count > maxCount)
                    maxCount = count;
            }
        }

        byte[] cloud = new byte[DensityGridSize * DensityGridSize];
        for (int y = 0; y < DensityGridSize; y++)
        {
            for (int x = 0; x < DensityGridSize; x++)
            {
                int count = grid[x * DensityGridSize + y];
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
        Array.Clear(_densityGrid);
    }
}
