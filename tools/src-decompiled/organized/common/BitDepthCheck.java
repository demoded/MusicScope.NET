/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.FFT;

public class BitDepthCheck {
    private static final int DSP = (int)Math.pow(2.0, 14.0);
    private int FFT = 24;
    private int responseView;
    private double AdditionalMetadataValue = Math.pow(2.0, this.FFT - 1);
    private final double[] AudioFileExtension = new double[DSP];
    private final int[] IAudioFileCodec = new int[16];
    private final double[] IAudioInputStream = new double[16];
    private int IAudioMetaInformation = 1;
    private int IBaseAudioCodec = 1;
    private final long[] MetaInfomationCopy = new long[]{-2L, -4L, -8L, -16L, -32L, -64L, -128L, -256L, -512L, -1024L};
    private int AacAudioCodec = 0;
    private final FFT AacMetaDataModel = new FFT(DSP);
    private final double[][] BufferedAacReader = new double[100][DSP / 2];
    private double AiffAudioCodec;
    private final double[] AiffMetaDataModel = new double[DSP / 2];
    private int AlacAudioCodec = 0;

    public void DSP(int n, int n2) {
        int n3;
        this.responseView = n;
        this.FFT = n2;
        this.AlacAudioCodec = 0;
        this.AdditionalMetadataValue = Math.pow(2.0, n2 - 1);
        this.IBaseAudioCodec = 1;
        this.IAudioMetaInformation = 1;
        for (n3 = 0; n3 < 16; ++n3) {
            this.IAudioFileCodec[n3] = 0;
            this.IAudioInputStream[n3] = 0.0;
        }
        for (n3 = 0; n3 < 100; ++n3) {
            this.AiffMetaDataModel[n3] = 0.0;
        }
        this.AiffAudioCodec = (double)n / (double)DSP;
    }

    public int DSP() {
        return this.AlacAudioCodec;
    }

    private void DSP(double[] dArray, double[] dArray2, long l) {
        for (int i = 0; i < DSP; ++i) {
            long l2 = (long)(dArray[i] * this.AdditionalMetadataValue);
            int n = l2 < 0L ? -1 : 1;
            l2 = Math.abs(l2);
            dArray2[i] = (double)((long)n * (l2 & l)) / this.AdditionalMetadataValue;
        }
    }

    private double DSP(double[] dArray, boolean bl) {
        double d;
        int n;
        double[] dArray2 = new double[DSP];
        double[] dArray3 = new double[DSP];
        double d2 = 0.0;
        double d3 = 0.0;
        double d4 = 0.0;
        System.arraycopy(dArray, 0, dArray2, 0, DSP);
        this.AacMetaDataModel.DSP(1, true, dArray2, dArray3);
        for (n = DSP / 4; n < DSP / 4 + DSP / 8 - 1; ++n) {
            d = Math.sqrt(dArray2[n] * dArray2[n] + dArray3[n] * dArray3[n]);
            d2 += d;
        }
        for (n = DSP / 4 + DSP / 8; n < DSP / 2 - 1; ++n) {
            d = Math.sqrt(dArray2[n] * dArray2[n] + dArray3[n] * dArray3[n]);
            d3 += d;
        }
        if (d2 > d3) {
            ++this.AacAudioCodec;
        }
        ++this.IBaseAudioCodec;
        d4 = d2 + d3;
        return d4;
    }

    public void DSP(double[] dArray) {
        int n;
        double[] dArray2 = new double[16];
        double[] dArray3 = new double[16];
        dArray2[0] = this.DSP(dArray, true);
        for (n = 0; n < 8; ++n) {
            this.DSP(dArray, this.AudioFileExtension, this.MetaInfomationCopy[n]);
            dArray2[n + 1] = this.DSP(this.AudioFileExtension, false);
            dArray3[n] = dArray2[0] - dArray2[n + 1];
            if (dArray3[n] < 0.0) {
                int n2 = n;
                this.IAudioFileCodec[n2] = this.IAudioFileCodec[n2] + 1;
            }
            this.IAudioInputStream[n] = (double)this.IAudioFileCodec[n] / (double)this.IAudioMetaInformation;
        }
        ++this.IAudioMetaInformation;
        this.AlacAudioCodec = 0;
        for (n = 0; n < 8 && this.IAudioInputStream[n] < 0.59; ++n) {
            ++this.AlacAudioCodec;
        }
    }
}

