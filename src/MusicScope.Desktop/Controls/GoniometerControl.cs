using System;
using System.Globalization;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Media;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// Phosphor-style Goniometer / Vector Scope with Tri-color Phase Correlation Bar.
/// Directly models Box 5 (Stereo) from the MusicScope UI.
/// </summary>
public sealed class GoniometerControl : Control
{
    public static readonly StyledProperty<double> CorrelationProperty =
        AvaloniaProperty.Register<GoniometerControl, double>(nameof(Correlation), 1.0);

    public static readonly StyledProperty<float[]?> PointsXProperty =
        AvaloniaProperty.Register<GoniometerControl, float[]?>(nameof(PointsX));

    public static readonly StyledProperty<float[]?> PointsYProperty =
        AvaloniaProperty.Register<GoniometerControl, float[]?>(nameof(PointsY));

    public double Correlation
    {
        get => GetValue(CorrelationProperty);
        set => SetValue(CorrelationProperty, value);
    }

    public float[]? PointsX
    {
        get => GetValue(PointsXProperty);
        set => SetValue(PointsXProperty, value);
    }

    public float[]? PointsY
    {
        get => GetValue(PointsYProperty);
        set => SetValue(PointsYProperty, value);
    }

    private static readonly IBrush HeaderBrush = new SolidColorBrush(Color.FromRgb(0, 220, 0)); // Green Stereo
    private static readonly IBrush CornerLabelBrush = new SolidColorBrush(Color.FromRgb(220, 220, 220));
    private static readonly IBrush OutOfPhaseBrush = new SolidColorBrush(Color.FromRgb(55, 60, 65));
    private static readonly IBrush PhosphorGlowBrush = new SolidColorBrush(Color.FromArgb(140, 0, 255, 68));
    private static readonly IBrush PhosphorCoreBrush = new SolidColorBrush(Color.FromArgb(220, 20, 255, 100));
    private static readonly IPen AxisPen = new Pen(new SolidColorBrush(Color.FromRgb(45, 50, 55)), 1);

    // Correlation Bar Brushes
    private static readonly IBrush CorrRedBrush = new SolidColorBrush(Color.FromRgb(187, 0, 0));
    private static readonly IBrush CorrYellowBrush = new SolidColorBrush(Color.FromRgb(221, 221, 0));
    private static readonly IBrush CorrGreenBrush = new SolidColorBrush(Color.FromRgb(0, 153, 0));
    private static readonly IPen CursorPen = new Pen(new SolidColorBrush(Color.FromRgb(255, 255, 255)), 2);

    static GoniometerControl()
    {
        AffectsRender<GoniometerControl>(CorrelationProperty, PointsXProperty, PointsYProperty);
    }

    public override void Render(DrawingContext context)
    {
        double width = Bounds.Width;
        double height = Bounds.Height;
        if (width < 30 || height < 30) return;

        var tf = Typeface.Default;

        // Header: Stereo
        DrawText(context, "Stereo", tf, 11, HeaderBrush, 8, 8);

        // Scope Area
        double barAreaHeight = 32;
        double scopeTop = 22;
        double scopeBottom = height - barAreaHeight;
        double scopeHeight = scopeBottom - scopeTop;
        double centerX = width / 2.0;
        double centerY = scopeTop + scopeHeight / 2.0;
        double radius = Math.Min(centerX - 16, scopeHeight / 2.0 - 10);

        if (radius > 15)
        {
            // Corner Labels: +L (top-left), +R (top-right), -R (bottom-left), -L (bottom-right)
            DrawText(context, "+L", tf, 10, CornerLabelBrush, 8, scopeTop + 4);
            DrawTextRight(context, "+R", tf, 10, CornerLabelBrush, width - 8, scopeTop + 4);
            DrawText(context, "-R", tf, 10, CornerLabelBrush, 8, scopeBottom - 14);
            DrawTextRight(context, "-L", tf, 10, CornerLabelBrush, width - 8, scopeBottom - 14);

            // "out of phase" dim labels
            DrawText(context, "out of\nphase", tf, 9, OutOfPhaseBrush, 8, centerY - 10);
            DrawTextRight(context, "out of\nphase", tf, 9, OutOfPhaseBrush, width - 8, centerY - 10);

            // Diagonal crosshair axes
            double d = radius * 0.95;
            context.DrawLine(AxisPen, new Point(centerX - d, centerY - d), new Point(centerX + d, centerY + d));
            context.DrawLine(AxisPen, new Point(centerX - d, centerY + d), new Point(centerX + d, centerY - d));

            // Draw Phosphor Cloud (Lissajous stereo field)
            float[]? px = PointsX;
            float[]? py = PointsY;
            if (px != null && py != null && px.Length > 0 && py.Length > 0)
            {
                int count = Math.Min(px.Length, py.Length);
                for (int i = 0; i < count; i++)
                {
                    double x = centerX + px[i] * radius;
                    double y = centerY - py[i] * radius;
                    context.DrawRectangle(PhosphorGlowBrush, null, new Rect(x - 1, y - 1, 3, 3));
                    context.DrawRectangle(PhosphorCoreBrush, null, new Rect(x, y, 1.2, 1.2));
                }
            }
        }

        // Tri-Color Phase Correlation Bar at bottom
        double barY = height - 20;
        double barLeft = 14;
        double barRight = width - 14;
        double barWidth = barRight - barLeft;
        double barH = 7;

        // Segments: -1 to -0.2 (Red, 40%), -0.2 to +0.2 (Yellow, 20%), +0.2 to +1.0 (Green, 40%)
        double redW = barWidth * 0.40;
        double yellowW = barWidth * 0.20;
        double greenW = barWidth * 0.40;

        context.FillRectangle(CorrRedBrush, new Rect(barLeft, barY, redW, barH));
        context.FillRectangle(CorrYellowBrush, new Rect(barLeft + redW, barY, yellowW, barH));
        context.FillRectangle(CorrGreenBrush, new Rect(barLeft + redW + yellowW, barY, greenW, barH));

        // Correlation Cursor Tick
        double clampedCorr = Math.Clamp(Correlation, -1.0, 1.0);
        double cursorX = barLeft + ((clampedCorr + 1.0) / 2.0) * barWidth;
        context.DrawLine(CursorPen, new Point(cursorX, barY - 4), new Point(cursorX, barY + barH + 4));

        // Labels: -1 (Red), 0 (Yellow), +1 (Green)
        DrawText(context, "-1", tf, 9, CorrRedBrush, barLeft, barY + barH + 2);
        DrawCenteredText(context, "0", tf, 9, CorrYellowBrush, barLeft + redW + yellowW * 0.5, barY + barH + 2);
        DrawTextRight(context, "+1", tf, 9, CorrGreenBrush, barRight, barY + barH + 2);
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
