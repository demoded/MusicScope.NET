/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Vector;
import sdfgjkljljoftrytrszgijpokjprs.ChannelData;
import sdfgjkljljoftrytrszgijpokjprs.PCMProcessor;
import sdfgjkljljoftrytrszgijpokjprs.ByteData;
import sdfgjkljljoftrytrszgijpokjprs.StreamInfo;
import sdfgjkljljoftrytrszgijpokjprs.FrameDecodeException;
import sdfgjkljljoftrytrszgijpokjprs.ChannelVerbatim;
import sdfgjkljljoftrytrszgijpokjprs.CueSheet;
import sdfgjkljljoftrytrszgijpokjprs.VorbisComment;
import sdfgjkljljoftrytrszgijpokjprs.Unknown;
import sdfgjkljljoftrytrszgijpokjprs.Frame;
import sdfgjkljljoftrytrszgijpokjprs.Constants;
import sdfgjkljljoftrytrszgijpokjprs.ChannelFixed;
import sdfgjkljljoftrytrszgijpokjprs.Application;
import sdfgjkljljoftrytrszgijpokjprs.CRC16;
import sdfgjkljljoftrytrszgijpokjprs.SeekTable;
import sdfgjkljljoftrytrszgijpokjprs.Metadata;
import sdfgjkljljoftrytrszgijpokjprs.PCMProcessors;
import sdfgjkljljoftrytrszgijpokjprs.Padding;
import sdfgjkljljoftrytrszgijpokjprs.BadHeaderException;
import sdfgjkljljoftrytrszgijpokjprs.BitInputStream;
import sdfgjkljljoftrytrszgijpokjprs.FrameListeners;
import sdfgjkljljoftrytrszgijpokjprs.Header;
import sdfgjkljljoftrytrszgijpokjprs.Picture;
import sdfgjkljljoftrytrszgijpokjprs.ChannelLPC;
import sdfgjkljljoftrytrszgijpokjprs.ChannelConstant;

public class FLACDecoder {
    private static final byte[] DSP = new byte[]{73, 68, 51};
    private BitInputStream FFT;
    private final ChannelData[] responseView = new ChannelData[8];
    private int AdditionalMetadataValue;
    private int AudioFileExtension;
    private long IAudioFileCodec;
    private StreamInfo IAudioInputStream;
    private Frame IAudioMetaInformation = new Frame();
    private final byte[] IBaseAudioCodec = new byte[2];
    private int MetaInfomationCopy;
    private int AacAudioCodec;
    private int AacMetaDataModel;
    private int BufferedAacReader;
    private int AiffAudioCodec;
    private final InputStream AiffMetaDataModel;
    private int AlacAudioCodec;
    private int AlacMetaDataModel;
    private boolean BufferedAlacReader = false;
    private FrameListeners AlacContextModel = new FrameListeners();
    private PCMProcessors AlacDecoderUtils = new PCMProcessors();

    public FLACDecoder(InputStream inputStream) {
        this.AiffMetaDataModel = inputStream;
        this.FFT = new BitInputStream(inputStream);
        this.IAudioFileCodec = 0L;
    }

    public void DSP(PCMProcessor exyuhjCFdYRxawtXrlsOAtI) {
        this.AlacDecoderUtils.DSP(exyuhjCFdYRxawtXrlsOAtI);
    }

    private boolean DSP(Frame sptUCyYjbKJFZiSwGHPpFmZ) {
        ByteData jBphLoDiqESKYDzcdAUENWI = this.DSP(sptUCyYjbKJFZiSwGHPpFmZ, null);
        this.AlacDecoderUtils.DSP(jBphLoDiqESKYDzcdAUENWI);
        return !this.AlacDecoderUtils.DSP();
    }

