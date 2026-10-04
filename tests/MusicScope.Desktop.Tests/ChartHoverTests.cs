using System.Runtime.InteropServices;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Data;
using Avalonia.Headless;
using Avalonia.Headless.XUnit;
using Avalonia.Media.Imaging;
using Avalonia.Threading;
using Avalonia.VisualTree;
using MusicScope.Desktop.Controls;
using MusicScope.Desktop.ViewModels;
using MusicScope.Desktop.Views;

[assembly: AvaloniaTestApplication(typeof(MusicScope.Desktop.Tests.TestAppBuilder))]

namespace MusicScope.Desktop.Tests;

public static class TestAppBuilder
{
    public static AppBuilder BuildAvaloniaApp() => AppBuilder.Configure<App>()
        .UseSkia()
        .UseHeadless(new AvaloniaHeadlessPlatformOptions { UseHeadlessDrawing = false });
}

public class ChartHoverTests
{
    [AvaloniaTheory]
    [InlineData(16, 168)]
    [InlineData(24, 113)]
    public void Spectrum_RendersCurveAndQuietLiveBarsUsingSelectedScale(int bitDepth, int curveY)
    {
        var chart = new SpectrumGraphControl
        {
            BitDepth = bitDepth,
            MagnitudesDb = [-60, -60, -60, -60],
            InstantMagnitudesDb = [-100, -100, -100, -100]
        };
        var window = new Window { Width = 548, Height = 236, Content = chart };
        window.Show();
        try
        {
            using var frame = window.CaptureRenderedFrame();
            Assert.NotNull(frame);
            using var buffer = frame.Lock();
            Assert.True(buffer.Format == Avalonia.Platform.PixelFormat.Bgra8888 ||
                buffer.Format == Avalonia.Platform.PixelFormat.Rgba8888);
            var pixels = new byte[buffer.RowBytes * buffer.Size.Height];
            Marshal.Copy(buffer.Address, pixels, 0, pixels.Length);
            (byte Red, byte Green, byte Blue) Pixel(int x, int y)
            {
                int offset = y * buffer.RowBytes + x * 4;
                return buffer.Format == Avalonia.Platform.PixelFormat.Bgra8888
                    ? (pixels[offset + 2], pixels[offset + 1], pixels[offset])
                    : (pixels[offset], pixels[offset + 1], pixels[offset + 2]);
            }

            // At -60 dB the legacy scale puts the curve near y=168 (16-bit) or y=113 (24-bit).
            Assert.Contains(Enumerable.Range(curveY - 1, 4), y =>
            {
                var pixel = Pixel(280, y);
                return pixel.Red > 100 && pixel.Green > 60 && pixel.Blue < 20;
            });
            // A -100 dB live bar is visible above the baseline only on the 144 dB scale.
            bool quietBarVisible = Enumerable.Range(203, 4).Any(x =>
            {
                var pixel = Pixel(x, 190);
                return pixel.Green > 30 && pixel.Red < 10 && pixel.Blue < 60;
            });
            Assert.Equal(bitDepth == 24, quietBarVisible);
        }
        finally { window.Close(); }
    }

    [AvaloniaTheory]
    [InlineData(44100, 16, -96, -34.9)]
    [InlineData(96000, 16, -96, -34.9)]
    [InlineData(44100, 24, -144, -57.0)]
    [InlineData(96000, 24, -144, -57.0)]
    [InlineData(48000, 32, -144, -57.0)]
    public void Spectrum_XamlSelectsScaleByBitDepth(double sampleRate, int bitDepth, double floor, double midpoint)
    {
        var vm = new MainViewModel { SampleRate = sampleRate, BitDepth = bitDepth };
        var window = new MainWindow { DataContext = vm };
        window.Show();
        try
        {
            var chart = window.GetVisualDescendants().OfType<SpectrumGraphControl>().Single();
            double x = 38 + (chart.Bounds.Width - 48) / 2;
            double bottom = chart.Bounds.Height - 36;
            window.MouseMove(chart.TranslatePoint(new Point(x, bottom), window)!.Value);
            Assert.Equal(floor, chart.GetHoverReadout()!.Value.CursorDb);
            window.MouseMove(chart.TranslatePoint(new Point(x, (16 + bottom) / 2), window)!.Value);
            Assert.Equal(midpoint, chart.GetHoverReadout()!.Value.CursorDb!.Value, 1);
        }
        finally { window.Close(); }
    }

