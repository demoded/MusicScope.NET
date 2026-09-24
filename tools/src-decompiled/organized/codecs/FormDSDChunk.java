/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.OutOfChunkRangeException;
import sdfgjkljljoftrytrszgijpokjprs.ILocalPropertyChunk;
import sdfgjkljljoftrytrszgijpokjprs.ChunkRequiredAnnotation;
import sdfgjkljljoftrytrszgijpokjprs.IChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.DSDFileStructure;
import sdfgjkljljoftrytrszgijpokjprs.ChunkIDAnnotation;

@ChunkRequiredAnnotation
@ChunkIDAnnotation(DSP="FRM8")
public class FormDSDChunk
extends DSDFileStructure
implements ILocalPropertyChunk {
    private String DSP;

    @Override
    public boolean DSP() {
        return true;
    }

    @Override
    public long responseView() {
        return 4L + super.responseView();
    }

    @Override
    public long IAudioInputStream() {
        return super.IAudioInputStream() - 4L;
    }

    @Override
    public void DSP(IChunkReader zBNFgJwMMkkaOLFmIBSJWbh) throws OutOfChunkRangeException {
        byte[] byArray;
        this.DSP = "";
        super.DSP(zBNFgJwMMkkaOLFmIBSJWbh);
        for (byte by : byArray = zBNFgJwMMkkaOLFmIBSJWbh.FFT(4).array()) {
            this.DSP = this.DSP + (char)by;
        }
        this.DSP(this.AudioFileExtension() + 4L);
    }

    @Override
    public boolean IAudioFileCodec() {
        boolean bl = super.IAudioFileCodec();
        return bl &= this.DSP.equals("DSD ");
    }
}

