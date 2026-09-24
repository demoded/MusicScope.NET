/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import javax.sound.sampled.AudioFormat;
import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.FFT;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormatCode;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;

class Mp3MetaDataModel
implements IAudioMetaInformation {
    private final AudioFormat DSP;
    private final int FFT;
    private FFT responseView;

    public Mp3MetaDataModel(AudioFormat audioFormat, int n) {
        this.DSP = audioFormat;
        this.FFT = n;
    }

    @Override
    public int DSP() {
        return (int)this.DSP.getSampleRate();
    }

    @Override
    public BitsPerSample FFT() {
        return BitsPerSample.FFT(this.DSP.getSampleSizeInBits());
    }

    @Override
    public int responseView() {
        return this.DSP() * this.AdditionalMetadataValue() * this.FFT().FFT();
    }

    @Override
    public int AdditionalMetadataValue() {
        return this.DSP.getChannels();
    }

    @Override
    public AudioFormatCode AudioFileExtension() {
        return AudioFormatCode.responseView;
    }

    @Override
    public long IAudioFileCodec() {
        return this.FFT / this.responseView();
    }

    @Override
    public long IAudioInputStream() {
        return this.FFT;
    }

    @Override
    public String IAudioMetaInformation() {
        return "No name";
    }

    @Override
    public int IBaseAudioCodec() {
        return 0;
    }

    @Override
    public AudioExtension MetaInfomationCopy() {
        return AudioExtension.IAudioMetaInformation;
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
        return this.responseView;
    }

    public void DSP(FFT qPeIwmpzLZIktKLXAJOQHcO) {
        this.responseView = qPeIwmpzLZIktKLXAJOQHcO;
    }
}

