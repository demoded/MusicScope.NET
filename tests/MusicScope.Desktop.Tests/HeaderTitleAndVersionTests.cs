using System.Linq;
using Avalonia.Controls;
using Avalonia.Headless.XUnit;
using Avalonia.Layout;
using Avalonia.VisualTree;
using MusicScope.Desktop.ViewModels;
using MusicScope.Desktop.Views;
using Xunit;

namespace MusicScope.Desktop.Tests;

public class HeaderTitleAndVersionTests
{
    [Fact]
    public void MainViewModel_AppVersion_ResolvesSemanticVersion()
    {
        var vm = new MainViewModel();
        Assert.NotNull(vm.AppVersion);
        Assert.StartsWith("v1.", vm.AppVersion);
        Assert.Equal("v1.3.0", vm.AppVersion);
    }

    [AvaloniaFact]
    public void MainWindow_Header_DisplaysAppNameAndCurrentVersion()
    {
        var vm = new MainViewModel();
        var window = new MainWindow { DataContext = vm };
        window.Show();
        try
        {
            var headerStack = window.GetVisualDescendants()
                .OfType<StackPanel>()
                .First(sp => sp.Spacing == 8 && sp.Orientation == Orientation.Horizontal);

            var textBlocks = headerStack.Children.OfType<TextBlock>()
                .Concat(headerStack.Children.OfType<Border>().SelectMany(b => b.Child is TextBlock tb ? new[] { tb } : System.Array.Empty<TextBlock>()))
                .ToList();

            // Verify top left app name is "MusicScope.NET"
            var appNameBlock = textBlocks.FirstOrDefault(tb => tb.Text == "MusicScope.NET");
            Assert.NotNull(appNameBlock);
            Assert.Equal("MusicScope.NET", appNameBlock.Text);

            // Verify old tag ".NET 10" is not present anywhere in window
            var oldTagBlock = window.GetVisualDescendants().OfType<TextBlock>().FirstOrDefault(tb => tb.Text == ".NET 10");
            Assert.Null(oldTagBlock);

            // Verify version badge displays current version
            var versionTagBlock = textBlocks.FirstOrDefault(tb => tb.Text == vm.AppVersion);
            Assert.NotNull(versionTagBlock);
            Assert.Equal("v1.3.0", versionTagBlock.Text);
        }
        finally
        {
            window.Close();
        }
    }
}
