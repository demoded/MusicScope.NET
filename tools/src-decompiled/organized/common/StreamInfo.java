/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.Metadata;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;

public class StreamInfo
extends Metadata {
    private final byte[] DSP = new byte[16];
    private final int FFT;
    private final int responseView;
    private final int AdditionalMetadataValue;
    private final int AudioFileExtension;
    private final int IAudioMetaInformation;
    private final int IBaseAudioCodec;
    private final int MetaInfomationCopy;
    private long AacAudioCodec;

    public StreamInfo(BitInputStream hrgPgVhiQYIZXeWOluMaPwR2, int n, boolean bl) throws IOException {
        super(bl, n);
        int n2 = 0;
        this.FFT = hrgPgVhiQYIZXeWOluMaPwR2.responseView(16);
        n2 += 16;
        this.responseView = hrgPgVhiQYIZXeWOluMaPwR2.responseView(16);
        n2 += 16;
        this.AdditionalMetadataValue = hrgPgVhiQYIZXeWOluMaPwR2.responseView(24);
        n2 += 24;
        this.AudioFileExtension = hrgPgVhiQYIZXeWOluMaPwR2.responseView(24);
        n2 += 24;
        this.IAudioMetaInformation = hrgPgVhiQYIZXeWOluMaPwR2.responseView(20);
        n2 += 20;
        this.IBaseAudioCodec = hrgPgVhiQYIZXeWOluMaPwR2.responseView(3) + 1;
        n2 += 3;
        this.MetaInfomationCopy = hrgPgVhiQYIZXeWOluMaPwR2.responseView(5) + 1;
        n2 += 5;
        this.AacAudioCodec = hrgPgVhiQYIZXeWOluMaPwR2.IAudioFileCodec(36);
        n2 += 36;
        hrgPgVhiQYIZXeWOluMaPwR2.DSP(this.DSP, 16);
        hrgPgVhiQYIZXeWOluMaPwR2.DSP(null, n -= (n2 += 128) / 8);
    }

    public String toString() {
        return "StreamInfo: BlockSize=" + this.FFT + "-" + this.responseView + " FrameSize" + this.AdditionalMetadataValue + "-" + this.AudioFileExtension + " SampleRate=" + this.IAudioMetaInformation + " Channels=" + this.IBaseAudioCodec + " BPS=" + this.MetaInfomationCopy + " TotalSamples=" + this.AacAudioCodec;
    }

    public int responseView() {
        return this.responseView;
    }

    public int AdditionalMetadataValue() {
        return this.FFT;
    }

    public long AudioFileExtension() {
        return this.AacAudioCodec;
    }

    public int IAudioFileCodec() {
        return this.IAudioMetaInformation;
    }

    public int IAudioInputStream() {
        return this.MetaInfomationCopy;
    }

    public int IAudioMetaInformation() {
        return this.IBaseAudioCodec;
    }
}

