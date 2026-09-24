/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.Channel;
import sdfgjkljljoftrytrszgijpokjprs.Header;

public class Frame {
    public Header DSP;
    public Channel[] FFT = new Channel[8];
    private short responseView;

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Frame Header: " + this.DSP + "\n");
        for (int i = 0; i < this.DSP.responseView; ++i) {
            stringBuffer.append("\tFrame Data " + this.FFT[i].toString() + "\n");
        }
        stringBuffer.append("\tFrame Footer: " + this.responseView);
        return stringBuffer.toString();
    }

    public short DSP() {
        return this.responseView;
    }

    public void DSP(short s) {
        this.responseView = s;
    }
}

