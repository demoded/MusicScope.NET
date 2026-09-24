/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.ReportingParameter;

public class LoudnessModel {
    double DSP;
    double FFT;
    @ReportingParameter(DSP="Max. M-Loudness:", FFT="dB")
    double responseView;
    @ReportingParameter(DSP="Max. S-Loudness:", FFT="dB")
    double AdditionalMetadataValue;
    @ReportingParameter(DSP="Integrated Loudness:", FFT="dB")
    double AudioFileExtension;
    @ReportingParameter(DSP="Loudness Range:", FFT="dB")
    double IAudioFileCodec;
    @ReportingParameter(DSP="PLR Avg.:", FFT="dB")
    double IAudioInputStream;
    int[] IAudioMetaInformation;
    int IBaseAudioCodec;
    int[] MetaInfomationCopy;
    int AacAudioCodec;
    int AacMetaDataModel;
    int BufferedAacReader;
    int AiffAudioCodec;
    double AiffMetaDataModel;

    public LoudnessModel(double d, double d2, double d3, double d4, double d5, double d6, int[] nArray, int n, int[] nArray2, int n2, int n3, int n4, int n5, double d7, double d8) {
        this.DSP = d;
        this.FFT = d2;
        this.responseView = d3;
        this.AdditionalMetadataValue = d4;
        this.AudioFileExtension = d5;
        this.IAudioFileCodec = d6;
        this.IAudioMetaInformation = nArray;
        this.IBaseAudioCodec = n;
        this.MetaInfomationCopy = nArray2;
        this.AacAudioCodec = n2;
        this.AacMetaDataModel = n3;
        this.BufferedAacReader = n4;
        this.AiffAudioCodec = n5;
        this.AiffMetaDataModel = d7;
        this.IAudioInputStream = d8;
    }

    public LoudnessModel(LoudnessModel nusIkxnhKbIEOcBmUnwuuOS) {
        this(nusIkxnhKbIEOcBmUnwuuOS.DSP(), nusIkxnhKbIEOcBmUnwuuOS.FFT(), nusIkxnhKbIEOcBmUnwuuOS.responseView(), nusIkxnhKbIEOcBmUnwuuOS.AdditionalMetadataValue(), nusIkxnhKbIEOcBmUnwuuOS.AudioFileExtension(), nusIkxnhKbIEOcBmUnwuuOS.IAudioFileCodec(), nusIkxnhKbIEOcBmUnwuuOS.IAudioInputStream(), nusIkxnhKbIEOcBmUnwuuOS.IAudioMetaInformation(), nusIkxnhKbIEOcBmUnwuuOS.IBaseAudioCodec(), nusIkxnhKbIEOcBmUnwuuOS.MetaInfomationCopy(), nusIkxnhKbIEOcBmUnwuuOS.AacAudioCodec(), nusIkxnhKbIEOcBmUnwuuOS.AacMetaDataModel(), nusIkxnhKbIEOcBmUnwuuOS.BufferedAacReader(), nusIkxnhKbIEOcBmUnwuuOS.AiffAudioCodec(), nusIkxnhKbIEOcBmUnwuuOS.AiffMetaDataModel());
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

    public int[] IAudioInputStream() {
        return this.IAudioMetaInformation;
    }

    public int IAudioMetaInformation() {
        return this.IBaseAudioCodec;
    }

    public int[] IBaseAudioCodec() {
        return this.MetaInfomationCopy;
    }

    public int MetaInfomationCopy() {
        return this.AacAudioCodec;
    }

    public int AacAudioCodec() {
        return this.AacMetaDataModel;
    }

    public int AacMetaDataModel() {
        return this.BufferedAacReader;
    }

    public int BufferedAacReader() {
        return this.AiffAudioCodec;
    }

    public double AiffAudioCodec() {
        return this.AiffMetaDataModel;
    }

    public double AiffMetaDataModel() {
        return this.IAudioInputStream;
    }
}

