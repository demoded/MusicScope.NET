/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.EntropyPartitionedRiceContents;

public class ChannelData {
    private int[] DSP;
    private int[] FFT;
    private EntropyPartitionedRiceContents responseView;

    public ChannelData(int n) {
        this.DSP = new int[n];
        this.FFT = new int[n];
        this.responseView = new EntropyPartitionedRiceContents();
    }

    public int[] DSP() {
        return this.DSP;
    }

    public EntropyPartitionedRiceContents FFT() {
        return this.responseView;
    }

    public int[] responseView() {
        return this.FFT;
    }
}

