/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.File;
import sdfgjkljljoftrytrszgijpokjprs.ILoadableCallback;
import sdfgjkljljoftrytrszgijpokjprs.ICommitableStorage;
import sdfgjkljljoftrytrszgijpokjprs.ILoadingDialog;

public abstract class BaseFileWorker<T>
implements Runnable {
    protected File DSP;
    protected int FFT;
    protected boolean responseView;
    protected ICommitableStorage<T> AdditionalMetadataValue;
    protected ILoadingDialog AudioFileExtension;
    protected ILoadableCallback<T> IAudioFileCodec;

    public BaseFileWorker(File file, int n, ICommitableStorage<T> rvmLMdDLEGHIAejiawKxuPa, ILoadableCallback<T> cbBsidOPrITWbOsdPkAmBWy, ILoadingDialog uqIWpQkdtRoGxqvvNunoXtF2) {
        this.DSP = file;
        this.AdditionalMetadataValue = rvmLMdDLEGHIAejiawKxuPa;
        this.FFT = n;
        this.AudioFileExtension = uqIWpQkdtRoGxqvvNunoXtF2;
        this.IAudioFileCodec = cbBsidOPrITWbOsdPkAmBWy;
        this.responseView = true;
    }

    public void DSP(boolean bl) {
        this.responseView = bl;
    }
}

