/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JComboBox;

public class AudioSetup {
    public TargetDataLine DSP(float f, int n, JComboBox jComboBox, int[] nArray) {
        int n2 = 2;
        boolean bl = false;
        boolean bl2 = true;
        Mixer.Info[] infoArray = AudioSystem.getMixerInfo();
        TargetDataLine targetDataLine = null;
        AudioFormat audioFormat = new AudioFormat(f, n, n2, bl2, bl);
        Mixer mixer = AudioSystem.getMixer(infoArray[nArray[jComboBox.getSelectedIndex()]]);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
        try {
            targetDataLine = (TargetDataLine)mixer.getLine(info);
        }
        catch (LineUnavailableException lineUnavailableException) {
            System.out.println("No Input Audio Line Available!");
        }
        catch (Exception exception) {
            System.out.println("Try 16 Bit Audio-Input");
            n = 16;
            audioFormat = new AudioFormat(f, n, n2, bl2, bl);
            info = new DataLine.Info(TargetDataLine.class, audioFormat);
            try {
                targetDataLine = (TargetDataLine)mixer.getLine(info);
            }
            catch (LineUnavailableException lineUnavailableException) {
                System.out.println("Input Audio Format Not Supported!");
            }
        }
        try {
            targetDataLine.open(audioFormat);
            targetDataLine.start();
            System.out.println("Input Audio Line opened!");
        }
        catch (LineUnavailableException lineUnavailableException) {
            System.out.println("Unable to open Input Audio Line!");
        }
        return targetDataLine;
    }

    public void DSP(TargetDataLine targetDataLine) {
        targetDataLine.flush();
        targetDataLine.stop();
        targetDataLine.close();
    }
}

