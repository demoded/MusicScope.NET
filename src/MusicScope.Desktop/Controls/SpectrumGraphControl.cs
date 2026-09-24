using System;
using System.Globalization;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Media;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// Hardware-accelerated custom control rendering the logarithmic frequency spectrum (20 Hz - 48+ kHz).
/// Displays frequency grid, dBFS scale, and multi-curve spectrum.
/// </summary>
public sealed class SpectrumGraphControl : Control
{
    public static readonly StyledProperty<double[]?> MagnitudesDbProperty =
        AvaloniaProperty.Register<SpectrumGraphControl, double[]?>(nameof(MagnitudesDb));

    public static readonly StyledProperty<double> SampleRateProperty =
        AvaloniaProperty.Register<SpectrumGraphControl, double>(nameof(SampleRate), 44100.0);

    public double[]? MagnitudesDb
    {
        get => GetValue(MagnitudesDbProperty);
        set => SetValue(MagnitudesDbProperty, value);
    }

    public double SampleRate
    {
        get => GetValue(SampleRateProperty);
        set => SetValue(SampleRateProperty, value);
    }

    private static readonly IBrush BackgroundBrush = new SolidColorBrush(Color.FromRgb(15, 18, 22));
    private static readonly IBrush GridBrush = new SolidColorBrush(Color.FromArgb(40, 255, 255, 255));
    private static readonly IBrush MajorGridBrush = new SolidColorBrush(Color.FromArgb(80, 255, 255, 255));
    private static readonly IBrush TextBrush = new SolidColorBrush(Color.FromRgb(130, 145, 160));
    private static readonly IPen CurvePen = new Pen(new SolidColorBrush(Color.FromRgb(0, 225, 255)), 1.5);
    private static readonly IBrush CurveFillBrush = new LinearGradientBrush
    {
        StartPoint = new RelativePoint(0, 0, RelativeUnit.Relative),
        EndPoint = new RelativePoint(0, 1, RelativeUnit.Relative),
        GradientStops =
        [
            new GradientStop(Color.FromArgb(80, 0, 225, 255), 0.0),
            new GradientStop(Color.FromArgb(5, 0, 100, 200), 1.0)
        ]
    };

    static SpectrumGraphControl()
    {
        AffectsRender<SpectrumGraphControl>(MagnitudesDbProperty, SampleRateProperty);
    }

    public override void Render(DrawingContext context)
    {
        double width = Bounds.Width;
        double height = Bounds.Height;
        if (width < 20 || height < 20)
            return;

        // Background
        context.FillRectangle(BackgroundBrush, new Rect(0, 0, width, height));

        const double minFreq = 20.0;
        double maxFreq = SampleRate > 0 ? SampleRate / 2.0 : 22050.0;
        const double minDb = -120.0;
        const double maxDb = 0.0;

        double logMin = Math.Log10(minFreq);
        double logMax = Math.Log10(maxFreq);
        double logRange = logMax - logMin;

        // Draw horizontal dB grid lines (-100, -80, -60, -40, -20, 0 dB)
        for (double db = -120; db <= 0; db += 20)
        {
            double y = height * (1.0 - (db - minDb) / (maxDb - minDb));
            context.DrawLine(new Pen(db == 0 ? MajorGridBrush : GridBrush, 1), new Point(0, y), new Point(width, y));

            var ft = new FormattedText(
                $"{db:0} dB",
                CultureInfo.InvariantCulture,
                FlowDirection.LeftToRight,
                Typeface.Default,
                10,
                TextBrush);
            context.DrawText(ft, new Point(4, y - 12));
        }

        // Draw vertical frequency grid lines (50, 100, 200, 500, 1k, 2k, 5k, 10k, 20k)
        double[] freqLines = [50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000];
        foreach (double f in freqLines)
        {
            if (f > maxFreq) break;
            double x = width * (Math.Log10(f) - logMin) / logRange;
            context.DrawLine(new Pen(f is 100 or 1000 or 10000 ? MajorGridBrush : GridBrush, 1), new Point(x, 0), new Point(x, height));

            string label = f >= 1000 ? $"{f / 1000:0}k" : $"{f:0}";
            var ft = new FormattedText(
                label,
                CultureInfo.InvariantCulture,
                FlowDirection.LeftToRight,
                Typeface.Default,
                10,
                TextBrush);
            context.DrawText(ft, new Point(x + 2, height - 14));
        }

        // Draw Spectrum Curve
        double[]? mags = MagnitudesDb;
        if (mags == null || mags.Length < 2)
            return;

        int binCount = mags.Length;
        var geometry = new StreamGeometry();
        using (var ctx = geometry.Open())
        {
            bool started = false;
            Point firstPoint = default;
            Point lastPoint = default;

            for (int i = 1; i < binCount; i++)
            {
                double f = i * (SampleRate / 2.0) / binCount;
                if (f < minFreq) continue;
                if (f > maxFreq) break;

                double x = width * (Math.Log10(f) - logMin) / logRange;
                double valDb = Math.Clamp(mags[i], minDb, maxDb);
                double y = height * (1.0 - (valDb - minDb) / (maxDb - minDb));

                var pt = new Point(x, y);
                if (!started)
                {
                    ctx.BeginFigure(pt, isFilled: true);
                    firstPoint = pt;
                    started = true;
                }
                else
                {
                    ctx.LineTo(pt);
                }
                lastPoint = pt;
            }

            if (started)
            {
                // Close bottom for gradient fill
                ctx.LineTo(new Point(lastPoint.X, height));
                ctx.LineTo(new Point(firstPoint.X, height));
                ctx.EndFigure(isClosed: true);
            }
        }

        context.DrawGeometry(CurveFillBrush, CurvePen, geometry);
    }
}
