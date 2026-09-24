/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.MetaInfomationCopy;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormatCode;
import sdfgjkljljoftrytrszgijpokjprs.IAudioFileCodec;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.AudioFileExtension;
import sdfgjkljljoftrytrszgijpokjprs.DirectStreamDigitalDecoder;
import sdfgjkljljoftrytrszgijpokjprs.DffChunkReaderAdapter;
import sdfgjkljljoftrytrszgijpokjprs.IMetadataReader;
import sdfgjkljljoftrytrszgijpokjprs.DsfChunkReaderAdapter;

@AudioFileExtension(DSP={"dff", "dsf"})
public class DsdAudioCodec
implements IAudioFileCodec {
    private final ArrayList<IMetadataReader> DSP;
    private File FFT;
    private DirectStreamDigitalDecoder responseView;
    private IAudioMetaInformation AdditionalMetadataValue;
    private int AudioFileExtension;

    public DsdAudioCodec(int n) {
        this.AudioFileExtension = n;
        this.DSP = new ArrayList(0);
        this.DSP.add(new DffChunkReaderAdapter());
        this.DSP.add(new DsfChunkReaderAdapter());
        this.FFT = null;
    }

    public DsdAudioCodec(String string, int n, IMetadataReader rlLfOrnBfhXwDoxKtXtJZRR2) throws FileNotFoundException {
        this(n);
        this.FFT = new File(string);
        this.AudioFileExtension = n;
        this.responseView = rlLfOrnBfhXwDoxKtXtJZRR2.responseView(this.FFT).DSP() == 2822400 ? new DirectStreamDigitalDecoder(this.FFT, 176400, rlLfOrnBfhXwDoxKtXtJZRR2) : new DirectStreamDigitalDecoder(this.FFT, n, rlLfOrnBfhXwDoxKtXtJZRR2);
        this.AdditionalMetadataValue = this.responseView.DSP();
    }

    @Override
    public IAudioFileCodec DSP(String string) throws Exception {
        File file = new File(string);
        for (IMetadataReader rlLfOrnBfhXwDoxKtXtJZRR2 : this.DSP) {
            if (!rlLfOrnBfhXwDoxKtXtJZRR2.FFT(file)) continue;
            return new DsdAudioCodec(string, this.AudioFileExtension, rlLfOrnBfhXwDoxKtXtJZRR2);
        }
        return null;
    }

    @Override
    public int DSP(byte[] byArray, int n, int n2) {
        if (this.responseView != null) {
            return this.responseView.DSP(byArray, n, n2);
        }
        return -1;
    }

    @Override
    public IAudioMetaInformation FFT() {
        return this.AdditionalMetadataValue;
    }

    @Override
    public IAudioMetaInformation responseView() {
        MetaInfomationCopy nuCoRkdmXmpraThwzNAiTcw = new MetaInfomationCopy(this.AdditionalMetadataValue);
        if (this.AdditionalMetadataValue.DSP() == 2822400) {
            nuCoRkdmXmpraThwzNAiTcw.DSP(176400);
        } else {
            nuCoRkdmXmpraThwzNAiTcw.DSP(this.AudioFileExtension);
        }
        nuCoRkdmXmpraThwzNAiTcw.DSP(BitsPerSample.AdditionalMetadataValue);
        nuCoRkdmXmpraThwzNAiTcw.DSP(AudioFormatCode.DSP);
        nuCoRkdmXmpraThwzNAiTcw.FFT(BitsPerSample.AdditionalMetadataValue.FFT() * nuCoRkdmXmpraThwzNAiTcw.DSP() * 2);
        nuCoRkdmXmpraThwzNAiTcw.DSP((long)nuCoRkdmXmpraThwzNAiTcw.responseView() * nuCoRkdmXmpraThwzNAiTcw.IAudioFileCodec());
        return nuCoRkdmXmpraThwzNAiTcw;
    }

    @Override
    public void AdditionalMetadataValue() {
        this.responseView.FFT();
    }
}

