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

    public static readonly StyledProperty<double> MomentaryCurrentProperty =
        AvaloniaProperty.Register<LevelsBoxControl, double>(nameof(MomentaryCurrent), -60.0);

    public static readonly StyledProperty<double> MomentaryMaxProperty =
        AvaloniaProperty.Register<LevelsBoxControl, double>(nameof(MomentaryMax), -60.0);

    public static readonly StyledProperty<double> ShortTermCurrentProperty =
        AvaloniaProperty.Register<LevelsBoxControl, double>(nameof(ShortTermCurrent), -60.0);

    public static readonly StyledProperty<double> ShortTermMaxProperty =
        AvaloniaProperty.Register<LevelsBoxControl, double>(nameof(ShortTermMax), -60.0);

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
    public double MomentaryCurrent { get => GetValue(MomentaryCurrentProperty); set => SetValue(MomentaryCurrentProperty, value); }
    public double MomentaryMax { get => GetValue(MomentaryMaxProperty); set => SetValue(MomentaryMaxProperty, value); }
    public double ShortTermCurrent { get => GetValue(ShortTermCurrentProperty); set => SetValue(ShortTermCurrentProperty, value); }
    public double ShortTermMax { get => GetValue(ShortTermMaxProperty); set => SetValue(ShortTermMaxProperty, value); }
    public double IntegratedLoudness { get => GetValue(IntegratedLoudnessProperty); set => SetValue(IntegratedLoudnessProperty, value); }
    public double LoudnessRange { get => GetValue(LoudnessRangeProperty); set => SetValue(LoudnessRangeProperty, value); }

    // Backward-compatible aliases
    public double MomentaryLeft { get => MomentaryCurrent; set => MomentaryCurrent = value; }
    public double MomentaryRight { get => MomentaryMax; set => MomentaryMax = value; }
    public double ShortTermLeft { get => ShortTermCurrent; set => ShortTermCurrent = value; }
    public double ShortTermRight { get => ShortTermMax; set => ShortTermMax = value; }

    private static readonly IBrush HeaderActiveBrush = new SolidColorBrush(Color.FromRgb(0, 220, 0)); // Green header
    private static readonly IBrush HeaderDimBrush = new SolidColorBrush(Color.FromRgb(100, 105, 110)); // Inactive header
    private static readonly IBrush SubheaderBrush = new SolidColorBrush(Color.FromRgb(110, 115, 120)); // Dim subheader
    private static readonly IBrush LabelBrush = new SolidColorBrush(Color.FromRgb(220, 220, 220)); // White/light gray labels
    private static readonly IBrush TplNormalBrush = new SolidColorBrush(Color.FromRgb(0x00, 0xE8, 0x6F)); // #00E86F Green (< 0.05)
    private static readonly IBrush TplClipBrush = new SolidColorBrush(Color.FromRgb(0xEE, 0x00, 0x00)); // #EE0000 Red (>= 0.05)
    private static readonly IBrush RmsBrush = new SolidColorBrush(Color.FromRgb(0x00, 0xFF, 0x00)); // #00FF00 Green
    private static readonly IBrush CrestBrush = new SolidColorBrush(Color.FromRgb(0xDD, 0xDD, 0x55)); // #DDDD55 Yellow/Gold
    private static readonly IBrush PlrBrush = new SolidColorBrush(Color.FromRgb(0x55, 0xDD, 0xFF)); // #55DDFF Cyan
    private static readonly IBrush MomentaryBrush = new SolidColorBrush(Color.FromRgb(0x00, 0x9F, 0xC6)); // #009FC6 Cyan-blue
    private static readonly IBrush ShortTermBrush = new SolidColorBrush(Color.FromRgb(0xE8, 0x9A, 0x20)); // #E89A20 Amber/Orange
    private static readonly IBrush LoudnessWhiteBrush = new SolidColorBrush(Color.FromRgb(0xEE, 0xEE, 0xEE)); // #EEEEEE Light Gray
    private static readonly IPen DividerPen = new Pen(new SolidColorBrush(Color.FromRgb(50, 55, 60)), 1);

    static LevelsBoxControl()
    {
        AffectsRender<LevelsBoxControl>(
            TruePeakLeftProperty, TruePeakRightProperty, RmsLeftProperty, RmsRightProperty,
            CrestFactorProperty, PlrProperty, MomentaryCurrentProperty, MomentaryMaxProperty,
            ShortTermCurrentProperty, ShortTermMaxProperty, IntegratedLoudnessProperty, LoudnessRangeProperty);
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
        IBrush tplBrushL = TruePeakLeft < 0.05 ? TplNormalBrush : TplClipBrush;
        IBrush tplBrushR = TruePeakRight < 0.05 ? TplNormalBrush : TplClipBrush;
        DrawTextRight(context, FormatDb(TruePeakLeft), tf, 11, tplBrushL, val1X, y);
        DrawTextRight(context, FormatDb(TruePeakRight), tf, 11, tplBrushR, val2X, y);
        y += 18;

        // RMS
        DrawText(context, "RMS", tf, 11, LabelBrush, left, y);
        DrawTextRight(context, FormatDb(RmsLeft), tf, 11, RmsBrush, val1X, y);
        DrawTextRight(context, FormatDb(RmsRight), tf, 11, RmsBrush, val2X, y);
        y += 18;

        // CREST (XiVero gold #DDDD55)
        DrawText(context, "CREST", tf, 11, LabelBrush, left, y);
        DrawTextRight(context, FormatVal(CrestFactor), tf, 11, CrestBrush, val1X, y);
        y += 18;

        // PLR (Peak to Loudness Ratio, cyan #55DDFF)
        DrawText(context, "PLR", tf, 11, LabelBrush, left, y);
        DrawTextRight(context, FormatVal(Plr), tf, 11, PlrBrush, val1X, y);
        y += 24;

        // 3. Section 2: Loudness Full Scale
        DrawText(context, "Loudness Full Scale", tf, 10, SubheaderBrush, left, y);
        y += 16;
        context.DrawLine(DividerPen, new Point(left, y), new Point(right, y));
        y += 6;

        // M (Momentary: Current at Col 1, Max at Col 2, #009FC6)
        DrawText(context, "M", tf, 11, LabelBrush, left, y);
        DrawTextRight(context, FormatDb(MomentaryCurrent), tf, 11, MomentaryBrush, val1X, y);
        DrawTextRight(context, FormatDb(MomentaryMax), tf, 11, MomentaryBrush, val2X, y);
        y += 18;

        // S (Short-term: Current at Col 1, Max at Col 2, #E89A20)
        DrawText(context, "S", tf, 11, LabelBrush, left, y);
        DrawTextRight(context, FormatDb(ShortTermCurrent), tf, 11, ShortTermBrush, val1X, y);
        DrawTextRight(context, FormatDb(ShortTermMax), tf, 11, ShortTermBrush, val2X, y);
        y += 18;

        // I (Integrated: Col 1, #EEEEEE)
        DrawText(context, "I", tf, 11, LabelBrush, left, y);
        DrawTextRight(context, FormatDb(IntegratedLoudness), tf, 11, LoudnessWhiteBrush, val1X, y);
        y += 18;

        // LRA (Loudness Range: Col 1, #EEEEEE)
        DrawText(context, "LRA", tf, 11, LabelBrush, left, y);
        DrawTextRight(context, FormatVal(LoudnessRange), tf, 11, LoudnessWhiteBrush, val1X, y);
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
