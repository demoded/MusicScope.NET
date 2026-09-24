/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;
import sdfgjkljljoftrytrszgijpokjprs.IWritableAudioPlugin;

public class PackageSplitterBuffer
implements IWritableAudioPlugin<Future<AudioSampleModel>> {
    private final int DSP;
    private final IWritableAudioPlugin<AudioSampleModel> FFT;
    private final ExecutorService responseView = Executors.newSingleThreadExecutor();
    private final LinkedBlockingQueue<AudioSampleModel> AdditionalMetadataValue;
    private final LinkedBlockingQueue<AudioSampleModel> AudioFileExtension;
    private Thread IAudioFileCodec;
    private AudioSampleModel IAudioInputStream;
    private double[] IAudioMetaInformation;
    private double[] IBaseAudioCodec;
    private int MetaInfomationCopy;

    public PackageSplitterBuffer(IWritableAudioPlugin<AudioSampleModel> lKkynTEEZVTbuSJCgDWFhnQ2, int n) {
        this.FFT = lKkynTEEZVTbuSJCgDWFhnQ2;
        this.DSP = n;
        this.AdditionalMetadataValue = new LinkedBlockingQueue(10);
        this.AudioFileExtension = new LinkedBlockingQueue(100);
    }

    public Future<AudioSampleModel> DSP(AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf) {
        this.AdditionalMetadataValue.offer(zvOTVUaKFTNpNSbbMYZfoXf);
        if (this.IAudioFileCodec == null || !this.IAudioFileCodec.isAlive()) {
            this.IAudioFileCodec = new Thread(new FFT(this.AdditionalMetadataValue, this.AudioFileExtension, this.FFT));
            this.IAudioFileCodec.start();
        }
        Future<AudioSampleModel> future = this.responseView.submit(new DSP(zvOTVUaKFTNpNSbbMYZfoXf.AdditionalMetadataValue()));
        return future;
    }

    public synchronized AudioSampleModel DSP(int n) {
        int n2 = 0;
        this.IAudioMetaInformation = new double[n];
        this.IBaseAudioCodec = new double[n];
        while (n2 < n) {
            if (this.IAudioInputStream == null) {
                try {
                    this.MetaInfomationCopy = 0;
                    this.IAudioInputStream = this.AudioFileExtension.take();
                }
                catch (InterruptedException interruptedException) {
                    break;
                }
            }
            int n3 = Math.min(n - n2, this.IAudioInputStream.AdditionalMetadataValue() - this.MetaInfomationCopy);
            System.arraycopy(this.IAudioInputStream.responseView(), this.MetaInfomationCopy, this.IAudioMetaInformation, n2, n3);
            System.arraycopy(this.IAudioInputStream.FFT(), this.MetaInfomationCopy, this.IBaseAudioCodec, n2, n3);
            n2 += n3;
            this.MetaInfomationCopy += n3;
            if (this.IAudioInputStream.AdditionalMetadataValue() > this.MetaInfomationCopy) continue;
            this.IAudioInputStream = null;
        }
        return new AudioSampleModel(null, Arrays.copyOfRange(this.IAudioMetaInformation, 0, n2), Arrays.copyOfRange(this.IBaseAudioCodec, 0, n2));
    }

    @Override
    public /* synthetic */ Object FFT(AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf) {
        return this.DSP(zvOTVUaKFTNpNSbbMYZfoXf);
    }

    class DSP
    implements Callable<AudioSampleModel> {
        private final int FFT;

        DSP(int n) {
            this.FFT = n;
        }

        public AudioSampleModel DSP() throws Exception {
            AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf = PackageSplitterBuffer.this.DSP(this.FFT);
            return zvOTVUaKFTNpNSbbMYZfoXf;
        }

        @Override
        public /* synthetic */ Object call() throws Exception {
            return this.DSP();
        }
    }

    class FFT
    implements Runnable {
        private AudioSampleModel FFT;
        private boolean responseView = true;
        private final IWritableAudioPlugin<AudioSampleModel> AdditionalMetadataValue;
        private final LinkedBlockingQueue<AudioSampleModel> AudioFileExtension;
        private final LinkedBlockingQueue<AudioSampleModel> IAudioFileCodec;
        private double[] IAudioInputStream;
        private double[] IAudioMetaInformation;
        private int IBaseAudioCodec;

        FFT(LinkedBlockingQueue<AudioSampleModel> linkedBlockingQueue, LinkedBlockingQueue<AudioSampleModel> linkedBlockingQueue2, IWritableAudioPlugin<AudioSampleModel> lKkynTEEZVTbuSJCgDWFhnQ2) {
            this.AudioFileExtension = linkedBlockingQueue;
            this.IAudioFileCodec = linkedBlockingQueue2;
            this.AdditionalMetadataValue = lKkynTEEZVTbuSJCgDWFhnQ2;
        }

        public synchronized AudioSampleModel DSP(int n) {
            int n2 = 0;
            this.IAudioInputStream = new double[n];
            this.IAudioMetaInformation = new double[n];
            while (n2 < n) {
                if (this.FFT == null) {
                    try {
                        this.IBaseAudioCodec = 0;
                        this.FFT = this.AudioFileExtension.take();
                    }
                    catch (InterruptedException interruptedException) {
                        break;
                    }
                }
                int n3 = Math.min(n - n2, this.FFT.AdditionalMetadataValue() - this.IBaseAudioCodec);
                System.arraycopy(this.FFT.responseView(), this.IBaseAudioCodec, this.IAudioInputStream, n2, n3);
                System.arraycopy(this.FFT.FFT(), this.IBaseAudioCodec, this.IAudioMetaInformation, n2, n3);
                n2 += n3;
                this.IBaseAudioCodec += n3;
                if (this.FFT.AdditionalMetadataValue() > this.IBaseAudioCodec) continue;
                this.FFT = null;
            }
            return new AudioSampleModel(null, Arrays.copyOfRange(this.IAudioInputStream, 0, n2), Arrays.copyOfRange(this.IAudioMetaInformation, 0, n2));
        }

        @Override
        public void run() {
            while (this.AdditionalMetadataValue != null) {
                this.IAudioFileCodec.offer(this.AdditionalMetadataValue.FFT(this.DSP(PackageSplitterBuffer.this.DSP)));
            }
            this.responseView = false;
        }
    }
}

