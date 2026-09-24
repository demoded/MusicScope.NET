/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.OutOfChunkRangeException;
import sdfgjkljljoftrytrszgijpokjprs.IChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.DSDFileStructure;
import sdfgjkljljoftrytrszgijpokjprs.ChunkIDAnnotation;

@ChunkIDAnnotation(DSP="DSTC")
public class DSTFrameCRCChunk
extends DSDFileStructure {
    private static long DSP = 0L;
    private final long FFT = DSP++;
    private byte[] responseView;

    @Override
    public boolean IAudioFileCodec() {
        return super.IAudioFileCodec();
    }

    @Override
    public void DSP(IChunkReader zBNFgJwMMkkaOLFmIBSJWbh) throws OutOfChunkRangeException {
        this.responseView = null;
        super.DSP(zBNFgJwMMkkaOLFmIBSJWbh);
        this.responseView = zBNFgJwMMkkaOLFmIBSJWbh.FFT(4).array();
    }
}

