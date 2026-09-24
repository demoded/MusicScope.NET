/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import sdfgjkljljoftrytrszgijpokjprs.AlacContextModel;
import sdfgjkljljoftrytrszgijpokjprs.SampleDuration;
import sdfgjkljljoftrytrszgijpokjprs.DemuxUtils;
import sdfgjkljljoftrytrszgijpokjprs.QTMovieT;
import sdfgjkljljoftrytrszgijpokjprs.AlacInputStream;
import sdfgjkljljoftrytrszgijpokjprs.DemuxResT;
import sdfgjkljljoftrytrszgijpokjprs.AlacDecoderUtils;
import sdfgjkljljoftrytrszgijpokjprs.StreamUtils;
import sdfgjkljljoftrytrszgijpokjprs.AlacFile;
import sdfgjkljljoftrytrszgijpokjprs.MyStream;

public class AlacUtils {
    public static AlacContextModel DSP(String string) {
        AlacInputStream nwteqrdXWwWpaoJmiBDNxxz;
        FileInputStream fileInputStream;
        QTMovieT kLSmtNpcsSwOKRbEbUKdpqL = new QTMovieT();
        DemuxResT tTvVyJBikEjkvfEQPPobmUz = new DemuxResT();
        AlacContextModel cWUsTkhDhNSqiWmIUfrczRE = new AlacContextModel();
        cWUsTkhDhNSqiWmIUfrczRE.DSP(false);
        try {
            fileInputStream = new FileInputStream(string);
            nwteqrdXWwWpaoJmiBDNxxz = new AlacInputStream(fileInputStream);
        }
        catch (FileNotFoundException fileNotFoundException) {
            cWUsTkhDhNSqiWmIUfrczRE.DSP("File not found");
            cWUsTkhDhNSqiWmIUfrczRE.DSP(true);
            return cWUsTkhDhNSqiWmIUfrczRE;
        }
        cWUsTkhDhNSqiWmIUfrczRE.DSP(nwteqrdXWwWpaoJmiBDNxxz);
        int n = DemuxUtils.DSP(nwteqrdXWwWpaoJmiBDNxxz, kLSmtNpcsSwOKRbEbUKdpqL, tTvVyJBikEjkvfEQPPobmUz);
        if (n == 0) {
            cWUsTkhDhNSqiWmIUfrczRE.DSP(true);
            if (tTvVyJBikEjkvfEQPPobmUz.DSP == 0) {
                cWUsTkhDhNSqiWmIUfrczRE.DSP("Failed to load the QuickTime movie headers.");
                if (tTvVyJBikEjkvfEQPPobmUz.DSP != 0) {
                    cWUsTkhDhNSqiWmIUfrczRE.DSP(cWUsTkhDhNSqiWmIUfrczRE.IAudioFileCodec() + " File type: " + DemuxUtils.DSP(tTvVyJBikEjkvfEQPPobmUz.AudioFileExtension));
                }
            } else {
                cWUsTkhDhNSqiWmIUfrczRE.DSP("Error while loading the QuickTime movie headers.");
            }
            return cWUsTkhDhNSqiWmIUfrczRE;
        }
        if (n == 3) {
            try {
                cWUsTkhDhNSqiWmIUfrczRE.responseView().close();
            }
            catch (IOException iOException) {
                cWUsTkhDhNSqiWmIUfrczRE.DSP("Error when seeking to start");
                cWUsTkhDhNSqiWmIUfrczRE.DSP(true);
                return cWUsTkhDhNSqiWmIUfrczRE;
            }
            try {
                fileInputStream = new FileInputStream(string);
                nwteqrdXWwWpaoJmiBDNxxz = new AlacInputStream(fileInputStream);
                cWUsTkhDhNSqiWmIUfrczRE.DSP(nwteqrdXWwWpaoJmiBDNxxz);
                kLSmtNpcsSwOKRbEbUKdpqL.DSP().DSP(nwteqrdXWwWpaoJmiBDNxxz);
                kLSmtNpcsSwOKRbEbUKdpqL.DSP().DSP(0);
                StreamUtils.DSP(kLSmtNpcsSwOKRbEbUKdpqL.DSP(), kLSmtNpcsSwOKRbEbUKdpqL.responseView());
            }
            catch (FileNotFoundException fileNotFoundException) {
                cWUsTkhDhNSqiWmIUfrczRE.DSP("Input file not found");
                cWUsTkhDhNSqiWmIUfrczRE.DSP(true);
                return cWUsTkhDhNSqiWmIUfrczRE;
            }
        }
        AlacFile cmBIzvlUaDPTiIjykjjmigy2 = AlacDecoderUtils.DSP(tTvVyJBikEjkvfEQPPobmUz.responseView, tTvVyJBikEjkvfEQPPobmUz.FFT);
        AlacDecoderUtils.DSP(cmBIzvlUaDPTiIjykjjmigy2, tTvVyJBikEjkvfEQPPobmUz.AacAudioCodec);
        cWUsTkhDhNSqiWmIUfrczRE.DSP(tTvVyJBikEjkvfEQPPobmUz);
        cWUsTkhDhNSqiWmIUfrczRE.DSP(cmBIzvlUaDPTiIjykjjmigy2);
        return cWUsTkhDhNSqiWmIUfrczRE;
    }

