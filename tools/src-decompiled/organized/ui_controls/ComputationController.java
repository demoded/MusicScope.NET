/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import sdfgjkljljoftrytrszgijpokjprs.IComputationModule;
import sdfgjkljljoftrytrszgijpokjprs.ISystemSettings;
import sdfgjkljljoftrytrszgijpokjprs.ComputationListModel;
import sdfgjkljljoftrytrszgijpokjprs.ComputationObjects;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;
import sdfgjkljljoftrytrszgijpokjprs.IComputationListener;
import sdfgjkljljoftrytrszgijpokjprs.ComputationFutureModel;

public class ComputationController {
    private final ComputationObjects DSP;
    private final ArrayList<ComputationListModel> FFT;
    private final HashMap<DSP, ArrayList<IComputationListener>> responseView;
    private final ArrayList<ComputationFutureModel> AdditionalMetadataValue = new ArrayList(0);
    private final ExecutorService AudioFileExtension;
    private ISystemSettings IAudioFileCodec;

    public ComputationController() {
        this.DSP = ComputationObjects.DSP();
        this.FFT = new ArrayList(0);
        this.AudioFileExtension = Executors.newCachedThreadPool();
        this.responseView = new HashMap(0);
        for (DSP rHAjVyBgPhqkQKsOvJMPMYn2 : sdfgjkljljoftrytrszgijpokjprs.ComputationController$rHAjVyBgPhqkQKsOvJMPMYn.values()) {
            this.responseView.put(rHAjVyBgPhqkQKsOvJMPMYn2, new ArrayList(0));
        }
    }

    public synchronized void DSP(AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf) {
        this.DSP.DSP(zvOTVUaKFTNpNSbbMYZfoXf);
    }

    public synchronized void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA) {
        this.DSP.DSP(yGjBevanihqaxYKnUNtrNeA);
    }

    public synchronized void FFT(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA) {
        this.DSP.FFT(yGjBevanihqaxYKnUNtrNeA);
    }

    public double DSP() {
        return this.IAudioFileCodec.AudioFileExtension();
    }

    public void DSP(IComputationModule<?> eYLjCFiTTOgnPWQPYoTfNzn) {
        this.FFT.add(new ComputationListModel(eYLjCFiTTOgnPWQPYoTfNzn));
    }

    public void DSP(IComputationListener<?> ekxmGzMKSyHUczkNwMvschV2, DSP ... rHAjVyBgPhqkQKsOvJMPMYnArray) {
        for (DSP rHAjVyBgPhqkQKsOvJMPMYn2 : rHAjVyBgPhqkQKsOvJMPMYnArray) {
            ArrayList<IComputationListener> arrayList = this.DSP(rHAjVyBgPhqkQKsOvJMPMYn2);
            if (arrayList.contains(ekxmGzMKSyHUczkNwMvschV2)) continue;
            arrayList.add(ekxmGzMKSyHUczkNwMvschV2);
        }
    }

    public synchronized void FFT() {
        this.AdditionalMetadataValue.clear();
        for (ComputationListModel object : this.FFT) {
            IComputationModule eYLjCFiTTOgnPWQPYoTfNzn = object.FFT();
            eYLjCFiTTOgnPWQPYoTfNzn.DSP(this.IAudioFileCodec.AdditionalMetadataValue());
            Future future = this.AudioFileExtension.submit(eYLjCFiTTOgnPWQPYoTfNzn);
            this.AdditionalMetadataValue.add(new ComputationFutureModel(future, object.DSP()));
        }
        while (this.DSP(this.AdditionalMetadataValue)) {
            for (ComputationFutureModel oiyflxXGGmIcWtyMJWwDpJe2 : this.AdditionalMetadataValue) {
                if (!oiyflxXGGmIcWtyMJWwDpJe2.responseView() || !oiyflxXGGmIcWtyMJWwDpJe2.DSP().isDone()) continue;
                try {
                    this.DSP(oiyflxXGGmIcWtyMJWwDpJe2.FFT(), oiyflxXGGmIcWtyMJWwDpJe2.DSP().get());
                    oiyflxXGGmIcWtyMJWwDpJe2.AdditionalMetadataValue();
                }
                catch (InterruptedException | ExecutionException exception) {
                    oiyflxXGGmIcWtyMJWwDpJe2.AdditionalMetadataValue();
                }
            }
        }
    }

    private boolean DSP(ArrayList<ComputationFutureModel> arrayList) {
        for (ComputationFutureModel oiyflxXGGmIcWtyMJWwDpJe2 : arrayList) {
            if (oiyflxXGGmIcWtyMJWwDpJe2 == null || !oiyflxXGGmIcWtyMJWwDpJe2.responseView()) continue;
            return true;
        }
        return false;
    }

    private synchronized <V> void DSP(DSP rHAjVyBgPhqkQKsOvJMPMYn2, V v) {
        if (v != null) {
            ArrayList<IComputationListener> arrayList = this.DSP(rHAjVyBgPhqkQKsOvJMPMYn2);
            for (IComputationListener ekxmGzMKSyHUczkNwMvschV2 : arrayList) {
                ekxmGzMKSyHUczkNwMvschV2.DSP(v, rHAjVyBgPhqkQKsOvJMPMYn2);
            }
        }
    }

    private ArrayList<IComputationListener> DSP(DSP rHAjVyBgPhqkQKsOvJMPMYn2) {
        return this.responseView.get((Object)rHAjVyBgPhqkQKsOvJMPMYn2);
    }

    public void DSP(ISystemSettings oVwxYDEnxDAUeujkyADMYmG) {
        this.IAudioFileCodec = oVwxYDEnxDAUeujkyADMYmG;
    }

    public static enum DSP {
        DSP,
        FFT,
        responseView,
        AdditionalMetadataValue,
        AudioFileExtension,
        IAudioFileCodec,
        IAudioInputStream;

    }
}

