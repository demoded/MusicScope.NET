/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.DetailedAlbumModel;
import 83nnfii93jksoiow9.UserAlbumsViewController;
import 83nnfii93jksoiow9.GenericTableView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class UserAlbumsView
extends JPanel {
    private UserAlbumsViewController INavigationControlListener;
    private Box.Filler LraControl;
    private Box.Filler NavigationControl;
    private JPanel GenericSearchController;
    private JButton GenericSearchField;
    private JPanel GenericSearchPanel;
    private JPanel SearchItem;
    private JPanel SearchItemPanel;
    private JPanel TabCloseListener;
    private GenericTableView TabController;
    private JLabel TabHeaderPanel;
    private JLabel TabbedSearchView;
    private JLabel AbstractTableView;
    private JLabel AnnotatedTableView;
    private JPanel ColumnItem;
    private JPanel GenericTableView;

    public UserAlbumsView() {
        this.INavigationControlListener();
    }

    public void INavigationControlListener(UserAlbumsViewController controller) {
        this.INavigationControlListener = controller;
    }

    public void INavigationControlListener(GenericTableView<DetailedAlbumModel.NavigationControl> tableView) {
        this.TabCloseListener.setLayout(new BorderLayout());
        this.TabCloseListener.add(tableView, "Center");
        this.TabController = tableView;
    }

    public void INavigationControlListener(String name) {
        this.AnnotatedTableView.setText(name);
    }

    public void INavigationControlListener(int albumCounter) {
        this.TabbedSearchView.setText("" + albumCounter);
    }

    private void INavigationControlListener() {
        this.SearchItem = new JPanel();
        this.GenericTableView = new JPanel();
        this.ColumnItem = new JPanel();
        this.AbstractTableView = new JLabel();
        this.LraControl = new Box.Filler(new Dimension(30, 0), new Dimension(30, 0), new Dimension(30, Short.MAX_VALUE));
        this.AnnotatedTableView = new JLabel();
        this.GenericSearchController = new JPanel();
        this.TabHeaderPanel = new JLabel();
        this.NavigationControl = new Box.Filler(new Dimension(30, 0), new Dimension(30, 0), new Dimension(30, Short.MAX_VALUE));
        this.TabbedSearchView = new JLabel();
        this.SearchItemPanel = new JPanel();
        this.TabCloseListener = new JPanel();
        this.TabController = new GenericTableView();
        this.GenericSearchPanel = new JPanel();
        this.GenericSearchField = new JButton();
        this.ColumnItem.setLayout(new BoxLayout(this.ColumnItem, 0));
        this.AbstractTableView.setText("Album list of User:");
        this.ColumnItem.add(this.AbstractTableView);
        this.ColumnItem.add(this.LraControl);
        this.AnnotatedTableView.setText("-");
        this.ColumnItem.add(this.AnnotatedTableView);
        this.GenericSearchController.setLayout(new BoxLayout(this.GenericSearchController, 0));
        this.TabHeaderPanel.setText("Uploaded Albums:");
        this.GenericSearchController.add(this.TabHeaderPanel);
        this.GenericSearchController.add(this.NavigationControl);
        this.TabbedSearchView.setText("-");
        this.GenericSearchController.add(this.TabbedSearchView);
        GroupLayout valuePanelLayout = new GroupLayout(this.GenericTableView);
        this.GenericTableView.setLayout(valuePanelLayout);
        valuePanelLayout.setHorizontalGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.ColumnItem, -1, 616, Short.MAX_VALUE).addComponent(this.GenericSearchController, -1, -1, Short.MAX_VALUE));
        valuePanelLayout.setVerticalGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(valuePanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.ColumnItem, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.GenericSearchController, -2, -1, -2)));
        this.SearchItemPanel.setBorder(BorderFactory.createEtchedBorder());
        GroupLayout tablePanelLayout = new GroupLayout(this.TabCloseListener);
        this.TabCloseListener.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(tablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tablePanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.TabController, -1, 612, Short.MAX_VALUE).addGap(0, 0, 0)));
        tablePanelLayout.setVerticalGroup(tablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tablePanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.TabController, -1, 238, Short.MAX_VALUE).addGap(0, 0, 0)));
        this.GenericSearchPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(153, 153, 153)));
        this.GenericSearchField.setText("Load more");
        this.GenericSearchField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                UserAlbumsView.this.INavigationControlListener(evt);
            }
        });
        this.GenericSearchPanel.add(this.GenericSearchField);
        GroupLayout tabelBoarderPanelLayout = new GroupLayout(this.SearchItemPanel);
        this.SearchItemPanel.setLayout(tabelBoarderPanelLayout);
        tabelBoarderPanelLayout.setHorizontalGroup(tabelBoarderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tabelBoarderPanelLayout.createSequentialGroup().addGap(0, 0, 0).addGroup(tabelBoarderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.TabCloseListener, -1, -1, Short.MAX_VALUE).addComponent(this.GenericSearchPanel, -1, -1, Short.MAX_VALUE)).addGap(0, 0, 0)));
        tabelBoarderPanelLayout.setVerticalGroup(tabelBoarderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tabelBoarderPanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.TabCloseListener, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0).addComponent(this.GenericSearchPanel, -2, -1, -2).addGap(0, 0, 0)));
        GroupLayout rootPanelLayout = new GroupLayout(this.SearchItem);
        this.SearchItem.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rootPanelLayout.createSequentialGroup().addContainerGap().addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.SearchItemPanel, -1, -1, Short.MAX_VALUE).addComponent(this.GenericTableView, -1, -1, Short.MAX_VALUE)).addContainerGap()));
        rootPanelLayout.setVerticalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rootPanelLayout.createSequentialGroup().addContainerGap().addComponent(this.GenericTableView, -2, -1, -2).addGap(18, 18, 18).addComponent(this.SearchItemPanel, -1, -1, Short.MAX_VALUE).addContainerGap()));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.SearchItem, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.SearchItem, -1, -1, Short.MAX_VALUE));
    }

    private void INavigationControlListener(ActionEvent evt) {
        this.updateUI();
        if (this.INavigationControlListener != null) {
            this.INavigationControlListener.LraControl();
        }
    }
}

