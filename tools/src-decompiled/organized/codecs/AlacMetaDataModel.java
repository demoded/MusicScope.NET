/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.FFT;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormatCode;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;

public class AlacMetaDataModel
implements IAudioMetaInformation {
    private final BitsPerSample DSP;
    private final int FFT;
    private final int responseView;
    private final int AdditionalMetadataValue;
    private FFT AudioFileExtension;

    AlacMetaDataModel(int n, int n2, int n3, BitsPerSample kFVWmcqOBgYFxPgeswapvPe) {
        this.responseView = n;
        this.AdditionalMetadataValue = n2;
        this.FFT = n3;
        this.DSP = kFVWmcqOBgYFxPgeswapvPe;
    }

    @Override
    public int DSP() {
        return this.responseView;
    }

    @Override
    public BitsPerSample FFT() {
        return this.DSP;
    }

    @Override
    public int responseView() {
        return this.FFT().FFT() * this.AdditionalMetadataValue() * this.DSP();
    }

    @Override
    public int AdditionalMetadataValue() {
        return this.FFT;
    }

    @Override
    public AudioFormatCode AudioFileExtension() {
        return AudioFormatCode.responseView;
    }

    @Override
    public long IAudioFileCodec() {
        return this.IAudioInputStream() / (long)this.responseView();
    }

    @Override
    public long IAudioInputStream() {
        return this.AdditionalMetadataValue * this.FFT * this.FFT().FFT();
    }

    @Override
    public String IAudioMetaInformation() {
        return "NoTrackName";
    }

    @Override
    public int IBaseAudioCodec() {
        return -1;
    }

    @Override
    public AudioExtension MetaInfomationCopy() {
        return AudioExtension.DSP;
    }

    @Override
    public long AacAudioCodec() {
        return Long.MIN_VALUE;
    }

    @Override
    public long AacMetaDataModel() {
        return this.AacAudioCodec();
    }

    @Override
    public FFT BufferedAacReader() {
        return this.AudioFileExtension;
    }

    public void DSP(FFT qPeIwmpzLZIktKLXAJOQHcO) {
        this.AudioFileExtension = qPeIwmpzLZIktKLXAJOQHcO;
    }
}

