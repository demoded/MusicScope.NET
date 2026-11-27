using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Runtime.Intrinsics;

namespace MusicScope.Core.DSP;

/// <summary>
/// Stateful per-channel DSD to PCM decimator replicating XiVero's FIR decimation pipeline.
/// </summary>
public sealed class DsdChannelDecimator
{
    private const int RingSize = 201;

    private readonly int _dsdSampleRate;
    private readonly double[] _stage1FlatLut;
    private readonly int _stage1NumBlocks;
    private readonly double[] _stage2Filter;
    private readonly int _stage2Decimation;
    private readonly double[]? _stage3Filter;
    private readonly int _stage3Decimation;

    // Mirrored ring buffers (2 * RingSize) so FIR dot products read contiguous memory with zero wraparound branches
    private readonly byte[] _byteRing = new byte[RingSize * 2];
    private int _byteRingPos;

    private readonly double[] _stage2Ring = new double[RingSize * 2];
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
        int bitOrderIdx = (int)bitOrder;
        Array.Fill(_byteRing, (byte)0xAA);

        if (dsdSampleRate <= 2822400) // DSD64: 2.8224 MHz -> 176.4 kHz (16:1)
        {
            _stage1FlatLut = DsdDecimationFilter.Stage1FlatTables[0 * 2 + bitOrderIdx];
            _stage1NumBlocks = DsdDecimationFilter.Filter0.Length / 8;
            _stage2Filter = DsdDecimationFilter.Filter5;
            _stage2Decimation = 2;
        }
        else if (dsdSampleRate <= 5644800) // DSD128: 5.6448 MHz -> 352.8 kHz (16:1)
        {
            _stage1FlatLut = DsdDecimationFilter.Stage1FlatTables[1 * 2 + bitOrderIdx];
            _stage1NumBlocks = DsdDecimationFilter.Filter1.Length / 8;
            _stage2Filter = DsdDecimationFilter.Filter4;
            _stage2Decimation = 2;
        }
        else if (dsdSampleRate <= 11289600) // DSD256: 11.2896 MHz -> 352.8 kHz (32:1)
        {
            _stage1FlatLut = DsdDecimationFilter.Stage1FlatTables[2 * 2 + bitOrderIdx];
            _stage1NumBlocks = DsdDecimationFilter.Filter2.Length / 8;
            _stage2Filter = DsdDecimationFilter.Filter3;
            _stage2Decimation = 4;
        }
        else // DSD512: 22.5792 MHz -> 352.8 kHz (64:1)
        {
            _stage1FlatLut = DsdDecimationFilter.Stage1FlatTables[3 * 2 + bitOrderIdx];
            _stage1NumBlocks = DsdDecimationFilter.Filter7.Length / 8;
            _stage2Filter = DsdDecimationFilter.Filter8;
            _stage2Decimation = 2;
            _stage3Filter = DsdDecimationFilter.Filter3;
            _stage3Decimation = 4;
            _stage3Ring = new double[RingSize * 2];
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
    [MethodImpl(MethodImplOptions.AggressiveOptimization)]
    public int Process(ReadOnlySpan<byte> dsdBytes, Span<float> pcmOutput)
    {
        int outIdx = 0;
        int numBlocks = _stage1NumBlocks;
        int unrolledBlocksEnd = numBlocks - (numBlocks & 3);
        int s2Decim = _stage2Decimation;
        int s3Decim = _stage3Decimation;

        double[] s2Filter = _stage2Filter;
        int s2Len = s2Filter.Length;
        double[]? s3Filter = _stage3Filter;
        double[]? s3Ring = _stage3Ring;

        ref double pLutBase = ref MemoryMarshal.GetArrayDataReference(_stage1FlatLut);
        ref byte pByteRingBase = ref MemoryMarshal.GetArrayDataReference(_byteRing);
        ref double pS2RingBase = ref MemoryMarshal.GetArrayDataReference(_stage2Ring);
        ref double pS2Filter = ref MemoryMarshal.GetArrayDataReference(s2Filter);
        ref byte pDsd = ref MemoryMarshal.GetReference(dsdBytes);
        ref float pOut = ref MemoryMarshal.GetReference(pcmOutput);
        int outCap = pcmOutput.Length;

        int bytePos = _byteRingPos;
        int s2Pos = _stage2RingPos;
        int s2DecimCount = _stage2DecimCount;
        int s3Pos = _stage3RingPos;
        int s3DecimCount = _stage3DecimCount;

        for (int i = 0; i < dsdBytes.Length; i++)
        {
            byte inputByte = Unsafe.Add(ref pDsd, i);

            // Stage 1: mirrored byte ring buffer write
            Unsafe.Add(ref pByteRingBase, bytePos) = inputByte;
            Unsafe.Add(ref pByteRingBase, bytePos + RingSize) = inputByte;
            if (++bytePos >= RingSize) bytePos = 0;

            // Stage 1: contiguous dot-product via flat 256-entry lookup tables (4-way unrolled)
            ref byte pRing = ref Unsafe.Add(ref pByteRingBase, bytePos);
            double acc0 = 0.0, acc1 = 0.0, acc2 = 0.0, acc3 = 0.0;
            int j = 0;
            int lutOffset = 0;
            for (; j < unrolledBlocksEnd; j += 4, lutOffset += 1024)
            {
                acc0 += Unsafe.Add(ref pLutBase, lutOffset + Unsafe.Add(ref pRing, j));
                acc1 += Unsafe.Add(ref pLutBase, lutOffset + 256 + Unsafe.Add(ref pRing, j + 1));
                acc2 += Unsafe.Add(ref pLutBase, lutOffset + 512 + Unsafe.Add(ref pRing, j + 2));
                acc3 += Unsafe.Add(ref pLutBase, lutOffset + 768 + Unsafe.Add(ref pRing, j + 3));
            }
            double s1 = (acc0 + acc1) + (acc2 + acc3);
            for (; j < numBlocks; j++, lutOffset += 256)
            {
                s1 += Unsafe.Add(ref pLutBase, lutOffset + Unsafe.Add(ref pRing, j));
            }

            // Stage 2: mirrored double ring buffer write
            Unsafe.Add(ref pS2RingBase, s2Pos) = s1;
            Unsafe.Add(ref pS2RingBase, s2Pos + RingSize) = s1;
            if (++s2Pos >= RingSize) s2Pos = 0;

            if (s2DecimCount == 0)
            {
                double s2 = DotProduct(ref Unsafe.Add(ref pS2RingBase, s2Pos), ref pS2Filter, s2Len);

                if (s3Filter != null && s3Ring != null)
                {
                    // Stage 3 (for DSD512)
                    ref double pS3RingBase = ref MemoryMarshal.GetArrayDataReference(s3Ring);
                    Unsafe.Add(ref pS3RingBase, s3Pos) = s2;
                    Unsafe.Add(ref pS3RingBase, s3Pos + RingSize) = s2;
                    if (++s3Pos >= RingSize) s3Pos = 0;

                    if (s3DecimCount == 0)
                    {
                        double s3 = DotProduct(ref Unsafe.Add(ref pS3RingBase, s3Pos), ref MemoryMarshal.GetArrayDataReference(s3Filter), s3Filter.Length);
                        if (outIdx < outCap)
                            Unsafe.Add(ref pOut, outIdx++) = (float)(s3 * DsdDecimationFilter.DsdGainFactor);
                    }
                    if (++s3DecimCount == s3Decim) s3DecimCount = 0;
                }
                else
                {
                    if (outIdx < outCap)
                        Unsafe.Add(ref pOut, outIdx++) = (float)(s2 * DsdDecimationFilter.DsdGainFactor);
                }
            }
            if (++s2DecimCount == s2Decim) s2DecimCount = 0;
        }

        _byteRingPos = bytePos;
        _stage2RingPos = s2Pos;
        _stage2DecimCount = s2DecimCount;
        _stage3RingPos = s3Pos;
        _stage3DecimCount = s3DecimCount;

        return outIdx;
    }

    [MethodImpl(MethodImplOptions.AggressiveInlining)]
    private static double DotProduct(ref double ring, ref double filter, int length)
    {
        int k = 0;
        double sum = 0.0;
        if (Vector256.IsHardwareAccelerated)
        {
            Vector256<double> acc = Vector256<double>.Zero;
            int simdEnd = length - (length & 3);
            for (; k < simdEnd; k += 4)
            {
                var vf = Vector256.LoadUnsafe(ref filter, (nuint)k);
                var vr = Vector256.LoadUnsafe(ref ring, (nuint)k);
                acc = Vector256.FusedMultiplyAdd(vf, vr, acc);
            }
            sum = Vector256.Sum(acc);
        }
        for (; k < length; k++)
        {
            sum += Unsafe.Add(ref filter, k) * Unsafe.Add(ref ring, k);
        }
        return sum;
    }
}
