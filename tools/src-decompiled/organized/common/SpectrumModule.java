/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.ComputationSubject;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerEventListener;
import sdfgjkljljoftrytrszgijpokjprs.ISpectrumControlLogSwitchListener;
import sdfgjkljljoftrytrszgijpokjprs.AbstractComputationModule;
import sdfgjkljljoftrytrszgijpokjprs.ComputationObjects;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;
import sdfgjkljljoftrytrszgijpokjprs.SpectrumModel;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;
import sdfgjkljljoftrytrszgijpokjprs.ITrackLoadedListener;
import sdfgjkljljoftrytrszgijpokjprs.IAnalyzerStartListener;

@ComputationSubject(DSP=ComputationController.DSP.Spectrum)
public class SpectrumModule
extends AbstractComputationModule<SpectrumModel>
implements IPlayerStateListener,
IPlayerEventListener,
ISpectrumControlLogSwitchListener,
ITrackLoadedListener,
IAnalyzerStartListener {
    private int FFT = 8192;
    private int responseView;
    private final double[] AdditionalMetadataValue = new double[this.FFT];
    private final double[] AudioFileExtension = new double[this.FFT];
    private final double[] IAudioFileCodec = new double[this.FFT];
    private final double[] IAudioInputStream = new double[this.FFT];
    private final double IAudioMetaInformation = Math.pow(10.0, -10.0);
    private double IBaseAudioCodec;
    private int MetaInfomationCopy;
    private SpectrumModel AacAudioCodec;
    private boolean AacMetaDataModel = false;
    private int BufferedAacReader;
    ComputationObjects DSP = this.DSP();

    public SpectrumModule() {
        this.responseView();
    }

    private synchronized void IAudioFileCodec() {
        for (int i = 0; i < this.FFT; ++i) {
            double d = Math.PI * 2 * (double)i / (double)(this.FFT - 1);
            this.AdditionalMetadataValue[i] = 0.27105140069342 - 0.43329793923448 * Math.cos(d) + 0.21812299954311 * Math.cos(2.0 * d) - 0.06592544638803 * Math.cos(3.0 * d) + 0.01081174209837 * Math.cos(4.0 * d) - 7.7658482522E-4 * Math.cos(5.0 * d) + 1.388721735E-5 * Math.cos(6.0 * d);
        }
    }

    public final synchronized void responseView() {
        this.FFT = !this.AacMetaDataModel ? 2048 : 8192;
        this.responseView = (int)(Math.log10(this.FFT) / Math.log10(2.0));
        this.IBaseAudioCodec = this.FFT / 8;
        this.IAudioFileCodec();
        for (int i = 0; i < this.FFT; ++i) {
            this.AudioFileExtension[i] = this.IAudioMetaInformation;
            this.IAudioInputStream[i] = 0.0;
            this.IAudioFileCodec[i] = 0.0;
        }
        this.MetaInfomationCopy = 0;
        this.BufferedAacReader = 0;
    }

    private void DSP(double[] dArray, double[] dArray2, double[] dArray3, double[] dArray4) {
        int n;
        boolean bl = true;
        int n2 = this.FFT;
        int n3 = this.responseView;
        int n4 = n2 >> 1;
        int n5 = 0;
        for (n = 0; n < n2 - 1; ++n) {
            int n6;
            if (n < n5) {
                double d = dArray[n];
                double d2 = dArray2[n];
                dArray[n] = dArray[n5];
                dArray2[n] = dArray2[n5];
                dArray[n5] = d;
                dArray2[n5] = d2;
            }
            for (n6 = n4; n6 <= n5; n5 -= n6, n6 >>= 1) {
            }
            n5 += n6;
        }
        double d = -1.0;
        double d3 = 0.0;
        int n7 = 1;
        for (int i = 0; i < n3; ++i) {
            int n8 = n7;
            n7 <<= 1;
            double d4 = 1.0;
            double d5 = 0.0;
            for (n5 = 0; n5 < n8; ++n5) {
                for (n = n5; n < n2; n += n7) {
                    int n9 = n + n8;
                    double d6 = d4 * dArray[n9] - d5 * dArray2[n9];
                    double d7 = d4 * dArray2[n9] + d5 * dArray[n9];
                    dArray[n9] = dArray[n] - d6;
                    dArray2[n9] = dArray2[n] - d7;
                    int n10 = n;
                    dArray[n10] = dArray[n10] + d6;
                    int n11 = n;
                    dArray2[n11] = dArray2[n11] + d7;
                }
                double d8 = d4 * d - d5 * d3;
                d5 = d4 * d3 + d5 * d;
                d4 = d8;
            }
            d3 = Math.sqrt((1.0 - d) / 2.0);
            if (bl) {
                d3 = -d3;
            }
            d = Math.sqrt((1.0 + d) / 2.0);
        }
        n = 0;
        while ((double)n < (double)this.FFT / 2.0) {
            dArray3[n] = dArray[n] / this.IBaseAudioCodec;
            dArray4[n] = dArray2[n] / this.IBaseAudioCodec;
            ++n;
        }
    }

    public synchronized SpectrumModel AudioFileExtension() throws Exception {
        int n;
        Thread.currentThread().setName(this.getClass().getSimpleName());
        double[] dArray = new double[this.FFT];
        double[] dArray2 = new double[this.FFT];
        double[] dArray3 = new double[this.FFT];
        double[] dArray4 = new double[this.FFT];
        double[] dArray5 = new double[this.FFT];
        double[] dArray6 = new double[this.FFT];
        double[] dArray7 = new double[this.FFT];
        double[] dArray8 = new double[this.FFT];
        AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf = this.DSP.responseView();
        int n2 = zvOTVUaKFTNpNSbbMYZfoXf.AdditionalMetadataValue();
        if (n2 > this.FFT) {
            n2 = this.FFT;
        }
        for (n = 0; n < n2; ++n) {
            this.IAudioFileCodec[this.MetaInfomationCopy] = zvOTVUaKFTNpNSbbMYZfoXf.responseView()[n];
            this.IAudioInputStream[this.MetaInfomationCopy] = zvOTVUaKFTNpNSbbMYZfoXf.FFT()[n];
            ++this.MetaInfomationCopy;
            if (this.MetaInfomationCopy < this.FFT) continue;
            this.MetaInfomationCopy = 0;
        }
        if (this.BufferedAacReader < this.FFT) {
            this.BufferedAacReader += n2;
        } else {
            int n3 = this.MetaInfomationCopy;
            for (n = 0; n < this.FFT; ++n) {
                dArray[n] = this.AdditionalMetadataValue[n] * this.IAudioFileCodec[n3];
                dArray2[n] = 0.0;
                dArray3[n] = this.AdditionalMetadataValue[n] * this.IAudioInputStream[n3];
                dArray4[n] = 0.0;
                if (++n3 < this.FFT) continue;
                n3 = 0;
            }
            this.DSP(dArray, dArray2, dArray5, dArray6);
            this.DSP(dArray3, dArray4, dArray7, dArray8);
            n = 0;
            while ((double)n < (double)this.FFT / 2.0) {
                double d = Math.sqrt(dArray5[n] * dArray5[n] + dArray6[n] * dArray6[n]);
                double d2 = Math.sqrt(dArray7[n] * dArray7[n] + dArray8[n] * dArray8[n]);
                this.AudioFileExtension[n] = (this.AudioFileExtension[n] + (d + d2) / 2.0) / 2.0;
                if (this.AudioFileExtension[n] < this.IAudioMetaInformation) {
                    this.AudioFileExtension[n] = this.IAudioMetaInformation;
                }
                ++n;
            }
        }
        this.AacAudioCodec = new SpectrumModel(this.AudioFileExtension, dArray5, dArray6, dArray7, dArray8);
        return this.AacAudioCodec;
    }

    @Override
    public void AdditionalMetadataValue() {
        this.BufferedAacReader = 0;
        for (int i = 0; i < this.FFT / 2; ++i) {
            this.AudioFileExtension[i] = this.IAudioMetaInformation;
        }
    }

    @Override
    public void DSP(String string) {
        this.responseView();
    }

    @Override
    public void DSP(PlayerState zjoyaRSokkGYDwXHPKTBIiX) {
    }

    @Override
    public boolean DSP(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2, Object object) {
        switch (lXCOGwZXVelwEKHMMPwpSAO2) {
            case AudioFileExtension: {
                this.responseView();
            }
        }
        return true;
    }

    @Override
    public void DSP(boolean bl) {
        this.AacMetaDataModel = bl;
        this.responseView();
    }

    @Override
    public /* synthetic */ Object FFT() throws Exception {
        return this.AudioFileExtension();
    }
}

