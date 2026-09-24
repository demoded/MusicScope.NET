/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import sdfgjkljljoftrytrszgijpokjprs.AudioLevelDisplay;
import sdfgjkljljoftrytrszgijpokjprs.AudioInput;
import sdfgjkljljoftrytrszgijpokjprs.AudioSetup;
import sdfgjkljljoftrytrszgijpokjprs.Display;

public class TurntableRPM
extends JDialog
implements ActionListener {
    private final Mixer.Info[] DSP = AudioSystem.getMixerInfo();
    private final int[] FFT = new int[this.DSP.length + 1];
    private TargetDataLine responseView;
    private final int AdditionalMetadataValue = 120;
    private double AudioFileExtension = 33.333333333;
    private final double[] IAudioFileCodec = new double[121];
    private double IAudioInputStream = 1000.0;
    private int IAudioMetaInformation = 1;
    private final AudioSetup IBaseAudioCodec = new AudioSetup();
    private AudioInput MetaInfomationCopy;
    private final JLabel AacAudioCodec = new JLabel("Input:");
    private final JComboBox AacMetaDataModel = new JComboBox();
    private final AudioLevelDisplay BufferedAacReader = new AudioLevelDisplay();
    private final JComboBox AiffAudioCodec = new JComboBox();
    private final JLabel AiffMetaDataModel = new JLabel("Frequency:");
    private final JTextField AlacAudioCodec = new JTextField();
    private final JLabel AlacMetaDataModel = new JLabel("Hz");
    private final JButton BufferedAlacReader = new JButton("Start");
    private final JLabel AlacContextModel = new JLabel("Deviation: - %");
    private final JLabel AlacDecoderUtils = new JLabel("RPM: -");
    private final JLabel AlacFile = new JLabel("Frequency: - Hz");
    private final Display AlacInputStream = new Display();

    public TurntableRPM(Frame frame, boolean bl) {
        super(frame, bl);
        this.FFT();
    }

    public void DSP(double d) {
        double d2 = (d - this.IAudioInputStream) / this.IAudioInputStream * 100.0;
        if (d2 > 1.0) {
            d2 = 1.01;
        } else if (d2 < -1.0) {
            d2 = -1.01;
        }
        this.IAudioFileCodec[this.IAudioMetaInformation] = d2;
        double d3 = 0.0;
        double d4 = 0.0;
        for (int i = 1; i <= this.IAudioMetaInformation; ++i) {
            if (Math.abs(this.IAudioFileCodec[i]) <= 1.0) {
                d3 += this.IAudioFileCodec[i];
                continue;
            }
            d4 += 1.0;
        }
        double d5 = this.AudioFileExtension + this.AudioFileExtension * (d3 /= (double)this.IAudioMetaInformation - d4) / 100.0;
        double d6 = this.IAudioInputStream + this.IAudioInputStream * d3 / 100.0;
        d3 = (double)Math.round(d3 * 100.0) / 100.0;
        d5 = (double)Math.round(d5 * 1000.0) / 1000.0;
        d6 = (double)Math.round(d6 * 10.0) / 10.0;
        this.AlacContextModel.setText("Deviation: " + String.valueOf(d3) + " %");
        this.AlacDecoderUtils.setText("RPM: " + String.valueOf(d5));
        this.AlacFile.setText("Frequency: " + String.valueOf(d6) + " Hz");
        ++this.IAudioMetaInformation;
        if (this.IAudioMetaInformation > 120) {
            this.MetaInfomationCopy();
        }
        this.AlacInputStream.DSP(this.IAudioFileCodec);
    }

    private Component DSP() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, 1));
        jPanel.setBackground(Color.white);
        JPanel jPanel2 = new JPanel();
        JPanel jPanel3 = new JPanel();
        JPanel jPanel4 = new JPanel();
        jPanel4.setBackground(Color.white);
        jPanel.add(jPanel2);
        jPanel.add(jPanel3);
        jPanel.add(jPanel4);
        jPanel2.add(this.AacAudioCodec);
        this.AacMetaDataModel.setPreferredSize(new Dimension(200, 25));
        jPanel2.add(this.AacMetaDataModel);
        this.BufferedAacReader.setPreferredSize(new Dimension(102, 20));
        jPanel2.add(this.BufferedAacReader);
        jPanel2.add(Box.createHorizontalStrut(5));
        jPanel2.add(this.AiffAudioCodec);
        this.AiffAudioCodec.addActionListener(this);
        jPanel2.add(Box.createHorizontalStrut(5));
        jPanel2.add(this.AiffMetaDataModel);
        this.AlacAudioCodec.setHorizontalAlignment(4);
        this.AlacAudioCodec.setColumns(4);
        this.AlacAudioCodec.setText("1000");
        jPanel2.add(this.AlacAudioCodec);
        this.AlacAudioCodec.addActionListener(this);
        jPanel2.add(this.AlacMetaDataModel);
        jPanel2.add(Box.createHorizontalStrut(5));
        jPanel2.add(this.BufferedAlacReader);
        this.BufferedAlacReader.addActionListener(this);
        this.AlacContextModel.setPreferredSize(new Dimension(200, 25));
        jPanel3.add(this.AlacContextModel);
        this.AlacDecoderUtils.setPreferredSize(new Dimension(200, 25));
        jPanel3.add(this.AlacDecoderUtils);
        this.AlacFile.setPreferredSize(new Dimension(200, 25));
        jPanel3.add(this.AlacFile);
        this.AlacInputStream.setPreferredSize(new Dimension(720, 455));
        jPanel4.add(this.AlacInputStream);
        this.responseView();
        this.AdditionalMetadataValue();
        return jPanel;
    }

    private void FFT() {
        Dimension dimension = new Dimension(800, 580);
        this.setLocation(100, 100);
        this.setPreferredSize(dimension);
        this.setMinimumSize(dimension);
        this.setMaximumSize(dimension);
        this.setSize(dimension);
        this.setDefaultCloseOperation(1);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Turntable RPM Measurement");
        Component component = this.DSP();
        this.getContentPane().add(component, "Center");
    }

    private void responseView() {
        int n = 0;
        boolean bl = false;
        Line.Info info = new Line.Info(TargetDataLine.class);
        for (int i = 0; i < this.DSP.length; ++i) {
            Mixer mixer = AudioSystem.getMixer(this.DSP[i]);
            if (!mixer.isLineSupported(info)) continue;
            this.AacMetaDataModel.addItem(this.DSP[i].getName());
            this.FFT[n] = i;
            ++n;
        }
    }

    private void AdditionalMetadataValue() {
        this.AiffAudioCodec.removeAllItems();
        this.AiffAudioCodec.addItem("33 1/3 RPM");
        this.AiffAudioCodec.addItem("45 RPM");
    }

    private void AudioFileExtension() {
        for (int i = 0; i < 120; ++i) {
            this.IAudioFileCodec[i] = 0.0;
        }
        this.IAudioMetaInformation = 1;
    }

    private void IAudioFileCodec() {
        String string = this.AlacAudioCodec.getText();
        string = string.trim();
        try {
            this.IAudioInputStream = Integer.parseInt(string);
        }
        catch (Exception exception) {
            this.IAudioInputStream = 1000.0;
            this.AlacAudioCodec.setText("1000");
        }
        if (this.IAudioInputStream < 400.0) {
            this.IAudioInputStream = 400.0;
            this.AlacAudioCodec.setText("400");
        } else if (this.IAudioInputStream > 5000.0) {
            this.IAudioInputStream = 5000.0;
            this.AlacAudioCodec.setText("5000");
        }
    }

    private void IAudioInputStream() {
        this.BufferedAlacReader.setText("Start");
        this.AacMetaDataModel.setEnabled(true);
        this.AiffAudioCodec.setEnabled(true);
        this.AlacAudioCodec.setEnabled(true);
    }

    private void IAudioMetaInformation() {
        this.BufferedAlacReader.setText("Stop");
        this.AacMetaDataModel.setEnabled(false);
        this.AiffAudioCodec.setEnabled(false);
        this.AlacAudioCodec.setEnabled(false);
    }

    private void IBaseAudioCodec() {
        this.IAudioMetaInformation();
        int n = 48000;
        int n2 = 16;
        this.responseView = this.IBaseAudioCodec.DSP(n, n2, this.AacMetaDataModel, this.FFT);
        this.AudioFileExtension();
        this.IAudioFileCodec();
        this.AlacInputStream.DSP(this.IAudioFileCodec);
        this.AlacInputStream.DSP(this.AudioFileExtension);
        this.MetaInfomationCopy = new AudioInput(n2, this.responseView);
        this.MetaInfomationCopy.DSP(this.BufferedAacReader);
        this.MetaInfomationCopy.DSP(this);
        new Thread(this.MetaInfomationCopy).start();
    }

    private void MetaInfomationCopy() {
        this.MetaInfomationCopy.DSP(true);
        try {
            Thread.sleep(500L);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        this.IBaseAudioCodec.DSP(this.responseView);
        this.IAudioInputStream();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.BufferedAlacReader) {
            if ("Start".equals(this.BufferedAlacReader.getText())) {
                this.IBaseAudioCodec();
            } else {
                this.MetaInfomationCopy();
            }
        }
        if (actionEvent.getSource() == this.AiffAudioCodec) {
            String string = this.AiffAudioCodec.getSelectedItem().toString();
            if ("33 1/3 RPM".equals(string)) {
                this.AudioFileExtension = 33.333333333333;
            } else if ("45 RPM".equals(string)) {
                this.AudioFileExtension = 45.0;
            }
            this.AlacInputStream.DSP(this.AudioFileExtension);
        }
        if (actionEvent.getSource() == this.AlacAudioCodec) {
            this.IAudioFileCodec();
        }
    }
}

