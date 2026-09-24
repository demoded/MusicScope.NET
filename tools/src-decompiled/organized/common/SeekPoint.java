/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;

public class SeekPoint {
    protected long DSP;
    protected long FFT;
    protected int responseView;

    public SeekPoint(BitInputStream hrgPgVhiQYIZXeWOluMaPwR2) throws IOException {
        this.DSP = hrgPgVhiQYIZXeWOluMaPwR2.IAudioFileCodec(64);
        this.FFT = hrgPgVhiQYIZXeWOluMaPwR2.IAudioFileCodec(64);
        this.responseView = hrgPgVhiQYIZXeWOluMaPwR2.responseView(16);
    }

    public String toString() {
        return "sampleNumber=" + this.DSP + " streamOffset=" + this.FFT + " frameSamples=" + this.responseView;
    }
}

