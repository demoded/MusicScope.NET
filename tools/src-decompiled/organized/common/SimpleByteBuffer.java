/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

class SimpleByteBuffer {
    private final byte[] DSP;
    private final int FFT;
    private int responseView;
    private int AdditionalMetadataValue;

    SimpleByteBuffer(int n) {
        this.FFT = n;
        this.DSP = new byte[n];
    }

    int DSP(byte[] byArray, int n, int n2) {
        System.arraycopy(byArray, n, this.DSP, this.responseView, n2);
        this.responseView += n2;
        return this.responseView;
    }

    int FFT(byte[] byArray, int n, int n2) {
        int n3 = Math.min(n2, this.FFT - this.AdditionalMetadataValue);
        System.arraycopy(this.DSP, this.AdditionalMetadataValue, byArray, n, n3);
        this.AdditionalMetadataValue += n3;
        return n3;
    }

    boolean DSP() {
        return this.FFT > this.AdditionalMetadataValue;
    }
}

