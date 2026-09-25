using System;

namespace MusicScope.Core;

/// <summary>
/// Instantaneous snapshot of all active charts, meters, and counters during live audio playback/decoding.
/// Directly powers the real-time visual updates in MusicScope.
/// </summary>
public record AudioRealtimeSnapshot
{
    public double ProgressFraction { get; init; }

    // Loudness
    public double MomentaryLufs { get; init; } = -70.0;
    public double ShortTermLufs { get; init; } = -70.0;
    public double RunningIntegratedLufs { get; init; } = -70.0;
    public double RunningLra { get; init; } = 0.0;

    // Peak & RMS (instantaneous block values for bouncy LED meters)
    public double CurrentPeakLeftDb { get; init; } = -100.0;
    public double CurrentPeakRightDb { get; init; } = -100.0;
    public double CurrentRmsLeftDb { get; init; } = -100.0;
    public double CurrentRmsRightDb { get; init; } = -100.0;

    // True Peak hold
    public double MaxTruePeakLeftDb { get; init; } = -100.0;
    public double MaxTruePeakRightDb { get; init; } = -100.0;

    // Running CREST Factor Avg
    public double RunningCrestDb { get; init; } = 0.0;

    // Stereo
    public double Correlation { get; init; } = 1.0;
    public double MidLevelDb { get; init; } = -100.0;
    public double SideLevelDb { get; init; } = -100.0;

    // Spectrum curves
    public double[]? InstantSpectrumDb { get; init; }
    public double[]? CumulativePeakSpectrumDb { get; init; }

    // Goniometer vector points for phosphor scope
    public float[]? GoniometerPointsX { get; init; }
    public float[]? GoniometerPointsY { get; init; }
}
