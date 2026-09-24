/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.DetailedAlbumModel;
import 83nnfii93jksoiow9.ILoadMoreActionListener;
import 83nnfii93jksoiow9.GenericTableView;
import 83nnfii93jksoiow9.SingleExecutor;
import 83nnfii93jksoiow9.IExecutorStateChangedListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AlbumOverviewView
extends JPanel
implements IExecutorStateChangedListener {
    private ILoadMoreActionListener INavigationControlListener;
    private JButton LraControl;
    private JPanel NavigationControl;
    private JPanel GenericSearchController;
    private GenericTableView GenericSearchField;

    public AlbumOverviewView() {
        this.INavigationControlListener();
        SingleExecutor.INavigationControlListener().INavigationControlListener(this);
    }

    public void INavigationControlListener(ILoadMoreActionListener loadMoreActionListener) {
        this.INavigationControlListener = loadMoreActionListener;
    }

    public void INavigationControlListener(boolean enabled) {
        this.LraControl.setEnabled(enabled);
    }

    public void INavigationControlListener(GenericTableView<? extends DetailedAlbumModel> tableView) {
        this.GenericSearchController.setLayout(new BorderLayout());
        this.GenericSearchController.add(tableView, "Center");
        this.GenericSearchField = tableView;
    }

    private void INavigationControlListener() {
        this.GenericSearchController = new JPanel();
        this.GenericSearchField = new GenericTableView();
        this.NavigationControl = new JPanel();
        this.LraControl = new JButton();
        GroupLayout rootPanelLayout = new GroupLayout(this.GenericSearchController);
        this.GenericSearchController.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.GenericSearchField, -1, 471, Short.MAX_VALUE));
        rootPanelLayout.setVerticalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.GenericSearchField, -1, 246, Short.MAX_VALUE));
        this.NavigationControl.setBackground(this.GenericSearchField.getBackground());
        this.NavigationControl.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(153, 153, 153)));
        this.NavigationControl.setInheritsPopupMenu(true);
        this.NavigationControl.setRequestFocusEnabled(false);
        this.LraControl.setText("Update & Load more");
        this.LraControl.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                AlbumOverviewView.this.INavigationControlListener(evt);
            }
        });
        this.NavigationControl.add(this.LraControl);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.GenericSearchController, -1, -1, Short.MAX_VALUE).addComponent(this.NavigationControl, -1, -1, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.GenericSearchController, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0).addComponent(this.NavigationControl, -2, 34, -2)));
    }

    private void INavigationControlListener(ActionEvent evt) {
        if (this.INavigationControlListener != null) {
            this.INavigationControlListener.INavigationControlListener();
        }
    }

    @Override
    public void INavigationControlListener(IExecutorStateChangedListener.INavigationControlListener state) {
        this.LraControl.setEnabled(state == IExecutorStateChangedListener.INavigationControlListener.LraControl);
        this.GenericSearchField.INavigationControlListener(state == IExecutorStateChangedListener.INavigationControlListener.INavigationControlListener);
    }
}

