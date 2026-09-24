using System;
using System.Collections.Generic;
using MusicScope.Core.DSP;

namespace MusicScope.Core.Hardware;

/// <summary>
/// Encapsulates the results of a Total Harmonic Distortion (THD) measurement.
/// </summary>
public record ThdResult
{
    /// <summary>
    /// Fundamental frequency in Hz (typically ~1000 Hz).
    /// </summary>
    public double FundamentalHz { get; init; } = 1000.0;

    /// <summary>
    /// Fundamental signal amplitude in dBFS.
    /// </summary>
    public double FundamentalDb { get; init; } = 0.0;

    /// <summary>
    /// Total Harmonic Distortion percentage (%).
    /// </summary>
    public double ThdPercent { get; init; } = 0.0;

    /// <summary>
    /// THD attenuation in dB (e.g. -80 dB).
    /// </summary>
    public double ThdAttenuationDb { get; init; } = -120.0;

    /// <summary>
    /// THD + Noise percentage (%).
    /// </summary>
    public double ThdPlusNoisePercent { get; init; } = 0.0;

    /// <summary>
    /// Signal to Noise and Distortion Ratio (SINAD) in dB.
    /// </summary>
    public double SinadDb { get; init; } = 0.0;

    /// <summary>
    /// Amplitudes of individual harmonics (h2, h3, ..., h10) relative to fundamental in dB.
    /// </summary>
    public double[] HarmonicsDb { get; init; } = [];
}

/// <summary>
/// Professional THD Analyzer utilizing 1 kHz sine stimulus.
/// Replicates the measurement logic from MusicScope's THDAnalyser.
/// </summary>
public sealed class ThdAnalyzer
{
    private readonly double _sampleRate;
    private readonly int _fftSize;
    private readonly FastFourierTransform _fft;
    private readonly double[] _window;

    public ThdAnalyzer(double sampleRate = 48000, int fftSize = 16384)
    {
        _sampleRate = sampleRate;
        _fftSize = fftSize;
        _fft = new FastFourierTransform(fftSize);
        _window = WindowFunctions.Create(WindowType.BlackmanHarris, fftSize);
    }

    /// <summary>
    /// Analyzes an audio buffer containing a 1 kHz test tone to compute THD and harmonics.
    /// </summary>
    public ThdResult Analyze(ReadOnlySpan<double> samples)
    {
        if (samples.Length < _fftSize)
            return new ThdResult();

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

        // 1. Locate fundamental peak around 1000 Hz (+/- 10%)
        int searchMinBin = _fft.GetBinForFrequency(900, _sampleRate);
        int searchMaxBin = _fft.GetBinForFrequency(1100, _sampleRate);

        int fundBin = searchMinBin;
        double maxFundPower = 0.0;
        for (int b = searchMinBin; b <= searchMaxBin && b < halfSize; b++)
        {
            if (powerSpectrum[b] > maxFundPower)
            {
                maxFundPower = powerSpectrum[b];
                fundBin = b;
            }
        }

        double fundFreqHz = _fft.GetFrequencyForBin(fundBin, _sampleRate);
        if (maxFundPower < 1e-12)
        {
            return new ThdResult { FundamentalHz = fundFreqHz };
        }

        // Integrate power around fundamental (peak +/- 2 bins)
        double fundPowerSum = SumPowerInBinBand(powerSpectrum, fundBin, 2);

        // 2. Identify harmonics h2 through h10
        double sumHarmonicPower = 0.0;
        List<double> harmonicsDb = new(9);

        for (int h = 2; h <= 10; h++)
        {
            double harmonicFreq = fundFreqHz * h;
            if (harmonicFreq >= _sampleRate / 2.0)
                break;

            int harmonicCenterBin = _fft.GetBinForFrequency(harmonicFreq, _sampleRate);
            // Search local max in +/- 2 bins
            int hPeakBin = FindLocalPeak(powerSpectrum, harmonicCenterBin, 2);
            double hPower = SumPowerInBinBand(powerSpectrum, hPeakBin, 1);

            sumHarmonicPower += hPower;

            double hRatio = fundPowerSum > 0 ? hPower / fundPowerSum : 0.0;
            double hDb = hRatio > 1e-12 ? 10.0 * Math.Log10(hRatio) : -120.0;
            harmonicsDb.Add(Math.Round(hDb, 1));
        }

        // 3. Compute THD percentage and attenuation
        double thdRatio = fundPowerSum > 0 ? Math.Sqrt(sumHarmonicPower / fundPowerSum) : 0.0;
        double thdPercent = thdRatio * 100.0;
        double thdAttenDb = thdRatio > 1e-8 ? 20.0 * Math.Log10(thdRatio) : -140.0;

        // 4. Compute THD+N
        double totalPower = 0.0;
        int lowBinCutoff = _fft.GetBinForFrequency(20, _sampleRate);
        for (int b = lowBinCutoff; b < halfSize; b++)
        {
            totalPower += powerSpectrum[b];
        }
        double noiseAndHarmonicsPower = Math.Max(0.0, totalPower - fundPowerSum);
        double thdPlusNoiseRatio = fundPowerSum > 0 ? Math.Sqrt(noiseAndHarmonicsPower / fundPowerSum) : 0.0;
        double sinadDb = thdPlusNoiseRatio > 1e-8 ? -20.0 * Math.Log10(thdPlusNoiseRatio) : 100.0;

        double fundAmpDb = 10.0 * Math.Log10(fundPowerSum / (_fftSize * _fftSize));

        return new ThdResult
        {
            FundamentalHz = Math.Round(fundFreqHz, 1),
            FundamentalDb = Math.Round(fundAmpDb, 1),
            ThdPercent = Math.Round(thdPercent, 4),
            ThdAttenuationDb = Math.Round(thdAttenDb, 1),
            ThdPlusNoisePercent = Math.Round(thdPlusNoiseRatio * 100.0, 3),
            SinadDb = Math.Round(sinadDb, 1),
            HarmonicsDb = harmonicsDb.ToArray()
        };
    }

    private static int FindLocalPeak(double[] spectrum, int centerBin, int radius)
    {
        int minB = Math.Max(0, centerBin - radius);
        int maxB = Math.Min(spectrum.Length - 1, centerBin + radius);
        int peak = centerBin;
        double maxVal = 0.0;
        for (int b = minB; b <= maxB; b++)
        {
            if (spectrum[b] > maxVal)
            {
                maxVal = spectrum[b];
                peak = b;
            }
        }
        return peak;
    }

    private static double SumPowerInBinBand(double[] spectrum, int centerBin, int radius)
    {
        int minB = Math.Max(0, centerBin - radius);
        int maxB = Math.Min(spectrum.Length - 1, centerBin + radius);
        double sum = 0.0;
        for (int b = minB; b <= maxB; b++)
        {
            sum += spectrum[b];
        }
        return sum;
    }
}
