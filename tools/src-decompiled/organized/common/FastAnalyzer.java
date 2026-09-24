/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.ArrayList;
import java.util.concurrent.Future;
import sdfgjkljljoftrytrszgijpokjprs.IMetaInformationListener;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;
import sdfgjkljljoftrytrszgijpokjprs.IAnalyzerStrategy;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;
import sdfgjkljljoftrytrszgijpokjprs.IWritableAudioPlugin;

public class FastAnalyzer
implements IMetaInformationListener,
IAnalyzerStrategy {
    private final ComputationController DSP;
    private final ArrayList<IWritableAudioPlugin<Future<AudioSampleModel>>> FFT;

    public FastAnalyzer(ComputationController cJizbPrgAISXyzXrgujHPeB2) {
        this.DSP = cJizbPrgAISXyzXrgujHPeB2;
        this.FFT = new ArrayList(0);
    }

    @Override
    public boolean DSP(AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf) {
        this.DSP.DSP(zvOTVUaKFTNpNSbbMYZfoXf);
        this.DSP.FFT();
        if (this.DSP.DSP() > 0.0) {
            try {
                Thread.sleep((long)Math.floor(this.DSP.DSP()), (int)(this.DSP.DSP() % 1.0) * 1000000);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        return true;
    }

    @Override
    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA2) {
        this.DSP.DSP(yGjBevanihqaxYKnUNtrNeA);
        this.DSP.FFT(yGjBevanihqaxYKnUNtrNeA2);
    }

    public synchronized boolean DSP(IWritableAudioPlugin<Future<AudioSampleModel>> lKkynTEEZVTbuSJCgDWFhnQ2) {
        if (lKkynTEEZVTbuSJCgDWFhnQ2 != null && !this.FFT.contains(lKkynTEEZVTbuSJCgDWFhnQ2)) {
            return this.FFT.add(lKkynTEEZVTbuSJCgDWFhnQ2);
        }
        return false;
    }

    public synchronized boolean FFT(IWritableAudioPlugin<Future<AudioSampleModel>> lKkynTEEZVTbuSJCgDWFhnQ2) {
        return this.FFT.remove(lKkynTEEZVTbuSJCgDWFhnQ2);
    }
}

