using System;
using System.Globalization;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Media;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// S-Mode Precision Loudness & LED Peak Meter with Distribution Histogram and LRA bracket.
/// Directly models Box 3 of the MusicScope UI.
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
        AvaloniaProperty.Register<SModeMeterControl, double>(nameof(LoudnessRange), 6.9);

    public double PeakLeftDb { get => GetValue(PeakLeftDbProperty); set => SetValue(PeakLeftDbProperty, value); }
    public double PeakRightDb { get => GetValue(PeakRightDbProperty); set => SetValue(PeakRightDbProperty, value); }
    public double MomentaryLufs { get => GetValue(MomentaryLufsProperty); set => SetValue(MomentaryLufsProperty, value); }
    public double ShortTermLufs { get => GetValue(ShortTermLufsProperty); set => SetValue(ShortTermLufsProperty, value); }
    public double LoudnessRange { get => GetValue(LoudnessRangeProperty); set => SetValue(LoudnessRangeProperty, value); }

    private static readonly IBrush SModeHeaderBrush = new SolidColorBrush(Color.FromRgb(255, 166, 87)); // Orange S-Mode
    private static readonly IBrush RedBrush = new SolidColorBrush(Color.FromRgb(255, 40, 40));
    private static readonly IBrush GreenBrush = new SolidColorBrush(Color.FromRgb(0, 180, 0));
    private static readonly IBrush CyanBrush = new SolidColorBrush(Color.FromRgb(0, 229, 255));
    private static readonly IBrush AmberHistogramBrush = new SolidColorBrush(Color.FromRgb(232, 154, 32));
    private static readonly IBrush GridTextBrush = new SolidColorBrush(Color.FromRgb(220, 220, 220));
    private static readonly IBrush DimTextBrush = new SolidColorBrush(Color.FromRgb(90, 95, 100));
    private static readonly IPen RedGridPen = new Pen(new SolidColorBrush(Color.FromRgb(180, 0, 0)), 1);
    private static readonly IPen DimGridPen = new Pen(new SolidColorBrush(Color.FromRgb(55, 60, 65)), 1);
    private static readonly IPen BracketPen = new Pen(new SolidColorBrush(Color.FromRgb(255, 255, 255)), 1.5);

    // Pre-allocated simulated/accumulated loudness distribution bell curve around typical -14 to -24 LUFS
    private static readonly double[] HistogramWeights = GenerateHistogram();

    private static double[] GenerateHistogram()
    {
        double[] h = new double[70]; // -60 to +10 dB
        for (int i = 0; i < h.Length; i++)
        {
            double db = -60 + i;
            // Bell curve centered around -16 dB
            double dist = (db - (-16.0)) / 6.0;
            h[i] = Math.Exp(-0.5 * dist * dist);
        }
        return h;
    }

    static SModeMeterControl()
    {
        AffectsRender<SModeMeterControl>(
            PeakLeftDbProperty, PeakRightDbProperty, MomentaryLufsProperty,
            ShortTermLufsProperty, LoudnessRangeProperty);
    }

    public override void Render(DrawingContext context)
    {
        double width = Bounds.Width;
        double height = Bounds.Height;
        if (width < 40 || height < 40) return;

        var tf = Typeface.Default;

        // Header: S-Mode
        var ftSMode = new FormattedText("S-Mode", CultureInfo.InvariantCulture, FlowDirection.LeftToRight, tf, 11, SModeHeaderBrush);
        context.DrawText(ftSMode, new Point(width * 0.45, 8));

        double topY = 24.0;
        double bottomY = height - 24.0;
        double meterH = bottomY - topY;

        double DbToY(double db)
        {
            // MusicScope log scale: 3 dB at top, -60 dB at bottom
            double clamped = Math.Clamp(db, -60.0, 3.0);
            double norm = (3.0 - clamped) / 63.0;
            return topY + norm * meterH;
        }

        // dB Scale grid lines & labels: 3, 0, -3, -6, -12, -20, -30, -40, -50, -60
        double[] dbMarks = [3.0, 0.0, -3.0, -6.0, -12.0, -20.0, -30.0, -40.0, -50.0, -60.0];
        double gridLeft = 32;
        double gridRight = width * 0.38;

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

        // Loudness Distribution Histogram (Horizontal amber lines)
        double histoLeft = width * 0.44;
        double histoMaxW = width * 0.35;

        for (int i = 0; i < HistogramWeights.Length; i++)
        {
            double db = -60 + i;
            double y = DbToY(db);
            double w = HistogramWeights[i] * histoMaxW;
            if (w > 1.0)
            {
                context.DrawLine(new Pen(AmberHistogramBrush, 1.5), new Point(histoLeft, y), new Point(histoLeft + w, y));
            }
        }

        // LRA Bracket (White vertical bracket with label)
        double lraCenterDb = -16.0;
        double lraSpan = Math.Max(2.0, LoudnessRange);
        double lraTopY = DbToY(lraCenterDb + lraSpan * 0.5);
        double lraBotY = DbToY(lraCenterDb - lraSpan * 0.5);
        double bracketX = histoLeft + histoMaxW + 8;

        context.DrawLine(BracketPen, new Point(bracketX - 4, lraTopY), new Point(bracketX, lraTopY));
        context.DrawLine(BracketPen, new Point(bracketX, lraTopY), new Point(bracketX, lraBotY));
        context.DrawLine(BracketPen, new Point(bracketX - 4, lraBotY), new Point(bracketX, lraBotY));

        // LRA Number
        string lraText = LoudnessRange.ToString("F1", CultureInfo.InvariantCulture);
        DrawCenteredText(context, lraText, tf, 10, GridTextBrush, bracketX, lraTopY - 14);
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
