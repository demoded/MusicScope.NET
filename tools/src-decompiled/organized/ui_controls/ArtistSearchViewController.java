/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.DetailedArtistModel;
import 83nnfii93jksoiow9.ArtistModel;
import 83nnfii93jksoiow9.ITableItemClickedListener;
import 83nnfii93jksoiow9.ResourceCache;
import 83nnfii93jksoiow9.GenericTableView;
import 83nnfii93jksoiow9.SingleExecutor;
import 83nnfii93jksoiow9.ArtistSearchView;
import 83nnfii93jksoiow9.ResourceLoader;
import 83nnfii93jksoiow9.IExecutorStateChangedListener;
import 83nnfii93jksoiow9.ResponseModel;
import 83nnfii93jksoiow9.BaseSearchViewController;
import 83nnfii93jksoiow9.ArtistAlbumsViewController;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArtistSearchViewController
extends BaseSearchViewController<ArtistSearchView> {
    private static final int GenericSearchController = 20;
    private final GenericTableView<DetailedArtistModel.INavigationControlListener> GenericSearchField = new GenericTableView<DetailedArtistModel.INavigationControlListener>(DetailedArtistModel.INavigationControlListener.class);
    private final IExecutorStateChangedListener GenericSearchPanel;
    private final SingleExecutor SearchItem;

    public ArtistSearchViewController() {
        this.GenericSearchField.INavigationControlListener(new LraControl(this));
        this.GenericSearchField.INavigationControlListener(new INavigationControlListener());
        this.SearchItem = SingleExecutor.INavigationControlListener();
        this.GenericSearchPanel = new NavigationControl(this.GenericSearchField);
        this.SearchItem.INavigationControlListener(this.GenericSearchPanel);
        this.LraControl(new ArtistSearchView());
        ((ArtistSearchView)this.GenericSearchController()).INavigationControlListener(this.GenericSearchField);
        ((ArtistSearchView)this.GenericSearchController()).INavigationControlListener(this);
    }

    @Override
    public void INavigationControlListener() {
        this.GenericSearchField.NavigationControl();
        this.LraControl("");
    }

    @Override
    private void LraControl(String filter) {
        ArtistSearchView view = (ArtistSearchView)this.GenericSearchController();
        ArtistModel artistFilterModel = new ArtistModel();
        artistFilterModel.INavigationControlListener("%" + view.INavigationControlListener() + "%");
        HashMap<String, String> artistFilterMap = ResourceLoader.LraControl(artistFilterModel);
        String filterString = this.INavigationControlListener(artistFilterMap, "AND", "LIKE");
        if (!filter.trim().isEmpty()) {
            filterString = filter + " AND " + filterString;
        }
        ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
        try {
            ResponseModel<ArtistModel> responseModel = resourceLoader.INavigationControlListener(ArtistModel.class, filterString, "-id", 0, 20);
            for (final ArtistModel artistModel : responseModel.INavigationControlListener()) {
                this.SearchItem.INavigationControlListener(new Thread(new Runnable(){

                    @Override
                    public void run() {
                        DetailedArtistModel.INavigationControlListener detailedArtistModel = new DetailedArtistModel.INavigationControlListener(artistModel, true);
                        if (detailedArtistModel.GenericSearchField() > 0) {
                            ArtistSearchViewController.this.GenericSearchField.INavigationControlListener(detailedArtistModel);
                        }
                    }
                }));
            }
        }
        catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(ArtistSearchViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void INavigationControlListener(String value) {
        ((ArtistSearchView)this.GenericSearchController()).INavigationControlListener(value);
    }

    @Override
    public String LraControl() {
        return "Search: " + ((ArtistSearchView)this.GenericSearchController()).INavigationControlListener();
    }

    public void NavigationControl() {
        this.LraControl("id < " + this.SearchItemPanel());
        this.LraControl("id > " + this.SearchItem());
    }

    private int SearchItem() {
        int id = 0;
        for (DetailedArtistModel.INavigationControlListener row : this.GenericSearchField.LraControl()) {
            if (row.INavigationControlListener().GenericTableView() <= id) continue;
            id = row.INavigationControlListener().GenericTableView();
        }
        return id;
    }

    private int SearchItemPanel() {
        int id = Integer.MAX_VALUE;
        for (DetailedArtistModel.INavigationControlListener row : this.GenericSearchField.LraControl()) {
            if (row.INavigationControlListener().GenericTableView() >= id) continue;
            id = row.INavigationControlListener().GenericTableView();
        }
        return id;
    }

    private static class NavigationControl
    implements IExecutorStateChangedListener {
        private final GenericTableView<?> INavigationControlListener;

        private NavigationControl(GenericTableView<?> tableView) {
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
    implements Comparator<DetailedArtistModel.INavigationControlListener> {
        private INavigationControlListener() {
        }

        public int INavigationControlListener(DetailedArtistModel.INavigationControlListener o1, DetailedArtistModel.INavigationControlListener o2) {
            return Integer.compare(o2.INavigationControlListener().GenericTableView(), o1.INavigationControlListener().GenericTableView());
        }

        @Override
        public /* synthetic */ int compare(Object x0, Object x1) {
            return this.INavigationControlListener((DetailedArtistModel.INavigationControlListener)x0, (DetailedArtistModel.INavigationControlListener)x1);
        }
    }

    private static class LraControl
    implements ITableItemClickedListener<DetailedArtistModel.INavigationControlListener> {
        private final ArtistSearchViewController INavigationControlListener;

        public LraControl(ArtistSearchViewController controller) {
            this.INavigationControlListener = controller;
        }

        @Override
        public void INavigationControlListener(DetailedArtistModel.INavigationControlListener item, String columnName, Object value) {
            ArtistAlbumsViewController artistAlbumsViewController = new ArtistAlbumsViewController(item);
            this.INavigationControlListener.INavigationControlListener(artistAlbumsViewController);
            artistAlbumsViewController.INavigationControlListener();
        }
    }
}

