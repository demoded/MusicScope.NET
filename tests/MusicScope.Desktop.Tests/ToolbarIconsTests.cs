using System;
using System.Linq;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Headless.XUnit;
using Avalonia.Media.Imaging;
using Avalonia.VisualTree;
using MusicScope.Desktop.ViewModels;
using MusicScope.Desktop.Views;

namespace MusicScope.Desktop.Tests;

public class ToolbarIconsTests
{
    [AvaloniaFact]
    public void MainWindow_StartMeasurementButton_DisplaysOriginalMicroscopeIcon()
    {
        var window = new MainWindow { DataContext = new MainViewModel() };
        window.Show();
        try
        {
            var button = window.GetVisualDescendants().OfType<Button>().Single(b => b.Name == "StartMeasurementBtn");
            var icon = button.GetVisualDescendants().OfType<Image>().Single(i => i.Name == "StartMeasurementIcon");
            var bitmap = Assert.IsAssignableFrom<Bitmap>(icon.Source);
            Assert.Equal(new PixelSize(16, 16), bitmap.PixelSize);
        }
        finally
        {
            window.Close();
        }
    }
}
