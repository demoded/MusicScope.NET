/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import sdfgjkljljoftrytrszgijpokjprs.IValueComposer;
import sdfgjkljljoftrytrszgijpokjprs.IScreenReporting;
import sdfgjkljljoftrytrszgijpokjprs.IValueReporting;
import sdfgjkljljoftrytrszgijpokjprs.IValueCollector;
import sdfgjkljljoftrytrszgijpokjprs.IImageComposer;

public class ReportingController {
    private static final ReportingController DSP = new ReportingController();
    private final HashMap<String, IScreenReporting> FFT;
    private final ArrayList<IValueReporting<?>> responseView = new ArrayList(0);

    private ReportingController() {
        this.FFT = new HashMap(0);
    }

    public static ReportingController DSP() {
        return DSP;
    }

    public void DSP(String string, IScreenReporting mjycquzYfQnWsdeiKxtdpyM2) {
        if (this.FFT != null && !this.FFT.containsKey(string)) {
            this.FFT.put(string, mjycquzYfQnWsdeiKxtdpyM2);
        }
    }

    public BufferedImage DSP(String string) {
        IScreenReporting mjycquzYfQnWsdeiKxtdpyM2 = this.FFT.get(string);
        if (mjycquzYfQnWsdeiKxtdpyM2 != null) {
            return mjycquzYfQnWsdeiKxtdpyM2.BufferedAlacReader();
        }
        return null;
    }

    public void DSP(IImageComposer zQAHKnBQNWhKYbQLXMAgfmk2, String string) {
        BufferedImage bufferedImage = zQAHKnBQNWhKYbQLXMAgfmk2.DSP(DSP);
        try {
            ImageIO.write((RenderedImage)bufferedImage, "PNG", new File(string));
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void DSP(IValueReporting<?> oJKdRGEwipDKnLCbDZfxxHL2, int n) {
        int n2 = n;
        if (oJKdRGEwipDKnLCbDZfxxHL2 != null && !this.responseView.contains(oJKdRGEwipDKnLCbDZfxxHL2)) {
            n2 = n2 < 0 ? 0 : n2;
            n2 = n2 > this.responseView.size() ? this.responseView.size() : n2;
            this.responseView.add(n2, oJKdRGEwipDKnLCbDZfxxHL2);
        }
    }

    public ArrayList<IValueReporting<?>> FFT() {
        return this.responseView;
    }

    public void DSP(IValueComposer dagtpHXBKJqWifDEjrWyNPG, String string) {
        File file = new File(string);
        StringBuilder stringBuilder = dagtpHXBKJqWifDEjrWyNPG.FFT(DSP);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.append(stringBuilder.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        }
        catch (IOException iOException) {
            Logger.getLogger(ReportingController.class.getName()).log(Level.SEVERE, null, iOException);
        }
    }

    public void DSP(IValueCollector qJjaUDPMXHYSaEdpDZAtvaH2) {
        qJjaUDPMXHYSaEdpDZAtvaH2.DSP(DSP);
    }
}

