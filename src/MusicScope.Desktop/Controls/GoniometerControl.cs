using System;
using System.Globalization;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Media;
using Avalonia.Media.Imaging;
using Avalonia.Platform;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// Phosphor-style Goniometer / Vector Scope with Tri-color Phase Correlation Bar.
/// Directly models Box 5 (Stereo) from the MusicScope UI, featuring real-time 5000-point
/// high-resolution phosphor decay trace and the cumulative 2D stereo phosphor density cloud.
/// </summary>
public sealed class GoniometerControl : Control
{
    public static readonly StyledProperty<double> CorrelationProperty =
        AvaloniaProperty.Register<GoniometerControl, double>(nameof(Correlation), 1.0);

    public static readonly StyledProperty<float[]?> PointsXProperty =
        AvaloniaProperty.Register<GoniometerControl, float[]?>(nameof(PointsX));

    public static readonly StyledProperty<float[]?> PointsYProperty =
        AvaloniaProperty.Register<GoniometerControl, float[]?>(nameof(PointsY));

    public static readonly StyledProperty<byte[]?> DensityCloudProperty =
        AvaloniaProperty.Register<GoniometerControl, byte[]?>(nameof(DensityCloud));

    public static readonly StyledProperty<double> TrackProgressProperty =
        AvaloniaProperty.Register<GoniometerControl, double>(nameof(TrackProgress), 0.0);

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

    public byte[]? DensityCloud
    {
        get => GetValue(DensityCloudProperty);
        set => SetValue(DensityCloudProperty, value);
    }

    public double TrackProgress
    {
        get => GetValue(TrackProgressProperty);
        set => SetValue(TrackProgressProperty, value);
    }

    private static readonly IBrush HeaderBrush = new SolidColorBrush(Color.FromRgb(0, 220, 0)); // Green Stereo
    private static readonly IBrush CornerLabelBrush = new SolidColorBrush(Color.FromRgb(220, 220, 220));
    private static readonly IBrush OutOfPhaseBrush = new SolidColorBrush(Color.FromRgb(55, 60, 65));
    private static readonly IPen AxisPen = new Pen(new SolidColorBrush(Color.FromRgb(45, 50, 55)), 1);

    // Phosphor decay palette (16 brightness shades from dark phosphor decay to neon core)
    private static readonly IBrush[] PhosphorPalette = InitializePhosphorPalette();
    private static readonly IPen[] PhosphorPens = InitializePhosphorPens();

    private static IBrush[] InitializePhosphorPalette()
    {
        var palette = new IBrush[16];
        for (int i = 0; i < 16; i++)
        {
            double t = (i + 1) / 16.0;
            byte r = (byte)(15 * t);
            byte g = (byte)(40 + 215 * t);
            byte b = (byte)(35 * t);
            palette[i] = new SolidColorBrush(Color.FromRgb(r, g, b));
        }
        return palette;
    }

    private static IPen[] InitializePhosphorPens()
    {
        var pens = new IPen[16];
        for (int i = 0; i < 16; i++)
        {
            double t = (i + 1) / 16.0;
            byte r = (byte)(15 * t);
            byte g = (byte)(40 + 215 * t);
            byte b = (byte)(35 * t);
            pens[i] = new Pen(new SolidColorBrush(Color.FromRgb(r, g, b)), 1);
        }
        return pens;
    }

    // Correlation Bar Brushes
    private static readonly IBrush CorrRedBrush = new SolidColorBrush(Color.FromRgb(187, 0, 0));
    private static readonly IBrush CorrYellowBrush = new SolidColorBrush(Color.FromRgb(221, 221, 0));
    private static readonly IBrush CorrGreenBrush = new SolidColorBrush(Color.FromRgb(0, 153, 0));
    private static readonly IPen CursorPen = new Pen(new SolidColorBrush(Color.FromRgb(255, 255, 255)), 2);

    private WriteableBitmap? _cloudBitmap;
    private byte[]? _cachedDensityCloud;
    private readonly double[] _corrHistory = new double[100];
    private int _corrHistoryCount = 0;

    static GoniometerControl()
    {
        AffectsRender<GoniometerControl>(CorrelationProperty, PointsXProperty, PointsYProperty, DensityCloudProperty, TrackProgressProperty);
        CorrelationProperty.Changed.AddClassHandler<GoniometerControl>((ctrl, e) =>
        {
            if (e.NewValue is double val)
            {
                ctrl.PushCorrelationHistory(val);
            }
        });
    }

