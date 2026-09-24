/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.Metadata;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;
import sdfgjkljljoftrytrszgijpokjprs.SeekPoint;

public class SeekTable
extends Metadata {
    protected SeekPoint[] DSP;

    public SeekTable(BitInputStream hrgPgVhiQYIZXeWOluMaPwR2, int n, boolean bl) throws IOException {
        super(bl, n);
        int n2 = n / 18;
        this.DSP = new SeekPoint[n2];
        for (int i = 0; i < this.DSP.length; ++i) {
            this.DSP[i] = new SeekPoint(hrgPgVhiQYIZXeWOluMaPwR2);
        }
        if ((n -= n * 18) > 0) {
            hrgPgVhiQYIZXeWOluMaPwR2.DSP(null, n);
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SeekTable: points=" + this.DSP.length + "\n");
        for (int i = 0; i < this.DSP.length; ++i) {
            stringBuffer.append("\tPoint " + this.DSP[i].toString() + "\n");
        }
        return stringBuffer.toString();
    }
}

