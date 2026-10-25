using System;

namespace MusicScope.Core.DSP;

/// <summary>
/// Stateful per-channel DSD to PCM decimator replicating XiVero's FIR decimation pipeline.
/// </summary>
public sealed class DsdChannelDecimator
{
    private readonly int _dsdSampleRate;
    private readonly int _bitOrderIdx;
    private readonly double[,][] _stage1Lut;
    private readonly int _stage1NumBlocks;
    private readonly double[] _stage2Filter;
    private readonly int _stage2Decimation;
    private readonly double[]? _stage3Filter;
    private readonly int _stage3Decimation;

    private readonly byte[] _byteRing = new byte[201];
    private int _byteRingPos;

    private readonly double[] _stage2Ring = new double[201];
    private int _stage2RingPos;
    private int _stage2DecimCount;

    private readonly double[]? _stage3Ring;
    private int _stage3RingPos;
    private int _stage3DecimCount;

    public int OutputSampleRate => DsdDecimationFilter.GetOutputSampleRate(_dsdSampleRate);
    public int DecimationRatio => DsdDecimationFilter.GetDecimationRatio(_dsdSampleRate);

    public DsdChannelDecimator(int dsdSampleRate, DsdBitOrder bitOrder)
    {
        _dsdSampleRate = dsdSampleRate;
        _bitOrderIdx = (int)bitOrder;
        Array.Fill(_byteRing, (byte)0xAA);

        if (dsdSampleRate <= 2822400) // DSD64: 2.8224 MHz -> 176.4 kHz (16:1)
        {
            _stage1Lut = DsdDecimationFilter.Stage1Tables[0];
            _stage1NumBlocks = DsdDecimationFilter.Filter0.Length / 8;
            _stage2Filter = DsdDecimationFilter.Filter5;
            _stage2Decimation = 2;
        }
        else if (dsdSampleRate <= 5644800) // DSD128: 5.6448 MHz -> 352.8 kHz (16:1)
        {
            _stage1Lut = DsdDecimationFilter.Stage1Tables[1];
            _stage1NumBlocks = DsdDecimationFilter.Filter1.Length / 8;
            _stage2Filter = DsdDecimationFilter.Filter4;
            _stage2Decimation = 2;
        }
        else if (dsdSampleRate <= 11289600) // DSD256: 11.2896 MHz -> 352.8 kHz (32:1)
        {
            _stage1Lut = DsdDecimationFilter.Stage1Tables[2];
            _stage1NumBlocks = DsdDecimationFilter.Filter2.Length / 8;
            _stage2Filter = DsdDecimationFilter.Filter3;
            _stage2Decimation = 4;
        }
        else // DSD512: 22.5792 MHz -> 352.8 kHz (64:1)
        {
            _stage1Lut = DsdDecimationFilter.Stage1Tables[3];
            _stage1NumBlocks = DsdDecimationFilter.Filter7.Length / 8;
            _stage2Filter = DsdDecimationFilter.Filter8;
            _stage2Decimation = 2;
            _stage3Filter = DsdDecimationFilter.Filter3;
            _stage3Decimation = 4;
            _stage3Ring = new double[201];
        }
    }

    /// <summary>
    /// Resets filter history buffers.
    /// </summary>
    public void Reset()
    {
        Array.Fill(_byteRing, (byte)0xAA);
        _byteRingPos = 0;
        Array.Clear(_stage2Ring);
        _stage2RingPos = 0;
        _stage2DecimCount = 0;
        if (_stage3Ring != null)
        {
            Array.Clear(_stage3Ring);
            _stage3RingPos = 0;
            _stage3DecimCount = 0;
        }
    }

    /// <summary>
    /// Processes a buffer of 1-bit DSD bytes for this channel and writes decimated PCM float samples.
    /// </summary>
    public int Process(ReadOnlySpan<byte> dsdBytes, Span<float> pcmOutput)
    {
        int outIdx = 0;
        int bo = _bitOrderIdx;
        int numBlocks = _stage1NumBlocks;
        var lut = _stage1Lut;
        var s2Filter = _stage2Filter;
        int s2Decim = _stage2Decimation;
        var s3Filter = _stage3Filter;
        int s3Decim = _stage3Decimation;
        var s3Ring = _stage3Ring;

        for (int i = 0; i < dsdBytes.Length; i++)
        {
            // Stage 1: byte ring buffer write
            _byteRing[_byteRingPos] = dsdBytes[i];
            if (++_byteRingPos > 200) _byteRingPos = 0;

            // Stage 1: dot-product via precomputed 256-entry table lookup
            int r1 = _byteRingPos;
            double s1 = 0.0;
            for (int j = 0; j < numBlocks; j++)
            {
                s1 += lut[bo, j][_byteRing[r1]];
                if (++r1 > 200) r1 = 0;
            }

            // Stage 2: double ring buffer write
            _stage2Ring[_stage2RingPos] = s1;
            if (++_stage2RingPos > 200) _stage2RingPos = 0;

            if (_stage2DecimCount == 0)
            {
                int r2 = _stage2RingPos;
                double s2 = 0.0;
                for (int j = 0; j < s2Filter.Length; j++)
                {
                    s2 += s2Filter[j] * _stage2Ring[r2];
                    if (++r2 > 200) r2 = 0;
                }

                if (s3Filter != null && s3Ring != null)
                {
                    // Stage 3 (for DSD512)
                    s3Ring[_stage3RingPos] = s2;
                    if (++_stage3RingPos > 200) _stage3RingPos = 0;

                    if (_stage3DecimCount == 0)
                    {
                        int r3 = _stage3RingPos;
                        double s3 = 0.0;
                        for (int j = 0; j < s3Filter.Length; j++)
                        {
                            s3 += s3Filter[j] * s3Ring[r3];
                            if (++r3 > 200) r3 = 0;
                        }
                        if (outIdx < pcmOutput.Length)
                            pcmOutput[outIdx++] = (float)(s3 * DsdDecimationFilter.DsdGainFactor);
                    }
                    if (++_stage3DecimCount == s3Decim) _stage3DecimCount = 0;
                }
                else
                {
                    if (outIdx < pcmOutput.Length)
                        pcmOutput[outIdx++] = (float)(s2 * DsdDecimationFilter.DsdGainFactor);
                }
            }
            if (++_stage2DecimCount == s2Decim) _stage2DecimCount = 0;
        }

        return outIdx;
    }
}
