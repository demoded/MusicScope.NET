/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.ChunkInfo;
import sdfgjkljljoftrytrszgijpokjprs.SampleInfo;

class DemuxResT {
    public int DSP;
    public int FFT;
    public int responseView;
    public int AdditionalMetadataValue;
    public int AudioFileExtension;
    public int[] IAudioFileCodec = new int[81920];
    public SampleInfo[] IAudioInputStream = new SampleInfo[16];
    public int IAudioMetaInformation;
    public int[] IBaseAudioCodec;
    public int MetaInfomationCopy;
    public int[] AacAudioCodec = new int[1024];
    public int[] AacMetaDataModel;
    public ChunkInfo[] BufferedAacReader;
    public int AiffAudioCodec;

    public DemuxResT() {
        for (int i = 0; i < 16; ++i) {
            this.IAudioInputStream[i] = new SampleInfo();
        }
    }
}

