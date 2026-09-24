/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import sdfgjkljljoftrytrszgijpokjprs.AiffBaseChunk;
import sdfgjkljljoftrytrszgijpokjprs.SubChunk;

@SubChunk(DSP="SSND", responseView=8L)
public class SsndSubChunk
extends AiffBaseChunk {
    private int DSP;
    private int FFT;
    private long responseView;

    public int AdditionalMetadataValue() {
        return this.DSP;
    }

    public int AudioFileExtension() {
        return (int)this.responseView;
    }

    @Override
    public void FFT(ByteBuffer byteBuffer) {
        this.DSP = byteBuffer.order(ByteOrder.BIG_ENDIAN).getInt();
        this.FFT = byteBuffer.order(ByteOrder.BIG_ENDIAN).getInt();
        this.responseView = this.DSP() + (long)byteBuffer.position() + (long)this.AdditionalMetadataValue();
    }
}

