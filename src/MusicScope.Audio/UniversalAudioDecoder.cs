using System;
using System.IO;
using System.Threading;
using System.Threading.Tasks;
using MusicScope.Audio.Dsd;

namespace MusicScope.Audio;

/// <summary>
/// Universal audio decoder that routes DSD formats (.dsf, .dff) to the native XiVero DSD decimation pipeline,
/// and PCM/compressed formats (WAV, FLAC, AIFF, MP3, AAC, ALAC, etc.) to the FFmpeg streaming decoder.
/// </summary>
public sealed class UniversalAudioDecoder : IAudioDecoder
{
    private readonly FfmpegAudioDecoder _ffmpegDecoder;
    private readonly DsdAudioDecoder _dsdDecoder;

    public UniversalAudioDecoder(string? customFfmpegPath = null)
    {
        _ffmpegDecoder = new FfmpegAudioDecoder(customFfmpegPath);
        _dsdDecoder = new DsdAudioDecoder();
    }

    public static bool IsDsd(string filePath)
    {
        string ext = Path.GetExtension(filePath);
        if (ext.Equals(".dsf", StringComparison.OrdinalIgnoreCase) ||
            ext.Equals(".dff", StringComparison.OrdinalIgnoreCase))
        {
            return true;
        }

        return DsfReader.IsDsfFile(filePath) || DffReader.IsDffFile(filePath);
    }

    public Task<AudioFileInfo> ProbeAsync(string filePath, CancellationToken ct = default)
    {
        if (IsDsd(filePath))
        {
            return _dsdDecoder.ProbeAsync(filePath, ct);
        }

        return _ffmpegDecoder.ProbeAsync(filePath, ct);
    }

    public Task DecodeAsync(
        string filePath,
        AudioChunkDecodedHandler onChunkDecoded,
        CancellationToken ct = default)
    {
        if (IsDsd(filePath))
        {
            return _dsdDecoder.DecodeAsync(filePath, onChunkDecoded, ct);
        }

        return _ffmpegDecoder.DecodeAsync(filePath, onChunkDecoded, ct);
    }
}
