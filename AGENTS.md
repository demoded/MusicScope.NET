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

Use PowerShell 7 (`pwsh`) and the packaging helper from the repository root. It publishes Release, self-contained, single-file builds with the original application icon and platform launcher metadata:

The helper enables `PublishSingleFile` and `IncludeNativeLibrariesForSelfExtract`, bundles the .NET runtime and native Avalonia libraries, embeds managed debugging symbols, and removes standalone PDB files. Native libraries extract automatically at startup. FFmpeg remains an external dependency and must be available in `PATH` or beside the executable for audio file decoding.

```pwsh
# Windows (x64 / arm64)
./tools/publish-desktop.ps1 -RuntimeIdentifier win-x64
./tools/publish-desktop.ps1 -RuntimeIdentifier win-arm64

# macOS (Apple Silicon / Intel)
./tools/publish-desktop.ps1 -RuntimeIdentifier osx-arm64
./tools/publish-desktop.ps1 -RuntimeIdentifier osx-x64

# Linux (x64 / arm64)
./tools/publish-desktop.ps1 -RuntimeIdentifier linux-x64
./tools/publish-desktop.ps1 -RuntimeIdentifier linux-arm64
```

Output defaults to `dist/<RuntimeIdentifier>/`. Use `-OutputDirectory` to override it and `-Version 1.0.0` to set the assembly and macOS bundle version; the version must have three numeric components without a `v` prefix. Use fresh output folders for each release to avoid packaging stale files.

* **Windows**: output contains one `MusicScope.NET.exe`, which embeds `Assets/MusicScope.ico`; the main window uses the same icon.
* **macOS**: output contains one `MusicScope.NET` executable in `MusicScope.NET.app/Contents/MacOS/`, plus `Contents/Resources/MusicScope.icns` and `Contents/Info.plist`. Install the `.app` in Applications and launch the bundle for the Dock icon and application metadata.
* **Linux**: output includes one `MusicScope.NET` executable plus PNG icons, `MusicScope.NET.desktop`, and `install-desktop-entry.sh`. Extract to a permanent location, then run `sh ./install-desktop-entry.sh` from that directory to install the launcher and icons for the current user. Run it again after moving the application.

Create macOS and Linux archives on Unix to preserve executable permissions. When cross-publishing from Windows, set the executable permission on Unix before packaging (see below). A successful cross-publish does not verify launch behavior or taskbar/Dock icons; check those on each target OS.

### Regenerate the Original Application Icon

The checked-in icon assets are generated from the original `OriginalJavaApp/MusicScope.exe`. Normal builds and publishes do not require Python. To regenerate the assets after extracting the original distribution:

```pwsh
python -m pip install Pillow
python tools/extract_application_icon.py
```

The extractor preserves the native 16, 32, 48, 64, 128, and 256 pixel Windows icon resources and creates the PNG and ICNS variants in `src/MusicScope.Desktop/Assets/`. Only the 512 and 1024 pixel Retina variants are upscaled. Commit these generated application assets; keep the extracted legacy distribution ignored.

### Creating Cross-Platform GitHub Releases

To build, package, hash, and publish a full multi-platform release for all 6 supported architectures (`win-x64`, `win-arm64`, `osx-x64`, `osx-arm64`, `linux-x64`, `linux-arm64`):

#### 1. Publish All Target Platforms

```pwsh
$ReleaseVersion = "1.0.0"
$Version = "v$ReleaseVersion"
$RIDs = @("win-x64", "win-arm64", "osx-x64", "osx-arm64", "linux-x64", "linux-arm64")
foreach ($rid in $RIDs) {
    ./tools/publish-desktop.ps1 -RuntimeIdentifier $rid -Version $ReleaseVersion
}
```

#### 2. Verify Single-File Output

The helper removes standalone PDBs. Verify that no loose managed/native libraries or debugging symbols remain in the six distribution folders, including the nested macOS bundles:

```pwsh
foreach ($rid in $RIDs) {
    $looseFiles = Get-ChildItem -LiteralPath "dist/$rid" -File -Recurse |
        Where-Object { $_.Name -match '\.(dll|dylib|so(\..*)?|pdb)$' }
    if ($looseFiles) { throw "Unexpected libraries or symbols in dist/$rid; publish to a fresh folder." }
}
```

#### 3. Package Distribution Archives

Run each platform's packaging commands from the repository root on the indicated host. If using a separate shell or machine, set `$Version` to the same release tag (for example, `v1.0.0`) and copy the published folders there first.

Windows, using PowerShell 7:

```pwsh
New-Item -ItemType Directory -Force -Path dist/release | Out-Null

Compress-Archive -Path dist/win-x64/* -DestinationPath "dist/release/MusicScope.NET-$Version-win-x64.zip" -Force
Compress-Archive -Path dist/win-arm64/* -DestinationPath "dist/release/MusicScope.NET-$Version-win-arm64.zip" -Force
```

macOS, using PowerShell 7 and `ditto` to preserve the `.app` bundle and executable permissions:

```pwsh
New-Item -ItemType Directory -Force -Path dist/release | Out-Null

chmod +x dist/osx-x64/MusicScope.NET.app/Contents/MacOS/MusicScope.NET
chmod +x dist/osx-arm64/MusicScope.NET.app/Contents/MacOS/MusicScope.NET
ditto -c -k --sequesterRsrc --keepParent dist/osx-x64/MusicScope.NET.app "dist/release/MusicScope.NET-$Version-osx-x64.zip"
ditto -c -k --sequesterRsrc --keepParent dist/osx-arm64/MusicScope.NET.app "dist/release/MusicScope.NET-$Version-osx-arm64.zip"
```

Linux, using PowerShell 7 and `tar` to preserve executable permissions:

```pwsh
New-Item -ItemType Directory -Force -Path dist/release | Out-Null

chmod +x dist/linux-x64/MusicScope.NET
chmod +x dist/linux-arm64/MusicScope.NET
tar -czf "dist/release/MusicScope.NET-$Version-linux-x64.tar.gz" -C dist/linux-x64 .
tar -czf "dist/release/MusicScope.NET-$Version-linux-arm64.tar.gz" -C dist/linux-arm64 .
```

#### 4. Generate SHA-256 Checksums

Collect all six archives in `dist/release/` before hashing:

```pwsh
Get-ChildItem -LiteralPath dist/release -File |
    Where-Object { $_.Name -like "MusicScope.NET-$Version-*.zip" -or $_.Name -like "MusicScope.NET-$Version-*.tar.gz" } |
    Sort-Object Name |
    Get-FileHash -Algorithm SHA256 |
    ForEach-Object { "$($_.Hash.ToLowerInvariant())  $(Split-Path $_.Path -Leaf)" } |
    Set-Content -Encoding utf8NoBOM dist/release/SHA256SUMS.txt
```

#### 5. Tag and Publish GitHub Release

Write `dist/release/RELEASE_NOTES.md` and verify the packaged applications on their target platforms before publishing. Tag the commit containing the release changes:

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
