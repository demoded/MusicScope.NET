/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.ChannelData;
import sdfgjkljljoftrytrszgijpokjprs.BitMath;
import sdfgjkljljoftrytrszgijpokjprs.FrameDecodeException;
import sdfgjkljljoftrytrszgijpokjprs.Channel;
import sdfgjkljljoftrytrszgijpokjprs.EntropyPartitionedRice2;
import sdfgjkljljoftrytrszgijpokjprs.EntropyPartitionedRice;
import sdfgjkljljoftrytrszgijpokjprs.LPCPredictor;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;
import sdfgjkljljoftrytrszgijpokjprs.Header;
import sdfgjkljljoftrytrszgijpokjprs.EntropyCodingMethod;

public class ChannelLPC
extends Channel {
    private EntropyCodingMethod responseView;
    private int AdditionalMetadataValue;
    private int AudioFileExtension;
    private int IAudioFileCodec;
    private int[] IAudioInputStream = new int[32];
    private int[] IAudioMetaInformation = new int[32];
    private int[] IBaseAudioCodec;

    public ChannelLPC(BitInputStream hrgPgVhiQYIZXeWOluMaPwR2, Header kQcqxPiJjcCJzfxjxICEYyx2, ChannelData eXlCnePJhpzaNHMHTvZqRrN, int n, int n2, int n3) throws IOException, FrameDecodeException {
        super(kQcqxPiJjcCJzfxjxICEYyx2, n2);
        int n4;
        int n5;
        this.IBaseAudioCodec = eXlCnePJhpzaNHMHTvZqRrN.responseView();
        this.AdditionalMetadataValue = n3;
        for (n5 = 0; n5 < n3; ++n5) {
            this.IAudioMetaInformation[n5] = hrgPgVhiQYIZXeWOluMaPwR2.AudioFileExtension(n);
        }
        n5 = hrgPgVhiQYIZXeWOluMaPwR2.responseView(4);
        if (n5 == 15) {
            throw new FrameDecodeException("STREAM_DECODER_ERROR_STATUS_LOST_SYNC");
        }
        this.AudioFileExtension = n5 + 1;
        this.IAudioFileCodec = hrgPgVhiQYIZXeWOluMaPwR2.AudioFileExtension(5);
        for (n4 = 0; n4 < n3; ++n4) {
            this.IAudioInputStream[n4] = hrgPgVhiQYIZXeWOluMaPwR2.AudioFileExtension(this.AudioFileExtension);
        }
        n4 = hrgPgVhiQYIZXeWOluMaPwR2.responseView(2);
        switch (n4) {
            case 0: {
                this.responseView = new EntropyPartitionedRice();
                break;
            }
            case 1: {
                this.responseView = new EntropyPartitionedRice2();
                break;
            }
            default: {
                throw new FrameDecodeException("STREAM_DECODER_UNPARSEABLE_STREAM, " + n4);
            }
        }
        this.responseView.DSP = hrgPgVhiQYIZXeWOluMaPwR2.responseView(4);
        this.responseView.FFT = eXlCnePJhpzaNHMHTvZqRrN.FFT();
        this.responseView.DSP(hrgPgVhiQYIZXeWOluMaPwR2, n3, this.responseView.DSP, kQcqxPiJjcCJzfxjxICEYyx2, eXlCnePJhpzaNHMHTvZqRrN.responseView());
        System.arraycopy(this.IAudioMetaInformation, 0, eXlCnePJhpzaNHMHTvZqRrN.DSP(), 0, n3);
        if (n + this.AudioFileExtension + BitMath.DSP(n3) <= 32) {
            if (n <= 16 && this.AudioFileExtension <= 16) {
                LPCPredictor.DSP(eXlCnePJhpzaNHMHTvZqRrN.responseView(), kQcqxPiJjcCJzfxjxICEYyx2.DSP - n3, this.IAudioInputStream, n3, this.IAudioFileCodec, eXlCnePJhpzaNHMHTvZqRrN.DSP(), n3);
            } else {
                LPCPredictor.DSP(eXlCnePJhpzaNHMHTvZqRrN.responseView(), kQcqxPiJjcCJzfxjxICEYyx2.DSP - n3, this.IAudioInputStream, n3, this.IAudioFileCodec, eXlCnePJhpzaNHMHTvZqRrN.DSP(), n3);
            }
        } else {
            LPCPredictor.FFT(eXlCnePJhpzaNHMHTvZqRrN.responseView(), kQcqxPiJjcCJzfxjxICEYyx2.DSP - n3, this.IAudioInputStream, n3, this.IAudioFileCodec, eXlCnePJhpzaNHMHTvZqRrN.DSP(), n3);
        }
    }

    public String toString() {
        int n;
        StringBuffer stringBuffer = new StringBuffer("ChannelLPC: Order=" + this.AdditionalMetadataValue + " WastedBits=" + this.FFT);
        stringBuffer.append(" qlpCoeffPrecision=" + this.AudioFileExtension + " quantizationLevel=" + this.IAudioFileCodec);
        stringBuffer.append("\n\t\tqlpCoeff: ");
        for (n = 0; n < this.AdditionalMetadataValue; ++n) {
            stringBuffer.append(this.IAudioInputStream[n] + " ");
        }
        stringBuffer.append("\n\t\tWarmup: ");
        for (n = 0; n < this.AdditionalMetadataValue; ++n) {
            stringBuffer.append(this.IAudioMetaInformation[n] + " ");
        }
        stringBuffer.append("\n\t\tParameter: ");
        for (n = 0; n < 1 << ((EntropyPartitionedRice)this.responseView).DSP; ++n) {
            stringBuffer.append(((EntropyPartitionedRice)this.responseView).FFT.DSP[n] + " ");
        }
        return stringBuffer.toString();
    }
}

