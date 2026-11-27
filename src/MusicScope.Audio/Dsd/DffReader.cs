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
        await using var fs = new FileStream(
            filePath,
            FileMode.Open,
            FileAccess.Read,
            FileShare.Read,
            bufferSize: 262144,
            FileOptions.Asynchronous | FileOptions.SequentialScan);
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

        // Read in chunks of 65536 frames (matching ~65536 PCM float chunks for stereo DSD64)
        const int framesPerChunk = 65536;
        int rawChunkBytes = framesPerChunk * channelCount;
        byte[] rawBuffer = System.Buffers.ArrayPool<byte>.Shared.Rent(rawChunkBytes);

        byte[][] channelBytes = new byte[channelCount][];
        float[][] channelOutputs = new float[channelCount][];
        for (int ch = 0; ch < channelCount; ch++)
        {
            channelBytes[ch] = new byte[framesPerChunk];
            channelOutputs[ch] = new float[framesPerChunk + 64];
        }

        int interleavedCap = (framesPerChunk + 64) * channelCount;
        float[] interleavedA = new float[interleavedCap];
        float[] interleavedB = new float[interleavedCap];
        bool useBufferA = true;
        Task pendingCallback = Task.CompletedTask;

        long totalBytesRead = 0;

        try
        {
            while (totalBytesRead < audioDataBytes)
            {
                ct.ThrowIfCancellationRequested();

                int toRead = (int)Math.Min((long)rawChunkBytes, audioDataBytes - totalBytesRead);
                int bytesRead = await fs.ReadAtLeastAsync(rawBuffer.AsMemory(0, toRead), toRead, throwOnEndOfStream: false, ct);
                if (bytesRead <= 0)
                    break;

                totalBytesRead += bytesRead;

                int framesInChunk = bytesRead / channelCount;
                if (framesInChunk <= 0)
                    continue;

                if (channelCount == 2)
                {
                    DeinterleaveStereoBytes(rawBuffer.AsSpan(0, framesInChunk * 2), channelBytes[0], channelBytes[1], framesInChunk);
                }
                else
                {
                    for (int f = 0; f < framesInChunk; f++)
                    {
                        int baseIdx = f * channelCount;
                        for (int ch = 0; ch < channelCount; ch++)
                        {
                            channelBytes[ch][f] = rawBuffer[baseIdx + ch];
                        }
                    }
                }

                int samplesWritten = 0;
                if (channelCount == 2)
                {
                    int frames = framesInChunk;
                    Task<int> ch1Task = Task.Run(() => decimators[1].Process(channelBytes[1].AsSpan(0, frames), channelOutputs[1].AsSpan()));
                    samplesWritten = decimators[0].Process(channelBytes[0].AsSpan(0, frames), channelOutputs[0].AsSpan());
                    await ch1Task;
                }
                else
                {
                    for (int ch = 0; ch < channelCount; ch++)
                    {
                        ReadOnlySpan<byte> chSpan = channelBytes[ch].AsSpan(0, framesInChunk);
                        samplesWritten = decimators[ch].Process(chSpan, channelOutputs[ch].AsSpan());
                    }
                }

                if (samplesWritten > 0)
                {
                    float[] currentOut = useBufferA ? interleavedA : interleavedB;
                    useBufferA = !useBufferA;

                    if (channelCount == 2)
                    {
                        InterleaveStereoFloats(channelOutputs[0], channelOutputs[1], currentOut, samplesWritten);
                    }
                    else
                    {
                        for (int s = 0; s < samplesWritten; s++)
                        {
                            int baseIdx = s * channelCount;
                            for (int ch = 0; ch < channelCount; ch++)
                            {
                                currentOut[baseIdx + ch] = channelOutputs[ch][s];
                            }
                        }
                    }

                    double progress = audioDataBytes > 0
                        ? Math.Min(1.0, (double)totalBytesRead / audioDataBytes)
                        : 0.0;

                    await pendingCallback;
                    int sampleCount = samplesWritten * channelCount;
                    pendingCallback = Task.Run(
                        () => onChunkDecoded(currentOut.AsMemory(0, sampleCount), channelCount, pcmSampleRate, progress),
                        ct);
                }
            }

            await pendingCallback;
        }
        finally
        {
            try { await pendingCallback; } catch { }
            System.Buffers.ArrayPool<byte>.Shared.Return(rawBuffer);
        }
    }

    private static void DeinterleaveStereoBytes(ReadOnlySpan<byte> interleaved, Span<byte> left, Span<byte> right, int frames)
    {
        int f = 0;
        int unrolledEnd = frames - (frames & 3);
        for (; f < unrolledEnd; f += 4)
        {
            int idx = f << 1;
            left[f] = interleaved[idx];
            right[f] = interleaved[idx + 1];
            left[f + 1] = interleaved[idx + 2];
            right[f + 1] = interleaved[idx + 3];
            left[f + 2] = interleaved[idx + 4];
            right[f + 2] = interleaved[idx + 5];
            left[f + 3] = interleaved[idx + 6];
            right[f + 3] = interleaved[idx + 7];
        }
        for (; f < frames; f++)
        {
            int idx = f << 1;
            left[f] = interleaved[idx];
            right[f] = interleaved[idx + 1];
        }
    }

    private static void InterleaveStereoFloats(ReadOnlySpan<float> left, ReadOnlySpan<float> right, Span<float> interleaved, int samples)
    {
        int s = 0;
        int unrolledEnd = samples - (samples & 3);
        for (; s < unrolledEnd; s += 4)
        {
            int idx = s << 1;
            interleaved[idx] = left[s];
            interleaved[idx + 1] = right[s];
            interleaved[idx + 2] = left[s + 1];
            interleaved[idx + 3] = right[s + 1];
            interleaved[idx + 4] = left[s + 2];
            interleaved[idx + 5] = right[s + 2];
            interleaved[idx + 6] = left[s + 3];
            interleaved[idx + 7] = right[s + 3];
        }
        for (; s < samples; s++)
        {
            int idx = s << 1;
            interleaved[idx] = left[s];
            interleaved[idx + 1] = right[s];
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
