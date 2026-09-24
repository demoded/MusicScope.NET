/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import sdfgjkljljoftrytrszgijpokjprs.OutOfChunkRangeException;
import sdfgjkljljoftrytrszgijpokjprs.ChunkAddedInfo;
import sdfgjkljljoftrytrszgijpokjprs.IRootChunkStructure;
import sdfgjkljljoftrytrszgijpokjprs.IChainedPropertyChunk;
import sdfgjkljljoftrytrszgijpokjprs.IChunkAvailableCallback;
import sdfgjkljljoftrytrszgijpokjprs.IRootChunkLoadedCallback;
import sdfgjkljljoftrytrszgijpokjprs.ChunkReader;
import sdfgjkljljoftrytrszgijpokjprs.ILocalPropertyChunk;
import sdfgjkljljoftrytrszgijpokjprs.MultipleInstancesAnnotation;
import sdfgjkljljoftrytrszgijpokjprs.RootChunkException;
import sdfgjkljljoftrytrszgijpokjprs.ChunkRequiredAnnotation;
import sdfgjkljljoftrytrszgijpokjprs.IChunkErrorCallback;
import sdfgjkljljoftrytrszgijpokjprs.ChunkIDAnnotation;

public class FileChunkReader<F extends IRootChunkStructure, R extends F> {
    private final Class<R> DSP;
    private final Class<F> FFT;
    private final LinkedList<F> responseView;
    private final HashMap<String, Class<? extends F>> AdditionalMetadataValue;
    private IChunkErrorCallback AudioFileExtension;
    private IRootChunkLoadedCallback<R> IAudioFileCodec;

    public FileChunkReader(Class<F> clazz, Class<R> clazz2) {
        this.FFT = clazz;
        this.DSP = clazz2;
        this.responseView = new LinkedList();
        this.AdditionalMetadataValue = new HashMap(0);
    }

    public <C extends F> ChunkAddedInfo DSP(Class<C> clazz) {
        ChunkIDAnnotation tyOkjsTUxOpfPxqEnMYqtXo2 = clazz.getAnnotation(ChunkIDAnnotation.class);
        ChunkAddedInfo ibMszXSpiIkSRDMZNNMqOkq = new ChunkAddedInfo(false, false, null);
        if (tyOkjsTUxOpfPxqEnMYqtXo2 != null) {
            Class<C> clazz2 = this.AdditionalMetadataValue.put(tyOkjsTUxOpfPxqEnMYqtXo2.DSP(), clazz);
            ibMszXSpiIkSRDMZNNMqOkq.DSP(true);
            ibMszXSpiIkSRDMZNNMqOkq.FFT(clazz2 != null);
            ibMszXSpiIkSRDMZNNMqOkq.DSP(clazz2);
        }
        return ibMszXSpiIkSRDMZNNMqOkq;
    }

    public Class<? extends F> DSP(String string) {
        return this.AdditionalMetadataValue.get(string);
    }

    public F FFT(String string) {
        for (IRootChunkStructure lMMqFwohHquNkNfhbpHHdcV : this.responseView) {
            String string2 = this.responseView(lMMqFwohHquNkNfhbpHHdcV.getClass());
            if (!string2.equals(string)) continue;
            return (F)lMMqFwohHquNkNfhbpHHdcV;
        }
        return null;
    }

    public <C extends F> C FFT(Class<C> clazz) {
        return (C)this.FFT(this.responseView(clazz));
    }

    public void DSP(File file) throws IOException, RootChunkException {
        this.DSP(file, (IChunkErrorCallback)null);
    }

    public void DSP(File file, IChunkErrorCallback jyvhBhJUCEBlAMBGMFTgNEy2) throws IOException, RootChunkException {
        this.DSP(file, 0L, jyvhBhJUCEBlAMBGMFTgNEy2, null);
    }

    public void DSP(File file, long l, IChunkErrorCallback jyvhBhJUCEBlAMBGMFTgNEy2, IChunkAvailableCallback<F> nVxuTncGQFCJJeNojiRLcml) throws IOException, RootChunkException {
        this.responseView.clear();
        ChunkReader rBEDoDrXEedwSjzkgSRdKqV = new ChunkReader(file);
        rBEDoDrXEedwSjzkgSRdKqV.DSP(l);
        this.AudioFileExtension = jyvhBhJUCEBlAMBGMFTgNEy2;
        R r = this.DSP(rBEDoDrXEedwSjzkgSRdKqV);
        if (r != null && r.IAudioFileCodec()) {
            this.DSP(r, nVxuTncGQFCJJeNojiRLcml);
            if (this.IAudioFileCodec != null) {
                this.IAudioFileCodec.DSP(r);
            }
        } else {
            throw new RootChunkException(this.FFT, "Requested chunk was null or not valid.");
        }
        if (this.DSP(r) || this.FFT(r)) {
            this.DSP(r, rBEDoDrXEedwSjzkgSRdKqV, nVxuTncGQFCJJeNojiRLcml);
        }
        rBEDoDrXEedwSjzkgSRdKqV.IAudioFileCodec();
    }

