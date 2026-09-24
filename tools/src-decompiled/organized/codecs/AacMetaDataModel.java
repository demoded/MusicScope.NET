/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.FFT;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormatCode;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;

class AacMetaDataModel
implements IAudioMetaInformation {
    private final BitsPerSample DSP;
    private final int FFT;
    private final int responseView;
    private final double AdditionalMetadataValue;
    private FFT AudioFileExtension;

    public AacMetaDataModel(int n, int n2, int n3, double d) {
        this.DSP = BitsPerSample.FFT(n);
        this.FFT = n2;
        this.responseView = n3;
        this.AdditionalMetadataValue = d;
        this.AudioFileExtension = null;
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
        return this.AdditionalMetadataValue() * this.FFT().FFT() * this.DSP();
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
        return (long)this.AdditionalMetadataValue;
    }

    @Override
    public long IAudioInputStream() {
        return (long)(this.AdditionalMetadataValue * (double)this.responseView());
    }

    @Override
    public String IAudioMetaInformation() {
        return "No Name";
    }

    @Override
    public int IBaseAudioCodec() {
        return 0;
    }

    @Override
    public AudioExtension MetaInfomationCopy() {
        return AudioExtension.FFT;
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

