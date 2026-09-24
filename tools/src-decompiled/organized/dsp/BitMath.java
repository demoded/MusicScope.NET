/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

public final class BitMath {
    public static int DSP(int n) {
        int n2 = 0;
        while ((n >>= 1) != 0) {
            ++n2;
        }
        return n2;
    }
}

