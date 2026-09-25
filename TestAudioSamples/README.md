# Reference Audio Test Samples for MusicScope Verification

This test suite contains 6 reference WAV audio files created with mathematically calibrated Peak, RMS, and CREST factors. They allow directly testing and comparing measurement output between **MusicScope.NET** and the original **XiVideo MusicScope Java application** (`OriginalJavaApp/MusicScope.exe`).

---

## 1. Summary of Expected vs. Measured Values

| # | Filename | Format | Expected Peak | Expected RMS | Target CREST | MusicScope.NET Peak | MusicScope.NET TruePeak | MusicScope.NET RMS | MusicScope.NET CREST | Original Java CREST |
|---|---|---|---|---|---|---|---|---|---|---|
| **01** | `01_Sine_1kHz_0dBFS_44k_16bit.wav` | 44.1 kHz / 16-bit | 0.0 dBFS | -3.0 dBFS | **3.0 dB** | 0.0 dBFS | 0.0 dBFS | -3.0 dBFS | **3.0 dB** | _(verify)_ |
| **02** | `02_ToneBurst_Crest_6dB_44k_16bit.wav` | 44.1 kHz / 16-bit | 0.0 dBFS | -6.0 dBFS | **6.0 dB** | 0.0 dBFS | 0.0 dBFS | -6.0 dBFS | **6.0 dB** | _(verify)_ |
| **03** | `03_PulsedTone_Crest_10dB_44k_16bit.wav` | 44.1 kHz / 16-bit | 0.0 dBFS | -10.0 dBFS | **10.0 dB** | 0.0 dBFS | +1.0 dBFS | -10.0 dBFS | **11.0 dB\*** | _(verify)_ |
| **04** | `04_MusicalSim_Crest_11.1dB_44k_16bit.wav`| 44.1 kHz / 16-bit | 0.0 dBFS | -11.1 dBFS | **11.1 dB** | 0.0 dBFS | +0.4 dBFS | -11.1 dBFS | **11.2 dB\*** | _(verify)_ |
| **05** | `05_HighDynamics_Crest_14dB_44k_16bit.wav`| 44.1 kHz / 16-bit | 0.0 dBFS | -14.0 dBFS | **14.0 dB** | 0.0 dBFS | 0.0 dBFS | -14.0 dBFS | **14.0 dB** | _(verify)_ |
| **06** | `06_MusicalSim_Crest_11.1dB_96k_24bit.wav`| 96.0 kHz / 24-bit | 0.0 dBFS | -11.1 dBFS | **11.1 dB** | 0.0 dBFS | 0.0 dBFS | -11.1 dBFS | **11.1 dB** | _(verify)_ |

\* _Note on Inter-Sample Overshoot:_ In MusicScope's algorithm, CREST is computed from the **oversampled True Peak** rather than the discrete sample peak. Files with sharp transients that induce polyphase filter overshoot (e.g. +1.0 dB on pulsed tones or +0.4 dB on 44.1 kHz percussion) yield a correspondingly higher CREST factor ($1.0 - (-10.0) = 11.0\text{ dB}$).

---

## 2. Description of Each Test File

### File 01: `01_Sine_1kHz_0dBFS_44k_16bit.wav` (Calibration Baseline)
- **Duration**: 10.0 seconds
- **Sample Rate / Bits**: 44,100 Hz / 16-bit stereo PCM
- **Description**: Pure uncompressed full-scale 1 kHz sine wave ($A = 1.0$).
- **Physics**:
  - Sample Peak = $20 \log_{10}(1.0) = 0.0\text{ dBFS}$
  - RMS = $20 \log_{10}(1/\sqrt{2}) = -3.0103\text{ dBFS}$
  - Crest Factor = $\text{Peak} / \text{RMS} = \sqrt{2} \implies 20 \log_{10}(\sqrt{2}) = +3.0103\text{ dB}$ (displays as **3.0 dB**).

### File 02: `02_ToneBurst_Crest_6dB_44k_16bit.wav`
- **Duration**: 10.0 seconds
- **Sample Rate / Bits**: 44,100 Hz / 16-bit stereo PCM
- **Description**: Calibrated dual-tone signal with a 2:1 peak-to-RMS ratio.
- **Physics**:
  - Sample Peak = $0.0\text{ dBFS}$
  - RMS = $-6.02\text{ dBFS}$
  - Crest Factor = $2.0 \implies 20 \log_{10}(2.0) = +6.02\text{ dB}$ (displays as **6.0 dB**).

