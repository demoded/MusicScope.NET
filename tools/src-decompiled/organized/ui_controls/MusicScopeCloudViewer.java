/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

public class MusicScopeCloudViewer {
    private static MusicScopeCloudViewer DSP;
    private static final Object FFT;
    private String responseView = "Guest";

    private MusicScopeCloudViewer() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static MusicScopeCloudViewer DSP() {
        Object object = FFT;
        synchronized (object) {
            if (DSP == null) {
                DSP = new MusicScopeCloudViewer();
            }
        }
        return DSP;
    }

    static {
        FFT = new Object();
    }
}