    public ByteData DSP(Frame sptUCyYjbKJFZiSwGHPpFmZ, ByteData jBphLoDiqESKYDzcdAUENWI) {
        block9: {
            block10: {
                block8: {
                    int n = sptUCyYjbKJFZiSwGHPpFmZ.DSP.DSP * this.MetaInfomationCopy * ((this.IAudioInputStream.IAudioInputStream() + 7) / 2);
                    if (jBphLoDiqESKYDzcdAUENWI == null || jBphLoDiqESKYDzcdAUENWI.DSP().length < n) {
                        jBphLoDiqESKYDzcdAUENWI = new ByteData(n);
                    } else {
                        jBphLoDiqESKYDzcdAUENWI.FFT(0);
                    }
                    if (this.IAudioInputStream.IAudioInputStream() != 8) break block8;
                    for (int i = 0; i < sptUCyYjbKJFZiSwGHPpFmZ.DSP.DSP; ++i) {
                        for (int j = 0; j < this.MetaInfomationCopy; ++j) {
                            jBphLoDiqESKYDzcdAUENWI.DSP((byte)(this.responseView[j].DSP()[i] + 128));
                        }
                    }
                    break block9;
                }
                if (this.IAudioInputStream.IAudioInputStream() != 16) break block10;
                for (int i = 0; i < sptUCyYjbKJFZiSwGHPpFmZ.DSP.DSP; ++i) {
                    for (int j = 0; j < this.MetaInfomationCopy; ++j) {
                        short s = (short)this.responseView[j].DSP()[i];
                        jBphLoDiqESKYDzcdAUENWI.DSP((byte)(s & 0xFF));
                        jBphLoDiqESKYDzcdAUENWI.DSP((byte)(s >> 8 & 0xFF));
                    }
                }
                break block9;
            }
            if (this.IAudioInputStream.IAudioInputStream() != 24) break block9;
            for (int i = 0; i < sptUCyYjbKJFZiSwGHPpFmZ.DSP.DSP; ++i) {
                for (int j = 0; j < this.MetaInfomationCopy; ++j) {
                    int n = this.responseView[j].DSP()[i];
                    jBphLoDiqESKYDzcdAUENWI.DSP((byte)(n & 0xFF));
                    jBphLoDiqESKYDzcdAUENWI.DSP((byte)(n >> 8 & 0xFF));
                    jBphLoDiqESKYDzcdAUENWI.DSP((byte)(n >> 16 & 0xFF));
                }
            }
        }
        return jBphLoDiqESKYDzcdAUENWI;
    }

    public Metadata[] DSP() throws IOException {
        Metadata eJnHMGFAPyJBjoMvbKedmZt2;
        this.AudioFileExtension();
        Vector<Metadata> vector = new Vector<Metadata>();
        this.AlacAudioCodec = 0;
        do {
            eJnHMGFAPyJBjoMvbKedmZt2 = this.responseView();
            vector.add(eJnHMGFAPyJBjoMvbKedmZt2);
            this.AlacAudioCodec += eJnHMGFAPyJBjoMvbKedmZt2.FFT();
        } while (!eJnHMGFAPyJBjoMvbKedmZt2.DSP());
        return vector.toArray(new Metadata[vector.size()]);
    }

    public void FFT() throws IOException {
        this.DSP();
        try {
            while (true) {
                this.IAudioInputStream();
                try {
                    this.AdditionalMetadataValue();
                    this.AlacContextModel.DSP(this.IAudioMetaInformation);
                    if (this.DSP(this.IAudioMetaInformation)) continue;
                    throw new EOFException();
                }
                catch (FrameDecodeException kuIPvTWWBrvoyCVGcBCqmee) {
                    ++this.AlacMetaDataModel;
                    continue;
                }
                break;
            }
        }
        catch (EOFException eOFException) {
            this.BufferedAlacReader = true;
            return;
        }
    }

    private void DSP(int n, int n2) {
        if (n <= this.AdditionalMetadataValue && n2 <= this.AudioFileExtension) {
            return;
        }
        Arrays.fill(this.responseView, null);
        for (int i = 0; i < n2; ++i) {
            this.responseView[i] = new ChannelData(n);
        }
        this.AdditionalMetadataValue = n;
        this.AudioFileExtension = n2;
    }

    private void AudioFileExtension() throws IOException {
        int n = 0;
        int n2 = 0;
        while (n2 < 4) {
            int n3 = this.FFT.responseView(8);
            if (n3 == Constants.DSP[n2]) {
                ++n2;
                n = 0;
                continue;
            }
            if (n3 == DSP[n]) {
                n2 = 0;
                if (++n != 3) continue;
                this.IAudioFileCodec();
                n = 0;
                continue;
            }
            throw new IOException("Could not find Stream Sync");
        }
    }

    public Metadata responseView() throws IOException {
        Metadata eJnHMGFAPyJBjoMvbKedmZt2 = null;
        boolean bl = this.FFT.responseView(1) != 0;
        int n = this.FFT.responseView(7);
        int n2 = this.FFT.responseView(24);
        if (n == 0) {
            eJnHMGFAPyJBjoMvbKedmZt2 = new StreamInfo(this.FFT, n2, bl);
            if (eJnHMGFAPyJBjoMvbKedmZt2.AudioFileExtension() > 0L) {
                this.IAudioInputStream = eJnHMGFAPyJBjoMvbKedmZt2;
                this.AlacDecoderUtils.DSP(this.IAudioInputStream);
            }
        } else {
            eJnHMGFAPyJBjoMvbKedmZt2 = n == 3 ? new SeekTable(this.FFT, n2, bl) : (n == 2 ? new Application(this.FFT, n2, bl) : (n == 1 ? new Padding(this.FFT, n2, bl) : (n == 4 ? new VorbisComment(this.FFT, n2, bl) : (n == 5 ? new CueSheet(this.FFT, n2, bl) : (n == 6 ? new Picture(this.FFT, n2, bl) : new Unknown(this.FFT, n2, bl))))));
        }
        this.AlacContextModel.DSP(eJnHMGFAPyJBjoMvbKedmZt2);
        return eJnHMGFAPyJBjoMvbKedmZt2;
    }

