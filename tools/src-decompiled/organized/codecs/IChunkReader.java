/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.nio.ByteBuffer;
import sdfgjkljljoftrytrszgijpokjprs.OutOfChunkRangeException;

public interface IChunkReader {
    public ByteBuffer FFT(int var1) throws OutOfChunkRangeException;

    public ByteBuffer DSP(int var1) throws OutOfChunkRangeException;

    public long AdditionalMetadataValue();
}

