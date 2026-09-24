/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

public class StereoMeterModel {
    private final int DSP;
    private final double[] FFT;
    private final double[] responseView;
    private final double[] AdditionalMetadataValue;
    private final double AudioFileExtension;
    private final double[] IAudioFileCodec;

    public StereoMeterModel(int n, double[] dArray, double[] dArray2, double[] dArray3, double d, double[] dArray4) {
        this.DSP = n;
        this.FFT = dArray;
        this.responseView = dArray2;
        this.AdditionalMetadataValue = dArray3;
        this.AudioFileExtension = d;
        this.IAudioFileCodec = dArray4;
    }

    public StereoMeterModel(StereoMeterModel fLTjkCbqdFcYQIXMwzSzQor) {
        this(fLTjkCbqdFcYQIXMwzSzQor.DSP(), fLTjkCbqdFcYQIXMwzSzQor.FFT(), fLTjkCbqdFcYQIXMwzSzQor.responseView(), fLTjkCbqdFcYQIXMwzSzQor.AdditionalMetadataValue(), fLTjkCbqdFcYQIXMwzSzQor.AudioFileExtension(), fLTjkCbqdFcYQIXMwzSzQor.IAudioFileCodec());
    }

    public int DSP() {
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

    public double AudioFileExtension() {
        return this.AudioFileExtension;
    }

    public double[] IAudioFileCodec() {
        return this.IAudioFileCodec;
    }
}