    private void IAudioFileCodec() throws IOException {
        int n = this.FFT.AudioFileExtension(8);
        int n2 = this.FFT.AudioFileExtension(8);
        int n3 = this.FFT.AudioFileExtension(8);
        int n4 = 0;
        for (int i = 0; i < 4; ++i) {
            int n5 = this.FFT.responseView(8);
            n4 <<= 7;
            n4 |= n5 & 0x7F;
        }
        this.FFT.DSP(null, n4);
    }

    private void IAudioInputStream() throws IOException {
        boolean bl = true;
        if (this.IAudioInputStream != null && this.IAudioInputStream.AudioFileExtension() > 0L && this.IAudioFileCodec >= this.IAudioInputStream.AudioFileExtension()) {
            return;
        }
        if (!this.FFT.FFT()) {
            this.FFT.responseView(this.FFT.responseView());
        }
        try {
            while (true) {
                int n;
                if ((n = this.FFT.responseView(8)) == 255) {
                    this.IBaseAudioCodec[0] = (byte)n;
                    n = this.FFT.AdditionalMetadataValue(8);
                    if (n >> 2 == 62) {
                        this.IBaseAudioCodec[1] = (byte)this.FFT.responseView(8);
                        return;
                    }
                }
                if (!bl) continue;
                this.AlacContextModel.DSP("FindSync LOST_SYNC: " + Integer.toHexString(n & 0xFF));
                bl = false;
            }
        }
        catch (EOFException eOFException) {
            if (!bl) {
                this.AlacContextModel.DSP("FindSync LOST_SYNC: Left over data in file");
            }
            return;
        }
    }

