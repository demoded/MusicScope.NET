/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.IRootChunkStructure;

public class RootChunkException
extends Exception {
    private static final long serialVersionUID = 1L;
    private final Class<? extends IRootChunkStructure> DSP;

    public RootChunkException(Class<? extends IRootChunkStructure> clazz, String string) {
        super(string);
        this.DSP = clazz;
    }
}

