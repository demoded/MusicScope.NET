/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.OverlayLayout;
import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerEventListener;
import sdfgjkljljoftrytrszgijpokjprs.IMetaInformationListener;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;
import sdfgjkljljoftrytrszgijpokjprs.ComposedModel;
import sdfgjkljljoftrytrszgijpokjprs.IComputationListener;
import sdfgjkljljoftrytrszgijpokjprs.ILevelMeterControlMidSideSwitch;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;
import sdfgjkljljoftrytrszgijpokjprs.ITrackLoadedListener;
import sdfgjkljljoftrytrszgijpokjprs.IReportSaveCallback;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerControl;
import sdfgjkljljoftrytrszgijpokjprs.IAnalyzerStartListener;

public class TimeControl
extends JPanel
implements IPlayerStateListener,
IPlayerEventListener,
IMetaInformationListener,
IComputationListener<ComposedModel>,
ILevelMeterControlMidSideSwitch,
ITrackLoadedListener,
IAnalyzerStartListener {
    private static final long serialVersionUID = 1L;
    private IReportSaveCallback DSP;
    private final DateFormat FFT = new SimpleDateFormat("HH:mm:ss");
    private final double responseView = 0.05;
    private double AdditionalMetadataValue = -1.0;
    private long AudioFileExtension;
    private long IAudioFileCodec;
    private long IAudioInputStream;
    private int IAudioMetaInformation;
    private int IBaseAudioCodec;
    private int MetaInfomationCopy;
    private int AacAudioCodec;
    private int AacMetaDataModel;
    private File BufferedAacReader;
    private IPlayerControl AiffAudioCodec;
    private BitsPerSample AiffMetaDataModel;
    private AudioExtension AlacAudioCodec;
    private int AlacMetaDataModel;
    private JLabel BufferedAlacReader;
    private JLabel AlacContextModel;
    private JLabel AlacDecoderUtils;
    private JLabel AlacFile;
    private JLabel AlacInputStream;
    private JPanel AlacUtils;
    private JPanel ChunkInfo;
    private JPanel DemuxResT;

    public TimeControl() {
        this.responseView();
    }

    private String DSP(long l) {
        int n = (int)((double)l / 3600.0);
        int n2 = (int)((double)l / 60.0 - (double)n * 60.0);
        int n3 = (int)((double)l - (double)n2 * 60.0 - (double)n * 3600.0);
        return String.format("%d:%02d:%02d", n, n2, n3);
    }

    private void DSP() {
        this.ChunkInfo.setVisible(false);
        this.DemuxResT.setVisible(!this.FFT());
        this.AlacUtils.setVisible(this.FFT());
        if (this.AiffMetaDataModel != null) {
            this.IAudioMetaInformation = (int)((double)this.AacAudioCodec * 0.05);
            this.IAudioMetaInformation = this.AlacMetaDataModel * this.AiffMetaDataModel.FFT() * this.IAudioMetaInformation;
            this.IBaseAudioCodec = 0;
            this.AudioFileExtension = (long)((double)(this.IAudioInputStream / (long)this.IAudioMetaInformation) * 0.05);
            this.IAudioFileCodec = 0L;
            this.AlacDecoderUtils.setText(this.DSP(0L));
            this.AlacContextModel.setText(this.DSP(0L));
            this.BufferedAlacReader.setText(this.DSP(this.AudioFileExtension));
        }
    }

    public void DSP(IReportSaveCallback sLuwRITuDanyEuFWGUJWQqQ2) {
        this.DSP = sLuwRITuDanyEuFWGUJWQqQ2;
    }

    public void DSP(IPlayerControl tSwCDjQKHwQVvbvetpZIATg2) {
        this.AiffAudioCodec = tSwCDjQKHwQVvbvetpZIATg2;
    }

    private boolean FFT() {
        return this.AlacAudioCodec == AudioExtension.MetaInfomationCopy || this.AlacAudioCodec == AudioExtension.AacAudioCodec;
    }

    private void responseView() {
        this.DemuxResT = new JPanel();
        this.AlacDecoderUtils = new JLabel();
        this.AlacInputStream = new JLabel();
        this.BufferedAlacReader = new JLabel();
        this.AlacUtils = new JPanel();
        this.AlacContextModel = new JLabel();
        this.ChunkInfo = new JPanel();
        this.AlacFile = new JLabel();
        this.setBackground(new Color(0, 0, 0));
        this.setLayout(new OverlayLayout(this));
        this.DemuxResT.setBackground(new Color(0, 0, 0));
        this.AlacDecoderUtils.setBackground(this.getBackground());
        this.AlacDecoderUtils.setForeground(new Color(255, 255, 255));
        this.AlacDecoderUtils.setHorizontalAlignment(4);
        this.AlacDecoderUtils.setText("0:00:00");
        this.AlacInputStream.setBackground(this.getBackground());
        this.AlacInputStream.setForeground(new Color(255, 255, 255));
        this.AlacInputStream.setHorizontalAlignment(0);
        this.AlacInputStream.setText("/");
        this.AlacInputStream.setToolTipText("");
        this.BufferedAlacReader.setBackground(this.getBackground());
        this.BufferedAlacReader.setForeground(new Color(255, 255, 255));
        this.BufferedAlacReader.setHorizontalAlignment(2);
        this.BufferedAlacReader.setText("0:00:00");
        GroupLayout groupLayout = new GroupLayout(this.DemuxResT);
        this.DemuxResT.setLayout(groupLayout);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(this.AlacDecoderUtils, -1, 47, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.AlacInputStream, -2, 14, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.BufferedAlacReader, -1, 47, Short.MAX_VALUE).addContainerGap()));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AlacDecoderUtils, -1, 38, Short.MAX_VALUE).addComponent(this.AlacInputStream, -1, -1, Short.MAX_VALUE).addComponent(this.BufferedAlacReader, -1, -1, Short.MAX_VALUE));
        this.add(this.DemuxResT);
        this.AlacUtils.setBackground(new Color(0, 0, 0));
        this.AlacContextModel.setBackground(this.getBackground());
        this.AlacContextModel.setForeground(new Color(255, 255, 255));
        this.AlacContextModel.setHorizontalAlignment(0);
        this.AlacContextModel.setText("0:00:00");
        GroupLayout groupLayout2 = new GroupLayout(this.AlacUtils);
        this.AlacUtils.setLayout(groupLayout2);
        groupLayout2.setHorizontalGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 140, Short.MAX_VALUE).addGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout2.createSequentialGroup().addContainerGap().addComponent(this.AlacContextModel, -1, 120, Short.MAX_VALUE).addContainerGap())));
        groupLayout2.setVerticalGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 38, Short.MAX_VALUE).addGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AlacContextModel, -1, 38, Short.MAX_VALUE)));
        this.add(this.AlacUtils);
        this.ChunkInfo.setBackground(new Color(0, 0, 0));
        this.ChunkInfo.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                TimeControl.this.DSP(mouseEvent);
            }
        });
        this.AlacFile.setFont(new Font("Tahoma", 1, 12));
        this.AlacFile.setForeground(new Color(0, 144, 0));
        this.AlacFile.setHorizontalAlignment(0);
        this.AlacFile.setText("Report");
        GroupLayout groupLayout3 = new GroupLayout(this.ChunkInfo);
        this.ChunkInfo.setLayout(groupLayout3);
        groupLayout3.setHorizontalGroup(groupLayout3.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout3.createSequentialGroup().addContainerGap().addComponent(this.AlacFile, -1, 120, Short.MAX_VALUE).addContainerGap()));
        groupLayout3.setVerticalGroup(groupLayout3.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AlacFile, -1, 38, Short.MAX_VALUE));
        this.add(this.ChunkInfo);
    }

    private void DSP(MouseEvent mouseEvent) {
        if (this.DSP != null) {
            this.DSP.DSP(this.BufferedAacReader);
        }
    }

    @Override
    public void DSP(ComposedModel dbWUuIVxtlDbLbZVylyCBng2, ComputationController.DSP rHAjVyBgPhqkQKsOvJMPMYn2) {
        if (rHAjVyBgPhqkQKsOvJMPMYn2 == ComputationController.DSP.AudioFileExtension) {
            this.IBaseAudioCodec += this.IAudioMetaInformation;
            this.MetaInfomationCopy += this.IAudioMetaInformation;
            this.IAudioFileCodec = (long)((double)(this.IBaseAudioCodec / this.IAudioMetaInformation) * 0.05);
            if ((double)this.IAudioFileCodec != this.AdditionalMetadataValue) {
                this.AdditionalMetadataValue = this.IAudioFileCodec;
                this.AlacDecoderUtils.setText(this.DSP(this.IAudioFileCodec));
                this.AlacContextModel.setText(this.DSP(this.IAudioFileCodec));
            }
        }
    }

    @Override
    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA2) {
        this.IAudioInputStream = yGjBevanihqaxYKnUNtrNeA.IAudioInputStream();
        this.AacAudioCodec = yGjBevanihqaxYKnUNtrNeA.DSP();
        this.AacMetaDataModel = yGjBevanihqaxYKnUNtrNeA.FFT().DSP();
        this.AiffMetaDataModel = yGjBevanihqaxYKnUNtrNeA.FFT();
        this.AlacMetaDataModel = yGjBevanihqaxYKnUNtrNeA.AdditionalMetadataValue();
        this.AlacAudioCodec = yGjBevanihqaxYKnUNtrNeA.MetaInfomationCopy();
    }

    @Override
    public void AdditionalMetadataValue() {
        this.DSP();
        this.MetaInfomationCopy = 0;
    }

    @Override
    public void DSP(String string) {
        this.BufferedAacReader = new File(string);
        this.MetaInfomationCopy = 0;
        this.DSP();
        this.repaint();
    }

    @Override
    public void DSP(PlayerState zjoyaRSokkGYDwXHPKTBIiX) {
        switch (zjoyaRSokkGYDwXHPKTBIiX) {
            case DSP: {
                this.IAudioInputStream = 0L;
                this.AlacDecoderUtils.setText(this.DSP(0L));
                this.BufferedAlacReader.setText(this.DSP(0L));
            }
        }
    }

    @Override
    public boolean DSP(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2, Object object) {
        switch (lXCOGwZXVelwEKHMMPwpSAO2) {
            case IAudioFileCodec: {
                if (this.FFT()) break;
                this.ChunkInfo.setVisible(true);
                this.DemuxResT.setVisible(false);
                this.AlacUtils.setVisible(false);
                break;
            }
            case AudioFileExtension: {
                this.DSP();
            }
        }
        return true;
    }

    @Override
    public void FFT(boolean bl) {
        this.DSP();
    }
}

