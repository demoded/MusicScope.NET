/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.FFT;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormatCode;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;

public class MetaInfomationCopy
implements IAudioMetaInformation {
    private int DSP;
    private BitsPerSample FFT;
    private int responseView;
    private int AdditionalMetadataValue;
    private AudioFormatCode AudioFileExtension;
    private long IAudioFileCodec;
    private long IAudioInputStream;
    private String IAudioMetaInformation;
    private int IBaseAudioCodec;
    private AudioExtension MetaInfomationCopy;
    private long AacAudioCodec;
    private long AacMetaDataModel;
    private FFT BufferedAacReader;

    public MetaInfomationCopy(int n, BitsPerSample kFVWmcqOBgYFxPgeswapvPe, int n2, int n3, AudioFormatCode tWPHktttPCBsYgoUXmAgKnC, long l, long l2, String string, int n4, AudioExtension aGpEswewZOXTtopHPFqtErb, long l3, long l4, FFT qPeIwmpzLZIktKLXAJOQHcO) {
        this.DSP = n;
        this.FFT = kFVWmcqOBgYFxPgeswapvPe;
        this.responseView = n2;
        this.AdditionalMetadataValue = n3;
        this.AudioFileExtension = tWPHktttPCBsYgoUXmAgKnC;
        this.IAudioFileCodec = l;
        this.IAudioInputStream = l2;
        this.IAudioMetaInformation = string;
        this.IBaseAudioCodec = n4;
        this.MetaInfomationCopy = aGpEswewZOXTtopHPFqtErb;
        this.AacAudioCodec = l3;
        this.AacMetaDataModel = l4;
        this.BufferedAacReader = qPeIwmpzLZIktKLXAJOQHcO;
    }

    public MetaInfomationCopy(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA) {
        this(yGjBevanihqaxYKnUNtrNeA.DSP(), yGjBevanihqaxYKnUNtrNeA.FFT(), yGjBevanihqaxYKnUNtrNeA.responseView(), yGjBevanihqaxYKnUNtrNeA.AdditionalMetadataValue(), yGjBevanihqaxYKnUNtrNeA.AudioFileExtension(), yGjBevanihqaxYKnUNtrNeA.IAudioFileCodec(), yGjBevanihqaxYKnUNtrNeA.IAudioInputStream(), yGjBevanihqaxYKnUNtrNeA.IAudioMetaInformation(), yGjBevanihqaxYKnUNtrNeA.IBaseAudioCodec(), yGjBevanihqaxYKnUNtrNeA.MetaInfomationCopy(), yGjBevanihqaxYKnUNtrNeA.AacAudioCodec(), yGjBevanihqaxYKnUNtrNeA.AacMetaDataModel(), yGjBevanihqaxYKnUNtrNeA.BufferedAacReader());
    }

    @Override
    public int DSP() {
        return this.DSP;
    }

    @Override
    public BitsPerSample FFT() {
        return this.FFT;
    }

    @Override
    public int responseView() {
        return this.responseView;
    }

    @Override
    public int AdditionalMetadataValue() {
        return this.AdditionalMetadataValue;
    }

    @Override
    public AudioFormatCode AudioFileExtension() {
        return this.AudioFileExtension;
    }

    @Override
    public long IAudioFileCodec() {
        return this.IAudioFileCodec;
    }

    @Override
    public long IAudioInputStream() {
        return this.IAudioInputStream;
    }

    @Override
    public String IAudioMetaInformation() {
        return this.IAudioMetaInformation;
    }

    @Override
    public int IBaseAudioCodec() {
        return this.IBaseAudioCodec;
    }

    @Override
    public AudioExtension MetaInfomationCopy() {
        return this.MetaInfomationCopy;
    }

    @Override
    public long AacAudioCodec() {
        return this.AacAudioCodec;
    }

    @Override
    public long AacMetaDataModel() {
        return this.AacMetaDataModel;
    }

    public void DSP(int n) {
        this.DSP = n;
    }

    public void DSP(BitsPerSample kFVWmcqOBgYFxPgeswapvPe) {
        this.FFT = kFVWmcqOBgYFxPgeswapvPe;
    }

    public void FFT(int n) {
        this.responseView = n;
    }

    public void responseView(int n) {
        this.AdditionalMetadataValue = n;
    }

    public void DSP(AudioFormatCode tWPHktttPCBsYgoUXmAgKnC) {
        this.AudioFileExtension = tWPHktttPCBsYgoUXmAgKnC;
    }

    public void AdditionalMetadataValue(int n) {
        this.IAudioFileCodec = n;
    }

    public void DSP(long l) {
        this.IAudioInputStream = l;
    }

    @Override
    public FFT BufferedAacReader() {
        return this.BufferedAacReader;
    }
}

