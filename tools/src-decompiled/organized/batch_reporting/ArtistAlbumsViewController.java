/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.DetailedAlbumModel;
import 83nnfii93jksoiow9.DetailedArtistModel;
import 83nnfii93jksoiow9.ArtistModel;
import 83nnfii93jksoiow9.ITableItemClickedListener;
import 83nnfii93jksoiow9.BaseViewController;
import 83nnfii93jksoiow9.ResourceCache;
import 83nnfii93jksoiow9.GenericTableView;
import 83nnfii93jksoiow9.SingleExecutor;
import 83nnfii93jksoiow9.ArtistAlbumsView;
import 83nnfii93jksoiow9.ResourceLoader;
import 83nnfii93jksoiow9.IExecutorStateChangedListener;
import 83nnfii93jksoiow9.ResponseModel;
import 83nnfii93jksoiow9.AlbumModel;
import 83nnfii93jksoiow9.DetailedAlbumViewController;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArtistAlbumsViewController
extends BaseViewController<ArtistAlbumsView> {
    private static final int INavigationControlListener = 20;
    private static final String LraControl = "artists/%s/albums/";
    private final ArtistModel GenericSearchController;
    private final GenericTableView<DetailedAlbumModel.NavigationControl> GenericSearchField;
    private final SingleExecutor GenericSearchPanel;
    private final NavigationControl SearchItem;

    public ArtistAlbumsViewController(DetailedArtistModel.INavigationControlListener detailedArtistModel) {
        this.GenericSearchController = detailedArtistModel.INavigationControlListener();
        this.GenericSearchField = new GenericTableView<DetailedAlbumModel.NavigationControl>(DetailedAlbumModel.NavigationControl.class);
        this.GenericSearchField.INavigationControlListener(new INavigationControlListener(this));
        this.GenericSearchField.INavigationControlListener(new LraControl());
        this.GenericSearchPanel = SingleExecutor.INavigationControlListener();
        this.SearchItem = new NavigationControl(this.GenericSearchField);
        this.GenericSearchPanel.INavigationControlListener(this.SearchItem);
        this.LraControl(new ArtistAlbumsView());
        ((ArtistAlbumsView)this.GenericSearchController()).INavigationControlListener(this.GenericSearchField);
        ((ArtistAlbumsView)this.GenericSearchController()).INavigationControlListener(this);
        ((ArtistAlbumsView)this.GenericSearchController()).INavigationControlListener(this.GenericSearchController.INavigationControlListener());
        ((ArtistAlbumsView)this.GenericSearchController()).INavigationControlListener(detailedArtistModel.GenericSearchField());
        this.SearchItem.INavigationControlListener(IExecutorStateChangedListener.INavigationControlListener.LraControl);
    }

    @Override
    public void INavigationControlListener() {
        this.INavigationControlListener("");
    }

    private void INavigationControlListener(String filter) {
        final ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
        resourceLoader.INavigationControlListener(String.format(LraControl, this.GenericSearchController.GenericTableView()));
        try {
            ResponseModel<AlbumModel> responseModel = resourceLoader.INavigationControlListener(AlbumModel.class, filter, "-id", 0, 20);
            resourceLoader.INavigationControlListener((String)null);
            for (final AlbumModel albumModel : responseModel.INavigationControlListener()) {
                this.GenericSearchPanel.INavigationControlListener(new Thread(new Runnable(){

                    @Override
                    public void run() {
                        resourceLoader.INavigationControlListener(albumModel);
                        DetailedAlbumModel.NavigationControl detailedAlbumModel = new DetailedAlbumModel.NavigationControl(albumModel, true);
                        ArtistAlbumsViewController.this.GenericSearchField.INavigationControlListener(detailedAlbumModel);
                    }
                }));
            }
        }
        catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(ArtistAlbumsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void LraControl() {
        this.INavigationControlListener("id < " + this.SearchItem());
        this.INavigationControlListener("id > " + this.NavigationControl());
    }

    private int NavigationControl() {
        int id = 0;
        for (DetailedAlbumModel.NavigationControl row : this.GenericSearchField.LraControl()) {
            if (row.INavigationControlListener().GenericTableView() <= id) continue;
            id = row.INavigationControlListener().GenericTableView();
        }
        return id;
    }

    private int SearchItem() {
        int id = Integer.MAX_VALUE;
        for (DetailedAlbumModel.NavigationControl row : this.GenericSearchField.LraControl()) {
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

    private static class INavigationControlListener
    implements ITableItemClickedListener<DetailedAlbumModel.NavigationControl> {
        private final ArtistAlbumsViewController INavigationControlListener;

        private INavigationControlListener(ArtistAlbumsViewController controller) {
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

