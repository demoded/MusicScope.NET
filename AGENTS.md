# AGENTS.md: Developer & AI Agent Guidelines for MusicScope.NET

Welcome to **MusicScope.NET**. This guide provides architecture context, domain concepts, coding conventions, and developer workflows for AI coding assistants and contributors working on this codebase.

---

## 1. Project Overview

* **Repository**: [MusicScope.NET](https://github.com/demoded/MusicScope.NET)
* **Goal**: Modern, high-precision, open-source cross-platform rewrite of the legendary **XiVero MusicScope v2.1.0** audio analyzer and acoustic measurement suite.
* **Target Platforms**: Windows (`win-x64`, `win-arm64`), macOS (`osx-arm64`, `osx-x64`), Linux (`linux-x64`, `linux-arm64`).
* **Technology Stack**:
  * **Runtime**: .NET 10 (`net10.0`), C# 13, Native AOT-ready.
  * **UI Framework**: AvaloniaUI 12.x (XAML + MVVM via `CommunityToolkit.Mvvm`).
  * **Audio Pipeline**: Cross-platform FFmpeg streaming audio decoder (`FfmpegAudioDecoder`).
  * **Network Bridge**: Asynchronous TCP socket server on port `8989` (`DawSocketServer`) for DAW plugin compatibility.
  * **Testing**: xUnit with real audio test vectors.

---

## 2. Solution Architecture

```
MusicScope.NET/
├── MusicScope.slnx              # Solution file (modern XML solution format)
├── src/
│   ├── MusicScope.Core/         # Pure DSP, FFT, ITU-R BS.1770-4 loudness, True Peak, HW analyzers
│   │   ├── DSP/                 # FFT (Cooley-Tukey), Polyphase FIR, K-Weighting IIR, Window Functions
│   │   ├── Loudness/            # EBU R128 / ITU-R BS.1770-4 meter, gating, LRA, S-Mode histogram
│   │   ├── Levels/              # True Peak oversampling meter, CREST factor, RMS, Peak-to-Loudness Ratio (PLR)
│   │   ├── Stereo/              # Phase correlation [-1.0, +1.0], Mid/Side extraction, Goniometer density
│   │   ├── Hardware/            # THD (1 kHz notch), Jitter (11.025 kHz J-Test), Turntable RPM & Wow/Flutter
│   │   ├── AudioAnalysisEngine.cs # Multi-threaded coordinator processing audio blocks
│   │   └── AudioRealtimeSnapshot.cs # Thread-safe UI update state record
│   ├── MusicScope.Audio/        # Universal FFmpeg streaming audio decoder (WAV, FLAC, DSD/DSF/DFF, ALAC, MP3, etc.)
│   ├── MusicScope.Network/      # TCP Socket Server (Port 8989) streaming 32-bit float audio from DAWs
│   ├── MusicScope.Reporting/    # Export reports: CSV, JSON, and formatted TXT
│   └── MusicScope.Desktop/      # AvaloniaUI MVVM desktop application
│       ├── Controls/            # Custom direct-rendered vector controls (60 FPS Skia canvas)
│       │   ├── SModeMeterControl.cs    # Box 3: S-Mode loudness, L/R peak bars, PLR, LU meter, histogram, LRA
│       │   ├── LevelsBoxControl.cs     # Box 2: Numerical readouts (TPL, RMS, CREST, PLR, M, S, I, LRA)
│       │   ├── FormatBoxControl.cs     # Box 1: Format matrix, sample rate, bit depth, DSD flags
│       │   ├── HistoryDialControl.cs   # Box 4: Polar circular history radar dial
│       │   ├── GoniometerControl.cs    # Box 5: Stereo vector scope and phase correlation bar
│       │   ├── SpectrumGraphControl.cs # Linear/Log FFT frequency spectrum
│       │   └── WaterfallControl.cs     # 2D Spectrogram / Waterfall heatmap
│       ├── ViewModels/          # CommunityToolkit MVVM ViewModels (MainViewModel)
│       └── Views/               # MainWindow.axaml and view code-behinds
├── tests/
│   └── MusicScope.Core.Tests/   # xUnit tests verifying DSP accuracy against original standards
├── OriginalJavaApp/             # Original legacy distribution package
│   └── OriginalJavaApp.zip      # Full legacy XiVero MusicScope distribution archive
└── tools/                       # Decompilation archives, scripts, and references
    ├── src-decompiled.zip       # Complete categorized decompiled Java reference files
    └── organize_and_deobfuscate.py # Symbol mapper and bytecode organizer script
```

---

## 3. Essential Developer Commands

All commands should be executed from the repository root:

### Unpack Reference Archives (Required Before Development)
Before continuing development, reverse engineering, or verifying DSP/UI parity against the original application, extract the legacy reference archives:
```pwsh
# 1. Unpack original Java distribution into OriginalJavaApp/
Expand-Archive OriginalJavaApp/OriginalJavaApp.zip -DestinationPath OriginalJavaApp/

# 2. Unpack decompiled Java references into tools/src-decompiled/
Expand-Archive tools/src-decompiled.zip -DestinationPath tools/
```
> [!IMPORTANT]
> **Do not commit unzipped files!**
> The extracted contents (`OriginalJavaApp/jre/`, `OriginalJavaApp/lib/`, `OriginalJavaApp/*.exe`, and `tools/src-decompiled/`) are strictly isolated and ignored by `.gitignore` to keep the git history clean and compact. Only `.zip` files should be tracked.

### Build Solution
```pwsh
dotnet build MusicScope.slnx
```

### Run Unit Tests
```pwsh
dotnet test --no-build MusicScope.slnx
```

### Run Desktop Application
```pwsh
dotnet run --project src/MusicScope.Desktop/MusicScope.Desktop.csproj
```

### Publish Self-Contained Builds (Single Platform)
```pwsh
# Windows (x64 / arm64)
dotnet publish src/MusicScope.Desktop -r win-x64 -c Release -o publish/win-x64
dotnet publish src/MusicScope.Desktop -r win-arm64 -c Release -o publish/win-arm64

# macOS (Apple Silicon / Intel)
dotnet publish src/MusicScope.Desktop -r osx-arm64 -c Release -o publish/osx-arm64
dotnet publish src/MusicScope.Desktop -r osx-x64 -c Release -o publish/osx-x64

# Linux (x64 / arm64)
dotnet publish src/MusicScope.Desktop -r linux-x64 -c Release -o publish/linux-x64
dotnet publish src/MusicScope.Desktop -r linux-arm64 -c Release -o publish/linux-arm64
```

### Creating Cross-Platform GitHub Releases

To build, package, hash, and publish a full multi-platform release for all 6 supported architectures (`win-x64`, `win-arm64`, `osx-x64`, `osx-arm64`, `linux-x64`, `linux-arm64`):

#### 1. Publish All Target Platforms
```pwsh
$RIDs = @("win-x64", "win-arm64", "osx-x64", "osx-arm64", "linux-x64", "linux-arm64")
foreach ($rid in $RIDs) {
    dotnet publish src/MusicScope.Desktop -r $rid -c Release -p:PublishSingleFile=true --self-contained true -o "dist/$rid"
}
```

#### 2. Clean Debugging Symbols (PDBs)
Strip bulky native and managed symbol files (`libSkiaSharp.pdb`, etc.) from distribution folders to save ~100 MB per archive:
```pwsh
Remove-Item dist/*/*.pdb -Force
```

#### 3. Package Distribution Archives
```pwsh
New-Item -ItemType Directory -Force -Path dist/release
$Version = "v1.0.0"

# Windows (ZIP)
Compress-Archive -Path dist/win-x64/* -DestinationPath "dist/release/MusicScope.NET-$Version-win-x64.zip" -Force
Compress-Archive -Path dist/win-arm64/* -DestinationPath "dist/release/MusicScope.NET-$Version-win-arm64.zip" -Force

# macOS (ZIP)
Compress-Archive -Path dist/osx-x64/* -DestinationPath "dist/release/MusicScope.NET-$Version-osx-x64.zip" -Force
Compress-Archive -Path dist/osx-arm64/* -DestinationPath "dist/release/MusicScope.NET-$Version-osx-arm64.zip" -Force

# Linux (TAR.GZ to preserve POSIX file execution permissions)
tar -czf "dist/release/MusicScope.NET-$Version-linux-x64.tar.gz" -C dist/linux-x64 .
tar -czf "dist/release/MusicScope.NET-$Version-linux-arm64.tar.gz" -C dist/linux-arm64 .
```

#### 4. Generate SHA-256 Checksums
```pwsh
Get-ChildItem -Path dist/release -Include *.zip,*.tar.gz | 
    Get-FileHash -Algorithm SHA256 | 
    ForEach-Object { "$($_.Hash.ToLower())  $(Split-Path $_.Path -Leaf)" } | 
    Set-Content -Encoding utf8 dist/release/SHA256SUMS.txt
```

#### 5. Tag and Publish GitHub Release
```pwsh
# 1. Create and push git tag
git tag -a $Version -m "Release $Version - Cross-Platform Release"
git push origin $Version

# 2. Publish release with assets via GitHub CLI
gh release create $Version `
    dist/release/MusicScope.NET-$Version-win-x64.zip `
    dist/release/MusicScope.NET-$Version-win-arm64.zip `
    dist/release/MusicScope.NET-$Version-osx-x64.zip `
    dist/release/MusicScope.NET-$Version-osx-arm64.zip `
    dist/release/MusicScope.NET-$Version-linux-x64.tar.gz `
    dist/release/MusicScope.NET-$Version-linux-arm64.tar.gz `
    dist/release/SHA256SUMS.txt `
    --title "$Version - Cross-Platform Release" `
    --notes-file dist/release/RELEASE_NOTES.md
```

---

## 4. Architectural Rules & Domain Principles

When modifying or expanding the codebase, always follow these core principles:

### 1. Parity with the Reference Application
* The original Java implementation is available in `tools/src-decompiled.zip`.
* When implementing or fixing UI controls or DSP algorithms, verify against the decompiled source (e.g., `LevelMeterControl.java`, `LoudnessModule.java`, `LevelsModule.java`, `CircleControl.java`).
* Preserve exact mathematical scale transformations, such as the exponential-logarithmic scale mapping:
  $$\text{norm} = \frac{10^{(dB + 60) / 90} - 1}{10^{63 / 90} - 1}$$

### 2. Distinction Between Live Metering and Summary Reports
* **Instantaneous Values** (`InstantPeakLeft/Right`, `InstantRmsLeft/Right`, `MomentaryCurrent`, `ShortTermCurrent`, `InstantPlr`):
  * Active and bouncing during playback/decoding.
  * Reset to $-60\text{ dBFS}$ (or $0.0$ for PLR) when playback/analysis stops or completes.
* **Peak-Hold and Integrated Summary Values** (`TruePeakLeft/Right`, `RmsLeft/Right`, `MomentaryMax`, `ShortTermMax`, `IntegratedLoudness`, `LoudnessRange`):
  * Track cumulative statistics across the file.
  * Retained and displayed when playback/analysis finishes (peak hold tick marks, white integrated line, etc.).

### 3. UI Color Palette & Visual Integrity
* Backgrounds are pitch black (`#000000`) or deep carbon (`#0A0D10`).
* **S-Mode / History**: Amber/Orange (`#E89A20`).
* **LU (Momentary Loudness)**: Deep Cyan-Blue (`#006F96`).
* **PLR (Peak-to-Loudness Ratio)**: Bright Cyan (`#55DDFF`), drawn vertically between L and R from $0\text{ dB}$ down to $-PLR\text{ dB}$.
* **RMS Indicators**: Bright Lime Green (`#00FF00`).
* **Peak Hold & True Peak**: Mint Green (`#00E86F`), turns Red (`#DD0000` or `#EE0000`) on clip ($\ge 0.05\text{ dB}$).
* **Decaying Peak Hold**: Dark Green (`#00BB00`).

### 4. Interactive Control Behaviors
* Custom Avalonia controls override `OnPointerPressed` for user interactions matching original XiVero shortcuts:
  * In `SModeMeterControl`: clicking the meter area ($x < 100$) toggles **Stereo (L/R)** vs **Mid/Side (M/S)** mode; clicking the distribution area ($x \ge 100$) cycles **S-Mode**, **M-Mode**, and **TPL**.

### 5. Cross-Platform Compatibility
* Never rely on platform-specific Windows APIs (GDI+, Win32, DirectX). All rendering must go through Avalonia's cross-platform vector `DrawingContext`.
* Audio decoding relies on standard FFmpeg CLI / libraries available across Windows, macOS, and Linux.

---

## 5. Coding & Style Conventions

* Use **C# 13** modern features (file-scoped namespaces, pattern matching, collection expressions `[...]`, primary constructors where appropriate).
* Preserve existing XML documentation comments and docstrings.
* Keep DSP inner loops allocation-free and optimized with `Span<T>` and `ReadOnlySpan<T>`.
* In Avalonia controls, always register dependency properties with `AffectsRender<TControl>(...)` to ensure smooth 60 FPS repaints.
