/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.nio.ByteBuffer;
import java.util.Random;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.FIRDecimationFilter;

public class ChannelDecoder {
    private final int IBaseAudioCodec;
    private final int MetaInfomationCopy;
    private int AacAudioCodec;
    private final BitsPerSample AacMetaDataModel;
    private double[] BufferedAacReader;
    private final double AiffAudioCodec = Math.pow(10.0, -7.3);
    private final Random AiffMetaDataModel = new Random();
    FIRDecimationFilter DSP = new FIRDecimationFilter(0);
    FIRDecimationFilter FFT = new FIRDecimationFilter(1);
    FIRDecimationFilter responseView = new FIRDecimationFilter(2);
    FIRDecimationFilter AdditionalMetadataValue = new FIRDecimationFilter(7);
    FIRDecimationFilter AudioFileExtension = new FIRDecimationFilter(3);
    FIRDecimationFilter IAudioFileCodec = new FIRDecimationFilter(8);
    FIRDecimationFilter IAudioInputStream = new FIRDecimationFilter(4);
    FIRDecimationFilter IAudioMetaInformation = new FIRDecimationFilter(5);

    public ChannelDecoder(int n, int n2, BitsPerSample kFVWmcqOBgYFxPgeswapvPe, int n3, boolean bl) {
        this.IBaseAudioCodec = n;
        this.MetaInfomationCopy = n2;
        this.AacMetaDataModel = kFVWmcqOBgYFxPgeswapvPe;
        this.AacAudioCodec = !bl ? 0 : 1;
        this.BufferedAacReader = new double[n3];
    }

    public byte[] DSP(byte[] byArray) {
        int n;
        int n2 = n = byArray.length;
        if (this.IBaseAudioCodec == 2822400) {
            if (this.AacMetaDataModel == BitsPerSample.DSP) {
                this.DSP.DSP(this.AacAudioCodec, 0, n2, byArray, this.BufferedAacReader);
                this.IAudioMetaInformation.DSP(0, 2, n2, this.BufferedAacReader, this.BufferedAacReader);
                n2 /= 2;
            } else {
                this.IAudioMetaInformation.FFT(1, 2, n2, byArray, this.BufferedAacReader);
                n2 /= 2;
            }
        }
        if (this.IBaseAudioCodec == 5644800) {
            if (this.AacMetaDataModel == BitsPerSample.DSP) {
                this.FFT.DSP(this.AacAudioCodec, 1, n2, byArray, this.BufferedAacReader);
                this.IAudioInputStream.DSP(0, 2, n2, this.BufferedAacReader, this.BufferedAacReader);
                n2 /= 2;
            } else {
                this.IAudioInputStream.FFT(1, 2, n2, byArray, this.BufferedAacReader);
                n2 /= 2;
            }
        }
        if (this.IBaseAudioCodec == 11289600) {
            if (this.AacMetaDataModel == BitsPerSample.DSP) {
                this.responseView.DSP(this.AacAudioCodec, 2, n2, byArray, this.BufferedAacReader);
                this.AudioFileExtension.DSP(0, 4, n2, this.BufferedAacReader, this.BufferedAacReader);
                n2 /= 4;
            } else {
                this.AudioFileExtension.FFT(1, 4, n2, byArray, this.BufferedAacReader);
                n2 /= 4;
            }
        }
        if (this.IBaseAudioCodec == 22579200) {
            if (this.AacMetaDataModel == BitsPerSample.DSP) {
                this.AdditionalMetadataValue.DSP(this.AacAudioCodec, 7, n2, byArray, this.BufferedAacReader);
                this.IAudioFileCodec.DSP(0, 2, n2, this.BufferedAacReader, this.BufferedAacReader);
                this.AudioFileExtension.DSP(1, 4, n2 /= 2, this.BufferedAacReader, this.BufferedAacReader);
                n2 /= 4;
            } else {
                this.IAudioFileCodec.FFT(1, 2, n2, byArray, this.BufferedAacReader);
                this.AudioFileExtension.DSP(1, 4, n2 /= 2, this.BufferedAacReader, this.BufferedAacReader);
                n2 /= 4;
            }
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2 * BitsPerSample.AdditionalMetadataValue.FFT());
        for (int i = 0; i < n2; ++i) {
            this.DSP(BitsPerSample.AdditionalMetadataValue, byteBuffer, BitsPerSample.AdditionalMetadataValue.FFT(this.BufferedAacReader[i]));
        }
        return byteBuffer.array();
    }

    private void DSP(BitsPerSample kFVWmcqOBgYFxPgeswapvPe, ByteBuffer byteBuffer, long l) {
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(8);
        byteBuffer2.putLong(l);
        for (int i = 7; i >= 8 - kFVWmcqOBgYFxPgeswapvPe.FFT(); --i) {
            byteBuffer.put(byteBuffer2.get(i));
        }
    }
}

