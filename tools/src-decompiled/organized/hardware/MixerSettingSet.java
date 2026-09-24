/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Mixer;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;

public class MixerSettingSet {
    private final AudioFormat.Encoding DSP;
    private final boolean FFT;
    private final Mixer.Info responseView;
    private final List<Integer> AdditionalMetadataValue;
    private final List<Integer> AudioFileExtension;
    private final List<BitsPerSample> IAudioFileCodec;

    public MixerSettingSet(Mixer.Info info, AudioFormat.Encoding encoding, boolean bl) {
        this.responseView = info;
        this.DSP = encoding;
        this.FFT = bl;
        this.AdditionalMetadataValue = new ArrayList<Integer>(0);
        this.AudioFileExtension = new ArrayList<Integer>(0);
        this.IAudioFileCodec = new ArrayList<BitsPerSample>(0);
    }

    public void DSP(int n) {
        if (!this.AdditionalMetadataValue.contains(n)) {
            this.AdditionalMetadataValue.add(n);
        }
    }

    public void FFT(int n) {
        if (!this.AudioFileExtension.contains(n)) {
            this.AudioFileExtension.add(n);
        }
    }

    public void DSP(BitsPerSample kFVWmcqOBgYFxPgeswapvPe) {
        if (!this.IAudioFileCodec.contains((Object)kFVWmcqOBgYFxPgeswapvPe)) {
            this.IAudioFileCodec.add(kFVWmcqOBgYFxPgeswapvPe);
        }
    }

    public Mixer.Info DSP() {
        return this.responseView;
    }

    public List<Integer> FFT() {
        return Collections.unmodifiableList(this.AdditionalMetadataValue);
    }

    public List<Integer> responseView() {
        return Collections.unmodifiableList(this.AudioFileExtension);
    }

    public List<BitsPerSample> AdditionalMetadataValue() {
        return Collections.unmodifiableList(this.IAudioFileCodec);
    }

    public AudioFormat DSP(int n, BitsPerSample kFVWmcqOBgYFxPgeswapvPe, int n2) {
        return new AudioFormat(this.DSP, n, kFVWmcqOBgYFxPgeswapvPe.DSP(), n2, n2 * kFVWmcqOBgYFxPgeswapvPe.FFT(), (float)n2 * (float)n, this.FFT);
    }

    public String toString() {
        if (this.responseView != null) {
            return this.responseView.getName();
        }
        return "No Audio Output";
    }
}

