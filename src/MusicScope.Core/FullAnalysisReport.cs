using System;
using MusicScope.Core.Levels;
using MusicScope.Core.Loudness;
using MusicScope.Core.Stereo;

namespace MusicScope.Core;

/// <summary>
/// Comprehensive analysis report for a single track or audio session.
/// Matches the metrics displayed in XiVero MusicScope's main UI and reports.
/// </summary>
public record FullAnalysisReport
{
    public string Title { get; init; } = string.Empty;
    public string FilePath { get; init; } = string.Empty;
    public string Format { get; init; } = string.Empty;
    public double SampleRate { get; init; } = 44100.0;
    public int Channels { get; init; } = 2;
    public int BitDepth { get; init; } = 16;
    public TimeSpan Duration { get; init; } = TimeSpan.Zero;
    public DateTime AnalyzedAt { get; init; } = DateTime.UtcNow;

    public LoudnessResult Loudness { get; init; } = new();
    public LevelsResult Levels { get; init; } = new();
    public StereoResult Stereo { get; init; } = new();

    /// <summary>
    /// Average frequency spectrum in dBFS (size / 2 frequency bins).
    /// </summary>
    public double[] SpectrumMagnitudesDb { get; init; } = [];

    /// <summary>
    /// Polar peak history across 512 radial bins.
    /// </summary>
    public double[] PeakHistory { get; init; } = [];

    /// <summary>
    /// Polar loudness history across 512 radial bins.
    /// </summary>
    public double[] LoudnessHistory { get; init; } = [];

    /// <summary>
    /// Cumulative 2D Spectrogram buffers (250 rows x 1024 frequency bins).
    /// </summary>
    public float[]? SpectrogramMax { get; init; }
    public float[]? SpectrogramAvg { get; init; }
    public float[]? SpectrogramMin { get; init; }
    public int[]? SpectrogramRowCount { get; init; }
}
