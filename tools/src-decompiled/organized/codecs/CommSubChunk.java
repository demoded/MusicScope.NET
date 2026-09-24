/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import sdfgjkljljoftrytrszgijpokjprs.AiffBaseChunk;
import sdfgjkljljoftrytrszgijpokjprs.SubChunk;
import sdfgjkljljoftrytrszgijpokjprs.ChunkAttribute;

@SubChunk(DSP="COMM")
public class CommSubChunk
extends AiffBaseChunk {
    private short DSP;
    private int FFT;
    private short responseView;
    private double AdditionalMetadataValue;
    @ChunkAttribute(DSP=4)
    private char[] AudioFileExtension = new char[4];

    public short AdditionalMetadataValue() {
        return this.DSP;
    }

    public int AudioFileExtension() {
        return this.FFT;
    }

    public short IAudioFileCodec() {
        return this.responseView;
    }

    public double IAudioInputStream() {
        return this.AdditionalMetadataValue;
    }

    public char[] IAudioMetaInformation() {
        return (char[])this.AudioFileExtension.clone();
    }

    @Override
    public void FFT(ByteBuffer byteBuffer) {
        int n;
        this.DSP = byteBuffer.order(ByteOrder.BIG_ENDIAN).getShort();
        this.FFT = byteBuffer.order(ByteOrder.BIG_ENDIAN).getInt();
        this.responseView = byteBuffer.order(ByteOrder.BIG_ENDIAN).getShort();
        byte[] byArray = new byte[10];
        for (n = 0; n < 10; ++n) {
            byArray[n] = byteBuffer.get();
        }
        this.AdditionalMetadataValue = this.DSP(byArray);
        if (byteBuffer.position() + 4 <= byteBuffer.limit()) {
            for (n = 0; n < 4; ++n) {
                this.AudioFileExtension[n] = (char)byteBuffer.get();
            }
        }
    }

    private double DSP(byte[] byArray) {
        double d;
        long l = 0L;
        long l2 = 0L;
        long l3 = (byArray[0] & 0x7F) << 8 | byArray[1] & 0xFF;
        l = (long)(byArray[2] & 0xFF) << 24 | (long)(byArray[3] & 0xFF) << 16 | (long)(byArray[4] & 0xFF) << 8 | (long)(byArray[5] & 0xFF);
        l2 = (long)(byArray[6] & 0xFF) << 24 | (long)(byArray[7] & 0xFF) << 16 | (long)(byArray[8] & 0xFF) << 8 | (long)(byArray[9] & 0xFF);
        if (l3 == 0L && l == 0L && l2 == 0L) {
            d = 0.0;
        } else if (l3 == 32767L) {
            d = Double.MAX_VALUE;
        } else {
            l3 -= 16383L;
            d = (double)l * Math.pow(2.0, l3 -= 31L);
            d += (double)l2 * Math.pow(2.0, l3 -= 32L);
        }
        if ((byArray[0] & 0x80) == 128) {
            return -d;
        }
        return d;
    }
}

