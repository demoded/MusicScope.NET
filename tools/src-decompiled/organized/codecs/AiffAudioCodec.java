/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import sdfgjkljljoftrytrszgijpokjprs.AiffMetaDataModel;
import sdfgjkljljoftrytrszgijpokjprs.responseView;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.FFT;
import sdfgjkljljoftrytrszgijpokjprs.IAudioFileCodec;
import sdfgjkljljoftrytrszgijpokjprs.AiffBaseChunk;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.FormChunk;
import sdfgjkljljoftrytrszgijpokjprs.AudioFileExtension;
import sdfgjkljljoftrytrszgijpokjprs.MetaInformationParser;

@AudioFileExtension(DSP={"aif", "aiff"})
public class AiffAudioCodec
implements IAudioFileCodec {
    private final RandomAccessFile DSP;
    private AiffMetaDataModel FFT;
    private MetaInformationParser<AiffBaseChunk, FormChunk> responseView;
    private FFT AdditionalMetadataValue;
    private long AudioFileExtension;
    private int IAudioFileCodec;

    public AiffAudioCodec() {
        this(null, "");
    }

    public AiffAudioCodec(RandomAccessFile randomAccessFile, String string) {
        this.DSP = randomAccessFile;
        this.FFT = null;
        this.IAudioFileCodec = 0;
        this.AdditionalMetadataValue = sdfgjkljljoftrytrszgijpokjprs.responseView.DSP(string);
        if (randomAccessFile != null && this.FFT() != null) {
            this.AudioFileExtension = this.FFT().IBaseAudioCodec();
        }
    }

    @Override
    public IAudioFileCodec DSP(String string) throws Exception {
        boolean bl = true;
        AiffAudioCodec vJgxuXjYdOvTUFZsFThavLL2 = new AiffAudioCodec(new RandomAccessFile(string, "r"), string);
        IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA = vJgxuXjYdOvTUFZsFThavLL2.FFT();
        if (yGjBevanihqaxYKnUNtrNeA != null) {
            bl &= yGjBevanihqaxYKnUNtrNeA.AdditionalMetadataValue() == 1 || yGjBevanihqaxYKnUNtrNeA.AdditionalMetadataValue() == 2;
            bl &= yGjBevanihqaxYKnUNtrNeA.IAudioInputStream() > 0L;
            bl &= yGjBevanihqaxYKnUNtrNeA.DSP() > 0;
            bl &= yGjBevanihqaxYKnUNtrNeA.responseView() > 0;
            bl &= yGjBevanihqaxYKnUNtrNeA.FFT() != BitsPerSample.DSP;
            bl &= yGjBevanihqaxYKnUNtrNeA.FFT() != BitsPerSample.FFT;
        } else {
            bl = false;
        }
        return bl ? vJgxuXjYdOvTUFZsFThavLL2 : null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int DSP(byte[] byArray, int n, int n2) {
        int n3 = n2;
        int n4 = -1;
        AiffMetaDataModel foVkhFqKjpvomrYQnnVFKwY = (AiffMetaDataModel)this.FFT();
        try {
            if (this.DSP != null && foVkhFqKjpvomrYQnnVFKwY.IAudioInputStream() > (long)this.IAudioFileCodec) {
                RandomAccessFile randomAccessFile = this.DSP;
                synchronized (randomAccessFile) {
                    if (this.DSP.getFilePointer() != this.AudioFileExtension) {
                        this.DSP.seek(this.AudioFileExtension);
                    }
                    if (this.DSP() < (long)n2) {
                        n3 = (int)this.DSP();
                    }
                    byte[] byArray2 = new byte[n3];
                    n4 = this.DSP.read(byArray2, n, n3);
                    ByteBuffer byteBuffer = ByteBuffer.wrap(byArray2);
                    switch (this.FFT().FFT()) {
                        case FFT: {
                            byArray = byteBuffer.array();
                            break;
                        }
                        case responseView: {
                            for (int i = 0; i < byArray2.length; i += BitsPerSample.responseView.FFT()) {
                                if (foVkhFqKjpvomrYQnnVFKwY.AiffAudioCodec()) {
                                    byArray[i] = byteBuffer.get();
                                    byArray[i + 1] = byteBuffer.get();
                                    continue;
                                }
                                byArray[i + 1] = byteBuffer.get();
                                byArray[i] = byteBuffer.get();
                            }
                            break;
                        }
                        case AdditionalMetadataValue: {
                            for (int i = 0; i < byArray2.length; i += BitsPerSample.AdditionalMetadataValue.FFT()) {
                                byArray[i + 2] = byteBuffer.get();
                                byArray[i + 1] = byteBuffer.get();
                                byArray[i] = byteBuffer.get();
                            }
                            break;
                        }
                        case AudioFileExtension: {
                            for (int i = 0; i < byArray2.length; i += BitsPerSample.AudioFileExtension.FFT()) {
                                byArray[i + 3] = byteBuffer.get();
                                byArray[i + 2] = byteBuffer.get();
                                byArray[i + 1] = byteBuffer.get();
                                byArray[i] = byteBuffer.get();
                            }
                            break;
                        }
                    }
                    this.AudioFileExtension = this.DSP.getFilePointer();
                }
            }
            this.IAudioFileCodec += n4;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return n4;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final IAudioMetaInformation FFT() {
        if (this.FFT == null && this.DSP != null) {
            RandomAccessFile randomAccessFile = this.DSP;
            synchronized (randomAccessFile) {
                FormChunk cdSECoXyrascmeWiUkiBTmK2 = new FormChunk();
                try {
                    this.responseView = new MetaInformationParser<AiffBaseChunk, FormChunk>(AiffBaseChunk.class, FormChunk.class);
                    this.responseView.DSP(cdSECoXyrascmeWiUkiBTmK2, this.DSP);
                    this.FFT = new AiffMetaDataModel(cdSECoXyrascmeWiUkiBTmK2, this.DSP.length(), this.responseView.FFT());
                    this.FFT.DSP(this.AdditionalMetadataValue);
                }
                catch (IOException iOException) {
                    return null;
                }
            }
        }
        return this.FFT;
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
            // empty catch block
        }
    }

    private long DSP() {
        return this.FFT().IAudioInputStream() - this.AudioFileExtension + (long)this.FFT().IBaseAudioCodec();
    }
}

