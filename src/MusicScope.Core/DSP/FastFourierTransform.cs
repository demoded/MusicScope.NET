using System;
using System.Numerics;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Runtime.Intrinsics;

namespace MusicScope.Core.DSP;

/// <summary>
/// High-performance Radix-2 Cooley-Tukey Fast Fourier Transform (FFT).
/// Provides precomputed bit-reversal tables and twiddle factors for real-time audio visualization.
/// </summary>
public sealed class FastFourierTransform
{
    private readonly int _size;
    private readonly int _levels;
    private readonly int[] _swapPairs;
    private readonly double[] _cosTwiddles;
    private readonly double[] _sinTwiddles;
    private readonly double[][] _stageCos;
    private readonly double[][] _stageSin;

    public int Size => _size;

    public FastFourierTransform(int size)
    {
        if (size <= 0 || (size & (size - 1)) != 0)
            throw new ArgumentException("FFT size must be a power of 2.", nameof(size));

        _size = size;
        _levels = BitOperations.TrailingZeroCount((uint)size);

        // Precompute bit-reversal swap pairs (only indices where j > i)
        int pairCount = 0;
        var bitRev = new int[size];
        for (int i = 0; i < size; i++)
        {
            int reversed = 0;
            int temp = i;
            for (int bit = 0; bit < _levels; bit++)
            {
                reversed = (reversed << 1) | (temp & 1);
                temp >>= 1;
            }
            bitRev[i] = reversed;
            if (reversed > i)
                pairCount++;
        }

        _swapPairs = new int[pairCount * 2];
        int p = 0;
        for (int i = 0; i < size; i++)
        {
            int j = bitRev[i];
            if (j > i)
            {
                _swapPairs[p++] = i;
                _swapPairs[p++] = j;
            }
        }

        // Precompute twiddle factors: e^(-2*pi*i*k/N)
        _cosTwiddles = new double[size / 2];
        _sinTwiddles = new double[size / 2];
        for (int i = 0; i < size / 2; i++)
        {
            double angle = -2.0 * Math.PI * i / size;
            _cosTwiddles[i] = Math.Cos(angle);
            _sinTwiddles[i] = Math.Sin(angle);
        }

        // Contiguous per-stage twiddle tables for SIMD butterfly stages (stage >= 3)
        _stageCos = new double[_levels + 1][];
        _stageSin = new double[_levels + 1][];
        for (int stage = 3; stage <= _levels; stage++)
        {
            int m2 = 1 << (stage - 1);
            int step = size >> stage;
            var sc = new double[m2];
            var ss = new double[m2];
            for (int j = 0; j < m2; j++)
            {
                int idx = j * step;
                sc[j] = _cosTwiddles[idx];
                ss[j] = _sinTwiddles[idx];
            }
            _stageCos[stage] = sc;
            _stageSin[stage] = ss;
        }
    }