    [AvaloniaFact]
    public void Spectrum_BitDepthChangeRedrawsCurveAndGrid_AndUpdatesStationaryCursor()
    {
        var vm = new MainViewModel
        {
            SampleRate = 48000, BitDepth = 16,
            SpectrumMagnitudes = [-60, -60, -60, -60],
            InstantSpectrumMagnitudes = [-100, -100, -100, -100]
        };
        var window = new MainWindow { DataContext = vm };
        window.Show();
        try
        {
            var chart = window.GetVisualDescendants().OfType<SpectrumGraphControl>().Single();
            var point = new Point(38 + (chart.Bounds.Width - 48) / 2, (16 + chart.Bounds.Height - 36) / 2);
            window.MouseMove(chart.TranslatePoint(point, window)!.Value);
            var before = Capture(window);
            Assert.Equal(-34.9, chart.GetHoverReadout()!.Value.CursorDb!.Value, 1);
            vm.BitDepth = 24;
            Dispatcher.UIThread.RunJobs();
            Assert.Equal(-57.0, chart.GetHoverReadout()!.Value.CursorDb!.Value, 1);
            Assert.False(before.SequenceEqual(Capture(window)));
            vm.BitDepth = 16;
            Dispatcher.UIThread.RunJobs();
            Assert.Equal(-34.9, chart.GetHoverReadout()!.Value.CursorDb!.Value, 1);
            Assert.Equal(before, Capture(window));
        }
        finally { window.Close(); }
    }

