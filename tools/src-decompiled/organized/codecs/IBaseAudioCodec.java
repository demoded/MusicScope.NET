/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;

public interface IBaseAudioCodec {
    public int DSP(byte[] var1, int var2, int var3);

    public IAudioMetaInformation FFT();

    public IAudioMetaInformation responseView();

    public void AdditionalMetadataValue();
}

