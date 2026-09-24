/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.GenericTableView;
import 83nnfii93jksoiow9.SingleExecutor;
import 83nnfii93jksoiow9.AlbumSearchViewController;
import 83nnfii93jksoiow9.IExecutorStateChangedListener;
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
import javax.swing.LayoutStyle;

public class AlbumSearchView
extends JPanel
implements IExecutorStateChangedListener {
    private AlbumSearchViewController INavigationControlListener;
    private JButton LraControl;
    private JPanel NavigationControl;
    private JLabel GenericSearchController;
    private JTextField GenericSearchField;
    private JLabel GenericSearchPanel;
    private JLabel SearchItem;
    private JTextField SearchItemPanel;
    private JTextField TabCloseListener;
    private JPanel TabController;
    private JButton TabHeaderPanel;
    private JPanel TabbedSearchView;
    private JPanel AbstractTableView;
    private GenericTableView AnnotatedTableView;
    private JPanel ColumnItem;

    public AlbumSearchView() {
        this.SearchItemPanel();
        SingleExecutor.INavigationControlListener().INavigationControlListener(this);
    }

    public void INavigationControlListener(AlbumSearchViewController controller) {
        this.INavigationControlListener = controller;
    }

    public boolean INavigationControlListener() {
        return !this.LraControl().isEmpty();
    }

    public String LraControl() {
        return this.GenericSearchField.getText().trim();
    }

    public void INavigationControlListener(String name) {
        this.GenericSearchField.setText(name);
    }

    public boolean NavigationControl() {
        return !this.TabCloseListener.getText().trim().isEmpty();
    }

    public int GenericSearchController() {
        if (this.NavigationControl()) {
            return Integer.valueOf(this.TabCloseListener.getText().trim());
        }
        return -1;
    }

    public boolean GenericSearchField() {
        return !this.SearchItemPanel.getText().trim().isEmpty();
    }

    public int GenericSearchPanel() {
        if (this.GenericSearchField()) {
            return Integer.valueOf(this.SearchItemPanel.getText().trim());
        }
        return -1;
    }

    public void INavigationControlListener(GenericTableView<?> tableView) {
        this.AbstractTableView.setLayout(new BorderLayout());
        this.AbstractTableView.add(tableView, "Center");
        this.AnnotatedTableView = tableView;
    }

    private void SearchItem() {
        this.updateUI();
        if (this.INavigationControlListener != null) {
            this.AnnotatedTableView.INavigationControlListener(true);
            this.INavigationControlListener.INavigationControlListener();
        }
    }

    @Override
    public void INavigationControlListener(IExecutorStateChangedListener.INavigationControlListener state) {
        if (state == IExecutorStateChangedListener.INavigationControlListener.LraControl) {
            this.AnnotatedTableView.INavigationControlListener(false);
        }
    }

    private void SearchItemPanel() {
        this.TabController = new JPanel();
        this.ColumnItem = new JPanel();
        this.GenericSearchController = new JLabel();
        this.GenericSearchPanel = new JLabel();
        this.SearchItem = new JLabel();
        this.GenericSearchField = new JTextField();
        this.SearchItemPanel = new JTextField();
        this.TabCloseListener = new JTextField();
        this.TabHeaderPanel = new JButton();
        this.TabbedSearchView = new JPanel();
        this.AbstractTableView = new JPanel();
        this.AnnotatedTableView = new GenericTableView();
        this.NavigationControl = new JPanel();
        this.LraControl = new JButton();
        this.GenericSearchController.setText("Album:");
        this.GenericSearchPanel.setText("Number of Tracks:");
        this.SearchItem.setText("Release Year:");
        this.GenericSearchField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                AlbumSearchView.this.LraControl(evt);
            }
        });
        this.SearchItemPanel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                AlbumSearchView.this.NavigationControl(evt);
            }
        });
        this.TabCloseListener.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                AlbumSearchView.this.GenericSearchController(evt);
            }
        });
        this.TabHeaderPanel.setText("Search");
        this.TabHeaderPanel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                AlbumSearchView.this.INavigationControlListener(evt);
            }
        });
        GroupLayout valuePanelLayout = new GroupLayout(this.ColumnItem);
        this.ColumnItem.setLayout(valuePanelLayout);
        valuePanelLayout.setHorizontalGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(valuePanelLayout.createSequentialGroup().addGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.GenericSearchController).addComponent(this.GenericSearchPanel).addComponent(this.SearchItem)).addGap(35, 35, 35).addGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.SearchItemPanel, GroupLayout.Alignment.TRAILING).addComponent(this.GenericSearchField, -1, 412, Short.MAX_VALUE).addComponent(this.TabCloseListener, GroupLayout.Alignment.TRAILING))).addGroup(GroupLayout.Alignment.TRAILING, valuePanelLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.TabHeaderPanel, -2, 100, -2)));
        valuePanelLayout.setVerticalGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(valuePanelLayout.createSequentialGroup().addGap(0, 0, 0).addGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.GenericSearchController).addComponent(this.GenericSearchField, -2, -1, -2)).addGap(18, 18, 18).addGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.SearchItemPanel, -2, -1, -2).addComponent(this.GenericSearchPanel)).addGap(18, 18, 18).addGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.TabCloseListener, -2, -1, -2).addComponent(this.SearchItem)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE).addComponent(this.TabHeaderPanel)));
        this.TabbedSearchView.setBorder(BorderFactory.createEtchedBorder());
        GroupLayout tablePanelLayout = new GroupLayout(this.AbstractTableView);
        this.AbstractTableView.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(tablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tablePanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.AnnotatedTableView, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0)));
        tablePanelLayout.setVerticalGroup(tablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tablePanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.AnnotatedTableView, -1, 82, Short.MAX_VALUE).addGap(0, 0, 0)));
        this.NavigationControl.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(153, 153, 153)));
        this.LraControl.setText("Load more");
        this.LraControl.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                AlbumSearchView.this.GenericSearchField(evt);
            }
        });
        this.NavigationControl.add(this.LraControl);
        GroupLayout tabelBoarderPanelLayout = new GroupLayout(this.TabbedSearchView);
        this.TabbedSearchView.setLayout(tabelBoarderPanelLayout);
        tabelBoarderPanelLayout.setHorizontalGroup(tabelBoarderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tabelBoarderPanelLayout.createSequentialGroup().addGap(0, 0, 0).addGroup(tabelBoarderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AbstractTableView, -1, -1, Short.MAX_VALUE).addComponent(this.NavigationControl, -1, -1, Short.MAX_VALUE)).addGap(0, 0, 0)));
        tabelBoarderPanelLayout.setVerticalGroup(tabelBoarderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tabelBoarderPanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.AbstractTableView, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0).addComponent(this.NavigationControl, -2, -1, -2).addGap(0, 0, 0)));
        GroupLayout rootPanelLayout = new GroupLayout(this.TabController);
        this.TabController.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rootPanelLayout.createSequentialGroup().addContainerGap().addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.ColumnItem, -1, -1, Short.MAX_VALUE).addComponent(this.TabbedSearchView, -1, -1, Short.MAX_VALUE)).addContainerGap()));
        rootPanelLayout.setVerticalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rootPanelLayout.createSequentialGroup().addContainerGap().addComponent(this.ColumnItem, -2, -1, -2).addGap(18, 18, 18).addComponent(this.TabbedSearchView, -1, -1, Short.MAX_VALUE).addContainerGap()));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.TabController, -1, -1, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.TabController, -1, -1, Short.MAX_VALUE));
    }

    private void INavigationControlListener(ActionEvent evt) {
        this.SearchItem();
    }

    private void LraControl(ActionEvent evt) {
        this.SearchItem();
    }

    private void NavigationControl(ActionEvent evt) {
        this.SearchItem();
    }

    private void GenericSearchController(ActionEvent evt) {
        this.SearchItem();
    }

    private void GenericSearchField(ActionEvent evt) {
        this.updateUI();
        if (this.INavigationControlListener != null) {
            this.INavigationControlListener.NavigationControl();
        }
    }
}

