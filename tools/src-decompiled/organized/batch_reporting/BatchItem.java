/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.IUpdatedCallback;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.OverallValueModel;
import sdfgjkljljoftrytrszgijpokjprs.IUpdatableState;

public class BatchItem
implements IUpdatableState {
    private final String DSP;
    private final String FFT;
    private String responseView;
    private boolean AdditionalMetadataValue;
    private boolean AudioFileExtension;
    private boolean IAudioFileCodec;
    private boolean IAudioInputStream;
    private boolean IAudioMetaInformation;
    private OverallValueModel IBaseAudioCodec;
    private IAudioMetaInformation MetaInfomationCopy;
    private IUpdatedCallback AacAudioCodec;

    public BatchItem(String string, String string2, String string3, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        this.DSP = string;
        this.FFT = string2;
        this.responseView = string3;
        this.AdditionalMetadataValue = bl;
        this.AudioFileExtension = bl2;
        this.IAudioInputStream = bl4;
        this.IAudioFileCodec = bl3;
        this.IBaseAudioCodec = null;
    }

    public String DSP() {
        return this.DSP;
    }

    public String FFT() {
        return this.FFT;
    }

    public String responseView() {
        return this.responseView;
    }

    @Override
    public void DSP(String string) {
        this.responseView = string;
        if (this.AacAudioCodec != null) {
            this.AacAudioCodec.BufferedAacReader();
        }
    }

    public boolean AdditionalMetadataValue() {
        return this.AdditionalMetadataValue;
    }

    public void DSP(boolean bl) {
        this.AdditionalMetadataValue = bl;
        if (bl) {
            this.IBaseAudioCodec = null;
        }
    }

    public boolean AudioFileExtension() {
        return this.AudioFileExtension;
    }

    public void FFT(boolean bl) {
        this.AudioFileExtension = bl;
        if (bl) {
            this.IBaseAudioCodec = null;
        }
    }

    public IAudioMetaInformation IAudioFileCodec() {
        return this.MetaInfomationCopy;
    }

    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA) {
        this.MetaInfomationCopy = yGjBevanihqaxYKnUNtrNeA;
    }

    public Object DSP(int n) {
        switch (n) {
            case 0: {
                return this.FFT();
            }
            case 1: {
                return this.AdditionalMetadataValue();
            }
            case 2: {
                return this.AudioFileExtension();
            }
            case 3: {
                return this.responseView();
            }
        }
        return null;
    }

    public void DSP(Object object, int n) {
        switch (n) {
            case 1: {
                this.DSP((Boolean)object);
                break;
            }
            case 2: {
                this.FFT((Boolean)object);
                break;
            }
            case 3: {
                this.DSP((String)object);
            }
        }
    }

    public boolean IAudioInputStream() {
        return this.IAudioMetaInformation;
    }

    public void responseView(boolean bl) {
        this.IAudioMetaInformation = bl;
    }

    public OverallValueModel IAudioMetaInformation() {
        return this.IBaseAudioCodec;
    }

    public void DSP(OverallValueModel hLDzMMEJpHepjqnDHxBExAn2) {
        this.IBaseAudioCodec = hLDzMMEJpHepjqnDHxBExAn2;
    }

    public void DSP(IUpdatedCallback tDhalVHIgJpVEqwCgwFnQOQ) {
        this.AacAudioCodec = tDhalVHIgJpVEqwCgwFnQOQ;
    }
}

