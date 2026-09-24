using System;
using System.Globalization;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Media;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// Precision Numerical Readout Control directly modeling Box 2 of the MusicScope UI.
/// Displays True Peak Meter (TPL, RMS, CREST, PLR) and Loudness Full Scale (M, S, I, LRA).
/// </summary>
public sealed class LevelsBoxControl : Control
{
    public static readonly StyledProperty<double> TruePeakLeftProperty =
        AvaloniaProperty.Register<LevelsBoxControl, double>(nameof(TruePeakLeft), -60.0);

    public static readonly StyledProperty<double> TruePeakRightProperty =
        AvaloniaProperty.Register<LevelsBoxControl, double>(nameof(TruePeakRight), -60.0);

    public static readonly StyledProperty<double> RmsLeftProperty =
        AvaloniaProperty.Register<LevelsBoxControl, double>(nameof(RmsLeft), -60.0);

    public static readonly StyledProperty<double> RmsRightProperty =
        AvaloniaProperty.Register<LevelsBoxControl, double>(nameof(RmsRight), -60.0);

    public static readonly StyledProperty<double> CrestFactorProperty =
        AvaloniaProperty.Register<LevelsBoxControl, double>(nameof(CrestFactor), 0.0);

    public static readonly StyledProperty<double> PlrProperty =
        AvaloniaProperty.Register<LevelsBoxControl, double>(nameof(Plr), 0.0);

    public static readonly StyledProperty<double> MomentaryLeftProperty =
        AvaloniaProperty.Register<LevelsBoxControl, double>(nameof(MomentaryLeft), -60.0);

    public static readonly StyledProperty<double> MomentaryRightProperty =
        AvaloniaProperty.Register<LevelsBoxControl, double>(nameof(MomentaryRight), -60.0);

    public static readonly StyledProperty<double> ShortTermLeftProperty =
        AvaloniaProperty.Register<LevelsBoxControl, double>(nameof(ShortTermLeft), -60.0);

    public static readonly StyledProperty<double> ShortTermRightProperty =
        AvaloniaProperty.Register<LevelsBoxControl, double>(nameof(ShortTermRight), -60.0);

    public static readonly StyledProperty<double> IntegratedLoudnessProperty =
        AvaloniaProperty.Register<LevelsBoxControl, double>(nameof(IntegratedLoudness), -60.0);

    public static readonly StyledProperty<double> LoudnessRangeProperty =
        AvaloniaProperty.Register<LevelsBoxControl, double>(nameof(LoudnessRange), 0.0);

    public double TruePeakLeft { get => GetValue(TruePeakLeftProperty); set => SetValue(TruePeakLeftProperty, value); }
    public double TruePeakRight { get => GetValue(TruePeakRightProperty); set => SetValue(TruePeakRightProperty, value); }
    public double RmsLeft { get => GetValue(RmsLeftProperty); set => SetValue(RmsLeftProperty, value); }
    public double RmsRight { get => GetValue(RmsRightProperty); set => SetValue(RmsRightProperty, value); }
    public double CrestFactor { get => GetValue(CrestFactorProperty); set => SetValue(CrestFactorProperty, value); }
    public double Plr { get => GetValue(PlrProperty); set => SetValue(PlrProperty, value); }
    public double MomentaryLeft { get => GetValue(MomentaryLeftProperty); set => SetValue(MomentaryLeftProperty, value); }
    public double MomentaryRight { get => GetValue(MomentaryRightProperty); set => SetValue(MomentaryRightProperty, value); }
    public double ShortTermLeft { get => GetValue(ShortTermLeftProperty); set => SetValue(ShortTermLeftProperty, value); }
    public double ShortTermRight { get => GetValue(ShortTermRightProperty); set => SetValue(ShortTermRightProperty, value); }
    public double IntegratedLoudness { get => GetValue(IntegratedLoudnessProperty); set => SetValue(IntegratedLoudnessProperty, value); }
    public double LoudnessRange { get => GetValue(LoudnessRangeProperty); set => SetValue(LoudnessRangeProperty, value); }

    private static readonly IBrush HeaderActiveBrush = new SolidColorBrush(Color.FromRgb(0, 220, 0)); // Green header
    private static readonly IBrush HeaderDimBrush = new SolidColorBrush(Color.FromRgb(100, 105, 110)); // Inactive header
    private static readonly IBrush SubheaderBrush = new SolidColorBrush(Color.FromRgb(110, 115, 120)); // Dim subheader
    private static readonly IBrush LabelBrush = new SolidColorBrush(Color.FromRgb(220, 220, 220)); // White/light gray labels
    private static readonly IBrush CyanBrush = new SolidColorBrush(Color.FromRgb(0, 229, 255)); // Cyan values
    private static readonly IBrush GreenBrush = new SolidColorBrush(Color.FromRgb(0, 230, 80)); // Green RMS
    private static readonly IBrush OrangeBrush = new SolidColorBrush(Color.FromRgb(255, 166, 87)); // Orange short-term
    private static readonly IBrush RedBrush = new SolidColorBrush(Color.FromRgb(255, 50, 50)); // Red clipping
    private static readonly IPen DividerPen = new Pen(new SolidColorBrush(Color.FromRgb(50, 55, 60)), 1);

