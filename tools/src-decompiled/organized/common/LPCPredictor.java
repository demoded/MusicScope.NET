/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

public class LPCPredictor {
    public static void DSP(int[] nArray, int n, int[] nArray2, int n2, int n3, int[] nArray3, int n4) {
        for (int i = 0; i < n; ++i) {
            int n5 = 0;
            for (int j = 0; j < n2; ++j) {
                n5 += nArray2[j] * nArray3[n4 + i - j - 1];
            }
            nArray3[n4 + i] = nArray[i] + (n5 >> n3);
        }
    }

    public static void FFT(int[] nArray, int n, int[] nArray2, int n2, int n3, int[] nArray3, int n4) {
        for (int i = 0; i < n; ++i) {
            long l = 0L;
            for (int j = 0; j < n2; ++j) {
                l += (long)nArray2[j] * (long)nArray3[n4 + i - j - 1];
            }
            nArray3[n4 + i] = nArray[i] + (int)(l >> n3);
        }
    }
}

