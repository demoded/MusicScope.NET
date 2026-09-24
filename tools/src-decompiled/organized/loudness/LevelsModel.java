/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.ReportingParameter;

public class LevelsModel {
    double DSP;
    double FFT;
    double responseView;
    double AdditionalMetadataValue;
    @ReportingParameter(DSP="TPL Left:", FFT="dB")
    double AudioFileExtension;
    @ReportingParameter(DSP="TPL Right:", FFT="dB")
    double IAudioFileCodec;
    @ReportingParameter(DSP="TPL Mid:", FFT="dB")
    double IAudioInputStream;
    @ReportingParameter(DSP="TPL Side:", FFT="dB")
    double IAudioMetaInformation;
    @ReportingParameter(DSP="RMS Left:", FFT="dB")
    double IBaseAudioCodec;
    @ReportingParameter(DSP="RMS Right:", FFT="dB")
    double MetaInfomationCopy;
    @ReportingParameter(DSP="RMS Mid:", FFT="dB")
    double AacAudioCodec;
    @ReportingParameter(DSP="RMS Side:", FFT="dB")
    double AacMetaDataModel;
    @ReportingParameter(DSP="CREST Avg.:", FFT="dB")
    double BufferedAacReader;
    @ReportingParameter(DSP="IS L/M:", responseView={"DSD"})
    int AiffAudioCodec;
    @ReportingParameter(DSP="IS R/S:", responseView={"DSD"})
    int AiffMetaDataModel;
    double AlacAudioCodec;
    double AlacMetaDataModel;
    double BufferedAlacReader;
    String AlacContextModel;
    String AlacDecoderUtils;
    boolean AlacFile;
    boolean AlacInputStream;
    double AlacUtils;
    double ChunkInfo;
    double DemuxResT;
    double DemuxUtils;
    double LeadingZeros;
    double MyStream;
    int[] QTMovieT;
    int[] SampleDuration;

    public LevelsModel(double d, double d2, double d3, double d4, double d5, double d6, int n, int n2, double d7, double d8, double d9, double d10, double d11, double d12, String string, String string2, boolean bl, boolean bl2, double d13, double d14, double d15, double d16, double d17, double d18, double d19, double d20, double d21, double d22, int[] nArray, int[] nArray2) {
        this.DSP = d;
        this.FFT = d2;
        this.responseView = d3;
        this.AdditionalMetadataValue = d4;
        this.AudioFileExtension = d5;
        this.IAudioFileCodec = d6;
        this.AiffAudioCodec = n;
        this.AiffMetaDataModel = n2;
        this.AlacAudioCodec = d7;
        this.AlacMetaDataModel = d8;
        this.BufferedAlacReader = d9;
        this.BufferedAacReader = d10;
        this.IBaseAudioCodec = d11;
        this.MetaInfomationCopy = d12;
        this.AlacContextModel = string;
        this.AlacDecoderUtils = string2;
        this.AlacFile = bl;
        this.AlacInputStream = bl2;
        this.AlacUtils = d13;
        this.ChunkInfo = d14;
        this.DemuxResT = d15;
        this.DemuxUtils = d16;
        this.IAudioInputStream = d17;
        this.IAudioMetaInformation = d18;
        this.LeadingZeros = d19;
        this.MyStream = d20;
        this.AacAudioCodec = d21;
        this.AacMetaDataModel = d22;
        this.QTMovieT = nArray;
        this.SampleDuration = nArray2;
    }

