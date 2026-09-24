/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.BufferedAlacReader;
import sdfgjkljljoftrytrszgijpokjprs.IAudioFileCodec;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.AudioFileExtension;

@AudioFileExtension(DSP={"m4a"})
public class AlacAudioCodec
implements IAudioFileCodec {
    private final BufferedAlacReader DSP;

    public AlacAudioCodec() {
        this.DSP = null;
    }

    public AlacAudioCodec(String string) {
        this.DSP = new BufferedAlacReader(string);
    }

    @Override
    public IAudioFileCodec DSP(String string) throws Exception {
        return new AlacAudioCodec(string);
    }

    @Override
    public int DSP(byte[] byArray, int n, int n2) {
        if (this.DSP != null) {
            return this.DSP.DSP(byArray, n, n2);
        }
        return -1;
    }

    @Override
    public IAudioMetaInformation FFT() {
        if (this.DSP != null) {
            return this.DSP.FFT();
        }
        return null;
    }

    @Override
    public IAudioMetaInformation responseView() {
        return this.FFT();
    }

    @Override
    public void AdditionalMetadataValue() {
        this.DSP.DSP();
    }
}

