using System;
using System.Globalization;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Media;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// Linear Frequency Spectrum Control directly modeling the middle row of the MusicScope UI.
/// Displays dB scale (0, -6, -12, -24, -40, -60, -96), glowing amber frequency curve,
/// linear frequency axis with Nyquist ticks, and switches (Left/Right, Pano/Phase, -200dB Mode).
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

    private static readonly IBrush BgBrush = new SolidColorBrush(Color.FromRgb(0, 0, 0)); // Pure black
    private static readonly IBrush GridBrush = new SolidColorBrush(Color.FromRgb(35, 40, 45)); // Dim grid line
    private static readonly IBrush TextBrush = new SolidColorBrush(Color.FromRgb(200, 205, 210)); // Grid dB text
    private static readonly IBrush GreenHeaderBrush = new SolidColorBrush(Color.FromRgb(0, 220, 0)); // Green "Linear Frequency Spectrum [kHz]"
    private static readonly IBrush SwitchTextBrush = new SolidColorBrush(Color.FromRgb(120, 125, 130)); // Switch button text
    private static readonly IPen SpectrumPen = new Pen(new SolidColorBrush(Color.FromRgb(255, 176, 0)), 1.2); // Amber curve
    private static readonly IPen SpectrumGlowPen = new Pen(new SolidColorBrush(Color.FromArgb(90, 255, 160, 0)), 2.5);

    static SpectrumGraphControl()
    {
        AffectsRender<SpectrumGraphControl>(MagnitudesDbProperty, SampleRateProperty);
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
            // Segmented/non-linear dB mapping to give high resolution near peak levels:
            // 0 dB -> topY, -96 dB -> bottomAxisY
            double clamped = Math.Clamp(db, -96.0, 0.0);
            double norm = Math.Pow(-clamped / 96.0, 0.7); // slight expansion near 0 dB
            return topY + norm * plotHeight;
        }

        // Draw "dB" header
        DrawText(context, "dB", tf, 10, TextBrush, 8, topY - 12);

        // Draw horizontal grid lines & labels
        for (int i = 0; i < dbMarks.Length; i++)
        {
            double db = dbMarks[i];
            double y = DbToY(db);

            context.DrawLine(new Pen(GridBrush, 1), new Point(leftMargin, y), new Point(leftMargin + plotWidth, y));
            DrawTextRight(context, db.ToString("F0"), tf, 10, TextBrush, leftMargin - 4, y - 6);
        }

        // Bottom Axis Labels
        // "Linear Frequency Spectrum [kHz]" in green
        DrawText(context, "Linear Frequency Spectrum [kHz]", tf, 10, GreenHeaderBrush, leftMargin, bottomAxisY + 4);

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

        DrawCenteredText(context, f1.ToString("F2", CultureInfo.InvariantCulture), tf, 10, TextBrush, x1, bottomAxisY + 4);
        DrawCenteredText(context, f2.ToString("F2", CultureInfo.InvariantCulture), tf, 10, TextBrush, x2, bottomAxisY + 4);
        DrawCenteredText(context, f3.ToString("F2", CultureInfo.InvariantCulture), tf, 10, TextBrush, x3, bottomAxisY + 4);
        DrawTextRight(context, f4.ToString("F2", CultureInfo.InvariantCulture), tf, 10, TextBrush, x4, bottomAxisY + 4);

        // Switch Buttons / Annotations
        DrawCenteredText(context, "Left/Right", tf, 9, SwitchTextBrush, leftMargin + plotWidth * 0.33, bottomAxisY + 4);
        DrawCenteredText(context, "Pano/Phase", tf, 9, SwitchTextBrush, leftMargin + plotWidth * 0.42, bottomAxisY + 4);
        DrawCenteredText(context, "-200dB Mode", tf, 9, SwitchTextBrush, leftMargin + plotWidth * 0.60, bottomAxisY + 4);

        // Draw Frequency Spectrum Line
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
                context.DrawLine(SpectrumGlowPen, prevPt.Value, pt);
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
