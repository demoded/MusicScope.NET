/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.DSFFileStructure;
import sdfgjkljljoftrytrszgijpokjprs.OutOfChunkRangeException;
import sdfgjkljljoftrytrszgijpokjprs.IChainedPropertyChunk;
import sdfgjkljljoftrytrszgijpokjprs.ChunkRequiredAnnotation;
import sdfgjkljljoftrytrszgijpokjprs.IChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.ChunkIDAnnotation;

@ChunkRequiredAnnotation
@ChunkIDAnnotation(DSP="DSD ")
public class DSDChunkHeader
extends DSFFileStructure
implements IChainedPropertyChunk {
    private long DSP;
    private long FFT;

    @Override
    public boolean IAudioFileCodec() {
        return super.IAudioFileCodec();
    }

    @Override
    public boolean DSP() {
        return true;
    }

    @Override
    public void DSP(IChunkReader zBNFgJwMMkkaOLFmIBSJWbh) throws OutOfChunkRangeException {
        this.DSP = -1L;
        this.FFT = 0L;
        super.DSP(zBNFgJwMMkkaOLFmIBSJWbh);
        this.DSP = zBNFgJwMMkkaOLFmIBSJWbh.DSP(8).getLong();
        this.FFT = zBNFgJwMMkkaOLFmIBSJWbh.DSP(8).getLong();
    }
}

