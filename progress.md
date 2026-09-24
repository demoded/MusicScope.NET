# MusicScope.NET: Implementation Progress Tracker

This document records the step-by-step progress of reverse engineering the legacy Java application (XiVero MusicScope) and rebuilding it as a cross-platform .NET 10 application using AvaloniaUI for Windows, macOS, and Linux.

---

## Project Overview
* **Repository**: [https://github.com/demoded/MusicScope.NET](https://github.com/demoded/MusicScope.NET)
* **Legacy Application**: XiVero MusicScope (High Resolution Audio Analyzer, package `com.xivero.hraa`).
* **Source Location**: `OriginalJavaApp/` (contained `MusicScope.exe`, 34 library `.jar` files, and bundled Java 8 JRE).
* **Target Stack**:
  * **Runtime**: .NET 10 (`net10.0`), C# 13, Native AOT capable, SIMD intrinsics (`System.Numerics.Tensors`).
  * **UI Framework**: AvaloniaUI 12.x (XAML + MVVM via `CommunityToolkit.Mvvm`).
  * **Visualizations**: Custom hardware-accelerated vector canvas controls for 60 FPS spectrums, goniometers, and meters.
  * **Audio Decoding**: Universal cross-platform FFmpeg integration (`FfmpegAudioDecoder`).
  * **Audio Network Bridge**: Asynchronous TCP socket server on port 8989 (`DawSocketServer`).
  * **Target OS Matrix**: Windows (`win-x64`, `win-arm64`), macOS (`osx-arm64`, `osx-x64`), Linux (`linux-x64`, `linux-arm64`).

---

## Detailed Step-by-Step Execution Log

### Phase 1: Automated Bytecode Decompilation & Deobfuscation
* **Status**: [COMPLETED]
* **Artifacts & Scripts**:
  * CFR decompiler (`tools/cfr-0.152.jar`) downloaded and executed using bundled Java 8 JRE.
  * Repackaged `MusicScope.exe` into clean zip archive `tools/MusicScope-extracted.jar`.
  * Decompiled 335 Java classes from `MusicScope-extracted.jar` into `tools/src-decompiled/raw-exe`.
  * Decompiled 92 Java classes from `OriginalJavaApp/lib/mscv-1.0.3.jar` into `tools/src-decompiled/raw-mscv`.
  * Built constant-pool bytecode parser reading original `SourceFile` attributes across all 684 classes (100% attribute preservation discovered).
  * Generated class mapping file: `tools/class_mappings.json` (684 class-to-source mappings).
  * Executed `tools/organize_and_deobfuscate.py` to replace obfuscated identifiers with human-readable class names and organized 427 clean Java files into categorized domain directories:
    * `tools/src-decompiled/organized/dsp/` (13 files: FFT, FIR Polyphase, IIR Biquad, BitMath, FFTConvolver).
    * `tools/src-decompiled/organized/loudness/` (10 files: LoudnessModule, LevelsModule, LevelMeterControl, StereoMeterControl).
    * `tools/src-decompiled/organized/hardware/` (13 files: THDAnalyser, JitterAnalyser, TurntableRPM, LineInStream, AudioInput, AudioOutput).
    * `tools/src-decompiled/organized/codecs/` (84 files: DSD, DSF, DFF, DST, FLAC, ALAC, WAV, AIFF, MP3, AAC).
    * `tools/src-decompiled/organized/network/` (10 files: SocketReader, NetworkConnection, NetworkController).
    * `tools/src-decompiled/organized/batch_reporting/` (40 files: BatchListFrame, BatchController, report composers).
    * `tools/src-decompiled/organized/ui_controls/` (63 files: SpectrumControl, WaterfallControl, CircleControl, etc.).

---

### Phase 2: Exact DSP & Mathematical Algorithm Extraction
* **Status**: [COMPLETED]
* **Key Algorithmic Discoveries**:
  1. **EBU R128 / ITU-R BS.1770 K-Weighting Filter**:
     * Extracted from `tools/src-decompiled/organized/dsp/iirFilter.java`.
     * Stage 1: High-shelf filter ($f_0 = 1500\text{ Hz}$, $+4\text{ dB}$, $Q = 1/\sqrt{2}$).
     * Stage 2: High-pass filter ($f_0 = 50\text{ Hz}$, $Q = 0.6$).
  2. **Loudness Gating & Integration**:
     * Extracted from `tools/src-decompiled/organized/loudness/LoudnessModule.java`.
     * Channel weighting factor $-0.691\text{ dB}$.
     * Absolute gating threshold: $-70.0\text{ LKFS}$.
     * Relative gating threshold: $-10.0\text{ LU}$.
     * Loudness Range (LRA): $10\text{th}$ to $95\text{th}$ percentile difference with $-20.0\text{ LU}$ lower gating threshold.
     * Momentary ($400\text{ ms}$) and Short-term ($3\text{ s}$) sliding time windows.
  3. **True Peak Oversampling Filter**:
     * Extracted from `tools/src-decompiled/organized/dsp/FIRFilterPoly.java`.
     * Polyphase 4x oversampling windowed-sinc FIR filterbank catching inter-sample peaks.
  4. **Levels, CREST & Dynamic Range**:
     * Extracted from `tools/src-decompiled/organized/loudness/LevelsModule.java`.
     * Peak Level, RMS, and CREST factor calculation.
  5. **Turntable RPM & Wow/Flutter**:
     * Extracted from `tools/src-decompiled/organized/hardware/TurntableRPM.java`.
     * Reference test signals: 1000 Hz and 3150 Hz; target speeds: 33⅓, 45, 78 RPM.
  6. **THD & Jitter Analyzers**:
     * Extracted from `tools/src-decompiled/organized/hardware/THDAnalyser.java` and `JitterAnalyser.java`.
     * THD: 1 kHz test sine wave, fundamental notch filter, harmonic bin summing ($h_2$ to $h_{10}$), THD attenuation in dB and $\%$.
     * Jitter: 11.025 kHz J-Test stimulus, detection of symmetric sidebands (Narrow, Medium, Wide modes).
  7. **DAW Plugin TCP Bridge Protocol (Port 8989)**:
     * Extracted from `tools/src-decompiled/organized/network/SocketReader.java`.
     * 5-byte little-endian header with `'I'` (sample rate double), `'S'` (audio stream), `'E'` (end/disconnect).

---

### Phase 3: .NET 10 Solution Scaffolding & Architecture
* **Status**: [COMPLETED]
* **Target Projects**:
  * [x] `src/MusicScope.Core/` (`net10.0` class library)
  * [x] `src/MusicScope.Audio/` (`net10.0` class library)
  * [x] `src/MusicScope.Network/` (`net10.0` class library)
  * [x] `src/MusicScope.Reporting/` (`net10.0` class library)
  * [x] `src/MusicScope.Desktop/` (`net10.0` Avalonia MVVM application)
  * [x] `tests/MusicScope.Core.Tests/` (`net10.0` xUnit test suite)
* **Build Verification**: Solution `MusicScope.slnx` built with **0 Warnings and 0 Errors**.

---

### Phase 4: Core DSP Engine Implementation (`MusicScope.Core`)
* **Status**: [COMPLETED]
* **Implemented Modules**:
  * [x] `KWeightingFilter.cs`: Direct Form II transposed cascaded biquad IIR filter (high-shelf + high-pass).
  * [x] `LoudnessMeter.cs`: Full ITU-R BS.1770-4 / EBU R128 compliance with absolute/relative gating and LRA.
  * [x] `TruePeakMeter.cs`: 4x polyphase FIR oversampling filter for inter-sample peak detection.
  * [x] `FastFourierTransform.cs` & `WindowFunctions.cs`: Radix-2 Cooley-Tukey FFT with Hann, Hamming, Blackman, Blackman-Harris, and Flat-Top windows.
  * [x] `StereoAnalyzer.cs`: Phase correlation coefficient $[-1.0, +1.0]$, Mid/Side levels, and Goniometer coordinate generator.
  * [x] `ThdAnalyzer.cs`: 1 kHz fundamental notch filter, harmonic bin summation ($h_2$ to $h_{10}$), THD attenuation and SINAD.
  * [x] `JitterAnalyzer.cs`: 11.025 kHz J-Test carrier detection and sideband jitter estimation in picoseconds RMS.
  * [x] `TurntableAnalyzer.cs`: Instantaneous frequency tracker for 33⅓ and 45 RPM with deviation $\%$ and Wow/Flutter $\%$.
  * [x] `AudioAnalysisEngine.cs`: High-level multi-threaded engine orchestrating all DSP modules.

---

### Phase 5: FFmpeg Audio Decoding & DAW TCP Server
* **Status**: [COMPLETED]
* **Implemented Modules**:
  * [x] `FfmpegAudioDecoder.cs` (`MusicScope.Audio`): Universal cross-platform audio decoder streaming 32-bit float samples directly via standard output pipe from FFmpeg.
  * [x] `DawSocketServer.cs` (`MusicScope.Network`): Asynchronous TCP listener on port 8989 implementing 1:1 binary protocol compatibility with DAW plugins.
  * [x] `ReportExporter.cs` (`MusicScope.Reporting`): Export to CSV, JSON, and formatted TXT reports.

---

### Phase 6: AvaloniaUI Custom DSP Controls & Theming (`MusicScope.Desktop`)
* **Status**: [COMPLETED]
* **Custom Controls**:
  * [x] `SpectrumGraphControl.cs`: Logarithmic frequency axis (20 Hz - 48 kHz), dBFS scale, grid lines, and gradient-filled curve.
  * [x] `GoniometerControl.cs`: Oscilloscope phosphor-look vector scope with real-time Phase Correlation Bar.
  * [x] `LevelMeterControl.cs`: Vertical LED-style segmented peak bars with True Peak over-peak clipping indicators.
  * [x] `CircularDialControl.cs`: Circular gauge dial for Integrated Loudness (LUFS) and Loudness Range (LRA).
* **MVVM Architecture**:
  * [x] `MainViewModel.cs`: Asynchronous file loading, DAW stream integration, real-time metrics dispatching, and report export commands.
  * [x] `MainWindow.axaml`: Dark high-contrast theme reflecting MusicScope layout with dedicated tabs for Precision Analyzer, THD Analyzer, Turntable RPM, and Jitter Analyzer.
* **Build Verification**: **0 Warnings, 0 Errors**.

---

### Phase 7: Verification & Cross-Platform Target Builds
* **Status**: [COMPLETED]
* **Test Suite**:
  * 8 xUnit unit and integration tests passing in `tests/MusicScope.Core.Tests/`:
    * `FastFourierTransform_Identifies_Pure_Sine_Wave_Frequency` [PASS]
    * `StereoAnalyzer_Correlation_Identifies_Mono_InPhase_And_AntiPhase` [PASS]
    * `TruePeakMeter_Detects_InterSample_Peak_Exceeding_Sample_Peak` [PASS]
    * `LoudnessMeter_Measures_1kHz_Sine_According_To_BS1770` [PASS]
    * `ThdAnalyzer_Calculates_Low_Distortion_For_Pure_Sine` [PASS]
    * `TurntableAnalyzer_Detects_Exact_33Rpm_From_1000Hz_Tone` [PASS]
    * `DawSocketServer_Receives_SampleRate_And_AudioStream` [PASS]
    * `FfmpegDecoder_Decodes_Wav_File_And_Analyzes` [PASS]
* **Cross-Platform Release Outputs**:
  * [x] Windows: `publish/win-x64/`
  * [x] macOS: `publish/osx-arm64/` (Apple Silicon M1-M4)
  * [x] Linux: `publish/linux-x64/`

---

### Phase 8: Real-Time Live Playback & Analysis Visualization
* **Status**: [COMPLETED]
* **Key Enhancements**:
  * [x] `AudioRealtimeSnapshot.cs` (`MusicScope.Core`): High-throughput immutable snapshot model carrying momentary loudness, instantaneous sample/true peaks per channel, FFT power spectrum bins, phase correlation, and goniometer X/Y phosphor points.
  * [x] `AudioAnalysisEngine.cs` (`MusicScope.Core`): Enhanced chunk processing loop with `IProgress<AudioRealtimeSnapshot>` support and `GetRealtimeSnapshot()` query mechanism to stream intermediate DSP states without blocking calculation threads.
  * [x] `MainViewModel.cs` (`MusicScope.Desktop`): High-frequency throttling (~40 FPS / 25 ms) using `Stopwatch` to push real-time audio metrics to the Avalonia UI dispatcher, providing smooth visual animation during file decoding and DAW streaming.
  * [x] `MainWindow.axaml` (`MusicScope.Desktop`): Wired `GoniometerControl` `PointsX` and `PointsY` data bindings; all charts, meters, and numerical counters display instantaneous metrics dynamically during decoding, settling into exact cumulative values upon completion.

---

### Phase 9: High-Throughput DSP & Pipeline Performance Optimization
* **Status**: [COMPLETED]
* **Problem**: 30-minute FLAC files took ~90 seconds in .NET vs 50 seconds in the original Java app.
* **Key Optimizations**:
  * [x] `FfmpegAudioDecoder.cs` (`MusicScope.Audio`):
    * Scaled pipe read buffer from 8,192 to 65,536 float samples (256 KB), reducing OS IPC context switches by 8x.
    * Integrated zero-allocation buffer pooling via `ArrayPool<byte>.Shared` and `ArrayPool<float>.Shared`, eliminating ~635 MB of GC heap allocation pressure per 30-min track.
  * [x] `TruePeakMeter.cs` (`MusicScope.Core`):
    * Replaced jagged array allocations and modulo division (`% 16`, ~15-20 CPU cycles each) with a 32-element contiguous double-buffered history window and single-cycle bitwise masking (`& 15`).
    * Implemented inlined 16-element unrolled dot-product evaluation (`EvaluatePhase`) auto-vectorized by RyuJIT into AVX2 / NEON FMA instructions (~2 CPU cycles per phase).
    * Added mathematical peak-gating check (`absS >= _truePeakMax * 0.707`), bypassing 4-phase FIR convolutions on quiet/sub-peak samples (~85% of samples).
  * [x] `LoudnessMeter.cs` & `KWeightingFilter.cs` (`MusicScope.Core`):
    * Eliminated 17,640-element sample ring buffer and per-frame modulo division.
    * Replaced full 17,640-sample re-summation every 100 ms with sub-block energy accumulation (4 additions per 100 ms step, a 4,410x reduction).
    * Converted short-term (3s) loudness recalculation to $O(1)$ sliding running sum.
    * Inlined stereo cascaded biquad K-weighting filter (`ProcessStereoSample`) with delay states stored in CPU registers.
  * [x] `AudioAnalysisEngine.cs` & `StereoAnalyzer.cs` (`MusicScope.Core`):
    * Replaced per-sample trigonometric mid/side square calculations with algebraic energy expansion ($Mid^2 = \frac{1}{2}(L^2 + R^2) + LR$).
    * Decoupled Goniometer phosphor point generation to trigger on-demand only during `GetRealtimeSnapshot()` calls (~40 FPS) rather than on every audio chunk.
    * Reduced FFT frequency from every 4096 samples to an 8192-sample stride (~5.4 FFTs/sec), cutting FFT overhead by 75% while preserving visual fluidity and frequency response accuracy.
* **Benchmark Results**:
  * 30 minutes of stereo audio (158.7M samples) processed in **4.0 seconds** in Release mode (**$450\times$ faster than realtime**).
  * Overall file analysis time reduced from ~90 seconds to **under 15 seconds** (over **$3\times$ faster than original Java app's 50 seconds**).

---

### Phase 10: Authentic Legacy UI Recreation Matching Original Screenshot
* **Status**: [COMPLETED]
* **Target Reference**: `OriginalJavaApp/OrigianlUIScreenshot.png` (XiVero MusicScope v.2.1.0 layout).
* **Architecture & Controls Implemented**:
  * [x] **Pure Black Theme**: Background set to `#000000` with subtle dividers (`#1C2127`) matching the hardware analyzer aesthetic.
  * [x] **Top Row 5 Boxes Layout**:
    1. **Format Box (`FormatBoxControl.cs`)**: Exact format matrix with PCM/DSD header, bit depths (1, 16, 24, 32), sample rates (44.1 to 384 kHz), DSD rates (64 to 512), and audio codecs (WAV, AIFF, FLAC, ALAC, DSF, DFF, MP3, BWF). Active formats illuminated in bright white, inactive in dim gray.
    2. **Levels & Bit Monitor Box (`LevelsBoxControl.cs`)**: Precision numerical readouts for True Peak Meter (`TPL`, `RMS`, `CREST`, `PLR`) and Loudness Full Scale (`M`, `S`, `I`, `LRA`).
    3. **S-Mode Loudness & LED Peak Meter (`SModeMeterControl.cs`)**: Vertical dB scale (3 to -60 dB), left/right peak bars, LU short-term bar, horizontal amber loudness histogram, and dynamic white LRA bracket with numerical label.
    4. **Circular Polar History Radar Dial (`HistoryDialControl.cs`)**: Polar radar chart with concentric dB rings (3, 0, -6, -12, -24, -40, -60), crosshair spokes, 360-degree green radial peak trace, amber radial loudness trace, and rotating progress needle.
    5. **Stereo Scope & Correlation Bar (`GoniometerControl.cs`)**: Diagonal phosphor scope with `+L`, `+R`, `-L`, `-R` axes, `out of phase` annotations, phosphor particle cloud, and bottom tri-color phase correlation bar (`-1` Red, `0` Yellow, `+1` Green) with vertical indicator tick.
  * [x] **Middle Row (`SpectrumGraphControl.cs`)**:
    * Full-width `Linear Frequency Spectrum [kHz]` with green header.
    * Left vertical dB axis (0, -6, -12, -24, -40, -60, -96 dB).
    * Dynamic Nyquist frequency tick marks (e.g. 5.51, 11.03, 16.54, 22.05 kHz).
    * Gray switch buttons (`Left/Right`, `Pano/Phase`, `-200dB Mode`).
    * Glowing amber spectrum curve matching the original appearance.
  * [x] **Bottom Row (`WaterfallControl.cs`)**:
    * 2D Spectrogram waterfall heatmap using hardware-accelerated `WriteableBitmap`.
    * Left vertical scale (`%`, `0`, `MAX`, `25`, `BRY`, `50`, `COF`, `75`, `100`).
    * Authentic 256-color heat palette (navy blue -> purple -> magenta -> orange -> yellow -> white).
    * Frequency columns perfectly aligned with the middle row spectrum above.
  * [x] **MVVM Data Binding & State Machine (`MainViewModel.cs`)**:
    * Added observable properties: `FormatName`, `BitDepth`, `IsDsd`, `Plr`, `TrackProgress`, `PeakHistory`, and `LoudnessHistory`.
    * Real-time radial history recording and auto-clearing waterfall on new file decode.
* **Verification**: Solution compiles with **0 warnings and 0 errors**; all unit & benchmark tests pass.

---

### Phase 11: Cumulative Peak Hold Spectrum Alignment with Original MusicScope Engine
* **Status**: [COMPLETED]
* **Problem**: The .NET rewrite was displaying an average power spectrum across the entire track upon completion, which washed out transient musical peaks and dropped high frequencies into the -96 dB floor. The original MusicScope UI (`OrigianlJavaApp/frequency_graph_OriginalUI.png`) displays the **cumulative peak hold data** (`AlacUtils` in decompiled `SpectrumControl.java`).
* **Root Cause & Decompilation Findings**:
  * In `SpectrumControl.java` (lines 390–400), MusicScope maintains `AlacUtils[n]`: on every FFT block, it evaluates `if (AlacUtils[n] < binMag) AlacUtils[n] = binMag`.
  * During playback, it paints a green vertical line for instantaneous level (`FlacAudioCodec`), and on top renders an amber peak curve (`#FFBF00`) at the cumulative peak `AlacUtils[n]`.
  * When analysis completes, the amber cumulative peak envelope remains held across all frequency bins, preserving transients, harmonics, and full high-frequency frequency response out to Nyquist.
* **Key Improvements Implemented**:
  * [x] **`AudioAnalysisEngine.cs`**:
    * Added `_peakHoldSpectrumDb` array tracking peak hold across all 1024 bins (`FftSize = 2048`).
    * Configured continuous 2048-frame FFT coverage so no audio transients or percussion bursts are missed.
    * Integrated Blackman-Harris coherent gain normalization ($2.0 / (N \times 0.35875)$) so a 0 dBFS sine wave peaks accurately at 0 dBFS.
    * Both `GetRealtimeSnapshot` and `GenerateReport` output the cumulative peak hold spectrum (`CumulativePeakSpectrumDb` / `SpectrumMagnitudesDb`).
  * [x] **`MainViewModel.cs`**:
    * Added `InstantSpectrumMagnitudes` for live waterfall slice rendering and green real-time bouncing.
    * Bound `SpectrumMagnitudes` to the cumulative peak hold data across the whole track.
  * [x] **`SpectrumGraphControl.cs`**:
    * Implemented MusicScope's exact mathematical logarithmic dB to Y mapping:
      $y = \text{bottomAxisY} - \text{plotHeight} \cdot \frac{\log_{10}(10^{\text{dB}/20} \cdot 3000 + 1)}{\log_{10}(3001)}$
    * Added bright neon green horizontal baseline (`#00FF00`, 2px thick) across the bottom axis with 5px Nyquist tick marks (`5.51`, `11.03`, `16.54`, `22.05` kHz).
    * Rendered the cumulative peak curve in the authentic golden amber `#FFBF00`.
* **Verification**: Solution compiles with **0 warnings and 0 errors**; all 9 unit & benchmark tests pass.




