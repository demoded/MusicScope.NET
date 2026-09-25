using System;
using System.Globalization;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Input;
using Avalonia.Media;
using Avalonia.Media.Imaging;
using Avalonia.Platform;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// 2D Spectrogram / Waterfall Control directly modeling the bottom row of the MusicScope UI.
/// Maps frequency bins along the X-axis and track playback progression (0% to 100%) along the Y-axis.
/// Supports clickable buttons in the left axis:
///  - MAX / AVG / MIN: switches magnitude aggregation mode
///  - BRY / MON / COL: switches heatmap colormap (Bright Red-Yellow / Monochrome Green / Rainbow Color)
///  - COF: toggles Cut-Off Frequency line overlay
/// </summary>
public sealed class WaterfallControl : Control
{
    public static readonly StyledProperty<double[]?> LatestSpectrumProperty =
        AvaloniaProperty.Register<WaterfallControl, double[]?>(nameof(LatestSpectrum));

    public static readonly StyledProperty<double> TrackProgressProperty =
        AvaloniaProperty.Register<WaterfallControl, double>(nameof(TrackProgress), 0.0);

    public static readonly StyledProperty<double> SampleRateProperty =
        AvaloniaProperty.Register<WaterfallControl, double>(nameof(SampleRate), 44100.0);

    public static readonly StyledProperty<double> CutoffFrequencyHzProperty =
        AvaloniaProperty.Register<WaterfallControl, double>(nameof(CutoffFrequencyHz), 0.0);

    public static readonly StyledProperty<int> AggregationModeProperty =
        AvaloniaProperty.Register<WaterfallControl, int>(nameof(AggregationMode), 0); // 0 = MAX, 1 = AVG, 2 = MIN

    public static readonly StyledProperty<int> ColormapModeProperty =
        AvaloniaProperty.Register<WaterfallControl, int>(nameof(ColormapMode), 2); // 0 = MON, 1 = COL, 2 = BRY

    public static readonly StyledProperty<bool> ShowCutOffFrequencyProperty =
        AvaloniaProperty.Register<WaterfallControl, bool>(nameof(ShowCutOffFrequency), false);

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

    public double SampleRate
    {
        get => GetValue(SampleRateProperty);
        set => SetValue(SampleRateProperty, value);
    }

    public double CutoffFrequencyHz
    {
        get => GetValue(CutoffFrequencyHzProperty);
        set => SetValue(CutoffFrequencyHzProperty, value);
    }

    public int AggregationMode
    {
        get => GetValue(AggregationModeProperty);
        set => SetValue(AggregationModeProperty, value);
    }

    public int ColormapMode
    {
        get => GetValue(ColormapModeProperty);
        set => SetValue(ColormapModeProperty, value);
    }

    public bool ShowCutOffFrequency
    {
        get => GetValue(ShowCutOffFrequencyProperty);
        set => SetValue(ShowCutOffFrequencyProperty, value);
    }

    private const int BitmapWidth = 512;
    private const int BitmapHeight = 250;
    private readonly WriteableBitmap _bitmap;
    private int _lastRenderedRow = -1;

    // Multi-mode row buffers for MAX, AVG, MIN
    private readonly float[,] _rowMaxDb = new float[BitmapHeight, BitmapWidth];
    private readonly float[,] _rowAvgDb = new float[BitmapHeight, BitmapWidth];
    private readonly float[,] _rowMinDb = new float[BitmapHeight, BitmapWidth];
    private readonly int[] _rowCount = new int[BitmapHeight];

    // Precomputed 256-color palettes (ARGB)
    private static readonly uint[] PaletteBry = InitializeBryPalette();
    private static readonly uint[] PaletteCol = InitializeColPalette();
    private static readonly uint[] PaletteMon = InitializeMonPalette();

