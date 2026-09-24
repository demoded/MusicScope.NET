/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.ChannelData;
import sdfgjkljljoftrytrszgijpokjprs.FrameDecodeException;
import sdfgjkljljoftrytrszgijpokjprs.Channel;
import sdfgjkljljoftrytrszgijpokjprs.FixedPredictor;
import sdfgjkljljoftrytrszgijpokjprs.EntropyPartitionedRice2;
import sdfgjkljljoftrytrszgijpokjprs.EntropyPartitionedRice;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;
import sdfgjkljljoftrytrszgijpokjprs.Header;
import sdfgjkljljoftrytrszgijpokjprs.EntropyCodingMethod;

public class ChannelFixed
extends Channel {
    private EntropyCodingMethod responseView;
    private int AdditionalMetadataValue;
    private int[] AudioFileExtension = new int[4];
    private int[] IAudioFileCodec;

    public ChannelFixed(BitInputStream hrgPgVhiQYIZXeWOluMaPwR2, Header kQcqxPiJjcCJzfxjxICEYyx2, ChannelData eXlCnePJhpzaNHMHTvZqRrN, int n, int n2, int n3) throws IOException, FrameDecodeException {
        super(kQcqxPiJjcCJzfxjxICEYyx2, n2);
        EntropyCodingMethod pBrXGRDpfdXbiVOtIFewNeq2;
        int n4;
        this.IAudioFileCodec = eXlCnePJhpzaNHMHTvZqRrN.responseView();
        this.AdditionalMetadataValue = n3;
        for (n4 = 0; n4 < n3; ++n4) {
            this.AudioFileExtension[n4] = hrgPgVhiQYIZXeWOluMaPwR2.AudioFileExtension(n);
        }
        n4 = hrgPgVhiQYIZXeWOluMaPwR2.responseView(2);
        switch (n4) {
            case 0: {
                pBrXGRDpfdXbiVOtIFewNeq2 = new EntropyPartitionedRice();
                break;
            }
            case 1: {
                pBrXGRDpfdXbiVOtIFewNeq2 = new EntropyPartitionedRice2();
                break;
            }
            default: {
                throw new FrameDecodeException("STREAM_DECODER_UNPARSEABLE_STREAM, type:" + n4);
            }
        }
        this.responseView = pBrXGRDpfdXbiVOtIFewNeq2;
        pBrXGRDpfdXbiVOtIFewNeq2.DSP = hrgPgVhiQYIZXeWOluMaPwR2.responseView(4);
        pBrXGRDpfdXbiVOtIFewNeq2.FFT = eXlCnePJhpzaNHMHTvZqRrN.FFT();
        pBrXGRDpfdXbiVOtIFewNeq2.DSP(hrgPgVhiQYIZXeWOluMaPwR2, n3, pBrXGRDpfdXbiVOtIFewNeq2.DSP, kQcqxPiJjcCJzfxjxICEYyx2, eXlCnePJhpzaNHMHTvZqRrN.responseView());
        System.arraycopy(this.AudioFileExtension, 0, eXlCnePJhpzaNHMHTvZqRrN.DSP(), 0, n3);
        FixedPredictor.DSP(this.IAudioFileCodec, kQcqxPiJjcCJzfxjxICEYyx2.DSP - n3, n3, eXlCnePJhpzaNHMHTvZqRrN.DSP(), n3);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("FLACSubframe_Fixed: Order=" + this.AdditionalMetadataValue + " PartitionOrder=" + ((EntropyPartitionedRice)this.responseView).DSP + " WastedBits=" + this.FFT);
        for (int i = 0; i < this.AdditionalMetadataValue; ++i) {
            stringBuffer.append(" warmup[" + i + "]=" + this.AudioFileExtension[i]);
        }
        return stringBuffer.toString();
    }
}

