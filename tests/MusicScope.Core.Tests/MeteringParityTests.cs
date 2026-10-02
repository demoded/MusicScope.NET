using MusicScope.Audio;
using MusicScope.Core.Levels;
using MusicScope.Core.Loudness;

namespace MusicScope.Core.Tests;

public class MeteringParityTests
{
    [Fact]
    public async Task HighDynamicsReference_MatchesOriginalCrestAndPlr_IncludingFinalPartialBlock()
    {
        string path = Path.GetFullPath(Path.Combine(AppContext.BaseDirectory,
            "..", "..", "..", "..", "..", "TestAudioSamples", "05_HighDynamics_Crest_14dB_44k_16bit.wav"));
        var decoder = new FfmpegAudioDecoder();
        var info = await decoder.ProbeAsync(path);
        var levels = new TruePeakMeter(info.ChannelCount, info.SampleRate);
        var loudness = new LoudnessMeter(info.SampleRate, info.ChannelCount);
        var engine = new AudioAnalysisEngine(info.SampleRate, info.ChannelCount, info.TotalFrames);
        await decoder.DecodeAsync(path, (samples, _, _, _) =>
        {
            levels.ProcessInterleaved(samples.Span);
            loudness.ProcessInterleaved(samples.Span);
            engine.ProcessAudioBlock(samples.Span);
            return Task.CompletedTask;
        });

        var levelResult = levels.CalculateResult();
        var loudnessResult = loudness.CalculateResult();
        var report = engine.GenerateReport();
        Assert.Equal(13.9, levelResult.CrestFactorDb);
        Assert.Equal(10.0, loudnessResult.PlrAvgDb);
        Assert.Equal(levelResult.CrestFactorDb, report.Levels.CrestFactorDb);
        Assert.Equal(loudnessResult.PlrAvgDb, report.Loudness.PlrAvgDb);

        // 440,320 frames: 199 complete 50 ms blocks and a 1,525-frame tail.
        // Unrounded results from the isolated legacy-block diagnostic catch a skipped or padded tail.
        Assert.InRange(levels.CrestAvgDb, 13.945529, 13.945532);
        Assert.InRange(loudness.PlrAvgDb, 10.030837, 10.030840);
        double crest = levels.CrestAvgDb;
        double plr = loudness.PlrAvgDb;
        levels.CalculateResult();
        loudness.CalculateResult();
        Assert.Equal(crest, levels.CrestAvgDb);
        Assert.Equal(plr, loudness.PlrAvgDb);
    }

    [Theory]
    [InlineData(22050, 1103, 1, false)]
    [InlineData(44100, 2205, 2, false)]
    [InlineData(44100, 2205, 1, true)]
    [InlineData(48000, 2400, 2, true)]
    [InlineData(88200, 4410, 1, false)]
    [InlineData(96000, 4800, 2, false)]
    [InlineData(96000, 4800, 1, true)]
    [InlineData(192000, 9600, 2, true)]
    public void Meters_Use50msBlocks_AndEightBlockWarmup(int sampleRate, int blockFrames, int channels, bool useFloat)
    {
        double[] samples = CreateSignal(blockFrames * 9, sampleRate, channels);
        var levels = new TruePeakMeter(channels, sampleRate);
        var loudness = new LoudnessMeter(sampleRate, channels);

        Process(levels, loudness, samples.AsSpan(0, (blockFrames - 1) * channels), useFloat);
        Assert.Equal(-100, levels.CurrentBlockPeakLeftDb);
        Process(levels, loudness, samples.AsSpan((blockFrames - 1) * channels, channels), useFloat);
        Assert.True(levels.CurrentBlockPeakLeftDb > -10);

        Process(levels, loudness, samples.AsSpan(blockFrames * channels, (8 * blockFrames - 1) * channels), useFloat);
        Assert.Equal(0, levels.CrestAvgDb);
        Assert.Equal(0, loudness.PlrAvgDb);
        Process(levels, loudness, samples.AsSpan((9 * blockFrames - 1) * channels, channels), useFloat);
        Assert.True(levels.CrestAvgDb > 0);
        Assert.NotEqual(0, loudness.PlrAvgDb);
    }

    [Theory]
    [InlineData(44100, 2, false)]
    [InlineData(44100, 1, true)]
    [InlineData(96000, 1, false)]
    [InlineData(96000, 2, true)]
    public void Meters_AreIndependentOfInputChunks_AndResetClearsPartialBlocks(int sampleRate, int channels, bool useFloat)
    {
        double[] samples = CreateSignal(sampleRate + 137, sampleRate, channels);
        var wholeLevels = new TruePeakMeter(channels, sampleRate);
        var wholeLoudness = new LoudnessMeter(sampleRate, channels);
        Process(wholeLevels, wholeLoudness, samples, useFloat);
        var expectedLevels = wholeLevels.CalculateResult();
        var expectedLoudness = wholeLoudness.CalculateResult();

        var chunkedLevels = new TruePeakMeter(channels, sampleRate);
        var chunkedLoudness = new LoudnessMeter(sampleRate, channels);
        Process(chunkedLevels, chunkedLoudness, samples.AsSpan(0, 123 * channels), useFloat);
        chunkedLevels.Reset();
        chunkedLoudness.Reset();
        for (int offset = 0; offset < samples.Length; offset += 137 * channels)
            Process(chunkedLevels, chunkedLoudness,
                samples.AsSpan(offset, Math.Min(137 * channels, samples.Length - offset)), useFloat);

        Assert.Equal(expectedLevels, chunkedLevels.CalculateResult());
        Assert.Equal(expectedLoudness.PlrAvgDb, chunkedLoudness.CalculateResult().PlrAvgDb);
        Assert.Equal(wholeLevels.CrestAvgDb, chunkedLevels.CrestAvgDb, 9);
        Assert.Equal(wholeLoudness.PlrAvgDb, chunkedLoudness.PlrAvgDb, 9);
        chunkedLevels.CalculateResult();
        chunkedLoudness.CalculateResult();
        Assert.Equal(wholeLevels.CrestAvgDb, chunkedLevels.CrestAvgDb, 9);
        Assert.Equal(wholeLoudness.PlrAvgDb, chunkedLoudness.PlrAvgDb, 9);
    }

    private static double[] CreateSignal(int frames, int sampleRate, int channels)
    {
        var samples = new double[frames * channels];
        for (int frame = 0; frame < frames; frame++)
        {
            double amplitude = frame % 3079 < 500 ? 0.8 : 0.02;
            double value = amplitude * Math.Sin(2 * Math.PI * 997 * frame / sampleRate);
            for (int channel = 0; channel < channels; channel++)
                samples[frame * channels + channel] = value;
        }
        return samples;
    }

    private static void Process(TruePeakMeter levels, LoudnessMeter loudness, ReadOnlySpan<double> samples, bool useFloat)
    {
        if (useFloat)
        {
            float[] floats = samples.ToArray().Select(value => (float)value).ToArray();
            levels.ProcessInterleaved(floats);
            loudness.ProcessInterleaved(floats);
        }
        else
        {
            levels.ProcessInterleaved(samples);
            loudness.ProcessInterleaved(samples);
        }
    }
}
