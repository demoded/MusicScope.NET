using System;
using System.Globalization;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Media;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// Linear Frequency Spectrum Control directly modeling the middle row of the MusicScope UI.
/// Displays dB scale (0, -6, -12, -24, -40, -60, -96), cumulative peak hold curve (#FFBF00),
/// live instantaneous green level bars, bright green baseline, Nyquist ticks, and switch buttons.
/// </summary>
public sealed class SpectrumGraphControl : Control
{
    public static readonly StyledProperty<double[]?> MagnitudesDbProperty =
        AvaloniaProperty.Register<SpectrumGraphControl, double[]?>(nameof(MagnitudesDb));

    public static readonly StyledProperty<double[]?> InstantMagnitudesDbProperty =
        AvaloniaProperty.Register<SpectrumGraphControl, double[]?>(nameof(InstantMagnitudesDb));

    public static readonly StyledProperty<double> SampleRateProperty =
        AvaloniaProperty.Register<SpectrumGraphControl, double>(nameof(SampleRate), 44100.0);

    public double[]? MagnitudesDb
    {
        get => GetValue(MagnitudesDbProperty);
        set => SetValue(MagnitudesDbProperty, value);
    }

    public double[]? InstantMagnitudesDb
    {
        get => GetValue(InstantMagnitudesDbProperty);
        set => SetValue(InstantMagnitudesDbProperty, value);
    }

    public double SampleRate
    {
        get => GetValue(SampleRateProperty);
        set => SetValue(SampleRateProperty, value);
    }

    private static readonly IBrush BgBrush = new SolidColorBrush(Color.FromRgb(0, 0, 0)); // Pure black
    private static readonly IBrush GridBrush = new SolidColorBrush(Color.FromRgb(35, 40, 45)); // Dim grid line (#333333)
    private static readonly IBrush TextBrush = new SolidColorBrush(Color.FromRgb(220, 220, 220)); // Grid dB text
    private static readonly IBrush GreenHeaderBrush = new SolidColorBrush(Color.FromRgb(0, 255, 0)); // Neon green #00FF00
    private static readonly IBrush SwitchTextBrush = new SolidColorBrush(Color.FromRgb(102, 102, 102)); // Switch button text #666666
    private static readonly IPen GreenBaselinePen = new Pen(new SolidColorBrush(Color.FromRgb(0, 255, 0)), 2.0); // Bright green baseline #00FF00
    private static readonly IPen GreenTickPen = new Pen(new SolidColorBrush(Color.FromRgb(0, 255, 0)), 1.5);
    private static readonly IPen SpectrumPen = new Pen(new SolidColorBrush(Color.FromRgb(255, 191, 0)), 1.0); // Iconic Amber #FFBF00
    private static readonly IPen InstantGreenPen = new Pen(new SolidColorBrush(Color.FromRgb(0, 160, 40)), 1.0); // Dim green live bounce

    static SpectrumGraphControl()
    {
        AffectsRender<SpectrumGraphControl>(MagnitudesDbProperty, InstantMagnitudesDbProperty, SampleRateProperty);
    }

