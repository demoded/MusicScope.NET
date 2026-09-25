#!/usr/bin/env python3
"""
Test WAV generator for MusicScope Audio Analysis Verification.
Generates reference WAV files with mathematically exact Peak, RMS, and CREST factors.
"""

import math
import os
import struct
import wave

OUTPUT_DIR = os.path.join(os.path.dirname(__file__), "..", "TestAudioSamples")
os.makedirs(OUTPUT_DIR, exist_ok=True)


def write_wav_16bit(filename, sample_rate, channels, samples_left, samples_right):
    filepath = os.path.join(OUTPUT_DIR, filename)
    num_frames = len(samples_left)
    with wave.open(filepath, "wb") as wf:
        wf.setnchannels(channels)
        wf.setsampwidth(2)  # 16-bit
        wf.setframerate(sample_rate)
        
        raw_data = bytearray(num_frames * channels * 2)
        idx = 0
        for i in range(num_frames):
            sL = max(-32768, min(32767, int(samples_left[i] * 32767.0)))
            struct.pack_into("<h", raw_data, idx, sL)
            idx += 2
            if channels == 2:
                sR = max(-32768, min(32767, int(samples_right[i] * 32767.0)))
                struct.pack_into("<h", raw_data, idx, sR)
                idx += 2
        wf.writeframes(raw_data)
    print(f"Created: {filename} ({num_frames / sample_rate:.1f}s, {sample_rate}Hz, 16-bit)")


def write_wav_24bit(filename, sample_rate, channels, samples_left, samples_right):
    filepath = os.path.join(OUTPUT_DIR, filename)
    num_frames = len(samples_left)
    
    # Custom 24-bit PCM WAV writer
    bytes_per_sample = 3
    block_align = channels * bytes_per_sample
    byte_rate = sample_rate * block_align
    data_size = num_frames * block_align
    
    with open(filepath, "wb") as f:
        # RIFF Header
        f.write(b"RIFF")
        f.write(struct.pack("<I", 36 + data_size))
        f.write(b"WAVE")
        
        # fmt chunk
        f.write(b"fmt ")
        f.write(struct.pack("<I", 16))          # chunk size
        f.write(struct.pack("<H", 1))           # PCM format
        f.write(struct.pack("<H", channels))   # channels
        f.write(struct.pack("<I", sample_rate))# sample rate
        f.write(struct.pack("<I", byte_rate))  # byte rate
        f.write(struct.pack("<H", block_align))# block align
        f.write(struct.pack("<H", 24))          # bits per sample
        
        # data chunk
        f.write(b"data")
        f.write(struct.pack("<I", data_size))
        
        max_int24 = 8388607
        min_int24 = -8388608
        
        for i in range(num_frames):
            sL = max(min_int24, min(max_int24, int(samples_left[i] * 8388607.0)))
            # 24-bit little endian signed integer
            f.write(struct.pack("<i", sL)[:3])
            if channels == 2:
                sR = max(min_int24, min(max_int24, int(samples_right[i] * 8388607.0)))
                f.write(struct.pack("<i", sR)[:3])
                
    print(f"Created: {filename} ({num_frames / sample_rate:.1f}s, {sample_rate}Hz, 24-bit)")


