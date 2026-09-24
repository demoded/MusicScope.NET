/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.MyStream;

class StreamUtils {
    public static void DSP(MyStream xurJiSGFDQVeEtISoLSazWS2, int n, int[] nArray, int n2) {
        byte[] byArray = new byte[n];
        int n3 = StreamUtils.DSP(xurJiSGFDQVeEtISoLSazWS2, n, byArray, 0);
        for (int i = 0; i < n3; ++i) {
            nArray[n2 + i] = byArray[i];
        }
    }

    public static int DSP(MyStream xurJiSGFDQVeEtISoLSazWS2, int n, byte[] byArray, int n2) {
        int n3 = 0;
        try {
            n3 = xurJiSGFDQVeEtISoLSazWS2.DSP().read(byArray, n2, n);
        }
        catch (Exception exception) {
            System.err.println("stream_read: exception thrown: " + exception);
        }
        xurJiSGFDQVeEtISoLSazWS2.DSP(xurJiSGFDQVeEtISoLSazWS2.FFT() + n3);
        return n3;
    }

    public static int DSP(MyStream xurJiSGFDQVeEtISoLSazWS2) {
        int n = 0;
        int n2 = 0;
        byte[] byArray = xurJiSGFDQVeEtISoLSazWS2.responseView();
        int n3 = 0;
        try {
            n3 = xurJiSGFDQVeEtISoLSazWS2.DSP().read(byArray, 0, 4);
            xurJiSGFDQVeEtISoLSazWS2.DSP(xurJiSGFDQVeEtISoLSazWS2.FFT() + n3);
            n2 = byArray[0] & 0xFF;
            n = n2 << 24;
            n2 = byArray[1] & 0xFF;
            n |= n2 << 16;
            n2 = byArray[2] & 0xFF;
            n |= n2 << 8;
            n2 = byArray[3] & 0xFF;
            n |= n2;
        }
        catch (Exception exception) {
            System.err.println("stream_read_uint32: exception thrown: " + exception);
        }
        return n;
    }

    public static int FFT(MyStream xurJiSGFDQVeEtISoLSazWS2) {
        int n = 0;
        int n2 = 0;
        byte[] byArray = xurJiSGFDQVeEtISoLSazWS2.responseView();
        int n3 = 0;
        try {
            n3 = xurJiSGFDQVeEtISoLSazWS2.DSP().read(byArray, 0, 2);
            xurJiSGFDQVeEtISoLSazWS2.DSP(xurJiSGFDQVeEtISoLSazWS2.FFT() + n3);
            n2 = byArray[0] & 0xFF;
            n = n2 << 8;
            n2 = byArray[1] & 0xFF;
            n |= n2;
        }
        catch (Exception exception) {
            // empty catch block
        }
        return n;
    }

    public static int responseView(MyStream xurJiSGFDQVeEtISoLSazWS2) {
        int n = 0;
        int n2 = 0;
        byte[] byArray = xurJiSGFDQVeEtISoLSazWS2.responseView();
        try {
            n2 = xurJiSGFDQVeEtISoLSazWS2.DSP().read(byArray, 0, 1);
            n = byArray[0] & 0xFF;
            xurJiSGFDQVeEtISoLSazWS2.DSP(xurJiSGFDQVeEtISoLSazWS2.FFT() + 1);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return n;
    }

    public static void DSP(MyStream xurJiSGFDQVeEtISoLSazWS2, int n) {
        int n2 = n;
        int n3 = 0;
        if (n2 < 0) {
            System.err.println("stream_skip: request to seek backwards in stream - not supported, sorry");
            return;
        }
        try {
            n3 = xurJiSGFDQVeEtISoLSazWS2.DSP().skipBytes(n2);
            xurJiSGFDQVeEtISoLSazWS2.DSP(xurJiSGFDQVeEtISoLSazWS2.FFT() + n3);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public static int AdditionalMetadataValue(MyStream xurJiSGFDQVeEtISoLSazWS2) {
        return 0;
    }

    public static int AudioFileExtension(MyStream xurJiSGFDQVeEtISoLSazWS2) {
        return xurJiSGFDQVeEtISoLSazWS2.FFT();
    }

    public static int FFT(MyStream xurJiSGFDQVeEtISoLSazWS2, int n) {
        return -1;
    }
}

