/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdfgjkljljoftrytrszgijpokjprs.AudioOutput;
import sdfgjkljljoftrytrszgijpokjprs.IMetaInformationListener;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModelFuture;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;
import sdfgjkljljoftrytrszgijpokjprs.IAnalyzerStrategy;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;
import sdfgjkljljoftrytrszgijpokjprs.IWritableAudioPlugin;

public class RealtimeAnalyzer
implements IMetaInformationListener,
IAnalyzerStrategy {
    private final Object DSP = new Object();
    private final ComputationController FFT;
    private final ArrayList<IWritableAudioPlugin<Future<AudioSampleModel>>> responseView;
    private final LinkedBlockingQueue<Future<AudioSampleModel>> AdditionalMetadataValue;
    private final Thread AudioFileExtension;
    private final ExecutorService IAudioFileCodec = Executors.newCachedThreadPool();

    public RealtimeAnalyzer(ComputationController cJizbPrgAISXyzXrgujHPeB2, AudioOutput ksHoYvJaofzRsWbjpDlDnPw, RealtimeAnalyzer aWYfUFxCCDbNJVWMPiBUKrO2) {
        this.FFT = cJizbPrgAISXyzXrgujHPeB2;
        this.responseView = new ArrayList(0);
        this.AdditionalMetadataValue = new LinkedBlockingQueue(1);
        if (aWYfUFxCCDbNJVWMPiBUKrO2 != null) {
            for (IWritableAudioPlugin<Future<AudioSampleModel>> lKkynTEEZVTbuSJCgDWFhnQ2 : aWYfUFxCCDbNJVWMPiBUKrO2.DSP()) {
                this.DSP(lKkynTEEZVTbuSJCgDWFhnQ2);
            }
        }
        this.AudioFileExtension = new Thread(new DSP(ksHoYvJaofzRsWbjpDlDnPw, this.AdditionalMetadataValue));
        this.FFT();
    }

    private void FFT() {
        this.AudioFileExtension.start();
    }

    @Override
    public boolean DSP(AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf) {
        FFT qPeIwmpzLZIktKLXAJOQHcO = new FFT(zvOTVUaKFTNpNSbbMYZfoXf);
        Future<?> future = this.IAudioFileCodec.submit(qPeIwmpzLZIktKLXAJOQHcO);
        try {
            future.get(200L, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException exception) {
            return false;
        }
        return qPeIwmpzLZIktKLXAJOQHcO.DSP();
    }

    @Override
    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA2) {
        this.FFT.DSP(yGjBevanihqaxYKnUNtrNeA);
        this.FFT.FFT(yGjBevanihqaxYKnUNtrNeA2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean DSP(IWritableAudioPlugin<Future<AudioSampleModel>> lKkynTEEZVTbuSJCgDWFhnQ2) {
        if (lKkynTEEZVTbuSJCgDWFhnQ2 != null && !this.responseView.contains(lKkynTEEZVTbuSJCgDWFhnQ2)) {
            Object object = this.DSP;
            synchronized (object) {
                return this.responseView.add(lKkynTEEZVTbuSJCgDWFhnQ2);
            }
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean FFT(IWritableAudioPlugin<Future<AudioSampleModel>> lKkynTEEZVTbuSJCgDWFhnQ2) {
        boolean bl;
        Object object = this.DSP;
        synchronized (object) {
            bl = this.responseView.remove(lKkynTEEZVTbuSJCgDWFhnQ2);
            if (bl) {
                this.AdditionalMetadataValue.clear();
            }
        }
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Future<AudioSampleModel> FFT(AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf) {
        Object object = new AudioSampleModelFuture(zvOTVUaKFTNpNSbbMYZfoXf);
        Object object2 = this.DSP;
        synchronized (object2) {
            for (IWritableAudioPlugin<Future<AudioSampleModel>> lKkynTEEZVTbuSJCgDWFhnQ2 : this.responseView) {
                if (lKkynTEEZVTbuSJCgDWFhnQ2 == null) continue;
                try {
                    object = lKkynTEEZVTbuSJCgDWFhnQ2.FFT((AudioSampleModel)object.get());
                }
                catch (InterruptedException | ExecutionException exception) {
                    Logger.getLogger(RealtimeAnalyzer.class.getName()).log(Level.SEVERE, null, exception);
                    return new AudioSampleModelFuture(zvOTVUaKFTNpNSbbMYZfoXf);
                }
            }
        }
        return object;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<IWritableAudioPlugin<Future<AudioSampleModel>>> DSP() {
        Object object = this.DSP;
        synchronized (object) {
            return Collections.unmodifiableList(this.responseView);
        }
    }

    private class FFT
    implements Runnable {
        private final AudioSampleModel FFT;
        private boolean responseView = true;

        FFT(AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf) {
            this.FFT = zvOTVUaKFTNpNSbbMYZfoXf;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            Thread.currentThread().setName("WriteSampleWorker");
            RealtimeAnalyzer.this.FFT.DSP(this.FFT);
            RealtimeAnalyzer.this.FFT.FFT();
            Object object = RealtimeAnalyzer.this.DSP;
            synchronized (object) {
                Future<AudioSampleModel> future = RealtimeAnalyzer.this.FFT(this.FFT);
                try {
                    RealtimeAnalyzer.this.AdditionalMetadataValue.put(future);
                }
                catch (InterruptedException interruptedException) {
                    Logger.getLogger(RealtimeAnalyzer.class.getName()).log(Level.SEVERE, null, interruptedException);
                    this.responseView = false;
                }
            }
        }

        public boolean DSP() {
            return this.responseView;
        }
    }

    private class DSP
    implements Runnable {
        private final AudioOutput FFT;
        private final LinkedBlockingQueue<Future<AudioSampleModel>> responseView;

        private DSP(AudioOutput ksHoYvJaofzRsWbjpDlDnPw, LinkedBlockingQueue<Future<AudioSampleModel>> linkedBlockingQueue) {
            this.FFT = ksHoYvJaofzRsWbjpDlDnPw;
            this.responseView = linkedBlockingQueue;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Future<AudioSampleModel> future = this.responseView.take();
                    this.FFT.DSP(future.get());
                }
                catch (InterruptedException | ExecutionException exception) {
                    Logger.getLogger(RealtimeAnalyzer.class.getName()).log(Level.SEVERE, null, exception);
                }
            }
        }
    }
}

