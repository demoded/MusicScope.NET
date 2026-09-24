/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.BaseModel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NavigationControl
extends JPanel {
    private 83nnfii93jksoiow9.INavigationControlListener INavigationControlListener;
    private DefaultComboBoxModel<INavigationControlListener> LraControl = new DefaultComboBoxModel();
    private JButton NavigationControl;
    private Box.Filler GenericSearchController;
    private Box.Filler GenericSearchField;
    private Box.Filler GenericSearchPanel;
    private Box.Filler SearchItem;
    private Box.Filler SearchItemPanel;
    private JButton TabCloseListener;
    private JPanel TabController;
    private JPanel TabHeaderPanel;
    private JButton TabbedSearchView;
    private JComboBox<INavigationControlListener> AbstractTableView;
    private JTextField AnnotatedTableView;
    private JButton ColumnItem;

    public NavigationControl() {
        this.INavigationControlListener();
        this.AbstractTableView.setModel(this.LraControl);
    }

    public void INavigationControlListener(83nnfii93jksoiow9.INavigationControlListener listener) {
        this.INavigationControlListener = listener;
    }

    public void INavigationControlListener(String name, Class<? extends BaseModel> modelClass) {
        this.LraControl.addElement(new INavigationControlListener(modelClass, name));
    }

    public void INavigationControlListener(boolean enabled) {
        this.NavigationControl.setEnabled(enabled);
    }

    private void INavigationControlListener() {
        this.TabHeaderPanel = new JPanel();
        this.TabController = new JPanel();
        this.NavigationControl = new JButton();
        this.SearchItem = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, Short.MAX_VALUE));
        this.TabCloseListener = new JButton();
        this.SearchItemPanel = new Box.Filler(new Dimension(50, 0), new Dimension(0, 0), new Dimension(500, 0));
        this.AnnotatedTableView = new JTextField();
        this.GenericSearchController = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, Short.MAX_VALUE));
        this.AbstractTableView = new JComboBox();
        this.GenericSearchField = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, Short.MAX_VALUE));
        this.TabbedSearchView = new JButton();
        this.GenericSearchPanel = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, Short.MAX_VALUE));
        this.ColumnItem = new JButton();
        this.TabController.setLayout(new BoxLayout(this.TabController, 2));
        this.NavigationControl.setText("Back");
        this.NavigationControl.setFocusable(false);
        this.NavigationControl.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                NavigationControl.this.LraControl(evt);
            }
        });
        this.TabController.add(this.NavigationControl);
        this.TabController.add(this.SearchItem);
        this.TabCloseListener.setText("Home");
        this.TabCloseListener.setFocusable(false);
        this.TabCloseListener.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                NavigationControl.this.GenericSearchController(evt);
            }
        });
        this.TabController.add(this.TabCloseListener);
        this.TabController.add(this.SearchItemPanel);
        this.AnnotatedTableView.setMaximumSize(new Dimension(300, 21));
        this.AnnotatedTableView.setMinimumSize(new Dimension(100, 20));
        this.AnnotatedTableView.setPreferredSize(new Dimension(100, 20));
        this.AnnotatedTableView.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                NavigationControl.this.NavigationControl(evt);
            }
        });
        this.TabController.add(this.AnnotatedTableView);
        this.TabController.add(this.GenericSearchController);
        this.AbstractTableView.setFocusable(false);
        this.AbstractTableView.setMaximumSize(new Dimension(100, 21));
        this.AbstractTableView.setMinimumSize(new Dimension(100, 20));
        this.AbstractTableView.setPreferredSize(new Dimension(100, 20));
        this.TabController.add(this.AbstractTableView);
        this.TabController.add(this.GenericSearchField);
        this.TabbedSearchView.setText("New Search");
        this.TabbedSearchView.setFocusable(false);
        this.TabbedSearchView.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                NavigationControl.this.INavigationControlListener(evt);
            }
        });
        this.TabController.add(this.TabbedSearchView);
        this.TabController.add(this.GenericSearchPanel);
        this.ColumnItem.setIcon(new ImageIcon(this.getClass().getResource("/com/xivero/musicscopecloud/icon/settings-16.png")));
        this.ColumnItem.setFocusable(false);
        this.ColumnItem.setMaximumSize(new Dimension(49, 23));
        this.ColumnItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                NavigationControl.this.GenericSearchField(evt);
            }
        });
        this.TabController.add(this.ColumnItem);
        GroupLayout rootLayout = new GroupLayout(this.TabHeaderPanel);
        this.TabHeaderPanel.setLayout(rootLayout);
        rootLayout.setHorizontalGroup(rootLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.TabController, GroupLayout.Alignment.TRAILING, -1, 701, Short.MAX_VALUE));
        rootLayout.setVerticalGroup(rootLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rootLayout.createSequentialGroup().addComponent(this.TabController, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0)));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.TabHeaderPanel, -1, -1, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.TabHeaderPanel, -1, -1, Short.MAX_VALUE));
    }

    private void INavigationControlListener(ActionEvent evt) {
        if (this.INavigationControlListener != null) {
            INavigationControlListener searchType = (INavigationControlListener)this.AbstractTableView.getSelectedItem();
            this.INavigationControlListener.searchNavigationAction(this.AnnotatedTableView.getText(), searchType.INavigationControlListener());
            this.AnnotatedTableView.setText("");
        }
    }

    private void LraControl(ActionEvent evt) {
        if (this.INavigationControlListener != null) {
            this.INavigationControlListener.backNavigationAction();
        }
    }

    private void NavigationControl(ActionEvent evt) {
        if (this.INavigationControlListener != null && !this.AnnotatedTableView.getText().isEmpty()) {
            INavigationControlListener searchType = (INavigationControlListener)this.AbstractTableView.getSelectedItem();
            this.INavigationControlListener.searchNavigationAction(this.AnnotatedTableView.getText(), searchType.INavigationControlListener());
            this.AnnotatedTableView.setText("");
        }
    }

    private void GenericSearchController(ActionEvent evt) {
        if (this.INavigationControlListener != null) {
            this.INavigationControlListener.homeNavigationAction();
        }
    }

    private void GenericSearchField(ActionEvent evt) {
        if (this.INavigationControlListener != null) {
            this.INavigationControlListener.userSettingsAction();
        }
    }

    public class INavigationControlListener {
        private Class<? extends BaseModel> LraControl;
        private String NavigationControl;

        public INavigationControlListener(Class<? extends BaseModel> modelClass, String name) {
            this.LraControl = modelClass;
            this.NavigationControl = name;
        }

        public Class<? extends BaseModel> INavigationControlListener() {
            return this.LraControl;
        }

        public String LraControl() {
            return this.NavigationControl;
        }

        public String toString() {
            return this.NavigationControl;
        }
    }
}

