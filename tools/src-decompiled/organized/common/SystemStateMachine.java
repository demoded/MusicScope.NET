/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.EnumSet;
import java.util.HashMap;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerEventListener;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;

public class SystemStateMachine {
    private static SystemStateMachine DSP = new SystemStateMachine();
    private final HashMap<PlayerState, EnumSet<PlayerEvent>> FFT = new HashMap();
    private PlayerState responseView;

    private SystemStateMachine() {
        this.FFT.put(PlayerState.DSP, EnumSet.of(PlayerEvent.DSP, PlayerEvent.AudioFileExtension));
        this.FFT.put(PlayerState.AdditionalMetadataValue, EnumSet.of(PlayerEvent.DSP, PlayerEvent.AudioFileExtension, PlayerEvent.FFT, PlayerEvent.IAudioInputStream, PlayerEvent.IAudioMetaInformation));
        this.FFT.put(PlayerState.responseView, EnumSet.of(PlayerEvent.DSP, new PlayerEvent[]{PlayerEvent.AudioFileExtension, PlayerEvent.IAudioMetaInformation, PlayerEvent.AdditionalMetadataValue, PlayerEvent.responseView, PlayerEvent.FFT, PlayerEvent.IAudioFileCodec}));
        this.FFT.put(PlayerState.FFT, EnumSet.of(PlayerEvent.DSP, new PlayerEvent[]{PlayerEvent.AudioFileExtension, PlayerEvent.IAudioInputStream, PlayerEvent.AdditionalMetadataValue, PlayerEvent.responseView, PlayerEvent.FFT, PlayerEvent.IAudioFileCodec}));
        this.FFT.put(PlayerState.AudioFileExtension, EnumSet.of(PlayerEvent.DSP, new PlayerEvent[]{PlayerEvent.AudioFileExtension, PlayerEvent.IAudioInputStream, PlayerEvent.IAudioMetaInformation, PlayerEvent.responseView, PlayerEvent.FFT, PlayerEvent.IAudioFileCodec}));
        this.responseView = PlayerState.DSP;
    }

    public static SystemStateMachine DSP() {
        return DSP;
    }

    public void DSP(IPlayerEventListener kXzLGKHeRupGOUCLSJARWrw, PlayerEvent ... lXCOGwZXVelwEKHMMPwpSAOArray) {
        for (PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2 : lXCOGwZXVelwEKHMMPwpSAOArray) {
            lXCOGwZXVelwEKHMMPwpSAO2.DSP(kXzLGKHeRupGOUCLSJARWrw);
        }
    }

    public void DSP(IPlayerStateListener hAfXhvYubJkskjSMlFLLdgY, PlayerState ... zjoyaRSokkGYDwXHPKTBIiXArray) {
        for (PlayerState zjoyaRSokkGYDwXHPKTBIiX : zjoyaRSokkGYDwXHPKTBIiXArray) {
            zjoyaRSokkGYDwXHPKTBIiX.DSP(hAfXhvYubJkskjSMlFLLdgY);
        }
    }

    public void FFT(IPlayerStateListener hAfXhvYubJkskjSMlFLLdgY, PlayerState ... zjoyaRSokkGYDwXHPKTBIiXArray) {
        for (PlayerState zjoyaRSokkGYDwXHPKTBIiX : zjoyaRSokkGYDwXHPKTBIiXArray) {
            zjoyaRSokkGYDwXHPKTBIiX.FFT(hAfXhvYubJkskjSMlFLLdgY);
        }
    }

    public void DSP(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2) {
        this.DSP(lXCOGwZXVelwEKHMMPwpSAO2, null);
    }

    public void DSP(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2, Object object) {
        if (this.FFT(lXCOGwZXVelwEKHMMPwpSAO2) && lXCOGwZXVelwEKHMMPwpSAO2.DSP(lXCOGwZXVelwEKHMMPwpSAO2, object)) {
            this.responseView = lXCOGwZXVelwEKHMMPwpSAO2.DSP() != null ? lXCOGwZXVelwEKHMMPwpSAO2.DSP() : this.responseView;
            this.responseView.DSP(this.responseView);
        }
    }

    public boolean FFT(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2) {
        EnumSet<PlayerEvent> enumSet = this.FFT.get((Object)this.responseView);
        return enumSet.contains((Object)lXCOGwZXVelwEKHMMPwpSAO2);
    }

    public PlayerState FFT() {
        return this.responseView;
    }
}

