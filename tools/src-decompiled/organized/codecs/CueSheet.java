/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.Metadata;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;
import sdfgjkljljoftrytrszgijpokjprs.CueTrack;

public class CueSheet
extends Metadata {
    protected byte[] DSP = new byte[129];
    protected long FFT = 0L;
    protected boolean responseView = false;
    protected int AdditionalMetadataValue = 0;
    protected CueTrack[] AudioFileExtension;

    public CueSheet(BitInputStream hrgPgVhiQYIZXeWOluMaPwR2, int n, boolean bl) throws IOException {
        super(bl, n);
        hrgPgVhiQYIZXeWOluMaPwR2.DSP(this.DSP, 128);
        this.FFT = hrgPgVhiQYIZXeWOluMaPwR2.IAudioFileCodec(64);
        this.responseView = hrgPgVhiQYIZXeWOluMaPwR2.responseView(1) != 0;
        hrgPgVhiQYIZXeWOluMaPwR2.DSP(2071);
        this.AdditionalMetadataValue = hrgPgVhiQYIZXeWOluMaPwR2.responseView(8);
        if (this.AdditionalMetadataValue > 0) {
            this.AudioFileExtension = new CueTrack[this.AdditionalMetadataValue];
            for (int i = 0; i < this.AdditionalMetadataValue; ++i) {
                this.AudioFileExtension[i] = new CueTrack(hrgPgVhiQYIZXeWOluMaPwR2);
            }
        }
    }
}

