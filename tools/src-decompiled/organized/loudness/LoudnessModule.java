/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.ComputationSubject;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerEventListener;
import sdfgjkljljoftrytrszgijpokjprs.AbstractComputationModule;
import sdfgjkljljoftrytrszgijpokjprs.LoudnessModel;
import sdfgjkljljoftrytrszgijpokjprs.ComputationObjects;
import sdfgjkljljoftrytrszgijpokjprs.iirFilter;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;
import sdfgjkljljoftrytrszgijpokjprs.IValueReporting;
import sdfgjkljljoftrytrszgijpokjprs.ITrackLoadedListener;
import sdfgjkljljoftrytrszgijpokjprs.IAnalyzerStartListener;

@ComputationSubject(DSP=ComputationController.DSP.Loudness)
public class LoudnessModule
extends AbstractComputationModule<LoudnessModel>
implements IPlayerStateListener,
IPlayerEventListener,
IValueReporting<LoudnessModel>,
ITrackLoadedListener,
IAnalyzerStartListener {
    static double[] DSP = new double[19200];
    static double[] FFT = new double[19200];
    static double[] responseView = new double[8];
    static double[] AdditionalMetadataValue = new double[60];
    static double[] AudioFileExtension = new double[8];
    static int IAudioFileCodec = 0;
    static int IAudioInputStream = 0;
    static int IAudioMetaInformation = 0;
    static int[] IBaseAudioCodec = new int[800];
    static int[] MetaInfomationCopy = new int[800];
    static double AacAudioCodec;
    static double AacMetaDataModel;
    static double BufferedAacReader;
    static double AiffAudioCodec;
    private double AlacUtils;
    private double ChunkInfo;
    private double DemuxResT;
    private double DemuxUtils;
    static int AiffMetaDataModel;
    static int AlacAudioCodec;
    static int AlacMetaDataModel;
    static int BufferedAlacReader;
    static int AlacContextModel;
    static int AlacDecoderUtils;
    private static int LeadingZeros;
    private static int MyStream;
    private static int QTMovieT;
    private static int SampleDuration;
    private LoudnessModel SampleInfo;
    iirFilter AlacFile;
    ComputationObjects AlacInputStream = this.DSP();

    public LoudnessModule() {
        this.IAudioMetaInformation();
    }

    private void IAudioMetaInformation() {
        int n;
        IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA = this.AlacInputStream.FFT();
        AiffMetaDataModel = yGjBevanihqaxYKnUNtrNeA.DSP();
        AlacAudioCodec = yGjBevanihqaxYKnUNtrNeA.FFT().DSP();
        this.AlacFile = new iirFilter(AiffMetaDataModel);
        AacAudioCodec = -60.0;
        AiffAudioCodec = -60.0;
        BufferedAacReader = -60.0;
        AacMetaDataModel = 0.0;
        this.ChunkInfo = 0.0;
        this.DemuxResT = 1.0;
        SampleDuration = 0;
        for (n = 0; n < 800; ++n) {
            LoudnessModule.MetaInfomationCopy[n] = 0;
            LoudnessModule.IBaseAudioCodec[n] = 0;
        }
        for (n = 0; n < 8; ++n) {
            LoudnessModule.AudioFileExtension[n] = 0.0;
            LoudnessModule.responseView[n] = 0.0;
        }
        for (n = 0; n < 60; ++n) {
            LoudnessModule.AdditionalMetadataValue[n] = 0.0;
        }
        IAudioMetaInformation = 0;
        IAudioInputStream = 0;
        IAudioFileCodec = 0;
        AlacContextModel = 0;
        AlacDecoderUtils = 0;
        QTMovieT = 0;
    }

    public void responseView() {
    }

    public static void DSP(double d, double d2) {
        double d3;
        double d4;
        int n;
        int n2;
        int n3;
        if (d >= -70.0 && d <= 5.0 && AlacDecoderUtils > 7) {
            int n4 = n3 = (int)Math.round(10.0 * (d + 70.0));
            MetaInfomationCopy[n4] = MetaInfomationCopy[n4] + 1;
            n2 = 0;
            double d5 = 0;
            AlacMetaDataModel = 1;
            for (n = 0; n < 751; ++n) {
                d4 = (double)n / 10.0 - 70.0;
                d3 = Math.pow(10.0, d4 / 10.0);
                n2 += MetaInfomationCopy[n];
                d5 += (double)MetaInfomationCopy[n] * d3;
                if (AlacMetaDataModel >= MetaInfomationCopy[n]) continue;
                AlacMetaDataModel = MetaInfomationCopy[n];
            }
            d5 /= (double)n2;
            double d6 = (d5 = 10.0 * Math.log10(d5)) - 10.0;
            if (d6 < -70.0) {
                d6 = -70.0;
            }
            n3 = (int)Math.round(10.0 * (d6 + 70.0));
            AacAudioCodec = 0.0;
            n2 = 0;
            for (n = n3; n < 751; ++n) {
                d4 = (double)n / 10.0 - 70.0;
                d3 = Math.pow(10.0, d4 / 10.0);
                n2 += MetaInfomationCopy[n];
                AacAudioCodec += (double)MetaInfomationCopy[n] * d3;
            }
            AacAudioCodec /= (double)n2;
            AacAudioCodec = 10.0 * Math.log10(AacAudioCodec);
        } else {
            ++AlacDecoderUtils;
        }
        if (d2 >= -70.0 && d2 <= 5.0 && AlacContextModel > 59) {
            QTMovieT = 1;
            int n5 = n3 = (int)Math.round(10.0 * (d2 + 70.0));
            IBaseAudioCodec[n5] = IBaseAudioCodec[n5] + 1;
            n2 = 0;
            double d7 = 0;
            BufferedAlacReader = 1;
            for (n = 0; n < 751; ++n) {
                d4 = (double)n / 10.0 - 70.0;
                d3 = Math.pow(10.0, d4 / 10.0);
                n2 += IBaseAudioCodec[n];
                d7 += (double)IBaseAudioCodec[n] * d3;
                if (BufferedAlacReader >= IBaseAudioCodec[n]) continue;
                BufferedAlacReader = IBaseAudioCodec[n];
            }
            d7 /= (double)n2;
            double d8 = (d7 = 10.0 * Math.log10(d7)) - 20.0;
            if (d8 < -70.0) {
                d8 = -70.0;
            }
            n3 = (int)Math.round(10.0 * (d8 + 70.0));
            n2 = 0;
            for (n = n3; n < 751; ++n) {
                n2 += IBaseAudioCodec[n];
            }
            int n6 = 0;
            LeadingZeros = -1;
            MyStream = -1;
            for (n = n3; n < 751; ++n) {
                if ((double)(n6 += IBaseAudioCodec[n]) / (double)n2 > 0.1 && LeadingZeros == -1) {
                    LeadingZeros = n;
                }
                if (!((double)n6 / (double)n2 > 0.95) || MyStream != -1) continue;
                MyStream = n;
            }
            double d9 = (double)LeadingZeros / 10.0 - 70.0;
            double d10 = (double)MyStream / 10.0 - 70.0;
            AacMetaDataModel = d10 - d9;
        } else {
            ++AlacContextModel;
        }
    }

    public LoudnessModel IAudioFileCodec() throws Exception {
        int n;
        Thread.currentThread().setName(this.getClass().getSimpleName());
        AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf = this.AlacInputStream.responseView();
        int n2 = zvOTVUaKFTNpNSbbMYZfoXf.responseView().length;
        this.AlacFile.DSP(1, zvOTVUaKFTNpNSbbMYZfoXf.responseView(), DSP, n2);
        this.AlacFile.DSP(2, zvOTVUaKFTNpNSbbMYZfoXf.FFT(), FFT, n2);
        this.AlacFile.DSP(3, DSP, DSP, n2);
        this.AlacFile.DSP(4, FFT, FFT, n2);
        double d = 0.0;
        double d2 = 0.0;
        double d3 = 0.0;
        double d4 = 0.0;
        for (n = 0; n < n2; ++n) {
            double d5 = DSP[n];
            double d6 = FFT[n];
            d4 += d5 * d5;
            d3 += d6 * d6;
            double d7 = Math.abs(d5);
            double d8 = Math.abs(d6);
            if (d2 < d7) {
                d2 = d7;
            }
            if (!(d < d8)) continue;
            d = d8;
        }
        double d9 = (d4 + d3) / (double)n2;
        if (d2 < d) {
            d2 = d;
        }
        LoudnessModule.AudioFileExtension[LoudnessModule.IAudioFileCodec] = d2;
        if (++IAudioFileCodec > 7) {
            IAudioFileCodec = 0;
        }
        double d10 = 0.0;
        for (n = 0; n < 8; ++n) {
            d10 += AudioFileExtension[n];
        }
        if ((d10 = (d10 /= 8.0) > 0.0 ? 20.0 * Math.log10(d10) - 0.691 : -60.0) < -60.0) {
            d10 = -60.0;
        }
        LoudnessModule.responseView[LoudnessModule.IAudioInputStream] = d9;
        if (++IAudioInputStream > 7) {
            IAudioInputStream = 0;
        }
        double d11 = 0.0;
        for (n = 0; n < 8; ++n) {
            d11 += responseView[n];
        }
        double d12 = (d11 /= 8.0) > 0.0 ? 10.0 * Math.log10(d11) - 0.691 : -90.0;
        LoudnessModule.AdditionalMetadataValue[LoudnessModule.IAudioMetaInformation] = d9;
        if (++IAudioMetaInformation > 59) {
            IAudioMetaInformation = 0;
        }
        d11 = 0.0;
        for (n = 0; n < 60; ++n) {
            d11 += AdditionalMetadataValue[n];
        }
        double d13 = (d11 /= 60.0) > 0.0 ? 10.0 * Math.log10(d11) - 0.691 : -90.0;
        LoudnessModule.DSP(d12, d13);
        if (d12 < -60.0) {
            d12 = -60.0;
        }
        if (d13 < -60.0) {
            d13 = -60.0;
        }
        this.AlacUtils = d10 - d12;
        if (this.AlacUtils < 0.0) {
            this.AlacUtils = 0.0;
        }
        if (SampleDuration < 8) {
            ++SampleDuration;
            this.AlacUtils = 0.0;
        } else {
            this.ChunkInfo += Math.pow(10.0, this.AlacUtils / 20.0);
            this.DemuxResT += 1.0;
            this.DemuxUtils = this.ChunkInfo > 0.0 ? 20.0 * Math.log10(this.ChunkInfo / this.DemuxResT) : 0.0;
        }
        if (BufferedAacReader < d12) {
            BufferedAacReader = d12;
        }
        if (AiffAudioCodec < d13) {
            AiffAudioCodec = d13;
        }
        if (AacAudioCodec < -60.0) {
            AacAudioCodec = -60.0;
        }
        this.SampleInfo = new LoudnessModel(d12, d13, BufferedAacReader, AiffAudioCodec, AacAudioCodec, AacMetaDataModel, MetaInfomationCopy, AlacMetaDataModel, IBaseAudioCodec, BufferedAlacReader, LeadingZeros, MyStream, QTMovieT, this.AlacUtils, this.DemuxUtils);
        return this.SampleInfo;
    }

    @Override
    public void DSP(String string) {
        this.IAudioMetaInformation();
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
                this.IAudioMetaInformation();
            }
        }
        return true;
    }

    @Override
    public void AdditionalMetadataValue() {
        this.IAudioMetaInformation();
    }

    public LoudnessModel IAudioInputStream() {
        return this.SampleInfo;
    }

    @Override
    public /* synthetic */ Object FFT() throws Exception {
        return this.IAudioFileCodec();
    }

    @Override
    public /* synthetic */ Object AudioFileExtension() {
        return this.IAudioInputStream();
    }

    static {
        QTMovieT = 0;
        SampleDuration = 0;
    }
}

