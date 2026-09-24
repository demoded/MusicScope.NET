using System;
using System.Globalization;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Media;
using Avalonia.Media.Imaging;
using Avalonia.Platform;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// 2D Spectrogram / Waterfall Control directly modeling the bottom row of the MusicScope UI.
/// Maps frequency bins along the X-axis and track playback progression (0% to 100%) along the Y-axis.
/// Uses the classic MusicScope thermal heatmap palette (blue -> purple -> magenta -> orange -> yellow -> white).
/// </summary>
public sealed class WaterfallControl : Control
{
    public static readonly StyledProperty<double[]?> LatestSpectrumProperty =
        AvaloniaProperty.Register<WaterfallControl, double[]?>(nameof(LatestSpectrum));

    public static readonly StyledProperty<double> TrackProgressProperty =
        AvaloniaProperty.Register<WaterfallControl, double>(nameof(TrackProgress), 0.0);

    public double[]? LatestSpectrum
    {
        get => GetValue(LatestSpectrumProperty);
        set => SetValue(LatestSpectrumProperty, value);
    }

    public double TrackProgress
    {
        get => GetValue(TrackProgressProperty);
        set => SetValue(TrackProgressProperty, value);
    }

    private const int BitmapWidth = 512;
    private const int BitmapHeight = 320;
    private readonly WriteableBitmap _bitmap;
    private int _lastRenderedRow = -1;

    // Precomputed 256-color heat palette (ARGB)
    private static readonly uint[] ColorPalette = InitializePalette();

    private static uint[] InitializePalette()
    {
        uint[] pal = new uint[256];
        for (int i = 0; i < 256; i++)
        {
            double t = i / 255.0;
            byte r, g, b;

            if (t < 0.2) // Deep navy to purple
            {
                double u = t / 0.2;
                r = (byte)(u * 80);
                g = 0;
                b = (byte)(40 + u * 120);
            }
            else if (t < 0.45) // Purple to magenta / hot pink
            {
                double u = (t - 0.2) / 0.25;
                r = (byte)(80 + u * 160);
                g = (byte)(u * 20);
                b = (byte)(160 - u * 80);
            }
            else if (t < 0.75) // Hot pink to vivid orange
            {
                double u = (t - 0.45) / 0.30;
                r = 255;
                g = (byte)(20 + u * 140);
                b = (byte)(80 * (1.0 - u));
            }
            else if (t < 0.92) // Orange to bright yellow
            {
                double u = (t - 0.75) / 0.17;
                r = 255;
                g = (byte)(160 + u * 80);
                b = (byte)(u * 40);
            }
            else // Yellow to white
            {
                double u = (t - 0.92) / 0.08;
                r = 255;
                g = (byte)(240 + u * 15);
                b = (byte)(40 + u * 215);
            }

            pal[i] = 0xFF000000 | ((uint)r << 16) | ((uint)g << 8) | b;
        }
        return pal;
    }

    private static readonly IBrush BgBrush = new SolidColorBrush(Color.FromRgb(0, 0, 0));
    private static readonly IBrush AxisTextBrush = new SolidColorBrush(Color.FromRgb(200, 205, 210));
    private static readonly IBrush GreenAnnotationBrush = new SolidColorBrush(Color.FromRgb(0, 220, 0));
    private static readonly IBrush DimAnnotationBrush = new SolidColorBrush(Color.FromRgb(100, 105, 110));

    public WaterfallControl()
    {
        _bitmap = new WriteableBitmap(
            new PixelSize(BitmapWidth, BitmapHeight),
            new Vector(96, 96),
            PixelFormat.Bgra8888,
            AlphaFormat.Opaque);

        ClearBitmap();
    }

    private void ClearBitmap()
    {
        using var fb = _bitmap.Lock();
        unsafe
        {
            uint* ptr = (uint*)fb.Address;
            int total = BitmapWidth * BitmapHeight;
            // Initialize with deep navy quiet background
            uint bgPixel = ColorPalette[0];
            for (int i = 0; i < total; i++)
            {
                ptr[i] = bgPixel;
            }
        }
        _lastRenderedRow = -1;
    }

