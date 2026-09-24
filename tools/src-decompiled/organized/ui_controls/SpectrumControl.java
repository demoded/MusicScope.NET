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
import java.awt.event.MouseMotionListener;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JComponent;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.LevelMeterControl;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerEventListener;
import sdfgjkljljoftrytrszgijpokjprs.ISpectrumControlLogSwitchListener;
import sdfgjkljljoftrytrszgijpokjprs.IMetaInformationListener;
import sdfgjkljljoftrytrszgijpokjprs.ValueModel;
import sdfgjkljljoftrytrszgijpokjprs.ISpectrumControlScaledListener;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;
import sdfgjkljljoftrytrszgijpokjprs.IComputationListener;
import sdfgjkljljoftrytrszgijpokjprs.ReportingParameter;
import sdfgjkljljoftrytrszgijpokjprs.SpectrumModel;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;
import sdfgjkljljoftrytrszgijpokjprs.WaterfallControl;
import sdfgjkljljoftrytrszgijpokjprs.IValueReporting;
import sdfgjkljljoftrytrszgijpokjprs.ITrackLoadedListener;
import sdfgjkljljoftrytrszgijpokjprs.IAnalyzerStartListener;

public class SpectrumControl
extends JComponent
implements MouseListener,
MouseMotionListener,
Runnable,
IPlayerStateListener,
IPlayerEventListener,
IMetaInformationListener,
IComputationListener<SpectrumModel>,
IValueReporting<SpectrumControl>,
ITrackLoadedListener,
IAnalyzerStartListener {
    private final ArrayList<ISpectrumControlScaledListener> DSP;
    private final ArrayList<ISpectrumControlLogSwitchListener> FFT;
    private final Locale responseView = new Locale("en", "us");
    private boolean AdditionalMetadataValue = false;
    private final int AudioFileExtension = -200;
    private int IAudioFileCodec = -100;
    private int IAudioInputStream = -100;
    private int IAudioMetaInformation = -100;
    private int IBaseAudioCodec = 1;
    private int MetaInfomationCopy = 1;
    private final int AacAudioCodec = 250;
    private final int AacMetaDataModel = 1024;
    private int BufferedAacReader = 24;
    private int AiffAudioCodec = 24;
    private int AiffMetaDataModel = 96000;
    private double AlacAudioCodec;
    private double AlacMetaDataModel;
    private double BufferedAlacReader;
    private double AlacContextModel = 1.0;
    private double AlacDecoderUtils = 1.0;
    private double AlacFile = 0.1;
    private double AlacInputStream = 2.5E-4;
    private final double[] AlacUtils = new double[1026];
    private final double[] ChunkInfo = new double[1026];
    private final double[] DemuxResT = new double[1026];
    private final double[] DemuxUtils = new double[1026];
    private final double[] LeadingZeros = new double[1026];
    private final double[] MyStream = new double[1026];
    private final double[] QTMovieT = new double[1026];
    private final double[] SampleDuration = new double[1026];
    private final double[] SampleInfo = new double[1026];
    private final double[] StreamUtils = new double[1026];
    private final double[] DffChunkReaderAdapter = new double[1026];
    private final double[] DsdAudioCodec = new double[1026];
    private final double[] DsdMetaDataModel = new double[1026];
    private final double[] DsfChunkReaderAdapter = new double[1026];
    private final double[] AbstractManagedWorker = new double[1026];
    private final double[] ChannelDecoder = new double[1026];
    private final double[] ChannelSplitter = new double[1026];
    private final double[][] DirectStreamDigitalDecoder = new double[][]{{-96.0, -60.0, -40.0, -24.0, -12.0, -6.0, 0.0}, {-144.0, -100.0, -60.0, -40.0, -24.0, -12.0, 0.0}, {-200.0, -160.0, -144.0, -96.0, -60.0, -24.0, 0.0}};
    private final String[][] DstDecodeUtil = new String[][]{{"-96", "-60", "-40", "-24", "-12", "-6", "0"}, {"-144", "-100", "-60", "-40", "-24", "-12", "0"}, {"-200", "-160", "-144", "-96", "-60", "-24", "0"}};
    private boolean DstDecoder = false;
    private boolean EmptySimpleByteBuffer = false;
    private boolean ExtendedDSTSoundDataChunk = false;
    private boolean FIRDecimationFilter = false;
    private boolean IChannelInputReader = false;
    private final double IMetadataReader = 4096.0;
    private double IRawInputReader = 0.005;
    private double IReadableChannelBuffer;
    private final double[] InterleavedBufferBuilder = new double[8192];
    private final double[] SimpleByteBuffer = new double[8192];
    private double StreamDecoder = 0.0;
    private double ThreadState = 0.0;
    private final Color[] BufferedFlacReader = new Color[512];
    private Color FlacAudioCodec = new Color(0, 180, 0);
    private Color FlacMetaDataModel = new Color(0, 255, 0);
    private Color ChannelData = new Color(0, 0, 255);
    private double Constants;
    private SpectrumModel FLACDecoder = new SpectrumModel(new double[16384], new double[16384], new double[16384], new double[16384], new double[16384]);

    public SpectrumControl() {
        this.DSP = new ArrayList();
        this.FFT = new ArrayList();
        this.FFT();
    }

    public void DSP(ISpectrumControlScaledListener wzJNMaKxyCrKDJVNaqpMAQv) {
        if (this.DSP != null && !this.DSP.contains(wzJNMaKxyCrKDJVNaqpMAQv)) {
            this.DSP.add(wzJNMaKxyCrKDJVNaqpMAQv);
        }
    }

    private void DSP(double d, double d2, int n) {
        if (this.DSP != null) {
            for (ISpectrumControlScaledListener wzJNMaKxyCrKDJVNaqpMAQv : this.DSP) {
                wzJNMaKxyCrKDJVNaqpMAQv.DSP(d, d2, n);
            }
        }
    }

    public void DSP(ISpectrumControlLogSwitchListener knBQDXFKOonhSgKXKARkjlE) {
        if (this.FFT != null && !this.FFT.contains(knBQDXFKOonhSgKXKARkjlE)) {
            this.FFT.add(knBQDXFKOonhSgKXKARkjlE);
        }
    }

    private void DSP(boolean bl) {
        if (this.FFT != null) {
            for (ISpectrumControlLogSwitchListener knBQDXFKOonhSgKXKARkjlE : this.FFT) {
                knBQDXFKOonhSgKXKARkjlE.DSP(bl);
            }
        }
    }

    private void FFT() {
        int n;
        this.IAudioFileCodec();
        this.FLACDecoder = new SpectrumModel(new double[16384], new double[16384], new double[16384], new double[16384], new double[16384]);
        if (this.AdditionalMetadataValue) {
            this.AlacAudioCodec = Math.pow(10.0, -10.0);
            this.IBaseAudioCodec = 2;
            this.AlacMetaDataModel = 5.0E8;
            this.BufferedAlacReader = 28.1;
        } else if (this.BufferedAacReader == 16) {
            this.AlacAudioCodec = Math.pow(10.0, -4.8);
            this.IBaseAudioCodec = 0;
            this.AlacMetaDataModel = 3000.0;
            this.BufferedAlacReader = 70.4;
        } else {
            this.AlacAudioCodec = Math.pow(10.0, -7.2);
            this.IBaseAudioCodec = 1;
            this.AlacMetaDataModel = 500000.0;
            this.BufferedAlacReader = 42.9;
        }
        for (n = 0; n < 1025; ++n) {
            this.ChunkInfo[n] = this.DemuxResT[n] = this.AlacAudioCodec;
            this.AlacUtils[n] = this.DemuxResT[n];
            this.ChannelSplitter[n] = 0.0;
            this.ChannelDecoder[n] = 0.0;
            this.AbstractManagedWorker[n] = 0.0;
            this.DsfChunkReaderAdapter[n] = 0.0;
            this.DsdMetaDataModel[n] = 0.0;
            this.SampleDuration[n] = 0.0;
            this.DsdAudioCodec[n] = 0.0;
            this.DffChunkReaderAdapter[n] = 0.0;
            this.StreamUtils[n] = 0.0;
            this.SampleInfo[n] = 0.0;
        }
        this.IReadableChannelBuffer = 1024.0 / Math.log10(4096.0 * this.IRawInputReader + 1.0);
        int n2 = 0;
        int n3 = 255;
        int n4 = 0;
        for (n = 0; n < 200; ++n) {
            this.BufferedFlacReader[n] = new Color(n2, n3, n4);
        }
        n2 = 255;
        n3 = 255;
        n4 = 0;
        for (n = 200; n < 300; ++n) {
            this.BufferedFlacReader[n] = new Color(n2, n3, n4);
        }
        n2 = 255;
        n3 = 0;
        n4 = 0;
        for (n = 300; n < 500; ++n) {
            this.BufferedFlacReader[n] = new Color(n2, n3, n4);
        }
        this.repaint();
    }

    private void responseView() {
        this.FLACDecoder = new SpectrumModel(new double[16384], new double[16384], new double[16384], new double[16384], new double[16384]);
        for (int i = 0; i < 1025; ++i) {
            this.ChunkInfo[i] = this.DemuxResT[i] = this.AlacAudioCodec;
            this.ChannelSplitter[i] = 0.0;
            this.ChannelDecoder[i] = 0.0;
            this.AbstractManagedWorker[i] = 0.0;
            this.DsfChunkReaderAdapter[i] = 0.0;
            this.DsdMetaDataModel[i] = 0.0;
            this.SampleDuration[i] = 0.0;
            this.DsdAudioCodec[i] = 0.0;
            this.DffChunkReaderAdapter[i] = 0.0;
            this.StreamUtils[i] = 0.0;
            this.SampleInfo[i] = 0.0;
        }
        this.repaint();
    }

    @ReportingParameter(DSP="Spectrum:")
    public ArrayList<AbstractMap.SimpleEntry<String, ValueModel>> reportMaxSpectrum() {
        int n = (int)((double)this.AiffMetaDataModel / 2.0);
        ArrayList<AbstractMap.SimpleEntry<String, ValueModel>> arrayList = new ArrayList<AbstractMap.SimpleEntry<String, ValueModel>>();
        arrayList.add(new AbstractMap.SimpleEntry<String, ValueModel>("[kHz]", new ValueModel("[dB]", "")));
        for (int i = 1000; i < 50000; i += 1000) {
            int n2 = i * 1024 / n;
            if (i > n) break;
            arrayList.add(new AbstractMap.SimpleEntry<String, ValueModel>(String.valueOf(Math.round((double)i / 1000.0)), new ValueModel(String.format(this.responseView, "%7.1f", 20.0 * Math.log10(this.AlacUtils[n2])), "")));
        }
        return arrayList;
    }

    public void DSP(int n) {
        if (n == -100) {
            this.AlacDecoderUtils = 1.0;
            this.AlacContextModel = 1.0;
        } else if (n < 250) {
            if (n < this.IAudioInputStream) {
                this.AlacContextModel += this.AlacFile;
            }
            if (n > this.IAudioInputStream) {
                this.AlacContextModel -= this.AlacFile;
            }
            if (this.AlacContextModel > 4.0) {
                this.AlacContextModel = 4.0;
            }
            if (this.AlacContextModel < 0.01) {
                this.AlacContextModel = 0.01;
            }
            if (this.AlacContextModel > 0.5) {
                this.AlacFile = 0.1;
            }
            if (this.AlacContextModel < 0.5) {
                this.AlacFile = 0.01;
            }
            if (this.AlacContextModel < 0.05) {
                this.AlacFile = 0.001;
            }
            this.AlacDecoderUtils = 244.0 / (this.BufferedAlacReader * Math.log10(this.AlacContextModel * this.AlacMetaDataModel + 1.0));
            this.IAudioInputStream = n;
        }
        this.DSP(this.AlacContextModel, this.AlacDecoderUtils, 1);
        this.repaint();
    }

    public void DSP(int n, int n2) {
        if (n == -100) {
            this.IRawInputReader = 0.005;
        } else if (n2 > 250) {
            if (n < this.IAudioMetaInformation) {
                this.IRawInputReader -= this.AlacInputStream;
            }
            if (n > this.IAudioMetaInformation) {
                this.IRawInputReader += this.AlacInputStream;
            }
            this.IAudioMetaInformation = n;
            if (this.IRawInputReader > 0.015) {
                this.IRawInputReader = 0.015;
            }
            if (this.IRawInputReader < 1.0E-4) {
                this.IRawInputReader = 1.0E-4;
            }
            if (this.IRawInputReader < 0.005) {
                this.AlacInputStream = 1.0E-4;
            }
            if (this.IRawInputReader < 0.003) {
                this.AlacInputStream = 5.0E-5;
            }
            if (this.IRawInputReader < 0.001) {
                this.AlacInputStream = 2.5E-5;
            }
            if (this.IRawInputReader > 0.005) {
                this.AlacInputStream = 2.5E-4;
            }
        }
        this.IReadableChannelBuffer = 1024.0 / Math.log10(4096.0 * this.IRawInputReader + 1.0);
        this.DSP(this.IRawInputReader, 0.0, 2);
        this.repaint();
    }

    public void FFT(int n, int n2) {
        this.IAudioFileCodec = n;
        this.IAudioInputStream = n2;
        this.repaint();
    }

    private void IAudioFileCodec() {
        int n = 4096;
        for (int i = 0; i < n; ++i) {
            double d = Math.PI * 2 * (double)i / (double)(n - 1);
            this.SimpleByteBuffer[i] = 0.27105140069342 - 0.43329793923448 * Math.cos(d) + 0.21812299954311 * Math.cos(2.0 * d) - 0.06592544638803 * Math.cos(3.0 * d) + 0.01081174209837 * Math.cos(4.0 * d) - 7.7658482522E-4 * Math.cos(5.0 * d) + 1.388721735E-5 * Math.cos(6.0 * d);
        }
    }

    private void IAudioInputStream() {
        int n;
        boolean bl = false;
        int n2 = 4096;
        int n3 = n2 / 8;
        double[] dArray = new double[n2];
        double[] dArray2 = new double[n2];
        for (n = 0; n < n2; ++n) {
            dArray[n] = Math.log10(this.FLACDecoder.DSP()[n]);
            dArray2[n] = 0.0;
        }
        int n4 = n2;
        int n5 = 12;
        int n6 = n4 >> 1;
        int n7 = 0;
        for (n = 0; n < n4 - 1; ++n) {
            int n8;
            if (n < n7) {
                double d = dArray[n];
                double d2 = dArray2[n];
                dArray[n] = dArray[n7];
                dArray2[n] = dArray2[n7];
                dArray[n7] = d;
                dArray2[n7] = d2;
            }
            for (n8 = n6; n8 <= n7; n7 -= n8, n8 >>= 1) {
            }
            n7 += n8;
        }
        double d = -1.0;
        double d3 = 0.0;
        int n9 = 1;
        for (int i = 0; i < n5; ++i) {
            int n10 = n9;
            n9 <<= 1;
            double d4 = 1.0;
            double d5 = 0.0;
            for (n7 = 0; n7 < n10; ++n7) {
                for (n = n7; n < n4; n += n9) {
                    int n11 = n + n10;
                    double d6 = d4 * dArray[n11] - d5 * dArray2[n11];
                    double d7 = d4 * dArray2[n11] + d5 * dArray[n11];
                    dArray[n11] = dArray[n] - d6;
                    dArray2[n11] = dArray2[n] - d7;
                    int n12 = n;
                    dArray[n12] = dArray[n12] + d6;
                    int n13 = n;
                    dArray2[n13] = dArray2[n13] + d7;
                }
                double d8 = d4 * d - d5 * d3;
                d5 = d4 * d3 + d5 * d;
                d4 = d8;
            }
            d3 = Math.sqrt((1.0 - d) / 2.0);
            if (bl) {
                d3 = -d3;
            }
            d = Math.sqrt((1.0 + d) / 2.0);
        }
        dArray[0] = 0.0;
        dArray2[0] = 0.0;
        for (n = 0; n < n2 / 2; ++n) {
            double d9 = dArray[n] / (double)n3;
            double d10 = dArray2[n] / (double)n3;
            this.InterleavedBufferBuilder[n] = (this.InterleavedBufferBuilder[n] + Math.sqrt(d9 * d9 + d10 * d10)) / 2.0;
            if (!(this.InterleavedBufferBuilder[n] < this.AlacAudioCodec)) continue;
            this.InterleavedBufferBuilder[n] = this.AlacAudioCodec;
        }
    }

    @Override
    public void DSP(SpectrumModel hBuuKncbHhHairukyzCONrT2, ComputationController.DSP rHAjVyBgPhqkQKsOvJMPMYn2) {
        boolean bl = false;
        if (rHAjVyBgPhqkQKsOvJMPMYn2 == ComputationController.DSP.DSP) {
            int n;
            int n2;
            this.FLACDecoder = new SpectrumModel(hBuuKncbHhHairukyzCONrT2);
            for (n2 = 0; n2 < 1024; ++n2) {
                double d;
                if (!this.DstDecoder) {
                    d = this.FLACDecoder.DSP()[n2];
                } else {
                    n = (int)((Math.pow(10.0, (double)n2 / this.IReadableChannelBuffer) - 1.0) / this.IRawInputReader);
                    d = this.FLACDecoder.DSP()[n];
                }
                if (!(this.AlacUtils[n2] < d)) continue;
                this.AlacUtils[n2] = d;
            }
            if (this.DstDecoder && this.ExtendedDSTSoundDataChunk) {
                this.IAudioInputStream();
            }
            if (this.FIRDecimationFilter | this.IChannelInputReader) {
                for (n2 = 0; n2 < 1024; ++n2) {
                    double d;
                    double d2;
                    double d3;
                    double d4;
                    if (!this.DstDecoder) {
                        d4 = this.FLACDecoder.FFT()[n2];
                        d3 = this.FLACDecoder.responseView()[n2];
                        d2 = this.FLACDecoder.AdditionalMetadataValue()[n2];
                        d = this.FLACDecoder.AudioFileExtension()[n2];
                    } else {
                        n = (int)((Math.pow(10.0, (double)n2 / this.IReadableChannelBuffer) - 1.0) / this.IRawInputReader);
                        d4 = this.FLACDecoder.FFT()[n];
                        d3 = this.FLACDecoder.responseView()[n];
                        d2 = this.FLACDecoder.AdditionalMetadataValue()[n];
                        d = this.FLACDecoder.AudioFileExtension()[n];
                    }
                    this.ChunkInfo[n2] = (this.ChunkInfo[n2] + Math.sqrt(d4 * d4 + d3 * d3)) / 2.0;
                    this.DemuxResT[n2] = (this.DemuxResT[n2] + Math.sqrt(d2 * d2 + d * d)) / 2.0;
                    this.DemuxUtils[n2] = d4;
                    this.LeadingZeros[n2] = d3;
                    this.MyStream[n2] = d2;
                    this.QTMovieT[n2] = d;
                }
            }
            if (this.IChannelInputReader) {
                this.ThreadState = 0.0;
                for (n2 = 0; n2 < 1024; ++n2) {
                    double d;
                    if (this.ChunkInfo[n2] > this.AlacAudioCodec && this.DemuxResT[n2] > this.AlacAudioCodec) {
                        double d5;
                        double d6 = Math.atan2(this.LeadingZeros[n2], this.DemuxUtils[n2]);
                        d = d6 - (d5 = Math.atan2(this.QTMovieT[n2], this.MyStream[n2]));
                        if (d > Math.PI) {
                            d -= Math.PI * 2;
                        }
                        if (d < -Math.PI) {
                            d += Math.PI * 2;
                        }
                        d = Math.abs(d);
                    } else {
                        d = 0.0;
                    }
                    this.SampleDuration[n2] = d;
                    this.SampleDuration[n2] = (this.SampleDuration[n2] + this.SampleInfo[n2] + this.StreamUtils[n2] + this.DffChunkReaderAdapter[n2]) / 4.0;
                    this.DsdAudioCodec[n2] = this.DffChunkReaderAdapter[n2];
                    this.DffChunkReaderAdapter[n2] = this.StreamUtils[n2];
                    this.StreamUtils[n2] = this.SampleInfo[n2];
                    this.SampleInfo[n2] = this.SampleDuration[n2];
                    if (this.ChunkInfo[n2] > this.AlacAudioCodec || this.DemuxResT[n2] > this.AlacAudioCodec) {
                        this.DsdMetaDataModel[n2] = this.DemuxResT[n2] - this.ChunkInfo[n2];
                        this.DsdMetaDataModel[n2] = (this.DsdMetaDataModel[n2] + this.DsfChunkReaderAdapter[n2] + this.AbstractManagedWorker[n2] + this.ChannelDecoder[n2]) / 4.0;
                        this.ChannelSplitter[n2] = this.ChannelDecoder[n2];
                        this.ChannelDecoder[n2] = this.AbstractManagedWorker[n2];
                        this.AbstractManagedWorker[n2] = this.DsfChunkReaderAdapter[n2];
                        this.DsfChunkReaderAdapter[n2] = this.DsdMetaDataModel[n2];
                        if (!(Math.abs(this.DsdMetaDataModel[n2]) > this.ThreadState)) continue;
                        this.ThreadState = Math.abs(this.DsdMetaDataModel[n2]);
                        continue;
                    }
                    this.DsdMetaDataModel[n2] = 0.0;
                }
                if (this.ThreadState < 0.001) {
                    this.ThreadState = 1.0;
                }
            }
            EventQueue.invokeLater(this);
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        double d;
        int n;
        int n2;
        String string;
        double d2;
        int n3;
        Graphics2D graphics2D = (Graphics2D)graphics;
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        boolean bl = false;
        double d3 = 0.0;
        double d4 = 0.0;
        graphics2D.setColor(Color.decode("#00FF00"));
        if (!this.DstDecoder) {
            graphics2D.drawString("Linear Frequency Spectrum [kHz]", 40, 267);
            graphics2D.setColor(Color.decode("#EEEEEE"));
            for (n3 = 1; n3 < 5; ++n3) {
                d2 = (double)Math.round((double)(this.AiffMetaDataModel / 2 * n3) / 40.0) / 100.0;
                string = String.valueOf(d2);
                n2 = 36 + n3 * 256;
                graphics2D.drawLine(n2, 250, n2, 255);
                graphics2D.drawString(string, n2 - fontMetrics.stringWidth(string) / 2, 270);
            }
        } else {
            graphics2D.drawString("Log. Frequency Spectrum [kHz]", 40, 267);
            graphics2D.setColor(Color.decode("#EEEEEE"));
            for (n3 = 1; n3 < 5; ++n3) {
                n = (int)((Math.pow(10.0, (double)(n3 * 256) / this.IReadableChannelBuffer) - 1.0) / this.IRawInputReader);
                d2 = (double)n / 4096.0 * (double)this.AiffMetaDataModel / 20.0;
                d2 = (double)Math.round(d2) / 100.0;
                string = String.valueOf(d2);
                n2 = 36 + n3 * 256;
                graphics2D.drawLine(n2, 250, n2, 255);
                graphics2D.drawString(string, n2 - fontMetrics.stringWidth(string) / 2, 270);
            }
            if (!this.ExtendedDSTSoundDataChunk) {
                graphics2D.setColor(Color.decode("#666666"));
            } else {
                graphics2D.setColor(Color.decode("#00FF00"));
            }
            graphics2D.drawString("Cepstrum", 830, 267);
        }
        if (this.AdditionalMetadataValue) {
            graphics2D.setColor(Color.decode("#00FF00"));
        } else {
            graphics2D.setColor(Color.decode("#666666"));
        }
        graphics2D.drawString("-200dB Mode", 625, 267);
        if (this.FIRDecimationFilter) {
            graphics2D.setColor(Color.decode("#00FF00"));
        } else {
            graphics2D.setColor(Color.decode("#666666"));
        }
        graphics2D.drawString("Left/Right", 330, 267);
        if (this.IChannelInputReader) {
            graphics2D.setColor(Color.decode("#00FF00"));
        } else {
            graphics2D.setColor(Color.decode("#666666"));
        }
        graphics2D.drawString("Pano/Phase", 430, 267);
        if (this.IChannelInputReader) {
            graphics2D.setColor(Color.decode("#DDDDDD"));
            graphics2D.drawString("L", 1070 - fontMetrics.stringWidth("L"), 62);
            graphics2D.drawString("R", 1070 - fontMetrics.stringWidth("R"), 190);
        }
        graphics2D.setColor(Color.decode("#666666"));
        graphics2D.drawString("dB", 0, 10);
        for (n3 = 0; n3 < 7; ++n3) {
            d = Math.pow(10.0, this.DirectStreamDigitalDecoder[this.IBaseAudioCodec][n3] / 20.0);
            int n4 = 250 - (int)(this.AlacDecoderUtils * this.BufferedAlacReader * Math.log10(this.AlacContextModel * d * this.AlacMetaDataModel + 1.0));
            graphics2D.setColor(Color.decode("#333333"));
            graphics2D.drawLine(36, --n4, 1060, n4);
            graphics2D.setColor(Color.decode("#EEEEEE"));
            graphics2D.drawString(this.DstDecodeUtil[this.IBaseAudioCodec][n3], 32 - fontMetrics.stringWidth(this.DstDecodeUtil[this.IBaseAudioCodec][n3]), n4 + 5);
        }
        n = 0;
        for (n3 = 0; n3 < 1024; ++n3) {
            double d5;
            n2 = n3 + 36;
            if (!this.DstDecoder) {
                d5 = this.FLACDecoder.DSP()[n3];
            } else {
                n = (int)((Math.pow(10.0, (double)n3 / this.IReadableChannelBuffer) - 1.0) / this.IRawInputReader);
                d5 = this.FLACDecoder.DSP()[n];
            }
            if (d5 < this.AlacAudioCodec) {
                d5 = this.AlacAudioCodec;
            }
            d5 = d5 != 0.0 ? 250.0 - this.AlacDecoderUtils * this.BufferedAlacReader * Math.log10(this.AlacContextModel * d5 * this.AlacMetaDataModel + 1.0) : 250.0;
            double d6 = this.AlacUtils[n3] != 0.0 ? 250.0 - this.AlacDecoderUtils * this.BufferedAlacReader * Math.log10(this.AlacContextModel * this.AlacUtils[n3] * this.AlacMetaDataModel + 1.0) : 250.0;
            if (!this.FIRDecimationFilter) {
                graphics2D.setColor(this.FlacAudioCodec);
                graphics2D.drawLine(n2, 250, n2, (int)(d5 += 1.0));
            }
            if (this.FIRDecimationFilter) {
                double d7 = this.ChunkInfo[n3];
                double d8 = this.DemuxResT[n3];
                if (d7 < this.AlacAudioCodec) {
                    d7 = this.AlacAudioCodec;
                }
                if (d8 < this.AlacAudioCodec) {
                    d8 = this.AlacAudioCodec;
                }
                d7 = d7 != 0.0 ? 250.0 - this.AlacDecoderUtils * this.BufferedAlacReader * Math.log10(this.AlacContextModel * d7 * this.AlacMetaDataModel + 1.0) : 250.0;
                d8 = d8 != 0.0 ? 250.0 - this.AlacDecoderUtils * this.BufferedAlacReader * Math.log10(this.AlacContextModel * d8 * this.AlacMetaDataModel + 1.0) : 250.0;
                d7 += 1.0;
                d8 += 1.0;
                if (n3 > 0) {
                    graphics2D.setStroke(new BasicStroke(2.0f));
                    graphics2D.setColor(this.ChannelData);
                    graphics2D.drawLine(n2 - 1, (int)d4, n2, (int)d8);
                    graphics2D.setColor(this.FlacMetaDataModel);
                    graphics2D.drawLine(n2 - 1, (int)d3, n2, (int)d7);
                }
                d3 = d7;
                d4 = d8;
            }
            graphics2D.setStroke(new BasicStroke(1.0f));
            graphics2D.setColor(Color.decode("#FFBF00"));
            graphics2D.drawLine(n2, (int)(d6 += 1.0), n2, (int)d6);
            if (this.DstDecoder && this.ExtendedDSTSoundDataChunk && n3 > 0) {
                double d9 = this.InterleavedBufferBuilder[n3];
                d9 = d9 != 0.0 ? 250.0 - 5000000.0 * Math.log10(d9 * 1.0E-4 + 1.0) : 250.0;
                graphics2D.setColor(Color.decode("#FF0000"));
                graphics2D.drawLine(n2, 250, n2, (int)d9);
                this.Constants = d9;
            }
            if (!this.IChannelInputReader) continue;
            graphics2D.setStroke(new BasicStroke(2.0f));
            double d10 = this.DsdMetaDataModel[n3] / this.ThreadState;
            int n5 = (int)(this.SampleDuration[n3] / Math.PI * 499.0);
            graphics2D.setColor(this.BufferedFlacReader[n5]);
            graphics2D.drawLine(n2 - 1, (int)this.StreamDecoder + 125, n2, (int)(d10 *= 124.0) + 125);
            this.StreamDecoder = d10;
            graphics2D.setStroke(new BasicStroke(1.0f));
        }
        if (this.IAudioFileCodec > 35 && this.IAudioFileCodec < 1061) {
            double d11;
            int n6 = this.IAudioFileCodec - 36;
            if (!this.DstDecoder) {
                d11 = (double)Math.round(0.5 * (double)this.AiffMetaDataModel / 1024.0 * (double)n6 / 100.0) / 10.0;
            } else {
                n = (int)((Math.pow(10.0, (double)n6 / this.IReadableChannelBuffer) - 1.0) / this.IRawInputReader);
                d11 = (double)n / 4096.0 * (double)this.AiffMetaDataModel / 20.0;
                d11 = (double)Math.round(d11) / 100.0;
            }
            int n7 = (int)Math.round(20.0 * Math.log10(this.AlacUtils[n6]));
            int n8 = n6 > 512 ? -35 : 40;
            int n9 = this.IAudioInputStream;
            if (n9 < 5) {
                n9 = 5;
            }
            if (n9 > 249) {
                n9 = 249;
            }
            int n10 = n9 < 125 ? 35 : -25;
            d = (Math.pow(10.0, (double)(250 - n9) / (this.AlacDecoderUtils * this.BufferedAlacReader)) - 1.0) / (this.AlacContextModel * this.AlacMetaDataModel);
            int n11 = (int)Math.round(20.0 * Math.log10(d));
            graphics2D.setColor(Color.decode("#0000FF"));
            graphics2D.drawLine(this.IAudioFileCodec, 5, this.IAudioFileCodec, 249);
            graphics2D.setColor(Color.decode("#EEEEEE"));
            string = String.valueOf(d11);
            String string2 = String.valueOf(n7);
            String string3 = String.valueOf(n11);
            graphics2D.drawString(string + " kHz", this.IAudioFileCodec - fontMetrics.stringWidth(string) + n8, n9 - 15 + n10);
            graphics2D.setColor(Color.decode("#FFBF00"));
            graphics2D.drawString(string2 + " dB", this.IAudioFileCodec - fontMetrics.stringWidth(string2) + n8, n9 + n10);
            graphics2D.setColor(Color.decode("#00EEEE"));
            if (this.MetaInfomationCopy == 1) {
                graphics2D.drawLine(this.IAudioFileCodec - 10, n9, this.IAudioFileCodec + 10, n9);
                graphics2D.drawString(string3 + " dB", this.IAudioFileCodec - fontMetrics.stringWidth(string3) + n8, n9 + 15 + n10);
            }
            if (this.DstDecoder && this.ExtendedDSTSoundDataChunk && n6 > 0) {
                d11 = (double)n6 * (2.0 / (double)this.AiffMetaDataModel);
                string = String.valueOf((double)Math.round(d11 * 100000.0) / 100.0);
                string2 = String.valueOf((double)Math.round(1.0 / d11 * 0.1) / 100.0);
                graphics2D.setColor(Color.decode("#FF0000"));
                graphics2D.drawString(string + " ms", 935 - fontMetrics.stringWidth(string), 267);
                graphics2D.drawString(string2 + " kHz", 1000 - fontMetrics.stringWidth(string2), 267);
            }
        }
    }

    @Override
    public void run() {
        this.repaint();
    }

    @Override
    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA2) {
        this.BufferedAacReader = yGjBevanihqaxYKnUNtrNeA.FFT().DSP();
        this.AiffMetaDataModel = yGjBevanihqaxYKnUNtrNeA.DSP();
        if (this.BufferedAacReader != this.AiffAudioCodec) {
            this.AiffAudioCodec = this.BufferedAacReader;
            if (!this.AdditionalMetadataValue) {
                this.DSP(-100);
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
        if (zjoyaRSokkGYDwXHPKTBIiX == PlayerState.DSP) {
            this.FFT();
        }
    }

    @Override
    public boolean DSP(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2, Object object) {
        switch (lXCOGwZXVelwEKHMMPwpSAO2) {
            case AudioFileExtension: {
                this.FFT();
                this.responseView();
            }
        }
        return true;
    }

    @Override
    public void AdditionalMetadataValue() {
        this.EmptySimpleByteBuffer = false;
        this.FFT();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int n;
        if (mouseEvent.getSource() == this && mouseEvent.getClickCount() == 2) {
            this.DSP(-100);
            this.DSP(-100, 0);
        }
        if (mouseEvent.getSource() == this) {
            n = mouseEvent.getX();
            int n2 = mouseEvent.getY();
            if (n > 36 && n < 250 && n2 > 250) {
                this.DstDecoder = !this.DstDecoder;
                this.EmptySimpleByteBuffer = true;
                this.DSP(this.DstDecoder);
                this.FFT();
                this.repaint();
            }
            if (n > 330 && n < 400 && n2 > 250) {
                this.FIRDecimationFilter = !this.FIRDecimationFilter;
                this.repaint();
            }
            if (n > 430 && n < 505 && n2 > 250) {
                if (!this.IChannelInputReader) {
                    this.IChannelInputReader = true;
                    this.FlacAudioCodec = new Color(0, 80, 0);
                    this.FlacMetaDataModel = new Color(0, 80, 0);
                    this.ChannelData = new Color(0, 0, 80);
                } else {
                    this.IChannelInputReader = false;
                    this.FlacAudioCodec = new Color(0, 180, 0);
                    this.FlacMetaDataModel = new Color(0, 255, 0);
                    this.ChannelData = new Color(0, 0, 255);
                }
                this.repaint();
            }
            if (n > 625 && n < 710 && n2 > 250) {
                this.AdditionalMetadataValue = !this.AdditionalMetadataValue;
                this.DSP(-100);
                this.FFT();
                this.repaint();
            }
            if (n > 830 && n < 895 && n2 > 250) {
                this.ExtendedDSTSoundDataChunk = !this.ExtendedDSTSoundDataChunk;
                this.FFT();
                this.repaint();
            }
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
        if (mouseEvent.getSource() == this || mouseEvent.getSource().getClass() == WaterfallControl.class) {
            this.FFT(0, 0);
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == this) {
            this.DSP(mouseEvent.getY());
            if (this.DstDecoder) {
                this.DSP(mouseEvent.getX(), mouseEvent.getY());
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        if (mouseEvent.getSource().getClass() == WaterfallControl.class) {
            this.MetaInfomationCopy = 0;
            this.FFT(mouseEvent.getX(), 250);
        }
        if (mouseEvent.getSource() == this) {
            this.MetaInfomationCopy = 1;
            this.FFT(mouseEvent.getX(), mouseEvent.getY());
        }
    }

    public SpectrumControl DSP() {
        if (this.DstDecoder || this.EmptySimpleByteBuffer) {
            return null;
        }
        return this;
    }

    @Override
    public /* synthetic */ Object AudioFileExtension() {
        return this.DSP();
    }
}

