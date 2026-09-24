/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

public class OutOfChunkRangeException
extends Exception {
    private static final long serialVersionUID = 1L;
    private final long DSP;
    private final long FFT;
    private final long responseView;

    public OutOfChunkRangeException(long l, long l2, long l3) {
        super(String.format("Out of chunk range exception. [offset = %d | size = %d | outOfRange = %d]", l, l2, l3));
        this.DSP = l;
        this.FFT = l2;
        this.responseView = l3;
    }
}

