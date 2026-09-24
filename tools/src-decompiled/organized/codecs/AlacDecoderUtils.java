/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.AlacFile;
import sdfgjkljljoftrytrszgijpokjprs.LeadingZeros;

class AlacDecoderUtils {
    public static void DSP(AlacFile cmBIzvlUaDPTiIjykjjmigy2, int[] nArray) {
        int n = 0;
        n += 4;
        n += 4;
        n += 4;
        n += 4;
        n += 4;
        cmBIzvlUaDPTiIjykjjmigy2.IAudioFileCodec((nArray[n += 4] << 24) + (nArray[n + 1] << 16) + (nArray[n + 2] << 8) + nArray[n + 3]);
        cmBIzvlUaDPTiIjykjjmigy2.IAudioInputStream(nArray[n += 4]);
        cmBIzvlUaDPTiIjykjjmigy2.IAudioMetaInformation(nArray[++n]);
        cmBIzvlUaDPTiIjykjjmigy2.IBaseAudioCodec(nArray[++n] & 0xFF);
        cmBIzvlUaDPTiIjykjjmigy2.MetaInfomationCopy(nArray[++n] & 0xFF);
        cmBIzvlUaDPTiIjykjjmigy2.AacAudioCodec(nArray[++n] & 0xFF);
        cmBIzvlUaDPTiIjykjjmigy2.AacMetaDataModel(nArray[++n]);
        cmBIzvlUaDPTiIjykjjmigy2.BufferedAacReader((nArray[++n] << 8) + nArray[n + 1]);
        cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec((nArray[n += 2] << 24) + (nArray[n + 1] << 16) + (nArray[n + 2] << 8) + nArray[n + 3]);
        cmBIzvlUaDPTiIjykjjmigy2.AiffMetaDataModel((nArray[n += 4] << 24) + (nArray[n + 1] << 16) + (nArray[n + 2] << 8) + nArray[n + 3]);
        cmBIzvlUaDPTiIjykjjmigy2.AlacAudioCodec((nArray[n += 4] << 24) + (nArray[n + 1] << 16) + (nArray[n + 2] << 8) + nArray[n + 3]);
        n += 4;
    }

    static int DSP(AlacFile cmBIzvlUaDPTiIjykjjmigy2, int n) {
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        n4 = cmBIzvlUaDPTiIjykjjmigy2.DSP()[cmBIzvlUaDPTiIjykjjmigy2.FFT()] & 0xFF;
        n5 = cmBIzvlUaDPTiIjykjjmigy2.DSP()[cmBIzvlUaDPTiIjykjjmigy2.FFT() + 1] & 0xFF;
        n6 = cmBIzvlUaDPTiIjykjjmigy2.DSP()[cmBIzvlUaDPTiIjykjjmigy2.FFT() + 2] & 0xFF;
        n2 = n4 << 16 | n5 << 8 | n6;
        n2 <<= cmBIzvlUaDPTiIjykjjmigy2.responseView();
        n2 &= 0xFFFFFF;
        n3 = cmBIzvlUaDPTiIjykjjmigy2.responseView() + n;
        cmBIzvlUaDPTiIjykjjmigy2.DSP(cmBIzvlUaDPTiIjykjjmigy2.FFT() + (n3 >> 3));
        cmBIzvlUaDPTiIjykjjmigy2.FFT(n3 & 7);
        return n2 >>= 24 - n;
    }

    static int FFT(AlacFile cmBIzvlUaDPTiIjykjjmigy2, int n) {
        int n2 = 0;
        if (n > 16) {
            n2 = AlacDecoderUtils.DSP(cmBIzvlUaDPTiIjykjjmigy2, 16) << (n -= 16);
        }
        return n2 |= AlacDecoderUtils.DSP(cmBIzvlUaDPTiIjykjjmigy2, n);
    }

    static int DSP(AlacFile cmBIzvlUaDPTiIjykjjmigy2) {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        n = n3 = cmBIzvlUaDPTiIjykjjmigy2.DSP()[cmBIzvlUaDPTiIjykjjmigy2.FFT()] & 0xFF;
        n <<= cmBIzvlUaDPTiIjykjjmigy2.responseView();
        n = n >> 7 & 1;
        n2 = cmBIzvlUaDPTiIjykjjmigy2.responseView() + 1;
        cmBIzvlUaDPTiIjykjjmigy2.DSP(cmBIzvlUaDPTiIjykjjmigy2.FFT() + n2 / 8);
        cmBIzvlUaDPTiIjykjjmigy2.FFT(n2 % 8);
        return n;
    }

