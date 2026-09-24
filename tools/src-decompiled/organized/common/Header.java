/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.ByteData;
import sdfgjkljljoftrytrszgijpokjprs.StreamInfo;
import sdfgjkljljoftrytrszgijpokjprs.CRC8;
import sdfgjkljljoftrytrszgijpokjprs.BadHeaderException;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;

public class Header {
    public int DSP;
    public int FFT;
    public int responseView;
    public int AdditionalMetadataValue;
    public int AudioFileExtension;
    public int IAudioFileCodec;
    public long IAudioInputStream;

    public Header(BitInputStream hrgPgVhiQYIZXeWOluMaPwR2, byte[] byArray, StreamInfo kTkvbLlxuYGcvSGyJtmnFsX) throws IOException, BadHeaderException {
        int n;
        int n2;
        boolean bl;
        ByteData jBphLoDiqESKYDzcdAUENWI;
        int n3;
        int n4;
        block54: {
            int n5;
            block53: {
                int n6;
                this.IAudioFileCodec = -1;
                this.IAudioInputStream = -1L;
                n4 = 0;
                n3 = 0;
                jBphLoDiqESKYDzcdAUENWI = new ByteData(16);
                bl = kTkvbLlxuYGcvSGyJtmnFsX != null && kTkvbLlxuYGcvSGyJtmnFsX.AdditionalMetadataValue() != kTkvbLlxuYGcvSGyJtmnFsX.responseView();
                boolean bl2 = kTkvbLlxuYGcvSGyJtmnFsX != null && kTkvbLlxuYGcvSGyJtmnFsX.AdditionalMetadataValue() == kTkvbLlxuYGcvSGyJtmnFsX.responseView();
                jBphLoDiqESKYDzcdAUENWI.DSP(byArray[0]);
                jBphLoDiqESKYDzcdAUENWI.DSP(byArray[1]);
                if ((jBphLoDiqESKYDzcdAUENWI.DSP(1) & 2) != 0) {
                    throw new BadHeaderException("Bad Magic Number: " + (jBphLoDiqESKYDzcdAUENWI.DSP(1) & 0xFF));
                }
                for (n6 = 0; n6 < 2; ++n6) {
                    if (hrgPgVhiQYIZXeWOluMaPwR2.AdditionalMetadataValue(8) == 255) {
                        hrgPgVhiQYIZXeWOluMaPwR2.responseView(8);
                        throw new BadHeaderException("Found sync byte");
                    }
                    jBphLoDiqESKYDzcdAUENWI.DSP((byte)hrgPgVhiQYIZXeWOluMaPwR2.responseView(8));
                }
                n6 = jBphLoDiqESKYDzcdAUENWI.DSP(2) >> 4 & 0xF;
                switch (n6) {
                    case 0: {
                        if (!bl2) {
                            throw new BadHeaderException("Unknown Block Size (0)");
                        }
                        this.DSP = kTkvbLlxuYGcvSGyJtmnFsX.AdditionalMetadataValue();
                        break;
                    }
                    case 1: {
                        this.DSP = 192;
                        break;
                    }
                    case 2: 
                    case 3: 
                    case 4: 
                    case 5: {
                        this.DSP = 576 << n6 - 2;
                        break;
                    }
                    case 6: 
                    case 7: {
                        n4 = n6;
                        break;
                    }
                    case 8: 
                    case 9: 
                    case 10: 
                    case 11: 
                    case 12: 
                    case 13: 
                    case 14: 
                    case 15: {
                        this.DSP = 256 << n6 - 8;
                        break;
                    }
                }
                int n7 = jBphLoDiqESKYDzcdAUENWI.DSP(2) & 0xF;
                switch (n7) {
                    case 0: {
                        if (kTkvbLlxuYGcvSGyJtmnFsX == null) {
                            throw new BadHeaderException("Bad Sample Rate (0)");
                        }
                        this.FFT = kTkvbLlxuYGcvSGyJtmnFsX.IAudioFileCodec();
                        break;
                    }
                    case 1: {
                        this.FFT = 88200;
                        break;
                    }
                    case 2: {
                        this.FFT = 176400;
                        break;
                    }
                    case 3: {
                        this.FFT = 192000;
                        break;
                    }
                    case 4: {
                        this.FFT = 8000;
                        break;
                    }
                    case 5: {
                        this.FFT = 16000;
                        break;
                    }
                    case 6: {
                        this.FFT = 22050;
                        break;
                    }
                    case 7: {
                        this.FFT = 24000;
                        break;
                    }
                    case 8: {
                        this.FFT = 32000;
                        break;
                    }
                    case 9: {
                        this.FFT = 44100;
                        break;
                    }
                    case 10: {
                        this.FFT = 48000;
                        break;
                    }
                    case 11: {
                        this.FFT = 96000;
                        break;
                    }
                    case 12: 
                    case 13: 
                    case 14: {
                        n3 = n7;
                        break;
                    }
                    case 15: {
                        throw new BadHeaderException("Bad Sample Rate (" + n7 + ")");
                    }
                }
                n5 = jBphLoDiqESKYDzcdAUENWI.DSP(3) >> 4 & 0xF;
                if ((n5 & 8) == 0) break block53;
                this.responseView = 2;
                switch (n5 & 7) {
                    case 0: {
                        this.AdditionalMetadataValue = 1;
                        break block54;
                    }
                    case 1: {
                        this.AdditionalMetadataValue = 2;
                        break block54;
                    }
                    case 2: {
                        this.AdditionalMetadataValue = 3;
                        break block54;
                    }
                    default: {
                        throw new BadHeaderException("Bad Channel Assignment (" + n5 + ")");
                    }
                }
            }
            this.responseView = n5 + 1;
            this.AdditionalMetadataValue = 0;
        }
        int n8 = (jBphLoDiqESKYDzcdAUENWI.DSP(3) & 0xE) >> 1;
        switch (n8) {
            case 0: {
                if (kTkvbLlxuYGcvSGyJtmnFsX != null) {
                    this.AudioFileExtension = kTkvbLlxuYGcvSGyJtmnFsX.IAudioInputStream();
                    break;
                }
                throw new BadHeaderException("Bad BPS (" + n8 + ")");
            }
            case 1: {
                this.AudioFileExtension = 8;
                break;
            }
            case 2: {
                this.AudioFileExtension = 12;
                break;
            }
            case 4: {
                this.AudioFileExtension = 16;
                break;
            }
            case 5: {
                this.AudioFileExtension = 20;
                break;
            }
            case 6: {
                this.AudioFileExtension = 24;
                break;
            }
            case 3: 
            case 7: {
                throw new BadHeaderException("Bad BPS (" + n8 + ")");
            }
        }
        if ((jBphLoDiqESKYDzcdAUENWI.DSP(3) & 1) != 0) {
            throw new BadHeaderException("this should be a zero padding bit");
        }
        if (n4 != 0 && bl) {
            this.IAudioInputStream = hrgPgVhiQYIZXeWOluMaPwR2.FFT(jBphLoDiqESKYDzcdAUENWI);
            if (this.IAudioInputStream == -1L) {
                throw new BadHeaderException("Bad Sample Number");
            }
        } else {
            this.IAudioFileCodec = hrgPgVhiQYIZXeWOluMaPwR2.DSP(jBphLoDiqESKYDzcdAUENWI);
            if (this.IAudioFileCodec == -1) {
                throw new BadHeaderException("Bad Last Frame");
            }
            this.IAudioInputStream = (long)kTkvbLlxuYGcvSGyJtmnFsX.AdditionalMetadataValue() * (long)this.IAudioFileCodec;
        }
        if (n4 != 0) {
            n2 = hrgPgVhiQYIZXeWOluMaPwR2.responseView(8);
            jBphLoDiqESKYDzcdAUENWI.DSP((byte)n2);
            if (n4 == 7) {
                n = hrgPgVhiQYIZXeWOluMaPwR2.responseView(8);
                jBphLoDiqESKYDzcdAUENWI.DSP((byte)n);
                n2 = n2 << 8 | n;
            }
            this.DSP = n2 + 1;
        }
        if (n3 != 0) {
            n2 = hrgPgVhiQYIZXeWOluMaPwR2.responseView(8);
            jBphLoDiqESKYDzcdAUENWI.DSP((byte)n2);
            if (n3 != 12) {
                n = hrgPgVhiQYIZXeWOluMaPwR2.responseView(8);
                jBphLoDiqESKYDzcdAUENWI.DSP((byte)n);
                n2 = n2 << 8 | n;
            }
            this.FFT = n3 == 12 ? n2 * 1000 : (n3 == 13 ? n2 : n2 * 10);
        }
        n2 = (byte)hrgPgVhiQYIZXeWOluMaPwR2.responseView(8);
        if (CRC8.DSP(jBphLoDiqESKYDzcdAUENWI.DSP(), jBphLoDiqESKYDzcdAUENWI.FFT()) != n2) {
            throw new BadHeaderException("STREAM_DECODER_ERROR_STATUS_BAD_HEADER");
        }
    }

    public String toString() {
        return "FrameHeader: BlockSize=" + this.DSP + " SampleRate=" + this.FFT + " Channels=" + this.responseView + " ChannelAssignment=" + this.AdditionalMetadataValue + " BPS=" + this.AudioFileExtension + " SampleNumber=" + this.IAudioInputStream;
    }
}

