using System;
using MusicScope.Core.DSP;
using MusicScope.Core.Levels;
using MusicScope.Core.Loudness;
using MusicScope.Core.Stereo;

namespace MusicScope.Core;

/// <summary>
/// Core real-time and offline audio analysis processor.
/// Feeds audio streams through the complete DSP pipeline:
/// K-weighting, EBU R128 Loudness, 4x True Peak FIR, Stereo Correlation, and FFT Spectrum.
/// </summary>
public sealed class AudioAnalysisEngine
{
    private readonly double _sampleRate;
    private readonly int _channelCount;

    private readonly LoudnessMeter _loudnessMeter;
    private readonly TruePeakMeter _truePeakMeter;
    private readonly StereoAnalyzer _stereoAnalyzer;
    private readonly FastFourierTransform _fft;
    private readonly double[] _fftWindow;

    private const int FftSize = 4096;
    private readonly double[] _fftRealBuffer = new double[FftSize];
    private readonly double[] _fftImagBuffer = new double[FftSize];
    private readonly double[] _accumulatedSpectrum = new double[FftSize / 2];
    private int _spectrumFftCount;

    public AudioAnalysisEngine(double sampleRate = 44100.0, int channelCount = 2)
    {
        _sampleRate = sampleRate;
        _channelCount = channelCount;

        _loudnessMeter = new LoudnessMeter(sampleRate, channelCount);
        _truePeakMeter = new TruePeakMeter(channelCount);
        _stereoAnalyzer = new StereoAnalyzer();

        _fft = new FastFourierTransform(FftSize);
        _fftWindow = WindowFunctions.Create(WindowType.BlackmanHarris, FftSize);
    }

    /// <summary>
    /// Processes an interleaved audio block (float).
    /// </summary>
    public void ProcessAudioBlock(ReadOnlySpan<float> interleavedSamples)
    {
        _loudnessMeter.ProcessInterleaved(interleavedSamples);
        _truePeakMeter.ProcessInterleaved(interleavedSamples);

        if (_channelCount >= 2)
        {
            _stereoAnalyzer.ProcessInterleaved(interleavedSamples);
        }

        // Perform periodic FFT on mono downmix
        int frameCount = interleavedSamples.Length / _channelCount;
        for (int i = 0; i < frameCount; i += FftSize)
        {
            int blockLen = Math.Min(FftSize, frameCount - i);
            if (blockLen < FftSize)
                break;

            for (int k = 0; k < FftSize; k++)
            {
                int sampleIdx = (i + k) * _channelCount;
                float mono = 0f;
                for (int ch = 0; ch < _channelCount; ch++)
                {
                    mono += interleavedSamples[sampleIdx + ch];
                }
                mono /= _channelCount;

                _fftRealBuffer[k] = mono * _fftWindow[k];
                _fftImagBuffer[k] = 0.0;
            }

            _fft.Forward(_fftRealBuffer, _fftImagBuffer);

            // Accumulate power spectrum
            for (int b = 0; b < FftSize / 2; b++)
            {
                double magSq = _fftRealBuffer[b] * _fftRealBuffer[b] + _fftImagBuffer[b] * _fftImagBuffer[b];
                _accumulatedSpectrum[b] += magSq;
            }
            _spectrumFftCount++;
        }
    }

    /// <summary>
    /// Generates the complete analysis report.
    /// </summary>
    public FullAnalysisReport GenerateReport(string title = "", string filePath = "", string format = "", TimeSpan duration = default, int bitDepth = 16)
    {
        var loudnessResult = _loudnessMeter.CalculateResult();
        var levelsResult = _truePeakMeter.CalculateResult();
        var stereoResult = _channelCount >= 2 ? _stereoAnalyzer.CalculateResult() : new StereoResult();

        double[] finalSpectrumDb = new double[FftSize / 2];
        double scale = _spectrumFftCount > 0 ? 1.0 / _spectrumFftCount : 1.0;
        double normScale = 2.0 / FftSize;

        for (int b = 0; b < FftSize / 2; b++)
        {
            double avgMag = Math.Sqrt(_accumulatedSpectrum[b] * scale) * normScale;
            finalSpectrumDb[b] = avgMag > 1e-7 ? Math.Max(-140.0, 20.0 * Math.Log10(avgMag)) : -140.0;
        }

        return new FullAnalysisReport
        {
            Title = title,
            FilePath = filePath,
            Format = format,
            SampleRate = _sampleRate,
            Channels = _channelCount,
            BitDepth = bitDepth,
            Duration = duration,
            Loudness = loudnessResult,
            Levels = levelsResult,
            Stereo = stereoResult,
            SpectrumMagnitudesDb = finalSpectrumDb
        };
    }

    /// <summary>
    /// Resets all internal meters.
    /// </summary>
    public void Reset()
    {
        _loudnessMeter.Reset();
        _truePeakMeter.Reset();
        _stereoAnalyzer.Reset();
        Array.Clear(_accumulatedSpectrum, 0, _accumulatedSpectrum.Length);
        _spectrumFftCount = 0;
    }
}
