/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.Frame;
import java.awt.IllegalComponentStateException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import sdfgjkljljoftrytrszgijpokjprs.VolumePanel;
import sdfgjkljljoftrytrszgijpokjprs.IVolumeValueListener;

public class VolumePopup
extends JDialog {
    private static final long serialVersionUID = 1L;
    private Timer DSP;
    private int FFT = 500;
    private VolumePanel responseView;

    public VolumePopup(Frame frame, boolean bl) {
        super(frame, bl);
        this.AudioFileExtension();
        this.DSP = new Timer();
    }

    public void DSP(IVolumeValueListener fdLwzVOPYyeAPmrtqvpdjBY2) {
        this.responseView.DSP(fdLwzVOPYyeAPmrtqvpdjBY2);
    }

    public int DSP() {
        return this.responseView.DSP();
    }

    public boolean FFT() {
        try {
            Point point = this.getLocationOnScreen().getLocation();
            Point point2 = MouseInfo.getPointerInfo().getLocation();
            boolean bl = true;
            double d = point2.getX();
            double d2 = point2.getY();
            double d3 = point.getX();
            double d4 = point.getY();
            bl &= d >= d3;
            bl &= d < d3 + (double)this.getWidth();
            bl &= d2 >= d4;
            return bl &= d2 < d4 + (double)this.getHeight();
        }
        catch (IllegalComponentStateException illegalComponentStateException) {
            return false;
        }
    }

    public void responseView() {
        this.AdditionalMetadataValue();
        this.DSP = new Timer();
        this.DSP.schedule(new TimerTask(){

            @Override
            public void run() {
                if (!VolumePopup.this.FFT()) {
                    VolumePopup.this.setVisible(false);
                    VolumePopup.this.DSP.cancel();
                    VolumePopup.this.DSP = null;
                }
            }
        }, this.FFT, (long)this.FFT);
    }

    public void AdditionalMetadataValue() {
        if (this.DSP != null) {
            this.DSP.cancel();
            this.DSP = null;
        }
    }

    private void AudioFileExtension() {
        this.responseView = new VolumePanel();
        this.setDefaultCloseOperation(2);
        this.setUndecorated(true);
        GroupLayout groupLayout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(groupLayout);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.responseView, -2, -1, -2));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.responseView, -1, -1, -2));
        this.pack();
    }
}

