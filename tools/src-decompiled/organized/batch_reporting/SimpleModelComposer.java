/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.File;
import sdfgjkljljoftrytrszgijpokjprs.IMetaInformationListener;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.ITrackLoadedListener;

public class SimpleModelComposer
implements IMetaInformationListener,
ITrackLoadedListener {
    private IAudioMetaInformation DSP;
    private File FFT;

    @Override
    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA2) {
        this.DSP = yGjBevanihqaxYKnUNtrNeA2;
    }

    @Override
    public void DSP(String string) {
        this.FFT = new File(string);
    }
}

