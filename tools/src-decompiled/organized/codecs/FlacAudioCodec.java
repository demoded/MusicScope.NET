/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.FileNotFoundException;
import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.responseView;
import sdfgjkljljoftrytrszgijpokjprs.StreamInfo;
import sdfgjkljljoftrytrszgijpokjprs.BufferedFlacReader;
import sdfgjkljljoftrytrszgijpokjprs.IAudioFileCodec;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.AudioFileExtension;
import sdfgjkljljoftrytrszgijpokjprs.FlacMetaDataModel;

@AudioFileExtension(DSP={"flac"})
public class FlacAudioCodec
implements IAudioFileCodec {
    private final BufferedFlacReader DSP;
    private final String FFT;
    private FlacMetaDataModel responseView;

    public FlacAudioCodec() {
        this.DSP = null;
        this.FFT = null;
    }

    public FlacAudioCodec(String string) throws FileNotFoundException, IOException {
        this.FFT = string;
        this.DSP = new BufferedFlacReader(string);
    }

    @Override
    public IAudioFileCodec DSP(String string) throws FileNotFoundException, IOException {
        return new FlacAudioCodec(string);
    }

    @Override
    public int DSP(byte[] byArray, int n, int n2) {
        if (this.DSP != null) {
            return this.DSP.DSP(byArray, n, n2);
        }
        return 0;
    }

    @Override
    public IAudioMetaInformation FFT() {
        if (this.DSP != null && this.responseView == null && this.FFT != null) {
            StreamInfo kTkvbLlxuYGcvSGyJtmnFsX = this.DSP.FFT();
            this.responseView = new FlacMetaDataModel(kTkvbLlxuYGcvSGyJtmnFsX);
            this.responseView.DSP(sdfgjkljljoftrytrszgijpokjprs.responseView.DSP(this.FFT));
        }
        return this.responseView;
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

