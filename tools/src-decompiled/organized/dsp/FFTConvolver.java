/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.FFT;

public class FFTConvolver {
    private final int responseView;
    private int AdditionalMetadataValue = 48000;
    private double AudioFileExtension = this.AdditionalMetadataValue / 4;
    private double IAudioFileCodec = this.AdditionalMetadataValue / 2;
    private double IAudioInputStream = 1.0;
    private int IAudioMetaInformation;
    private int IBaseAudioCodec;
    private int MetaInfomationCopy;
    private final double[] AacAudioCodec;
    private final double[] AacMetaDataModel;
    private final double[] BufferedAacReader;
    private final double[] AiffAudioCodec;
    private final double[] AiffMetaDataModel;
    private final double[] AlacAudioCodec;
    private int AlacMetaDataModel;
    private int BufferedAlacReader;
    private int AlacContextModel;
    private int AlacDecoderUtils;
    private final int AlacFile;
    private final double[] AlacInputStream;
    private final double[] AlacUtils;
    private double[] ChunkInfo;
    private final int DemuxResT;
    private final double DemuxUtils = Math.pow(10.0, -10.0);
    private boolean LeadingZeros;
    private final double MyStream = 2.0;
    double DSP = 0.0;
    double FFT = 1.0;
    private final FFT QTMovieT;
    private final FFT SampleDuration;

    public FFTConvolver(int n) {
        this.responseView = n;
        this.QTMovieT = new FFT(n);
        this.SampleDuration = new FFT(n);
        this.AlacInputStream = new double[n];
        this.AlacUtils = new double[n];
        this.AlacFile = n;
        this.DemuxResT = n / 2;
        this.AacAudioCodec = new double[this.AlacFile];
        this.AacMetaDataModel = new double[this.AlacFile];
        this.BufferedAacReader = new double[this.AlacFile];
        this.AiffAudioCodec = new double[this.AlacFile];
        this.AiffMetaDataModel = new double[this.AlacFile];
        this.AlacAudioCodec = new double[this.AlacFile];
        this.AlacMetaDataModel = 0;
        this.BufferedAlacReader = this.AlacFile / 2;
        this.AlacContextModel = 0;
        this.AlacDecoderUtils = 0;
        this.LeadingZeros = false;
        this.FFT();
        this.DSP();
    }

    private void DSP() {
        this.DSP(this.AudioFileExtension);
    }

    public void DSP(int n) {
        this.AdditionalMetadataValue = n;
        this.IAudioInputStream = (double)n / (double)this.responseView;
    }

    public synchronized void DSP(double d) {
        this.AudioFileExtension = d;
        this.MetaInfomationCopy = (int)(d / this.IAudioInputStream);
        if (this.MetaInfomationCopy < 0) {
            this.MetaInfomationCopy = 0;
        } else if (this.MetaInfomationCopy > this.responseView - 1) {
            this.MetaInfomationCopy = this.responseView - 1;
        }
        this.FFT(this.IAudioFileCodec);
    }

    public synchronized void FFT(double d) {
        this.IAudioFileCodec = d;
        this.IAudioMetaInformation = (int)((this.AudioFileExtension - d / 2.0) / this.IAudioInputStream);
        this.IBaseAudioCodec = (int)((this.AudioFileExtension + d / 2.0) / this.IAudioInputStream);
        if (this.IAudioMetaInformation >= this.IBaseAudioCodec) {
            this.IAudioMetaInformation = this.IBaseAudioCodec - 1;
        }
        if (this.IAudioMetaInformation < 1) {
            this.IAudioMetaInformation = 1;
        } else if (this.IAudioMetaInformation > this.responseView - 2 - 2) {
            this.IAudioMetaInformation = this.responseView - 2 - 2;
        }
        if (this.IBaseAudioCodec < 2) {
            this.IBaseAudioCodec = 2;
        } else if (this.IBaseAudioCodec > this.responseView / 2 - 1) {
            this.IBaseAudioCodec = this.responseView / 2 - 1;
        }
        this.responseView();
    }

