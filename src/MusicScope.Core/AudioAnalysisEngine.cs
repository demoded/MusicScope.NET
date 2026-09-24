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

    private const int FftSize = 2048;
    private const int FftStride = 2048; // Compute FFT every 2048 audio frames (~46ms at 44.1kHz)
    private int _framesSinceLastFft;
    private readonly double[] _fftRealBuffer = new double[FftSize];
    private readonly double[] _fftImagBuffer = new double[FftSize];
    private readonly double[] _accumulatedSpectrum = new double[FftSize / 2];
    private readonly double[] _latestInstantSpectrumDb = new double[FftSize / 2];
    private readonly double[] _peakHoldSpectrumDb = new double[FftSize / 2];
    private int _spectrumFftCount;

    private readonly float[] _lastInterleavedBlock = new float[1024];
    private int _lastInterleavedCount;
    private readonly float[] _goniometerX = new float[256];
    private readonly float[] _goniometerY = new float[256];

    public AudioAnalysisEngine(double sampleRate = 44100.0, int channelCount = 2)
    {
        _sampleRate = sampleRate;
        _channelCount = channelCount;

        _loudnessMeter = new LoudnessMeter(sampleRate, channelCount);
        _truePeakMeter = new TruePeakMeter(channelCount);
        _stereoAnalyzer = new StereoAnalyzer();

        _fft = new FastFourierTransform(FftSize);
        _fftWindow = WindowFunctions.Create(WindowType.BlackmanHarris, FftSize);
        Array.Fill(_latestInstantSpectrumDb, -140.0);
        Array.Fill(_peakHoldSpectrumDb, -140.0);
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

            // Cache latest samples for on-demand goniometer calculation
            int copyLen = Math.Min(interleavedSamples.Length, _lastInterleavedBlock.Length);
            interleavedSamples.Slice(0, copyLen).CopyTo(_lastInterleavedBlock);
            _lastInterleavedCount = copyLen;
        }

        // Perform periodic FFT on mono downmix
        int frameCount = interleavedSamples.Length / _channelCount;
        for (int i = 0; i < frameCount; i++)
        {
            _framesSinceLastFft++;
            if (_framesSinceLastFft >= FftStride && (i + FftSize) <= frameCount)
            {
                _framesSinceLastFft = 0;

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

                // Blackman-Harris coherent gain is 0.35875
                double scale = 2.0 / (FftSize * 0.35875);
                for (int b = 0; b < FftSize / 2; b++)
                {
                    double magSq = _fftRealBuffer[b] * _fftRealBuffer[b] + _fftImagBuffer[b] * _fftImagBuffer[b];
                    _accumulatedSpectrum[b] += magSq;

                    double instantMag = Math.Sqrt(magSq) * scale;
                    double instantDb = instantMag > 1e-7 ? Math.Max(-140.0, 20.0 * Math.Log10(instantMag)) : -140.0;
                    
                    // Instantaneous smoothed display for live dancing
                    _latestInstantSpectrumDb[b] = _latestInstantSpectrumDb[b] * 0.4 + instantDb * 0.6;

                    // Cumulative peak hold across the entire track
                    if (instantDb > _peakHoldSpectrumDb[b])
                    {
                        _peakHoldSpectrumDb[b] = instantDb;
                    }
                }
                _spectrumFftCount++;
                i += FftSize - 1; // Advance past this FFT frame
            }
        }
    }

    /// <summary>
    /// Captures a real-time snapshot of all current metrics, levels, spectrum, and scope.
    /// </summary>
    public AudioRealtimeSnapshot GetRealtimeSnapshot(double progress = 0.0)
    {
        double[] spectrumCopy = new double[FftSize / 2];
        Array.Copy(_latestInstantSpectrumDb, spectrumCopy, spectrumCopy.Length);

        double[] peakHoldCopy = new double[FftSize / 2];
        Array.Copy(_peakHoldSpectrumDb, peakHoldCopy, peakHoldCopy.Length);

        float[] gonioX = new float[_goniometerX.Length];
        float[] gonioY = new float[_goniometerY.Length];

        if (_channelCount >= 2 && _lastInterleavedCount > 0)
        {
            _stereoAnalyzer.GenerateGoniometerPoints(_lastInterleavedBlock.AsSpan(0, _lastInterleavedCount), _goniometerX, _goniometerY);
        }

        Array.Copy(_goniometerX, gonioX, gonioX.Length);
        Array.Copy(_goniometerY, gonioY, gonioY.Length);

        return new AudioRealtimeSnapshot
        {
            ProgressFraction = progress,
            MomentaryLufs = _loudnessMeter.CurrentMomentaryLufs,
            ShortTermLufs = _loudnessMeter.CurrentShortTermLufs,
            RunningIntegratedLufs = _loudnessMeter.CurrentShortTermLufs, // during live stream, short-term represents the current perceived level
            CurrentPeakLeftDb = _truePeakMeter.CurrentBlockPeakLeftDb,
            CurrentPeakRightDb = _truePeakMeter.CurrentBlockPeakRightDb,
            CurrentRmsLeftDb = _truePeakMeter.CurrentBlockRmsLeftDb,
            CurrentRmsRightDb = _truePeakMeter.CurrentBlockRmsRightDb,
            MaxTruePeakLeftDb = _truePeakMeter.MaxTruePeakLeftDb,
            MaxTruePeakRightDb = _truePeakMeter.MaxTruePeakRightDb,
            Correlation = _stereoAnalyzer.RealtimeCorrelation,
            InstantSpectrumDb = spectrumCopy,
            CumulativePeakSpectrumDb = peakHoldCopy,
            GoniometerPointsX = gonioX,
            GoniometerPointsY = gonioY
        };
    }

    /// <summary>
    /// Generates the complete analysis report.
    /// </summary>
    public FullAnalysisReport GenerateReport(string title = "", string filePath = "", string format = "", TimeSpan duration = default, int bitDepth = 16)
    {
        var loudnessResult = _loudnessMeter.CalculateResult();
        var levelsResult = _truePeakMeter.CalculateResult();
        var stereoResult = _channelCount >= 2 ? _stereoAnalyzer.CalculateResult() : new StereoResult();

        // MusicScope displays the full cumulative peak hold spectrum across the entire track
        double[] finalSpectrumDb = new double[FftSize / 2];
        Array.Copy(_peakHoldSpectrumDb, finalSpectrumDb, finalSpectrumDb.Length);

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
        Array.Fill(_peakHoldSpectrumDb, -140.0);
        Array.Fill(_latestInstantSpectrumDb, -140.0);
        _spectrumFftCount = 0;
    }
}
