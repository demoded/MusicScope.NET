using System;

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
    private readonly int _size;
    private readonly int _stride;
    private readonly FastFourierTransform _fft;
    private readonly double[] _window;
    private readonly double[] _packedReal, _packedImag, _twiddleCos, _twiddleSin;
    private readonly double[] _ringLeft, _ringRight, _realLeft, _imagLeft, _realRight, _imagRight;
    private readonly double[] _combined, _peak, _left, _right, _panorama, _phase;
    private readonly double[][] _panHistory, _phaseHistory;
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
        _panHistory = [new double[bins], new double[bins], new double[bins]];
        _phaseHistory = [new double[bins], new double[bins], new double[bins]];
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

    private void Execute()
    {
        _sinceFft = 0;
        bool equalChannels = true;
        int source = _position;
        for (int i = 0; i < _size; i++)
        {

            _realLeft[i] = _ringLeft[source] * _window[i];
            _realRight[i] = _ringRight[source] * _window[i];
            _imagLeft[i] = _imagRight[i] = 0;
            equalChannels &= _realLeft[i] == _realRight[i];
            if (++source == _size) source = 0;
        }
        TransformReal(_realLeft, _imagLeft);
        if (equalChannels)
        {
            _realLeft.AsSpan().CopyTo(_realRight);
            _imagLeft.AsSpan().CopyTo(_imagRight);
        }
        else TransformReal(_realRight, _imagRight);
        Update(_realLeft, _imagLeft, _realRight, _imagRight);
    }

    private void TransformReal(double[] real, double[] imag)
    {
        int half = _size / 2;
        for (int i = 0; i < half; i++)
        {
            _packedReal[i] = real[2 * i];
            _packedImag[i] = real[2 * i + 1];
        }
        _fft.Forward(_packedReal, _packedImag);
        for (int k = 0; k < half; k++)
        {
            int mirror = k == 0 ? 0 : half - k;
            double evenReal = (_packedReal[k] + _packedReal[mirror]) * 0.5;
            double evenImag = (_packedImag[k] - _packedImag[mirror]) * 0.5;
            double oddReal = (_packedImag[k] + _packedImag[mirror]) * 0.5;
            double oddImag = (_packedReal[mirror] - _packedReal[k]) * 0.5;
            real[k] = evenReal + _twiddleCos[k] * oddReal - _twiddleSin[k] * oddImag;
            imag[k] = evenImag + _twiddleCos[k] * oddImag + _twiddleSin[k] * oddReal;
        }
    }

    public void Update(ReadOnlySpan<double> realLeft, ReadOnlySpan<double> imagLeft,
        ReadOnlySpan<double> realRight, ReadOnlySpan<double> imagRight)
    {
        double normalization = 8.0 / _size;
        for (int b = 0; b < _combined.Length; b++)
        {
            double l = Math.Sqrt(realLeft[b] * realLeft[b] + imagLeft[b] * imagLeft[b]) * normalization;
            // Identical channels have identical smoothing histories and zero panorama/phase.
            // Avoid a second magnitude and history updates until a channel difference occurs.
            if (!_hasChannelDifference && realLeft[b] == realRight[b] && imagLeft[b] == imagRight[b])
            {
                _left[b] = Math.Max(1e-10, (_left[b] + l) / 2);
                _right[b] = _combined[b] = _left[b];
                _peak[b] = Math.Max(_peak[b], _combined[b]);
                continue;
            }
            _hasChannelDifference = true;
            double r = Math.Sqrt(realRight[b] * realRight[b] + imagRight[b] * imagRight[b]) * normalization;
            _left[b] = Math.Max(1e-10, (_left[b] + l) / 2);
            _right[b] = Math.Max(1e-10, (_right[b] + r) / 2);
            _combined[b] = Math.Max(1e-10, (_combined[b] + (l + r) / 2) / 2);
            _peak[b] = Math.Max(_peak[b], _combined[b]);
            double phase = 0;
            if (l > 1e-10 && r > 1e-10)
            {
                phase = Math.Atan2(imagLeft[b], realLeft[b]) - Math.Atan2(imagRight[b], realRight[b]);
                if (phase > Math.PI) phase -= 2 * Math.PI;
                else if (phase < -Math.PI) phase += 2 * Math.PI;
                phase = Math.Abs(phase);
            }
            _phase[b] = Smooth(phase, _phaseHistory, b);
            _panorama[b] = Smooth(_right[b] - _left[b], _panHistory, b);
        }
    }

    private static double Smooth(double value, double[][] history, int bin)
    {
        double result = (value + history[0][bin] + history[1][bin] + history[2][bin]) / 4;
        history[2][bin] = history[1][bin];
        history[1][bin] = history[0][bin];
        history[0][bin] = result;
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
        foreach (var values in _panHistory) Array.Clear(values);
        foreach (var values in _phaseHistory) Array.Clear(values);
    }
}
