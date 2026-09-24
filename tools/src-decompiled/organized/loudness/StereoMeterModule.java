/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.ComputationSubject;
import sdfgjkljljoftrytrszgijpokjprs.StereoMeterModel;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerEventListener;
import sdfgjkljljoftrytrszgijpokjprs.AbstractComputationModule;
import sdfgjkljljoftrytrszgijpokjprs.ComputationObjects;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;
import sdfgjkljljoftrytrszgijpokjprs.ITrackLoadedListener;
import sdfgjkljljoftrytrszgijpokjprs.IAnalyzerStartListener;

@ComputationSubject(DSP=ComputationController.DSP.StereoMeter)
public class StereoMeterModule
extends AbstractComputationModule<StereoMeterModel>
implements IPlayerStateListener,
IPlayerEventListener,
ITrackLoadedListener,
IAnalyzerStartListener {
    private static final double DSP = 1.0 / Math.sqrt(2.0);
    private static final double[] FFT = new double[5000];
    private static final double[] responseView = new double[5000];
    private static final double[] AdditionalMetadataValue = new double[5000];
    private final double[] AudioFileExtension = new double[5000];
    private final double[] IAudioFileCodec = new double[5000];
    private final double[] IAudioInputStream = new double[100];
    private final double[] IAudioMetaInformation = new double[100];
    private static int IBaseAudioCodec = 0;
    private static int MetaInfomationCopy;
    private static int AacAudioCodec;
    private static double AacMetaDataModel;

    public StereoMeterModule() {
        this.IAudioFileCodec();
    }

    private void IAudioFileCodec() {
        int n;
        AacMetaDataModel = 0.0;
        IBaseAudioCodec = 0;
        for (n = 0; n < 5000; ++n) {
            StereoMeterModule.responseView[n] = 0.0;
            StereoMeterModule.FFT[n] = 0.0;
            this.IAudioFileCodec[n] = 0.0;
            this.AudioFileExtension[n] = 0.0;
        }
        for (n = 0; n < 100; ++n) {
            this.IAudioMetaInformation[n] = 0.0;
            this.IAudioInputStream[n] = 0.0;
        }
    }

    public void responseView() {
        this.IAudioFileCodec();
    }

    public StereoMeterModel AudioFileExtension() throws Exception {
        int n;
        Thread.currentThread().setName(this.getClass().getSimpleName());
        double d = 0.0;
        double d2 = 0.0;
        double d3 = 0.0;
        ComputationObjects pYJBgzPCdrZnekQfrnwIPxJ = this.DSP();
        AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf = pYJBgzPCdrZnekQfrnwIPxJ.responseView();
        if (zvOTVUaKFTNpNSbbMYZfoXf.AdditionalMetadataValue() < 500) {
            return null;
        }
        int n2 = zvOTVUaKFTNpNSbbMYZfoXf.responseView().length;
        int n3 = n2 / 500;
        for (n = 0; n < n2; n += n3) {
            this.AudioFileExtension[StereoMeterModule.IBaseAudioCodec] = zvOTVUaKFTNpNSbbMYZfoXf.responseView()[n];
            this.IAudioFileCodec[StereoMeterModule.IBaseAudioCodec] = zvOTVUaKFTNpNSbbMYZfoXf.FFT()[n];
            if (++IBaseAudioCodec < 5000) continue;
            IBaseAudioCodec = 0;
        }
        MetaInfomationCopy = IBaseAudioCodec;
        double d4 = 2.0E-4;
        double d5 = 0.0;
        for (n = 0; n < 5000; ++n) {
            StereoMeterModule.FFT[n] = -DSP * (this.AudioFileExtension[MetaInfomationCopy] - this.IAudioFileCodec[MetaInfomationCopy]);
            StereoMeterModule.responseView[n] = -DSP * (this.AudioFileExtension[MetaInfomationCopy] + this.IAudioFileCodec[MetaInfomationCopy]);
            if (++MetaInfomationCopy >= 5000) {
                MetaInfomationCopy = 0;
            }
            StereoMeterModule.AdditionalMetadataValue[n] = d5 += d4;
        }
        double d6 = 0.0;
        for (n = 0; n < n2; ++n) {
            d = zvOTVUaKFTNpNSbbMYZfoXf.responseView()[n];
            d2 = zvOTVUaKFTNpNSbbMYZfoXf.FFT()[n];
            if (Math.abs(d2) > Math.abs(d)) {
                d6 += 1.0;
            } else if (Math.abs(d2) < Math.abs(d)) {
                d6 -= 1.0;
            }
            if (d > 0.0) {
                d = 1.0;
            } else if (d < 0.0) {
                d = -1.0;
            }
            if (d2 > 0.0) {
                d2 = 1.0;
            } else if (d2 < 0.0) {
                d2 = -1.0;
            }
            d3 += d * d2;
        }
        d3 /= (double)n2;
        this.IAudioInputStream[StereoMeterModule.AacAudioCodec] = d6 /= (double)n2;
        if (++AacAudioCodec >= 100) {
            AacAudioCodec = 0;
        }
        int n4 = AacAudioCodec;
        for (n = 0; n < 100; ++n) {
            this.IAudioMetaInformation[n] = this.IAudioInputStream[n4];
            if (++n4 < 100) continue;
            n4 = 0;
        }
        if (AacMetaDataModel < d3) {
            AacMetaDataModel += 0.004;
        }
        if (AacMetaDataModel > d3) {
            AacMetaDataModel -= 0.004;
        }
        if (AacMetaDataModel < -1.0) {
            AacMetaDataModel = -1.0;
        }
        if (AacMetaDataModel > 1.0) {
            AacMetaDataModel = 1.0;
        }
        StereoMeterModel fLTjkCbqdFcYQIXMwzSzQor = new StereoMeterModel(5000, AdditionalMetadataValue, FFT, responseView, AacMetaDataModel, this.IAudioMetaInformation);
        return fLTjkCbqdFcYQIXMwzSzQor;
    }

    @Override
    public void DSP(String string) {
        this.IAudioFileCodec();
    }

    @Override
    public void DSP(PlayerState zjoyaRSokkGYDwXHPKTBIiX) {
        if (zjoyaRSokkGYDwXHPKTBIiX == PlayerState.AdditionalMetadataValue) {
            this.responseView();
        }
    }

    @Override
    public boolean DSP(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2, Object object) {
        switch (lXCOGwZXVelwEKHMMPwpSAO2) {
            case AudioFileExtension: {
                this.IAudioFileCodec();
            }
        }
        return true;
    }

    @Override
    public void AdditionalMetadataValue() {
        this.IAudioFileCodec();
    }

    @Override
    public /* synthetic */ Object FFT() throws Exception {
        return this.AudioFileExtension();
    }

    static {
        AacAudioCodec = 0;
        AacMetaDataModel = 0.0;
    }
}

