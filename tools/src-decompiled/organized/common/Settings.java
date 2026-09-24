/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import sdfgjkljljoftrytrszgijpokjprs.OperatingSystem;

public class Settings {
    private static final String DSP = OperatingSystem.DSP("MusicScope", "SystemSettings.properties");
    private static final Object FFT = new Object();
    private static Settings responseView;
    private final Properties AdditionalMetadataValue = new Properties();

    private Settings() {
        this.FFT(DSP);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Settings DSP() {
        if (responseView == null) {
            Object object = FFT;
            synchronized (object) {
                if (responseView == null) {
                    responseView = new Settings();
                }
            }
        }
        return responseView;
    }

    public String DSP(String string) {
        return this.AdditionalMetadataValue.getProperty(string);
    }

    public void DSP(String string, String string2) {
        this.AdditionalMetadataValue.setProperty(string, string2);
        this.responseView(DSP);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void FFT(String string) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(string);
            this.AdditionalMetadataValue.load(fileInputStream);
        }
        catch (IOException iOException) {
        }
        finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            }
            catch (IOException iOException) {}
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void responseView(String string) {
        File file = new File(string);
        FileOutputStream fileOutputStream = null;
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            fileOutputStream = new FileOutputStream(file);
            this.AdditionalMetadataValue.store(fileOutputStream, null);
        }
        catch (IOException iOException) {
        }
        finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                }
                catch (IOException iOException) {}
            }
        }
    }
}

