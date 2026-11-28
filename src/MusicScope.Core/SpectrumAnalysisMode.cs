namespace MusicScope.Core;

/// <summary>
/// Selects how linear spectrum and spectrogram FFT windows are sampled from the audio stream.
/// </summary>
public enum SpectrumAnalysisMode
{
    /// <summary>
    /// Original XiVero MusicScope-compatible sampling: a 2,048-frame linear FFT on the first
    /// 2,048 frames of each 50 ms interval, alongside an 8,192-frame logarithmic FFT.
    /// </summary>
    Original = 0,

    /// <summary>
    /// Continuous overlapping 8,192-frame FFT updated every 50 ms (capped at 4,096 frames
    /// above 81.92 kHz for >= 50% overlap), covering every decoded frame across both linear
    /// and logarithmic displays with 4x finer frequency bin resolution.
    /// </summary>
    Overlapping = 1
}
