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
        // Ensure 30 min of audio processes in under 30 seconds even in unoptimized Debug mode (runs in ~4s in Release)
        Assert.True(sw.Elapsed.TotalSeconds < 30.0, $"Processing 30 min audio took {sw.Elapsed.TotalSeconds:F2}s (throughput {30.0 / (sw.Elapsed.TotalMinutes):F0}x realtime)");
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
}