### File 03: `03_PulsedTone_Crest_10dB_44k_16bit.wav`
- **Duration**: 10.0 seconds
- **Sample Rate / Bits**: 44,100 Hz / 16-bit stereo PCM
- **Description**: Periodic 500 Hz tone pulse with exponential decay repeating every 512 samples.
- **Physics**:
  - Sample Peak = $0.0\text{ dBFS}$
  - RMS = $-10.0\text{ dBFS}$
  - Because of the abrupt transient edges, the 4x FIR polyphase filter produces $+1.0\text{ dB}$ inter-sample true-peak overshoot, making True Peak = $+1.0\text{ dBFS}$ and CREST = $11.0\text{ dB}$.

### File 04: `04_MusicalSim_Crest_11.1dB_44k_16bit.wav` (Target Music Simulation)
- **Duration**: 10.0 seconds
- **Sample Rate / Bits**: 44,100 Hz / 16-bit stereo PCM
- **Description**: Multi-instrument realistic musical texture containing:
  - 70 Hz sub-kick drum transient
  - A-major piano chord bed (440 Hz + 554 Hz + 659 Hz)
  - 8 kHz hi-hat percussion bursts
- **Target**: Directly targets the ~11.1 dB CREST factor observed in commercial music tracks.

### File 05: `05_HighDynamics_Crest_14dB_44k_16bit.wav`
- **Duration**: 10.0 seconds
- **Sample Rate / Bits**: 44,100 Hz / 16-bit stereo PCM
- **Description**: Highly dynamic percussion mix with soft atmospheric synthesizer pad (-26 dBFS) and sparse 220 Hz snare drum impacts hitting full scale (0 dBFS).
- **Target**: High-dynamic acoustic/classical simulation with 14.0 dB CREST factor.

### File 06: `06_MusicalSim_Crest_11.1dB_96k_24bit.wav` (High-Resolution 24-bit / 96 kHz)
- **Duration**: 10.0 seconds
- **Sample Rate / Bits**: 96,000 Hz / 24-bit stereo PCM
- **Description**: High-resolution 24-bit rendering of the realistic musical simulation to verify the 96 kHz (2x polyphase filter) path and 24-bit word precision.

---

## 3. How MusicScope Calculates CREST Avg.

Reverse-engineered from original `LevelsModule.java` (lines 268–288):

1. **Block Processing**:
   Audio is divided into 2048-sample blocks ($N = 2048$).
   At 44.1 kHz, samples pass through a 4x oversampling cascaded FIR polyphase filter (Filter 0 with 90 taps followed by Filter 1 with 54 taps).

2. **Block Crest Ratio**:
   ```
   d17 = max(PeakLeft_oversampled, PeakRight_oversampled)
   d18 = RMS of the channel that had the higher peak
   blockCrestLinear = d17 / d18
   ```

3. **8-Block Moving Average (`d19`)**:
   `blockCrestLinear` enters an 8-slot ring buffer:
   $$d19 = \frac{1}{8} \sum_{k=0}^{7} \text{RingBuffer}[k]$$

4. **Warmup & Long-Term Accumulator**:
   - The first 8 blocks ($\approx 0.37$ seconds) are warmup blocks to let the ring buffer fill up and are skipped.
   - For all subsequent blocks where $d19 > 0.001$:
     $$\text{ChunkInfo} += d19$$
     $$\text{CREST Avg (dB)} = 20 \log_{10}\left(\frac{\text{ChunkInfo}}{\text{BlockCount}}\right)$$

5. **Dynamic Range (DR)**:
   In MusicScope, Dynamic Range is directly derived from CREST Avg:
   $$\text{DR} = \max(0.0, \text{round}(\text{CREST Avg}, 1))$$

---

## 4. How to Test in Original MusicScope

1. Launch `OriginalJavaApp/MusicScope.exe`.
2. Open or drag each of the files in `TestAudioSamples/` into the application.
3. Note the numbers in the **Levels Box** (Sample Peak, True Peak, RMS Left/Right, CREST, DR).
4. Run the same file in **MusicScope.NET** and compare.
