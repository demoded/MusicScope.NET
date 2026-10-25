using System;
using System.Buffers.Binary;
using System.IO;
using System.Threading.Tasks;
using MusicScope.Audio;
using MusicScope.Audio.Dsd;
using MusicScope.Core.DSP;
using Xunit;

namespace MusicScope.Core.Tests;

public class DsdDecimationTests
{
    [Fact]
    public void FilterCoefficients_HaveExpectedLengthsAndUnityGain()
    {
        Assert.Equal(184, DsdDecimationFilter.Filter0.Length);
        Assert.Equal(144, DsdDecimationFilter.Filter1.Length);
        Assert.Equal(152, DsdDecimationFilter.Filter2.Length);
        Assert.Equal(95, DsdDecimationFilter.Filter3.Length);
        Assert.Equal(49, DsdDecimationFilter.Filter4.Length);
        Assert.Equal(41, DsdDecimationFilter.Filter5.Length);
        Assert.Equal(2, DsdDecimationFilter.Filter6.Length);
        Assert.Equal(168, DsdDecimationFilter.Filter7.Length);
        Assert.Equal(45, DsdDecimationFilter.Filter8.Length);

        double sum0 = 0.0, sum1 = 0.0, sum2 = 0.0, sum3 = 0.0, sum4 = 0.0, sum5 = 0.0;
        foreach (var c in DsdDecimationFilter.Filter0) sum0 += c;
        foreach (var c in DsdDecimationFilter.Filter1) sum1 += c;
        foreach (var c in DsdDecimationFilter.Filter2) sum2 += c;
        foreach (var c in DsdDecimationFilter.Filter3) sum3 += c;
        foreach (var c in DsdDecimationFilter.Filter4) sum4 += c;
        foreach (var c in DsdDecimationFilter.Filter5) sum5 += c;

        Assert.InRange(sum0, 0.99, 1.01);
        Assert.InRange(sum1, 0.99, 1.01);
        Assert.InRange(sum2, 0.99, 1.01);
        Assert.InRange(sum3, 0.99, 1.01);
        Assert.InRange(sum4, 0.99, 1.01);
        Assert.InRange(sum5, 0.99, 1.01);
    }

    [Fact]
    public void Dsd64_Decimator_SilenceInput_YieldsZeroDc()
    {
        var decimator = new DsdChannelDecimator(2822400, DsdBitOrder.LsbFirst);
        Assert.Equal(176400, decimator.OutputSampleRate);
        Assert.Equal(16, decimator.DecimationRatio);

        // 0x55 is alternating 01010101 (zero signal DC in DSD)
        byte[] input = new byte[1024];
        Array.Fill(input, (byte)0x55);

        float[] output = new float[input.Length];
        int samplesWritten = decimator.Process(input, output);

        Assert.Equal(input.Length / 2, samplesWritten); // 16:1 decimation = 2 input bytes per 1 output sample

        // Once the 201-byte ring buffers wrap and settle, output should be zero (< 1e-6)
        for (int i = 250; i < samplesWritten; i++)
        {
            Assert.True(Math.Abs(output[i]) < 1e-6f, $"Sample {i} was {output[i]}, expected ~0.0");
        }
    }

    [Fact]
    public void DsdRates_HaveCorrectOutputRatesAndRatios()
    {
        var dsd64 = new DsdChannelDecimator(2822400, DsdBitOrder.LsbFirst);
        Assert.Equal(176400, dsd64.OutputSampleRate);
        Assert.Equal(16, dsd64.DecimationRatio);

        var dsd128 = new DsdChannelDecimator(5644800, DsdBitOrder.LsbFirst);
        Assert.Equal(352800, dsd128.OutputSampleRate);
        Assert.Equal(16, dsd128.DecimationRatio);

        var dsd256 = new DsdChannelDecimator(11289600, DsdBitOrder.LsbFirst);
        Assert.Equal(352800, dsd256.OutputSampleRate);
        Assert.Equal(32, dsd256.DecimationRatio);

        var dsd512 = new DsdChannelDecimator(22579200, DsdBitOrder.LsbFirst);
        Assert.Equal(352800, dsd512.OutputSampleRate);
        Assert.Equal(64, dsd512.DecimationRatio);
    }

