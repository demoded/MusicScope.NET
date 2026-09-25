using System;
using System.Globalization;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Media;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// S-Mode Precision Loudness &amp; LED Peak Meter with Real-Time Distribution Histogram and LRA bracket.
/// Directly models Box 3 of the MusicScope UI matching LevelMeterControl.java and LoudnessModule.java.
/// </summary>
public sealed class SModeMeterControl : Control
{
    public static readonly StyledProperty<double> PeakLeftDbProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(PeakLeftDb), -60.0);

    public static readonly StyledProperty<double> PeakRightDbProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(PeakRightDb), -60.0);

    public static readonly StyledProperty<double> MomentaryLufsProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(MomentaryLufs), -60.0);

    public static readonly StyledProperty<double> ShortTermLufsProperty =
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(ShortTermLufs), -60.0);

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
    public double MomentaryLufs { get => GetValue(MomentaryLufsProperty); set => SetValue(MomentaryLufsProperty, value); }
    public double ShortTermLufs { get => GetValue(ShortTermLufsProperty); set => SetValue(ShortTermLufsProperty, value); }
    public double LoudnessRange { get => GetValue(LoudnessRangeProperty); set => SetValue(LoudnessRangeProperty, value); }
    public double LraLow { get => GetValue(LraLowProperty); set => SetValue(LraLowProperty, value); }
    public double LraHigh { get => GetValue(LraHighProperty); set => SetValue(LraHighProperty, value); }
    public int[]? SModeHistogram { get => GetValue(SModeHistogramProperty); set => SetValue(SModeHistogramProperty, value); }
    public int SModeMaxCount { get => GetValue(SModeMaxCountProperty); set => SetValue(SModeMaxCountProperty, value); }

    private static readonly IBrush SModeHeaderBrush = new SolidColorBrush(Color.FromRgb(232, 154, 32)); // #E89A20 Orange S-Mode
    private static readonly IBrush RedBrush = new SolidColorBrush(Color.FromRgb(255, 40, 40));
    private static readonly IBrush GreenBrush = new SolidColorBrush(Color.FromRgb(0, 180, 0));
    private static readonly IBrush CyanBrush = new SolidColorBrush(Color.FromRgb(0, 229, 255));
    private static readonly IBrush AmberHistogramBrush = new SolidColorBrush(Color.FromRgb(232, 154, 32)); // #E89A20
    private static readonly IPen AmberHistogramPen = new Pen(AmberHistogramBrush, 1);
    private static readonly IBrush GridTextBrush = new SolidColorBrush(Color.FromRgb(220, 220, 220));
    private static readonly IBrush DimTextBrush = new SolidColorBrush(Color.FromRgb(90, 95, 100));
    private static readonly IPen RedGridPen = new Pen(new SolidColorBrush(Color.FromRgb(180, 0, 0)), 1);
    private static readonly IPen DimGridPen = new Pen(new SolidColorBrush(Color.FromRgb(55, 60, 65)), 1);
    private static readonly IPen BracketPen = new Pen(new SolidColorBrush(Color.FromRgb(255, 255, 255)), 1.5);

    static SModeMeterControl()
    {
        AffectsRender<SModeMeterControl>(
            PeakLeftDbProperty, PeakRightDbProperty, MomentaryLufsProperty,
            ShortTermLufsProperty, LoudnessRangeProperty, LraLowProperty, LraHighProperty,
            SModeHistogramProperty, SModeMaxCountProperty);
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
        double gridLeft = 28;
        double gridRight = width * 0.48;
        double histoLeft = gridRight + 2;
        double maxHistoW = Math.Max(25.0, width - histoLeft - 18);

        // Header: "S-Mode" in orange aligned to the right side of the S-Mode section
        DrawTextRight(context, "S-Mode", tf, 11, SModeHeaderBrush, histoLeft + maxHistoW, 8);

        for (int i = 0; i < dbMarks.Length; i++)
        {
            double db = dbMarks[i];
            double y = DbToY(db);

            bool isOver = db >= 0.0;
            IPen linePen = isOver ? RedGridPen : DimGridPen;
            IBrush textBrush = isOver ? RedBrush : (i == 1 ? GridTextBrush : (i <= 3 ? GridTextBrush : DimTextBrush));

            context.DrawLine(linePen, new Point(gridLeft, y), new Point(gridRight, y));
            DrawTextRight(context, db.ToString("F0"), tf, 9, textBrush, gridLeft - 4, y - 5);
        }

        // Peak / RMS indicators at 0 and -3 dB
        double y0 = DbToY(0.0);
        double yNeg3 = DbToY(-3.0);
        context.DrawLine(new Pen(CyanBrush, 2), new Point(gridLeft + 2, y0), new Point(gridLeft + 12, y0));
        context.DrawLine(new Pen(SModeHeaderBrush, 2), new Point(gridLeft + 20, yNeg3), new Point(gridLeft + 30, yNeg3));

        // Meter Bars: L, R, LU
        double barW = 6;
        double barL_X = gridLeft + 4;
        double barR_X = barL_X + barW + 4;
        double barLU_X = barR_X + barW + 8;

        // Left channel bar
        double leftDb = Math.Clamp(PeakLeftDb, -60.0, 3.0);
        double leftBarY = DbToY(leftDb);
        context.FillRectangle(GreenBrush, new Rect(barL_X, leftBarY, barW, Math.Max(0, bottomY - leftBarY)));

        // Right channel bar
        double rightDb = Math.Clamp(PeakRightDb, -60.0, 3.0);
        double rightBarY = DbToY(rightDb);
        context.FillRectangle(GreenBrush, new Rect(barR_X, rightBarY, barW, Math.Max(0, bottomY - rightBarY)));

        // LU (Loudness Unit) bar
        double luDb = Math.Clamp(ShortTermLufs, -60.0, 3.0);
        double luBarY = DbToY(luDb);
        context.FillRectangle(AmberHistogramBrush, new Rect(barLU_X, luBarY, barW + 4, Math.Max(0, bottomY - luBarY)));

        // Bottom channel labels: L, R, LU
        DrawText(context, "L", tf, 9, GridTextBrush, barL_X, bottomY + 4);
        DrawText(context, "R", tf, 9, GridTextBrush, barR_X, bottomY + 4);
        DrawText(context, "LU", tf, 9, GridTextBrush, barLU_X, bottomY + 4);

        // Vertical Orange Baseline for S-Mode histogram (LevelMeterControl.java line 255)
        context.DrawLine(AmberHistogramPen, new Point(histoLeft, DbToY(3.0)), new Point(histoLeft, DbToY(-60.0)));

        // Real-Time S-Mode Loudness Distribution Histogram (LevelMeterControl.java lines 229-236)
        var hist = SModeHistogram;
        int maxCount = SModeMaxCount;

        if (hist != null && maxCount > 0)
        {
            // Iterate from -60.0 dB to +3.0 dB in 0.1 dB steps
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
            DrawCenteredText(context, lraText, tf, 11, GridTextBrush, bracketX, yHigh - 15);
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
