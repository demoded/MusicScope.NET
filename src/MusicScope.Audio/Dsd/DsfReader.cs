using System;
using System.Buffers.Binary;
using System.IO;
using System.Threading;
using System.Threading.Tasks;
using MusicScope.Core.DSP;

namespace MusicScope.Audio.Dsd;

/// <summary>
/// Native high-performance DSF (Sony DSD Stream File) container reader and streaming decoder.
/// Decodes 1-bit DSD audio directly to float PCM using XiVero's exact multi-stage FIR decimation filters.
/// </summary>
public static class DsfReader
{
    public static bool IsDsfFile(string filePath)
    {
        try
        {
            using var fs = new FileStream(filePath, FileMode.Open, FileAccess.Read, FileShare.Read);
            Span<byte> magic = stackalloc byte[4];
            if (fs.Read(magic) < 4) return false;
            return magic[0] == 'D' && magic[1] == 'S' && magic[2] == 'D' && magic[3] == ' ';
        }
        catch
        {
            return false;
        }
    }

    public static AudioFileInfo Probe(string filePath)
    {
        using var fs = new FileStream(filePath, FileMode.Open, FileAccess.Read, FileShare.Read);
        Span<byte> dsdChunk = stackalloc byte[28];
        if (fs.Read(dsdChunk) < 28)
            throw new InvalidDataException("Invalid DSF file: header too short.");

        if (dsdChunk[0] != 'D' || dsdChunk[1] != 'S' || dsdChunk[2] != 'D' || dsdChunk[3] != ' ')
            throw new InvalidDataException("Invalid DSF file: magic mismatch.");

        Span<byte> fmtChunkHeader = stackalloc byte[12];
        if (fs.Read(fmtChunkHeader) < 12)
            throw new InvalidDataException("Invalid DSF file: missing fmt chunk.");

        if (fmtChunkHeader[0] != 'f' || fmtChunkHeader[1] != 'm' || fmtChunkHeader[2] != 't' || fmtChunkHeader[3] != ' ')
            throw new InvalidDataException("Invalid DSF file: expected 'fmt ' chunk.");

        ulong fmtSize = BinaryPrimitives.ReadUInt64LittleEndian(fmtChunkHeader.Slice(4, 8));
        int fmtPayloadSize = (int)(fmtSize - 12);
        if (fmtPayloadSize < 40)
            throw new InvalidDataException("Invalid DSF file: fmt chunk payload too small.");

        byte[] fmtData = new byte[fmtPayloadSize];
        if (fs.Read(fmtData) < fmtPayloadSize)
            throw new InvalidDataException("Invalid DSF file: truncated fmt payload.");

        int channelCount = BinaryPrimitives.ReadInt32LittleEndian(fmtData.AsSpan(12, 4));
        int dsdSampleRate = BinaryPrimitives.ReadInt32LittleEndian(fmtData.AsSpan(16, 4));
        ulong sampleCount = BinaryPrimitives.ReadUInt64LittleEndian(fmtData.AsSpan(24, 8));

        double durationSeconds = dsdSampleRate > 0 ? (double)sampleCount / dsdSampleRate : 0.0;
        int pcmSampleRate = DsdDecimationFilter.GetOutputSampleRate(dsdSampleRate);

        return new AudioFileInfo
        {
            FilePath = filePath,
            FormatName = "DSF",
            SampleRate = pcmSampleRate,
            ContainerSampleRate = dsdSampleRate,
            ChannelCount = channelCount,
            BitDepth = 1,
            IsDsd = true,
            Duration = TimeSpan.FromSeconds(durationSeconds),
            FileSizeBytes = new FileInfo(filePath).Length
        };
    }

