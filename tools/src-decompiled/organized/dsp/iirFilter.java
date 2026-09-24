/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

public class iirFilter {
    double[][] DSP = new double[64][8];
    double[][] FFT = new double[64][8];
    double[][] responseView = new double[64][8];

    public iirFilter(float f) {
        this.DSP(f);
    }

    private void DSP(float f) {
        double d = 1.0 / Math.sqrt(2.0);
        int n = 1;
        double d2 = 1500.0;
        double d3 = 4.0;
        double d4 = Math.pow(10.0, d3 / 40.0);
        double d5 = Math.PI * 2 * d2 / (double)f;
        double d6 = Math.sin(d5) / (2.0 * d);
        double d7 = d4 * (d4 + 1.0 + (d4 - 1.0) * Math.cos(d5) + 2.0 * Math.sqrt(d4) * d6);
        double d8 = -2.0 * d4 * (d4 - 1.0 + (d4 + 1.0) * Math.cos(d5));
        double d9 = d4 * (d4 + 1.0 + (d4 - 1.0) * Math.cos(d5) - 2.0 * Math.sqrt(d4) * d6);
        double d10 = d4 + 1.0 - (d4 - 1.0) * Math.cos(d5) + 2.0 * Math.sqrt(d4) * d6;
        double d11 = 2.0 * (d4 - 1.0 - (d4 + 1.0) * Math.cos(d5));
        double d12 = d4 + 1.0 - (d4 - 1.0) * Math.cos(d5) - 2.0 * Math.sqrt(d4) * d6;
        double d13 = d7 / d10;
        this.DSP[n][1] = d13;
        this.DSP[n + 1][1] = d13;
        double d14 = d8 / d10;
        this.DSP[n][2] = d14;
        this.DSP[n + 1][2] = d14;
        double d15 = d9 / d10;
        this.DSP[n][3] = d15;
        this.DSP[n + 1][3] = d15;
        double d16 = d11 / d10;
        this.DSP[n][4] = d16;
        this.DSP[n + 1][4] = d16;
        double d17 = d12 / d10;
        this.DSP[n][5] = d17;
        this.DSP[n + 1][5] = d17;
        d = 0.6;
        n = 3;
        d2 = 50.0;
        d5 = Math.PI * 2 * d2 / (double)f;
        d6 = Math.sin(d5) / (2.0 * d);
        d7 = (1.0 + Math.cos(d5)) / 2.0;
        d8 = -(1.0 + Math.cos(d5));
        d9 = (1.0 + Math.cos(d5)) / 2.0;
        d10 = 1.0 + d6;
        d11 = -2.0 * Math.cos(d5);
        d12 = 1.0 - d6;
        double d18 = d7 / d10;
        this.DSP[n][1] = d18;
        this.DSP[n + 1][1] = d18;
        double d19 = d8 / d10;
        this.DSP[n][2] = d19;
        this.DSP[n + 1][2] = d19;
        double d20 = d9 / d10;
        this.DSP[n][3] = d20;
        this.DSP[n + 1][3] = d20;
        double d21 = d11 / d10;
        this.DSP[n][4] = d21;
        this.DSP[n + 1][4] = d21;
        double d22 = d12 / d10;
        this.DSP[n][5] = d22;
        this.DSP[n + 1][5] = d22;
    }

    public void DSP(int n, double[] dArray, double[] dArray2, int n2) {
        for (int i = 0; i < n2; ++i) {
            this.FFT[n][0] = this.FFT[n][1];
            this.FFT[n][1] = this.FFT[n][2];
            this.FFT[n][2] = dArray[i];
            this.responseView[n][0] = this.responseView[n][1];
            this.responseView[n][1] = this.responseView[n][2];
            this.responseView[n][2] = this.DSP[n][1] * this.FFT[n][2] + this.DSP[n][2] * this.FFT[n][1] + this.DSP[n][3] * this.FFT[n][0] - this.DSP[n][4] * this.responseView[n][1] - this.DSP[n][5] * this.responseView[n][0];
            dArray2[i] = this.responseView[n][2];
        }
    }
}

