using System;
using System.Globalization;
using Avalonia;
using Avalonia.Input;
using Avalonia.Media;
using MusicScope.Core.DSP;

namespace MusicScope.Desktop.Controls;

/// <summary>
/// Frequency spectrum with the original Linear/Log, Left/Right, Pano/Phase and -200dB actions.
/// </summary>
public sealed class SpectrumGraphControl : FrequencyChartControl
{
    public static readonly StyledProperty<int> BitDepthProperty =
        AvaloniaProperty.Register<SpectrumGraphControl, int>(nameof(BitDepth), 16);

    public int BitDepth
    {
        get => GetValue(BitDepthProperty);
        set => SetValue(BitDepthProperty, value);
    }

    // SpectrumControl.java selects the 96 dB scale only for 16-bit input.
    private double MinimumDb => BitDepth == 16 ? -96.0 : -144.0;
    private double AmplitudeScale => BitDepth == 16 ? 3000.0 : 500000.0;
    private static readonly double[] DbMarks16Bit = [0, -6, -12, -24, -40, -60, -96];
    private static readonly double[] DbMarksOther = [0, -12, -24, -40, -60, -100, -144];

    public static readonly StyledProperty<double[]?> MagnitudesDbProperty =
        AvaloniaProperty.Register<SpectrumGraphControl, double[]?>(nameof(MagnitudesDb));
    public static readonly StyledProperty<double[]?> InstantMagnitudesDbProperty =
        AvaloniaProperty.Register<SpectrumGraphControl, double[]?>(nameof(InstantMagnitudesDb));
    public static readonly StyledProperty<SpectrumFrame?> LinearSpectrumProperty =
        AvaloniaProperty.Register<SpectrumGraphControl, SpectrumFrame?>(nameof(LinearSpectrum));
    public static readonly StyledProperty<SpectrumFrame?> LogSpectrumProperty =
        AvaloniaProperty.Register<SpectrumGraphControl, SpectrumFrame?>(nameof(LogSpectrum));
    public static readonly StyledProperty<int> BitDepthProperty =
        AvaloniaProperty.Register<SpectrumGraphControl, int>(nameof(BitDepth), 16);
    public static readonly StyledProperty<bool> IsLogarithmicProperty =
        AvaloniaProperty.Register<SpectrumGraphControl, bool>(nameof(IsLogarithmic));
    public static readonly StyledProperty<bool> ShowLeftRightProperty =
        AvaloniaProperty.Register<SpectrumGraphControl, bool>(nameof(ShowLeftRight));
    public static readonly StyledProperty<bool> ShowPanoramaPhaseProperty =
        AvaloniaProperty.Register<SpectrumGraphControl, bool>(nameof(ShowPanoramaPhase));
    public static readonly StyledProperty<bool> ExtendedRangeProperty =
        AvaloniaProperty.Register<SpectrumGraphControl, bool>(nameof(ExtendedRange));

    public double[]? MagnitudesDb { get => GetValue(MagnitudesDbProperty); set => SetValue(MagnitudesDbProperty, value); }
    public double[]? InstantMagnitudesDb { get => GetValue(InstantMagnitudesDbProperty); set => SetValue(InstantMagnitudesDbProperty, value); }
    public SpectrumFrame? LinearSpectrum { get => GetValue(LinearSpectrumProperty); set => SetValue(LinearSpectrumProperty, value); }
    public SpectrumFrame? LogSpectrum { get => GetValue(LogSpectrumProperty); set => SetValue(LogSpectrumProperty, value); }
    public int BitDepth { get => GetValue(BitDepthProperty); set => SetValue(BitDepthProperty, value); }
    public bool IsLogarithmic { get => GetValue(IsLogarithmicProperty); set => SetValue(IsLogarithmicProperty, value); }
    public bool ShowLeftRight { get => GetValue(ShowLeftRightProperty); set => SetValue(ShowLeftRightProperty, value); }
    public bool ShowPanoramaPhase { get => GetValue(ShowPanoramaPhaseProperty); set => SetValue(ShowPanoramaPhaseProperty, value); }
    public bool ExtendedRange { get => GetValue(ExtendedRangeProperty); set => SetValue(ExtendedRangeProperty, value); }