    public static void DSP(AlacContextModel cWUsTkhDhNSqiWmIUfrczRE) {
        if (null != cWUsTkhDhNSqiWmIUfrczRE.responseView()) {
            try {
                cWUsTkhDhNSqiWmIUfrczRE.responseView().close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public static int DSP(AlacContextModel cWUsTkhDhNSqiWmIUfrczRE, int[] nArray) {
        SampleDuration damZYXCIxKyKrnRzlNpeRfa = new SampleDuration();
        byte[] byArray = cWUsTkhDhNSqiWmIUfrczRE.IAudioInputStream();
        int n = 73728;
        MyStream xurJiSGFDQVeEtISoLSazWS2 = new MyStream();
        xurJiSGFDQVeEtISoLSazWS2.DSP(cWUsTkhDhNSqiWmIUfrczRE.responseView());
        if (cWUsTkhDhNSqiWmIUfrczRE.AdditionalMetadataValue() >= cWUsTkhDhNSqiWmIUfrczRE.DSP().IBaseAudioCodec.length) {
            return 0;
        }
        if (AlacUtils.DSP(cWUsTkhDhNSqiWmIUfrczRE.DSP(), cWUsTkhDhNSqiWmIUfrczRE.AdditionalMetadataValue(), damZYXCIxKyKrnRzlNpeRfa) == 0) {
            return 0;
        }
        int n2 = damZYXCIxKyKrnRzlNpeRfa.DSP();
        StreamUtils.DSP(xurJiSGFDQVeEtISoLSazWS2, n2, byArray, 0);
        int n3 = n;
        n3 = AlacDecoderUtils.DSP(cWUsTkhDhNSqiWmIUfrczRE.FFT(), byArray, nArray, n3);
        cWUsTkhDhNSqiWmIUfrczRE.DSP(cWUsTkhDhNSqiWmIUfrczRE.AdditionalMetadataValue() + 1);
        System.arraycopy(nArray, cWUsTkhDhNSqiWmIUfrczRE.AudioFileExtension(), nArray, 0, n3 -= cWUsTkhDhNSqiWmIUfrczRE.AudioFileExtension() * AlacUtils.AudioFileExtension(cWUsTkhDhNSqiWmIUfrczRE));
        cWUsTkhDhNSqiWmIUfrczRE.FFT(0);
        return n3;
    }

    public static int FFT(AlacContextModel cWUsTkhDhNSqiWmIUfrczRE) {
        if (null != cWUsTkhDhNSqiWmIUfrczRE && cWUsTkhDhNSqiWmIUfrczRE.DSP().AdditionalMetadataValue != 0) {
            return cWUsTkhDhNSqiWmIUfrczRE.DSP().AdditionalMetadataValue;
        }
        return 44100;
    }

    public static int responseView(AlacContextModel cWUsTkhDhNSqiWmIUfrczRE) {
        if (null != cWUsTkhDhNSqiWmIUfrczRE && cWUsTkhDhNSqiWmIUfrczRE.DSP().FFT != 0) {
            return cWUsTkhDhNSqiWmIUfrczRE.DSP().FFT;
        }
        return 2;
    }

    public static int AdditionalMetadataValue(AlacContextModel cWUsTkhDhNSqiWmIUfrczRE) {
        if (null != cWUsTkhDhNSqiWmIUfrczRE && cWUsTkhDhNSqiWmIUfrczRE.DSP().responseView != 0) {
            return cWUsTkhDhNSqiWmIUfrczRE.DSP().responseView;
        }
        return 16;
    }

    public static int AudioFileExtension(AlacContextModel cWUsTkhDhNSqiWmIUfrczRE) {
        if (null != cWUsTkhDhNSqiWmIUfrczRE && cWUsTkhDhNSqiWmIUfrczRE.DSP().responseView != 0) {
            return (int)Math.ceil(cWUsTkhDhNSqiWmIUfrczRE.DSP().responseView / 8);
        }
        return 2;
    }

    public static int IAudioFileCodec(AlacContextModel cWUsTkhDhNSqiWmIUfrczRE) {
        int n = 0;
        int n2 = 0;
        SampleDuration damZYXCIxKyKrnRzlNpeRfa = new SampleDuration();
        boolean bl = false;
        int n3 = 0;
        for (int i = 0; i < cWUsTkhDhNSqiWmIUfrczRE.DSP().IBaseAudioCodec.length; ++i) {
            int n4 = 0;
            n2 = 0;
            n3 = AlacUtils.DSP(cWUsTkhDhNSqiWmIUfrczRE.DSP(), i, damZYXCIxKyKrnRzlNpeRfa);
            if (n3 == 0) {
                return -1;
            }
            n4 = damZYXCIxKyKrnRzlNpeRfa.FFT();
            n2 = damZYXCIxKyKrnRzlNpeRfa.DSP();
            n += n4;
        }
        return n;
    }

    static int DSP(DemuxResT tTvVyJBikEjkvfEQPPobmUz, int n, SampleDuration damZYXCIxKyKrnRzlNpeRfa) {
        int n2 = 0;
        int n3 = 0;
        if (n >= tTvVyJBikEjkvfEQPPobmUz.IBaseAudioCodec.length) {
            System.err.println("sample " + n + " does not exist ");
            return 0;
        }
        if (tTvVyJBikEjkvfEQPPobmUz.IAudioMetaInformation == 0) {
            System.err.println("no time to samples");
            return 0;
        }
        while (tTvVyJBikEjkvfEQPPobmUz.IAudioInputStream[n3].DSP() + n2 <= n) {
            n2 += tTvVyJBikEjkvfEQPPobmUz.IAudioInputStream[n3].DSP();
            if (++n3 < tTvVyJBikEjkvfEQPPobmUz.IAudioMetaInformation) continue;
            System.err.println("sample " + n + " does not have a duration");
            return 0;
        }
        damZYXCIxKyKrnRzlNpeRfa.FFT(tTvVyJBikEjkvfEQPPobmUz.IAudioInputStream[n3].FFT());
        damZYXCIxKyKrnRzlNpeRfa.DSP(tTvVyJBikEjkvfEQPPobmUz.IBaseAudioCodec[n]);
        return 1;
    }
}