    public static async Task DecodeAsync(
        string filePath,
        AudioChunkDecodedHandler onChunkDecoded,
        CancellationToken ct = default)
    {
        await using var fs = new FileStream(filePath, FileMode.Open, FileAccess.Read, FileShare.Read, 65536, useAsync: true);

        // 1. Read 'DSD ' chunk (28 bytes)
        byte[] dsdHeader = new byte[28];
        if (await fs.ReadAsync(dsdHeader.AsMemory(0, 28), ct) < 28)
            throw new InvalidDataException("Truncated DSF header.");

        // 2. Read 'fmt ' chunk
        byte[] fmtHeader = new byte[12];
        if (await fs.ReadAsync(fmtHeader.AsMemory(0, 12), ct) < 12)
            throw new InvalidDataException("Truncated fmt chunk.");

        ulong fmtSize = BinaryPrimitives.ReadUInt64LittleEndian(fmtHeader.AsSpan(4, 8));
        int fmtPayloadSize = (int)(fmtSize - 12);
        byte[] fmtData = new byte[fmtPayloadSize];
        if (await fs.ReadAsync(fmtData.AsMemory(0, fmtPayloadSize), ct) < fmtPayloadSize)
            throw new InvalidDataException("Truncated fmt payload.");

        int channelCount = BinaryPrimitives.ReadInt32LittleEndian(fmtData.AsSpan(12, 4));
        int dsdSampleRate = BinaryPrimitives.ReadInt32LittleEndian(fmtData.AsSpan(16, 4));
        ulong sampleCount = BinaryPrimitives.ReadUInt64LittleEndian(fmtData.AsSpan(24, 8));
        int blockSize = BinaryPrimitives.ReadInt32LittleEndian(fmtData.AsSpan(32, 4));
        if (blockSize <= 0) blockSize = 4096;

        // 3. Read 'data' chunk header
        byte[] dataHeader = new byte[12];
        if (await fs.ReadAsync(dataHeader.AsMemory(0, 12), ct) < 12)
            throw new InvalidDataException("Truncated data chunk header.");

        if (dataHeader[0] != 'd' || dataHeader[1] != 'a' || dataHeader[2] != 't' || dataHeader[3] != 'a')
            throw new InvalidDataException("Expected 'data' chunk in DSF file.");

        ulong dataChunkSize = BinaryPrimitives.ReadUInt64LittleEndian(dataHeader.AsSpan(4, 8));
        long audioDataBytes = (long)(dataChunkSize - 12);

        int pcmSampleRate = DsdDecimationFilter.GetOutputSampleRate(dsdSampleRate);

        // Initialize per-channel decimators (DSF uses LsbFirst bit order)
        var decimators = new DsdChannelDecimator[channelCount];
        for (int ch = 0; ch < channelCount; ch++)
        {
            decimators[ch] = new DsdChannelDecimator(dsdSampleRate, DsdBitOrder.LsbFirst);
        }

        // Buffer for reading 1 multi-channel block (blockSize bytes per channel)
        int blockBytesTotal = blockSize * channelCount;
        byte[] blockBuffer = new byte[blockBytesTotal];

        // Per-channel and interleaved output float buffers
        int maxSamplesPerChannel = blockSize + 64;
        float[][] channelOutputs = new float[channelCount][];
        for (int ch = 0; ch < channelCount; ch++)
            channelOutputs[ch] = new float[maxSamplesPerChannel];

        float[] interleavedOutput = new float[maxSamplesPerChannel * channelCount];

        long totalBytesRead = 0;
        long totalChannelBytes = (long)((sampleCount + 7) / 8);
        long targetAudioBytes = Math.Min(audioDataBytes, totalChannelBytes * channelCount);

        while (totalBytesRead < targetAudioBytes)
        {
            ct.ThrowIfCancellationRequested();

            int toRead = (int)Math.Min((long)blockBytesTotal, targetAudioBytes - totalBytesRead);
            int bytesRead = await fs.ReadAsync(blockBuffer.AsMemory(0, toRead), ct);
            if (bytesRead <= 0)
                break;

            totalBytesRead += bytesRead;

            // In DSF: each block contains 'blockSize' bytes for channel 0, then 'blockSize' bytes for channel 1, etc.
            int bytesPerChannelInBlock = bytesRead / channelCount;
            if (bytesPerChannelInBlock <= 0)
                continue;

            int samplesWritten = 0;
            for (int ch = 0; ch < channelCount; ch++)
            {
                int offset = ch * bytesPerChannelInBlock;
                ReadOnlySpan<byte> chSpan = blockBuffer.AsSpan(offset, bytesPerChannelInBlock);
                samplesWritten = decimators[ch].Process(chSpan, channelOutputs[ch].AsSpan());
            }

            if (samplesWritten > 0)
            {
                // Interleave channel samples
                for (int s = 0; s < samplesWritten; s++)
                {
                    int baseIdx = s * channelCount;
                    for (int ch = 0; ch < channelCount; ch++)
                    {
                        interleavedOutput[baseIdx + ch] = channelOutputs[ch][s];
                    }
                }

                double progress = targetAudioBytes > 0
                    ? Math.Min(1.0, (double)totalBytesRead / targetAudioBytes)
                    : 0.0;

                await onChunkDecoded(
                    interleavedOutput.AsMemory(0, samplesWritten * channelCount),
                    channelCount,
                    pcmSampleRate,
                    progress);
            }
        }
    }
}
