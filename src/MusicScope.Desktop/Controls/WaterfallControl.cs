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
/// Maps frequency bins (1024 cols) along the X-axis and track playback progression (250 rows) along the Y-axis.
/// Supports clickable buttons in the left axis:
///  - MAX / AVG / MIN: switches magnitude aggregation mode
///  - BRY / MON / COL: switches heatmap colormap (Bright Red-Yellow / Monochrome Green / Rainbow Color)
///  - COF: toggles Cut-Off Frequency line overlay
/// </summary>
public sealed class WaterfallControl : Control
{
    public static readonly StyledProperty<double[]?> LatestSpectrumProperty =
        AvaloniaProperty.Register<WaterfallControl, double[]?>(nameof(LatestSpectrum));

    public static readonly StyledProperty<float[]?> SpectrogramMaxProperty =
        AvaloniaProperty.Register<WaterfallControl, float[]?>(nameof(SpectrogramMax));

    public static readonly StyledProperty<float[]?> SpectrogramAvgProperty =
        AvaloniaProperty.Register<WaterfallControl, float[]?>(nameof(SpectrogramAvg));

    public static readonly StyledProperty<float[]?> SpectrogramMinProperty =
        AvaloniaProperty.Register<WaterfallControl, float[]?>(nameof(SpectrogramMin));

    public static readonly StyledProperty<int[]?> SpectrogramRowCountProperty =
        AvaloniaProperty.Register<WaterfallControl, int[]?>(nameof(SpectrogramRowCount));

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

    public float[]? SpectrogramMax
    {
        get => GetValue(SpectrogramMaxProperty);
        set => SetValue(SpectrogramMaxProperty, value);
    }

    public float[]? SpectrogramAvg
    {
        get => GetValue(SpectrogramAvgProperty);
        set => SetValue(SpectrogramAvgProperty, value);
    }

    public float[]? SpectrogramMin
    {
        get => GetValue(SpectrogramMinProperty);
        set => SetValue(SpectrogramMinProperty, value);
    }

