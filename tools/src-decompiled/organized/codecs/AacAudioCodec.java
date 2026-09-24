/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.IAudioFileCodec;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.AudioFileExtension;
import sdfgjkljljoftrytrszgijpokjprs.BufferedAacReader;

@AudioFileExtension(DSP={"m4a"})
public class AacAudioCodec
implements IAudioFileCodec {
    private final BufferedAacReader DSP;

    public AacAudioCodec() {
        this.DSP = null;
    }

    public AacAudioCodec(String string) throws IOException {
        this.DSP = new BufferedAacReader(string);
    }

    @Override
    public IAudioFileCodec DSP(String string) throws Exception {
        return new AacAudioCodec(string);
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
        return this.responseView();
    }

    @Override
    public IAudioMetaInformation responseView() {
        if (this.DSP != null) {
            return this.DSP.DSP();
        }
        return null;
    }

    @Override
    public void AdditionalMetadataValue() {
        this.DSP.FFT();
    }
}

