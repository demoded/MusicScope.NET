/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.LinkedList;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;

public enum PlayerState {
    DSP,
    FFT,
    responseView,
    AdditionalMetadataValue,
    AudioFileExtension;

    private LinkedList<IPlayerStateListener> IAudioFileCodec = new LinkedList();

    synchronized void DSP(IPlayerStateListener hAfXhvYubJkskjSMlFLLdgY) {
        if (this.IAudioFileCodec != null && !this.IAudioFileCodec.contains(hAfXhvYubJkskjSMlFLLdgY)) {
            this.IAudioFileCodec.add(hAfXhvYubJkskjSMlFLLdgY);
        }
    }

    synchronized void FFT(IPlayerStateListener hAfXhvYubJkskjSMlFLLdgY) {
        if (this.IAudioFileCodec != null) {
            this.IAudioFileCodec.remove(hAfXhvYubJkskjSMlFLLdgY);
        }
    }

    synchronized void DSP(PlayerState zjoyaRSokkGYDwXHPKTBIiX) {
        if (this.IAudioFileCodec != null) {
            for (IPlayerStateListener hAfXhvYubJkskjSMlFLLdgY : this.IAudioFileCodec) {
                try {
                    hAfXhvYubJkskjSMlFLLdgY.DSP(zjoyaRSokkGYDwXHPKTBIiX);
                }
                catch (Exception exception) {}
            }
        }
    }
}

