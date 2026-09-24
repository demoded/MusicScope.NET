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
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.LevelsModel;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.LevelMeterControl;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerEventListener;
import sdfgjkljljoftrytrszgijpokjprs.IMetaInformationListener;
import sdfgjkljljoftrytrszgijpokjprs.LoudnessModel;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;
import sdfgjkljljoftrytrszgijpokjprs.ComposedModel;
import sdfgjkljljoftrytrszgijpokjprs.IComputationListener;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;
import sdfgjkljljoftrytrszgijpokjprs.WaterfallControl;
import sdfgjkljljoftrytrszgijpokjprs.ITrackLoadedListener;
import sdfgjkljljoftrytrszgijpokjprs.IAnalyzerStartListener;

public class CircleControl
extends JComponent
implements MouseListener,
MouseMotionListener,
Runnable,
IPlayerStateListener,
IPlayerEventListener,
IMetaInformationListener,
IComputationListener<ComposedModel>,
ITrackLoadedListener,
IAnalyzerStartListener {
    private static final long serialVersionUID = 1L;
    private LevelsModel BufferedAacReader;
    private LoudnessModel AiffAudioCodec;
    private long AiffMetaDataModel;
    private long AlacAudioCodec = 512L;
    private long AlacMetaDataModel = 0L;
    private long BufferedAlacReader;
    private long AlacContextModel;
    private int AlacDecoderUtils;
    private int AlacFile;
    private int AlacInputStream;
    private int AlacUtils;
    private int ChunkInfo = 0;
    private int DemuxResT = 0;
    private int DemuxUtils = 0;
    private int LeadingZeros = 0;
    private final int MyStream = 512;
    private double QTMovieT = -60.0;
    private double SampleDuration = -60.0;
    private double SampleInfo = -60.0;
    private double StreamUtils = -60.0;
    private double DffChunkReaderAdapter = -60.0;
    private double DsdAudioCodec = -60.0;
    private double DsdMetaDataModel = 360.0;
    private final int DsfChunkReaderAdapter = 151;
    private final double AbstractManagedWorker = 0.05;
    private boolean ChannelDecoder = false;
    private boolean ChannelSplitter = false;
    private boolean DirectStreamDigitalDecoder = false;
    public int DSP = 0;
    public int FFT = 0;
    double[] responseView = new double[10];
    double[] AdditionalMetadataValue = new double[517];
    double[] AudioFileExtension = new double[517];
    double[] IAudioFileCodec = new double[517];
    double[] IAudioInputStream = new double[517];
    double[] IAudioMetaInformation = new double[517];
    double[] IBaseAudioCodec = new double[517];
    double[] MetaInfomationCopy = new double[517];
    double[] AacAudioCodec = new double[517];
    double[] AacMetaDataModel = new double[517];
    private final BufferedImage DstDecodeUtil = new BufferedImage(300, 300, 1);

    public CircleControl() {
        this.FFT();
    }

    private synchronized void FFT() {
        int n;
        int n2 = 1000;
        double[] dArray = new double[]{3.0, 0.0, -6.0, -12.0, -24.0, -40.0, -60.0};
        for (n = 0; n < 513; ++n) {
            this.AdditionalMetadataValue[n] = Math.sin(Math.PI * 2 * (double)n / 512.0 - 1.5707963267948966);
            this.AudioFileExtension[n] = Math.cos(Math.PI * 2 * (double)n / 512.0 - 1.5707963267948966);
            this.IAudioFileCodec[n] = this.DSP(-60.0);
            this.IAudioInputStream[n] = this.DSP(-60.0);
            this.MetaInfomationCopy[n] = -60.0;
            this.AacAudioCodec[n] = -60.0;
            this.IAudioMetaInformation[n] = this.DSP(-60.0);
            this.IBaseAudioCodec[n] = this.DSP(-60.0);
            this.AacMetaDataModel[n] = this.DSP(-60.0);
        }
        for (int i = 0; i < 7; ++i) {
            double d = this.DSP(dArray[i]);
            for (n = 0; n < n2 + 1; ++n) {
                int n3;
                int n4;
                int n5;
                int n6 = (int)(d * Math.sin(Math.PI * 2 * (double)n / (double)n2)) + 151;
                int n7 = (int)(d * Math.cos(Math.PI * 2 * (double)n / (double)n2)) + 151;
                if (i == 0 || i == 1) {
                    n5 = 120;
                    n4 = 0;
                    n3 = 0;
                } else {
                    n5 = 50;
                    n4 = 50;
                    n3 = 50;
                }
                int n8 = n5 << 16 | n4 << 8 | n3;
                this.DstDecodeUtil.setRGB(n7, n6, n8);
            }
        }
        this.AlacMetaDataModel = 0L;
        this.AlacInputStream = 0;
        this.AlacDecoderUtils = 0;
        this.AlacFile = -1;
        this.DsdAudioCodec = -60.0;
        this.DffChunkReaderAdapter = -60.0;
        this.StreamUtils = -60.0;
        this.SampleInfo = -60.0;
        this.SampleDuration = -60.0;
        this.QTMovieT = -60.0;
        this.DsdMetaDataModel = 360.0;
        if (this.AlacAudioCodec < 512L) {
            this.DsdMetaDataModel = this.DsdMetaDataModel / 512.0 * (double)this.AlacAudioCodec;
        }
        this.LeadingZeros = 0;
        this.repaint();
    }

    public void DSP(int n, int n2) {
        this.AlacUtils = (int)((double)n2 / 20.0);
        this.AlacUtils = n == 16 ? 4 * this.AlacUtils : (n == 32 ? 8 * this.AlacUtils : (n == 64 ? 16 * this.AlacUtils : 6 * this.AlacUtils));
        this.AlacAudioCodec = (int)(this.AiffMetaDataModel / (long)this.AlacUtils);
        this.BufferedAlacReader = (long)((double)(this.AiffMetaDataModel / (long)this.AlacUtils) * 0.05);
        this.AlacContextModel = 0L;
    }

    public double DSP(double d) {
        d = 36.6 * (Math.pow(10.0, (d + 60.0) / 90.0) - 1.0);
        return d;
    }

    private String DSP(long l) {
        int n = (int)((double)l / 3600.0);
        int n2 = (int)((double)l / 60.0 - (double)n * 60.0);
        int n3 = (int)((double)l - (double)n2 * 60.0 - (double)n * 3600.0);
        return String.format("%d:%02d:%02d", n, n2, n3);
    }

    public void DSP(int n, int n2, boolean bl) {
        if (n == -1 && n2 == -1) {
            this.ChunkInfo = 0;
        } else {
            this.ChunkInfo = 1;
            if (!bl) {
                n -= 151;
                n2 = 151 - n2;
            }
            double d = Math.atan2(n2, n);
            this.DemuxResT = (int)(148.0 * Math.sin(d - 1.5707963267948966));
            this.DemuxUtils = (int)(148.0 * Math.cos(d - 1.5707963267948966));
            this.DemuxResT = 151 - this.DemuxResT - 1;
            this.DemuxUtils = 151 - this.DemuxUtils;
            if ((d = 450.0 - d / Math.PI * 180.0) > 360.0) {
                d -= 360.0;
            }
            if (d < 0.0) {
                d += 360.0;
            }
            this.AlacContextModel = (long)((double)this.BufferedAlacReader / this.DsdMetaDataModel * d);
            this.LeadingZeros = (int)(1.4222222222222223 * d);
        }
        this.repaint();
    }

    public void DSP() {
        if (this.QTMovieT < this.BufferedAacReader.DSP()) {
            this.QTMovieT = this.BufferedAacReader.DSP();
        }
        if (this.SampleDuration < this.BufferedAacReader.FFT()) {
            this.SampleDuration = this.BufferedAacReader.FFT();
        }
        if (this.DffChunkReaderAdapter < this.AiffAudioCodec.FFT()) {
            this.DffChunkReaderAdapter = this.AiffAudioCodec.FFT();
        }
        if (this.SampleInfo < this.BufferedAacReader.AlacContextModel()) {
            this.SampleInfo = this.BufferedAacReader.AlacContextModel();
        }
        if (this.StreamUtils < this.BufferedAacReader.AlacDecoderUtils()) {
            this.StreamUtils = this.BufferedAacReader.AlacDecoderUtils();
        }
        if (this.DsdAudioCodec < this.AiffAudioCodec.AiffAudioCodec()) {
            this.DsdAudioCodec = this.AiffAudioCodec.AiffAudioCodec();
        }
        this.AlacDecoderUtils = this.AlacAudioCodec > 511L ? (int)((double)this.AlacMetaDataModel / (double)this.AiffMetaDataModel * 512.0) : ++this.AlacDecoderUtils;
        if (this.AlacDecoderUtils > 512) {
            this.AlacDecoderUtils = 512;
        }
        if (this.AlacDecoderUtils != this.AlacFile) {
            this.MetaInfomationCopy[this.AlacDecoderUtils] = this.DffChunkReaderAdapter;
            this.AacAudioCodec[this.AlacDecoderUtils] = this.DsdAudioCodec;
            if (this.QTMovieT < this.SampleDuration) {
                this.QTMovieT = this.SampleDuration;
            }
            this.IAudioFileCodec[this.AlacDecoderUtils] = this.DSP(this.QTMovieT);
            this.IAudioInputStream[this.AlacDecoderUtils] = this.DSP(this.DffChunkReaderAdapter);
            this.IAudioMetaInformation[this.AlacDecoderUtils] = this.DSP(this.SampleInfo);
            this.IBaseAudioCodec[this.AlacDecoderUtils] = this.DSP(this.StreamUtils);
            this.AacMetaDataModel[this.AlacDecoderUtils] = this.DSP(-this.DsdAudioCodec);
            this.DsdAudioCodec = -60.0;
            this.StreamUtils = -60.0;
            this.SampleInfo = -60.0;
            this.SampleDuration = -60.0;
            this.QTMovieT = -60.0;
            this.DffChunkReaderAdapter = -60.0;
            EventQueue.invokeLater(this);
        }
        this.AlacFile = this.AlacDecoderUtils;
        this.AlacMetaDataModel += (long)this.AlacUtils;
        if (this.ChannelSplitter) {
            if (this.AlacMetaDataModel > this.AiffMetaDataModel) {
                this.AlacMetaDataModel = 0L;
                this.AlacDecoderUtils = 0;
                this.AlacFile = -1;
            }
            this.AlacInputStream = 511;
        } else {
            this.AlacInputStream = this.AlacDecoderUtils;
        }
    }

    @Override
    public void run() {
        this.repaint();
    }

    @Override
    public synchronized void DSP(ComposedModel dbWUuIVxtlDbLbZVylyCBng2, ComputationController.DSP rHAjVyBgPhqkQKsOvJMPMYn2) {
        if (rHAjVyBgPhqkQKsOvJMPMYn2 == ComputationController.DSP.AudioFileExtension) {
            this.AiffAudioCodec = new LoudnessModel((LoudnessModel)dbWUuIVxtlDbLbZVylyCBng2.DSP(ComputationController.DSP.responseView));
            this.BufferedAacReader = new LevelsModel((LevelsModel)dbWUuIVxtlDbLbZVylyCBng2.DSP(ComputationController.DSP.FFT));
        }
        if (this.AiffAudioCodec != null && this.BufferedAacReader != null) {
            this.DSP();
        }
    }

    @Override
    public synchronized void paintComponent(Graphics graphics) {
        int n;
        int n2;
        int n3;
        Graphics2D graphics2D = (Graphics2D)graphics;
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        String[] stringArray = new String[]{"3", "0", "-6", "-12", "-24", "-40", "-60"};
        double[] dArray = new double[]{3.0, 0.0, -6.0, -12.0, -24.0, -40.0, -60.0};
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setStroke(new BasicStroke(2.0f));
        for (n3 = 0; n3 < 7; ++n3) {
            double d = this.DSP(dArray[n3]);
            if (n3 == 0 || n3 == 1) {
                graphics2D.setColor(Color.decode("#770000"));
            } else {
                graphics2D.setColor(Color.decode("#444444"));
            }
            graphics2D.drawOval((int)(151.0 - d), (int)(151.0 - d), (int)(2.0 * d), (int)(2.0 * d));
        }
        graphics2D.setStroke(new BasicStroke(1.0f));
        if (this.DirectStreamDigitalDecoder) {
            graphics2D.setColor(Color.decode("#55DDFF"));
            graphics2D.drawString("PLR", 0, 270);
        } else {
            graphics2D.setColor(Color.decode("#CCCCCC"));
            graphics2D.drawString("PLR", 0, 270);
        }
        if (this.ChunkInfo == 1) {
            graphics2D.setColor(Color.decode("#FFFFFF"));
            String string = this.DSP(this.AlacContextModel);
            graphics2D.drawString(string, 300 - fontMetrics.stringWidth(string), 10);
            graphics2D.setColor(Color.decode("#CCCCCC"));
            graphics2D.drawLine(151, 151, this.DemuxResT, this.DemuxUtils);
            if (!this.ChannelDecoder) {
                graphics2D.setColor(Color.decode("#E89A20"));
                string = String.valueOf((double)Math.round(this.MetaInfomationCopy[this.LeadingZeros] * 10.0) / 10.0);
                string = string + " dB";
                graphics2D.drawString(string, 300 - fontMetrics.stringWidth(string), 30);
            }
            if (this.DirectStreamDigitalDecoder) {
                graphics2D.setColor(Color.decode("#55DDFF"));
                string = String.valueOf((double)Math.round(this.AacAudioCodec[this.LeadingZeros] * 10.0) / 10.0);
                string = string + " dB";
                graphics2D.drawString(string, 0, 290);
            }
        }
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        if (!this.ChannelDecoder) {
            graphics2D.setColor(Color.decode("#EEEEEE"));
            graphics2D.drawString("P/L", 0, 30);
            for (n3 = 1; n3 < this.AlacInputStream; ++n3) {
                graphics2D.setColor(Color.decode("#00B000"));
                if (this.IAudioFileCodec[n3] > 133.49960598230444) {
                    graphics2D.setColor(Color.decode("#FF0000"));
                }
                this.DSP = (int)(this.IAudioFileCodec[n3 - 1] * this.AudioFileExtension[n3 - 1]) + 151;
                this.FFT = (int)(this.IAudioFileCodec[n3 - 1] * this.AdditionalMetadataValue[n3 - 1]) + 151;
                n2 = (int)(this.IAudioFileCodec[n3] * this.AudioFileExtension[n3]) + 151;
                n = (int)(this.IAudioFileCodec[n3] * this.AdditionalMetadataValue[n3]) + 151;
                graphics2D.drawLine(this.DSP, this.FFT, n2, n);
                graphics2D.setColor(Color.decode("#E89A20"));
                this.DSP = (int)(this.IAudioInputStream[n3 - 1] * this.AudioFileExtension[n3 - 1]) + 151;
                this.FFT = (int)(this.IAudioInputStream[n3 - 1] * this.AdditionalMetadataValue[n3 - 1]) + 151;
                n2 = (int)(this.IAudioInputStream[n3] * this.AudioFileExtension[n3]) + 151;
                n = (int)(this.IAudioInputStream[n3] * this.AdditionalMetadataValue[n3]) + 151;
                graphics2D.drawLine(this.DSP, this.FFT, n2, n);
            }
        } else {
            graphics2D.setColor(Color.decode("#FF0000"));
            graphics2D.drawString("M/S", 0, 30);
            for (n3 = 1; n3 < this.AlacInputStream; ++n3) {
                graphics2D.setColor(Color.decode("#00B000"));
                if (this.IAudioMetaInformation[n3] > 133.49960598230444) {
                    graphics2D.setColor(Color.decode("#FF0000"));
                }
                this.DSP = (int)(this.IAudioMetaInformation[n3 - 1] * this.AudioFileExtension[n3 - 1]) + 151;
                this.FFT = (int)(this.IAudioMetaInformation[n3 - 1] * this.AdditionalMetadataValue[n3 - 1]) + 151;
                n2 = (int)(this.IAudioMetaInformation[n3] * this.AudioFileExtension[n3]) + 151;
                n = (int)(this.IAudioMetaInformation[n3] * this.AdditionalMetadataValue[n3]) + 151;
                graphics2D.drawLine(this.DSP, this.FFT, n2, n);
                graphics2D.setColor(Color.decode("#E89A20"));
                if (this.IBaseAudioCodec[n3] > 133.49960598230444) {
                    graphics2D.setColor(Color.decode("#FF0000"));
                }
                this.DSP = (int)(this.IBaseAudioCodec[n3 - 1] * this.AudioFileExtension[n3 - 1]) + 151;
                this.FFT = (int)(this.IBaseAudioCodec[n3 - 1] * this.AdditionalMetadataValue[n3 - 1]) + 151;
                n2 = (int)(this.IBaseAudioCodec[n3] * this.AudioFileExtension[n3]) + 151;
                n = (int)(this.IBaseAudioCodec[n3] * this.AdditionalMetadataValue[n3]) + 151;
                graphics2D.drawLine(this.DSP, this.FFT, n2, n);
            }
        }
        if (this.DirectStreamDigitalDecoder) {
            graphics2D.setColor(Color.decode("#55DDFF"));
            for (n3 = 1; n3 < this.AlacInputStream; ++n3) {
                this.DSP = (int)(this.AacMetaDataModel[n3 - 1] * this.AudioFileExtension[n3 - 1]) + 151;
                this.FFT = (int)(this.AacMetaDataModel[n3 - 1] * this.AdditionalMetadataValue[n3 - 1]) + 151;
                n2 = (int)(this.AacMetaDataModel[n3] * this.AudioFileExtension[n3]) + 151;
                n = (int)(this.AacMetaDataModel[n3] * this.AdditionalMetadataValue[n3]) + 151;
                graphics2D.drawLine(this.DSP, this.FFT, n2, n);
            }
        }
        for (n3 = 0; n3 < 7; ++n3) {
            n = 151 - (int)this.DSP(dArray[n3]);
            if (n3 == 0 || n3 == 1) {
                graphics2D.setColor(Color.decode("#FF0000"));
            } else {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            }
            graphics2D.drawString(stringArray[n3], 155 - fontMetrics.stringWidth(stringArray[n3]), n + 5);
        }
    }

    @Override
    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA2) {
        this.AiffMetaDataModel = yGjBevanihqaxYKnUNtrNeA.IAudioInputStream();
        this.DSP(yGjBevanihqaxYKnUNtrNeA.FFT().DSP(), yGjBevanihqaxYKnUNtrNeA.DSP());
        this.ChannelSplitter = yGjBevanihqaxYKnUNtrNeA2.MetaInfomationCopy() == AudioExtension.MetaInfomationCopy || yGjBevanihqaxYKnUNtrNeA2.MetaInfomationCopy() == AudioExtension.AacAudioCodec;
    }

    @Override
    public void DSP(String string) {
        this.FFT();
    }

    @Override
    public void DSP(PlayerState zjoyaRSokkGYDwXHPKTBIiX) {
        if (zjoyaRSokkGYDwXHPKTBIiX == PlayerState.AdditionalMetadataValue) {
            // empty if block
        }
    }

    @Override
    public synchronized boolean DSP(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2, Object object) {
        switch (lXCOGwZXVelwEKHMMPwpSAO2) {
            case AudioFileExtension: {
                this.FFT();
            }
        }
        return true;
    }

    @Override
    public void AdditionalMetadataValue() {
        this.FFT();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == this) {
            this.DSP(mouseEvent.getX(), mouseEvent.getY(), false);
        }
        if (mouseEvent.getSource().getClass() == WaterfallControl.class && mouseEvent.getY() < 251) {
            double d = (double)mouseEvent.getY() / 250.0 * 2.0 * Math.PI;
            this.DSP((int)(100.0 * Math.sin(d)), (int)(100.0 * Math.cos(d)), true);
        }
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == this) {
            this.DSP(-1, -1, false);
        }
        if (mouseEvent.getSource().getClass() == WaterfallControl.class) {
            this.DSP(-1, -1, false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int n;
        if (mouseEvent.getSource() == this) {
            n = mouseEvent.getX();
            int n2 = mouseEvent.getY();
            if (n2 > 250 && n2 < 300 && n > 0 && n < 80) {
                this.DirectStreamDigitalDecoder = !this.DirectStreamDigitalDecoder;
                this.repaint();
            }
        }
        if (mouseEvent.getSource().getClass() == LevelMeterControl.class && (n = mouseEvent.getX()) > 0 && n < 100) {
            this.ChannelDecoder = !this.ChannelDecoder;
            this.repaint();
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
}

