using System;
using System.Linq;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Headless;
using Avalonia.Headless.XUnit;
using Avalonia.Input;
using Avalonia.VisualTree;
using MusicScope.Desktop.Controls;
using MusicScope.Desktop.ViewModels;
using MusicScope.Desktop.Views;
using Xunit;

namespace MusicScope.Desktop.Tests;

public class WaterfallLogModeTests
{
    [AvaloniaFact]
    public void Waterfall_LogColumnMap_MatchesXiVeroCurvatureAndDimensions()
    {
        var map = WaterfallControl.LogColumnMap;
        Assert.Equal(WaterfallControl.BitmapWidth, map.Length);

        // Monotonic non-decreasing
        for (int i = 0; i < map.Length - 1; i++)
        {
            Assert.True(map[i] <= map[i + 1], $"Non-monotonic at index {i}: {map[i]} > {map[i + 1]}");
            Assert.InRange(map[i], 0, WaterfallControl.BitmapWidth - 1);
        }

        // Starts at 0
        Assert.Equal(0, map[0]);

        // Middle column (512) corresponds to (sqrt(21.48) - 1) / 20.48 * 1024 ~ 181
        Assert.Equal(181, map[512]);

        // Nyquist end
        Assert.InRange(map[1023], 1018, 1023);
    }

    [AvaloniaFact]
    public void Waterfall_FrequencyMapping_TransitionsBetweenLinearAndLogarithmic()
    {
        var waterfall = new WaterfallControl { SampleRate = 48000.0, IsLogarithmic = false };

        // Linear mode: 12 kHz on 24 kHz Nyquist is at 50% (fraction 0.5)
        double linearFraction = waterfall.FrequencyToFraction(12000.0);
        Assert.Equal(0.5, linearFraction, tolerance: 1e-6);

        double linearFreq = waterfall.FractionToFrequency(0.5);
        Assert.Equal(12000.0, linearFreq, tolerance: 1e-6);

        // Switch to Logarithmic mode
        waterfall.IsLogarithmic = true;

        // In log mode: middle of the plot (fraction 0.5) corresponds to ~4.26 kHz (expanding low/mid frequencies)
        double logMiddleFreq = waterfall.FractionToFrequency(0.5);
        double expectedMiddleFreq = 24000.0 * (Math.Sqrt(21.48) - 1.0) / 20.48;
        Assert.Equal(expectedMiddleFreq, logMiddleFreq, tolerance: 1e-4);

        // In log mode: 12 kHz is stretched out to ~78.8% of the plot
        double logFraction12k = waterfall.FrequencyToFraction(12000.0);
        double expectedFraction12k = Math.Log10(1.0 + 20.48 * 0.5) / Math.Log10(21.48);
        Assert.Equal(expectedFraction12k, logFraction12k, tolerance: 1e-6);
        Assert.True(logFraction12k > 0.78 && logFraction12k < 0.80);
    }

    [AvaloniaFact]
    public void Waterfall_GetLevels_MapsLogarithmicColumnUnderCursor()
    {
        var waterfall = new WaterfallControl
        {
            SampleRate = 48000.0,
            Width = 1072, // 38 margin + 1024 plot + 10 right = 1072
            Height = 260
        };

        // Populate row 0 with silence except for a peak at linear bin 181
        float[] specMax = new float[WaterfallControl.BitmapWidth * WaterfallControl.BitmapHeight];
        specMax[181] = 1.0f; // 0 dBFS peak at bin 181
        int[] rowCounts = new int[WaterfallControl.BitmapHeight];
        rowCounts[0] = 1;

        waterfall.SpectrogramMax = specMax;
        waterfall.SpectrogramAvg = specMax;
        waterfall.SpectrogramMin = specMax;
        waterfall.SpectrogramRowCount = rowCounts;
        waterfall.TrackProgress = 1.0;

        var window = new Window { Width = 1072, Height = 260, Content = waterfall };
        window.Show();
        try
        {
            // Position corresponding to linear bin 181 (x = 38 + 181) and row 0 (y = 6)
            var ptLinear = new Point(38 + 181, 6);
            var (lvlLinear, _, _) = waterfall.GetLevels(ptLinear);
            Assert.NotNull(lvlLinear);
            Assert.Equal(0.0, lvlLinear.Value, tolerance: 0.1);

            // Position at middle column (x = 38 + 512): in linear mode it is silent (< -100 dB)
            var ptMiddle = new Point(38 + 512, 6);
            var (lvlMiddleLinear, _, _) = waterfall.GetLevels(ptMiddle);
            Assert.True(lvlMiddleLinear == null || double.IsNegativeInfinity(lvlMiddleLinear.Value) || lvlMiddleLinear.Value < -100);

            // Switch to Logarithmic mode
            waterfall.IsLogarithmic = true;

            // In log mode: middle column (512) now samples bin 181 (LogColumnMap[512] == 181)!
            var (lvlMiddleLog, _, _) = waterfall.GetLevels(ptMiddle);
            Assert.NotNull(lvlMiddleLog);
            Assert.Equal(0.0, lvlMiddleLog.Value, tolerance: 0.1);
        }
        finally
        {
            window.Close();
        }
    }

