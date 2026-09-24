/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.FFT;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormatCode;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.RiffChunk;

public class WavMetaDataModel
implements IAudioMetaInformation {
    private final RiffChunk DSP;
    private final boolean FFT;
    private final long responseView;
    private final long AdditionalMetadataValue;
    private FFT AudioFileExtension;

    public WavMetaDataModel(RiffChunk zNUkXzVStfiQtZdxpmhzalK, boolean bl, long l, long l2) {
        this.DSP = zNUkXzVStfiQtZdxpmhzalK;
        this.FFT = bl;
        this.responseView = l;
        this.AdditionalMetadataValue = l2;
        this.AudioFileExtension = null;
    }

    @Override
    public int DSP() {
        return this.DSP.AdditionalMetadataValue().IAudioFileCodec();
    }

    @Override
    public BitsPerSample FFT() {
        return BitsPerSample.FFT(this.DSP.AdditionalMetadataValue().IAudioMetaInformation());
    }

    @Override
    public int responseView() {
        return this.DSP.AdditionalMetadataValue().IAudioInputStream();
    }

    @Override
    public int AdditionalMetadataValue() {
        return this.DSP.AdditionalMetadataValue().AudioFileExtension();
    }

    @Override
    public AudioFormatCode AudioFileExtension() {
        switch (this.DSP.AdditionalMetadataValue().AdditionalMetadataValue()) {
            case 1: {
                return AudioFormatCode.DSP;
            }
            case 3: {
                return AudioFormatCode.FFT;
            }
        }
        return AudioFormatCode.responseView;
    }

    @Override
    public long IAudioFileCodec() {
        return this.DSP.AudioFileExtension().FFT() / (long)this.responseView();
    }

    @Override
    public long IAudioInputStream() {
        return this.DSP.AudioFileExtension().FFT();
    }

    @Override
    public String IAudioMetaInformation() {
        return "No name";
    }

    @Override
    public int IBaseAudioCodec() {
        return (int)this.DSP.AudioFileExtension().DSP();
    }

    @Override
    public AudioExtension MetaInfomationCopy() {
        return this.FFT ? AudioExtension.AdditionalMetadataValue : AudioExtension.responseView;
    }

    @Override
    public long AacAudioCodec() {
        return this.responseView;
    }

    @Override
    public long AacMetaDataModel() {
        return this.AdditionalMetadataValue;
    }

    @Override
    public FFT BufferedAacReader() {
        return this.AudioFileExtension;
    }

    public void DSP(FFT qPeIwmpzLZIktKLXAJOQHcO) {
        this.AudioFileExtension = qPeIwmpzLZIktKLXAJOQHcO;
    }
}

