/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 */
package sdfgjkljljoftrytrszgijpokjprs;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import sdfgjkljljoftrytrszgijpokjprs.MessageModel;

public class UploadResponseModel {
    @SerializedName(value="error")
    private boolean DSP;
    @SerializedName(value="messages")
    private MessageModel[] FFT;

    public boolean DSP() {
        return this.DSP;
    }

    public MessageModel[] FFT() {
        return this.FFT;
    }

    public List<Integer> responseView() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>(0);
        for (MessageModel zVnipNutzPTkUTYOtYDkVhZ : this.FFT) {
            arrayList.add(zVnipNutzPTkUTYOtYDkVhZ.DSP());
        }
        return arrayList;
    }
}

