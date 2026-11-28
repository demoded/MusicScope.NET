using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Runtime.Intrinsics;

namespace MusicScope.Core.DSP;

/// <summary>Detached spectrum data for channel and panorama/phase displays.</summary>
public sealed record SpectrumFrame
{
    public double[] InstantDb { get; init; } = [];
    public double[] PeakDb { get; init; } = [];
    public double[] LeftDb { get; init; } = [];
    public double[] RightDb { get; init; } = [];
    public double[] Panorama { get; init; } = [];
    public double[] PhaseRadians { get; init; } = [];
}

/// <summary>
/// SpectrumModule/SpectrumControl channel smoothing and four-update panorama/phase smoothing.
/// The logarithmic display uses the original 8192-point FFT for low-frequency resolution.
/// </summary>
internal sealed class SpectrumAnalyzer
{
    private struct History3
    {
        public double H0;
        public double H1;
        public double H2;
    }

    private readonly int _size;
    private readonly int _stride;
    private readonly FastFourierTransform _fft;
    private readonly double[] _window;
    private readonly double[] _packedReal, _packedImag, _twiddleCos, _twiddleSin;
    private readonly double[] _ringLeft, _ringRight, _realLeft, _imagLeft, _realRight, _imagRight;
    private readonly double[] _combined, _peak, _left, _right, _panorama, _phase;
    private readonly History3[] _panHistory, _phaseHistory;
    private int _position, _buffered, _sinceFft;
    private bool _hasChannelDifference;

    public SpectrumAnalyzer(int size, double sampleRate)
    {
        _size = size;
        _stride = Math.Max(1, (int)(sampleRate * 0.050));
        // Pack even/odd real samples into a half-size complex FFT.
        _fft = new(size / 2);
        _packedReal = new double[size / 2];
        _packedImag = new double[size / 2];
        _twiddleCos = new double[size / 2];
        _twiddleSin = new double[size / 2];
        for (int i = 0; i < size / 2; i++)
        {
            _twiddleCos[i] = Math.Cos(-2 * Math.PI * i / size);
            _twiddleSin[i] = Math.Sin(-2 * Math.PI * i / size);
        }
        _window = WindowFunctions.Create(WindowType.BlackmanHarris, size);
        _ringLeft = new double[size]; _ringRight = new double[size];
        _realLeft = new double[size]; _imagLeft = new double[size];
        _realRight = new double[size]; _imagRight = new double[size];
        int bins = size / 2;
        _combined = new double[bins]; _peak = new double[bins];
        _left = new double[bins]; _right = new double[bins];
        _panorama = new double[bins]; _phase = new double[bins];
        _panHistory = new History3[bins];
        _phaseHistory = new History3[bins];
        Reset();
    }

    public void Process(ReadOnlySpan<float> samples, int channels)
    {
        int frames = samples.Length / channels;
        int offset = 0;
        while (offset < frames)
        {
            int count = Math.Min(frames - offset, Math.Min(_size - _position, _stride - _sinceFft));
            for (int i = 0; i < count; i++)
            {
                int source = (offset + i) * channels;
                _ringLeft[_position + i] = samples[source];
                _ringRight[_position + i] = channels >= 2 ? samples[source + 1] : samples[source];
            }
            _position += count;
            if (_position == _size) _position = 0;
            _buffered = Math.Min(_size, _buffered + count);
            _sinceFft += count;
            offset += count;
            if (_sinceFft == _stride)
            {
                _sinceFft = 0;
                if (_buffered == _size) Execute();
            }
        }
    }

    public void Finish()
    {
        if (_buffered == _size && _sinceFft > 0) Execute();
    }

    [MethodImpl(MethodImplOptions.AggressiveOptimization)]
    private void Execute()
    {
        _sinceFft = 0;
        ProcessRingWindow(_ringLeft, _ringRight, _position);
    }

    [MethodImpl(MethodImplOptions.AggressiveOptimization)]
    public void ProcessRingWindow(double[] ringLeft, double[] ringRight, int position)
    {
        bool equalChannels = ringLeft.AsSpan().SequenceEqual(ringRight);
        WindowRing(ringLeft, position, _window, _realLeft);
        TransformReal(_realLeft, _imagLeft);
        if (equalChannels)
        {
            int bins = _size / 2;
            _realLeft.AsSpan(0, bins).CopyTo(_realRight);
            _imagLeft.AsSpan(0, bins).CopyTo(_imagRight);
        }
        else
        {
            WindowRing(ringRight, position, _window, _realRight);
            TransformReal(_realRight, _imagRight);
        }
        Update(_realLeft, _imagLeft, _realRight, _imagRight);
    }

    [MethodImpl(MethodImplOptions.AggressiveOptimization)]
    private static void WindowRing(double[] ring, int position, double[] window, double[] dest)
    {
        int size = ring.Length;
        int firstLen = size - position;
        MultiplySpans(ring.AsSpan(position, firstLen), window.AsSpan(0, firstLen), dest.AsSpan(0, firstLen));
        if (position > 0)
        {
            MultiplySpans(ring.AsSpan(0, position), window.AsSpan(firstLen, position), dest.AsSpan(firstLen, position));
        }
    }

