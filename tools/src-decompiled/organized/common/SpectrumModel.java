/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

public class SpectrumModel {
    private final double[] DSP;
    private final double[] FFT;
    private final double[] responseView;
    private final double[] AdditionalMetadataValue;
    private final double[] AudioFileExtension;

    public SpectrumModel(double[] dArray, double[] dArray2, double[] dArray3, double[] dArray4, double[] dArray5) {
        this.DSP = dArray;
        this.FFT = dArray2;
        this.responseView = dArray3;
        this.AdditionalMetadataValue = dArray4;
        this.AudioFileExtension = dArray5;
    }

    public SpectrumModel(SpectrumModel hBuuKncbHhHairukyzCONrT2) {
        this(hBuuKncbHhHairukyzCONrT2.DSP(), hBuuKncbHhHairukyzCONrT2.FFT(), hBuuKncbHhHairukyzCONrT2.responseView(), hBuuKncbHhHairukyzCONrT2.AdditionalMetadataValue(), hBuuKncbHhHairukyzCONrT2.AudioFileExtension());
    }

    public double[] DSP() {
        return this.DSP;
    }

    public double[] FFT() {
        return this.FFT;
    }

    public double[] responseView() {
        return this.responseView;
    }

    public double[] AdditionalMetadataValue() {
        return this.AdditionalMetadataValue;
    }

    public double[] AudioFileExtension() {
        return this.AudioFileExtension;
    }
}

