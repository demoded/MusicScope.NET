/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class OperatingSystem {
    private static final String DSP = System.getProperty("os.name").toLowerCase();

    public static DSP DSP() {
        return sdfgjkljljoftrytrszgijpokjprs.OperatingSystem$rHAjVyBgPhqkQKsOvJMPMYn.DSP(DSP);
    }

    public static boolean FFT() {
        return sdfgjkljljoftrytrszgijpokjprs.OperatingSystem$rHAjVyBgPhqkQKsOvJMPMYn.DSP(DSP) == sdfgjkljljoftrytrszgijpokjprs.OperatingSystem$rHAjVyBgPhqkQKsOvJMPMYn.DSP;
    }

    public static boolean responseView() {
        return sdfgjkljljoftrytrszgijpokjprs.OperatingSystem$rHAjVyBgPhqkQKsOvJMPMYn.DSP(DSP) == sdfgjkljljoftrytrszgijpokjprs.OperatingSystem$rHAjVyBgPhqkQKsOvJMPMYn.FFT;
    }

    public static boolean AdditionalMetadataValue() {
        return sdfgjkljljoftrytrszgijpokjprs.OperatingSystem$rHAjVyBgPhqkQKsOvJMPMYn.DSP(DSP) == sdfgjkljljoftrytrszgijpokjprs.OperatingSystem$rHAjVyBgPhqkQKsOvJMPMYn.responseView;
    }

    public static String DSP(String string, String string2) {
        if (OperatingSystem.FFT()) {
            return System.getenv("APPDATA") + File.separator + string + File.separator + string2;
        }
        if (OperatingSystem.responseView()) {
            return System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + File.separator + string + File.separator + string2;
        }
        return string2;
    }

    public static enum DSP {
        DSP("win"),
        FFT("mac"),
        responseView("nix", "nux", "aix", "linux"),
        AdditionalMetadataValue("sunos"),
        AudioFileExtension("None");

        private final String[] IAudioFileCodec;

        private DSP(String ... stringArray) {
            this.IAudioFileCodec = stringArray;
        }

        public List<String> DSP() {
            return Arrays.asList(this.IAudioFileCodec);
        }

        public static DSP DSP(String string) {
            DSP[] rHAjVyBgPhqkQKsOvJMPMYnArray;
            for (DSP rHAjVyBgPhqkQKsOvJMPMYn2 : rHAjVyBgPhqkQKsOvJMPMYnArray = sdfgjkljljoftrytrszgijpokjprs.OperatingSystem$rHAjVyBgPhqkQKsOvJMPMYn.values()) {
                List<String> list = rHAjVyBgPhqkQKsOvJMPMYn2.DSP();
                for (String string2 : list) {
                    if (!string.contains(string2)) continue;
                    return rHAjVyBgPhqkQKsOvJMPMYn2;
                }
            }
            return AudioFileExtension;
        }
    }
}

