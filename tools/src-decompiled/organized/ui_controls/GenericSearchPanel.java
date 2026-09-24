/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.SearchItemPanel;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GenericSearchPanel
extends JPanel {
    private JPanel INavigationControlListener;
    private JScrollPane LraControl;

    public GenericSearchPanel() {
        this.NavigationControl();
    }

    public void INavigationControlListener(SearchItemPanel searchItem) {
        this.INavigationControlListener.add(searchItem);
    }

    public List<SearchItemPanel> INavigationControlListener() {
        ArrayList<SearchItemPanel> searchItems = new ArrayList<SearchItemPanel>(0);
        for (Component component : this.INavigationControlListener.getComponents()) {
            if (!(component instanceof SearchItemPanel)) continue;
            searchItems.add((SearchItemPanel)component);
        }
        return searchItems;
    }

    public int LraControl() {
        return this.INavigationControlListener.getComponentCount();
    }

    public void LraControl(SearchItemPanel searchItem) {
        if (searchItem != null) {
            this.INavigationControlListener.remove(searchItem);
        }
    }

    @Override
    public void removeAll() {
        this.INavigationControlListener.removeAll();
    }

    private void NavigationControl() {
        this.LraControl = new JScrollPane();
        this.INavigationControlListener = new JPanel();
        this.LraControl.setBorder(null);
        this.LraControl.setHorizontalScrollBarPolicy(31);
        this.INavigationControlListener.setLayout(new BoxLayout(this.INavigationControlListener, 1));
        this.LraControl.setViewportView(this.INavigationControlListener);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.LraControl, -1, 300, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.LraControl, -1, 100, Short.MAX_VALUE));
    }
}

