using System;
using MusicScope.Core.DSP;
using MusicScope.Core.Hardware;
using MusicScope.Core.Levels;
using MusicScope.Core.Loudness;
using MusicScope.Core.Stereo;
using Xunit;

namespace MusicScope.Core.Tests;

public class DspAndMeteringTests
{
    [Fact]
    public void FastFourierTransform_Identifies_Pure_Sine_Wave_Frequency()
    {
        int fftSize = 4096;
        double sampleRate = 48000.0;
        double targetFreq = 1000.0;

        var fft = new FastFourierTransform(fftSize);
        double[] real = new double[fftSize];
        double[] imag = new double[fftSize];
        double[] window = WindowFunctions.Create(WindowType.Hann, fftSize);

        for (int i = 0; i < fftSize; i++)
        {
            real[i] = Math.Sin(2.0 * Math.PI * targetFreq * i / sampleRate) * window[i];
            imag[i] = 0.0;
        }

        fft.Forward(real, imag);

        double[] magsDb = new double[fftSize / 2];
        fft.CalculateMagnitudeDb(real, imag, magsDb);

        // Find peak bin
        int peakBin = 0;
        double maxMag = -140.0;
        for (int i = 0; i < magsDb.Length; i++)
        {
            if (magsDb[i] > maxMag)
            {
                maxMag = magsDb[i];
                peakBin = i;
            }
        }

        double detectedFreq = fft.GetFrequencyForBin(peakBin, sampleRate);
        Assert.InRange(detectedFreq, 980.0, 1020.0);
    }

    [Fact]
    public void StereoAnalyzer_Correlation_Identifies_Mono_InPhase_And_AntiPhase()
    {
        var analyzer = new StereoAnalyzer();

        // 1. In-phase mono (L == R)
        double[] inPhase = new double[1000];
        for (int i = 0; i < 500; i++)
        {
            double val = Math.Sin(2.0 * Math.PI * 440.0 * i / 48000.0);
            inPhase[i * 2] = val;
            inPhase[i * 2 + 1] = val;
        }
        analyzer.ProcessInterleaved(inPhase);
        var inPhaseResult = analyzer.CalculateResult();
        Assert.Equal(1.0, inPhaseResult.Correlation, 0.01);

        // 2. Anti-phase (L == -R)
        analyzer.Reset();
        double[] antiPhase = new double[1000];
        for (int i = 0; i < 500; i++)
        {
            double val = Math.Sin(2.0 * Math.PI * 440.0 * i / 48000.0);
            antiPhase[i * 2] = val;
            antiPhase[i * 2 + 1] = -val;
        }
        analyzer.ProcessInterleaved(antiPhase);
        var antiPhaseResult = analyzer.CalculateResult();
        Assert.Equal(-1.0, antiPhaseResult.Correlation, 0.01);
    }

    [Fact]
    public void StereoAnalyzer_Generates_DensityCloud_And_5000_LivePoints()
    {
        var analyzer = new StereoAnalyzer();

        // Feed 10,000 stereo frames of a stereo panned signal
        int frames = 10000;
        double[] stereo = new double[frames * 2];
        for (int i = 0; i < frames; i++)
        {
            stereo[i * 2] = 0.6 * Math.Sin(2.0 * Math.PI * 440.0 * i / 48000.0);
            stereo[i * 2 + 1] = 0.4 * Math.Cos(2.0 * Math.PI * 440.0 * i / 48000.0);
        }

        analyzer.ProcessInterleaved(stereo);

        float[] liveX = new float[StereoAnalyzer.LivePointCount];
        float[] liveY = new float[StereoAnalyzer.LivePointCount];
        analyzer.GenerateGoniometerPoints(liveX, liveY);

        // Verify live points were populated with non-zero coordinates
        bool hasNonZeroLive = false;
        for (int i = 0; i < liveX.Length; i++)
        {
            if (liveX[i] != 0f || liveY[i] != 0f)
            {
                hasNonZeroLive = true;
                break;
            }
        }
        Assert.True(hasNonZeroLive);

        // Verify density cloud is 256x256 and has non-zero intensity
        var result = analyzer.CalculateResult();
        Assert.NotNull(result.DensityCloud);
        Assert.Equal(StereoAnalyzer.DensityGridSize * StereoAnalyzer.DensityGridSize, result.DensityCloud.Length);

        bool hasNonZeroCloud = false;
        for (int i = 0; i < result.DensityCloud.Length; i++)
        {
            if (result.DensityCloud[i] > 0)
            {
                hasNonZeroCloud = true;
                break;
            }
        }
        Assert.True(hasNonZeroCloud);
    }

