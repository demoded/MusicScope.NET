/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerEventListener;
import sdfgjkljljoftrytrszgijpokjprs.IBatchListSizeListener;
import sdfgjkljljoftrytrszgijpokjprs.IMetaInformationListener;
import sdfgjkljljoftrytrszgijpokjprs.SystemStateMachine;
import sdfgjkljljoftrytrszgijpokjprs.IAudioInputStream;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.BatchController;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;

public class InfoControl
extends JPanel
implements IPlayerStateListener,
IPlayerEventListener,
IBatchListSizeListener,
IMetaInformationListener {
    private static final long serialVersionUID = 1L;
    private String DSP = "";
    private IAudioMetaInformation FFT;
    private Object responseView;
    private JLabel AdditionalMetadataValue;

    public InfoControl() {
        this.DSP();
    }

    private void DSP() {
        this.AdditionalMetadataValue = new JLabel();
        this.AdditionalMetadataValue.setForeground(new Color(255, 255, 255));
        this.AdditionalMetadataValue.setText("No track loaded - Click here to open the playlist or drag & drop audio files");
        this.AdditionalMetadataValue.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                InfoControl.this.DSP(mouseEvent);
            }
        });
        GroupLayout groupLayout = new GroupLayout(this);
        this.setLayout(groupLayout);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(this.AdditionalMetadataValue, -1, 452, Short.MAX_VALUE).addContainerGap()));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AdditionalMetadataValue, -1, 36, Short.MAX_VALUE));
    }

    private void DSP(MouseEvent mouseEvent) {
        BatchController.DSP().DSP(true);
    }

    @Override
    public boolean DSP(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2, Object object) {
        try {
            switch (lXCOGwZXVelwEKHMMPwpSAO2) {
                case FFT: {
                    this.AdditionalMetadataValue.setText(this.DSP("No track loaded - Click here to open the playlist or drag & drop audio files"));
                    break;
                }
                case DSP: {
                    this.responseView = object;
                }
                case responseView: {
                    if (this.responseView == null) break;
                    if (this.responseView instanceof String) {
                        File file = new File((String)object);
                        this.AdditionalMetadataValue.setText(this.DSP(file.getName()));
                        break;
                    }
                    if (!(this.responseView instanceof IAudioInputStream)) break;
                    this.AdditionalMetadataValue.setText(this.DSP(String.format("%s Input Selected - Click Play to activate", this.DSP)));
                    break;
                }
                case IAudioMetaInformation: {
                    if (!(this.responseView instanceof IAudioInputStream)) break;
                    this.AdditionalMetadataValue.setText(this.DSP(String.format("%s Input Activated!", this.DSP)));
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return true;
    }

    @Override
    public void DSP(int n) {
        this.FFT();
    }

    @Override
    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA2) {
        this.FFT = yGjBevanihqaxYKnUNtrNeA;
        switch (yGjBevanihqaxYKnUNtrNeA.MetaInfomationCopy()) {
            case MetaInfomationCopy: {
                this.DSP = "Audio";
                break;
            }
            case AacAudioCodec: {
                this.DSP = "VST/AU";
            }
        }
    }

    @Override
    public void DSP(PlayerState zjoyaRSokkGYDwXHPKTBIiX) {
        this.FFT();
    }

    private void FFT() {
        if (BatchController.DSP().FFT() && !this.responseView()) {
            this.AdditionalMetadataValue.setForeground(Color.decode("#55b9ff"));
        } else if (this.responseView() && SystemStateMachine.DSP().FFT() != PlayerState.DSP) {
            this.AdditionalMetadataValue.setForeground(Color.RED);
        } else {
            this.AdditionalMetadataValue.setForeground(Color.WHITE);
        }
    }

    private String DSP(String string) {
        FontMetrics fontMetrics = this.AdditionalMetadataValue.getGraphics().getFontMetrics();
        String string2 = string;
        int n = fontMetrics.stringWidth(string);
        int n2 = this.AdditionalMetadataValue.getWidth();
        int n3 = (string.length() - 3) / 2;
        int n4 = 1;
        while (n > n2) {
            string2 = string.substring(0, n3 - n4);
            string2 = string2 + "...";
            string2 = string2 + string.substring(n3 + n4, string.length());
            n = fontMetrics.stringWidth(string2);
            ++n4;
        }
        return string2;
    }

    private boolean responseView() {
        return this.FFT.MetaInfomationCopy() == AudioExtension.MetaInfomationCopy || this.FFT.MetaInfomationCopy() == AudioExtension.AacAudioCodec;
    }
}

