/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.ThreadState;

public abstract class AbstractManagedWorker
implements Runnable {
    protected final Object DSP = new Object();
    protected ThreadState FFT = ThreadState.DSP;
    protected String responseView;
    private int AdditionalMetadataValue = 5;

    public ThreadState DSP() {
        return this.FFT;
    }

    public void DSP(String string) {
        this.responseView = string;
    }

    public void DSP(int n) {
        this.AdditionalMetadataValue = n;
    }

    public abstract void FFT();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        Thread.currentThread().setName(this.responseView);
        Thread.currentThread().setPriority(this.AdditionalMetadataValue);
        this.FFT = ThreadState.responseView;
        while (this.FFT == ThreadState.responseView && !Thread.currentThread().isInterrupted()) {
            this.FFT();
        }
        this.FFT = ThreadState.DSP;
        Object object = this.DSP;
        synchronized (object) {
            this.DSP.notify();
        }
    }
}

