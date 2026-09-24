/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.INavigationListener;
import 83nnfii93jksoiow9.BaseViewController;
import 83nnfii93jksoiow9.IViewLoader;
import java.util.ArrayList;
import java.util.LinkedList;

public class ViewManager
implements IViewLoader {
    private final ArrayList<INavigationListener> INavigationControlListener = new ArrayList(0);
    private final LinkedList<BaseViewController<?>> LraControl = new LinkedList();
    private BaseViewController<?> NavigationControl;

    public boolean hasPreviousView() {
        return !this.LraControl.isEmpty() && this.NavigationControl != this.LraControl.getFirst();
    }

    public boolean hasNextView() {
        return !this.LraControl.isEmpty() && this.NavigationControl != this.LraControl.getLast();
    }

    public int getCurrentIndex() {
        if (!this.LraControl.isEmpty() || this.NavigationControl != null) {
            return this.LraControl.indexOf(this.NavigationControl);
        }
        return -1;
    }

    @Override
    public void navigateBack() {
        if (this.hasPreviousView()) {
            this.LraControl.removeLast();
            this.reloadView(this.LraControl.getLast());
        }
    }

    public void navigateForwarde() {
        if (this.hasNextView()) {
            this.reloadView(this.LraControl.get(this.getCurrentIndex() + 1));
        }
    }

    public void navigateFirstView() {
        if (this.LraControl.size() > 0) {
            BaseViewController<?> view = this.LraControl.getFirst();
            this.LraControl.clear();
            this.loadView(view);
        }
    }

    public boolean addNavigationListener(INavigationListener listener) {
        if (!this.INavigationControlListener.contains(listener)) {
            return this.INavigationControlListener.add(listener);
        }
        return false;
    }

    public boolean removeNavigationListener(INavigationListener listener) {
        if (listener != null) {
            return this.INavigationControlListener.remove(listener);
        }
        return false;
    }

    @Override
    public <T extends BaseViewController<?>> void loadView(T viewController) {
        this.reloadView(viewController);
        if (this.LraControl.isEmpty() || this.LraControl.getLast() != viewController) {
            this.LraControl.add(this.NavigationControl);
        }
    }

    public <T extends BaseViewController<?>> void reloadView(T viewController) {
        if (this.NavigationControl != null) {
            this.NavigationControl.INavigationControlListener((IViewLoader)null);
        }
        this.NavigationControl = viewController;
        this.NavigationControl.INavigationControlListener(this);
        this.INavigationControlListener(this.NavigationControl);
    }

    @Override
    public void loadContent() {
        this.NavigationControl.INavigationControlListener();
    }

    private <T extends BaseViewController<?>> void INavigationControlListener(T viewController) {
        for (INavigationListener listener : this.INavigationControlListener) {
            listener.viewChanged(viewController);
        }
    }
}

