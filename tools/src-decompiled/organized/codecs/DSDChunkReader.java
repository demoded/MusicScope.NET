/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.CompressionTypeChunk;
import sdfgjkljljoftrytrszgijpokjprs.SampleRateChunk;
import sdfgjkljljoftrytrszgijpokjprs.FormDSDChunk;
import sdfgjkljljoftrytrszgijpokjprs.DSTSoundDataChunk;
import sdfgjkljljoftrytrszgijpokjprs.DSDSoundDataChunk;
import sdfgjkljljoftrytrszgijpokjprs.DSDFileStructure;
import sdfgjkljljoftrytrszgijpokjprs.PropertyChunk;
import sdfgjkljljoftrytrszgijpokjprs.ChannelsChunk;
import sdfgjkljljoftrytrszgijpokjprs.FileChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.FormatVersionChunk;

public class DSDChunkReader
extends FileChunkReader<DSDFileStructure, FormDSDChunk> {
    public DSDChunkReader() {
        super(DSDFileStructure.class, FormDSDChunk.class);
        this.DSP(ChannelsChunk.class);
        this.DSP(CompressionTypeChunk.class);
        this.DSP(FormatVersionChunk.class);
        this.DSP(PropertyChunk.class);
        this.DSP(SampleRateChunk.class);
        this.DSP(DSDSoundDataChunk.class);
        this.DSP(DSTSoundDataChunk.class);
    }
}

