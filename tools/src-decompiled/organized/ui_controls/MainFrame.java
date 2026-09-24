/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.INavigationControlListener;
import 83nnfii93jksoiow9.BaseModel;
import 83nnfii93jksoiow9.NavigationControl;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;

public class MainFrame
extends JFrame {
    private JPanel INavigationControlListener;
    private NavigationControl LraControl;

    public MainFrame() {
        this.INavigationControlListener();
        this.setDefaultCloseOperation(1);
        this.setLocationRelativeTo(null);
    }

    public void INavigationControlListener(JPanel content) {
        this.INavigationControlListener.removeAll();
        this.INavigationControlListener.setLayout(new BorderLayout());
        this.INavigationControlListener.add((Component)content, "Center");
        this.INavigationControlListener.updateUI();
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                MainFrame.this.repaint();
            }
        });
    }

    public void INavigationControlListener(List<? extends Image> icons) {
        this.setIconImages(icons);
    }

    public void INavigationControlListener(String name, Class<? extends BaseModel> modelClass) {
        this.LraControl.INavigationControlListener(name, modelClass);
    }

    public void INavigationControlListener(INavigationControlListener listener) {
        this.LraControl.INavigationControlListener(listener);
    }

    public void INavigationControlListener(boolean enabled) {
        this.LraControl.INavigationControlListener(enabled);
    }

    private void INavigationControlListener() {
        this.INavigationControlListener = new JPanel();
        this.LraControl = new NavigationControl();
        this.setDefaultCloseOperation(3);
        this.setMinimumSize(new Dimension(880, 600));
        this.setName("CloudViewFrame");
        this.setPreferredSize(new Dimension(880, 600));
        this.INavigationControlListener.setBorder(BorderFactory.createEtchedBorder());
        GroupLayout contentPanelLayout = new GroupLayout(this.INavigationControlListener);
        this.INavigationControlListener.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        contentPanelLayout.setVerticalGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 546, Short.MAX_VALUE));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.INavigationControlListener, -1, -1, Short.MAX_VALUE).addComponent(this.LraControl, -1, 856, Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.LraControl, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.INavigationControlListener, -1, -1, Short.MAX_VALUE).addContainerGap()));
        this.pack();
    }
}

