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

    // MusicScope Format Matrix
    [ObservableProperty]
    private string _formatName = "FLAC";

    [ObservableProperty]
    private int _bitDepth = 16;

    [ObservableProperty]
    private bool _isDsd;

    [ObservableProperty]
    private double _plr = 8.8;

    [ObservableProperty]
    private double _trackProgress = 0.0;

    [ObservableProperty]
    private double[] _peakHistory = InitializeHistory(-60.0);

    [ObservableProperty]
    private double[] _loudnessHistory = InitializeHistory(-60.0);

    private static double[] InitializeHistory(double defaultDb)
    {
        double[] arr = new double[512];
        Array.Fill(arr, defaultDb);
        return arr;
    }

    // Loudness metrics
    [ObservableProperty]
    private double _integratedLoudness = -70.0;

    [ObservableProperty]
    private double _momentaryCurrent = -60.0;

    [ObservableProperty]
    private double _momentaryMax = -70.0;

    [ObservableProperty]
    private double _shortTermCurrent = -60.0;

    [ObservableProperty]
    private double _shortTermMax = -70.0;

    [ObservableProperty]
    private double _loudnessRange = 0.0;

    [ObservableProperty]
    private int[]? _sModeHistogram;

    [ObservableProperty]
    private int _sModeMaxCount;

    [ObservableProperty]
    private double _lraLow = -70.0;

    [ObservableProperty]
    private double _lraHigh = -70.0;

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
    private double[]? _instantSpectrumMagnitudes;

    [ObservableProperty]
    private float[]? _goniometerPointsX;

    [ObservableProperty]
    private float[]? _goniometerPointsY;

    [ObservableProperty]
    private byte[]? _stereoDensityCloud;

    [ObservableProperty]
    private float[]? _spectrogramMax;

    [ObservableProperty]
    private float[]? _spectrogramAvg;

    [ObservableProperty]
    private float[]? _spectrogramMin;

    [ObservableProperty]
    private int[]? _spectrogramRowCount;

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
        TrackProgress = 0.0;
        MomentaryCurrent = -60.0;
        MomentaryMax = -60.0;
        ShortTermCurrent = -60.0;
        ShortTermMax = -60.0;
        PeakHistory = InitializeHistory(-60.0);
        LoudnessHistory = InitializeHistory(-60.0);
        StereoDensityCloud = null;
        SpectrogramMax = null;
        SpectrogramAvg = null;
        SpectrogramMin = null;
        SpectrogramRowCount = null;
        SModeHistogram = null;
        SModeMaxCount = 0;
        LraLow = -70.0;
        LraHigh = -70.0;
        FilePath = path;
        TrackTitle = Path.GetFileNameWithoutExtension(path);

        try
        {
            var info = await _decoder.ProbeAsync(path, ct);
            FormatDetails = $"{info.FormatName} | {info.SampleRate:0} Hz | {info.ChannelCount} Channels | {info.BitDepth}-bit | {info.Duration:mm\\:ss}";
            SampleRate = info.SampleRate;
            FormatName = info.FormatName;
            BitDepth = info.BitDepth;
            IsDsd = info.FormatName.Contains("DSD", StringComparison.OrdinalIgnoreCase) ||
                    info.FormatName.Contains("DSF", StringComparison.OrdinalIgnoreCase) ||
                    info.FormatName.Contains("DFF", StringComparison.OrdinalIgnoreCase);

            var engine = new AudioAnalysisEngine(info.SampleRate, info.ChannelCount, info.TotalFrames);

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
            TrackProgress = 1.0;
        }
    }

    private void UpdateFromSnapshot(AudioRealtimeSnapshot s)
    {
        AnalysisProgress = s.ProgressFraction * 100.0;
        TrackProgress = s.ProgressFraction;
        IntegratedLoudness = s.RunningIntegratedLufs;

        MomentaryCurrent = s.MomentaryLufs;
        if (s.MomentaryLufs > MomentaryMax)
            MomentaryMax = s.MomentaryLufs;

        ShortTermCurrent = s.ShortTermLufs;
        if (s.ShortTermLufs > ShortTermMax)
            ShortTermMax = s.ShortTermLufs;

        if (s.RunningLra > 0.0)
        {
            LoudnessRange = s.RunningLra;
        }

        if (s.SModeHistogram != null)
        {
            SModeHistogram = s.SModeHistogram;
            SModeMaxCount = s.SModeMaxCount;
            LraLow = s.SModeLraLow;
            LraHigh = s.SModeLraHigh;
        }

        SamplePeakLeft = s.CurrentPeakLeftDb;
        SamplePeakRight = s.CurrentPeakRightDb;
        TruePeakLeft = s.MaxTruePeakLeftDb;
        TruePeakRight = s.MaxTruePeakRightDb;
        RmsLeft = s.CurrentRmsLeftDb;
        RmsRight = s.CurrentRmsRightDb;

        if (s.RunningPlrDb > 0.0)
        {
            Plr = s.RunningPlrDb;
        }
        else
        {
            double currentMaxPeak = Math.Max(s.CurrentPeakLeftDb, s.CurrentPeakRightDb);
            if (currentMaxPeak > -60.0 && s.RunningIntegratedLufs > -60.0)
            {
                Plr = Math.Max(0.0, currentMaxPeak - s.RunningIntegratedLufs);
            }
        }
        if (s.RunningInstantCrestDb > 0.0)
        {
            CrestFactor = s.RunningInstantCrestDb;
        }
        else if (s.RunningCrestDb > 0.0)
        {
            CrestFactor = s.RunningCrestDb;
        }

        PhaseCorrelation = s.Correlation;
        MidLevel = s.MidLevelDb;
        SideLevel = s.SideLevelDb;

        SpectrumMagnitudes = s.CumulativePeakSpectrumDb ?? s.InstantSpectrumDb;
        InstantSpectrumMagnitudes = s.InstantSpectrumDb;
        GoniometerPointsX = s.GoniometerPointsX;
        GoniometerPointsY = s.GoniometerPointsY;

        if (s.SpectrogramMax != null)
        {
            SpectrogramMax = s.SpectrogramMax;
            SpectrogramAvg = s.SpectrogramAvg;
            SpectrogramMin = s.SpectrogramMin;
            SpectrogramRowCount = s.SpectrogramRowCount;
        }

        if (s.PeakHistory != null)
            PeakHistory = s.PeakHistory;
        if (s.LoudnessHistory != null)
            LoudnessHistory = s.LoudnessHistory;
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
        MomentaryCurrent = -60.0;
        MomentaryMax = r.Loudness.MomentaryMax;
        ShortTermCurrent = -60.0;
        ShortTermMax = r.Loudness.ShortTermMax;
        LoudnessRange = r.Loudness.LoudnessRange;
        LraLow = r.Loudness.LraLow;
        LraHigh = r.Loudness.LraHigh;
        SModeHistogram = r.Loudness.SModeHistogram;
        SModeMaxCount = r.Loudness.SModeMaxCount;

        SamplePeakLeft = r.Levels.SamplePeakLeftDb;
        SamplePeakRight = r.Levels.SamplePeakRightDb;
        TruePeakLeft = r.Levels.TruePeakLeftDb;
        TruePeakRight = r.Levels.TruePeakRightDb;
        RmsLeft = r.Levels.RmsLeftDb;
        RmsRight = r.Levels.RmsRightDb;
        CrestFactor = r.Levels.CrestFactorDb;
        DynamicRange = r.Levels.DynamicRangeDb;
        Plr = r.Loudness.PlrAvgDb > 0.0 ? r.Loudness.PlrAvgDb : Math.Max(0.0, Math.Max(r.Levels.TruePeakLeftDb, r.Levels.TruePeakRightDb) - r.Loudness.IntegratedLoudness);

        PhaseCorrelation = r.Stereo.Correlation;
        MidLevel = r.Stereo.MidLevelDb;
        SideLevel = r.Stereo.SideLevelDb;

        SpectrumMagnitudes = r.SpectrumMagnitudesDb;
        if (r.PeakHistory != null && r.PeakHistory.Length > 0)
            PeakHistory = r.PeakHistory;
        if (r.LoudnessHistory != null && r.LoudnessHistory.Length > 0)
            LoudnessHistory = r.LoudnessHistory;
        if (r.SpectrogramMax != null)
        {
            SpectrogramMax = r.SpectrogramMax;
            SpectrogramAvg = r.SpectrogramAvg;
            SpectrogramMin = r.SpectrogramMin;
            SpectrogramRowCount = r.SpectrogramRowCount;
        }
        StereoDensityCloud = r.Stereo.DensityCloud;
        GoniometerPointsX = null;
        GoniometerPointsY = null;
        TrackProgress = 1.0;
    }
}
