/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.DSTSoundDataChunk;
import sdfgjkljljoftrytrszgijpokjprs.ChunkRequiredAnnotation;
import sdfgjkljljoftrytrszgijpokjprs.ChunkIDAnnotation;

@ChunkIDAnnotation(DSP="DST ")
@ChunkRequiredAnnotation(FFT={"DSD "})
public class ExtendedDSTSoundDataChunk
extends DSTSoundDataChunk {
    @Override
    public boolean DSP() {
        return true;
    }
}

