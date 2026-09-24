/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.ChannelData;
import sdfgjkljljoftrytrszgijpokjprs.Channel;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;
import sdfgjkljljoftrytrszgijpokjprs.Header;

public class ChannelVerbatim
extends Channel {
    private final int[] responseView;

    public ChannelVerbatim(BitInputStream hrgPgVhiQYIZXeWOluMaPwR2, Header kQcqxPiJjcCJzfxjxICEYyx2, ChannelData eXlCnePJhpzaNHMHTvZqRrN, int n, int n2) throws IOException {
        super(kQcqxPiJjcCJzfxjxICEYyx2, n2);
        this.responseView = eXlCnePJhpzaNHMHTvZqRrN.responseView();
        for (int i = 0; i < kQcqxPiJjcCJzfxjxICEYyx2.DSP; ++i) {
            this.responseView[i] = hrgPgVhiQYIZXeWOluMaPwR2.AudioFileExtension(n);
        }
        System.arraycopy(this.responseView, 0, eXlCnePJhpzaNHMHTvZqRrN.DSP(), 0, kQcqxPiJjcCJzfxjxICEYyx2.DSP);
    }

    public String toString() {
        return "ChannelVerbatim: WastedBits=" + this.FFT;
    }
}

