/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import sdfgjkljljoftrytrszgijpokjprs.IComputationModule;
import sdfgjkljljoftrytrszgijpokjprs.AbstractComputationModule;
import sdfgjkljljoftrytrszgijpokjprs.ComputationListModel;
import sdfgjkljljoftrytrszgijpokjprs.ComposedModel;
import sdfgjkljljoftrytrszgijpokjprs.ComputationFutureModel;

public class ComposedModule
extends AbstractComputationModule<ComposedModel> {
    private ArrayList<ComputationListModel> DSP;
    private ArrayList<ComputationFutureModel> FFT;
    private ExecutorService responseView = Executors.newCachedThreadPool();

    public ComposedModule(IComputationModule ... eYLjCFiTTOgnPWQPYoTfNznArray) {
        this.DSP = new ArrayList();
        this.FFT = new ArrayList();
        for (IComputationModule eYLjCFiTTOgnPWQPYoTfNzn : eYLjCFiTTOgnPWQPYoTfNznArray) {
            this.DSP.add(new ComputationListModel(eYLjCFiTTOgnPWQPYoTfNzn));
        }
    }

    public ComposedModel responseView() throws Exception {
        ComposedModel dbWUuIVxtlDbLbZVylyCBng2 = new ComposedModel();
        this.FFT.clear();
        for (ComputationListModel object : this.DSP) {
            Future future = this.responseView.submit(object.FFT());
            this.FFT.add(new ComputationFutureModel(future, object.DSP()));
        }
        while (this.DSP(this.FFT)) {
            for (ComputationFutureModel oiyflxXGGmIcWtyMJWwDpJe2 : this.FFT) {
                if (!oiyflxXGGmIcWtyMJWwDpJe2.responseView() || !oiyflxXGGmIcWtyMJWwDpJe2.DSP().isDone()) continue;
                try {
                    dbWUuIVxtlDbLbZVylyCBng2.DSP(oiyflxXGGmIcWtyMJWwDpJe2.FFT(), oiyflxXGGmIcWtyMJWwDpJe2.DSP().get());
                    oiyflxXGGmIcWtyMJWwDpJe2.AdditionalMetadataValue();
                }
                catch (InterruptedException | ExecutionException exception) {
                    oiyflxXGGmIcWtyMJWwDpJe2.AdditionalMetadataValue();
                }
            }
        }
        return dbWUuIVxtlDbLbZVylyCBng2;
    }

    private boolean DSP(ArrayList<ComputationFutureModel> arrayList) {
        for (ComputationFutureModel oiyflxXGGmIcWtyMJWwDpJe2 : arrayList) {
            if (!oiyflxXGGmIcWtyMJWwDpJe2.responseView()) continue;
            return true;
        }
        return false;
    }

    @Override
    public /* synthetic */ Object FFT() throws Exception {
        return this.responseView();
    }
}

