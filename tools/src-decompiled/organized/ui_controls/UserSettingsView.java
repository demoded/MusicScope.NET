/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.UserSettingsController;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class UserSettingsView
extends JPanel {
    private UserSettingsController INavigationControlListener;
    private Box.Filler LraControl;
    private JLabel NavigationControl;
    private JPanel GenericSearchController;
    private JPanel GenericSearchField;
    private JPanel GenericSearchPanel;
    private JLabel SearchItem;
    private JTextField SearchItemPanel;
    private JButton TabCloseListener;

    public UserSettingsView() {
        this.LraControl();
    }

    public void INavigationControlListener(UserSettingsController controller) {
        this.INavigationControlListener = controller;
    }

    public void INavigationControlListener(String name) {
        this.SearchItemPanel.setText(name);
    }

    public String INavigationControlListener() {
        return this.SearchItemPanel.getText();
    }

    private void LraControl() {
        this.GenericSearchController = new JPanel();
        this.GenericSearchField = new JPanel();
        this.SearchItem = new JLabel();
        this.LraControl = new Box.Filler(new Dimension(25, 0), new Dimension(25, 0), new Dimension(25, Short.MAX_VALUE));
        this.SearchItemPanel = new JTextField();
        this.GenericSearchPanel = new JPanel();
        this.NavigationControl = new JLabel();
        this.TabCloseListener = new JButton();
        this.GenericSearchField.setLayout(new BoxLayout(this.GenericSearchField, 2));
        this.SearchItem.setText("Name:");
        this.GenericSearchField.add(this.SearchItem);
        this.GenericSearchField.add(this.LraControl);
        this.GenericSearchField.add(this.SearchItemPanel);
        ResourceBundle bundle = ResourceBundle.getBundle("com/xivero/musicscopecloud/PropertiesBundle");
        this.NavigationControl.setText(bundle.getString("UserSettingsView.UserName.Title"));
        GroupLayout jPanel3Layout = new GroupLayout(this.GenericSearchPanel);
        this.GenericSearchPanel.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addComponent(this.NavigationControl).addGap(0, 140, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addComponent(this.NavigationControl).addGap(0, 0, 0)));
        this.TabCloseListener.setText("Okay");
        this.TabCloseListener.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                UserSettingsView.this.INavigationControlListener(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(this.GenericSearchController);
        this.GenericSearchController.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.GenericSearchField, -1, -1, Short.MAX_VALUE).addComponent(this.GenericSearchPanel, -1, -1, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.TabCloseListener, -2, 100, -2))).addContainerGap()));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.GenericSearchPanel, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.GenericSearchField, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE).addComponent(this.TabCloseListener).addContainerGap()));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.GenericSearchController, -1, -1, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.GenericSearchController, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0)));
    }

    private void INavigationControlListener(ActionEvent evt) {
        if (this.INavigationControlListener != null) {
            this.INavigationControlListener.LraControl();
        }
    }
}

