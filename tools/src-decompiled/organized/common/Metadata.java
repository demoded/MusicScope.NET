/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

public abstract class Metadata {
    protected boolean IAudioFileCodec;
    protected int IAudioInputStream;

    public Metadata(boolean bl, int n) {
        this.IAudioFileCodec = bl;
        this.IAudioInputStream = n;
    }

    public boolean DSP() {
        return this.IAudioFileCodec;
    }

    public int FFT() {
        return this.IAudioInputStream + 4;
    }
}

