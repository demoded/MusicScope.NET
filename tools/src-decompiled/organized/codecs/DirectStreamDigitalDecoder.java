/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdfgjkljljoftrytrszgijpokjprs.CompressionTypeChunk;
import sdfgjkljljoftrytrszgijpokjprs.InterleavedBufferBuilder;
import sdfgjkljljoftrytrszgijpokjprs.SampleRateChunk;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.MetaInfomationCopy;
import sdfgjkljljoftrytrszgijpokjprs.IChannelInputReader;
import sdfgjkljljoftrytrszgijpokjprs.DSTSoundDataChunk;
import sdfgjkljljoftrytrszgijpokjprs.DSDSoundDataChunk;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.DSFChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.ChannelSplitter;
import sdfgjkljljoftrytrszgijpokjprs.DATAChunkHeader;
import sdfgjkljljoftrytrszgijpokjprs.IReadableChannelBuffer;
import sdfgjkljljoftrytrszgijpokjprs.DstDecodeUtil;
import sdfgjkljljoftrytrszgijpokjprs.ChannelsChunk;
import sdfgjkljljoftrytrszgijpokjprs.IMetadataReader;
import sdfgjkljljoftrytrszgijpokjprs.FileChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.StreamDecoder;
import sdfgjkljljoftrytrszgijpokjprs.DstDecoder;
import sdfgjkljljoftrytrszgijpokjprs.DSDChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.FMTChunkHeader;
import sdfgjkljljoftrytrszgijpokjprs.IRawInputReader;

public class DirectStreamDigitalDecoder {
    private final FileChunkReader<?, ?> DSP;
    private final IMetadataReader FFT;
    private final IAudioMetaInformation responseView;
    private RandomAccessFile AdditionalMetadataValue;
    private BitsPerSample AudioFileExtension;
    private boolean IAudioFileCodec;
    private long IAudioInputStream;
    private long IAudioMetaInformation;
    private int IBaseAudioCodec;
    private int MetaInfomationCopy;
    private int AacAudioCodec;
    private int AacMetaDataModel;
    private final ChannelSplitter BufferedAacReader;
    private final StreamDecoder AiffAudioCodec;
    private final InterleavedBufferBuilder AiffMetaDataModel;
    private ExecutorService AlacAudioCodec;
    private CompressionTypeChunk.DSP AlacMetaDataModel;
    private DstDecoder BufferedAlacReader;

    public DirectStreamDigitalDecoder(File file, int n, IMetadataReader rlLfOrnBfhXwDoxKtXtJZRR2) throws FileNotFoundException {
        this.DSP = rlLfOrnBfhXwDoxKtXtJZRR2.DSP(file);
        this.MetaInfomationCopy = n;
        this.FFT = rlLfOrnBfhXwDoxKtXtJZRR2;
        this.responseView = rlLfOrnBfhXwDoxKtXtJZRR2.responseView(file);
        this.responseView();
        this.AlacAudioCodec = Executors.newFixedThreadPool(4);
        if (this.AlacMetaDataModel == CompressionTypeChunk.DSP.FFT) {
            this.BufferedAlacReader = this.DSP(file, (int)this.IAudioInputStream);
        } else {
            this.AdditionalMetadataValue = new RandomAccessFile(file, "r");
            try {
                this.AdditionalMetadataValue.seek(this.IAudioInputStream);
            }
            catch (IOException iOException) {
                Logger.getLogger(DirectStreamDigitalDecoder.class.getName()).log(Level.SEVERE, null, iOException);
            }
        }
        this.BufferedAacReader = this.AdditionalMetadataValue();
        this.AiffAudioCodec = this.DSP(this.BufferedAacReader);
        this.AiffMetaDataModel = this.FFT(this.AiffAudioCodec);
        this.AlacAudioCodec.submit(this.AiffMetaDataModel);
        this.AlacAudioCodec.submit(this.BufferedAacReader);
    }

