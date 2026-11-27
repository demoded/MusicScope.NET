using System;
using System.Collections.Generic;
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
    // SpectrumControl.java selects the 96 dB scale only for 16-bit input.
    private double MinimumDb => BitDepth == 16 ? -96.0 : -144.0;
    private double AmplitudeScale => BitDepth == 16 ? 3000.0 : 500000.0;
    private static readonly double[] DbMarks16Bit = [0, -6, -12, -24, -40, -60, -96];
    private static readonly double[] DbMarksOther = [0, -12, -24, -40, -60, -100, -144];
    private static readonly double[] DbMarksExtended = [0, -24, -60, -96, -144, -160, -200];

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
    public bool ShowLeftRight { get => GetValue(ShowLeftRightProperty); set => SetValue(ShowLeftRightProperty, value); }
    public bool ShowPanoramaPhase { get => GetValue(ShowPanoramaPhaseProperty); set => SetValue(ShowPanoramaPhaseProperty, value); }
    public bool ExtendedRange { get => GetValue(ExtendedRangeProperty); set => SetValue(ExtendedRangeProperty, value); }

    private SpectrumFrame? SelectedSpectrum => IsLogarithmic ? LogSpectrum : LinearSpectrum;
    internal double FloorDb => ExtendedRange ? -200 : BitDepth == 16 ? -96 : -144;
    private double ScaleFactor => ExtendedRange ? 5e8 : BitDepth == 16 ? 3000 : 500000;
    protected override Rect PlotBounds => new(38, 16, Math.Max(0, Bounds.Width - 48), Math.Max(0, Bounds.Height - 52));

    protected override void OnPropertyChanged(AvaloniaPropertyChangedEventArgs change)
    {
        base.OnPropertyChanged(change);
    }

    internal double DbToY(double db)
    {
        double amplitude = Math.Pow(10, Math.Clamp(db, FloorDb, 0) / 20);
        return PlotBounds.Bottom - Math.Log10(1 + amplitude * ScaleFactor) /
            Math.Log10(1 + ScaleFactor) * PlotBounds.Height;
    }

    protected internal override (double? LevelDb, double? CursorDb, string LevelName) GetLevels(Point position)
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

    private static readonly FormattedText DbHeaderText = Format("dB", 10, SwitchBrush);
    private static readonly FormattedText LeftLabelText = Format("L", 10, TextBrush);
    private static readonly FormattedText RightLabelText = Format("R", 10, TextBrush);
    private static readonly FormattedText ActionLogActive = Format("Log. Frequency Spectrum [kHz]", 10, Brushes.Lime);
    private static readonly FormattedText ActionLinActive = Format("Linear Frequency Spectrum [kHz]", 10, Brushes.Lime);
    private static readonly FormattedText ActionLrActive = Format("Left/Right", 10, Brushes.Lime);
    private static readonly FormattedText ActionLrInactive = Format("Left/Right", 10, SwitchBrush);
    private static readonly FormattedText ActionPanoActive = Format("Pano/Phase", 10, Brushes.Lime);
    private static readonly FormattedText ActionPanoInactive = Format("Pano/Phase", 10, SwitchBrush);
    private static readonly FormattedText ActionExtActive = Format("-200dB Mode", 10, Brushes.Lime);
    private static readonly FormattedText ActionExtInactive = Format("-200dB Mode", 10, SwitchBrush);
    private static readonly Dictionary<double, FormattedText> DbMarkTexts = new()
    {
        [0] = Format("0", 10, TextBrush),
        [-6] = Format("-6", 10, TextBrush),
        [-12] = Format("-12", 10, TextBrush),
        [-24] = Format("-24", 10, TextBrush),
        [-40] = Format("-40", 10, TextBrush),
        [-60] = Format("-60", 10, TextBrush),
        [-96] = Format("-96", 10, TextBrush),
        [-100] = Format("-100", 10, TextBrush),
        [-144] = Format("-144", 10, TextBrush),
        [-160] = Format("-160", 10, TextBrush),
        [-200] = Format("-200", 10, TextBrush),
    };

    private double[]? _cachedX;
    private int _cachedXCount;
    private double _cachedXRate;
    private bool _cachedXLog;
    private double _cachedXLeft;
    private double _cachedXWidth;
    private bool _cachedXHasFrame;

    static SpectrumGraphControl()
    {
        AffectsRender<SpectrumGraphControl>(MagnitudesDbProperty, InstantMagnitudesDbProperty,
            LinearSpectrumProperty, LogSpectrumProperty, SampleRateProperty, BitDepthProperty,
            IsLogarithmicProperty, ShowLeftRightProperty, ShowPanoramaPhaseProperty, ExtendedRangeProperty);
    }

    private static FormattedText Format(string text, double size, IBrush brush) =>
        new(text, CultureInfo.InvariantCulture, FlowDirection.LeftToRight, Typeface.Default, size, brush);

    private double GetActionWidth(int action) => action switch
    {
        0 => (IsLogarithmic ? ActionLogActive : ActionLinActive).Width,
        1 => ActionLrInactive.Width,
        2 => ActionPanoInactive.Width,
        _ => ActionExtInactive.Width
    };

    // Measure the actual labels for rendering and hit testing; keep separate frequency and action rows.
    internal Rect GetActionBounds(int action)
    {
        double w0 = GetActionWidth(0);
        double w1 = ActionLrInactive.Width;
        double w2 = ActionPanoInactive.Width;
        double w3 = ActionExtInactive.Width;
        Rect plot = PlotBounds;
        double gap = Math.Max(8, (plot.Width - (w0 + w1 + w2 + w3)) / 3);
        double x = plot.Left;
        if (action >= 1) x += w0 + gap;
        if (action >= 2) x += w1 + gap;
        if (action >= 3) x += w2 + gap;
        double w = action switch { 0 => w0, 1 => w1, 2 => w2, _ => w3 };
        return new Rect(x, plot.Bottom + 20, w, 16);
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

    private double[] GetOrBuildXCoords(int count, bool hasFrame, Rect plot, double effectiveSampleRate, bool isLog)
    {
        if (_cachedX != null &&
            _cachedXCount == count &&
            _cachedXHasFrame == hasFrame &&
            _cachedXRate == effectiveSampleRate &&
            _cachedXLog == isLog &&
            _cachedXLeft == plot.Left &&
            _cachedXWidth == plot.Width)
        {
            return _cachedX;
        }

        double[] xs = (_cachedX != null && _cachedX.Length == count) ? _cachedX : new double[count];
        double denom = hasFrame ? count : count - 1;
        double halfRate = effectiveSampleRate * 0.5;
        double plotLeft = plot.Left;
        double plotWidth = plot.Width;
        if (isLog)
        {
            double invLog2148 = 1.0 / Math.Log10(21.48);
            for (int b = 0; b < count; b++)
            {
                double norm = (b / denom);
                double frac = Math.Log10(1.0 + 20.48 * norm) * invLog2148;
                xs[b] = plotLeft + frac * plotWidth;
            }
        }
        else
        {
            for (int b = 0; b < count; b++)
            {
                double frac = b / denom;
                xs[b] = plotLeft + frac * plotWidth;
            }
        }

        _cachedX = xs;
        _cachedXCount = count;
        _cachedXHasFrame = hasFrame;
        _cachedXRate = effectiveSampleRate;
        _cachedXLog = isLog;
        _cachedXLeft = plotLeft;
        _cachedXWidth = plotWidth;
        return xs;
    }

    public override void Render(DrawingContext context)
    {
        context.FillRectangle(Brushes.Black, new Rect(Bounds.Size));
        Rect plot = PlotBounds;
        if (plot.Width <= 0 || plot.Height <= 0) return;

        bool extendedRange = ExtendedRange;
        int bitDepth = BitDepth;
        bool isLog = IsLogarithmic;
        bool showLeftRight = ShowLeftRight;
        bool showPanoramaPhase = ShowPanoramaPhase;
        double effectiveSampleRate = EffectiveSampleRate;
        double floorDb = extendedRange ? -200 : bitDepth == 16 ? -96 : -144;
        double scaleFactor = extendedRange ? 5e8 : bitDepth == 16 ? 3000 : 500000;
        double plotBottom = plot.Bottom;
        double plotHeight = plot.Height;
        double yScale = plotHeight / Math.Log10(1.0 + scaleFactor);

        double MapDbToY(double db)
        {
            double clamped = db < floorDb ? floorDb : (db > 0.0 ? 0.0 : db);
            double amplitude = Math.Pow(10.0, clamped * 0.05);
            return plotBottom - Math.Log10(1.0 + amplitude * scaleFactor) * yScale;
        }

        double[] marks = extendedRange ? DbMarksExtended :
            bitDepth == 16 ? DbMarks16Bit : DbMarksOther;
        context.DrawText(DbHeaderText, new Point(8, 4));
        foreach (double db in marks)
        {
            double y = MapDbToY(db);
            context.DrawLine(GridPen, new Point(plot.Left, y), new Point(plot.Right, y));
            var text = DbMarkTexts[db];
            context.DrawText(text, new Point(plot.Left - text.Width - 6, y - 6));
        }
        for (int i = 1; i <= 4; i++)
        {
            double x = plot.Left + plot.Width * i / 4;
            context.DrawLine(BaselinePen, new Point(x, plot.Bottom), new Point(x, plot.Bottom + 4));
            var text = Format((FractionToFrequency(i / 4.0) / 1000).ToString("F2", CultureInfo.InvariantCulture), 10, TextBrush);
            context.DrawText(text, new Point(x - (i == 4 ? text.Width : text.Width / 2), plot.Bottom + 4));
        }

        // Draw footer actions with pre-shaped FormattedText
        FormattedText a0 = isLog ? ActionLogActive : ActionLinActive;
        FormattedText a1 = showLeftRight ? ActionLrActive : ActionLrInactive;
        FormattedText a2 = showPanoramaPhase ? ActionPanoActive : ActionPanoInactive;
        FormattedText a3 = extendedRange ? ActionExtActive : ActionExtInactive;
        double gap = Math.Max(8, (plot.Width - (a0.Width + a1.Width + a2.Width + a3.Width)) / 3);
        double ax = plot.Left;
        double ay = plot.Bottom + 20;
        context.DrawText(a0, new Point(ax, ay)); ax += a0.Width + gap;
        context.DrawText(a1, new Point(ax, ay)); ax += a1.Width + gap;
        context.DrawText(a2, new Point(ax, ay)); ax += a2.Width + gap;
        context.DrawText(a3, new Point(ax, ay));

        context.DrawLine(BaselinePen, plot.BottomLeft, plot.BottomRight);
        using (context.PushClip(plot))
        {
            var frame = SelectedSpectrum;
            bool hasFrame = frame != null;

            void Curve(double[]? data, IPen pen, bool bars = false)
            {
                if (data is not { Length: >= 2 }) return;
                double[] xs = GetOrBuildXCoords(data.Length, hasFrame, plot, effectiveSampleRate, isLog);

                if (bars)
                {
                    if (data.Length > plot.Width * 1.5)
                    {
                        // Collapse multiple bins falling in the same pixel column to the highest peak (lowest Y)
                        int lastPx = int.MinValue;
                        double minY = plotBottom;
                        double lastX = 0;
                        bool hasBar = false;
                        for (int b = 0; b < data.Length; b++)
                        {
                            double db = data[b];
                            double x = xs[b];
                            int px = (int)x;
                            if (px != lastPx)
                            {
                                if (hasBar)
                                    context.DrawLine(pen, new Point(lastX, plotBottom), new Point(lastX, minY));
                                lastPx = px;
                                lastX = x;
                                minY = db > floorDb ? MapDbToY(db) : plotBottom;
                                hasBar = db > floorDb;
                            }
                            else if (db > floorDb)
                            {
                                double y = MapDbToY(db);
                                if (!hasBar || y < minY)
                                {
                                    minY = y;
                                    lastX = x;
                                    hasBar = true;
                                }
                            }
                        }
                        if (hasBar)
                            context.DrawLine(pen, new Point(lastX, plotBottom), new Point(lastX, minY));
                    }
                    else
                    {
                        for (int b = 0; b < data.Length; b++)
                        {
                            if (data[b] > floorDb)
                            {
                                double x = xs[b];
                                context.DrawLine(pen, new Point(x, plotBottom), new Point(x, MapDbToY(data[b])));
                            }
                        }
                    }
                }
                else
                {
                    var geom = new StreamGeometry();
                    using (var gc = geom.Open())
                    {
                        if (data.Length > plot.Width * 2.5)
                        {
                            // Pixel-column envelope decimation preserves exact min/max peaks while cutting Skia path segments
                            int lastPx = (int)xs[0];
                            double y0 = MapDbToY(data[0]);
                            double colMinY = y0, colMaxY = y0, colLastY = y0;
                            double colX = xs[0];
                            gc.BeginFigure(new Point(colX, y0), false);
                            int countInCol = 1;
                            for (int b = 1; b < data.Length; b++)
                            {
                                double x = xs[b];
                                double y = MapDbToY(data[b]);
                                int px = (int)x;
                                if (px == lastPx)
                                {
                                    if (y < colMinY) colMinY = y;
                                    if (y > colMaxY) colMaxY = y;
                                    colLastY = y;
                                    countInCol++;
                                }
                                else
                                {
                                    if (countInCol > 1)
                                    {
                                        if (colMinY != colLastY) gc.LineTo(new Point(colX, colMinY));
                                        if (colMaxY != colLastY) gc.LineTo(new Point(colX, colMaxY));
                                        gc.LineTo(new Point(colX, colLastY));
                                    }
                                    lastPx = px;
                                    colX = x;
                                    colMinY = colMaxY = colLastY = y;
                                    countInCol = 1;
                                    gc.LineTo(new Point(x, y));
                                }
                            }
                            if (countInCol > 1)
                            {
                                if (colMinY != colLastY) gc.LineTo(new Point(colX, colMinY));
                                if (colMaxY != colLastY) gc.LineTo(new Point(colX, colMaxY));
                                gc.LineTo(new Point(colX, colLastY));
                            }
                            gc.EndFigure(false);
                        }
                        else
                        {
                            gc.BeginFigure(new Point(xs[0], MapDbToY(data[0])), false);
                            for (int b = 1; b < data.Length; b++)
                            {
                                gc.LineTo(new Point(xs[b], MapDbToY(data[b])));
                            }
                            gc.EndFigure(false);
                        }
                    }
                    context.DrawGeometry(null, pen, geom);
                }
            }

            if (showLeftRight && frame != null)
            {
                Curve(frame.LeftDb, showPanoramaPhase ? DimLeftPen : LeftPen);
                Curve(frame.RightDb, showPanoramaPhase ? DimRightPen : RightPen);
            }
            else if (InstantMagnitudesDb != null)
                Curve(frame?.InstantDb ?? InstantMagnitudesDb, showPanoramaPhase ? DimInstantPen : InstantPen, true);
            Curve(frame?.PeakDb ?? MagnitudesDb, SpectrumPen);
            if (showPanoramaPhase && frame != null)
            {
                double max = 0;
                for (int b = 0; b < frame.Panorama.Length; b++)
                    if (frame.LeftDb[b] > floorDb || frame.RightDb[b] > floorDb)
                        max = Math.Max(max, Math.Abs(frame.Panorama[b]));
                if (max < 0.001) max = 1; // Original silence/near-silence guard.
                double[] xs = GetOrBuildXCoords(frame.Panorama.Length, hasFrame, plot, effectiveSampleRate, isLog);
                Point? previous = null;
                for (int b = 0; b < frame.Panorama.Length; b++)
                {
                    double value = frame.LeftDb[b] > floorDb || frame.RightDb[b] > floorDb ? frame.Panorama[b] / max : 0;
                    var point = new Point(xs[b], plot.Center.Y + value * (plotHeight / 2 - 1));
                    double phase = frame.LeftDb[b] > floorDb && frame.RightDb[b] > floorDb ? frame.PhaseRadians[b] : 0;
                    var pen = PhasePens[phase < Math.PI * 200 / 499 ? 0 : phase < Math.PI * 300 / 499 ? 1 : 2];
                    if (previous is { } p) context.DrawLine(pen, p, point);
                    previous = point;
                }
            }
        }
        if (showPanoramaPhase)
        {
            context.DrawText(LeftLabelText, new Point(plot.Right - 10, plot.Top + plotHeight / 4));
            context.DrawText(RightLabelText, new Point(plot.Right - 10, plot.Top + plotHeight * 3 / 4));
        }
        DrawHover(context);
    }
}
