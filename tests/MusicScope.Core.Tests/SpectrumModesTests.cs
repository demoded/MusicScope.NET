using MusicScope.Core;

namespace MusicScope.Core.Tests;

public class SpectrumModesTests
{
    [Theory]
    [InlineData(1, 0, 0)]
    [InlineData(1, Math.PI, 0)]
    [InlineData(0.25, Math.PI / 2, -1)]
    [InlineData(2, Math.PI / 2, 1)]
    public void Spectrum_PreservesChannelSeparation_PanoramaAndPhase(double rightGain, double phase, int panSign)
    {
        var engine = AnalyzeTone(1, rightGain, phase);
        var snapshot = engine.GetRealtimeSnapshot();
        Assert.NotNull(snapshot.LinearSpectrum);
        Assert.NotNull(snapshot.LogSpectrum);
        Assert.Equal(1024, snapshot.LinearSpectrum.PeakDb.Length);
        Assert.Equal(4096, snapshot.LogSpectrum.PeakDb.Length);
        Assert.Equal(snapshot.CumulativePeakSpectrumDb, snapshot.LinearSpectrum.PeakDb);
        foreach (var frame in new[] { snapshot.LinearSpectrum, snapshot.LogSpectrum })
        {
            int bin = frame.PeakDb.Length / 16; // 1500 Hz at 48 kHz.
            Assert.Equal(20 * Math.Log10(rightGain), frame.RightDb[bin] - frame.LeftDb[bin], 3);
            Assert.Equal(phase, frame.PhaseRadians[bin], 3);
            if (panSign == 0) Assert.InRange(Math.Abs(frame.Panorama[bin]), 0, 1e-7);
            else Assert.Equal(panSign, Math.Sign(frame.Panorama[bin]));
        }
    }

    [Fact]
    public void Spectrum_RetainsValuesBelow144Db_AndSilenceReaches200Db()
    {
        var report = AnalyzeTone(1e-8, 1, 0).GenerateReport();
        foreach (var frame in new[] { report.LinearSpectrum!, report.LogSpectrum! })
            Assert.InRange(frame.PeakDb[frame.PeakDb.Length / 16], -164, -158);
        var silence = new AudioAnalysisEngine(48000);
        silence.ProcessAudioBlock(new float[48000]);
        var silent = silence.GetRealtimeSnapshot();
        foreach (var frame in new[] { silent.LinearSpectrum!, silent.LogSpectrum! })
        {
            Assert.All(frame.PeakDb, db => Assert.Equal(-200, db));
            Assert.All(frame.PhaseRadians, p => Assert.Equal(0, p));
            Assert.All(frame.Panorama, p => Assert.Equal(0, p));
        }
    }

    [Fact]
    public void Spectrum_FinalReportAndSnapshotsOwnTheirData_AndResetClearsBothModes()
    {
        var engine = AnalyzeTone(1, 0.25, Math.PI);
        var report = engine.GenerateReport();
        var snapshot = engine.GetRealtimeSnapshot();
        Assert.NotSame(report.LogSpectrum!.PeakDb, snapshot.LogSpectrum!.PeakDb);
        Assert.Equal(report.LogSpectrum.PeakDb, snapshot.LogSpectrum.PeakDb);
        engine.Reset();
        Assert.All(engine.GetRealtimeSnapshot().LinearSpectrum!.PeakDb, db => Assert.Equal(-200, db));
        Assert.All(engine.GetRealtimeSnapshot().LogSpectrum!.PeakDb, db => Assert.Equal(-200, db));
        Assert.True(report.LogSpectrum.PeakDb.Max() > -20);
    }

    [Fact]
    public void MonoSpectrum_HasEqualChannelsAndNoPanoramaOrPhase()
    {
        var engine = new AudioAnalysisEngine(48000, 1);
        var samples = new float[48000];
        for (int i = 0; i < samples.Length; i++) samples[i] = (float)Math.Sin(2 * Math.PI * 1500 * i / 48000);
        engine.ProcessAudioBlock(samples);
        var report = engine.GenerateReport();
        foreach (var frame in new[] { report.LinearSpectrum!, report.LogSpectrum! })
        {
            Assert.Equal(frame.LeftDb, frame.RightDb);
            Assert.All(frame.Panorama, p => Assert.Equal(0, p));
            Assert.All(frame.PhaseRadians, p => Assert.Equal(0, p));
        }
    }