    public void DSP(boolean bl) {
        this.LeadingZeros = bl;
    }

    private void FFT() {
        this.ChunkInfo = new double[this.DemuxResT];
        for (int i = 0; i < this.DemuxResT; ++i) {
            double d = Math.PI * 2 * (double)i / (double)(this.DemuxResT - 1);
            this.ChunkInfo[i] = 0.27105140069342 - 0.43329793923448 * Math.cos(d) + 0.21812299954311 * Math.cos(2.0 * d) - 0.06592544638803 * Math.cos(3.0 * d) + 0.01081174209837 * Math.cos(4.0 * d) - 7.7658482522E-4 * Math.cos(5.0 * d) + 1.388721735E-5 * Math.cos(6.0 * d);
        }
    }

    private void DSP(double[] dArray, double[] dArray2) {
        int n;
        for (n = 0; n < this.DemuxResT; ++n) {
            dArray[n + this.responseView / 4] = 2.0 * this.ChunkInfo[n] * dArray[n + this.responseView / 4];
            dArray2[n + this.responseView / 4] = 2.0 * this.ChunkInfo[n] * dArray2[n + this.responseView / 4];
        }
        for (n = 0; n < this.responseView / 4; ++n) {
            dArray[n] = 0.0;
            dArray[this.responseView - n - 1] = 0.0;
            dArray2[n] = 0.0;
            dArray2[this.responseView - n - 1] = 0.0;
        }
    }

    public void DSP(int n, double[] dArray, double[] dArray2) {
        int n2;
        for (n2 = 0; n2 < n; ++n2) {
            this.AacAudioCodec[this.AlacMetaDataModel] = dArray[n2];
            this.AacMetaDataModel[this.AlacMetaDataModel] = dArray2[n2];
            ++this.AlacMetaDataModel;
            if (this.AlacMetaDataModel == this.AlacFile) {
                this.AlacMetaDataModel = 0;
            }
            ++this.AlacDecoderUtils;
        }
        if (this.AlacDecoderUtils == this.responseView / 2) {
            this.AlacDecoderUtils = 0;
            int n3 = this.AlacMetaDataModel;
            for (n2 = 0; n2 < this.responseView; ++n2) {
                this.AiffMetaDataModel[n2] = this.AacAudioCodec[n3];
                this.AlacAudioCodec[n2] = this.AacMetaDataModel[n3];
                if (++n3 != this.AlacFile) continue;
                n3 = 0;
            }
            this.QTMovieT.DSP(1, false, this.AiffMetaDataModel, this.AlacAudioCodec);
            this.FFT(this.AiffMetaDataModel, this.AlacAudioCodec);
            if (this.LeadingZeros) {
                this.responseView(this.AiffMetaDataModel, this.AlacAudioCodec);
            }
            this.QTMovieT.DSP(-1, false, this.AiffMetaDataModel, this.AlacAudioCodec);
            for (n2 = this.responseView / 4; n2 < 3 * this.responseView / 4; ++n2) {
                this.BufferedAacReader[this.BufferedAlacReader] = this.AiffMetaDataModel[n2];
                this.AiffAudioCodec[this.BufferedAlacReader] = this.AlacAudioCodec[n2];
                ++this.BufferedAlacReader;
                if (this.BufferedAlacReader != this.AlacFile) continue;
                this.BufferedAlacReader = 0;
            }
        }
        for (n2 = 0; n2 < n; ++n2) {
            dArray[n2] = this.BufferedAacReader[this.AlacContextModel];
            dArray2[n2] = this.AiffAudioCodec[this.AlacContextModel];
            ++this.AlacContextModel;
            if (this.AlacContextModel != this.AlacFile) continue;
            this.AlacContextModel = 0;
        }
    }

