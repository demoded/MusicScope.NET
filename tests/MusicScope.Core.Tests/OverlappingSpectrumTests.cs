using System;
using MusicScope.Core;

namespace MusicScope.Core.Tests;

public class OverlappingSpectrumTests
{
    [Fact]
    public void OverlappingMode_CapturesBriefTransientInSkippedTailOf96kHzInterval()
    {
        const int sampleRate = 96000;
        const int stride = 4800; // 50 ms at 96 kHz
        const int totalFrames = stride * 4;
        const double burstFreq = 32000.0;
        var samples = new float[totalFrames * 2];

        // Place a 32 kHz tone ONLY inside frames 2600..4400 of interval 2 (frames 12200..14000).
        // Original 2,048-frame first-window mode only inspects frames 9600..11647 of interval 2,
        // skipping frames 11648..14399 entirely.
        for (int f = stride * 2 + 2600; f < stride * 2 + 4400; f++)
        {
            float s = (float)(0.25 * Math.Sin(2.0 * Math.PI * burstFreq * f / sampleRate));
            samples[f * 2] = s;
            samples[f * 2 + 1] = s;
        }

        var originalEngine = new AudioAnalysisEngine(sampleRate, 2, totalFrames, SpectrumAnalysisMode.Original);
        originalEngine.ProcessAudioBlock(samples);
        var originalReport = originalEngine.GenerateReport();

        var overlappingEngine = new AudioAnalysisEngine(sampleRate, 2, totalFrames, SpectrumAnalysisMode.Overlapping);
        overlappingEngine.ProcessAudioBlock(samples);
        var overlappingReport = overlappingEngine.GenerateReport();

        Assert.Equal(SpectrumAnalysisMode.Original, originalReport.AnalysisMode);
        Assert.Equal(SpectrumAnalysisMode.Overlapping, overlappingReport.AnalysisMode);
        Assert.Equal(1024, originalReport.LinearSpectrum!.PeakDb.Length);
        Assert.Equal(4096, overlappingReport.LinearSpectrum!.PeakDb.Length);

        int origBin = (int)Math.Round(burstFreq * 2048.0 / sampleRate);
        int overBin = (int)Math.Round(burstFreq * 8192.0 / sampleRate);

        // Original linear mode misses the burst completely (noise floor -200 dB)
        Assert.True(originalReport.LinearSpectrum.PeakDb[origBin] < -120.0,
            $"Expected Original mode to miss burst, got {originalReport.LinearSpectrum.PeakDb[origBin]} dB");

        // Overlapping mode captures the burst in linear spectrum, report, and 1024-col linear spectrogram
        Assert.InRange(overlappingReport.LinearSpectrum.PeakDb[overBin], -30.0, -10.0);
        Assert.Equal(overlappingReport.LinearSpectrum.PeakDb, overlappingReport.SpectrumMagnitudesDb);

        int specCol = overBin / 4;
        float maxSpecCol = 0f;
        for (int r = 0; r < AudioAnalysisEngine.SpectrogramRows; r++)
        {
            maxSpecCol = Math.Max(maxSpecCol, overlappingReport.SpectrogramMax![r * AudioAnalysisEngine.SpectrogramCols + specCol]);
        }
        Assert.True(20.0 * Math.Log10(maxSpecCol) > -30.0);
    }

    [Fact]
    public void OverlappingMode_ResolvesCloselySpacedTones_With11_7HzSpacingAt96kHz()
    {
        const int sampleRate = 96000;
        const int totalFrames = 24000; // 250 ms
        const double f1 = 1000.0;
        const double f2 = 1046.875; // Exactly 1 bin apart in 2048-pt FFT, 4 bins apart in 8192-pt FFT
        var samples = new float[totalFrames * 2];

        for (int f = 0; f < totalFrames; f++)
        {
            float s = (float)(0.25 * Math.Sin(2.0 * Math.PI * f1 * f / sampleRate)
                            + 0.25 * Math.Sin(2.0 * Math.PI * f2 * f / sampleRate));
            samples[f * 2] = s;
            samples[f * 2 + 1] = s;
        }

        var engine = new AudioAnalysisEngine(sampleRate, 2, totalFrames, SpectrumAnalysisMode.Overlapping);
        engine.ProcessAudioBlock(samples);
        var report = engine.GenerateReport();

        double[] peakDb = report.LinearSpectrum!.PeakDb;
        Assert.Equal(4096, peakDb.Length);

        int bin1 = (int)Math.Round(f1 * 8192.0 / sampleRate); // ~85
        int binMid = (bin1 + (int)Math.Round(f2 * 8192.0 / sampleRate)) / 2;
        int bin2 = (int)Math.Round(f2 * 8192.0 / sampleRate); // ~89

        Assert.True(peakDb[bin1] > -20.0);
        Assert.True(peakDb[bin2] > -20.0);
        Assert.True(peakDb[binMid] < peakDb[bin1] - 3.0,
            $"Expected spectral dip between resolved tones: bin1={peakDb[bin1]:F2}, mid={peakDb[binMid]:F2}, bin2={peakDb[bin2]:F2}");
    }

