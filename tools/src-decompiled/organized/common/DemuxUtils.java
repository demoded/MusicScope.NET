/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.DataInputStream;
import sdfgjkljljoftrytrszgijpokjprs.QTMovieT;
import sdfgjkljljoftrytrszgijpokjprs.DemuxResT;
import sdfgjkljljoftrytrszgijpokjprs.StreamUtils;
import sdfgjkljljoftrytrszgijpokjprs.ChunkInfo;
import sdfgjkljljoftrytrszgijpokjprs.MyStream;

class DemuxUtils {
    public static int DSP(int n, int n2, int n3, int n4) {
        return n << 24 | n2 << 16 | n3 << 8 | n4;
    }

    public static int FFT(int n, int n2, int n3, int n4) {
        int n5 = 0;
        int n6 = n;
        n5 = n6 << 24;
        n6 = n2;
        n5 |= n6 << 16;
        n6 = n3;
        n5 |= n6 << 8;
        n6 = n4;
        return n5 |= n6;
    }

    public static String DSP(int n) {
        char c = (char)(n >> 24 & 0xFF);
        char c2 = (char)(n >> 16 & 0xFF);
        char c3 = (char)(n >> 8 & 0xFF);
        char c4 = (char)(n & 0xFF);
        String string = c + " " + c2 + " " + c3 + " " + c4;
        return string;
    }

