/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.File;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.FileChunkReader;

public interface IMetadataReader {
    public FileChunkReader<?, ?> DSP(File var1);

    public boolean FFT(File var1);

    public IAudioMetaInformation responseView(File var1);
}

