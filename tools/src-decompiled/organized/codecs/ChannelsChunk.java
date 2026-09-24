/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.OutOfChunkRangeException;
import sdfgjkljljoftrytrszgijpokjprs.ChunkRequiredAnnotation;
import sdfgjkljljoftrytrszgijpokjprs.IChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.DSDFileStructure;
import sdfgjkljljoftrytrszgijpokjprs.ChunkIDAnnotation;

@ChunkRequiredAnnotation
@ChunkIDAnnotation(DSP="CHNL")
public class ChannelsChunk
extends DSDFileStructure {
    private int DSP;
    private DSP[] FFT;

    public int DSP() {
        return this.DSP;
    }

    @Override
    public long responseView() {
        return 6L + super.responseView();
    }

    @Override
    public void DSP(IChunkReader zBNFgJwMMkkaOLFmIBSJWbh) throws OutOfChunkRangeException {
        this.DSP = -1;
        this.FFT = null;
        super.DSP(zBNFgJwMMkkaOLFmIBSJWbh);
        this.DSP = zBNFgJwMMkkaOLFmIBSJWbh.FFT(2).getShort();
        if (this.IAudioInputStream() - 2L == (long)(this.DSP * 4)) {
            this.FFT = new DSP[this.DSP];
            for (int i = 0; i < this.DSP; ++i) {
                this.FFT[i] = sdfgjkljljoftrytrszgijpokjprs.ChannelsChunk$rHAjVyBgPhqkQKsOvJMPMYn.DSP(zBNFgJwMMkkaOLFmIBSJWbh.FFT(4).array());
            }
        }
    }

    @Override
    public boolean IAudioFileCodec() {
        boolean bl = super.IAudioFileCodec();
        bl &= this.DSP > 0;
        bl &= this.FFT != null;
        for (DSP rHAjVyBgPhqkQKsOvJMPMYn2 : this.FFT) {
            bl &= rHAjVyBgPhqkQKsOvJMPMYn2 != null;
        }
        return bl;
    }

    public static enum DSP {
        DSP("SLFT"),
        FFT("SRGT"),
        responseView("MLFT"),
        AdditionalMetadataValue("MRGT"),
        AudioFileExtension("LS  "),
        IAudioFileCodec("RS  "),
        IAudioInputStream("C   "),
        IAudioMetaInformation("LFE "),
        IBaseAudioCodec("    ");

        private final String MetaInfomationCopy;

        private DSP(String string2) {
            this.MetaInfomationCopy = string2;
        }

        public String DSP() {
            return this.MetaInfomationCopy;
        }

        public static DSP DSP(byte[] byArray) {
            String string = "";
            for (byte by : byArray) {
                string = string + (char)by;
            }
            for (DSP rHAjVyBgPhqkQKsOvJMPMYn2 : sdfgjkljljoftrytrszgijpokjprs.ChannelsChunk$rHAjVyBgPhqkQKsOvJMPMYn.values()) {
                if (!rHAjVyBgPhqkQKsOvJMPMYn2.DSP().equals(string)) continue;
                return rHAjVyBgPhqkQKsOvJMPMYn2;
            }
            return IBaseAudioCodec;
        }
    }
}

