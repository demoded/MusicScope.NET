/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;

public class VorbisString {
    protected byte[] DSP;

    public VorbisString(BitInputStream hrgPgVhiQYIZXeWOluMaPwR2) throws IOException {
        int n = hrgPgVhiQYIZXeWOluMaPwR2.AudioFileExtension();
        if (n == 0) {
            return;
        }
        this.DSP = new byte[n];
        hrgPgVhiQYIZXeWOluMaPwR2.DSP(this.DSP, this.DSP.length);
    }

    public String toString() {
        String string;
        try {
            string = new String(this.DSP, "UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            string = "";
        }
        return string;
    }
}