    static void responseView(AlacFile cmBIzvlUaDPTiIjykjjmigy2, int n) {
        int n2 = cmBIzvlUaDPTiIjykjjmigy2.responseView() - n;
        cmBIzvlUaDPTiIjykjjmigy2.DSP(cmBIzvlUaDPTiIjykjjmigy2.FFT() + (n2 >> 3));
        cmBIzvlUaDPTiIjykjjmigy2.FFT(n2 & 7);
        if (cmBIzvlUaDPTiIjykjjmigy2.responseView() < 0) {
            cmBIzvlUaDPTiIjykjjmigy2.FFT(cmBIzvlUaDPTiIjykjjmigy2.responseView() * -1);
        }
    }

    static LeadingZeros DSP(int n, int n2, LeadingZeros gLHqHFGBEIDXbcqrsZzduVF2) {
        if ((n & 0xF0) == 0) {
            n2 += 4;
        } else {
            n >>= 4;
        }
        if ((n & 8) != 0) {
            gLHqHFGBEIDXbcqrsZzduVF2.FFT(n2);
            gLHqHFGBEIDXbcqrsZzduVF2.DSP(n);
            return gLHqHFGBEIDXbcqrsZzduVF2;
        }
        if ((n & 4) != 0) {
            gLHqHFGBEIDXbcqrsZzduVF2.FFT(n2 + 1);
            gLHqHFGBEIDXbcqrsZzduVF2.DSP(n);
            return gLHqHFGBEIDXbcqrsZzduVF2;
        }
        if ((n & 2) != 0) {
            gLHqHFGBEIDXbcqrsZzduVF2.FFT(n2 + 2);
            gLHqHFGBEIDXbcqrsZzduVF2.DSP(n);
            return gLHqHFGBEIDXbcqrsZzduVF2;
        }
        if ((n & 1) != 0) {
            gLHqHFGBEIDXbcqrsZzduVF2.FFT(n2 + 3);
            gLHqHFGBEIDXbcqrsZzduVF2.DSP(n);
            return gLHqHFGBEIDXbcqrsZzduVF2;
        }
        gLHqHFGBEIDXbcqrsZzduVF2.FFT(n2 + 4);
        gLHqHFGBEIDXbcqrsZzduVF2.DSP(n);
        return gLHqHFGBEIDXbcqrsZzduVF2;
    }

    static int DSP(int n, LeadingZeros gLHqHFGBEIDXbcqrsZzduVF2) {
        int n2 = 0;
        int n3 = 0;
        n3 = n >> 24;
        if (n3 != 0) {
            AlacDecoderUtils.DSP(n3, n2, gLHqHFGBEIDXbcqrsZzduVF2);
            n2 = gLHqHFGBEIDXbcqrsZzduVF2.FFT();
            n3 = gLHqHFGBEIDXbcqrsZzduVF2.DSP();
            return n2;
        }
        n2 += 8;
        n3 = n >> 16;
        if ((n3 & 0xFF) != 0) {
            AlacDecoderUtils.DSP(n3, n2, gLHqHFGBEIDXbcqrsZzduVF2);
            n2 = gLHqHFGBEIDXbcqrsZzduVF2.FFT();
            n3 = gLHqHFGBEIDXbcqrsZzduVF2.DSP();
            return n2;
        }
        n2 += 8;
        n3 = n >> 8;
        if ((n3 & 0xFF) != 0) {
            AlacDecoderUtils.DSP(n3, n2, gLHqHFGBEIDXbcqrsZzduVF2);
            n2 = gLHqHFGBEIDXbcqrsZzduVF2.FFT();
            n3 = gLHqHFGBEIDXbcqrsZzduVF2.DSP();
            return n2;
        }
        n2 += 8;
        n3 = n;
        if ((n3 & 0xFF) != 0) {
            AlacDecoderUtils.DSP(n3, n2, gLHqHFGBEIDXbcqrsZzduVF2);
            n2 = gLHqHFGBEIDXbcqrsZzduVF2.FFT();
            n3 = gLHqHFGBEIDXbcqrsZzduVF2.DSP();
            return n2;
        }
        return n2 += 8;
    }

