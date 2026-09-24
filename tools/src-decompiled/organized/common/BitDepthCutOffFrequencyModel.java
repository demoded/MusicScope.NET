/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

public class BitDepthCutOffFrequencyModel {
    private int DSP;
    private int FFT;
    private boolean responseView;

    public BitDepthCutOffFrequencyModel(int n, int n2, boolean bl) {
        this.DSP = n;
        this.FFT = n2;
        this.responseView = bl;
    }

    public BitDepthCutOffFrequencyModel(int n, int n2) {
        this(n, n2, false);
    }

    public BitDepthCutOffFrequencyModel(BitDepthCutOffFrequencyModel huiUBQjuWwwLeKbpxdFZAcJ) {
        this(huiUBQjuWwwLeKbpxdFZAcJ.DSP(), huiUBQjuWwwLeKbpxdFZAcJ.FFT(), huiUBQjuWwwLeKbpxdFZAcJ.responseView());
    }

    public int DSP() {
        return this.DSP;
    }

    public int FFT() {
        return this.FFT;
    }

    public boolean responseView() {
        return this.responseView;
    }
}

