/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.DSFFileStructure;
import sdfgjkljljoftrytrszgijpokjprs.OutOfChunkRangeException;
import sdfgjkljljoftrytrszgijpokjprs.ChunkRequiredAnnotation;
import sdfgjkljljoftrytrszgijpokjprs.IChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.ChunkIDAnnotation;

@ChunkRequiredAnnotation
@ChunkIDAnnotation(DSP="fmt ")
public class FMTChunkHeader
extends DSFFileStructure {
    private byte[] DSP;
    private FFT FFT;
    private DSP responseView;
    private int AdditionalMetadataValue;
    private int AudioFileExtension;
    private int IAudioFileCodec;
    private long IAudioInputStream;
    private int IAudioMetaInformation;
    private byte[] IBaseAudioCodec;

    public int DSP() {
        return this.AdditionalMetadataValue;
    }

    public int IAudioMetaInformation() {
        return this.AudioFileExtension;
    }

    public int IBaseAudioCodec() {
        return this.IAudioFileCodec;
    }

    public int MetaInfomationCopy() {
        return this.IAudioMetaInformation;
    }

    @Override
    public void DSP(IChunkReader zBNFgJwMMkkaOLFmIBSJWbh) throws OutOfChunkRangeException {
        this.DSP = new byte[4];
        this.FFT = sdfgjkljljoftrytrszgijpokjprs.FMTChunkHeader$QPeIwmpzLZIktKLXAJOQHcO.FFT;
        this.responseView = sdfgjkljljoftrytrszgijpokjprs.FMTChunkHeader$rHAjVyBgPhqkQKsOvJMPMYn.IAudioMetaInformation;
        this.AdditionalMetadataValue = -1;
        this.AudioFileExtension = -1;
        this.IAudioFileCodec = -1;
        this.IAudioInputStream = -1L;
        this.IAudioMetaInformation = -1;
        this.IBaseAudioCodec = new byte[4];
        super.DSP(zBNFgJwMMkkaOLFmIBSJWbh);
        this.DSP = zBNFgJwMMkkaOLFmIBSJWbh.DSP(4).array();
        this.FFT = sdfgjkljljoftrytrszgijpokjprs.FMTChunkHeader$QPeIwmpzLZIktKLXAJOQHcO.DSP(zBNFgJwMMkkaOLFmIBSJWbh.DSP(4).getInt());
        this.responseView = sdfgjkljljoftrytrszgijpokjprs.FMTChunkHeader$rHAjVyBgPhqkQKsOvJMPMYn.DSP(zBNFgJwMMkkaOLFmIBSJWbh.DSP(4).getInt());
        this.AdditionalMetadataValue = zBNFgJwMMkkaOLFmIBSJWbh.DSP(4).getInt();
        this.AudioFileExtension = zBNFgJwMMkkaOLFmIBSJWbh.DSP(4).getInt();
        this.IAudioFileCodec = zBNFgJwMMkkaOLFmIBSJWbh.DSP(4).getInt();
        this.IAudioInputStream = zBNFgJwMMkkaOLFmIBSJWbh.DSP(8).getLong();
        this.IAudioMetaInformation = zBNFgJwMMkkaOLFmIBSJWbh.DSP(4).getInt();
        this.IBaseAudioCodec = zBNFgJwMMkkaOLFmIBSJWbh.DSP(4).array();
    }

    @Override
    public boolean IAudioFileCodec() {
        boolean bl = true;
        return bl;
    }

    public static enum DSP {
        DSP(1),
        FFT(2),
        responseView(3),
        AdditionalMetadataValue(4),
        AudioFileExtension(5),
        IAudioFileCodec(6),
        IAudioInputStream(7),
        IAudioMetaInformation(-1);

        private final int IBaseAudioCodec;

        private DSP(int n2) {
            this.IBaseAudioCodec = n2;
        }

        public int DSP() {
            return this.IBaseAudioCodec;
        }

        public static DSP DSP(int n) {
            for (DSP rHAjVyBgPhqkQKsOvJMPMYn2 : sdfgjkljljoftrytrszgijpokjprs.FMTChunkHeader$rHAjVyBgPhqkQKsOvJMPMYn.values()) {
                if (rHAjVyBgPhqkQKsOvJMPMYn2.DSP() != n) continue;
                return rHAjVyBgPhqkQKsOvJMPMYn2;
            }
            return IAudioMetaInformation;
        }
    }

    public static enum FFT {
        DSP(0),
        FFT(-1);

        private final int responseView;

        private FFT(int n2) {
            this.responseView = n2;
        }

        public int DSP() {
            return this.responseView;
        }

        public static FFT DSP(int n) {
            for (FFT qPeIwmpzLZIktKLXAJOQHcO : sdfgjkljljoftrytrszgijpokjprs.FMTChunkHeader$QPeIwmpzLZIktKLXAJOQHcO.values()) {
                if (qPeIwmpzLZIktKLXAJOQHcO.DSP() != n) continue;
                return qPeIwmpzLZIktKLXAJOQHcO;
            }
            return FFT;
        }
    }
}

