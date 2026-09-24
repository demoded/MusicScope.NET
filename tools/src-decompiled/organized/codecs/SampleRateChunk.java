/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.OutOfChunkRangeException;
import sdfgjkljljoftrytrszgijpokjprs.ChunkRequiredAnnotation;
import sdfgjkljljoftrytrszgijpokjprs.IChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.DSDFileStructure;
import sdfgjkljljoftrytrszgijpokjprs.ChunkIDAnnotation;

@ChunkRequiredAnnotation
@ChunkIDAnnotation(DSP="FS  ")
public class SampleRateChunk
extends DSDFileStructure {
    private int DSP;

    public int DSP() {
        return this.DSP;
    }

    @Override
    public long responseView() {
        return 4L + super.responseView();
    }

    @Override
    public void DSP(IChunkReader zBNFgJwMMkkaOLFmIBSJWbh) throws OutOfChunkRangeException {
        this.DSP = -1;
        super.DSP(zBNFgJwMMkkaOLFmIBSJWbh);
        this.DSP = zBNFgJwMMkkaOLFmIBSJWbh.FFT(4).getInt();
    }

    @Override
    public boolean IAudioFileCodec() {
        boolean bl = super.IAudioFileCodec();
        bl &= this.DSP > 0;
        return bl &= this.DSP % 44100 == 0;
    }
}

