/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import sdfgjkljljoftrytrszgijpokjprs.ByteData;
import sdfgjkljljoftrytrszgijpokjprs.CRC16;

public class BitInputStream {
    private final byte[] DSP = new byte[1024];
    private int FFT = 0;
    private int responseView = 0;
    private int AdditionalMetadataValue = 0;
    private int AudioFileExtension = 0;
    private int IAudioFileCodec = 0;
    private short IAudioInputStream = 0;
    private final InputStream IAudioMetaInformation;

    public BitInputStream(InputStream inputStream) {
        this.IAudioMetaInformation = inputStream;
    }

    private int IAudioInputStream() throws IOException {
        if (this.responseView > 0 && this.FFT > this.responseView) {
            System.arraycopy(this.DSP, this.responseView, this.DSP, 0, this.FFT - this.responseView);
        }
        this.FFT -= this.responseView;
        this.responseView = 0;
        int n = this.DSP.length - this.FFT;
        if ((n = this.IAudioMetaInformation.read(this.DSP, this.FFT, n)) <= 0) {
            throw new EOFException();
        }
        this.FFT += n;
        this.AudioFileExtension += n << 3;
        return n;
    }

    public void DSP(short s) {
        this.IAudioInputStream = s;
    }

    public short DSP() {
        return this.IAudioInputStream;
    }

    public boolean FFT() {
        return (this.AdditionalMetadataValue & 7) == 0;
    }

    public int responseView() {
        return 8 - (this.AdditionalMetadataValue & 7);
    }

    public void DSP(int n) throws IOException {
        int n2;
        if (n == 0) {
            return;
        }
        int n3 = this.AdditionalMetadataValue & 7;
        if (n3 != 0) {
            n2 = Math.min(8 - n3, n);
            this.responseView(n2);
            n -= n2;
        }
        if ((n2 = n / 8) > 0) {
            this.DSP(null, n2);
            n %= 8;
        }
        if (n > 0) {
            this.responseView(n);
        }
    }

    public int AdditionalMetadataValue() throws IOException {
        while (true) {
            if (this.AudioFileExtension > 0) {
                int n = (this.DSP[this.responseView] & 128 >> this.AdditionalMetadataValue) != 0 ? 1 : 0;
                ++this.AdditionalMetadataValue;
                if (this.AdditionalMetadataValue == 8) {
                    this.IAudioInputStream = CRC16.DSP(this.DSP[this.responseView], this.IAudioInputStream);
                    ++this.responseView;
                    this.AdditionalMetadataValue = 0;
                }
                --this.AudioFileExtension;
                ++this.IAudioFileCodec;
                return n;
            }
            this.IAudioInputStream();
        }
    }

    public int FFT(int n) throws IOException {
        while (true) {
            if (this.AudioFileExtension > 0) {
                n <<= 1;
                n |= (this.DSP[this.responseView] & 128 >> this.AdditionalMetadataValue) != 0 ? 1 : 0;
                ++this.AdditionalMetadataValue;
                if (this.AdditionalMetadataValue == 8) {
                    this.IAudioInputStream = CRC16.DSP(this.DSP[this.responseView], this.IAudioInputStream);
                    ++this.responseView;
                    this.AdditionalMetadataValue = 0;
                }
                --this.AudioFileExtension;
                ++this.IAudioFileCodec;
                return n;
            }
            this.IAudioInputStream();
        }
    }

    public int DSP(int n, int n2) throws IOException {
        while (true) {
            if (n2 < this.AudioFileExtension) {
                n <<= 1;
                n = this.AdditionalMetadataValue + n2 >= 8 ? (n |= (this.DSP[this.responseView + 1] & 128 >> (n2 = (this.AdditionalMetadataValue + n2) % 8)) != 0 ? 1 : 0) : (n |= (this.DSP[this.responseView] & 128 >> this.AdditionalMetadataValue + n2) != 0 ? 1 : 0);
                return n;
            }
            this.IAudioInputStream();
        }
    }

    public long DSP(long l) throws IOException {
        while (true) {
            if (this.AudioFileExtension > 0) {
                l <<= 1;
                l |= (this.DSP[this.responseView] & 128 >> this.AdditionalMetadataValue) != 0 ? 1L : 0L;
                ++this.AdditionalMetadataValue;
                if (this.AdditionalMetadataValue == 8) {
                    this.IAudioInputStream = CRC16.DSP(this.DSP[this.responseView], this.IAudioInputStream);
                    ++this.responseView;
                    this.AdditionalMetadataValue = 0;
                }
                --this.AudioFileExtension;
                ++this.IAudioFileCodec;
                return l;
            }
            this.IAudioInputStream();
        }
    }

