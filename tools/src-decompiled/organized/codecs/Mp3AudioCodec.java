/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mpatric.mp3agic.InvalidDataException
 *  com.mpatric.mp3agic.Mp3File
 *  com.mpatric.mp3agic.NotSupportedException
 *  com.mpatric.mp3agic.UnsupportedTagException
 */
package sdfgjkljljoftrytrszgijpokjprs;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import sdfgjkljljoftrytrszgijpokjprs.Mp3MetaDataModel;
import sdfgjkljljoftrytrszgijpokjprs.responseView;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.FFT;
import sdfgjkljljoftrytrszgijpokjprs.IAudioFileCodec;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.AudioFileExtension;

@AudioFileExtension(DSP={"mp3"})
public class Mp3AudioCodec
implements IAudioFileCodec {
    private static final String DSP = System.getProperty("java.io.tmpdir");
    private Mp3File FFT;
    private Mp3MetaDataModel responseView;
    private AudioInputStream AdditionalMetadataValue;
    private AudioInputStream AudioFileExtension;
    private AudioFormat IAudioFileCodec;
    private AudioFormat IAudioInputStream;
    private FFT IAudioMetaInformation;
    private File IBaseAudioCodec;
    private long MetaInfomationCopy = 0L;

    public Mp3AudioCodec() {
    }

    public Mp3AudioCodec(String string) throws UnsupportedAudioFileException, IOException {
        this.IBaseAudioCodec = new File(DSP + "2342");
        if (this.IBaseAudioCodec.exists()) {
            this.IBaseAudioCodec.delete();
        }
        this.IBaseAudioCodec.deleteOnExit();
        this.IAudioMetaInformation = sdfgjkljljoftrytrszgijpokjprs.responseView.DSP(string);
        try {
            this.FFT = new Mp3File(string);
            this.MetaInfomationCopy = this.FFT.getLengthInMilliseconds();
            this.FFT.removeCustomTag();
            this.FFT.removeId3v1Tag();
            this.FFT.removeId3v2Tag();
            this.FFT.save(this.IBaseAudioCodec.getAbsolutePath());
        }
        catch (InvalidDataException | NotSupportedException | UnsupportedTagException | IOException throwable) {
            // empty catch block
        }
        this.AudioFileExtension = AudioSystem.getAudioInputStream(this.IBaseAudioCodec);
        this.IAudioFileCodec = this.AudioFileExtension.getFormat();
        this.IAudioInputStream = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, this.IAudioFileCodec.getSampleRate(), BitsPerSample.responseView.DSP(), this.IAudioFileCodec.getChannels(), this.IAudioFileCodec.getChannels() * 2, this.IAudioFileCodec.getSampleRate(), false);
        this.AdditionalMetadataValue = AudioSystem.getAudioInputStream(this.IAudioInputStream, this.AudioFileExtension);
    }

    @Override
    public IAudioFileCodec DSP(String string) throws Exception {
        return new Mp3AudioCodec(string);
    }

    @Override
    public int DSP(byte[] byArray, int n, int n2) {
        if (this.AdditionalMetadataValue != null) {
            try {
                return this.AdditionalMetadataValue.read(byArray, n, n2);
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return -1;
    }

    @Override
    public IAudioMetaInformation FFT() {
        return this.responseView();
    }

    @Override
    public IAudioMetaInformation responseView() {
        if (this.responseView == null) {
            this.responseView = new Mp3MetaDataModel(this.IAudioInputStream, this.DSP());
            this.responseView.DSP(this.IAudioMetaInformation);
        }
        return this.responseView;
    }

    @Override
    public void AdditionalMetadataValue() {
        try {
            this.AudioFileExtension.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public int DSP() {
        float f = (float)this.IAudioInputStream.getChannels() * this.IAudioInputStream.getSampleRate() * 2.0f / 1000.0f;
        return (int)((float)this.MetaInfomationCopy * f);
    }
}

