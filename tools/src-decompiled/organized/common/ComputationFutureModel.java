/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.concurrent.Future;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;

public class ComputationFutureModel {
    private boolean DSP = true;
    private Future FFT;
    private ComputationController.DSP responseView;

    public ComputationFutureModel(Future future, ComputationController.DSP rHAjVyBgPhqkQKsOvJMPMYn2) {
        this.FFT = future;
        this.responseView = rHAjVyBgPhqkQKsOvJMPMYn2;
    }

    public Future DSP() {
        return this.FFT;
    }

    public ComputationController.DSP FFT() {
        return this.responseView;
    }

    public boolean responseView() {
        return this.DSP;
    }

    public void AdditionalMetadataValue() {
        this.DSP = false;
    }
}