    [Fact]
    public async Task SyntheticDsfFile_ProbesAndDecodesSuccessfully()
    {
        string tempFile = Path.Combine(Path.GetTempPath(), $"musicscope_test_{Guid.NewGuid():N}.dsf");
        try
        {
            const int blockSize = 4096;
            const int channelCount = 2;
            const int dsdSampleRate = 2822400;
            const ulong sampleCount = blockSize * 8 * 2; // 2 blocks per channel = 65,536 samples per channel

            using (var fs = new FileStream(tempFile, FileMode.Create, FileAccess.Write))
            {
                // 1. 'DSD ' chunk (28 bytes)
                byte[] dsdChunk = new byte[28];
                dsdChunk[0] = (byte)'D'; dsdChunk[1] = (byte)'S'; dsdChunk[2] = (byte)'D'; dsdChunk[3] = (byte)' ';
                BinaryPrimitives.WriteUInt64LittleEndian(dsdChunk.AsSpan(4, 8), 28);
                // total file size will be 28 + 52 + 12 + dataBytes = 92 + (blockSize * 2 * 2) = 16476
                ulong totalFileSize = 28 + 52 + 12 + (blockSize * channelCount * 2);
                BinaryPrimitives.WriteUInt64LittleEndian(dsdChunk.AsSpan(12, 8), totalFileSize);
                BinaryPrimitives.WriteUInt64LittleEndian(dsdChunk.AsSpan(20, 8), 0); // metadata offset
                fs.Write(dsdChunk);

                // 2. 'fmt ' chunk (52 bytes)
                byte[] fmtChunk = new byte[52];
                fmtChunk[0] = (byte)'f'; fmtChunk[1] = (byte)'m'; fmtChunk[2] = (byte)'t'; fmtChunk[3] = (byte)' ';
                BinaryPrimitives.WriteUInt64LittleEndian(fmtChunk.AsSpan(4, 8), 52);
                BinaryPrimitives.WriteInt32LittleEndian(fmtChunk.AsSpan(12, 4), 1); // version
                BinaryPrimitives.WriteInt32LittleEndian(fmtChunk.AsSpan(16, 4), 0); // format ID (raw)
                BinaryPrimitives.WriteInt32LittleEndian(fmtChunk.AsSpan(20, 4), 2); // channel type (stereo)
                BinaryPrimitives.WriteInt32LittleEndian(fmtChunk.AsSpan(24, 4), channelCount);
                BinaryPrimitives.WriteInt32LittleEndian(fmtChunk.AsSpan(28, 4), dsdSampleRate);
                BinaryPrimitives.WriteInt32LittleEndian(fmtChunk.AsSpan(32, 4), 1); // 1-bit
                BinaryPrimitives.WriteUInt64LittleEndian(fmtChunk.AsSpan(36, 8), sampleCount);
                BinaryPrimitives.WriteInt32LittleEndian(fmtChunk.AsSpan(44, 4), blockSize);
                BinaryPrimitives.WriteInt32LittleEndian(fmtChunk.AsSpan(48, 4), 0); // reserved
                fs.Write(fmtChunk);

                // 3. 'data' chunk (12 bytes header + data)
                ulong audioDataBytes = blockSize * channelCount * 2;
                byte[] dataHeader = new byte[12];
                dataHeader[0] = (byte)'d'; dataHeader[1] = (byte)'a'; dataHeader[2] = (byte)'t'; dataHeader[3] = (byte)'a';
                BinaryPrimitives.WriteUInt64LittleEndian(dataHeader.AsSpan(4, 8), audioDataBytes + 12);
                fs.Write(dataHeader);

                // Write 2 blocks of silence (0x55)
                byte[] blockData = new byte[blockSize * channelCount];
                Array.Fill(blockData, (byte)0x55);
                fs.Write(blockData);
                fs.Write(blockData);
            }

            var decoder = new UniversalAudioDecoder();
            var info = await decoder.ProbeAsync(tempFile);

            Assert.Equal("DSF", info.FormatName);
            Assert.True(info.IsDsd);
            Assert.Equal(1, info.BitDepth);
            Assert.Equal(2, info.ChannelCount);
            Assert.Equal(2822400, info.ContainerSampleRate);
            Assert.Equal(176400, info.SampleRate);
            Assert.True(info.Duration.TotalSeconds > 0);

            long totalDecodedSamples = 0;
            double finalProgress = 0.0;

            await decoder.DecodeAsync(tempFile, (chunk, channels, rate, progress) =>
            {
                totalDecodedSamples += chunk.Length;
                finalProgress = progress;
                Assert.Equal(2, channels);
                Assert.Equal(176400, rate);
                return Task.CompletedTask;
            });

            Assert.True(totalDecodedSamples > 0);
            Assert.Equal(1.0, finalProgress, tolerance: 0.01);
        }
        finally
        {
            if (File.Exists(tempFile)) File.Delete(tempFile);
        }
    }

