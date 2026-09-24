/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.Metadata;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;

public class Application
extends Metadata {
    private byte[] DSP = new byte[4];
    private byte[] FFT;

    public Application(BitInputStream hrgPgVhiQYIZXeWOluMaPwR2, int n, boolean bl) throws IOException {
        super(bl, n);
        hrgPgVhiQYIZXeWOluMaPwR2.DSP(this.DSP, 4);
        if ((n -= 4) > 0) {
            this.FFT = new byte[n];
            hrgPgVhiQYIZXeWOluMaPwR2.DSP(this.FFT, n);
        }
    }
}

