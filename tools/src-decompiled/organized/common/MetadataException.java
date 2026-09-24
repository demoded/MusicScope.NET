/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.ArrayList;

public class MetadataException
extends Exception {
    private ArrayList<String> DSP = new ArrayList(0);

    public MetadataException(String string) {
        super(string);
    }

    public void DSP(String string) {
        this.DSP.add(string);
    }

    public ArrayList<String> DSP() {
        return this.DSP;
    }
}

