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
}
