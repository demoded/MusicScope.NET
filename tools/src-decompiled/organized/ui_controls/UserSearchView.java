/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.GenericTableView;
import 83nnfii93jksoiow9.UserSearchViewController;
import 83nnfii93jksoiow9.DetailedUserModel;
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

public class UserSearchView
extends JPanel {
    private UserSearchViewController INavigationControlListener;
    private JButton LraControl;
    private JPanel NavigationControl;
    private JPanel GenericSearchController;
    private JButton GenericSearchField;
    private JPanel GenericSearchPanel;
    private JPanel SearchItem;
    private GenericTableView SearchItemPanel;
    private JLabel TabCloseListener;
    private JTextField TabController;
    private JPanel TabHeaderPanel;

    public UserSearchView() {
        this.LraControl();
    }

    public void INavigationControlListener(String name) {
        this.TabController.setText(name);
    }

    public String INavigationControlListener() {
        return this.TabController.getText();
    }

    private void LraControl() {
        this.GenericSearchController = new JPanel();
        this.TabHeaderPanel = new JPanel();
        this.TabCloseListener = new JLabel();
        this.TabController = new JTextField();
        this.GenericSearchField = new JButton();
        this.GenericSearchPanel = new JPanel();
        this.SearchItem = new JPanel();
        this.SearchItemPanel = new GenericTableView();
        this.NavigationControl = new JPanel();
        this.LraControl = new JButton();
        this.TabCloseListener.setText("User:");
        this.TabController.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                UserSearchView.this.INavigationControlListener(evt);
            }
        });
        this.GenericSearchField.setText("Search");
        this.GenericSearchField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                UserSearchView.this.LraControl(evt);
            }
        });
        GroupLayout valuePanelLayout = new GroupLayout(this.TabHeaderPanel);
        this.TabHeaderPanel.setLayout(valuePanelLayout);
        valuePanelLayout.setHorizontalGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(valuePanelLayout.createSequentialGroup().addComponent(this.TabCloseListener).addGap(90, 90, 90).addComponent(this.TabController)).addGroup(GroupLayout.Alignment.TRAILING, valuePanelLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.GenericSearchField, -2, 100, -2)));
        valuePanelLayout.setVerticalGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(valuePanelLayout.createSequentialGroup().addGap(0, 0, 0).addGroup(valuePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.TabCloseListener).addComponent(this.TabController, -2, -1, -2)).addGap(21, 21, 21).addComponent(this.GenericSearchField)));
        this.GenericSearchPanel.setBorder(BorderFactory.createEtchedBorder());
        GroupLayout tablePanelLayout = new GroupLayout(this.SearchItem);
        this.SearchItem.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(tablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tablePanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.SearchItemPanel, -1, 565, Short.MAX_VALUE).addGap(0, 0, 0)));
        tablePanelLayout.setVerticalGroup(tablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tablePanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.SearchItemPanel, -1, 198, Short.MAX_VALUE).addGap(0, 0, 0)));
        this.NavigationControl.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(153, 153, 153)));
        this.LraControl.setText("Load more");
        this.LraControl.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                UserSearchView.this.NavigationControl(evt);
            }
        });
        this.NavigationControl.add(this.LraControl);
        GroupLayout tabelBoarderPanelLayout = new GroupLayout(this.GenericSearchPanel);
        this.GenericSearchPanel.setLayout(tabelBoarderPanelLayout);
        tabelBoarderPanelLayout.setHorizontalGroup(tabelBoarderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tabelBoarderPanelLayout.createSequentialGroup().addGap(0, 0, 0).addGroup(tabelBoarderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.SearchItem, -1, -1, Short.MAX_VALUE).addComponent(this.NavigationControl, -1, -1, Short.MAX_VALUE)).addGap(0, 0, 0)));
        tabelBoarderPanelLayout.setVerticalGroup(tabelBoarderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tabelBoarderPanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.SearchItem, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0).addComponent(this.NavigationControl, -2, -1, -2).addGap(0, 0, 0)));
        GroupLayout rootPanelLayout = new GroupLayout(this.GenericSearchController);
        this.GenericSearchController.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rootPanelLayout.createSequentialGroup().addContainerGap().addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.TabHeaderPanel, -1, -1, Short.MAX_VALUE).addComponent(this.GenericSearchPanel, -1, -1, Short.MAX_VALUE)).addContainerGap()));
        rootPanelLayout.setVerticalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rootPanelLayout.createSequentialGroup().addContainerGap().addComponent(this.TabHeaderPanel, -2, -1, -2).addGap(18, 18, 18).addComponent(this.GenericSearchPanel, -1, -1, Short.MAX_VALUE).addContainerGap()));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.GenericSearchController, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.GenericSearchController, -1, -1, Short.MAX_VALUE));
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
            this.SearchItemPanel.INavigationControlListener(true);
            this.INavigationControlListener.INavigationControlListener();
        }
    }

    public void INavigationControlListener(GenericTableView<DetailedUserModel.LraControl> tableView) {
        this.SearchItem.setLayout(new BorderLayout());
        this.SearchItem.add(tableView, "Center");
        this.SearchItemPanel = tableView;
    }

    public void INavigationControlListener(UserSearchViewController controller) {
        this.INavigationControlListener = controller;
    }
}

