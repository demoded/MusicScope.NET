/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.LeadingZeros;

class AlacFile {
    private byte[] DSP;
    private int FFT = 0;
    private int responseView = 0;
    private int AdditionalMetadataValue = 0;
    private int AudioFileExtension = 0;
    private int IAudioFileCodec = 0;
    private LeadingZeros IAudioInputStream = new LeadingZeros();
    private int IAudioMetaInformation = 16384;
    private int[] IBaseAudioCodec = new int[this.IAudioMetaInformation];
    private int[] MetaInfomationCopy = new int[this.IAudioMetaInformation];
    private int[] AacAudioCodec = new int[this.IAudioMetaInformation];
    private int[] AacMetaDataModel = new int[this.IAudioMetaInformation];
    private int[] BufferedAacReader = new int[this.IAudioMetaInformation];
    private int[] AiffAudioCodec = new int[this.IAudioMetaInformation];
    private int AiffMetaDataModel = 0;
    private int AlacAudioCodec = 0;
    private int AlacMetaDataModel = 0;
    private int BufferedAlacReader = 0;
    private int AlacContextModel = 0;
    private int AlacDecoderUtils = 0;
    private int AlacFile = 0;
    private int AlacInputStream = 0;
    private int AlacUtils = 0;
    private int ChunkInfo = 0;
    private int DemuxResT = 0;
    private int[] DemuxUtils = new int[1024];
    private int[] LeadingZeros = new int[1024];
    private int[] MyStream = new int[1024];

    AlacFile() {
    }

    public byte[] DSP() {
        return this.DSP;
    }

    public void DSP(byte[] byArray) {
        this.DSP = byArray;
    }

    public int FFT() {
        return this.FFT;
    }

    public void DSP(int n) {
        this.FFT = n;
    }

    public int responseView() {
        return this.responseView;
    }

    public void FFT(int n) {
        this.responseView = n;
    }

    public void responseView(int n) {
        this.AdditionalMetadataValue = n;
    }

    public int AdditionalMetadataValue() {
        return this.AudioFileExtension;
    }

    public void AdditionalMetadataValue(int n) {
        this.AudioFileExtension = n;
    }

    public int AudioFileExtension() {
        return this.IAudioFileCodec;
    }

    public void AudioFileExtension(int n) {
        this.IAudioFileCodec = n;
    }

    public LeadingZeros IAudioFileCodec() {
        return this.IAudioInputStream;
    }

    public int[] IAudioInputStream() {
        return this.IBaseAudioCodec;
    }

    public int[] IAudioMetaInformation() {
        return this.MetaInfomationCopy;
    }

    public int[] IBaseAudioCodec() {
        return this.AacAudioCodec;
    }

    public void DSP(int[] nArray) {
        this.AacAudioCodec = nArray;
    }

    public int[] MetaInfomationCopy() {
        return this.AacMetaDataModel;
    }

    public void FFT(int[] nArray) {
        this.AacMetaDataModel = nArray;
    }

    public int[] AacAudioCodec() {
        return this.BufferedAacReader;
    }

    public int[] AacMetaDataModel() {
        return this.AiffAudioCodec;
    }

    public int BufferedAacReader() {
        return this.AiffMetaDataModel;
    }

    public void IAudioFileCodec(int n) {
        this.AiffMetaDataModel = n;
    }

    public void IAudioInputStream(int n) {
        this.AlacAudioCodec = n;
    }

    public int AiffAudioCodec() {
        return this.AlacMetaDataModel;
    }

    public void IAudioMetaInformation(int n) {
        this.AlacMetaDataModel = n;
    }

    public int AiffMetaDataModel() {
        return this.BufferedAlacReader;
    }

    public void IBaseAudioCodec(int n) {
        this.BufferedAlacReader = n;
    }

    public int AlacAudioCodec() {
        return this.AlacContextModel;
    }

    public void MetaInfomationCopy(int n) {
        this.AlacContextModel = n;
    }

    public int AlacMetaDataModel() {
        return this.AlacDecoderUtils;
    }

    public void AacAudioCodec(int n) {
        this.AlacDecoderUtils = n;
    }

    public void AacMetaDataModel(int n) {
        this.AlacFile = n;
    }

    public void BufferedAacReader(int n) {
        this.AlacInputStream = n;
    }

    public void AiffAudioCodec(int n) {
        this.AlacUtils = n;
    }

    public void AiffMetaDataModel(int n) {
        this.ChunkInfo = n;
    }

    public void AlacAudioCodec(int n) {
        this.DemuxResT = n;
    }

    public int[] BufferedAlacReader() {
        return this.DemuxUtils;
    }

    public int[] AlacContextModel() {
        return this.LeadingZeros;
    }

    public int[] AlacDecoderUtils() {
        return this.MyStream;
    }
}