    [AvaloniaFact]
    public void Waterfall_GetLevels_UsesHighResolutionLogSpectrogramWhenAvailable()
    {
        var waterfall = new WaterfallControl
        {
            SampleRate = 48000.0,
            Width = 1072,
            Height = 260
        };

        // Linear buffer has silence everywhere
        float[] specMax = new float[WaterfallControl.BitmapWidth * WaterfallControl.BitmapHeight];
        // High-res log buffer has a discrete peak specifically at column 512
        float[] specLogMax = new float[WaterfallControl.BitmapWidth * WaterfallControl.BitmapHeight];
        specLogMax[512] = 1.0f; // 0 dBFS peak at column 512 in log space

        int[] rowCounts = new int[WaterfallControl.BitmapHeight];
        rowCounts[0] = 1;

        waterfall.SpectrogramMax = specMax;
        waterfall.SpectrogramAvg = specMax;
        waterfall.SpectrogramMin = specMax;
        waterfall.SpectrogramLogMax = specLogMax;
        waterfall.SpectrogramLogAvg = specLogMax;
        waterfall.SpectrogramLogMin = specLogMax;
        waterfall.SpectrogramRowCount = rowCounts;
        waterfall.TrackProgress = 1.0;

        var window = new Window { Width = 1072, Height = 260, Content = waterfall };
        window.Show();
        try
        {
            var ptMiddle = new Point(38 + 512, 6);

            // In linear mode: uses SpectrogramMax, which is silent at 512
            waterfall.IsLogarithmic = false;
            var (lvlLinear, _, _) = waterfall.GetLevels(ptMiddle);
            Assert.True(lvlLinear == null || double.IsNegativeInfinity(lvlLinear.Value) || lvlLinear.Value < -100);

            // In log mode: uses SpectrogramLogMax directly at column 512 without 1024-bin decimation/remapping
            waterfall.IsLogarithmic = true;
            var (lvlLog, _, _) = waterfall.GetLevels(ptMiddle);
            Assert.NotNull(lvlLog);
            Assert.Equal(0.0, lvlLog.Value, tolerance: 0.1);
        }
        finally
        {
            window.Close();
        }
    }

    [AvaloniaFact]
    public void MainWindow_TwoWayBinding_TogglesLogModeAcrossBothControls()
    {
        var vm = new MainViewModel { SampleRate = 48000 };
        var window = new MainWindow { DataContext = vm };
        window.Show();
        try
        {
            var spectrum = window.GetVisualDescendants().OfType<SpectrumGraphControl>().Single();
            var waterfall = window.GetVisualDescendants().OfType<WaterfallControl>().Single();

            Assert.False(vm.IsLogarithmic);
            Assert.False(spectrum.IsLogarithmic);
            Assert.False(waterfall.IsLogarithmic);

            // Click action 0 on SpectrumGraphControl ("Linear Frequency Spectrum [kHz]")
            var actionBounds = spectrum.GetActionBounds(0);
            var clickPoint = spectrum.TranslatePoint(actionBounds.Center, window)!.Value;

            window.MouseDown(clickPoint, MouseButton.Left);
            window.MouseUp(clickPoint, MouseButton.Left);

            // Both controls and the ViewModel should now be in Logarithmic mode
            Assert.True(vm.IsLogarithmic);
            Assert.True(spectrum.IsLogarithmic);
            Assert.True(waterfall.IsLogarithmic);

            // Click action 0 again to toggle back to Linear mode
            window.MouseDown(clickPoint, MouseButton.Left);
            window.MouseUp(clickPoint, MouseButton.Left);

            Assert.False(vm.IsLogarithmic);
            Assert.False(spectrum.IsLogarithmic);
            Assert.False(waterfall.IsLogarithmic);
        }
        finally
        {
            window.Close();
        }
    }
}