    [AvaloniaTheory]
    [InlineData(false)]
    [InlineData(true)]
    public void Hover_DrawsOverlay_AndLeavingPlotRemovesIt_EvenWithoutAudio(bool waterfall)
    {
        Control chart = waterfall ? new WaterfallControl() : new SpectrumGraphControl();
        var window = new Window { Width = 600, Height = 240, Content = chart };
        window.Show();
        try
        {
            var before = Capture(window);
            window.MouseMove(new Point(250, 100));
            var hovering = Capture(window);
            Assert.False(before.SequenceEqual(hovering), "Hovering must draw the crosshair and readout.");

            window.MouseMove(new Point(10, 200));
            Assert.Equal(before, Capture(window));
        }
        finally { window.Close(); }
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

    [AvaloniaTheory]
    [InlineData(38, 16, 48000, 0, -10, 0)]
    [InlineData(288, 108, 48000, 12000, -30, -34.9)]
    [InlineData(538, 200, 48000, 24000, -50, -96)]
    [InlineData(288, 108, 44100, 11025, -30, -34.9)]
    [InlineData(288, 108, 96000, 24000, -30, -34.9)]
    public void Spectrum_ReadsFrequencyAndCurve_SeparatelyFromNonlinearCursorAxis(
        double x, double y, double sampleRate, double frequency, double level, double cursor)
    {
        var chart = new SpectrumGraphControl { SampleRate = sampleRate, MagnitudesDb = [-10, -20, -30, -40, -50] };
        var window = new Window { Width = 548, Height = 236, Content = chart };
        window.Show();
        try
        {
            window.MouseMove(new Point(x, y));
            var reading = chart.GetHoverReadout();
            Assert.NotNull(reading);
            Assert.Equal(frequency, reading.Value.FrequencyHz, 6);
            Assert.Equal(level, reading.Value.LevelDb);
            Assert.Equal(cursor, reading.Value.CursorDb!.Value, 1);
        }
        finally { window.Close(); }
    }

    [AvaloniaFact]
    public void Waterfall_UsesBitDepthSpecificBrightnessMapping()
    {
        double sixteenBitIntensity = WaterfallControl.GetColorIntensity(0.001f, 16);
        double twentyFourBitIntensity = WaterfallControl.GetColorIntensity(0.001f, 24);
        Assert.Equal(70.4 * Math.Log10(0.001 * 6000 + 1), sixteenBitIntensity, 5);
        Assert.Equal(42.9 * Math.Log10(0.001 * 1_000_000 + 1), twentyFourBitIntensity, 5);
        Assert.True(twentyFourBitIntensity > sixteenBitIntensity);

        var vm = new MainViewModel { BitDepth = 24 };
        var window = new MainWindow { DataContext = vm };
        window.Show();
        try
        {
            var chart = window.GetVisualDescendants().OfType<WaterfallControl>().Single();
            Assert.Equal(24, chart.BitDepth);
            vm.BitDepth = 16;
            Dispatcher.UIThread.RunJobs();
            Assert.Equal(16, chart.BitDepth);
        }
        finally { window.Close(); }
    }

    [AvaloniaTheory]
    [InlineData(0, -6.0206)]
    [InlineData(1, -12.0412)]
    [InlineData(2, -18.0618)]
    public void Waterfall_ReadsSelectedCell_InSelectedAggregationMode(int mode, double expectedDb)
    {
        var chart = CreateWaterfall(mode);
        var window = new Window { Width = 1072, Height = 260, Content = chart };
        window.Show();
        try
        {
            window.MouseMove(new Point(40.5, 7.5)); // Column 2, row 1 in the displayed bitmap.
            var reading = chart.GetHoverReadout();
            Assert.NotNull(reading);
            Assert.Equal(expectedDb, reading.Value.LevelDb!.Value, 4);
            Assert.Equal(58.59375, reading.Value.FrequencyHz, 6);
            Assert.Null(reading.Value.CursorDb); // The vertical axis represents time, not dB.

            window.MouseMove(new Point(40.5, 8.5)); // Unpopulated row.
            Assert.Null(chart.GetHoverReadout()!.Value.LevelDb);
        }
        finally { window.Close(); }
    }

    [AvaloniaFact]
    public void Waterfall_UpdatesUnderStationaryPointer_AndDistinguishesSilenceFromNoData()
    {
        var chart = CreateWaterfall(0);
        var window = new Window { Width = 1072, Height = 260, Content = chart };
        window.Show();
        try
        {
            window.MouseMove(new Point(40.5, 7.5));
            var before = Capture(window);
            var updated = (float[])chart.SpectrogramMax!.Clone();
            updated[1026] = 0.25f;
            chart.SpectrogramMax = updated;
            Assert.Equal(-12.0412, chart.GetHoverReadout()!.Value.LevelDb!.Value, 4);
            Assert.False(before.SequenceEqual(Capture(window)));

            updated = (float[])updated.Clone();
            updated[1026] = 0;
            chart.SpectrogramMax = updated;
            Assert.Equal(double.NegativeInfinity, chart.GetHoverReadout()!.Value.LevelDb);

            chart.SpectrogramRowCount = new int[250];
            Assert.Null(chart.GetHoverReadout()!.Value.LevelDb);
        }
        finally { window.Close(); }
    }

    [AvaloniaFact]
    public void Waterfall_RightAndBottomEdges_SelectLastCell()
    {
        var chart = CreateWaterfall(0);
        chart.SpectrogramRowCount![249] = 1;
        chart.SpectrogramMax![249 * 1024 + 1023] = 0.5f;
        var window = new Window { Width = 1072, Height = 260, Content = chart };
        window.Show();
        try
        {
            window.MouseMove(new Point(1062, 256));
            Assert.Equal(24000, chart.GetHoverReadout()!.Value.FrequencyHz);
            Assert.Equal(-6.0206, chart.GetHoverReadout()!.Value.LevelDb!.Value, 4);
        }
        finally { window.Close(); }
    }

    [AvaloniaFact]
    public void Charts_ShareFrequencyGuide_AndKeepWaterfallButtonsWorking()
    {
        var spectrum = new SpectrumGraphControl { Height = 236, SampleRate = 48000 };
        var waterfall = new WaterfallControl { Height = 260, SampleRate = 48000 };
        var vm = new MainViewModel();
        foreach (FrequencyChartControl chart in new FrequencyChartControl[] { spectrum, waterfall })
            chart.Bind(FrequencyChartControl.HoverFrequencyProperty,
                new Binding(nameof(MainViewModel.HoverFrequency)) { Source = vm, Mode = BindingMode.TwoWay });
        var window = new Window
        {
            Width = 548, Height = 496,
            Content = new StackPanel { Children = { spectrum, waterfall } }
        };
        window.Show();
        try
        {
            window.MouseMove(new Point(288, 100));
            Assert.Equal(12000, waterfall.HoverFrequency);
            Assert.Null(waterfall.GetHoverReadout());

            window.MouseMove(new Point(163, 336));
            Assert.Equal(6000, spectrum.HoverFrequency);
            Assert.NotNull(waterfall.GetHoverReadout());
            Assert.Null(spectrum.GetHoverReadout());

            // MAX button is at y=12+250*0.12 within the waterfall.
            window.MouseMove(new Point(20, 278));
            Assert.Null(spectrum.HoverFrequency);
            Assert.Null(waterfall.GetHoverReadout());
            window.MouseDown(new Point(20, 278), Avalonia.Input.MouseButton.Left);
            window.MouseUp(new Point(20, 278), Avalonia.Input.MouseButton.Left);
            Assert.Equal(1, waterfall.AggregationMode);
        }
        finally { window.Close(); }
    }

    private static WaterfallControl CreateWaterfall(int mode)
    {
        var max = new float[250 * 1024];
        var avg = new float[250 * 1024];
        var min = new float[250 * 1024];
        var counts = new int[250];
        max[1026] = 0.5f;
        avg[1026] = 0.5f; // Two frames: mean amplitude is 0.25, not 0.5.
        min[1026] = 0.125f;
        counts[1] = 2;
        return new WaterfallControl
        {
            SampleRate = 48000, AggregationMode = mode, TrackProgress = 1,
            SpectrogramMax = max, SpectrogramAvg = avg, SpectrogramMin = min, SpectrogramRowCount = counts
        };
    }

    [AvaloniaFact]
    public void Spectrum_RecalculatesOnResizeAndSampleRateChange_AndClearsOnDetach()
    {
        var chart = new SpectrumGraphControl { SampleRate = 48000 };
        var window = new Window { Width = 548, Height = 236, Content = chart };
        window.Show();
        try
        {
            window.MouseMove(new Point(288, 116));
            Assert.Equal(12000, chart.HoverFrequency);
            window.Width = 1048;
            Dispatcher.UIThread.RunJobs();
            Assert.Equal(6000, chart.HoverFrequency);
            chart.SampleRate = 96000;
            Assert.Equal(12000, chart.HoverFrequency);
            window.Content = null;
            Assert.Null(chart.HoverFrequency);
            Assert.Null(chart.GetHoverReadout());
        }
        finally { window.Close(); }
    }

    [AvaloniaFact]
    public void MainWindow_XamlSynchronizesGuides_AndClearsThemWhenLeavingCharts()
    {
        var fixture = CreateWaterfall(0);
        var vm = new MainViewModel
        {
            SampleRate = 48000,
            SpectrumMagnitudes = [-60, -30, -12, -48, -70],
            SpectrogramMax = fixture.SpectrogramMax,
            SpectrogramAvg = fixture.SpectrogramAvg,
            SpectrogramMin = fixture.SpectrogramMin,
            SpectrogramRowCount = fixture.SpectrogramRowCount,
            TrackProgress = 1
        };
        var window = new MainWindow { DataContext = vm };
        window.Show();
        try
        {
            var spectrum = window.GetVisualDescendants().OfType<SpectrumGraphControl>().Single();
            var waterfall = window.GetVisualDescendants().OfType<WaterfallControl>().Single();
            var spectrumPoint = new Point(38 + (spectrum.Bounds.Width - 48) / 2, 100);
            window.MouseMove(spectrum.TranslatePoint(spectrumPoint, window)!.Value);
            Assert.Equal(12000, vm.HoverFrequency);
            Assert.Equal(12000, waterfall.HoverFrequency);
            SavePreview(window, "spectrum-hover.png");

            var waterfallPoint = new Point(38 + (waterfall.Bounds.Width - 48) * 2.5 / 1024,
                6 + (waterfall.Bounds.Height - 10) * 1.5 / 250);
            window.MouseMove(waterfall.TranslatePoint(waterfallPoint, window)!.Value);
            Assert.Equal(vm.HoverFrequency, spectrum.HoverFrequency);
            Assert.Equal(-6.0206, waterfall.GetHoverReadout()!.Value.LevelDb!.Value, 4);
            SavePreview(window, "waterfall-hover.png");

            window.MouseMove(new Point(5, 5));
            Assert.Null(vm.HoverFrequency);
            Assert.Null(spectrum.HoverFrequency);
            Assert.Null(waterfall.HoverFrequency);
        }
        finally { window.Close(); }
    }

    private static void SavePreview(Window window, string fileName)
    {
        // Keep inspection artifacts with the ignored build output, not in the source tree.
        string directory = Path.Combine(AppContext.BaseDirectory, "hover-previews");
        Directory.CreateDirectory(directory);
        using var frame = window.CaptureRenderedFrame();
        Assert.NotNull(frame);
        frame.Save(Path.Combine(directory, fileName), PngBitmapEncoderOptions.Default);
    }
}
