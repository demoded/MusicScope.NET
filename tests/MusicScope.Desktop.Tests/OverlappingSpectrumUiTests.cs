using System;
using System.Linq;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Controls.Primitives;
using Avalonia.Headless.XUnit;
using Avalonia.VisualTree;
using MusicScope.Core;
using MusicScope.Desktop.Controls;
using MusicScope.Desktop.ViewModels;
using MusicScope.Desktop.Views;

namespace MusicScope.Desktop.Tests;

public class OverlappingSpectrumUiTests
{
    [AvaloniaFact]
    public void SpectrumAndWaterfall_AgreeOnFrequencyAndMagnitude_InOverlappingMode()
    {
        const int sampleRate = 96000;
        const int frames = sampleRate * 15; // >= 250 intervals so every waterfall row is populated
        const double freq = 24000.0; // Exact quarter sample rate (Nyquist / 2)
        var samples = new float[frames * 2];
        for (int f = 0; f < frames; f++)
        {
            float s = (float)(0.5 * Math.Sin(2.0 * Math.PI * freq * f / sampleRate));
            samples[f * 2] = s;
            samples[f * 2 + 1] = s;
        }

        var engine = new AudioAnalysisEngine(sampleRate, 2, frames, SpectrumAnalysisMode.Overlapping);
        engine.ProcessAudioBlock(samples);
        var report = engine.GenerateReport();

        var spectrum = new SpectrumGraphControl
        {
            Width = 1072,
            Height = 236,
            SampleRate = sampleRate,
            BitDepth = 24,
            LinearSpectrum = report.LinearSpectrum,
            LogSpectrum = report.LogSpectrum,
            MagnitudesDb = report.SpectrumMagnitudesDb
        };
        var waterfall = new WaterfallControl
        {
            Width = 1072,
            Height = 260,
            SampleRate = sampleRate,
            BitDepth = 24,
            SpectrogramMax = report.SpectrogramMax,
            SpectrogramAvg = report.SpectrogramAvg,
            SpectrogramMin = report.SpectrogramMin,
            SpectrogramLogMax = report.SpectrogramLogMax,
            SpectrogramLogAvg = report.SpectrogramLogAvg,
            SpectrogramLogMin = report.SpectrogramLogMin,
            SpectrogramRowCount = report.SpectrogramRowCount,
            TrackProgress = 1.0
        };

        var window = new Window
        {
            Width = 1072,
            Height = 496,
            Content = new StackPanel { Children = { spectrum, waterfall } }
        };
        window.Show();
        try
        {
            // Hover at exact midpoint of plot (X = 38 + 512 = 550) => 24,000 Hz
            var specReadout = spectrum.GetLevels(new Point(38 + 512, 100));
            var wfReadout = waterfall.GetLevels(new Point(38 + 512, 125));

            Assert.NotNull(specReadout.LevelDb);
            Assert.NotNull(wfReadout.LevelDb);
            Assert.InRange(specReadout.LevelDb.Value, -8.0, -4.5);
            Assert.InRange(wfReadout.LevelDb.Value, -8.0, -4.5);
            Assert.Equal(specReadout.LevelDb.Value, wfReadout.LevelDb.Value, precision: 0);
        }
        finally
        {
            window.Close();
        }
    }

    [AvaloniaFact]
    public void MainWindow_FftModeToggle_SwitchesBetweenOverlappingAndOriginalModes()
    {
        var vm = new MainViewModel();
        Assert.True(vm.IsOverlappingSpectrum);
        Assert.Equal(SpectrumAnalysisMode.Overlapping, vm.SelectedAnalysisMode);

        var window = new MainWindow { DataContext = vm };
        window.Show();
        try
        {
            var toggle = window.GetVisualDescendants().OfType<ToggleButton>().Single(b => b.Name == "FftModeToggleBtn");
            Assert.Equal("FFT: Overlap 8K", toggle.Content);

            toggle.IsChecked = false;
            Assert.False(vm.IsOverlappingSpectrum);
            Assert.Equal(SpectrumAnalysisMode.Original, vm.SelectedAnalysisMode);
            Assert.Equal("FFT: Classic 2K", toggle.Content);
        }
        finally
        {
            window.Close();
        }
    }
}
