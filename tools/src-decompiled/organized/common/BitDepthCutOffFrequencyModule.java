/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.Arrays;
import sdfgjkljljoftrytrszgijpokjprs.ComputationSubject;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.BitDepthCutOffFrequencyModel;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerEventListener;
import sdfgjkljljoftrytrszgijpokjprs.AbstractComputationModule;
import sdfgjkljljoftrytrszgijpokjprs.ComputationObjects;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;
import sdfgjkljljoftrytrszgijpokjprs.FrequencyRangeCheck;
import sdfgjkljljoftrytrszgijpokjprs.ILevelMeterControlMidSideSwitch;
import sdfgjkljljoftrytrszgijpokjprs.BitDepthCheck;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;
import sdfgjkljljoftrytrszgijpokjprs.IValueReporting;

@ComputationSubject(DSP=ComputationController.DSP.BitDepth)
public class BitDepthCutOffFrequencyModule
extends AbstractComputationModule<BitDepthCutOffFrequencyModel>
implements IPlayerStateListener,
IPlayerEventListener,
ILevelMeterControlMidSideSwitch,
IValueReporting<BitDepthCutOffFrequencyModel> {
    private final double[] DSP;
    private final double[] FFT;
    private final BitDepthCheck responseView = new BitDepthCheck();
    private final FrequencyRangeCheck AdditionalMetadataValue = new FrequencyRangeCheck();
    private final ComputationObjects AudioFileExtension;
    private int IAudioFileCodec;
    private int IAudioInputStream;
    private int IAudioMetaInformation;
    private int IBaseAudioCodec;
    private BitDepthCutOffFrequencyModel MetaInfomationCopy;
    private boolean AacAudioCodec = false;

    public BitDepthCutOffFrequencyModule() {
        this.DSP = new double[20000];
        this.FFT = new double[20000];
        this.AudioFileExtension = this.DSP();
        this.IAudioFileCodec();
    }

    public void DSP(boolean bl) {
        this.AacAudioCodec = bl;
    }

    private synchronized void IAudioFileCodec() {
        IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA = this.AudioFileExtension.FFT();
        this.IAudioMetaInformation = yGjBevanihqaxYKnUNtrNeA.DSP();
        this.IBaseAudioCodec = yGjBevanihqaxYKnUNtrNeA.FFT().DSP();
        this.IAudioInputStream = 0;
        this.IAudioFileCodec = 0;
        Arrays.fill(this.DSP, 0.0);
        Arrays.fill(this.FFT, 0.0);
        this.responseView.DSP(this.IAudioMetaInformation, this.IBaseAudioCodec);
        this.AdditionalMetadataValue.DSP(this.IAudioMetaInformation, this.IBaseAudioCodec);
    }

    public BitDepthCutOffFrequencyModel responseView() throws Exception {
        Thread.currentThread().setName(this.getClass().getSimpleName());
        AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf = this.AudioFileExtension.responseView();
        int n = zvOTVUaKFTNpNSbbMYZfoXf.responseView().length;
        if (this.AacAudioCodec) {
            int n2;
            if (this.IBaseAudioCodec > 16) {
                for (n2 = 0; n2 < n; ++n2) {
                    this.DSP[this.IAudioFileCodec] = zvOTVUaKFTNpNSbbMYZfoXf.responseView()[n2];
                    ++this.IAudioFileCodec;
                    if (this.IAudioFileCodec != 16384) continue;
                    this.responseView.DSP(this.DSP);
                    this.IAudioFileCodec = 0;
                }
            }
            for (n2 = 0; n2 < n; ++n2) {
                this.FFT[this.IAudioInputStream] = zvOTVUaKFTNpNSbbMYZfoXf.responseView()[n2];
                ++this.IAudioInputStream;
                if (this.IAudioInputStream != 512) continue;
                this.AdditionalMetadataValue.DSP(this.FFT);
                this.IAudioInputStream = 0;
            }
            this.MetaInfomationCopy = new BitDepthCutOffFrequencyModel(this.responseView.DSP(), this.AdditionalMetadataValue.DSP(), this.AacAudioCodec);
        } else {
            this.MetaInfomationCopy = new BitDepthCutOffFrequencyModel(0, 0, this.AacAudioCodec);
        }
        return this.MetaInfomationCopy;
    }

    public BitDepthCutOffFrequencyModel AdditionalMetadataValue() {
        return this.MetaInfomationCopy;
    }

    @Override
    public void DSP(PlayerState zjoyaRSokkGYDwXHPKTBIiX) {
        switch (zjoyaRSokkGYDwXHPKTBIiX) {
            case AdditionalMetadataValue: {
                this.IAudioFileCodec();
            }
        }
    }

    @Override
    public boolean DSP(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2, Object object) {
        switch (lXCOGwZXVelwEKHMMPwpSAO2) {
            case DSP: {
                this.IAudioFileCodec();
                break;
            }
        }
        return true;
    }

    @Override
    public void FFT(boolean bl) {
    }

    @Override
    public /* synthetic */ Object FFT() throws Exception {
        return this.responseView();
    }

    @Override
    public /* synthetic */ Object AudioFileExtension() {
        return this.AdditionalMetadataValue();
    }
}

