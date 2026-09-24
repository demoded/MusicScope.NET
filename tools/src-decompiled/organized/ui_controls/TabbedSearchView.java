/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.TabHeaderPanel;
import 83nnfii93jksoiow9.TabCloseListener;
import java.awt.Cursor;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabbedSearchView
extends JPanel {
    private JPanel INavigationControlListener;
    private JTabbedPane LraControl;

    public TabbedSearchView() {
        this.INavigationControlListener();
    }

    public void INavigationControlListener(String title, JPanel panel, TabCloseListener closeListener) {
        TabHeaderPanel tabHeaderPanel = new TabHeaderPanel(title, closeListener);
        this.LraControl.addTab(title, panel);
        this.LraControl.setSelectedComponent(panel);
        this.LraControl.setTabComponentAt(this.LraControl.indexOfComponent(panel), tabHeaderPanel);
    }

    public void INavigationControlListener(JPanel panel) {
        this.LraControl.remove(panel);
    }

    private void INavigationControlListener() {
        this.INavigationControlListener = new JPanel();
        this.LraControl = new JTabbedPane();
        this.LraControl.setTabLayoutPolicy(1);
        this.LraControl.setCursor(new Cursor(0));
        GroupLayout rootPanelLayout = new GroupLayout(this.INavigationControlListener);
        this.INavigationControlListener.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.LraControl, GroupLayout.Alignment.TRAILING, -1, 335, Short.MAX_VALUE));
        rootPanelLayout.setVerticalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.LraControl, GroupLayout.Alignment.TRAILING, -1, 300, Short.MAX_VALUE));
        this.LraControl.getAccessibleContext().setAccessibleDescription("");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.INavigationControlListener, -1, -1, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.INavigationControlListener, -1, -1, Short.MAX_VALUE));
    }
}

