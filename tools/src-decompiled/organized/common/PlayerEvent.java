/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.ArrayList;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerEventListener;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;

public enum PlayerEvent {
    DSP(PlayerState.AdditionalMetadataValue),
    FFT(PlayerState.DSP),
    responseView(PlayerState.AdditionalMetadataValue),
    AdditionalMetadataValue(PlayerState.AudioFileExtension),
    AudioFileExtension(null),
    IAudioFileCodec(PlayerState.AdditionalMetadataValue),
    IAudioInputStream(PlayerState.responseView),
    IAudioMetaInformation(PlayerState.FFT);

    private final PlayerState IBaseAudioCodec;
    private final ArrayList<IPlayerEventListener> MetaInfomationCopy;

    private PlayerEvent(PlayerState zjoyaRSokkGYDwXHPKTBIiX) {
        this.IBaseAudioCodec = zjoyaRSokkGYDwXHPKTBIiX;
        this.MetaInfomationCopy = new ArrayList(0);
    }

    public PlayerState DSP() {
        return this.IBaseAudioCodec;
    }

    void DSP(IPlayerEventListener kXzLGKHeRupGOUCLSJARWrw) {
        if (this.MetaInfomationCopy != null && !this.MetaInfomationCopy.contains(kXzLGKHeRupGOUCLSJARWrw)) {
            this.MetaInfomationCopy.add(kXzLGKHeRupGOUCLSJARWrw);
        }
    }

    boolean DSP(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2, Object object) {
        boolean bl = true;
        if (this.MetaInfomationCopy != null) {
            for (IPlayerEventListener kXzLGKHeRupGOUCLSJARWrw : this.MetaInfomationCopy) {
                try {
                    bl = bl && kXzLGKHeRupGOUCLSJARWrw.DSP(lXCOGwZXVelwEKHMMPwpSAO2, object);
                }
                catch (Exception exception) {}
            }
        }
        return bl;
    }
}

