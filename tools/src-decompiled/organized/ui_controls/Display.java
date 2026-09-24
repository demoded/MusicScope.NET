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
import javax.swing.SwingUtilities;
import sdfgjkljljoftrytrszgijpokjprs.UHRSpectrumAnalyzer;

public class Display
extends JComponent
implements MouseListener,
MouseMotionListener,
Runnable {
    private int DSP = 48000;
    private int FFT = this.DSP / 2;
    private double responseView = -130.0;
    private double AdditionalMetadataValue = 0.0;
    private double AudioFileExtension = -65.0;
    private boolean IAudioFileCodec = true;
    private boolean IAudioInputStream = true;
    private int IAudioMetaInformation = 3;
    private int IBaseAudioCodec;
    private double MetaInfomationCopy;
    private double AacAudioCodec;
    private int AacMetaDataModel;
    private double[] BufferedAacReader;
    private int AiffAudioCodec = 2048;
    private boolean AiffMetaDataModel = false;
    private final int AlacAudioCodec = 520;
    private final int AlacMetaDataModel = 80;
    private final int BufferedAlacReader = 500;
    private final int AlacContextModel = 1024;
    private double AlacDecoderUtils = this.DSP / 4;
    private double AlacFile = this.DSP / 2;
    private double AlacInputStream = this.AlacDecoderUtils - this.AlacFile / 2.0;
    private double AlacUtils = this.AlacDecoderUtils + this.AlacFile / 2.0;
    private double ChunkInfo = (double)this.DSP / 2048.0;
    private int DemuxResT = 24000;
    private int DemuxUtils = 0;
    private int LeadingZeros = 48000;
    private int MyStream;
    private int QTMovieT;
    private int SampleDuration;
    private int SampleInfo;
    private int StreamUtils;
    private int DffChunkReaderAdapter;
    private boolean DsdAudioCodec = false;
    private double[][] DsdMetaDataModel;
    private double[] DsfChunkReaderAdapter = new double[1024];
    private final double[] AbstractManagedWorker = new double[1024];
    private float[][] ChannelDecoder;
    private int ChannelSplitter = 1;
    private int DirectStreamDigitalDecoder = 0;
    private final BufferedImage DstDecodeUtil = new BufferedImage(1024, 500, 1);
    private final int[] DstDecoder = new int[1280];
    private final int[] EmptySimpleByteBuffer = new int[256];
    private final int[] ExtendedDSTSoundDataChunk = new int[256];
    private final int[] FIRDecimationFilter = new int[1024];
    private double IChannelInputReader = 1279.0;
    private boolean IMetadataReader = false;
    private int IRawInputReader = 1;
    private boolean IReadableChannelBuffer = false;
    private boolean InterleavedBufferBuilder = false;
    private boolean SimpleByteBuffer = false;
    private boolean StreamDecoder = false;
    private UHRSpectrumAnalyzer ThreadState;

    public Display() {
        this.FFT();
        this.DSP();
    }

    private void DSP() {
        int n;
        int n2;
        this.MetaInfomationCopy = 500.0 / Math.abs(this.AdditionalMetadataValue - this.responseView);
        this.AacAudioCodec = this.IChannelInputReader / Math.abs(this.AdditionalMetadataValue - this.responseView);
        this.DirectStreamDigitalDecoder = 0;
        for (n2 = 0; n2 < this.ChannelSplitter; ++n2) {
            for (n = 0; n < this.AacMetaDataModel; ++n) {
                this.ChannelDecoder[n][n2] = -200.0f;
            }
        }
        this.responseView();
        for (n2 = 0; n2 < 1024; ++n2) {
            for (n = 0; n < 500; ++n) {
                this.DstDecodeUtil.setRGB(n2, n, 0);
            }
        }
        this.DSP(-50, -50);
    }

    private void FFT() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }

    public void DSP(boolean bl) {
        this.StreamDecoder = bl;
    }

    public void DSP(int n) {
        this.AiffAudioCodec = n;
        this.BufferedAacReader = new double[n / 2];
        this.AacMetaDataModel = n / 2;
        this.ChannelDecoder = null;
        this.ChannelSplitter = n < 0x100000 ? 500 : 250;
        this.ChannelDecoder = new float[this.AacMetaDataModel][this.ChannelSplitter];
        this.DsdMetaDataModel = new double[1024][this.ChannelSplitter];
        this.DsfChunkReaderAdapter = new double[1024];
        this.DSP();
    }

    public void FFT(int n) {
        if (n != this.DSP || !this.StreamDecoder) {
            this.DSP = n;
            this.AlacDecoderUtils = (double)n / 4.0;
            this.AlacFile = n / 2;
        }
        this.FFT = n / 2;
        this.ChunkInfo = (double)n / 2.0 / (double)this.AacMetaDataModel;
        this.AlacInputStream = this.AlacDecoderUtils - this.AlacFile / 2.0;
        this.AlacUtils = this.AlacDecoderUtils + this.AlacFile / 2.0;
        this.DemuxUtils = (int)Math.round(this.AlacDecoderUtils - (double)this.DemuxResT / 2.0);
        this.LeadingZeros = (int)Math.round(this.AlacDecoderUtils + (double)this.DemuxResT / 2.0);
        this.ThreadState.DSP(this.AlacDecoderUtils, this.AlacFile);
        this.IAudioFileCodec();
    }

    public void DSP(int n, boolean bl, boolean bl2, int n2) {
        this.DemuxResT = n;
        this.IAudioFileCodec = bl;
        this.IAudioInputStream = bl2;
        this.IAudioMetaInformation = n2;
        switch (n2) {
            case 1: 
            case 2: 
            case 5: {
                this.IChannelInputReader = 255.0;
                break;
            }
            case 3: {
                this.IChannelInputReader = 895.0;
                break;
            }
            case 4: {
                this.IChannelInputReader = 639.0;
                break;
            }
        }
        this.AacAudioCodec = this.IChannelInputReader / Math.abs(this.AdditionalMetadataValue - this.responseView);
        this.DsdAudioCodec = true;
        this.IBaseAudioCodec = (int)Math.round((double)this.AiffAudioCodec / 2048.0 / ((double)this.DSP / 2.0 / (double)n));
        this.DemuxUtils = (int)Math.round(this.AlacDecoderUtils - (double)n / 2.0);
        this.LeadingZeros = (int)Math.round(this.AlacDecoderUtils + (double)n / 2.0);
        this.AdditionalMetadataValue();
        this.IAudioFileCodec();
    }

    public void DSP(UHRSpectrumAnalyzer pumrgUjzIBRhWRYVWFJFxPn) {
        this.ThreadState = pumrgUjzIBRhWRYVWFJFxPn;
    }

    public void FFT(boolean bl) {
        this.IReadableChannelBuffer = bl;
        this.IAudioFileCodec();
    }

    public void responseView(boolean bl) {
        this.InterleavedBufferBuilder = bl;
        this.IAudioFileCodec();
    }

    public void AdditionalMetadataValue(boolean bl) {
        this.SimpleByteBuffer = bl;
        this.IAudioFileCodec();
    }

    public void DSP(double[] dArray) {
        if (!this.AiffMetaDataModel) {
            this.BufferedAacReader = dArray;
        }
        for (int i = 0; i < this.AacMetaDataModel; ++i) {
            this.ChannelDecoder[i][this.DirectStreamDigitalDecoder] = (float)dArray[i];
        }
        this.responseView(this.DirectStreamDigitalDecoder);
        ++this.DirectStreamDigitalDecoder;
        if (this.DirectStreamDigitalDecoder >= this.ChannelSplitter) {
            this.DirectStreamDigitalDecoder = 0;
        }
        this.IAudioFileCodec();
    }

    private void responseView() {
        int n;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        for (n = 0; n < 128; ++n) {
            this.DstDecoder[n] = n2 << 16 | n3 << 8 | n4;
            ++n4;
        }
        n2 = 0;
        n3 = 0;
        n4 = 127;
        for (n = 128; n < 256; ++n) {
            this.DstDecoder[n] = n2 << 16 | n3 << 8 | n4;
            ++n4;
            ++n3;
        }
        n2 = 0;
        n3 = 127;
        n4 = 255;
        for (n = 256; n < 384; ++n) {
            this.DstDecoder[n] = n2 << 16 | n3 << 8 | n4;
            ++n3;
            --n4;
        }
        n2 = 0;
        n3 = 255;
        n4 = 127;
        for (n = 384; n < 512; ++n) {
            this.DstDecoder[n] = n2 << 16 | n3 << 8 | n4;
            ++n2;
            --n4;
        }
        n2 = 127;
        n3 = 255;
        n4 = 0;
        for (n = 512; n < 640; ++n) {
            this.DstDecoder[n] = n2 << 16 | n3 << 8 | n4;
            ++n2;
        }
        n2 = 255;
        n3 = 255;
        n4 = 0;
        for (n = 640; n < 768; ++n) {
            this.DstDecoder[n] = n2 << 16 | n3 << 8 | n4;
            --n3;
        }
        n2 = 255;
        n3 = 127;
        n4 = 0;
        for (n = 768; n < 896; ++n) {
            this.DstDecoder[n] = n2 << 16 | n3 << 8 | n4;
            --n3;
        }
        n4 = 0;
        n3 = 0;
        n2 = 0;
        for (n = 0; n < 256; ++n) {
            this.EmptySimpleByteBuffer[n] = n2 << 16 | n3 << 8 | n4;
            ++n3;
        }
        n4 = 0;
        n3 = 0;
        n2 = 0;
        for (n = 0; n < 256; ++n) {
            this.ExtendedDSTSoundDataChunk[n] = n2 << 16 | n3 << 8 | n4;
            ++n2;
            ++n3;
            ++n4;
        }
        n2 = 0;
        n3 = 0;
        n4 = 0;
        for (n = 0; n < 128; ++n) {
            this.FIRDecimationFilter[n] = n2 << 16 | n3 << 8 | n4;
            ++n4;
        }
        n2 = 0;
        n3 = 0;
        n4 = 127;
        for (n = 128; n < 256; ++n) {
            this.FIRDecimationFilter[n] = n2 << 16 | n3 << 8 | n4;
            ++n2;
            ++n4;
        }
        n2 = 127;
        n3 = 0;
        n4 = 255;
        for (n = 256; n < 384; ++n) {
            this.FIRDecimationFilter[n] = n2 << 16 | n3 << 8 | n4;
            --n4;
            ++n2;
        }
        n2 = 255;
        n3 = 0;
        n4 = 127;
        for (n = 384; n < 512; ++n) {
            this.FIRDecimationFilter[n] = n2 << 16 | n3 << 8 | n4;
            --n4;
            ++n3;
        }
        n2 = 255;
        n3 = 127;
        n4 = 0;
        for (n = 512; n < 640; ++n) {
            this.FIRDecimationFilter[n] = n2 << 16 | n3 << 8 | n4;
            ++n3;
        }
    }

    private void DSP(int n, int n2) {
        this.StreamUtils = n - 80;
        this.DffChunkReaderAdapter = 520 - n2;
        this.IAudioFileCodec();
    }

    private void responseView(int n) {
        int n2 = (int)(this.AlacDecoderUtils / this.ChunkInfo);
        block7: for (int i = 0; i < 1024; ++i) {
            double d;
            int n3 = (i - 512) * this.IBaseAudioCodec;
            if ((n3 += n2) < 0 || n3 + this.IBaseAudioCodec > this.AacMetaDataModel) {
                d = -200.0;
            } else if (this.IBaseAudioCodec > 1) {
                d = -200.0;
                for (int j = 0; j < this.IBaseAudioCodec; ++j) {
                    if (!(d < (double)this.ChannelDecoder[n3 + j][n])) continue;
                    d = this.ChannelDecoder[n3 + j][n];
                }
            } else {
                d = this.ChannelDecoder[n3][n];
            }
            if (d < this.responseView) {
                d = this.responseView;
            }
            this.DsdMetaDataModel[i][n] = d;
            if ((d = this.AacAudioCodec * Math.abs(this.responseView - d)) > this.IChannelInputReader) {
                d = this.IChannelInputReader;
            }
            switch (this.IAudioMetaInformation) {
                case 1: {
                    this.DstDecodeUtil.setRGB(i, n, this.EmptySimpleByteBuffer[(int)d]);
                    continue block7;
                }
                case 2: {
                    this.DstDecodeUtil.setRGB(i, n, this.ExtendedDSTSoundDataChunk[(int)d]);
                    continue block7;
                }
                case 3: {
                    this.DstDecodeUtil.setRGB(i, n, this.DstDecoder[(int)d]);
                    continue block7;
                }
                case 4: {
                    this.DstDecodeUtil.setRGB(i, n, this.FIRDecimationFilter[(int)d]);
                    continue block7;
                }
                case 5: {
                    this.DstDecodeUtil.setRGB(i, n, this.ExtendedDSTSoundDataChunk[(int)(255.0 - d)]);
                    continue block7;
                }
            }
        }
    }

    private void AdditionalMetadataValue() {
        for (int i = 0; i < this.ChannelSplitter; ++i) {
            this.responseView(i);
        }
    }

    private void AudioFileExtension() {
        for (int i = 0; i < 1024; ++i) {
            for (int j = 0; j < this.ChannelSplitter; ++j) {
                this.DsfChunkReaderAdapter[i] = this.DsfChunkReaderAdapter[i] + this.DsdMetaDataModel[i][j];
            }
            this.DsfChunkReaderAdapter[i] = this.DsfChunkReaderAdapter[i] / (double)this.ChannelSplitter;
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        int n;
        int n2;
        int n3;
        int n4;
        double d;
        int n5;
        int n6;
        this.AiffMetaDataModel = true;
        int n7 = 0;
        int n8 = 0;
        Graphics2D graphics2D = (Graphics2D)graphics;
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        Color color = Color.decode("#FFFFFF");
        Color color2 = Color.decode("#FFFFFF");
        graphics2D.setColor(Color.decode("#555555"));
        graphics2D.drawLine(40, 500, 40, 290);
        graphics2D.drawLine(40, 250, 40, 40);
        graphics2D.setColor(Color.decode("#FFFFFF"));
        graphics2D.drawLine(80, 520, 1104, 520);
        graphics2D.drawLine(79, 520, 79, 20);
        graphics2D.drawLine(75, 20, 79, 20);
        graphics2D.drawLine(75, 270, 80, 270);
        graphics2D.drawLine(75, 520, 80, 520);
        graphics2D.drawLine(592, 520, 592, 525);
        graphics2D.drawLine(80, 520, 80, 525);
        graphics2D.drawLine(1104, 520, 1104, 525);
        String string = String.valueOf(Math.round(this.AlacDecoderUtils)) + " Hz";
        graphics2D.drawString(string, 592 - fontMetrics.stringWidth(string) / 2, 545);
        int n9 = this.DemuxUtils;
        string = n9 < 0 ? "- Hz" : String.valueOf(n9) + " Hz";
        graphics2D.drawString(string, 80, 545);
        n9 = this.LeadingZeros;
        string = n9 > this.FFT ? "- Hz" : String.valueOf(n9) + " Hz";
        graphics2D.drawString(string, 1104 - fontMetrics.stringWidth(string), 545);
        string = String.valueOf(Math.round(this.AdditionalMetadataValue)) + " dB";
        graphics2D.drawString(string, 70 - fontMetrics.stringWidth(string), 25);
        string = String.valueOf(Math.round(this.responseView)) + " dB";
        graphics2D.drawString(string, 70 - fontMetrics.stringWidth(string), 525);
        string = String.valueOf((double)Math.round(this.AudioFileExtension * 10.0) / 10.0) + " dB";
        graphics2D.drawString(string, 70 - fontMetrics.stringWidth(string), 275);
        if (this.IAudioInputStream) {
            graphics.drawImage(this.DstDecodeUtil, 80, 20, null);
            graphics2D.setColor(Color.decode("#FF0000"));
            graphics2D.drawLine(80, this.DirectStreamDigitalDecoder + 20, 1104, this.DirectStreamDigitalDecoder + 20);
        }
        int n10 = (int)(this.AlacDecoderUtils / this.ChunkInfo);
        if (this.IAudioFileCodec || this.SimpleByteBuffer) {
            switch (this.IAudioMetaInformation) {
                case 3: {
                    color = Color.decode("#FFFFFF");
                    color2 = Color.decode("#FFFFFF");
                    break;
                }
                case 2: {
                    color = Color.decode("#00FF00");
                    color2 = Color.decode("#00FF00");
                    break;
                }
                case 1: {
                    color = Color.decode("#EDFF00");
                    color2 = Color.decode("#EDFF00");
                    break;
                }
            }
        }
        if (this.IAudioFileCodec) {
            graphics2D.setColor(color);
            for (n6 = 0; n6 < 1024; ++n6) {
                n5 = (n6 - 512) * this.IBaseAudioCodec;
                if ((n5 += n10) < 0 || n5 + this.IBaseAudioCodec > this.AacMetaDataModel) {
                    d = -200.0;
                } else if (this.IBaseAudioCodec > 1) {
                    d = -200.0;
                    for (int i = 0; i < this.IBaseAudioCodec; ++i) {
                        if (!(d < this.BufferedAacReader[n5 + i])) continue;
                        d = this.BufferedAacReader[n5 + i];
                    }
                } else {
                    d = this.BufferedAacReader[n5];
                }
                d = this.AbstractManagedWorker[n6] = (this.AbstractManagedWorker[n6] + d) / 2.0;
                if (d < this.responseView) {
                    d = this.responseView;
                }
                d = 520.0 + this.MetaInfomationCopy * (this.responseView - d);
                n4 = 80 + n6;
                n3 = (int)d;
                if (n3 < -1) {
                    n3 = -1;
                }
                if (n6 == 0) {
                    n7 = n3;
                    continue;
                }
                graphics2D.drawLine(n4 - 1, n7, n4, n3);
                n7 = n3;
            }
        }
        if (this.SimpleByteBuffer) {
            this.AudioFileExtension();
            graphics2D.setColor(color2);
            for (n6 = 0; n6 < 1024; ++n6) {
                d = this.DsfChunkReaderAdapter[n6];
                if (d < this.responseView) {
                    d = this.responseView;
                }
                d = 520.0 + this.MetaInfomationCopy * (this.responseView - d);
                n4 = 80 + n6;
                n3 = (int)d;
                if (n3 < -1) {
                    n3 = -1;
                }
                if (n6 == 0) {
                    n8 = n3;
                    continue;
                }
                graphics2D.drawLine(n4 - 1, n8, n4, n3);
                n8 = n3;
            }
        }
        if (this.IReadableChannelBuffer) {
            double d2;
            graphics2D.setColor(Color.decode("#FFC400"));
            graphics2D.setStroke(new BasicStroke(2.0f));
            string = String.valueOf(Math.round(this.AlacFile)) + " Hz";
            graphics2D.drawString(string, 592 - fontMetrics.stringWidth(string) / 2, 565);
            double d3 = this.AlacInputStream < 0.0 ? 0.0 : this.AlacInputStream;
            n4 = (int)Math.round((d3 / this.ChunkInfo - (double)n10) / (double)this.IBaseAudioCodec + 512.0);
            if (n4 >= 0 && n4 <= 1024) {
                graphics2D.drawLine(80 + n4, 525, 80 + n4, 20);
                n2 = n4 < 412 ? 60 : -40;
                string = String.valueOf(Math.round(d3)) + " Hz";
                graphics2D.drawString(string, 80 + n4 + n2 - fontMetrics.stringWidth(string), 565);
            }
            if ((n4 = (int)Math.round(((d2 = this.AlacUtils > (double)(this.DSP / 2) ? (double)(this.DSP / 2) : this.AlacUtils) / this.ChunkInfo - (double)n10) / (double)this.IBaseAudioCodec + 512.0)) >= 0 && n4 <= 1025) {
                graphics2D.drawLine(80 + n4, 525, 80 + n4, 20);
                n2 = n4 > 612 ? 0 : 100;
                string = String.valueOf(Math.round(d2)) + " Hz";
                graphics2D.drawString(string, 80 + n4 + n2 - fontMetrics.stringWidth(string), 565);
            }
            graphics2D.setStroke(new BasicStroke(1.0f));
            if (this.InterleavedBufferBuilder) {
                graphics2D.setColor(Color.decode("#00FF00"));
                string = String.valueOf(Math.round(d3)) + "Hz - " + String.valueOf(Math.round(d2)) + " Hz  =>  0 Hz - " + String.valueOf(Math.round(d2 - d3)) + " Hz";
                graphics2D.drawString(string, 200, 545);
            }
        }
        if (this.StreamUtils >= 0 && this.StreamUtils <= 1024 && this.DffChunkReaderAdapter <= 500 && this.DffChunkReaderAdapter >= 0 && this.DsdAudioCodec) {
            String string2;
            double d4;
            if (this.DffChunkReaderAdapter > 333) {
                this.IRawInputReader = 100;
            } else if (this.DffChunkReaderAdapter > 166 && this.DffChunkReaderAdapter <= 333) {
                this.IRawInputReader = 10;
            } else if (this.DffChunkReaderAdapter > 0 && this.DffChunkReaderAdapter <= 166) {
                this.IRawInputReader = 1;
            }
            n2 = this.StreamUtils > 512 ? -15 : 95;
            int n11 = this.DffChunkReaderAdapter > 250 ? 35 : -65;
            graphics2D.setColor(Color.decode("#000000"));
            graphics.fillRect(80 + this.StreamUtils + n2 - 85, 520 - this.DffChunkReaderAdapter + n11 - 25, 90, 82);
            graphics2D.setColor(Color.decode("#FF0000"));
            graphics2D.drawLine(80 + this.StreamUtils - 20, 520 - this.DffChunkReaderAdapter, 80 + this.StreamUtils + 20, 520 - this.DffChunkReaderAdapter);
            graphics2D.drawLine(80 + this.StreamUtils, 520, 80 + this.StreamUtils, 20);
            graphics2D.setColor(Color.decode("#00E8E4"));
            double d5 = (((double)this.StreamUtils - 512.0) * (double)this.IBaseAudioCodec + (double)n10) * this.ChunkInfo;
            if (d5 >= 0.0 && d5 <= (double)this.FFT) {
                string = String.valueOf((double)Math.round(d5 * 100.0) / 100.0) + " Hz";
                d4 = this.responseView + (double)this.DffChunkReaderAdapter / this.MetaInfomationCopy;
                string2 = String.valueOf(Math.round(d4)) + " dB";
            } else {
                string = "- Hz";
                string2 = "- dB";
            }
            graphics2D.drawString(string, 80 + this.StreamUtils + n2 - fontMetrics.stringWidth(string), 520 - this.DffChunkReaderAdapter - 10 + n11);
            graphics2D.drawString(string2, 80 + this.StreamUtils + n2 - fontMetrics.stringWidth(string2), 520 - this.DffChunkReaderAdapter + 10 + n11);
            int n12 = 500 - this.DffChunkReaderAdapter;
            if (n12 > this.ChannelSplitter - 1) {
                n12 = this.ChannelSplitter - 1;
            } else if (n12 < 0) {
                n12 = 0;
            }
            graphics2D.setColor(Color.decode("#FFFF00"));
            n5 = (this.StreamUtils - 512) * this.IBaseAudioCodec;
            if ((n5 += n10) >= 0 && n5 < this.AacMetaDataModel) {
                d4 = this.ChannelDecoder[n5][n12];
                string = String.valueOf(Math.round(d4)) + " dB";
            } else {
                string = "- dB";
            }
            graphics2D.drawString(string, 80 + this.StreamUtils + n2 - fontMetrics.stringWidth(string), 520 - this.DffChunkReaderAdapter + 30 + n11);
            string = "< > " + String.valueOf(Math.round(this.IRawInputReader)) + " Hz";
            graphics2D.drawString(string, 80 + this.StreamUtils + n2 - fontMetrics.stringWidth(string), 520 - this.DffChunkReaderAdapter + 50 + n11);
        }
        if (this.StreamUtils < 0) {
            graphics2D.setColor(Color.decode("#FF0000"));
            n = this.DffChunkReaderAdapter - 30;
            graphics2D.drawLine(80 + this.StreamUtils, 520 - n, 80 + this.StreamUtils - 10, 520 - (n + 10));
            graphics2D.drawLine(80 + this.StreamUtils, 520 - n, 80 + this.StreamUtils + 10, 520 - (n + 10));
            n = this.DffChunkReaderAdapter + 30;
            graphics2D.drawLine(80 + this.StreamUtils, 520 - n, 80 + this.StreamUtils - 10, 520 - (n - 10));
            graphics2D.drawLine(80 + this.StreamUtils, 520 - n, 80 + this.StreamUtils + 10, 520 - (n - 10));
            string = this.StreamUtils < -40 ? "UL" : "LL";
            graphics2D.drawString(string, 80 + this.StreamUtils - fontMetrics.stringWidth(string) / 2, 520 - (this.DffChunkReaderAdapter + 40));
        }
        if (this.StreamUtils > 0 && this.DffChunkReaderAdapter < 0 && this.IReadableChannelBuffer) {
            graphics2D.setColor(Color.decode("#FFC400"));
            n = this.StreamUtils - 30;
            graphics2D.drawLine(80 + n, 520 - this.DffChunkReaderAdapter, 80 + (n + 10), 520 - (this.DffChunkReaderAdapter - 10));
            graphics2D.drawLine(80 + n, 520 - this.DffChunkReaderAdapter, 80 + (n + 10), 520 - (this.DffChunkReaderAdapter + 10));
            n = this.StreamUtils + 30;
            graphics2D.drawLine(80 + n, 520 - this.DffChunkReaderAdapter, 80 + (n - 10), 520 - (this.DffChunkReaderAdapter - 10));
            graphics2D.drawLine(80 + n, 520 - this.DffChunkReaderAdapter, 80 + (n - 10), 520 - (this.DffChunkReaderAdapter + 10));
            string = "BW";
            graphics2D.drawString(string, 80 + this.StreamUtils - 45 - fontMetrics.stringWidth(string) / 2, 520 - this.DffChunkReaderAdapter + 5);
        }
        this.AiffMetaDataModel = false;
    }

    private void IAudioFileCodec() {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                Display.this.repaint();
            }
        });
    }

    @Override
    public void run() {
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        int n = mouseEvent.getX();
        int n2 = mouseEvent.getY();
        double d = this.AlacDecoderUtils;
        if (n > 80 && n2 < 520) {
            if (this.MyStream < n) {
                d -= (double)this.IRawInputReader;
            } else if (this.MyStream > n) {
                d += (double)this.IRawInputReader;
            }
            this.MyStream = n;
            if (d < 0.0) {
                d = 0.0;
            } else if (d > (double)this.DSP / 2.0) {
                d = (double)this.DSP / 2.0;
            }
            this.AlacDecoderUtils = d;
            this.AlacInputStream = this.AlacDecoderUtils - this.AlacFile / 2.0;
            this.AlacUtils = this.AlacDecoderUtils + this.AlacFile / 2.0;
            this.DemuxUtils = (int)Math.round(this.AlacDecoderUtils - (double)this.DemuxResT / 2.0);
            this.LeadingZeros = (int)Math.round(this.AlacDecoderUtils + (double)this.DemuxResT / 2.0);
            this.ThreadState.DSP(this.AlacDecoderUtils, this.AlacFile);
            this.IMetadataReader = true;
        }
        if (n >= 40 && n < 80) {
            if (this.QTMovieT < n2) {
                this.responseView -= 1.0;
            } else if (this.QTMovieT > n2) {
                this.responseView += 1.0;
            }
            this.QTMovieT = n2;
            if (this.responseView < -200.0) {
                this.responseView = -200.0;
            } else if (this.responseView > 0.0) {
                this.responseView = 0.0;
            } else if (this.responseView >= this.AdditionalMetadataValue) {
                this.responseView = this.AdditionalMetadataValue - 1.0;
            }
            this.AudioFileExtension = (this.AdditionalMetadataValue - this.responseView) / 2.0 + this.responseView;
            this.IMetadataReader = true;
        }
        if (n < 40) {
            if (this.SampleDuration < n2) {
                this.AdditionalMetadataValue -= 1.0;
            } else if (this.SampleDuration > n2) {
                this.AdditionalMetadataValue += 1.0;
            }
            this.SampleDuration = n2;
            if (this.AdditionalMetadataValue < -200.0) {
                this.AdditionalMetadataValue = -200.0;
            } else if (this.AdditionalMetadataValue > 0.0) {
                this.AdditionalMetadataValue = 0.0;
            } else if (this.AdditionalMetadataValue <= this.responseView) {
                this.AdditionalMetadataValue = this.responseView + 1.0;
            }
            this.AudioFileExtension = (this.AdditionalMetadataValue - this.responseView) / 2.0 + this.responseView;
            this.IMetadataReader = true;
        }
        if (n < 80) {
            this.MetaInfomationCopy = 500.0 / Math.abs(this.AdditionalMetadataValue - this.responseView);
            this.AacAudioCodec = this.IChannelInputReader / Math.abs(this.AdditionalMetadataValue - this.responseView);
        }
        if (n > 80 && n2 > 520 && this.IReadableChannelBuffer) {
            if (this.SampleInfo < n) {
                this.AlacFile += 100.0;
            } else if (this.SampleInfo > n) {
                this.AlacFile -= 100.0;
            }
            this.SampleInfo = n;
            if (this.AlacFile < 100.0) {
                this.AlacFile = 100.0;
            } else if (this.AlacFile > (double)(this.DSP / 2)) {
                this.AlacFile = this.DSP / 2;
            }
            this.AlacInputStream = this.AlacDecoderUtils - this.AlacFile / 2.0;
            this.AlacUtils = this.AlacDecoderUtils + this.AlacFile / 2.0;
            this.ThreadState.DSP(this.AlacDecoderUtils, this.AlacFile);
        }
        this.DSP(mouseEvent.getX(), mouseEvent.getY());
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == this) {
            this.DSP(mouseEvent.getX(), mouseEvent.getY());
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (this.IMetadataReader) {
            this.IMetadataReader = false;
            this.AdditionalMetadataValue();
            this.IAudioFileCodec();
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == this) {
            this.DSP(-50, -50);
        }
    }
}

