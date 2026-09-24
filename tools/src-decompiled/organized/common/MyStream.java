/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.DataInputStream;

class MyStream {
    private DataInputStream DSP;
    private int FFT = 0;
    private byte[] responseView = new byte[8];

    MyStream() {
    }

    public DataInputStream DSP() {
        return this.DSP;
    }

    public void DSP(DataInputStream dataInputStream) {
        this.DSP = dataInputStream;
    }

    public int FFT() {
        return this.FFT;
    }

    public void DSP(int n) {
        this.FFT = n;
    }

    public byte[] responseView() {
        return this.responseView;
    }
}