    public static int DSP(DataInputStream dataInputStream, QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, DemuxResT tTvVyJBikEjkvfEQPPobmUz) {
        int n;
        boolean bl = false;
        boolean bl2 = false;
        kLSmtNpcsSwOKRbEbUKdpqL.DSP().DSP(dataInputStream);
        kLSmtNpcsSwOKRbEbUKdpqL.DSP(tTvVyJBikEjkvfEQPPobmUz);
        while (true) {
            int n2;
            n = 0;
            try {
                n2 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            }
            catch (Exception exception) {
                System.err.println("(top) error reading chunk_len - possibly number too large");
                n2 = 1;
            }
            if (StreamUtils.AdditionalMetadataValue(kLSmtNpcsSwOKRbEbUKdpqL.DSP()) != 0) {
                return 0;
            }
            if (n2 == 1) {
                System.err.println("need 64bit support");
                return 0;
            }
            n = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            if (n == DemuxUtils.FFT(102, 116, 121, 112)) {
                DemuxUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL, n2);
                continue;
            }
            if (n == DemuxUtils.FFT(109, 111, 111, 118)) {
                if (DemuxUtils.AlacMetaDataModel(kLSmtNpcsSwOKRbEbUKdpqL, n2) == 0) {
                    return 0;
                }
                if (bl2) {
                    return DemuxUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL);
                }
                bl = true;
                continue;
            }
            if (n == DemuxUtils.FFT(109, 100, 97, 116)) {
                int n3 = 0;
                if (!bl) {
                    n3 = 1;
                }
                DemuxUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL, n2, n3);
                if (bl) {
                    return 1;
                }
                bl2 = true;
                continue;
            }
            if (n == DemuxUtils.FFT(102, 114, 101, 101)) {
                StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), n2 - 8);
                continue;
            }
            if (n != DemuxUtils.FFT(106, 117, 110, 107)) break;
            StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), n2 - 8);
        }
        System.err.println("(top) unknown chunk id: " + DemuxUtils.DSP(n));
        return 0;
    }

    static void DSP(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        int n2 = 0;
        int n3 = 0;
        int n4 = n - 8;
        n2 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        n4 -= 4;
        if (n2 != DemuxUtils.FFT(77, 52, 65, 32)) {
            System.err.println("not M4A file");
            return;
        }
        n3 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        n4 -= 4;
        while (n4 != 0) {
            StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            n4 -= 4;
        }
    }

    static void FFT(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        int n2 = n - 8;
        StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), n2);
    }

    static void responseView(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        int n2 = n - 8;
        StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), n2);
    }

    static void AdditionalMetadataValue(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        int n2 = n - 8;
        StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), n2);
    }

    static void AudioFileExtension(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        int n2 = n - 8;
        StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), n2);
    }

    static void IAudioFileCodec(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        int n2 = 0;
        int n3 = 0;
        int n4 = n - 8;
        StreamUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        --n4;
        StreamUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        StreamUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        StreamUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        n4 -= 3;
        n2 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        n3 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        n4 -= 8;
        StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        n4 -= 4;
        StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        n4 -= 8;
        int n5 = StreamUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), --n4);
    }

    static int IAudioInputStream(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        int n2 = 0;
        int n3 = n - 8;
        StreamUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        --n3;
        StreamUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        StreamUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        StreamUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        n3 -= 3;
        try {
            n2 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        }
        catch (Exception exception) {
            System.err.println("(read_chunk_stsd) error reading numentries - possibly number too large");
            n2 = 0;
        }
        n3 -= 4;
        if (n2 != 1) {
            System.err.println("only expecting one entry in sample description atom!");
            return 0;
        }
        for (int i = 0; i < n2; ++i) {
            int n4;
            int n5 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            kLSmtNpcsSwOKRbEbUKdpqL.FFT.AudioFileExtension = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            int n6 = n5;
            n6 -= 8;
            if (kLSmtNpcsSwOKRbEbUKdpqL.FFT().AudioFileExtension != DemuxUtils.FFT(97, 108, 97, 99)) {
                System.err.println("(read_chunk_stsd) error reading description atom - expecting alac, got " + DemuxUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.FFT().AudioFileExtension));
                return 0;
            }
            StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), 6);
            n6 -= 6;
            int n7 = StreamUtils.FFT(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            if (n7 != 1) {
                System.err.println("unknown version??");
            }
            n6 -= 2;
            StreamUtils.FFT(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            n6 -= 6;
            StreamUtils.FFT(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            n6 -= 2;
            StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), 4);
            n6 -= 4;
            StreamUtils.FFT(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            StreamUtils.FFT(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            n6 -= 4;
            StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), 4);
            kLSmtNpcsSwOKRbEbUKdpqL.FFT.MetaInfomationCopy = (n6 -= 4) + 12 + 8;
            if (kLSmtNpcsSwOKRbEbUKdpqL.FFT().MetaInfomationCopy > kLSmtNpcsSwOKRbEbUKdpqL.FFT().AacAudioCodec.length) {
                System.err.println("(read_chunk_stsd) unexpected codec data length read from atom " + kLSmtNpcsSwOKRbEbUKdpqL.FFT().MetaInfomationCopy);
                return 0;
            }
            for (n4 = 0; n4 < kLSmtNpcsSwOKRbEbUKdpqL.FFT().MetaInfomationCopy; ++n4) {
                kLSmtNpcsSwOKRbEbUKdpqL.FFT.AacAudioCodec[n4] = 0;
            }
            kLSmtNpcsSwOKRbEbUKdpqL.FFT.AacAudioCodec[0] = 0xC000000;
            kLSmtNpcsSwOKRbEbUKdpqL.FFT.AacAudioCodec[1] = DemuxUtils.DSP(97, 109, 114, 102);
            kLSmtNpcsSwOKRbEbUKdpqL.FFT.AacAudioCodec[2] = DemuxUtils.DSP(99, 97, 108, 97);
            StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), n6, kLSmtNpcsSwOKRbEbUKdpqL.FFT().AacAudioCodec, 12);
            n6 -= n6;
            n4 = 29;
            kLSmtNpcsSwOKRbEbUKdpqL.FFT.responseView = kLSmtNpcsSwOKRbEbUKdpqL.FFT().AacAudioCodec[n4] & 0xFF;
            n4 = 33;
            kLSmtNpcsSwOKRbEbUKdpqL.FFT.FFT = kLSmtNpcsSwOKRbEbUKdpqL.FFT().AacAudioCodec[n4] & 0xFF;
            n4 = 44;
            kLSmtNpcsSwOKRbEbUKdpqL.FFT.AdditionalMetadataValue = (kLSmtNpcsSwOKRbEbUKdpqL.FFT().AacAudioCodec[n4] & 0xFF) << 24 | (kLSmtNpcsSwOKRbEbUKdpqL.FFT().AacAudioCodec[n4 + 1] & 0xFF) << 16 | (kLSmtNpcsSwOKRbEbUKdpqL.FFT().AacAudioCodec[n4 + 2] & 0xFF) << 8 | kLSmtNpcsSwOKRbEbUKdpqL.FFT().AacAudioCodec[n4 + 3] & 0xFF;
            if (n6 != 0) {
                StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), n6);
            }
            kLSmtNpcsSwOKRbEbUKdpqL.FFT.DSP = 1;
            if (kLSmtNpcsSwOKRbEbUKdpqL.FFT().AudioFileExtension == DemuxUtils.FFT(97, 108, 97, 99)) continue;
            return 0;
        }
        return 1;
    }

    static void IAudioMetaInformation(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        int n2 = 0;
        int n3 = n - 8;
        StreamUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        --n3;
        StreamUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        StreamUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        StreamUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        n3 -= 3;
        try {
            n2 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        }
        catch (Exception exception) {
            System.err.println("(read_chunk_stts) error reading numentries - possibly number too large");
            n2 = 0;
        }
        n3 -= 4;
        kLSmtNpcsSwOKRbEbUKdpqL.FFT.IAudioMetaInformation = n2;
        for (int i = 0; i < n2; ++i) {
            kLSmtNpcsSwOKRbEbUKdpqL.FFT.IAudioInputStream[i].DSP(StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP()));
            kLSmtNpcsSwOKRbEbUKdpqL.FFT.IAudioInputStream[i].FFT(StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP()));
            n3 -= 8;
        }
        if (n3 != 0) {
            System.err.println("(read_chunk_stts) size remaining?");
            StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), n3);
        }
    }

    static void IBaseAudioCodec(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        int n2 = 0;
        int n3 = 0;
        int n4 = n - 8;
        StreamUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        --n4;
        StreamUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        StreamUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        StreamUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        n4 -= 3;
        n3 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        if (n3 != 0) {
            int n5 = 0;
            n5 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            kLSmtNpcsSwOKRbEbUKdpqL.FFT.IBaseAudioCodec = new int[n5];
            for (int i = 0; i < n5; ++i) {
                kLSmtNpcsSwOKRbEbUKdpqL.FFT.IBaseAudioCodec[i] = n3;
            }
            n4 -= 4;
            return;
        }
        n4 -= 4;
        try {
            n2 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        }
        catch (Exception exception) {
            System.err.println("(read_chunk_stsz) error reading numentries - possibly number too large");
            n2 = 0;
        }
        n4 -= 4;
        kLSmtNpcsSwOKRbEbUKdpqL.FFT.IBaseAudioCodec = new int[n2];
        for (int i = 0; i < n2; ++i) {
            kLSmtNpcsSwOKRbEbUKdpqL.FFT.IBaseAudioCodec[i] = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            n4 -= 4;
        }
        if (n4 != 0) {
            System.err.println("(read_chunk_stsz) size remaining?");
            StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), n4);
        }
    }

    static int MetaInfomationCopy(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        int n2;
        for (int i = n - 8; i != 0; i -= n2) {
            int n3 = 0;
            try {
                n2 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            }
            catch (Exception exception) {
                System.err.println("(read_chunk_stbl) error reading sub_chunk_len - possibly number too large");
                n2 = 0;
            }
            if (n2 <= 1 || n2 > i) {
                System.err.println("strange size for chunk inside stbl " + n2 + " (remaining: " + i + ")");
                return 0;
            }
            n3 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            if (n3 == DemuxUtils.FFT(115, 116, 115, 100)) {
                if (DemuxUtils.IAudioInputStream(kLSmtNpcsSwOKRbEbUKdpqL, n2) != 0) continue;
                return 0;
            }
            if (n3 == DemuxUtils.FFT(115, 116, 116, 115)) {
                DemuxUtils.IAudioMetaInformation(kLSmtNpcsSwOKRbEbUKdpqL, n2);
                continue;
            }
            if (n3 == DemuxUtils.FFT(115, 116, 115, 122)) {
                DemuxUtils.IBaseAudioCodec(kLSmtNpcsSwOKRbEbUKdpqL, n2);
                continue;
            }
            if (n3 == DemuxUtils.FFT(115, 116, 115, 99)) {
                DemuxUtils.AlacContextModel(kLSmtNpcsSwOKRbEbUKdpqL, n2);
                continue;
            }
            if (n3 == DemuxUtils.FFT(115, 116, 99, 111)) {
                DemuxUtils.BufferedAlacReader(kLSmtNpcsSwOKRbEbUKdpqL, n2);
                continue;
            }
            System.err.println("(stbl) unknown chunk id: " + DemuxUtils.DSP(n3));
            return 0;
        }
        return 1;
    }

    private static void BufferedAlacReader(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        MyStream xurJiSGFDQVeEtISoLSazWS2 = kLSmtNpcsSwOKRbEbUKdpqL.DSP();
        StreamUtils.DSP(xurJiSGFDQVeEtISoLSazWS2, 4);
        int n2 = StreamUtils.DSP(xurJiSGFDQVeEtISoLSazWS2);
        kLSmtNpcsSwOKRbEbUKdpqL.FFT.AacMetaDataModel = new int[n2];
        for (int i = 0; i < n2; ++i) {
            kLSmtNpcsSwOKRbEbUKdpqL.FFT.AacMetaDataModel[i] = StreamUtils.DSP(xurJiSGFDQVeEtISoLSazWS2);
        }
    }

    private static void AlacContextModel(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        MyStream xurJiSGFDQVeEtISoLSazWS2 = kLSmtNpcsSwOKRbEbUKdpqL.DSP();
        StreamUtils.DSP(xurJiSGFDQVeEtISoLSazWS2, 4);
        int n2 = StreamUtils.DSP(xurJiSGFDQVeEtISoLSazWS2);
        kLSmtNpcsSwOKRbEbUKdpqL.FFT.BufferedAacReader = new ChunkInfo[n2];
        for (int i = 0; i < n2; ++i) {
            ChunkInfo ajlSZBxlJSwYUTeUsFfhumH2 = new ChunkInfo();
            ajlSZBxlJSwYUTeUsFfhumH2.DSP(StreamUtils.DSP(xurJiSGFDQVeEtISoLSazWS2));
            ajlSZBxlJSwYUTeUsFfhumH2.FFT(StreamUtils.DSP(xurJiSGFDQVeEtISoLSazWS2));
            ajlSZBxlJSwYUTeUsFfhumH2.responseView(StreamUtils.DSP(xurJiSGFDQVeEtISoLSazWS2));
            kLSmtNpcsSwOKRbEbUKdpqL.FFT.BufferedAacReader[i] = ajlSZBxlJSwYUTeUsFfhumH2;
        }
    }

    static int AacAudioCodec(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        int n2;
        int n3;
        int n4;
        int n5 = n - 8;
        try {
            n4 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        }
        catch (Exception exception) {
            System.err.println("(read_chunk_minf) error reading media_info_size - possibly number too large");
            n4 = 0;
        }
        if (n4 != 16) {
            System.err.println("unexpected size in media info\n");
            return 0;
        }
        if (StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP()) != DemuxUtils.FFT(115, 109, 104, 100)) {
            System.err.println("not a sound header! can't handle this.");
            return 0;
        }
        StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), 8);
        n5 -= 16;
        try {
            n3 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        }
        catch (Exception exception) {
            System.err.println("(read_chunk_minf) error reading dinf_size - possibly number too large");
            n3 = 0;
        }
        if (StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP()) != DemuxUtils.FFT(100, 105, 110, 102)) {
            System.err.println("expected dinf, didn't get it.");
            return 0;
        }
        StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), n3 - 8);
        n5 -= n3;
        try {
            n2 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
        }
        catch (Exception exception) {
            System.err.println("(read_chunk_minf) error reading stbl_size - possibly number too large");
            n2 = 0;
        }
        if (StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP()) != DemuxUtils.FFT(115, 116, 98, 108)) {
            System.err.println("expected stbl, didn't get it.");
            return 0;
        }
        if (DemuxUtils.MetaInfomationCopy(kLSmtNpcsSwOKRbEbUKdpqL, n2) == 0) {
            return 0;
        }
        if ((n5 -= n2) != 0) {
            System.err.println("(read_chunk_minf) - size remaining?");
            StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), n5);
        }
        return 1;
    }

    static int AacMetaDataModel(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        int n2;
        for (int i = n - 8; i != 0; i -= n2) {
            int n3 = 0;
            try {
                n2 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            }
            catch (Exception exception) {
                System.err.println("(read_chunk_mdia) error reading sub_chunk_len - possibly number too large");
                n2 = 0;
            }
            if (n2 <= 1 || n2 > i) {
                System.err.println("strange size for chunk inside mdia\n");
                return 0;
            }
            n3 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            if (n3 == DemuxUtils.FFT(109, 100, 104, 100)) {
                DemuxUtils.responseView(kLSmtNpcsSwOKRbEbUKdpqL, n2);
                continue;
            }
            if (n3 == DemuxUtils.FFT(104, 100, 108, 114)) {
                DemuxUtils.IAudioFileCodec(kLSmtNpcsSwOKRbEbUKdpqL, n2);
                continue;
            }
            if (n3 == DemuxUtils.FFT(109, 105, 110, 102)) {
                if (DemuxUtils.AacAudioCodec(kLSmtNpcsSwOKRbEbUKdpqL, n2) != 0) continue;
                return 0;
            }
            System.err.println("(mdia) unknown chunk id: " + DemuxUtils.DSP(n3));
            return 0;
        }
        return 1;
    }

    static int BufferedAacReader(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        int n2;
        for (int i = n - 8; i != 0; i -= n2) {
            int n3 = 0;
            try {
                n2 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            }
            catch (Exception exception) {
                System.err.println("(read_chunk_trak) error reading sub_chunk_len - possibly number too large");
                n2 = 0;
            }
            if (n2 <= 1 || n2 > i) {
                System.err.println("strange size for chunk inside trak");
                return 0;
            }
            n3 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            if (n3 == DemuxUtils.FFT(116, 107, 104, 100)) {
                DemuxUtils.FFT(kLSmtNpcsSwOKRbEbUKdpqL, n2);
                continue;
            }
            if (n3 == DemuxUtils.FFT(109, 100, 105, 97)) {
                if (DemuxUtils.AacMetaDataModel(kLSmtNpcsSwOKRbEbUKdpqL, n2) != 0) continue;
                return 0;
            }
            if (n3 == DemuxUtils.FFT(101, 100, 116, 115)) {
                DemuxUtils.AdditionalMetadataValue(kLSmtNpcsSwOKRbEbUKdpqL, n2);
                continue;
            }
            System.err.println("(trak) unknown chunk id: " + DemuxUtils.DSP(n3));
            return 0;
        }
        return 1;
    }

    static void AiffAudioCodec(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        int n2 = n - 8;
        StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), n2);
    }

    static void AiffMetaDataModel(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        int n2 = n - 8;
        StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), n2);
    }

    static void AlacAudioCodec(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        int n2 = n - 8;
        StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), n2);
    }

    static int AlacMetaDataModel(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n) {
        int n2;
        for (int i = n - 8; i != 0; i -= n2) {
            int n3 = 0;
            try {
                n2 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            }
            catch (Exception exception) {
                System.err.println("(read_chunk_moov) error reading sub_chunk_len - possibly number too large");
                n2 = 0;
            }
            if (n2 <= 1 || n2 > i) {
                System.err.println("strange size for chunk inside moov");
                return 0;
            }
            n3 = StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP());
            if (n3 == DemuxUtils.FFT(109, 118, 104, 100)) {
                DemuxUtils.AiffAudioCodec(kLSmtNpcsSwOKRbEbUKdpqL, n2);
                continue;
            }
            if (n3 == DemuxUtils.FFT(116, 114, 97, 107)) {
                if (DemuxUtils.BufferedAacReader(kLSmtNpcsSwOKRbEbUKdpqL, n2) != 0) continue;
                return 0;
            }
            if (n3 == DemuxUtils.FFT(117, 100, 116, 97)) {
                DemuxUtils.AiffMetaDataModel(kLSmtNpcsSwOKRbEbUKdpqL, n2);
                continue;
            }
            if (n3 == DemuxUtils.FFT(101, 108, 115, 116)) {
                DemuxUtils.AudioFileExtension(kLSmtNpcsSwOKRbEbUKdpqL, n2);
                continue;
            }
            if (n3 == DemuxUtils.FFT(105, 111, 100, 115)) {
                DemuxUtils.AlacAudioCodec(kLSmtNpcsSwOKRbEbUKdpqL, n2);
                continue;
            }
            if (n3 == DemuxUtils.FFT(102, 114, 101, 101)) {
                StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), n2 - 8);
                continue;
            }
            System.err.println("(moov) unknown chunk id: " + DemuxUtils.DSP(n3));
            return 0;
        }
        return 1;
    }

    static void DSP(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL, int n, int n2) {
        int n3 = n - 8;
        if (n3 == 0) {
            return;
        }
        kLSmtNpcsSwOKRbEbUKdpqL.FFT.AiffAudioCodec = n3;
        if (n2 != 0) {
            kLSmtNpcsSwOKRbEbUKdpqL.DSP(StreamUtils.AudioFileExtension(kLSmtNpcsSwOKRbEbUKdpqL.DSP()));
            StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), n3);
        }
    }

    static int DSP(QTMovieT kLSmtNpcsSwOKRbEbUKdpqL) {
        if (kLSmtNpcsSwOKRbEbUKdpqL.responseView() == -1) {
            System.err.println("stream contains mdat before moov but is not seekable");
            return 2;
        }
        if (StreamUtils.FFT(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), kLSmtNpcsSwOKRbEbUKdpqL.responseView()) != 0) {
            return 3;
        }
        return 1;
    }
}

