/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import sdfgjkljljoftrytrszgijpokjprs.VolumePopup;
import sdfgjkljljoftrytrszgijpokjprs.IVolumeValueListener;

public class VolumeControl
extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final VolumePopup DSP = new VolumePopup(null, false);
    private JButton FFT;

    public VolumeControl() {
        this.FFT();
    }

    public int DSP() {
        return DSP.DSP();
    }

    public void DSP(IVolumeValueListener fdLwzVOPYyeAPmrtqvpdjBY2) {
        DSP.DSP(fdLwzVOPYyeAPmrtqvpdjBY2);
    }

    private void FFT() {
        this.FFT = new JButton();
        this.setBackground(new Color(0, 0, 0));
        this.FFT.setBackground(new Color(0, 0, 0));
        this.FFT.setIcon(new ImageIcon(this.getClass().getResource("/com/xivero/hraa/icons/volume_up-16.png")));
        this.FFT.setBorder(null);
        this.FFT.setDefaultCapable(false);
        this.FFT.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                VolumeControl.this.DSP(mouseEvent);
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                VolumeControl.this.FFT(mouseEvent);
            }
        });
        this.FFT.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                VolumeControl.this.DSP(actionEvent);
            }
        });
        GroupLayout groupLayout = new GroupLayout(this);
        this.setLayout(groupLayout);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.FFT, -2, 31, -2));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.FFT, -1, 25, Short.MAX_VALUE));
    }

    private void DSP(MouseEvent mouseEvent) {
    }

    private void FFT(MouseEvent mouseEvent) {
        DSP.responseView();
    }

    private void DSP(ActionEvent actionEvent) {
        Point point = MouseInfo.getPointerInfo().getLocation();
        DSP.setLocation((int)point.getX() + 1, (int)point.getY() + 1);
        DSP.AdditionalMetadataValue();
        DSP.setVisible(true);
    }
}

