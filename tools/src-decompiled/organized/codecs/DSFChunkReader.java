/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.DSFFileStructure;
import sdfgjkljljoftrytrszgijpokjprs.DATAChunkHeader;
import sdfgjkljljoftrytrszgijpokjprs.MetadataHeaderChunk;
import sdfgjkljljoftrytrszgijpokjprs.FileChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.DSDChunkHeader;
import sdfgjkljljoftrytrszgijpokjprs.FMTChunkHeader;

public class DSFChunkReader
extends FileChunkReader<DSFFileStructure, DSDChunkHeader> {
    public DSFChunkReader() {
        super(DSFFileStructure.class, DSDChunkHeader.class);
        this.DSP(DATAChunkHeader.class);
        this.DSP(FMTChunkHeader.class);
        this.DSP(MetadataHeaderChunk.class);
    }
}

