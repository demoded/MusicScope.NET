/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.Arrays;

public class AudioSampleModel {
    private final byte[] DSP;
    private final double[] FFT;
    private final double[] responseView;

    public AudioSampleModel(double[] dArray, double[] dArray2, int n) {
        this(null, Arrays.copyOfRange(dArray2, 0, n), Arrays.copyOfRange(dArray, 0, n));
    }

    public AudioSampleModel(byte[] byArray, double[] dArray, double[] dArray2) {
        this.DSP = byArray;
        this.FFT = dArray2;
        this.responseView = dArray;
    }

    public byte[] DSP() {
        return this.DSP;
    }

    public double[] FFT() {
        return this.FFT;
    }

    public double[] responseView() {
        return this.responseView;
    }

    public int AdditionalMetadataValue() {
        return this.FFT.length == this.responseView.length ? this.FFT.length : 0;
    }

    public int AudioFileExtension() {
        return this.DSP.length;
    }
}

