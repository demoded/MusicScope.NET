/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Future;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import sdfgjkljljoftrytrszgijpokjprs.IWritableAudioOutput;
import sdfgjkljljoftrytrszgijpokjprs.Display;
import sdfgjkljljoftrytrszgijpokjprs.IMetaInformationListener;
import sdfgjkljljoftrytrszgijpokjprs.PackageSplitterBuffer;
import sdfgjkljljoftrytrszgijpokjprs.AudioInput;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.FFTConvolver;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;
import sdfgjkljljoftrytrszgijpokjprs.IWritableAudioPlugin;
import sdfgjkljljoftrytrszgijpokjprs.IAudioRouteChangeListner;

public class UHRSpectrumAnalyzer
extends JFrame
implements ActionListener,
ItemListener,
ChangeListener,
IMetaInformationListener {
    private static final long serialVersionUID = 1L;
    private IAudioRouteChangeListner DSP;
    private IWritableAudioPlugin<Future<AudioSampleModel>> FFT;
    private int responseView = 2048;
    private int AdditionalMetadataValue = 48000;
    private int AudioFileExtension = 24000;
    private double IAudioFileCodec = (double)this.AdditionalMetadataValue / 4.0;
    private double IAudioInputStream = (double)this.AdditionalMetadataValue / 2.0;
    private boolean IAudioMetaInformation = true;
    private boolean IBaseAudioCodec = true;
    private int MetaInfomationCopy = 3;
    private int AacAudioCodec = 1;
    private boolean AacMetaDataModel = false;
    private boolean BufferedAacReader = false;
    private boolean AiffAudioCodec = false;
    private double AiffMetaDataModel = 1.0;
    private final JButton AlacAudioCodec = new JButton("Start");
    private final JComboBox AlacMetaDataModel = new JComboBox();
    private final JComboBox BufferedAlacReader = new JComboBox();
    private final JComboBox AlacContextModel = new JComboBox();
    private final JComboBox AlacDecoderUtils = new JComboBox();
    private final JLabel AlacFile = new JLabel("Resolution: - Hz/Bin");
    private final JCheckBox AlacInputStream = new JCheckBox("Band-Pass Filter", false);
    private final JCheckBox AlacUtils = new JCheckBox("Frequency Translation", false);
    private final JCheckBox ChunkInfo = new JCheckBox("Spectrum", true);
    private final JCheckBox DemuxResT = new JCheckBox("Spectrogram", true);
    private final JCheckBox DemuxUtils = new JCheckBox("Averaged Spectrum", false);
    private final JSlider LeadingZeros = new JSlider(0, 0, 60, 0);
    private final JLabel MyStream = new JLabel("Gain [dB]: ");
    private final Display QTMovieT = new Display();
    private final AudioInput SampleDuration = new AudioInput();
    private IWritableAudioOutput SampleInfo;
    private final FFTConvolver StreamUtils = new FFTConvolver(8192);
    private final FFTConvolver DffChunkReaderAdapter = new FFTConvolver(8192);

    public UHRSpectrumAnalyzer() {
        this.FFT();
        this.AdditionalMetadataValue();
    }

    private void FFT() {
        ArrayList<Image> arrayList = new ArrayList<Image>(0);
        for (int i = 16; i <= 128; i *= 2) {
            arrayList.add(new ImageIcon(com.xivero.hraa.gui.frame.DSP.class.getResource("icon" + i + ".png")).getImage());
        }
        this.setIconImages(arrayList);
        this.QTMovieT.DSP(this);
        this.QTMovieT.DSP(this.responseView);
        this.QTMovieT.FFT(this.AdditionalMetadataValue);
    }

    public void DSP(boolean bl) {
        if (bl) {
            this.MyStream.setForeground(Color.red);
        } else {
            this.MyStream.setForeground(Color.black);
        }
    }

    @Override
    public void setVisible(boolean bl) {
        super.setVisible(bl);
    }

    public void DSP(int n) {
        if (this.AdditionalMetadataValue != n) {
            this.AdditionalMetadataValue = n;
            this.IAudioFileCodec = (double)n / 4.0;
            this.IAudioInputStream = (double)n / 2.0;
            this.StreamUtils.DSP(n);
            this.DffChunkReaderAdapter.DSP(n);
            this.DSP(this.IAudioFileCodec, this.IAudioInputStream);
        }
        this.AlacFile.setText(String.format(Locale.ENGLISH, "Resolution: %2.2f Hz/Bin", (double)n / 2.0 / ((double)this.responseView / 2.0)));
        this.IAudioFileCodec();
        this.QTMovieT.FFT(n);
        if (this.SampleDuration != null) {
            this.SampleDuration.FFT(n);
        }
        if (this.BufferedAlacReader.getSelectedItem() != null) {
            this.AudioFileExtension = Integer.valueOf(this.BufferedAlacReader.getSelectedItem().toString());
            this.QTMovieT.DSP(this.AudioFileExtension, this.IAudioMetaInformation, this.IBaseAudioCodec, this.MetaInfomationCopy);
        }
    }

    public IWritableAudioPlugin<Future<AudioSampleModel>> DSP() {
        return new PackageSplitterBuffer(this.SampleDuration, 2048);
    }

    public void DSP(IAudioRouteChangeListner xckstOXaLUUCHmGDBldnQSE2) {
        this.DSP = xckstOXaLUUCHmGDBldnQSE2;
    }

    public void DSP(double d, double d2) {
        this.IAudioFileCodec = d;
        this.IAudioInputStream = d2;
        this.StreamUtils.DSP(d);
        this.DffChunkReaderAdapter.DSP(d);
        this.StreamUtils.FFT(d2);
        this.DffChunkReaderAdapter.FFT(d2);
    }

    private Component responseView() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, 1));
        jPanel.setBackground(Color.black);
        JPanel jPanel2 = new JPanel();
        JPanel jPanel3 = new JPanel();
        JPanel jPanel4 = new JPanel();
        jPanel4.setBackground(Color.black);
        jPanel.add(jPanel2);
        jPanel.add(jPanel3);
        jPanel.add(jPanel4);
        JLabel jLabel = new JLabel("Spectrum Size:");
        JLabel jLabel2 = new JLabel("Display Bandwidth:");
        JLabel jLabel3 = new JLabel("Hz");
        JLabel jLabel4 = new JLabel("Speed:");
        jPanel2.add(this.AlacAudioCodec);
        this.AlacAudioCodec.addActionListener(this);
        jPanel2.add(Box.createHorizontalStrut(20));
        jPanel2.add(jLabel);
        jPanel2.add(this.AlacMetaDataModel);
        this.AlacMetaDataModel.addActionListener(this);
        this.AlacFile.setPreferredSize(new Dimension(160, 30));
        jPanel2.add(this.AlacFile);
        jPanel2.add(Box.createHorizontalStrut(15));
        jPanel2.add(this.AlacInputStream);
        this.AlacInputStream.addItemListener(this);
        jPanel2.add(Box.createHorizontalStrut(15));
        jPanel2.add(this.AlacUtils);
        this.AlacUtils.addItemListener(this);
        jPanel2.add(Box.createHorizontalStrut(20));
        jPanel2.add(this.MyStream);
        this.LeadingZeros.setName("Monitor Gain");
        this.LeadingZeros.setMajorTickSpacing(30);
        this.LeadingZeros.setMinorTickSpacing(10);
        this.LeadingZeros.setPaintTicks(true);
        this.LeadingZeros.setPaintLabels(true);
        this.LeadingZeros.setPaintTrack(true);
        this.LeadingZeros.addChangeListener(this);
        jPanel2.add(this.LeadingZeros);
        jPanel3.add(jLabel2);
        jPanel3.add(this.BufferedAlacReader);
        this.BufferedAlacReader.addActionListener(this);
        jPanel3.add(jLabel3);
        jPanel3.add(Box.createHorizontalStrut(15));
        jPanel3.add(this.ChunkInfo);
        this.ChunkInfo.addItemListener(this);
        jPanel3.add(this.DemuxUtils);
        this.DemuxUtils.addItemListener(this);
        jPanel3.add(this.DemuxResT);
        this.DemuxResT.addItemListener(this);
        jPanel3.add(Box.createHorizontalStrut(15));
        jPanel3.add(this.AlacDecoderUtils);
        this.AlacDecoderUtils.addActionListener(this);
        jPanel3.add(Box.createHorizontalStrut(15));
        jPanel3.add(jLabel4);
        jPanel3.add(this.AlacContextModel);
        this.AlacContextModel.addActionListener(this);
        this.QTMovieT.setPreferredSize(new Dimension(1110, 565));
        this.QTMovieT.setBackground(Color.black);
        jPanel4.add(this.QTMovieT);
        this.AudioFileExtension();
        this.IAudioFileCodec();
        this.IAudioInputStream();
        this.IAudioMetaInformation();
        return jPanel;
    }

    private void AdditionalMetadataValue() {
        Dimension dimension = new Dimension(1150, 750);
        this.setTitle("Ultra High Resolution Spectrum Analyzer");
        this.setDefaultCloseOperation(1);
        this.setPreferredSize(dimension);
        this.setMinimumSize(dimension);
        this.setMaximumSize(dimension);
        this.setSize(dimension);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.addComponentListener(new DSP());
        Component component = this.responseView();
        this.getContentPane().add(component, "Center");
    }

    private void AudioFileExtension() {
        int[] nArray = new int[]{11, 12, 13, 14, 15, 16};
        for (int i = 0; i < nArray.length; ++i) {
            this.AlacMetaDataModel.addItem((int)Math.pow(2.0, nArray[i]));
        }
        this.AlacMetaDataModel.setSelectedIndex(0);
        this.responseView = Integer.valueOf(this.AlacMetaDataModel.getSelectedItem().toString());
        this.AlacFile.setText(String.format(Locale.ENGLISH, "Resolution: %.2f Hz/Bin", (double)this.AdditionalMetadataValue / 2.0 / ((double)this.responseView / 2.0)));
    }

    private void IAudioFileCodec() {
        int n = 1024;
        this.BufferedAlacReader.removeAllItems();
        while (2 * n <= this.responseView) {
            this.BufferedAlacReader.addItem(String.format(Locale.ENGLISH, "%2.0f", (double)this.AdditionalMetadataValue / 2.0 / (double)(n / 1024)));
            n *= 2;
        }
    }

    private void IAudioInputStream() {
        this.AlacContextModel.removeAllItems();
        this.AlacContextModel.addItem("x1");
        this.AlacContextModel.addItem("x2");
        this.AlacContextModel.addItem("x4");
        this.AlacContextModel.setSelectedIndex(0);
    }

    private void IAudioMetaInformation() {
        this.AlacDecoderUtils.removeAllItems();
        this.AlacDecoderUtils.addItem("Color");
        this.AlacDecoderUtils.addItem("Green");
        this.AlacDecoderUtils.addItem("B&W");
        this.AlacDecoderUtils.addItem("Inv. B&W");
        this.AlacDecoderUtils.addItem("BRY");
        this.AlacDecoderUtils.setSelectedIndex(0);
    }

    private void IBaseAudioCodec() {
        this.AlacMetaDataModel.setEnabled(true);
    }

    private void MetaInfomationCopy() {
        this.AlacMetaDataModel.setEnabled(false);
    }

    private void AacAudioCodec() {
        this.MetaInfomationCopy();
        this.QTMovieT.DSP(true);
        this.QTMovieT.DSP(this.responseView);
        this.QTMovieT.FFT(this.AdditionalMetadataValue);
        this.QTMovieT.DSP(this.AudioFileExtension, this.IAudioMetaInformation, this.IBaseAudioCodec, this.MetaInfomationCopy);
        this.QTMovieT.FFT(this.BufferedAacReader);
        this.StreamUtils.DSP(this.AdditionalMetadataValue);
        this.DffChunkReaderAdapter.DSP(this.AdditionalMetadataValue);
        this.StreamUtils.DSP(this.IAudioFileCodec);
        this.DffChunkReaderAdapter.DSP(this.IAudioFileCodec);
        this.StreamUtils.FFT(this.IAudioInputStream);
        this.DffChunkReaderAdapter.FFT(this.IAudioInputStream);
        this.StreamUtils.DSP(this.AacMetaDataModel);
        this.DffChunkReaderAdapter.DSP(this.AacMetaDataModel);
        this.SampleDuration.DSP(this.SampleInfo);
        this.SampleDuration.DSP(this.responseView);
        this.SampleDuration.DSP(this);
        this.SampleDuration.FFT(this.AdditionalMetadataValue);
        this.SampleDuration.DSP(this.StreamUtils, this.DffChunkReaderAdapter);
        this.SampleDuration.DSP(this.QTMovieT);
        this.SampleDuration.FFT(this.BufferedAacReader);
        this.SampleDuration.responseView(this.AacAudioCodec);
        if (this.DSP != null) {
            this.FFT = this.DSP();
            this.DSP.DSP(this.FFT);
        }
    }

    private void AacMetaDataModel() {
        this.QTMovieT.DSP(false);
        if (this.DSP != null) {
            this.DSP.FFT(this.FFT);
        }
        this.IBaseAudioCodec();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String string;
        if (actionEvent.getSource() == this.AlacAudioCodec) {
            if ("Start".equals(this.AlacAudioCodec.getText())) {
                this.AacAudioCodec();
                this.AlacAudioCodec.setText("Stop");
            } else {
                this.AacMetaDataModel();
                this.AlacAudioCodec.setText("Start");
            }
        }
        if (actionEvent.getSource() == this.AlacMetaDataModel) {
            this.responseView = Integer.valueOf(this.AlacMetaDataModel.getSelectedItem().toString());
            this.AlacFile.setText(String.format(Locale.ENGLISH, "Resolution: %2.2f Hz/Bin", (double)this.AdditionalMetadataValue / 2.0 / ((double)this.responseView / 2.0)));
            this.IAudioFileCodec();
        }
        if (actionEvent.getSource() == this.BufferedAlacReader && this.BufferedAlacReader.getSelectedItem() != null) {
            this.AudioFileExtension = Integer.valueOf(this.BufferedAlacReader.getSelectedItem().toString());
            this.QTMovieT.DSP(this.AudioFileExtension, this.IAudioMetaInformation, this.IBaseAudioCodec, this.MetaInfomationCopy);
        }
        if (actionEvent.getSource() == this.AlacContextModel) {
            string = this.AlacContextModel.getSelectedItem().toString();
            if (string == "x1") {
                this.AacAudioCodec = 1;
            } else if (string == "x2") {
                this.AacAudioCodec = 2;
            } else if (string == "x4") {
                this.AacAudioCodec = 4;
            }
            if (this.SampleDuration != null) {
                this.SampleDuration.responseView(this.AacAudioCodec);
            }
        }
        if (actionEvent.getSource() == this.AlacDecoderUtils) {
            string = this.AlacDecoderUtils.getSelectedItem().toString();
            if (string == "Color") {
                this.MetaInfomationCopy = 3;
            } else if (string == "Green") {
                this.MetaInfomationCopy = 1;
            } else if (string == "B&W") {
                this.MetaInfomationCopy = 2;
            } else if (string == "BRY") {
                this.MetaInfomationCopy = 4;
            } else if (string == "Inv. B&W") {
                this.MetaInfomationCopy = 5;
            }
            this.QTMovieT.DSP(this.AudioFileExtension, this.IAudioMetaInformation, this.IBaseAudioCodec, this.MetaInfomationCopy);
        }
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        if (changeEvent.getSource() == this.LeadingZeros) {
            this.AiffMetaDataModel = this.LeadingZeros.getValue();
            this.SampleDuration.DSP(this.AiffMetaDataModel);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        if (itemEvent.getSource() == this.AlacInputStream) {
            if (itemEvent.getStateChange() == 1) {
                this.BufferedAacReader = true;
                this.AlacUtils.setEnabled(true);
            } else {
                this.BufferedAacReader = false;
                this.AlacUtils.setEnabled(false);
                this.AlacUtils.setSelected(false);
                this.AacMetaDataModel = false;
                this.StreamUtils.DSP(this.AacMetaDataModel);
                this.DffChunkReaderAdapter.DSP(this.AacMetaDataModel);
            }
            this.QTMovieT.FFT(this.BufferedAacReader);
            if (this.SampleDuration != null) {
                this.SampleDuration.FFT(this.BufferedAacReader);
            }
        }
        if (itemEvent.getSource() == this.AlacUtils) {
            this.AacMetaDataModel = itemEvent.getStateChange() == 1;
            this.QTMovieT.responseView(this.AacMetaDataModel);
            this.StreamUtils.DSP(this.AacMetaDataModel);
            this.DffChunkReaderAdapter.DSP(this.AacMetaDataModel);
        }
        if (itemEvent.getSource() == this.ChunkInfo) {
            if (itemEvent.getStateChange() == 1) {
                this.IAudioMetaInformation = true;
                this.QTMovieT.DSP(this.AudioFileExtension, this.IAudioMetaInformation, this.IBaseAudioCodec, this.MetaInfomationCopy);
            } else {
                this.IAudioMetaInformation = false;
                this.QTMovieT.DSP(this.AudioFileExtension, this.IAudioMetaInformation, this.IBaseAudioCodec, this.MetaInfomationCopy);
            }
        }
        if (itemEvent.getSource() == this.DemuxResT) {
            if (itemEvent.getStateChange() == 1) {
                this.IBaseAudioCodec = true;
                this.QTMovieT.DSP(this.AudioFileExtension, this.IAudioMetaInformation, this.IBaseAudioCodec, this.MetaInfomationCopy);
            } else {
                this.IBaseAudioCodec = false;
                this.QTMovieT.DSP(this.AudioFileExtension, this.IAudioMetaInformation, this.IBaseAudioCodec, this.MetaInfomationCopy);
            }
        }
        if (itemEvent.getSource() == this.DemuxUtils) {
            this.AiffAudioCodec = itemEvent.getStateChange() == 1;
            this.QTMovieT.AdditionalMetadataValue(this.AiffAudioCodec);
        }
    }

    @Override
    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA2) {
        this.DSP(yGjBevanihqaxYKnUNtrNeA.DSP());
    }

    private class DSP
    implements ComponentListener {
        private DSP() {
        }

        @Override
        public void componentResized(ComponentEvent componentEvent) {
        }

        @Override
        public void componentMoved(ComponentEvent componentEvent) {
        }

        @Override
        public void componentShown(ComponentEvent componentEvent) {
            UHRSpectrumAnalyzer.this.AacMetaDataModel();
        }

        @Override
        public void componentHidden(ComponentEvent componentEvent) {
            UHRSpectrumAnalyzer.this.AacMetaDataModel();
            if (UHRSpectrumAnalyzer.this.DSP != null) {
                UHRSpectrumAnalyzer.this.DSP.FFT(UHRSpectrumAnalyzer.this.FFT);
            }
        }
    }
}