    private synchronized void FFT(double[] dArray, double[] dArray2) {
        for (int i = 0; i < this.responseView; ++i) {
            dArray[i] = this.AlacInputStream[i] * dArray[i];
            dArray2[i] = this.AlacUtils[i] * dArray2[i];
        }
    }

    private void responseView(double[] dArray, double[] dArray2) {
        if (this.IAudioMetaInformation % 2 == 0) {
            this.IAudioMetaInformation = this.IAudioMetaInformation > 1 ? --this.IAudioMetaInformation : ++this.IAudioMetaInformation;
        }
        double[] dArray3 = new double[this.responseView];
        double[] dArray4 = new double[this.responseView];
        int n = this.responseView / 2 - 1 - this.IAudioMetaInformation;
        System.arraycopy(dArray, this.IAudioMetaInformation, dArray3, 1, n);
        System.arraycopy(dArray2, this.IAudioMetaInformation, dArray4, 1, n);
        System.arraycopy(dArray, this.responseView / 2 + 1, dArray3, this.responseView - 1 - n, n);
        System.arraycopy(dArray2, this.responseView / 2 + 1, dArray4, this.responseView - 1 - n, n);
        System.arraycopy(dArray3, 0, dArray, 0, this.responseView);
        System.arraycopy(dArray4, 0, dArray2, 0, this.responseView);
    }

    private void AdditionalMetadataValue(double[] dArray, double[] dArray2) {
        int n;
        double[] dArray3 = new double[this.responseView];
        double[] dArray4 = new double[this.responseView];
        System.arraycopy(dArray, 0, dArray3, 0, this.responseView);
        System.arraycopy(dArray2, 0, dArray4, 0, this.responseView);
        for (n = 0; n < this.responseView / 2; ++n) {
            dArray[n] = dArray3[this.responseView / 2 - 1 - n];
            dArray2[n] = dArray4[this.responseView / 2 - 1 - n];
        }
        int n2 = 1;
        for (n = this.responseView / 2; n < this.responseView; ++n) {
            dArray[n] = dArray3[this.responseView - n2];
            dArray2[n] = dArray4[this.responseView - n2];
            ++n2;
        }
    }

    private void AudioFileExtension(double[] dArray, double[] dArray2) {
        this.SampleDuration.DSP(-1, false, dArray, dArray2);
        this.AdditionalMetadataValue(dArray, dArray2);
        this.DSP(dArray, dArray2);
        this.AdditionalMetadataValue(dArray, dArray2);
        this.SampleDuration.DSP(1, false, dArray, dArray2);
    }

    private void responseView() {
        int n;
        double d = 0.49;
        for (n = 1; n < this.IAudioMetaInformation; ++n) {
            this.AlacInputStream[n] = this.DemuxUtils;
            this.AlacInputStream[this.responseView - n] = this.DemuxUtils;
            this.AlacUtils[n] = this.DemuxUtils;
            this.AlacUtils[this.responseView - n] = this.DemuxUtils;
        }
        for (n = this.IAudioMetaInformation; n < this.IBaseAudioCodec; ++n) {
            this.AlacInputStream[n] = d;
            this.AlacInputStream[this.responseView - n] = d;
            this.AlacUtils[n] = d;
            this.AlacUtils[this.responseView - n] = d;
        }
        for (n = this.IBaseAudioCodec; n < this.responseView / 2; ++n) {
            this.AlacInputStream[n] = this.DemuxUtils;
            this.AlacInputStream[this.responseView - n] = this.DemuxUtils;
            this.AlacUtils[n] = this.DemuxUtils;
            this.AlacUtils[this.responseView - n] = this.DemuxUtils;
        }
        this.AlacInputStream[0] = this.AlacUtils[0] = this.DemuxUtils;
        double d2 = this.DemuxUtils;
        this.AlacUtils[this.responseView / 2] = d2;
        this.AlacInputStream[this.responseView / 2] = d2;
        this.AudioFileExtension(this.AlacInputStream, this.AlacUtils);
    }
}

