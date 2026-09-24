/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.TableColumn;
import java.lang.reflect.Method;

public class ColumnItem {
    private String INavigationControlListener;
    private String LraControl;
    private Method NavigationControl;
    private int GenericSearchController;
    private int GenericSearchField;

    public ColumnItem() {
    }

    public ColumnItem(TableColumn tableColumn, Method method) {
        this(tableColumn.INavigationControlListener(), tableColumn.GenericSearchController(), tableColumn.LraControl(), tableColumn.NavigationControl(), method);
    }

    public ColumnItem(String name, String defaultValue, int position, int width, Method method) {
        this.INavigationControlListener = name;
        this.LraControl = defaultValue;
        this.GenericSearchField = width;
        this.GenericSearchController = position;
        this.NavigationControl = method;
    }

    public String INavigationControlListener() {
        return this.INavigationControlListener;
    }

    public void INavigationControlListener(String name) {
        this.INavigationControlListener = name;
    }

    public String LraControl() {
        return this.LraControl;
    }

    public void LraControl(String defaultValue) {
        this.LraControl = defaultValue;
    }

    public int NavigationControl() {
        return this.GenericSearchController;
    }

    public void INavigationControlListener(int position) {
        this.GenericSearchController = position;
    }

    public Method GenericSearchController() {
        return this.NavigationControl;
    }

    public void INavigationControlListener(Method method) {
        this.NavigationControl = method;
    }

    public int GenericSearchField() {
        return this.GenericSearchField;
    }

    public void LraControl(int width) {
        this.GenericSearchField = width;
    }
}

