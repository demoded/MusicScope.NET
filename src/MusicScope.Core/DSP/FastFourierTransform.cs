using System;
using System.Numerics;

namespace MusicScope.Core.DSP;

/// <summary>
/// High-performance Radix-2 Cooley-Tukey Fast Fourier Transform (FFT).
/// Provides precomputed bit-reversal tables and twiddle factors for real-time audio visualization.
/// </summary>
public sealed class FastFourierTransform
{
    private readonly int _size;
    private readonly int _levels;
    private readonly int[] _bitReversalTable;
    private readonly double[] _cosTwiddles;
    private readonly double[] _sinTwiddles;

    public int Size => _size;

    public FastFourierTransform(int size)
    {
        if (size <= 0 || (size & (size - 1)) != 0)
            throw new ArgumentException("FFT size must be a power of 2.", nameof(size));

        _size = size;
        _levels = (int)Math.Round(Math.Log2(size));

        // Precompute bit reversal permutation table
        _bitReversalTable = new int[size];
        for (int i = 0; i < size; i++)
        {
            int reversed = 0;
            int temp = i;
            for (int bit = 0; bit < _levels; bit++)
            {
                reversed = (reversed << 1) | (temp & 1);
                temp >>= 1;
            }
            _bitReversalTable[i] = reversed;
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
    }

    /// <summary>
    /// Executes an in-place Forward FFT on real and imaginary buffers.
    /// </summary>
    public void Forward(Span<double> real, Span<double> imag)
    {
        if (real.Length < _size || imag.Length < _size)
            throw new ArgumentException("Buffers must be at least FFT size.");

        // Bit-reversal reordering
        for (int i = 0; i < _size; i++)
        {
            int j = _bitReversalTable[i];
            if (j > i)
            {
                (real[i], real[j]) = (real[j], real[i]);
                (imag[i], imag[j]) = (imag[j], imag[i]);
            }
        }

        // Cooley-Tukey butterfly stages
        for (int stage = 1; stage <= _levels; stage++)
        {
            int m = 1 << stage;         // current butterfly block size (2, 4, 8, ...)
            int m2 = m >> 1;            // half block size
            int step = _size >> stage;  // twiddle step index

            for (int k = 0; k < _size; k += m)
            {
                for (int j = 0; j < m2; j++)
                {
                    int twiddleIdx = j * step;
                    double uCos = _cosTwiddles[twiddleIdx];
                    double uSin = _sinTwiddles[twiddleIdx];

                    int evenIdx = k + j;
                    int oddIdx = k + j + m2;

                    double oddR = real[oddIdx];
                    double oddI = imag[oddIdx];

                    // Complex multiplication: (oddR + i*oddI) * (uCos + i*uSin)
                    double tR = oddR * uCos - oddI * uSin;
                    double tI = oddR * uSin + oddI * uCos;

                    double evenR = real[evenIdx];
                    double evenI = imag[evenIdx];

                    real[evenIdx] = evenR + tR;
                    imag[evenIdx] = evenI + tI;
                    real[oddIdx] = evenR - tR;
                    imag[oddIdx] = evenI - tI;
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
