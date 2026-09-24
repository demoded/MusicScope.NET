/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.AlacInputStream;
import sdfgjkljljoftrytrszgijpokjprs.DemuxResT;
import sdfgjkljljoftrytrszgijpokjprs.AlacFile;

public class AlacContextModel {
    private DemuxResT DSP;
    private AlacFile FFT = new AlacFile();
    private AlacInputStream responseView;
    private int AdditionalMetadataValue = 0;
    private int AudioFileExtension;
    private boolean IAudioFileCodec;
    private String IAudioInputStream = "";
    private byte[] IAudioMetaInformation = new byte[81920];

    public AlacContextModel() {
        this.DSP = new DemuxResT();
    }

    public DemuxResT DSP() {
        return this.DSP;
    }

    public void DSP(DemuxResT tTvVyJBikEjkvfEQPPobmUz) {
        this.DSP = tTvVyJBikEjkvfEQPPobmUz;
    }

    public AlacFile FFT() {
        return this.FFT;
    }

    public void DSP(AlacFile cmBIzvlUaDPTiIjykjjmigy2) {
        this.FFT = cmBIzvlUaDPTiIjykjjmigy2;
    }

    public AlacInputStream responseView() {
        return this.responseView;
    }

    public void DSP(AlacInputStream nwteqrdXWwWpaoJmiBDNxxz) {
        this.responseView = nwteqrdXWwWpaoJmiBDNxxz;
    }

    public int AdditionalMetadataValue() {
        return this.AdditionalMetadataValue;
    }

    public void DSP(int n) {
        this.AdditionalMetadataValue = n;
    }

    public int AudioFileExtension() {
        return this.AudioFileExtension;
    }

    public void FFT(int n) {
        this.AudioFileExtension = n;
    }

    public void DSP(boolean bl) {
        this.IAudioFileCodec = bl;
    }

    public String IAudioFileCodec() {
        return this.IAudioInputStream;
    }

    public void DSP(String string) {
        this.IAudioInputStream = string;
    }

    public byte[] IAudioInputStream() {
        return this.IAudioMetaInformation;
    }
}

