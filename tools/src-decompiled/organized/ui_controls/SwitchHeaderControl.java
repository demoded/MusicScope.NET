/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import sdfgjkljljoftrytrszgijpokjprs.ISwitchLabelClickedListener;

public class SwitchHeaderControl
extends JPanel {
    private static final long serialVersionUID = 1L;
    private ISwitchLabelClickedListener DSP;
    private boolean FFT;
    private Color responseView;
    private Color AdditionalMetadataValue;
    private JLabel AudioFileExtension;
    private JLabel IAudioFileCodec;
    private JPanel IAudioInputStream;

    public SwitchHeaderControl() {
        this.DSP();
        this.FFT(this.FFT);
    }

    public void DSP(String string) {
        this.AudioFileExtension.setText(string);
    }

    public void FFT(String string) {
        this.IAudioFileCodec.setText(string);
    }

    public void DSP(Color color) {
        if (color != null) {
            this.responseView = color;
            this.FFT(this.FFT);
        }
    }

    public void FFT(Color color) {
        if (color != null) {
            this.AdditionalMetadataValue = color;
            this.FFT(this.FFT);
        }
    }

    public void DSP(boolean bl) {
        this.FFT = bl;
        this.FFT(bl);
    }

    public void DSP(ISwitchLabelClickedListener phdpEmucFSWEcDGcyHNJalI) {
        if (phdpEmucFSWEcDGcyHNJalI != null) {
            this.DSP = phdpEmucFSWEcDGcyHNJalI;
        }
    }

    private void DSP() {
        this.IAudioInputStream = new JPanel();
        this.AudioFileExtension = new JLabel();
        this.IAudioFileCodec = new JLabel();
        this.setBackground(new Color(0, 0, 0));
        this.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                SwitchHeaderControl.this.DSP(mouseEvent);
            }
        });
        this.IAudioInputStream.setBackground(this.IAudioInputStream.getBackground());
        this.IAudioInputStream.setOpaque(false);
        this.AudioFileExtension.setBackground(new Color(0, 0, 0));
        this.AudioFileExtension.setForeground(new Color(0, 144, 0));
        this.AudioFileExtension.setHorizontalAlignment(2);
        this.AudioFileExtension.setText("Left");
        this.AudioFileExtension.setMaximumSize(null);
        this.AudioFileExtension.setMinimumSize(null);
        this.AudioFileExtension.setOpaque(true);
        this.AudioFileExtension.setPreferredSize(null);
        this.AudioFileExtension.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                SwitchHeaderControl.this.FFT(mouseEvent);
            }
        });
        this.IAudioFileCodec.setBackground(new Color(0, 0, 0));
        this.IAudioFileCodec.setForeground(new Color(0, 144, 0));
        this.IAudioFileCodec.setHorizontalAlignment(4);
        this.IAudioFileCodec.setText("Bit Monitor");
        this.IAudioFileCodec.setMaximumSize(null);
        this.IAudioFileCodec.setMinimumSize(null);
        this.IAudioFileCodec.setOpaque(true);
        this.IAudioFileCodec.setPreferredSize(null);
        this.IAudioFileCodec.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                SwitchHeaderControl.this.responseView(mouseEvent);
            }
        });
        GroupLayout groupLayout = new GroupLayout(this.IAudioInputStream);
        this.IAudioInputStream.setLayout(groupLayout);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(this.AudioFileExtension, -1, -1, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.IAudioFileCodec, -1, -1, Short.MAX_VALUE)));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.IAudioFileCodec, -2, -1, -2).addComponent(this.AudioFileExtension, -2, -1, -2)));
        GroupLayout groupLayout2 = new GroupLayout(this);
        this.setLayout(groupLayout2);
        groupLayout2.setHorizontalGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.IAudioInputStream, -1, -1, Short.MAX_VALUE));
        groupLayout2.setVerticalGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.IAudioInputStream, -2, -1, -2));
    }

    private void DSP(MouseEvent mouseEvent) {
    }

    private void FFT(MouseEvent mouseEvent) {
        boolean bl = false;
        if (this.DSP != null && this.FFT != bl) {
            this.DSP(this.DSP.DSP(bl) ? bl : this.FFT);
        }
    }

    private void responseView(MouseEvent mouseEvent) {
        boolean bl = true;
        if (this.DSP != null && this.FFT != bl) {
            this.DSP(this.DSP.DSP(bl) ? bl : this.FFT);
        }
    }

    private void FFT(boolean bl) {
        this.AudioFileExtension.setForeground(bl ? this.AdditionalMetadataValue : this.responseView);
        this.IAudioFileCodec.setForeground(bl ? this.responseView : this.AdditionalMetadataValue);
    }
}

