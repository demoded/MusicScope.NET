/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.GenericTableView;
import 83nnfii93jksoiow9.SingleExecutor;
import 83nnfii93jksoiow9.IExecutorStateChangedListener;
import 83nnfii93jksoiow9.TrackSearchViewCrontroller;
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

public class TrackSearchView
extends JPanel
implements IExecutorStateChangedListener {
    private TrackSearchViewCrontroller INavigationControlListener;
    private JLabel LraControl;
    private JTextField NavigationControl;
    private JLabel GenericSearchController;
    private JTextField GenericSearchField;
    private JButton GenericSearchPanel;
    private JPanel SearchItem;
    private JLabel SearchItemPanel;
    private JTextField TabCloseListener;
    private JPanel TabController;
    private JButton TabHeaderPanel;
    private JPanel TabbedSearchView;
    private JPanel AbstractTableView;
    private GenericTableView AnnotatedTableView;
    private JPanel ColumnItem;

    public TrackSearchView() {
        this.GenericSearchController();
        SingleExecutor.INavigationControlListener().INavigationControlListener(this);
    }

    public void INavigationControlListener(GenericTableView<?> tableView) {
        this.AbstractTableView.setLayout(new BorderLayout());
        this.AbstractTableView.add(tableView, "Center");
        this.AnnotatedTableView = tableView;
    }

    public void INavigationControlListener(TrackSearchViewCrontroller crontroller) {
        this.INavigationControlListener = crontroller;
    }

    public void INavigationControlListener(String value) {
        this.TabCloseListener.setText(value);
    }

    public String INavigationControlListener() {
        return this.TabCloseListener.getText().trim();
    }

    public String LraControl() {
        return this.NavigationControl.getText().trim();
    }

    public String NavigationControl() {
        return this.GenericSearchField.getText().trim();
    }

    private void GenericSearchController() {
        this.TabController = new JPanel();
        this.ColumnItem = new JPanel();
        this.SearchItemPanel = new JLabel();
        this.GenericSearchController = new JLabel();
        this.LraControl = new JLabel();
        this.TabCloseListener = new JTextField();
        this.GenericSearchField = new JTextField();
        this.NavigationControl = new JTextField();
        this.TabHeaderPanel = new JButton();
        this.TabbedSearchView = new JPanel();
        this.AbstractTableView = new JPanel();
        this.AnnotatedTableView = new GenericTableView();
        this.SearchItem = new JPanel();
        this.GenericSearchPanel = new JButton();
        this.SearchItemPanel.setText("Track:");
        this.GenericSearchController.setText("Artist:");
        this.LraControl.setText("Album:");
        this.TabCloseListener.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                TrackSearchView.this.LraControl(evt);
            }
        });
        this.GenericSearchField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                TrackSearchView.this.NavigationControl(evt);
            }
        });
        this.NavigationControl.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                TrackSearchView.this.GenericSearchController(evt);
            }
        });
        this.TabHeaderPanel.setText("Search");
        this.TabHeaderPanel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                TrackSearchView.this.INavigationControlListener(evt);
            }
        });
        GroupLayout valuePanelLayout = new GroupLayout(this.ColumnItem);
        this.ColumnItem.setLayout(valuePanelLayout);
        valuePanelLayout.setHorizontalGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(valuePanelLayout.createSequentialGroup().addGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.SearchItemPanel).addComponent(this.GenericSearchController).addComponent(this.LraControl)).addGap(35, 35, 35).addGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.GenericSearchField, GroupLayout.Alignment.TRAILING).addComponent(this.TabCloseListener, -1, 467, Short.MAX_VALUE).addComponent(this.NavigationControl, GroupLayout.Alignment.TRAILING))).addGroup(GroupLayout.Alignment.TRAILING, valuePanelLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.TabHeaderPanel, -2, 100, -2)));
        valuePanelLayout.setVerticalGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(valuePanelLayout.createSequentialGroup().addGap(0, 0, 0).addGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.SearchItemPanel).addComponent(this.TabCloseListener, -2, -1, -2)).addGap(18, 18, 18).addGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.GenericSearchField, -2, -1, -2).addComponent(this.GenericSearchController)).addGap(18, 18, 18).addGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.NavigationControl, -2, -1, -2).addComponent(this.LraControl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE).addComponent(this.TabHeaderPanel)));
        this.TabbedSearchView.setBorder(BorderFactory.createEtchedBorder());
        GroupLayout tablePanelLayout = new GroupLayout(this.AbstractTableView);
        this.AbstractTableView.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(tablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AnnotatedTableView, GroupLayout.Alignment.TRAILING, -1, 531, Short.MAX_VALUE));
        tablePanelLayout.setVerticalGroup(tablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tablePanelLayout.createSequentialGroup().addComponent(this.AnnotatedTableView, -1, 83, Short.MAX_VALUE).addGap(0, 0, 0)));
        this.SearchItem.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(153, 153, 153)));
        this.GenericSearchPanel.setText("Load more");
        this.GenericSearchPanel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                TrackSearchView.this.GenericSearchField(evt);
            }
        });
        this.SearchItem.add(this.GenericSearchPanel);
        GroupLayout tableBoarderPanelLayout = new GroupLayout(this.TabbedSearchView);
        this.TabbedSearchView.setLayout(tableBoarderPanelLayout);
        tableBoarderPanelLayout.setHorizontalGroup(tableBoarderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AbstractTableView, -1, -1, Short.MAX_VALUE).addComponent(this.SearchItem, -1, -1, Short.MAX_VALUE));
        tableBoarderPanelLayout.setVerticalGroup(tableBoarderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tableBoarderPanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.AbstractTableView, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0).addComponent(this.SearchItem, -2, -1, -2)));
        GroupLayout rootPanelLayout = new GroupLayout(this.TabController);
        this.TabController.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rootPanelLayout.createSequentialGroup().addContainerGap().addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.ColumnItem, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE).addComponent(this.TabbedSearchView, -1, -1, Short.MAX_VALUE)).addContainerGap()));
        rootPanelLayout.setVerticalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rootPanelLayout.createSequentialGroup().addContainerGap().addComponent(this.ColumnItem, -2, -1, -2).addGap(18, 18, 18).addComponent(this.TabbedSearchView, -1, -1, Short.MAX_VALUE).addContainerGap()));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.TabController, -1, -1, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.TabController, -1, -1, Short.MAX_VALUE));
    }

    private void INavigationControlListener(ActionEvent evt) {
        this.GenericSearchField();
    }

    private void LraControl(ActionEvent evt) {
        this.GenericSearchField();
    }

    private void NavigationControl(ActionEvent evt) {
        this.GenericSearchField();
    }

    private void GenericSearchController(ActionEvent evt) {
        this.GenericSearchField();
    }

    private void GenericSearchField(ActionEvent evt) {
        this.updateUI();
        if (this.INavigationControlListener != null) {
            this.INavigationControlListener.NavigationControl();
        }
    }

    private void GenericSearchField() {
        this.updateUI();
        if (this.INavigationControlListener != null) {
            this.AnnotatedTableView.INavigationControlListener(true);
            this.INavigationControlListener.INavigationControlListener();
        }
    }

    @Override
    public void INavigationControlListener(IExecutorStateChangedListener.INavigationControlListener state) {
        this.AnnotatedTableView.INavigationControlListener(state == IExecutorStateChangedListener.INavigationControlListener.INavigationControlListener);
    }
}

