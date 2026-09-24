/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sdfgjkljljoftrytrszgijpokjprs.UploadResponseModel;
import sdfgjkljljoftrytrszgijpokjprs.AlbumModel;
import sdfgjkljljoftrytrszgijpokjprs.AdditionalMetadataValue;
import sdfgjkljljoftrytrszgijpokjprs.IUpdatableState;

public class AlbumUploadItem {
    private final ArrayList<IUpdatableState> DSP;
    private UploadResponseModel FFT;
    private Path responseView;
    private AlbumModel AdditionalMetadataValue;
    private boolean AudioFileExtension;
    private boolean IAudioFileCodec;
    private AdditionalMetadataValue<Integer> IAudioInputStream;
    private AdditionalMetadataValue<Integer> IAudioMetaInformation;

    public AlbumUploadItem(AlbumModel fikLFfybOLBBPFzDSaJuDxU2) {
        this.AdditionalMetadataValue = fikLFfybOLBBPFzDSaJuDxU2;
        this.IAudioFileCodec = false;
        this.DSP = new ArrayList(0);
    }

    public AlbumModel DSP() {
        return this.AdditionalMetadataValue;
    }

    public synchronized boolean FFT() {
        return this.IAudioFileCodec;
    }

    public synchronized void DSP(boolean bl) {
        this.IAudioFileCodec = bl;
    }

    public UploadResponseModel responseView() {
        return this.FFT;
    }

    public void DSP(UploadResponseModel dXwHeIuXrPXwwYsOazDjvwg2) {
        this.FFT = dXwHeIuXrPXwwYsOazDjvwg2;
    }

    public Path AdditionalMetadataValue() {
        return this.responseView;
    }

    public void DSP(Path path) {
        this.responseView = path;
    }

    public synchronized boolean AudioFileExtension() {
        return this.AudioFileExtension;
    }

    public synchronized void FFT(boolean bl) {
        this.AudioFileExtension = bl;
    }

    public List<IUpdatableState> IAudioFileCodec() {
        return Collections.unmodifiableList(this.DSP);
    }

    public void DSP(IUpdatableState nszTqDkxYiaGpuKyqsPMpEP2) {
        if (!this.DSP.contains(nszTqDkxYiaGpuKyqsPMpEP2)) {
            this.DSP.add(nszTqDkxYiaGpuKyqsPMpEP2);
        }
    }

    void DSP(AdditionalMetadataValue<Integer> iwitQCGCnkthNoQBBlYaPUM2) {
        this.IAudioMetaInformation = iwitQCGCnkthNoQBBlYaPUM2;
    }

    public AdditionalMetadataValue<Integer> IAudioInputStream() {
        return this.IAudioInputStream;
    }

    void FFT(AdditionalMetadataValue<Integer> iwitQCGCnkthNoQBBlYaPUM2) {
        this.IAudioInputStream = iwitQCGCnkthNoQBBlYaPUM2;
    }
}

