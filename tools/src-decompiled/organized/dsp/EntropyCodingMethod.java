/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.EntropyPartitionedRiceContents;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;
import sdfgjkljljoftrytrszgijpokjprs.Header;

public abstract class EntropyCodingMethod {
    protected int DSP;
    protected EntropyPartitionedRiceContents FFT;

    abstract void DSP(BitInputStream var1, int var2, int var3, Header var4, int[] var5) throws IOException;
}

