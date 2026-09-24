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
@ChunkIDAnnotation(DSP="FVER")
public class FormatVersionChunk
extends DSDFileStructure {
    private byte[] DSP;

    @Override
    public long responseView() {
        return 4L + super.responseView();
    }

    @Override
    public void DSP(IChunkReader zBNFgJwMMkkaOLFmIBSJWbh) throws OutOfChunkRangeException {
        this.DSP = new byte[4];
        super.DSP(zBNFgJwMMkkaOLFmIBSJWbh);
        this.DSP = zBNFgJwMMkkaOLFmIBSJWbh.FFT(4).array();
    }

    @Override
    public boolean IAudioFileCodec() {
        boolean bl = super.IAudioFileCodec();
        for (byte by : this.DSP) {
            bl &= by >= 0;
        }
        return bl;
    }
}

