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

    // Polar history dial (512 radial bins)
    public double[]? PeakHistory { get; init; }
    public double[]? LoudnessHistory { get; init; }

    // S-Mode Loudness Histogram (751 bins from -70.0 dB to +5.0 dB in 0.1 dB steps)
    public int[]? SModeHistogram { get; init; }
    public int SModeMaxCount { get; init; }
    public double SModeLraLow { get; init; } = -70.0;
    public double SModeLraHigh { get; init; } = -70.0;

    // 2D Spectrogram buffers (250 rows x 1024 frequency bins)
    public float[]? SpectrogramMax { get; init; }
    public float[]? SpectrogramAvg { get; init; }
    public float[]? SpectrogramMin { get; init; }
    public int[]? SpectrogramRowCount { get; init; }

    // Goniometer vector points for phosphor scope
    public float[]? GoniometerPointsX { get; init; }
    public float[]? GoniometerPointsY { get; init; }
}
