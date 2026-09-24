using System;
using System.IO;
using System.Threading;
using System.Threading.Tasks;
using Avalonia.Threading;
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using MusicScope.Audio;
using MusicScope.Core;
using MusicScope.Core.Hardware;
using MusicScope.Network;
using MusicScope.Reporting;

namespace MusicScope.Desktop.ViewModels;

public partial class MainViewModel : ViewModelBase
{
    private readonly IAudioDecoder _decoder;
    private readonly DawSocketServer _dawServer;
    private AudioAnalysisEngine? _liveEngine;
    private CancellationTokenSource? _analysisCts;

    [ObservableProperty]
    private string _trackTitle = "No Audio Loaded";

    [ObservableProperty]
    private string _filePath = string.Empty;

    [ObservableProperty]
    private string _formatDetails = "Select an audio file or connect DAW via TCP port 8989";

    [ObservableProperty]
    private bool _isAnalyzing;

    [ObservableProperty]
    private double _analysisProgress;

    // Loudness metrics
    [ObservableProperty]
    private double _integratedLoudness = -70.0;

    [ObservableProperty]
    private double _momentaryMax = -70.0;

    [ObservableProperty]
    private double _shortTermMax = -70.0;

    [ObservableProperty]
    private double _loudnessRange = 0.0;

    // Peak & RMS levels
    [ObservableProperty]
    private double _samplePeakLeft = -100.0;

    [ObservableProperty]
    private double _samplePeakRight = -100.0;

    [ObservableProperty]
    private double _truePeakLeft = -100.0;

    [ObservableProperty]
    private double _truePeakRight = -100.0;

    [ObservableProperty]
    private double _rmsLeft = -100.0;

    [ObservableProperty]
    private double _rmsRight = -100.0;

    [ObservableProperty]
    private double _crestFactor = 0.0;

    [ObservableProperty]
    private double _dynamicRange = 0.0;

    // Stereo field
    [ObservableProperty]
    private double _phaseCorrelation = 1.0;

    [ObservableProperty]
    private double _midLevel = -100.0;

    [ObservableProperty]
    private double _sideLevel = -100.0;

    // Spectrum & Goniometer
    [ObservableProperty]
    private double[]? _spectrumMagnitudes;

    [ObservableProperty]
    private float[]? _goniometerPointsX;

    [ObservableProperty]
    private float[]? _goniometerPointsY;

    [ObservableProperty]
    private double _sampleRate = 44100.0;

    // DAW Server
    [ObservableProperty]
    private bool _isDawServerRunning;

    [ObservableProperty]
    private string _dawStatusText = "DAW TCP Server: Stopped";

    // Hardware views
    [ObservableProperty]
    private double _thdPercent;

    [ObservableProperty]
    private double _thdAttenuationDb = -120.0;

    [ObservableProperty]
    private double _jitterPsRms;

    [ObservableProperty]
    private double _turntableRpm = 33.333;

    [ObservableProperty]
    private double _rpmDeviation;

    [ObservableProperty]
    private double _wowAndFlutter;

    public FullAnalysisReport? CurrentReport { get; private set; }

    public MainViewModel()
    {
        _decoder = new FfmpegAudioDecoder();
        _dawServer = new DawSocketServer();

        _dawServer.ConnectionStateChanged += (s, connected) =>
        {
            Dispatcher.UIThread.Post(() =>
            {
                DawStatusText = connected
                    ? "DAW TCP Server: Connected (Streaming)"
                    : "DAW TCP Server: Waiting on Port 8989...";
            });
        };

        _dawServer.AudioDataReceived += (s, e) =>
        {
            if (_liveEngine == null || Math.Abs(SampleRate - e.SampleRate) > 1.0)
            {
                SampleRate = e.SampleRate;
                _liveEngine = new AudioAnalysisEngine(e.SampleRate, 2);
            }

            int floatCount = e.AudioBytes.Length / sizeof(float);
            float[] floats = new float[floatCount];
            Buffer.BlockCopy(e.AudioBytes.ToArray(), 0, floats, 0, e.AudioBytes.Length);

            _liveEngine.ProcessAudioBlock(floats);
            var snapshot = _liveEngine.GetRealtimeSnapshot();

            Dispatcher.UIThread.Post(() => UpdateFromSnapshot(snapshot));
        };
    }

