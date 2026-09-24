/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.UploadResponseModel;

public class UploadException
extends Exception {
    private UploadResponseModel DSP;

    public UploadException(String string) {
        this(string, (UploadResponseModel)null);
    }

    public UploadException(String string, UploadResponseModel dXwHeIuXrPXwwYsOazDjvwg2) {
        super(string);
        this.DSP = dXwHeIuXrPXwwYsOazDjvwg2;
    }
}

