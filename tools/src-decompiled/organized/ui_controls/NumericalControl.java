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
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import sdfgjkljljoftrytrszgijpokjprs.LevelsModel;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormat;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.BitDepthCutOffFrequencyModel;
import sdfgjkljljoftrytrszgijpokjprs.LevelMeterControl;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerEventListener;
import sdfgjkljljoftrytrszgijpokjprs.IMetaInformationListener;
import sdfgjkljljoftrytrszgijpokjprs.LoudnessModel;
import sdfgjkljljoftrytrszgijpokjprs.ISwitchLabelClickedListener;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;
import sdfgjkljljoftrytrszgijpokjprs.ComposedModel;
import sdfgjkljljoftrytrszgijpokjprs.IComputationListener;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;
import sdfgjkljljoftrytrszgijpokjprs.ITrackLoadedListener;
import sdfgjkljljoftrytrszgijpokjprs.IAnalyzerStartListener;

public class NumericalControl
extends JComponent
implements MouseListener,
Runnable,
IPlayerStateListener,
IPlayerEventListener,
IMetaInformationListener,
ISwitchLabelClickedListener,
IComputationListener<Object>,
ITrackLoadedListener,
IAnalyzerStartListener {
    private static final long serialVersionUID = 1L;
    private LevelsModel DSP;
    private LoudnessModel FFT;
    private BitDepthCutOffFrequencyModel responseView;
    private int AdditionalMetadataValue = 0;
    private int AudioFileExtension = 24;
    private boolean IAudioFileCodec = false;
    private boolean IAudioInputStream = false;
    private boolean IAudioMetaInformation = false;
    private boolean IBaseAudioCodec = true;
    private double MetaInfomationCopy = 0.0;
    private double AacAudioCodec = 0.0;
    private double AacMetaDataModel = 0.0;
    private double BufferedAacReader = 0.0;
    private double AiffAudioCodec = 0.0;
    private double AiffMetaDataModel = 0.0;
    private double AlacAudioCodec;
    private final double AlacMetaDataModel = 0.05;
    private final int[] BufferedAlacReader = new int[64];
    private final int[] AlacContextModel = new int[64];
    private final int[] AlacDecoderUtils = new int[64];
    private final int[] AlacFile = new int[64];
    private int AlacInputStream;
    private int AlacUtils;
    private boolean ChunkInfo = false;
    private IAudioMetaInformation DemuxResT;
    private final BufferedImage DemuxUtils = new BufferedImage(130, 50, 1);
    private final BufferedImage LeadingZeros = new BufferedImage(130, 50, 1);
    private int MyStream = 0;
    private double QTMovieT = 0.0;
    private double SampleDuration = 0.0;
    private int SampleInfo;

    public NumericalControl() {
        this.FFT();
    }

    private void FFT() {
        this.DSP = new LevelsModel(-60.0, -60.0, -60.0, -60.0, -60.0, -60.0, 0, 0, -60.0, -60.0, 0.0, 0.0, 0.0, 0.0, "", "", false, false, -60.0, -60.0, -60.0, -60.0, -60.0, -60.0, -60.0, -60.0, 0.0, 0.0, new int[2000], new int[2000]);
        this.FFT = new LoudnessModel(-60.0, -60.0, -60.0, -60.0, -60.0, 0.0, new int[800], 1, new int[800], 1, 0, 0, 0, 0.0, 0.0);
        this.responseView = new BitDepthCutOffFrequencyModel(0, 0);
        this.SampleInfo = this.AudioFileExtension;
        this.IAudioFileCodec = false;
        this.MetaInfomationCopy = 0.0;
        this.IAudioInputStream = false;
        this.AacAudioCodec = 0.0;
        this.AdditionalMetadataValue = 0;
        this.AiffMetaDataModel = 0.0;
        this.AiffAudioCodec = 0.0;
        this.BufferedAacReader = 0.0;
        this.AacMetaDataModel = 0.0;
        this.SampleDuration = 0.0;
        this.QTMovieT = 0.0;
        for (int i = 0; i < 64; ++i) {
            this.AlacFile[i] = 0;
            this.AlacDecoderUtils[i] = 0;
            this.AlacContextModel[i] = 0;
            this.BufferedAlacReader[i] = 0;
        }
        this.AlacUtils = 0;
        this.AlacInputStream = 0;
        this.MyStream = 0;
        int n = 0;
        for (int i = 0; i < 50; ++i) {
            for (int j = 0; j < 130; ++j) {
                this.DemuxUtils.setRGB(j, i, n);
                this.LeadingZeros.setRGB(j, i, n);
            }
        }
        this.repaint();
    }

    private void responseView() {
        this.DSP = new LevelsModel(-60.0, -60.0, -60.0, -60.0, this.DSP.AudioFileExtension(), this.DSP.IAudioFileCodec(), this.DSP.IAudioInputStream(), this.DSP.IAudioMetaInformation(), -60.0, -60.0, 0.0, 0.0, 0.0, 0.0, "", "", false, false, -60.0, -60.0, -60.0, -60.0, this.DSP.AlacUtils(), this.DSP.ChunkInfo(), -60.0, -60.0, 0.0, 0.0, this.DSP.QTMovieT(), this.DSP.SampleDuration());
        this.FFT = new LoudnessModel(-60.0, -60.0, this.FFT.responseView(), this.FFT.AdditionalMetadataValue(), this.FFT.AudioFileExtension(), this.FFT.IAudioFileCodec(), this.FFT.IAudioInputStream(), this.FFT.IAudioMetaInformation(), this.FFT.IBaseAudioCodec(), this.FFT.MetaInfomationCopy(), this.FFT.AacAudioCodec(), this.FFT.AacMetaDataModel(), this.FFT.BufferedAacReader(), this.FFT.AiffAudioCodec(), this.FFT.AiffMetaDataModel());
        this.AlacAudioCodec = 0.0;
        this.repaint();
    }

    @Override
    public void run() {
        this.repaint();
    }

    private void AudioFileExtension() {
        this.SampleInfo = this.AudioFileExtension - this.responseView.DSP();
        this.repaint();
    }

    void DSP() {
        String string = this.DSP.AiffMetaDataModel();
        String string2 = this.DSP.AlacAudioCodec();
        for (int i = 0; i < this.AudioFileExtension; ++i) {
            int n = i == 0 && this.DSP.AlacMetaDataModel() ? 1 : Character.getNumericValue(string.charAt(i));
            if (n != this.AlacDecoderUtils[i]) {
                this.AlacDecoderUtils[i] = n;
                int n2 = i;
                this.BufferedAlacReader[n2] = this.BufferedAlacReader[n2] + 1;
                if (this.AlacInputStream < this.BufferedAlacReader[i]) {
                    this.AlacInputStream = this.BufferedAlacReader[i];
                }
            }
            if ((n = i == 0 && this.DSP.BufferedAlacReader() ? 1 : Character.getNumericValue(string2.charAt(i))) == this.AlacFile[i]) continue;
            this.AlacFile[i] = n;
            int n3 = i;
            this.AlacContextModel[n3] = this.AlacContextModel[n3] + 1;
            if (this.AlacUtils >= this.AlacContextModel[i]) continue;
            this.AlacUtils = this.AlacContextModel[i];
        }
        if (this.AlacDecoderUtils[0] == 1) {
            this.SampleDuration -= 1.0;
        }
        if (this.AlacDecoderUtils[0] == 0) {
            this.SampleDuration += 1.0;
        }
        if (this.AlacFile[0] == 1) {
            this.QTMovieT -= 1.0;
        }
        if (this.AlacFile[0] == 0) {
            this.QTMovieT += 1.0;
        }
        if (Math.abs(this.SampleDuration) > 60.0) {
            this.SampleDuration = 0.0;
        }
        if (Math.abs(this.QTMovieT) > 60.0) {
            this.QTMovieT = 0.0;
        }
        this.SampleDuration = this.SampleDuration > 0.0 ? (this.SampleDuration -= 0.05) : (this.SampleDuration += 0.05);
        this.QTMovieT = this.QTMovieT > 0.0 ? (this.QTMovieT -= 0.05) : (this.QTMovieT += 0.05);
    }

    @Override
    public void DSP(Object object, ComputationController.DSP rHAjVyBgPhqkQKsOvJMPMYn2) {
        switch (rHAjVyBgPhqkQKsOvJMPMYn2) {
            case AudioFileExtension: {
                if (!(object instanceof ComposedModel)) {
                    return;
                }
                ComposedModel dbWUuIVxtlDbLbZVylyCBng2 = (ComposedModel)object;
                this.FFT = new LoudnessModel((LoudnessModel)dbWUuIVxtlDbLbZVylyCBng2.DSP(ComputationController.DSP.responseView));
                this.DSP = new LevelsModel((LevelsModel)dbWUuIVxtlDbLbZVylyCBng2.DSP(ComputationController.DSP.FFT));
                this.DSP();
                break;
            }
            case IAudioInputStream: {
                if (!(object instanceof BitDepthCutOffFrequencyModel)) {
                    return;
                }
                this.responseView = new BitDepthCutOffFrequencyModel((BitDepthCutOffFrequencyModel)object);
            }
        }
        EventQueue.invokeLater(this);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n = 40;
        int n2 = 190;
        double d = 0.0;
        int n3 = 0xC8C8C8;
        graphics2D.setColor(Color.decode("#666666"));
        if (this.IAudioMetaInformation) {
            graphics2D.drawString("Bit Monitor", 0, n);
            graphics2D.drawLine(0, n + 7, 130, n + 7);
            graphics2D.drawString("Left", 0, n + 25);
            graphics2D.drawString("Right", 0, n + 150);
        } else {
            graphics2D.drawString("True Peak Meter", 0, n);
            graphics2D.drawLine(0, n + 7, 130, n + 7);
            graphics2D.drawString("Loudness Full Scale", 0, n2);
            graphics2D.drawLine(0, n2 + 7, 130, n2 + 7);
            graphics2D.setColor(Color.decode("#EEEEEE"));
            graphics2D.drawString("TPL", 0, n + 25);
            graphics2D.drawString("RMS", 0, n + 50);
            graphics2D.drawString("CREST", 0, n + 75);
            graphics2D.drawString("PLR", 0, n + 100);
            graphics2D.drawString("M", 0, n2 + 25);
            graphics2D.drawString("S", 0, n2 + 50);
            graphics2D.drawString("I", 0, n2 + 75);
            graphics2D.drawString("LRA", 0, n2 + 100);
        }
        if (this.DSP != null && this.FFT != null) {
            if (!this.IAudioMetaInformation) {
                double d2;
                double d3;
                double d4;
                double d5;
                if (this.ChunkInfo) {
                    d5 = this.DSP.AlacUtils();
                    d4 = this.DSP.ChunkInfo();
                    d3 = this.DSP.DemuxResT();
                    d2 = this.DSP.DemuxUtils();
                } else {
                    d5 = this.DSP.AudioFileExtension();
                    d4 = this.DSP.IAudioFileCodec();
                    d3 = this.DSP.IBaseAudioCodec();
                    d2 = this.DSP.MetaInfomationCopy();
                }
                if (d5 < 0.05) {
                    graphics2D.setColor(Color.decode("#00E86F"));
                } else {
                    graphics2D.setColor(Color.decode("#EE0000"));
                }
                String string = String.valueOf((double)Math.round(d5 * 10.0) / 10.0);
                graphics2D.drawString(string, 80 - fontMetrics.stringWidth(string), n + 25);
                if (d4 < 0.05) {
                    graphics2D.setColor(Color.decode("#00E86F"));
                } else {
                    graphics2D.setColor(Color.decode("#EE0000"));
                }
                string = String.valueOf((double)Math.round(d4 * 10.0) / 10.0);
                graphics2D.drawString(string, 130 - fontMetrics.stringWidth(string), n + 25);
                graphics2D.setColor(Color.decode("#00FF00"));
                if (this.AdditionalMetadataValue == 0) {
                    string = String.valueOf((double)Math.round(d3 * 10.0) / 10.0);
                    graphics2D.drawString(string, 80 - fontMetrics.stringWidth(string), n + 50);
                    string = String.valueOf((double)Math.round(d2 * 10.0) / 10.0);
                    graphics2D.drawString(string, 130 - fontMetrics.stringWidth(string), n + 50);
                } else {
                    String string2;
                    if (this.ChunkInfo) {
                        string = String.valueOf((double)Math.round(this.AiffAudioCodec * 10.0) / 10.0);
                        string2 = String.valueOf((double)Math.round(this.AiffMetaDataModel * 10.0) / 10.0);
                    } else {
                        string = String.valueOf((double)Math.round(this.AacMetaDataModel * 10.0) / 10.0);
                        string2 = String.valueOf((double)Math.round(this.BufferedAacReader * 10.0) / 10.0);
                    }
                    graphics2D.drawString(string, 80 - fontMetrics.stringWidth(string), n + 50);
                    graphics2D.drawString(string2, 130 - fontMetrics.stringWidth(string2), n + 50);
                }
                graphics2D.setColor(Color.decode("#DDDD55"));
                if (!this.IAudioFileCodec) {
                    string = String.valueOf((double)Math.round(this.DSP.AacAudioCodec() * 10.0) / 10.0);
                    graphics2D.drawString(string, 80 - fontMetrics.stringWidth(string), n + 75);
                } else {
                    string = String.valueOf((double)Math.round(this.MetaInfomationCopy * 10.0) / 10.0);
                    graphics2D.drawString(string, 80 - fontMetrics.stringWidth(string), n + 75);
                }
                graphics2D.setColor(Color.decode("#55DDFF"));
                if (!this.IAudioInputStream) {
                    if (this.AlacAudioCodec < 0.0) {
                        this.AlacAudioCodec = 0.0;
                    }
                    string = String.valueOf((double)Math.round(this.FFT.AiffAudioCodec() * 10.0) / 10.0);
                    graphics2D.drawString(string, 80 - fontMetrics.stringWidth(string), n + 100);
                } else {
                    string = String.valueOf((double)Math.round(this.AacAudioCodec * 10.0) / 10.0);
                    graphics2D.drawString(string, 80 - fontMetrics.stringWidth(string), n + 100);
                }
                graphics2D.setColor(Color.decode("#009Fc6"));
                string = String.valueOf((double)Math.round(this.FFT.DSP() * 10.0) / 10.0);
                graphics2D.drawString(string, 80 - fontMetrics.stringWidth(string), n2 + 25);
                string = String.valueOf((double)Math.round(this.FFT.responseView() * 10.0) / 10.0);
                graphics2D.drawString(string, 130 - fontMetrics.stringWidth(string), n2 + 25);
                graphics2D.setColor(Color.decode("#E89A20"));
                string = String.valueOf((double)Math.round(this.FFT.FFT() * 10.0) / 10.0);
                graphics2D.drawString(string, 80 - fontMetrics.stringWidth(string), n2 + 50);
                string = String.valueOf((double)Math.round(this.FFT.AdditionalMetadataValue() * 10.0) / 10.0);
                graphics2D.drawString(string, 130 - fontMetrics.stringWidth(string), n2 + 50);
                graphics2D.setColor(Color.decode("#EEEEEE"));
                string = String.valueOf((double)Math.round(this.FFT.AudioFileExtension() * 10.0) / 10.0);
                graphics2D.drawString(string, 80 - fontMetrics.stringWidth(string), n2 + 75);
                graphics2D.setColor(Color.decode("#EEEEEE"));
                string = String.valueOf((double)Math.round(this.FFT.IAudioFileCodec() * 10.0) / 10.0);
                graphics2D.drawString(string, 80 - fontMetrics.stringWidth(string), n2 + 100);
            } else if (this.IBaseAudioCodec && this.AlacInputStream != 0 && this.AlacUtils != 0) {
                int n4;
                int n5;
                if (this.AudioFileExtension == 64) {
                    n5 = 1;
                    n4 = 2;
                } else if (this.AudioFileExtension == 32) {
                    n5 = 2;
                    n4 = 4;
                } else {
                    n5 = 3;
                    n4 = 5;
                }
                graphics2D.setStroke(new BasicStroke(n5));
                for (int i = 0; i < this.AudioFileExtension; ++i) {
                    int n6 = i * n4 + 1;
                    float f = (float)this.BufferedAlacReader[i] / (float)this.AlacInputStream;
                    Color color = f == 0.0f ? new Color(0.0f, 0.0f, 1.0f) : (i >= this.SampleInfo ? new Color(1.0f, 0.0f, 0.0f) : new Color(f, f, f));
                    graphics2D.setColor(color);
                    graphics2D.drawLine(n6, n + 35, n6, n + 38);
                    f = (float)this.AlacContextModel[i] / (float)this.AlacUtils;
                    color = f == 0.0f ? new Color(0.0f, 0.0f, 1.0f) : (i >= this.SampleInfo ? new Color(1.0f, 0.0f, 0.0f) : new Color(f, f, f));
                    graphics2D.setColor(color);
                    graphics2D.drawLine(n6, n + 160, n6, n + 163);
                    if (this.AlacDecoderUtils[i] == 1) {
                        this.DemuxUtils.setRGB(n6 - 1, this.MyStream, n3);
                        this.DemuxUtils.setRGB(n6, this.MyStream, n3);
                    } else {
                        this.DemuxUtils.setRGB(n6 - 1, this.MyStream, 0);
                        this.DemuxUtils.setRGB(n6, this.MyStream, 0);
                    }
                    if (this.AlacFile[i] == 1) {
                        this.LeadingZeros.setRGB(n6 - 1, this.MyStream, n3);
                        this.LeadingZeros.setRGB(n6, this.MyStream, n3);
                        continue;
                    }
                    this.LeadingZeros.setRGB(n6 - 1, this.MyStream, 0);
                    this.LeadingZeros.setRGB(n6, this.MyStream, 0);
                }
                ++this.MyStream;
                if (this.MyStream > 49) {
                    this.MyStream = 0;
                }
                graphics2D.drawImage((Image)this.DemuxUtils, 0, n + 45, null);
                graphics2D.drawImage((Image)this.LeadingZeros, 0, n + 170, null);
                graphics2D.setStroke(new BasicStroke(1.0f));
                graphics2D.setColor(Color.decode("#AAAAAA"));
                graphics2D.drawLine(65, n + 105, 65, n + 115);
                graphics2D.drawString("-", 0, n + 115);
                graphics2D.drawString("+", 130 - fontMetrics.stringWidth("+"), n + 115);
                graphics2D.drawLine(65, n + 230, 65, n + 240);
                graphics2D.drawString("-", 0, n + 240);
                graphics2D.drawString("+", 130 - fontMetrics.stringWidth("+"), n + 240);
                graphics2D.setColor(Color.decode("#FFFFFF"));
                graphics2D.drawLine(65 + (int)this.SampleDuration, n + 105, 65 + (int)this.SampleDuration, n + 115);
                graphics2D.drawLine(65 + (int)this.QTMovieT, n + 230, 65 + (int)this.QTMovieT, n + 240);
            }
        }
    }

    @Override
    public void DSP(String string) {
        this.FFT();
    }

    @Override
    public void DSP(PlayerState zjoyaRSokkGYDwXHPKTBIiX) {
        if (zjoyaRSokkGYDwXHPKTBIiX == PlayerState.AdditionalMetadataValue) {
            this.responseView();
        }
    }

    @Override
    public void AdditionalMetadataValue() {
        this.FFT();
    }

    @Override
    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA2) {
        this.DemuxResT = yGjBevanihqaxYKnUNtrNeA2;
        this.IBaseAudioCodec = yGjBevanihqaxYKnUNtrNeA2.MetaInfomationCopy().FFT() != AudioFormat.FFT;
        this.AudioFileExtension = yGjBevanihqaxYKnUNtrNeA2.FFT().DSP();
        this.repaint();
    }

    @Override
    public boolean DSP(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2, Object object) {
        switch (lXCOGwZXVelwEKHMMPwpSAO2) {
            case IAudioFileCodec: {
                this.MetaInfomationCopy = this.DSP.AacMetaDataModel();
                this.AacAudioCodec = this.FFT.AiffMetaDataModel();
                this.IAudioFileCodec = true;
                this.IAudioInputStream = true;
                this.AiffAudioCodec = this.DSP.LeadingZeros();
                this.AiffMetaDataModel = this.DSP.MyStream();
                this.AacMetaDataModel = this.DSP.BufferedAacReader();
                this.BufferedAacReader = this.DSP.AiffAudioCodec();
                this.AdditionalMetadataValue = 1;
                this.AudioFileExtension();
                break;
            }
            case AudioFileExtension: {
                this.FFT();
            }
        }
        return true;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int n;
        if (mouseEvent.getSource().getClass() == LevelMeterControl.class && (n = mouseEvent.getX()) > 0 && n < 100) {
            this.ChunkInfo = !this.ChunkInfo;
            this.repaint();
        }
        if (mouseEvent.getSource() == this && this.IAudioMetaInformation) {
            n = mouseEvent.getX();
            int n2 = mouseEvent.getY();
            if (n2 > 70 && n2 < 300) {
                for (int i = 0; i < 64; ++i) {
                    this.AlacFile[i] = 0;
                    this.AlacDecoderUtils[i] = 0;
                    this.AlacContextModel[i] = 0;
                    this.BufferedAlacReader[i] = 0;
                }
                this.AlacUtils = 0;
                this.AlacInputStream = 0;
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
    public boolean DSP(boolean bl) {
        this.IAudioMetaInformation = bl;
        this.repaint();
        return true;
    }
}

