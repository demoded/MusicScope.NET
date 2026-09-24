/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.Arrays;
import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.FFT;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormatCode;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.FormChunk;

public class AiffMetaDataModel
implements IAudioMetaInformation {
    private static final char[] DSP = new char[]{'s', 'o', 'w', 't'};
    private final FormChunk FFT;
    private final AudioFormatCode responseView;
    private final long AdditionalMetadataValue;
    private final long AudioFileExtension;
    private FFT IAudioFileCodec;

    public AiffMetaDataModel(FormChunk cdSECoXyrascmeWiUkiBTmK2, long l, long l2) {
        this.FFT = cdSECoXyrascmeWiUkiBTmK2;
        this.AdditionalMetadataValue = l;
        this.AudioFileExtension = l2;
        this.IAudioFileCodec = null;
        switch (cdSECoXyrascmeWiUkiBTmK2.AdditionalMetadataValue()[3]) {
            case 'F': {
                this.responseView = AudioFormatCode.DSP;
                break;
            }
            case 'C': {
                this.responseView = AudioFormatCode.FFT;
                break;
            }
            default: {
                this.responseView = AudioFormatCode.responseView;
            }
        }
    }

    @Override
    public int DSP() {
        return (int)this.FFT.AudioFileExtension().IAudioInputStream();
    }

    @Override
    public BitsPerSample FFT() {
        return BitsPerSample.FFT(this.FFT.AudioFileExtension().IAudioFileCodec());
    }

    @Override
    public int responseView() {
        return this.AdditionalMetadataValue() * this.FFT().FFT() * this.DSP();
    }

    @Override
    public int AdditionalMetadataValue() {
        return this.FFT.AudioFileExtension().AdditionalMetadataValue();
    }

    @Override
    public AudioFormatCode AudioFileExtension() {
        return this.responseView;
    }

    @Override
    public long IAudioFileCodec() {
        return this.IAudioInputStream() / (long)this.responseView();
    }

    @Override
    public long IAudioInputStream() {
        return this.FFT.AudioFileExtension().AudioFileExtension() * this.FFT().FFT() * this.AdditionalMetadataValue();
    }

    @Override
    public String IAudioMetaInformation() {
        return "No Name";
    }

    @Override
    public int IBaseAudioCodec() {
        return this.FFT.IAudioFileCodec().AudioFileExtension();
    }

    @Override
    public AudioExtension MetaInfomationCopy() {
        return AudioExtension.IBaseAudioCodec;
    }

    @Override
    public long AacAudioCodec() {
        return this.AdditionalMetadataValue;
    }

    @Override
    public long AacMetaDataModel() {
        return this.AudioFileExtension;
    }

    @Override
    public FFT BufferedAacReader() {
        return this.IAudioFileCodec;
    }

    public void DSP(FFT qPeIwmpzLZIktKLXAJOQHcO) {
        this.IAudioFileCodec = qPeIwmpzLZIktKLXAJOQHcO;
    }

    public boolean AiffAudioCodec() {
        String string = Arrays.toString(this.FFT.AudioFileExtension().IAudioMetaInformation());
        String string2 = Arrays.toString(DSP);
        return string.equals(string2);
    }
}

