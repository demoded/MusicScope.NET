/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.nio.ByteBuffer;
import sdfgjkljljoftrytrszgijpokjprs.FmtSubChunk;
import sdfgjkljljoftrytrszgijpokjprs.DataSubChunk;
import sdfgjkljljoftrytrszgijpokjprs.WaveBaseChunk;
import sdfgjkljljoftrytrszgijpokjprs.ChunkAttribute;

public class RiffChunk
extends WaveBaseChunk {
    @ChunkAttribute(DSP=4)
    private final char[] DSP = new char[4];
    private final FmtSubChunk FFT;
    private final DataSubChunk responseView = new DataSubChunk();

    public RiffChunk() {
        this.FFT = new FmtSubChunk();
    }

    public FmtSubChunk AdditionalMetadataValue() {
        return this.FFT;
    }

    public DataSubChunk AudioFileExtension() {
        return this.responseView;
    }

    @Override
    public void FFT(ByteBuffer byteBuffer) {
        for (int i = 0; i < 4; ++i) {
            this.DSP[i] = (char)byteBuffer.get();
        }
    }
}

