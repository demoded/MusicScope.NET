/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.MetaInfomationCopy;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormatCode;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;

public class ComputationObjects {
    private static final Object DSP = new Object();
    private static final Object FFT = new Object();
    private static final Object responseView = new Object();
    private static ComputationObjects AdditionalMetadataValue = null;
    private IAudioMetaInformation AudioFileExtension = new MetaInfomationCopy(96000, BitsPerSample.AdditionalMetadataValue, 0, 0, AudioFormatCode.responseView, 0L, 0L, "No Track", 0, AudioExtension.responseView, 0L, 0L, null);
    private IAudioMetaInformation IAudioFileCodec;
    private AudioSampleModel IAudioInputStream = null;

    private ComputationObjects() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ComputationObjects DSP() {
        if (AdditionalMetadataValue != null) return AdditionalMetadataValue;
        Class<ComputationObjects> clazz = ComputationObjects.class;
        synchronized (ComputationObjects.class) {
            if (AdditionalMetadataValue != null) return AdditionalMetadataValue;
            AdditionalMetadataValue = new ComputationObjects();
            // ** MonitorExit[var0] (shouldn't be in output)
            return AdditionalMetadataValue;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public IAudioMetaInformation FFT() {
        MetaInfomationCopy nuCoRkdmXmpraThwzNAiTcw = null;
        Object object = FFT;
        synchronized (object) {
            if (this.AudioFileExtension != null) {
                nuCoRkdmXmpraThwzNAiTcw = new MetaInfomationCopy(this.AudioFileExtension);
            }
        }
        return nuCoRkdmXmpraThwzNAiTcw;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA) {
        Object object = FFT;
        synchronized (object) {
            this.AudioFileExtension = yGjBevanihqaxYKnUNtrNeA;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void FFT(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA) {
        Object object = responseView;
        synchronized (object) {
            this.IAudioFileCodec = yGjBevanihqaxYKnUNtrNeA;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public AudioSampleModel responseView() {
        Object object = DSP;
        synchronized (object) {
            return new AudioSampleModel(this.IAudioInputStream.DSP(), this.IAudioInputStream.responseView(), this.IAudioInputStream.FFT());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void DSP(AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf) {
        Object object = DSP;
        synchronized (object) {
            this.IAudioInputStream = zvOTVUaKFTNpNSbbMYZfoXf;
        }
    }
}

