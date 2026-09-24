/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.File;
import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.SampleRateChunk;
import sdfgjkljljoftrytrszgijpokjprs.DsdMetaDataModel;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormatCode;
import sdfgjkljljoftrytrszgijpokjprs.DSTSoundDataChunk;
import sdfgjkljljoftrytrszgijpokjprs.DSDSoundDataChunk;
import sdfgjkljljoftrytrszgijpokjprs.RootChunkException;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.ChannelsChunk;
import sdfgjkljljoftrytrszgijpokjprs.IMetadataReader;
import sdfgjkljljoftrytrszgijpokjprs.FileChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.DSDChunkReader;

public class DffChunkReaderAdapter
implements IMetadataReader {
    private final DSDChunkReader DSP = new DSDChunkReader();

    @Override
    public FileChunkReader<?, ?> DSP(File file) {
        if (this.FFT(file)) {
            return this.DSP;
        }
        return null;
    }

    @Override
    public boolean FFT(File file) {
        try {
            this.DSP.DSP(file);
        }
        catch (IOException | RootChunkException exception) {
            return false;
        }
        return this.DSP.DSP();
    }

    @Override
    public IAudioMetaInformation responseView(File file) {
        if (this.FFT(file)) {
            DSTSoundDataChunk vrBmTUlgGEBDCClukVjqNYg;
            DsdMetaDataModel ggoKTcuIevtAhezhbHlrofU = new DsdMetaDataModel();
            ChannelsChunk pWaynWJrpybCvPdHZNDjVhH2 = this.DSP.FFT(ChannelsChunk.class);
            ggoKTcuIevtAhezhbHlrofU.responseView(pWaynWJrpybCvPdHZNDjVhH2.DSP());
            SampleRateChunk gFsEHFuIaAcJUXDnlJCAkUP = this.DSP.FFT(SampleRateChunk.class);
            ggoKTcuIevtAhezhbHlrofU.DSP(gFsEHFuIaAcJUXDnlJCAkUP.DSP());
            DSDSoundDataChunk wWdABBAOLHkqAtdpmTKIFCl = this.DSP.FFT(DSDSoundDataChunk.class);
            if (wWdABBAOLHkqAtdpmTKIFCl != null) {
                ggoKTcuIevtAhezhbHlrofU.FFT(wWdABBAOLHkqAtdpmTKIFCl.AudioFileExtension());
                ggoKTcuIevtAhezhbHlrofU.DSP(wWdABBAOLHkqAtdpmTKIFCl.IAudioInputStream());
            }
            if ((vrBmTUlgGEBDCClukVjqNYg = this.DSP.FFT(DSTSoundDataChunk.class)) != null) {
                ggoKTcuIevtAhezhbHlrofU.FFT(vrBmTUlgGEBDCClukVjqNYg.AudioFileExtension());
                ggoKTcuIevtAhezhbHlrofU.DSP(vrBmTUlgGEBDCClukVjqNYg.IAudioInputStream());
            }
            ggoKTcuIevtAhezhbHlrofU.DSP(AudioExtension.IAudioInputStream);
            ggoKTcuIevtAhezhbHlrofU.DSP(AudioFormatCode.responseView);
            ggoKTcuIevtAhezhbHlrofU.DSP(BitsPerSample.DSP);
            ggoKTcuIevtAhezhbHlrofU.FFT(gFsEHFuIaAcJUXDnlJCAkUP.DSP() * pWaynWJrpybCvPdHZNDjVhH2.DSP() / 8);
            return ggoKTcuIevtAhezhbHlrofU;
        }
        return null;
    }
}

