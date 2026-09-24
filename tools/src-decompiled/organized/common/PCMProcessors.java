/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.HashSet;
import sdfgjkljljoftrytrszgijpokjprs.PCMProcessor;
import sdfgjkljljoftrytrszgijpokjprs.ByteData;
import sdfgjkljljoftrytrszgijpokjprs.StreamInfo;

class PCMProcessors
implements PCMProcessor {
    private final HashSet DSP = new HashSet();

    PCMProcessors() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void DSP(PCMProcessor exyuhjCFdYRxawtXrlsOAtI) {
        HashSet hashSet = this.DSP;
        synchronized (hashSet) {
            this.DSP.add(exyuhjCFdYRxawtXrlsOAtI);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void DSP(StreamInfo kTkvbLlxuYGcvSGyJtmnFsX) {
        HashSet hashSet = this.DSP;
        synchronized (hashSet) {
            for (PCMProcessor exyuhjCFdYRxawtXrlsOAtI : this.DSP) {
                exyuhjCFdYRxawtXrlsOAtI.DSP(kTkvbLlxuYGcvSGyJtmnFsX);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void DSP(ByteData jBphLoDiqESKYDzcdAUENWI) {
        HashSet hashSet = this.DSP;
        synchronized (hashSet) {
            for (PCMProcessor exyuhjCFdYRxawtXrlsOAtI : this.DSP) {
                exyuhjCFdYRxawtXrlsOAtI.DSP(jBphLoDiqESKYDzcdAUENWI);
            }
        }
    }

    public boolean DSP() {
        return this.DSP.size() == 0;
    }
}

