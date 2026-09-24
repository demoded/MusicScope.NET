/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.DetailedArtistModel;
import 83nnfii93jksoiow9.GenericTableView;
import 83nnfii93jksoiow9.ArtistSearchViewController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ArtistSearchView
extends JPanel {
    private ArtistSearchViewController INavigationControlListener;
    private JLabel LraControl;
    private JTextField NavigationControl;
    private JButton GenericSearchController;
    private JPanel GenericSearchField;
    private JPanel GenericSearchPanel;
    private JPanel SearchItem;
    private JPanel SearchItemPanel;
    private JButton TabCloseListener;
    private JPanel TabController;
    private JPanel TabHeaderPanel;
    private GenericTableView TabbedSearchView;
    private JLabel AbstractTableView;
    private JLabel AnnotatedTableView;
    private JTextField ColumnItem;
    private JTextField GenericTableView;
    private JPanel ITableItemClickedListener;
    private JPanel TableColumn;
    private JPanel AlbumOverviewViewController;

    public ArtistSearchView() {
        this.LraControl();
    }

    public void INavigationControlListener(String name) {
        this.NavigationControl.setText(name);
    }

    public String INavigationControlListener() {
        return this.NavigationControl.getText();
    }

    public void INavigationControlListener(GenericTableView<DetailedArtistModel.INavigationControlListener> tableView) {
        this.TabHeaderPanel.setLayout(new BorderLayout());
        this.TabHeaderPanel.add(tableView, "Center");
        this.TabbedSearchView = tableView;
    }

    public void INavigationControlListener(ArtistSearchViewController controller) {
        this.INavigationControlListener = controller;
    }

    private void LraControl() {
        this.SearchItemPanel = new JPanel();
        this.AlbumOverviewViewController = new JPanel();
        this.LraControl = new JLabel();
        this.NavigationControl = new JTextField();
        this.TabCloseListener = new JButton();
        this.TabController = new JPanel();
        this.TabHeaderPanel = new JPanel();
        this.TabbedSearchView = new GenericTableView();
        this.GenericSearchField = new JPanel();
        this.GenericSearchController = new JButton();
        this.LraControl.setText("Artist:");
        this.NavigationControl.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ArtistSearchView.this.INavigationControlListener(evt);
            }
        });
        this.TabCloseListener.setText("Search");
        this.TabCloseListener.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ArtistSearchView.this.LraControl(evt);
            }
        });
        GroupLayout valuePanel2Layout = new GroupLayout(this.AlbumOverviewViewController);
        this.AlbumOverviewViewController.setLayout(valuePanel2Layout);
        valuePanel2Layout.setHorizontalGroup(valuePanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(valuePanel2Layout.createSequentialGroup().addComponent(this.LraControl).addGap(90, 90, 90).addComponent(this.NavigationControl)).addGroup(GroupLayout.Alignment.TRAILING, valuePanel2Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.TabCloseListener, -2, 100, -2)));
        valuePanel2Layout.setVerticalGroup(valuePanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(valuePanel2Layout.createSequentialGroup().addGap(0, 0, 0).addGroup(valuePanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.LraControl).addComponent(this.NavigationControl, -2, -1, -2)).addGap(21, 21, 21).addComponent(this.TabCloseListener)));
        this.TabController.setBorder(BorderFactory.createEtchedBorder());
        GroupLayout tablePanelLayout = new GroupLayout(this.TabHeaderPanel);
        this.TabHeaderPanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(tablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tablePanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.TabbedSearchView, -1, 517, Short.MAX_VALUE).addGap(0, 0, 0)));
        tablePanelLayout.setVerticalGroup(tablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tablePanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.TabbedSearchView, -1, 186, Short.MAX_VALUE).addGap(0, 0, 0)));
        this.GenericSearchField.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(153, 153, 153)));
        this.GenericSearchController.setText("Load more");
        this.GenericSearchController.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ArtistSearchView.this.NavigationControl(evt);
            }
        });
        this.GenericSearchField.add(this.GenericSearchController);
        GroupLayout tabelBoarderPanelLayout = new GroupLayout(this.TabController);
        this.TabController.setLayout(tabelBoarderPanelLayout);
        tabelBoarderPanelLayout.setHorizontalGroup(tabelBoarderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tabelBoarderPanelLayout.createSequentialGroup().addGap(0, 0, 0).addGroup(tabelBoarderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.TabHeaderPanel, -1, -1, Short.MAX_VALUE).addComponent(this.GenericSearchField, -1, -1, Short.MAX_VALUE)).addGap(0, 0, 0)));
        tabelBoarderPanelLayout.setVerticalGroup(tabelBoarderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tabelBoarderPanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.TabHeaderPanel, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0).addComponent(this.GenericSearchField, -2, -1, -2).addGap(0, 0, 0)));
        GroupLayout rootPanel2Layout = new GroupLayout(this.SearchItemPanel);
        this.SearchItemPanel.setLayout(rootPanel2Layout);
        rootPanel2Layout.setHorizontalGroup(rootPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rootPanel2Layout.createSequentialGroup().addContainerGap().addGroup(rootPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AlbumOverviewViewController, -1, -1, Short.MAX_VALUE).addComponent(this.TabController, -1, -1, Short.MAX_VALUE)).addContainerGap()));
        rootPanel2Layout.setVerticalGroup(rootPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rootPanel2Layout.createSequentialGroup().addContainerGap().addComponent(this.AlbumOverviewViewController, -2, -1, -2).addGap(18, 18, 18).addComponent(this.TabController, -1, -1, Short.MAX_VALUE).addContainerGap()));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.SearchItemPanel, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.SearchItemPanel, -1, -1, Short.MAX_VALUE));
    }

    private void INavigationControlListener(ActionEvent evt) {
        this.NavigationControl();
    }

    private void LraControl(ActionEvent evt) {
        this.NavigationControl();
    }

    private void NavigationControl(ActionEvent evt) {
        this.updateUI();
        if (this.INavigationControlListener != null) {
            this.INavigationControlListener.NavigationControl();
        }
    }

    private void NavigationControl() {
        this.updateUI();
        if (this.INavigationControlListener != null) {
            this.TabbedSearchView.INavigationControlListener(true);
            this.INavigationControlListener.INavigationControlListener();
        }
    }
}

