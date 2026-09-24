/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.DetailedTrackModel;
import 83nnfii93jksoiow9.DetailedTrackViewController;
import 83nnfii93jksoiow9.TrackModel;
import 83nnfii93jksoiow9.ArtistModel;
import 83nnfii93jksoiow9.ITableItemClickedListener;
import 83nnfii93jksoiow9.BaseModel;
import 83nnfii93jksoiow9.ResourceCache;
import 83nnfii93jksoiow9.GenericTableView;
import 83nnfii93jksoiow9.SingleExecutor;
import 83nnfii93jksoiow9.ResourceLoader;
import 83nnfii93jksoiow9.IExecutorStateChangedListener;
import 83nnfii93jksoiow9.ResponseModel;
import 83nnfii93jksoiow9.BaseSearchViewController;
import 83nnfii93jksoiow9.AlbumModel;
import 83nnfii93jksoiow9.TrackSearchView;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrackSearchViewCrontroller
extends BaseSearchViewController<TrackSearchView> {
    private static final String GenericSearchController = "artists/%s/tracks/";
    private static final int GenericSearchField = 20;
    private final SingleExecutor GenericSearchPanel;
    private final GenericTableView<DetailedTrackModel.INavigationControlListener> SearchItem = new GenericTableView<DetailedTrackModel.INavigationControlListener>(DetailedTrackModel.INavigationControlListener.class);
    private final LraControl SearchItemPanel;

    public TrackSearchViewCrontroller() {
        this.SearchItem.INavigationControlListener(new NavigationControl(this));
        this.SearchItem.INavigationControlListener(new INavigationControlListener());
        this.GenericSearchPanel = SingleExecutor.INavigationControlListener();
        this.SearchItemPanel = new LraControl(this.SearchItem);
        this.GenericSearchPanel.INavigationControlListener(this.SearchItemPanel);
        this.LraControl(new TrackSearchView());
        ((TrackSearchView)this.GenericSearchController()).INavigationControlListener(this.SearchItem);
        ((TrackSearchView)this.GenericSearchController()).INavigationControlListener(this);
        this.SearchItemPanel.INavigationControlListener(IExecutorStateChangedListener.INavigationControlListener.LraControl);
    }

    @Override
    public void INavigationControlListener(String value) {
        ((TrackSearchView)this.GenericSearchController()).INavigationControlListener(value);
    }

    @Override
    public void INavigationControlListener() {
        this.SearchItem.NavigationControl();
        this.LraControl("");
    }

    @Override
    public String LraControl() {
        return "Search: " + ((TrackSearchView)this.GenericSearchController()).INavigationControlListener();
    }

    @Override
    public void LraControl(String filter) {
        if (!((TrackSearchView)this.GenericSearchController()).NavigationControl().isEmpty()) {
            this.NavigationControl(filter);
            return;
        }
        final ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
        try {
            String appendFilter = this.INavigationControlListener("AND", filter, this.SearchItemPanel(), this.SearchItem());
            ResponseModel<TrackModel> responseModel = resourceLoader.INavigationControlListener(TrackModel.class, appendFilter, "-id", 0, 20);
            for (final TrackModel trackModel : responseModel.INavigationControlListener()) {
                this.GenericSearchPanel.INavigationControlListener(new Thread(new Runnable(){

                    @Override
                    public void run() {
                        resourceLoader.INavigationControlListener(trackModel);
                        DetailedTrackModel.INavigationControlListener minimumDetailedAlbumModel = new DetailedTrackModel.INavigationControlListener(trackModel, true);
                        minimumDetailedAlbumModel.GenericSearchPanel();
                        TrackSearchViewCrontroller.this.SearchItem.INavigationControlListener(minimumDetailedAlbumModel);
                    }
                }));
            }
        }
        catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(GenericTableView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void NavigationControl(String filter) {
        List<ArtistModel> artistLike = this.GenericSearchController(((TrackSearchView)this.GenericSearchController()).NavigationControl());
        for (ArtistModel artistModel : artistLike) {
            this.INavigationControlListener(artistModel, filter);
        }
    }

    private void INavigationControlListener(ArtistModel artistModel, String filter) {
        final ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
        resourceLoader.INavigationControlListener(String.format(GenericSearchController, artistModel.GenericTableView()));
        try {
            String appendFilter = this.INavigationControlListener("AND", filter, this.SearchItemPanel(), this.SearchItem());
            ResponseModel<TrackModel> responseModel = resourceLoader.INavigationControlListener(TrackModel.class, appendFilter, "-id", 0, 20);
            for (final TrackModel trackModel : responseModel.INavigationControlListener()) {
                this.GenericSearchPanel.INavigationControlListener(new Thread(new Runnable(){

                    @Override
                    public void run() {
                        resourceLoader.INavigationControlListener(trackModel);
                        DetailedTrackModel.INavigationControlListener minimumDetailedAlbumModel = new DetailedTrackModel.INavigationControlListener(trackModel, true);
                        minimumDetailedAlbumModel.GenericSearchPanel();
                        TrackSearchViewCrontroller.this.SearchItem.INavigationControlListener(minimumDetailedAlbumModel);
                    }
                }));
            }
        }
        catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(GenericTableView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<ArtistModel> GenericSearchController(String name) {
        try {
            ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
            ArtistModel artistFilterModel = new ArtistModel();
            artistFilterModel.INavigationControlListener("%" + name + "%");
            HashMap<String, String> createFilter = ResourceLoader.LraControl(artistFilterModel);
            String filterString = this.INavigationControlListener(createFilter, "AND", "LIKE");
            ResponseModel<ArtistModel> responseModel = resourceLoader.INavigationControlListener(ArtistModel.class, filterString, "", 0, 20);
            return responseModel.INavigationControlListener();
        }
        catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TrackSearchViewCrontroller.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<ArtistModel>(0);
        }
    }

    private List<AlbumModel> GenericSearchField(String name) {
        try {
            ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
            AlbumModel albumFilterModel = new AlbumModel();
            albumFilterModel.INavigationControlListener("%" + name + "%");
            HashMap<String, String> createFilter = ResourceLoader.LraControl(albumFilterModel);
            String filterString = this.INavigationControlListener(createFilter, "AND", "LIKE");
            ResponseModel<AlbumModel> responseModel = resourceLoader.INavigationControlListener(AlbumModel.class, filterString, "", 0, 20);
            return responseModel.INavigationControlListener();
        }
        catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(TrackSearchViewCrontroller.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<AlbumModel>(0);
        }
    }

    private String SearchItem() {
        StringBuilder filterBuilder = new StringBuilder(0);
        if (!((TrackSearchView)this.GenericSearchController()).LraControl().isEmpty()) {
            List<AlbumModel> albumLike = this.GenericSearchField(((TrackSearchView)this.GenericSearchController()).LraControl());
            if (albumLike.isEmpty()) {
                return "";
            }
            filterBuilder.append("(");
            Iterator<AlbumModel> it = albumLike.iterator();
            while (it.hasNext()) {
                AlbumModel model = it.next();
                filterBuilder.append("albumId = ").append(model.GenericTableView());
                if (!it.hasNext()) continue;
                filterBuilder.append(" OR ");
            }
            filterBuilder.append(")");
        }
        return filterBuilder.toString();
    }

    private String SearchItemPanel() {
        TrackModel trackFilterModel = new TrackModel();
        trackFilterModel.INavigationControlListener("%" + ((TrackSearchView)this.GenericSearchController()).INavigationControlListener() + "%");
        HashMap<String, String> filterMap = ResourceLoader.LraControl(trackFilterModel);
        return this.INavigationControlListener(filterMap, "AND", "LIKE");
    }

    private <T extends BaseModel> String INavigationControlListener(ResourceLoader loader, List<T> list) {
        StringBuilder builder = new StringBuilder(0);
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            BaseModel model = (BaseModel)it.next();
            builder.append(this.INavigationControlListener(ResourceLoader.LraControl(model), "AND", "LIKE"));
            if (!it.hasNext()) continue;
            builder.append(" OR ");
        }
        return builder.toString();
    }

    private String INavigationControlListener(String link, String ... strings) {
        StringBuilder builder = new StringBuilder(0);
        for (String string : Arrays.asList(strings)) {
            if (string.isEmpty()) continue;
            if (builder.length() > 0) {
                builder.append(" ").append(link).append(" ");
            }
            builder.append(string);
        }
        return builder.toString();
    }

    public void NavigationControl() {
        this.LraControl("id < " + this.TabController());
        this.LraControl("id > " + this.TabCloseListener());
    }

    private int TabCloseListener() {
        int id = 0;
        for (DetailedTrackModel.INavigationControlListener row : this.SearchItem.LraControl()) {
            if (row.GenericSearchController().GenericTableView() <= id) continue;
            id = row.GenericSearchController().GenericTableView();
        }
        return id;
    }

    private int TabController() {
        int id = Integer.MAX_VALUE;
        for (DetailedTrackModel.INavigationControlListener row : this.SearchItem.LraControl()) {
            if (row.GenericSearchController().GenericTableView() >= id) continue;
            id = row.GenericSearchController().GenericTableView();
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
    implements Comparator<DetailedTrackModel.INavigationControlListener> {
        private INavigationControlListener() {
        }

        public int INavigationControlListener(DetailedTrackModel.INavigationControlListener o1, DetailedTrackModel.INavigationControlListener o2) {
            return Integer.compare(o2.GenericSearchController().GenericTableView(), o1.GenericSearchController().GenericTableView());
        }

        @Override
        public /* synthetic */ int compare(Object x0, Object x1) {
            return this.INavigationControlListener((DetailedTrackModel.INavigationControlListener)x0, (DetailedTrackModel.INavigationControlListener)x1);
        }
    }

    private static class NavigationControl
    implements ITableItemClickedListener<DetailedTrackModel.INavigationControlListener> {
        private final TrackSearchViewCrontroller INavigationControlListener;

        private NavigationControl(TrackSearchViewCrontroller controller) {
            this.INavigationControlListener = controller;
        }

        @Override
        public void INavigationControlListener(DetailedTrackModel.INavigationControlListener item, String columnName, Object value) {
            DetailedTrackViewController detailedTrackViewController = new DetailedTrackViewController(item);
            this.INavigationControlListener.INavigationControlListener(detailedTrackViewController);
            detailedTrackViewController.INavigationControlListener();
        }
    }
}