    [MethodImpl(MethodImplOptions.AggressiveInlining)]
    private static void MultiplySpans(ReadOnlySpan<double> a, ReadOnlySpan<double> b, Span<double> dst)
    {
        int i = 0;
        if (Vector256.IsHardwareAccelerated)
        {
            int vecEnd = dst.Length - (dst.Length & 3);
            ref double pa = ref MemoryMarshal.GetReference(a);
            ref double pb = ref MemoryMarshal.GetReference(b);
            ref double pd = ref MemoryMarshal.GetReference(dst);
            for (; i < vecEnd; i += 4)
            {
                var va = Vector256.LoadUnsafe(ref pa, (nuint)i);
                var vb = Vector256.LoadUnsafe(ref pb, (nuint)i);
                (va * vb).StoreUnsafe(ref pd, (nuint)i);
            }
        }
        for (; i < dst.Length; i++)
        {
            dst[i] = a[i] * b[i];
        }
    }

    [MethodImpl(MethodImplOptions.AggressiveOptimization)]
    private void TransformReal(double[] real, double[] imag)
    {
        int half = _size / 2;
        double[] packedReal = _packedReal;
        double[] packedImag = _packedImag;
        for (int i = 0; i < half; i++)
        {
            int idx = i << 1;
            packedReal[i] = real[idx];
            packedImag[i] = real[idx + 1];
        }
        _fft.Forward(packedReal, packedImag);

        // Handle DC bin (k == 0) outside the loop to keep the main loop branch-free
        double pr0 = packedReal[0];
        double pi0 = packedImag[0];
        real[0] = pr0 + pi0;
        imag[0] = 0.0;

        double[] twCos = _twiddleCos;
        double[] twSin = _twiddleSin;
        for (int k = 1; k < half; k++)
        {
            int mirror = half - k;
            double prk = packedReal[k];
            double prm = packedReal[mirror];
            double pik = packedImag[k];
            double pim = packedImag[mirror];

            double evenReal = (prk + prm) * 0.5;
            double evenImag = (pik - pim) * 0.5;
            double oddReal = (pik + pim) * 0.5;
            double oddImag = (prm - prk) * 0.5;
            double c = twCos[k];
            double s = twSin[k];
            real[k] = evenReal + c * oddReal - s * oddImag;
            imag[k] = evenImag + c * oddImag + s * oddReal;
        }
    }

