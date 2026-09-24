/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sourceforge.jaad.aac.Decoder
 *  net.sourceforge.jaad.aac.SampleBuffer
 *  net.sourceforge.jaad.mp4.MP4Container
 *  net.sourceforge.jaad.mp4.api.AudioTrack
 *  net.sourceforge.jaad.mp4.api.AudioTrack$AudioCodec
 *  net.sourceforge.jaad.mp4.api.Frame
 *  net.sourceforge.jaad.mp4.api.Movie
 *  net.sourceforge.jaad.mp4.api.Track$Codec
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.jaad.aac.Decoder;
import net.sourceforge.jaad.aac.SampleBuffer;
import net.sourceforge.jaad.mp4.MP4Container;
import net.sourceforge.jaad.mp4.api.AudioTrack;
import net.sourceforge.jaad.mp4.api.Frame;
import net.sourceforge.jaad.mp4.api.Movie;
import net.sourceforge.jaad.mp4.api.Track;
import sdfgjkljljoftrytrszgijpokjprs.responseView;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.AacMetaDataModel;

public class BufferedAacReader {
    private final BlockingQueue<FFT> DSP = new ArrayBlockingQueue<FFT>(10);
    private final Decoder FFT;
    private final MP4Container responseView;
    private final Movie AdditionalMetadataValue;
    private final AudioTrack AudioFileExtension;
    private final AacMetaDataModel IAudioFileCodec;
    private final sdfgjkljljoftrytrszgijpokjprs.FFT IAudioInputStream;
    private byte[] IAudioMetaInformation = null;
    private long IBaseAudioCodec;
    private long MetaInfomationCopy;
    private final long AacAudioCodec;
    private final DSP AacMetaDataModel = new DSP();
    private Thread BufferedAacReader = null;

    public BufferedAacReader(String string) throws FileNotFoundException, IOException {
        this.IAudioInputStream = sdfgjkljljoftrytrszgijpokjprs.responseView.DSP(string);
        this.responseView = new MP4Container(new RandomAccessFile(string, "r"));
        this.AdditionalMetadataValue = this.responseView.getMovie();
        this.AudioFileExtension = (AudioTrack)this.AdditionalMetadataValue.getTracks((Track.Codec)AudioTrack.AudioCodec.AAC).get(0);
        this.FFT = new Decoder(this.AudioFileExtension.getDecoderSpecificInfo());
        this.IAudioFileCodec = new AacMetaDataModel(this.AudioFileExtension.getSampleSize(), this.AudioFileExtension.getChannelCount(), this.AudioFileExtension.getSampleRate(), this.AdditionalMetadataValue.getDuration());
        this.IAudioFileCodec.DSP(this.IAudioInputStream);
        this.AacAudioCodec = this.IAudioFileCodec.IAudioInputStream();
        this.responseView();
    }

    public IAudioMetaInformation DSP() {
        return this.IAudioFileCodec;
    }

    private void responseView() {
        if (this.BufferedAacReader == null || !this.BufferedAacReader.isAlive()) {
            this.BufferedAacReader = new Thread(this.AacMetaDataModel);
            this.BufferedAacReader.setName("AAC Decoder");
            this.BufferedAacReader.setPriority(10);
            this.BufferedAacReader.start();
        }
    }

    public int DSP(byte[] byArray, int n, int n2) {
        int n3 = 0;
        byte[] byArray2 = new byte[n2];
        try {
            n3 = this.DSP(byArray2);
            System.arraycopy(byArray2, 0, byArray, n, n2);
        }
        catch (InterruptedException interruptedException) {
            n3 = -1;
        }
        this.MetaInfomationCopy += (long)n3;
        return n3;
    }

    private int DSP(byte[] byArray) throws InterruptedException {
        int n = 0;
        while (n < byArray.length) {
            if (this.IAudioMetaInformation == null) {
                if (this.AacAudioCodec <= this.IBaseAudioCodec || !this.BufferedAacReader.isAlive() && this.DSP.isEmpty()) break;
                FFT qPeIwmpzLZIktKLXAJOQHcO = this.DSP.take();
                this.IAudioMetaInformation = new byte[qPeIwmpzLZIktKLXAJOQHcO.FFT()];
                this.IBaseAudioCodec += (long)qPeIwmpzLZIktKLXAJOQHcO.FFT();
                System.arraycopy(qPeIwmpzLZIktKLXAJOQHcO.DSP(), 0, this.IAudioMetaInformation, 0, qPeIwmpzLZIktKLXAJOQHcO.FFT());
            }
            if (this.IAudioMetaInformation.length <= byArray.length - n) {
                System.arraycopy(this.IAudioMetaInformation, 0, byArray, n, this.IAudioMetaInformation.length);
                n += this.IAudioMetaInformation.length;
                this.IAudioMetaInformation = null;
                continue;
            }
            int n2 = byArray.length - n;
            byte[] byArray2 = new byte[this.IAudioMetaInformation.length - n2];
            System.arraycopy(this.IAudioMetaInformation, 0, byArray, n, n2);
            System.arraycopy(this.IAudioMetaInformation, n2, byArray2, 0, byArray2.length);
            this.IAudioMetaInformation = byArray2;
            n += byArray.length - n;
        }
        return n;
    }

    void FFT() {
        if (this.BufferedAacReader == null || !this.BufferedAacReader.isAlive()) {
            this.BufferedAacReader.interrupt();
        }
        this.BufferedAacReader = null;
    }

    private class FFT {
        private final byte[] FFT;

        public FFT(byte[] byArray) {
            this.FFT = byArray;
        }

        public byte[] DSP() {
            return this.FFT;
        }

        public int FFT() {
            return this.FFT.length;
        }
    }

    private class DSP
    implements Runnable {
        private Frame FFT;

        private DSP() {
        }

        @Override
        public void run() {
            try {
                while (BufferedAacReader.this.AudioFileExtension.hasMoreFrames() && !Thread.currentThread().isInterrupted()) {
                    SampleBuffer sampleBuffer = new SampleBuffer();
                    sampleBuffer.setBigEndian(false);
                    this.FFT = BufferedAacReader.this.AudioFileExtension.readNextFrame();
                    BufferedAacReader.this.FFT.decodeFrame(this.FFT.getData(), sampleBuffer);
                    BufferedAacReader.this.DSP.put(new FFT(sampleBuffer.getData()));
                }
            }
            catch (IOException | InterruptedException exception) {
                Logger.getLogger(BufferedAacReader.class.getName()).log(Level.SEVERE, null, exception);
            }
        }
    }
}

