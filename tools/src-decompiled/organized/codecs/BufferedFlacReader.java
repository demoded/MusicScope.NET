/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import sdfgjkljljoftrytrszgijpokjprs.PCMProcessor;
import sdfgjkljljoftrytrszgijpokjprs.ByteData;
import sdfgjkljljoftrytrszgijpokjprs.StreamInfo;
import sdfgjkljljoftrytrszgijpokjprs.VorbisComment;
import sdfgjkljljoftrytrszgijpokjprs.FLACDecoder;
import sdfgjkljljoftrytrszgijpokjprs.Metadata;
import sdfgjkljljoftrytrszgijpokjprs.Picture;
import sdfgjkljljoftrytrszgijpokjprs.EmptyByteData;

public class BufferedFlacReader {
    private final DSP DSP;
    private int FFT = 0;
    private long responseView = 0L;
    private long AdditionalMetadataValue = 0L;
    private FLACDecoder AudioFileExtension;
    private StreamInfo IAudioFileCodec;
    private VorbisComment IAudioInputStream;
    private final ArrayList<Picture> IAudioMetaInformation;
    private Thread IBaseAudioCodec;
    private ArrayBlockingQueue<ByteData> MetaInfomationCopy = new ArrayBlockingQueue(10);
    private byte[] AacAudioCodec = null;

    public BufferedFlacReader(String string) throws FileNotFoundException, IOException {
        this.DSP = new DSP(string);
        this.IAudioMetaInformation = new ArrayList(0);
        for (Metadata eJnHMGFAPyJBjoMvbKedmZt2 : this.DSP(string)) {
            if (eJnHMGFAPyJBjoMvbKedmZt2 instanceof VorbisComment) {
                this.IAudioInputStream = (VorbisComment)eJnHMGFAPyJBjoMvbKedmZt2;
                continue;
            }
            if (!(eJnHMGFAPyJBjoMvbKedmZt2 instanceof Picture)) continue;
            this.IAudioMetaInformation.add((Picture)eJnHMGFAPyJBjoMvbKedmZt2);
        }
        this.responseView();
    }

    private Metadata[] DSP(String string) {
        FFT qPeIwmpzLZIktKLXAJOQHcO = new FFT(string);
        try {
            Thread thread = new Thread(qPeIwmpzLZIktKLXAJOQHcO);
            thread.start();
            thread.join();
            return qPeIwmpzLZIktKLXAJOQHcO.DSP();
        }
        catch (InterruptedException interruptedException) {
            return null;
        }
    }

    public void DSP() {
        if (this.DSP != null) {
            this.DSP.DSP();
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
            this.MetaInfomationCopy.clear();
        }
        this.FFT += n3;
        return n3;
    }

    private void responseView() throws IOException {
        if (this.IBaseAudioCodec == null || !this.IBaseAudioCodec.isAlive()) {
            try {
                this.MetaInfomationCopy.clear();
                this.IBaseAudioCodec = new Thread(this.DSP);
                this.IBaseAudioCodec.setName("FLAC Decoder");
                this.IBaseAudioCodec.setPriority(10);
                this.IBaseAudioCodec.start();
                ByteData jBphLoDiqESKYDzcdAUENWI = this.MetaInfomationCopy.take();
                if (jBphLoDiqESKYDzcdAUENWI instanceof EmptyByteData) {
                    throw new IOException("FLAC Decoder Exception");
                }
                this.AacAudioCodec = new byte[jBphLoDiqESKYDzcdAUENWI.FFT()];
                this.AdditionalMetadataValue += (long)jBphLoDiqESKYDzcdAUENWI.FFT();
                System.arraycopy(jBphLoDiqESKYDzcdAUENWI.DSP(), 0, this.AacAudioCodec, 0, jBphLoDiqESKYDzcdAUENWI.FFT());
            }
            catch (InterruptedException interruptedException) {
                this.AacAudioCodec = null;
                this.AdditionalMetadataValue = 0L;
            }
        }
    }

