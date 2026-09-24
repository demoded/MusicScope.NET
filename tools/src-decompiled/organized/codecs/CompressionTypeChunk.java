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
@ChunkIDAnnotation(DSP="CMPR")
public class CompressionTypeChunk
extends DSDFileStructure {
    private DSP DSP;
    private short FFT;
    private String responseView;

    public DSP DSP() {
        return this.DSP;
    }

    @Override
    public long responseView() {
        return 5L + super.responseView();
    }

    @Override
    public void DSP(IChunkReader zBNFgJwMMkkaOLFmIBSJWbh) throws OutOfChunkRangeException {
        this.DSP = null;
        this.FFT = (short)-1;
        this.responseView = "";
        super.DSP(zBNFgJwMMkkaOLFmIBSJWbh);
        this.DSP = sdfgjkljljoftrytrszgijpokjprs.CompressionTypeChunk$rHAjVyBgPhqkQKsOvJMPMYn.DSP(zBNFgJwMMkkaOLFmIBSJWbh.FFT(4).array());
        this.FFT = zBNFgJwMMkkaOLFmIBSJWbh.FFT(1).get();
        byte[] byArray = zBNFgJwMMkkaOLFmIBSJWbh.FFT(this.FFT).array();
        for (int i = 0; i < this.FFT; ++i) {
            this.responseView = this.responseView + (char)byArray[i];
        }
    }

    @Override
    public boolean IAudioFileCodec() {
        boolean bl = super.IAudioFileCodec();
        bl &= this.DSP != null && this.DSP != sdfgjkljljoftrytrszgijpokjprs.CompressionTypeChunk$rHAjVyBgPhqkQKsOvJMPMYn.responseView;
        bl &= this.FFT >= 0;
        return bl &= !this.responseView.isEmpty() && this.responseView.length() == this.FFT;
    }

    public static enum DSP {
        DSP("DSD ", "not compressed", "Uncompressed, plain DSD audio data"),
        FFT("DST ", "DST Encoded", "DST Encoded audio data"),
        responseView("Unknown", "Unknown", "Unknown");

        private final String AdditionalMetadataValue;
        private final String AudioFileExtension;
        private final String IAudioFileCodec;

        private DSP(String string2, String string3, String string4) {
            this.AdditionalMetadataValue = string2;
            this.AudioFileExtension = string3;
            this.IAudioFileCodec = string4;
        }

        public String DSP() {
            return this.AdditionalMetadataValue;
        }

        public static DSP DSP(byte[] byArray) {
            String string = "";
            for (byte by : byArray) {
                string = string + (char)by;
            }
            for (DSP rHAjVyBgPhqkQKsOvJMPMYn2 : sdfgjkljljoftrytrszgijpokjprs.CompressionTypeChunk$rHAjVyBgPhqkQKsOvJMPMYn.values()) {
                if (!rHAjVyBgPhqkQKsOvJMPMYn2.DSP().equals(string)) continue;
                return rHAjVyBgPhqkQKsOvJMPMYn2;
            }
            return responseView;
        }
    }
}

