/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.ILocalPropertyChunk;
import sdfgjkljljoftrytrszgijpokjprs.ChunkRequiredAnnotation;
import sdfgjkljljoftrytrszgijpokjprs.DSDFileStructure;
import sdfgjkljljoftrytrszgijpokjprs.ChunkIDAnnotation;

@ChunkIDAnnotation(DSP="DST ")
@ChunkRequiredAnnotation(FFT={"DSD "})
public class DSTSoundDataChunk
extends DSDFileStructure
implements ILocalPropertyChunk {
    @Override
    public boolean DSP() {
        return false;
    }
}