def generate_all():
    duration = 10.0  # 10 seconds (plenty of blocks for full warmup & convergence)
    sr_44k = 44100
    n_44k = int(duration * sr_44k)

    # -------------------------------------------------------------
    # 1. 01_Sine_1kHz_0dBFS_44k_16bit.wav
    # Expected: Peak = 0.0 dBFS, RMS = -3.0 dBFS, CREST = 3.0 dB
    # -------------------------------------------------------------
    s_l = [math.sin(2.0 * math.pi * 1000.0 * t / sr_44k) for t in range(n_44k)]
    s_r = list(s_l)
    write_wav_16bit("01_Sine_1kHz_0dBFS_44k_16bit.wav", sr_44k, 2, s_l, s_r)

    # -------------------------------------------------------------
    # 2. 02_ToneBurst_Crest_6dB_44k_16bit.wav
    # Expected: Peak = 0.0 dBFS, RMS = -6.0 dBFS, CREST = 6.0 dB
    # Crest ratio: 10^(6.0/20) = 1.9953 (approx 2.0)
    # -------------------------------------------------------------
    target_crest_6db = 10.0 ** (6.0 / 20.0)  # ~1.9953
    target_rms_6db = 1.0 / target_crest_6db   # ~0.5012
    # Sine burst with duty cycle to match target RMS exactly
    block_size = 2048
    s_l = []
    for block_idx in range(n_44k // block_size):
        block = []
        for i in range(block_size):
            t = block_idx * block_size + i
            # Combination of sine harmonic: peak is 1.0
            # Phase-crafted 2-tone wave: sin(x) + a * sin(3x)
            val = math.sin(2.0 * math.pi * 440.0 * t / sr_44k)
            block.append(val)
        # Normalize block to peak 1.0 and RMS target_rms_6db
        peak = max(abs(x) for x in block)
        rms = math.sqrt(sum(x * x for x in block) / len(block))
        # Scale to peak 1.0 first
        block = [x / peak for x in block]
        curr_rms = math.sqrt(sum(x * x for x in block) / len(block))
        # Now compress/expand dynamics smoothly to hit exact target RMS while keeping peak 1.0:
        # y = sign(x) * |x|^p
        # where p controls the RMS relative to peak
        # Solve for p using bisection
        p_low, p_high = 0.1, 10.0
        for _ in range(40):
            p_mid = (p_low + p_high) / 2.0
            shaped = [math.copysign(abs(x) ** p_mid, x) for x in block]
            r = math.sqrt(sum(x * x for x in shaped) / len(shaped))
            if r < target_rms_6db:
                p_high = p_mid
            else:
                p_low = p_mid
        block = [math.copysign(abs(x) ** p_mid, x) for x in block]
        s_l.extend(block)
    write_wav_16bit("02_ToneBurst_Crest_6dB_44k_16bit.wav", sr_44k, 2, s_l, s_l)

    # -------------------------------------------------------------
    # 3. 03_PulsedTone_Crest_10dB_44k_16bit.wav
    # Expected: Peak = 0.0 dBFS, RMS = -10.0 dBFS, CREST = 10.0 dB
    # Target Crest ratio = 10^(10.0/20) = 3.1623
    # -------------------------------------------------------------
    target_crest_10db = 10.0 ** (10.0 / 20.0)  # ~3.1623
    target_rms_10db = 1.0 / target_crest_10db   # ~0.31623
    s_l = []
    for block_idx in range(n_44k // block_size):
        block = []
        for i in range(block_size):
            t = block_idx * block_size + i
            # Waveform with sharp transients and harmonic tail
            pulse = math.sin(2.0 * math.pi * 500.0 * t / sr_44k) * math.exp(-((i % 512) / 60.0))
            block.append(pulse)
        peak = max(abs(x) for x in block)
        block = [x / peak for x in block]
        p_low, p_high = 0.1, 20.0
        for _ in range(40):
            p_mid = (p_low + p_high) / 2.0
            shaped = [math.copysign(abs(x) ** p_mid, x) for x in block]
            r = math.sqrt(sum(x * x for x in shaped) / len(shaped))
            if r < target_rms_10db:
                p_high = p_mid
            else:
                p_low = p_mid
        block = [math.copysign(abs(x) ** p_mid, x) for x in block]
        s_l.extend(block)
    write_wav_16bit("03_PulsedTone_Crest_10dB_44k_16bit.wav", sr_44k, 2, s_l, s_l)

    # -------------------------------------------------------------
    # 4. 04_MusicalSim_Crest_11.1dB_44k_16bit.wav
    # Direct test of the 11.1 dB CREST reported by user!
    # Expected: Peak = 0.0 dBFS, RMS = -11.1 dBFS, CREST = 11.1 dB
    # Target Crest ratio = 10^(11.1/20) = 3.5892
    # -------------------------------------------------------------
    target_crest_11_1db = 10.0 ** (11.1 / 20.0)  # ~3.5892
    target_rms_11_1db = 1.0 / target_crest_11_1db # ~0.2786
    s_l = []
    s_r = []
    for block_idx in range(n_44k // block_size):
        block_l = []
        block_r = []
        for i in range(block_size):
            t = block_idx * block_size + i
            # Multi-layer musical simulation:
            # 1. Bass kick drum every 512 samples (~86 Hz repetition)
            kick = math.sin(2.0 * math.pi * 70.0 * (i % 512) / sr_44k) * math.exp(-((i % 512) / 80.0))
            # 2. Chord (A4 440 Hz + C#5 554 Hz + E5 659 Hz)
            chord = 0.3 * (
                math.sin(2.0 * math.pi * 440.0 * t / sr_44k) +
                math.sin(2.0 * math.pi * 554.37 * t / sr_44k) +
                math.sin(2.0 * math.pi * 659.25 * t / sr_44k)
            )
            # 3. Hi-hat transients at 8 kHz
            hihat = 0.2 * math.sin(2.0 * math.pi * 8000.0 * t / sr_44k) * (1.0 if (i % 256 < 32) else 0.0)
            
            raw_l = kick + chord + hihat
            raw_r = kick + chord * 0.9 + hihat * 1.1
            block_l.append(raw_l)
            block_r.append(raw_r)
            
        peak_l = max(abs(x) for x in block_l)
        block_l = [x / peak_l for x in block_l]
        p_low, p_high = 0.1, 25.0
        for _ in range(40):
            p_mid = (p_low + p_high) / 2.0
            shaped = [math.copysign(abs(x) ** p_mid, x) for x in block_l]
            r = math.sqrt(sum(x * x for x in shaped) / len(shaped))
            if r < target_rms_11_1db:
                p_high = p_mid
            else:
                p_low = p_mid
        block_l = [math.copysign(abs(x) ** p_mid, x) for x in block_l]
        
        # Mirror right channel with slight stereo spread while matching energy
        peak_r = max(abs(x) for x in block_r)
        block_r = [x / peak_r for x in block_r]
        p_low, p_high = 0.1, 25.0
        for _ in range(40):
            p_mid = (p_low + p_high) / 2.0
            shaped = [math.copysign(abs(x) ** p_mid, x) for x in block_r]
            r = math.sqrt(sum(x * x for x in shaped) / len(shaped))
            if r < target_rms_11_1db:
                p_high = p_mid
            else:
                p_low = p_mid
        block_r = [math.copysign(abs(x) ** p_mid, x) for x in block_r]
        
        s_l.extend(block_l)
        s_r.extend(block_r)
        
    write_wav_16bit("04_MusicalSim_Crest_11.1dB_44k_16bit.wav", sr_44k, 2, s_l, s_r)

    # -------------------------------------------------------------
    # 5. 05_HighDynamics_Crest_14dB_44k_16bit.wav
    # Expected: Peak = 0.0 dBFS, RMS = -14.0 dBFS, CREST = 14.0 dB
    # -------------------------------------------------------------
    target_crest_14db = 10.0 ** (14.0 / 20.0)  # ~5.0119
    target_rms_14db = 1.0 / target_crest_14db   # ~0.1995
    s_l = []
    for block_idx in range(n_44k // block_size):
        block = []
        for i in range(block_size):
            t = block_idx * block_size + i
            # Snare hit + sparse ambience
            snare = math.sin(2.0 * math.pi * 220.0 * (i % 1024) / sr_44k) * math.exp(-((i % 1024) / 40.0))
            pad = 0.05 * math.sin(2.0 * math.pi * 330.0 * t / sr_44k)
            block.append(snare + pad)
        peak = max(abs(x) for x in block)
        block = [x / peak for x in block]
        p_low, p_high = 0.1, 30.0
        for _ in range(40):
            p_mid = (p_low + p_high) / 2.0
            shaped = [math.copysign(abs(x) ** p_mid, x) for x in block]
            r = math.sqrt(sum(x * x for x in shaped) / len(shaped))
            if r < target_rms_14db:
                p_high = p_mid
            else:
                p_low = p_mid
        block = [math.copysign(abs(x) ** p_mid, x) for x in block]
        s_l.extend(block)
    write_wav_16bit("05_HighDynamics_Crest_14dB_44k_16bit.wav", sr_44k, 2, s_l, s_l)

    # -------------------------------------------------------------
    # 6. 06_MusicalSim_Crest_11.1dB_96k_24bit.wav
    # High-resolution 96 kHz 24-bit test file
    # Expected: Peak = 0.0 dBFS, RMS = -11.1 dBFS, CREST = 11.1 dB
    # -------------------------------------------------------------
    sr_96k = 96000
    n_96k = int(duration * sr_96k)
    block_size_96k = 2048
    s_l = []
    s_r = []
    for block_idx in range(n_96k // block_size_96k):
        block = []
        for i in range(block_size_96k):
            t = block_idx * block_size_96k + i
            kick = math.sin(2.0 * math.pi * 70.0 * (i % 512) / sr_96k) * math.exp(-((i % 512) / 120.0))
            chord = 0.3 * (
                math.sin(2.0 * math.pi * 440.0 * t / sr_96k) +
                math.sin(2.0 * math.pi * 554.37 * t / sr_96k) +
                math.sin(2.0 * math.pi * 659.25 * t / sr_96k)
            )
            block.append(kick + chord)
        peak = max(abs(x) for x in block)
        block = [x / peak for x in block]
        p_low, p_high = 0.1, 25.0
        for _ in range(40):
            p_mid = (p_low + p_high) / 2.0
            shaped = [math.copysign(abs(x) ** p_mid, x) for x in block]
            r = math.sqrt(sum(x * x for x in shaped) / len(shaped))
            if r < target_rms_11_1db:
                p_high = p_mid
            else:
                p_low = p_mid
        block = [math.copysign(abs(x) ** p_mid, x) for x in block]
        s_l.extend(block)
        s_r.extend(block)
    write_wav_24bit("06_MusicalSim_Crest_11.1dB_96k_24bit.wav", sr_96k, 2, s_l, s_r)

    # -------------------------------------------------------------
    # 7. 07_Sweep_10kHz_22.5kHz_-60dBFS_48k_24bit.wav
    # Frequency sweep: 10 kHz to 22.5 kHz at -60 dBFS
    # -------------------------------------------------------------
    sr_48k = 48000
    n_48k = int(duration * sr_48k)
    f0 = 10000.0
    f1 = 22500.0
    amp_60db = 10.0 ** (-60.0 / 20.0)  # 0.001 (-60 dBFS peak)
    fade_len = int(0.010 * sr_48k)     # 10ms smooth ramp

    s_sweep = []
    for n in range(n_48k):
        t = n / sr_48k
        phase = 2.0 * math.pi * (f0 * t + (f1 - f0) / (2.0 * duration) * t * t)
        val = amp_60db * math.sin(phase)
        if n < fade_len:
            val *= 0.5 * (1.0 - math.cos(math.pi * n / fade_len))
        elif n > n_48k - fade_len:
            val *= 0.5 * (1.0 - math.cos(math.pi * (n_48k - n) / fade_len))
        s_sweep.append(val)

    write_wav_24bit("07_Sweep_10kHz_22.5kHz_-60dBFS_48k_24bit.wav", sr_48k, 2, s_sweep, s_sweep)
    write_wav_16bit("07_Sweep_10kHz_22.5kHz_-60dBFS_48k_16bit.wav", sr_48k, 2, s_sweep, s_sweep)

    print("\nAll test reference WAV files successfully generated!")


if __name__ == "__main__":
    generate_all()