    public override void Render(DrawingContext context)
    {
        double width = Bounds.Width;
        double height = Bounds.Height;
        if (width < 30 || height < 30) return;

        var tf = Typeface.Default;
        context.FillRectangle(BgBrush, new Rect(0, 0, width, height));

        double topY = 16.0;
        double bottomAxisY = height - 20.0;
        double plotHeight = bottomAxisY - topY;
        double leftMargin = 38.0;
        double plotWidth = width - leftMargin - 10.0;

        // dB scale levels: 0, -6, -12, -24, -40, -60, -96
        double[] dbMarks = [0.0, -6.0, -12.0, -24.0, -40.0, -60.0, -96.0];

        double DbToY(double db)
        {
            // Exact formula from MusicScope SpectrumControl.java:
            // y = 250 - BufferedAlacReader * log10(d * AlacMetaDataModel + 1.0)
            double clamped = Math.Clamp(db, -96.0, 0.0);
            double d = Math.Pow(10.0, clamped / 20.0);
            double norm = Math.Log10(d * 3000.0 + 1.0) / Math.Log10(3001.0);
            return bottomAxisY - norm * plotHeight;
        }

        // Draw "dB" header
        DrawText(context, "dB", tf, 10, SwitchTextBrush, 8, topY - 12);

        // Draw horizontal grid lines & labels
        for (int i = 0; i < dbMarks.Length; i++)
        {
            double db = dbMarks[i];
            double y = DbToY(db);

            context.DrawLine(new Pen(GridBrush, 1), new Point(leftMargin, y), new Point(leftMargin + plotWidth, y));
            DrawTextRight(context, db.ToString("F0"), tf, 10, TextBrush, leftMargin - 6, y - 6);
        }

        // Draw Bright Green Baseline across bottom axis
        context.DrawLine(GreenBaselinePen, new Point(leftMargin, bottomAxisY), new Point(leftMargin + plotWidth, bottomAxisY));

        // Frequency Ticks: fs/8, fs/4, 3fs/8, fs/2 (Nyquist)
        double nyquistKhz = (SampleRate > 0 ? SampleRate : 44100.0) / 2000.0;
        double f1 = nyquistKhz * 0.25;
        double f2 = nyquistKhz * 0.50;
        double f3 = nyquistKhz * 0.75;
        double f4 = nyquistKhz;

        double x1 = leftMargin + plotWidth * 0.25;
        double x2 = leftMargin + plotWidth * 0.50;
        double x3 = leftMargin + plotWidth * 0.75;
        double x4 = leftMargin + plotWidth;

        // Draw tick marks extending below the green baseline
        context.DrawLine(GreenTickPen, new Point(x1, bottomAxisY), new Point(x1, bottomAxisY + 5));
        context.DrawLine(GreenTickPen, new Point(x2, bottomAxisY), new Point(x2, bottomAxisY + 5));
        context.DrawLine(GreenTickPen, new Point(x3, bottomAxisY), new Point(x3, bottomAxisY + 5));
        context.DrawLine(GreenTickPen, new Point(x4, bottomAxisY), new Point(x4, bottomAxisY + 5));

        // Bottom Axis Labels
        // "Linear Frequency Spectrum [kHz]" in green #00FF00
        DrawText(context, "Linear Frequency Spectrum [kHz]", tf, 10, GreenHeaderBrush, leftMargin, bottomAxisY + 6);

        DrawCenteredText(context, f1.ToString("F2", CultureInfo.InvariantCulture), tf, 10, TextBrush, x1, bottomAxisY + 6);
        DrawCenteredText(context, f2.ToString("F2", CultureInfo.InvariantCulture), tf, 10, TextBrush, x2, bottomAxisY + 6);
        DrawCenteredText(context, f3.ToString("F2", CultureInfo.InvariantCulture), tf, 10, TextBrush, x3, bottomAxisY + 6);
        DrawTextRight(context, f4.ToString("F2", CultureInfo.InvariantCulture), tf, 10, TextBrush, x4, bottomAxisY + 6);

        // Switch Buttons / Annotations in gray
        DrawCenteredText(context, "Left/Right", tf, 9, SwitchTextBrush, leftMargin + plotWidth * 0.33, bottomAxisY + 6);
        DrawCenteredText(context, "Pano/Phase", tf, 9, SwitchTextBrush, leftMargin + plotWidth * 0.43, bottomAxisY + 6);
        DrawCenteredText(context, "-200dB Mode", tf, 9, SwitchTextBrush, leftMargin + plotWidth * 0.62, bottomAxisY + 6);

        // 1. Draw Instantaneous Green Spectrum bars (if available during live playback)
        double[]? instant = InstantMagnitudesDb;
        if (instant != null && instant.Length > 0)
        {
            int instBins = instant.Length;
            for (int i = 0; i < instBins; i++)
            {
                double x = leftMargin + (i / (double)(instBins - 1)) * plotWidth;
                double valDb = instant[i];
                if (valDb > -96.0)
                {
                    double y = DbToY(valDb);
                    context.DrawLine(InstantGreenPen, new Point(x, bottomAxisY), new Point(x, y));
                }
            }
        }

        // 2. Draw Cumulative Peak Hold Curve in iconic Amber (#FFBF00)
        double[]? mags = MagnitudesDb;
        if (mags == null || mags.Length < 4) return;

        int binCount = mags.Length;
        Point? prevPt = null;

        for (int i = 0; i < binCount; i++)
        {
            double x = leftMargin + (i / (double)(binCount - 1)) * plotWidth;
            double valDb = mags[i];
            double y = DbToY(valDb);

            var pt = new Point(x, y);
            if (prevPt.HasValue)
            {
                context.DrawLine(SpectrumPen, prevPt.Value, pt);
            }
            prevPt = pt;
        }
    }

    private static void DrawText(DrawingContext ctx, string text, Typeface tf, double size, IBrush brush, double x, double y)
    {
        var ft = new FormattedText(text, CultureInfo.InvariantCulture, FlowDirection.LeftToRight, tf, size, brush);
        ctx.DrawText(ft, new Point(x, y));
    }

    private static void DrawTextRight(DrawingContext ctx, string text, Typeface tf, double size, IBrush brush, double rightX, double y)
    {
        var ft = new FormattedText(text, CultureInfo.InvariantCulture, FlowDirection.LeftToRight, tf, size, brush);
        ctx.DrawText(ft, new Point(rightX - ft.Width, y));
    }

    private static void DrawCenteredText(DrawingContext ctx, string text, Typeface tf, double size, IBrush brush, double x, double y)
    {
        var ft = new FormattedText(text, CultureInfo.InvariantCulture, FlowDirection.LeftToRight, tf, size, brush);
        ctx.DrawText(ft, new Point(x - ft.Width / 2.0, y));
    }
}
