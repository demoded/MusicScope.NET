/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import sdfgjkljljoftrytrszgijpokjprs.StereoMeterModel;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.LevelMeterControl;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerEventListener;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;
import sdfgjkljljoftrytrszgijpokjprs.IComputationListener;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;
import sdfgjkljljoftrytrszgijpokjprs.IAnalyzerStartListener;

public class StereoMeterControl
extends JComponent
implements MouseListener,
Runnable,
IPlayerStateListener,
IPlayerEventListener,
IComputationListener<StereoMeterModel>,
IAnalyzerStartListener {
    private static final long serialVersionUID = 1L;
    private StereoMeterModel DSP = new StereoMeterModel(0, new double[1], new double[1], new double[1], 0.0, new double[1]);
    private int FFT = -1;
    private int responseView = 1;
    private double AdditionalMetadataValue = 0.0;
    private double AudioFileExtension = 0.0;
    private double IAudioFileCodec = 0.0;
    private final BufferedImage IAudioInputStream = new BufferedImage(400, 400, 1);
    private int IAudioMetaInformation = 0;
    private final int[][] IBaseAudioCodec = new int[300][300];
    private final int MetaInfomationCopy = 5000;
    private final int[] AacAudioCodec = new int[5001];
    private final int[] AacMetaDataModel = new int[5001];
    private final Color[] BufferedAacReader = new Color[256];

    public StereoMeterControl() {
        this.DSP();
    }

    private void DSP() {
        this.DSP = new StereoMeterModel(5000, new double[5000], new double[5000], new double[5000], 0.0, new double[100]);
        this.responseView = 1;
        this.AdditionalMetadataValue = 0.0;
        this.AudioFileExtension = 0.0;
        this.IAudioMetaInformation = 0;
        for (int i = 0; i < 250; ++i) {
            for (int j = 0; j < 250; ++j) {
                this.IAudioInputStream.setRGB(j, i, 0);
                this.IBaseAudioCodec[j][i] = 0;
            }
        }
        for (int i = 0; i < 256; ++i) {
            this.BufferedAacReader[i] = new Color(0, i, 0);
        }
        this.repaint();
    }

    private void FFT() {
        int n;
        int n2 = 0;
        double d = 0.0;
        this.DSP = new StereoMeterModel(5000, new double[5000], new double[5000], new double[5000], 0.0, new double[100]);
        this.responseView = 0;
        this.IAudioFileCodec = 0.0;
        this.IAudioMetaInformation = 1;
        for (n = 0; n < 250; ++n) {
            for (n2 = 0; n2 < 250; ++n2) {
                if (n2 == 100 || n == 105 || !(d < (double)this.IBaseAudioCodec[n2][n])) continue;
                d = this.IBaseAudioCodec[n2][n];
            }
        }
        for (n = 0; n < 250; ++n) {
            for (n2 = 0; n2 < 250; ++n2) {
                double d2 = 10000.0 * ((double)this.IBaseAudioCodec[n2][n] / d);
                if (d2 > 0.0) {
                    d2 = (int)(70.0 * Math.log10(d2 + 1.0));
                }
                if (d2 > 255.0) {
                    d2 = 255.0;
                }
                int n3 = 0 | (int)d2 << 8 | 0;
                this.IAudioInputStream.setRGB(n2, n, n3);
            }
        }
        for (int i = 0; i < 5000; ++i) {
            this.AacMetaDataModel[i] = 0;
            this.AacAudioCodec[i] = 0;
        }
        this.repaint();
    }

    @Override
    public void DSP(StereoMeterModel fLTjkCbqdFcYQIXMwzSzQor, ComputationController.DSP rHAjVyBgPhqkQKsOvJMPMYn2) {
        if (rHAjVyBgPhqkQKsOvJMPMYn2 == ComputationController.DSP.AdditionalMetadataValue) {
            this.DSP = new StereoMeterModel(fLTjkCbqdFcYQIXMwzSzQor);
            this.IAudioFileCodec = this.DSP.AudioFileExtension();
            if (this.AdditionalMetadataValue > this.IAudioFileCodec && this.responseView == 1) {
                this.AdditionalMetadataValue = this.IAudioFileCodec;
            }
            if (this.AudioFileExtension < this.IAudioFileCodec && this.responseView == 1) {
                this.AudioFileExtension = this.IAudioFileCodec;
            }
            if (this.AdditionalMetadataValue < 1.0 && this.AdditionalMetadataValue < this.IAudioFileCodec) {
                this.AdditionalMetadataValue += 4.0E-4;
            }
            if (this.AudioFileExtension > -1.0 && this.AudioFileExtension > this.IAudioFileCodec) {
                this.AudioFileExtension -= 4.0E-4;
            }
            for (int i = 0; i < 5000; ++i) {
                this.AacAudioCodec[i] = (int)(100.0 + 110.0 * this.DSP(this.DSP.responseView()[i]));
                this.AacMetaDataModel[i] = (int)(105.0 + 110.0 * this.DSP(this.DSP.AdditionalMetadataValue()[i]));
                if (this.AacAudioCodec[i] <= 0 || this.AacMetaDataModel[i] <= 0) continue;
                int[] nArray = this.IBaseAudioCodec[this.AacAudioCodec[i]];
                int n = this.AacMetaDataModel[i];
                nArray[n] = nArray[n] + 1;
            }
            EventQueue.invokeLater(this);
        }
    }

    public double DSP(double d) {
        d = d < 0.0 ? -Math.log10(5.0 * Math.abs(d) + 1.0) : Math.log10(5.0 * d + 1.0);
        return d;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        int n;
        Color color;
        int n2;
        int n3;
        if (this.DSP == null) {
            return;
        }
        Graphics2D graphics2D = (Graphics2D)graphics;
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        boolean bl = false;
        int n4 = 25;
        if (this.IAudioMetaInformation != 0) {
            graphics.drawImage(this.IAudioInputStream, 0, n4, null);
        }
        graphics2D.setColor(Color.decode("#EEEEEE"));
        graphics2D.drawString("+L", 0, n4 + 10);
        graphics2D.drawString("+R", 200 - fontMetrics.stringWidth("+R"), n4 + 10);
        graphics2D.drawString("-R", 0, n4 + 210);
        graphics2D.drawString("-L", 200 - fontMetrics.stringWidth("-L"), n4 + 210);
        graphics2D.setColor(Color.decode("#BB0000"));
        graphics2D.drawString("-1", 0, n4 + 275);
        graphics2D.fillRect(0, 250 + n4, 81, 8);
        graphics2D.setColor(Color.decode("#DDDD00"));
        graphics2D.drawString("0", 100 - fontMetrics.stringWidth("0") / 2, 275 + n4);
        graphics2D.fillRect(81, 250 + n4, 40, 8);
        graphics2D.setColor(Color.decode("#009900"));
        graphics2D.fillRect(120, 250 + n4, 80, 8);
        graphics2D.drawString("+1", 200 - fontMetrics.stringWidth("+1"), 275 + n4);
        graphics2D.setColor(Color.decode("#333333"));
        graphics2D.drawString("out of", 10, 100 + n4);
        graphics2D.drawString("phase", 10, 120 + n4);
        graphics2D.drawString("out of", 150, 100 + n4);
        graphics2D.drawString("phase", 150, 120 + n4);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(Color.decode("#444444"));
        graphics2D.drawLine(20, n4 + 20, 180, n4 + 190);
        graphics2D.drawLine(20, n4 + 190, 180, n4 + 20);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        for (n3 = 0; n3 < 5000; ++n3) {
            graphics2D.drawLine(this.AacAudioCodec[n3], this.AacMetaDataModel[n3] + n4, this.AacAudioCodec[n3], this.AacMetaDataModel[n3] + n4);
            n2 = (int)(255.0 * this.DSP.FFT()[n3]);
            if (this.FFT == n2) continue;
            this.FFT = n2;
            color = this.BufferedAacReader[n2];
            graphics2D.setColor(color);
        }
        for (n3 = 0; n3 < 100; ++n3) {
            n2 = (int)(2.55 * (double)n3);
            color = this.BufferedAacReader[n2];
            graphics2D.setColor(color);
            n = (int)(100.0 + this.DSP.IAudioFileCodec()[n3] * 100.0);
            graphics2D.drawLine(n, n4 + 220, n, n4 + 230);
        }
        graphics2D.setColor(Color.decode("#EEEEEE"));
        graphics2D.setStroke(new BasicStroke(2.0f));
        n = (int)(100.0 + this.IAudioFileCodec * 100.0);
        graphics2D.drawLine(n, 248 + n4, n, 260 + n4);
        graphics2D.setStroke(new BasicStroke(1.0f));
        graphics2D.setColor(Color.decode("#FFFFFF"));
        n = (int)(100.0 + this.AdditionalMetadataValue * 100.0);
        graphics2D.drawLine(n, 250 + n4, n, 257 + n4);
        n = (int)(100.0 + this.AudioFileExtension * 100.0);
        graphics2D.drawLine(n, 250 + n4, n, 257 + n4);
    }

    @Override
    public void run() {
        this.repaint();
    }

    @Override
    public void DSP(PlayerState zjoyaRSokkGYDwXHPKTBIiX) {
        switch (zjoyaRSokkGYDwXHPKTBIiX) {
            case AdditionalMetadataValue: {
                this.FFT();
                break;
            }
            case responseView: 
            case FFT: {
                this.responseView = 1;
                break;
            }
            case DSP: {
                this.DSP();
            }
        }
    }

    @Override
    public boolean DSP(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2, Object object) {
        switch (lXCOGwZXVelwEKHMMPwpSAO2) {
            case AudioFileExtension: {
                this.DSP();
            }
        }
        return true;
    }

    @Override
    public void AdditionalMetadataValue() {
        this.DSP();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int n;
        if (mouseEvent.getSource().getClass() != LevelMeterControl.class || (n = mouseEvent.getX()) <= 0 || n < 100) {
            // empty if block
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }
}

