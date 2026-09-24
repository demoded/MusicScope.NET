/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.Mixer;
import sdfgjkljljoftrytrszgijpokjprs.AudioOutput;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;

public class NoAudioOutput
extends AudioOutput {
    private final Object DSP = new Object();
    private int FFT = 0;
    private int responseView;
    private int AdditionalMetadataValue;
    private Timer AudioFileExtension = new Timer("NoAudioOutput");

    public NoAudioOutput() {
        this(null);
    }

    private NoAudioOutput(Mixer.Info info) {
        super(info);
        this.AudioFileExtension.scheduleAtFixedRate(new TimerTask(){

            @Override
            public void run() {
                NoAudioOutput.this.responseView(NoAudioOutput.this.AdditionalMetadataValue);
            }
        }, 10L, 10L);
    }

    @Override
    public void DSP(AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf) {
        if (zvOTVUaKFTNpNSbbMYZfoXf != null) {
            this.FFT(zvOTVUaKFTNpNSbbMYZfoXf.AudioFileExtension());
        }
    }

    @Override
    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA2) {
        this.responseView = yGjBevanihqaxYKnUNtrNeA.responseView() / 10;
        this.AdditionalMetadataValue = yGjBevanihqaxYKnUNtrNeA.responseView() / 100;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void FFT(int n) {
        Object object = this.DSP;
        synchronized (object) {
            while (this.FFT + n > this.responseView) {
                try {
                    this.DSP.wait();
                }
                catch (InterruptedException interruptedException) {}
            }
            this.FFT += n;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void responseView(int n) {
        Object object = this.DSP;
        synchronized (object) {
            this.FFT -= n;
            this.FFT = this.FFT < 0 ? 0 : this.FFT;
            this.DSP.notifyAll();
        }
    }
}