    public int responseView(int n) throws IOException {
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            n2 = this.FFT(n2);
        }
        return n2;
    }

    public int AdditionalMetadataValue(int n) throws IOException {
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            n2 = this.DSP(n2, i);
        }
        return n2;
    }

    public int AudioFileExtension(int n) throws IOException {
        int n2;
        if (n == 0) {
            return 0;
        }
        int n3 = 0;
        for (n2 = 0; n2 < n; ++n2) {
            n3 = this.FFT(n3);
        }
        int n4 = 32 - n;
        if (n4 != 0) {
            n2 = n3 <<= n4;
            n2 >>= n4;
        } else {
            n2 = n3;
        }
        return n2;
    }

    public long IAudioFileCodec(int n) throws IOException {
        long l = 0L;
        for (int i = 0; i < n; ++i) {
            l = this.DSP(l);
        }
        return l;
    }

    public int AudioFileExtension() throws IOException {
        int n = this.responseView(8);
        int n2 = this.responseView(8);
        n |= n2 << 8;
        n2 = this.responseView(8);
        n |= n2 << 16;
        n2 = this.responseView(8);
        return n |= n2 << 24;
    }

    public void DSP(byte[] byArray, int n) throws IOException {
        int n2 = n;
        while (n > 0) {
            int n3 = Math.min(n, this.FFT - this.responseView);
            if (n3 <= 0) {
                this.IAudioInputStream();
                continue;
            }
            if (byArray != null) {
                System.arraycopy(this.DSP, this.responseView, byArray, n2 - n, n3);
            }
            n -= n3;
            this.responseView += n3;
            this.AudioFileExtension -= n3 << 3;
            this.IAudioFileCodec += n3 << 3;
        }
    }

    public int IAudioFileCodec() throws IOException {
        int n;
        int n2 = 0;
        while ((n = this.AdditionalMetadataValue()) == 0) {
            ++n2;
        }
        return n2;
    }

    public void DSP(int[] nArray, int n, int n2, int n3) throws IOException {
        int n4;
        int n5;
        byte by;
        byte by2;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        if (n2 == 0) {
            return;
        }
        int n12 = this.responseView;
        long l = this.responseView * 8 + this.AdditionalMetadataValue;
        if (this.AdditionalMetadataValue > 0) {
            by = by2 = this.DSP[n12];
            n7 = this.AdditionalMetadataValue;
            by2 = (byte)(by2 << n7);
            while (true) {
                if (n11 == 0) {
                    if (by2 != 0) {
                        n5 = 0;
                        while ((by2 & 0xFFFFFF80) == 0) {
                            by2 = (byte)(by2 << 1);
                            ++n5;
                        }
                        n9 += n5;
                        by2 = (byte)(by2 << 1);
                        n8 = 0;
                        n10 = n3;
                        ++n11;
                        if ((n7 += ++n5) != 8) continue;
                        n7 = 0;
                        this.IAudioInputStream = CRC16.DSP(by, this.IAudioInputStream);
                        break;
                    }
                    n9 += 8 - n7;
                    n7 = 0;
                    this.IAudioInputStream = CRC16.DSP(by, this.IAudioInputStream);
                    break;
                }
                n4 = 8 - n7;
                if (n10 >= n4) {
                    n8 <<= n4;
                    n8 |= (by2 & 0xFF) >> n7;
                    n7 = 0;
                    this.IAudioInputStream = CRC16.DSP(by, this.IAudioInputStream);
                    if (n10 == n4) {
                        nArray[n + n6++] = ((n8 |= n9 << n3) & 1) != 0 ? -(n8 >> 1) - 1 : n8 >> 1;
                        if (n6 == n2) break;
                        n9 = 0;
                        n11 = 0;
                    }
                    n10 -= n4;
                    break;
                }
                n8 <<= n10;
                n8 |= (by2 & 0xFF) >> 8 - n10;
                by2 = (byte)(by2 << n10);
                n7 += n10;
                nArray[n + n6++] = ((n8 |= n9 << n3) & 1) != 0 ? -(n8 >> 1) - 1 : n8 >> 1;
                if (n6 == n2) {
                    --n12;
                    break;
                }
                n9 = 0;
                n11 = 0;
            }
            this.responseView = ++n12;
            this.AdditionalMetadataValue = n7;
        }
        while (n6 < n2) {
            while (n12 < this.FFT && n6 < n2) {
                by = by2 = this.DSP[n12];
                n7 = 0;
                while (true) {
                    if (n11 == 0) {
                        if (by2 != 0) {
                            n5 = 0;
                            while ((by2 & 0xFFFFFF80) == 0) {
                                by2 = (byte)(by2 << 1);
                                ++n5;
                            }
                            n9 += n5;
                            by2 = (byte)(by2 << 1);
                            n8 = 0;
                            n10 = n3;
                            ++n11;
                            if ((n7 += ++n5) != 8) continue;
                            n7 = 0;
                            this.IAudioInputStream = CRC16.DSP(by, this.IAudioInputStream);
                            break;
                        }
                        n9 += 8 - n7;
                        n7 = 0;
                        this.IAudioInputStream = CRC16.DSP(by, this.IAudioInputStream);
                        break;
                    }
                    n4 = 8 - n7;
                    if (n10 >= n4) {
                        n8 <<= n4;
                        n8 |= (by2 & 0xFF) >> n7;
                        n7 = 0;
                        this.IAudioInputStream = CRC16.DSP(by, this.IAudioInputStream);
                        if (n10 == n4) {
                            nArray[n + n6++] = ((n8 |= n9 << n3) & 1) != 0 ? -(n8 >> 1) - 1 : n8 >> 1;
                            if (n6 == n2) break;
                            n9 = 0;
                            n11 = 0;
                        }
                        n10 -= n4;
                        break;
                    }
                    n8 <<= n10;
                    n8 |= (by2 & 0xFF) >> 8 - n10;
                    by2 = (byte)(by2 << n10);
                    n7 += n10;
                    nArray[n + n6++] = ((n8 |= n9 << n3) & 1) != 0 ? -(n8 >> 1) - 1 : n8 >> 1;
                    if (n6 == n2) {
                        --n12;
                        break;
                    }
                    n9 = 0;
                    n11 = 0;
                }
                ++n12;
            }
            this.responseView = n12;
            this.AdditionalMetadataValue = n7;
            if (n6 >= n2) continue;
            long l2 = this.responseView * 8 + this.AdditionalMetadataValue;
            this.IAudioFileCodec = (int)((long)this.IAudioFileCodec + (l2 - l));
            this.AudioFileExtension = (int)((long)this.AudioFileExtension - (l2 - l));
            this.IAudioInputStream();
            n12 = 0;
            l = this.responseView * 8 + this.AdditionalMetadataValue;
        }
        long l3 = this.responseView * 8 + this.AdditionalMetadataValue;
        this.IAudioFileCodec = (int)((long)this.IAudioFileCodec + (l3 - l));
        this.AudioFileExtension = (int)((long)this.AudioFileExtension - (l3 - l));
    }

    public int DSP(ByteData jBphLoDiqESKYDzcdAUENWI) throws IOException {
        int n;
        int n2 = 0;
        int n3 = this.responseView(8);
        if (jBphLoDiqESKYDzcdAUENWI != null) {
            jBphLoDiqESKYDzcdAUENWI.DSP((byte)n3);
        }
        if ((n3 & 0x80) == 0) {
            n2 = n3;
            n = 0;
        } else if ((n3 & 0xC0) != 0 && (n3 & 0x20) == 0) {
            n2 = n3 & 0x1F;
            n = 1;
        } else if ((n3 & 0xE0) != 0 && (n3 & 0x10) == 0) {
            n2 = n3 & 0xF;
            n = 2;
        } else if ((n3 & 0xF0) != 0 && (n3 & 8) == 0) {
            n2 = n3 & 7;
            n = 3;
        } else if ((n3 & 0xF8) != 0 && (n3 & 4) == 0) {
            n2 = n3 & 3;
            n = 4;
        } else if ((n3 & 0xFC) != 0 && (n3 & 2) == 0) {
            n2 = n3 & 1;
            n = 5;
        } else {
            return -1;
        }
        while (n > 0) {
            n3 = this.AdditionalMetadataValue(8);
            if ((n3 & 0x80) == 0 || (n3 & 0x40) != 0) {
                return -1;
            }
            n3 = this.responseView(8);
            if (jBphLoDiqESKYDzcdAUENWI != null) {
                jBphLoDiqESKYDzcdAUENWI.DSP((byte)n3);
            }
            n2 <<= 6;
            n2 |= n3 & 0x3F;
            --n;
        }
        return n2;
    }

    public long FFT(ByteData jBphLoDiqESKYDzcdAUENWI) throws IOException {
        int n;
        long l = 0L;
        int n2 = this.responseView(8);
        if (jBphLoDiqESKYDzcdAUENWI != null) {
            jBphLoDiqESKYDzcdAUENWI.DSP((byte)n2);
        }
        if ((n2 & 0x80) == 0) {
            l = n2;
            n = 0;
        } else if ((n2 & 0xC0) != 0 && (n2 & 0x20) == 0) {
            l = n2 & 0x1F;
            n = 1;
        } else if ((n2 & 0xE0) != 0 && (n2 & 0x10) == 0) {
            l = n2 & 0xF;
            n = 2;
        } else if ((n2 & 0xF0) != 0 && (n2 & 8) == 0) {
            l = n2 & 7;
            n = 3;
        } else if ((n2 & 0xF8) != 0 && (n2 & 4) == 0) {
            l = n2 & 3;
            n = 4;
        } else if ((n2 & 0xFC) != 0 && (n2 & 2) == 0) {
            l = n2 & 1;
            n = 5;
        } else if ((n2 & 0xFE) != 0 && (n2 & 1) == 0) {
            l = 0L;
            n = 6;
        } else {
            return -1L;
        }
        while (n > 0) {
            n2 = this.AdditionalMetadataValue(8);
            if ((n2 & 0x80) == 0 || (n2 & 0x40) != 0) {
                return -1L;
            }
            n2 = this.responseView(8);
            if (jBphLoDiqESKYDzcdAUENWI != null) {
                jBphLoDiqESKYDzcdAUENWI.DSP((byte)n2);
            }
            l <<= 6;
            l |= (long)(n2 & 0x3F);
            --n;
        }
        return l;
    }
}

