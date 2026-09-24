/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.IVersionSchemes;

public class Version {
    private final int[] DSP;
    private final IVersionSchemes FFT;

    public Version(IVersionSchemes sQDhCOKjrMUQKSzCoRsySPR) {
        this.FFT = sQDhCOKjrMUQKSzCoRsySPR;
        this.DSP = new int[sQDhCOKjrMUQKSzCoRsySPR.DSP()];
    }

    public Version(String string, String string2, IVersionSchemes sQDhCOKjrMUQKSzCoRsySPR) {
        this.FFT = sQDhCOKjrMUQKSzCoRsySPR;
        this.DSP = new int[sQDhCOKjrMUQKSzCoRsySPR.DSP()];
        String[] stringArray = string.split(string2);
        for (int i = 0; i < sQDhCOKjrMUQKSzCoRsySPR.DSP(); ++i) {
            this.DSP[i] = new Integer(stringArray[i]);
        }
    }

    public void DSP(int n, int n2) {
        if (n < this.DSP.length) {
            this.DSP[n] = n2;
        }
    }

    public int DSP(int n) {
        if (n < this.DSP.length) {
            return this.DSP[n];
        }
        return -1;
    }

    public IVersionSchemes DSP() {
        return this.FFT;
    }

    public String toString() {
        String string = "";
        for (int i = 0; i < this.FFT.DSP() - 1; ++i) {
            string = string + this.DSP[i] + ".";
        }
        string = string + this.DSP[this.FFT.DSP() - 1];
        return string;
    }

    public String FFT(int n) {
        String string = "";
        for (int i = 0; i < n; ++i) {
            string = string + this.DSP[i] + ".";
        }
        string = string + this.DSP[n];
        return string;
    }

    public boolean DSP(Version kTbZASHDopQBPRkAiVONWbv2) {
        if (kTbZASHDopQBPRkAiVONWbv2.DSP().getClass() == this.FFT.getClass()) {
            for (int i = 0; i < this.FFT.DSP(); ++i) {
                if (this.DSP[i] < kTbZASHDopQBPRkAiVONWbv2.DSP(i)) {
                    return true;
                }
                if (this.DSP[i] <= kTbZASHDopQBPRkAiVONWbv2.DSP(i)) continue;
                return false;
            }
        }
        return false;
    }
}

