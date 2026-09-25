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

    private readonly float[] _goniometerX = new float[StereoAnalyzer.LivePointCount];
    private readonly float[] _goniometerY = new float[StereoAnalyzer.LivePointCount];

    public const int HistoryBinCount = 512;
    private readonly double[] _peakHistory = new double[HistoryBinCount];
    private readonly double[] _loudnessHistory = new double[HistoryBinCount];

    public const int SpectrogramRows = 250;
    public const int SpectrogramCols = 1024;
    private readonly float[] _spectrogramMax = new float[SpectrogramRows * SpectrogramCols];
    private readonly float[] _spectrogramAvg = new float[SpectrogramRows * SpectrogramCols];
    private readonly float[] _spectrogramMin = new float[SpectrogramRows * SpectrogramCols];
    private readonly int[] _spectrogramRowCount = new int[SpectrogramRows];

    private long _totalFrames;
    private long _processedFrames;

    public long TotalFrames
    {
        get => _totalFrames;
        set => _totalFrames = value;
    }

    public AudioAnalysisEngine(double sampleRate = 44100.0, int channelCount = 2, long totalFrames = 0)
    {
        _sampleRate = sampleRate;
        _channelCount = channelCount;
        _totalFrames = totalFrames;

        _loudnessMeter = new LoudnessMeter(sampleRate, channelCount);
        _truePeakMeter = new TruePeakMeter(channelCount, sampleRate);
        _stereoAnalyzer = new StereoAnalyzer();

        _fft = new FastFourierTransform(FftSize);
        _fftWindow = WindowFunctions.Create(WindowType.BlackmanHarris, FftSize);
        Array.Fill(_latestInstantSpectrumDb, -140.0);
        Array.Fill(_peakHoldSpectrumDb, -140.0);
        Array.Fill(_peakHistory, -60.0);
        Array.Fill(_loudnessHistory, -60.0);
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
        int i = 0;
        while (i < frameCount)
        {
            int framesNeeded = FftStride - _framesSinceLastFft;
            if (framesNeeded > 0)
            {
                int step = Math.Min(framesNeeded, frameCount - i);
                _framesSinceLastFft += step;
                i += step;
                if (_framesSinceLastFft < FftStride)
                    break;
            }

            if (i + FftSize <= frameCount)
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

                // Determine row in 250-row spectrogram
                // matching MusicScope WaterfallControl.java lines 344-350
                int specRow = _totalFrames > 0
                    ? Math.Clamp((int)((double)(_processedFrames + i) / _totalFrames * SpectrogramRows), 0, SpectrogramRows - 1)
                    : Math.Clamp(_spectrumFftCount % SpectrogramRows, 0, SpectrogramRows - 1);

                int rowOffset = specRow * SpectrogramCols;
                bool isFirstInRow = (_spectrogramRowCount[specRow] == 0);
                _spectrogramRowCount[specRow]++;

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

                    // Accumulate linear magnitude into 2D spectrogram buffers (250 rows x 1024 cols)
                    float linMag = (float)instantMag;
                    int idx = rowOffset + b;
                    if (isFirstInRow)
                    {
                        _spectrogramMax[idx] = linMag;
                        _spectrogramAvg[idx] = linMag;
                        _spectrogramMin[idx] = linMag;
                    }
                    else
                    {
                        if (linMag > _spectrogramMax[idx]) _spectrogramMax[idx] = linMag;
                        if (linMag < _spectrogramMin[idx]) _spectrogramMin[idx] = linMag;
                        _spectrogramAvg[idx] += linMag;
                    }
                }

                _spectrumFftCount++;
                i += FftSize;
            }
            else
            {
                _framesSinceLastFft += (frameCount - i);
                break;
            }
        }

        // Update polar history dial (512 radial bins)
        if (frameCount > 0)
        {
            const int StepFrames = 2048;
            for (int offset = 0; offset < frameCount; offset += StepFrames)
            {
                int count = Math.Min(StepFrames, frameCount - offset);
                int sampleOffset = offset * _channelCount;
                int sampleCount = count * _channelCount;

                ReadOnlySpan<float> slice = interleavedSamples.Slice(sampleOffset, sampleCount);
                float maxSample = 0f;
                for (int s = 0; s < slice.Length; s++)
                {
                    float v = Math.Abs(slice[s]);
                    if (v > maxSample) maxSample = v;
                }
                double slicePeakDb = maxSample > 1e-6f ? 20.0 * Math.Log10(maxSample) : -100.0;
                double currentPeakDb = Math.Max(slicePeakDb, Math.Max(_truePeakMeter.CurrentBlockPeakLeftDb, _truePeakMeter.CurrentBlockPeakRightDb));
                double currentLoudnessDb = _loudnessMeter.CurrentShortTermLufs;

                long startF = _processedFrames + offset;
                long endF = startF + count;

                if (_totalFrames > 0)
                {
                    int startBin = (int)((double)startF / _totalFrames * HistoryBinCount);
                    int endBin = (int)((double)endF / _totalFrames * HistoryBinCount);
                    startBin = Math.Clamp(startBin, 0, HistoryBinCount - 1);
                    endBin = Math.Clamp(endBin, 0, HistoryBinCount - 1);

                    for (int b = startBin; b <= endBin; b++)
                    {
                        if (currentPeakDb > _peakHistory[b])
                            _peakHistory[b] = currentPeakDb;
                        if (currentLoudnessDb > _loudnessHistory[b])
                            _loudnessHistory[b] = currentLoudnessDb;
                    }
                }
                else
                {
                    int bin = (int)((endF / _sampleRate) % HistoryBinCount);
                    _peakHistory[bin] = currentPeakDb;
                    _loudnessHistory[bin] = currentLoudnessDb;
                }
            }
            _processedFrames += frameCount;
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

        if (_channelCount >= 2)
        {
            _stereoAnalyzer.GenerateGoniometerPoints(gonioX, gonioY);
        }

        double[] peakHistoryCopy = new double[HistoryBinCount];
        Array.Copy(_peakHistory, peakHistoryCopy, HistoryBinCount);

        double[] loudnessHistoryCopy = new double[HistoryBinCount];
        Array.Copy(_loudnessHistory, loudnessHistoryCopy, HistoryBinCount);

        int[] sModeHisto = _loudnessMeter.GetHistogramSnapshot(out int sModeMax, out double lraLow, out double lraHigh, out double runningLra);

        return new AudioRealtimeSnapshot
        {
            ProgressFraction = progress,
            MomentaryLufs = _loudnessMeter.CurrentMomentaryLufs,
            ShortTermLufs = _loudnessMeter.CurrentShortTermLufs,
            RunningIntegratedLufs = _loudnessMeter.CurrentShortTermLufs, // during live stream, short-term represents the current perceived level
            RunningLra = runningLra,
            SModeHistogram = sModeHisto,
            SModeMaxCount = sModeMax,
            SModeLraLow = lraLow,
            SModeLraHigh = lraHigh,
            CurrentPeakLeftDb = _truePeakMeter.CurrentBlockPeakLeftDb,
            CurrentPeakRightDb = _truePeakMeter.CurrentBlockPeakRightDb,
            CurrentRmsLeftDb = _truePeakMeter.CurrentBlockRmsLeftDb,
            CurrentRmsRightDb = _truePeakMeter.CurrentBlockRmsRightDb,
            MaxTruePeakLeftDb = _truePeakMeter.MaxTruePeakLeftDb,
            MaxTruePeakRightDb = _truePeakMeter.MaxTruePeakRightDb,
            RunningCrestDb = _truePeakMeter.CrestAvgDb,
            Correlation = _stereoAnalyzer.RealtimeCorrelation,
            InstantSpectrumDb = spectrumCopy,
            CumulativePeakSpectrumDb = peakHoldCopy,
            PeakHistory = peakHistoryCopy,
            LoudnessHistory = loudnessHistoryCopy,
            SpectrogramMax = _spectrogramMax,
            SpectrogramAvg = _spectrogramAvg,
            SpectrogramMin = _spectrogramMin,
            SpectrogramRowCount = _spectrogramRowCount,
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

        // Ensure trailing bins up to 511 are populated if decoding finished slightly early
        if (_totalFrames > 0 && _processedFrames > 0)
        {
            int lastBin = Math.Clamp((int)((double)_processedFrames / _totalFrames * HistoryBinCount), 0, HistoryBinCount - 1);
            for (int b = lastBin + 1; b < HistoryBinCount; b++)
            {
                _peakHistory[b] = _peakHistory[lastBin];
                _loudnessHistory[b] = _loudnessHistory[lastBin];
            }
        }

        double[] finalPeakHistory = new double[HistoryBinCount];
        Array.Copy(_peakHistory, finalPeakHistory, HistoryBinCount);

        double[] finalLoudnessHistory = new double[HistoryBinCount];
        Array.Copy(_loudnessHistory, finalLoudnessHistory, HistoryBinCount);

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
            SpectrumMagnitudesDb = finalSpectrumDb,
            PeakHistory = finalPeakHistory,
            LoudnessHistory = finalLoudnessHistory,
            SpectrogramMax = (float[])_spectrogramMax.Clone(),
            SpectrogramAvg = (float[])_spectrogramAvg.Clone(),
            SpectrogramMin = (float[])_spectrogramMin.Clone(),
            SpectrogramRowCount = (int[])_spectrogramRowCount.Clone()
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
        Array.Fill(_peakHistory, -60.0);
        Array.Fill(_loudnessHistory, -60.0);
        Array.Clear(_spectrogramMax, 0, _spectrogramMax.Length);
        Array.Clear(_spectrogramAvg, 0, _spectrogramAvg.Length);
        Array.Clear(_spectrogramMin, 0, _spectrogramMin.Length);
        Array.Clear(_spectrogramRowCount, 0, _spectrogramRowCount.Length);
        _processedFrames = 0;
        _spectrumFftCount = 0;
    }
}
