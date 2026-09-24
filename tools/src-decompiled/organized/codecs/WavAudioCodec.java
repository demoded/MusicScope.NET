/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdfgjkljljoftrytrszgijpokjprs.responseView;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.WaveBaseChunk;
import sdfgjkljljoftrytrszgijpokjprs.IAudioFileCodec;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.RiffChunk;
import sdfgjkljljoftrytrszgijpokjprs.AudioFileExtension;
import sdfgjkljljoftrytrszgijpokjprs.MetaInformationParser;
import sdfgjkljljoftrytrszgijpokjprs.WavMetaDataModel;

@AudioFileExtension(DSP={"wav"})
public final class WavAudioCodec
implements IAudioFileCodec {
    private final RandomAccessFile DSP;
    private long FFT;
    private WavMetaDataModel responseView;
    private MetaInformationParser<WaveBaseChunk, RiffChunk> AdditionalMetadataValue;
    private int AudioFileExtension;
    private RiffChunk IAudioFileCodec;
    private String IAudioInputStream;

    public WavAudioCodec() {
        this.DSP = null;
    }

    public WavAudioCodec(String string) throws FileNotFoundException {
        this.IAudioInputStream = string;
        this.DSP = new RandomAccessFile(string, "r");
        this.responseView = null;
        this.AudioFileExtension = 0;
        if (this.DSP != null && this.FFT() != null) {
            this.FFT = this.FFT().IBaseAudioCodec();
        }
    }

    public WavAudioCodec FFT(String string) throws FileNotFoundException {
        boolean bl = true;
        WavAudioCodec ivncmIpiNjIzzGkIkGWOXBB2 = new WavAudioCodec(string);
        IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA = ivncmIpiNjIzzGkIkGWOXBB2.FFT();
        if (yGjBevanihqaxYKnUNtrNeA != null) {
            bl &= yGjBevanihqaxYKnUNtrNeA.AdditionalMetadataValue() == 2 || yGjBevanihqaxYKnUNtrNeA.AdditionalMetadataValue() == 1;
            bl &= yGjBevanihqaxYKnUNtrNeA.IAudioInputStream() > 0L;
            bl &= yGjBevanihqaxYKnUNtrNeA.DSP() > 0;
            bl &= yGjBevanihqaxYKnUNtrNeA.responseView() > 0;
            bl &= yGjBevanihqaxYKnUNtrNeA.FFT() != BitsPerSample.DSP;
            bl &= yGjBevanihqaxYKnUNtrNeA.FFT() != BitsPerSample.FFT;
        } else {
            bl = false;
        }
        return bl ? ivncmIpiNjIzzGkIkGWOXBB2 : null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int DSP(byte[] byArray, int n, int n2) {
        int n3 = n2;
        int n4 = -1;
        if (this.DSP != null && this.FFT().IAudioInputStream() > (long)this.AudioFileExtension) {
            RandomAccessFile randomAccessFile = this.DSP;
            synchronized (randomAccessFile) {
                try {
                    if (this.DSP.getFilePointer() != this.FFT) {
                        this.DSP.seek(this.FFT);
                    }
                    if (this.DSP() < (long)n2) {
                        n3 = (int)this.DSP();
                    }
                    n4 = this.DSP.read(byArray, n, n3);
                    this.AudioFileExtension += n4;
                    this.FFT = this.DSP.getFilePointer();
                }
                catch (IOException iOException) {
                    Logger.getLogger(WavAudioCodec.class.getName()).log(Level.SEVERE, null, iOException);
                }
            }
        }
        return n4;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public IAudioMetaInformation FFT() {
        if (this.responseView == null && this.DSP != null) {
            RandomAccessFile randomAccessFile = this.DSP;
            synchronized (randomAccessFile) {
                this.IAudioFileCodec = new RiffChunk();
                try {
                    this.AdditionalMetadataValue = new MetaInformationParser<WaveBaseChunk, RiffChunk>(WaveBaseChunk.class, RiffChunk.class);
                    this.AdditionalMetadataValue.DSP(this.IAudioFileCodec, this.DSP);
                    Set<String> set = this.AdditionalMetadataValue.DSP();
                    boolean bl = set.contains("bext");
                    this.responseView = new WavMetaDataModel(this.IAudioFileCodec, bl, this.DSP.length(), this.AdditionalMetadataValue.FFT());
                    this.responseView.DSP(sdfgjkljljoftrytrszgijpokjprs.responseView.DSP(this.IAudioInputStream));
                }
                catch (IOException iOException) {
                    return null;
                }
            }
        }
        return this.responseView;
    }

    @Override
    public IAudioMetaInformation responseView() {
        return this.FFT();
    }

    @Override
    public void AdditionalMetadataValue() {
        try {
            this.DSP.close();
        }
        catch (IOException iOException) {
            Logger.getLogger(WavAudioCodec.class.getName()).log(Level.SEVERE, null, iOException);
        }
    }

    private long DSP() {
        return this.FFT().IAudioInputStream() - this.FFT + (long)this.FFT().IBaseAudioCodec();
    }

    @Override
    public /* synthetic */ IAudioFileCodec DSP(String string) throws Exception {
        return this.FFT(string);
    }
}

