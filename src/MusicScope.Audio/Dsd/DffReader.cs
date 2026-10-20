using System;
using System.Buffers.Binary;
using System.IO;
using System.Threading;
using System.Threading.Tasks;
using MusicScope.Core.DSP;

namespace MusicScope.Audio.Dsd;

/// <summary>
/// Native high-performance DSDIFF / DFF file reader and streaming decoder.
/// Decodes 1-bit DSD audio directly to float PCM using XiVero's exact multi-stage FIR decimation filters.
/// </summary>
public static class DffReader
{
    public static bool IsDffFile(string filePath)
    {
        try
        {
            using var fs = new FileStream(filePath, FileMode.Open, FileAccess.Read, FileShare.Read);
            Span<byte> magic = stackalloc byte[16];
            if (fs.Read(magic) < 16) return false;
            // FRM8 ... DSD 
            return magic[0] == 'F' && magic[1] == 'R' && magic[2] == 'M' && magic[3] == '8'
                && magic[12] == 'D' && magic[13] == 'S' && magic[14] == 'D' && magic[15] == ' ';
        }
        catch
        {
            return false;
        }
    }

    public static AudioFileInfo Probe(string filePath)
    {
        using var fs = new FileStream(filePath, FileMode.Open, FileAccess.Read, FileShare.Read);
        ParseHeader(fs, out int channelCount, out int dsdSampleRate, out long dataOffset, out long audioDataBytes);

        long samplesPerChannel = dsdSampleRate > 0 && channelCount > 0 ? (audioDataBytes * 8) / channelCount : 0;
        double durationSeconds = dsdSampleRate > 0 ? (double)samplesPerChannel / dsdSampleRate : 0.0;
        int pcmSampleRate = DsdDecimationFilter.GetOutputSampleRate(dsdSampleRate);

        return new AudioFileInfo
        {
            FilePath = filePath,
            FormatName = "DFF",
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
        ParseHeader(fs, out int channelCount, out int dsdSampleRate, out long dataOffset, out long audioDataBytes);

        if (channelCount <= 0 || dsdSampleRate <= 0 || audioDataBytes <= 0)
            throw new InvalidDataException("Invalid or unsupported DSDIFF file parameters.");

        fs.Position = dataOffset;
        int pcmSampleRate = DsdDecimationFilter.GetOutputSampleRate(dsdSampleRate);

        // Initialize per-channel decimators (DFF uses MsbFirst bit order)
        var decimators = new DsdChannelDecimator[channelCount];
        for (int ch = 0; ch < channelCount; ch++)
        {
            decimators[ch] = new DsdChannelDecimator(dsdSampleRate, DsdBitOrder.MsbFirst);
        }

        // Read in chunks of 4096 frames (4096 bytes per channel = 4096 * channelCount bytes)
        const int framesPerChunk = 4096;
        int rawChunkBytes = framesPerChunk * channelCount;
        byte[] rawBuffer = new byte[rawChunkBytes];

        byte[][] channelBytes = new byte[channelCount][];
        float[][] channelOutputs = new float[channelCount][];
        for (int ch = 0; ch < channelCount; ch++)
        {
            channelBytes[ch] = new byte[framesPerChunk];
            channelOutputs[ch] = new float[framesPerChunk + 64];
        }

        float[] interleavedOutput = new float[(framesPerChunk + 64) * channelCount];

        long totalBytesRead = 0;

        while (totalBytesRead < audioDataBytes)
        {
            ct.ThrowIfCancellationRequested();

            int toRead = (int)Math.Min((long)rawChunkBytes, audioDataBytes - totalBytesRead);
            int bytesRead = await fs.ReadAsync(rawBuffer.AsMemory(0, toRead), ct);
            if (bytesRead <= 0)
                break;

            totalBytesRead += bytesRead;

            int framesInChunk = bytesRead / channelCount;
            if (framesInChunk <= 0)
                continue;

            // In DFF: samples are interleaved byte-by-byte (ch0, ch1, ch0, ch1...)
            for (int f = 0; f < framesInChunk; f++)
            {
                int baseIdx = f * channelCount;
                for (int ch = 0; ch < channelCount; ch++)
                {
                    channelBytes[ch][f] = rawBuffer[baseIdx + ch];
                }
            }

            int samplesWritten = 0;
            for (int ch = 0; ch < channelCount; ch++)
            {
                ReadOnlySpan<byte> chSpan = channelBytes[ch].AsSpan(0, framesInChunk);
                samplesWritten = decimators[ch].Process(chSpan, channelOutputs[ch].AsSpan());
            }

            if (samplesWritten > 0)
            {
                for (int s = 0; s < samplesWritten; s++)
                {
                    int baseIdx = s * channelCount;
                    for (int ch = 0; ch < channelCount; ch++)
                    {
                        interleavedOutput[baseIdx + ch] = channelOutputs[ch][s];
                    }
                }

                double progress = audioDataBytes > 0
                    ? Math.Min(1.0, (double)totalBytesRead / audioDataBytes)
                    : 0.0;

                await onChunkDecoded(
                    interleavedOutput.AsMemory(0, samplesWritten * channelCount),
                    channelCount,
                    pcmSampleRate,
                    progress);
            }
        }
    }

    private static void ParseHeader(
        FileStream fs,
        out int channelCount,
        out int dsdSampleRate,
        out long dataOffset,
        out long audioDataBytes)
    {
        channelCount = 2;
        dsdSampleRate = 2822400;
        dataOffset = 0;
        audioDataBytes = 0;

        Span<byte> rootHeader = stackalloc byte[16];
        if (fs.Read(rootHeader) < 16)
            throw new InvalidDataException("Invalid DSDIFF file: file too short.");

        if (rootHeader[0] != 'F' || rootHeader[1] != 'R' || rootHeader[2] != 'M' || rootHeader[3] != '8'
            || rootHeader[12] != 'D' || rootHeader[13] != 'S' || rootHeader[14] != 'D' || rootHeader[15] != ' ')
        {
            throw new InvalidDataException("Invalid DSDIFF file magic.");
        }

        ulong frm8Size = BinaryPrimitives.ReadUInt64BigEndian(rootHeader.Slice(4, 8));
        long endOfFrm8 = 12 + (long)frm8Size;

        Span<byte> chunkHeader = stackalloc byte[12];
        Span<byte> propType = stackalloc byte[4];
        Span<byte> fsBytes = stackalloc byte[4];
        Span<byte> chBytes = stackalloc byte[2];

        while (fs.Position + 12 <= endOfFrm8 && fs.Position + 12 <= fs.Length)
        {
            if (fs.Read(chunkHeader) < 12)
                break;

            string chunkId = System.Text.Encoding.ASCII.GetString(chunkHeader.Slice(0, 4));
            ulong chunkSize = BinaryPrimitives.ReadUInt64BigEndian(chunkHeader.Slice(4, 8));
            long chunkDataPos = fs.Position;
            long nextChunkPos = chunkDataPos + (long)chunkSize + (long)(chunkSize & 1); // 2-byte aligned

            if (chunkId == "PROP")
            {
                if (fs.Read(propType) == 4 && propType[0] == 'S' && propType[1] == 'N' && propType[2] == 'D' && propType[3] == ' ')
                {
                    long propEnd = chunkDataPos + (long)chunkSize;
                    while (fs.Position + 12 <= propEnd)
                    {
                        if (fs.Read(chunkHeader) < 12) break;
                        string subId = System.Text.Encoding.ASCII.GetString(chunkHeader.Slice(0, 4));
                        ulong subSize = BinaryPrimitives.ReadUInt64BigEndian(chunkHeader.Slice(4, 8));
                        long subDataPos = fs.Position;
                        long nextSubPos = subDataPos + (long)subSize + (long)(subSize & 1);

                        if (subId == "FS  ")
                        {
                            if (fs.Read(fsBytes) == 4)
                                dsdSampleRate = BinaryPrimitives.ReadInt32BigEndian(fsBytes);
                        }
                        else if (subId == "CHNL")
                        {
                            if (fs.Read(chBytes) == 2)
                                channelCount = BinaryPrimitives.ReadUInt16BigEndian(chBytes);
                        }

                        fs.Position = nextSubPos;
                    }
                }
            }
            else if (chunkId == "DSD ")
            {
                dataOffset = chunkDataPos;
                audioDataBytes = (long)chunkSize;
                break;
            }

            fs.Position = nextChunkPos;
        }
    }
}