    [Theory]
    [InlineData(44100, 16)]
    [InlineData(48000, 24)]
    [InlineData(88200, 16)]
    [InlineData(96000, 24)]
    [InlineData(192000, 24)]
    public void OverlappingMode_IsIndependentOfChunkBoundaries_AndPreservesCalibration(int sampleRate, int bitDepth)
    {
        int frames = sampleRate / 4; // 250 ms
        var samples = new float[frames * 2];
        double quant = Math.Pow(2, bitDepth - 1);
        double testFreq = sampleRate * 0.25; // Nyquist/2

        for (int f = 0; f < frames; f++)
        {
            double val = 0.5 * Math.Sin(2.0 * Math.PI * testFreq * f / sampleRate);
            float q = (float)(Math.Round(val * quant) / quant);
            samples[f * 2] = q;
            samples[f * 2 + 1] = q * 0.5f;
        }

        var whole = new AudioAnalysisEngine(sampleRate, 2, frames, SpectrumAnalysisMode.Overlapping);
        whole.ProcessAudioBlock(samples);
        var wholeReport = whole.GenerateReport();

        var chunked = new AudioAnalysisEngine(sampleRate, 2, frames, SpectrumAnalysisMode.Overlapping);
        for (int offset = 0; offset < samples.Length; offset += 202)
        {
            chunked.ProcessAudioBlock(samples.AsSpan(offset, Math.Min(202, samples.Length - offset)));
        }
        var chunkedReport = chunked.GenerateReport();

        Assert.Equal(wholeReport.LinearSpectrum!.PeakDb, chunkedReport.LinearSpectrum!.PeakDb);
        Assert.Equal(wholeReport.LogSpectrum!.PeakDb, chunkedReport.LogSpectrum!.PeakDb);
        Assert.Equal(wholeReport.SpectrogramMax, chunkedReport.SpectrogramMax);
        Assert.Equal(wholeReport.SpectrogramAvg, chunkedReport.SpectrogramAvg);
        Assert.Equal(wholeReport.SpectrogramMin, chunkedReport.SpectrogramMin);

        int bin = (int)Math.Round(testFreq * 8192.0 / sampleRate);
        // Combined L=0.5, R=0.25 -> average amplitude ~0.375 (~ -8.5 dBFS, Blackman-Harris coherent scaling ~ -7.8 dB)
        Assert.InRange(wholeReport.LinearSpectrum.PeakDb[bin], -10.0, -6.5);
    }

    [Fact]
    public void OverlappingMode_CoversStartupEofAndShortInputs_AndResetsCleanly()
    {
        const int sampleRate = 96000;
        const double toneFreq = 12000.0;

        // 1. Short input (< 8192 frames) still produces calibrated peak in Finish()
        const int shortFrames = 4096;
        var shortSamples = new float[shortFrames * 2];
        for (int f = 0; f < shortFrames; f++)
        {
            float s = (float)(0.5 * Math.Sin(2.0 * Math.PI * toneFreq * f / sampleRate));
            shortSamples[f * 2] = s;
            shortSamples[f * 2 + 1] = s;
        }

        var engine = new AudioAnalysisEngine(sampleRate, 2, shortFrames, SpectrumAnalysisMode.Overlapping);
        engine.ProcessAudioBlock(shortSamples);
        var shortReport = engine.GenerateReport();
        int bin = (int)Math.Round(toneFreq * 8192.0 / sampleRate);
        Assert.InRange(shortReport.LinearSpectrum!.PeakDb[bin], -8.0, -4.5);

        // 2. Reset clears all accumulated peaks and allows clean reuse
        engine.Reset();
        engine.TotalFrames = shortFrames;
        engine.ProcessAudioBlock(new float[shortFrames * 2]);
        var silenceReport = engine.GenerateReport();
        Assert.All(silenceReport.LinearSpectrum!.PeakDb, db => Assert.True(db <= -190.0));

        // 3. Startup window main lobe (frames 2000..3500, before window 1) and EOF tail (frames 20800..23999,
        //    covering the trailing frames analyzed via Finish()) are both captured cleanly
        const int longFrames = 24000;
        var edgeSamples = new float[longFrames * 2];
        const double startFreq = 6000.0;
        const double endFreq = 30000.0;
        for (int f = 2000; f < 3500; f++)
        {
            float s = (float)(0.25 * Math.Sin(2.0 * Math.PI * startFreq * f / sampleRate));
            edgeSamples[f * 2] = s;
            edgeSamples[f * 2 + 1] = s;
        }
        for (int f = 20800; f < longFrames; f++)
        {
            float s = (float)(0.25 * Math.Sin(2.0 * Math.PI * endFreq * f / sampleRate));
            edgeSamples[f * 2] = s;
            edgeSamples[f * 2 + 1] = s;
        }

        var edgeEngine = new AudioAnalysisEngine(sampleRate, 2, longFrames, SpectrumAnalysisMode.Overlapping);
        edgeEngine.ProcessAudioBlock(edgeSamples);
        var edgeReport = edgeEngine.GenerateReport();

        int startBin = (int)Math.Round(startFreq * 8192.0 / sampleRate);
        int endBin = (int)Math.Round(endFreq * 8192.0 / sampleRate);
        Assert.True(edgeReport.LinearSpectrum!.PeakDb[startBin] > -35.0,
            $"Startup burst missed: {edgeReport.LinearSpectrum.PeakDb[startBin]}");
        Assert.True(edgeReport.LinearSpectrum.PeakDb[endBin] > -35.0,
            $"EOF burst missed: {edgeReport.LinearSpectrum.PeakDb[endBin]}");
    }
}
