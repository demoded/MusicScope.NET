/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.TrackModel;
import 83nnfii93jksoiow9.DetailedAlbumMesurementModel;
import 83nnfii93jksoiow9.ArtistModel;
import 83nnfii93jksoiow9.ResourceCache;
import 83nnfii93jksoiow9.ICorrespondingResourceLoader;
import 83nnfii93jksoiow9.AlbumMeasurementModel;
import 83nnfii93jksoiow9.ResourceLoader;
import 83nnfii93jksoiow9.TableColumn;
import 83nnfii93jksoiow9.ResponseModel;
import 83nnfii93jksoiow9.AlbumModel;
import 83nnfii93jksoiow9.SortedList;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DetailedAlbumModel
implements ICorrespondingResourceLoader {
    protected final String INavigationControlListener = "albums/%s/artists/";
    protected final String LraControl = "albums/%s/tracks/";
    protected final AlbumModel NavigationControl;
    protected SortedList<DetailedAlbumMesurementModel.INavigationControlListener> GenericSearchController;
    protected List<ArtistModel> GenericSearchField;
    protected List<TrackModel> GenericSearchPanel;

    public DetailedAlbumModel(int albumId, boolean loadCorrespondingResource) {
        this.NavigationControl = this.LraControl(albumId);
        this.GenericSearchController = new SortedList<DetailedAlbumMesurementModel.INavigationControlListener>(new INavigationControlListener());
        if (loadCorrespondingResource) {
            this.GenericSearchPanel();
        }
    }

    public DetailedAlbumModel(AlbumModel albumModel, boolean loadCorrespondingResource) {
        this.NavigationControl = albumModel;
        this.GenericSearchController = new SortedList<DetailedAlbumMesurementModel.INavigationControlListener>(new INavigationControlListener());
        if (loadCorrespondingResource) {
            this.GenericSearchPanel();
        }
    }

    public DetailedAlbumModel(DetailedAlbumModel detailedAlbumModel, boolean loadCorrespondingResource) {
        this(detailedAlbumModel.NavigationControl, loadCorrespondingResource);
        this.GenericSearchController = detailedAlbumModel.GenericSearchController;
        this.GenericSearchField = detailedAlbumModel.GenericSearchField;
        this.GenericSearchPanel = detailedAlbumModel.GenericSearchPanel;
    }

    public AlbumModel INavigationControlListener() {
        return this.NavigationControl;
    }

    public List<TrackModel> LraControl() {
        if (this.GenericSearchPanel == null || this.GenericSearchPanel.isEmpty()) {
            this.SearchItemPanel();
        }
        return Collections.unmodifiableList(this.GenericSearchPanel);
    }

    public int NavigationControl() {
        return this.GenericSearchController.get(0).LraControl();
    }

    public String GenericSearchController() {
        return this.GenericSearchController.get(0).GenericSearchField().LraControl();
    }

    protected String INavigationControlListener(int totalSecs) {
        int hours = totalSecs / 3600;
        int minutes = totalSecs % 3600 / 60;
        int seconds = totalSecs % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    protected AlbumModel LraControl(int id) {
        if (id > 0) {
            try {
                ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
                AlbumModel filterModel = new AlbumModel();
                filterModel.INavigationControlListener(id);
                HashMap<String, String> filter = ResourceLoader.LraControl(filterModel);
                ResponseModel<AlbumModel> responseModel = resourceLoader.INavigationControlListener(AlbumModel.class, filter, null, 0, 1);
                AlbumModel model = responseModel.INavigationControlListener().get(0);
                resourceLoader.INavigationControlListener(model);
                return model;
            }
            catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
                Logger.getLogger(DetailedAlbumModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    protected void GenericSearchField() {
        if (this.NavigationControl != null && this.GenericSearchController.isEmpty()) {
            try {
                ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
                AlbumMeasurementModel albumMeasurementModel = new AlbumMeasurementModel();
                albumMeasurementModel.INavigationControlListener((Integer)this.NavigationControl.GenericTableView());
                HashMap<String, String> filter = ResourceLoader.LraControl(albumMeasurementModel);
                ResponseModel<AlbumMeasurementModel> responseModel = resourceLoader.INavigationControlListener(AlbumMeasurementModel.class, filter, null, 0, 1);
                for (AlbumMeasurementModel measurementModel : responseModel.INavigationControlListener()) {
                    DetailedAlbumMesurementModel.INavigationControlListener model = new DetailedAlbumMesurementModel.INavigationControlListener(measurementModel);
                    model.GenericSearchPanel();
                    this.GenericSearchController.add(model);
                }
            }
            catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
                Logger.getLogger(DetailedAlbumModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    protected void SearchItem() {
        if (this.NavigationControl != null && this.GenericSearchField == null) {
            try {
                ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
                resourceLoader.INavigationControlListener(String.format("albums/%s/artists/", this.NavigationControl.GenericTableView()));
                ResponseModel<ArtistModel> responseModel = resourceLoader.INavigationControlListener(ArtistModel.class);
                this.GenericSearchField = responseModel.INavigationControlListener();
            }
            catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
                Logger.getLogger(DetailedAlbumModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    protected void SearchItemPanel() {
        if (this.NavigationControl != null && this.GenericSearchPanel == null) {
            try {
                ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
                resourceLoader.INavigationControlListener(String.format("albums/%s/tracks/", this.NavigationControl.GenericTableView()));
                ResponseModel<TrackModel> responseModel = resourceLoader.INavigationControlListener(TrackModel.class);
                resourceLoader.INavigationControlListener((String)null);
                resourceLoader.INavigationControlListener(responseModel.INavigationControlListener());
                this.GenericSearchPanel = responseModel.INavigationControlListener();
            }
            catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
                Logger.getLogger(DetailedAlbumModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    static class INavigationControlListener
    implements Comparator<DetailedAlbumMesurementModel.INavigationControlListener> {
        INavigationControlListener() {
        }

        public int INavigationControlListener(DetailedAlbumMesurementModel.INavigationControlListener o1, DetailedAlbumMesurementModel.INavigationControlListener o2) {
            return Integer.compare(o1.LraControl(), o2.LraControl());
        }

        @Override
        public /* synthetic */ int compare(Object x0, Object x1) {
            return this.INavigationControlListener((DetailedAlbumMesurementModel.INavigationControlListener)x0, (DetailedAlbumMesurementModel.INavigationControlListener)x1);
        }
    }

    public static class LraControl
    extends NavigationControl {
        public LraControl(AlbumModel albumModel, boolean loadCorrespondingResource) {
            super(albumModel, loadCorrespondingResource);
        }

        public LraControl(DetailedAlbumModel detailedAlbumModel, boolean loadCorrespondingResource) {
            super(detailedAlbumModel, loadCorrespondingResource);
        }

        @TableColumn(INavigationControlListener="Label", LraControl=210)
        public String TabCloseListener() {
            if (this.NavigationControl.LraControl() == null) {
                return "-";
            }
            return this.NavigationControl.LraControl().INavigationControlListener();
        }

        @TableColumn(INavigationControlListener="Bit Depth", LraControl=240)
        public int TabController() {
            return this.NavigationControl.TabCloseListener().INavigationControlListener();
        }

        @TableColumn(INavigationControlListener="Codec", LraControl=250)
        public String TabHeaderPanel() {
            return this.NavigationControl.GenericSearchController().INavigationControlListener();
        }

        @TableColumn(INavigationControlListener="Sample Rate", LraControl=230)
        public int TabbedSearchView() {
            return this.NavigationControl.SearchItem().INavigationControlListener();
        }

        @TableColumn(INavigationControlListener="Total Time", LraControl=410)
        public String AbstractTableView() {
            return this.INavigationControlListener(this.NavigationControl.ColumnItem());
        }

        @Override
        public void GenericSearchPanel() {
            super.GenericSearchPanel();
            this.GenericSearchField();
            this.SearchItemPanel();
        }
    }

    public static class NavigationControl
    extends DetailedAlbumModel {
        public NavigationControl(int albumId, boolean loadCorrespondingResource) {
            super(albumId, loadCorrespondingResource);
        }

        public NavigationControl(AlbumModel albumModel, boolean loadCorrespondingResource) {
            super(albumModel, loadCorrespondingResource);
        }

        public NavigationControl(DetailedAlbumModel detailedAlbumModel, boolean loadCorrespondingResource) {
            super(detailedAlbumModel, loadCorrespondingResource);
        }

        @TableColumn(INavigationControlListener="Album", LraControl=100, NavigationControl=200)
        public String AnnotatedTableView() {
            return this.NavigationControl.TabbedSearchView();
        }

        @TableColumn(INavigationControlListener="Artist", LraControl=200, NavigationControl=150)
        public String ColumnItem() {
            StringBuilder stringBuilder = new StringBuilder(0);
            Iterator iterator = this.GenericSearchField.iterator();
            while (iterator.hasNext()) {
                ArtistModel artistModel = (ArtistModel)iterator.next();
                stringBuilder.append(artistModel.INavigationControlListener());
                if (!iterator.hasNext()) continue;
                stringBuilder.append(", ");
            }
            return stringBuilder.toString();
        }

        @TableColumn(INavigationControlListener="LRA (dB)", LraControl=300, NavigationControl=50)
        public Double GenericTableView() {
            return ((DetailedAlbumMesurementModel.INavigationControlListener)this.GenericSearchController.get(0)).SearchItem();
        }

        @TableColumn(INavigationControlListener="Number of Tracks", LraControl=400, NavigationControl=50)
        public Integer ITableItemClickedListener() {
            return this.NavigationControl.AnnotatedTableView();
        }

        @TableColumn(INavigationControlListener="Release Year", LraControl=500, NavigationControl=50)
        public Integer TableColumn() {
            return this.NavigationControl.AbstractTableView();
        }

        @Override
        public void GenericSearchPanel() {
            this.SearchItem();
            this.GenericSearchField();
        }
    }
}

