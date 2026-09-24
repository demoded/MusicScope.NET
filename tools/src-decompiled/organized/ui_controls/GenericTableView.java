/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.AnnotatedTableView;
import 83nnfii93jksoiow9.ITableItemClickedListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.OverlayLayout;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class GenericTableView<T>
extends JPanel {
    private static final long serialVersionUID = 1L;
    private final ArrayList<ITableItemClickedListener<T>> INavigationControlListener;
    private AnnotatedTableView<T> LraControl;
    private JScrollPane NavigationControl;
    private JPanel GenericSearchController;
    private JPanel GenericSearchField;
    private JPanel GenericSearchPanel;
    private JLabel SearchItem;
    private JTable SearchItemPanel;

    public GenericTableView() {
        this.GenericSearchController();
        this.INavigationControlListener = new ArrayList(0);
    }

    public GenericTableView(Class<T> modelClass) {
        this();
        this.LraControl = new AnnotatedTableView<T>(modelClass);
        this.SearchItemPanel.setModel(this.LraControl);
        this.SearchItemPanel.addMouseListener(new INavigationControlListener());
        super.INavigationControlListener(this.SearchItemPanel);
    }

    public void INavigationControlListener(final T row) {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                GenericTableView.this.LraControl.INavigationControlListener(row);
            }
        });
    }

    public int INavigationControlListener() {
        return this.LraControl.getRowCount();
    }

    public List<T> LraControl() {
        return this.LraControl.LraControl();
    }

    public void NavigationControl() {
        this.LraControl.NavigationControl();
    }

    public void INavigationControlListener(Comparator<T> comparator) {
        this.LraControl.INavigationControlListener(comparator);
    }

    public boolean INavigationControlListener(ITableItemClickedListener<T> listener) {
        if (!this.INavigationControlListener.contains(listener)) {
            return this.INavigationControlListener.add(listener);
        }
        return false;
    }

    public boolean LraControl(ITableItemClickedListener<T> listener) {
        if (listener != null) {
            return this.INavigationControlListener.remove(listener);
        }
        return false;
    }

    public void INavigationControlListener(String text) {
        this.SearchItem.setText(text);
    }

    public void INavigationControlListener(Color color) {
        this.SearchItem.setBackground(color);
    }

    public void INavigationControlListener(boolean visible) {
        this.SearchItem.setVisible(visible);
    }

    private void INavigationControlListener(T item, String columnName, Object value) {
        for (ITableItemClickedListener<T> listener : this.INavigationControlListener) {
            listener.INavigationControlListener(item, columnName, value);
        }
    }

    private void INavigationControlListener(JTable table) {
        TableModel model = table.getModel();
        if (model instanceof AnnotatedTableView) {
            TableColumnModel columnModel = table.getColumnModel();
            AnnotatedTableView annotatedTableModel = (AnnotatedTableView)model;
            for (int i = 0; i < annotatedTableModel.getColumnCount(); ++i) {
                columnModel.getColumn(i).setPreferredWidth(annotatedTableModel.LraControl(i));
            }
        }
        table.updateUI();
    }

    private void GenericSearchController() {
        this.GenericSearchField = new JPanel();
        this.GenericSearchController = new JPanel();
        this.SearchItem = new JLabel();
        this.GenericSearchPanel = new JPanel();
        this.NavigationControl = new JScrollPane();
        this.SearchItemPanel = new JTable();
        this.GenericSearchField.setLayout(new OverlayLayout(this.GenericSearchField));
        this.GenericSearchController.setLayout(new BoxLayout(this.GenericSearchController, 2));
        this.SearchItem.setFont(new Font("Tahoma", 0, 18));
        this.SearchItem.setForeground(new Color(153, 153, 153));
        this.SearchItem.setText("Loading...");
        this.SearchItem.setOpaque(true);
        this.GenericSearchController.add(this.SearchItem);
        this.GenericSearchField.add(this.GenericSearchController);
        this.NavigationControl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.SearchItemPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.NavigationControl.setViewportView(this.SearchItemPanel);
        GroupLayout rootPanelLayout = new GroupLayout(this.GenericSearchPanel);
        this.GenericSearchPanel.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rootPanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.NavigationControl, -1, 359, Short.MAX_VALUE)));
        rootPanelLayout.setVerticalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.NavigationControl, -2, 0, Short.MAX_VALUE));
        this.GenericSearchField.add(this.GenericSearchPanel);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.GenericSearchField, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.GenericSearchField, -1, -1, Short.MAX_VALUE));
    }

    private class INavigationControlListener
    extends MouseAdapter {
        private INavigationControlListener() {
        }

        @Override
        public void mouseClicked(MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                int rowIndex = GenericTableView.this.SearchItemPanel.rowAtPoint(evt.getPoint());
                int columnIndex = GenericTableView.this.SearchItemPanel.columnAtPoint(evt.getPoint());
                if (rowIndex >= 0 && columnIndex >= 0) {
                    Object rowObject = GenericTableView.this.LraControl.INavigationControlListener(rowIndex);
                    String columnName = GenericTableView.this.LraControl.getColumnName(columnIndex);
                    Object value = GenericTableView.this.SearchItemPanel.getValueAt(rowIndex, columnIndex);
                    GenericTableView.this.INavigationControlListener(rowObject, columnName, value);
                }
            }
        }
    }
}