    public LevelsModel(LevelsModel aslFQuXDSXcUlvqmnZBFpYW) {
        this(aslFQuXDSXcUlvqmnZBFpYW.DSP(), aslFQuXDSXcUlvqmnZBFpYW.FFT(), aslFQuXDSXcUlvqmnZBFpYW.responseView(), aslFQuXDSXcUlvqmnZBFpYW.AdditionalMetadataValue(), aslFQuXDSXcUlvqmnZBFpYW.AudioFileExtension(), aslFQuXDSXcUlvqmnZBFpYW.IAudioFileCodec(), aslFQuXDSXcUlvqmnZBFpYW.IAudioInputStream(), aslFQuXDSXcUlvqmnZBFpYW.IAudioMetaInformation(), aslFQuXDSXcUlvqmnZBFpYW.IBaseAudioCodec(), aslFQuXDSXcUlvqmnZBFpYW.MetaInfomationCopy(), aslFQuXDSXcUlvqmnZBFpYW.AacAudioCodec(), aslFQuXDSXcUlvqmnZBFpYW.AacMetaDataModel(), aslFQuXDSXcUlvqmnZBFpYW.BufferedAacReader(), aslFQuXDSXcUlvqmnZBFpYW.AiffAudioCodec(), aslFQuXDSXcUlvqmnZBFpYW.AiffMetaDataModel(), aslFQuXDSXcUlvqmnZBFpYW.AlacAudioCodec(), aslFQuXDSXcUlvqmnZBFpYW.AlacMetaDataModel(), aslFQuXDSXcUlvqmnZBFpYW.BufferedAlacReader(), aslFQuXDSXcUlvqmnZBFpYW.AlacContextModel(), aslFQuXDSXcUlvqmnZBFpYW.AlacDecoderUtils(), aslFQuXDSXcUlvqmnZBFpYW.AlacFile(), aslFQuXDSXcUlvqmnZBFpYW.AlacInputStream(), aslFQuXDSXcUlvqmnZBFpYW.AlacUtils(), aslFQuXDSXcUlvqmnZBFpYW.ChunkInfo(), aslFQuXDSXcUlvqmnZBFpYW.DemuxResT(), aslFQuXDSXcUlvqmnZBFpYW.DemuxUtils(), aslFQuXDSXcUlvqmnZBFpYW.LeadingZeros(), aslFQuXDSXcUlvqmnZBFpYW.MyStream(), aslFQuXDSXcUlvqmnZBFpYW.QTMovieT(), aslFQuXDSXcUlvqmnZBFpYW.SampleDuration());
    }

    public double DSP() {
        return this.DSP;
    }

    public double FFT() {
        return this.FFT;
    }

    public double responseView() {
        return this.responseView;
    }

    public double AdditionalMetadataValue() {
        return this.AdditionalMetadataValue;
    }

    public double AudioFileExtension() {
        return this.AudioFileExtension;
    }

    public double IAudioFileCodec() {
        return this.IAudioFileCodec;
    }

    public int IAudioInputStream() {
        return this.AiffAudioCodec;
    }

    public int IAudioMetaInformation() {
        return this.AiffMetaDataModel;
    }

    public double IBaseAudioCodec() {
        return this.AlacAudioCodec;
    }

    public double MetaInfomationCopy() {
        return this.AlacMetaDataModel;
    }

    public double AacAudioCodec() {
        return this.BufferedAlacReader;
    }

    public double AacMetaDataModel() {
        return this.BufferedAacReader;
    }

    public double BufferedAacReader() {
        return this.IBaseAudioCodec;
    }

    public double AiffAudioCodec() {
        return this.MetaInfomationCopy;
    }

    public String AiffMetaDataModel() {
        return this.AlacContextModel;
    }

    public String AlacAudioCodec() {
        return this.AlacDecoderUtils;
    }

    public boolean AlacMetaDataModel() {
        return this.AlacFile;
    }

    public boolean BufferedAlacReader() {
        return this.AlacInputStream;
    }

    public double AlacContextModel() {
        return this.AlacUtils;
    }

    public double AlacDecoderUtils() {
        return this.ChunkInfo;
    }

    public double AlacFile() {
        return this.DemuxResT;
    }

    public double AlacInputStream() {
        return this.DemuxUtils;
    }

    public double AlacUtils() {
        return this.IAudioInputStream;
    }

    public double ChunkInfo() {
        return this.IAudioMetaInformation;
    }

    public double DemuxResT() {
        return this.LeadingZeros;
    }

    public double DemuxUtils() {
        return this.MyStream;
    }

    public double LeadingZeros() {
        return this.AacAudioCodec;
    }

    public double MyStream() {
        return this.AacMetaDataModel;
    }

    public int[] QTMovieT() {
        return this.QTMovieT;
    }

    public int[] SampleDuration() {
        return this.SampleDuration;
    }
}

