/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.CueIndex;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;

public class CueTrack {
    protected long DSP;
    protected byte FFT;
    protected byte[] responseView = new byte[13];
    protected int AdditionalMetadataValue;
    protected int AudioFileExtension;
    protected byte IAudioFileCodec;
    protected CueIndex[] IAudioInputStream;

    public CueTrack(BitInputStream hrgPgVhiQYIZXeWOluMaPwR2) throws IOException {
        this.DSP = hrgPgVhiQYIZXeWOluMaPwR2.IAudioFileCodec(64);
        this.FFT = (byte)hrgPgVhiQYIZXeWOluMaPwR2.responseView(8);
        hrgPgVhiQYIZXeWOluMaPwR2.DSP(this.responseView, 12);
        this.AdditionalMetadataValue = hrgPgVhiQYIZXeWOluMaPwR2.responseView(1);
        this.AudioFileExtension = hrgPgVhiQYIZXeWOluMaPwR2.responseView(1);
        hrgPgVhiQYIZXeWOluMaPwR2.DSP(110);
        this.IAudioFileCodec = (byte)hrgPgVhiQYIZXeWOluMaPwR2.responseView(8);
        if (this.IAudioFileCodec > 0) {
            this.IAudioInputStream = new CueIndex[this.IAudioFileCodec];
            for (int i = 0; i < this.IAudioFileCodec; ++i) {
                this.IAudioInputStream[i] = new CueIndex(hrgPgVhiQYIZXeWOluMaPwR2);
            }
        }
    }
}

