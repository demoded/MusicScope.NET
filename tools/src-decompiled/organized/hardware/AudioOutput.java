/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import sdfgjkljljoftrytrszgijpokjprs.IWritableAudioOutput;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormat;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.IMetaInformationListener;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;

public class AudioOutput
implements IWritableAudioOutput,
IPlayerStateListener,
IMetaInformationListener {
    private static final double DSP = Math.pow(10.0, -0.15);
    private IAudioMetaInformation FFT;
    private DataLine.Info responseView;
    private SourceDataLine AdditionalMetadataValue;
    private AudioFormat AudioFileExtension;
    private Mixer IAudioFileCodec;
    private final boolean IAudioInputStream;
    private double IAudioMetaInformation = 1.0;

    public AudioOutput(Mixer.Info info) {
        this.IAudioInputStream = false;
        this.IAudioFileCodec = AudioSystem.getMixer(info);
        this.AdditionalMetadataValue = null;
    }

    public void DSP(AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf) {
        if (this.AdditionalMetadataValue != null && this.AdditionalMetadataValue.isOpen()) {
            byte[] byArray = this.DSP(zvOTVUaKFTNpNSbbMYZfoXf, this.AudioFileExtension, this.FFT);
            this.AdditionalMetadataValue.write(byArray, 0, byArray.length);
        }
    }

    public void DSP(int n) {
        this.IAudioMetaInformation = Math.pow(10.0, (double)n / 20.0);
    }

    @Override
    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA2) {
        this.FFT = yGjBevanihqaxYKnUNtrNeA;
        if (!this.IAudioInputStream) {
            this.AudioFileExtension = new AudioFormat(yGjBevanihqaxYKnUNtrNeA.DSP(), yGjBevanihqaxYKnUNtrNeA.FFT().DSP(), yGjBevanihqaxYKnUNtrNeA.AdditionalMetadataValue(), true, false);
            for (int i = 0; i < 2; ++i) {
                try {
                    this.responseView = new DataLine.Info(SourceDataLine.class, this.AudioFileExtension);
                    this.AdditionalMetadataValue = (SourceDataLine)this.IAudioFileCodec.getLine(this.responseView);
                    break;
                }
                catch (Exception exception) {
                    this.IAudioFileCodec.close();
                    this.AudioFileExtension = new AudioFormat(yGjBevanihqaxYKnUNtrNeA.DSP() > 192000 ? (float)(yGjBevanihqaxYKnUNtrNeA.DSP() / 2) : (float)yGjBevanihqaxYKnUNtrNeA.DSP(), yGjBevanihqaxYKnUNtrNeA.FFT().DSP() > BitsPerSample.responseView.DSP() ? BitsPerSample.responseView.DSP() : yGjBevanihqaxYKnUNtrNeA.FFT().DSP(), yGjBevanihqaxYKnUNtrNeA.AdditionalMetadataValue(), true, false);
                    continue;
                }
            }
        }
    }

    @Override
    public void DSP(PlayerState zjoyaRSokkGYDwXHPKTBIiX) {
        if (this.AdditionalMetadataValue != null) {
            try {
                switch (zjoyaRSokkGYDwXHPKTBIiX) {
                    case responseView: {
                        this.AdditionalMetadataValue.flush();
                    }
                    case FFT: {
                        this.AdditionalMetadataValue.open(this.AudioFileExtension, this.FFT.responseView() / 10);
                        this.AdditionalMetadataValue.start();
                        break;
                    }
                    case DSP: {
                        this.AdditionalMetadataValue.flush();
                        this.AdditionalMetadataValue.close();
                        break;
                    }
                    case AdditionalMetadataValue: {
                        this.AdditionalMetadataValue.drain();
                        this.AdditionalMetadataValue.stop();
                        break;
                    }
                    case AudioFileExtension: {
                        this.AdditionalMetadataValue.stop();
                    }
                }
            }
            catch (LineUnavailableException lineUnavailableException) {
                Logger.getLogger(AudioOutput.class.getName()).log(Level.SEVERE, null, lineUnavailableException);
            }
        }
    }

    private byte[] DSP(AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf, AudioFormat audioFormat, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA) {
        BitsPerSample kFVWmcqOBgYFxPgeswapvPe = BitsPerSample.FFT(audioFormat.getSampleSizeInBits());
        int n = (int)(Math.pow(2.0, kFVWmcqOBgYFxPgeswapvPe.DSP()) - 1.0) / 2;
        int n2 = (int)(Math.pow(2.0, kFVWmcqOBgYFxPgeswapvPe.DSP()) - 1.0) / 2 * -1;
        int n3 = (float)yGjBevanihqaxYKnUNtrNeA.DSP() > audioFormat.getSampleRate() ? 2 : 1;
        ByteBuffer byteBuffer = ByteBuffer.allocate(zvOTVUaKFTNpNSbbMYZfoXf.AdditionalMetadataValue() * 2 * kFVWmcqOBgYFxPgeswapvPe.FFT() / n3);
        for (int i = 0; i < zvOTVUaKFTNpNSbbMYZfoXf.AdditionalMetadataValue(); i += n3) {
            int n4;
            int n5 = kFVWmcqOBgYFxPgeswapvPe.DSP(this.DSP(zvOTVUaKFTNpNSbbMYZfoXf.responseView()[i], this.IAudioMetaInformation));
            int n6 = kFVWmcqOBgYFxPgeswapvPe.DSP(this.DSP(zvOTVUaKFTNpNSbbMYZfoXf.FFT()[i], this.IAudioMetaInformation));
            if (yGjBevanihqaxYKnUNtrNeA.MetaInfomationCopy().FFT() == AudioFormat.FFT) {
                n5 = (int)((double)n5 * DSP);
                n6 = (int)((double)n6 * DSP);
                n5 = n5 < n ? n5 : n;
                n5 = n5 > n2 ? n5 : n2;
                n6 = n6 < n ? n6 : n;
                n6 = n6 > n2 ? n6 : n2;
            }
            for (n4 = 0; n4 < kFVWmcqOBgYFxPgeswapvPe.FFT(); ++n4) {
                byteBuffer.put((byte)(n5 >> 8 * n4));
            }
            for (n4 = 0; n4 < kFVWmcqOBgYFxPgeswapvPe.FFT(); ++n4) {
                byteBuffer.put((byte)(n6 >> 8 * n4));
            }
        }
        return byteBuffer.array();
    }

    private double DSP(double d, double d2) {
        return d * d2;
    }
}