    static WaterfallControl()
    {
        AffectsRender<WaterfallControl>(LatestSpectrumProperty, TrackProgressProperty);
    }

    /// <summary>
    /// Resets the spectrogram display for a new file.
    /// </summary>
    public void Reset()
    {
        ClearBitmap();
        InvalidateVisual();
    }

    public override void Render(DrawingContext context)
    {
        double width = Bounds.Width;
        double height = Bounds.Height;
        if (width < 30 || height < 30) return;

        var tf = Typeface.Default;
        context.FillRectangle(BgBrush, new Rect(0, 0, width, height));

        double leftMargin = 38.0;
        double plotWidth = width - leftMargin - 10.0;
        double plotHeight = height - 10.0;

        // Render newest row into bitmap based on TrackProgress
        double[]? spectrum = LatestSpectrum;
        if (spectrum != null && spectrum.Length > 0 && TrackProgress >= 0.0)
        {
            int targetRow = Math.Clamp((int)(TrackProgress * (BitmapHeight - 1)), 0, BitmapHeight - 1);
            if (targetRow < _lastRenderedRow)
            {
                ClearBitmap();
            }

            if (targetRow >= _lastRenderedRow)
            {
                using (var fb = _bitmap.Lock())
                {
                    unsafe
                    {
                        uint* ptr = (uint*)fb.Address;
                        int rowStart = Math.Max(0, _lastRenderedRow);
                        for (int r = rowStart; r <= targetRow; r++)
                        {
                            uint* rowPtr = ptr + r * BitmapWidth;
                            for (int c = 0; c < BitmapWidth; c++)
                            {
                                int binIdx = (int)(c * (spectrum.Length / (double)BitmapWidth));
                                double db = spectrum[Math.Clamp(binIdx, 0, spectrum.Length - 1)];
                                // Map -96 dB to 0 dB into 0..255
                                int palIdx = Math.Clamp((int)((db + 96.0) * (255.0 / 96.0)), 0, 255);
                                rowPtr[c] = ColorPalette[palIdx];
                            }
                        }
                    }
                }
                _lastRenderedRow = targetRow;
            }
        }

        // Draw Left Y-Axis labels: %, 0, MAX, 25, BRY, 50, COF, 75, 100
        DrawText(context, "%", tf, 9, AxisTextBrush, 8, 4);

        double y0 = 12;
        double yMax = y0 + plotHeight * 0.12;
        double y25 = y0 + plotHeight * 0.25;
        double yBry = y0 + plotHeight * 0.38;
        double y50 = y0 + plotHeight * 0.50;
        double yCof = y0 + plotHeight * 0.62;
        double y75 = y0 + plotHeight * 0.75;
        double y100 = y0 + plotHeight - 12;

        DrawTextRight(context, "0", tf, 9, AxisTextBrush, leftMargin - 4, y0);
        DrawTextRight(context, "MAX", tf, 9, GreenAnnotationBrush, leftMargin - 4, yMax);
        DrawTextRight(context, "25", tf, 9, AxisTextBrush, leftMargin - 4, y25);
        DrawTextRight(context, "BRY", tf, 9, GreenAnnotationBrush, leftMargin - 4, yBry);
        DrawTextRight(context, "50", tf, 9, AxisTextBrush, leftMargin - 4, y50);
        DrawTextRight(context, "COF", tf, 9, DimAnnotationBrush, leftMargin - 4, yCof);
        DrawTextRight(context, "75", tf, 9, AxisTextBrush, leftMargin - 4, y75);
        DrawTextRight(context, "100", tf, 9, AxisTextBrush, leftMargin - 4, y100);

        // Draw Waterfall Bitmap
        var destRect = new Rect(leftMargin, 6, plotWidth, plotHeight);
        context.DrawImage(_bitmap, destRect);
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
}
