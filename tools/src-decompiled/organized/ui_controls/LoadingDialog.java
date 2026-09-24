/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import sdfgjkljljoftrytrszgijpokjprs.ICancelable;
import sdfgjkljljoftrytrszgijpokjprs.ILoadingDialog;

public class LoadingDialog
extends JDialog
implements ILoadingDialog {
    private final Object DSP = new Object();
    private final Object FFT = new Object();
    private final Object responseView = new Object();
    private ICancelable AdditionalMetadataValue;
    private int AudioFileExtension = 0;
    private int IAudioFileCodec = 0;
    private int IAudioInputStream = 0;
    private JButton IAudioMetaInformation;
    private JProgressBar IBaseAudioCodec;
    private JLabel MetaInfomationCopy;
    private JLabel AacAudioCodec;
    private JPanel AacMetaDataModel;
    private JLabel BufferedAacReader;
    private JLabel AiffAudioCodec;

    public LoadingDialog(Frame frame, boolean bl) {
        super(frame, bl);
        this.AdditionalMetadataValue();
        this.AdditionalMetadataValue = null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void DSP(int n) {
        Object object = this.DSP;
        synchronized (object) {
            this.IAudioFileCodec += n;
        }
        this.responseView();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void FFT(int n) {
        Object object = this.FFT;
        synchronized (object) {
            this.IAudioInputStream += n;
        }
        this.responseView();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void responseView(int n) {
        Object object = this.responseView;
        synchronized (object) {
            this.AudioFileExtension += n;
            this.FFT(n);
        }
        this.responseView();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int DSP() {
        Object object = this.DSP;
        synchronized (object) {
            return this.IBaseAudioCodec.getMaximum();
        }
    }

    @Override
    public void setVisible(boolean bl) {
        this.setLocationRelativeTo(null);
        super.setVisible(bl);
        if (!bl) {
            this.FFT();
        }
    }

    @Override
    public boolean isVisible() {
        return super.isVisible();
    }

    private void responseView() {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                LoadingDialog.this.IBaseAudioCodec.setMaximum(LoadingDialog.this.IAudioFileCodec);
                LoadingDialog.this.IBaseAudioCodec.setValue(LoadingDialog.this.IAudioInputStream);
                LoadingDialog.this.AacAudioCodec.setText(String.format("%d", LoadingDialog.this.IAudioFileCodec));
                LoadingDialog.this.AiffAudioCodec.setText(String.format("%d", LoadingDialog.this.IAudioInputStream - LoadingDialog.this.AudioFileExtension));
            }
        });
    }

    @Override
    public void FFT() {
        this.AudioFileExtension = 0;
        this.IAudioFileCodec = 0;
        this.IAudioInputStream = 0;
        this.IAudioMetaInformation.setEnabled(true);
        this.responseView();
    }

    public void DSP(ICancelable zwqieKsENjbNlPHxlIFUCCz) {
        this.AdditionalMetadataValue = zwqieKsENjbNlPHxlIFUCCz;
    }

    private void AdditionalMetadataValue() {
        this.AacMetaDataModel = new JPanel();
        this.MetaInfomationCopy = new JLabel();
        this.AacAudioCodec = new JLabel();
        this.BufferedAacReader = new JLabel();
        this.AiffAudioCodec = new JLabel();
        this.IAudioMetaInformation = new JButton();
        this.IBaseAudioCodec = new JProgressBar();
        this.setDefaultCloseOperation(0);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                LoadingDialog.this.DSP(windowEvent);
            }
        });
        this.AacMetaDataModel.setLayout(new GridLayout(2, 2, 15, 5));
        this.MetaInfomationCopy.setText("Files found:");
        this.AacMetaDataModel.add(this.MetaInfomationCopy);
        this.AacAudioCodec.setText("0");
        this.AacMetaDataModel.add(this.AacAudioCodec);
        this.BufferedAacReader.setText("Files loadable:");
        this.AacMetaDataModel.add(this.BufferedAacReader);
        this.AiffAudioCodec.setText("0");
        this.AacMetaDataModel.add(this.AiffAudioCodec);
        this.IAudioMetaInformation.setText("Cancel");
        this.IAudioMetaInformation.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoadingDialog.this.DSP(actionEvent);
            }
        });
        this.IBaseAudioCodec.setMaximum(0);
        GroupLayout groupLayout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(groupLayout);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AacMetaDataModel, -1, -1, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.TRAILING, groupLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.IAudioMetaInformation, -2, 100, -2)).addComponent(this.IBaseAudioCodec, GroupLayout.Alignment.TRAILING, -1, 371, Short.MAX_VALUE)).addContainerGap()));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(this.AacMetaDataModel, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.IBaseAudioCodec, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.IAudioMetaInformation).addContainerGap()));
        this.pack();
    }

    private void DSP(ActionEvent actionEvent) {
        if (this.AdditionalMetadataValue != null) {
            JButton jButton = (JButton)actionEvent.getSource();
            jButton.setEnabled(false);
            this.AdditionalMetadataValue.FFT();
        }
    }

    private void DSP(WindowEvent windowEvent) {
        if (this.AdditionalMetadataValue != null) {
            this.IAudioMetaInformation.setEnabled(false);
            this.AdditionalMetadataValue.FFT();
        }
    }
}