    private SpectrumFrame? SelectedSpectrum => IsLogarithmic ? LogSpectrum : LinearSpectrum;
    internal double FloorDb => ExtendedRange ? -200 : BitDepth == 16 ? -96 : -144;
    private double ScaleFactor => ExtendedRange ? 5e8 : BitDepth == 16 ? 3000 : 500000;
    protected override Rect PlotBounds => new(38, 16, Math.Max(0, Bounds.Width - 48), Math.Max(0, Bounds.Height - 52));

    // SpectrumControl.java: default log curvature 0.005 with 4096 positive FFT bins.
    protected override double FractionToFrequency(double fraction) => EffectiveSampleRate / 2 *
        (IsLogarithmic ? (Math.Pow(21.48, fraction) - 1) / 20.48 : fraction);
    protected override double FrequencyToFraction(double frequency)
    {
        double fraction = frequency / (EffectiveSampleRate / 2);
        return IsLogarithmic ? Math.Log10(1 + 20.48 * fraction) / Math.Log10(21.48) : fraction;
    }

    protected override void OnPropertyChanged(AvaloniaPropertyChangedEventArgs change)
    {
        base.OnPropertyChanged(change);
        if (change.Property == IsLogarithmicProperty) UpdateHoverFrequency();
    }

    internal double DbToY(double db)
    {
        double amplitude = Math.Pow(10, Math.Clamp(db, FloorDb, 0) / 20);
        return PlotBounds.Bottom - Math.Log10(1 + amplitude * ScaleFactor) /
            Math.Log10(1 + ScaleFactor) * PlotBounds.Height;
    }

    protected override (double? LevelDb, double? CursorDb, string LevelName) GetLevels(Point position)
    {
        Rect plot = PlotBounds;
        double frequency = FractionToFrequency(Math.Clamp((position.X - plot.X) / plot.Width, 0, 1));
        double? peak = null;
        var magnitudes = SelectedSpectrum?.PeakDb ?? MagnitudesDb;
        if (magnitudes is { Length: >= 4 })
        {
            double bins = SelectedSpectrum == null ? magnitudes.Length - 1 : magnitudes.Length;
            int bin = Math.Clamp((int)Math.Round(frequency / (EffectiveSampleRate / 2) * bins), 0, magnitudes.Length - 1);
            peak = magnitudes[bin];
        }
        double t = Math.Clamp((plot.Bottom - position.Y) / plot.Height, 0, 1);
        double amplitude = (Math.Pow(1 + ScaleFactor, t) - 1) / ScaleFactor;
        double cursorDb = amplitude > 0 ? Math.Clamp(20 * Math.Log10(amplitude), FloorDb, 0) : FloorDb;
        return (peak, cursorDb, "Peak");
    }

    private static readonly IBrush GridBrush = new SolidColorBrush(Color.FromRgb(35, 40, 45));
    private static readonly IBrush TextBrush = new SolidColorBrush(Color.FromRgb(220, 220, 220));
    private static readonly IBrush SwitchBrush = new SolidColorBrush(Color.FromRgb(102, 102, 102));
    private static readonly IPen GridPen = new Pen(GridBrush, 1);
    private static readonly IPen BaselinePen = new Pen(Brushes.Lime, 2);
    private static readonly IPen SpectrumPen = new Pen(new SolidColorBrush(Color.Parse("#FFBF00")), 1);
    private static readonly IPen InstantPen = new Pen(new SolidColorBrush(Color.Parse("#00B400")), 1);
    private static readonly IPen DimInstantPen = new Pen(new SolidColorBrush(Color.Parse("#005000")), 1);
    private static readonly IPen LeftPen = new Pen(Brushes.Lime, 2);
    private static readonly IPen RightPen = new Pen(Brushes.Blue, 2);
    private static readonly IPen DimLeftPen = new Pen(new SolidColorBrush(Color.Parse("#005000")), 2);
    private static readonly IPen DimRightPen = new Pen(new SolidColorBrush(Color.Parse("#000050")), 2);
    private static readonly IPen[] PhasePens = [new Pen(Brushes.Lime, 2), new Pen(Brushes.Yellow, 2), new Pen(Brushes.Red, 2)];

