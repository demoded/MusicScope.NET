/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdfgjkljljoftrytrszgijpokjprs.SimpleByteBuffer;
import sdfgjkljljoftrytrszgijpokjprs.DSTFrameInformationChunk;
import sdfgjkljljoftrytrszgijpokjprs.IChunkAvailableCallback;
import sdfgjkljljoftrytrszgijpokjprs.ExtendedDSTSoundDataChunk;
import sdfgjkljljoftrytrszgijpokjprs.RootChunkException;
import sdfgjkljljoftrytrszgijpokjprs.DSTFrameDataChunk;
import sdfgjkljljoftrytrszgijpokjprs.EmptySimpleByteBuffer;
import sdfgjkljljoftrytrszgijpokjprs.DSDFileStructure;
import sdfgjkljljoftrytrszgijpokjprs.DstDecodeUtil;
import sdfgjkljljoftrytrszgijpokjprs.FileChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.DSTFrameCRCChunk;

public class DstDecoder {
    private final File DSP;
    private final int FFT;
    private FileChunkReader<DSDFileStructure, ExtendedDSTSoundDataChunk> responseView;
    private DstDecodeUtil AdditionalMetadataValue;
    private byte[] AudioFileExtension;
    private SimpleByteBuffer IAudioFileCodec;
    private ArrayBlockingQueue<SimpleByteBuffer> IAudioInputStream;
    private DSTFrameInformationChunk IAudioMetaInformation;
    private Thread IBaseAudioCodec;

    public DstDecoder(File file, int n, int n2, int n3) throws DstDecodeUtil.responseView {
        this.DSP = file;
        this.FFT = n;
        this.responseView = new FileChunkReader<DSDFileStructure, ExtendedDSTSoundDataChunk>(DSDFileStructure.class, ExtendedDSTSoundDataChunk.class);
        this.responseView.DSP(DSTFrameCRCChunk.class);
        this.responseView.DSP(DSTFrameDataChunk.class);
        this.responseView.DSP(DSTFrameInformationChunk.class);
        this.AdditionalMetadataValue = new DstDecodeUtil();
        this.AdditionalMetadataValue.DSP(n3, n2 / 44100);
        this.AudioFileExtension = new byte[n3 * this.AdditionalMetadataValue.DSP.DemuxUtils];
        this.IAudioInputStream = new ArrayBlockingQueue(10);
        this.AdditionalMetadataValue();
    }

    public int DSP() {
        return this.IAudioMetaInformation.IAudioMetaInformation();
    }

    public long FFT() {
        return this.IAudioMetaInformation.DSP();
    }

    private void AdditionalMetadataValue() {
        this.IBaseAudioCodec = new Thread(new Runnable(){

            @Override
            public void run() {
                Thread.currentThread().setPriority(10);
                DstDecoder.this.AudioFileExtension();
            }
        });
        this.IBaseAudioCodec.start();
    }

    public int DSP(byte[] byArray, int n, int n2) {
        int n3 = 0;
        if (this.IAudioFileCodec instanceof EmptySimpleByteBuffer) {
            return -1;
        }
        int n4 = n;
        while (n4 < n2) {
            if (this.IAudioFileCodec == null || !this.IAudioFileCodec.DSP()) {
                try {
                    this.IAudioFileCodec = this.IAudioInputStream.take();
                }
                catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            if (this.IAudioFileCodec == null) continue;
            if (this.IAudioFileCodec instanceof EmptySimpleByteBuffer) break;
            int n5 = this.IAudioFileCodec.FFT(byArray, n4, n2 - n4);
            n4 += n5;
            n3 += n5;
        }
        return n3;
    }

    private void AudioFileExtension() {
        try {
            this.responseView.DSP(this.DSP, this.FFT, null, new IChunkAvailableCallback<DSDFileStructure>(){

                @Override
                public void DSP(DSDFileStructure kNJpubvMpXzMSIqgitDifgT2) {
                    if (kNJpubvMpXzMSIqgitDifgT2 instanceof DSTFrameDataChunk) {
                        DSTFrameDataChunk zwTiailknOMaqcvWfNrVfBZ = (DSTFrameDataChunk)kNJpubvMpXzMSIqgitDifgT2;
                        DstDecoder.this.DSP(zwTiailknOMaqcvWfNrVfBZ);
                    } else if (kNJpubvMpXzMSIqgitDifgT2 instanceof DSTFrameInformationChunk) {
                        DSTFrameInformationChunk lCZYIWvhetdpHtrSRHttohl = (DSTFrameInformationChunk)kNJpubvMpXzMSIqgitDifgT2;
                        DstDecoder.this.DSP(lCZYIWvhetdpHtrSRHttohl);
                    } else if (kNJpubvMpXzMSIqgitDifgT2 instanceof DSTFrameCRCChunk) {
                        DSTFrameCRCChunk yjgUBgAKtenjypOkvqtWPco2 = (DSTFrameCRCChunk)kNJpubvMpXzMSIqgitDifgT2;
                        DstDecoder.this.DSP(yjgUBgAKtenjypOkvqtWPco2);
                    }
                }
            });
        }
        catch (IOException | RootChunkException exception) {
            Logger.getLogger(DstDecoder.class.getName()).log(Level.SEVERE, null, exception);
        }
        try {
            this.IAudioInputStream.put(new EmptySimpleByteBuffer());
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    private void DSP(DSTFrameDataChunk zwTiailknOMaqcvWfNrVfBZ) {
        try {
            this.AdditionalMetadataValue.DSP(zwTiailknOMaqcvWfNrVfBZ.IAudioMetaInformation(), this.AudioFileExtension, (int)zwTiailknOMaqcvWfNrVfBZ.IAudioInputStream(), (int)zwTiailknOMaqcvWfNrVfBZ.DSP());
            SimpleByteBuffer abkRNvAOeWIbEeMzsGvXdIr = new SimpleByteBuffer(this.AudioFileExtension.length);
            abkRNvAOeWIbEeMzsGvXdIr.DSP(this.AudioFileExtension, 0, this.AudioFileExtension.length);
            this.IAudioInputStream.put(abkRNvAOeWIbEeMzsGvXdIr);
        }
        catch (InterruptedException | DstDecodeUtil.responseView exception) {
            Thread.currentThread().interrupt();
        }
    }

    private void DSP(DSTFrameInformationChunk lCZYIWvhetdpHtrSRHttohl) {
        this.IAudioMetaInformation = lCZYIWvhetdpHtrSRHttohl;
    }

    private void DSP(DSTFrameCRCChunk yjgUBgAKtenjypOkvqtWPco2) {
    }

    public void responseView() {
        this.IBaseAudioCodec.interrupt();
    }
}

