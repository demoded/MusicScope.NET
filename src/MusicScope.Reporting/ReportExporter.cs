using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using MusicScope.Core;

namespace MusicScope.Reporting;

/// <summary>
/// Structured reporting service for exporting audio analysis results to CSV, JSON, and formatted TXT.
/// </summary>
public static class ReportExporter
{
    private static readonly JsonSerializerOptions JsonOptions = new() { WriteIndented = true };

    /// <summary>
    /// Exports a report to JSON format.
    /// </summary>
    public static async Task ExportToJsonAsync(FullAnalysisReport report, string outputPath)
    {
        string json = JsonSerializer.Serialize(report, JsonOptions);
        await File.WriteAllTextAsync(outputPath, json, Encoding.UTF8);
    }

    /// <summary>
    /// Exports a batch of reports to a CSV table.
    /// </summary>
    public static async Task ExportToCsvAsync(IEnumerable<FullAnalysisReport> reports, string outputPath)
    {
        var sb = new StringBuilder();
        sb.AppendLine("Title,FilePath,Format,SampleRate,Channels,BitDepth,DurationSec,IntegratedLUFS,MomentaryMaxLUFS,ShortTermMaxLUFS,LRA_LU,SamplePeakLeftDb,SamplePeakRightDb,TruePeakLeftDb,TruePeakRightDb,RmsLeftDb,RmsRightDb,CrestDb,DynamicRangeDb,Correlation");

        foreach (var r in reports)
        {
            sb.Append('\"').Append(r.Title.Replace("\"", "\"\"")).Append("\",");
            sb.Append('\"').Append(r.FilePath.Replace("\"", "\"\"")).Append("\",");
            sb.Append(r.Format).Append(',');
            sb.Append(r.SampleRate).Append(',');
            sb.Append(r.Channels).Append(',');
            sb.Append(r.BitDepth).Append(',');
            sb.Append(r.Duration.TotalSeconds.ToString("F1")).Append(',');
            sb.Append(r.Loudness.IntegratedLoudness.ToString("F1")).Append(',');
            sb.Append(r.Loudness.MomentaryMax.ToString("F1")).Append(',');
            sb.Append(r.Loudness.ShortTermMax.ToString("F1")).Append(',');
            sb.Append(r.Loudness.LoudnessRange.ToString("F1")).Append(',');
            sb.Append(r.Levels.SamplePeakLeftDb.ToString("F2")).Append(',');
            sb.Append(r.Levels.SamplePeakRightDb.ToString("F2")).Append(',');
            sb.Append(r.Levels.TruePeakLeftDb.ToString("F2")).Append(',');
            sb.Append(r.Levels.TruePeakRightDb.ToString("F2")).Append(',');
            sb.Append(r.Levels.RmsLeftDb.ToString("F2")).Append(',');
            sb.Append(r.Levels.RmsRightDb.ToString("F2")).Append(',');
            sb.Append(r.Levels.CrestFactorDb.ToString("F1")).Append(',');
            sb.Append(r.Levels.DynamicRangeDb.ToString("F1")).Append(',');
            sb.AppendLine(r.Stereo.Correlation.ToString("F3"));
        }

        await File.WriteAllTextAsync(outputPath, sb.ToString(), Encoding.UTF8);
    }

    /// <summary>
    /// Exports a human-readable text report matching the original MusicScope text layout.
    /// </summary>
    public static async Task ExportToTextReportAsync(FullAnalysisReport r, string outputPath)
    {
        var sb = new StringBuilder();
        sb.AppendLine("================================================================================");
        sb.AppendLine("                          MusicScope.NET Analysis Report                        ");
        sb.AppendLine("================================================================================");
        sb.AppendLine($"Track Title      : {r.Title}");
        sb.AppendLine($"File Location    : {r.FilePath}");
        sb.AppendLine($"Format / Codec   : {r.Format}");
        sb.AppendLine($"Audio Stream     : {r.SampleRate} Hz | {r.Channels} Channels | {r.BitDepth}-bit");
        sb.AppendLine($"Duration         : {r.Duration:mm\\:ss\\.ff}");
        sb.AppendLine($"Analyzed At      : {r.AnalyzedAt:yyyy-MM-dd HH:mm:ss} UTC");
        sb.AppendLine("--------------------------------------------------------------------------------");
        sb.AppendLine("LOUDNESS (ITU-R BS.1770-4 / EBU R128)");
        sb.AppendLine($"  Integrated Loudness (IL) : {r.Loudness.IntegratedLoudness,6:F1} LUFS");
        sb.AppendLine($"  Max Momentary Loudness   : {r.Loudness.MomentaryMax,6:F1} LUFS");
        sb.AppendLine($"  Max Short-Term Loudness  : {r.Loudness.ShortTermMax,6:F1} LUFS");
        sb.AppendLine($"  Loudness Range (LRA)     : {r.Loudness.LoudnessRange,6:F1} LU (from {r.Loudness.LraLow:F1} to {r.Loudness.LraHigh:F1})");
        sb.AppendLine("--------------------------------------------------------------------------------");
        sb.AppendLine("LEVELS & DYNAMICS");
        sb.AppendLine($"  Sample Peak (L / R)      : {r.Levels.SamplePeakLeftDb,6:F2} dBFS / {r.Levels.SamplePeakRightDb,6:F2} dBFS");
        sb.AppendLine($"  True Peak (L / R, 4x)    : {r.Levels.TruePeakLeftDb,6:F2} dBTP / {r.Levels.TruePeakRightDb,6:F2} dBTP");
        sb.AppendLine($"  RMS Energy (L / R)       : {r.Levels.RmsLeftDb,6:F2} dBFS / {r.Levels.RmsRightDb,6:F2} dBFS");
        sb.AppendLine($"  CREST Factor (Peak/RMS)  : {r.Levels.CrestFactorDb,6:F1} dB");
        sb.AppendLine($"  Dynamic Range (DR)       : DR{r.Levels.DynamicRangeDb:F0}");
        sb.AppendLine("--------------------------------------------------------------------------------");
        sb.AppendLine("STEREO FIELD");
        sb.AppendLine($"  Phase Correlation        : {r.Stereo.Correlation,6:F3} (Range: [-1.0, +1.0])");
        sb.AppendLine($"  Balance (L <-> R)        : {r.Stereo.Balance,6:F3}");
        sb.AppendLine($"  Mid / Side Levels        : {r.Stereo.MidLevelDb,6:F2} dBFS / {r.Stereo.SideLevelDb,6:F2} dBFS");
        sb.AppendLine("================================================================================");

        await File.WriteAllTextAsync(outputPath, sb.ToString(), Encoding.UTF8);
    }
}