    [Fact]
    public void TruePeakMeter_Detects_InterSample_Peak_Exceeding_Sample_Peak()
    {
        var meter = new TruePeakMeter(channelCount: 1);

        // An intersample peak occurs when sampling a sine near Nyquist (e.g. fs/4) offset by 45 degrees
        // where sample points hit +/- 0.7071 (-3 dBFS), but true continuous peak is 1.0 (0 dBFS, +3 dBTP higher!)
        int length = 200;
        double[] samples = new double[length];
        for (int i = 0; i < length; i++)
        {
            // Sample at 45 deg, 135 deg, 225 deg, 315 deg (cos = +/- 0.7071)
            samples[i] = Math.Sin(2.0 * Math.PI * 0.25 * i + Math.PI / 4.0);
        }

        meter.ProcessInterleaved(samples);
        var result = meter.CalculateResult();

        // Sample peak should be ~ -3 dBFS (0.7071)
        Assert.InRange(result.SamplePeakLeftDb, -3.1, -2.9);
        // True peak oversampled should reconstruct close to 0 dBTP (1.0)
        Assert.InRange(result.TruePeakLeftDb, -0.2, 0.2);
    }

    [Fact]
    public void LoudnessMeter_Measures_1kHz_Sine_According_To_BS1770()
    {
        double sampleRate = 48000.0;
        var meter = new LoudnessMeter(sampleRate, channelCount: 2);

        // Generate 2 seconds of 1 kHz stereo sine wave at 0 dBFS (amplitude 1.0)
        int numSamples = (int)(2.0 * sampleRate);
        double[] stereo = new double[numSamples * 2];
        for (int i = 0; i < numSamples; i++)
        {
            double val = Math.Sin(2.0 * Math.PI * 1000.0 * i / sampleRate);
            stereo[i * 2] = val;
            stereo[i * 2 + 1] = val;
        }

        meter.ProcessInterleaved(stereo);
        var result = meter.CalculateResult();

        // For a full-scale 1 kHz stereo sine, K-weighting has slight high-shelf boost (+0.69 dB),
        // yielding close to -3.0 LKFS per channel, summed ~ 0 LKFS
        Assert.True(result.HasAudibleSignal);
        Assert.InRange(result.IntegratedLoudness, -4.0, 1.0);
    }

    [Fact]
    public void ThdAnalyzer_Calculates_Low_Distortion_For_Pure_Sine()
    {
        double sampleRate = 48000.0;
        var thd = new ThdAnalyzer(sampleRate, fftSize: 8192);

        int count = 8192;
        double[] pureSine = new double[count];
        for (int i = 0; i < count; i++)
        {
            pureSine[i] = Math.Sin(2.0 * Math.PI * 1000.0 * i / sampleRate);
        }

        var result = thd.Analyze(pureSine);

        Assert.InRange(result.FundamentalHz, 990.0, 1010.0);
        Assert.True(result.ThdPercent < 0.05, $"Expected THD < 0.05%, got {result.ThdPercent}%");
    }

    [Fact]
    public void TurntableAnalyzer_Detects_Exact_33Rpm_From_1000Hz_Tone()
    {
        double sampleRate = 48000.0;
        var tt = new TurntableAnalyzer(sampleRate, referenceFrequencyHz: 1000.0, TurntableTargetSpeed.Rpm33_3);

        // 1 second of pure 1000 Hz tone
        int count = (int)sampleRate;
        double[] samples = new double[count];
        for (int i = 0; i < count; i++)
        {
            samples[i] = Math.Sin(2.0 * Math.PI * 1000.0 * i / sampleRate);
        }

        tt.ProcessSamples(samples);
        var res = tt.CalculateResult();

        Assert.Equal(33.333, res.TargetRpm, 0.01);
        Assert.Equal(33.333, res.CurrentRpm, 0.1);
        Assert.InRange(res.DeviationPercent, -0.5, 0.5);
    }

