using System;
using System.IO;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using MusicScope.Core;
using MusicScope.Core.Levels;
using MusicScope.Core.Loudness;
using MusicScope.Core.Stereo;
using MusicScope.Reporting;
using Xunit;

namespace MusicScope.Core.Tests;

public class ReportDurationFormattingTests
{
    [Theory]
    [InlineData(0, "00:00", "00:00.00")]
    [InlineData(-10, "00:00", "00:00.00")]
    [InlineData(45.5, "00:45", "00:45.50")]
    [InlineData(192.34, "03:12", "03:12.34")]
    [InlineData(904.5, "15:04", "15:04.50")]
    [InlineData(3599.99, "59:59", "59:59.99")]
    public void FormatDuration_UnderOneHour_FormatsWithoutHour(double seconds, string expectedStandard, string expectedWithHundredths)
    {
        var duration = TimeSpan.FromSeconds(seconds);
        string standard = ReportExporter.FormatDuration(duration, includeHundredths: false);
        string withHundredths = ReportExporter.FormatDuration(duration, includeHundredths: true);

        Assert.Equal(expectedStandard, standard);
        Assert.Equal(expectedWithHundredths, withHundredths);
    }

    [Theory]
    [InlineData(3600, "1:00:00", "1:00:00.00")]
    [InlineData(4504.5, "1:15:04", "1:15:04.50")] // Exact case from GitHub Issue #25 (1:15:04.500)
    [InlineData(36306.78, "10:05:06", "10:05:06.78")]
    [InlineData(93600, "26:00:00", "26:00:00.00")] // > 24 hours test
    public void FormatDuration_OneHourOrMore_IncludesHours(double seconds, string expectedStandard, string expectedWithHundredths)
    {
        var duration = TimeSpan.FromSeconds(seconds);
        string standard = ReportExporter.FormatDuration(duration, includeHundredths: false);
        string withHundredths = ReportExporter.FormatDuration(duration, includeHundredths: true);

        Assert.Equal(expectedStandard, standard);
        Assert.Equal(expectedWithHundredths, withHundredths);
    }

    [Fact]
    public async Task ExportToTextReportAsync_FormatsLongDurationWithHours()
    {
        var tempFile = Path.GetTempFileName();
        try
        {
            var report = new FullAnalysisReport
            {
                Title = "01_01_Stream せせらぎ",
                FilePath = @"C:\Musics\Test\stream.flac",
                Format = "FLAC",
                SampleRate = 96000,
                Channels = 2,
                BitDepth = 24,
                Duration = TimeSpan.FromSeconds(4504.5), // 1h 15m 04.5s
                AnalyzedAt = DateTime.UtcNow,
                Loudness = new LoudnessResult
                {
                    IntegratedLoudness = -18.2,
                    MomentaryMax = -14.1,
                    ShortTermMax = -15.3,
                    LoudnessRange = 6.2,
                    LraLow = -22.0,
                    LraHigh = -15.8
                },
                Levels = new LevelsResult
                {
                    SamplePeakLeftDb = -0.5,
                    SamplePeakRightDb = -0.4,
                    TruePeakLeftDb = 0.1,
                    TruePeakRightDb = 0.2,
                    RmsLeftDb = -20.1,
                    RmsRightDb = -19.8,
                    CrestFactorDb = 19.6,
                    DynamicRangeDb = 12.0
                },
                Stereo = new StereoResult
                {
                    Correlation = 0.85,
                    Balance = 0.0,
                    MidLevelDb = -18.0,
                    SideLevelDb = -24.0
                }
            };

            await ReportExporter.ExportToTextReportAsync(report, tempFile);
            string content = await File.ReadAllTextAsync(tempFile);

            Assert.Contains("Duration         : 1:15:04.50", content);
        }
        finally
        {
            if (File.Exists(tempFile))
                File.Delete(tempFile);
        }
    }

    [Theory]
    [InlineData("Duration: 01:15:04.50, start: 0.000000", 1, 15, 4.5)]
    [InlineData("Duration: 00:03:45.67, start: 0.000000", 0, 3, 45.67)]
    [InlineData("Duration: 02:00:00, start: 0.000000", 2, 0, 0.0)]
    public void FfmpegDurationRegex_MatchesWithAndWithoutFractions(string input, int expectedHours, int expectedMinutes, double expectedSeconds)
    {
        var durMatch = Regex.Match(input, @"Duration:\s*(\d+):(\d+):(\d+(?:\.\d+)?)");
        Assert.True(durMatch.Success);

        int h = int.Parse(durMatch.Groups[1].Value);
        int m = int.Parse(durMatch.Groups[2].Value);
        double s = double.Parse(durMatch.Groups[3].Value, System.Globalization.CultureInfo.InvariantCulture);

        Assert.Equal(expectedHours, h);
        Assert.Equal(expectedMinutes, m);
        Assert.Equal(expectedSeconds, s, precision: 2);
    }
}
