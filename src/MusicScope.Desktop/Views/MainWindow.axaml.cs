using System;
using System.IO;
using Avalonia.Controls;
using Avalonia.Interactivity;
using Avalonia.Platform.Storage;
using MusicScope.Desktop.ViewModels;

namespace MusicScope.Desktop.Views;

public partial class MainWindow : Window
{
    public MainWindow()
    {
        InitializeComponent();
    }

    private async void OnOpenFileClick(object? sender, RoutedEventArgs e)
    {
        var topLevel = GetTopLevel(this);
        if (topLevel == null) return;

        var files = await topLevel.StorageProvider.OpenFilePickerAsync(new FilePickerOpenOptions
        {
            Title = "Open Audio File for Analysis",
            AllowMultiple = false,
            FileTypeFilter =
            [
                new FilePickerFileType("Audio Files")
                {
                    Patterns = ["*.wav", "*.flac", "*.alac", "*.m4a", "*.mp3", "*.aac", "*.aiff", "*.aif", "*.dsf", "*.dff", "*.ogg", "*.opus", "*.wma"]
                },
                new FilePickerFileType("All Files") { Patterns = ["*.*"] }
            ]
        });

        if (files.Count > 0 && DataContext is MainViewModel vm)
        {
            string localPath = files[0].Path.LocalPath;
            await vm.AnalyzeFileCommand.ExecuteAsync(localPath);
        }
    }

    private async void OnExportReportClick(object? sender, RoutedEventArgs e)
    {
        var topLevel = GetTopLevel(this);
        if (topLevel == null || DataContext is not MainViewModel vm || vm.CurrentReport == null)
            return;

        var file = await topLevel.StorageProvider.SaveFilePickerAsync(new FilePickerSaveOptions
        {
            Title = "Export Audio Analysis Report",
            DefaultExtension = "txt",
            SuggestedFileName = $"{vm.TrackTitle}_report",
            FileTypeChoices =
            [
                new FilePickerFileType("Text Report (.txt)") { Patterns = ["*.txt"] },
                new FilePickerFileType("CSV Spreadsheet (.csv)") { Patterns = ["*.csv"] },
                new FilePickerFileType("JSON Metadata (.json)") { Patterns = ["*.json"] }
            ]
        });

        if (file != null)
        {
            string targetPath = file.Path.LocalPath;
            await vm.ExportReportCommand.ExecuteAsync(targetPath);
        }
    }
}