/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.BaseViewController;
import 83nnfii93jksoiow9.TabCloseListener;
import 83nnfii93jksoiow9.IViewLoader;
import 83nnfii93jksoiow9.TabbedSearchView;
import javax.swing.JPanel;

public class TabController
extends BaseViewController<TabbedSearchView> {
    private LraControl INavigationControlListener = new LraControl();

    public TabController() {
        this.LraControl(new TabbedSearchView());
    }

    public void INavigationControlListener(String title, BaseViewController<?> controller) {
        controller.INavigationControlListener(this.INavigationControlListener);
        Object view = controller.GenericSearchController();
        ((TabbedSearchView)this.GenericSearchController()).INavigationControlListener(title, (JPanel)view, new INavigationControlListener(this, (JPanel)view));
    }

    public void INavigationControlListener(JPanel view) {
        ((TabbedSearchView)this.GenericSearchController()).INavigationControlListener(view);
    }

    @Override
    public void INavigationControlListener() {
    }

    @Override
    public void INavigationControlListener(IViewLoader viewLoader) {
        super.INavigationControlListener(viewLoader);
        this.INavigationControlListener.INavigationControlListener(viewLoader);
    }

    private static class LraControl
    implements IViewLoader {
        private IViewLoader INavigationControlListener;

        private LraControl() {
        }

        public void INavigationControlListener(IViewLoader viewLoader) {
            this.INavigationControlListener = viewLoader;
        }

        @Override
        public <T extends BaseViewController<? extends JPanel>> void loadView(T viewController) {
            this.INavigationControlListener.loadView(viewController);
        }

        @Override
        public void loadContent() {
            this.INavigationControlListener.loadContent();
        }

        @Override
        public void navigateBack() {
            this.INavigationControlListener.navigateBack();
        }
    }

    private static class INavigationControlListener
    implements TabCloseListener {
        private final TabController INavigationControlListener;
        private final JPanel LraControl;

        private INavigationControlListener(TabController controller, JPanel view) {
            this.INavigationControlListener = controller;
            this.LraControl = view;
        }

        @Override
        public void INavigationControlListener() {
            this.INavigationControlListener.INavigationControlListener(this.LraControl);
        }
    }
}

