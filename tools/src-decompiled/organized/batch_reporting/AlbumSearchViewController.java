/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.DetailedAlbumModel;
import 83nnfii93jksoiow9.ITableItemClickedListener;
import 83nnfii93jksoiow9.ResourceCache;
import 83nnfii93jksoiow9.GenericTableView;
import 83nnfii93jksoiow9.SingleExecutor;
import 83nnfii93jksoiow9.AlbumSearchView;
import 83nnfii93jksoiow9.ResourceLoader;
import 83nnfii93jksoiow9.IExecutorStateChangedListener;
import 83nnfii93jksoiow9.ResponseModel;
import 83nnfii93jksoiow9.BaseSearchViewController;
import 83nnfii93jksoiow9.AlbumModel;
import 83nnfii93jksoiow9.DetailedAlbumViewController;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlbumSearchViewController
extends BaseSearchViewController<AlbumSearchView> {
    private static final int GenericSearchController = 20;
    private final GenericTableView<DetailedAlbumModel.NavigationControl> GenericSearchField = new GenericTableView<DetailedAlbumModel.NavigationControl>(DetailedAlbumModel.NavigationControl.class);
    private final SingleExecutor GenericSearchPanel;
    private final LraControl SearchItem;

    public AlbumSearchViewController() {
        this.GenericSearchField.INavigationControlListener(new NavigationControl(this));
        this.GenericSearchField.INavigationControlListener(new INavigationControlListener());
        this.GenericSearchPanel = SingleExecutor.INavigationControlListener();
        this.SearchItem = new LraControl(this.GenericSearchField);
        this.GenericSearchPanel.INavigationControlListener(this.SearchItem);
        this.LraControl(new AlbumSearchView());
        ((AlbumSearchView)this.GenericSearchController()).INavigationControlListener(this.GenericSearchField);
        ((AlbumSearchView)this.GenericSearchController()).INavigationControlListener(this);
        this.SearchItem.INavigationControlListener(IExecutorStateChangedListener.INavigationControlListener.LraControl);
    }

    @Override
    public void INavigationControlListener(String value) {
        ((AlbumSearchView)this.GenericSearchController()).INavigationControlListener(value);
    }

    @Override
    public void INavigationControlListener() {
        this.GenericSearchField.NavigationControl();
        this.LraControl("");
    }

    @Override
    public String LraControl() {
        return "Search: " + ((AlbumSearchView)this.GenericSearchController()).LraControl();
    }

    @Override
    public void LraControl(String filter) {
        AlbumSearchView view = (AlbumSearchView)this.GenericSearchController();
        AlbumModel albumFilterModel = new AlbumModel();
        albumFilterModel.INavigationControlListener("%" + view.LraControl() + "%");
        if (view.GenericSearchField()) {
            albumFilterModel.LraControl(view.GenericSearchPanel());
        }
        if (view.NavigationControl()) {
            albumFilterModel.INavigationControlListener((Integer)view.GenericSearchController());
        }
        try {
            final ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
            HashMap<String, String> createFilter = ResourceLoader.LraControl(albumFilterModel);
            String filterString = this.INavigationControlListener(createFilter, "AND", "LIKE");
            if (!filter.trim().isEmpty()) {
                filterString = filter + " AND " + filterString;
            }
            ResponseModel<AlbumModel> responseModel = resourceLoader.INavigationControlListener(AlbumModel.class, filterString, "-id", 0, 20);
            for (final AlbumModel albumModel : responseModel.INavigationControlListener()) {
                this.GenericSearchPanel.INavigationControlListener(new Thread(new Runnable(){

                    @Override
                    public void run() {
                        resourceLoader.INavigationControlListener(albumModel);
                        DetailedAlbumModel.LraControl minimumDetailedAlbumModel = new DetailedAlbumModel.LraControl(albumModel, true);
                        minimumDetailedAlbumModel.GenericSearchPanel();
                        AlbumSearchViewController.this.GenericSearchField.INavigationControlListener(minimumDetailedAlbumModel);
                    }
                }));
            }
        }
        catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(GenericTableView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void NavigationControl() {
        this.LraControl("id < " + this.SearchItemPanel());
        this.LraControl("id > " + this.SearchItem());
    }

    private int SearchItem() {
        int id = 0;
        for (DetailedAlbumModel.NavigationControl row : this.GenericSearchField.LraControl()) {
            if (row.INavigationControlListener().GenericTableView() <= id) continue;
            id = row.INavigationControlListener().GenericTableView();
        }
        return id;
    }

    private int SearchItemPanel() {
        int id = Integer.MAX_VALUE;
        for (DetailedAlbumModel.NavigationControl row : this.GenericSearchField.LraControl()) {
            if (row.INavigationControlListener().GenericTableView() >= id) continue;
            id = row.INavigationControlListener().GenericTableView();
        }
        return id;
    }

    private static class LraControl
    implements IExecutorStateChangedListener {
        private final GenericTableView<?> INavigationControlListener;

        private LraControl(GenericTableView<?> tableView) {
            this.INavigationControlListener = tableView;
        }

        @Override
        public void INavigationControlListener(IExecutorStateChangedListener.INavigationControlListener state) {
            switch (state) {
                case INavigationControlListener: {
                    this.INavigationControlListener.INavigationControlListener("Loading...");
                    this.INavigationControlListener.INavigationControlListener(true);
                    break;
                }
                default: {
                    this.INavigationControlListener.INavigationControlListener("No Results");
                    this.INavigationControlListener.INavigationControlListener(this.INavigationControlListener.INavigationControlListener() <= 0);
                }
            }
        }
    }

    private static class INavigationControlListener
    implements Comparator<DetailedAlbumModel.NavigationControl> {
        private INavigationControlListener() {
        }

        public int INavigationControlListener(DetailedAlbumModel.NavigationControl o1, DetailedAlbumModel.NavigationControl o2) {
            return Integer.compare(o2.INavigationControlListener().GenericTableView(), o1.INavigationControlListener().GenericTableView());
        }

        @Override
        public /* synthetic */ int compare(Object x0, Object x1) {
            return this.INavigationControlListener((DetailedAlbumModel.NavigationControl)x0, (DetailedAlbumModel.NavigationControl)x1);
        }
    }

    private static class NavigationControl
    implements ITableItemClickedListener<DetailedAlbumModel.NavigationControl> {
        private final AlbumSearchViewController INavigationControlListener;

        private NavigationControl(AlbumSearchViewController controller) {
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

