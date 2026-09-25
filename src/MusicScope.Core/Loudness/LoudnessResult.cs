namespace MusicScope.Core.Loudness;

/// <summary>
/// Encapsulates the results of EBU R128 / ITU-R BS.1770-4 loudness measurement.
/// </summary>
public record LoudnessResult
{
    /// <summary>
    /// Integrated Loudness in LUFS (LKFS).
    /// </summary>
    public double IntegratedLoudness { get; init; } = -70.0;

    /// <summary>
    /// Maximum Momentary Loudness (400 ms window) in LUFS.
    /// </summary>
    public double MomentaryMax { get; init; } = -70.0;

    /// <summary>
    /// Maximum Short-term Loudness (3 s window) in LUFS.
    /// </summary>
    public double ShortTermMax { get; init; } = -70.0;

    /// <summary>
    /// Loudness Range (LRA) in LU.
    /// </summary>
    public double LoudnessRange { get; init; } = 0.0;

    /// <summary>
    /// Lower boundary of the Loudness Range (10th percentile) in LUFS.
    /// </summary>
    public double LraLow { get; init; } = -70.0;

    /// <summary>
    /// Upper boundary of the Loudness Range (95th percentile) in LUFS.
    /// </summary>
    public double LraHigh { get; init; } = -70.0;

    /// <summary>
    /// Whether enough signal above the absolute threshold (-70 LUFS) was detected.
    /// </summary>
    public bool HasAudibleSignal => IntegratedLoudness > -70.0;

    /// <summary>
    /// S-Mode Loudness Histogram (751 bins from -70.0 dB to +5.0 dB in 0.1 dB steps).
    /// </summary>
    public int[]? SModeHistogram { get; init; }

    /// <summary>
    /// Peak count across any bin in the S-Mode histogram.
    /// </summary>
    public int SModeMaxCount { get; init; }
}
