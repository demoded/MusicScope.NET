/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.SwingUtilities;
import sdfgjkljljoftrytrszgijpokjprs.ILoadableCallback;
import sdfgjkljljoftrytrszgijpokjprs.IFinishedCallback;
import sdfgjkljljoftrytrszgijpokjprs.FileCounterWorker;
import sdfgjkljljoftrytrszgijpokjprs.ICommitableStorage;
import sdfgjkljljoftrytrszgijpokjprs.ICancelable;
import sdfgjkljljoftrytrszgijpokjprs.FileLoaderWorker;
import sdfgjkljljoftrytrszgijpokjprs.ILoadingDialog;

public class FileLoader<T>
implements ICommitableStorage<T>,
ICancelable {
    private final Object DSP = new Object();
    private ILoadingDialog FFT;
    private ExecutorService responseView;
    private ArrayList<IFinishedCallback> AdditionalMetadataValue;
    private LinkedList<FileLoaderWorker<T>> AudioFileExtension;
    private LinkedList<FileCounterWorker<T>> IAudioFileCodec;
    private ConcurrentLinkedQueue<Future<?>> IAudioInputStream;
    private ICommitableStorage<T> IAudioMetaInformation;
    private ArrayList<T> IBaseAudioCodec;
    private Timer MetaInfomationCopy;
    private TimerTask AacAudioCodec;
    private TimerTask AacMetaDataModel;
    private boolean BufferedAacReader;

    public FileLoader(ICommitableStorage<T> rvmLMdDLEGHIAejiawKxuPa, ILoadingDialog uqIWpQkdtRoGxqvvNunoXtF2) {
        this(rvmLMdDLEGHIAejiawKxuPa, uqIWpQkdtRoGxqvvNunoXtF2, Executors.newSingleThreadExecutor());
    }

    public FileLoader(ICommitableStorage<T> rvmLMdDLEGHIAejiawKxuPa, ILoadingDialog uqIWpQkdtRoGxqvvNunoXtF2, ExecutorService executorService) {
        this.FFT = uqIWpQkdtRoGxqvvNunoXtF2;
        this.responseView = executorService;
        this.IAudioMetaInformation = rvmLMdDLEGHIAejiawKxuPa;
        this.AdditionalMetadataValue = new ArrayList(0);
        this.IAudioFileCodec = new LinkedList();
        this.AudioFileExtension = new LinkedList();
        this.IAudioInputStream = new ConcurrentLinkedQueue();
        this.IBaseAudioCodec = new ArrayList(0);
        this.MetaInfomationCopy = new Timer();
        this.AacAudioCodec = new FFT();
        this.AacMetaDataModel = new DSP();
        this.MetaInfomationCopy.schedule(this.AacAudioCodec, 500L, 500L);
        this.MetaInfomationCopy.schedule(this.AacMetaDataModel, 500L, 500L);
    }

    public synchronized void DSP(File file, boolean bl, ILoadableCallback<T> cbBsidOPrITWbOsdPkAmBWy) {
        if (file.exists()) {
            int n = bl ? Integer.MAX_VALUE : 1;
            this.IAudioFileCodec.offer(new FileCounterWorker(file, n, this.FFT));
            this.AudioFileExtension.offer(new FileLoaderWorker<T>(file, n, this.IAudioMetaInformation, cbBsidOPrITWbOsdPkAmBWy, this.FFT));
        }
    }

    public boolean DSP(IFinishedCallback fWVSYwHLXQCcyAFBrRLKUjC) {
        if (!this.AdditionalMetadataValue.contains(fWVSYwHLXQCcyAFBrRLKUjC)) {
            this.AdditionalMetadataValue.add(fWVSYwHLXQCcyAFBrRLKUjC);
            return true;
        }
        return false;
    }

    public synchronized void DSP() {
        if (!this.BufferedAacReader) {
            this.BufferedAacReader = true;
            for (FileCounterWorker pXWGTuXQwPqeGEOZDFZSMdD2 : this.IAudioFileCodec) {
                this.IAudioInputStream.offer(this.responseView.submit(pXWGTuXQwPqeGEOZDFZSMdD2));
            }
            for (FileLoaderWorker tkBoqhkEEfdmrRvSNpbnpqq2 : this.AudioFileExtension) {
                this.IAudioInputStream.offer(this.responseView.submit(tkBoqhkEEfdmrRvSNpbnpqq2));
            }
        }
    }

    @Override
    public void FFT() {
        for (FileCounterWorker pXWGTuXQwPqeGEOZDFZSMdD2 : this.IAudioFileCodec) {
            pXWGTuXQwPqeGEOZDFZSMdD2.DSP(false);
        }
        for (FileLoaderWorker tkBoqhkEEfdmrRvSNpbnpqq2 : this.AudioFileExtension) {
            tkBoqhkEEfdmrRvSNpbnpqq2.DSP(false);
        }
    }

    @Override
    public synchronized void DSP(T t) {
        this.IBaseAudioCodec.add(t);
    }

    public ILoadingDialog responseView() {
        return this.FFT;
    }

    @Override
    private void DSP(final boolean bl) {
        if (this.FFT != null) {
            SwingUtilities.invokeLater(new Runnable(){

                @Override
                public void run() {
                    FileLoader.this.FFT.setVisible(bl);
                }
            });
        }
    }

    private void AdditionalMetadataValue() {
        for (IFinishedCallback fWVSYwHLXQCcyAFBrRLKUjC : this.AdditionalMetadataValue) {
            fWVSYwHLXQCcyAFBrRLKUjC.FFT();
        }
    }

    private class DSP
    extends TimerTask {
        private DSP() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            Object object = FileLoader.this.DSP;
            synchronized (object) {
                for (Future future : FileLoader.this.IAudioInputStream) {
                    if (!future.isDone() && !future.isCancelled()) continue;
                    FileLoader.this.IAudioInputStream.remove(future);
                }
                if (FileLoader.this.FFT != null && FileLoader.this.IAudioInputStream.isEmpty() && FileLoader.this.BufferedAacReader) {
                    FileLoader.this.IAudioInputStream.clear();
                    FileLoader.this.AudioFileExtension.clear();
                    FileLoader.this.IAudioFileCodec.clear();
                    FileLoader.this.FFT.FFT();
                    FileLoader.this.DSP(false);
                    FileLoader.this.AdditionalMetadataValue();
                    FileLoader.this.BufferedAacReader = false;
                }
            }
        }
    }

    private class FFT
    extends TimerTask {
        private FFT() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            Object object = FileLoader.this.DSP;
            synchronized (object) {
                if (FileLoader.this.FFT != null && !FileLoader.this.FFT.isVisible() && FileLoader.this.FFT.DSP() > 10) {
                    FileLoader.this.DSP(true);
                }
            }
        }
    }
}

