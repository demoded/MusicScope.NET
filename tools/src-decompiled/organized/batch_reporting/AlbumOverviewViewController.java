/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.DetailedAlbumModel;
import 83nnfii93jksoiow9.ILoadMoreActionListener;
import 83nnfii93jksoiow9.AlbumOverviewView;
import 83nnfii93jksoiow9.ITableItemClickedListener;
import 83nnfii93jksoiow9.BaseViewController;
import 83nnfii93jksoiow9.ResourceCache;
import 83nnfii93jksoiow9.GenericTableView;
import 83nnfii93jksoiow9.SingleExecutor;
import 83nnfii93jksoiow9.ResourceLoader;
import 83nnfii93jksoiow9.ResponseModel;
import 83nnfii93jksoiow9.AlbumModel;
import 83nnfii93jksoiow9.DetailedAlbumViewController;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlbumOverviewViewController
extends BaseViewController<AlbumOverviewView> {
    private final int INavigationControlListener = 30;
    private GenericTableView<DetailedAlbumModel.NavigationControl> LraControl = new GenericTableView<DetailedAlbumModel.NavigationControl>(DetailedAlbumModel.NavigationControl.class);

    public AlbumOverviewViewController() {
        this.LraControl.INavigationControlListener(new GenericSearchController(this));
        this.LraControl.INavigationControlListener(new LraControl());
        this.LraControl(new AlbumOverviewView());
        ((AlbumOverviewView)this.GenericSearchController()).INavigationControlListener(this.LraControl);
        ((AlbumOverviewView)this.GenericSearchController()).INavigationControlListener(new NavigationControl(this));
        ((AlbumOverviewView)this.GenericSearchController()).invalidate();
    }

    public int LraControl() {
        int id = 0;
        for (DetailedAlbumModel.NavigationControl row : this.LraControl.LraControl()) {
            if (row.INavigationControlListener().GenericTableView() <= id) continue;
            id = row.INavigationControlListener().GenericTableView();
        }
        return id;
    }

    public int NavigationControl() {
        int id = Integer.MAX_VALUE;
        for (DetailedAlbumModel.NavigationControl row : this.LraControl.LraControl()) {
            if (row.INavigationControlListener().GenericTableView() >= id) continue;
            id = row.INavigationControlListener().GenericTableView();
        }
        return id;
    }

    @Override
    public void INavigationControlListener() {
        this.INavigationControlListener("");
    }

    private void INavigationControlListener(String filter) {
        try {
            ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
            ResponseModel<AlbumModel> responseModel = resourceLoader.INavigationControlListener(AlbumModel.class, filter, "-id", 0, 30);
            CountDownLatch latch = new CountDownLatch(responseModel.INavigationControlListener().size());
            for (AlbumModel albumModel : responseModel.INavigationControlListener()) {
                SingleExecutor.INavigationControlListener().INavigationControlListener(new Thread(new INavigationControlListener(resourceLoader, albumModel, latch)));
            }
        }
        catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(GenericTableView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static class LraControl
    implements Comparator<DetailedAlbumModel.NavigationControl> {
        private LraControl() {
        }

        public int INavigationControlListener(DetailedAlbumModel.NavigationControl o1, DetailedAlbumModel.NavigationControl o2) {
            return Integer.compare(o2.INavigationControlListener().GenericTableView(), o1.INavigationControlListener().GenericTableView());
        }

        @Override
        public /* synthetic */ int compare(Object x0, Object x1) {
            return this.INavigationControlListener((DetailedAlbumModel.NavigationControl)x0, (DetailedAlbumModel.NavigationControl)x1);
        }
    }

    private class INavigationControlListener
    implements Runnable {
        private final ResourceLoader LraControl;
        private final AlbumModel NavigationControl;
        private final CountDownLatch GenericSearchController;

        private INavigationControlListener(ResourceLoader resourceLoader, AlbumModel albumModel, CountDownLatch latch) {
            this.LraControl = resourceLoader;
            this.NavigationControl = albumModel;
            this.GenericSearchController = latch;
        }

        @Override
        public void run() {
            this.LraControl.INavigationControlListener(this.NavigationControl);
            DetailedAlbumModel.LraControl minimumDetailedAlbumModel = new DetailedAlbumModel.LraControl(this.NavigationControl, true);
            AlbumOverviewViewController.this.LraControl.INavigationControlListener(minimumDetailedAlbumModel);
            this.GenericSearchController.countDown();
        }
    }

    private static class NavigationControl
    implements ILoadMoreActionListener {
        private final AlbumOverviewViewController INavigationControlListener;

        private NavigationControl(AlbumOverviewViewController controller) {
            this.INavigationControlListener = controller;
        }

        @Override
        public void INavigationControlListener() {
            this.INavigationControlListener.INavigationControlListener("id < " + this.INavigationControlListener.NavigationControl());
            this.INavigationControlListener.INavigationControlListener("id > " + this.INavigationControlListener.LraControl());
        }
    }

    private static class GenericSearchController
    implements ITableItemClickedListener<DetailedAlbumModel.NavigationControl> {
        private final AlbumOverviewViewController INavigationControlListener;

        private GenericSearchController(AlbumOverviewViewController controller) {
            this.INavigationControlListener = controller;
        }

        @Override
        public void INavigationControlListener(DetailedAlbumModel.NavigationControl item, String columnName, Object value) {
            DetailedAlbumViewController detailedAlbumViewController = new DetailedAlbumViewController(item);
            this.INavigationControlListener.INavigationControlListener(detailedAlbumViewController);
            detailedAlbumViewController.INavigationControlListener();
        }
    }
}

