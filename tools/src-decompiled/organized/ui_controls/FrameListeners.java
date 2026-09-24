/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.HashSet;
import sdfgjkljljoftrytrszgijpokjprs.Frame;
import sdfgjkljljoftrytrszgijpokjprs.Metadata;
import sdfgjkljljoftrytrszgijpokjprs.FrameListener;

class FrameListeners
implements FrameListener {
    private HashSet DSP = new HashSet();

    FrameListeners() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void DSP(Metadata eJnHMGFAPyJBjoMvbKedmZt2) {
        HashSet hashSet = this.DSP;
        synchronized (hashSet) {
            for (FrameListener fSbqrYweWgIzzPDikmuCWlG2 : this.DSP) {
                fSbqrYweWgIzzPDikmuCWlG2.DSP(eJnHMGFAPyJBjoMvbKedmZt2);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void DSP(Frame sptUCyYjbKJFZiSwGHPpFmZ) {
        HashSet hashSet = this.DSP;
        synchronized (hashSet) {
            for (FrameListener fSbqrYweWgIzzPDikmuCWlG2 : this.DSP) {
                fSbqrYweWgIzzPDikmuCWlG2.DSP(sptUCyYjbKJFZiSwGHPpFmZ);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void DSP(String string) {
        HashSet hashSet = this.DSP;
        synchronized (hashSet) {
            for (FrameListener fSbqrYweWgIzzPDikmuCWlG2 : this.DSP) {
                fSbqrYweWgIzzPDikmuCWlG2.DSP(string);
            }
        }
    }
}

