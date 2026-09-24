/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

public class FFT {
    private final int DSP;
    private final int FFT;
    private double[] responseView;
    private double AdditionalMetadataValue;

    public FFT(int n) {
        this.DSP = n;
        this.FFT = (int)(Math.log10(n) / Math.log10(2.0));
        this.DSP();
    }

    private synchronized void DSP() {
        this.responseView = new double[this.DSP];
        this.AdditionalMetadataValue = 0.0;
        for (int i = 0; i < this.DSP; ++i) {
            double d = Math.PI * 2 * (double)i / (double)(this.DSP - 1);
            this.responseView[i] = 0.27105140069342 - 0.43329793923448 * Math.cos(d) + 0.21812299954311 * Math.cos(2.0 * d) - 0.06592544638803 * Math.cos(3.0 * d) + 0.01081174209837 * Math.cos(4.0 * d) - 7.7658482522E-4 * Math.cos(5.0 * d) + 1.388721735E-5 * Math.cos(6.0 * d);
            this.AdditionalMetadataValue += this.responseView[i];
        }
        this.AdditionalMetadataValue = 1.0 / this.AdditionalMetadataValue * 2.0;
    }

    public void DSP(int n, boolean bl, double[] dArray, double[] dArray2) {
        block12: {
            int n2;
            int n3;
            block11: {
                n3 = this.DSP;
                int n4 = this.FFT;
                if (n == 1 && bl) {
                    for (n2 = 0; n2 < this.DSP; ++n2) {
                        dArray[n2] = this.responseView[n2] * dArray[n2];
                        dArray2[n2] = this.responseView[n2] * dArray2[n2];
                    }
                }
                int n5 = n3 >> 1;
                int n6 = 0;
                for (n2 = 0; n2 < n3 - 1; ++n2) {
                    int n7;
                    if (n2 < n6) {
                        double d = dArray[n2];
                        double d2 = dArray2[n2];
                        dArray[n2] = dArray[n6];
                        dArray2[n2] = dArray2[n6];
                        dArray[n6] = d;
                        dArray2[n6] = d2;
                    }
                    for (n7 = n5; n7 <= n6; n6 -= n7, n7 >>= 1) {
                    }
                    n6 += n7;
                }
                double d = -1.0;
                double d3 = 0.0;
                int n8 = 1;
                for (int i = 0; i < n4; ++i) {
                    int n9 = n8;
                    n8 <<= 1;
                    double d4 = 1.0;
                    double d5 = 0.0;
                    for (n6 = 0; n6 < n9; ++n6) {
                        for (n2 = n6; n2 < n3; n2 += n8) {
                            int n10 = n2 + n9;
                            double d6 = d4 * dArray[n10] - d5 * dArray2[n10];
                            double d7 = d4 * dArray2[n10] + d5 * dArray[n10];
                            dArray[n10] = dArray[n2] - d6;
                            dArray2[n10] = dArray2[n2] - d7;
                            int n11 = n2;
                            dArray[n11] = dArray[n11] + d6;
                            int n12 = n2;
                            dArray2[n12] = dArray2[n12] + d7;
                        }
                        double d8 = d4 * d - d5 * d3;
                        d5 = d4 * d3 + d5 * d;
                        d4 = d8;
                    }
                    d3 = Math.sqrt((1.0 - d) / 2.0);
                    if (n == 1) {
                        d3 = -d3;
                    }
                    d = Math.sqrt((1.0 + d) / 2.0);
                }
                if (n != 1 || !bl) break block11;
                n2 = 0;
                while (n2 < n3) {
                    int n13 = n2;
                    dArray[n13] = dArray[n13] * this.AdditionalMetadataValue;
                    int n14 = n2++;
                    dArray2[n14] = dArray2[n14] * this.AdditionalMetadataValue;
                }
                break block12;
            }
            if (n != 1 || bl) break block12;
            n2 = 0;
            while (n2 < n3) {
                int n15 = n2;
                dArray[n15] = dArray[n15] / (double)this.DSP;
                int n16 = n2++;
                dArray2[n16] = dArray2[n16] / (double)this.DSP;
            }
        }
    }
}

