using System;

namespace MusicScope.Core.DSP;

/// <summary>
/// Implements the two-stage K-weighting filter for ITU-R BS.1770-4 / EBU R128 compliance.
/// Stage 1: Pre-filter (high-shelf filter simulating head acoustic effects, ~1500 Hz, +4 dB gain).
/// Stage 2: RLB weighting filter (high-pass filter simulating human ear low-frequency roll-off, ~50 Hz).
/// Derived directly from the XiVero MusicScope iirFilter implementation.
/// </summary>
public sealed class KWeightingFilter
{
    private readonly double _sampleRate;
    private readonly int _channelCount;

    // Filter coefficients for Stage 1 (High-shelf)
    private double _s1B0, _s1B1, _s1B2;
    private double _s1A1, _s1A2;

    // Filter coefficients for Stage 2 (High-pass)
    private double _s2B0, _s2B1, _s2B2;
    private double _s2A1, _s2A2;

    // Filter state delays for each channel: [channel][delay_index]
    // Direct Form II Transposed delays: d1, d2
    private readonly double[] _s1D1;
    private readonly double[] _s1D2;
    private readonly double[] _s2D1;
    private readonly double[] _s2D2;

    public KWeightingFilter(double sampleRate, int channelCount = 2)
    {
        if (sampleRate <= 0)
            throw new ArgumentOutOfRangeException(nameof(sampleRate), "Sample rate must be positive.");
        if (channelCount <= 0)
            throw new ArgumentOutOfRangeException(nameof(channelCount), "Channel count must be positive.");

        _sampleRate = sampleRate;
        _channelCount = channelCount;

        _s1D1 = new double[channelCount];
        _s1D2 = new double[channelCount];
        _s2D1 = new double[channelCount];
        _s2D2 = new double[channelCount];

        CalculateCoefficients();
    }

    private void CalculateCoefficients()
    {
        // Stage 1: High-shelf pre-filter (f0 = 1500 Hz, gain = +4 dB, Q = 1 / sqrt(2))
        double q1 = 1.0 / Math.Sqrt(2.0);
        double f0_1 = 1500.0;
        double gainDb = 4.0;
        double a = Math.Pow(10.0, gainDb / 40.0);
        double w0_1 = 2.0 * Math.PI * f0_1 / _sampleRate;
        double alpha1 = Math.Sin(w0_1) / (2.0 * q1);
        double cosW0_1 = Math.Cos(w0_1);

        double b0_1 = a * ((a + 1.0) + (a - 1.0) * cosW0_1 + 2.0 * Math.Sqrt(a) * alpha1);
        double b1_1 = -2.0 * a * ((a - 1.0) + (a + 1.0) * cosW0_1);
        double b2_1 = a * ((a + 1.0) + (a - 1.0) * cosW0_1 - 2.0 * Math.Sqrt(a) * alpha1);
        double a0_1 = (a + 1.0) - (a - 1.0) * cosW0_1 + 2.0 * Math.Sqrt(a) * alpha1;
        double a1_1 = 2.0 * ((a - 1.0) - (a + 1.0) * cosW0_1);
        double a2_1 = (a + 1.0) - (a - 1.0) * cosW0_1 - 2.0 * Math.Sqrt(a) * alpha1;

        _s1B0 = b0_1 / a0_1;
        _s1B1 = b1_1 / a0_1;
        _s1B2 = b2_1 / a0_1;
        _s1A1 = a1_1 / a0_1;
        _s1A2 = a2_1 / a0_1;

        // Stage 2: High-pass RLB filter (f0 = 50 Hz, Q = 0.6)
        double q2 = 0.6;
        double f0_2 = 50.0;
        double w0_2 = 2.0 * Math.PI * f0_2 / _sampleRate;
        double alpha2 = Math.Sin(w0_2) / (2.0 * q2);
        double cosW0_2 = Math.Cos(w0_2);

        double b0_2 = (1.0 + cosW0_2) / 2.0;
        double b1_2 = -(1.0 + cosW0_2);
        double b2_2 = (1.0 + cosW0_2) / 2.0;
        double a0_2 = 1.0 + alpha2;
        double a1_2 = -2.0 * cosW0_2;
        double a2_2 = 1.0 - alpha2;

        _s2B0 = b0_2 / a0_2;
        _s2B1 = b1_2 / a0_2;
        _s2B2 = b2_2 / a0_2;
        _s2A1 = a1_2 / a0_2;
        _s2A2 = a2_2 / a0_2;
    }

