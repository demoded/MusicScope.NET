/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.concurrent.Future;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;
import sdfgjkljljoftrytrszgijpokjprs.IWritableAudioPlugin;

public interface IAudioRouteChangeListner {
    public void DSP(IWritableAudioPlugin<Future<AudioSampleModel>> var1);

    public void FFT(IWritableAudioPlugin<Future<AudioSampleModel>> var1);
}

