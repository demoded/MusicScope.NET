/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

public enum BitsPerSample {
    DSP(1),
    FFT(8),
    responseView(16),
    AdditionalMetadataValue(24),
    AudioFileExtension(32),
    IAudioFileCodec(64);

    private final int IAudioInputStream;
    private final double IAudioMetaInformation;

    private BitsPerSample(int n2) {
        this.IAudioInputStream = n2;
        this.IAudioMetaInformation = Math.pow(2.0, n2 - 1);
    }

    public int DSP() {
        return this.IAudioInputStream;
    }

    public int FFT() {
        return this.IAudioInputStream / 8;
    }

    public double DSP(int n) {
        double d = (double)n / this.IAudioMetaInformation;
        return d;
    }

    public int DSP(double d) {
        double d2 = d * this.IAudioMetaInformation;
        return (int)d2;
    }

    public long FFT(double d) {
        return Math.round(d * this.IAudioMetaInformation);
    }

    public static BitsPerSample FFT(int n) {
        BitsPerSample[] kFVWmcqOBgYFxPgeswapvPeArray;
        for (BitsPerSample kFVWmcqOBgYFxPgeswapvPe : kFVWmcqOBgYFxPgeswapvPeArray = BitsPerSample.values()) {
            if (kFVWmcqOBgYFxPgeswapvPe.DSP() != n) continue;
            return kFVWmcqOBgYFxPgeswapvPe;
        }
        return null;
    }

    public String toString() {
        return this.IAudioInputStream + "";
    }
}

