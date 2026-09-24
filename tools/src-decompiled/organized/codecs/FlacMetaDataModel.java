/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.StreamInfo;
import sdfgjkljljoftrytrszgijpokjprs.FFT;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormatCode;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;

public class FlacMetaDataModel
implements IAudioMetaInformation {
    private final StreamInfo DSP;
    private FFT FFT;

    public FlacMetaDataModel(StreamInfo kTkvbLlxuYGcvSGyJtmnFsX) {
        this.DSP = kTkvbLlxuYGcvSGyJtmnFsX;
    }

    @Override
    public int DSP() {
        return this.DSP.IAudioFileCodec();
    }

    @Override
    public BitsPerSample FFT() {
        return BitsPerSample.FFT(this.DSP.IAudioInputStream());
    }

    @Override
    public int responseView() {
        return this.FFT().FFT() * this.AdditionalMetadataValue() * this.DSP();
    }

    @Override
    public int AdditionalMetadataValue() {
        return this.DSP.IAudioMetaInformation();
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
        return this.DSP.AudioFileExtension() * (long)this.AdditionalMetadataValue() * (long)this.FFT().FFT();
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
        return AudioExtension.AudioFileExtension;
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
        return this.FFT;
    }

    public void DSP(FFT qPeIwmpzLZIktKLXAJOQHcO) {
        this.FFT = qPeIwmpzLZIktKLXAJOQHcO;
    }
}

