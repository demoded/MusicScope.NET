/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.IRootChunkStructure;

public class ChunkAddedInfo {
    private boolean DSP;
    private boolean FFT;
    private Class<? extends IRootChunkStructure> responseView;

    ChunkAddedInfo(boolean bl, boolean bl2, Class<? extends IRootChunkStructure> clazz) {
        this.DSP = bl;
        this.FFT = bl2;
        this.responseView = clazz;
    }

    void DSP(boolean bl) {
        this.DSP = bl;
    }

    void FFT(boolean bl) {
        this.FFT = bl;
    }

    void DSP(Class<? extends IRootChunkStructure> clazz) {
        this.responseView = clazz;
    }
}

