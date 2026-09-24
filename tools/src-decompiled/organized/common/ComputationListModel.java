/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.ComputationSubject;
import sdfgjkljljoftrytrszgijpokjprs.IComputationModule;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;

public class ComputationListModel {
    private final ComputationController.DSP DSP;
    private final IComputationModule FFT;

    public ComputationListModel(IComputationModule eYLjCFiTTOgnPWQPYoTfNzn) {
        this.FFT = eYLjCFiTTOgnPWQPYoTfNzn;
        Class<?> clazz = eYLjCFiTTOgnPWQPYoTfNzn.getClass();
        ComputationSubject brqrCQbbknwtebNWiMmRwch = clazz.getAnnotation(ComputationSubject.class);
        this.DSP = brqrCQbbknwtebNWiMmRwch != null ? brqrCQbbknwtebNWiMmRwch.DSP() : null;
    }

    public ComputationController.DSP DSP() {
        return this.DSP;
    }

    public IComputationModule FFT() {
        return this.FFT;
    }

    public boolean equals(Object object) {
        if (object instanceof IComputationModule) {
            return this.FFT.equals(object);
        }
        if (object instanceof ComputationListModel) {
            return this.FFT.equals(((ComputationListModel)object).FFT);
        }
        return false;
    }
}

