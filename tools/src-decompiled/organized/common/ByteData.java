/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

public class ByteData {
    private final byte[] DSP;
    private int FFT;

    public ByteData(int n) {
        if (n <= 0) {
            n = 256;
        }
        this.DSP = new byte[n];
        this.FFT = 0;
    }

    public void DSP(byte by) {
        this.DSP[this.FFT++] = by;
    }

    public byte[] DSP() {
        return this.DSP;
    }

    public byte DSP(int n) {
        return this.DSP[n];
    }

    public int FFT() {
        return this.FFT;
    }

    public void FFT(int n) {
        if (n > this.DSP.length) {
            n = this.DSP.length;
        }
        this.FFT = n;
    }
}