    static LevelsBoxControl()
    {
        AffectsRender<LevelsBoxControl>(
            TruePeakLeftProperty, TruePeakRightProperty, RmsLeftProperty, RmsRightProperty,
            CrestFactorProperty, PlrProperty, MomentaryLeftProperty, MomentaryRightProperty,
            ShortTermLeftProperty, ShortTermRightProperty, IntegratedLoudnessProperty, LoudnessRangeProperty);
    }

    public override void Render(DrawingContext context)
    {
        double width = Bounds.Width;
        double height = Bounds.Height;
        if (width < 30 || height < 30) return;

        var tf = Typeface.Default;
        double left = 10;
        double right = width - 10;
        double val1X = left + 85;
        double val2X = right;

        // 1. Top tabs: Levels (active green) | Bit Monitor (dim)
        DrawText(context, "Levels", tf, 11, HeaderActiveBrush, left, 8);
        DrawText(context, "Bit Monitor", tf, 11, HeaderDimBrush, left + 60, 8);

        // 2. Section 1: True Peak Meter
        double y = 32;
        DrawText(context, "True Peak Meter", tf, 10, SubheaderBrush, left, y);
        y += 16;
        context.DrawLine(DividerPen, new Point(left, y), new Point(right, y));
        y += 6;

        // TPL
        DrawText(context, "TPL", tf, 11, LabelBrush, left, y);
        IBrush tplBrushL = TruePeakLeft > 0.0 ? RedBrush : CyanBrush;
        IBrush tplBrushR = TruePeakRight > 0.0 ? RedBrush : CyanBrush;
        DrawTextRight(context, FormatDb(TruePeakLeft), tf, 11, tplBrushL, val1X, y);
        DrawTextRight(context, FormatDb(TruePeakRight), tf, 11, tplBrushR, val2X, y);
        y += 18;

        // RMS
        DrawText(context, "RMS", tf, 11, LabelBrush, left, y);
        DrawTextRight(context, FormatDb(RmsLeft), tf, 11, GreenBrush, val1X, y);
        DrawTextRight(context, FormatDb(RmsRight), tf, 11, GreenBrush, val2X, y);
        y += 18;

        // CREST
        DrawText(context, "CREST", tf, 11, LabelBrush, left, y);
        DrawTextRight(context, FormatVal(CrestFactor), tf, 11, CyanBrush, val1X, y);
        y += 18;

        // PLR (Peak to Loudness Ratio)
        DrawText(context, "PLR", tf, 11, LabelBrush, left, y);
        DrawTextRight(context, FormatVal(Plr), tf, 11, CyanBrush, val1X, y);
        y += 24;

        // 3. Section 2: Loudness Full Scale
        DrawText(context, "Loudness Full Scale", tf, 10, SubheaderBrush, left, y);
        y += 16;
        context.DrawLine(DividerPen, new Point(left, y), new Point(right, y));
        y += 6;

        // M (Momentary)
        DrawText(context, "M", tf, 11, LabelBrush, left, y);
        DrawTextRight(context, FormatDb(MomentaryLeft), tf, 11, CyanBrush, val1X, y);
        DrawTextRight(context, FormatDb(MomentaryRight), tf, 11, CyanBrush, val2X, y);
        y += 18;

        // S (Short-term)
        DrawText(context, "S", tf, 11, LabelBrush, left, y);
        DrawTextRight(context, FormatDb(ShortTermLeft), tf, 11, OrangeBrush, val1X, y);
        DrawTextRight(context, FormatDb(ShortTermRight), tf, 11, OrangeBrush, val2X, y);
        y += 18;

        // I (Integrated)
        DrawText(context, "I", tf, 11, LabelBrush, left, y);
        DrawTextRight(context, FormatDb(IntegratedLoudness), tf, 11, LabelBrush, val1X, y);
        y += 18;

        // LRA (Loudness Range)
        DrawText(context, "LRA", tf, 11, LabelBrush, left, y);
        DrawTextRight(context, FormatVal(LoudnessRange), tf, 11, LabelBrush, val1X, y);
    }

    private static string FormatDb(double val)
    {
        if (val <= -99.0 || double.IsNaN(val) || double.IsInfinity(val))
            return "-60.0";
        return val.ToString("F1", CultureInfo.InvariantCulture);
    }

    private static string FormatVal(double val)
    {
        if (double.IsNaN(val) || double.IsInfinity(val))
            return "0.0";
        return val.ToString("F1", CultureInfo.InvariantCulture);
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
