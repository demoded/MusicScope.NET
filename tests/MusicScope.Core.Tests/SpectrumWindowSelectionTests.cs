using MusicScope.Core;

namespace MusicScope.Core.Tests;

public class SpectrumWindowSelectionTests
{
    [Fact]
    public void LinearSpectrum_WarmsUpBeforeItsFirstMeasurement()
    {
        var engine = new AudioAnalysisEngine(96000);
        var samples = new float[4800 * 2];
        Array.Fill(samples, 0.125f);
        engine.ProcessAudioBlock(samples);
        Assert.All(engine.GetRealtimeSnapshot().LinearSpectrum!.PeakDb, db => Assert.Equal(-200, db));
        engine.ProcessAudioBlock(samples);
        Assert.InRange(engine.GetRealtimeSnapshot().LinearSpectrum!.PeakDb[0], -18, -17);
    }

    [Theory]
    [InlineData(44100, 16)]
    [InlineData(48000, 24)]
    [InlineData(88200, 16)]
    [InlineData(96000, 24)]
    [InlineData(192000, 24)]
    public void LinearSpectrum_UsesFirstWindowOfEach50MsInterval(int sampleRate, int bitDepth)
    {
        const int size = 2048;
        int stride = sampleRate / 20;
        int frames = stride * 4;
        var samples = new float[frames * 2];
        double quantization = Math.Pow(2, bitDepth - 1);
        for (int frame = 0; frame < frames; frame++)
        {
            // The original SpectrumModule caps each 50 ms input block to its first 2048 frames.
            // Different content in the rest of the block exposes selecting its last window instead.
            int position = frame % stride;
            double value = position < size
                ? 0.125 * Math.Sin(2 * Math.PI * 615 * position / size)
                : 0.0625 * Math.Sin(2 * Math.PI * 819 * position / size);
            samples[frame * 2] = (float)(Math.Round(value * quantization) / quantization);
            samples[frame * 2 + 1] = samples[frame * 2] * 0.5f;
        }

        var engine = new AudioAnalysisEngine(sampleRate, 2, frames);
        engine.ProcessAudioBlock(samples);
        var snapshot = engine.GetRealtimeSnapshot();
        var report = engine.GenerateReport();

        // Direct DFT, independent of the production FFT, window helper and smoothing helper.
        foreach (int bin in new[] { 0, 200, 615, 819, 1023 })
        {
            double magnitude = FirstWindowMagnitude(samples, bin);
            double expected = 20 * Math.Log10(Math.Max(1e-10, magnitude * 7 / 8 + 1e-10 / 8));
            // Allow rounding differences between a direct DFT and FFT near the -200 dB floor.
            Assert.InRange(snapshot.LinearSpectrum!.PeakDb[bin], expected - 0.001, expected + 0.001);
            Assert.InRange(report.SpectrumMagnitudesDb[bin], expected - 0.001, expected + 0.001);
        }

        // Window selection must be tied to audio frames, independently of decoder callback sizes.
        var chunked = new AudioAnalysisEngine(sampleRate, 2, frames);
        for (int offset = 0; offset < samples.Length; offset += 202)
            chunked.ProcessAudioBlock(samples.AsSpan(offset, Math.Min(202, samples.Length - offset)));
        var other = chunked.GenerateReport();
        Assert.Equal(report.LinearSpectrum!.PeakDb, other.LinearSpectrum!.PeakDb);
        Assert.Equal(report.LogSpectrum!.PeakDb, other.LogSpectrum!.PeakDb);
        Assert.Equal(report.SpectrogramMax, other.SpectrogramMax);
        Assert.Equal(report.SpectrogramAvg, other.SpectrogramAvg);
        Assert.Equal(report.SpectrogramMin, other.SpectrogramMin);
    }

    [Theory]
    [InlineData(29000)]
    [InlineData(38000)]
    [InlineData(47000)]
    public void ContinuousUltrasonicTone_RemainsVisibleInBothSpectrumModes(int frequency)
    {
        const int sampleRate = 96000;
        var samples = new float[sampleRate * 2];
        for (int frame = 0; frame < sampleRate; frame++)
        {
            samples[frame * 2] = (float)(0.01 * Math.Sin(2 * Math.PI * frequency * frame / sampleRate));
            samples[frame * 2 + 1] = samples[frame * 2];
        }
        var engine = new AudioAnalysisEngine(sampleRate);
        engine.ProcessAudioBlock(samples);
        var report = engine.GenerateReport();
        foreach (var spectrum in new[] { report.LinearSpectrum!, report.LogSpectrum! })
        {
            int bin = (int)Math.Round(frequency * spectrum.PeakDb.Length * 2.0 / sampleRate);
            Assert.InRange(spectrum.PeakDb[bin], -42, -38);
        }
    }

    private static double FirstWindowMagnitude(float[] samples, int bin)
    {
        const int size = 2048;
        double real = 0, imaginary = 0;
        for (int frame = 0; frame < size; frame++)
        {
            double angle = 2 * Math.PI * frame / (size - 1);
            double window = 0.27105140069342 - 0.43329793923448 * Math.Cos(angle)
                + 0.21812299954311 * Math.Cos(2 * angle) - 0.06592544638803 * Math.Cos(3 * angle)
                + 0.01081174209837 * Math.Cos(4 * angle) - 7.7658482522e-4 * Math.Cos(5 * angle)
                + 1.388721735e-5 * Math.Cos(6 * angle);
            double value = (samples[frame * 2] + samples[frame * 2 + 1]) / 2.0 * window;
            double phase = 2 * Math.PI * bin * frame / size;
            real += value * Math.Cos(phase);
            imaginary -= value * Math.Sin(phase);
        }
        return Math.Sqrt(real * real + imaginary * imaginary) * 8 / size;
    }
}
