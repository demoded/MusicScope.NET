/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import sdfgjkljljoftrytrszgijpokjprs.AbstractMetaDataModel;
import sdfgjkljljoftrytrszgijpokjprs.ChunkAttribute;

public abstract class AiffBaseChunk
extends AbstractMetaDataModel {
    @ChunkAttribute(DSP=4)
    private char[] DSP = new char[4];
    @ChunkAttribute(DSP=4)
    private long FFT;

    @Override
    public long FFT() {
        return this.FFT;
    }

    @Override
    public char[] responseView() {
        return this.DSP;
    }

    @Override
    public void DSP(ByteBuffer byteBuffer) {
        for (int i = 0; i < 4; ++i) {
            this.DSP[i] = (char)byteBuffer.get();
        }
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(8);
        byteBuffer2.putInt(4, byteBuffer.order(ByteOrder.BIG_ENDIAN).getInt());
        this.FFT = byteBuffer2.getLong();
    }
}

