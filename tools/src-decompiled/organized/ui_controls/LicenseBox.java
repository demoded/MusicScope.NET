/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.Color;
import java.awt.Frame;
import java.util.ResourceBundle;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class LicenseBox
extends JDialog {
    private JScrollPane DSP;
    private JTextPane FFT;

    public LicenseBox(Frame frame, boolean bl) {
        super(frame, bl);
        this.DSP();
        this.DSP.getVerticalScrollBar().setValue(0);
    }

    @Override
    public void setVisible(boolean bl) {
        super.setVisible(bl);
    }

    private void DSP() {
        this.DSP = new JScrollPane();
        this.FFT = new JTextPane();
        this.setDefaultCloseOperation(2);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com/xivero/hraa/gui/frame/license/Bundle");
        this.setTitle(resourceBundle.getString("LicenseBox.title"));
        this.FFT.setEditable(false);
        this.FFT.setBackground(new Color(240, 240, 240));
        this.FFT.setText(resourceBundle.getString("LicenseBox.textPane.text"));
        this.DSP.setViewportView(this.FFT);
        GroupLayout groupLayout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(groupLayout);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.DSP, -1, 709, Short.MAX_VALUE));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.DSP, -1, 677, Short.MAX_VALUE));
        this.pack();
    }
}

