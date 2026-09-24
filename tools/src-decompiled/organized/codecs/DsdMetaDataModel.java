/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.FFT;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormatCode;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;

public class DsdMetaDataModel
implements IAudioMetaInformation {
    private String DSP;
    private BitsPerSample FFT;
    private AudioExtension responseView;
    private AudioFormatCode AdditionalMetadataValue;
    private long AudioFileExtension;
    private int IAudioFileCodec;
    private int IAudioInputStream;
    private int IAudioMetaInformation;
    private long IBaseAudioCodec;
    private FFT MetaInfomationCopy;

    @Override
    public int DSP() {
        return this.IAudioInputStream;
    }

    @Override
    public BitsPerSample FFT() {
        return this.FFT;
    }

    @Override
    public int responseView() {
        return this.IAudioFileCodec;
    }

    @Override
    public int AdditionalMetadataValue() {
        return this.IAudioMetaInformation;
    }

    @Override
    public AudioFormatCode AudioFileExtension() {
        return this.AdditionalMetadataValue;
    }

    @Override
    public long IAudioFileCodec() {
        return this.IAudioInputStream() / (long)this.responseView();
    }

    @Override
    public long IAudioInputStream() {
        return this.AudioFileExtension;
    }

    @Override
    public String IAudioMetaInformation() {
        return this.DSP;
    }

    @Override
    public int IBaseAudioCodec() {
        return (int)this.IBaseAudioCodec;
    }

    @Override
    public AudioExtension MetaInfomationCopy() {
        return this.responseView;
    }

    @Override
    public long AacAudioCodec() {
        return -1L;
    }

    @Override
    public long AacMetaDataModel() {
        return -1L;
    }

    @Override
    public FFT BufferedAacReader() {
        return this.MetaInfomationCopy;
    }

    public void DSP(FFT qPeIwmpzLZIktKLXAJOQHcO) {
        this.MetaInfomationCopy = qPeIwmpzLZIktKLXAJOQHcO;
    }

    void DSP(int n) {
        this.IAudioInputStream = n;
    }

    void DSP(BitsPerSample kFVWmcqOBgYFxPgeswapvPe) {
        this.FFT = kFVWmcqOBgYFxPgeswapvPe;
    }

    void FFT(int n) {
        this.IAudioFileCodec = n;
    }

    void responseView(int n) {
        this.IAudioMetaInformation = n;
    }

    void DSP(AudioFormatCode tWPHktttPCBsYgoUXmAgKnC) {
        this.AdditionalMetadataValue = tWPHktttPCBsYgoUXmAgKnC;
    }

    void DSP(long l) {
        this.AudioFileExtension = l;
    }

    void FFT(long l) {
        this.IBaseAudioCodec = l;
    }

    void DSP(AudioExtension aGpEswewZOXTtopHPFqtErb) {
        this.responseView = aGpEswewZOXTtopHPFqtErb;
    }
}

