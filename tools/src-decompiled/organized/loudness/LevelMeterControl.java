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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import sdfgjkljljoftrytrszgijpokjprs.LevelsModel;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormat;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerEventListener;
import sdfgjkljljoftrytrszgijpokjprs.IMetaInformationListener;
import sdfgjkljljoftrytrszgijpokjprs.LoudnessModel;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;
import sdfgjkljljoftrytrszgijpokjprs.ComposedModel;
import sdfgjkljljoftrytrszgijpokjprs.IComputationListener;
import sdfgjkljljoftrytrszgijpokjprs.ILevelMeterControlMidSideSwitch;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;
import sdfgjkljljoftrytrszgijpokjprs.ITrackLoadedListener;
import sdfgjkljljoftrytrszgijpokjprs.IAnalyzerStartListener;

public class LevelMeterControl
extends JComponent
implements MouseListener,
Runnable,
IPlayerStateListener,
IPlayerEventListener,
IMetaInformationListener,
IComputationListener<ComposedModel>,
ITrackLoadedListener,
IAnalyzerStartListener {
    private static final long serialVersionUID = 1L;
    private final ArrayList<ILevelMeterControlMidSideSwitch> DSP = new ArrayList(0);
    private LevelsModel FFT;
    private LoudnessModel responseView;
    private final int AdditionalMetadataValue = 295;
    private final int AudioFileExtension = 1;
    private final double IAudioFileCodec = 0.05;
    private boolean IAudioInputStream = false;
    private int IAudioMetaInformation = 1;

    public void DSP(ILevelMeterControlMidSideSwitch htmDAEARyzYawOjAqLxxApa2) {
        if (this.DSP != null && !this.DSP.contains(htmDAEARyzYawOjAqLxxApa2)) {
            this.DSP.add(htmDAEARyzYawOjAqLxxApa2);
        }
    }

    private void DSP(boolean bl) {
        if (this.DSP != null) {
            for (ILevelMeterControlMidSideSwitch htmDAEARyzYawOjAqLxxApa2 : this.DSP) {
                htmDAEARyzYawOjAqLxxApa2.FFT(bl);
            }
        }
    }

    public LevelMeterControl() {
        this.DSP();
    }

    private void DSP() {
        this.FFT = new LevelsModel(-60.0, -60.0, -60.0, -60.0, -60.0, -60.0, 0, 0, -60.0, -60.0, 0.0, 0.0, 0.0, 0.0, "", "", false, false, -60.0, -60.0, -60.0, -60.0, -60.0, -60.0, -60.0, -60.0, 0.0, 0.0, new int[2000], new int[2000]);
        this.responseView = new LoudnessModel(-60.0, -60.0, -60.0, -60.0, -60.0, 0.0, new int[800], 1, new int[800], 1, 0, 0, 0, 0.0, 0.0);
        this.repaint();
    }

    private void FFT() {
        this.FFT = new LevelsModel(-60.0, -60.0, -60.0, -60.0, this.FFT.AudioFileExtension(), this.FFT.IAudioFileCodec(), this.FFT.IAudioInputStream(), this.FFT.IAudioMetaInformation(), -60.0, -60.0, 0.0, 0.0, 0.0, 0.0, this.FFT.AiffMetaDataModel(), this.FFT.AlacAudioCodec(), this.FFT.AlacMetaDataModel(), this.FFT.BufferedAlacReader(), -60.0, -60.0, -60.0, -60.0, this.FFT.AlacUtils(), this.FFT.ChunkInfo(), -60.0, -60.0, 0.0, 0.0, this.FFT.QTMovieT(), this.FFT.SampleDuration());
        this.responseView = new LoudnessModel(-60.0, -60.0, this.responseView.responseView(), this.responseView.AdditionalMetadataValue(), this.responseView.AudioFileExtension(), this.responseView.IAudioFileCodec(), this.responseView.IAudioInputStream(), this.responseView.IAudioMetaInformation(), this.responseView.IBaseAudioCodec(), this.responseView.MetaInfomationCopy(), this.responseView.AacAudioCodec(), this.responseView.AacMetaDataModel(), this.responseView.BufferedAacReader(), 0.0, 0.0);
        this.repaint();
    }

    @Override
    public void DSP(ComposedModel dbWUuIVxtlDbLbZVylyCBng2, ComputationController.DSP rHAjVyBgPhqkQKsOvJMPMYn2) {
        if (rHAjVyBgPhqkQKsOvJMPMYn2 == ComputationController.DSP.AudioFileExtension) {
            this.responseView = new LoudnessModel((LoudnessModel)dbWUuIVxtlDbLbZVylyCBng2.DSP(ComputationController.DSP.responseView));
            this.FFT = new LevelsModel((LevelsModel)dbWUuIVxtlDbLbZVylyCBng2.DSP(ComputationController.DSP.FFT));
        }
        if (this.responseView != null && this.FFT != null) {
            EventQueue.invokeLater(this);
        }
    }

    public double DSP(double d) {
        d = 295 - (int)(72.5 * (Math.pow(10.0, (d + 60.0) / 90.0) - 1.0));
        return d;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        int n;
        Graphics2D graphics2D = (Graphics2D)graphics;
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        String[] stringArray = new String[]{"3", "0", "-3", "-6", "-12", "-20", "-30", "-40", "-50", "-60"};
        double[] dArray = new double[]{3.0, 0.0, -3.0, -6.0, -12.0, -20.0, -30.0, -40.0, -50.0, -60.0};
        for (int i = 0; i < 10; ++i) {
            n = (int)this.DSP(dArray[i]);
            if (i <= 1) {
                graphics2D.setColor(Color.decode("#AA0000"));
                graphics2D.drawLine(30, n, 89, n);
                graphics2D.setColor(Color.decode("#FF0000"));
                graphics2D.drawString(stringArray[i], 25 - fontMetrics.stringWidth(stringArray[i]), n + 5);
                continue;
            }
            graphics2D.setColor(Color.decode("#555555"));
            graphics2D.drawLine(30, n, 89, n);
            graphics2D.setColor(Color.decode("#EEEEEE"));
            graphics2D.drawString(stringArray[i], 25 - fontMetrics.stringWidth(stringArray[i]), n + 5);
        }
        if (this.FFT != null && this.responseView != null) {
            double d;
            double d2;
            double d3;
            double d4;
            double d5;
            double d6;
            double d7;
            double d8;
            double d9;
            double d10;
            double d11;
            double d12;
            graphics2D.setColor(Color.decode("#55DDFF"));
            double d13 = this.responseView.FFT();
            graphics2D.setStroke(new BasicStroke(3.0f));
            graphics2D.setColor(Color.decode("#55DDFF"));
            double d14 = (int)this.DSP(-this.responseView.AiffAudioCodec());
            graphics2D.drawLine(42, 31, 42, (int)d14 - 1);
            graphics2D.setStroke(new BasicStroke(2.0f));
            if (this.IAudioInputStream) {
                d12 = (int)this.DSP(this.FFT.AlacContextModel());
                d11 = (int)this.DSP(this.FFT.AlacDecoderUtils());
                d10 = (int)this.DSP(this.FFT.DemuxResT());
                d9 = (int)this.DSP(this.FFT.DemuxUtils());
                d8 = this.FFT.AlacFile();
                d7 = this.FFT.AlacInputStream();
                d6 = (int)this.DSP(d8);
                d5 = (int)this.DSP(d7);
                d4 = this.FFT.AlacUtils();
                d3 = this.FFT.ChunkInfo();
                d2 = (int)this.DSP(d4);
                d = (int)this.DSP(d3);
            } else {
                d12 = (int)this.DSP(this.FFT.DSP());
                d11 = (int)this.DSP(this.FFT.FFT());
                d10 = (int)this.DSP(this.FFT.IBaseAudioCodec());
                d9 = (int)this.DSP(this.FFT.MetaInfomationCopy());
                d8 = this.FFT.responseView();
                d7 = this.FFT.AdditionalMetadataValue();
                d6 = (int)this.DSP(d8);
                d5 = (int)this.DSP(d7);
                d4 = this.FFT.AudioFileExtension();
                d3 = this.FFT.IAudioFileCodec();
                d2 = (int)this.DSP(d4);
                d = (int)this.DSP(d3);
            }
            graphics2D.setColor(Color.decode("#00AA00"));
            graphics2D.fillRect(30, (int)d12, 10, (int)(295.0 - d12));
            if (this.IAudioInputStream) {
                graphics2D.setColor(Color.decode("#E89A20"));
            }
            graphics2D.fillRect(45, (int)d11, 10, (int)(295.0 - d11));
            graphics2D.setColor(Color.decode("#00FF00"));
            graphics2D.drawLine(31, (int)d10, 39, (int)d10);
            graphics2D.drawLine(46, (int)d9, 54, (int)d9);
            if (d8 >= 0.05) {
                graphics2D.setColor(Color.decode("#DD0000"));
            } else {
                graphics2D.setColor(Color.decode("#00BB00"));
            }
            graphics2D.drawLine(31, (int)d6, 39, (int)d6);
            if (d7 >= 0.05) {
                graphics2D.setColor(Color.decode("#DD0000"));
            } else {
                graphics2D.setColor(Color.decode("#00BB00"));
            }
            graphics2D.drawLine(46, (int)d5, 54, (int)d5);
            if (d4 >= 0.05) {
                graphics2D.setColor(Color.decode("#DD0000"));
            } else {
                graphics2D.setColor(Color.decode("#00E86F"));
            }
            graphics2D.drawLine(31, (int)d2, 39, (int)d2);
            if (d3 >= 0.05) {
                graphics2D.setColor(Color.decode("#DD0000"));
            } else {
                graphics2D.setColor(Color.decode("#00E86F"));
            }
            graphics2D.drawLine(46, (int)d, 54, (int)d);
            graphics2D.setColor(Color.decode("#006F96"));
            double d15 = (int)this.DSP(this.responseView.DSP());
            graphics2D.fillRect(70, (int)d15, 20, (int)(295.0 - d15));
            graphics2D.setColor(Color.decode("#E89A20"));
            double d16 = (int)this.DSP(this.responseView.FFT());
            graphics2D.drawLine(71, (int)d16, 89, (int)d16);
            graphics2D.setColor(Color.decode("#006F96"));
            double d17 = (int)this.DSP(this.responseView.responseView());
            graphics2D.drawLine(71, (int)d17, 89, (int)d17);
            graphics2D.setColor(Color.decode("#E89A20"));
            double d18 = (int)this.DSP(this.responseView.AdditionalMetadataValue());
            graphics2D.drawLine(90, (int)d18, 95, (int)d18);
            graphics2D.drawLine(65, (int)d18, 70, (int)d18);
            graphics2D.setColor(Color.decode("#EEEEEE"));
            d18 = (int)this.DSP(this.responseView.AudioFileExtension());
            graphics2D.drawLine(75, (int)d18, 85, (int)d18);
            graphics2D.setStroke(new BasicStroke(1.0f));
            if (this.IAudioMetaInformation == 0) {
                graphics2D.setColor(Color.decode("#006F96"));
                int n2 = this.responseView.IAudioMetaInformation();
                for (int i = -600; i <= 30; ++i) {
                    n = (int)this.DSP((double)i / 10.0);
                    int n3 = Math.round(i + 700);
                    int n4 = 105 + (int)(60.0 * ((double)this.responseView.IAudioInputStream()[n3] / (double)n2));
                    if (n4 >= 170) continue;
                    graphics2D.drawLine(105, n, n4, n);
                }
                graphics2D.drawString("M-Mode", 165 - fontMetrics.stringWidth("M-Mode"), 10);
            } else if (this.IAudioMetaInformation == 1) {
                graphics2D.setColor(Color.decode("#E89A20"));
                int n5 = this.responseView.MetaInfomationCopy();
                for (int i = -600; i <= 30; ++i) {
                    n = (int)this.DSP((double)i / 10.0);
                    int n6 = Math.round(i + 700);
                    int n7 = 105 + (int)(60.0 * ((double)this.responseView.IBaseAudioCodec()[n6] / (double)n5));
                    if (n7 >= 170) continue;
                    graphics2D.drawLine(105, n, n7, n);
                }
                graphics2D.drawString("S-Mode", 165 - fontMetrics.stringWidth("S-Mode"), 10);
                if (this.responseView.BufferedAacReader() == 1) {
                    graphics2D.setColor(Color.decode("#FFFFFF"));
                    n = (int)this.DSP((double)(this.responseView.AacAudioCodec() - 700) / 10.0);
                    graphics2D.drawLine(150, n, 160, n);
                    int n8 = (int)this.DSP((double)(this.responseView.AacMetaDataModel() - 700) / 10.0);
                    graphics2D.drawLine(150, n8, 160, n8);
                    graphics2D.drawLine(155, n, 155, n8);
                    String string = String.valueOf((double)Math.round(this.responseView.IAudioFileCodec() * 10.0) / 10.0);
                    graphics2D.drawString(string, 166 - fontMetrics.stringWidth(string), n8 - 5);
                }
            } else if (this.IAudioMetaInformation == 2) {
                int n9;
                int n10 = this.FFT.QTMovieT()[1999];
                if (n10 == 0) {
                    n10 = 1;
                }
                graphics2D.setColor(Color.decode("#BBBBBB"));
                graphics2D.drawLine(105, 295, 105, 5);
                int n11 = 105;
                int n12 = 105;
                int n13 = n9 = (int)this.DSP(-60.0);
                for (int i = -600; i <= 30; ++i) {
                    n = (int)this.DSP((double)i / 10.0);
                    int n14 = (int)(105.0 + 60.0 * (double)this.FFT.SampleDuration()[i + 600] / (double)n10);
                    graphics2D.setColor(Color.decode("#0000FF"));
                    graphics2D.drawLine(n11, n9, n14, n);
                    n11 = n14;
                    n9 = n;
                    n14 = (int)(105.0 + 60.0 * (double)this.FFT.QTMovieT()[i + 600] / (double)n10);
                    graphics2D.setColor(Color.decode("#00FF00"));
                    graphics2D.drawLine(n12, n13, n14, n);
                    n12 = n14;
                    n13 = n;
                }
                graphics2D.setColor(Color.decode("#00E86F"));
                graphics2D.drawString("TPL", 150 - fontMetrics.stringWidth("TPL"), 10);
            }
        }
        graphics2D.setFont(graphics2D.getFont().deriveFont(12.0f));
        graphics2D.setColor(Color.decode("#EEEEEE"));
        graphics2D.drawString("LU", 89 - fontMetrics.stringWidth("LU"), 290);
        if (!this.IAudioInputStream) {
            graphics2D.drawString("L", 38 - fontMetrics.stringWidth("L"), 290);
            graphics2D.drawString("R", 54 - fontMetrics.stringWidth("R"), 290);
        } else {
            graphics2D.setColor(Color.decode("#FF0000"));
            graphics2D.drawString("M", 41 - fontMetrics.stringWidth("M"), 290);
            graphics2D.drawString("S", 54 - fontMetrics.stringWidth("S"), 290);
        }
    }

    @Override
    public void run() {
        this.repaint();
    }

    @Override
    public void DSP(String string) {
        this.DSP();
    }

    @Override
    public void DSP(PlayerState zjoyaRSokkGYDwXHPKTBIiX) {
        if (zjoyaRSokkGYDwXHPKTBIiX == PlayerState.AdditionalMetadataValue) {
            this.FFT();
        }
    }

    @Override
    public boolean DSP(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2, Object object) {
        switch (lXCOGwZXVelwEKHMMPwpSAO2) {
            case AudioFileExtension: {
                this.DSP();
                this.FFT();
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
        if (mouseEvent.getSource() == this) {
            int n = mouseEvent.getX();
            int n2 = mouseEvent.getY();
            if (n > 0 && n < 100) {
                this.IAudioInputStream = !this.IAudioInputStream;
                this.DSP(this.IAudioInputStream);
                this.repaint();
            }
            if (n > 105 && n < 170) {
                this.IAudioMetaInformation = this.IAudioMetaInformation < 2 ? ++this.IAudioMetaInformation : 0;
                this.repaint();
            }
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

    @Override
    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA2) {
        if (yGjBevanihqaxYKnUNtrNeA2.MetaInfomationCopy().FFT() == AudioFormat.FFT) {
            // empty if block
        }
    }
}

