/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.DetailedAlbumModel;
import 83nnfii93jksoiow9.GenericTableView;
import 83nnfii93jksoiow9.ArtistAlbumsViewController;
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

public class ArtistAlbumsView
extends JPanel {
    private ArtistAlbumsViewController INavigationControlListener;
    private JLabel LraControl;
    private JLabel NavigationControl;
    private JLabel GenericSearchController;
    private JLabel GenericSearchField;
    private JPanel GenericSearchPanel;
    private JPanel SearchItem;
    private Box.Filler SearchItemPanel;
    private Box.Filler TabCloseListener;
    private JButton TabController;
    private JPanel TabHeaderPanel;
    private JPanel TabbedSearchView;
    private JPanel AbstractTableView;
    private JPanel AnnotatedTableView;
    private GenericTableView ColumnItem;
    private JPanel GenericTableView;

    public ArtistAlbumsView() {
        this.INavigationControlListener();
    }

    private void INavigationControlListener() {
        this.TabbedSearchView = new JPanel();
        this.GenericTableView = new JPanel();
        this.GenericSearchPanel = new JPanel();
        this.GenericSearchController = new JLabel();
        this.SearchItemPanel = new Box.Filler(new Dimension(30, 0), new Dimension(30, 0), new Dimension(30, Short.MAX_VALUE));
        this.GenericSearchField = new JLabel();
        this.SearchItem = new JPanel();
        this.LraControl = new JLabel();
        this.TabCloseListener = new Box.Filler(new Dimension(30, 0), new Dimension(30, 0), new Dimension(30, Short.MAX_VALUE));
        this.NavigationControl = new JLabel();
        this.AbstractTableView = new JPanel();
        this.AnnotatedTableView = new JPanel();
        this.ColumnItem = new GenericTableView();
        this.TabHeaderPanel = new JPanel();
        this.TabController = new JButton();
        this.GenericSearchPanel.setLayout(new BoxLayout(this.GenericSearchPanel, 0));
        this.GenericSearchController.setText("Album list of Artist:");
        this.GenericSearchPanel.add(this.GenericSearchController);
        this.GenericSearchPanel.add(this.SearchItemPanel);
        this.GenericSearchField.setText("-");
        this.GenericSearchPanel.add(this.GenericSearchField);
        this.SearchItem.setLayout(new BoxLayout(this.SearchItem, 0));
        this.LraControl.setText("Number of Albums:");
        this.SearchItem.add(this.LraControl);
        this.SearchItem.add(this.TabCloseListener);
        this.NavigationControl.setText("-");
        this.SearchItem.add(this.NavigationControl);
        GroupLayout valuePanelLayout = new GroupLayout(this.GenericTableView);
        this.GenericTableView.setLayout(valuePanelLayout);
        valuePanelLayout.setHorizontalGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.GenericSearchPanel, -1, -1, Short.MAX_VALUE).addComponent(this.SearchItem, -1, 599, Short.MAX_VALUE));
        valuePanelLayout.setVerticalGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, valuePanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.GenericSearchPanel, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.SearchItem, -2, -1, -2)));
        this.AbstractTableView.setBorder(BorderFactory.createEtchedBorder());
        GroupLayout tablePanelLayout = new GroupLayout(this.AnnotatedTableView);
        this.AnnotatedTableView.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(tablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.ColumnItem, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE));
        tablePanelLayout.setVerticalGroup(tablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.ColumnItem, -1, 259, Short.MAX_VALUE));
        this.TabHeaderPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(153, 153, 153)));
        this.TabController.setText("Load more");
        this.TabController.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ArtistAlbumsView.this.INavigationControlListener(evt);
            }
        });
        this.TabHeaderPanel.add(this.TabController);
        GroupLayout tabelBoarderPanelLayout = new GroupLayout(this.AbstractTableView);
        this.AbstractTableView.setLayout(tabelBoarderPanelLayout);
        tabelBoarderPanelLayout.setHorizontalGroup(tabelBoarderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tabelBoarderPanelLayout.createSequentialGroup().addGap(0, 0, 0).addGroup(tabelBoarderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AnnotatedTableView, -1, -1, Short.MAX_VALUE).addComponent(this.TabHeaderPanel, -1, -1, Short.MAX_VALUE)).addGap(0, 0, 0)));
        tabelBoarderPanelLayout.setVerticalGroup(tabelBoarderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tabelBoarderPanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.AnnotatedTableView, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0).addComponent(this.TabHeaderPanel, -2, -1, -2).addGap(0, 0, 0)));
        GroupLayout rootPanelLayout = new GroupLayout(this.TabbedSearchView);
        this.TabbedSearchView.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rootPanelLayout.createSequentialGroup().addContainerGap().addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.GenericTableView, -1, -1, Short.MAX_VALUE).addComponent(this.AbstractTableView, -1, -1, Short.MAX_VALUE)).addContainerGap()));
        rootPanelLayout.setVerticalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, rootPanelLayout.createSequentialGroup().addContainerGap().addComponent(this.GenericTableView, -2, -1, -2).addGap(18, 18, 18).addComponent(this.AbstractTableView, -1, -1, Short.MAX_VALUE).addContainerGap()));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.TabbedSearchView, -1, -1, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.TabbedSearchView, -1, -1, Short.MAX_VALUE));
    }

    private void INavigationControlListener(ActionEvent evt) {
        this.updateUI();
        if (this.INavigationControlListener != null) {
            this.INavigationControlListener.LraControl();
        }
    }

    public void INavigationControlListener(ArtistAlbumsViewController controller) {
        this.INavigationControlListener = controller;
    }

    public void INavigationControlListener(GenericTableView<DetailedAlbumModel.NavigationControl> tableView) {
        this.AnnotatedTableView.setLayout(new BorderLayout());
        this.AnnotatedTableView.add(tableView, "Center");
        this.ColumnItem = tableView;
    }

    public void INavigationControlListener(String name) {
        this.GenericSearchField.setText(name);
    }

    public void INavigationControlListener(int albumCounter) {
        this.NavigationControl.setText("" + albumCounter);
    }
}

