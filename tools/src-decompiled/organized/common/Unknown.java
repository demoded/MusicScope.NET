/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.Metadata;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;

public class Unknown
extends Metadata {
    protected byte[] DSP;

    public Unknown(BitInputStream hrgPgVhiQYIZXeWOluMaPwR2, int n, boolean bl) throws IOException {
        super(bl, n);
        if (n > 0) {
            this.DSP = new byte[n];
            hrgPgVhiQYIZXeWOluMaPwR2.DSP(this.DSP, n);
        }
    }
}