    /// <summary>
    /// Executes an in-place Forward FFT on real and imaginary buffers.
    /// </summary>
    public void Forward(Span<double> real, Span<double> imag)
    {
        if (real.Length < _size || imag.Length < _size)
            throw new ArgumentException("Buffers must be at least FFT size.");

        ref double rBase = ref MemoryMarshal.GetReference(real);
        ref double iBase = ref MemoryMarshal.GetReference(imag);

        // Bit-reversal reordering using precomputed swap pairs
        int[] pairs = _swapPairs;
        for (int idx = 0; idx < pairs.Length; idx += 2)
        {
            int i = pairs[idx];
            int j = pairs[idx + 1];
            (Unsafe.Add(ref rBase, i), Unsafe.Add(ref rBase, j)) = (Unsafe.Add(ref rBase, j), Unsafe.Add(ref rBase, i));
            (Unsafe.Add(ref iBase, i), Unsafe.Add(ref iBase, j)) = (Unsafe.Add(ref iBase, j), Unsafe.Add(ref iBase, i));
        }

        int size = _size;
        int levels = _levels;

        // Stage 1 (m = 2, m2 = 1): twiddle is always (1, 0)
        if (levels >= 1)
        {
            for (int k = 0; k < size; k += 2)
            {
                double eR = Unsafe.Add(ref rBase, k);
                double eI = Unsafe.Add(ref iBase, k);
                double oR = Unsafe.Add(ref rBase, k + 1);
                double oI = Unsafe.Add(ref iBase, k + 1);

                Unsafe.Add(ref rBase, k) = eR + oR;
                Unsafe.Add(ref iBase, k) = eI + oI;
                Unsafe.Add(ref rBase, k + 1) = eR - oR;
                Unsafe.Add(ref iBase, k + 1) = eI - oI;
            }
        }

        // Stage 2 (m = 4, m2 = 2): twiddles are (1, 0) and (0, -1)
        if (levels >= 2)
        {
            for (int k = 0; k < size; k += 4)
            {
                // j = 0: twiddle (1, 0)
                double e0R = Unsafe.Add(ref rBase, k);
                double e0I = Unsafe.Add(ref iBase, k);
                double o0R = Unsafe.Add(ref rBase, k + 2);
                double o0I = Unsafe.Add(ref iBase, k + 2);

                Unsafe.Add(ref rBase, k) = e0R + o0R;
                Unsafe.Add(ref iBase, k) = e0I + o0I;
                Unsafe.Add(ref rBase, k + 2) = e0R - o0R;
                Unsafe.Add(ref iBase, k + 2) = e0I - o0I;

                // j = 1: twiddle (0, -1) => tR = o1I, tI = -o1R
                double e1R = Unsafe.Add(ref rBase, k + 1);
                double e1I = Unsafe.Add(ref iBase, k + 1);
                double o1R = Unsafe.Add(ref rBase, k + 3);
                double o1I = Unsafe.Add(ref iBase, k + 3);

                Unsafe.Add(ref rBase, k + 1) = e1R + o1I;
                Unsafe.Add(ref iBase, k + 1) = e1I - o1R;
                Unsafe.Add(ref rBase, k + 3) = e1R - o1I;
                Unsafe.Add(ref iBase, k + 3) = e1I + o1R;
            }
        }

        // Stages >= 3: m2 >= 4, SIMD Vector256 butterflies with contiguous twiddles
        for (int stage = 3; stage <= levels; stage++)
        {
            int m = 1 << stage;
            int m2 = m >> 1;
            ref double tcBase = ref MemoryMarshal.GetArrayDataReference(_stageCos[stage]);
            ref double tsBase = ref MemoryMarshal.GetArrayDataReference(_stageSin[stage]);

            if (Vector256.IsHardwareAccelerated)
            {
                for (int k = 0; k < size; k += m)
                {
                    int evenStart = k;
                    int oddStart = k + m2;
                    for (int j = 0; j < m2; j += 4)
                    {
                        var uCos = Vector256.LoadUnsafe(ref tcBase, (nuint)j);
                        var uSin = Vector256.LoadUnsafe(ref tsBase, (nuint)j);

                        var oddR = Vector256.LoadUnsafe(ref rBase, (nuint)(oddStart + j));
                        var oddI = Vector256.LoadUnsafe(ref iBase, (nuint)(oddStart + j));

                        var tR = oddR * uCos - oddI * uSin;
                        var tI = oddR * uSin + oddI * uCos;

                        var evenR = Vector256.LoadUnsafe(ref rBase, (nuint)(evenStart + j));
                        var evenI = Vector256.LoadUnsafe(ref iBase, (nuint)(evenStart + j));

                        (evenR + tR).StoreUnsafe(ref rBase, (nuint)(evenStart + j));
                        (evenI + tI).StoreUnsafe(ref iBase, (nuint)(evenStart + j));
                        (evenR - tR).StoreUnsafe(ref rBase, (nuint)(oddStart + j));
                        (evenI - tI).StoreUnsafe(ref iBase, (nuint)(oddStart + j));
                    }
                }
            }
            else
            {
                for (int k = 0; k < size; k += m)
                {
                    int evenStart = k;
                    int oddStart = k + m2;
                    for (int j = 0; j < m2; j++)
                    {
                        double uCos = Unsafe.Add(ref tcBase, j);
                        double uSin = Unsafe.Add(ref tsBase, j);

                        double oddR = Unsafe.Add(ref rBase, oddStart + j);
                        double oddI = Unsafe.Add(ref iBase, oddStart + j);

                        double tR = oddR * uCos - oddI * uSin;
                        double tI = oddR * uSin + oddI * uCos;

                        double evenR = Unsafe.Add(ref rBase, evenStart + j);
                        double evenI = Unsafe.Add(ref iBase, evenStart + j);

                        Unsafe.Add(ref rBase, evenStart + j) = evenR + tR;
                        Unsafe.Add(ref iBase, evenStart + j) = evenI + tI;
                        Unsafe.Add(ref rBase, oddStart + j) = evenR - tR;
                        Unsafe.Add(ref iBase, oddStart + j) = evenI - tI;
                    }
                }
            }
        }
    }

    /// <summary>
    /// Computes the normalized magnitude spectrum in dBFS for positive frequencies (size / 2 bins).
    /// </summary>
    public void CalculateMagnitudeDb(
        ReadOnlySpan<double> real,
        ReadOnlySpan<double> imag,
        Span<double> magnitudesDb,
        double windowSum = 1.0,
        double floorDb = -140.0)
    {
        int halfSize = _size / 2;
        int count = Math.Min(halfSize, magnitudesDb.Length);
        double scale = 2.0 / (windowSum > 0 ? windowSum : _size);

        for (int i = 0; i < count; i++)
        {
            double r = real[i] * scale;
            double im = imag[i] * scale;
            double mag = Math.Sqrt(r * r + im * im);

            magnitudesDb[i] = mag > 1e-7 ? Math.Max(floorDb, 20.0 * Math.Log10(mag)) : floorDb;
        }
    }

    /// <summary>
    /// Computes the center frequency in Hz for a given FFT bin.
    /// </summary>
    public double GetFrequencyForBin(int binIndex, double sampleRate)
    {
        return binIndex * sampleRate / _size;
    }

    /// <summary>
    /// Computes the FFT bin index closest to the specified frequency in Hz.
    /// </summary>
    public int GetBinForFrequency(double frequencyHz, double sampleRate)
    {
        int bin = (int)Math.Round(frequencyHz * _size / sampleRate);
        return Math.Clamp(bin, 0, _size / 2 - 1);
    }
}
