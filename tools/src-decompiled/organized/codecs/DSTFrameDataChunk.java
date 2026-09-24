/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.OutOfChunkRangeException;
import sdfgjkljljoftrytrszgijpokjprs.MultipleInstancesAnnotation;
import sdfgjkljljoftrytrszgijpokjprs.IChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.DSDFileStructure;
import sdfgjkljljoftrytrszgijpokjprs.ChunkIDAnnotation;

@MultipleInstancesAnnotation
@ChunkIDAnnotation(DSP="DSTF")
public class DSTFrameDataChunk
extends DSDFileStructure {
    private static long DSP = 0L;
    private final long FFT = DSP++;
    private byte[] responseView;

    public long DSP() {
        return this.FFT;
    }

    public byte[] IAudioMetaInformation() {
        return this.responseView;
    }

    @Override
    public void DSP(IChunkReader zBNFgJwMMkkaOLFmIBSJWbh) throws OutOfChunkRangeException {
        this.responseView = null;
        super.DSP(zBNFgJwMMkkaOLFmIBSJWbh);
        this.responseView = zBNFgJwMMkkaOLFmIBSJWbh.FFT((int)this.IAudioInputStream()).array();
    }
}