    static SpectrumGraphControl()
    {
        AffectsRender<SpectrumGraphControl>(MagnitudesDbProperty, InstantMagnitudesDbProperty,
            LinearSpectrumProperty, LogSpectrumProperty, SampleRateProperty, BitDepthProperty,
            IsLogarithmicProperty, ShowLeftRightProperty, ShowPanoramaPhaseProperty, ExtendedRangeProperty);
    }

    private string ActionText(int action) => action switch
    {
        0 => IsLogarithmic ? "Log. Frequency Spectrum [kHz]" : "Linear Frequency Spectrum [kHz]",
        1 => "Left/Right", 2 => "Pano/Phase", _ => "-200dB Mode"
    };

    private static FormattedText Format(string text, double size, IBrush brush) =>
        new(text, CultureInfo.InvariantCulture, FlowDirection.LeftToRight, Typeface.Default, size, brush);

    // Measure the actual labels for rendering and hit testing; keep separate frequency and action rows.
    internal Rect GetActionBounds(int action)
    {
        double total = 0;
        for (int i = 0; i < 4; i++) total += Format(ActionText(i), 10, SwitchBrush).Width;
        double gap = Math.Max(8, (PlotBounds.Width - total) / 3);
        double x = PlotBounds.Left;
        for (int i = 0; i < action; i++) x += Format(ActionText(i), 10, SwitchBrush).Width + gap;
        return new Rect(x, PlotBounds.Bottom + 20, Format(ActionText(action), 10, SwitchBrush).Width, 16);
    }

    protected override void OnPointerMoved(PointerEventArgs e)
    {
        base.OnPointerMoved(e);
        var position = e.GetPosition(this);
        bool overAction = false;
        for (int i = 0; i < 4; i++) overAction |= GetActionBounds(i).Contains(position);
        Cursor = new Cursor(overAction ? StandardCursorType.Hand : StandardCursorType.Arrow);
    }

    protected override void OnPointerPressed(PointerPressedEventArgs e)
    {
        base.OnPointerPressed(e);
        if (!e.GetCurrentPoint(this).Properties.IsLeftButtonPressed) return;
        for (int i = 0; i < 4; i++)
        {
            if (!GetActionBounds(i).Contains(e.GetPosition(this))) continue;
            switch (i)
            {
                case 0: SetCurrentValue(IsLogarithmicProperty, !IsLogarithmic); break;
                case 1: SetCurrentValue(ShowLeftRightProperty, !ShowLeftRight); break;
                case 2: SetCurrentValue(ShowPanoramaPhaseProperty, !ShowPanoramaPhase); break;
                case 3: SetCurrentValue(ExtendedRangeProperty, !ExtendedRange); break;
            }
            e.Handled = true;
            return;
        }
    }