    public void AdditionalMetadataValue() throws IOException, FrameDecodeException {
        block24: {
            int n;
            int n2;
            short s;
            block23: {
                s = 0;
                s = CRC16.DSP(this.IBaseAudioCodec[0], s);
                s = CRC16.DSP(this.IBaseAudioCodec[1], s);
                this.FFT.DSP(s);
                try {
                    this.IAudioMetaInformation.DSP = new Header(this.FFT, this.IBaseAudioCodec, this.IAudioInputStream);
                }
                catch (BadHeaderException gTVlynIXxRUCYNEJFcwJfzY2) {
                    this.AlacContextModel.DSP("Found bad header: " + gTVlynIXxRUCYNEJFcwJfzY2);
                    throw new FrameDecodeException("Bad Frame Header: " + gTVlynIXxRUCYNEJFcwJfzY2, gTVlynIXxRUCYNEJFcwJfzY2);
                }
                this.DSP(this.IAudioMetaInformation.DSP.DSP, this.IAudioMetaInformation.DSP.responseView);
                for (n2 = 0; n2 < this.IAudioMetaInformation.DSP.responseView; ++n2) {
                    n = this.IAudioMetaInformation.DSP.AudioFileExtension;
                    switch (this.IAudioMetaInformation.DSP.AdditionalMetadataValue) {
                        case 0: {
                            break;
                        }
                        case 1: {
                            if (n2 != 1) break;
                            ++n;
                            break;
                        }
                        case 2: {
                            if (n2 != 0) break;
                            ++n;
                            break;
                        }
                        case 3: {
                            if (n2 != 1) break;
                            ++n;
                            break;
                        }
                    }
                    try {
                        this.FFT(n2, n);
                        continue;
                    }
                    catch (IOException iOException) {
                        this.AlacContextModel.DSP("ReadSubframe: " + iOException);
                        throw iOException;
                    }
                }
                this.IAudioMetaInformation();
                s = this.FFT.DSP();
                this.IAudioMetaInformation.DSP((short)this.FFT.responseView(16));
                if (s != this.IAudioMetaInformation.DSP()) break block23;
                switch (this.IAudioMetaInformation.DSP.AdditionalMetadataValue) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        for (int i = 0; i < this.IAudioMetaInformation.DSP.DSP; ++i) {
                            this.responseView[1].DSP()[i] = this.responseView[0].DSP()[i] - this.responseView[1].DSP()[i];
                        }
                        break block24;
                    }
                    case 2: {
                        for (int i = 0; i < this.IAudioMetaInformation.DSP.DSP; ++i) {
                            int[] nArray = this.responseView[0].DSP();
                            int n3 = i;
                            nArray[n3] = nArray[n3] + this.responseView[1].DSP()[i];
                        }
                        break block24;
                    }
                    case 3: {
                        for (int i = 0; i < this.IAudioMetaInformation.DSP.DSP; ++i) {
                            int n4 = this.responseView[0].DSP()[i];
                            int n5 = this.responseView[1].DSP()[i];
                            n4 <<= 1;
                            if ((n5 & 1) != 0) {
                                ++n4;
                            }
                            int n6 = n4 + n5;
                            int n7 = n4 - n5;
                            this.responseView[0].DSP()[i] = n6 >> 1;
                            this.responseView[1].DSP()[i] = n7 >> 1;
                        }
                        break block24;
                    }
                }
                break block24;
            }
            this.AlacContextModel.DSP("CRC Error: " + Integer.toHexString(s & 0xFFFF) + " vs " + Integer.toHexString(this.IAudioMetaInformation.DSP() & 0xFFFF));
            for (n2 = 0; n2 < this.IAudioMetaInformation.DSP.responseView; ++n2) {
                for (n = 0; n < this.IAudioMetaInformation.DSP.DSP; ++n) {
                    this.responseView[n2].DSP()[n] = 0;
                }
            }
        }
        this.MetaInfomationCopy = this.IAudioMetaInformation.DSP.responseView;
        this.AacAudioCodec = this.IAudioMetaInformation.DSP.AdditionalMetadataValue;
        this.AacMetaDataModel = this.IAudioMetaInformation.DSP.AudioFileExtension;
        this.BufferedAacReader = this.IAudioMetaInformation.DSP.FFT;
        this.AiffAudioCodec = this.IAudioMetaInformation.DSP.DSP;
        this.IAudioFileCodec += (long)this.IAudioMetaInformation.DSP.DSP;
    }

    private void FFT(int n, int n2) throws IOException, FrameDecodeException {
        int n3 = this.FFT.responseView(8);
        boolean bl = (n3 & 1) != 0;
        n3 &= 0xFE;
        int n4 = 0;
        if (bl) {
            n4 = this.FFT.IAudioFileCodec() + 1;
            n2 -= n4;
        }
        if ((n3 & 0x80) != 0) {
            this.AlacContextModel.DSP("ReadSubframe LOST_SYNC: " + Integer.toHexString(n3 & 0xFF));
            throw new FrameDecodeException("ReadSubframe LOST_SYNC: " + Integer.toHexString(n3 & 0xFF));
        }
        if (n3 == 0) {
            this.IAudioMetaInformation.FFT[n] = new ChannelConstant(this.FFT, this.IAudioMetaInformation.DSP, this.responseView[n], n2, n4);
        } else if (n3 == 2) {
            this.IAudioMetaInformation.FFT[n] = new ChannelVerbatim(this.FFT, this.IAudioMetaInformation.DSP, this.responseView[n], n2, n4);
        } else {
            if (n3 < 16) {
                throw new FrameDecodeException("ReadSubframe Bad Subframe Type: " + Integer.toHexString(n3 & 0xFF));
            }
            if (n3 <= 24) {
                this.IAudioMetaInformation.FFT[n] = new ChannelFixed(this.FFT, this.IAudioMetaInformation.DSP, this.responseView[n], n2, n4, n3 >> 1 & 7);
            } else {
                if (n3 < 64) {
                    throw new FrameDecodeException("ReadSubframe Bad Subframe Type: " + Integer.toHexString(n3 & 0xFF));
                }
                this.IAudioMetaInformation.FFT[n] = new ChannelLPC(this.FFT, this.IAudioMetaInformation.DSP, this.responseView[n], n2, n4, (n3 >> 1 & 0x1F) + 1);
            }
        }
        if (bl) {
            n3 = this.IAudioMetaInformation.FFT[n].DSP();
            int n5 = 0;
            while (n5 < this.IAudioMetaInformation.DSP.DSP) {
                int[] nArray = this.responseView[n].DSP();
                int n6 = n5++;
                nArray[n6] = nArray[n6] << n3;
            }
        }
    }

    private void IAudioMetaInformation() throws IOException, FrameDecodeException {
        int n;
        if (!this.FFT.FFT() && (n = this.FFT.responseView(this.FFT.responseView())) != 0) {
            this.AlacContextModel.DSP("ZeroPaddingError: " + Integer.toHexString(n));
            throw new FrameDecodeException("ZeroPaddingError: " + Integer.toHexString(n));
        }
    }
}

