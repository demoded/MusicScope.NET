/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.Arrays;
import sdfgjkljljoftrytrszgijpokjprs.FFT;

public class FrequencyRangeCheck {
    private static final int DSP = (int)Math.pow(2.0, 9.0);
    private int FFT = 24;
    private int responseView = 44100;
    private double AdditionalMetadataValue = Math.pow(2.0, this.FFT - 1);
    private FFT AudioFileExtension;
    private double IAudioFileCodec;
    private final double[][] IAudioInputStream = new double[20000][DSP / 2];
    private final double[] IAudioMetaInformation = new double[DSP];
    private final int[] IBaseAudioCodec = new int[DSP / 2];
    private final int[] MetaInfomationCopy = new int[DSP / 2];
    private final int[] AacAudioCodec = new int[DSP / 2];
    private final int[] AacMetaDataModel = new int[DSP / 2];
    private final int[] BufferedAacReader = new int[DSP / 2];
    private final double[] AiffAudioCodec = new double[DSP / 2];
    private final double[] AiffMetaDataModel = new double[DSP / 2];
    private final double[] AlacAudioCodec = new double[DSP / 2];
    private final double[] AlacMetaDataModel = new double[DSP / 2];
    private int BufferedAlacReader;
    private int AlacContextModel;
    private int AlacDecoderUtils;
    private int AlacFile;
    private int AlacInputStream;

    public void DSP(int n, int n2) {
        this.responseView = n;
        this.FFT = n2;
        this.AdditionalMetadataValue = Math.pow(2.0, n2 - 1);
        this.IAudioFileCodec = (double)n / (double)DSP;
        this.BufferedAlacReader = 0;
        this.AlacInputStream = 10 * n / DSP;
        this.AlacContextModel = n / 2;
        this.AudioFileExtension = new FFT(DSP);
        this.AlacFile = 0;
        this.AlacDecoderUtils = 0;
        for (int i = 0; i < DSP / 2; ++i) {
            this.AlacAudioCodec[i] = 1.0;
        }
        this.FFT();
    }

    public int DSP() {
        return this.BufferedAlacReader;
    }

    public void FFT() {
        for (int i = 0; i < 256; ++i) {
            this.AiffAudioCodec[i] = 0.0;
            this.AiffMetaDataModel[i] = 0.0;
            this.IBaseAudioCodec[i] = 0;
            this.AacAudioCodec[i] = 0;
            this.BufferedAacReader[i] = 0;
            this.AacMetaDataModel[i] = 0;
            this.MetaInfomationCopy[i] = 0;
            for (int j = 0; j < 20000; ++j) {
                this.AlacAudioCodec[i] = 1.0;
                this.AlacMetaDataModel[i] = 0.0;
            }
        }
        this.AlacDecoderUtils = 0;
    }

    private void responseView() {
        int n;
        int n2;
        int[][] nArray = new int[8][256];
        int[] nArray2 = new int[256];
        for (n2 = 0; n2 < 256; ++n2) {
            for (n = 0; n < 20000; ++n) {
                this.AiffAudioCodec[n2] = this.AiffAudioCodec[n2] + this.IAudioInputStream[n][n2];
                if (this.AlacAudioCodec[n2] > this.IAudioInputStream[n][n2]) {
                    this.AlacAudioCodec[n2] = this.IAudioInputStream[n][n2];
                }
                if (!(this.AlacMetaDataModel[n2] < this.IAudioInputStream[n][n2])) continue;
                this.AlacMetaDataModel[n2] = this.IAudioInputStream[n][n2];
            }
            this.AiffAudioCodec[n2] = this.AiffAudioCodec[n2] / 20000.0;
        }
        for (n2 = 0; n2 < 256; ++n2) {
            for (n = 0; n < 20000; ++n) {
                this.IAudioInputStream[n][n2] = this.IAudioInputStream[n][n2] >= this.AiffAudioCodec[n2] ? 1.0 : 0.0;
            }
        }
        for (n2 = 0; n2 < 256; ++n2) {
            for (n = 0; n < 20000; ++n) {
                if (this.IAudioInputStream[n][n2] != 1.0) continue;
                int n3 = n2;
                this.IBaseAudioCodec[n3] = this.IBaseAudioCodec[n3] + 1;
            }
        }
        for (n2 = 0; n2 < 256; ++n2) {
            for (n = 0; n < 20000; ++n) {
                if (this.IAudioInputStream[n][n2] == (double)this.BufferedAacReader[n2]) {
                    int n4 = n2;
                    nArray2[n4] = nArray2[n4] + 1;
                    continue;
                }
                if (nArray2[n2] < 6) {
                    int[] nArray3 = nArray[nArray2[n2]];
                    int n5 = n2;
                    nArray3[n5] = nArray3[n5] + 1;
                } else {
                    int[] nArray4 = nArray[6];
                    int n6 = n2;
                    nArray4[n6] = nArray4[n6] + 1;
                }
                this.BufferedAacReader[n2] = (int)this.IAudioInputStream[n][n2];
                nArray2[n2] = 0;
            }
        }
        for (n2 = 0; n2 < 256; ++n2) {
            if (this.IBaseAudioCodec[n2] <= 9050 || this.IBaseAudioCodec[n2] >= 10346) continue;
            int n7 = n2;
            this.MetaInfomationCopy[n7] = this.MetaInfomationCopy[n7] + 1;
            this.AacAudioCodec[n2] = 1;
        }
        for (n2 = 0; n2 < 256; ++n2) {
            if (nArray[1][n2] < 2267 || nArray[1][n2] > 2733 || nArray[2][n2] < 1079 || nArray[2][n2] > 1421 || nArray[3][n2] < 502 || nArray[3][n2] > 748 || nArray[4][n2] < 223 || nArray[4][n2] > 402 || nArray[5][n2] < 90 || nArray[5][n2] > 223 || nArray[6][n2] < 90 || nArray[6][n2] > 223) continue;
            int n8 = n2;
            this.MetaInfomationCopy[n8] = this.MetaInfomationCopy[n8] + 1;
            this.AacMetaDataModel[n2] = 1;
        }
        for (n2 = 0; n2 < 256; ++n2) {
            if (this.MetaInfomationCopy[n2] != 2) continue;
            this.AlacContextModel = (int)((double)n2 * this.IAudioFileCodec);
            if (this.AlacContextModel <= this.BufferedAlacReader) break;
            this.BufferedAlacReader = this.AlacContextModel;
            break;
        }
    }

    public void DSP(double[] dArray) {
        if (this.AlacDecoderUtils < 20000 && this.AlacFile > this.AlacInputStream) {
            Arrays.fill(this.IAudioMetaInformation, 0, DSP, 0.0);
            this.AudioFileExtension.DSP(1, true, dArray, this.IAudioMetaInformation);
            for (int i = 0; i < 256; ++i) {
                this.IAudioInputStream[this.AlacDecoderUtils][i] = Math.sqrt(dArray[i] * dArray[i] + this.IAudioMetaInformation[i] * this.IAudioMetaInformation[i]);
            }
            ++this.AlacDecoderUtils;
        } else if (this.AlacDecoderUtils >= 20000) {
            this.responseView();
            this.FFT();
        }
        ++this.AlacFile;
    }
}

