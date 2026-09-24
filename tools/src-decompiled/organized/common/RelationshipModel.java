/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.ForeignKey;
import java.lang.reflect.Field;

public class RelationshipModel {
    private ForeignKey INavigationControlListener;
    private Field LraControl;
    private int NavigationControl;

    public RelationshipModel(ForeignKey foreignKey, Field field, int id) {
        this.INavigationControlListener = foreignKey;
        this.LraControl = field;
        this.NavigationControl = id;
    }

    public ForeignKey INavigationControlListener() {
        return this.INavigationControlListener;
    }

    public void INavigationControlListener(ForeignKey foreignKey) {
        this.INavigationControlListener = foreignKey;
    }

    public Field LraControl() {
        return this.LraControl;
    }

    public void INavigationControlListener(Field field) {
        this.LraControl = field;
    }

    public int NavigationControl() {
        return this.NavigationControl;
    }
}