    public int[]? SpectrogramRowCount
    {
        get => GetValue(SpectrogramRowCountProperty);
        set => SetValue(SpectrogramRowCountProperty, value);
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

    public const int BitmapWidth = 1024;
    public const int BitmapHeight = 250;
    private readonly WriteableBitmap _bitmap;
    private int _lastRenderedRow = -1;

    // Palettes matching original MusicScope Java WaterfallControl.java
    private static readonly uint[] PaletteMon = InitializeMonPalette();
    private static readonly uint[] PaletteCol = InitializeColPalette();
    private static readonly uint[] PaletteBry = InitializeBryPalette();

    private static uint[] InitializeMonPalette()
    {
        uint[] pal = new uint[256];
        for (int i = 0; i < 256; i++)
        {
            pal[i] = 0xFF000000u | ((uint)i << 8);
        }
        return pal;
    }

    private static uint[] InitializeColPalette()
    {
        uint[] pal = new uint[896];
        int r = 0, g = 0, b = 0;
        for (int i = 0; i < 128; i++) pal[i] = 0xFF000000u | ((uint)r << 16) | ((uint)g << 8) | (uint)b++;
        r = 0; g = 0; b = 127;
        for (int i = 128; i < 256; i++) { pal[i] = 0xFF000000u | ((uint)r << 16) | ((uint)g << 8) | (uint)b++; g++; }
        r = 0; g = 127; b = 255;
        for (int i = 256; i < 384; i++) { pal[i] = 0xFF000000u | ((uint)r << 16) | ((uint)g << 8) | (uint)b--; g++; }
        r = 0; g = 255; b = 127;
        for (int i = 384; i < 512; i++) { pal[i] = 0xFF000000u | ((uint)r << 16) | ((uint)g << 8) | (uint)b--; r++; }
        r = 127; g = 255; b = 0;
        for (int i = 512; i < 640; i++) { pal[i] = 0xFF000000u | ((uint)r << 16) | ((uint)g << 8) | (uint)b; r++; }
        r = 255; g = 255; b = 0;
        for (int i = 640; i < 768; i++) { pal[i] = 0xFF000000u | ((uint)r << 16) | ((uint)g << 8) | (uint)b; g--; }
        r = 255; g = 127; b = 0;
        for (int i = 768; i < 896; i++) { pal[i] = 0xFF000000u | ((uint)r << 16) | ((uint)g << 8) | (uint)b; g--; }
        return pal;
    }

    private static uint[] InitializeBryPalette()
    {
        uint[] pal = new uint[640];
        int r = 0, g = 0, b = 0;
        for (int i = 0; i < 128; i++) pal[i] = 0xFF000000u | ((uint)r << 16) | ((uint)g << 8) | (uint)b++;
        r = 0; g = 0; b = 127;
        for (int i = 128; i < 256; i++) { pal[i] = 0xFF000000u | ((uint)r << 16) | ((uint)g << 8) | (uint)b++; r++; }
        r = 127; g = 0; b = 255;
        for (int i = 256; i < 384; i++) { pal[i] = 0xFF000000u | ((uint)r << 16) | ((uint)g << 8) | (uint)b--; r++; }
        r = 255; g = 0; b = 127;
        for (int i = 384; i < 512; i++) { pal[i] = 0xFF000000u | ((uint)r << 16) | ((uint)g << 8) | (uint)b--; g++; }
        r = 255; g = 127; b = 0;
        for (int i = 512; i < 640; i++) { pal[i] = 0xFF000000u | ((uint)r << 16) | ((uint)g << 8) | (uint)b; g++; }
        return pal;
    }

    private static readonly IBrush BgBrush = new SolidColorBrush(Color.FromRgb(0, 0, 0));
    private static readonly IBrush AxisTextBrush = new SolidColorBrush(Color.FromRgb(200, 205, 210));
    private static readonly IBrush GreenAnnotationBrush = new SolidColorBrush(Color.FromRgb(0, 255, 0));
    private static readonly IBrush DimAnnotationBrush = new SolidColorBrush(Color.FromRgb(102, 102, 102));
    private static readonly IPen RedCutoffPen = new Pen(new SolidColorBrush(Color.FromRgb(255, 0, 0)), 2.0);
    private static readonly IPen GridPen = new Pen(new SolidColorBrush(Color.FromRgb(50, 50, 50)), 1.0);

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
        using var fb = _bitmap.Lock();
        unsafe
        {
            uint* ptr = (uint*)fb.Address;
            int total = BitmapWidth * BitmapHeight;
            for (int i = 0; i < total; i++)
            {
                ptr[i] = 0xFF000000u;
            }
        }
        _lastRenderedRow = -1;
    }

    private void RebuildBitmap()
    {
        float[]? srcMax = SpectrogramMax;
        float[]? srcAvg = SpectrogramAvg;
        float[]? srcMin = SpectrogramMin;
        int[]? rowCounts = SpectrogramRowCount;

        using var fb = _bitmap.Lock();
        unsafe
        {
            uint* ptr = (uint*)fb.Address;

            for (int r = 0; r < BitmapHeight; r++)
            {
                uint* rowPtr = ptr + r * BitmapWidth;
                int count = (rowCounts != null && r < rowCounts.Length) ? rowCounts[r] : 0;
                if (count == 0)
                {
                    for (int c = 0; c < BitmapWidth; c++)
                        rowPtr[c] = 0xFF000000u;
                    continue;
                }

                int rowOffset = r * BitmapWidth;
                for (int c = 0; c < BitmapWidth; c++)
                {
                    float mag = 0f;
                    if (srcMax != null && srcAvg != null && srcMin != null && (rowOffset + c) < srcMax.Length)
                    {
                        mag = AggregationMode switch
                        {
                            1 => srcAvg[rowOffset + c] / count,
                            2 => srcMin[rowOffset + c],
                            _ => srcMax[rowOffset + c]
                        };
                    }

                    double d = 70.4 * Math.Log10(mag * 6000.0 + 1.0);
                    uint color = ColormapMode switch
                    {
                        0 => PaletteMon[Math.Clamp((int)d, 0, 255)],
                        1 => PaletteCol[Math.Clamp((int)(3.5 * d), 0, 895)],
                        _ => PaletteBry[Math.Clamp((int)(2.5 * d), 0, 639)]
                    };
                    rowPtr[c] = color;
                }
            }
        }
    }

