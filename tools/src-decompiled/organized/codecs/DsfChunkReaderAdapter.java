/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.File;
import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.DsdMetaDataModel;
import sdfgjkljljoftrytrszgijpokjprs.responseView;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.FFT;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormatCode;
import sdfgjkljljoftrytrszgijpokjprs.RootChunkException;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.DSFChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.DATAChunkHeader;
import sdfgjkljljoftrytrszgijpokjprs.IMetadataReader;
import sdfgjkljljoftrytrszgijpokjprs.FileChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.FMTChunkHeader;

public class DsfChunkReaderAdapter
implements IMetadataReader {
    private final DSFChunkReader DSP = new DSFChunkReader();

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
        FFT qPeIwmpzLZIktKLXAJOQHcO = responseView.DSP(file.getPath());
        if (this.FFT(file)) {
            DsdMetaDataModel ggoKTcuIevtAhezhbHlrofU = new DsdMetaDataModel();
            FMTChunkHeader tgEnMDWUChukKDvHjnBDvdF2 = this.DSP.FFT(FMTChunkHeader.class);
            ggoKTcuIevtAhezhbHlrofU.responseView(tgEnMDWUChukKDvHjnBDvdF2.DSP());
            ggoKTcuIevtAhezhbHlrofU.DSP(tgEnMDWUChukKDvHjnBDvdF2.IAudioMetaInformation());
            DATAChunkHeader jpSvGdIXyBXOcuLxzsPHYvF2 = this.DSP.FFT(DATAChunkHeader.class);
            ggoKTcuIevtAhezhbHlrofU.FFT(jpSvGdIXyBXOcuLxzsPHYvF2.AudioFileExtension());
            ggoKTcuIevtAhezhbHlrofU.DSP(jpSvGdIXyBXOcuLxzsPHYvF2.IAudioInputStream());
            ggoKTcuIevtAhezhbHlrofU.DSP(AudioExtension.IAudioFileCodec);
            ggoKTcuIevtAhezhbHlrofU.DSP(AudioFormatCode.responseView);
            ggoKTcuIevtAhezhbHlrofU.DSP(BitsPerSample.DSP);
            ggoKTcuIevtAhezhbHlrofU.FFT(tgEnMDWUChukKDvHjnBDvdF2.IAudioMetaInformation() * tgEnMDWUChukKDvHjnBDvdF2.DSP() / 8);
            ggoKTcuIevtAhezhbHlrofU.DSP(qPeIwmpzLZIktKLXAJOQHcO);
            return ggoKTcuIevtAhezhbHlrofU;
        }
        return null;
    }
}

