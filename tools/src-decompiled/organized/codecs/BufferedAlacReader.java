/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jaudiotagger.audio.AudioFile
 *  org.jaudiotagger.audio.AudioFileIO
 *  org.jaudiotagger.audio.exceptions.CannotReadException
 *  org.jaudiotagger.audio.exceptions.InvalidAudioFrameException
 *  org.jaudiotagger.audio.exceptions.ReadOnlyFileException
 *  org.jaudiotagger.tag.FieldKey
 *  org.jaudiotagger.tag.KeyNotFoundException
 *  org.jaudiotagger.tag.Tag
 *  org.jaudiotagger.tag.TagException
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import sdfgjkljljoftrytrszgijpokjprs.AlacContextModel;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.AlacUtils;
import sdfgjkljljoftrytrszgijpokjprs.AdditionalMetadataValue;
import sdfgjkljljoftrytrszgijpokjprs.AlacMetaDataModel;

public class BufferedAlacReader {
    private final BlockingQueue<FFT> DSP;
    private final AlacContextModel FFT;
    private final sdfgjkljljoftrytrszgijpokjprs.FFT responseView;
    private AlacMetaDataModel AdditionalMetadataValue;
    private byte[] AudioFileExtension = null;
    private long IAudioFileCodec;
    private long IAudioInputStream;
    private long IAudioMetaInformation;
    private DSP IBaseAudioCodec;
    private Thread MetaInfomationCopy;

    public BufferedAlacReader(String string) {
        this.responseView = this.DSP(string);
        this.DSP = new ArrayBlockingQueue<FFT>(10);
        this.FFT = AlacUtils.DSP(string);
        this.AdditionalMetadataValue = this.FFT();
        this.IAudioMetaInformation = this.AdditionalMetadataValue.IAudioInputStream();
        this.IBaseAudioCodec = new DSP();
        this.MetaInfomationCopy = null;
        this.responseView();
    }

    public void DSP() {
        AlacUtils.DSP(this.FFT);
        if (this.MetaInfomationCopy != null && this.MetaInfomationCopy.isAlive()) {
            this.MetaInfomationCopy.interrupt();
        }
        this.MetaInfomationCopy = null;
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
        this.IAudioInputStream += (long)n3;
        return n3;
    }

    public final AlacMetaDataModel FFT() {
        if (this.AdditionalMetadataValue == null) {
            int n = AlacUtils.AdditionalMetadataValue(this.FFT);
            int n2 = AlacUtils.responseView(this.FFT);
            int n3 = AlacUtils.IAudioFileCodec(this.FFT);
            int n4 = AlacUtils.FFT(this.FFT);
            this.AdditionalMetadataValue = new AlacMetaDataModel(n4, n3, n2, BitsPerSample.FFT(n));
            this.AdditionalMetadataValue.DSP(this.responseView);
        }
        return this.AdditionalMetadataValue;
    }

    private sdfgjkljljoftrytrszgijpokjprs.FFT DSP(String string) {
        sdfgjkljljoftrytrszgijpokjprs.FFT qPeIwmpzLZIktKLXAJOQHcO = new sdfgjkljljoftrytrszgijpokjprs.FFT();
        try {
            Object object;
            File file = new File(string);
            AudioFile audioFile = AudioFileIO.read((File)file);
            Tag tag = audioFile.getTag();
            if (tag.hasField(FieldKey.ALBUM_ARTIST) && !(object = tag.getAll(FieldKey.ALBUM_ARTIST)).isEmpty()) {
                qPeIwmpzLZIktKLXAJOQHcO.IAudioFileCodec(new AdditionalMetadataValue<String[]>(object.toArray(new String[object.size()])));
            }
            if (tag.hasField(FieldKey.ALBUM) && !((String)(object = tag.getFirst(FieldKey.ALBUM))).isEmpty()) {
                qPeIwmpzLZIktKLXAJOQHcO.AdditionalMetadataValue(new AdditionalMetadataValue<Object>(object));
            }
            if (tag.hasField(FieldKey.DISC_NO) && !((String)(object = tag.getFirst(FieldKey.DISC_NO))).isEmpty()) {
                qPeIwmpzLZIktKLXAJOQHcO.AacAudioCodec(new AdditionalMetadataValue<Integer>(Integer.parseInt((String)object)));
            }
            if (tag.hasField(FieldKey.GENRE) && !((String)(object = tag.getFirst(FieldKey.GENRE))).isEmpty()) {
                qPeIwmpzLZIktKLXAJOQHcO.FFT(new AdditionalMetadataValue<Object>(object));
            }
            if (tag.hasField(FieldKey.PRODUCER) && !((String)(object = tag.getFirst(FieldKey.PRODUCER))).isEmpty()) {
                qPeIwmpzLZIktKLXAJOQHcO.responseView(new AdditionalMetadataValue<Object>(object));
            }
            if (tag.hasField(FieldKey.DISC_TOTAL) && !((String)(object = tag.getFirst(FieldKey.DISC_TOTAL))).isEmpty()) {
                qPeIwmpzLZIktKLXAJOQHcO.MetaInfomationCopy(new AdditionalMetadataValue<Integer>(Integer.parseInt((String)object)));
            }
            if (tag.hasField(FieldKey.TRACK_TOTAL) && !((String)(object = tag.getFirst(FieldKey.TRACK_TOTAL))).isEmpty()) {
                qPeIwmpzLZIktKLXAJOQHcO.IAudioInputStream(new AdditionalMetadataValue<Integer>(Integer.parseInt((String)object)));
            }
            if (tag.hasField(FieldKey.YEAR) && !((String)(object = tag.getFirst(FieldKey.YEAR))).isEmpty()) {
                qPeIwmpzLZIktKLXAJOQHcO.IBaseAudioCodec(new AdditionalMetadataValue<Integer>(Integer.parseInt((String)object)));
            }
            if (tag.hasField(FieldKey.ARTIST) && !(object = tag.getAll(FieldKey.ARTIST)).isEmpty()) {
                qPeIwmpzLZIktKLXAJOQHcO.AudioFileExtension(new AdditionalMetadataValue<String[]>(object.toArray(new String[object.size()])));
            }
            if (tag.hasField(FieldKey.TITLE) && !((String)(object = tag.getFirst(FieldKey.TITLE))).isEmpty()) {
                qPeIwmpzLZIktKLXAJOQHcO.DSP(new AdditionalMetadataValue<Object>(object));
            }
            if (tag.hasField(FieldKey.TRACK) && !((String)(object = tag.getFirst(FieldKey.TRACK))).isEmpty()) {
                qPeIwmpzLZIktKLXAJOQHcO.IAudioMetaInformation(new AdditionalMetadataValue<Integer>(Integer.parseInt((String)object)));
            }
        }
        catch (IOException | NumberFormatException | CannotReadException | InvalidAudioFrameException | ReadOnlyFileException | KeyNotFoundException | TagException throwable) {
            Logger.getLogger(BufferedAlacReader.class.getName()).log(Level.SEVERE, null, throwable);
            return null;
        }
        return qPeIwmpzLZIktKLXAJOQHcO;
    }

