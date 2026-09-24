/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class AudioLevelDisplay
extends JComponent
implements Runnable {
    private double DSP = -60.0;
    private final double FFT = 100.0 / Math.abs(this.DSP);

    public void DSP(double d) {
        double d2 = d > 0.001 ? 20.0 * Math.log10(d) : -60.0;
        if (this.DSP < d2) {
            this.DSP = d2;
        }
        if (this.DSP > d2) {
            this.DSP -= 1.0;
        }
        this.DSP();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        double d = Math.round(this.DSP);
        graphics2D.drawLine(0, 0, 100, 0);
        graphics2D.drawLine(0, 0, 0, 19);
        graphics2D.drawLine(100, 0, 100, 19);
        graphics2D.drawLine(0, 19, 100, 19);
        if (d > -1.0) {
            graphics2D.setColor(Color.decode("#FF0000"));
        } else if (d >= -20.0) {
            graphics2D.setColor(Color.decode("#00DD00"));
        } else {
            graphics2D.setColor(Color.decode("#0000FF"));
        }
        String string = String.valueOf(d) + " dB";
        graphics2D.drawString(string, 75 - fontMetrics.stringWidth(string), 15);
        int n = (int)(100.0 + this.DSP * this.FFT);
        graphics2D.setStroke(new BasicStroke(2.0f));
        graphics2D.drawLine(n, 2, n, 18);
    }

    private void DSP() {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                AudioLevelDisplay.this.repaint();
            }
        });
    }

    @Override
    public void run() {
    }
}

