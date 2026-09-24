/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import sdfgjkljljoftrytrszgijpokjprs.OperatingSystem;
import sdfgjkljljoftrytrszgijpokjprs.IVolumeValueListener;
import sdfgjkljljoftrytrszgijpokjprs.Slinder;

public class VolumePanel
extends JPanel {
    private static final long serialVersionUID = 1L;
    private IVolumeValueListener DSP;
    private JPanel FFT;
    private Slinder responseView;

    public VolumePanel() {
        this.FFT();
    }

    public void DSP(IVolumeValueListener fdLwzVOPYyeAPmrtqvpdjBY2) {
        this.DSP = fdLwzVOPYyeAPmrtqvpdjBY2;
    }

    private void FFT() {
        this.FFT = new JPanel();
        this.responseView = new Slinder();
        this.FFT.setBackground(new Color(51, 51, 51));
        this.FFT.setForeground(new Color(255, 255, 255));
        this.responseView.setBackground(this.FFT.getBackground());
        this.responseView.setForeground(new Color(255, 255, 255));
        this.responseView.setMajorTickSpacing(60);
        this.responseView.setMaximum(0);
        this.responseView.setMinimum(-60);
        this.responseView.setMinorTickSpacing(10);
        this.responseView.setOrientation(1);
        this.responseView.setPaintLabels(true);
        this.responseView.setPaintTicks(true);
        this.responseView.setFocusable(OperatingSystem.FFT());
        this.responseView.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                VolumePanel.this.DSP(changeEvent);
            }
        });
        GroupLayout groupLayout = new GroupLayout(this.FFT);
        this.FFT.setLayout(groupLayout);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, groupLayout.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addComponent(this.responseView, -2, -1, -2).addContainerGap()));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(this.responseView, -1, 149, Short.MAX_VALUE).addContainerGap()));
        GroupLayout groupLayout2 = new GroupLayout(this);
        this.setLayout(groupLayout2);
        groupLayout2.setHorizontalGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.FFT, -2, -1, -2));
        groupLayout2.setVerticalGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.FFT, -1, -1, Short.MAX_VALUE));
    }

    private void DSP(ChangeEvent changeEvent) {
        if (this.DSP != null) {
            this.DSP.DSP(this.responseView.getValue());
        }
    }

    int DSP() {
        return this.responseView.getValue();
    }
}

