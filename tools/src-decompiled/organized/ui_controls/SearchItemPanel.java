/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchItemPanel
extends JPanel {
    private JLabel INavigationControlListener;
    private JPanel LraControl;
    private JTextField NavigationControl;

    public SearchItemPanel() {
        this.NavigationControl();
    }

    public SearchItemPanel(String name) {
        this.NavigationControl();
        this.INavigationControlListener(name);
        this.LraControl("");
    }

    public void INavigationControlListener(String name) {
        this.INavigationControlListener.setText(name);
    }

    public void LraControl(String value) {
        this.NavigationControl.setText(value);
    }

    public String INavigationControlListener() {
        return this.INavigationControlListener.getName();
    }

    public String LraControl() {
        return this.NavigationControl.getText();
    }

    private void NavigationControl() {
        this.LraControl = new JPanel();
        this.INavigationControlListener = new JLabel();
        this.NavigationControl = new JTextField();
        this.setMaximumSize(new Dimension(5555, 35));
        this.setMinimumSize(new Dimension(100, 35));
        this.LraControl.setMaximumSize(new Dimension(Short.MAX_VALUE, 35));
        this.LraControl.setMinimumSize(new Dimension(0, 35));
        this.INavigationControlListener.setText("Title Text");
        this.NavigationControl.setText("jTextField1");
        GroupLayout rootPanelLayout = new GroupLayout(this.LraControl);
        this.LraControl.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, rootPanelLayout.createSequentialGroup().addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.INavigationControlListener, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.NavigationControl, -1, 360, Short.MAX_VALUE)).addContainerGap()));
        rootPanelLayout.setVerticalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rootPanelLayout.createSequentialGroup().addComponent(this.INavigationControlListener).addGap(1, 1, 1).addComponent(this.NavigationControl, -2, -1, -2)));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.LraControl, -1, -1, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.LraControl, -2, -1, -2)));
    }
}

