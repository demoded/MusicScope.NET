/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.AudioFormat;

public enum AudioExtension {
    DSP("m4a", AudioFormat.DSP),
    FFT("m4a", AudioFormat.DSP),
    responseView("wav", AudioFormat.DSP),
    AdditionalMetadataValue("wav", AudioFormat.DSP),
    AudioFileExtension("flac", AudioFormat.DSP),
    IAudioFileCodec("dsf", AudioFormat.FFT),
    IAudioInputStream("dff", AudioFormat.FFT),
    IAudioMetaInformation("mp3", AudioFormat.DSP),
    IBaseAudioCodec("aif", AudioFormat.DSP),
    MetaInfomationCopy("", AudioFormat.DSP),
    AacAudioCodec("", AudioFormat.DSP),
    AacMetaDataModel("", AudioFormat.responseView);

    private final String BufferedAacReader;
    private final AudioFormat AiffAudioCodec;

    private AudioExtension(String string2, AudioFormat ejrvtUxaUIJixnuJbyIXpxs) {
        this.BufferedAacReader = string2;
        this.AiffAudioCodec = ejrvtUxaUIJixnuJbyIXpxs;
    }

    public String DSP() {
        return this.BufferedAacReader;
    }

    public AudioFormat FFT() {
        return this.AiffAudioCodec;
    }
}

