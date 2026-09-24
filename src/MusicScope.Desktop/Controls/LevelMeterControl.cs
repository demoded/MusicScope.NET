using System;
using System.Globalization;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Media;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// Precision vertical LED-style Level Meter displaying Sample Peak, True Peak, and RMS.
/// Includes dBTP over-peak clipping indicators.
/// </summary>
public sealed class LevelMeterControl : Control
{
    public static readonly StyledProperty<double> PeakDbProperty =
        AvaloniaProperty.Register<LevelMeterControl, double>(nameof(PeakDb), -100.0);

    public static readonly StyledProperty<double> TruePeakDbProperty =
        AvaloniaProperty.Register<LevelMeterControl, double>(nameof(TruePeakDb), -100.0);

    public static readonly StyledProperty<double> RmsDbProperty =
        AvaloniaProperty.Register<LevelMeterControl, double>(nameof(RmsDb), -100.0);

    public static readonly StyledProperty<string> ChannelNameProperty =
        AvaloniaProperty.Register<LevelMeterControl, string>(nameof(ChannelName), "L");

    public double PeakDb
    {
        get => GetValue(PeakDbProperty);
        set => SetValue(PeakDbProperty, value);
    }

    public double TruePeakDb
    {
        get => GetValue(TruePeakDbProperty);
        set => SetValue(TruePeakDbProperty, value);
    }

    public double RmsDb
    {
        get => GetValue(RmsDbProperty);
        set => SetValue(RmsDbProperty, value);
    }

    public string ChannelName
    {
        get => GetValue(ChannelNameProperty);
        set => SetValue(ChannelNameProperty, value);
    }

    private static readonly IBrush BgBrush = new SolidColorBrush(Color.FromRgb(20, 24, 28));
    private static readonly IBrush TextBrush = new SolidColorBrush(Color.FromRgb(140, 155, 170));
    private static readonly IBrush PeakLineBrush = new SolidColorBrush(Color.FromRgb(255, 255, 255));
    private static readonly IBrush TruePeakBrush = new SolidColorBrush(Color.FromRgb(255, 80, 80));

    static LevelMeterControl()
    {
        AffectsRender<LevelMeterControl>(PeakDbProperty, TruePeakDbProperty, RmsDbProperty, ChannelNameProperty);
    }

    public override void Render(DrawingContext context)
    {
        double width = Bounds.Width;
        double height = Bounds.Height;
        if (width < 16 || height < 40)
            return;

        context.FillRectangle(BgBrush, new Rect(0, 0, width, height));

        double meterTop = 16.0;
        double meterBottom = height - 20.0;
        double meterHeight = meterBottom - meterTop;

        const double minDb = -60.0;
        const double maxDb = 3.0; // allows +3 dBTP inter-sample over-peaks

        // Channel label at top
        var chFt = new FormattedText(ChannelName, CultureInfo.InvariantCulture, FlowDirection.LeftToRight, Typeface.Default, 10, TextBrush);
        context.DrawText(chFt, new Point((width - chFt.Width) / 2.0, 1));

        // Draw segmented LED meter bar for RMS
        int segments = 40;
        double segHeight = meterHeight / segments;

        double currentRms = Math.Clamp(RmsDb, minDb, maxDb);
        double rmsFraction = (currentRms - minDb) / (maxDb - minDb);
        int activeSegments = (int)(rmsFraction * segments);

        for (int i = 0; i < segments; i++)
        {
            // segment index from bottom to top
            double segDb = minDb + ((double)i / segments) * (maxDb - minDb);
            double y = meterBottom - (i + 1) * segHeight;

            Color segColor = segDb switch
            {
                >= 0.0 => Color.FromRgb(255, 40, 40),    // Red (Over 0 dBFS / Clipping)
                >= -6.0 => Color.FromRgb(255, 180, 0),  // Orange/Yellow (Hot)
                >= -18.0 => Color.FromRgb(220, 220, 50), // Lime
                _ => Color.FromRgb(0, 200, 100)          // Green (Safe)
            };

            bool isActive = i < activeSegments;
            IBrush brush = isActive ? new SolidColorBrush(segColor) : new SolidColorBrush(Color.FromArgb(25, segColor.R, segColor.G, segColor.B));
            context.FillRectangle(brush, new Rect(3, y + 0.5, width - 6, segHeight - 1));
        }

        // Draw Peak hold line
        double clampedPeak = Math.Clamp(PeakDb, minDb, maxDb);
        double peakY = meterBottom - ((clampedPeak - minDb) / (maxDb - minDb)) * meterHeight;
        context.FillRectangle(PeakLineBrush, new Rect(2, peakY - 1, width - 4, 2));

        // Draw True Peak indicator dot / mark
        if (TruePeakDb > minDb)
        {
            double clampedTp = Math.Clamp(TruePeakDb, minDb, maxDb);
            double tpY = meterBottom - ((clampedTp - minDb) / (maxDb - minDb)) * meterHeight;
            context.FillRectangle(TruePeakBrush, new Rect(1, tpY - 1.5, width - 2, 3));
        }

        // Numeric Peak value at bottom
        string peakValStr = PeakDb > minDb ? $"{PeakDb:F1}" : "-inf";
        var valFt = new FormattedText(peakValStr, CultureInfo.InvariantCulture, FlowDirection.LeftToRight, Typeface.Default, 8, TextBrush);
        context.DrawText(valFt, new Point((width - valFt.Width) / 2.0, height - 15));
    }
}
