/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.AbstractTableView;
import 83nnfii93jksoiow9.TableColumn;
import 83nnfii93jksoiow9.ColumnItem;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class AnnotatedTableView<T>
extends AbstractTableView<T> {
    public AnnotatedTableView(Class<T> modelClass) {
        this.INavigationControlListener = this.INavigationControlListener(modelClass);
    }

    public T INavigationControlListener(int index) {
        if (index < this.getRowCount()) {
            return this.LraControl().get(index);
        }
        return null;
    }

    @Override
    public synchronized Object getValueAt(int rowIndex, int columnIndex) {
        if (this.LraControl().size() > rowIndex && this.INavigationControlListener().size() > columnIndex) {
            Object model = this.LraControl().get(rowIndex);
            ColumnItem column = this.INavigationControlListener().get(columnIndex);
            try {
                Object value;
                if (!column.GenericSearchController().isAccessible()) {
                    column.GenericSearchController().setAccessible(true);
                }
                if ((value = column.GenericSearchController().invoke(model, new Object[0])) == null) {
                    return column.LraControl();
                }
                return value;
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                return column.LraControl();
            }
        }
        return null;
    }

    public synchronized int LraControl(int columnIndex) {
        if (this.INavigationControlListener().size() > columnIndex) {
            return this.INavigationControlListener().get(columnIndex).GenericSearchField();
        }
        return 0;
    }

    private synchronized ArrayList<ColumnItem> INavigationControlListener(Class<T> modelClass) {
        ArrayList<ColumnItem> tableColumns = new ArrayList<ColumnItem>(0);
        ArrayList<Method> methods = this.LraControl(modelClass);
        for (Method method : methods) {
            TableColumn tableColumn = method.getAnnotation(TableColumn.class);
            if (tableColumn == null) continue;
            tableColumns.add(new ColumnItem(tableColumn, method));
        }
        Collections.sort(tableColumns, new Comparator<ColumnItem>(){

            public int INavigationControlListener(ColumnItem column, ColumnItem compareColumn) {
                return Integer.compare(column.NavigationControl(), compareColumn.NavigationControl());
            }

            @Override
            public /* synthetic */ int compare(Object x0, Object x1) {
                return this.INavigationControlListener((ColumnItem)x0, (ColumnItem)x1);
            }
        });
        return tableColumns;
    }

    private synchronized ArrayList<Method> LraControl(Class<?> model) {
        return this.INavigationControlListener(model, new ArrayList<Method>(0));
    }

    private synchronized ArrayList<Method> INavigationControlListener(Class<?> model, ArrayList<Method> fields) {
        Class<?> superclass = model.getSuperclass();
        if (superclass != null) {
            this.INavigationControlListener(superclass, fields);
        }
        fields.addAll(Arrays.asList(model.getDeclaredMethods()));
        return fields;
    }
}