    [RelayCommand]
    public async Task AnalyzeFileAsync(string path)
    {
        if (!File.Exists(path))
            return;

        _analysisCts?.Cancel();
        _analysisCts = new CancellationTokenSource();
        var ct = _analysisCts.Token;

        IsAnalyzing = true;
        AnalysisProgress = 0.0;
        FilePath = path;
        TrackTitle = Path.GetFileNameWithoutExtension(path);

        try
        {
            var info = await _decoder.ProbeAsync(path, ct);
            FormatDetails = $"{info.FormatName} | {info.SampleRate:0} Hz | {info.ChannelCount} Channels | {info.BitDepth}-bit | {info.Duration:mm\\:ss}";
            SampleRate = info.SampleRate;

            var engine = new AudioAnalysisEngine(info.SampleRate, info.ChannelCount);

            var sw = System.Diagnostics.Stopwatch.StartNew();
            long lastUiUpdateMs = 0;

            await _decoder.DecodeAsync(path, (chunk, channels, sRate, progress) =>
            {
                engine.ProcessAudioBlock(chunk.Span);

                long now = sw.ElapsedMilliseconds;
                if (now - lastUiUpdateMs >= 25) // ~40 FPS real-time visual update
                {
                    lastUiUpdateMs = now;
                    var snapshot = engine.GetRealtimeSnapshot(progress);
                    Dispatcher.UIThread.Post(() => UpdateFromSnapshot(snapshot));
                }
                return Task.CompletedTask;
            }, ct);

            CurrentReport = engine.GenerateReport(TrackTitle, path, info.FormatName, info.Duration, info.BitDepth);
            Dispatcher.UIThread.Post(() => UpdateFromReport(CurrentReport));
        }
        catch (OperationCanceledException) { }
        catch (Exception ex)
        {
            FormatDetails = $"Error analyzing file: {ex.Message}";
        }
        finally
        {
            IsAnalyzing = false;
            AnalysisProgress = 100.0;
        }
    }

    private void UpdateFromSnapshot(AudioRealtimeSnapshot s)
    {
        AnalysisProgress = s.ProgressFraction * 100.0;
        IntegratedLoudness = s.RunningIntegratedLufs;
        MomentaryMax = s.MomentaryLufs;
        ShortTermMax = s.ShortTermLufs;

        SamplePeakLeft = s.CurrentPeakLeftDb;
        SamplePeakRight = s.CurrentPeakRightDb;
        TruePeakLeft = s.MaxTruePeakLeftDb;
        TruePeakRight = s.MaxTruePeakRightDb;
        RmsLeft = s.CurrentRmsLeftDb;
        RmsRight = s.CurrentRmsRightDb;

        PhaseCorrelation = s.Correlation;
        MidLevel = s.MidLevelDb;
        SideLevel = s.SideLevelDb;

        SpectrumMagnitudes = s.InstantSpectrumDb;
        GoniometerPointsX = s.GoniometerPointsX;
        GoniometerPointsY = s.GoniometerPointsY;
    }

    [RelayCommand]
    public void ToggleDawServer()
    {
        if (IsDawServerRunning)
        {
            _ = _dawServer.StopAsync();
            IsDawServerRunning = false;
            DawStatusText = "DAW TCP Server: Stopped";
        }
        else
        {
            _dawServer.Start();
            IsDawServerRunning = true;
            DawStatusText = "DAW TCP Server: Waiting on Port 8989...";
        }
    }

    [RelayCommand]
    public async Task ExportReportAsync(string targetFilePath)
    {
        if (CurrentReport == null)
            return;

        string ext = Path.GetExtension(targetFilePath).ToLowerInvariant();
        if (ext == ".json")
        {
            await ReportExporter.ExportToJsonAsync(CurrentReport, targetFilePath);
        }
        else if (ext == ".csv")
        {
            await ReportExporter.ExportToCsvAsync([CurrentReport], targetFilePath);
        }
        else
        {
            await ReportExporter.ExportToTextReportAsync(CurrentReport, targetFilePath);
        }
    }

    private void UpdateFromReport(FullAnalysisReport r)
    {
        IntegratedLoudness = r.Loudness.IntegratedLoudness;
        MomentaryMax = r.Loudness.MomentaryMax;
        ShortTermMax = r.Loudness.ShortTermMax;
        LoudnessRange = r.Loudness.LoudnessRange;

        SamplePeakLeft = r.Levels.SamplePeakLeftDb;
        SamplePeakRight = r.Levels.SamplePeakRightDb;
        TruePeakLeft = r.Levels.TruePeakLeftDb;
        TruePeakRight = r.Levels.TruePeakRightDb;
        RmsLeft = r.Levels.RmsLeftDb;
        RmsRight = r.Levels.RmsRightDb;
        CrestFactor = r.Levels.CrestFactorDb;
        DynamicRange = r.Levels.DynamicRangeDb;

        PhaseCorrelation = r.Stereo.Correlation;
        MidLevel = r.Stereo.MidLevelDb;
        SideLevel = r.Stereo.SideLevelDb;

        SpectrumMagnitudes = r.SpectrumMagnitudesDb;
    }
}
