using System;
using System.Globalization;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Media;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// Circular Polar History Radar Dial directly modeling Box 4 of the MusicScope UI.
/// Plots the entire track's peak/RMS and loudness history radially around concentric dB rings (3 to -60 dB).
/// </summary>
public sealed class HistoryDialControl : Control
{
    public static readonly StyledProperty<double[]> PeakHistoryProperty =
        AvaloniaProperty.Register<HistoryDialControl, double[]>(nameof(PeakHistory), InitializeHistory(-60.0));

    public static readonly StyledProperty<double[]> LoudnessHistoryProperty =
        AvaloniaProperty.Register<HistoryDialControl, double[]>(nameof(LoudnessHistory), InitializeHistory(-60.0));

    public static readonly StyledProperty<double> TrackProgressProperty =
        AvaloniaProperty.Register<HistoryDialControl, double>(nameof(TrackProgress), 0.0);

    public double[] PeakHistory
    {
        get => GetValue(PeakHistoryProperty);
        set => SetValue(PeakHistoryProperty, value);
    }

    public double[] LoudnessHistory
    {
        get => GetValue(LoudnessHistoryProperty);
        set => SetValue(LoudnessHistoryProperty, value);
    }

    public double TrackProgress
    {
        get => GetValue(TrackProgressProperty);
        set => SetValue(TrackProgressProperty, value);
    }

    private static double[] InitializeHistory(double defaultDb)
    {
        double[] arr = new double[512];
        Array.Fill(arr, defaultDb);
        return arr;
    }

    private static readonly IBrush HeaderBrush = new SolidColorBrush(Color.FromRgb(0, 220, 0)); // Green History
    private static readonly IBrush LabelBrush = new SolidColorBrush(Color.FromRgb(220, 220, 220));
    private static readonly IBrush DimLabelBrush = new SolidColorBrush(Color.FromRgb(140, 145, 150));
    private static readonly IBrush RedRingBrush = new SolidColorBrush(Color.FromRgb(180, 0, 0));
    private static readonly IBrush GreenWaveformBrush = new SolidColorBrush(Color.FromRgb(0, 200, 50));
    private static readonly IBrush OrangeWaveformBrush = new SolidColorBrush(Color.FromRgb(232, 154, 32));
    private static readonly IBrush ScaleWhiteBrush = new SolidColorBrush(Color.FromRgb(240, 243, 246)); // Bright white scale numbers
    private static readonly IBrush ScaleRedBrush = new SolidColorBrush(Color.FromRgb(255, 60, 60));     // Bright red for 3 and 0 dB
    private static readonly IBrush DialBackgroundBrush = new SolidColorBrush(Color.FromRgb(0, 0, 0));
    private static readonly Typeface ScaleTypeface = new Typeface(FontFamily.Default, FontStyle.Normal, FontWeight.SemiBold);
    private static readonly IPen RedOuterPen = new Pen(RedRingBrush, 1.5);
    private static readonly IPen RedZeroPen = new Pen(RedRingBrush, 1.0);
    private static readonly IPen RingPen = new Pen(new SolidColorBrush(Color.FromRgb(45, 50, 55)), 1);
    private static readonly IPen SpokePen = new Pen(new SolidColorBrush(Color.FromRgb(35, 40, 45)), 1);
    private static readonly IPen GreenWaveformPen = new Pen(GreenWaveformBrush, 1.2);
    private static readonly IPen RedWaveformPen = new Pen(new SolidColorBrush(Color.FromRgb(255, 0, 0)), 1.2);
    private static readonly IPen OrangeWaveformPen = new Pen(OrangeWaveformBrush, 1.2);
    private static readonly IPen NeedlePen = new Pen(LabelBrush, 1);

    private static readonly double[] RingDbs = [3.0, 0.0, -6.0, -12.0, -24.0, -40.0, -60.0];
    private static readonly double MaxFactor = Math.Pow(10.0, (3.0 + 60.0) / 90.0) - 1.0;
    private static readonly double[] RingNorms = InitializeRingNorms();
    private static readonly (double Cos, double Sin)[] UnitAngles = InitializeUnitAngles();

