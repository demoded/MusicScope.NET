/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.ColumnItem;
import 83nnfii93jksoiow9.SortedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public abstract class AbstractTableView<T>
extends AbstractTableModel {
    protected List<ColumnItem> INavigationControlListener;
    protected SortedList<T> LraControl;

    public AbstractTableView() {
        this(new ArrayList<ColumnItem>(0));
    }

    public AbstractTableView(List<ColumnItem> columns) {
        this.INavigationControlListener = columns;
        this.LraControl = new SortedList();
    }

    @Override
    public synchronized int getRowCount() {
        return this.LraControl().size();
    }

    @Override
    public synchronized int getColumnCount() {
        return this.INavigationControlListener().size();
    }

    public void INavigationControlListener(Comparator<T> comparator) {
        this.LraControl.INavigationControlListener(comparator);
    }

    public synchronized List<ColumnItem> INavigationControlListener() {
        return Collections.unmodifiableList(this.INavigationControlListener);
    }

    @Override
    public synchronized String getColumnName(int column) {
        return this.INavigationControlListener().get(column).INavigationControlListener();
    }

    public synchronized List<T> LraControl() {
        return Collections.unmodifiableList(this.LraControl);
    }

    public synchronized void INavigationControlListener(T[] rowList) {
        if (rowList != null && rowList.length > 0) {
            this.INavigationControlListener((T)Arrays.asList(rowList));
        }
    }

    public synchronized void INavigationControlListener(List<T> rowList) {
        if (rowList != null && rowList.size() > 0) {
            this.LraControl.addAll((Collection<T>)rowList);
            this.fireTableDataChanged();
        }
    }

    public synchronized void INavigationControlListener(T row) {
        if (row != null) {
            this.LraControl.add(row);
            this.fireTableDataChanged();
        }
    }

    public synchronized void INavigationControlListener(T row, int index) {
        if (row != null) {
            this.LraControl.add(index, row);
            this.fireTableDataChanged();
        }
    }

    public synchronized void NavigationControl() {
        this.LraControl.clear();
    }
}

