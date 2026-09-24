using System;
using System.Globalization;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Media;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// Audio Format Matrix Control directly modeling Box 1 of the MusicScope UI.
/// Highlights the active PCM/DSD format, bit depth, sample rate, and codec in white,
/// keeping inactive rates and formats in dim gray.
/// </summary>
public sealed class FormatBoxControl : Control
{
    public static readonly StyledProperty<string> FormatNameProperty =
        AvaloniaProperty.Register<FormatBoxControl, string>(nameof(FormatName), "FLAC");

    public static readonly StyledProperty<double> SampleRateProperty =
        AvaloniaProperty.Register<FormatBoxControl, double>(nameof(SampleRate), 44100.0);

    public static readonly StyledProperty<int> BitDepthProperty =
        AvaloniaProperty.Register<FormatBoxControl, int>(nameof(BitDepth), 16);

    public static readonly StyledProperty<bool> IsDsdProperty =
        AvaloniaProperty.Register<FormatBoxControl, bool>(nameof(IsDsd), false);

    public string FormatName
    {
        get => GetValue(FormatNameProperty);
        set => SetValue(FormatNameProperty, value);
    }

    public double SampleRate
    {
        get => GetValue(SampleRateProperty);
        set => SetValue(SampleRateProperty, value);
    }

    public int BitDepth
    {
        get => GetValue(BitDepthProperty);
        set => SetValue(BitDepthProperty, value);
    }

    public bool IsDsd
    {
        get => GetValue(IsDsdProperty);
        set => SetValue(IsDsdProperty, value);
    }

    private static readonly IBrush HeaderBrush = new SolidColorBrush(Color.FromRgb(0, 220, 0)); // Green header
    private static readonly IBrush ActiveBrush = new SolidColorBrush(Color.FromRgb(240, 240, 240)); // Active white
    private static readonly IBrush DimBrush = new SolidColorBrush(Color.FromRgb(70, 75, 80)); // Inactive dark gray
    private static readonly IPen DividerPen = new Pen(new SolidColorBrush(Color.FromRgb(50, 55, 60)), 1);

    static FormatBoxControl()
    {
        AffectsRender<FormatBoxControl>(FormatNameProperty, SampleRateProperty, BitDepthProperty, IsDsdProperty);
    }

    public override void Render(DrawingContext context)
    {
        double width = Bounds.Width;
        double height = Bounds.Height;
        if (width < 30 || height < 30) return;

        var tf = Typeface.Default;
        double left = 12;
        double right = width - 12;
        double col2 = left + (right - left) * 0.5;

        // 1. Header: Format
        var ftHeader = new FormattedText("Format", CultureInfo.InvariantCulture, FlowDirection.LeftToRight, tf, 11, HeaderBrush);
        context.DrawText(ftHeader, new Point(left, 8));

        double y = 32;

        // 2. PCM vs DSD
        bool isDsd = IsDsd || FormatName.Equals("DSF", StringComparison.OrdinalIgnoreCase) || FormatName.Equals("DFF", StringComparison.OrdinalIgnoreCase);
        DrawText(context, "PCM", tf, 11, !isDsd ? ActiveBrush : DimBrush, left, y);
        DrawTextRight(context, "DSD", tf, 11, isDsd ? ActiveBrush : DimBrush, right, y);

        y += 18;
        context.DrawLine(DividerPen, new Point(left, y), new Point(right, y));
        y += 6;

        // 3. Bit Depths: 1   16   24   32
        int bitDepth = BitDepth;
        double wBit = (right - left) / 3.0;
        DrawText(context, "1", tf, 11, bitDepth == 1 ? ActiveBrush : DimBrush, left, y);
        DrawText(context, "16", tf, 11, bitDepth == 16 ? ActiveBrush : DimBrush, left + wBit * 0.9, y);
        DrawText(context, "24", tf, 11, bitDepth == 24 ? ActiveBrush : DimBrush, left + wBit * 1.9, y);
        DrawTextRight(context, "32", tf, 11, bitDepth == 32 ? ActiveBrush : DimBrush, right, y);

        y += 18;
        context.DrawLine(DividerPen, new Point(left, y), new Point(right, y));
        y += 6;

        // 4. Sample Rates (2 columns)
        double sr = SampleRate;
        double srKhz = sr / 1000.0;

        DrawRate(context, "44.1", Math.Abs(srKhz - 44.1) < 0.2, tf, left, y);
        DrawRate(context, "48", Math.Abs(srKhz - 48.0) < 0.2, tf, col2, y);
        y += 16;
        DrawRate(context, "88.2", Math.Abs(srKhz - 88.2) < 0.2, tf, left, y);
        DrawRate(context, "96", Math.Abs(srKhz - 96.0) < 0.2, tf, col2, y);
        y += 16;
        DrawRate(context, "176.4", Math.Abs(srKhz - 176.4) < 0.2, tf, left, y);
        DrawRate(context, "192", Math.Abs(srKhz - 192.0) < 0.2, tf, col2, y);
        y += 16;
        DrawRate(context, "352.8", Math.Abs(srKhz - 352.8) < 0.2, tf, left, y);
        DrawRate(context, "384", Math.Abs(srKhz - 384.0) < 0.2, tf, col2, y);

        y += 18;
        // 5. DSD Rates: 64  128  256  512
        double wDsd = (right - left) / 3.0;
        DrawText(context, "64", tf, 10, isDsd && Math.Abs(sr - 2822400) < 1000 ? ActiveBrush : DimBrush, left, y);
        DrawText(context, "128", tf, 10, isDsd && Math.Abs(sr - 5644800) < 1000 ? ActiveBrush : DimBrush, left + wDsd * 0.9, y);
        DrawText(context, "256", tf, 10, isDsd && Math.Abs(sr - 11289600) < 1000 ? ActiveBrush : DimBrush, left + wDsd * 1.9, y);
        DrawTextRight(context, "512", tf, 10, isDsd && Math.Abs(sr - 22579200) < 1000 ? ActiveBrush : DimBrush, right, y);

        y += 16;
        context.DrawLine(DividerPen, new Point(left, y), new Point(right, y));
        y += 6;

        // 6. Codecs (2 columns)
        string fmt = FormatName.ToUpperInvariant();
        DrawCodec(context, "WAV", fmt == "WAV", tf, left, y);
        DrawCodec(context, "DSF", fmt == "DSF", tf, col2, y);
        y += 15;
        DrawCodec(context, "AIFF", fmt == "AIFF" || fmt == "AIF", tf, left, y);
        DrawCodec(context, "DFF", fmt == "DFF", tf, col2, y);
        y += 15;
        DrawCodec(context, "FLAC", fmt == "FLAC", tf, left, y);
        DrawCodec(context, "MP3", fmt == "MP3", tf, col2, y);
        y += 15;
        DrawCodec(context, "ALAC", fmt == "ALAC" || fmt == "M4A", tf, left, y);
        DrawCodec(context, "BWF", fmt == "BWF", tf, col2, y);
    }

    private static void DrawRate(DrawingContext ctx, string text, bool active, Typeface tf, double x, double y)
    {
        DrawText(ctx, text, tf, 11, active ? ActiveBrush : DimBrush, x, y);
    }

    private static void DrawCodec(DrawingContext ctx, string text, bool active, Typeface tf, double x, double y)
    {
        DrawText(ctx, text, tf, 10, active ? ActiveBrush : DimBrush, x, y);
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
