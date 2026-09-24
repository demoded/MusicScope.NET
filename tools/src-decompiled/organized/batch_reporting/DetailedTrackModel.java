/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.DetailedAlbumModel;
import 83nnfii93jksoiow9.TrackModel;
import 83nnfii93jksoiow9.DetailedTrackMeasurementModel;
import 83nnfii93jksoiow9.ArtistModel;
import 83nnfii93jksoiow9.ResourceCache;
import 83nnfii93jksoiow9.ICorrespondingResourceLoader;
import 83nnfii93jksoiow9.ResourceLoader;
import 83nnfii93jksoiow9.TableColumn;
import 83nnfii93jksoiow9.ResponseModel;
import 83nnfii93jksoiow9.TrackMeasurementModel;
import 83nnfii93jksoiow9.SortedList;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DetailedTrackModel
implements ICorrespondingResourceLoader {
    private static final String GenericSearchController = "tracks/%s/artists/";
    private static final String GenericSearchField = "tracks/%s/trackMeasurements/";
    protected final TrackModel INavigationControlListener;
    protected List<ArtistModel> LraControl;
    protected SortedList<DetailedTrackMeasurementModel.INavigationControlListener> NavigationControl;

    public DetailedTrackModel(TrackModel trackModel, boolean loadCorrespondingResource) {
        this.INavigationControlListener = trackModel;
        this.NavigationControl = new SortedList<DetailedTrackMeasurementModel.INavigationControlListener>(new LraControl());
        if (loadCorrespondingResource) {
            this.GenericSearchPanel();
        }
    }

    public DetailedTrackModel(DetailedTrackModel model, boolean loadCorrespondingResource) {
        this.INavigationControlListener = model.INavigationControlListener;
        this.NavigationControl = model.NavigationControl;
        if (loadCorrespondingResource) {
            this.GenericSearchPanel();
        }
    }

    public List<DetailedTrackMeasurementModel.INavigationControlListener> INavigationControlListener() {
        return Collections.unmodifiableList(this.NavigationControl);
    }

    public int LraControl() {
        return this.INavigationControlListener.GenericSearchField();
    }

    public String NavigationControl() {
        StringBuilder stringBuilder = new StringBuilder(0);
        Iterator<ArtistModel> iterator = this.LraControl.iterator();
        while (iterator.hasNext()) {
            ArtistModel artistModel = iterator.next();
            stringBuilder.append(artistModel.INavigationControlListener());
            if (!iterator.hasNext()) continue;
            stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }

    public TrackModel GenericSearchController() {
        return this.INavigationControlListener;
    }

    public int GenericSearchField() {
        return this.NavigationControl.get(0).LraControl();
    }

    public String SearchItem() {
        return this.NavigationControl.get(0).GenericSearchField().LraControl();
    }

    protected String INavigationControlListener(int totalSecs) {
        int minutes = totalSecs / 60;
        int seconds = totalSecs % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public synchronized void SearchItemPanel() {
        if (this.INavigationControlListener != null && this.LraControl == null) {
            try {
                ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
                resourceLoader.INavigationControlListener(String.format(GenericSearchController, this.INavigationControlListener.GenericTableView()));
                ResponseModel<ArtistModel> responseModel = resourceLoader.INavigationControlListener(ArtistModel.class);
                this.LraControl = responseModel.INavigationControlListener();
            }
            catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
                Logger.getLogger(DetailedAlbumModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public synchronized void TabCloseListener() {
        if (this.INavigationControlListener != null) {
            try {
                ResourceCache cache = ResourceCache.LraControl();
                ResourceLoader resourceLoader = new ResourceLoader(cache);
                resourceLoader.INavigationControlListener(String.format(GenericSearchField, this.INavigationControlListener.GenericTableView()));
                ResponseModel<TrackMeasurementModel> responseModel = resourceLoader.INavigationControlListener(TrackMeasurementModel.class);
                for (TrackMeasurementModel measurementModel : responseModel.INavigationControlListener()) {
                    DetailedTrackMeasurementModel.INavigationControlListener model = new DetailedTrackMeasurementModel.INavigationControlListener(measurementModel);
                    model.GenericSearchPanel();
                    this.NavigationControl.add(model);
                }
            }
            catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
                Logger.getLogger(DetailedAlbumModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    static class LraControl
    implements Comparator<DetailedTrackMeasurementModel.INavigationControlListener> {
        LraControl() {
        }

        public int INavigationControlListener(DetailedTrackMeasurementModel.INavigationControlListener model, DetailedTrackMeasurementModel.INavigationControlListener compareModel) {
            return Integer.compare(model.LraControl(), compareModel.LraControl());
        }

        @Override
        public /* synthetic */ int compare(Object x0, Object x1) {
            return this.INavigationControlListener((DetailedTrackMeasurementModel.INavigationControlListener)x0, (DetailedTrackMeasurementModel.INavigationControlListener)x1);
        }
    }

    public static class INavigationControlListener
    extends DetailedTrackModel {
        public INavigationControlListener(TrackModel trackModel, boolean loadCorrespondingResource) {
            super(trackModel, loadCorrespondingResource);
        }

        @TableColumn(INavigationControlListener="Track", LraControl=100, NavigationControl=400)
        public String TabController() {
            return this.INavigationControlListener.SearchItem();
        }

        @TableColumn(INavigationControlListener="Number", LraControl=300)
        public Integer TabHeaderPanel() {
            return this.INavigationControlListener.TabCloseListener();
        }

        @TableColumn(INavigationControlListener="Duration", LraControl=400)
        public String TabbedSearchView() {
            return this.INavigationControlListener(this.INavigationControlListener.TabController());
        }

        @TableColumn(INavigationControlListener="LRA (dB)", LraControl=200)
        public double AbstractTableView() {
            return ((DetailedTrackMeasurementModel.INavigationControlListener)this.NavigationControl.get(0)).SearchItem();
        }

        public String AnnotatedTableView() {
            if (this.INavigationControlListener.LraControl() != null) {
                return this.INavigationControlListener.LraControl().INavigationControlListener();
            }
            return "-";
        }

        public String ColumnItem() {
            if (this.INavigationControlListener.GenericSearchController() != null) {
                return this.INavigationControlListener.GenericSearchController().INavigationControlListener();
            }
            return "-";
        }

        @Override
        public void GenericSearchPanel() {
            this.SearchItemPanel();
            this.TabCloseListener();
        }
    }
}

