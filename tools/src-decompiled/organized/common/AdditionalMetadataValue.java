/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

public class AdditionalMetadataValue<T> {
    private T DSP;
    private boolean FFT;

    public AdditionalMetadataValue(T t) {
        this.DSP = t;
        this.FFT = true;
    }

    public AdditionalMetadataValue() {
        this.FFT = false;
    }

    public T DSP() {
        return this.DSP;
    }

    public boolean FFT() {
        return this.FFT;
    }
}

