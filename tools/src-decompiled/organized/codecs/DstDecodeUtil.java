/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.Arrays;

public class DstDecodeUtil {
    IAudioFileCodec DSP;
    FFT FFT;
    FFT responseView;
    int[][] AdditionalMetadataValue;
    byte[] AudioFileExtension;
    int IAudioFileCodec;
    IAudioMetaInformation IAudioInputStream;
    AdditionalMetadataValue IAudioMetaInformation;
    AudioFileExtension IBaseAudioCodec;
    short[][] MetaInfomationCopy;
    byte[] AacAudioCodec = new byte[8];
    DSP AacMetaDataModel;
    short[][][] BufferedAacReader = new short[12][16][256];
    int[][] AiffAudioCodec = new int[6][16];
    static short[] AiffMetaDataModel = new short[]{1, 65, 33, 97, 17, 81, 49, 113, 9, 73, 41, 105, 25, 89, 57, 121, 5, 69, 37, 101, 21, 85, 53, 117, 13, 77, 45, 109, 29, 93, 61, 125, 3, 67, 35, 99, 19, 83, 51, 115, 11, 75, 43, 107, 27, 91, 59, 123, 7, 71, 39, 103, 23, 87, 55, 119, 15, 79, 47, 111, 31, 95, 63, 127, 2, 66, 34, 98, 18, 82, 50, 114, 10, 74, 42, 106, 26, 90, 58, 122, 6, 70, 38, 102, 22, 86, 54, 118, 14, 78, 46, 110, 30, 94, 62, 126, 4, 68, 36, 100, 20, 84, 52, 116, 12, 76, 44, 108, 28, 92, 60, 124, 8, 72, 40, 104, 24, 88, 56, 120, 16, 80, 48, 112, 32, 96, 64, 128};

    static void DSP(int n, int n2, IAudioInputStream vmkNmvoUyyfcyaaKFyveguY, byte[][] byArray) {
        for (int i = 0; i < n; ++i) {
            byte by;
            int n3;
            byte[] byArray2 = byArray[i];
            int n4 = 0;
            for (n3 = 0; n3 < vmkNmvoUyyfcyaaKFyveguY.responseView[i] - 1; ++n3) {
                by = (byte)vmkNmvoUyyfcyaaKFyveguY.AdditionalMetadataValue[i][n3];
                int n5 = n4 + vmkNmvoUyyfcyaaKFyveguY.DSP * 8 * vmkNmvoUyyfcyaaKFyveguY.FFT[i][n3];
                for (int j = n4; j < n5; ++j) {
                    byArray2[j] = by;
                }
                n4 += vmkNmvoUyyfcyaaKFyveguY.DSP * 8 * vmkNmvoUyyfcyaaKFyveguY.FFT[i][n3];
            }
            by = (byte)(vmkNmvoUyyfcyaaKFyveguY.AdditionalMetadataValue[i][n3] & 0xFF);
            Arrays.fill(byArray2, n4, byArray2.length, by);
        }
    }

    static short DSP(short s) {
        return AiffMetaDataModel[s + 512 & 0x7F];
    }