    [Fact]
    public void Benchmark_AudioAnalysisEngine_Throughput()
    {
        double sampleRate = 44100.0;
        int channels = 2;
        var engine = new AudioAnalysisEngine(sampleRate, channels);

        // 30 minutes of audio = 79,380,000 frames = 158,760,000 floats
        int chunkFloats = 65536;
        float[] chunk = new float[chunkFloats];
        for (int i = 0; i < chunkFloats; i++)
        {
            chunk[i] = (float)(0.7 * Math.Sin(2.0 * Math.PI * 1000.0 * (i / 2) / sampleRate));
        }

        long totalFloatsToProcess = 30L * 60 * 44100 * 2; // 30 minutes
        int iterations = (int)(totalFloatsToProcess / chunkFloats);

        var sw = System.Diagnostics.Stopwatch.StartNew();
        for (int i = 0; i < iterations; i++)
        {
            engine.ProcessAudioBlock(chunk);
        }
        sw.Stop();

        var report = engine.GenerateReport("Benchmark", "", "FLAC", TimeSpan.FromMinutes(30));
        Assert.True(report.Loudness.HasAudibleSignal);
        // Ensure 30 min of audio processes faster than the original Java app (50s) even in unoptimized Debug mode (runs in ~5s in Release)
#if DEBUG
        double thresholdSeconds = 45.0;
#else
        double thresholdSeconds = 15.0;
#endif
        Assert.True(sw.Elapsed.TotalSeconds < thresholdSeconds, $"Processing 30 min audio took {sw.Elapsed.TotalSeconds:F2}s (throughput {30.0 / (sw.Elapsed.TotalMinutes):F0}x realtime)");
    }

    [Fact]
    public void TruePeakMeter_Calculates_Running_Crest_Factor_Matching_MusicScope()
    {
        var meter = new TruePeakMeter(channelCount: 2);
        // Feed 16 blocks (16 * 2048 = 32768 frames) of a pure 1 kHz stereo sine wave at 0 dBFS
        int frameCount = 32768;
        double[] stereo = new double[frameCount * 2];
        for (int i = 0; i < frameCount; i++)
        {
            double val = Math.Sin(2.0 * Math.PI * 1000.0 * i / 44100.0);
            stereo[i * 2] = val;
            stereo[i * 2 + 1] = val;
        }

        meter.ProcessInterleaved(stereo);
        var result = meter.CalculateResult();

        // For a sine wave, theoretical Crest factor is sqrt(2) = 3.01 dB (~3.0 dB)
        Assert.InRange(result.CrestFactorDb, 2.9, 3.1);
        Assert.InRange(meter.CrestAvgDb, 2.9, 3.1);
    }

    [Fact]
    public void AudioAnalysisEngine_PolarHistory_PopulatesAll512Bins_RegardlessOfChunkSize()
    {
        double sampleRate = 44100.0;
        int durationSec = 10;
        int totalFrames = (int)(durationSec * sampleRate);
        float[] pcm = new float[totalFrames * 2];
        for (int i = 0; i < totalFrames; i++)
        {
            float val = (float)(0.5 * Math.Sin(2.0 * Math.PI * 1000.0 * i / sampleRate)); // -6.02 dBFS
            pcm[i * 2] = val;
            pcm[i * 2 + 1] = val;
        }

        // Engine A: large chunks (e.g. 65536 floats)
        var engineA = new AudioAnalysisEngine(sampleRate, 2, totalFrames);
        int chunkSizeA = 65536;
        for (int offset = 0; offset < pcm.Length; offset += chunkSizeA)
        {
            int len = Math.Min(chunkSizeA, pcm.Length - offset);
            engineA.ProcessAudioBlock(pcm.AsSpan(offset, len));
        }
        var reportA = engineA.GenerateReport(duration: TimeSpan.FromSeconds(durationSec));

        // Engine B: small chunks (e.g. 1024 floats)
        var engineB = new AudioAnalysisEngine(sampleRate, 2, totalFrames);
        int chunkSizeB = 1024;
        for (int offset = 0; offset < pcm.Length; offset += chunkSizeB)
        {
            int len = Math.Min(chunkSizeB, pcm.Length - offset);
            engineB.ProcessAudioBlock(pcm.AsSpan(offset, len));
        }
        var reportB = engineB.GenerateReport(duration: TimeSpan.FromSeconds(durationSec));

        Assert.Equal(512, reportA.PeakHistory.Length);
        Assert.Equal(512, reportA.LoudnessHistory.Length);
        Assert.Equal(512, reportB.PeakHistory.Length);
        Assert.Equal(512, reportB.LoudnessHistory.Length);

        // Every bin should be populated (~ -6.0 dBFS) without any bin dropping to -60 dB
        for (int b = 0; b < 512; b++)
        {
            Assert.True(reportA.PeakHistory[b] > -10.0, $"Engine A bin {b} was unpopulated: {reportA.PeakHistory[b]} dB");
            Assert.True(reportB.PeakHistory[b] > -10.0, $"Engine B bin {b} was unpopulated: {reportB.PeakHistory[b]} dB");
            Assert.InRange(Math.Abs(reportA.PeakHistory[b] - reportB.PeakHistory[b]), 0.0, 0.5);
        }
    }
}
