/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import com.xivero.hraa.gui.frame.jitter.JitterAnalyser;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Future;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import sdfgjkljljoftrytrszgijpokjprs.AudioLineUtil;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.ISystemSettings;
import sdfgjkljljoftrytrszgijpokjprs.SystemController;
import sdfgjkljljoftrytrszgijpokjprs.UHRSpectrumAnalyzer;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;
import sdfgjkljljoftrytrszgijpokjprs.Settings;
import sdfgjkljljoftrytrszgijpokjprs.THDAnalyser;
import sdfgjkljljoftrytrszgijpokjprs.IMixerSettings;
import sdfgjkljljoftrytrszgijpokjprs.IUserActionListener;
import sdfgjkljljoftrytrszgijpokjprs.IWritableAudioPlugin;
import sdfgjkljljoftrytrszgijpokjprs.NetworkController;
import sdfgjkljljoftrytrszgijpokjprs.TurntableRPM;
import sdfgjkljljoftrytrszgijpokjprs.MixerSettingSet;
import sdfgjkljljoftrytrszgijpokjprs.IConnectionsListener;
import sdfgjkljljoftrytrszgijpokjprs.IAudioRouteChangeListner;

public class StreamSettingsDialog
extends JDialog
implements ISystemSettings,
IMixerSettings,
IConnectionsListener {
    private static final long serialVersionUID = 1L;
    private static final int FFT = (int)Math.pow(2.0, 16.0) - 1;
    private static final int[] responseView = new int[]{44100, 48000, 88200, 96000, 176400, 192000, 352800, 384000};
    private static final int[] AdditionalMetadataValue = new int[]{2};
    private static final BitsPerSample[] AudioFileExtension = new BitsPerSample[]{BitsPerSample.responseView, BitsPerSample.AdditionalMetadataValue};
    private static final Dictionary<Integer, JLabel> IAudioFileCodec;
    private static final Dictionary<Integer, JLabel> IAudioInputStream;
    private static final Dimension IAudioMetaInformation;
    public static final MixerSettingSet DSP;
    private final ResourceBundle IBaseAudioCodec = ResourceBundle.getBundle("com/xivero/hraa/gui/frame/settings/Bundle");
    private final NetworkController MetaInfomationCopy;
    private final JitterAnalyser AacAudioCodec;
    private final THDAnalyser AacMetaDataModel;
    private final UHRSpectrumAnalyzer BufferedAacReader;
    private final TurntableRPM AiffAudioCodec;
    private boolean AiffMetaDataModel = false;
    private IUserActionListener AlacAudioCodec;
    private JButton AlacMetaDataModel;
    private JButton BufferedAlacReader;
    private JButton AlacContextModel;
    private JButton AlacDecoderUtils;
    private JButton AlacFile;
    private JComboBox AlacInputStream;
    private JComboBox AlacUtils;
    private JComboBox ChunkInfo;
    private JComboBox DemuxResT;
    private JLabel DemuxUtils;
    private JLabel LeadingZeros;
    private JLabel MyStream;
    private JLabel QTMovieT;
    private JLabel SampleDuration;
    private JLabel SampleInfo;
    private JPanel StreamUtils;
    private JPanel DffChunkReaderAdapter;
    private JPanel DsdAudioCodec;
    private JPanel DsdMetaDataModel;
    private JPanel DsfChunkReaderAdapter;
    private JPanel AbstractManagedWorker;
    private JPanel ChannelDecoder;
    private JPanel ChannelSplitter;
    private JPanel DirectStreamDigitalDecoder;
    private JPanel DstDecodeUtil;
    private JPanel DstDecoder;
    private JPanel EmptySimpleByteBuffer;
    private JPanel ExtendedDSTSoundDataChunk;
    private JPanel FIRDecimationFilter;
    private JPanel IChannelInputReader;
    private JScrollPane IMetadataReader;
    private JScrollPane IRawInputReader;
    private JScrollPane IReadableChannelBuffer;
    private JScrollPane InterleavedBufferBuilder;
    private JScrollPane SimpleByteBuffer;
    private JScrollPane StreamDecoder;
    private JScrollPane ThreadState;
    private JSlider BufferedFlacReader;
    private JSlider FlacAudioCodec;
    private JTabbedPane FlacMetaDataModel;
    private JTextPane ChannelData;
    private JTextPane Constants;
    private JTextPane FLACDecoder;
    private JTextPane FixedPredictor;
    private JTextPane FrameDecodeException;
    private JTextPane FrameListener;
    private JTextPane FrameListeners;
    private JLabel LPCPredictor;
    private JButton PCMProcessor;
    private JButton PCMProcessors;
    private JButton BadHeaderException;
    private JButton Channel;
    private JLabel ChannelConstant;
    private JLabel ChannelFixed;
    private JLabel ChannelLPC;

    public StreamSettingsDialog(Frame frame, boolean bl) {
        super(frame, bl);
        this.AacAudioCodec = new JitterAnalyser(frame, bl);
        this.AacMetaDataModel = new THDAnalyser(frame, bl);
        this.AiffAudioCodec = new TurntableRPM(frame, bl);
        this.BufferedAacReader = new UHRSpectrumAnalyzer();
        SystemController.IAudioFileCodec().DSP(this.BufferedAacReader);
        this.BufferedAacReader.DSP(new IAudioRouteChangeListner(){

            @Override
            public void DSP(IWritableAudioPlugin<Future<AudioSampleModel>> lKkynTEEZVTbuSJCgDWFhnQ2) {
                SystemController.IAudioFileCodec().DSP(lKkynTEEZVTbuSJCgDWFhnQ2);
            }

            @Override
            public void FFT(IWritableAudioPlugin<Future<AudioSampleModel>> lKkynTEEZVTbuSJCgDWFhnQ2) {
                SystemController.IAudioFileCodec().FFT(lKkynTEEZVTbuSJCgDWFhnQ2);
            }
        });
        this.AacMetaDataModel();
        this.MetaInfomationCopy();
        this.IBaseAudioCodec();
        this.AacAudioCodec();
        this.IAudioFileCodec();
        this.MetaInfomationCopy = NetworkController.DSP();
        this.MetaInfomationCopy.DSP(this);
        this.MetaInfomationCopy.DSP(8989);
        this.setLocationRelativeTo(null);
    }

    @Override
    public int AdditionalMetadataValue() {
        return this.FlacAudioCodec.getValue() == 0 ? 1 : this.FlacAudioCodec.getValue();
    }

    @Override
    public double AudioFileExtension() {
        return (this.BufferedFlacReader.getValue() - 250) / 10 * -1;
    }

    private static JLabel DSP(JLabel jLabel, Dimension dimension) {
        jLabel.setPreferredSize(dimension);
        jLabel.setHorizontalAlignment(0);
        return jLabel;
    }

    private void IBaseAudioCodec() {
        List<MixerSettingSet> list = AudioLineUtil.DSP(TargetDataLine.class, responseView, AdditionalMetadataValue, AudioFileExtension, false);
        this.AlacInputStream.removeAllItems();
        for (MixerSettingSet sAVBeCYeaIWVZOjBqiIeTcH2 : list) {
            this.AlacInputStream.addItem(sAVBeCYeaIWVZOjBqiIeTcH2);
        }
        this.AlacContextModel.setEnabled(this.AlacInputStream.getItemCount() > 0);
    }

    private void MetaInfomationCopy() {
        List<MixerSettingSet> list = AudioLineUtil.DSP(SourceDataLine.class, responseView, AdditionalMetadataValue, AudioFileExtension, false);
        this.ChunkInfo.removeAllItems();
        for (MixerSettingSet sAVBeCYeaIWVZOjBqiIeTcH2 : list) {
            this.ChunkInfo.addItem(sAVBeCYeaIWVZOjBqiIeTcH2);
        }
        this.ChunkInfo.addItem(DSP);
    }

    private void AacAudioCodec() {
        String string = Settings.DSP().DSP("SelectedAudioOutpurMixer");
        if (string != null && !string.isEmpty()) {
            for (int i = 0; i < this.ChunkInfo.getItemCount(); ++i) {
                String string2 = this.DSP((MixerSettingSet)this.ChunkInfo.getItemAt(i));
                if (!string2.equals(string)) continue;
                this.ChunkInfo.setSelectedIndex(i);
                this.AiffMetaDataModel = true;
                break;
            }
        }
    }

    public void IAudioFileCodec() {
        Settings fZjZKQGQFsYZzSsbxLVpseZ2 = Settings.DSP();
        Integer n = null;
        Integer n2 = null;
        try {
            n = Integer.parseInt(fZjZKQGQFsYZzSsbxLVpseZ2.DSP("SystemAnalysingDelaySettings"));
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        try {
            n2 = Integer.parseInt(fZjZKQGQFsYZzSsbxLVpseZ2.DSP("SystemThreadPriotitySettings"));
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        this.BufferedFlacReader.setValue(n != null ? n : 250);
        this.FlacAudioCodec.setValue(n2 != null ? n2 : 8);
    }

    public void DSP(IUserActionListener iYiMprtDmuIaOXdDBuaIVGo2) {
        this.AlacAudioCodec = iYiMprtDmuIaOXdDBuaIVGo2;
    }

    private void AacMetaDataModel() {
        this.FlacMetaDataModel = new JTabbedPane();
        this.DirectStreamDigitalDecoder = new JPanel();
        this.IMetadataReader = new JScrollPane();
        this.Constants = new JTextPane();
        this.DsdAudioCodec = new JPanel();
        this.ChannelSplitter = new JPanel();
        this.MyStream = new JLabel();
        this.AlacInputStream = new JComboBox();
        this.DsfChunkReaderAdapter = new JPanel();
        this.QTMovieT = new JLabel();
        this.AlacUtils = new JComboBox();
        this.LeadingZeros = new JLabel();
        this.DemuxResT = new JComboBox();
        this.AbstractManagedWorker = new JPanel();
        this.AlacContextModel = new JButton();
        this.BufferedAlacReader = new JButton();
        this.AlacMetaDataModel = new JButton();
        this.DstDecodeUtil = new JPanel();
        this.IReadableChannelBuffer = new JScrollPane();
        this.FLACDecoder = new JTextPane();
        this.StreamUtils = new JPanel();
        this.SampleDuration = new JLabel();
        this.ChunkInfo = new JComboBox();
        this.AlacDecoderUtils = new JButton();
        this.AlacFile = new JButton();
        this.IChannelInputReader = new JPanel();
        this.Channel = new JButton();
        this.ThreadState = new JScrollPane();
        this.FrameListeners = new JTextPane();
        this.ChannelLPC = new JLabel();
        this.ChannelDecoder = new JPanel();
        this.PCMProcessor = new JButton();
        this.InterleavedBufferBuilder = new JScrollPane();
        this.ChannelData = new JTextPane();
        this.LPCPredictor = new JLabel();
        this.EmptySimpleByteBuffer = new JPanel();
        this.PCMProcessors = new JButton();
        this.SimpleByteBuffer = new JScrollPane();
        this.FrameDecodeException = new JTextPane();
        this.ChannelConstant = new JLabel();
        this.FIRDecimationFilter = new JPanel();
        this.BadHeaderException = new JButton();
        this.StreamDecoder = new JScrollPane();
        this.FrameListener = new JTextPane();
        this.ChannelFixed = new JLabel();
        this.ExtendedDSTSoundDataChunk = new JPanel();
        this.IRawInputReader = new JScrollPane();
        this.FixedPredictor = new JTextPane();
        this.DsdMetaDataModel = new JPanel();
        this.DstDecoder = new JPanel();
        this.SampleInfo = new JLabel();
        this.FlacAudioCodec = new JSlider();
        this.DffChunkReaderAdapter = new JPanel();
        this.DemuxUtils = new JLabel();
        this.BufferedFlacReader = new JSlider();
        this.BufferedFlacReader.setLabelTable(IAudioInputStream);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com/xivero/hraa/gui/frame/settings/Bundle");
        this.setTitle(resourceBundle.getString("StreamSettingsDialog.title"));
        this.setMinimumSize(new Dimension(500, 321));
        this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        this.setName("LineSettings");
        this.setResizable(false);
        this.IMetadataReader.setBorder(null);
        this.IMetadataReader.setHorizontalScrollBarPolicy(31);
        this.IMetadataReader.setVerticalScrollBarPolicy(21);
        this.Constants.setEditable(false);
        this.Constants.setBackground(this.getBackground());
        this.Constants.setBorder(null);
        this.Constants.setText(resourceBundle.getString("StreamSettingsDialog.jTextPaneLineInfo.text"));
        this.Constants.setFocusable(false);
        this.IMetadataReader.setViewportView(this.Constants);
        this.DsdAudioCodec.setLayout(new BoxLayout(this.DsdAudioCodec, 2));
        this.ChannelSplitter.setPreferredSize(new Dimension(100, 100));
        this.MyStream.setText(resourceBundle.getString("StreamSettingsDialog.jLabelLineInputDevice.text"));
        this.AlacInputStream.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                StreamSettingsDialog.this.DSP(itemEvent);
            }
        });
        GroupLayout groupLayout = new GroupLayout(this.ChannelSplitter);
        this.ChannelSplitter.setLayout(groupLayout);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(this.MyStream, -1, -1, Short.MAX_VALUE).addContainerGap()).addComponent(this.AlacInputStream, 0, -1, Short.MAX_VALUE));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, groupLayout.createSequentialGroup().addComponent(this.MyStream).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.AlacInputStream, -2, -1, -2)));
        this.DsdAudioCodec.add(this.ChannelSplitter);
        this.QTMovieT.setText(resourceBundle.getString("StreamSettingsDialog.jLabelLineSampleRate.text"));
        this.LeadingZeros.setText(resourceBundle.getString("StreamSettingsDialog.jLabelLineBitsPerSample.text"));
        GroupLayout groupLayout2 = new GroupLayout(this.DsfChunkReaderAdapter);
        this.DsfChunkReaderAdapter.setLayout(groupLayout2);
        groupLayout2.setHorizontalGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AlacUtils, 0, -1, Short.MAX_VALUE).addComponent(this.DemuxResT, 0, -1, Short.MAX_VALUE).addGroup(groupLayout2.createSequentialGroup().addGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.QTMovieT).addComponent(this.LeadingZeros)).addGap(0, 0, Short.MAX_VALUE)));
        groupLayout2.setVerticalGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout2.createSequentialGroup().addComponent(this.QTMovieT).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.AlacUtils, -2, -1, -2).addGap(18, 18, 18).addComponent(this.LeadingZeros).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.DemuxResT, -2, -1, -2)));
        this.AlacContextModel.setText(resourceBundle.getString("StreamSettingsDialog.jButtonLineStart.text"));
        this.AlacContextModel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StreamSettingsDialog.this.IAudioInputStream(actionEvent);
            }
        });
        this.BufferedAlacReader.setText(resourceBundle.getString("StreamSettingsDialog.jButtonLineRefresh.text"));
        this.BufferedAlacReader.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StreamSettingsDialog.this.IAudioFileCodec(actionEvent);
            }
        });
        this.AlacMetaDataModel.setText(resourceBundle.getString("StreamSettingsDialog.jButtonLineOkay.text"));
        this.AlacMetaDataModel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StreamSettingsDialog.this.AudioFileExtension(actionEvent);
            }
        });
        GroupLayout groupLayout3 = new GroupLayout(this.AbstractManagedWorker);
        this.AbstractManagedWorker.setLayout(groupLayout3);
        groupLayout3.setHorizontalGroup(groupLayout3.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, groupLayout3.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addComponent(this.BufferedAlacReader, -2, 100, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.AlacContextModel, -2, 100, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.AlacMetaDataModel, -2, 100, -2)));
        groupLayout3.setVerticalGroup(groupLayout3.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout3.createSequentialGroup().addContainerGap().addGroup(groupLayout3.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.AlacContextModel).addComponent(this.BufferedAlacReader).addComponent(this.AlacMetaDataModel)).addGap(0, 0, Short.MAX_VALUE)));
        GroupLayout groupLayout4 = new GroupLayout(this.DirectStreamDigitalDecoder);
        this.DirectStreamDigitalDecoder.setLayout(groupLayout4);
        groupLayout4.setHorizontalGroup(groupLayout4.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout4.createSequentialGroup().addContainerGap().addGroup(groupLayout4.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.IMetadataReader).addComponent(this.DsdAudioCodec, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE).addComponent(this.DsfChunkReaderAdapter, -1, -1, Short.MAX_VALUE).addComponent(this.AbstractManagedWorker, -1, -1, Short.MAX_VALUE)).addContainerGap()));
        groupLayout4.setVerticalGroup(groupLayout4.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout4.createSequentialGroup().addGap(11, 11, 11).addComponent(this.IMetadataReader, -2, 49, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.DsdAudioCodec, -2, 71, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.DsfChunkReaderAdapter, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.AbstractManagedWorker, -2, -1, -2).addContainerGap()));
        this.FlacMetaDataModel.addTab(resourceBundle.getString("StreamSettingsDialog.jPanelLineInSettings.TabConstraints.tabTitle"), this.DirectStreamDigitalDecoder);
        this.IReadableChannelBuffer.setBorder(null);
        this.IReadableChannelBuffer.setHorizontalScrollBarPolicy(31);
        this.IReadableChannelBuffer.setVerticalScrollBarPolicy(21);
        this.FLACDecoder.setEditable(false);
        this.FLACDecoder.setBackground(this.getBackground());
        this.FLACDecoder.setBorder(null);
        this.FLACDecoder.setText(resourceBundle.getString("StreamSettingsDialog.jTextPanePlaybackInfo.text"));
        this.FLACDecoder.setFocusable(false);
        this.IReadableChannelBuffer.setViewportView(this.FLACDecoder);
        this.SampleDuration.setText(resourceBundle.getString("StreamSettingsDialog.jLabelPlaybackOutputMixer.text"));
        GroupLayout groupLayout5 = new GroupLayout(this.StreamUtils);
        this.StreamUtils.setLayout(groupLayout5);
        groupLayout5.setHorizontalGroup(groupLayout5.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout5.createSequentialGroup().addGap(0, 0, 0).addGroup(groupLayout5.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.ChunkInfo, 0, -1, Short.MAX_VALUE).addGroup(groupLayout5.createSequentialGroup().addComponent(this.SampleDuration, -2, 449, -2).addGap(0, 0, Short.MAX_VALUE)))));
        groupLayout5.setVerticalGroup(groupLayout5.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout5.createSequentialGroup().addComponent(this.SampleDuration).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.ChunkInfo, -2, -1, -2).addContainerGap()));
        this.AlacDecoderUtils.setText(resourceBundle.getString("StreamSettingsDialog.jButtonPlaybackOkay.text"));
        this.AlacDecoderUtils.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StreamSettingsDialog.this.AdditionalMetadataValue(actionEvent);
            }
        });
        this.AlacFile.setText(resourceBundle.getString("StreamSettingsDialog.jButtonPlaybackRefresh.text"));
        this.AlacFile.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StreamSettingsDialog.this.responseView(actionEvent);
            }
        });
        GroupLayout groupLayout6 = new GroupLayout(this.DstDecodeUtil);
        this.DstDecodeUtil.setLayout(groupLayout6);
        groupLayout6.setHorizontalGroup(groupLayout6.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout6.createSequentialGroup().addGroup(groupLayout6.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout6.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.AlacFile, -2, 100, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.AlacDecoderUtils, -2, 100, -2)).addGroup(groupLayout6.createSequentialGroup().addContainerGap().addGroup(groupLayout6.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.IReadableChannelBuffer).addComponent(this.StreamUtils, -1, -1, Short.MAX_VALUE)))).addContainerGap()));
        groupLayout6.setVerticalGroup(groupLayout6.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout6.createSequentialGroup().addGap(11, 11, 11).addComponent(this.IReadableChannelBuffer, -2, 49, -2).addGap(17, 17, 17).addComponent(this.StreamUtils, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(groupLayout6.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.AlacDecoderUtils).addComponent(this.AlacFile)).addContainerGap()));
        this.FlacMetaDataModel.addTab(resourceBundle.getString("StreamSettingsDialog.jPanelPlaybackSettings.TabConstraints.tabTitle"), this.DstDecodeUtil);
        this.Channel.setText(resourceBundle.getString("StreamSettingsDialog.startUHRSpectrumAnalyserButton.text"));
        this.Channel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StreamSettingsDialog.this.IAudioMetaInformation(actionEvent);
            }
        });
        this.ThreadState.setBorder(null);
        this.ThreadState.setHorizontalScrollBarPolicy(31);
        this.ThreadState.setVerticalScrollBarPolicy(21);
        this.FrameListeners.setEditable(false);
        this.FrameListeners.setBackground(this.getBackground());
        this.FrameListeners.setBorder(null);
        this.FrameListeners.setContentType("text/html");
        this.FrameListeners.setFont(new Font("Tahoma", 0, 10));
        this.FrameListeners.setText(resourceBundle.getString("StreamSettingsDialog.jTextPaneUHRAnalyser.text"));
        this.FrameListeners.setFocusable(false);
        this.FrameListeners.addHyperlinkListener(new HyperlinkListener(){

            @Override
            public void hyperlinkUpdate(HyperlinkEvent hyperlinkEvent) {
                StreamSettingsDialog.this.responseView(hyperlinkEvent);
            }
        });
        this.ThreadState.setViewportView(this.FrameListeners);
        this.ChannelLPC.setForeground(new Color(255, 0, 0));
        this.ChannelLPC.setHorizontalAlignment(4);
        this.ChannelLPC.setText(resourceBundle.getString("StreamSettingsDialog.uhrFullVersionInfo.text"));
        GroupLayout groupLayout7 = new GroupLayout(this.IChannelInputReader);
        this.IChannelInputReader.setLayout(groupLayout7);
        groupLayout7.setHorizontalGroup(groupLayout7.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout7.createSequentialGroup().addContainerGap().addGroup(groupLayout7.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, groupLayout7.createSequentialGroup().addComponent(this.ChannelLPC, -2, 538, -2).addGap(18, 18, 18).addComponent(this.Channel, -2, 100, -2)).addComponent(this.ThreadState, -2, 0, Short.MAX_VALUE)).addContainerGap()));
        groupLayout7.setVerticalGroup(groupLayout7.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, groupLayout7.createSequentialGroup().addContainerGap().addComponent(this.ThreadState, -1, 438, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(groupLayout7.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.Channel).addComponent(this.ChannelLPC)).addContainerGap()));
        this.FlacMetaDataModel.addTab(resourceBundle.getString("StreamSettingsDialog.jPanelUHR.TabConstraints.tabTitle"), this.IChannelInputReader);
        this.PCMProcessor.setText(resourceBundle.getString("StreamSettingsDialog.startJitterAnalyserButton.text"));
        this.PCMProcessor.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StreamSettingsDialog.this.FFT(actionEvent);
            }
        });
        this.InterleavedBufferBuilder.setBorder(null);
        this.InterleavedBufferBuilder.setHorizontalScrollBarPolicy(31);
        this.InterleavedBufferBuilder.setVerticalScrollBarPolicy(21);
        this.ChannelData.setEditable(false);
        this.ChannelData.setBackground(this.getBackground());
        this.ChannelData.setBorder(null);
        this.ChannelData.setContentType("text/html");
        this.ChannelData.setFont(new Font("Tahoma", 0, 10));
        this.ChannelData.setText(resourceBundle.getString("StreamSettingsDialog.jTextPaneJitterAnalyser.text"));
        this.ChannelData.setFocusable(false);
        this.ChannelData.addHyperlinkListener(new HyperlinkListener(){

            @Override
            public void hyperlinkUpdate(HyperlinkEvent hyperlinkEvent) {
                StreamSettingsDialog.this.FFT(hyperlinkEvent);
            }
        });
        this.InterleavedBufferBuilder.setViewportView(this.ChannelData);
        this.LPCPredictor.setForeground(new Color(255, 0, 0));
        this.LPCPredictor.setHorizontalAlignment(4);
        this.LPCPredictor.setText(resourceBundle.getString("StreamSettingsDialog.jitterFullVersionInfo.text"));
        GroupLayout groupLayout8 = new GroupLayout(this.ChannelDecoder);
        this.ChannelDecoder.setLayout(groupLayout8);
        groupLayout8.setHorizontalGroup(groupLayout8.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout8.createSequentialGroup().addContainerGap().addGroup(groupLayout8.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, groupLayout8.createSequentialGroup().addComponent(this.LPCPredictor, -2, 538, -2).addGap(18, 18, 18).addComponent(this.PCMProcessor, -2, 100, -2)).addComponent(this.InterleavedBufferBuilder, -2, 0, Short.MAX_VALUE)).addContainerGap()));
        groupLayout8.setVerticalGroup(groupLayout8.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, groupLayout8.createSequentialGroup().addContainerGap().addComponent(this.InterleavedBufferBuilder, -1, 438, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(groupLayout8.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.PCMProcessor).addComponent(this.LPCPredictor)).addContainerGap()));
        this.FlacMetaDataModel.addTab(resourceBundle.getString("StreamSettingsDialog.jPanelJitter.TabConstraints.tabTitle"), this.ChannelDecoder);
        this.PCMProcessors.setText(resourceBundle.getString("StreamSettingsDialog.startThdAnalyserButton.text"));
        this.PCMProcessors.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StreamSettingsDialog.this.DSP(actionEvent);
            }
        });
        this.SimpleByteBuffer.setBorder(null);
        this.SimpleByteBuffer.setHorizontalScrollBarPolicy(31);
        this.SimpleByteBuffer.setVerticalScrollBarPolicy(21);
        this.FrameDecodeException.setEditable(false);
        this.FrameDecodeException.setBackground(this.getBackground());
        this.FrameDecodeException.setBorder(null);
        this.FrameDecodeException.setContentType("text/html");
        this.FrameDecodeException.setFont(new Font("Tahoma", 0, 10));
        this.FrameDecodeException.setText(resourceBundle.getString("StreamSettingsDialog.jTextPaneTHDAnalyser.text"));
        this.FrameDecodeException.setFocusable(false);
        this.FrameDecodeException.addHyperlinkListener(new HyperlinkListener(){

            @Override
            public void hyperlinkUpdate(HyperlinkEvent hyperlinkEvent) {
                StreamSettingsDialog.this.DSP(hyperlinkEvent);
            }
        });
        this.SimpleByteBuffer.setViewportView(this.FrameDecodeException);
        this.ChannelConstant.setForeground(new Color(255, 0, 0));
        this.ChannelConstant.setHorizontalAlignment(4);
        this.ChannelConstant.setText(resourceBundle.getString("StreamSettingsDialog.thdFullVersionInfo.text"));
        GroupLayout groupLayout9 = new GroupLayout(this.EmptySimpleByteBuffer);
        this.EmptySimpleByteBuffer.setLayout(groupLayout9);
        groupLayout9.setHorizontalGroup(groupLayout9.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout9.createSequentialGroup().addContainerGap().addGroup(groupLayout9.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, groupLayout9.createSequentialGroup().addComponent(this.ChannelConstant, -2, 538, -2).addGap(18, 18, 18).addComponent(this.PCMProcessors, -2, 100, -2)).addComponent(this.SimpleByteBuffer, -2, 0, Short.MAX_VALUE)).addContainerGap()));
        groupLayout9.setVerticalGroup(groupLayout9.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, groupLayout9.createSequentialGroup().addContainerGap().addComponent(this.SimpleByteBuffer, -1, 438, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(groupLayout9.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.PCMProcessors).addComponent(this.ChannelConstant)).addContainerGap()));
        this.FlacMetaDataModel.addTab(resourceBundle.getString("StreamSettingsDialog.jPanelTHD.TabConstraints.tabTitle"), this.EmptySimpleByteBuffer);
        this.BadHeaderException.setText(resourceBundle.getString("StreamSettingsDialog.startTurntableRpmButton.text"));
        this.BadHeaderException.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StreamSettingsDialog.this.IBaseAudioCodec(actionEvent);
            }
        });
        this.StreamDecoder.setBorder(null);
        this.StreamDecoder.setHorizontalScrollBarPolicy(31);
        this.StreamDecoder.setVerticalScrollBarPolicy(21);
        this.FrameListener.setEditable(false);
        this.FrameListener.setBackground(this.getBackground());
        this.FrameListener.setBorder(null);
        this.FrameListener.setContentType("text/html");
        this.FrameListener.setFont(new Font("Tahoma", 0, 10));
        this.FrameListener.setText(resourceBundle.getString("StreamSettingsDialog.jTextPaneTurntableRPM.text_1"));
        this.FrameListener.setFocusable(false);
        this.FrameListener.addHyperlinkListener(new HyperlinkListener(){

            @Override
            public void hyperlinkUpdate(HyperlinkEvent hyperlinkEvent) {
                StreamSettingsDialog.this.AdditionalMetadataValue(hyperlinkEvent);
            }
        });
        this.StreamDecoder.setViewportView(this.FrameListener);
        this.ChannelFixed.setForeground(new Color(255, 0, 0));
        this.ChannelFixed.setHorizontalAlignment(4);
        this.ChannelFixed.setText(resourceBundle.getString("StreamSettingsDialog.turntableRpmFullVersionInfo.text"));
        GroupLayout groupLayout10 = new GroupLayout(this.FIRDecimationFilter);
        this.FIRDecimationFilter.setLayout(groupLayout10);
        groupLayout10.setHorizontalGroup(groupLayout10.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout10.createSequentialGroup().addContainerGap().addGroup(groupLayout10.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, groupLayout10.createSequentialGroup().addComponent(this.ChannelFixed, -2, 538, -2).addGap(18, 18, 18).addComponent(this.BadHeaderException, -2, 100, -2)).addComponent(this.StreamDecoder, -2, 0, Short.MAX_VALUE)).addContainerGap()));
        groupLayout10.setVerticalGroup(groupLayout10.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, groupLayout10.createSequentialGroup().addContainerGap().addComponent(this.StreamDecoder, -1, 438, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(groupLayout10.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.BadHeaderException).addComponent(this.ChannelFixed)).addContainerGap()));
        this.FlacMetaDataModel.addTab(resourceBundle.getString("StreamSettingsDialog.jPanelTurntable.TabConstraints.tabTitle"), this.FIRDecimationFilter);
        this.IRawInputReader.setBorder(null);
        this.IRawInputReader.setHorizontalScrollBarPolicy(31);
        this.IRawInputReader.setVerticalScrollBarPolicy(21);
        this.FixedPredictor.setEditable(false);
        this.FixedPredictor.setBackground(this.getBackground());
        this.FixedPredictor.setBorder(null);
        this.FixedPredictor.setText(resourceBundle.getString("StreamSettingsDialog.jTextPaneSystemInfo.text"));
        this.FixedPredictor.setFocusable(false);
        this.IRawInputReader.setViewportView(this.FixedPredictor);
        this.DsdMetaDataModel.setLayout(new BoxLayout(this.DsdMetaDataModel, 1));
        this.DstDecoder.setPreferredSize(new Dimension(100, 100));
        this.SampleInfo.setText(resourceBundle.getString("StreamSettingsDialog.jLabelPriority.text"));
        this.FlacAudioCodec.setMajorTickSpacing(5);
        this.FlacAudioCodec.setMaximum(10);
        this.FlacAudioCodec.setMinorTickSpacing(1);
        this.FlacAudioCodec.setPaintLabels(true);
        this.FlacAudioCodec.setPaintTicks(true);
        this.FlacAudioCodec.setSnapToTicks(true);
        this.FlacAudioCodec.setValue(8);
        this.FlacAudioCodec.setLabelTable(IAudioFileCodec);
        this.FlacAudioCodec.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                StreamSettingsDialog.this.FFT(changeEvent);
            }
        });
        GroupLayout groupLayout11 = new GroupLayout(this.DstDecoder);
        this.DstDecoder.setLayout(groupLayout11);
        groupLayout11.setHorizontalGroup(groupLayout11.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout11.createSequentialGroup().addComponent(this.SampleInfo).addGap(0, 0, Short.MAX_VALUE)).addComponent(this.FlacAudioCodec, -1, 1011, Short.MAX_VALUE));
        groupLayout11.setVerticalGroup(groupLayout11.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout11.createSequentialGroup().addComponent(this.SampleInfo).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.FlacAudioCodec, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE)));
        this.DsdMetaDataModel.add(this.DstDecoder);
        this.DffChunkReaderAdapter.setPreferredSize(new Dimension(100, 100));
        this.DemuxUtils.setText(resourceBundle.getString("StreamSettingsDialog.jLabelAnalyseSpeed.text"));
        this.BufferedFlacReader.setMaximum(250);
        this.BufferedFlacReader.setMinorTickSpacing(50);
        this.BufferedFlacReader.setPaintLabels(true);
        this.BufferedFlacReader.setPaintTicks(true);
        this.BufferedFlacReader.setToolTipText(resourceBundle.getString("StreamSettingsDialog.jSliderAnalysingDelay.toolTipText"));
        this.BufferedFlacReader.setValue(250);
        this.BufferedFlacReader.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                StreamSettingsDialog.this.DSP(changeEvent);
            }
        });
        GroupLayout groupLayout12 = new GroupLayout(this.DffChunkReaderAdapter);
        this.DffChunkReaderAdapter.setLayout(groupLayout12);
        groupLayout12.setHorizontalGroup(groupLayout12.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout12.createSequentialGroup().addComponent(this.DemuxUtils).addGap(0, 940, Short.MAX_VALUE)).addComponent(this.BufferedFlacReader, -1, -1, Short.MAX_VALUE));
        groupLayout12.setVerticalGroup(groupLayout12.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout12.createSequentialGroup().addComponent(this.DemuxUtils).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.BufferedFlacReader, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE)));
        this.DsdMetaDataModel.add(this.DffChunkReaderAdapter);
        GroupLayout groupLayout13 = new GroupLayout(this.ExtendedDSTSoundDataChunk);
        this.ExtendedDSTSoundDataChunk.setLayout(groupLayout13);
        groupLayout13.setHorizontalGroup(groupLayout13.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout13.createSequentialGroup().addContainerGap().addGroup(groupLayout13.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.DsdMetaDataModel, -1, 1011, Short.MAX_VALUE).addComponent(this.IRawInputReader, -1, 1011, Short.MAX_VALUE)).addContainerGap()));
        groupLayout13.setVerticalGroup(groupLayout13.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout13.createSequentialGroup().addContainerGap().addComponent(this.IRawInputReader, -2, 49, -2).addGap(14, 14, 14).addComponent(this.DsdMetaDataModel, -2, 154, -2).addContainerGap(261, Short.MAX_VALUE)));
        this.FlacMetaDataModel.addTab(resourceBundle.getString("StreamSettingsDialog.jPanelThreadingSettings.TabConstraints.tabTitle"), this.ExtendedDSTSoundDataChunk);
        GroupLayout groupLayout14 = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(groupLayout14);
        groupLayout14.setHorizontalGroup(groupLayout14.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.FlacMetaDataModel));
        groupLayout14.setVerticalGroup(groupLayout14.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.FlacMetaDataModel));
        this.pack();
    }

    private void DSP(HyperlinkEvent hyperlinkEvent) {
        this.FFT(hyperlinkEvent);
    }

    private void DSP(ActionEvent actionEvent) {
        if (this.AlacAudioCodec != null) {
            this.AlacAudioCodec.DSP();
            this.setVisible(false);
        }
        this.AacMetaDataModel.setVisible(true);
    }

    private void FFT(HyperlinkEvent hyperlinkEvent) {
        if (hyperlinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            try {
                Desktop desktop;
                Desktop desktop2 = desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(hyperlinkEvent.getURL().toURI());
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private void FFT(ActionEvent actionEvent) {
        if (this.AlacAudioCodec != null) {
            this.AlacAudioCodec.DSP();
            this.setVisible(false);
        }
        this.AacAudioCodec.setVisible(true);
    }

    private void responseView(ActionEvent actionEvent) {
        Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {
                StreamSettingsDialog.this.ChunkInfo.setEnabled(false);
                StreamSettingsDialog.this.MetaInfomationCopy();
                StreamSettingsDialog.this.ChunkInfo.setEnabled(true);
            }
        });
        thread.start();
    }

    private void AdditionalMetadataValue(ActionEvent actionEvent) {
        if (this.AiffMetaDataModel || !this.AiffMetaDataModel && this.ChunkInfo.getSelectedIndex() != 0) {
            String string = this.DSP((MixerSettingSet)this.ChunkInfo.getSelectedItem());
            Settings.DSP().DSP("SelectedAudioOutpurMixer", string);
        }
        this.setVisible(false);
    }

    private void AudioFileExtension(ActionEvent actionEvent) {
        this.setVisible(false);
    }

    private void IAudioFileCodec(ActionEvent actionEvent) {
        Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {
                StreamSettingsDialog.this.DSP(false);
                StreamSettingsDialog.this.IBaseAudioCodec();
                StreamSettingsDialog.this.DSP(true);
            }
        });
        thread.start();
    }

    private void IAudioInputStream(ActionEvent actionEvent) {
        if (this.AlacAudioCodec != null) {
            try {
                this.AlacAudioCodec.DSP(this.IAudioInputStream(), this.IAudioMetaInformation());
                this.setVisible(false);
            }
            catch (LineUnavailableException lineUnavailableException) {
                // empty catch block
            }
        }
    }

    private void DSP(ItemEvent itemEvent) {
        MixerSettingSet sAVBeCYeaIWVZOjBqiIeTcH2 = (MixerSettingSet)this.AlacInputStream.getSelectedItem();
        MixerSettingSet sAVBeCYeaIWVZOjBqiIeTcH3 = (MixerSettingSet)this.ChunkInfo.getSelectedItem();
        if (sAVBeCYeaIWVZOjBqiIeTcH2 != null && sAVBeCYeaIWVZOjBqiIeTcH3 != null) {
            ArrayList<BitsPerSample> arrayList = new ArrayList<BitsPerSample>(sAVBeCYeaIWVZOjBqiIeTcH2.AdditionalMetadataValue());
            arrayList.retainAll(sAVBeCYeaIWVZOjBqiIeTcH3.AdditionalMetadataValue());
            ArrayList<Integer> arrayList2 = new ArrayList<Integer>(sAVBeCYeaIWVZOjBqiIeTcH2.FFT());
            arrayList2.retainAll(sAVBeCYeaIWVZOjBqiIeTcH3.FFT());
            this.AlacUtils.removeAllItems();
            this.DemuxResT.removeAllItems();
            for (Integer object : arrayList2) {
                this.AlacUtils.addItem(object);
            }
            for (BitsPerSample kFVWmcqOBgYFxPgeswapvPe : arrayList) {
                this.DemuxResT.addItem(kFVWmcqOBgYFxPgeswapvPe);
            }
        }
    }

    private void IAudioMetaInformation(ActionEvent actionEvent) {
        if (this.AlacAudioCodec != null) {
            this.setVisible(false);
        }
        this.BufferedAacReader.setVisible(true);
    }

    private void responseView(HyperlinkEvent hyperlinkEvent) {
    }

    private void IBaseAudioCodec(ActionEvent actionEvent) {
        if (this.AlacAudioCodec != null) {
            this.setVisible(false);
        }
        this.AiffAudioCodec.setVisible(true);
    }

    private void AdditionalMetadataValue(HyperlinkEvent hyperlinkEvent) {
    }

    private void DSP(ChangeEvent changeEvent) {
        JSlider jSlider = (JSlider)changeEvent.getSource();
        if (!jSlider.getValueIsAdjusting()) {
            Settings.DSP().DSP("SystemAnalysingDelaySettings", String.valueOf(jSlider.getValue()));
        }
    }

    private void FFT(ChangeEvent changeEvent) {
        JSlider jSlider = (JSlider)changeEvent.getSource();
        if (!jSlider.getValueIsAdjusting()) {
            Settings.DSP().DSP("SystemThreadPriotitySettings", String.valueOf(jSlider.getValue()));
        }
    }

    private void DSP(boolean bl) {
        this.AlacInputStream.setEnabled(bl);
        this.ChunkInfo.setEnabled(bl);
        this.DemuxResT.setEnabled(bl);
        this.AlacUtils.setEnabled(bl);
        this.AlacContextModel.setEnabled(bl && this.AlacInputStream.getItemCount() > 0);
    }

    public TargetDataLine IAudioInputStream() throws LineUnavailableException {
        MixerSettingSet sAVBeCYeaIWVZOjBqiIeTcH2 = (MixerSettingSet)this.AlacInputStream.getSelectedItem();
        AudioFormat audioFormat = sAVBeCYeaIWVZOjBqiIeTcH2.DSP((Integer)this.AlacUtils.getSelectedItem(), (BitsPerSample)((Object)this.DemuxResT.getSelectedItem()), 2);
        Mixer mixer = AudioSystem.getMixer(sAVBeCYeaIWVZOjBqiIeTcH2.DSP());
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
        return (TargetDataLine)mixer.getLine(info);
    }

    public SourceDataLine IAudioMetaInformation() throws LineUnavailableException {
        int n = (Integer)(this.AlacUtils.getSelectedItem() != null ? this.AlacUtils.getSelectedItem() : Integer.valueOf(44100));
        BitsPerSample kFVWmcqOBgYFxPgeswapvPe = this.DemuxResT.getSelectedItem() != null ? this.DemuxResT.getSelectedItem() : BitsPerSample.responseView;
        MixerSettingSet sAVBeCYeaIWVZOjBqiIeTcH2 = (MixerSettingSet)this.ChunkInfo.getSelectedItem();
        AudioFormat audioFormat = sAVBeCYeaIWVZOjBqiIeTcH2.DSP(n, kFVWmcqOBgYFxPgeswapvPe, 2);
        Mixer mixer = AudioSystem.getMixer(sAVBeCYeaIWVZOjBqiIeTcH2.DSP());
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        return (SourceDataLine)mixer.getLine(info);
    }

    @Override
    public Mixer.Info FFT() throws LineUnavailableException {
        MixerSettingSet sAVBeCYeaIWVZOjBqiIeTcH2 = (MixerSettingSet)this.ChunkInfo.getSelectedItem();
        return sAVBeCYeaIWVZOjBqiIeTcH2.DSP();
    }

    @Override
    public boolean responseView() {
        return this.ChunkInfo.getSelectedItem().equals(DSP);
    }

    @Override
    public void setVisible(boolean bl) {
        if (this.isVisible() && !bl) {
            this.MetaInfomationCopy.responseView();
        }
        this.PCMProcessor.setEnabled(true);
        this.PCMProcessors.setEnabled(true);
        this.Channel.setEnabled(true);
        this.BadHeaderException.setEnabled(true);
        this.LPCPredictor.setVisible(false);
        this.ChannelConstant.setVisible(false);
        this.ChannelLPC.setVisible(false);
        this.ChannelFixed.setVisible(false);
        super.setVisible(bl);
    }

    private String DSP(MixerSettingSet sAVBeCYeaIWVZOjBqiIeTcH2) {
        if (sAVBeCYeaIWVZOjBqiIeTcH2.toString().equals(DSP.toString())) {
            return sAVBeCYeaIWVZOjBqiIeTcH2.toString();
        }
        Mixer.Info info = sAVBeCYeaIWVZOjBqiIeTcH2.DSP();
        return info.getName() + ";" + info.getVendor() + ";" + info.getVersion();
    }

    @Override
    public void DSP() {
    }

    static {
        IAudioMetaInformation = new Dimension(75, 13);
        DSP = new MixerSettingSet(null, AudioFormat.Encoding.PCM_SIGNED, false);
        for (BitsPerSample kFVWmcqOBgYFxPgeswapvPe : AudioFileExtension) {
            DSP.DSP(kFVWmcqOBgYFxPgeswapvPe);
        }
        for (int n : responseView) {
            DSP.DSP(n);
        }
        for (int n : AdditionalMetadataValue) {
            DSP.FFT(n);
        }
        IAudioFileCodec = new Hashtable<Integer, JLabel>(0);
        IAudioFileCodec.put(10, StreamSettingsDialog.DSP(new JLabel("Maximum"), IAudioMetaInformation));
        IAudioFileCodec.put(5, StreamSettingsDialog.DSP(new JLabel("Normal"), IAudioMetaInformation));
        IAudioFileCodec.put(0, new JLabel("Minimum"));
        IAudioInputStream = new Hashtable<Integer, JLabel>(0);
        IAudioInputStream.put(0, StreamSettingsDialog.DSP(new JLabel("Slow"), IAudioMetaInformation));
        IAudioInputStream.put(250, StreamSettingsDialog.DSP(new JLabel("Fast"), IAudioMetaInformation));
    }
}