    public override void Render(DrawingContext context)
    {
        context.FillRectangle(Brushes.Black, new Rect(Bounds.Size));
        Rect plot = PlotBounds;
        if (plot.Width <= 0 || plot.Height <= 0) return;
        double[] marks = ExtendedRange ? [0, -24, -60, -96, -144, -160, -200] :
            BitDepth == 16 ? [0, -6, -12, -24, -40, -60, -96] : [0, -12, -24, -40, -60, -100, -144];
        context.DrawText(Format("dB", 10, SwitchBrush), new Point(8, 4));
        foreach (double db in marks)
        {
            double y = DbToY(db);
            context.DrawLine(GridPen, new Point(plot.Left, y), new Point(plot.Right, y));
            var text = Format(db.ToString("F0", CultureInfo.InvariantCulture), 10, TextBrush);
            context.DrawText(text, new Point(plot.Left - text.Width - 6, y - 6));
        }
        for (int i = 1; i <= 4; i++)
        {
            double x = plot.Left + plot.Width * i / 4;
            context.DrawLine(BaselinePen, new Point(x, plot.Bottom), new Point(x, plot.Bottom + 4));
            var text = Format((FractionToFrequency(i / 4.0) / 1000).ToString("F2", CultureInfo.InvariantCulture), 10, TextBrush);
            context.DrawText(text, new Point(x - (i == 4 ? text.Width : text.Width / 2), plot.Bottom + 4));
        }
        for (int i = 0; i < 4; i++)
        {
            bool active = i switch { 0 => true, 1 => ShowLeftRight, 2 => ShowPanoramaPhase, _ => ExtendedRange };
            context.DrawText(Format(ActionText(i), 10, active ? Brushes.Lime : SwitchBrush), GetActionBounds(i).TopLeft);
        }
        context.DrawLine(BaselinePen, plot.BottomLeft, plot.BottomRight);
        using (context.PushClip(plot))
        {
            var frame = SelectedSpectrum;
            double X(int bin, int count) => plot.Left + FrequencyToFraction(
                bin / (double)(frame == null ? count - 1 : count) * EffectiveSampleRate / 2) * plot.Width;
            void Curve(double[]? data, IPen pen, bool bars = false)
            {
                if (data is not { Length: >= 2 }) return;
                Point? previous = null;
                for (int b = 0; b < data.Length; b++)
                {
                    var point = new Point(X(b, data.Length), DbToY(data[b]));
                    if (bars && data[b] > FloorDb) context.DrawLine(pen, new Point(point.X, plot.Bottom), point);
                    else if (!bars && previous is { } p) context.DrawLine(pen, p, point);
                    previous = point;
                }
            }
            if (ShowLeftRight && frame != null)
            {
                Curve(frame.LeftDb, ShowPanoramaPhase ? DimLeftPen : LeftPen);
                Curve(frame.RightDb, ShowPanoramaPhase ? DimRightPen : RightPen);
            }
            else if (InstantMagnitudesDb != null)
                Curve(frame?.InstantDb ?? InstantMagnitudesDb, ShowPanoramaPhase ? DimInstantPen : InstantPen, true);
            Curve(frame?.PeakDb ?? MagnitudesDb, SpectrumPen);
            if (ShowPanoramaPhase && frame != null)
            {
                double max = 0;
                for (int b = 0; b < frame.Panorama.Length; b++)
                    if (frame.LeftDb[b] > FloorDb || frame.RightDb[b] > FloorDb)
                        max = Math.Max(max, Math.Abs(frame.Panorama[b]));
                if (max < 0.001) max = 1; // Original silence/near-silence guard.
                Point? previous = null;
                for (int b = 0; b < frame.Panorama.Length; b++)
                {
                    double value = frame.LeftDb[b] > FloorDb || frame.RightDb[b] > FloorDb ? frame.Panorama[b] / max : 0;
                    var point = new Point(X(b, frame.Panorama.Length), plot.Center.Y + value * (plot.Height / 2 - 1));
                    double phase = frame.LeftDb[b] > FloorDb && frame.RightDb[b] > FloorDb ? frame.PhaseRadians[b] : 0;
                    var pen = PhasePens[phase < Math.PI * 200 / 499 ? 0 : phase < Math.PI * 300 / 499 ? 1 : 2];
                    if (previous is { } p) context.DrawLine(pen, p, point);
                    previous = point;
                }
            }
        }
        if (ShowPanoramaPhase)
        {
            context.DrawText(Format("L", 10, TextBrush), new Point(plot.Right - 10, plot.Top + plot.Height / 4));
            context.DrawText(Format("R", 10, TextBrush), new Point(plot.Right - 10, plot.Top + plot.Height * 3 / 4));
        }
        DrawHover(context);
    }
}
