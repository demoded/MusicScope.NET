using System;
using System.Globalization;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Media;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// Circular phosphor-style Goniometer / Vector Scope with real-time Phase Correlation Bar.
/// Directly models the StereoMeterControl from MusicScope.
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

    private static readonly IBrush BgBrush = new SolidColorBrush(Color.FromRgb(15, 18, 22));
    private static readonly IBrush ReticleBrush = new SolidColorBrush(Color.FromArgb(40, 255, 255, 255));
    private static readonly IBrush PhosphorBrush = new SolidColorBrush(Color.FromArgb(180, 0, 255, 170));
    private static readonly IPen PhosphorPen = new Pen(new SolidColorBrush(Color.FromArgb(200, 0, 255, 170)), 1.2);
    private static readonly IBrush LabelBrush = new SolidColorBrush(Color.FromRgb(130, 145, 160));

    static GoniometerControl()
    {
        AffectsRender<GoniometerControl>(CorrelationProperty, PointsXProperty, PointsYProperty);
    }

    public override void Render(DrawingContext context)
    {
        double width = Bounds.Width;
        double height = Bounds.Height;
        if (width < 30 || height < 30)
            return;

        context.FillRectangle(BgBrush, new Rect(0, 0, width, height));

        // Reserve bottom 28 pixels for the correlation meter bar
        double scopeHeight = Math.Max(20, height - 28);
        double centerX = width / 2.0;
        double centerY = scopeHeight / 2.0;
        double radius = Math.Min(centerX, centerY) - 8;

        if (radius > 10)
        {
            // Draw circular reticle
            var reticlePen = new Pen(ReticleBrush, 1);
            context.DrawEllipse(null, reticlePen, new Point(centerX, centerY), radius, radius);
            context.DrawEllipse(null, reticlePen, new Point(centerX, centerY), radius * 0.5, radius * 0.5);

            // Diagonal axes: +45 deg (L) and -45 deg (R)
            double diagOffset = radius * 0.70710678;
            context.DrawLine(reticlePen, new Point(centerX - diagOffset, centerY + diagOffset), new Point(centerX + diagOffset, centerY - diagOffset));
            context.DrawLine(reticlePen, new Point(centerX - diagOffset, centerY - diagOffset), new Point(centerX + diagOffset, centerY + diagOffset));

            // Axis labels: +M (top), -M (bottom), +S (right), -S (left)
            var font = Typeface.Default;
            DrawCenteredText(context, "+M", font, 9, LabelBrush, centerX, centerY - radius + 4);
            DrawCenteredText(context, "-M", font, 9, LabelBrush, centerX, centerY + radius - 12);
            DrawCenteredText(context, "+S", font, 9, LabelBrush, centerX + radius - 14, centerY - 6);
            DrawCenteredText(context, "-S", font, 9, LabelBrush, centerX - radius + 4, centerY - 6);

            // Draw vector scope trace points
            float[]? px = PointsX;
            float[]? py = PointsY;
            if (px != null && py != null && px.Length > 0 && py.Length > 0)
            {
                int count = Math.Min(px.Length, py.Length);
                for (int i = 0; i < count; i++)
                {
                    double x = centerX + px[i] * radius;
                    double y = centerY - py[i] * radius;
                    context.DrawRectangle(PhosphorBrush, null, new Rect(x, y, 1.5, 1.5));
                }
            }
        }

        // Draw Phase Correlation Bar at bottom
        double barY = height - 22;
        double barWidth = width - 40;
        double barLeft = 20;

        // Background groove
        context.FillRectangle(new SolidColorBrush(Color.FromRgb(30, 35, 42)), new Rect(barLeft, barY, barWidth, 10));

        // Center line (0.0 correlation)
        double centerBarX = barLeft + barWidth / 2.0;
        context.DrawLine(new Pen(ReticleBrush, 1), new Point(centerBarX, barY - 2), new Point(centerBarX, barY + 12));

        // Correlation indicator
        double clampedCorr = Math.Clamp(Correlation, -1.0, 1.0);
        double markerX = barLeft + ((clampedCorr + 1.0) / 2.0) * barWidth;

        Color corrColor = clampedCorr switch
        {
            > 0.3 => Color.FromRgb(0, 230, 120),  // Green (good in-phase mono compatibility)
            > 0.0 => Color.FromRgb(240, 200, 50),  // Yellow (acceptable stereo spread)
            _ => Color.FromRgb(255, 60, 60)        // Red (anti-phase cancellation risk)
        };

        context.FillRectangle(new SolidColorBrush(corrColor), new Rect(Math.Min(centerBarX, markerX), barY, Math.Abs(markerX - centerBarX) + 1, 10));

        // Correlation labels: -1, 0, +1
        DrawCenteredText(context, "-1", Typeface.Default, 8, LabelBrush, barLeft, barY + 11);
        DrawCenteredText(context, "0", Typeface.Default, 8, LabelBrush, centerBarX, barY + 11);
        DrawCenteredText(context, "+1", Typeface.Default, 8, LabelBrush, barLeft + barWidth, barY + 11);
    }

    private static void DrawCenteredText(DrawingContext ctx, string text, Typeface tf, double size, IBrush brush, double x, double y)
    {
        var ft = new FormattedText(text, CultureInfo.InvariantCulture, FlowDirection.LeftToRight, tf, size, brush);
        ctx.DrawText(ft, new Point(x - ft.Width / 2.0, y));
    }
}
