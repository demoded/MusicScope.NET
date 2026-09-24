/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.OutOfChunkRangeException;
import sdfgjkljljoftrytrszgijpokjprs.IRootChunkStructure;
import sdfgjkljljoftrytrszgijpokjprs.IChunkReader;

public class DSFFileStructure
implements IRootChunkStructure {
    private String DSP;
    private long FFT;
    private long responseView;
    private long AdditionalMetadataValue;

    @Override
    public String FFT() {
        return this.DSP;
    }

    public long IAudioInputStream() {
        return this.FFT;
    }

    @Override
    public long responseView() {
        return this.IAudioInputStream();
    }

    @Override
    public long AdditionalMetadataValue() {
        return this.responseView;
    }

    @Override
    public long AudioFileExtension() {
        return this.AdditionalMetadataValue;
    }

    @Override
    public void DSP(IChunkReader zBNFgJwMMkkaOLFmIBSJWbh) throws OutOfChunkRangeException {
        byte[] byArray;
        this.DSP = "";
        this.FFT = -1L;
        this.responseView = -1L;
        this.AdditionalMetadataValue = -1L;
        this.responseView = zBNFgJwMMkkaOLFmIBSJWbh.AdditionalMetadataValue();
        for (byte by : byArray = zBNFgJwMMkkaOLFmIBSJWbh.FFT(4).array()) {
            this.DSP = this.DSP + (char)by;
        }
        this.FFT = zBNFgJwMMkkaOLFmIBSJWbh.DSP(8).getLong();
        this.AdditionalMetadataValue = zBNFgJwMMkkaOLFmIBSJWbh.AdditionalMetadataValue();
    }

    @Override
    public boolean IAudioFileCodec() {
        boolean bl = true;
        bl &= this.DSP != null && this.DSP.length() == 4;
        bl &= this.FFT > 0L;
        bl &= this.responseView >= 0L;
        return bl &= this.AdditionalMetadataValue >= 12L;
    }
}

