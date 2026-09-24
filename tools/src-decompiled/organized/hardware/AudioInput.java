/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.IWritableAudioOutput;
import sdfgjkljljoftrytrszgijpokjprs.Display;
import sdfgjkljljoftrytrszgijpokjprs.UHRSpectrumAnalyzer;
import sdfgjkljljoftrytrszgijpokjprs.FFT;
import sdfgjkljljoftrytrszgijpokjprs.FFTConvolver;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;
import sdfgjkljljoftrytrszgijpokjprs.IWritableAudioPlugin;
import sdfgjkljljoftrytrszgijpokjprs.AutomaticGainControl;

public class AudioInput
implements Runnable,
IWritableAudioPlugin {
    private final double DSP = Math.pow(10.0, -10.0);
    private final int FFT = 2048;
    private int responseView = 0;
    private int AdditionalMetadataValue = 1;
    private int AudioFileExtension;
    private boolean IAudioFileCodec = false;
    private double[] IAudioInputStream;
    private double[] IAudioMetaInformation;
    private double[] IBaseAudioCodec;
    private double[] MetaInfomationCopy;
    private double[] AacAudioCodec;
    private double[] AacMetaDataModel;
    private double[] BufferedAacReader;
    private double[] AiffAudioCodec;
    private double[] AiffMetaDataModel;
    private double[] AlacAudioCodec;
    private double[] AlacMetaDataModel;
    private double[] BufferedAlacReader;
    private double[] AlacContextModel;
    private IWritableAudioOutput AlacDecoderUtils;
    private FFTConvolver AlacFile;
    private FFTConvolver AlacInputStream;
    private Display AlacUtils;
    private FFT ChunkInfo;
    private final AutomaticGainControl DemuxResT = new AutomaticGainControl();
    private UHRSpectrumAnalyzer DemuxUtils;

    public void DSP(UHRSpectrumAnalyzer pumrgUjzIBRhWRYVWFJFxPn) {
        this.DemuxUtils = pumrgUjzIBRhWRYVWFJFxPn;
    }

    public void DSP(int n) {
        this.AudioFileExtension = n;
        this.IAudioInputStream = new double[n];
        this.IAudioMetaInformation = new double[n];
        this.IBaseAudioCodec = new double[n];
        this.MetaInfomationCopy = new double[n];
        this.AacAudioCodec = new double[n];
        this.AacMetaDataModel = new double[n];
        this.BufferedAacReader = new double[n];
        this.AiffAudioCodec = new double[n];
        this.AiffMetaDataModel = new double[n];
        this.AlacAudioCodec = new double[n];
        this.AlacMetaDataModel = new double[n];
        this.BufferedAlacReader = new double[n];
        this.AlacContextModel = new double[n / 2];
        this.ChunkInfo = new FFT(n);
        this.DemuxResT.DSP(this);
        this.responseView = 0;
    }

    public void FFT(int n) {
        this.DemuxResT.DSP(n, 1.0);
        if (this.AlacFile != null) {
            this.AlacFile.DSP(n);
            this.AlacInputStream.DSP(n);
        }
    }

    public void DSP(boolean bl) {
        this.DemuxUtils.DSP(bl);
    }

    public void DSP(IWritableAudioOutput dYjJAEaHDOOMdcovALSqoto) {
        this.AlacDecoderUtils = dYjJAEaHDOOMdcovALSqoto;
    }

    public void DSP(double d) {
        this.DemuxResT.DSP(Math.pow(10.0, d / 20.0));
    }

    public void DSP(FFTConvolver ylmiLcPXZRgdvOrDbrHLHsZ, FFTConvolver ylmiLcPXZRgdvOrDbrHLHsZ2) {
        this.AlacFile = ylmiLcPXZRgdvOrDbrHLHsZ;
        this.AlacInputStream = ylmiLcPXZRgdvOrDbrHLHsZ2;
    }

    public void DSP(Display gzwPWPrZDOEHewlFodtbKbX) {
        this.AlacUtils = gzwPWPrZDOEHewlFodtbKbX;
    }

    public void FFT(boolean bl) {
        this.IAudioFileCodec = bl;
    }

    public void responseView(int n) {
        this.AdditionalMetadataValue = n;
    }

    private void DSP(double[] dArray, double[] dArray2, double[] dArray3, double[] dArray4) {
        for (int i = 0; i < this.AudioFileExtension / 2; ++i) {
            double d = (Math.sqrt(dArray[i] * dArray[i] + dArray2[i] * dArray2[i]) + Math.sqrt(dArray3[i] * dArray3[i] + dArray4[i] * dArray4[i])) / 2.0;
            this.AlacContextModel[i] = d > this.DSP ? 20.0 * Math.log10(d) : -200.0;
        }
        this.AlacUtils.DSP(this.AlacContextModel);
    }

    public void DSP(int n, double[] dArray, double[] dArray2, double[] dArray3, double[] dArray4) {
        if (n == 0) {
            System.arraycopy(dArray, 0, this.AlacAudioCodec, 0, this.AudioFileExtension);
            System.arraycopy(dArray2, 0, this.AiffMetaDataModel, 0, this.AudioFileExtension);
            System.arraycopy(dArray3, 0, this.BufferedAlacReader, 0, this.AudioFileExtension);
            System.arraycopy(dArray4, 0, this.AlacMetaDataModel, 0, this.AudioFileExtension);
            this.ChunkInfo.DSP(1, true, this.AlacAudioCodec, this.AiffMetaDataModel);
            this.ChunkInfo.DSP(1, true, this.BufferedAlacReader, this.AlacMetaDataModel);
            this.DSP(this.AlacAudioCodec, this.AiffMetaDataModel, this.BufferedAlacReader, this.AlacMetaDataModel);
        } else {
            int n2 = n;
            for (int i = 0; i < this.AudioFileExtension; ++i) {
                this.AlacAudioCodec[i] = dArray[n2];
                this.AiffMetaDataModel[i] = dArray2[n2];
                this.BufferedAlacReader[i] = dArray3[n2];
                this.AlacMetaDataModel[i] = dArray4[n2];
                if (++n2 < this.AudioFileExtension) continue;
                n2 = 0;
            }
            this.ChunkInfo.DSP(1, true, this.AlacAudioCodec, this.AiffMetaDataModel);
            this.ChunkInfo.DSP(1, true, this.BufferedAlacReader, this.AlacMetaDataModel);
            this.DSP(this.AlacAudioCodec, this.AiffMetaDataModel, this.BufferedAlacReader, this.AlacMetaDataModel);
        }
    }

    public AudioSampleModel DSP(AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf) {
        int n = zvOTVUaKFTNpNSbbMYZfoXf.AdditionalMetadataValue();
        for (int i = 0; i < n; ++i) {
            this.AacMetaDataModel[i] = zvOTVUaKFTNpNSbbMYZfoXf.responseView()[i];
            this.AiffAudioCodec[i] = zvOTVUaKFTNpNSbbMYZfoXf.FFT()[i];
            this.AacAudioCodec[i] = 0.0;
            this.BufferedAacReader[i] = 0.0;
            this.IAudioMetaInformation[this.responseView] = this.AacMetaDataModel[i];
            this.MetaInfomationCopy[this.responseView] = this.AiffAudioCodec[i];
            this.IAudioInputStream[this.responseView] = 0.0;
            this.IBaseAudioCodec[this.responseView] = 0.0;
            ++this.responseView;
            if (this.responseView == this.AudioFileExtension) {
                this.responseView = 0;
            }
            if (this.responseView == 0 && this.AdditionalMetadataValue == 1) {
                this.DSP(this.responseView, this.IAudioMetaInformation, this.IAudioInputStream, this.MetaInfomationCopy, this.IBaseAudioCodec);
                continue;
            }
            if ((this.responseView == 0 || this.responseView == this.AudioFileExtension / 2) && this.AdditionalMetadataValue == 2) {
                this.DSP(this.responseView, this.IAudioMetaInformation, this.IAudioInputStream, this.MetaInfomationCopy, this.IBaseAudioCodec);
                continue;
            }
            if (this.responseView != 0 && this.responseView != this.AudioFileExtension / 4 && this.responseView != this.AudioFileExtension / 2 && this.responseView != 3 * this.AudioFileExtension / 4 || this.AdditionalMetadataValue != 4) continue;
            this.DSP(this.responseView, this.IAudioMetaInformation, this.IAudioInputStream, this.MetaInfomationCopy, this.IBaseAudioCodec);
        }
        if (this.IAudioFileCodec) {
            this.AlacFile.DSP(2048, this.AacMetaDataModel, this.AacAudioCodec);
            this.AlacInputStream.DSP(2048, this.AiffAudioCodec, this.BufferedAacReader);
        }
        this.DemuxResT.DSP(2048, this.AacMetaDataModel, this.AiffAudioCodec);
        return new AudioSampleModel(this.AacMetaDataModel, this.AiffAudioCodec, 2048);
    }

    @Override
    public void run() {
    }

    public /* synthetic */ Object FFT(AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf) {
        return this.DSP(zvOTVUaKFTNpNSbbMYZfoXf);
    }
}

