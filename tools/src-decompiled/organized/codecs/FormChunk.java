/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.nio.ByteBuffer;
import sdfgjkljljoftrytrszgijpokjprs.CommSubChunk;
import sdfgjkljljoftrytrszgijpokjprs.AiffBaseChunk;
import sdfgjkljljoftrytrszgijpokjprs.ChunkAttribute;
import sdfgjkljljoftrytrszgijpokjprs.SsndSubChunk;

public class FormChunk
extends AiffBaseChunk {
    @ChunkAttribute(DSP=4)
    private final char[] DSP = new char[4];
    private final CommSubChunk FFT = new CommSubChunk();
    private final SsndSubChunk responseView = new SsndSubChunk();

    public char[] AdditionalMetadataValue() {
        return this.DSP;
    }

    public CommSubChunk AudioFileExtension() {
        return this.FFT;
    }

    public SsndSubChunk IAudioFileCodec() {
        return this.responseView;
    }

    @Override
    public void FFT(ByteBuffer byteBuffer) {
        for (int i = 0; i < 4; ++i) {
            this.DSP[i] = (char)byteBuffer.get();
        }
    }
}

