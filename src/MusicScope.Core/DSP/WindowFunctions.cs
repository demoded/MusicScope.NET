using System;

namespace MusicScope.Core.DSP;

/// <summary>
/// Window types for FFT analysis.
/// </summary>
public enum WindowType
{
    Rectangular,
    Hann,
    Hamming,
    Blackman,
    BlackmanHarris,
    FlatTop
}

/// <summary>
/// Precomputed window functions for spectral leakage reduction.
/// </summary>
public static class WindowFunctions
{
    public static double[] Create(WindowType type, int length)
    {
        if (length <= 0)
            throw new ArgumentOutOfRangeException(nameof(length));

        double[] window = new double[length];
        double nMinusOne = length > 1 ? length - 1 : 1;

        switch (type)
        {
            case WindowType.Rectangular:
                Array.Fill(window, 1.0);
                break;

            case WindowType.Hann:
                for (int i = 0; i < length; i++)
                {
                    window[i] = 0.5 * (1.0 - Math.Cos(2.0 * Math.PI * i / nMinusOne));
                }
                break;

            case WindowType.Hamming:
                for (int i = 0; i < length; i++)
                {
                    window[i] = 0.54 - 0.46 * Math.Cos(2.0 * Math.PI * i / nMinusOne);
                }
                break;

            case WindowType.Blackman:
                for (int i = 0; i < length; i++)
                {
                    double a = 2.0 * Math.PI * i / nMinusOne;
                    window[i] = 0.42 - 0.5 * Math.Cos(a) + 0.08 * Math.Cos(2.0 * a);
                }
                break;

            case WindowType.BlackmanHarris:
                // 4-term Blackman-Harris (optimal side-lobe attenuation ~92 dB)
                for (int i = 0; i < length; i++)
                {
                    double a = 2.0 * Math.PI * i / nMinusOne;
                    window[i] = 0.35875 
                              - 0.48829 * Math.Cos(a) 
                              + 0.14128 * Math.Cos(2.0 * a) 
                              - 0.01168 * Math.Cos(3.0 * a);
                }
                break;

            case WindowType.FlatTop:
                // Flat-Top window (optimal for precise amplitude calibration)
                for (int i = 0; i < length; i++)
                {
                    double a = 2.0 * Math.PI * i / nMinusOne;
                    window[i] = 0.21557895
                              - 0.41663158 * Math.Cos(a)
                              + 0.277263158 * Math.Cos(2.0 * a)
                              - 0.083578947 * Math.Cos(3.0 * a)
                              + 0.006947368 * Math.Cos(4.0 * a);
                }
                break;
        }

        return window;
    }
}
