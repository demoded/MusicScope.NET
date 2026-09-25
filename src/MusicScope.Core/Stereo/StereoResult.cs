namespace MusicScope.Core.Stereo;

/// <summary>
/// Encapsulates stereo phase correlation, balance, and Mid/Side energy metrics.
/// </summary>
public record StereoResult
{
    /// <summary>
    /// Phase correlation coefficient in range [-1.0, +1.0].
    /// +1.0 = Pure mono (in-phase).
    ///  0.0 = Uncorrelated stereo.
    /// -1.0 = Out-of-phase (180 deg phase inverted).
    /// </summary>
    public double Correlation { get; init; } = 1.0;

    /// <summary>
    /// Stereo balance in range [-1.0, +1.0] (-1.0 = Left, 0.0 = Center, +1.0 = Right).
    /// </summary>
    public double Balance { get; init; } = 0.0;

    /// <summary>
    /// Mid channel RMS level in dBFS.
    /// </summary>
    public double MidLevelDb { get; init; } = -100.0;

    /// <summary>
    /// Side channel RMS level in dBFS.
    /// </summary>
    public double SideLevelDb { get; init; } = -100.0;

    /// <summary>
    /// Cumulative 2D stereo density cloud histogram (256x256 normalized intensity bytes).
    /// </summary>
    public byte[]? DensityCloud { get; init; }

    /// <summary>
    /// Mid/Side ratio in dB (Mid - Side). Positive indicates mono-dominant, negative indicates wide stereo.
    /// </summary>
    public double MidSideRatioDb => MidLevelDb - SideLevelDb;
}
