/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.Metadata;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;
import sdfgjkljljoftrytrszgijpokjprs.VorbisString;

public class VorbisComment
extends Metadata {
    protected byte[] DSP = new byte[0];
    protected int FFT = 0;
    protected VorbisString[] responseView;

    public VorbisComment(BitInputStream hrgPgVhiQYIZXeWOluMaPwR2, int n, boolean bl) throws IOException {
        super(bl, n);
        int n2 = hrgPgVhiQYIZXeWOluMaPwR2.AudioFileExtension();
        this.DSP = new byte[n2];
        hrgPgVhiQYIZXeWOluMaPwR2.DSP(this.DSP, this.DSP.length);
        this.FFT = hrgPgVhiQYIZXeWOluMaPwR2.AudioFileExtension();
        if (this.FFT > 0) {
            this.responseView = new VorbisString[this.FFT];
        }
        for (int i = 0; i < this.FFT; ++i) {
            this.responseView[i] = new VorbisString(hrgPgVhiQYIZXeWOluMaPwR2);
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("VendorString '" + new String(this.DSP) + "'\n");
        stringBuffer.append("VorbisComment (count=" + this.FFT + ")");
        for (int i = 0; i < this.FFT; ++i) {
            stringBuffer.append("\n\t" + this.responseView[i].toString());
        }
        return stringBuffer.toString();
    }
}

