/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

public class EntropyPartitionedRiceContents {
    protected int[] DSP;
    protected int[] FFT;
    protected int responseView = 0;

    public void DSP(int n) {
        if (this.responseView >= n) {
            return;
        }
        this.DSP = new int[1 << n];
        this.FFT = new int[1 << n];
        this.responseView = n;
    }
}

