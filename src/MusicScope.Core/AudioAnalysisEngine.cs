using System;
using System.Runtime.InteropServices;
using System.Runtime.Intrinsics;
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
    private readonly SpectrumAnalyzer _linearSpectrum;
    private readonly SpectrumAnalyzer _logSpectrum;

    private const int FftSize = 2048;
    private readonly int _fftStride;
    private int _framesSinceLastFft;
    private int _bufferedFrames;
    private int _ringBufferPos;
    private readonly double[] _ringBufferLeft = new double[FftSize];
    private readonly double[] _ringBufferRight = new double[FftSize];
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
    private readonly float[] _spectrogramLogMax = new float[SpectrogramRows * SpectrogramCols];
    private readonly float[] _spectrogramLogAvg = new float[SpectrogramRows * SpectrogramCols];
    private readonly float[] _spectrogramLogMin = new float[SpectrogramRows * SpectrogramCols];
    private readonly int[] _spectrogramRowCount = new int[SpectrogramRows];

    // Precomputed 1024-column to 4096-bin mapping for 8192-point FFT logarithmic spectrogram.
    // Matches original XiVero WaterfallControl.java lines 270 & 338.
    private static readonly int[] LogSpectrogramBinMap = InitializeLogSpectrogramBinMap();

    private static int[] InitializeLogSpectrogramBinMap()
    {
        int[] map = new int[SpectrogramCols];
        double streamDecoder = 1024.0 / Math.Log10(4096.0 * 0.005 + 1.0);
        for (int n = 0; n < SpectrogramCols; n++)
        {
            int bin = (int)((Math.Pow(10.0, (double)n / streamDecoder) - 1.0) / 0.005);
            map[n] = Math.Clamp(bin, 0, 4095);
        }
        return map;
    }

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

        _fftStride = Math.Max(FftSize, (int)(sampleRate * 0.050));
        _linearSpectrum = new SpectrumAnalyzer(FftSize, sampleRate);
        _logSpectrum = new SpectrumAnalyzer(8192, sampleRate);
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

        // Feed circular buffers and execute FFT every 50ms (matching MusicScope SystemController.java & SpectrumModule.java)
        int frameCount = interleavedSamples.Length / _channelCount;
        int frameOffset = 0;
        while (frameOffset < frameCount)
        {
            int remaining = frameCount - frameOffset;
            int toStride = _fftStride - _framesSinceLastFft;
            int step = toStride > 0 ? Math.Min(remaining, toStride) : 1;

            if (_framesSinceLastFft < FftSize)
            {
                int ringFill = Math.Min(step, FftSize - _framesSinceLastFft);
                for (int i = 0; i < ringFill; i++)
                {
                    int sampleIdx = (frameOffset + i) * _channelCount;
                    float left = interleavedSamples[sampleIdx];
                    float right = _channelCount >= 2 ? interleavedSamples[sampleIdx + 1] : left;
                    _ringBufferLeft[_ringBufferPos] = left;
                    _ringBufferRight[_ringBufferPos] = right;
                    if (++_ringBufferPos >= FftSize) _ringBufferPos = 0;
                }
                _bufferedFrames += ringFill;
            }

            _logSpectrum.Process(interleavedSamples.Slice(frameOffset * _channelCount, step * _channelCount), _channelCount);
            _framesSinceLastFft += step;

            if (_bufferedFrames >= FftSize && _framesSinceLastFft >= _fftStride)
            {
                ExecuteFft(_processedFrames + frameOffset + step - 1);
            }

            frameOffset += step;
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
                float maxSample = MaxMagnitude(slice);
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
        SpectrumFrame linearFrame = _linearSpectrum.Capture();
        double[] spectrumCopy;
        double[] peakHoldCopy;
        if (_spectrumFftCount > 0)
        {
            spectrumCopy = linearFrame.InstantDb;
            peakHoldCopy = linearFrame.PeakDb;
        }
        else
        {
            spectrumCopy = new double[FftSize / 2];
            peakHoldCopy = new double[FftSize / 2];
            Array.Fill(spectrumCopy, -140.0);
            Array.Fill(peakHoldCopy, -140.0);
        }

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
            RunningInstantCrestDb = _truePeakMeter.CurrentInstantCrestDb,
            RunningPlrDb = _loudnessMeter.PlrAvgDb,
            Correlation = _stereoAnalyzer.RealtimeCorrelation,
            InstantSpectrumDb = spectrumCopy,
            CumulativePeakSpectrumDb = peakHoldCopy,
            LinearSpectrum = linearFrame,
            LogSpectrum = _logSpectrum.Capture(),
            PeakHistory = peakHistoryCopy,
            LoudnessHistory = loudnessHistoryCopy,
            SpectrogramMax = _spectrogramMax,
            SpectrogramAvg = _spectrogramAvg,
            SpectrogramMin = _spectrogramMin,
            SpectrogramLogMax = _spectrogramLogMax,
            SpectrogramLogAvg = _spectrogramLogAvg,
            SpectrogramLogMin = _spectrogramLogMin,
            SpectrogramRowCount = _spectrogramRowCount,
            GoniometerPointsX = gonioX,
            GoniometerPointsY = gonioY
        };
    }

    private void ExecuteFft(long currentFrame)
    {
        _framesSinceLastFft = 0;

        // The reference fills its ring on the first interval and starts smoothing on the next.
        if (_spectrumFftCount > 0)
            _linearSpectrum.ProcessRingWindow(_ringBufferLeft, _ringBufferRight, _ringBufferPos);

        // Determine row in 250-row spectrogram
        // matching MusicScope WaterfallControl.java lines 344-350
        int specRow = _totalFrames > 0
            ? Math.Clamp((int)((double)currentFrame / _totalFrames * SpectrogramRows), 0, SpectrogramRows - 1)
            : Math.Clamp(_spectrumFftCount % SpectrogramRows, 0, SpectrogramRows - 1);

        int rowOffset = specRow * SpectrogramCols;
        bool isFirstInRow = (_spectrogramRowCount[specRow] == 0);
        _spectrogramRowCount[specRow]++;

        for (int b = 0; b < FftSize / 2; b++)
        {
            // Accumulate linear magnitude into 2D spectrogram buffers (250 rows x 1024 cols).
            float linMag = (float)_linearSpectrum.GetMagnitude(b);
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

        for (int n = 0; n < SpectrogramCols; n++)
        {
            int logBin = LogSpectrogramBinMap[n];
            double logMag = _logSpectrum.GetMagnitude(logBin);
            float logLinMag = (float)logMag;
            int idx = rowOffset + n;
            if (isFirstInRow)
            {
                _spectrogramLogMax[idx] = logLinMag;
                _spectrogramLogAvg[idx] = logLinMag;
                _spectrogramLogMin[idx] = logLinMag;
            }
            else
            {
                if (logLinMag > _spectrogramLogMax[idx]) _spectrogramLogMax[idx] = logLinMag;
                if (logLinMag < _spectrogramLogMin[idx]) _spectrogramLogMin[idx] = logLinMag;
                _spectrogramLogAvg[idx] += logLinMag;
            }
        }

        _spectrumFftCount++;
    }

    /// <summary>
    /// Generates the complete analysis report.
    /// </summary>
    public FullAnalysisReport GenerateReport(string title = "", string filePath = "", string format = "", TimeSpan duration = default, int bitDepth = 16)
    {
        _logSpectrum.Finish();
        if (_bufferedFrames >= FftSize && _framesSinceLastFft > 0)
        {
            ExecuteFft(_processedFrames);
        }

        var loudnessResult = _loudnessMeter.CalculateResult();
        var levelsResult = _truePeakMeter.CalculateResult();
        var stereoResult = _channelCount >= 2 ? _stereoAnalyzer.CalculateResult() : new StereoResult();

        // MusicScope displays the full cumulative peak hold spectrum across the entire track
        SpectrumFrame linearFrame = _linearSpectrum.Capture();
        double[] finalSpectrumDb;
        if (_spectrumFftCount > 0)
        {
            finalSpectrumDb = linearFrame.PeakDb;
        }
        else
        {
            finalSpectrumDb = new double[FftSize / 2];
            Array.Fill(finalSpectrumDb, -140.0);
        }

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
            LinearSpectrum = linearFrame,
            LogSpectrum = _logSpectrum.Capture(),
            PeakHistory = finalPeakHistory,
            LoudnessHistory = finalLoudnessHistory,
            SpectrogramMax = (float[])_spectrogramMax.Clone(),
            SpectrogramAvg = (float[])_spectrogramAvg.Clone(),
            SpectrogramMin = (float[])_spectrogramMin.Clone(),
            SpectrogramLogMax = (float[])_spectrogramLogMax.Clone(),
            SpectrogramLogAvg = (float[])_spectrogramLogAvg.Clone(),
            SpectrogramLogMin = (float[])_spectrogramLogMin.Clone(),
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
        _linearSpectrum.Reset();
        _logSpectrum.Reset();
        _bufferedFrames = 0;
        _framesSinceLastFft = 0;
        _ringBufferPos = 0;
        Array.Clear(_ringBufferLeft, 0, _ringBufferLeft.Length);
        Array.Clear(_ringBufferRight, 0, _ringBufferRight.Length);
        Array.Fill(_peakHistory, -60.0);
        Array.Fill(_loudnessHistory, -60.0);
        Array.Clear(_spectrogramMax, 0, _spectrogramMax.Length);
        Array.Clear(_spectrogramAvg, 0, _spectrogramAvg.Length);
        Array.Clear(_spectrogramMin, 0, _spectrogramMin.Length);
        Array.Clear(_spectrogramLogMax, 0, _spectrogramLogMax.Length);
        Array.Clear(_spectrogramLogAvg, 0, _spectrogramLogAvg.Length);
        Array.Clear(_spectrogramLogMin, 0, _spectrogramLogMin.Length);
        Array.Clear(_spectrogramRowCount, 0, _spectrogramRowCount.Length);
        _processedFrames = 0;
        _spectrumFftCount = 0;
    }

    private static float MaxMagnitude(ReadOnlySpan<float> samples)
    {
        int i = 0;
        float max = 0f;
        if (Vector256.IsHardwareAccelerated && samples.Length >= 8)
        {
            ref float p = ref MemoryMarshal.GetReference(samples);
            var vMax = Vector256<float>.Zero;
            int vecEnd = samples.Length - (samples.Length & 7);
            for (; i < vecEnd; i += 8)
            {
                var v = Vector256.Abs(Vector256.LoadUnsafe(ref p, (nuint)i));
                vMax = Vector256.Max(vMax, v);
            }
            for (int lane = 0; lane < 8; lane++)
            {
                float val = vMax.GetElement(lane);
                if (val > max) max = val;
            }
        }
        for (; i < samples.Length; i++)
        {
            float v = MathF.Abs(samples[i]);
            if (v > max) max = v;
        }
        return max;
    }
}
