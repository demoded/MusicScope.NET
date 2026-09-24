/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.BitDepthCutOffFrequencyModel;
import sdfgjkljljoftrytrszgijpokjprs.LevelMeterControl;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerEventListener;
import sdfgjkljljoftrytrszgijpokjprs.IMetaInformationListener;
import sdfgjkljljoftrytrszgijpokjprs.SpectrumControl;
import sdfgjkljljoftrytrszgijpokjprs.ISpectrumControlScaledListener;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;
import sdfgjkljljoftrytrszgijpokjprs.IComputationListener;
import sdfgjkljljoftrytrszgijpokjprs.ReportingParameter;
import sdfgjkljljoftrytrszgijpokjprs.SpectrumModel;
import sdfgjkljljoftrytrszgijpokjprs.BitDepthCutOffFrequencyModule;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;
import sdfgjkljljoftrytrszgijpokjprs.IValueReporting;
import sdfgjkljljoftrytrszgijpokjprs.ITrackLoadedListener;
import sdfgjkljljoftrytrszgijpokjprs.IAnalyzerStartListener;

public class WaterfallControl
extends JComponent
implements MouseListener,
MouseMotionListener,
Runnable,
IPlayerStateListener,
IPlayerEventListener,
IMetaInformationListener,
ISpectrumControlScaledListener,
IComputationListener<Object>,
IValueReporting<WaterfallControl>,
ITrackLoadedListener,
IAnalyzerStartListener {
    private static final long serialVersionUID = 1L;
    private final int DSP = 1024;
    private int FFT = 0;
    private int responseView = -1;
    private int AdditionalMetadataValue;
    private int AudioFileExtension;
    private int IAudioFileCodec;
    private int IAudioInputStream;
    private int IAudioMetaInformation = 0;
    private int IBaseAudioCodec = 0;
    private int MetaInfomationCopy = 2;
    private long AacAudioCodec;
    private final int AacMetaDataModel = 250;
    private final double[][] BufferedAacReader = new double[255][1025];
    private final double[][] AiffAudioCodec = new double[255][1025];
    private final double[][] AiffMetaDataModel = new double[255][1025];
    private final double[] AlacAudioCodec = new double[1026];
    private final double[] AlacMetaDataModel = new double[1026];
    private final double[] BufferedAlacReader = new double[1026];
    private double AlacContextModel = 0.0;
    private double AlacDecoderUtils = 0.0;
    private double AlacFile = 3000.0;
    private double AlacInputStream = 70.4;
    private double AlacUtils = 1.0;
    private double ChunkInfo = 1.0;
    private double DemuxResT = -144.0;
    private double DemuxUtils;
    private double LeadingZeros = 250.0;
    private final double MyStream = 0.05;
    private final BufferedImage QTMovieT = new BufferedImage(1060, 252, 1);
    private final BufferedImage SampleDuration = new BufferedImage(1060, 252, 1);
    private final BufferedImage SampleInfo = new BufferedImage(1060, 252, 1);
    private final BufferedImage StreamUtils = new BufferedImage(1060, 252, 1);
    private final BufferedImage DffChunkReaderAdapter = new BufferedImage(1060, 252, 1);
    private final BufferedImage DsdAudioCodec = new BufferedImage(1060, 252, 1);
    private final BufferedImage DsdMetaDataModel = new BufferedImage(1060, 252, 1);
    private final BufferedImage DsfChunkReaderAdapter = new BufferedImage(1060, 252, 1);
    private final BufferedImage AbstractManagedWorker = new BufferedImage(1060, 252, 1);
    private final int[] ChannelDecoder = new int[896];
    private final int[] ChannelSplitter = new int[896];
    private boolean DirectStreamDigitalDecoder = false;
    private double DstDecodeUtil = 96000.0;
    private double DstDecoder = 24.0;
    private int EmptySimpleByteBuffer = 0;
    private boolean ExtendedDSTSoundDataChunk = false;
    private boolean FIRDecimationFilter = false;
    private boolean IChannelInputReader = false;
    private boolean IMetadataReader = false;
    private boolean IRawInputReader = false;
    private double IReadableChannelBuffer = 0.0;
    private final double InterleavedBufferBuilder = 4096.0;
    private double SimpleByteBuffer = 0.005;
    private double StreamDecoder;
    private boolean ThreadState = false;
    private final int BufferedFlacReader = -200;
    private double FlacAudioCodec = this.DstDecodeUtil / 2048.0;
    private double FlacMetaDataModel = 10.0 * Math.log10(this.DstDecodeUtil / 2.0 / this.FlacAudioCodec);
    private SpectrumModel ChannelData = new SpectrumModel(new double[1024], new double[1024], new double[1024], new double[1024], new double[1024]);
    private BitDepthCutOffFrequencyModel Constants = new BitDepthCutOffFrequencyModel(0, 0);
    private BitDepthCutOffFrequencyModule FLACDecoder;

    public WaterfallControl() {
        this.IAudioInputStream();
    }

    private void responseView() {
        if (this.IMetadataReader && this.FIRDecimationFilter) {
            this.ExtendedDSTSoundDataChunk = true;
            this.IReadableChannelBuffer = this.Constants.FFT();
            this.EmptySimpleByteBuffer = this.IReadableChannelBuffer != 0.0 ? 36 + (int)Math.round(this.IReadableChannelBuffer / (this.DstDecodeUtil / 2.0 / 1024.0)) : 1060;
            this.repaint();
        }
    }

    private synchronized void IAudioFileCodec() {
        int n;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        for (n = 0; n < 128; ++n) {
            this.ChannelDecoder[n] = n2 << 16 | n3 << 8 | n4;
            ++n4;
        }
        n2 = 0;
        n3 = 0;
        n4 = 127;
        for (n = 128; n < 256; ++n) {
            this.ChannelDecoder[n] = n2 << 16 | n3 << 8 | n4;
            ++n4;
            ++n3;
        }
        n2 = 0;
        n3 = 127;
        n4 = 255;
        for (n = 256; n < 384; ++n) {
            this.ChannelDecoder[n] = n2 << 16 | n3 << 8 | n4;
            ++n3;
            --n4;
        }
        n2 = 0;
        n3 = 255;
        n4 = 127;
        for (n = 384; n < 512; ++n) {
            this.ChannelDecoder[n] = n2 << 16 | n3 << 8 | n4;
            ++n2;
            --n4;
        }
        n2 = 127;
        n3 = 255;
        n4 = 0;
        for (n = 512; n < 640; ++n) {
            this.ChannelDecoder[n] = n2 << 16 | n3 << 8 | n4;
            ++n2;
        }
        n2 = 255;
        n3 = 255;
        n4 = 0;
        for (n = 640; n < 768; ++n) {
            this.ChannelDecoder[n] = n2 << 16 | n3 << 8 | n4;
            --n3;
        }
        n2 = 255;
        n3 = 127;
        n4 = 0;
        for (n = 768; n < 896; ++n) {
            this.ChannelDecoder[n] = n2 << 16 | n3 << 8 | n4;
            --n3;
        }
        n2 = 0;
        n3 = 0;
        n4 = 0;
        for (n = 0; n < 128; ++n) {
            this.ChannelSplitter[n] = n2 << 16 | n3 << 8 | n4;
            ++n4;
        }
        n2 = 0;
        n3 = 0;
        n4 = 127;
        for (n = 128; n < 256; ++n) {
            this.ChannelSplitter[n] = n2 << 16 | n3 << 8 | n4;
            ++n2;
            ++n4;
        }
        n2 = 127;
        n3 = 0;
        n4 = 255;
        for (n = 256; n < 384; ++n) {
            this.ChannelSplitter[n] = n2 << 16 | n3 << 8 | n4;
            --n4;
            ++n2;
        }
        n2 = 255;
        n3 = 0;
        n4 = 127;
        for (n = 384; n < 512; ++n) {
            this.ChannelSplitter[n] = n2 << 16 | n3 << 8 | n4;
            --n4;
            ++n3;
        }
        n2 = 255;
        n3 = 127;
        n4 = 0;
        for (n = 512; n < 640; ++n) {
            this.ChannelSplitter[n] = n2 << 16 | n3 << 8 | n4;
            ++n3;
        }
    }

    private String DSP(long l) {
        int n = (int)((double)l / 3600.0);
        int n2 = (int)((double)l / 60.0 - (double)n * 60.0);
        int n3 = (int)((double)l - (double)n2 * 60.0 - (double)n * 3600.0);
        return String.format("%d:%02d:%02d", n, n2, n3);
    }

    private synchronized void IAudioInputStream() {
        int n;
        int n2;
        this.IAudioFileCodec();
        for (n2 = 0; n2 < 1025; ++n2) {
            this.AlacAudioCodec[n2] = this.DemuxUtils;
            this.AlacMetaDataModel[n2] = this.DemuxUtils;
            this.BufferedAlacReader[n2] = 0.0;
            for (int i = 0; i < 251; ++i) {
                this.BufferedAacReader[i][n2] = this.DemuxResT;
                this.AiffAudioCodec[i][n2] = this.DemuxResT;
                this.AiffMetaDataModel[i][n2] = this.DemuxResT;
            }
        }
        for (n = 0; n < 1024; ++n) {
            for (int i = 0; i < 252; ++i) {
                this.QTMovieT.setRGB(n + 36, i, 0);
                this.SampleDuration.setRGB(n + 36, i, 0);
                this.SampleInfo.setRGB(n + 36, i, 0);
                this.StreamUtils.setRGB(n + 36, i, 0);
                this.DffChunkReaderAdapter.setRGB(n + 36, i, 0);
                this.DsdAudioCodec.setRGB(n + 36, i, 0);
                this.DsdMetaDataModel.setRGB(n + 36, i, 0);
                this.DsfChunkReaderAdapter.setRGB(n + 36, i, 0);
                this.AbstractManagedWorker.setRGB(n + 36, i, 0);
            }
        }
        int n3 = 50;
        int n4 = n3 << 16 | n3 << 8 | n3;
        for (n2 = 0; n2 < 5; ++n2) {
            int n5 = (int)((double)(25 * n2) * 2.5);
            for (n = 36; n < 1060; ++n) {
                this.QTMovieT.setRGB(n, n5, n4);
                this.SampleDuration.setRGB(n, n5, n4);
                this.SampleInfo.setRGB(n, n5, n4);
                this.StreamUtils.setRGB(n, n5, n4);
                this.DffChunkReaderAdapter.setRGB(n, n5, n4);
                this.DsdAudioCodec.setRGB(n, n5, n4);
                this.DsdMetaDataModel.setRGB(n, n5, n4);
                this.DsfChunkReaderAdapter.setRGB(n, n5, n4);
                this.AbstractManagedWorker.setRGB(n, n5, n4);
            }
        }
        this.FFT = 0;
        this.responseView = -1;
        this.AlacDecoderUtils = 0.0;
        this.StreamDecoder = 1024.0 / Math.log10(4096.0 * this.SimpleByteBuffer + 1.0);
        this.LeadingZeros = 250.0;
        if (this.IAudioFileCodec < 250) {
            this.LeadingZeros = this.LeadingZeros / 250.0 * (double)this.IAudioFileCodec;
        }
        this.ExtendedDSTSoundDataChunk = false;
        this.IReadableChannelBuffer = 0.0;
        this.EmptySimpleByteBuffer = 0;
        this.IMetadataReader = false;
        this.IRawInputReader = false;
        this.repaint();
    }

    public void DSP(int n, int n2) {
        this.AdditionalMetadataValue = n;
        this.AudioFileExtension = n2;
        this.repaint();
    }

    private synchronized void IAudioMetaInformation() {
        if (this.ThreadState) {
            this.DemuxResT = -200.0;
            this.DemuxUtils = Math.pow(10.0, -10.0);
            this.AlacFile = 5.0E8;
            this.AlacInputStream = 28.1;
        } else if (this.DstDecoder == 16.0) {
            this.DemuxResT = -200.0;
            this.DemuxUtils = Math.pow(10.0, -10.0);
            this.AlacFile = 3000.0;
            this.AlacInputStream = 70.4;
        } else {
            this.DemuxResT = -200.0;
            this.DemuxUtils = Math.pow(10.0, -10.0);
            this.AlacFile = 500000.0;
            this.AlacInputStream = 42.9;
        }
    }

    private void IBaseAudioCodec() {
        if (!this.IChannelInputReader) {
            this.FlacAudioCodec = this.DstDecodeUtil / 2048.0;
            this.FlacMetaDataModel = 10.0 * Math.log10(this.DstDecodeUtil / 2.0 / this.FlacAudioCodec);
        } else {
            this.FlacAudioCodec = this.DstDecodeUtil / 8192.0;
            this.FlacMetaDataModel = 10.0 * Math.log10(this.DstDecodeUtil / 2.0 / this.FlacAudioCodec);
        }
    }

    public void FFT(int n, int n2) {
        double d = (double)n2 / 2.0;
        this.DstDecodeUtil = n2;
        this.DstDecoder = n;
        this.IAudioMetaInformation();
        this.IAudioInputStream = (int)((double)n2 / 20.0);
        this.IAudioInputStream = n == 16 ? 4 * this.IAudioInputStream : (n == 32 ? 8 * this.IAudioInputStream : (n == 64 ? 16 * this.IAudioInputStream : 6 * this.IAudioInputStream));
        this.IAudioFileCodec = (int)(this.AlacContextModel / (double)this.IAudioInputStream);
        this.AacAudioCodec = (long)(this.AlacContextModel / (double)this.IAudioInputStream * 0.05);
        this.IBaseAudioCodec();
    }

    public synchronized void DSP() {
        double d;
        int n;
        this.AlacDecoderUtils += (double)this.IAudioInputStream;
        for (n = 0; n < 1024; ++n) {
            if (!this.IChannelInputReader) {
                d = this.ChannelData.DSP()[n];
            } else {
                int n2 = (int)((Math.pow(10.0, (double)n / this.StreamDecoder) - 1.0) / this.SimpleByteBuffer);
                d = this.ChannelData.DSP()[n2];
            }
            if (d < this.DemuxUtils) {
                d = this.DemuxUtils;
            }
            if (this.AlacAudioCodec[n] < d) {
                this.AlacAudioCodec[n] = d;
            }
            this.AlacMetaDataModel[n] = (this.AlacMetaDataModel[n] + d) / 2.0;
            if (!(this.BufferedAlacReader[n] > d)) continue;
            this.BufferedAlacReader[n] = d;
        }
        if (this.AlacContextModel != 0.0) {
            if (this.IAudioFileCodec > 250) {
                double d2 = this.AlacDecoderUtils / this.AlacContextModel;
                this.FFT = (int)(250.0 * d2);
            } else {
                ++this.FFT;
            }
            if (this.FFT > 250) {
                this.FFT = 250;
            }
            if (this.FFT != this.responseView) {
                int n3;
                this.responseView = this.FFT;
                for (n = 0; n < 1024; ++n) {
                    this.BufferedAacReader[this.responseView][n] = 20.0 * Math.log10(this.AlacAudioCodec[n]);
                    this.AiffAudioCodec[this.responseView][n] = 20.0 * Math.log10(this.AlacMetaDataModel[n]);
                    this.AiffMetaDataModel[this.responseView][n] = 20.0 * Math.log10(this.BufferedAlacReader[n]);
                    d = this.ChunkInfo * this.AlacInputStream * Math.log10(this.AlacAudioCodec[n] * this.AlacFile * this.AlacUtils * 2.0 + 1.0);
                    int n4 = (int)d;
                    if (n4 > 255) {
                        n4 = 255;
                    }
                    n3 = n4 << 8;
                    this.QTMovieT.setRGB(n + 36, this.FFT, n3);
                    n4 = (int)(3.5 * d);
                    if (n4 > 895) {
                        n4 = 895;
                    }
                    this.StreamUtils.setRGB(n + 36, this.FFT, this.ChannelDecoder[n4]);
                    n4 = (int)(2.5 * d);
                    if (n4 > 639) {
                        n4 = 639;
                    }
                    this.DsdMetaDataModel.setRGB(n + 36, this.FFT, this.ChannelSplitter[n4]);
                    d = this.ChunkInfo * this.AlacInputStream * Math.log10(this.BufferedAlacReader[n] * this.AlacFile * this.AlacUtils * 2.0 + 1.0);
                    n4 = (int)d;
                    if (n4 > 255) {
                        n4 = 255;
                    }
                    n3 = n4 << 8;
                    this.SampleInfo.setRGB(n + 36, this.FFT, n3);
                    n4 = (int)(3.5 * d);
                    if (n4 > 895) {
                        n4 = 895;
                    }
                    this.DsdAudioCodec.setRGB(n + 36, this.FFT, this.ChannelDecoder[n4]);
                    n4 = (int)(2.5 * d);
                    if (n4 > 639) {
                        n4 = 639;
                    }
                    this.AbstractManagedWorker.setRGB(n + 36, this.FFT, this.ChannelSplitter[n4]);
                    d = this.ChunkInfo * this.AlacInputStream * Math.log10(this.AlacMetaDataModel[n] * this.AlacFile * this.AlacUtils * 2.0 + 1.0);
                    n4 = (int)d;
                    if (n4 > 255) {
                        n4 = 255;
                    }
                    n3 = n4 << 8;
                    this.SampleDuration.setRGB(n + 36, this.FFT, n3);
                    n4 = (int)(3.5 * d);
                    if (n4 > 895) {
                        n4 = 895;
                    }
                    this.DffChunkReaderAdapter.setRGB(n + 36, this.FFT, this.ChannelDecoder[n4]);
                    n4 = (int)(2.5 * d);
                    if (n4 > 639) {
                        n4 = 639;
                    }
                    this.DsfChunkReaderAdapter.setRGB(n + 36, this.FFT, this.ChannelSplitter[n4]);
                }
                if (this.FFT < 250 && this.DirectStreamDigitalDecoder) {
                    n3 = 0xFFFFFF;
                    for (n = 0; n < 1024; ++n) {
                        this.QTMovieT.setRGB(n + 36, this.FFT + 1, n3);
                        this.StreamUtils.setRGB(n + 36, this.FFT + 1, n3);
                        this.DsdMetaDataModel.setRGB(n + 36, this.FFT + 1, n3);
                        this.SampleInfo.setRGB(n + 36, this.FFT + 1, n3);
                        this.DsdAudioCodec.setRGB(n + 36, this.FFT + 1, n3);
                        this.AbstractManagedWorker.setRGB(n + 36, this.FFT + 1, n3);
                        this.SampleDuration.setRGB(n + 36, this.FFT + 1, n3);
                        this.DffChunkReaderAdapter.setRGB(n + 36, this.FFT + 1, n3);
                        this.DsfChunkReaderAdapter.setRGB(n + 36, this.FFT + 1, n3);
                    }
                }
                this.repaint();
                for (n = 0; n < 1024; ++n) {
                    this.AlacAudioCodec[n] = 0.0;
                    this.AlacMetaDataModel[n] = 0.0;
                    this.BufferedAlacReader[n] = 1.0;
                }
            }
            if (this.DirectStreamDigitalDecoder && this.AlacDecoderUtils > this.AlacContextModel) {
                this.AlacDecoderUtils = 0.0;
                this.FFT = 0;
                this.responseView = -1;
            }
        }
    }

    @Override
    public void DSP(Object object, ComputationController.DSP rHAjVyBgPhqkQKsOvJMPMYn2) {
        switch (rHAjVyBgPhqkQKsOvJMPMYn2) {
            case DSP: {
                if (!(object instanceof SpectrumModel)) {
                    return;
                }
                this.ChannelData = new SpectrumModel((SpectrumModel)object);
                this.DSP();
                break;
            }
            case IAudioInputStream: {
                if (!(object instanceof BitDepthCutOffFrequencyModel)) {
                    return;
                }
                this.Constants = (BitDepthCutOffFrequencyModel)object;
            }
        }
    }

    @Override
    public synchronized void paintComponent(Graphics graphics) {
        String string;
        Graphics2D graphics2D = (Graphics2D)graphics;
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        switch (this.IBaseAudioCodec) {
            case 0: {
                if (this.MetaInfomationCopy == 0) {
                    graphics.drawImage(this.QTMovieT, 0, 0, null);
                    break;
                }
                if (this.MetaInfomationCopy == 1) {
                    graphics.drawImage(this.StreamUtils, 0, 0, null);
                    break;
                }
                graphics.drawImage(this.DsdMetaDataModel, 0, 0, null);
                break;
            }
            case 1: {
                if (this.MetaInfomationCopy == 0) {
                    graphics.drawImage(this.SampleDuration, 0, 0, null);
                    break;
                }
                if (this.MetaInfomationCopy == 1) {
                    graphics.drawImage(this.DffChunkReaderAdapter, 0, 0, null);
                    break;
                }
                graphics.drawImage(this.DsfChunkReaderAdapter, 0, 0, null);
                break;
            }
            default: {
                if (this.MetaInfomationCopy == 0) {
                    graphics.drawImage(this.SampleInfo, 0, 0, null);
                    break;
                }
                if (this.MetaInfomationCopy == 1) {
                    graphics.drawImage(this.DsdAudioCodec, 0, 0, null);
                    break;
                }
                graphics.drawImage(this.AbstractManagedWorker, 0, 0, null);
            }
        }
        if (this.AdditionalMetadataValue > 35 && this.AdditionalMetadataValue < 1061) {
            int n = this.AdditionalMetadataValue - 36;
            graphics2D.setColor(Color.decode("#0000FF"));
            graphics2D.drawLine(this.AdditionalMetadataValue, 0, this.AdditionalMetadataValue, 250);
            if (this.AudioFileExtension >= 0 && this.AudioFileExtension <= 250 && this.IAudioMetaInformation == 1) {
                int n2;
                int n3;
                int n4;
                int n5;
                graphics2D.drawLine(this.AdditionalMetadataValue - 10, this.AudioFileExtension, this.AdditionalMetadataValue + 10, this.AudioFileExtension);
                graphics2D.setColor(Color.decode("#FFFFFF"));
                switch (this.IBaseAudioCodec) {
                    case 0: {
                        string = String.valueOf((int)Math.round(this.BufferedAacReader[this.AudioFileExtension][n])) + " dB";
                        break;
                    }
                    case 1: {
                        string = String.valueOf((int)Math.round(this.AiffAudioCodec[this.AudioFileExtension][n])) + " dB";
                        break;
                    }
                    default: {
                        string = String.valueOf((int)Math.round(this.AiffMetaDataModel[this.AudioFileExtension][n])) + " dB";
                    }
                }
                String string2 = this.DSP((long)((double)(this.AacAudioCodec * (long)this.AudioFileExtension) / this.LeadingZeros));
                String string3 = "ND: (" + String.valueOf((double)Math.round(10.0 * this.FlacAudioCodec) / 10.0) + " Hz) " + String.valueOf((int)Math.round(this.AiffAudioCodec[this.AudioFileExtension][n] + this.FlacMetaDataModel)) + " dB";
                int n6 = this.AudioFileExtension > 125 ? -20 : 30;
                if (n > 512) {
                    n5 = -35;
                    n4 = this.AdditionalMetadataValue - fontMetrics.stringWidth(string) + 15;
                    n3 = this.AdditionalMetadataValue - fontMetrics.stringWidth(string2) + 15;
                    n2 = this.AdditionalMetadataValue - fontMetrics.stringWidth(string3) + 15;
                } else {
                    n5 = 15;
                    n3 = n2 = this.AdditionalMetadataValue + 5;
                    n4 = n2;
                }
                graphics2D.drawString(string, n4 + n5, this.AudioFileExtension + n6);
                graphics2D.drawString(string2, n3 + n5, this.AudioFileExtension + n6 - 15);
                graphics2D.drawString(string3, n2 + n5, this.AudioFileExtension + n6 + 15);
            }
        }
        graphics2D.setColor(Color.decode("#666666"));
        graphics2D.drawString("%", 14 - fontMetrics.stringWidth("%"), 10);
        graphics2D.setColor(Color.decode("#00FF00"));
        switch (this.IBaseAudioCodec) {
            case 0: {
                graphics2D.drawString("MAX", 32 - fontMetrics.stringWidth("MAX"), 37);
                break;
            }
            case 1: {
                graphics2D.drawString("AVG", 32 - fontMetrics.stringWidth("AVG"), 37);
                break;
            }
            default: {
                graphics2D.drawString("MIN", 32 - fontMetrics.stringWidth("MIN"), 37);
            }
        }
        if (this.MetaInfomationCopy == 0) {
            graphics2D.drawString("MON", 32 - fontMetrics.stringWidth("MON"), 99);
        } else if (this.MetaInfomationCopy == 1) {
            graphics2D.drawString("COL", 32 - fontMetrics.stringWidth("COL"), 99);
        } else {
            graphics2D.drawString("BRY", 32 - fontMetrics.stringWidth("COL"), 99);
        }
        if (!this.FIRDecimationFilter) {
            graphics2D.setColor(Color.decode("#666666"));
        } else {
            graphics2D.setColor(Color.decode("#00FF00"));
        }
        graphics2D.drawString("COF", 32 - fontMetrics.stringWidth("COF"), 161);
        graphics2D.setColor(Color.decode("#EEEEEE"));
        for (int i = 0; i < 5; ++i) {
            int n = (int)((double)(25 * i) * 2.5);
            string = String.valueOf(25 * i);
            if (n == 0) {
                n = 5;
            }
            if (n == 250) {
                n = 245;
            }
            graphics2D.drawString(string, 32 - fontMetrics.stringWidth(string), n + 5);
        }
        if (this.ExtendedDSTSoundDataChunk && this.FIRDecimationFilter && this.IMetadataReader) {
            graphics2D.setColor(Color.decode("#FF0000"));
            graphics2D.setStroke(new BasicStroke(2.0f));
            graphics2D.drawLine(this.EmptySimpleByteBuffer, 0, this.EmptySimpleByteBuffer, 250);
        }
    }

    @Override
    public void run() {
    }

    @Override
    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA2) {
        this.AlacContextModel = yGjBevanihqaxYKnUNtrNeA.IAudioInputStream();
        this.FFT(yGjBevanihqaxYKnUNtrNeA.FFT().DSP(), yGjBevanihqaxYKnUNtrNeA.DSP());
        this.DirectStreamDigitalDecoder = yGjBevanihqaxYKnUNtrNeA2.MetaInfomationCopy() == AudioExtension.MetaInfomationCopy || yGjBevanihqaxYKnUNtrNeA2.MetaInfomationCopy() == AudioExtension.AacAudioCodec;
    }

    @Override
    public void DSP(String string) {
        this.IAudioInputStream();
    }

    @Override
    public void DSP(PlayerState zjoyaRSokkGYDwXHPKTBIiX) {
        if (zjoyaRSokkGYDwXHPKTBIiX == PlayerState.AdditionalMetadataValue) {
            // empty if block
        }
    }

    @Override
    public void AdditionalMetadataValue() {
        this.IAudioInputStream();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int n;
        if (mouseEvent.getSource().getClass() == SpectrumControl.class) {
            n = mouseEvent.getX();
            int n2 = mouseEvent.getY();
            if (n > 36 && n < 250 && n2 > 250) {
                if (!this.IChannelInputReader) {
                    this.IChannelInputReader = true;
                    this.FIRDecimationFilter = false;
                    this.FLACDecoder.DSP(this.FIRDecimationFilter);
                    if (this.IRawInputReader) {
                        this.IMetadataReader = false;
                    }
                } else {
                    this.IChannelInputReader = false;
                }
                this.IBaseAudioCodec();
                this.repaint();
            }
            if (n > 625 && n < 710 && n2 > 250) {
                this.ThreadState = !this.ThreadState;
                this.IAudioMetaInformation();
                this.repaint();
            }
        }
        if (mouseEvent.getSource() == this) {
            int n3 = mouseEvent.getX();
            int n4 = mouseEvent.getY();
            if (n3 > 0 && n3 < 35 && n4 > 25 && n4 < 43) {
                switch (this.IBaseAudioCodec) {
                    case 0: {
                        this.IBaseAudioCodec = 1;
                        break;
                    }
                    case 1: {
                        this.IBaseAudioCodec = 2;
                        break;
                    }
                    default: {
                        this.IBaseAudioCodec = 0;
                    }
                }
            }
            if (n3 > 0 && n3 < 35 && n4 > 88 && n4 < 106) {
                this.MetaInfomationCopy = this.MetaInfomationCopy == 0 ? 1 : (this.MetaInfomationCopy == 1 ? 2 : 0);
            }
            if (n3 > 0 && n3 < 35 && n4 > 149 && n4 < 169) {
                if (!(this.FIRDecimationFilter || this.IRawInputReader || this.IChannelInputReader)) {
                    this.FIRDecimationFilter = true;
                } else if (this.FIRDecimationFilter && !this.IRawInputReader) {
                    this.FIRDecimationFilter = false;
                }
                this.FLACDecoder.DSP(this.FIRDecimationFilter);
            }
            this.repaint();
        }
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
        if (mouseEvent.getSource() == this || mouseEvent.getSource().getClass() == SpectrumControl.class) {
            this.DSP(0, 0);
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == this || mouseEvent.getSource().getClass() == SpectrumControl.class) {
            this.DSP(mouseEvent.getX(), mouseEvent.getY());
            this.IAudioMetaInformation = mouseEvent.getSource() == this ? 1 : 0;
        }
    }

    @Override
    public void DSP(double d, double d2, int n) {
        if (n == 1) {
            this.AlacUtils = d;
            this.ChunkInfo = d2;
        } else if (n == 2) {
            this.SimpleByteBuffer = d;
            this.StreamDecoder = 1024.0 / Math.log10(4096.0 * this.SimpleByteBuffer + 1.0);
        }
    }

    @ReportingParameter(DSP="Cut-Off Frequency:", FFT="kHz")
    public Object getCutOffFrequency() {
        Object object = this.ExtendedDSTSoundDataChunk ? (this.IReadableChannelBuffer == 0.0 ? Double.valueOf((double)Math.round(10.0 * this.DstDecodeUtil / 2.0) / 10000.0) : Double.valueOf((double)Math.round(10.0 * this.IReadableChannelBuffer) / 10000.0)) : "---";
        return object;
    }

    @ReportingParameter(DSP="Effective Bits:", FFT="bit", responseView={"DSD"})
    public Object getEffectifBitDepth() {
        if (this.Constants.responseView()) {
            return (int)this.DstDecoder - this.Constants.DSP();
        }
        return "---";
    }

    public WaterfallControl FFT() {
        return this;
    }

    @Override
    public synchronized boolean DSP(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2, Object object) {
        switch (lXCOGwZXVelwEKHMMPwpSAO2) {
            case IAudioFileCodec: {
                this.responseView();
                this.IRawInputReader = false;
                break;
            }
            case AudioFileExtension: {
                this.IAudioInputStream();
                break;
            }
            case IAudioMetaInformation: {
                this.IRawInputReader = true;
                this.IMetadataReader = true;
                break;
            }
            case IAudioInputStream: {
                this.IRawInputReader = true;
                this.IMetadataReader = true;
                break;
            }
            case responseView: {
                this.IMetadataReader = false;
                this.IRawInputReader = false;
            }
        }
        return true;
    }

    public void DSP(BitDepthCutOffFrequencyModule jCRbtyIYklMgdOqCNOayzCg2) {
        this.FLACDecoder = jCRbtyIYklMgdOqCNOayzCg2;
    }

    @Override
    public /* synthetic */ Object AudioFileExtension() {
        return this.FFT();
    }
}

