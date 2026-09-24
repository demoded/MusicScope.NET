/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import sdfgjkljljoftrytrszgijpokjprs.AudioInput;
import sdfgjkljljoftrytrszgijpokjprs.ISystemSettings;
import sdfgjkljljoftrytrszgijpokjprs.StreamSettingsDialog;
import sdfgjkljljoftrytrszgijpokjprs.AboutBox;
import sdfgjkljljoftrytrszgijpokjprs.IMixerSettings;
import sdfgjkljljoftrytrszgijpokjprs.IUserActionListener;
import sdfgjkljljoftrytrszgijpokjprs.LineInStream;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerControl;

public class SettingsPanel
extends JPanel {
    private static final long serialVersionUID = 1L;
    private AboutBox DSP;
    private StreamSettingsDialog FFT;
    private IPlayerControl responseView;
    private JButton AdditionalMetadataValue;
    private JButton AudioFileExtension;
    private JPanel IAudioFileCodec;

    public SettingsPanel() {
        this.responseView();
    }

    public SettingsPanel(Frame frame) {
        this.responseView();
        this.DSP = new AboutBox(frame, true);
        this.FFT = new StreamSettingsDialog(frame, true);
    }

    public IMixerSettings DSP() {
        return this.FFT;
    }

    public ISystemSettings FFT() {
        return this.FFT;
    }

    private void responseView() {
        this.IAudioFileCodec = new JPanel();
        this.AudioFileExtension = new JButton();
        this.AdditionalMetadataValue = new JButton();
        this.setBackground(new Color(0, 0, 0));
        this.setForeground(new Color(255, 255, 255));
        this.IAudioFileCodec.setBackground(this.getBackground());
        this.IAudioFileCodec.setForeground(this.getForeground());
        this.IAudioFileCodec.setLayout(new GridLayout(1, 0));
        this.AudioFileExtension.setBackground(new Color(0, 0, 0));
        this.AudioFileExtension.setForeground(new Color(255, 255, 255));
        this.AudioFileExtension.setIcon(new ImageIcon(this.getClass().getResource("/com/xivero/hraa/icons/settings-16.png")));
        this.AudioFileExtension.setBorder(null);
        this.AudioFileExtension.setFocusable(false);
        this.AudioFileExtension.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SettingsPanel.this.FFT(actionEvent);
            }
        });
        this.IAudioFileCodec.add(this.AudioFileExtension);
        this.AdditionalMetadataValue.setBackground(new Color(0, 0, 0));
        this.AdditionalMetadataValue.setForeground(new Color(255, 255, 255));
        this.AdditionalMetadataValue.setIcon(new ImageIcon(this.getClass().getResource("/com/xivero/hraa/icons/about-16.png")));
        this.AdditionalMetadataValue.setBorder(null);
        this.AdditionalMetadataValue.setFocusable(false);
        this.AdditionalMetadataValue.setHorizontalTextPosition(4);
        this.AdditionalMetadataValue.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SettingsPanel.this.DSP(actionEvent);
            }
        });
        this.IAudioFileCodec.add(this.AdditionalMetadataValue);
        GroupLayout groupLayout = new GroupLayout(this);
        this.setLayout(groupLayout);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.IAudioFileCodec, -1, 100, Short.MAX_VALUE));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.IAudioFileCodec, -1, 27, Short.MAX_VALUE));
    }

    private void DSP(ActionEvent actionEvent) {
        this.DSP.setVisible(true);
    }

    private void FFT(ActionEvent actionEvent) {
        this.FFT.DSP(new IUserActionListener(){

            @Override
            public void DSP() {
                SettingsPanel.this.responseView.FFT();
            }

            @Override
            public void DSP(TargetDataLine targetDataLine, SourceDataLine sourceDataLine) {
                SettingsPanel.this.responseView.DSP(new LineInStream(new AudioInput(targetDataLine)));
                SettingsPanel.this.responseView.DSP(sourceDataLine);
            }
        });
        this.FFT.setVisible(true);
    }

    public void DSP(IPlayerControl tSwCDjQKHwQVvbvetpZIATg2) {
        if (tSwCDjQKHwQVvbvetpZIATg2 != null) {
            this.responseView = tSwCDjQKHwQVvbvetpZIATg2;
        }
    }
}

