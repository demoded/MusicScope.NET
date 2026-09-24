/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

public class FixedPredictor {
    public static void DSP(int[] nArray, int n, int n2, int[] nArray2, int n3) {
        int n4 = n;
        switch (n2) {
            case 0: {
                for (int i = 0; i < n4; ++i) {
                    nArray2[i + n3] = nArray[i];
                }
                break;
            }
            case 1: {
                for (int i = 0; i < n4; ++i) {
                    nArray2[i + n3] = nArray[i] + nArray2[i + n3 - 1];
                }
                break;
            }
            case 2: {
                for (int i = 0; i < n4; ++i) {
                    nArray2[i + n3] = nArray[i] + (nArray2[i + n3 - 1] << 1) - nArray2[i + n3 - 2];
                }
                break;
            }
            case 3: {
                for (int i = 0; i < n4; ++i) {
                    nArray2[i + n3] = nArray[i] + ((nArray2[i + n3 - 1] - nArray2[i + n3 - 2] << 1) + (nArray2[i + n3 - 1] - nArray2[i + n3 - 2])) + nArray2[i + n3 - 3];
                }
                break;
            }
            case 4: {
                for (int i = 0; i < n4; ++i) {
                    nArray2[i + n3] = nArray[i] + (nArray2[i + n3 - 1] + nArray2[i + n3 - 3] << 2) - ((nArray2[i + n3 - 2] << 2) + (nArray2[i + n3 - 2] << 1)) - nArray2[i + n3 - 4];
                }
                break;
            }
        }
    }
}

