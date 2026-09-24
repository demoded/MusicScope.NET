/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.FFT;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormatCode;

public interface IAudioMetaInformation {
    public int DSP();

    public BitsPerSample FFT();

    public int responseView();

    public int AdditionalMetadataValue();

    public AudioFormatCode AudioFileExtension();

    public long IAudioFileCodec();

    public long IAudioInputStream();

    public String IAudioMetaInformation();

    public int IBaseAudioCodec();

    public AudioExtension MetaInfomationCopy();

    public long AacAudioCodec();

    public long AacMetaDataModel();

    public FFT BufferedAacReader();
}