    private static readonly FormattedText HeaderText = CreateText("History", Typeface.Default, 11, HeaderBrush);
    private static readonly FormattedText PlText = CreateText("P/L", Typeface.Default, 10, LabelBrush);
    private static readonly FormattedText PlrText = CreateText("PLR", Typeface.Default, 10, DimLabelBrush);
    private static readonly FormattedText[] RingLabelTexts =
    [
        CreateText("3", ScaleTypeface, 10.5, ScaleRedBrush),
        CreateText("0", ScaleTypeface, 10.5, ScaleRedBrush),
        CreateText("-6", ScaleTypeface, 10.5, ScaleWhiteBrush),
        CreateText("-12", ScaleTypeface, 10.5, ScaleWhiteBrush),
        CreateText("-24", ScaleTypeface, 10.5, ScaleWhiteBrush),
        CreateText("-40", ScaleTypeface, 10.5, ScaleWhiteBrush),
        CreateText("-60", ScaleTypeface, 10.5, ScaleWhiteBrush),
    ];

    private static FormattedText CreateText(string text, Typeface tf, double size, IBrush brush) =>
        new(text, CultureInfo.InvariantCulture, FlowDirection.LeftToRight, tf, size, brush);

    private static double[] InitializeRingNorms()
    {
        var norms = new double[RingDbs.Length];
        for (int i = 0; i < RingDbs.Length; i++)
        {
            norms[i] = (Math.Pow(10.0, (RingDbs[i] + 60.0) / 90.0) - 1.0) / MaxFactor;
        }
        return norms;
    }

    private static (double Cos, double Sin)[] InitializeUnitAngles()
    {
        var angles = new (double, double)[512];
        for (int i = 0; i < 512; i++)
        {
            double angleRad = (i / 512.0) * (2.0 * Math.PI) - Math.PI / 2.0;
            angles[i] = (Math.Cos(angleRad), Math.Sin(angleRad));
        }
        return angles;
    }

    static HistoryDialControl()
    {
        AffectsRender<HistoryDialControl>(PeakHistoryProperty, LoudnessHistoryProperty, TrackProgressProperty);
    }

