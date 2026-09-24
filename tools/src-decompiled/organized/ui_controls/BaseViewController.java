/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.IViewLoader;
import javax.swing.JPanel;

public abstract class BaseViewController<T extends JPanel> {
    protected final int NavigationControl = 900;
    private T INavigationControlListener;
    private IViewLoader LraControl;

    public void INavigationControlListener(IViewLoader viewLoader) {
        this.LraControl = viewLoader;
    }

    public T GenericSearchController() {
        return this.INavigationControlListener;
    }

    public IViewLoader GenericSearchField() {
        return this.LraControl;
    }

    public void LraControl(T view) {
        this.INavigationControlListener = view;
    }

    protected void GenericSearchPanel() {
        if (this.LraControl != null) {
            this.LraControl.loadView(this);
        }
    }

    public void INavigationControlListener(BaseViewController<? extends JPanel> view) {
        if (this.LraControl != null) {
            this.LraControl.loadView(view);
        }
    }

    public abstract void INavigationControlListener();
}