    public static int DSP(AlacFile cmBIzvlUaDPTiIjykjjmigy2, int n, int n2, int n3) {
        int n4;
        for (n4 = 0; n4 <= 8 && AlacDecoderUtils.DSP(cmBIzvlUaDPTiIjykjjmigy2) != 0; ++n4) {
        }
        if (n4 > 8) {
            int n5 = 0;
            n5 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, n);
            n4 = n5 &= -1 >> 32 - n;
        } else if (n2 != 1) {
            int n6 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, n2);
            n4 *= (1 << n2) - 1 & n3;
            if (n6 > 1) {
                n4 += n6 - 1;
            } else {
                AlacDecoderUtils.responseView(cmBIzvlUaDPTiIjykjjmigy2, 1);
            }
        }
        return n4;
    }

    public static void DSP(AlacFile cmBIzvlUaDPTiIjykjjmigy2, int[] nArray, int n, int n2, int n3, int n4, int n5, int n6) {
        int n7 = n3;
        int n8 = 0;
        for (int i = 0; i < n; ++i) {
            int n9 = 0;
            int n10 = 0;
            int n11 = 0;
            n11 = 31 - n4 - AlacDecoderUtils.DSP((n7 >> 9) + 3, cmBIzvlUaDPTiIjykjjmigy2.IAudioFileCodec());
            n11 = n11 < 0 ? (n11 += n4) : n4;
            n9 = AlacDecoderUtils.DSP(cmBIzvlUaDPTiIjykjjmigy2, n2, n11, -1);
            n10 = ((n9 += n8) + 1) / 2;
            if ((n9 & 1) != 0) {
                n10 *= -1;
            }
            nArray[i] = n10;
            n8 = 0;
            n7 += n9 * n5 - (n7 * n5 >> 9);
            if (n9 > 65535) {
                n7 = 65535;
            }
            if (n7 >= 128 || i + 1 >= n) continue;
            int n12 = 0;
            n8 = 1;
            n11 = AlacDecoderUtils.DSP(n7, cmBIzvlUaDPTiIjykjjmigy2.IAudioFileCodec()) + (n7 + 16) / 64 - 24;
            n12 = AlacDecoderUtils.DSP(cmBIzvlUaDPTiIjykjjmigy2, 16, n11, n6);
            if (n12 > 0) {
                int n13 = 0;
                n13 = n12;
                for (int j = 0; j < n13; ++j) {
                    nArray[i + 1 + j] = 0;
                }
                i += n12;
            }
            if (n12 > 65535) {
                n8 = 0;
            }
            n7 = 0;
        }
    }

    static int[] DSP(int[] nArray, int n, int n2, int[] nArray2, int n3, int n4) {
        int n5;
        int n6;
        int n7 = 0;
        int n8 = 0;
        int[] nArray3 = nArray;
        if (n3 == 0) {
            if (n <= 1) {
                return nArray3;
            }
            int n9 = 0;
            n9 = (n - 1) * 4;
            System.arraycopy(nArray, 1, nArray3, 1, n9);
            return nArray3;
        }
        if (n3 == 31) {
            if (n <= 1) {
                return nArray3;
            }
            for (int i = 0; i < n - 1; ++i) {
                int n10 = 0;
                int n11 = 0;
                n10 = nArray3[i];
                n11 = nArray[i + 1];
                n8 = 32 - n2;
                nArray3[i + 1] = n10 + n11 << n8 >> n8;
            }
            return nArray3;
        }
        if (n3 > 0) {
            for (n6 = 0; n6 < n3; ++n6) {
                n5 = 0;
                n5 = nArray3[n6] + nArray[n6 + 1];
                n8 = 32 - n2;
                nArray3[n6 + 1] = n5 = n5 << n8 >> n8;
            }
        }
        if (n3 > 0) {
            n7 = 0;
            for (n6 = n3 + 1; n6 < n; ++n6) {
                int n12;
                int n13;
                int n14;
                int n15 = 0;
                int n16 = nArray[n6];
                for (n5 = 0; n5 < n3; ++n5) {
                    n15 += (nArray3[n7 + n3 - n5] - nArray3[n7]) * nArray2[n5];
                }
                int n17 = (1 << n4 - 1) + n15;
                n17 >>= n4;
                n17 = n17 + nArray3[n7] + n16;
                n8 = 32 - n2;
                nArray3[n7 + n3 + 1] = n17 = n17 << n8 >> n8;
                if (n16 > 0) {
                    for (n13 = n3 - 1; n13 >= 0 && n16 > 0; n16 -= ((n12 *= n14) >> n4) * (n3 - n13), --n13) {
                        n12 = nArray3[n7] - nArray3[n7 + n3 - n13];
                        n14 = n12 < 0 ? -1 : (n12 > 0 ? 1 : 0);
                        int n18 = n13;
                        nArray2[n18] = nArray2[n18] - n14;
                    }
                } else if (n16 < 0) {
                    for (n13 = n3 - 1; n13 >= 0 && n16 < 0; n16 -= ((n12 *= n14) >> n4) * (n3 - n13), --n13) {
                        n12 = nArray3[n7] - nArray3[n7 + n3 - n13];
                        n14 = -(n12 < 0 ? -1 : (n12 > 0 ? 1 : 0));
                        int n19 = n13;
                        nArray2[n19] = nArray2[n19] - n14;
                    }
                }
                ++n7;
            }
        }
        return nArray3;
    }

    public static void DSP(int[] nArray, int[] nArray2, int[] nArray3, int n, int n2, int n3, int n4) {
        if (n2 <= 0) {
            return;
        }
        if (0 != n4) {
            for (int i = 0; i < n2; ++i) {
                int n5 = 0;
                int n6 = 0;
                int n7 = 0;
                int n8 = 0;
                n6 = nArray[i];
                n5 = nArray2[i];
                n8 = n6 - (n5 * n4 >> n3);
                nArray3[i * n] = n7 = n8 + n5;
                nArray3[i * n + 1] = n8;
            }
            return;
        }
        for (int i = 0; i < n2; ++i) {
            int n9 = 0;
            int n10 = 0;
            n9 = nArray[i];
            n10 = nArray2[i];
            nArray3[i * n] = n9;
            nArray3[i * n + 1] = n10;
        }
    }

    public static void DSP(int[] nArray, int[] nArray2, int n, int[] nArray3, int[] nArray4, int[] nArray5, int n2, int n3, int n4, int n5) {
        if (n3 <= 0) {
            return;
        }
        if (n5 != 0) {
            for (int i = 0; i < n3; ++i) {
                int n6 = 0;
                int n7 = 0;
                int n8 = 0;
                int n9 = 0;
                n7 = nArray[i];
                n6 = nArray2[i];
                n9 = n7 - (n6 * n5 >> n4);
                n8 = n9 + n6;
                if (n != 0) {
                    int n10 = ~(-1 << n * 8);
                    n8 <<= n * 8;
                    n9 <<= n * 8;
                    n8 |= nArray3[i] & n10;
                    n9 |= nArray4[i] & n10;
                }
                nArray5[i * n2 * 3] = n8 & 0xFF;
                nArray5[i * n2 * 3 + 1] = n8 >> 8 & 0xFF;
                nArray5[i * n2 * 3 + 2] = n8 >> 16 & 0xFF;
                nArray5[i * n2 * 3 + 3] = n9 & 0xFF;
                nArray5[i * n2 * 3 + 4] = n9 >> 8 & 0xFF;
                nArray5[i * n2 * 3 + 5] = n9 >> 16 & 0xFF;
            }
            return;
        }
        for (int i = 0; i < n3; ++i) {
            int n11 = 0;
            int n12 = 0;
            n11 = nArray[i];
            n12 = nArray2[i];
            if (n != 0) {
                int n13 = ~(-1 << n * 8);
                n11 <<= n * 8;
                n12 <<= n * 8;
                n11 |= nArray3[i] & n13;
                n12 |= nArray4[i] & n13;
            }
            nArray5[i * n2 * 3] = n11 & 0xFF;
            nArray5[i * n2 * 3 + 1] = n11 >> 8 & 0xFF;
            nArray5[i * n2 * 3 + 2] = n11 >> 16 & 0xFF;
            nArray5[i * n2 * 3 + 3] = n12 & 0xFF;
            nArray5[i * n2 * 3 + 4] = n12 >> 8 & 0xFF;
            nArray5[i * n2 * 3 + 5] = n12 >> 16 & 0xFF;
        }
    }

    public static int DSP(AlacFile cmBIzvlUaDPTiIjykjjmigy2, byte[] byArray, int[] nArray, int n) {
        block45: {
            int n2;
            int n3;
            block44: {
                int n4;
                int n5;
                int n6;
                n3 = cmBIzvlUaDPTiIjykjjmigy2.BufferedAacReader();
                cmBIzvlUaDPTiIjykjjmigy2.DSP(byArray);
                cmBIzvlUaDPTiIjykjjmigy2.FFT(0);
                cmBIzvlUaDPTiIjykjjmigy2.DSP(0);
                n2 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 3);
                n = n3 * cmBIzvlUaDPTiIjykjjmigy2.AudioFileExtension();
                if (n2 != 0) break block44;
                int n7 = 0;
                AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 4);
                AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 12);
                int n8 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 1);
                int n9 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 2);
                int n10 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 1);
                if (n8 != 0) {
                    n3 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 32);
                    n = n3 * cmBIzvlUaDPTiIjykjjmigy2.AudioFileExtension();
                }
                int n11 = cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec() - n9 * 8;
                if (n10 == 0) {
                    int n12;
                    int[] nArray2 = cmBIzvlUaDPTiIjykjjmigy2.BufferedAlacReader();
                    AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 8);
                    AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 8);
                    n6 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 4);
                    n5 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 4);
                    int n13 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 3);
                    n4 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 5);
                    for (n12 = 0; n12 < n4; ++n12) {
                        n7 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 16);
                        if (n7 > Short.MAX_VALUE) {
                            n7 -= 65536;
                        }
                        nArray2[n12] = n7;
                    }
                    if (n9 != 0) {
                        for (n12 = 0; n12 < n3; ++n12) {
                            cmBIzvlUaDPTiIjykjjmigy2.AacAudioCodec()[n12] = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, n9 * 8);
                        }
                    }
                    AlacDecoderUtils.DSP(cmBIzvlUaDPTiIjykjjmigy2, cmBIzvlUaDPTiIjykjjmigy2.IAudioInputStream(), n3, n11, cmBIzvlUaDPTiIjykjjmigy2.AlacAudioCodec(), cmBIzvlUaDPTiIjykjjmigy2.AlacMetaDataModel(), n13 * (cmBIzvlUaDPTiIjykjjmigy2.AiffMetaDataModel() / 4), (1 << cmBIzvlUaDPTiIjykjjmigy2.AlacMetaDataModel()) - 1);
                    if (n6 == 0) {
                        cmBIzvlUaDPTiIjykjjmigy2.DSP(AlacDecoderUtils.DSP(cmBIzvlUaDPTiIjykjjmigy2.IAudioInputStream(), n3, n11, nArray2, n4, n5));
                    } else {
                        System.err.println("FIXME: unhandled predicition type: " + n6);
                    }
                } else {
                    int n14;
                    if (cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec() <= 16) {
                        n14 = 0;
                        for (n4 = 0; n4 < n3; ++n4) {
                            n6 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec());
                            n14 = 32 - cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec();
                            cmBIzvlUaDPTiIjykjjmigy2.IBaseAudioCodec()[n4] = n6 = n6 << n14 >> n14;
                        }
                    } else {
                        n4 = 0x800000;
                        for (n6 = 0; n6 < n3; ++n6) {
                            n5 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 16);
                            n5 <<= cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec() - 16;
                            n14 = (n5 |= AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec() - 16)) & 0xFFFFFF;
                            cmBIzvlUaDPTiIjykjjmigy2.IBaseAudioCodec()[n6] = n5 = (n14 ^ n4) - n4;
                        }
                    }
                    n9 = 0;
                }
                switch (cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec()) {
                    case 16: {
                        for (int i = 0; i < n3; ++i) {
                            nArray[i * cmBIzvlUaDPTiIjykjjmigy2.AdditionalMetadataValue()] = n4 = cmBIzvlUaDPTiIjykjjmigy2.IBaseAudioCodec()[i];
                            nArray[i * cmBIzvlUaDPTiIjykjjmigy2.AdditionalMetadataValue() + 1] = 0;
                        }
                        break block45;
                    }
                    case 24: {
                        for (int i = 0; i < n3; ++i) {
                            n4 = cmBIzvlUaDPTiIjykjjmigy2.IBaseAudioCodec()[i];
                            if (n9 != 0) {
                                n6 = 0;
                                n4 <<= n9 * 8;
                                n6 = ~(-1 << n9 * 8);
                                n4 |= cmBIzvlUaDPTiIjykjjmigy2.AacAudioCodec()[i] & n6;
                            }
                            nArray[i * cmBIzvlUaDPTiIjykjjmigy2.AdditionalMetadataValue() * 3] = n4 & 0xFF;
                            nArray[i * cmBIzvlUaDPTiIjykjjmigy2.AdditionalMetadataValue() * 3 + 1] = n4 >> 8 & 0xFF;
                            nArray[i * cmBIzvlUaDPTiIjykjjmigy2.AdditionalMetadataValue() * 3 + 2] = n4 >> 16 & 0xFF;
                            nArray[i * cmBIzvlUaDPTiIjykjjmigy2.AdditionalMetadataValue() * 3 + 3] = 0;
                            nArray[i * cmBIzvlUaDPTiIjykjjmigy2.AdditionalMetadataValue() * 3 + 4] = 0;
                            nArray[i * cmBIzvlUaDPTiIjykjjmigy2.AdditionalMetadataValue() * 3 + 5] = 0;
                        }
                        break block45;
                    }
                    case 20: 
                    case 32: {
                        System.err.println("FIXME: unimplemented sample size " + cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec());
                    }
                }
                break block45;
            }
            if (n2 == 1) {
                int n15;
                int n16;
                AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 4);
                AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 12);
                int n17 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 1);
                int n18 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 2);
                int n19 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 1);
                if (n17 != 0) {
                    n3 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 32);
                    n = n3 * cmBIzvlUaDPTiIjykjjmigy2.AudioFileExtension();
                }
                int n20 = cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec() - n18 * 8 + 1;
                if (n19 == 0) {
                    int n21;
                    int[] nArray3 = cmBIzvlUaDPTiIjykjjmigy2.AlacContextModel();
                    int[] nArray4 = cmBIzvlUaDPTiIjykjjmigy2.AlacDecoderUtils();
                    int n22 = 0;
                    n16 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 8);
                    n15 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 8);
                    int n23 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 4);
                    int n24 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 4);
                    int n25 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 3);
                    int n26 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 5);
                    for (n21 = 0; n21 < n26; ++n21) {
                        n22 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 16);
                        if (n22 > Short.MAX_VALUE) {
                            n22 -= 65536;
                        }
                        nArray3[n21] = n22;
                    }
                    int n27 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 4);
                    int n28 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 4);
                    int n29 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 3);
                    int n30 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 5);
                    for (n21 = 0; n21 < n30; ++n21) {
                        n22 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 16);
                        if (n22 > Short.MAX_VALUE) {
                            n22 -= 65536;
                        }
                        nArray4[n21] = n22;
                    }
                    if (n18 != 0) {
                        for (n21 = 0; n21 < n3; ++n21) {
                            cmBIzvlUaDPTiIjykjjmigy2.AacAudioCodec()[n21] = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, n18 * 8);
                            cmBIzvlUaDPTiIjykjjmigy2.AacMetaDataModel()[n21] = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, n18 * 8);
                        }
                    }
                    AlacDecoderUtils.DSP(cmBIzvlUaDPTiIjykjjmigy2, cmBIzvlUaDPTiIjykjjmigy2.IAudioInputStream(), n3, n20, cmBIzvlUaDPTiIjykjjmigy2.AlacAudioCodec(), cmBIzvlUaDPTiIjykjjmigy2.AlacMetaDataModel(), n25 * (cmBIzvlUaDPTiIjykjjmigy2.AiffMetaDataModel() / 4), (1 << cmBIzvlUaDPTiIjykjjmigy2.AlacMetaDataModel()) - 1);
                    if (n23 == 0) {
                        cmBIzvlUaDPTiIjykjjmigy2.DSP(AlacDecoderUtils.DSP(cmBIzvlUaDPTiIjykjjmigy2.IAudioInputStream(), n3, n20, nArray3, n26, n24));
                    } else {
                        System.err.println("FIXME: unhandled predicition type: " + n23);
                    }
                    AlacDecoderUtils.DSP(cmBIzvlUaDPTiIjykjjmigy2, cmBIzvlUaDPTiIjykjjmigy2.IAudioMetaInformation(), n3, n20, cmBIzvlUaDPTiIjykjjmigy2.AlacAudioCodec(), cmBIzvlUaDPTiIjykjjmigy2.AlacMetaDataModel(), n29 * (cmBIzvlUaDPTiIjykjjmigy2.AiffMetaDataModel() / 4), (1 << cmBIzvlUaDPTiIjykjjmigy2.AlacMetaDataModel()) - 1);
                    if (n27 == 0) {
                        cmBIzvlUaDPTiIjykjjmigy2.FFT(AlacDecoderUtils.DSP(cmBIzvlUaDPTiIjykjjmigy2.IAudioMetaInformation(), n3, n20, nArray4, n30, n28));
                    } else {
                        System.err.println("FIXME: unhandled predicition type: " + n27);
                    }
                } else {
                    if (cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec() <= 16) {
                        for (int i = 0; i < n3; ++i) {
                            int n31 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec());
                            int n32 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec());
                            int n33 = 32 - cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec();
                            n31 = n31 << n33 >> n33;
                            n32 = n32 << n33 >> n33;
                            cmBIzvlUaDPTiIjykjjmigy2.IBaseAudioCodec()[i] = n31;
                            cmBIzvlUaDPTiIjykjjmigy2.MetaInfomationCopy()[i] = n32;
                        }
                    } else {
                        int n34 = 0x800000;
                        for (int i = 0; i < n3; ++i) {
                            int n35 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 16);
                            n35 <<= cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec() - 16;
                            int n36 = (n35 |= AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec() - 16)) & 0xFFFFFF;
                            n35 = (n36 ^ n34) - n34;
                            int n37 = AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, 16);
                            n37 <<= cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec() - 16;
                            n36 = (n37 |= AlacDecoderUtils.FFT(cmBIzvlUaDPTiIjykjjmigy2, cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec() - 16)) & 0xFFFFFF;
                            n37 = (n36 ^ n34) - n34;
                            cmBIzvlUaDPTiIjykjjmigy2.IBaseAudioCodec()[i] = n35;
                            cmBIzvlUaDPTiIjykjjmigy2.MetaInfomationCopy()[i] = n37;
                        }
                    }
                    n18 = 0;
                    n16 = 0;
                    n15 = 0;
                }
                switch (cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec()) {
                    case 16: {
                        AlacDecoderUtils.DSP(cmBIzvlUaDPTiIjykjjmigy2.IBaseAudioCodec(), cmBIzvlUaDPTiIjykjjmigy2.MetaInfomationCopy(), nArray, cmBIzvlUaDPTiIjykjjmigy2.AdditionalMetadataValue(), n3, n16, n15);
                        break;
                    }
                    case 24: {
                        AlacDecoderUtils.DSP(cmBIzvlUaDPTiIjykjjmigy2.IBaseAudioCodec(), cmBIzvlUaDPTiIjykjjmigy2.MetaInfomationCopy(), n18, cmBIzvlUaDPTiIjykjjmigy2.AacAudioCodec(), cmBIzvlUaDPTiIjykjjmigy2.AacMetaDataModel(), nArray, cmBIzvlUaDPTiIjykjjmigy2.AdditionalMetadataValue(), n3, n16, n15);
                        break;
                    }
                    case 20: 
                    case 32: {
                        System.err.println("FIXME: unimplemented sample size " + cmBIzvlUaDPTiIjykjjmigy2.AiffAudioCodec());
                    }
                }
            }
        }
        return n;
    }

    public static AlacFile DSP(int n, int n2) {
        AlacFile cmBIzvlUaDPTiIjykjjmigy2 = new AlacFile();
        cmBIzvlUaDPTiIjykjjmigy2.responseView(n);
        cmBIzvlUaDPTiIjykjjmigy2.AdditionalMetadataValue(n2);
        cmBIzvlUaDPTiIjykjjmigy2.AudioFileExtension(n / 8 * n2);
        return cmBIzvlUaDPTiIjykjjmigy2;
    }
}