    [MethodImpl(MethodImplOptions.AggressiveOptimization)]
    public void Update(ReadOnlySpan<double> realLeft, ReadOnlySpan<double> imagLeft,
        ReadOnlySpan<double> realRight, ReadOnlySpan<double> imagRight)
    {
        double normalization = 8.0 / _size;
        double[] leftArr = _left;
        double[] rightArr = _right;
        double[] combinedArr = _combined;
        double[] peakArr = _peak;
        double[] phaseArr = _phase;
        double[] panArr = _panorama;
        History3[] phaseHist = _phaseHistory;
        History3[] panHist = _panHistory;
        int len = combinedArr.Length;

        int b = 0;
        if (!_hasChannelDifference)
        {
            for (; b < len; b++)
            {
                double rL = realLeft[b];
                double iL = imagLeft[b];
                double rR = realRight[b];
                double iR = imagRight[b];
                if (rL != rR || iL != iR)
                {
                    _hasChannelDifference = true;
                    break;
                }
                double l = Math.Sqrt(rL * rL + iL * iL) * normalization;
                double sm = Math.Max(1e-10, (leftArr[b] + l) * 0.5);
                leftArr[b] = sm;
                rightArr[b] = sm;
                combinedArr[b] = sm;
                if (sm > peakArr[b]) peakArr[b] = sm;
            }
        }

        if (b < len)
        {
            if (Vector256.IsHardwareAccelerated && len - b >= 4)
            {
                ref double pRL = ref MemoryMarshal.GetReference(realLeft);
                ref double pIL = ref MemoryMarshal.GetReference(imagLeft);
                ref double pRR = ref MemoryMarshal.GetReference(realRight);
                ref double pIR = ref MemoryMarshal.GetReference(imagRight);
                ref double pLeft = ref MemoryMarshal.GetArrayDataReference(leftArr);
                ref double pRight = ref MemoryMarshal.GetArrayDataReference(rightArr);
                ref double pComb = ref MemoryMarshal.GetArrayDataReference(combinedArr);
                ref double pPeak = ref MemoryMarshal.GetArrayDataReference(peakArr);

                var vNorm = Vector256.Create(normalization);
                var vHalf = Vector256.Create(0.5);
                var vFloor = Vector256.Create(1e-10);

                int vecEnd = len - ((len - b) & 3);
                for (; b < vecEnd; b += 4)
                {
                    nuint offset = (nuint)b;
                    var vRL = Vector256.LoadUnsafe(ref pRL, offset);
                    var vIL = Vector256.LoadUnsafe(ref pIL, offset);
                    var vRR = Vector256.LoadUnsafe(ref pRR, offset);
                    var vIR = Vector256.LoadUnsafe(ref pIR, offset);

                    var vL = Vector256.Sqrt(vRL * vRL + vIL * vIL) * vNorm;
                    var vR = Vector256.Sqrt(vRR * vRR + vIR * vIR) * vNorm;

                    var vOldL = Vector256.LoadUnsafe(ref pLeft, offset);
                    var vOldR = Vector256.LoadUnsafe(ref pRight, offset);
                    var vOldC = Vector256.LoadUnsafe(ref pComb, offset);
                    var vOldP = Vector256.LoadUnsafe(ref pPeak, offset);

                    var vNewL = Vector256.Max(vFloor, (vOldL + vL) * vHalf);
                    var vNewR = Vector256.Max(vFloor, (vOldR + vR) * vHalf);
                    var vNewC = Vector256.Max(vFloor, (vOldC + (vL + vR) * vHalf) * vHalf);
                    var vNewP = Vector256.Max(vOldP, vNewC);

                    vNewL.StoreUnsafe(ref pLeft, offset);
                    vNewR.StoreUnsafe(ref pRight, offset);
                    vNewC.StoreUnsafe(ref pComb, offset);
                    vNewP.StoreUnsafe(ref pPeak, offset);

                    // Compute phase and panorama smoothing for the 4 bins
                    var vAbove = Vector256.GreaterThan(vL, vFloor) & Vector256.GreaterThan(vR, vFloor);
                    uint mask = vAbove.ExtractMostSignificantBits();

                    for (int k = 0; k < 4; k++)
                    {
                        int bin = b + k;
                        double phase = 0.0;
                        if ((mask & (1u << k)) != 0)
                        {
                            double rL = vRL.GetElement(k);
                            double iL = vIL.GetElement(k);
                            double rR = vRR.GetElement(k);
                            double iR = vIR.GetElement(k);
                            phase = Math.Abs(Math.Atan2(iL * rR - rL * iR, rL * rR + iL * iR));
                        }
                        phaseArr[bin] = Smooth(phase, ref phaseHist[bin]);
                        panArr[bin] = Smooth(vNewR.GetElement(k) - vNewL.GetElement(k), ref panHist[bin]);
                    }
                }
            }

            for (; b < len; b++)
            {
                double rL = realLeft[b];
                double iL = imagLeft[b];
                double rR = realRight[b];
                double iR = imagRight[b];
                double l = Math.Sqrt(rL * rL + iL * iL) * normalization;
                double r = Math.Sqrt(rR * rR + iR * iR) * normalization;
                double newL = Math.Max(1e-10, (leftArr[b] + l) * 0.5);
                double newR = Math.Max(1e-10, (rightArr[b] + r) * 0.5);
                double newC = Math.Max(1e-10, (combinedArr[b] + (l + r) * 0.5) * 0.5);
                leftArr[b] = newL;
                rightArr[b] = newR;
                combinedArr[b] = newC;
                if (newC > peakArr[b]) peakArr[b] = newC;

                double phase = 0.0;
                if (l > 1e-10 && r > 1e-10)
                {
                    phase = Math.Abs(Math.Atan2(iL * rR - rL * iR, rL * rR + iL * iR));
                }
                phaseArr[b] = Smooth(phase, ref phaseHist[b]);
                panArr[b] = Smooth(newR - newL, ref panHist[b]);
            }
        }
    }

    [MethodImpl(MethodImplOptions.AggressiveInlining)]
    private static double Smooth(double value, ref History3 history)
    {
        double result = (value + history.H0 + history.H1 + history.H2) * 0.25;
        history.H2 = history.H1;
        history.H1 = history.H0;
        history.H0 = result;
        return result;
    }

    public double GetMagnitude(int bin) => _combined[bin];
    public double GetPeakMagnitude(int bin) => _peak[bin];

    public SpectrumFrame Capture() => new()
    {
        InstantDb = ToDb(_combined), PeakDb = ToDb(_peak),
        LeftDb = ToDb(_left), RightDb = ToDb(_right),
        Panorama = (double[])_panorama.Clone(), PhaseRadians = (double[])_phase.Clone()
    };

    private static double[] ToDb(double[] magnitudes)
    {
        var result = new double[magnitudes.Length];
        for (int i = 0; i < result.Length; i++) result[i] = 20 * Math.Log10(magnitudes[i]);
        return result;
    }

    public void Reset()
    {
        _position = _buffered = _sinceFft = 0;
        _hasChannelDifference = false;
        Array.Clear(_ringLeft); Array.Clear(_ringRight);
        foreach (var values in new[] { _combined, _peak, _left, _right }) Array.Fill(values, 1e-10);
        Array.Clear(_panorama); Array.Clear(_phase);
        Array.Clear(_panHistory);
        Array.Clear(_phaseHistory);
    }
}
