/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.LevelsModel;
import sdfgjkljljoftrytrszgijpokjprs.ComputationSubject;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerEventListener;
import sdfgjkljljoftrytrszgijpokjprs.AbstractComputationModule;
import sdfgjkljljoftrytrszgijpokjprs.ComputationObjects;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;
import sdfgjkljljoftrytrszgijpokjprs.ILevelMeterControlMidSideSwitch;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;
import sdfgjkljljoftrytrszgijpokjprs.IValueReporting;
import sdfgjkljljoftrytrszgijpokjprs.FIRFilterPoly;
import sdfgjkljljoftrytrszgijpokjprs.ITrackLoadedListener;
import sdfgjkljljoftrytrszgijpokjprs.IAnalyzerStartListener;

@ComputationSubject(DSP=ComputationController.DSP.Levels)
public class LevelsModule
extends AbstractComputationModule<LevelsModel>
implements IPlayerStateListener,
IPlayerEventListener,
ILevelMeterControlMidSideSwitch,
IValueReporting<LevelsModel>,
ITrackLoadedListener,
IAnalyzerStartListener {
    static double[] DSP = new double[19200];
    static double[] FFT = new double[19200];
    static double[] responseView = new double[19200];
    static double[] AdditionalMetadataValue = new double[19200];
    static double[] AudioFileExtension = new double[8];
    static double[] IAudioFileCodec = new double[8];
    static double[] IAudioInputStream = new double[8];
    static double[] IAudioMetaInformation = new double[8];
    static double[] IBaseAudioCodec = new double[60];
    static double[] MetaInfomationCopy = new double[8];
    private final int[] IMetadataReader = new int[2000];
    private final int[] IRawInputReader = new int[2000];
    private int IReadableChannelBuffer;
    static int AacAudioCodec = 0;
    static double AacMetaDataModel;
    static double BufferedAacReader;
    static double AiffAudioCodec;
    static double AiffMetaDataModel;
    static double AlacAudioCodec;
    static double AlacMetaDataModel;
    static double BufferedAlacReader;
    static double AlacContextModel;
    static double AlacDecoderUtils;
    static double AlacFile;
    static double AlacInputStream;
    static double AlacUtils;
    static double ChunkInfo;
    static double DemuxResT;
    static double DemuxUtils;
    static double LeadingZeros;
    static double MyStream;
    static double QTMovieT;
    static final double SampleDuration;
    static long SampleInfo;
    static int StreamUtils;
    static int DffChunkReaderAdapter;
    static int DsdAudioCodec;
    static int DsdMetaDataModel;
    static int DsfChunkReaderAdapter;
    static int AbstractManagedWorker;
    static int ChannelDecoder;
    String ChannelSplitter = "";
    String DirectStreamDigitalDecoder = "";
    private LevelsModel InterleavedBufferBuilder;
    private boolean SimpleByteBuffer = false;
    private boolean StreamDecoder = false;
    private boolean ThreadState = false;
    private boolean BufferedFlacReader = false;
    private double[] FlacAudioCodec = new double[20000];
    private int FlacMetaDataModel = 0;
    private double[] ChannelData = new double[20000];
    private int Constants = 0;
    FIRFilterPoly DstDecodeUtil = new FIRFilterPoly(0);
    FIRFilterPoly DstDecoder = new FIRFilterPoly(1);
    FIRFilterPoly EmptySimpleByteBuffer = new FIRFilterPoly(2);
    FIRFilterPoly ExtendedDSTSoundDataChunk = new FIRFilterPoly(3);
    FIRFilterPoly FIRDecimationFilter = new FIRFilterPoly(4);
    ComputationObjects IChannelInputReader = this.DSP();

    public LevelsModule() {
        this.IAudioInputStream();
    }

    private void IAudioInputStream() {
        int n;
        this.DstDecodeUtil = new FIRFilterPoly(0);
        this.DstDecoder = new FIRFilterPoly(1);
        this.EmptySimpleByteBuffer = new FIRFilterPoly(2);
        this.ExtendedDSTSoundDataChunk = new FIRFilterPoly(3);
        this.FIRDecimationFilter = new FIRFilterPoly(4);
        IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA = this.IChannelInputReader.FFT();
        StreamUtils = yGjBevanihqaxYKnUNtrNeA.DSP();
        DffChunkReaderAdapter = yGjBevanihqaxYKnUNtrNeA.FFT().DSP();
        AlacContextModel = -60.0;
        BufferedAlacReader = -60.0;
        AlacMetaDataModel = -60.0;
        AlacAudioCodec = -60.0;
        AiffMetaDataModel = -60.0;
        AiffAudioCodec = -60.0;
        BufferedAacReader = -60.0;
        AacMetaDataModel = -60.0;
        DsfChunkReaderAdapter = 0;
        DsdMetaDataModel = 0;
        ChunkInfo = 0.0;
        ChannelDecoder = 1;
        DsdAudioCodec = 0;
        AacAudioCodec = 0;
        QTMovieT = 0.0;
        MyStream = 0.0;
        LeadingZeros = 0.0;
        DemuxUtils = 0.0;
        SampleInfo = 0L;
        AbstractManagedWorker = 0;
        for (n = 0; n < 19200; ++n) {
            LevelsModule.AdditionalMetadataValue[n] = 0.0;
            LevelsModule.responseView[n] = 0.0;
            LevelsModule.FFT[n] = 0.0;
            LevelsModule.DSP[n] = 0.0;
        }
        for (n = 0; n < 8; ++n) {
            LevelsModule.MetaInfomationCopy[n] = 0.0;
            LevelsModule.IAudioMetaInformation[n] = 0.0;
            LevelsModule.IAudioInputStream[n] = 0.0;
            LevelsModule.IAudioFileCodec[n] = 0.0;
            LevelsModule.AudioFileExtension[n] = 0.0;
        }
        for (n = 0; n < 60; ++n) {
            LevelsModule.IBaseAudioCodec[n] = 0.0;
        }
        for (n = 0; n < 2000; ++n) {
            this.IRawInputReader[n] = 0;
            this.IMetadataReader[n] = 0;
        }
        this.IReadableChannelBuffer = 0;
    }

    private void IAudioMetaInformation() {
        AlacMetaDataModel = -60.0;
        AlacAudioCodec = -60.0;
        BufferedAacReader = -60.0;
        AacMetaDataModel = -60.0;
    }

    void DSP(double d, double d2) {
        if (d > 0.001) {
            int n;
            d = 20.0 * Math.log10(d);
            int n2 = n = (int)Math.round(d * 10.0 + 600.0);
            this.IMetadataReader[n2] = this.IMetadataReader[n2] + 1;
            if (this.IReadableChannelBuffer < this.IMetadataReader[n]) {
                this.IReadableChannelBuffer = this.IMetadataReader[n];
            }
        }
        if (d2 > 0.001) {
            int n;
            d2 = 20.0 * Math.log10(d2);
            int n3 = n = (int)Math.round(d2 * 10.0 + 600.0);
            this.IRawInputReader[n3] = this.IRawInputReader[n3] + 1;
            if (this.IReadableChannelBuffer < this.IRawInputReader[n]) {
                this.IReadableChannelBuffer = this.IRawInputReader[n];
            }
        }
    }

    void FFT(double d, double d2) {
        Double d3 = Math.abs(d) * Math.pow(2.0, DffChunkReaderAdapter - 1);
        Double d4 = Math.abs(d2) * Math.pow(2.0, DffChunkReaderAdapter - 1);
        long l = d3.longValue();
        long l2 = d4.longValue();
        this.StreamDecoder = d < 0.0;
        boolean bl = this.ThreadState = d2 < 0.0;
        if (DffChunkReaderAdapter == 16) {
            this.ChannelSplitter = String.format("%16s", Long.toBinaryString(l)).replace(" ", "0");
            this.DirectStreamDigitalDecoder = String.format("%16s", Long.toBinaryString(l2)).replace(" ", "0");
        } else if (DffChunkReaderAdapter == 24) {
            this.ChannelSplitter = String.format("%24s", Long.toBinaryString(l)).replace(" ", "0");
            this.DirectStreamDigitalDecoder = String.format("%24s", Long.toBinaryString(l2)).replace(" ", "0");
        } else if (DffChunkReaderAdapter == 32) {
            this.ChannelSplitter = String.format("%32s", Long.toBinaryString(l)).replace(" ", "0");
            this.DirectStreamDigitalDecoder = String.format("%32s", Long.toBinaryString(l2)).replace(" ", "0");
        } else if (DffChunkReaderAdapter == 64) {
            this.ChannelSplitter = String.format("%64s", Long.toBinaryString(l)).replace(" ", "0");
            this.DirectStreamDigitalDecoder = String.format("%64s", Long.toBinaryString(l2)).replace(" ", "0");
        }
    }

    public LevelsModel responseView() throws Exception {
        int n;
        int n2;
        Thread.currentThread().setName(this.getClass().getSimpleName());
        AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf = this.IChannelInputReader.responseView();
        int n3 = zvOTVUaKFTNpNSbbMYZfoXf.responseView().length;
        this.FFT(zvOTVUaKFTNpNSbbMYZfoXf.responseView()[0], zvOTVUaKFTNpNSbbMYZfoXf.FFT()[0]);
        if (StreamUtils == 44100) {
            n2 = 4;
            this.DstDecodeUtil.DSP(0, n3, zvOTVUaKFTNpNSbbMYZfoXf.responseView(), zvOTVUaKFTNpNSbbMYZfoXf.FFT(), DSP, FFT);
            this.DstDecoder.DSP(0, n3 * 2, DSP, FFT, responseView, AdditionalMetadataValue);
        } else if (StreamUtils == 48000) {
            n2 = 4;
            this.EmptySimpleByteBuffer.DSP(0, n3, zvOTVUaKFTNpNSbbMYZfoXf.responseView(), zvOTVUaKFTNpNSbbMYZfoXf.FFT(), DSP, FFT);
            this.ExtendedDSTSoundDataChunk.DSP(0, n3 * 2, DSP, FFT, responseView, AdditionalMetadataValue);
        } else if (StreamUtils == 88200) {
            n2 = 2;
            this.DstDecoder.DSP(1, n3, zvOTVUaKFTNpNSbbMYZfoXf.responseView(), zvOTVUaKFTNpNSbbMYZfoXf.FFT(), responseView, AdditionalMetadataValue);
        } else if (StreamUtils == 96000) {
            n2 = 2;
            this.ExtendedDSTSoundDataChunk.DSP(1, n3, zvOTVUaKFTNpNSbbMYZfoXf.responseView(), zvOTVUaKFTNpNSbbMYZfoXf.FFT(), responseView, AdditionalMetadataValue);
        } else {
            n2 = 1;
            System.arraycopy(zvOTVUaKFTNpNSbbMYZfoXf.responseView(), 0, responseView, 0, n3);
            System.arraycopy(zvOTVUaKFTNpNSbbMYZfoXf.FFT(), 0, AdditionalMetadataValue, 0, n3);
        }
        double d = 0.0;
        double d2 = 0.0;
        double d3 = 0.0;
        double d4 = 0.0;
        double d5 = 0.0;
        double d6 = 0.0;
        double d7 = 0.0;
        double d8 = 0.0;
        for (n = 0; n < n3 * n2; ++n) {
            double d9 = responseView[n];
            double d10 = AdditionalMetadataValue[n];
            double d11 = Math.abs(d9);
            double d12 = Math.abs(d10);
            this.DSP(d11, d12);
            if (d8 < d11) {
                d8 = d11;
            }
            if (d7 < d12) {
                d7 = d12;
            }
            d4 += d9 * d9;
            d3 += d10 * d10;
            double d13 = (d9 + d10) / 2.0;
            double d14 = (d9 - d10) / 2.0;
            double d15 = Math.abs(d13);
            double d16 = Math.abs(d14);
            if (d6 < d15) {
                d6 = d15;
            }
            if (d5 < d16) {
                d5 = d16;
            }
            d2 += d13 * d13;
            d += d14 * d14;
        }
        DemuxUtils += (d4 /= (double)(n3 * n2));
        LeadingZeros += (d3 /= (double)(n3 * n2));
        MyStream += (d2 /= (double)(n3 * n2));
        QTMovieT += (d /= (double)(n3 * n2));
        ++SampleInfo;
        LevelsModule.AudioFileExtension[LevelsModule.AacAudioCodec] = d4;
        LevelsModule.IAudioFileCodec[LevelsModule.AacAudioCodec] = d3;
        LevelsModule.IAudioInputStream[LevelsModule.AacAudioCodec] = d2;
        LevelsModule.IAudioMetaInformation[LevelsModule.AacAudioCodec] = d;
        double d17 = d8;
        double d18 = d4;
        if (d17 < d7) {
            d17 = d7;
            d18 = d3;
        }
        LevelsModule.MetaInfomationCopy[LevelsModule.AacAudioCodec] = (d18 = Math.sqrt(d18)) > 0.0 ? d17 / d18 : 0.0;
        if (++AacAudioCodec > 7) {
            AacAudioCodec = 0;
        }
        double d19 = 0.0;
        for (n = 0; n < 8; ++n) {
            d19 += MetaInfomationCopy[n];
        }
        if ((d19 /= 8.0) > 0.001) {
            if (AbstractManagedWorker < 8) {
                ++AbstractManagedWorker;
            } else {
                DemuxResT = 20.0 * Math.log10((ChunkInfo += d19) / (double)ChannelDecoder);
                ++ChannelDecoder;
            }
            d19 = 20.0 * Math.log10(d19);
        } else {
            d19 = 0.0;
        }
        d = 0.0;
        d2 = 0.0;
        d3 = 0.0;
        d4 = 0.0;
        for (n = 0; n < 8; ++n) {
            d4 += AudioFileExtension[n];
            d3 += IAudioFileCodec[n];
            d2 += IAudioInputStream[n];
            d += IAudioMetaInformation[n];
        }
        d4 = Math.sqrt(d4 / 8.0);
        d3 = Math.sqrt(d3 / 8.0);
        d2 = Math.sqrt(d2 / 8.0);
        d = Math.sqrt(d / 8.0);
        d4 = d4 > 0.001 ? 20.0 * Math.log10(d4) : -60.0;
        d3 = d3 > 0.001 ? 20.0 * Math.log10(d3) : -60.0;
        d2 = d2 > 0.001 ? 20.0 * Math.log10(d2) : -60.0;
        d = d > 0.001 ? 20.0 * Math.log10(d) : -60.0;
        d8 = d8 > 0.001 ? 20.0 * Math.log10(d8) : -60.0;
        d7 = d7 > 0.001 ? 20.0 * Math.log10(d7) : -60.0;
        d6 = d6 > 0.001 ? 20.0 * Math.log10(d6) : -60.0;
        d5 = d5 > 0.001 ? 20.0 * Math.log10(d5) : -60.0;
        if (d8 > 3.0) {
            d8 = 3.0;
        }
        if (d7 > 3.0) {
            d7 = 3.0;
        }
        if (d6 > 3.0) {
            d6 = 3.0;
        }
        if (d5 > 3.0) {
            d5 = 3.0;
        }
        if (d8 > 0.05) {
            ++DsdMetaDataModel;
        }
        if (d7 > 0.05) {
            ++DsfChunkReaderAdapter;
        }
        if (AlacAudioCodec < d8) {
            AlacAudioCodec = d8;
        }
        if (AlacMetaDataModel < d7) {
            AlacMetaDataModel = d7;
        }
        if (BufferedAlacReader < d6) {
            BufferedAlacReader = d6;
        }
        if (AlacContextModel < d5) {
            AlacContextModel = d5;
        }
        if (AacMetaDataModel < d8) {
            AacMetaDataModel = d8;
            AlacDecoderUtils = 0.0;
        } else {
            AlacDecoderUtils += 0.005;
        }
        if (BufferedAacReader < d7) {
            BufferedAacReader = d7;
            AlacFile = 0.0;
        } else {
            AlacFile += 0.005;
        }
        if (AiffAudioCodec < d6) {
            AiffAudioCodec = d6;
            AlacInputStream = 0.0;
        } else {
            AlacInputStream += 0.005;
        }
        if (AiffMetaDataModel < d5) {
            AiffMetaDataModel = d5;
            AlacUtils = 0.0;
        } else {
            AlacUtils += 0.005;
        }
        double d20 = Math.sqrt(DemuxUtils / (double)SampleInfo);
        double d21 = Math.sqrt(LeadingZeros / (double)SampleInfo);
        double d22 = Math.sqrt(MyStream / (double)SampleInfo);
        double d23 = Math.sqrt(QTMovieT / (double)SampleInfo);
        d20 = d20 > 0.001 ? 20.0 * Math.log10(d20) : -60.0;
        d21 = d21 > 0.001 ? 20.0 * Math.log10(d21) : -60.0;
        d22 = d22 > 0.001 ? 20.0 * Math.log10(d22) : -60.0;
        d23 = d23 > 0.001 ? 20.0 * Math.log10(d23) : -60.0;
        this.IMetadataReader[1999] = this.IRawInputReader[1999] = this.IReadableChannelBuffer;
        this.InterleavedBufferBuilder = new LevelsModel(d8, d7, AacMetaDataModel, BufferedAacReader, AlacAudioCodec, AlacMetaDataModel, DsdMetaDataModel, DsfChunkReaderAdapter, d4, d3, d19, DemuxResT, d20, d21, this.ChannelSplitter, this.DirectStreamDigitalDecoder, this.StreamDecoder, this.ThreadState, d6, d5, AiffAudioCodec, AiffMetaDataModel, BufferedAlacReader, AlacContextModel, d2, d, d22, d23, this.IMetadataReader, this.IRawInputReader);
        AacMetaDataModel -= AlacDecoderUtils;
        BufferedAacReader -= AlacFile;
        AiffAudioCodec -= AlacInputStream;
        AiffMetaDataModel -= AlacUtils;
        return this.InterleavedBufferBuilder;
    }

    @Override
    public void DSP(String string) {
        this.IAudioInputStream();
    }

    @Override
    public void DSP(PlayerState zjoyaRSokkGYDwXHPKTBIiX) {
        if (zjoyaRSokkGYDwXHPKTBIiX == PlayerState.AdditionalMetadataValue) {
            this.IAudioMetaInformation();
        }
    }

    @Override
    public void AdditionalMetadataValue() {
        this.IAudioInputStream();
    }

    public LevelsModel IAudioFileCodec() {
        return this.InterleavedBufferBuilder;
    }

    @Override
    public boolean DSP(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2, Object object) {
        switch (lXCOGwZXVelwEKHMMPwpSAO2) {
            case AudioFileExtension: {
                this.IAudioInputStream();
                break;
            }
        }
        return true;
    }

    @Override
    public void FFT(boolean bl) {
        this.SimpleByteBuffer = bl;
    }

    @Override
    public /* synthetic */ Object FFT() throws Exception {
        return this.responseView();
    }

    @Override
    public /* synthetic */ Object AudioFileExtension() {
        return this.IAudioFileCodec();
    }

    static {
        AlacDecoderUtils = 0.0;
        AlacFile = 0.0;
        AlacInputStream = 0.0;
        AlacUtils = 0.0;
        ChunkInfo = 0.0;
        DemuxUtils = 0.0;
        LeadingZeros = 0.0;
        MyStream = 0.0;
        QTMovieT = 0.0;
        SampleDuration = Math.pow(10.0, 0.0025);
        SampleInfo = 0L;
        DsdAudioCodec = 0;
        DsdMetaDataModel = 0;
        DsfChunkReaderAdapter = 0;
        AbstractManagedWorker = 0;
        ChannelDecoder = 1;
    }
}

