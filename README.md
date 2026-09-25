# MusicScope.NET

A modern, high-precision, cross-platform audio analyzer and acoustic measurement suite built on **.NET 10** and **AvaloniaUI 12**.

This project is a complete modern rewrite of the original **XiVideo MusicScope v.2.1.0** (XiVero) audio analyzer, re-implemented from the ground up as a clean, local-first, open-source desktop application targeting **Windows**, **macOS** (Apple Silicon & Intel), and **Linux**.

---

## Features

### Precision Signal Analysis
* **EBU R128 / ITU-R BS.1770-4 Loudness Metering**:
  * Integrated Loudness ($L_I$) with $-70\text{ LKFS}$ absolute and $-10\text{ LU}$ relative gating.
  * Momentary Loudness ($L_m$, 400 ms) & Short-term Loudness ($L_s$, 3 s).
  * Loudness Range (LRA, EBU Tech 3342 percentile difference).
* **True Peak & Dynamic Range**:
  * 4x Polyphase FIR oversampling filter for accurate inter-sample peak detection ($\text{dBTP}$).
  * Sample Peak ($\text{dBFS}$), RMS energy, CREST factor, and official Dynamic Range ($\text{DR}$) ratings.
* **Frequency Spectrum (FFT)**:
  * Hardware-accelerated logarithmic frequency spectrum ($20\text{ Hz} - 48+\text{ kHz}$).
  * High-precision windowing (Blackman-Harris 92 dB side-lobe suppression, Hann, Flat-Top).
* **Stereo Goniometer & Phase Scope**:
  * Phosphor-look vector scope with Mid/Side coordinate projection.
  * Real-time Phase Correlation Bar ($-1.0$ to $+1.0$) with mono compatibility warning zones.

### Hardware & Acoustic Measurement Modules
* **THD Analyzer**: Total Harmonic Distortion analysis via 1 kHz test sine wave, measuring harmonic bins ($h_2$ to $h_{10}$), attenuation ($\text{dB}$), and SINAD.
* **Jitter Analyzer**: Clock jitter and sideband spur measurement utilizing the Julian Dunn 11.025 kHz J-Test stimulus.
* **Turntable RPM & Wow/Flutter**: Precision turntable speed tracking with 1000 Hz or 3150 Hz reference test records, measuring speed deviation $\%$ and 2-sigma peak-to-peak wow/flutter.

### Audio Pipeline & DAW Integration
* **Universal Audio Codec Support**: Native cross-platform **FFmpeg** integration streaming 32-bit floating-point audio data for WAV, AIFF, FLAC, ALAC, MP3, AAC, DSD (DSF/DFF), OGG, and Opus.
* **DAW Plugin TCP Bridge**: Built-in asynchronous socket server on port `8989` providing 1:1 drop-in binary compatibility with DAW streaming plugins.
* **Multi-Format Export**: Export analysis results to CSV spreadsheets, structured JSON metadata, or formatted text reports.

---

## Solution Architecture

```
MusicScope.NET/
├── src/
│   ├── MusicScope.Core/        # DSP, FFT, ITU-R BS.1770, True Peak, Measurement Engines
│   ├── MusicScope.Audio/       # Cross-platform FFmpeg streaming audio decoder
│   ├── MusicScope.Network/     # DAW TCP Socket Bridge (Port 8989)
│   ├── MusicScope.Reporting/   # CSV, JSON, and formatted TXT report exporters
│   └── MusicScope.Desktop/     # AvaloniaUI 12.x desktop app (Windows, macOS, Linux)
├── tests/
│   └── MusicScope.Core.Tests/  # xUnit tests verifying DSP parity against BS.1770
├── OriginalJavaApp/            # Original legacy distribution package
│   └── OriginalJavaApp.zip     # Full legacy XiVero MusicScope distribution archive
└── tools/                      # Decompilation archives, scripts, and references
    ├── src-decompiled.zip      # Complete categorized decompiled Java reference files
    └── organize_and_deobfuscate.py # Symbol mapper and bytecode organizer script
```

---

## Getting Started & Contributing

### 1. Unpacking Reference Archives
Before continuing development, inspecting original algorithms, or contributing to this project, extract the legacy reference archives:

```bash
# 1. Extract original Java application into OriginalJavaApp/
tar -xf OriginalJavaApp/OriginalJavaApp.zip -C OriginalJavaApp/
# or PowerShell:
# Expand-Archive OriginalJavaApp/OriginalJavaApp.zip -DestinationPath OriginalJavaApp/

# 2. Extract decompiled Java source references into tools/src-decompiled/
tar -xf tools/src-decompiled.zip -C tools/
# or PowerShell:
# Expand-Archive tools/src-decompiled.zip -DestinationPath tools/
```

> **Note**: Unzipped files (`OriginalJavaApp/jre/`, `OriginalJavaApp/lib/`, `OriginalJavaApp/*.exe`, and `tools/src-decompiled/`) are strictly isolated by `.gitignore` and **must not be committed** to the repository.

---

## Building & Running

### Prerequisites
* [.NET 10 SDK](https://dotnet.microsoft.com/download)
* [FFmpeg](https://ffmpeg.org/) (installed in `PATH` or bundled)

### Build Solution
```bash
dotnet build MusicScope.slnx
```

### Run Unit Tests
```bash
dotnet test tests/MusicScope.Core.Tests/MusicScope.Core.Tests.csproj
```

### Run Desktop Application
```bash
dotnet run --project src/MusicScope.Desktop/MusicScope.Desktop.csproj
```

### Publish Cross-Platform Standalone Builds
```bash
# Windows x64
dotnet publish src/MusicScope.Desktop -r win-x64 -c Release -o publish/win-x64

# macOS Apple Silicon (M1/M2/M3/M4)
dotnet publish src/MusicScope.Desktop -r osx-arm64 -c Release -o publish/osx-arm64

# Linux x64
dotnet publish src/MusicScope.Desktop -r linux-x64 -c Release -o publish/linux-x64
```

---

## License
MIT License.