    public override void Render(DrawingContext context)
    {
        double width = Bounds.Width;
        double height = Bounds.Height;
        if (width < 40 || height < 40) return;

        var tf = Typeface.Default;

        // Labels
        context.DrawText(HeaderText, new Point(8, 8));
        context.DrawText(PlText, new Point(8, 26));
        context.DrawText(PlrText, new Point(8, height - 20));

        // Center of circle
        double centerX = width / 2.0;
        double centerY = height / 2.0;
        double maxRadius = Math.Min(centerX, centerY) - 10;
        if (maxRadius < 15) return;

        // Formula from decompiled CircleControl.java:
        // radius = maxRadius * (Math.Pow(10.0, (dB + 60.0) / 90.0) - 1.0) / (Math.Pow(10.0, (3.0 + 60.0) / 90.0) - 1.0);
        double invMaxFactor = maxRadius / MaxFactor;
        double DbToRadius(double db)
        {
            double clamped = db < -60.0 ? -60.0 : (db > 3.0 ? 3.0 : db);
            double factor = Math.Pow(10.0, (clamped + 60.0) / 90.0) - 1.0;
            double r = factor * invMaxFactor;
            return r > 0.0 ? r : 0.0;
        }

        // Concentric rings & 12 o'clock labels: 3, 0, -6, -12, -24, -40, -60
        for (int i = 0; i < RingDbs.Length; i++)
        {
            double r = maxRadius * RingNorms[i];
            if (r > 1)
            {
                IPen pen = i == 0 ? RedOuterPen : (i == 1 ? RedZeroPen : RingPen);
                context.DrawEllipse(null, pen, new Point(centerX, centerY), r, r);
            }
        }

        // Radial crosshair spokes
        context.DrawLine(SpokePen, new Point(centerX - maxRadius, centerY), new Point(centerX + maxRadius, centerY));
        context.DrawLine(SpokePen, new Point(centerX, centerY - maxRadius), new Point(centerX, centerY + maxRadius));

        // Draw Peak History (Green / Red when > 0 dB) & Loudness History (Amber) around the circle
        double[] peaks = PeakHistory;
        double[] loudness = LoudnessHistory;
        int points = Math.Min(peaks.Length, loudness.Length);
        if (points < 2) return;

        int pointsToDraw = TrackProgress >= 1.0 ? points : Math.Clamp((int)(TrackProgress * points), 0, points);
        if (pointsToDraw < 2) return;

        bool useUnitTable = points == 512;
        var loudGeom = new StreamGeometry();
        var greenGeom = new StreamGeometry();
        bool hasRedSegments = false;

        using (var lc = loudGeom.Open())
        using (var gc = greenGeom.Open())
        {
            for (int i = 0; i < pointsToDraw; i++)
            {
                double cos, sin;
                if (useUnitTable)
                {
                    (cos, sin) = UnitAngles[i];
                }
                else
                {
                    double angleRad = (i / (double)points) * (2.0 * Math.PI) - Math.PI / 2.0;
                    cos = Math.Cos(angleRad);
                    sin = Math.Sin(angleRad);
                }

                double pDb = peaks[i];
                double lDb = loudness[i];

                double rPeak = DbToRadius(pDb);
                double rLoud = DbToRadius(lDb);

                Point ptPeak = new Point(centerX + rPeak * cos, centerY + rPeak * sin);
                Point ptLoud = new Point(centerX + rLoud * cos, centerY + rLoud * sin);

                if (i == 0)
                {
                    lc.BeginFigure(ptLoud, false);
                    gc.BeginFigure(ptPeak, false);
                }
                else
                {
                    lc.LineTo(ptLoud);
                    gc.LineTo(ptPeak);
                    if (pDb > 0.0) hasRedSegments = true;
                }
            }
            lc.EndFigure(false);
            gc.EndFigure(false);
        }

        context.DrawGeometry(null, OrangeWaveformPen, loudGeom);
        context.DrawGeometry(null, GreenWaveformPen, greenGeom);

        if (hasRedSegments)
        {
            Point? prevPt = null;
            for (int i = 0; i < pointsToDraw; i++)
            {
                double cos, sin;
                if (useUnitTable)
                    (cos, sin) = UnitAngles[i];
                else
                {
                    double angleRad = (i / (double)points) * (2.0 * Math.PI) - Math.PI / 2.0;
                    cos = Math.Cos(angleRad);
                    sin = Math.Sin(angleRad);
                }
                double pDb = peaks[i];
                double rPeak = DbToRadius(pDb);
                Point ptPeak = new Point(centerX + rPeak * cos, centerY + rPeak * sin);
                if (prevPt.HasValue && pDb > 0.0)
                {
                    context.DrawLine(RedWaveformPen, prevPt.Value, ptPeak);
                }
                prevPt = ptPeak;
            }
        }

        // Current track progress needle (subtle spoke while actively analyzing)
        if (TrackProgress > 0.0 && TrackProgress < 1.0)
        {
            double curAngle = TrackProgress * (2.0 * Math.PI) - Math.PI / 2.0;
            Point needleEnd = new Point(centerX + maxRadius * Math.Cos(curAngle), centerY + maxRadius * Math.Sin(curAngle));
            context.DrawLine(NeedlePen, new Point(centerX, centerY), needleEnd);
        }

        // Scale labels at 12 o'clock drawn on TOP of rings, spokes, and waveforms (center-aligned vertically along radial axis)
        for (int i = 0; i < RingDbs.Length; i++)
        {
            double r = maxRadius * RingNorms[i];
            var ft = RingLabelTexts[i];

            double textX = centerX - ft.Width / 2.0;
            double textY = centerY - r - ft.Height / 2.0;

            if (i == 0)
            {
                textY = Math.Max(2.0, textY);
            }

            // Draw clean background cutout so numbers are never crossed or obscured by radial lines or waveforms
            context.FillRectangle(DialBackgroundBrush, new Rect(textX - 2, textY - 1, ft.Width + 4, ft.Height + 2));
            context.DrawText(ft, new Point(textX, textY));
        }
    }

    private static void DrawText(DrawingContext ctx, string text, Typeface tf, double size, IBrush brush, double x, double y)
    {
        var ft = new FormattedText(text, CultureInfo.InvariantCulture, FlowDirection.LeftToRight, tf, size, brush);
        ctx.DrawText(ft, new Point(x, y));
    }

    private static void DrawCenteredText(DrawingContext ctx, string text, Typeface tf, double size, IBrush brush, double x, double y)
    {
        var ft = new FormattedText(text, CultureInfo.InvariantCulture, FlowDirection.LeftToRight, tf, size, brush);
        ctx.DrawText(ft, new Point(x - ft.Width / 2.0, y));
    }
}