    void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, long l, int n, byte[] byArray) throws responseView {
        int n2 = (int)(l * (long)n);
        for (int i = 0; i < n2; ++i) {
            byArray[i] = yGjBevanihqaxYKnUNtrNeA.FFT(8);
        }
    }

    int DSP(long l) {
        int n = 0;
        while (l >= (long)(1 << n)) {
            ++n;
        }
        return n;
    }

    void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, int n, int n2, int n3, int n4, boolean bl, IAudioFileCodec vZagBOcdslnIaKEkojULJvx) throws responseView {
        int n5 = 0;
        int n6 = 0;
        boolean bl2 = false;
        int n7 = 0;
        int n8 = n2 - n4 / 8;
        IAudioInputStream vmkNmvoUyyfcyaaKFyveguY = bl ? vZagBOcdslnIaKEkojULJvx.AiffAudioCodec : vZagBOcdslnIaKEkojULJvx.AlacAudioCodec;
        int n9 = yGjBevanihqaxYKnUNtrNeA.responseView(1);
        if (n9 == 1) {
            int n10 = yGjBevanihqaxYKnUNtrNeA.responseView(1);
            while (n10 == 0) {
                int n11;
                if (n7 >= n3) {
                    throw new responseView("Too many segments for this channel!", -1);
                }
                if (!bl2) {
                    n11 = this.DSP(n2 - n4 / 8);
                    vmkNmvoUyyfcyaaKFyveguY.DSP = yGjBevanihqaxYKnUNtrNeA.responseView(n11);
                    if (vmkNmvoUyyfcyaaKFyveguY.DSP == 0 || vmkNmvoUyyfcyaaKFyveguY.DSP > n2 - n4 / 8) {
                        throw new responseView("Invalid segment resolution!", -1);
                    }
                    bl2 = true;
                }
                n11 = this.DSP(n8 / vmkNmvoUyyfcyaaKFyveguY.DSP);
                vmkNmvoUyyfcyaaKFyveguY.FFT[0][n7] = yGjBevanihqaxYKnUNtrNeA.responseView(n11);
                if (vmkNmvoUyyfcyaaKFyveguY.DSP * 8 * vmkNmvoUyyfcyaaKFyveguY.FFT[0][n7] < n4 || vmkNmvoUyyfcyaaKFyveguY.DSP * 8 * vmkNmvoUyyfcyaaKFyveguY.FFT[0][n7] > n2 * 8 - n6 - n4) {
                    throw new responseView("Invalid segment length!", -1);
                }
                n6 += vmkNmvoUyyfcyaaKFyveguY.DSP * 8 * vmkNmvoUyyfcyaaKFyveguY.FFT[0][n7];
                n8 -= vmkNmvoUyyfcyaaKFyveguY.DSP * vmkNmvoUyyfcyaaKFyveguY.FFT[0][n7];
                ++n7;
                n10 = yGjBevanihqaxYKnUNtrNeA.responseView(1);
            }
            vmkNmvoUyyfcyaaKFyveguY.responseView[0] = n7 + 1;
            vmkNmvoUyyfcyaaKFyveguY.FFT[0][n7] = 0;
            for (n5 = 1; n5 < n; ++n5) {
                vmkNmvoUyyfcyaaKFyveguY.responseView[n5] = vmkNmvoUyyfcyaaKFyveguY.responseView[0];
                for (n7 = 0; n7 < vmkNmvoUyyfcyaaKFyveguY.responseView[0]; ++n7) {
                    vmkNmvoUyyfcyaaKFyveguY.FFT[n5][n7] = vmkNmvoUyyfcyaaKFyveguY.FFT[0][n7];
                }
            }
        } else {
            while (n5 < n) {
                if (n7 >= n3) {
                    throw new responseView("Too many segments for this channel!", -1);
                }
                int n12 = yGjBevanihqaxYKnUNtrNeA.responseView(1);
                if (n12 == 0) {
                    int n13;
                    if (!bl2) {
                        n13 = this.DSP(n2 - n4 / 8);
                        vmkNmvoUyyfcyaaKFyveguY.DSP = yGjBevanihqaxYKnUNtrNeA.responseView(n13);
                        if (vmkNmvoUyyfcyaaKFyveguY.DSP == 0 || vmkNmvoUyyfcyaaKFyveguY.DSP > n2 - n4 / 8) {
                            throw new responseView("Invalid segment resolution!", -1);
                        }
                        bl2 = true;
                    }
                    n13 = this.DSP(n8 / vmkNmvoUyyfcyaaKFyveguY.DSP);
                    vmkNmvoUyyfcyaaKFyveguY.FFT[n5][n7] = yGjBevanihqaxYKnUNtrNeA.responseView(n13);
                    if (vmkNmvoUyyfcyaaKFyveguY.DSP * 8 * vmkNmvoUyyfcyaaKFyveguY.FFT[n5][n7] < n4 || vmkNmvoUyyfcyaaKFyveguY.DSP * 8 * vmkNmvoUyyfcyaaKFyveguY.FFT[n5][n7] > n2 * 8 - n6 - n4) {
                        throw new responseView("Invalid segment length!", -1);
                    }
                    n6 += vmkNmvoUyyfcyaaKFyveguY.DSP * 8 * vmkNmvoUyyfcyaaKFyveguY.FFT[n5][n7];
                    n8 -= vmkNmvoUyyfcyaaKFyveguY.DSP * vmkNmvoUyyfcyaaKFyveguY.FFT[n5][n7];
                    ++n7;
                    continue;
                }
                vmkNmvoUyyfcyaaKFyveguY.responseView[n5] = n7 + 1;
                vmkNmvoUyyfcyaaKFyveguY.FFT[n5][n7] = 0;
                n7 = 0;
                n6 = 0;
                n8 = n2 - n4 / 8;
                ++n5;
            }
        }
        if (!bl2) {
            vmkNmvoUyyfcyaaKFyveguY.DSP = 1;
        }
        if (bl) {
            vZagBOcdslnIaKEkojULJvx.AlacDecoderUtils = n9;
        } else {
            vZagBOcdslnIaKEkojULJvx.AlacInputStream = n9;
        }
    }

    void DSP(IAudioFileCodec vZagBOcdslnIaKEkojULJvx) throws responseView {
        int[] nArray = vZagBOcdslnIaKEkojULJvx.AlacAudioCodec.responseView;
        int[] nArray2 = vZagBOcdslnIaKEkojULJvx.AiffAudioCodec.responseView;
        vZagBOcdslnIaKEkojULJvx.AlacAudioCodec.DSP = vZagBOcdslnIaKEkojULJvx.AiffAudioCodec.DSP;
        vZagBOcdslnIaKEkojULJvx.AlacInputStream = 1;
        for (int i = 0; i < vZagBOcdslnIaKEkojULJvx.FFT; ++i) {
            nArray[i] = nArray2[i];
            if (nArray[i] > 8) {
                throw new responseView("Too many segments!", -1);
            }
            if (nArray[i] != nArray[0]) {
                vZagBOcdslnIaKEkojULJvx.AlacInputStream = 0;
            }
            for (int j = 0; j < nArray[i]; ++j) {
                int[] nArray3 = vZagBOcdslnIaKEkojULJvx.AlacAudioCodec.FFT[i];
                int[] nArray4 = vZagBOcdslnIaKEkojULJvx.AiffAudioCodec.FFT[i];
                nArray3[j] = nArray4[j];
                if (nArray3[j] != 0 && vZagBOcdslnIaKEkojULJvx.AlacAudioCodec.DSP * 8 * nArray3[j] < 32) {
                    throw new responseView("ERROR: Invalid segment length!", -1);
                }
                if (nArray3[j] == vZagBOcdslnIaKEkojULJvx.AlacAudioCodec.FFT[0][j]) continue;
                vZagBOcdslnIaKEkojULJvx.AlacInputStream = 0;
            }
        }
    }

    void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioFileCodec vZagBOcdslnIaKEkojULJvx) throws responseView {
        vZagBOcdslnIaKEkojULJvx.BufferedAlacReader = yGjBevanihqaxYKnUNtrNeA.responseView(1);
        this.DSP(yGjBevanihqaxYKnUNtrNeA, vZagBOcdslnIaKEkojULJvx.FFT, vZagBOcdslnIaKEkojULJvx.DemuxUtils, 4, 1024, true, vZagBOcdslnIaKEkojULJvx);
        if (vZagBOcdslnIaKEkojULJvx.BufferedAlacReader == 1) {
            this.DSP(vZagBOcdslnIaKEkojULJvx);
        } else {
            this.DSP(yGjBevanihqaxYKnUNtrNeA, vZagBOcdslnIaKEkojULJvx.FFT, vZagBOcdslnIaKEkojULJvx.DemuxUtils, 8, 32, false, vZagBOcdslnIaKEkojULJvx);
        }
    }

    void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, int n, int n2, boolean bl, IAudioFileCodec vZagBOcdslnIaKEkojULJvx) throws responseView {
        int n3 = 1;
        int n4 = 1;
        IAudioInputStream vmkNmvoUyyfcyaaKFyveguY = bl ? vZagBOcdslnIaKEkojULJvx.AiffAudioCodec : vZagBOcdslnIaKEkojULJvx.AlacAudioCodec;
        vmkNmvoUyyfcyaaKFyveguY.AdditionalMetadataValue[0][0] = 0;
        int n5 = yGjBevanihqaxYKnUNtrNeA.responseView(1);
        if (n5 == 1) {
            int n6;
            for (n6 = 1; n6 < vmkNmvoUyyfcyaaKFyveguY.responseView[0]; ++n6) {
                n4 = this.DSP(n3);
                vmkNmvoUyyfcyaaKFyveguY.AdditionalMetadataValue[0][n6] = yGjBevanihqaxYKnUNtrNeA.responseView(n4);
                if (vmkNmvoUyyfcyaaKFyveguY.AdditionalMetadataValue[0][n6] == n3) {
                    ++n3;
                    continue;
                }
                if (vmkNmvoUyyfcyaaKFyveguY.AdditionalMetadataValue[0][n6] <= n3) continue;
                throw new responseView("Invalid table number for segment!", -1);
            }
            for (int i = 1; i < n; ++i) {
                if (vmkNmvoUyyfcyaaKFyveguY.responseView[i] != vmkNmvoUyyfcyaaKFyveguY.responseView[0]) {
                    throw new responseView("Mapping can't be the same for all channels!", -1);
                }
                for (n6 = 0; n6 < vmkNmvoUyyfcyaaKFyveguY.responseView[0]; ++n6) {
                    vmkNmvoUyyfcyaaKFyveguY.AdditionalMetadataValue[i][n6] = vmkNmvoUyyfcyaaKFyveguY.AdditionalMetadataValue[0][n6];
                }
            }
        } else {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < vmkNmvoUyyfcyaaKFyveguY.responseView[i]; ++j) {
                    if (i == 0 && j == 0) continue;
                    n4 = this.DSP(n3);
                    vmkNmvoUyyfcyaaKFyveguY.AdditionalMetadataValue[i][j] = yGjBevanihqaxYKnUNtrNeA.responseView(n4);
                    if (vmkNmvoUyyfcyaaKFyveguY.AdditionalMetadataValue[i][j] == n3) {
                        ++n3;
                        continue;
                    }
                    if (vmkNmvoUyyfcyaaKFyveguY.AdditionalMetadataValue[i][j] <= n3) continue;
                    throw new responseView("ERROR: Invalid table number for segment!", -1);
                }
            }
        }
        if (n3 > n2) {
            throw new responseView("Too many tables for this frame!", -1);
        }
        if (bl) {
            vZagBOcdslnIaKEkojULJvx.responseView = n3;
            vZagBOcdslnIaKEkojULJvx.AlacFile = n5;
        } else {
            vZagBOcdslnIaKEkojULJvx.AdditionalMetadataValue = n3;
            vZagBOcdslnIaKEkojULJvx.AlacUtils = n5;
        }
    }

    void FFT(IAudioFileCodec vZagBOcdslnIaKEkojULJvx) throws responseView {
        vZagBOcdslnIaKEkojULJvx.AlacUtils = 1;
        for (int i = 0; i < vZagBOcdslnIaKEkojULJvx.FFT; ++i) {
            if (vZagBOcdslnIaKEkojULJvx.AlacAudioCodec.responseView[i] == vZagBOcdslnIaKEkojULJvx.AiffAudioCodec.responseView[i]) {
                for (int j = 0; j < vZagBOcdslnIaKEkojULJvx.AiffAudioCodec.responseView[i]; ++j) {
                    vZagBOcdslnIaKEkojULJvx.AlacAudioCodec.AdditionalMetadataValue[i][j] = vZagBOcdslnIaKEkojULJvx.AiffAudioCodec.AdditionalMetadataValue[i][j];
                    if (vZagBOcdslnIaKEkojULJvx.AlacAudioCodec.AdditionalMetadataValue[i][j] == vZagBOcdslnIaKEkojULJvx.AlacAudioCodec.AdditionalMetadataValue[0][j]) continue;
                    vZagBOcdslnIaKEkojULJvx.AlacUtils = 0;
                }
                continue;
            }
            throw new responseView("Not same number of segments for filters and Ptables!", -1);
        }
        vZagBOcdslnIaKEkojULJvx.AdditionalMetadataValue = vZagBOcdslnIaKEkojULJvx.responseView;
        if (vZagBOcdslnIaKEkojULJvx.AdditionalMetadataValue > vZagBOcdslnIaKEkojULJvx.DemuxResT) {
            throw new responseView("Too many tables for this frame!", -1);
        }
    }

    void FFT(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioFileCodec vZagBOcdslnIaKEkojULJvx) throws responseView {
        vZagBOcdslnIaKEkojULJvx.AlacContextModel = yGjBevanihqaxYKnUNtrNeA.responseView(1);
        this.DSP(yGjBevanihqaxYKnUNtrNeA, vZagBOcdslnIaKEkojULJvx.FFT, vZagBOcdslnIaKEkojULJvx.ChunkInfo, true, vZagBOcdslnIaKEkojULJvx);
        if (vZagBOcdslnIaKEkojULJvx.AlacContextModel == 1) {
            this.FFT(vZagBOcdslnIaKEkojULJvx);
        } else {
            this.DSP(yGjBevanihqaxYKnUNtrNeA, vZagBOcdslnIaKEkojULJvx.FFT, vZagBOcdslnIaKEkojULJvx.DemuxResT, false, vZagBOcdslnIaKEkojULJvx);
        }
        for (int i = 0; i < vZagBOcdslnIaKEkojULJvx.FFT; ++i) {
            vZagBOcdslnIaKEkojULJvx.AacMetaDataModel[i] = yGjBevanihqaxYKnUNtrNeA.responseView(1);
        }
    }

    int DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, int n) throws responseView {
        int n2;
        int n3;
        int n4 = 0;
        do {
            n3 = yGjBevanihqaxYKnUNtrNeA.responseView(1);
            n4 += 1 - n3;
        } while (n3 == 0);
        int n5 = yGjBevanihqaxYKnUNtrNeA.responseView(n);
        int n6 = (n4 << n) + n5;
        if (n6 != 0 && (n2 = yGjBevanihqaxYKnUNtrNeA.responseView(1)) == 1) {
            n6 = -n6;
        }
        return n6;
    }

    void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, int n, IAudioFileCodec vZagBOcdslnIaKEkojULJvx, FFT qPeIwmpzLZIktKLXAJOQHcO) throws responseView {
        for (int i = 0; i < vZagBOcdslnIaKEkojULJvx.responseView; ++i) {
            int n2;
            vZagBOcdslnIaKEkojULJvx.IAudioFileCodec[i] = yGjBevanihqaxYKnUNtrNeA.responseView(7);
            int n3 = i;
            vZagBOcdslnIaKEkojULJvx.IAudioFileCodec[n3] = vZagBOcdslnIaKEkojULJvx.IAudioFileCodec[n3] + 1;
            qPeIwmpzLZIktKLXAJOQHcO.responseView[i] = yGjBevanihqaxYKnUNtrNeA.responseView(1);
            if (qPeIwmpzLZIktKLXAJOQHcO.responseView[i] == 0) {
                qPeIwmpzLZIktKLXAJOQHcO.AdditionalMetadataValue[i] = -1;
                for (n2 = 0; n2 < vZagBOcdslnIaKEkojULJvx.IAudioFileCodec[i]; ++n2) {
                    vZagBOcdslnIaKEkojULJvx.IAudioMetaInformation[i][n2] = yGjBevanihqaxYKnUNtrNeA.AdditionalMetadataValue(9);
                }
            } else {
                qPeIwmpzLZIktKLXAJOQHcO.AdditionalMetadataValue[i] = yGjBevanihqaxYKnUNtrNeA.responseView(2);
                int n4 = qPeIwmpzLZIktKLXAJOQHcO.AdditionalMetadataValue[i];
                if (qPeIwmpzLZIktKLXAJOQHcO.DSP[n4] >= vZagBOcdslnIaKEkojULJvx.IAudioFileCodec[i]) {
                    throw new responseView("Invalid coefficient coding method!", -1);
                }
                for (n2 = 0; n2 < qPeIwmpzLZIktKLXAJOQHcO.DSP[n4]; ++n2) {
                    vZagBOcdslnIaKEkojULJvx.IAudioMetaInformation[i][n2] = yGjBevanihqaxYKnUNtrNeA.AdditionalMetadataValue(9);
                }
                qPeIwmpzLZIktKLXAJOQHcO.AudioFileExtension[i][n4] = yGjBevanihqaxYKnUNtrNeA.responseView(3);
                for (n2 = qPeIwmpzLZIktKLXAJOQHcO.DSP[n4]; n2 < vZagBOcdslnIaKEkojULJvx.IAudioFileCodec[i]; ++n2) {
                    int n5 = 0;
                    for (int j = 0; j < qPeIwmpzLZIktKLXAJOQHcO.DSP[n4]; ++j) {
                        n5 += qPeIwmpzLZIktKLXAJOQHcO.FFT[n4][j] * vZagBOcdslnIaKEkojULJvx.IAudioMetaInformation[i][n2 - j - 1];
                    }
                    int n6 = n5 >= 0 ? this.DSP(yGjBevanihqaxYKnUNtrNeA, qPeIwmpzLZIktKLXAJOQHcO.AudioFileExtension[i][n4]) - (n5 + 4) / 8 : this.DSP(yGjBevanihqaxYKnUNtrNeA, qPeIwmpzLZIktKLXAJOQHcO.AudioFileExtension[i][n4]) + (-n5 + 3) / 8;
                    if (n6 < -256 || n6 >= 256) {
                        throw new responseView("filter coefficient out of range!", -1);
                    }
                    vZagBOcdslnIaKEkojULJvx.IAudioMetaInformation[i][n2] = (short)n6;
                }
            }
            if (n2 >= vZagBOcdslnIaKEkojULJvx.IAudioMetaInformation[i].length) continue;
            Arrays.fill(vZagBOcdslnIaKEkojULJvx.IAudioMetaInformation[i], n2, vZagBOcdslnIaKEkojULJvx.IAudioMetaInformation[i].length, 0);
        }
        for (int i = 0; i < n; ++i) {
            vZagBOcdslnIaKEkojULJvx.BufferedAacReader[i] = vZagBOcdslnIaKEkojULJvx.IAudioFileCodec[vZagBOcdslnIaKEkojULJvx.AiffAudioCodec.AdditionalMetadataValue[i][0]];
        }
    }

    void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioFileCodec vZagBOcdslnIaKEkojULJvx, FFT qPeIwmpzLZIktKLXAJOQHcO, int[][] nArray) throws responseView {
        for (int i = 0; i < vZagBOcdslnIaKEkojULJvx.AdditionalMetadataValue; ++i) {
            vZagBOcdslnIaKEkojULJvx.IAudioInputStream[i] = yGjBevanihqaxYKnUNtrNeA.responseView(6);
            int n = i;
            vZagBOcdslnIaKEkojULJvx.IAudioInputStream[n] = vZagBOcdslnIaKEkojULJvx.IAudioInputStream[n] + 1;
            if (vZagBOcdslnIaKEkojULJvx.IAudioInputStream[i] > 1) {
                int n2;
                qPeIwmpzLZIktKLXAJOQHcO.responseView[i] = yGjBevanihqaxYKnUNtrNeA.responseView(1);
                if (qPeIwmpzLZIktKLXAJOQHcO.responseView[i] == 0) {
                    qPeIwmpzLZIktKLXAJOQHcO.AdditionalMetadataValue[i] = -1;
                    n2 = 0;
                    while (n2 < vZagBOcdslnIaKEkojULJvx.IAudioInputStream[i]) {
                        nArray[i][n2] = yGjBevanihqaxYKnUNtrNeA.responseView(7);
                        int[] nArray2 = nArray[i];
                        int n3 = n2++;
                        nArray2[n3] = nArray2[n3] + 1;
                    }
                    continue;
                }
                qPeIwmpzLZIktKLXAJOQHcO.AdditionalMetadataValue[i] = yGjBevanihqaxYKnUNtrNeA.responseView(2);
                int n4 = qPeIwmpzLZIktKLXAJOQHcO.AdditionalMetadataValue[i];
                if (qPeIwmpzLZIktKLXAJOQHcO.DSP[n4] >= vZagBOcdslnIaKEkojULJvx.IAudioInputStream[i]) {
                    throw new responseView("Invalid Ptable coding method!", -1);
                }
                n2 = 0;
                while (n2 < qPeIwmpzLZIktKLXAJOQHcO.DSP[n4]) {
                    nArray[i][n2] = yGjBevanihqaxYKnUNtrNeA.responseView(7);
                    int[] nArray3 = nArray[i];
                    int n5 = n2++;
                    nArray3[n5] = nArray3[n5] + 1;
                }
                qPeIwmpzLZIktKLXAJOQHcO.AudioFileExtension[i][n4] = yGjBevanihqaxYKnUNtrNeA.responseView(3);
                for (n2 = qPeIwmpzLZIktKLXAJOQHcO.DSP[n4]; n2 < vZagBOcdslnIaKEkojULJvx.IAudioInputStream[i]; ++n2) {
                    int n6 = 0;
                    for (int j = 0; j < qPeIwmpzLZIktKLXAJOQHcO.DSP[n4]; ++j) {
                        n6 += qPeIwmpzLZIktKLXAJOQHcO.FFT[n4][j] * nArray[i][n2 - j - 1];
                    }
                    int n7 = n6 >= 0 ? this.DSP(yGjBevanihqaxYKnUNtrNeA, qPeIwmpzLZIktKLXAJOQHcO.AudioFileExtension[i][n4]) - (n6 + 4) / 8 : this.DSP(yGjBevanihqaxYKnUNtrNeA, qPeIwmpzLZIktKLXAJOQHcO.AudioFileExtension[i][n4]) + (-n6 + 3) / 8;
                    if (n7 < 1 || n7 > 128) {
                        throw new responseView(String.format("Ptable entry (%d) out of range!", n7), -1);
                    }
                    nArray[i][n2] = n7;
                }
                continue;
            }
            nArray[i][0] = 128;
            qPeIwmpzLZIktKLXAJOQHcO.AdditionalMetadataValue[i] = -1;
        }
    }

    void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, int n, byte[] byArray) throws responseView {
        if (n == 0) {
            return;
        }
        byArray[0] = yGjBevanihqaxYKnUNtrNeA.FFT(1);
        if (byArray[0] != 0) {
            throw new responseView(String.format("Illegal arithmetic code in frame %d!", this.DSP.DSP), -1);
        }
        for (int i = 1; i < n; ++i) {
            byArray[i] = yGjBevanihqaxYKnUNtrNeA.FFT(1);
        }
    }

    void DSP(FFT qPeIwmpzLZIktKLXAJOQHcO) throws responseView {
        switch (qPeIwmpzLZIktKLXAJOQHcO.IAudioInputStream) {
            case 0: {
                int n;
                qPeIwmpzLZIktKLXAJOQHcO.DSP[0] = 1;
                qPeIwmpzLZIktKLXAJOQHcO.FFT[0][0] = -8;
                for (n = qPeIwmpzLZIktKLXAJOQHcO.DSP[0]; n < 3; ++n) {
                    qPeIwmpzLZIktKLXAJOQHcO.FFT[0][n] = 0;
                }
                qPeIwmpzLZIktKLXAJOQHcO.DSP[1] = 2;
                qPeIwmpzLZIktKLXAJOQHcO.FFT[1][0] = -16;
                qPeIwmpzLZIktKLXAJOQHcO.FFT[1][1] = 8;
                for (n = qPeIwmpzLZIktKLXAJOQHcO.DSP[1]; n < 3; ++n) {
                    qPeIwmpzLZIktKLXAJOQHcO.FFT[1][n] = 0;
                }
                qPeIwmpzLZIktKLXAJOQHcO.DSP[2] = 3;
                qPeIwmpzLZIktKLXAJOQHcO.FFT[2][0] = -9;
                qPeIwmpzLZIktKLXAJOQHcO.FFT[2][1] = -5;
                qPeIwmpzLZIktKLXAJOQHcO.FFT[2][2] = 6;
                for (n = qPeIwmpzLZIktKLXAJOQHcO.DSP[2]; n < 3; ++n) {
                    qPeIwmpzLZIktKLXAJOQHcO.FFT[2][n] = 0;
                }
                break;
            }
            case 1: {
                int n;
                qPeIwmpzLZIktKLXAJOQHcO.DSP[0] = 1;
                qPeIwmpzLZIktKLXAJOQHcO.FFT[0][0] = -8;
                for (n = qPeIwmpzLZIktKLXAJOQHcO.DSP[0]; n < 3; ++n) {
                    qPeIwmpzLZIktKLXAJOQHcO.FFT[0][n] = 0;
                }
                qPeIwmpzLZIktKLXAJOQHcO.DSP[1] = 2;
                qPeIwmpzLZIktKLXAJOQHcO.FFT[1][0] = -16;
                qPeIwmpzLZIktKLXAJOQHcO.FFT[1][1] = 8;
                for (n = qPeIwmpzLZIktKLXAJOQHcO.DSP[1]; n < 3; ++n) {
                    qPeIwmpzLZIktKLXAJOQHcO.FFT[1][n] = 0;
                }
                qPeIwmpzLZIktKLXAJOQHcO.DSP[2] = 3;
                qPeIwmpzLZIktKLXAJOQHcO.FFT[2][0] = -24;
                qPeIwmpzLZIktKLXAJOQHcO.FFT[2][1] = 24;
                qPeIwmpzLZIktKLXAJOQHcO.FFT[2][2] = -8;
                for (n = qPeIwmpzLZIktKLXAJOQHcO.DSP[2]; n < 3; ++n) {
                    qPeIwmpzLZIktKLXAJOQHcO.FFT[2][n] = 0;
                }
                break;
            }
            default: {
                throw new responseView("Illegal table type", -1);
            }
        }
    }

    void DSP(int n, int n2) throws responseView {
        this.IAudioInputStream = new IAudioMetaInformation();
        this.DSP = new IAudioFileCodec();
        this.DSP.FFT = n;
        this.DSP.DSP = 0;
        this.DSP.AudioFileExtension = n2;
        this.DSP.DemuxUtils = 588 * n2 / 8;
        this.DSP.LeadingZeros = this.DSP.DemuxUtils * this.DSP.FFT;
        this.DSP.MyStream = this.DSP.LeadingZeros * 8L;
        this.DSP.QTMovieT = this.DSP.DemuxUtils * 8;
        this.DSP.ChunkInfo = 2 * this.DSP.FFT;
        this.DSP.DemuxResT = 2 * this.DSP.FFT;
        this.DSP.IAudioMetaInformation = new int[this.DSP.ChunkInfo][128];
        this.FFT = new FFT(this.DSP);
        this.responseView = new FFT(this.DSP);
        this.FFT.IAudioInputStream = 0;
        this.responseView.IAudioInputStream = 1;
        this.AudioFileExtension = new byte[(int)this.DSP.MyStream];
        this.AdditionalMetadataValue = new int[this.DSP.DemuxResT][64];
        this.DSP(this.FFT);
        this.DSP(this.responseView);
        this.IAudioMetaInformation = new AdditionalMetadataValue();
        this.IBaseAudioCodec = new AudioFileExtension();
        this.IBaseAudioCodec.DSP = new int[this.DSP.FFT];
        this.IBaseAudioCodec.FFT = new int[this.DSP.FFT][128];
        this.MetaInfomationCopy = new short[this.DSP.FFT][(int)this.DSP.QTMovieT];
        for (int i = 0; i < 8; ++i) {
            this.AacAudioCodec[i] = (byte)(1 << i & 0xFF);
        }
        this.AacMetaDataModel = new DSP();
    }

    void DSP(byte[] byArray, byte[] byArray2) throws responseView {
        this.IAudioInputStream.DSP(byArray, this.DSP.MetaInfomationCopy);
        this.DSP.IBaseAudioCodec = this.IAudioInputStream.responseView(1);
        if (this.DSP.IBaseAudioCodec == 0) {
            int n = this.IAudioInputStream.responseView(1);
            n = this.IAudioInputStream.responseView(6);
            if (n != 0) {
                throw new responseView(String.format("Illegal stuffing pattern in frame %d!", this.DSP.DSP), 0);
            }
            System.out.printf("Processing dsd frame%n", new Object[0]);
            this.DSP(this.IAudioInputStream, (long)this.DSP.DemuxUtils, this.DSP.FFT, byArray2);
        } else {
            this.DSP(this.IAudioInputStream, this.DSP);
            this.FFT(this.IAudioInputStream, this.DSP);
            this.DSP(this.IAudioInputStream, this.DSP.FFT, this.DSP, this.FFT);
            this.DSP(this.IAudioInputStream, this.DSP, this.responseView, this.AdditionalMetadataValue);
            this.IAudioFileCodec = (int)(this.DSP.AacAudioCodec - this.IAudioInputStream.FFT());
            this.DSP(this.IAudioInputStream, this.IAudioFileCodec, this.AudioFileExtension);
        }
    }

    final void DSP(short[][][] sArray) {
        for (int i = 0; i < this.DSP.responseView; ++i) {
            int n = this.DSP.IAudioFileCodec[i];
            for (int j = 0; j < 16; ++j) {
                int n2 = n - j * 8;
                if (n2 > 8) {
                    n2 = 8;
                } else if (n2 < 0) {
                    n2 = 0;
                }
                for (int k = 0; k < 256; ++k) {
                    int n3 = 0;
                    for (int i2 = 0; i2 < n2; ++i2) {
                        n3 += ((k >> i2 & 1) * 2 - 1) * this.DSP.IAudioMetaInformation[i][j * 8 + i2];
                    }
                    sArray[i][j][k] = (short)n3;
                }
            }
        }
    }

    final void DSP(int[][] nArray) {
        for (int i = 0; i < this.DSP.FFT; ++i) {
            for (int j = 0; j < 16; ++j) {
                nArray[i][j] = 170;
            }
        }
    }

    final int DSP(short s, int n) {
        int n2 = (s > 0 ? s : -s) >> 3;
        if (n2 >= n) {
            n2 = n - 1;
        }
        return n2;
    }

    void DSP(byte[] byArray, byte[] byArray2, int n, int n2) throws responseView {
        int n3 = (int)this.DSP.QTMovieT;
        this.DSP.DSP = n2;
        this.DSP.MetaInfomationCopy = n;
        this.DSP.AacAudioCodec = this.DSP.MetaInfomationCopy * 8;
        this.DSP(byArray, byArray2);
        int n4 = this.DSP.FFT;
        if (this.DSP.IBaseAudioCodec == 1) {
            DstDecodeUtil.DSP(this.DSP.FFT, n3, this.DSP.AiffAudioCodec, this.DSP.AiffMetaDataModel);
            DstDecodeUtil.DSP(this.DSP.FFT, n3, this.DSP.AlacAudioCodec, this.DSP.AlacMetaDataModel);
            this.DSP(this.BufferedAacReader);
            this.DSP(this.AiffAudioCodec);
            this.AacMetaDataModel.DSP(this.AudioFileExtension, this.IAudioFileCodec);
            int n5 = this.AacMetaDataModel.DSP(DstDecodeUtil.DSP((short)this.DSP.IAudioMetaInformation[0][0]), this.AudioFileExtension, this.IAudioFileCodec);
            Arrays.fill(byArray2, 0, n3 * n4 / 8, (byte)0);
            for (int i = 0; i < n3; ++i) {
                int n6 = i / 8;
                for (int j = 0; j < n4; ++j) {
                    short s = 0;
                    short s2 = 0;
                    byte by = this.DSP.AiffMetaDataModel[j][i];
                    short[][] sArray = this.BufferedAacReader[by];
                    int[] nArray = this.AiffAudioCodec[j];
                    s = (short)(sArray[0][nArray[0]] + sArray[1][nArray[1]] + sArray[2][nArray[2]] + sArray[3][nArray[3]] + sArray[4][nArray[4]] + sArray[5][nArray[5]] + sArray[6][nArray[6]] + sArray[7][nArray[7]] + sArray[8][nArray[8]] + sArray[9][nArray[9]] + sArray[10][nArray[10]] + sArray[11][nArray[11]] + sArray[12][nArray[12]] + sArray[13][nArray[13]] + sArray[14][nArray[14]] + sArray[15][nArray[15]]);
                    if (this.DSP.AacMetaDataModel[j] == 1 && i < this.DSP.BufferedAacReader[j]) {
                        s2 = (short)this.AacMetaDataModel.DSP(128, this.AudioFileExtension, this.IAudioFileCodec);
                    } else {
                        byte by2 = this.DSP.AlacMetaDataModel[j][i];
                        int n7 = this.DSP(s, this.DSP.IAudioInputStream[by2]);
                        s2 = (short)this.AacMetaDataModel.DSP(this.AdditionalMetadataValue[by2][n7], this.AudioFileExtension, this.IAudioFileCodec);
                    }
                    short s3 = (short)((s >> 15 ^ s2) & 1);
                    int n8 = n6 * n4 + j;
                    byArray2[n8] = (byte)(byArray2[n8] | (byte)(s3 << 7 - i % 8) & 0xFF);
                    for (int k = 15; k > 0; --k) {
                        this.AiffAudioCodec[j][k] = (this.AiffAudioCodec[j][k] << 1 | this.AiffAudioCodec[j][k - 1] >> 7 & 1) & 0xFF;
                    }
                    this.AiffAudioCodec[j][0] = (this.AiffAudioCodec[j][0] << 1 | s3) & 0xFF;
                }
            }
            n5 = this.AacMetaDataModel.FFT(this.AudioFileExtension, this.IAudioFileCodec);
            if (n5 != 1) {
                throw new responseView("Arithmetic decoding error!", -1);
            }
        }
    }

    static final class DSP {
        int DSP = 1;
        int FFT;
        int responseView;
        int AdditionalMetadataValue;

        DSP() {
        }

        void DSP(byte[] byArray, int n) {
            this.responseView = 4095;
            this.FFT = 0;
            this.AdditionalMetadataValue = 1;
            while (this.AdditionalMetadataValue <= 12) {
                this.FFT <<= 1;
                if (this.AdditionalMetadataValue < n) {
                    this.FFT |= byArray[this.AdditionalMetadataValue];
                }
                ++this.AdditionalMetadataValue;
            }
        }

        int DSP(int n, byte[] byArray, int n2) {
            int n3;
            int n4 = (this.responseView >> 8 | this.responseView >> 7 & 1) * n;
            int n5 = this.responseView - n4;
            if (this.FFT >= n5) {
                n3 = 0;
                this.FFT -= n5;
                this.responseView = n4;
            } else {
                n3 = 1;
                this.responseView = n5;
            }
            while (this.responseView < 2048) {
                this.responseView <<= 1;
                this.FFT <<= 1;
                if (this.AdditionalMetadataValue < n2) {
                    this.FFT |= byArray[this.AdditionalMetadataValue];
                }
                ++this.AdditionalMetadataValue;
            }
            return n3;
        }

        int FFT(byte[] byArray, int n) {
            return this.AdditionalMetadataValue < n - 7 ? 0 : 1;
        }
    }

    static final class AudioFileExtension {
        int[] DSP;
        int[][] FFT;

        AudioFileExtension() {
        }
    }

    static final class AdditionalMetadataValue {
        AdditionalMetadataValue() {
        }
    }

    static final class IAudioMetaInformation {
        byte[] DSP;
        int FFT;
        int responseView;
        int AdditionalMetadataValue;
        byte AudioFileExtension;
        static int[] IAudioFileCodec = new int[]{0, 1, 3, 7, 15, 31, 63, 127, 255};

        IAudioMetaInformation() {
        }

        void DSP() {
            this.AdditionalMetadataValue = 0;
            this.responseView = 0;
            this.AudioFileExtension = 0;
        }

        void DSP(int n) throws responseView {
            this.FFT = n;
        }

        void DSP(byte[] byArray, int n) throws responseView {
            this.DSP(n);
            this.DSP = byArray;
            this.DSP();
        }

        byte FFT(int n) throws responseView {
            if (n > 0) {
                return (byte)this.AudioFileExtension(n);
            }
            if (n == 0) {
                return 0;
            }
            throw new responseView("EOD", -1);
        }

        int responseView(int n) throws responseView {
            if (n > 0) {
                return (int)this.AudioFileExtension(n);
            }
            if (n == 0) {
                return 0;
            }
            throw new responseView("EOD", -1);
        }

        short AdditionalMetadataValue(int n) throws responseView {
            if (n > 0) {
                short s = (short)this.AudioFileExtension(n);
                if (s >= 1 << n - 1) {
                    s = (short)(s - (1 << n));
                }
                return s;
            }
            if (n == 0) {
                return 0;
            }
            throw new responseView("EOD", -1);
        }

        long AudioFileExtension(int n) throws responseView {
            if (n == 1) {
                if (this.AdditionalMetadataValue == 0) {
                    this.AudioFileExtension = this.DSP[this.responseView++];
                    if (this.responseView > this.FFT) {
                        throw new responseView("EOF", -1);
                    }
                    this.AdditionalMetadataValue = 8;
                }
                --this.AdditionalMetadataValue;
                return this.AudioFileExtension >> this.AdditionalMetadataValue & 1;
            }
            long l = 0L;
            while (n > 0) {
                if (this.AdditionalMetadataValue == 0) {
                    this.AudioFileExtension = this.DSP[this.responseView++];
                    if (this.responseView > this.FFT) {
                        throw new responseView("EOF", -1);
                    }
                    this.AdditionalMetadataValue = 8;
                }
                int n2 = this.AdditionalMetadataValue < n ? this.AdditionalMetadataValue : n;
                int n3 = this.AdditionalMetadataValue - n2;
                int n4 = IAudioFileCodec[n2] << n3;
                l = (n3 = n - n2 - n3) <= 0 ? (l |= (long)((this.AudioFileExtension & n4) >> -n3)) : (l |= (long)((this.AudioFileExtension & n4) << n3));
                n -= n2;
                this.AdditionalMetadataValue -= n2;
            }
            return l;
        }

        long FFT() {
            return (long)this.responseView * 8L - (long)this.AdditionalMetadataValue;
        }
    }

    static class FFT {
        int[] DSP = new int[3];
        int[][] FFT = new int[3][3];
        int[] responseView;
        int[] AdditionalMetadataValue;
        int[][] AudioFileExtension;
        int[] IAudioFileCodec;
        int IAudioInputStream;

        FFT(IAudioFileCodec vZagBOcdslnIaKEkojULJvx) {
            this.responseView = new int[vZagBOcdslnIaKEkojULJvx.ChunkInfo];
            this.AdditionalMetadataValue = new int[vZagBOcdslnIaKEkojULJvx.ChunkInfo];
            this.AudioFileExtension = new int[vZagBOcdslnIaKEkojULJvx.DemuxResT][3];
            this.IAudioFileCodec = new int[vZagBOcdslnIaKEkojULJvx.DemuxResT];
        }
    }

    static class IAudioFileCodec {
        int DSP;
        int FFT;
        int responseView;
        int AdditionalMetadataValue;
        int AudioFileExtension;
        int[] IAudioFileCodec = new int[12];
        int[] IAudioInputStream = new int[12];
        int[][] IAudioMetaInformation;
        int IBaseAudioCodec;
        int MetaInfomationCopy;
        long AacAudioCodec;
        int[] AacMetaDataModel = new int[6];
        int[] BufferedAacReader = new int[6];
        IAudioInputStream AiffAudioCodec;
        byte[][] AiffMetaDataModel = new byte[6][37632];
        IAudioInputStream AlacAudioCodec;
        byte[][] AlacMetaDataModel = new byte[6][37632];
        int BufferedAlacReader;
        int AlacContextModel;
        int AlacDecoderUtils;
        int AlacFile;
        int AlacInputStream;
        int AlacUtils;
        int ChunkInfo;
        int DemuxResT;
        int DemuxUtils;
        long LeadingZeros;
        long MyStream;
        long QTMovieT;

        IAudioFileCodec() {
            this.AiffAudioCodec = new IAudioInputStream();
            this.AlacAudioCodec = new IAudioInputStream();
        }
    }

    static class IAudioInputStream {
        int DSP;
        int[][] FFT = new int[6][8];
        int[] responseView = new int[6];
        int[][] AdditionalMetadataValue = new int[6][8];

        IAudioInputStream() {
        }
    }

    static class responseView
    extends Exception {
        int DSP;

        public responseView(String string, int n) {
            super(string);
            this.DSP = n;
        }
    }
}