    /// <summary>
    /// Processes a single sample for a specific channel through the cascaded K-weighting filters.
    /// </summary>
    [System.Runtime.CompilerServices.MethodImpl(System.Runtime.CompilerServices.MethodImplOptions.AggressiveInlining)]
    public double ProcessSample(int channel, double input)
    {
        // Stage 1: High-shelf filter (Direct Form II Transposed)
        double stage1Out = _s1B0 * input + _s1D1[channel];
        _s1D1[channel] = _s1B1 * input - _s1A1 * stage1Out + _s1D2[channel];
        _s1D2[channel] = _s1B2 * input - _s1A2 * stage1Out;

        // Stage 2: High-pass filter (Direct Form II Transposed)
        double stage2Out = _s2B0 * stage1Out + _s2D1[channel];
        _s2D1[channel] = _s2B1 * stage1Out - _s2A1 * stage2Out + _s2D2[channel];
        _s2D2[channel] = _s2B2 * stage1Out - _s2A2 * stage2Out;

        return stage2Out;
    }

    /// <summary>
    /// Fast inlined stereo K-weighting filter avoiding array index and bounds overhead.
    /// </summary>
    [System.Runtime.CompilerServices.MethodImpl(System.Runtime.CompilerServices.MethodImplOptions.AggressiveInlining)]
    public void ProcessStereoSample(double inL, double inR, out double outL, out double outR)
    {
        // Stage 1: Left
        double s1OutL = _s1B0 * inL + _s1D1[0];
        _s1D1[0] = _s1B1 * inL - _s1A1 * s1OutL + _s1D2[0];
        _s1D2[0] = _s1B2 * inL - _s1A2 * s1OutL;

        // Stage 2: Left
        outL = _s2B0 * s1OutL + _s2D1[0];
        _s2D1[0] = _s2B1 * s1OutL - _s2A1 * outL + _s2D2[0];
        _s2D2[0] = _s2B2 * s1OutL - _s2A2 * outL;

        // Stage 1: Right
        double s1OutR = _s1B0 * inR + _s1D1[1];
        _s1D1[1] = _s1B1 * inR - _s1A1 * s1OutR + _s1D2[1];
        _s1D2[1] = _s1B2 * inR - _s1A2 * s1OutR;

        // Stage 2: Right
        outR = _s2B0 * s1OutR + _s2D1[1];
        _s2D1[1] = _s2B1 * s1OutR - _s2A1 * outR + _s2D2[1];
        _s2D2[1] = _s2B2 * s1OutR - _s2A2 * outR;
    }

    /// <summary>
    /// Filters an in-place buffer of samples for a specific channel.
    /// </summary>
    public void ProcessChannel(int channel, Span<double> samples)
    {
        for (int i = 0; i < samples.Length; i++)
        {
            samples[i] = ProcessSample(channel, samples[i]);
        }
    }

    /// <summary>
    /// Resets all internal delay states across all channels.
    /// </summary>
    public void Reset()
    {
        Array.Clear(_s1D1, 0, _s1D1.Length);
        Array.Clear(_s1D2, 0, _s1D2.Length);
        Array.Clear(_s2D1, 0, _s2D1.Length);
        Array.Clear(_s2D2, 0, _s2D2.Length);
    }
}
