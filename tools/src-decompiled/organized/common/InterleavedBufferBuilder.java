/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.concurrent.ArrayBlockingQueue;
import sdfgjkljljoftrytrszgijpokjprs.SimpleByteBuffer;
import sdfgjkljljoftrytrszgijpokjprs.ThreadState;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.IChannelInputReader;
import sdfgjkljljoftrytrszgijpokjprs.EmptySimpleByteBuffer;
import sdfgjkljljoftrytrszgijpokjprs.AbstractManagedWorker;

public class InterleavedBufferBuilder
extends AbstractManagedWorker {
    private static final BitsPerSample AdditionalMetadataValue = BitsPerSample.AdditionalMetadataValue;
    private final int AudioFileExtension;
    private final int IAudioFileCodec;
    private final IChannelInputReader IAudioInputStream;
    private final int IAudioMetaInformation;
    private final byte[][] IBaseAudioCodec;
    private final int[] MetaInfomationCopy;
    private SimpleByteBuffer AacAudioCodec;
    private final ArrayBlockingQueue<SimpleByteBuffer> AacMetaDataModel;

    public InterleavedBufferBuilder(int n, int n2, int n3, IChannelInputReader rRnnNgUDxUFGFeaiYduOghS) {
        this.AudioFileExtension = n;
        this.IAudioFileCodec = n2;
        this.IAudioMetaInformation = n3;
        this.IAudioInputStream = rRnnNgUDxUFGFeaiYduOghS;
        this.AacMetaDataModel = new ArrayBlockingQueue(n2);
        this.IBaseAudioCodec = new byte[n][n3];
        this.MetaInfomationCopy = new int[n];
        this.DSP("Interleaved Buffer Builder");
    }

    public synchronized int DSP(byte[] byArray, int n, int n2) {
        int n3 = 0;
        if (this.AacAudioCodec instanceof EmptySimpleByteBuffer) {
            return -1;
        }
        int n4 = n;
        while (n4 < n2) {
            if (this.AacAudioCodec == null || !this.AacAudioCodec.DSP()) {
                try {
                    this.AacAudioCodec = this.AacMetaDataModel.take();
                }
                catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            if (this.AacAudioCodec == null && this.DSP() == ThreadState.responseView) continue;
            if (this.AacAudioCodec instanceof EmptySimpleByteBuffer || this.DSP() != ThreadState.responseView) break;
            int n5 = this.AacAudioCodec.FFT(byArray, n4, n2 - n4);
            n4 += n5;
            n3 += n5;
        }
        return n3;
    }

    private void DSP(SimpleByteBuffer abkRNvAOeWIbEeMzsGvXdIr) {
        try {
            this.AacMetaDataModel.put(abkRNvAOeWIbEeMzsGvXdIr);
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void FFT() {
        for (int i = 0; i < this.AudioFileExtension; ++i) {
            this.MetaInfomationCopy[i] = this.IAudioInputStream.DSP(i, this.IBaseAudioCodec[i]);
        }
        if (this.DSP(this.MetaInfomationCopy)) {
            this.DSP(new EmptySimpleByteBuffer());
            return;
        }
        SimpleByteBuffer abkRNvAOeWIbEeMzsGvXdIr = new SimpleByteBuffer(this.FFT(this.MetaInfomationCopy));
        for (int i = 0; i < this.MetaInfomationCopy[0]; i += AdditionalMetadataValue.FFT()) {
            for (int j = 0; j < this.AudioFileExtension; ++j) {
                abkRNvAOeWIbEeMzsGvXdIr.DSP(this.IBaseAudioCodec[j], i, AdditionalMetadataValue.FFT());
            }
        }
        this.DSP(abkRNvAOeWIbEeMzsGvXdIr);
    }

    private boolean DSP(int[] nArray) {
        for (int n : nArray) {
            if (n != -1) continue;
            return true;
        }
        return false;
    }

    private int FFT(int[] nArray) {
        int n = 0;
        for (int n2 : nArray) {
            n += n2;
        }
        return n;
    }
}

