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
        await using var fs = new FileStream(
            filePath,
            FileMode.Open,
            FileAccess.Read,
            FileShare.Read,
            bufferSize: 262144,
            FileOptions.Asynchronous | FileOptions.SequentialScan);

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

        // Batch multiple DSF blocks per chunk (~65,536 bytes per channel) to amortize async I/O and parallelize channels
        int blocksPerChunk = Math.Max(1, 65536 / blockSize);
        int maxBytesPerChannel = blocksPerChunk * blockSize;
        int blockBytesTotal = blockSize * channelCount;
        int chunkBytesTotal = blocksPerChunk * blockBytesTotal;
        byte[] chunkBuffer = System.Buffers.ArrayPool<byte>.Shared.Rent(chunkBytesTotal);

        byte[][] channelBytes = new byte[channelCount][];
        float[][] channelOutputs = new float[channelCount][];
        int maxSamplesPerChannel = maxBytesPerChannel + 64;
        for (int ch = 0; ch < channelCount; ch++)
        {
            channelBytes[ch] = new byte[maxBytesPerChannel];
            channelOutputs[ch] = new float[maxSamplesPerChannel];
        }

        int interleavedCap = maxSamplesPerChannel * channelCount;
        float[] interleavedA = new float[interleavedCap];
        float[] interleavedB = new float[interleavedCap];
        bool useBufferA = true;
        Task pendingCallback = Task.CompletedTask;

        long totalBytesRead = 0;
        long totalChannelBytes = (long)((sampleCount + 7) / 8);
        long targetAudioBytes = Math.Min(audioDataBytes, totalChannelBytes * channelCount);

        try
        {
            while (totalBytesRead < targetAudioBytes)
            {
                ct.ThrowIfCancellationRequested();

                int toRead = (int)Math.Min((long)chunkBytesTotal, targetAudioBytes - totalBytesRead);
                int bytesRead = await fs.ReadAtLeastAsync(chunkBuffer.AsMemory(0, toRead), toRead, throwOnEndOfStream: false, ct);
                if (bytesRead <= 0)
                    break;

                totalBytesRead += bytesRead;

                // In DSF: each block contains 'blockSize' bytes for channel 0, then 'blockSize' bytes for channel 1, etc.
                int fullBlocks = bytesRead / blockBytesTotal;
                int bytesPerChannelInChunk = 0;
                if (fullBlocks > 0)
                {
                    if (fullBlocks == 1 && bytesRead == blockBytesTotal)
                    {
                        for (int ch = 0; ch < channelCount; ch++)
                        {
                            Buffer.BlockCopy(chunkBuffer, ch * blockSize, channelBytes[ch], 0, blockSize);
                        }
                        bytesPerChannelInChunk = blockSize;
                    }
                    else
                    {
                        for (int b = 0; b < fullBlocks; b++)
                        {
                            int srcBase = b * blockBytesTotal;
                            int dstBase = b * blockSize;
                            for (int ch = 0; ch < channelCount; ch++)
                            {
                                Buffer.BlockCopy(chunkBuffer, srcBase + ch * blockSize, channelBytes[ch], dstBase, blockSize);
                            }
                        }
                        bytesPerChannelInChunk = fullBlocks * blockSize;
                    }
                }

                int remBytes = bytesRead - fullBlocks * blockBytesTotal;
                int remPerCh = remBytes / channelCount;
                if (remPerCh > 0)
                {
                    int srcBase = fullBlocks * blockBytesTotal;
                    for (int ch = 0; ch < channelCount; ch++)
                    {
                        Buffer.BlockCopy(chunkBuffer, srcBase + ch * remPerCh, channelBytes[ch], bytesPerChannelInChunk, remPerCh);
                    }
                    bytesPerChannelInChunk += remPerCh;
                }

                if (bytesPerChannelInChunk <= 0)
                    continue;

                int samplesWritten = 0;
                if (channelCount == 2)
                {
                    int chLen = bytesPerChannelInChunk;
                    Task<int> ch1Task = Task.Run(() => decimators[1].Process(channelBytes[1].AsSpan(0, chLen), channelOutputs[1].AsSpan()));
                    samplesWritten = decimators[0].Process(channelBytes[0].AsSpan(0, chLen), channelOutputs[0].AsSpan());
                    await ch1Task;
                }
                else
                {
                    for (int ch = 0; ch < channelCount; ch++)
                    {
                        ReadOnlySpan<byte> chSpan = channelBytes[ch].AsSpan(0, bytesPerChannelInChunk);
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

                    double progress = targetAudioBytes > 0
                        ? Math.Min(1.0, (double)totalBytesRead / targetAudioBytes)
                        : 0.0;

                    await pendingCallback;
                    int sampleCountOut = samplesWritten * channelCount;
                    pendingCallback = Task.Run(
                        () => onChunkDecoded(currentOut.AsMemory(0, sampleCountOut), channelCount, pcmSampleRate, progress),
                        ct);
                }
            }

            await pendingCallback;
        }
        finally
        {
            try { await pendingCallback; } catch { }
            System.Buffers.ArrayPool<byte>.Shared.Return(chunkBuffer);
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
}