    [Fact]
    public async Task SyntheticDffFile_ProbesAndDecodesSuccessfully()
    {
        string tempFile = Path.Combine(Path.GetTempPath(), $"musicscope_test_{Guid.NewGuid():N}.dff");
        try
        {
            const int channelCount = 2;
            const int dsdSampleRate = 2822400;
            const int audioDataBytes = 16384; // 8192 bytes per channel

            using (var fs = new FileStream(tempFile, FileMode.Create, FileAccess.Write))
            {
                // FRM8 Header (12 bytes)
                // FRM8 (4) + size (8) + DSD (4) + subchunks...
                // PROP: 4 ID + 8 len + 4 SND + (FS: 4 ID + 8 len + 4 data) + (CHNL: 4 ID + 8 len + 2 data + 8 IDs)
                // DSD : 4 ID + 8 len + audioDataBytes
                byte[] frm8Header = new byte[16];
                frm8Header[0] = (byte)'F'; frm8Header[1] = (byte)'R'; frm8Header[2] = (byte)'M'; frm8Header[3] = (byte)'8';
                BinaryPrimitives.WriteUInt64BigEndian(frm8Header.AsSpan(4, 8), 50000); // will be large enough
                frm8Header[12] = (byte)'D'; frm8Header[13] = (byte)'S'; frm8Header[14] = (byte)'D'; frm8Header[15] = (byte)' ';
                fs.Write(frm8Header);

                // PROP chunk
                // SND (4) + FS (16) + CHNL (22) = 42 bytes
                byte[] propHeader = new byte[12];
                propHeader[0] = (byte)'P'; propHeader[1] = (byte)'R'; propHeader[2] = (byte)'O'; propHeader[3] = (byte)'P';
                BinaryPrimitives.WriteUInt64BigEndian(propHeader.AsSpan(4, 8), 4 + 16 + 22);
                fs.Write(propHeader);
                fs.Write([(byte)'S', (byte)'N', (byte)'D', (byte)' ']);

                // FS  subchunk: 4 ID + 8 len + 4 rate = 16 bytes
                byte[] fsChunk = new byte[16];
                fsChunk[0] = (byte)'F'; fsChunk[1] = (byte)'S'; fsChunk[2] = (byte)' '; fsChunk[3] = (byte)' ';
                BinaryPrimitives.WriteUInt64BigEndian(fsChunk.AsSpan(4, 8), 4);
                BinaryPrimitives.WriteInt32BigEndian(fsChunk.AsSpan(12, 4), dsdSampleRate);
                fs.Write(fsChunk);

                // CHNL subchunk: 4 ID + 8 len + 2 channels + 8 IDs = 22 bytes
                byte[] chnlChunk = new byte[22];
                chnlChunk[0] = (byte)'C'; chnlChunk[1] = (byte)'H'; chnlChunk[2] = (byte)'N'; chnlChunk[3] = (byte)'L';
                BinaryPrimitives.WriteUInt64BigEndian(chnlChunk.AsSpan(4, 8), 10);
                BinaryPrimitives.WriteUInt16BigEndian(chnlChunk.AsSpan(12, 2), channelCount);
                chnlChunk[14] = (byte)'S'; chnlChunk[15] = (byte)'L'; chnlChunk[16] = (byte)'F'; chnlChunk[17] = (byte)'T';
                chnlChunk[18] = (byte)'S'; chnlChunk[19] = (byte)'R'; chnlChunk[20] = (byte)'G'; chnlChunk[21] = (byte)'T';
                fs.Write(chnlChunk);

                // DSD  data chunk: 4 ID + 8 len + audioDataBytes
                byte[] dsdDataHeader = new byte[12];
                dsdDataHeader[0] = (byte)'D'; dsdDataHeader[1] = (byte)'S'; dsdDataHeader[2] = (byte)'D'; dsdDataHeader[3] = (byte)' ';
                BinaryPrimitives.WriteUInt64BigEndian(dsdDataHeader.AsSpan(4, 8), (ulong)audioDataBytes);
                fs.Write(dsdDataHeader);

                byte[] audio = new byte[audioDataBytes];
                Array.Fill(audio, (byte)0x55);
                fs.Write(audio);
            }

            var decoder = new UniversalAudioDecoder();
            var info = await decoder.ProbeAsync(tempFile);

            Assert.Equal("DFF", info.FormatName);
            Assert.True(info.IsDsd);
            Assert.Equal(1, info.BitDepth);
            Assert.Equal(2, info.ChannelCount);
            Assert.Equal(2822400, info.ContainerSampleRate);
            Assert.Equal(176400, info.SampleRate);

            long totalDecoded = 0;
            await decoder.DecodeAsync(tempFile, (chunk, channels, rate, progress) =>
            {
                totalDecoded += chunk.Length;
                Assert.Equal(2, channels);
                Assert.Equal(176400, rate);
                return Task.CompletedTask;
            });

            Assert.True(totalDecoded > 0);
        }
        finally
        {
            if (File.Exists(tempFile)) File.Delete(tempFile);
        }
    }

