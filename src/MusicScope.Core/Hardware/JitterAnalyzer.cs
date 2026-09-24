using System;
using MusicScope.Core.DSP;

namespace MusicScope.Core.Hardware;

public enum JitterBandwidthMode
{
    Narrow,
    Medium,
    Wide
}

public record JitterResult
{
    /// <summary>
    /// J-Test carrier frequency in Hz (~11025 Hz).
    /// </summary>
    public double CarrierFrequencyHz { get; init; } = 11025.0;

    /// <summary>
    /// Carrier peak power in dBFS.
    /// </summary>
    public double CarrierPowerDb { get; init; } = 0.0;

    /// <summary>
    /// Total detected jitter amplitude in picoseconds (ps RMS).
    /// </summary>
    public double JitterPsRms { get; init; } = 0.0;

    /// <summary>
    /// Worst-case jitter peak spur amplitude relative to carrier in dBc.
    /// </summary>
    public double PeakSpurDbc { get; init; } = -120.0;

    /// <summary>
    /// Frequency of the largest jitter sideband spur in Hz.
    /// </summary>
    public double PeakSpurFrequencyHz { get; init; } = 0.0;
}

/// <summary>
/// Jitter Analyzer implementing Julian Dunn J-Test sideband extraction.
/// Directly reflects MusicScope's JitterAnalyser module.
/// </summary>
public sealed class JitterAnalyzer
{
    private readonly double _sampleRate;
    private readonly int _fftSize;
    private readonly FastFourierTransform _fft;
    private readonly double[] _window;

    public JitterAnalyzer(double sampleRate = 44100, int fftSize = 16384)
    {
        _sampleRate = sampleRate;
        _fftSize = fftSize;
        _fft = new FastFourierTransform(fftSize);
        _window = WindowFunctions.Create(WindowType.BlackmanHarris, fftSize);
    }

    /// <summary>
    /// Analyzes an audio buffer containing the 11.025 kHz J-Test stimulus.
    /// </summary>
    public JitterResult Analyze(ReadOnlySpan<double> samples, JitterBandwidthMode mode = JitterBandwidthMode.Medium)
    {
        if (samples.Length < _fftSize)
            return new JitterResult();

        double[] real = new double[_fftSize];
        double[] imag = new double[_fftSize];

        for (int i = 0; i < _fftSize; i++)
        {
            real[i] = samples[i] * _window[i];
            imag[i] = 0.0;
        }

        _fft.Forward(real, imag);

        int halfSize = _fftSize / 2;
        double[] powerSpectrum = new double[halfSize];
        for (int i = 0; i < halfSize; i++)
        {
            powerSpectrum[i] = real[i] * real[i] + imag[i] * imag[i];
        }

        // 1. Locate carrier peak near 11025 Hz (fs / 4)
        double targetCarrierHz = _sampleRate / 4.0;
        int centerBin = _fft.GetBinForFrequency(targetCarrierHz, _sampleRate);
        int searchRadius = _fft.GetBinForFrequency(500, _sampleRate);

        int minB = Math.Max(0, centerBin - searchRadius);
        int maxB = Math.Min(halfSize - 1, centerBin + searchRadius);

        int carrierBin = centerBin;
        double maxCarrierPower = 0.0;
        for (int b = minB; b <= maxB; b++)
        {
            if (powerSpectrum[b] > maxCarrierPower)
            {
                maxCarrierPower = powerSpectrum[b];
                carrierBin = b;
            }
        }

        double carrierFreqHz = _fft.GetFrequencyForBin(carrierBin, _sampleRate);
        if (maxCarrierPower < 1e-12)
        {
            return new JitterResult { CarrierFrequencyHz = carrierFreqHz };
        }

        // Carrier power (peak +/- 2 bins)
        double carrierPowerSum = 0.0;
        for (int b = Math.Max(0, carrierBin - 2); b <= Math.Min(halfSize - 1, carrierBin + 2); b++)
        {
            carrierPowerSum += powerSpectrum[b];
        }

        // 2. Sideband search bandwidth around carrier
        double sidebandSpanHz = mode switch
        {
            JitterBandwidthMode.Narrow => 1000.0,
            JitterBandwidthMode.Wide => 5000.0,
            _ => 2500.0 // Medium
        };

        int sidebandRadiusBins = _fft.GetBinForFrequency(sidebandSpanHz, _sampleRate);
        int startSideBin = Math.Max(0, carrierBin - sidebandRadiusBins);
        int endSideBin = Math.Min(halfSize - 1, carrierBin + sidebandRadiusBins);

        double sumSidebandPower = 0.0;
        double maxSpurPower = 0.0;
        int maxSpurBin = 0;

        for (int b = startSideBin; b <= endSideBin; b++)
        {
            // Exclude carrier notch (carrierBin +/- 3 bins)
            if (Math.Abs(b - carrierBin) <= 3)
                continue;

            double p = powerSpectrum[b];
            sumSidebandPower += p;

            if (p > maxSpurPower)
            {
                maxSpurPower = p;
                maxSpurBin = b;
            }
        }

        // Jitter amplitude calculation (in picoseconds):
        // Jitter (seconds) = (1 / (pi * f_carrier)) * sqrt(P_sideband / P_carrier)
        double sidebandRatio = carrierPowerSum > 0 ? sumSidebandPower / carrierPowerSum : 0.0;
        double jitterSec = (1.0 / (Math.PI * carrierFreqHz)) * Math.Sqrt(sidebandRatio);
        double jitterPs = jitterSec * 1e12; // convert to picoseconds

        double peakSpurRatio = carrierPowerSum > 0 ? maxSpurPower / carrierPowerSum : 0.0;
        double peakSpurDbc = peakSpurRatio > 1e-12 ? 10.0 * Math.Log10(peakSpurRatio) : -140.0;
        double peakSpurFreq = maxSpurBin > 0 ? _fft.GetFrequencyForBin(maxSpurBin, _sampleRate) : 0.0;

        double carrierDb = 10.0 * Math.Log10(carrierPowerSum / (_fftSize * _fftSize));

        return new JitterResult
        {
            CarrierFrequencyHz = Math.Round(carrierFreqHz, 1),
            CarrierPowerDb = Math.Round(carrierDb, 1),
            JitterPsRms = Math.Round(jitterPs, 1),
            PeakSpurDbc = Math.Round(peakSpurDbc, 1),
            PeakSpurFrequencyHz = Math.Round(peakSpurFreq, 1)
        };
    }
}
