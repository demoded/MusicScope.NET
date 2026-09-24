/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.OperatingSystem;
import sdfgjkljljoftrytrszgijpokjprs.MixerSettingSet;

public class AudioLineUtil {
    public static <C extends DataLine> List<MixerSettingSet> DSP(Class<C> clazz, int[] nArray, int[] nArray2, BitsPerSample[] kFVWmcqOBgYFxPgeswapvPeArray, boolean bl) {
        ArrayList<MixerSettingSet> arrayList = new ArrayList<MixerSettingSet>(0);
        if (!OperatingSystem.AdditionalMetadataValue()) {
            for (Mixer.Info info : AudioSystem.getMixerInfo()) {
                MixerSettingSet sAVBeCYeaIWVZOjBqiIeTcH2 = new MixerSettingSet(info, AudioFormat.Encoding.PCM_SIGNED, bl);
                for (int n : nArray) {
                    for (int n2 : nArray2) {
                        for (BitsPerSample kFVWmcqOBgYFxPgeswapvPe : kFVWmcqOBgYFxPgeswapvPeArray) {
                            AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, n, kFVWmcqOBgYFxPgeswapvPe.DSP(), n2, n2 * kFVWmcqOBgYFxPgeswapvPe.FFT(), (float)n2 * (float)n, bl);
                            DataLine.Info info2 = new DataLine.Info(clazz, audioFormat);
                            if (!AudioSystem.getMixer(info).isLineSupported(info2)) continue;
                            try (DataLine dataLine = (DataLine)AudioSystem.getMixer(info).getLine(info2);){
                                dataLine.open();
                                sAVBeCYeaIWVZOjBqiIeTcH2.DSP(kFVWmcqOBgYFxPgeswapvPe);
                                sAVBeCYeaIWVZOjBqiIeTcH2.FFT(n2);
                                sAVBeCYeaIWVZOjBqiIeTcH2.DSP(n);
                            }
                            catch (LineUnavailableException lineUnavailableException) {
                                // empty catch block
                            }
                        }
                    }
                }
                if (sAVBeCYeaIWVZOjBqiIeTcH2.AdditionalMetadataValue().isEmpty() || sAVBeCYeaIWVZOjBqiIeTcH2.responseView().isEmpty() || sAVBeCYeaIWVZOjBqiIeTcH2.FFT().isEmpty()) continue;
                arrayList.add(sAVBeCYeaIWVZOjBqiIeTcH2);
            }
        }
        return arrayList;
    }
}

