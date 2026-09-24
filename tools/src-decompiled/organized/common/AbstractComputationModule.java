/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.IComputationModule;
import sdfgjkljljoftrytrszgijpokjprs.ComputationObjects;
import sdfgjkljljoftrytrszgijpokjprs.OperatingSystem;

public abstract class AbstractComputationModule<V>
implements IComputationModule<V> {
    private int DSP = 10;

    @Override
    public final V call() throws Exception {
        switch (OperatingSystem.DSP()) {
            case DSP: {
                Thread.currentThread().setPriority(this.FFT(this.DSP));
                break;
            }
            case FFT: {
                Thread.currentThread().setPriority(this.FFT(this.DSP - 1));
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
        return this.FFT();
    }

    public ComputationObjects DSP() {
        return ComputationObjects.DSP();
    }

    @Override
    public void DSP(int n) {
        this.DSP = n;
    }

    private int FFT(int n) {
        int n2 = n;
        n2 = n2 > 10 ? 10 : n2;
        n2 = n2 < 1 ? 1 : n2;
        return n2;
    }
}

