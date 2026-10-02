using System;
using System.Globalization;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Input;
using Avalonia.Media;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// Shared frequency guide and hover readout for the spectrum and sonogram.
/// HoverFrequency can be bound between charts; the horizontal marker belongs only to the hovered chart.
/// </summary>
public abstract class FrequencyChartControl : Control
{
    public static readonly StyledProperty<double> SampleRateProperty =
        AvaloniaProperty.Register<FrequencyChartControl, double>(nameof(SampleRate), 44100.0);

    public static readonly StyledProperty<double?> HoverFrequencyProperty =
        AvaloniaProperty.Register<FrequencyChartControl, double?>(nameof(HoverFrequency));

    public double SampleRate
    {
        get => GetValue(SampleRateProperty);
        set => SetValue(SampleRateProperty, value);
    }

    public double? HoverFrequency
    {
        get => GetValue(HoverFrequencyProperty);
        set => SetValue(HoverFrequencyProperty, value);
    }

    protected double EffectiveSampleRate => double.IsFinite(SampleRate) && SampleRate > 0 ? SampleRate : 44100.0;
    protected abstract Rect PlotBounds { get; }
    protected abstract (double? LevelDb, double? CursorDb, string LevelName) GetLevels(Point position);
    protected virtual double FractionToFrequency(double fraction) => fraction * EffectiveSampleRate / 2;
    protected virtual double FrequencyToFraction(double frequency) => frequency / (EffectiveSampleRate / 2);

    private Point? _hoverPosition;
    private static readonly IPen GuidePen = new Pen(Brushes.Blue, 1);
    private static readonly IPen MarkerPen = new Pen(new SolidColorBrush(Color.Parse("#00EEEE")), 1);
    private static readonly IBrush LabelBackground = new SolidColorBrush(Color.FromArgb(235, 0, 0, 0));
    private static readonly IBrush LevelBrush = new SolidColorBrush(Color.Parse("#FFBF00"));
    private static readonly IBrush CursorBrush = new SolidColorBrush(Color.Parse("#00EEEE"));

    static FrequencyChartControl()
    {
        AffectsRender<FrequencyChartControl>(HoverFrequencyProperty, SampleRateProperty);
    }

    protected override void OnPointerMoved(PointerEventArgs e)
    {
        base.OnPointerMoved(e);
        var position = e.GetPosition(this);
        if (IsInPlot(position))
        {
            _hoverPosition = position;
            UpdateHoverFrequency();
            InvalidateVisual(); // A vertical-only move does not change HoverFrequency.
        }
        else
        {
            ClearHover();
        }
    }

    protected override void OnPointerExited(PointerEventArgs e)
    {
        base.OnPointerExited(e);
        ClearHover();
    }

    protected override void OnDetachedFromVisualTree(VisualTreeAttachmentEventArgs e)
    {
        ClearHover();
        base.OnDetachedFromVisualTree(e);
    }

    protected override void OnPropertyChanged(AvaloniaPropertyChangedEventArgs change)
    {
        base.OnPropertyChanged(change);
        if (change.Property == BoundsProperty || change.Property == SampleRateProperty)
            UpdateHoverFrequency();
    }

    private bool IsInPlot(Point position) => PlotBounds.Width > 0 && PlotBounds.Height > 0 && PlotBounds.Contains(position);

    protected void UpdateHoverFrequency()
    {
        if (_hoverPosition is not { } position) return;
        if (!IsInPlot(position))
        {
            ClearHover();
            return;
        }
        SetCurrentValue(HoverFrequencyProperty, FractionToFrequency((position.X - PlotBounds.X) / PlotBounds.Width));
    }

    private void ClearHover()
    {
        if (_hoverPosition == null) return; // Do not clear a guide owned by the other chart.
        _hoverPosition = null;
        SetCurrentValue(HoverFrequencyProperty, null);
        InvalidateVisual();
    }

    internal ChartHoverReadout? GetHoverReadout()
    {
        if (_hoverPosition is not { } position || !IsInPlot(position)) return null;
        var (level, cursor, name) = GetLevels(position);
        double frequency = FractionToFrequency((position.X - PlotBounds.X) / PlotBounds.Width);
        return new ChartHoverReadout(frequency, level, cursor, name);
    }

    protected void DrawHover(DrawingContext context)
    {
        Rect plot = PlotBounds;
        if (plot.Width <= 0 || plot.Height <= 0 || HoverFrequency is not { } frequency ||
            !double.IsFinite(frequency) || frequency < 0 || frequency > EffectiveSampleRate / 2) return;

        using var clip = context.PushClip(plot);
        double x = plot.X + FrequencyToFraction(frequency) * plot.Width;
        context.DrawLine(GuidePen, new Point(x, plot.Top), new Point(x, plot.Bottom));

        if (GetHoverReadout() is not { } readout || _hoverPosition is not { } position) return;
        context.DrawLine(MarkerPen, new Point(x - 10, position.Y), new Point(x + 10, position.Y));

        FormattedText Format(string text, IBrush brush) => new(text, CultureInfo.InvariantCulture,
            FlowDirection.LeftToRight, Typeface.Default, 11, brush);
        var frequencyText = Format(readout.FrequencyHz.ToString("F1", CultureInfo.InvariantCulture) + " Hz", Brushes.White);
        var levelText = Format(readout.LevelName + ": " + FormatDb(readout.LevelDb), LevelBrush);
        var cursorText = readout.CursorDb is { } cursorDb ? Format("Cursor: " + FormatDb(cursorDb), CursorBrush) : null;
        double labelWidth = Math.Max(frequencyText.Width, Math.Max(levelText.Width, cursorText?.Width ?? 0)) + 10;
        double labelHeight = cursorText == null ? 36 : 50;
        double labelX = x + 14;
        if (labelX + labelWidth > plot.Right) labelX = x - labelWidth - 14;
        labelX = Math.Clamp(labelX, plot.Left, Math.Max(plot.Left, plot.Right - labelWidth));
        double labelY = position.Y + 14;
        if (labelY + labelHeight > plot.Bottom) labelY = position.Y - labelHeight - 14;
        labelY = Math.Clamp(labelY, plot.Top, Math.Max(plot.Top, plot.Bottom - labelHeight));
        context.FillRectangle(LabelBackground, new Rect(labelX, labelY, labelWidth, labelHeight));
        context.DrawText(frequencyText, new Point(labelX + 5, labelY + 3));
        context.DrawText(levelText, new Point(labelX + 5, labelY + 17));
        if (cursorText != null) context.DrawText(cursorText, new Point(labelX + 5, labelY + 31));
    }

    private static string FormatDb(double? db) => db switch
    {
        null => "No data",
        double.NegativeInfinity => "−∞ dB",
        { } value when double.IsFinite(value) => value.ToString("F1", CultureInfo.InvariantCulture) + " dB",
        _ => "No data"
    };
}

internal readonly record struct ChartHoverReadout(double FrequencyHz, double? LevelDb, double? CursorDb, string LevelName);