    private void PushCorrelationHistory(double val)
    {
        if (_corrHistoryCount < _corrHistory.Length)
        {
            _corrHistory[_corrHistoryCount++] = val;
        }
        else
        {
            Array.Copy(_corrHistory, 1, _corrHistory, 0, _corrHistory.Length - 1);
            _corrHistory[^1] = val;
        }
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

            // Draw Phosphor Cloud (Cumulative 2D Density Cloud or Live 5000 Lissajous points)
            byte[]? cloud = DensityCloud;
            float[]? px = PointsX;
            float[]? py = PointsY;

            if (cloud == null)
            {
                _cachedDensityCloud = null;
                _cloudBitmap = null;
            }

            if (cloud != null && cloud.Length == 256 * 256 && (TrackProgress >= 1.0 || px == null || px.Length == 0))
            {
                // Final cumulative green phosphor cloud
                if (_cloudBitmap == null || _cachedDensityCloud != cloud)
                {
                    _cloudBitmap ??= new WriteableBitmap(
                        new PixelSize(256, 256),
                        new Vector(96, 96),
                        PixelFormat.Bgra8888,
                        AlphaFormat.Premul);

                    using (var buf = _cloudBitmap.Lock())
                    {
                        unsafe
                        {
                            uint* ptr = (uint*)buf.Address;
                            for (int i = 0; i < 256 * 256; i++)
                            {
                                byte g = cloud[i];
                                ptr[i] = g > 0 ? (0xFF000000u | ((uint)g << 8)) : 0u;
                            }
                        }
                    }
                    _cachedDensityCloud = cloud;
                }

                Rect destRect = new Rect(centerX - radius, centerY - radius, radius * 2, radius * 2);
                context.DrawImage(_cloudBitmap, destRect);
            }
            else if (px != null && py != null && px.Length > 0 && py.Length > 0)
            {
                // High-resolution live oscilloscope trace (5000 points with phosphor decay)
                int count = Math.Min(px.Length, py.Length);
                double scale = radius / 0.77815;

                for (int i = 0; i < count; i++)
                {
                    double x = centerX + px[i] * scale;
                    double y = centerY + py[i] * scale;

                    int paletteIdx = (i * 16) / count;
                    var brush = PhosphorPalette[paletteIdx];
                    context.DrawRectangle(brush, null, new Rect(x - 0.75, y - 0.75, 1.5, 1.5));
                }
            }
        }

        // Tri-Color Phase Correlation Bar at bottom
        double barY = height - 22;
        double barLeft = 14;
        double barRight = width - 14;
        double barWidth = barRight - barLeft;
        double barH = 8;

        // Segments: -1 to -0.19 (Red, 40.5%), -0.19 to +0.20 (Yellow, 19.5%), +0.20 to +1.0 (Green, 40%)
        double redW = barWidth * 0.405;
        double yellowW = barWidth * 0.195;
        double greenW = barWidth * 0.40;

        context.FillRectangle(CorrRedBrush, new Rect(barLeft, barY, redW, barH));
        context.FillRectangle(CorrYellowBrush, new Rect(barLeft + redW, barY, yellowW, barH));
        context.FillRectangle(CorrGreenBrush, new Rect(barLeft + redW + yellowW, barY, greenW, barH));

        // Correlation Cursor on the bar:
        double clampedCorr = Math.Clamp(Correlation, -1.0, 1.0);
        double cursorX = barLeft + ((clampedCorr + 1.0) / 2.0) * barWidth;
        context.DrawLine(CursorPen, new Point(cursorX, barY - 2), new Point(cursorX, barY + barH + 2));

        // Green Phosphor Correlation comb / indicator above the bar (height 10px, barY - 12 to barY - 3)
        double combTop = barY - 12;
        double combBottom = barY - 3;
        if (TrackProgress >= 1.0 || _corrHistoryCount == 0)
        {
            // Final single green vertical tick line at final correlation
            context.DrawLine(PhosphorPens[^1], new Point(cursorX, combTop), new Point(cursorX, combBottom));
        }
        else
        {
            // Live correlation history comb
            for (int i = 0; i < _corrHistoryCount; i++)
            {
                int pIdx = (i * 16) / Math.Max(1, _corrHistoryCount);
                var pen = PhosphorPens[Math.Clamp(pIdx, 0, 15)];
                double cX = barLeft + ((Math.Clamp(_corrHistory[i], -1.0, 1.0) + 1.0) / 2.0) * barWidth;
                context.DrawLine(pen, new Point(cX, combTop), new Point(cX, combBottom));
            }
        }

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
