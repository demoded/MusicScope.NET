/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import sdfgjkljljoftrytrszgijpokjprs.SimpleByteBuffer;
import sdfgjkljljoftrytrszgijpokjprs.ThreadState;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.IChannelInputReader;
import sdfgjkljljoftrytrszgijpokjprs.EmptySimpleByteBuffer;
import sdfgjkljljoftrytrszgijpokjprs.AbstractManagedWorker;
import sdfgjkljljoftrytrszgijpokjprs.ChannelDecoder;
import sdfgjkljljoftrytrszgijpokjprs.IReadableChannelBuffer;

public class StreamDecoder
implements IReadableChannelBuffer {
    private final int DSP;
    private final int FFT;
    private final int responseView;
    private final int AdditionalMetadataValue;
    private final int AudioFileExtension;
    private final BitsPerSample IAudioFileCodec;
    private final IChannelInputReader IAudioInputStream;
    private final HashMap<Integer, SimpleByteBuffer> IAudioMetaInformation;
    private final HashMap<Integer, ChannelDecoder> IBaseAudioCodec;
    private final HashMap<Integer, DSP> MetaInfomationCopy;
    private final HashMap<Integer, ArrayBlockingQueue<SimpleByteBuffer>> AacAudioCodec;
    private final ExecutorService AacMetaDataModel;

    public StreamDecoder(ExecutorService executorService, int n, int n2, int n3, int n4, int n5, BitsPerSample kFVWmcqOBgYFxPgeswapvPe, boolean bl, IChannelInputReader rRnnNgUDxUFGFeaiYduOghS) {
        int n6;
        this.DSP = n;
        this.IAudioFileCodec = kFVWmcqOBgYFxPgeswapvPe;
        this.IAudioInputStream = rRnnNgUDxUFGFeaiYduOghS;
        this.AdditionalMetadataValue = n4;
        this.AudioFileExtension = n5;
        this.FFT = n2;
        this.responseView = n3;
        this.AacMetaDataModel = executorService;
        this.IAudioMetaInformation = new HashMap(n);
        this.IBaseAudioCodec = new HashMap(n);
        this.MetaInfomationCopy = new HashMap(n);
        this.AacAudioCodec = new HashMap(n);
        for (n6 = 0; n6 < n; ++n6) {
            this.IBaseAudioCodec.put(n6, new ChannelDecoder(n4, n5, kFVWmcqOBgYFxPgeswapvPe, n2, bl));
            this.AacAudioCodec.put(n6, new ArrayBlockingQueue(n3));
        }
        if (executorService != null) {
            for (n6 = 0; n6 < n; ++n6) {
                DSP rHAjVyBgPhqkQKsOvJMPMYn2 = new DSP(n6, n2, this.IBaseAudioCodec.get(n6), rRnnNgUDxUFGFeaiYduOghS);
                rHAjVyBgPhqkQKsOvJMPMYn2.DSP(10);
                this.MetaInfomationCopy.put(n6, rHAjVyBgPhqkQKsOvJMPMYn2);
                executorService.submit(rHAjVyBgPhqkQKsOvJMPMYn2);
            }
        }
    }

    @Override
    public int DSP(int n, byte[] byArray, int n2, int n3) {
        int n4 = 0;
        DSP rHAjVyBgPhqkQKsOvJMPMYn2 = this.MetaInfomationCopy.get(n);
        if (this.AacAudioCodec.get(n).peek() instanceof EmptySimpleByteBuffer) {
            return -1;
        }
        int n5 = n2;
        while (n5 < n3) {
            SimpleByteBuffer abkRNvAOeWIbEeMzsGvXdIr;
            if (this.IAudioMetaInformation.get(n) == null || !this.IAudioMetaInformation.get(n).DSP()) {
                try {
                    this.IAudioMetaInformation.put(n, this.AacAudioCodec.get(n).take());
                }
                catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            if ((abkRNvAOeWIbEeMzsGvXdIr = this.IAudioMetaInformation.get(n)) == null && rHAjVyBgPhqkQKsOvJMPMYn2.DSP() == ThreadState.responseView) continue;
            if (abkRNvAOeWIbEeMzsGvXdIr instanceof EmptySimpleByteBuffer || rHAjVyBgPhqkQKsOvJMPMYn2.DSP() != ThreadState.responseView) break;
            int n6 = abkRNvAOeWIbEeMzsGvXdIr.FFT(byArray, n5, n3 - n5);
            n5 += n6;
            n4 += n6;
        }
        return n4;
    }

    private void DSP(int n, SimpleByteBuffer abkRNvAOeWIbEeMzsGvXdIr) {
        try {
            this.AacAudioCodec.get(n).put(abkRNvAOeWIbEeMzsGvXdIr);
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    private class DSP
    extends AbstractManagedWorker {
        private final int AudioFileExtension;
        private final int IAudioFileCodec;
        private final ChannelDecoder IAudioInputStream;
        private final IChannelInputReader IAudioMetaInformation;
        private byte[] IBaseAudioCodec;
        private byte[] MetaInfomationCopy;

        DSP(int n, int n2, ChannelDecoder izYvnpJmGiMoepycLkTtHXY2, IChannelInputReader rRnnNgUDxUFGFeaiYduOghS) {
            this.AudioFileExtension = n;
            this.IAudioFileCodec = n2;
            this.IAudioInputStream = izYvnpJmGiMoepycLkTtHXY2;
            this.IAudioMetaInformation = rRnnNgUDxUFGFeaiYduOghS;
            this.IBaseAudioCodec = new byte[n2];
            this.DSP("Stream Decoder - Channel " + n);
        }

        @Override
        public synchronized void FFT() {
            int n = this.IAudioMetaInformation.DSP(this.AudioFileExtension, this.IBaseAudioCodec);
            if (n > 0) {
                byte[] byArray = new byte[n];
                System.arraycopy(this.IBaseAudioCodec, 0, byArray, 0, n);
                this.MetaInfomationCopy = this.IAudioInputStream.DSP(byArray);
                SimpleByteBuffer abkRNvAOeWIbEeMzsGvXdIr = new SimpleByteBuffer(this.MetaInfomationCopy.length);
                abkRNvAOeWIbEeMzsGvXdIr.DSP(this.MetaInfomationCopy, 0, this.MetaInfomationCopy.length);
                StreamDecoder.this.DSP(this.AudioFileExtension, abkRNvAOeWIbEeMzsGvXdIr);
            } else if (n == -1) {
                StreamDecoder.this.DSP(this.AudioFileExtension, new EmptySimpleByteBuffer());
            }
        }
    }
}

