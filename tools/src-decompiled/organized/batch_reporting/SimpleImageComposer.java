/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import com.xivero.hraa.Main;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import sdfgjkljljoftrytrszgijpokjprs.ReportingController;
import sdfgjkljljoftrytrszgijpokjprs.IImageComposer;

public class SimpleImageComposer
implements IImageComposer {
    private static final String DSP = String.format("MusicScope %s | www.xivero.com", Main.DSP.FFT(2));
    private final String FFT;

    public SimpleImageComposer(String string) {
        this.FFT = string;
    }

    @Override
    public BufferedImage DSP(ReportingController fiypGaFYdzscEBybnfTNrMU) {
        int n = 0;
        int n2 = 0;
        int n3 = 20;
        int n4 = 20;
        BufferedImage bufferedImage = fiypGaFYdzscEBybnfTNrMU.DSP("Format");
        BufferedImage bufferedImage2 = fiypGaFYdzscEBybnfTNrMU.DSP("History");
        BufferedImage bufferedImage3 = fiypGaFYdzscEBybnfTNrMU.DSP("Levels");
        BufferedImage bufferedImage4 = fiypGaFYdzscEBybnfTNrMU.DSP("Spectrum");
        BufferedImage bufferedImage5 = fiypGaFYdzscEBybnfTNrMU.DSP("Stereo");
        n += 10;
        n += bufferedImage4.getWidth();
        n2 += 50;
        n2 += bufferedImage.getHeight();
        n2 += 20;
        BufferedImage bufferedImage6 = new BufferedImage(n += 10, 945, 6);
        Graphics2D graphics2D = bufferedImage6.createGraphics();
        graphics2D.setBackground(Color.decode("#000000"));
        graphics2D.clearRect(0, 0, n, n2 += bufferedImage4.getHeight());
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(this.FFT, n3, n4);
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n5 = fontMetrics.stringWidth(DSP);
        graphics2D.drawString(DSP, n - n5 - 20, n4);
        graphics2D.drawImage((Image)bufferedImage, n3, n4 += 30, null);
        graphics2D.drawImage((Image)bufferedImage3, n3 += bufferedImage.getWidth() + 30, n4, null);
        graphics2D.drawImage((Image)bufferedImage2, n3 += bufferedImage3.getWidth() + 30, n4, null);
        n3 = bufferedImage4.getWidth() - 5 - bufferedImage5.getWidth();
        graphics2D.drawImage((Image)bufferedImage5, n3, n4, null);
        n3 = 10;
        graphics2D.drawImage((Image)bufferedImage4, n3, n4 += bufferedImage2.getHeight() + 20, null);
        graphics2D.dispose();
        return bufferedImage6;
    }
}

