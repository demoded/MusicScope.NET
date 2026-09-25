using System;
using System.Globalization;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Input;
using Avalonia.Media;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// S-Mode Precision Loudness &amp; LED Peak Meter with Real-Time Distribution Histogram and LRA bracket.
/// Bit-exact replica of MusicScope's LevelMeterControl (Box 3 of the UI).
/// </summary>
public sealed class SModeMeterControl : Control
{
    public static readonly StyledProperty<double> PeakLeftDbProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(PeakLeftDb), -60.0);

    public static readonly StyledProperty<double> PeakRightDbProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(PeakRightDb), -60.0);

    public static readonly StyledProperty<double> MaxPeakLeftDbProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(MaxPeakLeftDb), -60.0);

    public static readonly StyledProperty<double> MaxPeakRightDbProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(MaxPeakRightDb), -60.0);

    public static readonly StyledProperty<double> RmsLeftDbProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(RmsLeftDb), -60.0);

    public static readonly StyledProperty<double> RmsRightDbProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(RmsRightDb), -60.0);

    public static readonly StyledProperty<double> MomentaryLufsProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(MomentaryLufs), -60.0);

    public static readonly StyledProperty<double> MomentaryMaxLufsProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(MomentaryMaxLufs), -60.0);

    public static readonly StyledProperty<double> ShortTermLufsProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(ShortTermLufs), -60.0);

    public static readonly StyledProperty<double> ShortTermMaxLufsProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(ShortTermMaxLufs), -60.0);

    public static readonly StyledProperty<double> IntegratedLufsProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(IntegratedLufs), -60.0);

    public static readonly StyledProperty<double> PlrDbProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(PlrDb), 0.0);

    public static readonly StyledProperty<double> MidLevelDbProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(MidLevelDb), -60.0);

    public static readonly StyledProperty<double> SideLevelDbProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(SideLevelDb), -60.0);

    public static readonly StyledProperty<bool> IsMidSideProperty =
        AvaloniaProperty.Register<SModeMeterControl, bool>(nameof(IsMidSide), false);

    public static readonly StyledProperty<int> MeterModeProperty =
        AvaloniaProperty.Register<SModeMeterControl, int>(nameof(MeterMode), 0); // 0=S-Mode, 1=M-Mode, 2=TPL

    public static readonly StyledProperty<double> LoudnessRangeProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(LoudnessRange), 0.0);

    public static readonly StyledProperty<double> LraLowProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(LraLow), -70.0);

    public static readonly StyledProperty<double> LraHighProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(LraHigh), -70.0);

    public static readonly StyledProperty<int[]?> SModeHistogramProperty =
        AvaloniaProperty.Register<SModeMeterControl, int[]?>(nameof(SModeHistogram));

    public static readonly StyledProperty<int> SModeMaxCountProperty =
        AvaloniaProperty.Register<SModeMeterControl, int>(nameof(SModeMaxCount), 0);

    public double PeakLeftDb { get => GetValue(PeakLeftDbProperty); set => SetValue(PeakLeftDbProperty, value); }
    public double PeakRightDb { get => GetValue(PeakRightDbProperty); set => SetValue(PeakRightDbProperty, value); }
    public double MaxPeakLeftDb { get => GetValue(MaxPeakLeftDbProperty); set => SetValue(MaxPeakLeftDbProperty, value); }
    public double MaxPeakRightDb { get => GetValue(MaxPeakRightDbProperty); set => SetValue(MaxPeakRightDbProperty, value); }
    public double RmsLeftDb { get => GetValue(RmsLeftDbProperty); set => SetValue(RmsLeftDbProperty, value); }
    public double RmsRightDb { get => GetValue(RmsRightDbProperty); set => SetValue(RmsRightDbProperty, value); }
    public double MomentaryLufs { get => GetValue(MomentaryLufsProperty); set => SetValue(MomentaryLufsProperty, value); }
    public double MomentaryMaxLufs { get => GetValue(MomentaryMaxLufsProperty); set => SetValue(MomentaryMaxLufsProperty, value); }
    public double ShortTermLufs { get => GetValue(ShortTermLufsProperty); set => SetValue(ShortTermLufsProperty, value); }
    public double ShortTermMaxLufs { get => GetValue(ShortTermMaxLufsProperty); set => SetValue(ShortTermMaxLufsProperty, value); }
    public double IntegratedLufs { get => GetValue(IntegratedLufsProperty); set => SetValue(IntegratedLufsProperty, value); }
    public double PlrDb { get => GetValue(PlrDbProperty); set => SetValue(PlrDbProperty, value); }
    public double MidLevelDb { get => GetValue(MidLevelDbProperty); set => SetValue(MidLevelDbProperty, value); }
    public double SideLevelDb { get => GetValue(SideLevelDbProperty); set => SetValue(SideLevelDbProperty, value); }
    public bool IsMidSide { get => GetValue(IsMidSideProperty); set => SetValue(IsMidSideProperty, value); }
    public int MeterMode { get => GetValue(MeterModeProperty); set => SetValue(MeterModeProperty, value); }
    public double LoudnessRange { get => GetValue(LoudnessRangeProperty); set => SetValue(LoudnessRangeProperty, value); }
    public double LraLow { get => GetValue(LraLowProperty); set => SetValue(LraLowProperty, value); }
    public double LraHigh { get => GetValue(LraHighProperty); set => SetValue(LraHighProperty, value); }
    public int[]? SModeHistogram { get => GetValue(SModeHistogramProperty); set => SetValue(SModeHistogramProperty, value); }
    public int SModeMaxCount { get => GetValue(SModeMaxCountProperty); set => SetValue(SModeMaxCountProperty, value); }

    // Colors matching LevelMeterControl.java decompiled source
    private static readonly IBrush SModeHeaderBrush = new SolidColorBrush(Color.FromRgb(232, 154, 32)); // #E89A20 Orange S-Mode
    private static readonly IBrush MModeHeaderBrush = new SolidColorBrush(Color.FromRgb(0, 111, 150));  // #006F96 M-Mode
    private static readonly IBrush TplHeaderBrush = new SolidColorBrush(Color.FromRgb(0, 232, 111));    // #00E86F TPL

    private static readonly IBrush BarGreenBrush = new SolidColorBrush(Color.FromRgb(0x00, 0xAA, 0x00));  // #00AA00
    private static readonly IBrush BarOrangeBrush = new SolidColorBrush(Color.FromRgb(0xE8, 0x9A, 0x20)); // #E89A20 (Side bar in M/S)
    private static readonly IBrush BarLuBlueBrush = new SolidColorBrush(Color.FromRgb(0x00, 0x6F, 0x96));  // #006F96 Deep Cyan-Blue for LU

    private static readonly IBrush RmsGreenBrush = new SolidColorBrush(Color.FromRgb(0x00, 0xFF, 0x00));       // #00FF00 Bright Lime
    private static readonly IBrush PeakHoldGreenBrush = new SolidColorBrush(Color.FromRgb(0x00, 0xBB, 0x00));  // #00BB00
    private static readonly IBrush MaxPeakMintBrush = new SolidColorBrush(Color.FromRgb(0x00, 0xE8, 0x6F));    // #00E86F Mint
    private static readonly IBrush ClipRedBrush = new SolidColorBrush(Color.FromRgb(0xDD, 0x00, 0x00));        // #DD0000

    private static readonly IBrush LuShortTermOrangeBrush = new SolidColorBrush(Color.FromRgb(0xE8, 0x9A, 0x20)); // #E89A20
    private static readonly IBrush LuMaxMomentaryBrush = new SolidColorBrush(Color.FromRgb(0x00, 0x6F, 0x96));    // #006F96
    private static readonly IBrush LuIntegratedWhiteBrush = new SolidColorBrush(Color.FromRgb(0xEE, 0xEE, 0xEE)); // #EEEEEE

    private static readonly IPen PlrPen = new Pen(new SolidColorBrush(Color.FromRgb(0x55, 0xDD, 0xFF)), 3.0, lineCap: PenLineCap.Square); // #55DDFF

    private static readonly IPen RedGridPen = new Pen(new SolidColorBrush(Color.FromRgb(0xAA, 0x00, 0x00)), 1); // #AA0000
    private static readonly IPen DimGridPen = new Pen(new SolidColorBrush(Color.FromRgb(0x55, 0x55, 0x55)), 1); // #555555
    private static readonly IBrush ScaleRedBrush = new SolidColorBrush(Color.FromRgb(0xFF, 0x00, 0x00));        // #FF0000
    private static readonly IBrush ScaleGrayBrush = new SolidColorBrush(Color.FromRgb(0xEE, 0xEE, 0xEE));       // #EEEEEE
    private static readonly IBrush LabelGrayBrush = new SolidColorBrush(Color.FromRgb(0xEE, 0xEE, 0xEE));       // #EEEEEE
    private static readonly IBrush LabelRedBrush = new SolidColorBrush(Color.FromRgb(0xFF, 0x00, 0x00));        // #FF0000 for M/S

    private static readonly IPen RmsPen = new Pen(RmsGreenBrush, 2.0);
    private static readonly IPen PeakHoldNormalPen = new Pen(PeakHoldGreenBrush, 2.0);
    private static readonly IPen PeakHoldClipPen = new Pen(ClipRedBrush, 2.0);
    private static readonly IPen MaxPeakNormalPen = new Pen(MaxPeakMintBrush, 2.0);
    private static readonly IPen MaxPeakClipPen = new Pen(ClipRedBrush, 2.0);

    private static readonly IPen LuShortTermPen = new Pen(LuShortTermOrangeBrush, 2.0);
    private static readonly IPen LuMaxMomentaryPen = new Pen(LuMaxMomentaryBrush, 2.0);
    private static readonly IPen LuShortTermMaxWingPen = new Pen(LuShortTermOrangeBrush, 2.0);
    private static readonly IPen LuIntegratedPen = new Pen(LuIntegratedWhiteBrush, 2.0);

    private static readonly IPen AmberHistogramPen = new Pen(new SolidColorBrush(Color.FromRgb(232, 154, 32)), 1);
    private static readonly IPen BracketPen = new Pen(new SolidColorBrush(Color.FromRgb(255, 255, 255)), 1.5);

    private double _decayLeft = -60.0;
    private double _decayRight = -60.0;

    static SModeMeterControl()
    {
        AffectsRender<SModeMeterControl>(
            PeakLeftDbProperty, PeakRightDbProperty, MaxPeakLeftDbProperty, MaxPeakRightDbProperty,
            RmsLeftDbProperty, RmsRightDbProperty, MomentaryLufsProperty, MomentaryMaxLufsProperty,
            ShortTermLufsProperty, ShortTermMaxLufsProperty, IntegratedLufsProperty, PlrDbProperty,
            MidLevelDbProperty, SideLevelDbProperty, IsMidSideProperty, MeterModeProperty,
            LoudnessRangeProperty, LraLowProperty, LraHighProperty,
            SModeHistogramProperty, SModeMaxCountProperty);
    }

    protected override void OnPointerPressed(PointerPressedEventArgs e)
    {
        base.OnPointerPressed(e);
        var pt = e.GetPosition(this);
        if (pt.X > 0 && pt.X < 100)
        {
            // Toggle Stereo (L/R) vs Mid/Side (M/S) mode
            IsMidSide = !IsMidSide;
        }
        else if (pt.X >= 100)
        {
            // Cycle distribution histogram mode: S-Mode -> M-Mode -> TPL
            MeterMode = (MeterMode + 1) % 3;
        }
    }

    public override void Render(DrawingContext context)
    {
        double width = Bounds.Width;
        double height = Bounds.Height;
        if (width < 40 || height < 40) return;

        var tf = Typeface.Default;

        double topY = 24.0;
        double bottomY = height - 24.0;
        double meterH = bottomY - topY;

        // Exact exponential-log scale mapping from MusicScope LevelMeterControl.java:
        // d = 295 - (int)(72.5 * (Math.pow(10.0, (d + 60.0) / 90.0) - 1.0))
        double DbToY(double db)
        {
            double clamped = Math.Clamp(db, -60.0, 3.0);
            double f = Math.Pow(10.0, (clamped + 60.0) / 90.0) - 1.0;
            double norm = f / 4.011872336; // Math.Pow(10.0, 63.0 / 90.0) - 1.0
            return bottomY - norm * meterH;
        }

        // dB Scale grid lines & labels: 3, 0, -3, -6, -12, -20, -30, -40, -50, -60
        double[] dbMarks = [3.0, 0.0, -3.0, -6.0, -12.0, -20.0, -30.0, -40.0, -50.0, -60.0];
        string[] dbLabels = ["3", "0", "-3", "-6", "-12", "-20", "-30", "-40", "-50", "-60"];

        double gridLeft = 30.0;
        double gridRight = 89.0;

        // 1. Grid lines and dB Scale labels
        for (int i = 0; i < dbMarks.Length; i++)
        {
            double db = dbMarks[i];
            double y = DbToY(db);

            bool isRed = i <= 1; // 3 and 0 dB are red
            IPen linePen = isRed ? RedGridPen : DimGridPen;
            IBrush textBrush = isRed ? ScaleRedBrush : ScaleGrayBrush;

            context.DrawLine(linePen, new Point(gridLeft, y), new Point(gridRight, y));
            DrawTextRight(context, dbLabels[i], tf, 9, textBrush, gridLeft - 5, y - 5);
        }

        // 2. Active Channel Levels & Decay
        bool isMidSide = IsMidSide;
        double leftVal = isMidSide ? MidLevelDb : PeakLeftDb;
        double rightVal = isMidSide ? SideLevelDb : PeakRightDb;
        double leftDb = Math.Clamp(leftVal, -60.0, 3.0);
        double rightDb = Math.Clamp(rightVal, -60.0, 3.0);

        // Peak decay tracking (LevelMeterControl.java lines 345-367)
        if (leftDb >= _decayLeft)
            _decayLeft = leftDb;
        else
            _decayLeft = Math.Max(-60.0, _decayLeft - 0.4);

        if (rightDb >= _decayRight)
            _decayRight = rightDb;
        else
            _decayRight = Math.Max(-60.0, _decayRight - 0.4);

        double barL_X = 30.0;
        double barL_W = 10.0;
        double barPLR_X = 42.0;
        double barR_X = 45.0;
        double barR_W = 10.0;
        double barLU_X = 70.0;
        double barLU_W = 20.0;

        // Left channel bar
        double leftBarY = DbToY(leftDb);
        if (bottomY > leftBarY)
        {
            context.FillRectangle(BarGreenBrush, new Rect(barL_X, leftBarY, barL_W, bottomY - leftBarY));
        }

        // Right channel bar (Green in stereo, Orange in Mid/Side)
        IBrush rightBarBrush = isMidSide ? BarOrangeBrush : BarGreenBrush;
        double rightBarY = DbToY(rightDb);
        if (bottomY > rightBarY)
        {
            context.FillRectangle(rightBarBrush, new Rect(barR_X, rightBarY, barR_W, bottomY - rightBarY));
        }

        // RMS lines: LevelMeterControl.java lines 172-174 (x: 31..39 and 46..54)
        double rmsL = Math.Clamp(RmsLeftDb, -60.0, 3.0);
        double rmsR = Math.Clamp(RmsRightDb, -60.0, 3.0);
        double yRmsL = DbToY(rmsL);
        double yRmsR = DbToY(rmsR);
        context.DrawLine(RmsPen, new Point(barL_X + 1, yRmsL), new Point(barL_X + barL_W - 1, yRmsL));
        context.DrawLine(RmsPen, new Point(barR_X + 1, yRmsR), new Point(barR_X + barR_W - 1, yRmsR));

        // Decaying Peak Hold lines: LevelMeterControl.java lines 175-186
        if (_decayLeft > -59.5)
        {
            double yDecayL = DbToY(_decayLeft);
            IPen penDecayL = _decayLeft >= 0.05 ? PeakHoldClipPen : PeakHoldNormalPen;
            context.DrawLine(penDecayL, new Point(barL_X + 1, yDecayL), new Point(barL_X + barL_W - 1, yDecayL));
        }
        if (_decayRight > -59.5)
        {
            double yDecayR = DbToY(_decayRight);
            IPen penDecayR = _decayRight >= 0.05 ? PeakHoldClipPen : PeakHoldNormalPen;
            context.DrawLine(penDecayR, new Point(barR_X + 1, yDecayR), new Point(barR_X + barR_W - 1, yDecayR));
        }

        // Max Peak / True Peak hold lines: LevelMeterControl.java lines 187-198
        if (MaxPeakLeftDb > -59.5)
        {
            double yMaxL = DbToY(MaxPeakLeftDb);
            IPen penMaxL = MaxPeakLeftDb >= 0.05 ? MaxPeakClipPen : MaxPeakNormalPen;
            context.DrawLine(penMaxL, new Point(barL_X + 1, yMaxL), new Point(barL_X + barL_W - 1, yMaxL));
        }
        if (MaxPeakRightDb > -59.5)
        {
            double yMaxR = DbToY(MaxPeakRightDb);
            IPen penMaxR = MaxPeakRightDb >= 0.05 ? MaxPeakClipPen : MaxPeakNormalPen;
            context.DrawLine(penMaxR, new Point(barR_X + 1, yMaxR), new Point(barR_X + barR_W - 1, yMaxR));
        }

        // PLR Vertical Cyan Line between L and R: LevelMeterControl.java lines 132-137
        // In original Swing: graphics2D.drawLine(42, 31, 42, (int)d14 - 1); from 0 dB down to -PLR
        double y0 = DbToY(0.0);
        double yPlr = DbToY(-Math.Max(0.0, PlrDb));
        double endPlrY = Math.Max(y0 + 1, yPlr);
        context.DrawLine(PlrPen, new Point(barPLR_X, y0), new Point(barPLR_X, endPlrY));

        // 3. LU Bar & Loudness Indicators: LevelMeterControl.java lines 199-214
        // LU filled bar (Deep Cyan-Blue #006F96, Momentary loudness)
        double momDb = Math.Clamp(MomentaryLufs, -60.0, 3.0);
        double luBarY = DbToY(momDb);
        if (bottomY > luBarY)
        {
            context.FillRectangle(BarLuBlueBrush, new Rect(barLU_X, luBarY, barLU_W, bottomY - luBarY));
        }

        // Max Momentary loudness line across LU bar (x: 71..89, deep cyan-blue #006F96)
        if (MomentaryMaxLufs > -59.5)
        {
            double yMaxMom = DbToY(MomentaryMaxLufs);
            context.DrawLine(LuMaxMomentaryPen, new Point(barLU_X + 1, yMaxMom), new Point(barLU_X + barLU_W - 1, yMaxMom));
        }

        // Short-Term loudness line across LU bar (x: 71..89, orange #E89A20)
        double stDb = Math.Clamp(ShortTermLufs, -60.0, 3.0);
        double ySt = DbToY(stDb);
        context.DrawLine(LuShortTermPen, new Point(barLU_X + 1, ySt), new Point(barLU_X + barLU_W - 1, ySt));

        // Max Short-Term side wings (left: 65..70, right: 90..95, orange #E89A20)
        if (ShortTermMaxLufs > -59.5)
        {
            double yMaxSt = DbToY(ShortTermMaxLufs);
            context.DrawLine(LuShortTermMaxWingPen, new Point(barLU_X - 5, yMaxSt), new Point(barLU_X, yMaxSt));
            context.DrawLine(LuShortTermMaxWingPen, new Point(barLU_X + barLU_W, yMaxSt), new Point(barLU_X + barLU_W + 5, yMaxSt));
        }

        // Integrated Loudness center tick inside LU bar (x: 75..85, white #EEEEEE)
        if (IntegratedLufs > -59.5)
        {
            double yInt = DbToY(IntegratedLufs);
            context.DrawLine(LuIntegratedPen, new Point(barLU_X + 5, yInt), new Point(barLU_X + barLU_W - 5, yInt));
        }

        // 4. Bottom Channel Labels: LevelMeterControl.java lines 276-286
        IBrush channelLabelBrush = isMidSide ? LabelRedBrush : LabelGrayBrush;
        string leftLabel = isMidSide ? "M" : "L";
        string rightLabel = isMidSide ? "S" : "R";

        DrawCenteredText(context, leftLabel, tf, 11, channelLabelBrush, barL_X + barL_W / 2.0, bottomY + 4);
        DrawCenteredText(context, rightLabel, tf, 11, channelLabelBrush, barR_X + barR_W / 2.0, bottomY + 4);
        DrawCenteredText(context, "LU", tf, 11, LabelGrayBrush, barLU_X + barLU_W / 2.0, bottomY + 4);

        // 5. Histogram Section & Header: LevelMeterControl.java lines 216-274
        double histoLeft = 105.0;
        double maxHistoW = Math.Max(25.0, width - histoLeft - 18);

        string headerText = MeterMode switch
        {
            1 => "M-Mode",
            2 => "TPL",
            _ => "S-Mode"
        };
        IBrush headerBrush = MeterMode switch
        {
            1 => MModeHeaderBrush,
            2 => TplHeaderBrush,
            _ => SModeHeaderBrush
        };
        DrawTextRight(context, headerText, tf, 11, headerBrush, histoLeft + maxHistoW, 8);

        // Vertical baseline for histogram (LevelMeterControl.java line 255)
        context.DrawLine(AmberHistogramPen, new Point(histoLeft, DbToY(3.0)), new Point(histoLeft, bottomY));

        // Real-Time Distribution Histogram
        var hist = SModeHistogram;
        int maxCount = SModeMaxCount;

        if (hist != null && maxCount > 0)
        {
            for (int i = -600; i <= 30; i++)
            {
                int bin = i + 700;
                if ((uint)bin < (uint)hist.Length)
                {
                    int count = hist[bin];
                    if (count > 0)
                    {
                        double db = i / 10.0;
                        double y = DbToY(db);
                        double w = (double)count / maxCount * maxHistoW;
                        if (w > 0.5)
                        {
                            context.DrawLine(AmberHistogramPen, new Point(histoLeft, y), new Point(histoLeft + w, y));
                        }
                    }
                }
            }
        }

        // LRA I-Beam Bracket & Value (LevelMeterControl.java lines 238-247)
        if (LoudnessRange > 0.0 && LraHigh > -70.0 && LraLow > -70.0)
        {
            double bracketX = histoLeft + maxHistoW * 0.78;
            if (bracketX > width - 12)
                bracketX = width - 12;

            double yLow = DbToY(LraLow);   // 10th percentile
            double yHigh = DbToY(LraHigh); // 95th percentile

            // Horizontal ticks (width 10px: bracketX - 5 to bracketX + 5)
            context.DrawLine(BracketPen, new Point(bracketX - 5, yLow), new Point(bracketX + 5, yLow));
            context.DrawLine(BracketPen, new Point(bracketX - 5, yHigh), new Point(bracketX + 5, yHigh));

            // Vertical stem connecting ticks
            context.DrawLine(BracketPen, new Point(bracketX, yLow), new Point(bracketX, yHigh));

            // LRA Value text centered above the top tick (e.g. "6.9")
            string lraText = LoudnessRange.ToString("F1", CultureInfo.InvariantCulture);
            DrawCenteredText(context, lraText, tf, 11, LabelGrayBrush, bracketX, yHigh - 15);
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
