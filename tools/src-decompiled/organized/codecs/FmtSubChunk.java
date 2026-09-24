/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import sdfgjkljljoftrytrszgijpokjprs.WaveBaseChunk;
import sdfgjkljljoftrytrszgijpokjprs.SubChunk;

@SubChunk(DSP="fmt ")
public class FmtSubChunk
extends WaveBaseChunk {
    private short DSP;
    private short FFT;
    private int responseView;
    private int AdditionalMetadataValue;
    private short AudioFileExtension;
    private short IAudioFileCodec;

    public short AdditionalMetadataValue() {
        return this.DSP;
    }

    public short AudioFileExtension() {
        return this.FFT;
    }

    public int IAudioFileCodec() {
        return this.responseView;
    }

    public int IAudioInputStream() {
        return this.AdditionalMetadataValue;
    }

    public short IAudioMetaInformation() {
        return this.IAudioFileCodec;
    }

    @Override
    public void FFT(ByteBuffer byteBuffer) {
        this.DSP = byteBuffer.order(ByteOrder.LITTLE_ENDIAN).getShort();
        this.FFT = byteBuffer.order(ByteOrder.LITTLE_ENDIAN).getShort();
        this.responseView = byteBuffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
        this.AdditionalMetadataValue = byteBuffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
        this.AudioFileExtension = byteBuffer.order(ByteOrder.LITTLE_ENDIAN).getShort();
        this.IAudioFileCodec = byteBuffer.order(ByteOrder.LITTLE_ENDIAN).getShort();
    }
}