    private R DSP(ChunkReader rBEDoDrXEedwSjzkgSRdKqV) {
        try {
            IRootChunkStructure lMMqFwohHquNkNfhbpHHdcV = (IRootChunkStructure)this.DSP.newInstance();
            lMMqFwohHquNkNfhbpHHdcV.DSP(rBEDoDrXEedwSjzkgSRdKqV);
            return (R)lMMqFwohHquNkNfhbpHHdcV;
        }
        catch (IllegalAccessException | InstantiationException | OutOfChunkRangeException exception) {
            if (this.AudioFileExtension != null) {
                this.AudioFileExtension.DSP(exception);
            }
            return null;
        }
    }

    private void DSP(F f, ChunkReader rBEDoDrXEedwSjzkgSRdKqV, IChunkAvailableCallback<F> nVxuTncGQFCJJeNojiRLcml) {
        if (this.DSP(f)) {
            rBEDoDrXEedwSjzkgSRdKqV.DSP(f.AudioFileExtension());
        } else {
            rBEDoDrXEedwSjzkgSRdKqV.DSP(f.AdditionalMetadataValue() + f.responseView());
        }
        while (rBEDoDrXEedwSjzkgSRdKqV.responseView()) {
            try {
                IRootChunkStructure lMMqFwohHquNkNfhbpHHdcV = (IRootChunkStructure)this.FFT.newInstance();
                lMMqFwohHquNkNfhbpHHdcV.DSP(rBEDoDrXEedwSjzkgSRdKqV);
                F f2 = this.DSP(lMMqFwohHquNkNfhbpHHdcV, rBEDoDrXEedwSjzkgSRdKqV);
                this.DSP(f2, nVxuTncGQFCJJeNojiRLcml);
                if (!this.DSP(f2) && !this.FFT(f2)) continue;
                ChunkReader rBEDoDrXEedwSjzkgSRdKqV2 = this.FFT((IRootChunkStructure)f2, rBEDoDrXEedwSjzkgSRdKqV);
                this.DSP(f2, rBEDoDrXEedwSjzkgSRdKqV2, nVxuTncGQFCJJeNojiRLcml);
            }
            catch (IllegalAccessException | InstantiationException | OutOfChunkRangeException exception) {
                if (this.AudioFileExtension == null) continue;
                this.AudioFileExtension.DSP(exception);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private F DSP(IRootChunkStructure lMMqFwohHquNkNfhbpHHdcV, ChunkReader rBEDoDrXEedwSjzkgSRdKqV) {
        long l;
        IRootChunkStructure lMMqFwohHquNkNfhbpHHdcV2 = null;
        String string = lMMqFwohHquNkNfhbpHHdcV.FFT();
        try {
            Class<F> clazz;
            if (string != null && !string.isEmpty() && (clazz = this.DSP(string)) != null) {
                lMMqFwohHquNkNfhbpHHdcV2 = (IRootChunkStructure)clazz.newInstance();
                ChunkReader rBEDoDrXEedwSjzkgSRdKqV2 = this.FFT(lMMqFwohHquNkNfhbpHHdcV, rBEDoDrXEedwSjzkgSRdKqV);
                lMMqFwohHquNkNfhbpHHdcV2.DSP(rBEDoDrXEedwSjzkgSRdKqV2);
            }
            l = lMMqFwohHquNkNfhbpHHdcV.responseView() % 2L == 0L ? lMMqFwohHquNkNfhbpHHdcV.responseView() : lMMqFwohHquNkNfhbpHHdcV.responseView() + 1L;
        }
        catch (IllegalAccessException | InstantiationException | OutOfChunkRangeException exception) {
            long l2;
            try {
                if (this.AudioFileExtension != null) {
                    this.AudioFileExtension.DSP(exception);
                }
                l2 = lMMqFwohHquNkNfhbpHHdcV.responseView() % 2L == 0L ? lMMqFwohHquNkNfhbpHHdcV.responseView() : lMMqFwohHquNkNfhbpHHdcV.responseView() + 1L;
            }
            catch (Throwable throwable) {
                long l3 = lMMqFwohHquNkNfhbpHHdcV.responseView() % 2L == 0L ? lMMqFwohHquNkNfhbpHHdcV.responseView() : lMMqFwohHquNkNfhbpHHdcV.responseView() + 1L;
                rBEDoDrXEedwSjzkgSRdKqV.DSP(lMMqFwohHquNkNfhbpHHdcV.AdditionalMetadataValue() + l3);
                throw throwable;
            }
            rBEDoDrXEedwSjzkgSRdKqV.DSP(lMMqFwohHquNkNfhbpHHdcV.AdditionalMetadataValue() + l2);
        }
        rBEDoDrXEedwSjzkgSRdKqV.DSP(lMMqFwohHquNkNfhbpHHdcV.AdditionalMetadataValue() + l);
        return (F)lMMqFwohHquNkNfhbpHHdcV2;
    }

    private void DSP(F f, IChunkAvailableCallback<F> nVxuTncGQFCJJeNojiRLcml) {
        if (f != null && this.responseView(f.getClass()) != null && (this.FFT(this.responseView(f.getClass())) == null || this.AdditionalMetadataValue(f.getClass()))) {
            if (nVxuTncGQFCJJeNojiRLcml == null) {
                this.responseView.addLast(f);
            } else {
                nVxuTncGQFCJJeNojiRLcml.DSP(f);
            }
        }
    }

    private ChunkReader FFT(IRootChunkStructure lMMqFwohHquNkNfhbpHHdcV, ChunkReader rBEDoDrXEedwSjzkgSRdKqV) throws OutOfChunkRangeException {
        rBEDoDrXEedwSjzkgSRdKqV.DSP(lMMqFwohHquNkNfhbpHHdcV.AdditionalMetadataValue());
        return new ChunkReader(rBEDoDrXEedwSjzkgSRdKqV, lMMqFwohHquNkNfhbpHHdcV.responseView());
    }

    private String responseView(Class<?> clazz) {
        ChunkIDAnnotation tyOkjsTUxOpfPxqEnMYqtXo2 = clazz.getAnnotation(ChunkIDAnnotation.class);
        if (tyOkjsTUxOpfPxqEnMYqtXo2 != null) {
            return tyOkjsTUxOpfPxqEnMYqtXo2.DSP();
        }
        return null;
    }

    private boolean AdditionalMetadataValue(Class<?> clazz) {
        MultipleInstancesAnnotation sjljqMstYuKgmMVZLvWQxBn = clazz.getAnnotation(MultipleInstancesAnnotation.class);
        if (sjljqMstYuKgmMVZLvWQxBn != null) {
            return sjljqMstYuKgmMVZLvWQxBn.DSP();
        }
        return false;
    }

    private boolean DSP(F f) {
        if (f != null && ILocalPropertyChunk.class.isInstance(f)) {
            ILocalPropertyChunk rHinMirXtRhDCCloYonXKGl = (ILocalPropertyChunk)f;
            return rHinMirXtRhDCCloYonXKGl.DSP();
        }
        return false;
    }

    private boolean FFT(F f) {
        if (f != null && IChainedPropertyChunk.class.isInstance(f)) {
            IChainedPropertyChunk nHqdoLcnZjfCWlzkBTJyvNW = (IChainedPropertyChunk)f;
            return nHqdoLcnZjfCWlzkBTJyvNW.DSP();
        }
        return false;
    }

    private boolean AudioFileExtension(Class<?> clazz) {
        ChunkRequiredAnnotation yRJGfWcyJJQdwqOWeghJzoA = clazz.getAnnotation(ChunkRequiredAnnotation.class);
        return yRJGfWcyJJQdwqOWeghJzoA != null && yRJGfWcyJJQdwqOWeghJzoA.DSP() && !yRJGfWcyJJQdwqOWeghJzoA.responseView().isEmpty() && this.FFT(yRJGfWcyJJQdwqOWeghJzoA.responseView()) != null;
    }

    private boolean IAudioFileCodec(Class<?> clazz) {
        ChunkRequiredAnnotation yRJGfWcyJJQdwqOWeghJzoA = clazz.getAnnotation(ChunkRequiredAnnotation.class);
        for (String string : yRJGfWcyJJQdwqOWeghJzoA.FFT()) {
            F f = this.FFT(string);
            if (f != null && f.IAudioFileCodec()) continue;
            return true;
        }
        return false;
    }

    public boolean DSP() {
        for (Class<? extends F> clazz : this.AdditionalMetadataValue.values()) {
            F f;
            if (!this.AudioFileExtension(clazz) || (f = this.FFT(clazz)) != null && f.IAudioFileCodec() || this.IAudioFileCodec(clazz)) continue;
            return false;
        }
        return true;
    }
}

