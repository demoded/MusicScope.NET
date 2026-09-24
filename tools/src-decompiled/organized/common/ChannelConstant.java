/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.ChannelData;
import sdfgjkljljoftrytrszgijpokjprs.Channel;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;
import sdfgjkljljoftrytrszgijpokjprs.Header;

public class ChannelConstant
extends Channel {
    private int responseView;

    public ChannelConstant(BitInputStream hrgPgVhiQYIZXeWOluMaPwR2, Header kQcqxPiJjcCJzfxjxICEYyx2, ChannelData eXlCnePJhpzaNHMHTvZqRrN, int n, int n2) throws IOException {
        super(kQcqxPiJjcCJzfxjxICEYyx2, n2);
        this.responseView = hrgPgVhiQYIZXeWOluMaPwR2.AudioFileExtension(n);
        for (int i = 0; i < kQcqxPiJjcCJzfxjxICEYyx2.DSP; ++i) {
            eXlCnePJhpzaNHMHTvZqRrN.DSP()[i] = this.responseView;
        }
    }

    public String toString() {
        return "ChannelConstant: Value=" + this.responseView + " WastedBits=" + this.FFT;
    }
}

