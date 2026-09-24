namespace MusicScope.Core.Levels;

/// <summary>
/// Encapsulates sample peak, true peak, RMS, and dynamic range measurements.
/// </summary>
public record LevelsResult
{
    /// <summary>
    /// Sample Peak in dBFS (left channel).
    /// </summary>
    public double SamplePeakLeftDb { get; init; } = -100.0;

    /// <summary>
    /// Sample Peak in dBFS (right channel).
    /// </summary>
    public double SamplePeakRightDb { get; init; } = -100.0;

    /// <summary>
    /// True Peak in dBTP (left channel, 4x oversampled).
    /// </summary>
    public double TruePeakLeftDb { get; init; } = -100.0;

    /// <summary>
    /// True Peak in dBTP (right channel, 4x oversampled).
    /// </summary>
    public double TruePeakRightDb { get; init; } = -100.0;

    /// <summary>
    /// Maximum True Peak across all channels in dBTP.
    /// </summary>
    public double MaxTruePeakDb => Math.Max(TruePeakLeftDb, TruePeakRightDb);

    /// <summary>
    /// RMS Level in dBFS (left channel).
    /// </summary>
    public double RmsLeftDb { get; init; } = -100.0;

    /// <summary>
    /// RMS Level in dBFS (right channel).
    /// </summary>
    public double RmsRightDb { get; init; } = -100.0;

    /// <summary>
    /// CREST Factor (Peak-to-RMS ratio) in dB.
    /// </summary>
    public double CrestFactorDb { get; init; } = 0.0;

    /// <summary>
    /// Dynamic Range (DR) in dB (XiVero / Pleasurize Music Foundation algorithm).
    /// </summary>
    public double DynamicRangeDb { get; init; } = 0.0;
}
