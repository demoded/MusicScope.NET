using System;
using System.Threading;
using System.Threading.Tasks;

namespace MusicScope.Audio;

/// <summary>
/// Delegate called when a decoded interleaved float audio buffer is ready for analysis.
/// </summary>
public delegate Task AudioChunkDecodedHandler(
    ReadOnlyMemory<float> interleavedSamples,
    int channelCount,
    double sampleRate,
    double progressFraction);

/// <summary>
/// Cross-platform audio file decoder interface.
/// </summary>
public interface IAudioDecoder
{
    /// <summary>
    /// Extracts metadata and audio stream parameters for the given file.
    /// </summary>
    Task<AudioFileInfo> ProbeAsync(string filePath, CancellationToken ct = default);

    /// <summary>
    /// Decodes the audio file and streams interleaved 32-bit float samples to the callback.
    /// </summary>
    Task DecodeAsync(
        string filePath,
        AudioChunkDecodedHandler onChunkDecoded,
        CancellationToken ct = default);
}
