/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.Files
 */
package sdfgjkljljoftrytrszgijpokjprs;

import com.google.common.io.Files;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ResourceBundle;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import sdfgjkljljoftrytrszgijpokjprs.UserAction;
import sdfgjkljljoftrytrszgijpokjprs.IUserActionCallback;

public class ReportSaver
extends JDialog {
    private static final long serialVersionUID = 1L;
    private static final JFrame DSP = new JFrame();
    private static final FileDialog FFT = new FileDialog(DSP);
    private IUserActionCallback responseView;
    private JButton AdditionalMetadataValue;
    private JButton AudioFileExtension;
    private JButton IAudioFileCodec;
    private JCheckBox IAudioInputStream;
    private JCheckBox IAudioMetaInformation;
    private JLabel IBaseAudioCodec;
    private JPanel MetaInfomationCopy;
    private JPanel AacAudioCodec;
    private JPanel AacMetaDataModel;
    private JTextField BufferedAacReader;

    public ReportSaver(Frame frame, boolean bl) {
        super(frame, bl);
        this.AdditionalMetadataValue();
        this.setLocationRelativeTo(null);
    }

    public boolean DSP() {
        return this.IAudioMetaInformation.isSelected();
    }

    public boolean FFT() {
        return this.IAudioInputStream.isSelected();
    }

    public void DSP(String string) {
        this.BufferedAacReader.setText(string);
    }

    public String responseView() {
        return this.BufferedAacReader.getText();
    }

    public void DSP(IUserActionCallback qAPYmrSBwvJMqiHvTVjYfvo2) {
        this.responseView = qAPYmrSBwvJMqiHvTVjYfvo2;
    }

    private void AdditionalMetadataValue() {
        this.MetaInfomationCopy = new JPanel();
        this.AacAudioCodec = new JPanel();
        this.BufferedAacReader = new JTextField();
        this.AudioFileExtension = new JButton();
        this.IBaseAudioCodec = new JLabel();
        this.AacMetaDataModel = new JPanel();
        this.IAudioMetaInformation = new JCheckBox();
        this.IAudioInputStream = new JCheckBox();
        this.IAudioFileCodec = new JButton();
        this.AdditionalMetadataValue = new JButton();
        this.setDefaultCloseOperation(2);
        this.setResizable(false);
        this.setType(Window.Type.POPUP);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com/xivero/hraa/gui/frame/reporting/Bundle");
        this.AudioFileExtension.setText(resourceBundle.getString("ReportSaver.jButtonChooseFolder.text"));
        this.AudioFileExtension.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ReportSaver.this.DSP(actionEvent);
            }
        });
        this.IBaseAudioCodec.setText(resourceBundle.getString("ReportSaver.jLabel1.text"));
        GroupLayout groupLayout = new GroupLayout(this.AacAudioCodec);
        this.AacAudioCodec.setLayout(groupLayout);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(this.BufferedAacReader).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.AudioFileExtension, -2, 39, -2)).addGroup(groupLayout.createSequentialGroup().addComponent(this.IBaseAudioCodec, -2, 149, -2).addContainerGap(352, Short.MAX_VALUE)));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(this.IBaseAudioCodec).addGap(3, 3, 3).addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.AudioFileExtension, -2, 0, Short.MAX_VALUE).addComponent(this.BufferedAacReader)).addContainerGap(-1, Short.MAX_VALUE)));
        this.AacMetaDataModel.setLayout(new GridLayout(2, 1));
        this.IAudioMetaInformation.setSelected(true);
        this.IAudioMetaInformation.setText(resourceBundle.getString("ReportSaver.jCheckValueExport.text"));
        this.AacMetaDataModel.add(this.IAudioMetaInformation);
        this.IAudioInputStream.setSelected(true);
        this.IAudioInputStream.setText(resourceBundle.getString("ReportSaver.jCheckImageExport.text"));
        this.AacMetaDataModel.add(this.IAudioInputStream);
        this.IAudioFileCodec.setText(resourceBundle.getString("ReportSaver.jButtonSave.text"));
        this.IAudioFileCodec.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ReportSaver.this.FFT(actionEvent);
            }
        });
        this.AdditionalMetadataValue.setText(resourceBundle.getString("ReportSaver.jButtonCancel.text"));
        this.AdditionalMetadataValue.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ReportSaver.this.responseView(actionEvent);
            }
        });
        GroupLayout groupLayout2 = new GroupLayout(this.MetaInfomationCopy);
        this.MetaInfomationCopy.setLayout(groupLayout2);
        groupLayout2.setHorizontalGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AacAudioCodec, -1, -1, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.TRAILING, groupLayout2.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addComponent(this.AdditionalMetadataValue, -2, 120, -2).addGap(18, 18, 18).addComponent(this.IAudioFileCodec, -2, 120, -2)).addComponent(this.AacMetaDataModel, -1, -1, Short.MAX_VALUE));
        groupLayout2.setVerticalGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout2.createSequentialGroup().addGap(14, 14, 14).addComponent(this.AacAudioCodec, -2, 54, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.AacMetaDataModel, -1, 67, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.IAudioFileCodec).addComponent(this.AdditionalMetadataValue)).addGap(20, 20, 20)));
        GroupLayout groupLayout3 = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(groupLayout3);
        groupLayout3.setHorizontalGroup(groupLayout3.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout3.createSequentialGroup().addGap(19, 19, 19).addComponent(this.MetaInfomationCopy, -1, -1, Short.MAX_VALUE).addGap(19, 19, 19)));
        groupLayout3.setVerticalGroup(groupLayout3.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.MetaInfomationCopy, -1, -1, Short.MAX_VALUE));
        this.pack();
    }

    private void DSP(ActionEvent actionEvent) {
        String string = Files.getFileExtension((String)this.responseView());
        String string2 = Files.getNameWithoutExtension((String)this.responseView());
        String string3 = new File(this.responseView()).getParent();
        FFT.setDirectory(string3);
        FFT.setFile(string2 + "." + string);
        FFT.setVisible(true);
        File[] fileArray = FFT.getFiles();
        if (fileArray.length == 1) {
            this.DSP(fileArray[0].getAbsolutePath());
        }
    }

    private void FFT(ActionEvent actionEvent) {
        if (this.responseView != null) {
            this.responseView.DSP(UserAction.DSP);
        }
    }

    private void responseView(ActionEvent actionEvent) {
        if (this.responseView != null) {
            this.responseView.DSP(UserAction.FFT);
        }
    }

    static {
        FFT.setMultipleMode(false);
        FFT.setMode(1);
    }
}

