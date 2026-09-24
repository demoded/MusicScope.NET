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

public class LineInMetaDataModel
implements IAudioMetaInformation {
    private final AudioFormat DSP;
    private final long FFT;

    LineInMetaDataModel(AudioFormat audioFormat, long l) {
        this.DSP = audioFormat;
        this.FFT = l;
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
        return this.DSP() * this.FFT().FFT() * this.AdditionalMetadataValue();
    }

    @Override
    public int AdditionalMetadataValue() {
        return this.DSP.getChannels();
    }

    @Override
    public AudioFormatCode AudioFileExtension() {
        return AudioFormatCode.DSP;
    }

    @Override
    public long IAudioFileCodec() {
        return 60L;
    }

    @Override
    public long IAudioInputStream() {
        return (long)this.responseView() * this.IAudioFileCodec();
    }

    @Override
    public String IAudioMetaInformation() {
        return "LINE IN";
    }

    @Override
    public int IBaseAudioCodec() {
        return -1;
    }

    @Override
    public AudioExtension MetaInfomationCopy() {
        return AudioExtension.MetaInfomationCopy;
    }

    @Override
    public long AacAudioCodec() {
        return this.FFT;
    }

    @Override
    public long AacMetaDataModel() {
        return this.AacAudioCodec();
    }

    @Override
    public FFT BufferedAacReader() {
        return null;
    }
}