    private int DSP(byte[] byArray) throws InterruptedException {
        int n = 0;
        while (n < byArray.length) {
            int n2;
            if (this.AacAudioCodec == null) {
                if (this.responseView <= this.AdditionalMetadataValue || !this.IBaseAudioCodec.isAlive() && this.MetaInfomationCopy.isEmpty()) break;
                ByteData jBphLoDiqESKYDzcdAUENWI = this.MetaInfomationCopy.take();
                this.AacAudioCodec = new byte[jBphLoDiqESKYDzcdAUENWI.FFT()];
                this.AdditionalMetadataValue += (long)jBphLoDiqESKYDzcdAUENWI.FFT();
                System.arraycopy(jBphLoDiqESKYDzcdAUENWI.DSP(), 0, this.AacAudioCodec, 0, jBphLoDiqESKYDzcdAUENWI.FFT());
            }
            if (this.AacAudioCodec.length <= byArray.length - n) {
                System.arraycopy(this.AacAudioCodec, 0, byArray, n, this.AacAudioCodec.length);
                n += this.AacAudioCodec.length;
                n2 = Arrays.equals(this.AacAudioCodec, byArray) ? 1 : 0;
                this.AacAudioCodec = null;
                continue;
            }
            n2 = byArray.length - n;
            byte[] byArray2 = new byte[this.AacAudioCodec.length - n2];
            System.arraycopy(this.AacAudioCodec, 0, byArray, n, n2);
            System.arraycopy(this.AacAudioCodec, n2, byArray2, 0, byArray2.length);
            this.AacAudioCodec = byArray2;
            n += byArray.length - n;
        }
        return n;
    }

    public StreamInfo FFT() {
        if (this.IAudioFileCodec == null) {
            try {
                ByteData jBphLoDiqESKYDzcdAUENWI = this.MetaInfomationCopy.take();
                this.AacAudioCodec = new byte[jBphLoDiqESKYDzcdAUENWI.FFT()];
                this.AdditionalMetadataValue += (long)jBphLoDiqESKYDzcdAUENWI.FFT();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        return this.IAudioFileCodec;
    }

    private class FFT
    implements Runnable {
        private Metadata[] FFT;
        private String responseView;

        public FFT(String string) {
            this.responseView = string;
        }

        @Override
        public void run() {
            try (FileInputStream fileInputStream = new FileInputStream(this.responseView);){
                FLACDecoder urPYhCUqiTisyGhbxRbXXJc = new FLACDecoder(fileInputStream);
                this.FFT = urPYhCUqiTisyGhbxRbXXJc.DSP();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }

        public Metadata[] DSP() {
            if (this.FFT != null) {
                return (Metadata[])this.FFT.clone();
            }
            return new Metadata[0];
        }
    }

    private class DSP
    implements Runnable,
    PCMProcessor {
        private FileInputStream FFT;

        public DSP(String string) throws FileNotFoundException {
            this.FFT = new FileInputStream(string);
            BufferedFlacReader.this.AudioFileExtension = new FLACDecoder(this.FFT);
        }

        @Override
        public void run() {
            try {
                if (BufferedFlacReader.this.AudioFileExtension != null) {
                    BufferedFlacReader.this.AudioFileExtension.DSP(this);
                    BufferedFlacReader.this.AudioFileExtension.FFT();
                }
            }
            catch (IOException iOException) {
                this.DSP(new EmptyByteData());
                this.DSP();
            }
        }

        @Override
        public void DSP(StreamInfo kTkvbLlxuYGcvSGyJtmnFsX) {
            BufferedFlacReader.this.IAudioFileCodec = kTkvbLlxuYGcvSGyJtmnFsX;
            BufferedFlacReader.this.responseView = BufferedFlacReader.this.IAudioFileCodec.AudioFileExtension() * (long)BufferedFlacReader.this.IAudioFileCodec.IAudioMetaInformation() * (long)(BufferedFlacReader.this.IAudioFileCodec.IAudioInputStream() / 8);
        }

        @Override
        public void DSP(ByteData jBphLoDiqESKYDzcdAUENWI) {
            try {
                BufferedFlacReader.this.MetaInfomationCopy.put(jBphLoDiqESKYDzcdAUENWI);
            }
            catch (InterruptedException interruptedException) {
                this.DSP();
            }
        }

        public void DSP() {
            try {
                BufferedFlacReader.this.AudioFileExtension = null;
                this.FFT.close();
            }
            catch (IOException iOException) {
                this.FFT = null;
            }
        }
    }
}

