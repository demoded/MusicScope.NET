/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import sdfgjkljljoftrytrszgijpokjprs.Metadata;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;

public class Picture
extends Metadata {
    private DSP FFT;
    private int responseView;
    private String AdditionalMetadataValue;
    private int AudioFileExtension;
    private String IAudioMetaInformation;
    private int IBaseAudioCodec;
    private int MetaInfomationCopy;
    private int AacAudioCodec;
    private int AacMetaDataModel;
    private int BufferedAacReader;
    protected byte[] DSP;

    public Picture(BitInputStream hrgPgVhiQYIZXeWOluMaPwR2, int n, boolean bl) throws IOException {
        super(bl, n);
        int n2 = 0;
        int n3 = hrgPgVhiQYIZXeWOluMaPwR2.responseView(32);
        for (DSP rHAjVyBgPhqkQKsOvJMPMYn2 : sdfgjkljljoftrytrszgijpokjprs.Picture$rHAjVyBgPhqkQKsOvJMPMYn.values()) {
            if (rHAjVyBgPhqkQKsOvJMPMYn2.AlacInputStream != n3) continue;
            this.FFT = rHAjVyBgPhqkQKsOvJMPMYn2;
        }
        n2 += 32;
        this.responseView = hrgPgVhiQYIZXeWOluMaPwR2.responseView(32);
        n2 += 32;
        byte[] byArray = new byte[this.responseView];
        hrgPgVhiQYIZXeWOluMaPwR2.DSP(byArray, this.responseView);
        n2 += this.responseView * 8;
        this.AdditionalMetadataValue = new String(byArray);
        this.AudioFileExtension = hrgPgVhiQYIZXeWOluMaPwR2.responseView(32);
        n2 += 32;
        if (this.AudioFileExtension != 0) {
            byArray = new byte[this.AudioFileExtension];
            hrgPgVhiQYIZXeWOluMaPwR2.DSP(byArray, this.AudioFileExtension);
            n2 += this.AudioFileExtension * 8;
            try {
                this.IAudioMetaInformation = new String(byArray, "UTF-8");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {}
        } else {
            this.IAudioMetaInformation = new String("");
        }
        this.IBaseAudioCodec = hrgPgVhiQYIZXeWOluMaPwR2.responseView(32);
        n2 += 32;
        this.MetaInfomationCopy = hrgPgVhiQYIZXeWOluMaPwR2.responseView(32);
        n2 += 32;
        this.AacAudioCodec = hrgPgVhiQYIZXeWOluMaPwR2.responseView(32);
        n2 += 32;
        this.AacMetaDataModel = hrgPgVhiQYIZXeWOluMaPwR2.responseView(32);
        n2 += 32;
        this.BufferedAacReader = hrgPgVhiQYIZXeWOluMaPwR2.responseView(32);
        n2 += 32;
        this.DSP = new byte[this.BufferedAacReader];
        hrgPgVhiQYIZXeWOluMaPwR2.DSP(this.DSP, this.BufferedAacReader);
        if ((n -= (n2 += this.BufferedAacReader * 8) / 8) > 0) {
            hrgPgVhiQYIZXeWOluMaPwR2.DSP(null, n);
        }
    }

    public String toString() {
        return "Picture:  Type=" + (Object)((Object)this.FFT) + " MIME type=" + this.AdditionalMetadataValue + " Description=\"" + this.IAudioMetaInformation + "\" Pixels (WxH)=" + this.IBaseAudioCodec + "x" + this.MetaInfomationCopy + " Color Depth=" + this.AacAudioCodec + " Color Count=" + this.AacMetaDataModel + " Picture Size (bytes)=" + this.BufferedAacReader + " last =" + this.IAudioFileCodec;
    }

    public static enum DSP {
        DSP(0),
        FFT(1),
        responseView(2),
        AdditionalMetadataValue(3),
        AudioFileExtension(4),
        IAudioFileCodec(5),
        IAudioInputStream(6),
        IAudioMetaInformation(7),
        IBaseAudioCodec(8),
        MetaInfomationCopy(9),
        AacAudioCodec(10),
        AacMetaDataModel(11),
        BufferedAacReader(12),
        AiffAudioCodec(13),
        AiffMetaDataModel(14),
        AlacAudioCodec(15),
        AlacMetaDataModel(16),
        BufferedAlacReader(17),
        AlacContextModel(18),
        AlacDecoderUtils(19),
        AlacFile(20);

        final int AlacInputStream;

        private DSP(int n2) {
            this.AlacInputStream = n2;
        }
    }
}