    private void responseView() {
        if (this.DSP instanceof DSFChunkReader) {
            DSFChunkReader cIaNgIszMXSoKdZbRGCiSuk2 = (DSFChunkReader)this.DSP;
            FMTChunkHeader tgEnMDWUChukKDvHjnBDvdF2 = cIaNgIszMXSoKdZbRGCiSuk2.FFT(FMTChunkHeader.class);
            DATAChunkHeader jpSvGdIXyBXOcuLxzsPHYvF2 = cIaNgIszMXSoKdZbRGCiSuk2.FFT(DATAChunkHeader.class);
            this.AudioFileExtension = BitsPerSample.FFT(tgEnMDWUChukKDvHjnBDvdF2.IBaseAudioCodec());
            this.IBaseAudioCodec = tgEnMDWUChukKDvHjnBDvdF2.IAudioMetaInformation();
            this.AacAudioCodec = tgEnMDWUChukKDvHjnBDvdF2.DSP();
            this.AacMetaDataModel = tgEnMDWUChukKDvHjnBDvdF2.MetaInfomationCopy();
            this.IAudioInputStream = jpSvGdIXyBXOcuLxzsPHYvF2.AudioFileExtension();
            this.IAudioMetaInformation = jpSvGdIXyBXOcuLxzsPHYvF2.IAudioInputStream();
            this.AlacMetaDataModel = CompressionTypeChunk.DSP.DSP;
            this.IAudioFileCodec = true;
        } else if (this.DSP instanceof DSDChunkReader) {
            DSDChunkReader tQZPiEROvTBoxdSKRnTuEga2 = (DSDChunkReader)this.DSP;
            DSDSoundDataChunk wWdABBAOLHkqAtdpmTKIFCl = tQZPiEROvTBoxdSKRnTuEga2.FFT(DSDSoundDataChunk.class);
            DSTSoundDataChunk vrBmTUlgGEBDCClukVjqNYg = tQZPiEROvTBoxdSKRnTuEga2.FFT(DSTSoundDataChunk.class);
            SampleRateChunk gFsEHFuIaAcJUXDnlJCAkUP = tQZPiEROvTBoxdSKRnTuEga2.FFT(SampleRateChunk.class);
            ChannelsChunk pWaynWJrpybCvPdHZNDjVhH2 = tQZPiEROvTBoxdSKRnTuEga2.FFT(ChannelsChunk.class);
            CompressionTypeChunk amVDEgunHyXIgyaarlTajMI = tQZPiEROvTBoxdSKRnTuEga2.FFT(CompressionTypeChunk.class);
            if (wWdABBAOLHkqAtdpmTKIFCl != null) {
                this.IAudioInputStream = wWdABBAOLHkqAtdpmTKIFCl.AudioFileExtension();
                this.IAudioMetaInformation = wWdABBAOLHkqAtdpmTKIFCl.IAudioInputStream();
            }
            if (vrBmTUlgGEBDCClukVjqNYg != null) {
                this.IAudioInputStream = vrBmTUlgGEBDCClukVjqNYg.AdditionalMetadataValue();
                this.IAudioMetaInformation = vrBmTUlgGEBDCClukVjqNYg.IAudioInputStream();
            }
            this.AudioFileExtension = BitsPerSample.DSP;
            this.IBaseAudioCodec = gFsEHFuIaAcJUXDnlJCAkUP.DSP();
            this.AacAudioCodec = pWaynWJrpybCvPdHZNDjVhH2.DSP();
            this.AlacMetaDataModel = amVDEgunHyXIgyaarlTajMI.DSP();
            this.AacMetaDataModel = 1;
            this.IAudioFileCodec = false;
        }
    }

    public IAudioMetaInformation DSP() {
        MetaInfomationCopy nuCoRkdmXmpraThwzNAiTcw = new MetaInfomationCopy(this.responseView);
        if (this.AlacMetaDataModel == CompressionTypeChunk.DSP.FFT && this.BufferedAlacReader != null) {
            double d = this.BufferedAlacReader.FFT() / (long)this.BufferedAlacReader.DSP();
            nuCoRkdmXmpraThwzNAiTcw.AdditionalMetadataValue((int)d);
        }
        return nuCoRkdmXmpraThwzNAiTcw;
    }

    public int DSP(byte[] byArray, int n, int n2) {
        return this.AiffMetaDataModel.DSP(byArray, n, n2);
    }

    private DstDecoder DSP(File file, int n) {
        try {
            return new DstDecoder(file, n, this.IBaseAudioCodec, this.AacAudioCodec);
        }
        catch (DstDecodeUtil.responseView jSuXbPLjXcQLpTiButqPpoI) {
            Logger.getLogger(DirectStreamDigitalDecoder.class.getName()).log(Level.SEVERE, null, jSuXbPLjXcQLpTiButqPpoI);
            return null;
        }
    }

    private ChannelSplitter AdditionalMetadataValue() {
        return new ChannelSplitter(this.AacAudioCodec, this.AacMetaDataModel, new IRawInputReader(){

            @Override
            public int DSP(byte[] byArray) {
                try {
                    switch (DirectStreamDigitalDecoder.this.AlacMetaDataModel) {
                        case FFT: {
                            return DirectStreamDigitalDecoder.this.BufferedAlacReader.DSP(byArray, 0, byArray.length);
                        }
                    }
                    if (DirectStreamDigitalDecoder.this.AdditionalMetadataValue != null) {
                        return DirectStreamDigitalDecoder.this.AdditionalMetadataValue.read(byArray);
                    }
                    return -1;
                }
                catch (IOException iOException) {
                    Logger.getLogger(DirectStreamDigitalDecoder.class.getName()).log(Level.SEVERE, null, iOException);
                    return 0;
                }
            }
        });
    }

    private StreamDecoder DSP(final IReadableChannelBuffer mzuRiLdvInzoAeWfRiBnJOp2) {
        return new StreamDecoder(this.AlacAudioCodec, this.AacAudioCodec, 4096, 10, this.IBaseAudioCodec, this.MetaInfomationCopy, this.AudioFileExtension, this.IAudioFileCodec, new IChannelInputReader(){

            @Override
            public int DSP(int n, byte[] byArray) {
                return mzuRiLdvInzoAeWfRiBnJOp2.DSP(n, byArray, 0, byArray.length);
            }
        });
    }

    private InterleavedBufferBuilder FFT(final IReadableChannelBuffer mzuRiLdvInzoAeWfRiBnJOp2) {
        return new InterleavedBufferBuilder(this.AacAudioCodec, 10, 12288, new IChannelInputReader(){

            @Override
            public int DSP(int n, byte[] byArray) {
                return mzuRiLdvInzoAeWfRiBnJOp2.DSP(n, byArray, 0, byArray.length);
            }
        });
    }

    public void FFT() {
        try {
            this.AlacAudioCodec.shutdownNow();
            if (this.BufferedAlacReader != null) {
                this.BufferedAlacReader.responseView();
            }
            if (this.AdditionalMetadataValue != null) {
                this.AdditionalMetadataValue.close();
            }
        }
        catch (IOException iOException) {
            Logger.getLogger(DirectStreamDigitalDecoder.class.getName()).log(Level.SEVERE, null, iOException);
        }
    }
}

