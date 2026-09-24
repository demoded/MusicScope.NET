/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.AbstractFuture
 */
package sdfgjkljljoftrytrszgijpokjprs;

import com.google.common.util.concurrent.AbstractFuture;
import java.util.concurrent.ExecutionException;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;

public class AudioSampleModelFuture
extends AbstractFuture<AudioSampleModel> {
    private final AudioSampleModel DSP;

    AudioSampleModelFuture(AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf) {
        this.DSP = zvOTVUaKFTNpNSbbMYZfoXf;
    }

    public AudioSampleModel DSP() throws InterruptedException, ExecutionException {
        return this.DSP;
    }

    public /* synthetic */ Object get() throws InterruptedException, ExecutionException {
        return this.DSP();
    }
}