    private void responseView() {
        if (this.MetaInfomationCopy == null || !this.MetaInfomationCopy.isAlive()) {
            this.MetaInfomationCopy = new Thread(this.IBaseAudioCodec);
            this.MetaInfomationCopy.setName("ALAC Decoder");
            this.MetaInfomationCopy.setPriority(10);
            this.MetaInfomationCopy.start();
        }
    }

    private int DSP(byte[] byArray) throws InterruptedException {
        int n = 0;
        while (n < byArray.length) {
            if (this.AudioFileExtension == null) {
                if (this.IAudioMetaInformation <= this.IAudioFileCodec || !this.MetaInfomationCopy.isAlive() && this.DSP.isEmpty()) break;
                FFT qPeIwmpzLZIktKLXAJOQHcO = this.DSP.take();
                this.AudioFileExtension = new byte[qPeIwmpzLZIktKLXAJOQHcO.FFT()];
                this.IAudioFileCodec += (long)qPeIwmpzLZIktKLXAJOQHcO.FFT();
                System.arraycopy(qPeIwmpzLZIktKLXAJOQHcO.DSP(), 0, this.AudioFileExtension, 0, qPeIwmpzLZIktKLXAJOQHcO.FFT());
            }
            if (this.AudioFileExtension.length <= byArray.length - n) {
                System.arraycopy(this.AudioFileExtension, 0, byArray, n, this.AudioFileExtension.length);
                n += this.AudioFileExtension.length;
                this.AudioFileExtension = null;
                continue;
            }
            int n2 = byArray.length - n;
            byte[] byArray2 = new byte[this.AudioFileExtension.length - n2];
            System.arraycopy(this.AudioFileExtension, 0, byArray, n, n2);
            System.arraycopy(this.AudioFileExtension, n2, byArray2, 0, byArray2.length);
            this.AudioFileExtension = byArray2;
            n += byArray.length - n;
        }
        return n;
    }

    private class FFT {
        private final byte[] FFT;
        private final int responseView;

        public FFT(int[] nArray, int n) {
            this.FFT = this.DSP(nArray, n);
            this.responseView = n;
        }

        public byte[] DSP() {
            return this.FFT;
        }

        public int FFT() {
            return this.responseView;
        }

        private byte[] DSP(int[] nArray, int n) {
            int n2 = 0;
            int n3 = 0;
            byte[] byArray = new byte[65536];
            switch (BufferedAlacReader.this.FFT().FFT().FFT()) {
                case 1: {
                    while (n > 0) {
                        byArray[n2] = (byte)(0xFF & nArray[n2] + 128);
                        ++n2;
                        --n;
                    }
                    break;
                }
                case 2: {
                    while (n > 0) {
                        byte[] byArray2 = ByteBuffer.allocate(4).putInt(nArray[n3]).array();
                        byArray[n2] = byArray2[3];
                        byArray[++n2] = byArray2[2];
                        ++n2;
                        ++n3;
                        n -= 2;
                    }
                    break;
                }
                case 3: {
                    while (n > 0) {
                        byArray[n2] = (byte)nArray[n3];
                        ++n2;
                        ++n3;
                        --n;
                    }
                    break;
                }
            }
            return byArray;
        }
    }

    private class DSP
    implements Runnable {
        private DSP() {
        }

        @Override
        public void run() {
            int n;
            int[] nArray = new int[73728];
            while ((n = AlacUtils.DSP(BufferedAlacReader.this.FFT, nArray)) > 0 && !Thread.currentThread().isInterrupted()) {
                try {
                    BufferedAlacReader.this.DSP.put(new FFT(nArray, n));
                }
                catch (InterruptedException interruptedException) {
                    BufferedAlacReader.this.DSP.clear();
                }
            }
        }
    }
}

