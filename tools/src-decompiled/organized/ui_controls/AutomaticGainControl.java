/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.AudioInput;

public class AutomaticGainControl {
    private int DSP = 48000;
    private double FFT = 1.0 / ((double)this.DSP * 1.0);
    private double responseView = 1.0;
    private double AdditionalMetadataValue = 1.0;
    private boolean AudioFileExtension = false;
    private AudioInput IAudioFileCodec;

    public void DSP(AudioInput xsszcpBmUzsbpEmZGaMAfXJ) {
        this.IAudioFileCodec = xsszcpBmUzsbpEmZGaMAfXJ;
    }

    public void DSP(int n, double d) {
        this.DSP = n;
        this.FFT = 1.0 / ((double)n * d);
    }

    public void DSP(double d) {
        this.responseView = d;
    }

    public void DSP(int n, double[] dArray, double[] dArray2) {
        for (int i = 0; i < n; ++i) {
            double d;
            double d2 = this.responseView * dArray[i];
            double d3 = this.responseView * dArray2[i];
            double d4 = Math.abs(d2);
            if (d4 < (d = Math.abs(d3))) {
                d4 = d;
            }
            if (d4 > 1.0) {
                if ((d4 = 1.0 / d4) < this.AdditionalMetadataValue) {
                    this.AdditionalMetadataValue = d4;
                    if (!this.AudioFileExtension) {
                        this.AudioFileExtension = true;
                        this.IAudioFileCodec.DSP(this.AudioFileExtension);
                    }
                }
            } else {
                this.AdditionalMetadataValue += this.FFT;
                if (this.AdditionalMetadataValue > 1.0) {
                    this.AdditionalMetadataValue = 1.0;
                    if (this.AudioFileExtension) {
                        this.AudioFileExtension = false;
                        this.IAudioFileCodec.DSP(this.AudioFileExtension);
                    }
                }
            }
            dArray[i] = this.AdditionalMetadataValue * d2 * 0.999;
            dArray2[i] = this.AdditionalMetadataValue * d3 * 0.999;
        }
    }
}

