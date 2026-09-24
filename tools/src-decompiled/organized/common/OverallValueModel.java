/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.nio.file.Path;
import sdfgjkljljoftrytrszgijpokjprs.ValueModel;

public class OverallValueModel {
    private Path DSP;
    private String FFT;
    private String responseView;
    private String AdditionalMetadataValue;
    private ValueModel AudioFileExtension;
    private ValueModel IAudioFileCodec;
    private ValueModel IAudioInputStream;
    private ValueModel IAudioMetaInformation;
    private ValueModel IBaseAudioCodec;
    private ValueModel MetaInfomationCopy;
    private ValueModel AacAudioCodec;
    private ValueModel AacMetaDataModel;
    private ValueModel BufferedAacReader;
    private ValueModel AiffAudioCodec;
    private ValueModel AiffMetaDataModel;
    private ValueModel AlacAudioCodec;
    private ValueModel AlacMetaDataModel;
    private ValueModel BufferedAlacReader;
    private ValueModel AlacContextModel;
    private ValueModel AlacDecoderUtils;
    private ValueModel AlacFile;
    private String AlacInputStream;
    private ValueModel AlacUtils;
    private ValueModel ChunkInfo;

    public String DSP() {
        return this.FFT;
    }

    public void DSP(String string) {
        this.FFT = string;
    }

    public String FFT() {
        return this.responseView;
    }

    public void FFT(String string) {
        this.responseView = string;
    }

    public ValueModel responseView() {
        return this.AudioFileExtension;
    }

    public void DSP(int n) {
        this.AudioFileExtension = new ValueModel(n, "Hz");
    }

    public ValueModel AdditionalMetadataValue() {
        return this.IAudioFileCodec;
    }

    public void FFT(int n) {
        this.IAudioFileCodec = new ValueModel(n, "bit");
    }

    public ValueModel AudioFileExtension() {
        return this.IBaseAudioCodec;
    }

    public void DSP(ValueModel rOJuPbsRZYkDSnyfZISOUMD) {
        this.IBaseAudioCodec = rOJuPbsRZYkDSnyfZISOUMD;
    }

    public ValueModel IAudioFileCodec() {
        return this.MetaInfomationCopy;
    }

    public void FFT(ValueModel rOJuPbsRZYkDSnyfZISOUMD) {
        this.MetaInfomationCopy = rOJuPbsRZYkDSnyfZISOUMD;
    }

    public ValueModel IAudioInputStream() {
        return this.BufferedAacReader;
    }

    public void responseView(ValueModel rOJuPbsRZYkDSnyfZISOUMD) {
        this.BufferedAacReader = rOJuPbsRZYkDSnyfZISOUMD;
    }

    public ValueModel IAudioMetaInformation() {
        return this.AiffAudioCodec;
    }

    public void AdditionalMetadataValue(ValueModel rOJuPbsRZYkDSnyfZISOUMD) {
        this.AiffAudioCodec = rOJuPbsRZYkDSnyfZISOUMD;
    }

    public ValueModel IBaseAudioCodec() {
        return this.AlacMetaDataModel;
    }

    public void AudioFileExtension(ValueModel rOJuPbsRZYkDSnyfZISOUMD) {
        this.AlacMetaDataModel = rOJuPbsRZYkDSnyfZISOUMD;
    }

    public ValueModel MetaInfomationCopy() {
        return this.BufferedAlacReader;
    }

    public void IAudioFileCodec(ValueModel rOJuPbsRZYkDSnyfZISOUMD) {
        this.BufferedAlacReader = rOJuPbsRZYkDSnyfZISOUMD;
    }

    public ValueModel AacAudioCodec() {
        return this.AlacContextModel;
    }

    public void IAudioInputStream(ValueModel rOJuPbsRZYkDSnyfZISOUMD) {
        this.AlacContextModel = rOJuPbsRZYkDSnyfZISOUMD;
    }

    public ValueModel AacMetaDataModel() {
        return this.AlacDecoderUtils;
    }

    public void IAudioMetaInformation(ValueModel rOJuPbsRZYkDSnyfZISOUMD) {
        this.AlacDecoderUtils = rOJuPbsRZYkDSnyfZISOUMD;
    }

    public ValueModel BufferedAacReader() {
        return this.AacAudioCodec;
    }

    public void IBaseAudioCodec(ValueModel rOJuPbsRZYkDSnyfZISOUMD) {
        this.AacAudioCodec = rOJuPbsRZYkDSnyfZISOUMD;
    }

    public ValueModel AiffAudioCodec() {
        return this.AacMetaDataModel;
    }

    public void MetaInfomationCopy(ValueModel rOJuPbsRZYkDSnyfZISOUMD) {
        this.AacMetaDataModel = rOJuPbsRZYkDSnyfZISOUMD;
    }

    public ValueModel AiffMetaDataModel() {
        return this.AiffMetaDataModel;
    }

    public void AacAudioCodec(ValueModel rOJuPbsRZYkDSnyfZISOUMD) {
        this.AiffMetaDataModel = rOJuPbsRZYkDSnyfZISOUMD;
    }

    public ValueModel AlacAudioCodec() {
        return this.AlacAudioCodec;
    }

    public void AacMetaDataModel(ValueModel rOJuPbsRZYkDSnyfZISOUMD) {
        this.AlacAudioCodec = rOJuPbsRZYkDSnyfZISOUMD;
    }

    public ValueModel AlacMetaDataModel() {
        return this.IAudioInputStream;
    }

    public void BufferedAacReader(ValueModel rOJuPbsRZYkDSnyfZISOUMD) {
        this.IAudioInputStream = rOJuPbsRZYkDSnyfZISOUMD;
    }

    public ValueModel BufferedAlacReader() {
        return this.IAudioMetaInformation;
    }

    public void AiffAudioCodec(ValueModel rOJuPbsRZYkDSnyfZISOUMD) {
        this.IAudioMetaInformation = rOJuPbsRZYkDSnyfZISOUMD;
    }

    public ValueModel AlacContextModel() {
        return this.AlacFile;
    }

    public void AiffMetaDataModel(ValueModel rOJuPbsRZYkDSnyfZISOUMD) {
        if (rOJuPbsRZYkDSnyfZISOUMD.DSP() instanceof String) {
            this.AlacFile = new ValueModel(-1, "Hz");
        } else if (rOJuPbsRZYkDSnyfZISOUMD.DSP() instanceof Double) {
            this.AlacFile = new ValueModel((int)((Double)rOJuPbsRZYkDSnyfZISOUMD.DSP() * 1000.0), "Hz");
        }
    }

    public String AlacDecoderUtils() {
        return this.AlacInputStream;
    }

    void responseView(String string) {
        this.AlacInputStream = string;
    }

    public ValueModel AlacFile() {
        return this.AlacUtils;
    }

    void AlacAudioCodec(ValueModel rOJuPbsRZYkDSnyfZISOUMD) {
        this.AlacUtils = rOJuPbsRZYkDSnyfZISOUMD;
    }

    public ValueModel AlacInputStream() {
        return this.ChunkInfo;
    }

    void AlacMetaDataModel(ValueModel rOJuPbsRZYkDSnyfZISOUMD) {
        this.ChunkInfo = rOJuPbsRZYkDSnyfZISOUMD;
    }

    public Path AlacUtils() {
        return this.DSP;
    }

    public void DSP(Path path) {
        this.DSP = path;
    }

    public String ChunkInfo() {
        return this.AdditionalMetadataValue;
    }

    public void AdditionalMetadataValue(String string) {
        this.AdditionalMetadataValue = string;
    }
}

