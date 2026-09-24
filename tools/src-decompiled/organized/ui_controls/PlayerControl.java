/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.Files
 */
package sdfgjkljljoftrytrszgijpokjprs;

import com.google.common.io.Files;
import com.xivero.hraa.icons.Icons;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.DragAndDropListener;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.IMetaInformationListener;
import sdfgjkljljoftrytrszgijpokjprs.SystemStateMachine;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.OperatingSystem;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.BatchController;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerControl;

public class PlayerControl
extends JPanel
implements IPlayerStateListener,
IMetaInformationListener {
    public static final String[] DSP = new String[]{"wav", "dff", "dsf", "flac", "m4a", "aif", "aiff", "mp3"};
    private static final long serialVersionUID = 1L;
    private final String[] FFT = new String[]{"wav"};
    private final String[] responseView = new String[]{"dff", "dsf"};
    private final String[] AdditionalMetadataValue = new String[]{"flac"};
    private final String[] AudioFileExtension = new String[]{"m4a"};
    private final String[] IAudioFileCodec = new String[]{"mp3"};
    private final String[] IAudioInputStream = new String[]{"aif", "aiff"};
    private final SystemStateMachine IAudioMetaInformation = SystemStateMachine.DSP();
    private final JFrame IBaseAudioCodec = new JFrame();
    private final FileDialog MetaInfomationCopy;
    private IPlayerControl AacAudioCodec;
    private DragAndDropListener AacMetaDataModel;
    private AudioExtension BufferedAacReader;
    private final ResourceBundle AiffAudioCodec = ResourceBundle.getBundle("com/xivero/hraa/gui/control/player/Strings");
    private JButton AiffMetaDataModel;
    private JButton AlacAudioCodec;
    private JButton AlacMetaDataModel;
    private JButton BufferedAlacReader;

    public PlayerControl() {
        this.DSP();
        this.MetaInfomationCopy = new FileDialog(this.IBaseAudioCodec);
        this.MetaInfomationCopy.setMultipleMode(true);
        this.MetaInfomationCopy.setMode(0);
        if (OperatingSystem.FFT()) {
            String string = "";
            for (String string2 : DSP) {
                string = string + "*." + string2 + ";";
            }
            this.MetaInfomationCopy.setFile(string);
        }
        this.MetaInfomationCopy.setFilenameFilter(new FilenameFilter(){

            @Override
            public boolean accept(File file, String string) {
                return Arrays.asList(DSP).contains(Files.getFileExtension((String)string));
            }
        });
        this.DSP(SystemStateMachine.DSP().FFT());
    }

    public void DSP(IPlayerControl tSwCDjQKHwQVvbvetpZIATg2) {
        this.AacAudioCodec = tSwCDjQKHwQVvbvetpZIATg2;
        this.AacMetaDataModel = new DragAndDropListener(tSwCDjQKHwQVvbvetpZIATg2);
    }

    private void DSP() {
        this.AlacAudioCodec = new JButton();
        this.BufferedAlacReader = new JButton();
        this.AlacMetaDataModel = new JButton();
        this.AiffMetaDataModel = new JButton();
        this.setBackground(new Color(0, 0, 0));
        this.setLayout(new GridLayout(1, 0));
        this.AlacAudioCodec.setBackground(new Color(0, 0, 0));
        this.AlacAudioCodec.setForeground(new Color(255, 255, 255));
        this.AlacAudioCodec.setIcon(new ImageIcon(this.getClass().getResource("/com/xivero/hraa/icons/folder.png")));
        this.AlacAudioCodec.setBorder(null);
        this.AlacAudioCodec.setBorderPainted(false);
        this.AlacAudioCodec.setContentAreaFilled(false);
        this.AlacAudioCodec.setFocusPainted(false);
        this.AlacAudioCodec.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PlayerControl.this.DSP(actionEvent);
            }
        });
        this.add(this.AlacAudioCodec);
        this.BufferedAlacReader.setBackground(new Color(0, 0, 0));
        this.BufferedAlacReader.setForeground(new Color(255, 255, 255));
        this.BufferedAlacReader.setIcon(new ImageIcon(this.getClass().getResource("/com/xivero/hraa/icons/stop.png")));
        this.BufferedAlacReader.setBorder(null);
        this.BufferedAlacReader.setBorderPainted(false);
        this.BufferedAlacReader.setContentAreaFilled(false);
        this.BufferedAlacReader.setFocusPainted(false);
        this.BufferedAlacReader.setMinimumSize(new Dimension(55, 22));
        this.BufferedAlacReader.setPreferredSize(new Dimension(55, 22));
        this.BufferedAlacReader.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PlayerControl.this.FFT(actionEvent);
            }
        });
        this.add(this.BufferedAlacReader);
        this.AlacMetaDataModel.setBackground(new Color(0, 0, 0));
        this.AlacMetaDataModel.setForeground(new Color(255, 255, 255));
        this.AlacMetaDataModel.setIcon(new ImageIcon(this.getClass().getResource("/com/xivero/hraa/icons/play.png")));
        this.AlacMetaDataModel.setBorder(null);
        this.AlacMetaDataModel.setBorderPainted(false);
        this.AlacMetaDataModel.setContentAreaFilled(false);
        this.AlacMetaDataModel.setFocusPainted(false);
        this.AlacMetaDataModel.setMinimumSize(new Dimension(55, 22));
        this.AlacMetaDataModel.setPreferredSize(new Dimension(55, 22));
        this.AlacMetaDataModel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PlayerControl.this.AdditionalMetadataValue(actionEvent);
            }
        });
        this.add(this.AlacMetaDataModel);
        this.AiffMetaDataModel.setBackground(new Color(0, 0, 0));
        this.AiffMetaDataModel.setForeground(new Color(255, 255, 255));
        this.AiffMetaDataModel.setIcon(new ImageIcon(this.getClass().getResource("/com/xivero/hraa/icons/research.png")));
        this.AiffMetaDataModel.setBorder(null);
        this.AiffMetaDataModel.setBorderPainted(false);
        this.AiffMetaDataModel.setContentAreaFilled(false);
        this.AiffMetaDataModel.setFocusPainted(false);
        this.AiffMetaDataModel.setPreferredSize(new Dimension(55, 22));
        this.AiffMetaDataModel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PlayerControl.this.responseView(actionEvent);
            }
        });
        this.add(this.AiffMetaDataModel);
    }

    private void DSP(ActionEvent actionEvent) {
        BatchController kfrVEsKUvFeBfAoSkvCCRmV2 = BatchController.DSP();
        if (this.AacMetaDataModel != null) {
            this.MetaInfomationCopy.setVisible(true);
            File[] fileArray = this.MetaInfomationCopy.getFiles();
            if (fileArray.length > 0) {
                if (fileArray.length > 1) {
                    for (File file : fileArray) {
                        if (!Arrays.asList(DSP).contains(Files.getFileExtension((String)file.getAbsolutePath()).toLowerCase())) continue;
                        this.AacMetaDataModel.DSP(file);
                    }
                    this.AacMetaDataModel.DSP();
                } else if (Arrays.asList(DSP).contains(Files.getFileExtension((String)this.MetaInfomationCopy.getFiles()[0].getAbsolutePath()).toLowerCase())) {
                    if (kfrVEsKUvFeBfAoSkvCCRmV2.FFT()) {
                        kfrVEsKUvFeBfAoSkvCCRmV2.DSP(this.MetaInfomationCopy.getFiles()[0].getAbsolutePath());
                        kfrVEsKUvFeBfAoSkvCCRmV2.DSP(true);
                    } else {
                        this.AacMetaDataModel.DSP(this.MetaInfomationCopy.getFiles()[0]);
                        this.AacMetaDataModel.DSP();
                    }
                }
            }
        }
    }

    private void FFT(ActionEvent actionEvent) {
        if (this.AacAudioCodec != null && this.IAudioMetaInformation.FFT(PlayerEvent.AdditionalMetadataValue)) {
            this.AacAudioCodec.responseView();
        } else if (this.AacAudioCodec != null && this.IAudioMetaInformation.FFT(PlayerEvent.responseView)) {
            this.AacAudioCodec.FFT();
        }
    }

    private void responseView(ActionEvent actionEvent) {
        if (this.AacAudioCodec != null) {
            this.AacAudioCodec.AdditionalMetadataValue();
        }
    }

    private void AdditionalMetadataValue(ActionEvent actionEvent) {
        if (this.AacAudioCodec != null) {
            this.AacAudioCodec.AudioFileExtension();
        }
    }

    @Override
    public final void DSP(PlayerState zjoyaRSokkGYDwXHPKTBIiX) {
        this.AiffMetaDataModel.setEnabled(this.IAudioMetaInformation.FFT(PlayerEvent.IAudioInputStream) && !this.FFT());
        this.AlacMetaDataModel.setEnabled(this.IAudioMetaInformation.FFT(PlayerEvent.IAudioMetaInformation));
        this.AlacAudioCodec.setEnabled(this.IAudioMetaInformation.FFT(PlayerEvent.DSP));
        this.DSP(this.IAudioMetaInformation);
    }

    private void DSP(SystemStateMachine nmkQXzdXSthcCtycxNGkyHA) {
        if (nmkQXzdXSthcCtycxNGkyHA.FFT(PlayerEvent.AdditionalMetadataValue)) {
            this.BufferedAlacReader.setIcon(new ImageIcon(Icons.class.getResource("pause.png")));
            this.BufferedAlacReader.setEnabled(true);
        } else if (nmkQXzdXSthcCtycxNGkyHA.FFT(PlayerEvent.responseView)) {
            this.BufferedAlacReader.setIcon(new ImageIcon(Icons.class.getResource("stop.png")));
            this.BufferedAlacReader.setEnabled(true);
        } else {
            this.BufferedAlacReader.setIcon(new ImageIcon(Icons.class.getResource("stop.png")));
            this.BufferedAlacReader.setEnabled(false);
        }
    }

    @Override
    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA2) {
        this.BufferedAacReader = yGjBevanihqaxYKnUNtrNeA.MetaInfomationCopy();
    }

    private boolean FFT() {
        return this.BufferedAacReader == AudioExtension.MetaInfomationCopy || this.BufferedAacReader == AudioExtension.AacAudioCodec;
    }
}

