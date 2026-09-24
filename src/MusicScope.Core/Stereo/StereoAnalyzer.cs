using System;

namespace MusicScope.Core.Stereo;

/// <summary>
/// Precision Stereo Phase Correlation, Mid/Side balance, and Goniometer Analyzer.
/// Directly reflects the measurement logic in MusicScope's StereoMeterControl.
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

    /// <summary>
    /// Current smoothed real-time correlation value [-1.0, +1.0].
    /// </summary>
    public double RealtimeCorrelation => _smoothedCorrelation;

    /// <summary>
    /// Processes interleaved stereo samples [L0, R0, L1, R1, ...].
    /// </summary>
    public void ProcessInterleaved(ReadOnlySpan<double> stereoSamples)
    {
        int frameCount = stereoSamples.Length / 2;
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
    }

    /// <summary>
    /// Processes interleaved float stereo samples.
    /// </summary>
    public void ProcessInterleaved(ReadOnlySpan<float> stereoSamples)
    {
        int frameCount = stereoSamples.Length / 2;
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

        double denom = Math.Sqrt(frameSumL2 * frameSumR2);
        if (denom > 1e-9)
        {
            double instantCorr = Math.Clamp(frameSumLR / denom, -1.0, 1.0);
            _smoothedCorrelation = _smoothedCorrelation * (1.0 - SmoothingFactor) + instantCorr * SmoothingFactor;
        }
    }

    /// <summary>
    /// Generates normalized Goniometer / Vector Scope (X, Y) coordinates for visualization.
    /// X is Side (Stereo width), Y is Mid (Mono sum).
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

            // 45 degree rotation: X = (L - R) / sqrt(2), Y = (L + R) / sqrt(2)
            outX[i] = (l - r) * 0.70710678f;
            outY[i] = (l + r) * 0.70710678f;
        }
    }

    /// <summary>
    /// Computes overall cumulative stereo metrics.
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
            SideLevelDb = Math.Round(sideDb, 2)
        };
    }

    /// <summary>
    /// Resets accumulators for a new measurement session.
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
    }
}
