using System.Runtime.InteropServices;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Headless;
using Avalonia.Headless.XUnit;
using Avalonia.Input;
using Avalonia.Media.Imaging;
using Avalonia.Threading;
using Avalonia.VisualTree;
using MusicScope.Core;
using MusicScope.Desktop.Controls;
using MusicScope.Desktop.ViewModels;
using MusicScope.Desktop.Views;

namespace MusicScope.Desktop.Tests;

public class SpectrumActionsTests
{
    [AvaloniaFact]
    public void FooterActions_ToggleAndRenderIndependently_AndRejectOtherClicks()
    {
        var engine = new AudioAnalysisEngine(48000);
        var samples = new float[96000];
        for (int i = 0; i < samples.Length / 2; i++)
        {
            samples[i * 2] = (float)Math.Sin(2 * Math.PI * 1500 * i / 48000);
            samples[i * 2 + 1] = (float)(0.25 * Math.Cos(2 * Math.PI * 1500 * i / 48000));
        }
        engine.ProcessAudioBlock(samples);
        var s = engine.GetRealtimeSnapshot();
        var chart = new SpectrumGraphControl
        {
            LinearSpectrum = s.LinearSpectrum, LogSpectrum = s.LogSpectrum,
            InstantMagnitudesDb = s.InstantSpectrumDb, SampleRate = 48000
        };
        var window = new Window { Width = 600, Height = 240, Content = chart };
        window.Show();
        try
        {
            for (int action = 0; action < 4; action++)
            {
                var before = Capture(window);
                Click(window, chart.GetActionBounds(action).Center);
                Assert.True(IsActive(chart, action));
                Assert.False(before.SequenceEqual(Capture(window)));
            }
            Assert.True(chart.IsLogarithmic && chart.ShowLeftRight && chart.ShowPanoramaPhase && chart.ExtendedRange);
            Click(window, new Point(250, 100));
            Click(window, chart.GetActionBounds(0).Center, MouseButton.Right);
            Assert.True(chart.IsLogarithmic);
            for (int action = 0; action < 4; action++)
            {
                Click(window, chart.GetActionBounds(action).Center);
                Assert.False(IsActive(chart, action));
            }
        }
        finally { window.Close(); }
    }

    [AvaloniaFact]
    public void LogHover_UsesOriginalCurvature_AndSynchronizesFrequencyWithLinearWaterfall()
    {
        var spectrum = new SpectrumGraphControl { Height = 236, SampleRate = 48000 };
        var waterfall = new WaterfallControl { Height = 260, SampleRate = 48000 };
        waterfall.Bind(FrequencyChartControl.HoverFrequencyProperty,
            new Avalonia.Data.Binding(nameof(SpectrumGraphControl.HoverFrequency)) { Source = spectrum });
        var window = new Window { Width = 548, Height = 496, Content = new StackPanel { Children = { spectrum, waterfall } } };
        window.Show();
        try
        {
            window.MouseMove(new Point(288, 108));
            Assert.Equal(12000, spectrum.HoverFrequency);
            spectrum.IsLogarithmic = true; // Stationary pointer must update too.
            double expected = 24000 * (Math.Sqrt(21.48) - 1) / 20.48;
            Assert.Equal(expected, spectrum.GetHoverReadout()!.Value.FrequencyHz, 6);
            Assert.Equal(expected, waterfall.HoverFrequency!.Value, 6);
            window.MouseMove(new Point(538, 108));
            Assert.Equal(24000, spectrum.GetHoverReadout()!.Value.FrequencyHz, 6);
        }
        finally { window.Close(); }
    }

    [AvaloniaTheory]
    [InlineData(16, false, -60)]
    [InlineData(24, false, -100)]
    [InlineData(16, true, -160)]
    [InlineData(24, true, -160)]
    public void VerticalScale_MapsCurveAndCursorConsistently(int bits, bool extended, double db)
    {
        var chart = new SpectrumGraphControl { BitDepth = bits, ExtendedRange = extended };
        var window = new Window { Width = 600, Height = 240, Content = chart };
        window.Show();
        try
        {
            double y = chart.DbToY(db);
            window.MouseMove(new Point(250, y));
            Assert.Equal(db, chart.GetHoverReadout()!.Value.CursorDb!.Value, 5);
            Assert.Equal(extended ? -200 : bits == 16 ? -96 : -144, chart.FloorDb);
            Assert.True(y < 204);
        }
        finally { window.Close(); }
    }

    [AvaloniaFact]
    public async Task FileAnalysis_BindsBothModesAndBitDepth_AndKeepsActionsUsableWhenFinished()
    {
        string path = Path.GetFullPath(Path.Combine(AppContext.BaseDirectory, "../../../../../TestAudioSamples/07_Sweep_10kHz_22.5kHz_-60dBFS_48k_24bit.wav"));
        var vm = new MainViewModel();
        var window = new MainWindow { DataContext = vm };
        window.Show();
        try
        {
            await vm.AnalyzeFileAsync(path);
            Dispatcher.UIThread.RunJobs();
            Assert.NotNull(vm.CurrentReport);
            var chart = window.GetVisualDescendants().OfType<SpectrumGraphControl>().Single();
            Assert.Equal(24, chart.BitDepth);
            Assert.Same(vm.CurrentReport.LinearSpectrum, chart.LinearSpectrum);
            Assert.Same(vm.CurrentReport.LogSpectrum, chart.LogSpectrum);
            Assert.Null(chart.InstantMagnitudesDb);
            foreach (int action in new[] { 0, 1, 2, 3 })
            {
                Click(window, chart.TranslatePoint(chart.GetActionBounds(action).Center, window)!.Value);
                Assert.True(IsActive(chart, action));
            }
            string directory = Path.Combine(AppContext.BaseDirectory, "spectrum-previews");
            Directory.CreateDirectory(directory);
            using var bitmap = window.CaptureRenderedFrame();
            Assert.NotNull(bitmap);
            bitmap.Save(Path.Combine(directory, "spectrum-actions.png"), PngBitmapEncoderOptions.Default);
        }
        finally { window.Close(); }
    }

    private static bool IsActive(SpectrumGraphControl chart, int action) => action switch
    {
        0 => chart.IsLogarithmic, 1 => chart.ShowLeftRight, 2 => chart.ShowPanoramaPhase, _ => chart.ExtendedRange
    };

    private static void Click(Window window, Point point, MouseButton button = MouseButton.Left)
    {
        window.MouseMove(point);
        window.MouseDown(point, button);
        window.MouseUp(point, button);
    }

    private static byte[] Capture(Window window)
    {
        using var bitmap = window.CaptureRenderedFrame();
        Assert.NotNull(bitmap);
        using var framebuffer = bitmap.Lock();
        var pixels = new byte[framebuffer.RowBytes * framebuffer.Size.Height];
        Marshal.Copy(framebuffer.Address, pixels, 0, pixels.Length);
        return pixels;
    }
}