    protected override void OnPropertyChanged(AvaloniaPropertyChangedEventArgs change)
    {
        base.OnPropertyChanged(change);

        if (change.Property == SpectrogramMaxProperty ||
            change.Property == SpectrogramAvgProperty ||
            change.Property == SpectrogramMinProperty ||
            change.Property == SpectrogramRowCountProperty)
        {
            RebuildBitmap();
            InvalidateVisual();
        }
    }

    static WaterfallControl()
    {
        AffectsRender<WaterfallControl>(
            LatestSpectrumProperty, SpectrogramMaxProperty, SpectrogramAvgProperty,
            SpectrogramMinProperty, SpectrogramRowCountProperty, TrackProgressProperty,
            SampleRateProperty, CutoffFrequencyHzProperty, AggregationModeProperty,
            ColormapModeProperty, ShowCutOffFrequencyProperty);
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
            if (Math.Abs(pt.Y - _yMax) <= 14)
            {
                AggregationMode = (AggregationMode + 1) % 3;
                RebuildBitmap();
                InvalidateVisual();
                e.Handled = true;
            }
            // Button 2: BRY / MON / COL
            else if (Math.Abs(pt.Y - _yBry) <= 14)
            {
                ColormapMode = (ColormapMode + 1) % 3;
                RebuildBitmap();
                InvalidateVisual();
                e.Handled = true;
            }
            // Button 3: COF
            else if (Math.Abs(pt.Y - _yCof) <= 14)
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
            (Math.Abs(pt.Y - _yMax) <= 14 || Math.Abs(pt.Y - _yBry) <= 14 || Math.Abs(pt.Y - _yCof) <= 14))
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

        // Reset if new analysis starts
        if (TrackProgress <= 0.001 && _lastRenderedRow > 10)
        {
            ClearBitmap();
        }

        // Render newest rows into bitmap based on Spectrogram buffers & TrackProgress
        float[]? srcMax = SpectrogramMax;
        float[]? srcAvg = SpectrogramAvg;
        float[]? srcMin = SpectrogramMin;
        int[]? rowCounts = SpectrogramRowCount;

        if (srcMax != null && srcAvg != null && srcMin != null && rowCounts != null)
        {
            int targetRow = (TrackProgress >= 1.0)
                ? BitmapHeight - 1
                : Math.Clamp((int)(TrackProgress * (BitmapHeight - 1)), 0, BitmapHeight - 1);

            int rowStart = Math.Max(0, _lastRenderedRow);
            if (targetRow >= rowStart)
            {
                using (var fb = _bitmap.Lock())
                {
                    unsafe
                    {
                        uint* ptr = (uint*)fb.Address;
                        for (int r = rowStart; r <= targetRow; r++)
                        {
                            uint* rowPtr = ptr + r * BitmapWidth;
                            int count = r < rowCounts.Length ? rowCounts[r] : 0;
                            if (count == 0)
                                continue;

                            int rowOffset = r * BitmapWidth;
                            for (int c = 0; c < BitmapWidth; c++)
                            {
                                float mag = AggregationMode switch
                                {
                                    1 => srcAvg[rowOffset + c] / count,
                                    2 => srcMin[rowOffset + c],
                                    _ => srcMax[rowOffset + c]
                                };

                                double d = 70.4 * Math.Log10(mag * 6000.0 + 1.0);
                                uint color = ColormapMode switch
                                {
                                    0 => PaletteMon[Math.Clamp((int)d, 0, 255)],
                                    1 => PaletteCol[Math.Clamp((int)(3.5 * d), 0, 895)],
                                    _ => PaletteBry[Math.Clamp((int)(2.5 * d), 0, 639)]
                                };
                                rowPtr[c] = color;
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

        // Subtle 25%, 50%, 75% horizontal grid lines (matching WaterfallControl.java lines 253-260)
        for (int i = 1; i <= 3; i++)
        {
            double yGrid = 6 + plotHeight * (i * 0.25);
            context.DrawLine(GridPen, new Point(leftMargin, yGrid), new Point(leftMargin + plotWidth, yGrid));
        }

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