    [Fact]
    public void DsdGainFactor_MatchesScarletBookSpecification()
    {
        // Scarlet Book specifies +6 dB boost to align 0 dB SACD with 0 dBFS PCM.
        // XiVero implements this as Math.Pow(10.0, 0.3) in rHAjVyBgPhqkQKsOvJMPMYn.java line 25.
        // 20 * log10(10^0.3) = 20 * 0.3 = 6.0 dB exactly.
        double expectedGain = Math.Pow(10.0, 0.3);
        Assert.Equal(DsdDecimationFilter.DsdGainFactor, expectedGain, tolerance: 1e-12);

        double dbBoost = 20.0 * Math.Log10(DsdDecimationFilter.DsdGainFactor);
        Assert.Equal(6.0, dbBoost, tolerance: 1e-9);
    }

    [Fact]
    public void DsdDecimator_AppliesScarletBookGainToDecimatedOutput()
    {
        var decimator = new DsdChannelDecimator(2822400, DsdBitOrder.LsbFirst);

        // Constant +1.0 signal in 1-bit DSD (0xFF = all 1 bits)
        byte[] input = new byte[1024];
        Array.Fill(input, (byte)0xFF);

        float[] output = new float[input.Length];
        int samplesWritten = decimator.Process(input, output);

        // Steady state output should be scaled by DsdGainFactor (~2.0037 rather than ~1.004)
        for (int i = 250; i < samplesWritten; i++)
        {
            Assert.True(output[i] > 1.95f && output[i] < 2.05f,
                $"Sample {i} was {output[i]}, expected ~2.0037 (scaled by DsdGainFactor).");
        }
    }
}