    private static uint[] InitializeBryPalette()
    {
        uint[] pal = new uint[256];
        for (int i = 0; i < 256; i++)
        {
            double t = i / 255.0;
            byte r, g, b;

            if (t < 0.20) // Deep navy to purple
            {
                double u = t / 0.20;
                r = (byte)(u * 80);
                g = 0;
                b = (byte)(40 + u * 120);
            }
            else if (t < 0.45) // Purple to magenta / hot pink
            {
                double u = (t - 0.20) / 0.25;
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

    private static uint[] InitializeColPalette()
    {
        uint[] pal = new uint[256];
        for (int i = 0; i < 256; i++)
        {
            double t = i / 255.0;
            byte r, g, b;

            if (t < 0.15) // Black to dark blue
            {
                double u = t / 0.15;
                r = 0;
                g = 0;
                b = (byte)(u * 160);
            }
            else if (t < 0.35) // Blue to cyan
            {
                double u = (t - 0.15) / 0.20;
                r = 0;
                g = (byte)(u * 220);
                b = 255;
            }
            else if (t < 0.55) // Cyan to green
            {
                double u = (t - 0.35) / 0.20;
                r = 0;
                g = 255;
                b = (byte)(255 * (1.0 - u));
            }
            else if (t < 0.75) // Green to yellow
            {
                double u = (t - 0.55) / 0.20;
                r = (byte)(u * 255);
                g = 255;
                b = 0;
            }
            else if (t < 0.90) // Yellow to orange-red
            {
                double u = (t - 0.75) / 0.15;
                r = 255;
                g = (byte)(255 * (1.0 - u * 0.7));
                b = 0;
            }
            else // Red to dark red
            {
                double u = (t - 0.90) / 0.10;
                r = 255;
                g = (byte)(75 * (1.0 - u));
                b = (byte)(u * 80);
            }

            pal[i] = 0xFF000000 | ((uint)r << 16) | ((uint)g << 8) | b;
        }
        return pal;
    }

    private static uint[] InitializeMonPalette()
    {
        uint[] pal = new uint[256];
        for (int i = 0; i < 256; i++)
        {
            double t = i / 255.0;
            byte r, g, b;

            if (t < 0.05)
            {
                r = 0; g = 0; b = 0;
            }
            else
            {
                double u = (t - 0.05) / 0.95;
                r = (byte)(20 * u);
                g = (byte)(40 + 215 * u);
                b = (byte)(30 * u);
            }

            pal[i] = 0xFF000000 | ((uint)r << 16) | ((uint)g << 8) | b;
        }
        return pal;
    }

    private static readonly IBrush BgBrush = new SolidColorBrush(Color.FromRgb(0, 0, 0));
    private static readonly IBrush AxisTextBrush = new SolidColorBrush(Color.FromRgb(200, 205, 210));
    private static readonly IBrush GreenAnnotationBrush = new SolidColorBrush(Color.FromRgb(0, 220, 0));
    private static readonly IBrush DimAnnotationBrush = new SolidColorBrush(Color.FromRgb(102, 102, 102));
    private static readonly IPen RedCutoffPen = new Pen(new SolidColorBrush(Color.FromRgb(255, 0, 0)), 2.0);

    // Hit-testing cached metrics
    private double _leftMargin = 38.0;
    private double _yMax = 0.0;
    private double _yBry = 0.0;
    private double _yCof = 0.0;

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
        uint[] palette = ColormapMode switch
        {
            0 => PaletteMon,
            1 => PaletteCol,
            _ => PaletteBry
        };

        using var fb = _bitmap.Lock();
        unsafe
        {
            uint* ptr = (uint*)fb.Address;
            int total = BitmapWidth * BitmapHeight;
            uint bgPixel = palette[0];
            for (int i = 0; i < total; i++)
            {
                ptr[i] = bgPixel;
            }
        }
        Array.Clear(_rowMaxDb);
        Array.Clear(_rowAvgDb);
        Array.Clear(_rowMinDb);
        Array.Clear(_rowCount);
        _lastRenderedRow = -1;
    }

    private void RebuildBitmap()
    {
        uint[] palette = ColormapMode switch
        {
            0 => PaletteMon,
            1 => PaletteCol,
            _ => PaletteBry
        };

        using var fb = _bitmap.Lock();
        unsafe
        {
            uint* ptr = (uint*)fb.Address;
            uint bg = palette[0];

            for (int r = 0; r < BitmapHeight; r++)
            {
                uint* rowPtr = ptr + r * BitmapWidth;
                if (r > _lastRenderedRow || _rowCount[r] == 0)
                {
                    for (int c = 0; c < BitmapWidth; c++)
                        rowPtr[c] = bg;
                    continue;
                }

                int count = Math.Max(1, _rowCount[r]);
                for (int c = 0; c < BitmapWidth; c++)
                {
                    float db = AggregationMode switch
                    {
                        1 => _rowAvgDb[r, c] / count,
                        2 => _rowMinDb[r, c],
                        _ => _rowMaxDb[r, c]
                    };
                    int palIdx = Math.Clamp((int)((db + 96.0f) * (255.0f / 96.0f)), 0, 255);
                    rowPtr[c] = palette[palIdx];
                }
            }
        }
    }

    static WaterfallControl()
    {
        AffectsRender<WaterfallControl>(
            LatestSpectrumProperty, TrackProgressProperty, SampleRateProperty,
            CutoffFrequencyHzProperty, AggregationModeProperty, ColormapModeProperty,
            ShowCutOffFrequencyProperty);
    }

    /// <summary>
    /// Resets the spectrogram display for a new file.
    /// </summary>
    public void Reset()
    {
        ClearBitmap();
        InvalidateVisual();
    }

    protected override void OnPointerPressed(PointerPressedEventArgs e)
    {
        base.OnPointerPressed(e);
        var pt = e.GetPosition(this);

        if (pt.X <= _leftMargin)
        {
            // Button 1: MAX / AVG / MIN
            if (Math.Abs(pt.Y - _yMax) <= 12)
            {
                AggregationMode = (AggregationMode + 1) % 3;
                RebuildBitmap();
                InvalidateVisual();
                e.Handled = true;
            }
            // Button 2: BRY / MON / COL
            else if (Math.Abs(pt.Y - _yBry) <= 12)
            {
                ColormapMode = (ColormapMode + 1) % 3;
                RebuildBitmap();
                InvalidateVisual();
                e.Handled = true;
            }
            // Button 3: COF
            else if (Math.Abs(pt.Y - _yCof) <= 12)
            {
                ShowCutOffFrequency = !ShowCutOffFrequency;
                InvalidateVisual();
                e.Handled = true;
            }
        }
    }

    protected override void OnPointerMoved(PointerEventArgs e)
    {
        base.OnPointerMoved(e);
        var pt = e.GetPosition(this);

        if (pt.X <= _leftMargin &&
            (Math.Abs(pt.Y - _yMax) <= 12 || Math.Abs(pt.Y - _yBry) <= 12 || Math.Abs(pt.Y - _yCof) <= 12))
        {
            Cursor = new Cursor(StandardCursorType.Hand);
        }
        else
        {
            Cursor = Cursor.Default;
        }
    }

    public override void Render(DrawingContext context)
    {
        double width = Bounds.Width;
        double height = Bounds.Height;
        if (width < 30 || height < 30) return;

        var tf = Typeface.Default;
        context.FillRectangle(BgBrush, new Rect(0, 0, width, height));

        double leftMargin = 38.0;
        _leftMargin = leftMargin;
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
                uint[] palette = ColormapMode switch
                {
                    0 => PaletteMon,
                    1 => PaletteCol,
                    _ => PaletteBry
                };

                using (var fb = _bitmap.Lock())
                {
                    unsafe
                    {
                        uint* ptr = (uint*)fb.Address;
                        int rowStart = Math.Max(0, _lastRenderedRow);
                        for (int r = rowStart; r <= targetRow; r++)
                        {
                            if (_rowCount[r] == 0)
                            {
                                _rowCount[r] = 1;
                                for (int c = 0; c < BitmapWidth; c++)
                                {
                                    int binIdx = (int)(c * (spectrum.Length / (double)BitmapWidth));
                                    float db = (float)spectrum[Math.Clamp(binIdx, 0, spectrum.Length - 1)];
                                    _rowMaxDb[r, c] = db;
                                    _rowAvgDb[r, c] = db;
                                    _rowMinDb[r, c] = db;
                                }
                            }
                            else
                            {
                                _rowCount[r]++;
                                for (int c = 0; c < BitmapWidth; c++)
                                {
                                    int binIdx = (int)(c * (spectrum.Length / (double)BitmapWidth));
                                    float db = (float)spectrum[Math.Clamp(binIdx, 0, spectrum.Length - 1)];
                                    if (db > _rowMaxDb[r, c]) _rowMaxDb[r, c] = db;
                                    if (db < _rowMinDb[r, c]) _rowMinDb[r, c] = db;
                                    _rowAvgDb[r, c] += db;
                                }
                            }

                            uint* rowPtr = ptr + r * BitmapWidth;
                            int count = Math.Max(1, _rowCount[r]);

                            for (int c = 0; c < BitmapWidth; c++)
                            {
                                float db = AggregationMode switch
                                {
                                    1 => _rowAvgDb[r, c] / count,
                                    2 => _rowMinDb[r, c],
                                    _ => _rowMaxDb[r, c]
                                };
                                int palIdx = Math.Clamp((int)((db + 96.0f) * (255.0f / 96.0f)), 0, 255);
                                rowPtr[c] = palette[palIdx];
                            }
                        }
                    }
                }
                _lastRenderedRow = targetRow;
            }
        }

        // Left Y-Axis labels: %, 0, MAX/AVG/MIN, 25, BRY/MON/COL, 50, COF, 75, 100
        DrawText(context, "%", tf, 9, AxisTextBrush, 8, 4);

        double y0 = 12;
        double yMax = y0 + plotHeight * 0.12;
        double y25 = y0 + plotHeight * 0.25;
        double yBry = y0 + plotHeight * 0.38;
        double y50 = y0 + plotHeight * 0.50;
        double yCof = y0 + plotHeight * 0.62;
        double y75 = y0 + plotHeight * 0.75;
        double y100 = y0 + plotHeight - 12;

        _yMax = yMax;
        _yBry = yBry;
        _yCof = yCof;

        string maxText = AggregationMode switch { 1 => "AVG", 2 => "MIN", _ => "MAX" };
        string colText = ColormapMode switch { 0 => "MON", 1 => "COL", _ => "BRY" };
        IBrush cofBrush = ShowCutOffFrequency ? GreenAnnotationBrush : DimAnnotationBrush;

        DrawTextRight(context, "0", tf, 9, AxisTextBrush, leftMargin - 4, y0);
        DrawTextRight(context, maxText, tf, 9, GreenAnnotationBrush, leftMargin - 4, yMax);
        DrawTextRight(context, "25", tf, 9, AxisTextBrush, leftMargin - 4, y25);
        DrawTextRight(context, colText, tf, 9, GreenAnnotationBrush, leftMargin - 4, yBry);
        DrawTextRight(context, "50", tf, 9, AxisTextBrush, leftMargin - 4, y50);
        DrawTextRight(context, "COF", tf, 9, cofBrush, leftMargin - 4, yCof);
        DrawTextRight(context, "75", tf, 9, AxisTextBrush, leftMargin - 4, y75);
        DrawTextRight(context, "100", tf, 9, AxisTextBrush, leftMargin - 4, y100);

        // Draw Waterfall Bitmap
        var destRect = new Rect(leftMargin, 6, plotWidth, plotHeight);
        context.DrawImage(_bitmap, destRect);

        // Draw Cut-Off Frequency vertical line if enabled (WaterfallControl.java lines 594-598)
        if (ShowCutOffFrequency)
        {
            double cutoffHz = CutoffFrequencyHz > 0 ? CutoffFrequencyHz : (SampleRate > 0 ? Math.Min(22050.0, SampleRate * 0.45) : 20000.0);
            double nyquistHz = SampleRate > 0 ? SampleRate / 2.0 : 22050.0;
            double cofNorm = Math.Clamp(cutoffHz / nyquistHz, 0.0, 1.0);
            double cofX = leftMargin + cofNorm * plotWidth;

            context.DrawLine(RedCutoffPen, new Point(cofX, 6), new Point(cofX, 6 + plotHeight));
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
}
