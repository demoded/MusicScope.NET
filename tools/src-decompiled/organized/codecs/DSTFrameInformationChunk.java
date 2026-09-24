/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.OutOfChunkRangeException;
import sdfgjkljljoftrytrszgijpokjprs.ChunkRequiredAnnotation;
import sdfgjkljljoftrytrszgijpokjprs.IChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.DSDFileStructure;
import sdfgjkljljoftrytrszgijpokjprs.ChunkIDAnnotation;

@ChunkRequiredAnnotation(responseView="DST ")
@ChunkIDAnnotation(DSP="FRTE")
public class DSTFrameInformationChunk
extends DSDFileStructure {
    private long DSP;
    private int FFT;

    public long DSP() {
        return this.DSP;
    }

    public int IAudioMetaInformation() {
        return this.FFT;
    }

    @Override
    public long responseView() {
        return 6L + super.responseView();
    }

    @Override
    public boolean IAudioFileCodec() {
        return super.IAudioFileCodec();
    }

    @Override
    public void DSP(IChunkReader zBNFgJwMMkkaOLFmIBSJWbh) throws OutOfChunkRangeException {
        this.DSP = -1L;
        this.FFT = -1;
        super.DSP(zBNFgJwMMkkaOLFmIBSJWbh);
        this.DSP = zBNFgJwMMkkaOLFmIBSJWbh.FFT(4).getInt();
        this.FFT = zBNFgJwMMkkaOLFmIBSJWbh.FFT(2).getShort();
        this.DSP(zBNFgJwMMkkaOLFmIBSJWbh.AdditionalMetadataValue());
    }
}

