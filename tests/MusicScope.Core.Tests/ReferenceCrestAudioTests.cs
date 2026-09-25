using System;
using System.IO;
using System.Threading.Tasks;
using MusicScope.Audio;
using MusicScope.Core;
using Xunit;
using Xunit.Abstractions;

namespace MusicScope.Core.Tests;

public class ReferenceCrestAudioTests
{
    private readonly ITestOutputHelper _output;

    public ReferenceCrestAudioTests(ITestOutputHelper output)
    {
        _output = output;
    }

    [Fact]
    public async Task Analyze_All_Reference_Wav_Samples()
    {
        string[] candidates =
        [
            Path.GetFullPath(Path.Combine(AppContext.BaseDirectory, "..", "..", "..", "..", "..", "TestAudioSamples")),
            Path.GetFullPath(Path.Combine(AppContext.BaseDirectory, "..", "..", "..", "..", "TestAudioSamples")),
            Path.GetFullPath(Path.Combine(Directory.GetCurrentDirectory(), "TestAudioSamples")),
            Path.GetFullPath(@"D:\git\MusicScope.NET\TestAudioSamples")
        ];
        string samplesDir = Array.Find(candidates, Directory.Exists) ?? candidates[0];

        Assert.True(Directory.Exists(samplesDir), $"Directory not found: {samplesDir}");

        var decoder = new FfmpegAudioDecoder();
        var files = Directory.GetFiles(samplesDir, "*.wav");
        Array.Sort(files);

        Assert.NotEmpty(files);

        _output.WriteLine("=================================================================================================");
        _output.WriteLine(string.Format("{0,-40} | {1,8} | {2,8} | {3,8} | {4,8} | {5,8} | {6,6}", "Filename", "SamplePeak", "TruePeak", "RMS", "CREST", "PLR_Avg", "DR"));
        _output.WriteLine("=================================================================================================");

        foreach (var file in files)
        {
            var probe = await decoder.ProbeAsync(file);
            var engine = new AudioAnalysisEngine(probe.SampleRate, probe.ChannelCount, probe.TotalFrames);

            await decoder.DecodeAsync(file, (chunk, channels, sRate, progress) =>
            {
                engine.ProcessAudioBlock(chunk.Span);
                return Task.CompletedTask;
            });

            var report = engine.GenerateReport(Path.GetFileNameWithoutExtension(file), file, probe.FormatName, probe.Duration, probe.BitDepth);
            _output.WriteLine(string.Format("{0,-40} | {1,8:F1} | {2,8:F1} | {3,8:F1} | {4,8:F1} | {5,8:F1} | {6,6:F1}",
                Path.GetFileName(file),
                report.Levels.SamplePeakLeftDb,
                report.Levels.TruePeakLeftDb,
                report.Levels.RmsLeftDb,
                report.Levels.CrestFactorDb,
                report.Loudness.PlrAvgDb,
                report.Levels.DynamicRangeDb));
        }
        _output.WriteLine("=================================================================================================");
    }

    [Fact]
    public async Task Sweep_FrequencySpectrum_Matches_MusicScope_Original_Characteristics()
    {
        string[] candidates =
        [
            Path.GetFullPath(Path.Combine(AppContext.BaseDirectory, "..", "..", "..", "..", "..", "TestAudioSamples")),
            Path.GetFullPath(Path.Combine(AppContext.BaseDirectory, "..", "..", "..", "..", "TestAudioSamples")),
            Path.GetFullPath(Path.Combine(Directory.GetCurrentDirectory(), "TestAudioSamples")),
            Path.GetFullPath(@"D:\git\MusicScope.NET\TestAudioSamples")
        ];
        string samplesDir = Array.Find(candidates, Directory.Exists) ?? candidates[0];
        string sweepFile = Path.Combine(samplesDir, "07_Sweep_10kHz_22.5kHz_-60dBFS_48k_24bit.wav");

        Assert.True(File.Exists(sweepFile), $"Sweep file not found: {sweepFile}");

        var decoder = new FfmpegAudioDecoder();
        var probe = await decoder.ProbeAsync(sweepFile);
        var engine = new AudioAnalysisEngine(probe.SampleRate, probe.ChannelCount, probe.TotalFrames);

        await decoder.DecodeAsync(sweepFile, (chunk, channels, sRate, progress) =>
        {
            engine.ProcessAudioBlock(chunk.Span);
            return Task.CompletedTask;
        });

        var report = engine.GenerateReport(Path.GetFileNameWithoutExtension(sweepFile), sweepFile, probe.FormatName, probe.Duration, probe.BitDepth);
        double[] spectrum = report.SpectrumMagnitudesDb;

        // At 48kHz, bin resolution is 48000 / 2048 = 23.4375 Hz/bin.
        // Sweep is between 10,000 Hz (bin ~427) and 22,500 Hz (bin ~960)
        int startBin = (int)(10000.0 / (48000.0 / 2048.0));
        int endBin = (int)(22500.0 / (48000.0 / 2048.0));

        var sweepBins = spectrum[startBin..endBin];
        double maxDb = double.MinValue;
        double minDb = double.MaxValue;
        for (int i = 0; i < sweepBins.Length; i++)
        {
            if (sweepBins[i] > maxDb) maxDb = sweepBins[i];
            if (sweepBins[i] < minDb) minDb = sweepBins[i];
        }

        _output.WriteLine($"Sweep spectrum range: min = {minDb:F2} dB, max = {maxDb:F2} dB");

        // 1. Must never touch or exceed -60 dB line (MusicScope original peak is ~-64.4 dB)
        Assert.True(maxDb < -62.0, $"Sweep spectrum max value {maxDb:F2} dB reached or exceeded -60 dB!");
        Assert.True(maxDb > -67.0, $"Sweep spectrum max value {maxDb:F2} dB was unexpectedly low!");

        // 2. Central 90% of sweep bins must have minimal fluctuation (< 2.5 dB)
        var sorted = (double[])sweepBins.Clone();
        Array.Sort(sorted);
        double p5 = sorted[(int)(sorted.Length * 0.05)];
        double p95 = sorted[(int)(sorted.Length * 0.95)];
        double delta = p95 - p5;

        _output.WriteLine($"Central 90% sweep ripple: p5 = {p5:F2} dB, p95 = {p95:F2} dB, delta = {delta:F2} dB");

        Assert.True(delta < 2.5, $"Sweep spectrum ripple delta ({delta:F2} dB) exceeded 2.5 dB!");
    }
}
