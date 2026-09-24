/*
 * Decompiled with CFR 0.152.
 */
package com.xivero.hraa.gui.frame.jitter;

import com.xivero.hraa.gui.frame.jitter.responseView;
import com.xivero.hraa.gui.frame.jitter.DSP;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class JitterAnalyser
extends JDialog
implements ActionListener {
    static final Mixer.Info[] DSP = AudioSystem.getMixerInfo();
    static final int[] FFT = new int[DSP.length + 1];
    static final int[] responseView = new int[DSP.length + 1];
    static final JComboBox AdditionalMetadataValue = new JComboBox();
    static final JComboBox AudioFileExtension = new JComboBox();
    private static final JButton AacMetaDataModel = new JButton("Start");
    static JRadioButton IAudioFileCodec = new JRadioButton("Narrow", true);
    static JRadioButton IAudioInputStream = new JRadioButton("Medium");
    static JRadioButton IAudioMetaInformation = new JRadioButton("Wide");
    static JRadioButton IBaseAudioCodec = new JRadioButton("Ultra Wide");
    public static final responseView MetaInfomationCopy = new responseView();
    public static DSP AacAudioCodec = new DSP();

    public JitterAnalyser(Frame frame, boolean bl) {
        super(frame, bl);
        JitterAnalyser.AdditionalMetadataValue();
        this.responseView();
    }

    private Component FFT() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, 3));
        jPanel.setBackground(Color.black);
        JPanel jPanel2 = new JPanel();
        JPanel jPanel3 = new JPanel();
        jPanel3.setBackground(Color.white);
        jPanel.add(jPanel2);
        jPanel.add(jPanel3);
        JLabel jLabel = new JLabel("Input:");
        AdditionalMetadataValue.setPreferredSize(new Dimension(250, 25));
        AudioFileExtension.setPreferredSize(new Dimension(200, 25));
        jPanel2.add(jLabel);
        jPanel2.add(AdditionalMetadataValue);
        MetaInfomationCopy.setPreferredSize(new Dimension(1100, 750));
        MetaInfomationCopy.setBackground(Color.white);
        jPanel3.add(MetaInfomationCopy);
        jPanel2.add(AacMetaDataModel);
        AacMetaDataModel.addActionListener(this);
        JLabel jLabel2 = new JLabel("     Bandwidth:");
        jPanel2.add(jLabel2);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(IAudioFileCodec);
        buttonGroup.add(IAudioInputStream);
        buttonGroup.add(IAudioMetaInformation);
        buttonGroup.add(IBaseAudioCodec);
        jPanel2.add(IAudioFileCodec);
        jPanel2.add(IAudioInputStream);
        jPanel2.add(IAudioMetaInformation);
        jPanel2.add(IBaseAudioCodec);
        IAudioFileCodec.addActionListener(this);
        IAudioInputStream.addActionListener(this);
        IAudioMetaInformation.addActionListener(this);
        IBaseAudioCodec.addActionListener(this);
        return jPanel;
    }

    private void responseView() {
        Dimension dimension = new Dimension(1150, 850);
        this.setTitle("MusicScope - Jitter Analyzer");
        this.setLocation(10, 10);
        this.setPreferredSize(dimension);
        this.setMinimumSize(dimension);
        this.setMaximumSize(dimension);
        this.setSize(dimension);
        this.setDefaultCloseOperation(1);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                JitterAnalyser.this.IAudioFileCodec();
            }
        });
        Component component = this.FFT();
        this.getContentPane().add(component, "Center");
    }

    private static void AdditionalMetadataValue() {
        int n = 0;
        boolean bl = false;
        Line.Info info = new Line.Info(TargetDataLine.class);
        for (int i = 0; i < DSP.length; ++i) {
            Mixer mixer = AudioSystem.getMixer(DSP[i]);
            if (!mixer.isLineSupported(info)) continue;
            AdditionalMetadataValue.addItem(DSP[i].getName());
            JitterAnalyser.FFT[n] = i;
            ++n;
        }
    }

    private void AudioFileExtension() {
        if (!AacAudioCodec.DSP()) {
            AacAudioCodec.DSP(true);
        }
        try {
            Thread.sleep(500L);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        MetaInfomationCopy.DSP();
        AacAudioCodec.DSP(false);
        new Thread(AacAudioCodec).start();
        AacMetaDataModel.setText("Stop");
    }

    private void IAudioFileCodec() {
        AacAudioCodec.DSP(true);
        AacMetaDataModel.setText("Start");
    }

    public static void DSP() {
        AacMetaDataModel.setText("Start");
    }

    public static void DSP(double[] dArray) {
        if (MetaInfomationCopy != null) {
            MetaInfomationCopy.DSP(dArray);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == AacMetaDataModel) {
            if ("Start".equals(AacMetaDataModel.getText())) {
                this.AudioFileExtension();
            } else {
                this.IAudioFileCodec();
            }
        }
        if (actionEvent.getSource() == IAudioFileCodec) {
            MetaInfomationCopy.DSP(1);
        }
        if (actionEvent.getSource() == IAudioInputStream) {
            MetaInfomationCopy.DSP(2);
        }
        if (actionEvent.getSource() == IAudioMetaInformation) {
            MetaInfomationCopy.DSP(4);
        }
        if (actionEvent.getSource() == IBaseAudioCodec) {
            MetaInfomationCopy.DSP(8);
        }
    }

    public static void main(String[] stringArray) {
        JitterAnalyser.AdditionalMetadataValue();
    }

    @Override
    public void setVisible(boolean bl) {
        if (bl) {
            this.setLocationRelativeTo(null);
        }
        super.setVisible(bl);
    }
}

