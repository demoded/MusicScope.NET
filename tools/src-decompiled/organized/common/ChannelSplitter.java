/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import sdfgjkljljoftrytrszgijpokjprs.SimpleByteBuffer;
import sdfgjkljljoftrytrszgijpokjprs.ThreadState;
import sdfgjkljljoftrytrszgijpokjprs.EmptySimpleByteBuffer;
import sdfgjkljljoftrytrszgijpokjprs.AbstractManagedWorker;
import sdfgjkljljoftrytrszgijpokjprs.IReadableChannelBuffer;
import sdfgjkljljoftrytrszgijpokjprs.IRawInputReader;

public class ChannelSplitter
extends AbstractManagedWorker
implements IReadableChannelBuffer {
    private final int AdditionalMetadataValue;
    private final int AudioFileExtension;
    private final int IAudioFileCodec;
    private final int IAudioInputStream;
    private final IRawInputReader IAudioMetaInformation;
    private byte[] IBaseAudioCodec;
    private HashMap<Integer, SimpleByteBuffer> MetaInfomationCopy;
    private HashMap<Integer, ArrayBlockingQueue<SimpleByteBuffer>> AacAudioCodec;

    public ChannelSplitter(int n, int n2, IRawInputReader yKwRggLaMfgwMIUFQoKHBce2) {
        this(n, 10, n2, yKwRggLaMfgwMIUFQoKHBce2);
    }

    public ChannelSplitter(int n, int n2, int n3, IRawInputReader yKwRggLaMfgwMIUFQoKHBce2) {
        this.AdditionalMetadataValue = n;
        this.IAudioMetaInformation = yKwRggLaMfgwMIUFQoKHBce2;
        this.AudioFileExtension = n2;
        this.IAudioInputStream = n3;
        this.IAudioFileCodec = 4096;
        this.MetaInfomationCopy = new HashMap(0);
        this.AacAudioCodec = new HashMap(0);
        this.IBaseAudioCodec = new byte[this.IAudioFileCodec * n];
        for (int i = 0; i < n; ++i) {
            this.AacAudioCodec.put(i, new ArrayBlockingQueue(n2));
        }
        this.DSP("Channel Splitter");
    }

    @Override
    public int DSP(int n, byte[] byArray, int n2, int n3) {
        int n4 = 0;
        if (this.AacAudioCodec.get(n).peek() instanceof EmptySimpleByteBuffer) {
            return -1;
        }
        int n5 = n2;
        while (n5 < n3 && this.DSP() == ThreadState.responseView) {
            SimpleByteBuffer abkRNvAOeWIbEeMzsGvXdIr;
            if (this.MetaInfomationCopy.get(n) == null || !this.MetaInfomationCopy.get(n).DSP()) {
                this.MetaInfomationCopy.put(n, this.AacAudioCodec.get(n).poll());
            }
            if ((abkRNvAOeWIbEeMzsGvXdIr = this.MetaInfomationCopy.get(n)) == null && this.DSP() == ThreadState.responseView) continue;
            if (abkRNvAOeWIbEeMzsGvXdIr instanceof EmptySimpleByteBuffer || this.DSP() != ThreadState.responseView) break;
            int n6 = abkRNvAOeWIbEeMzsGvXdIr.FFT(byArray, n5, n3 - n5);
            n5 += n6;
            n4 += n6;
        }
        return n4;
    }

    private void DSP(int n, SimpleByteBuffer abkRNvAOeWIbEeMzsGvXdIr) {
        try {
            this.AacAudioCodec.get(n).put(abkRNvAOeWIbEeMzsGvXdIr);
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void FFT() {
        block5: {
            int n;
            block6: {
                int n2;
                if (this.IAudioMetaInformation == null) break block5;
                ArrayList<SimpleByteBuffer> arrayList = new ArrayList<SimpleByteBuffer>(this.AdditionalMetadataValue);
                for (n = 0; n < this.AdditionalMetadataValue; ++n) {
                    arrayList.add(new SimpleByteBuffer(this.IAudioFileCodec));
                }
                n = this.IAudioMetaInformation.DSP(this.IBaseAudioCodec);
                if (n <= 0) break block6;
                for (n2 = 0; n2 < n; n2 += this.IAudioInputStream * this.AdditionalMetadataValue) {
                    for (int i = 0; i < this.AdditionalMetadataValue; ++i) {
                        ((SimpleByteBuffer)arrayList.get(i)).DSP(this.IBaseAudioCodec, i * this.IAudioInputStream + n2, this.IAudioInputStream);
                    }
                }
                for (n2 = 0; n2 < this.AdditionalMetadataValue; ++n2) {
                    this.DSP(n2, (SimpleByteBuffer)arrayList.get(n2));
                }
                break block5;
            }
            if (n != -1) break block5;
            for (int i = 0; i < this.AdditionalMetadataValue; ++i) {
                this.DSP(i, new EmptySimpleByteBuffer());
            }
        }
    }
}

