/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;
import sdfgjkljljoftrytrszgijpokjprs.Header;
import sdfgjkljljoftrytrszgijpokjprs.EntropyCodingMethod;

public class EntropyPartitionedRice2
extends EntropyCodingMethod {
    @Override
    void DSP(BitInputStream hrgPgVhiQYIZXeWOluMaPwR2, int n, int n2, Header kQcqxPiJjcCJzfxjxICEYyx2, int[] nArray) throws IOException {
        int n3;
        int n4 = 0;
        int n5 = 1 << n2;
        int n6 = n3 = n2 > 0 ? kQcqxPiJjcCJzfxjxICEYyx2.DSP >> n2 : kQcqxPiJjcCJzfxjxICEYyx2.DSP - n;
        if (n == 0 ? kQcqxPiJjcCJzfxjxICEYyx2.DSP < n : n3 < n) {
            return;
        }
        this.FFT.DSP(Math.max(6, n2));
        for (int i = 0; i < n5; ++i) {
            int n7;
            int n8;
            this.FFT.DSP[i] = n8 = hrgPgVhiQYIZXeWOluMaPwR2.responseView(5);
            if (n8 < 31) {
                n7 = n2 == 0 || i > 0 ? n3 : n3 - n;
                hrgPgVhiQYIZXeWOluMaPwR2.DSP(nArray, n4, n7, n8);
                n4 += n7;
                continue;
            }
            this.FFT.FFT[i] = n8 = hrgPgVhiQYIZXeWOluMaPwR2.responseView(5);
            int n9 = n7 = n2 == 0 || i > 0 ? 0 : n;
            while (n7 < n3) {
                nArray[n4] = hrgPgVhiQYIZXeWOluMaPwR2.AudioFileExtension(n8);
                ++n7;
                ++n4;
            }
        }
    }
}

