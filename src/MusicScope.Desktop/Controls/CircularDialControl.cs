using System;
using System.Globalization;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Media;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// Circular dial meter control for Integrated Loudness (LUFS) and Loudness Range (LRA).
/// Replicates MusicScope's signature circular loudness meter.
/// </summary>
public sealed class CircularDialControl : Control
{
    public static readonly StyledProperty<double> ValueProperty =
        AvaloniaProperty.Register<CircularDialControl, double>(nameof(Value), -70.0);

    public static readonly StyledProperty<double> MinValueProperty =
        AvaloniaProperty.Register<CircularDialControl, double>(nameof(MinValue), -60.0);

    public static readonly StyledProperty<double> MaxValueProperty =
        AvaloniaProperty.Register<CircularDialControl, double>(nameof(MaxValue), 0.0);

    public static readonly StyledProperty<string> LabelProperty =
        AvaloniaProperty.Register<CircularDialControl, string>(nameof(Label), "INTEGRATED");

    public static readonly StyledProperty<string> UnitProperty =
        AvaloniaProperty.Register<CircularDialControl, string>(nameof(Unit), "LUFS");

    public double Value
    {
        get => GetValue(ValueProperty);
        set => SetValue(ValueProperty, value);
    }

    public double MinValue
    {
        get => GetValue(MinValueProperty);
        set => SetValue(MinValueProperty, value);
    }

    public double MaxValue
    {
        get => GetValue(MaxValueProperty);
        set => SetValue(MaxValueProperty, value);
    }

    public string Label
    {
        get => GetValue(LabelProperty);
        set => SetValue(LabelProperty, value);
    }

    public string Unit
    {
        get => GetValue(UnitProperty);
        set => SetValue(UnitProperty, value);
    }

    private static readonly IBrush BgBrush = new SolidColorBrush(Color.FromRgb(15, 18, 22));
    private static readonly IBrush ArcBgBrush = new SolidColorBrush(Color.FromRgb(35, 40, 48));
    private static readonly IBrush ValueBrush = new SolidColorBrush(Color.FromRgb(0, 225, 255));
    private static readonly IBrush TextBrush = new SolidColorBrush(Color.FromRgb(140, 155, 170));
    private static readonly IBrush NumberBrush = new SolidColorBrush(Color.FromRgb(240, 245, 250));

    static CircularDialControl()
    {
        AffectsRender<CircularDialControl>(ValueProperty, MinValueProperty, MaxValueProperty, LabelProperty, UnitProperty);
    }

    public override void Render(DrawingContext context)
    {
        double width = Bounds.Width;
        double height = Bounds.Height;
        if (width < 30 || height < 30)
            return;

        context.FillRectangle(BgBrush, new Rect(0, 0, width, height));

        double centerX = width / 2.0;
        double centerY = height / 2.0;
        double radius = Math.Min(centerX, centerY) - 8;
        if (radius < 10) return;

        // Circular background track (240 degree sweep: from 150 deg to 390 deg)
        const double startAngleDeg = 150.0;
        const double sweepAngleDeg = 240.0;
        const double strokeWidth = 6.0;

        var arcBgPen = new Pen(ArcBgBrush, strokeWidth, lineCap: PenLineCap.Round);
        context.DrawEllipse(null, arcBgPen, new Point(centerX, centerY), radius, radius);

        // Active value arc
        double clamped = Math.Clamp(Value, MinValue, MaxValue);
        double fraction = (clamped - MinValue) / (MaxValue - MinValue);

        Color arcColor = Value switch
        {
            >= -14.0 => Color.FromRgb(255, 160, 40), // Loud (Streaming target -14 LUFS)
            >= -24.0 => Color.FromRgb(0, 225, 255),  // Target EBU R128 (-23 LUFS)
            _ => Color.FromRgb(0, 160, 220)          // Quiet
        };
        var valuePen = new Pen(new SolidColorBrush(arcColor), strokeWidth + 1, lineCap: PenLineCap.Round);

        if (fraction > 0.02)
        {
            double startRad = startAngleDeg * Math.PI / 180.0;
            double currentAngleRad = (startAngleDeg + fraction * sweepAngleDeg) * Math.PI / 180.0;
            var arcGeom = new StreamGeometry();
            using (var ctx = arcGeom.Open())
            {
                ctx.BeginFigure(new Point(centerX + radius * Math.Cos(startRad), centerY + radius * Math.Sin(startRad)), false);
                ctx.ArcTo(
                    new Point(centerX + radius * Math.Cos(currentAngleRad), centerY + radius * Math.Sin(currentAngleRad)),
                    new Size(radius, radius),
                    0,
                    fraction * sweepAngleDeg > 180,
                    SweepDirection.Clockwise);
                ctx.EndFigure(false);
            }
            context.DrawGeometry(null, valuePen, arcGeom);
        }

        // Inner values text
        string valStr = Value > MinValue ? $"{Value:F1}" : "--";
        var numFt = new FormattedText(valStr, CultureInfo.InvariantCulture, FlowDirection.LeftToRight, Typeface.Default, 16, NumberBrush);
        context.DrawText(numFt, new Point(centerX - numFt.Width / 2.0, centerY - 14));

        var unitFt = new FormattedText(Unit, CultureInfo.InvariantCulture, FlowDirection.LeftToRight, Typeface.Default, 9, ValueBrush);
        context.DrawText(unitFt, new Point(centerX - unitFt.Width / 2.0, centerY + 4));

        var labelFt = new FormattedText(Label, CultureInfo.InvariantCulture, FlowDirection.LeftToRight, Typeface.Default, 8, TextBrush);
        context.DrawText(labelFt, new Point(centerX - labelFt.Width / 2.0, centerY + radius - 10));
    }
}
