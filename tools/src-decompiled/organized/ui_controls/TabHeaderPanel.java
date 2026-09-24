/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.TabCloseListener;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TabHeaderPanel
extends JPanel {
    private TabCloseListener INavigationControlListener;
    private JLabel LraControl;
    private JLabel NavigationControl;

    public TabHeaderPanel() {
        this.INavigationControlListener();
    }

    public TabHeaderPanel(String title, TabCloseListener closeListener) {
        this();
        this.NavigationControl.setText(title);
        this.INavigationControlListener = closeListener;
    }

    private void INavigationControlListener() {
        this.NavigationControl = new JLabel();
        this.LraControl = new JLabel();
        this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        this.NavigationControl.setText("jLabel1");
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = 17;
        gridBagConstraints.weightx = 1.0;
        this.add((Component)this.NavigationControl, gridBagConstraints);
        this.LraControl.setFont(new Font("Consolas", 0, 14));
        this.LraControl.setText("x");
        this.LraControl.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                TabHeaderPanel.this.INavigationControlListener(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = 13;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(0, 10, 0, 0);
        this.add((Component)this.LraControl, gridBagConstraints);
    }

    private void INavigationControlListener(MouseEvent evt) {
        if (this.INavigationControlListener != null) {
            this.INavigationControlListener.INavigationControlListener();
        }
    }
}

