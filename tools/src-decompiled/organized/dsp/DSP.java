/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import sdfgjkljljoftrytrszgijpokjprs.THDAnalyser;
import sdfgjkljljoftrytrszgijpokjprs.FFT;

public class DSP
implements Runnable {
    private final float FFT = 44100.0f;
    private int responseView = 24;
    private TargetDataLine AdditionalMetadataValue = null;
    private boolean AudioFileExtension = false;
    private final byte[] IAudioFileCodec = new byte[131072];
    private final double[] IAudioInputStream = new double[65536];
    private final double IAudioMetaInformation = 0.022675736961451247;
    private final double IBaseAudioCodec = Math.pow(10.0, -5.0);
    private final Random MetaInfomationCopy = new Random();
    private int AacAudioCodec;
    private int AacMetaDataModel;
    private int BufferedAacReader = 4;
    public static FFT DSP = new FFT();

    public void DSP(boolean bl) {
        this.AudioFileExtension = bl;
    }

    public boolean DSP() {
        return this.AudioFileExtension;
    }

    private void FFT() {
        int n = 2;
        boolean bl = false;
        boolean bl2 = true;
        Mixer mixer = AudioSystem.getMixer(THDAnalyser.DSP[THDAnalyser.FFT[THDAnalyser.AdditionalMetadataValue.getSelectedIndex()]]);
        AudioFormat audioFormat = new AudioFormat(44100.0f, this.responseView, n, bl2, bl);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
        try {
            this.BufferedAacReader = 6;
            this.AdditionalMetadataValue = (TargetDataLine)mixer.getLine(info);
            this.AdditionalMetadataValue.open(audioFormat);
            this.AdditionalMetadataValue.start();
            this.AdditionalMetadataValue.flush();
        }
        catch (LineUnavailableException lineUnavailableException) {
            System.out.println("No Input Audio Line Available!");
        }
        catch (Exception exception) {
            System.out.println("Input Audio Format Not Supported! Try 16 Bit.");
            this.responseView = 16;
            this.BufferedAacReader = 4;
            audioFormat = new AudioFormat(44100.0f, this.responseView, n, bl2, bl);
            info = new DataLine.Info(TargetDataLine.class, audioFormat);
            try {
                this.AdditionalMetadataValue = (TargetDataLine)mixer.getLine(info);
                this.AdditionalMetadataValue.open(audioFormat);
                this.AdditionalMetadataValue.start();
                this.AdditionalMetadataValue.flush();
            }
            catch (LineUnavailableException lineUnavailableException) {
                Logger.getLogger(DSP.class.getName()).log(Level.SEVERE, null, lineUnavailableException);
                System.out.println("Input Audio Format Not Supported!");
            }
        }
    }

    private void responseView() {
        boolean bl = true;
        int n = 0;
        int n2 = 512;
        int n3 = this.BufferedAacReader * n2;
        double d = Math.pow(2.0, this.responseView);
        double[] dArray = new double[n3];
        this.AudioFileExtension = false;
        while (!this.AudioFileExtension) {
            int n4 = this.AdditionalMetadataValue.read(this.IAudioFileCodec, 0, n3);
            boolean bl2 = false;
            for (int i = 0; i < n3; i += this.BufferedAacReader) {
                int n5;
                int n6;
                if (this.responseView == 24) {
                    n6 = this.IAudioFileCodec[i + 2];
                    n6 = n6 << 8 | this.IAudioFileCodec[i + 1] & 0xFF;
                    n6 = n6 << 8 | this.IAudioFileCodec[i] & 0xFF;
                    n5 = this.IAudioFileCodec[i + 5];
                    n5 = n5 << 8 | this.IAudioFileCodec[i + 4] & 0xFF;
                    n5 = n5 << 8 | this.IAudioFileCodec[i + 3] & 0xFF;
                } else {
                    n6 = this.IAudioFileCodec[i + 1];
                    n6 = n6 << 8 | this.IAudioFileCodec[i] & 0xFF;
                    n5 = this.IAudioFileCodec[i + 3];
                    n5 = n5 << 8 | this.IAudioFileCodec[i + 2] & 0xFF;
                }
                this.IAudioInputStream[this.AacAudioCodec] = (double)(n6 + n5) / d;
                ++this.AacAudioCodec;
                if (this.AacAudioCodec > 8192) {
                    this.AacAudioCodec = 0;
                }
                if (++n <= 4096 || DSP.DSP()) continue;
                n = 0;
                this.AacMetaDataModel = this.AacAudioCodec;
                for (int j = 0; j < 2048; ++j) {
                    dArray[j] = this.IAudioInputStream[this.AacMetaDataModel];
                    ++this.AacMetaDataModel;
                    if (this.AacMetaDataModel <= 8192) continue;
                    this.AacMetaDataModel = 0;
                }
                DSP.DSP(dArray);
                new Thread(DSP).start();
            }
        }
        this.AdditionalMetadataValue.flush();
        this.AdditionalMetadataValue.stop();
        this.AdditionalMetadataValue.close();
        THDAnalyser.DSP();
    }

    @Override
    public void run() {
        this.FFT();
        this.responseView();
    }
}

