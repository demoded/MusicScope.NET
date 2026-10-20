using System;
using System.IO;
using System.Threading;
using System.Threading.Tasks;

namespace MusicScope.Audio.Dsd;

/// <summary>
/// Dedicated decoder for DSD audio files (.dsf and .dff) using XiVero's exact multi-stage FIR decimation.
/// </summary>
public sealed class DsdAudioDecoder : IAudioDecoder
{
    public Task<AudioFileInfo> ProbeAsync(string filePath, CancellationToken ct = default)
    {
        if (DsfReader.IsDsfFile(filePath) || Path.GetExtension(filePath).Equals(".dsf", StringComparison.OrdinalIgnoreCase))
        {
            return Task.FromResult(DsfReader.Probe(filePath));
        }

        if (DffReader.IsDffFile(filePath) || Path.GetExtension(filePath).Equals(".dff", StringComparison.OrdinalIgnoreCase))
        {
            return Task.FromResult(DffReader.Probe(filePath));
        }

        throw new NotSupportedException($"Unsupported DSD file format: {filePath}");
    }

    public Task DecodeAsync(
        string filePath,
        AudioChunkDecodedHandler onChunkDecoded,
        CancellationToken ct = default)
    {
        if (DsfReader.IsDsfFile(filePath) || Path.GetExtension(filePath).Equals(".dsf", StringComparison.OrdinalIgnoreCase))
        {
            return DsfReader.DecodeAsync(filePath, onChunkDecoded, ct);
        }

        if (DffReader.IsDffFile(filePath) || Path.GetExtension(filePath).Equals(".dff", StringComparison.OrdinalIgnoreCase))
        {
            return DffReader.DecodeAsync(filePath, onChunkDecoded, ct);
        }

        throw new NotSupportedException($"Unsupported DSD file format: {filePath}");
    }
}