    [Fact]
    public void LogSpectrum_PackedRealFftMatchesFullComplexFft_RegardlessOfInputChunkBoundaries()
    {
        const int size = 8192;
        const int frames = size; // Exactly one FFT at the first full reference window.
        var samples = new float[frames * 2];
        var random = new Random(5);
        for (int i = 0; i < samples.Length; i++) samples[i] = (float)(random.NextDouble() - 0.5);
        var engine = new AudioAnalysisEngine(48000);
        engine.ProcessAudioBlock(samples);
        var actual = engine.GetRealtimeSnapshot().LogSpectrum!;
        var fft = new MusicScope.Core.DSP.FastFourierTransform(size);
        var window = MusicScope.Core.DSP.WindowFunctions.Create(MusicScope.Core.DSP.WindowType.BlackmanHarris, size);
        for (int channel = 0; channel < 2; channel++)
        {
            var real = new double[size];
            var imag = new double[size];
            for (int i = 0; i < size; i++) real[i] = samples[(frames - size + i) * 2 + channel] * window[i];
            fft.Forward(real, imag);
            var channelDb = channel == 0 ? actual.LeftDb : actual.RightDb;
            for (int i = 0; i < size / 2; i++)
            {
                double magnitude = Math.Sqrt(real[i] * real[i] + imag[i] * imag[i]) / (size / 8.0);
                double expected = 20 * Math.Log10(Math.Max(1e-10, (1e-10 + magnitude) / 2));
                Assert.Equal(expected, channelDb[i], 8);
            }
        }
        var chunked = new AudioAnalysisEngine(48000);
        for (int offset = 0; offset < samples.Length; offset += 202)
            chunked.ProcessAudioBlock(samples.AsSpan(offset, Math.Min(202, samples.Length - offset)));
        var other = chunked.GetRealtimeSnapshot().LogSpectrum!;
        Assert.Equal(actual.PeakDb, other.PeakDb);
        Assert.Equal(actual.LeftDb, other.LeftDb);
        Assert.Equal(actual.RightDb, other.RightDb);
        Assert.Equal(actual.Panorama, other.Panorama);
        Assert.Equal(actual.PhaseRadians, other.PhaseRadians);
    }

    [Fact]
    public void LinearSpectrum_UpdatesOncePer2048SampleReferenceBlock()
    {
        const int sampleRate = 48000;
        const int fftSize = 2048;
        const int frames = fftSize * 4;
        var samples = new float[frames];
        var random = new Random(17);
        for (int i = 0; i < samples.Length; i++) samples[i] = (float)(random.NextDouble() - 0.5);

        var engine = new AudioAnalysisEngine(sampleRate, channelCount: 1);
        engine.ProcessAudioBlock(samples);
        var actual = engine.GetRealtimeSnapshot().LinearSpectrum!.PeakDb;

        var fft = new MusicScope.Core.DSP.FastFourierTransform(fftSize);
        var window = MusicScope.Core.DSP.WindowFunctions.Create(MusicScope.Core.DSP.WindowType.BlackmanHarris, fftSize);
        var smoothed = Enumerable.Repeat(1e-10, fftSize / 2).ToArray();
        var expected = Enumerable.Repeat(1e-10, fftSize / 2).ToArray();
        for (int start = 0; start < frames; start += fftSize)
        {
            var real = new double[fftSize];
            var imag = new double[fftSize];
            for (int i = 0; i < fftSize; i++) real[i] = samples[start + i] * window[i];
            fft.Forward(real, imag);
            for (int bin = 0; bin < expected.Length; bin++)
            {
                double magnitude = Math.Sqrt(real[bin] * real[bin] + imag[bin] * imag[bin]) * 8 / fftSize;
                smoothed[bin] = Math.Max(1e-10, (smoothed[bin] + magnitude) / 2);
                expected[bin] = Math.Max(expected[bin], smoothed[bin]);
            }
        }

        for (int bin = 0; bin < expected.Length; bin++)
            Assert.Equal(20 * Math.Log10(expected[bin]), actual[bin], 8);
    }

    private static AudioAnalysisEngine AnalyzeTone(double amplitude, double rightGain, double phase)
    {
        var engine = new AudioAnalysisEngine(48000);
        var samples = new float[48000 * 2 * 16];
        for (int i = 0; i < samples.Length / 2; i++)
        {
            double angle = 2 * Math.PI * 1500 * i / 48000;
            samples[i * 2] = (float)(amplitude * Math.Sin(angle));
            samples[i * 2 + 1] = (float)(amplitude * rightGain * Math.Sin(angle + phase));
        }
        engine.ProcessAudioBlock(samples);
        return engine;
    }
}
