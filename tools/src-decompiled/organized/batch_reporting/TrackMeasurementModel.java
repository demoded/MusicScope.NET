/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.reflect.TypeToken
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.BaseModel;
import 83nnfii93jksoiow9.ResponseModel;
import 83nnfii93jksoiow9.ResourcePath;
import com.google.common.reflect.TypeToken;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Type;

@ResourcePath(INavigationControlListener="trackMeasurements/")
public class TrackMeasurementModel
extends BaseModel {
    @SerializedName(value="trackId")
    private Integer INavigationControlListener;
    @SerializedName(value="lra")
    private Double LraControl;
    @SerializedName(value="leftTpl")
    private Double NavigationControl;
    @SerializedName(value="rightTpl")
    private Double GenericSearchController;
    @SerializedName(value="midTpl")
    private Double GenericSearchField;
    @SerializedName(value="sideTpl")
    private Double GenericSearchPanel;
    @SerializedName(value="leftRms")
    private Double SearchItem;
    @SerializedName(value="rightRms")
    private Double SearchItemPanel;
    @SerializedName(value="midRms")
    private Double TabCloseListener;
    @SerializedName(value="sideRms")
    private Double TabController;
    @SerializedName(value="integratedLoudness")
    private Double TabHeaderPanel;
    @SerializedName(value="loudnessShortTermMax")
    private Double TabbedSearchView;
    @SerializedName(value="loudnessMomentaryMax")
    private Double AbstractTableView;
    @SerializedName(value="plrAverage")
    private Double AnnotatedTableView;
    @SerializedName(value="crestAverage")
    private Double ColumnItem;
    @SerializedName(value="cutOffFrequency")
    private Double GenericTableView;
    @SerializedName(value="leftIntersamplePeaks")
    private Integer ITableItemClickedListener;
    @SerializedName(value="rightIntersamplePeaks")
    private Integer TableColumn;
    @SerializedName(value="timestamp")
    private Integer AlbumOverviewViewController;
    @SerializedName(value="hash")
    private String AlbumSearchViewController;

    public int INavigationControlListener() {
        return this.INavigationControlListener;
    }

    public double LraControl() {
        return this.LraControl;
    }

    public double NavigationControl() {
        return this.NavigationControl;
    }

    public double GenericSearchController() {
        return this.GenericSearchController;
    }

    public double GenericSearchField() {
        return this.GenericSearchField;
    }

    public double SearchItem() {
        return this.GenericSearchPanel;
    }

    public double SearchItemPanel() {
        return this.SearchItem;
    }

    public double TabCloseListener() {
        return this.SearchItemPanel;
    }

    public double TabController() {
        return this.TabCloseListener;
    }

    public double TabHeaderPanel() {
        return this.TabController;
    }

    public double TabbedSearchView() {
        return this.TabHeaderPanel;
    }

    public double AbstractTableView() {
        return this.TabbedSearchView;
    }

    public double AnnotatedTableView() {
        return this.AbstractTableView;
    }

    public double ColumnItem() {
        return this.AnnotatedTableView;
    }

    public double ITableItemClickedListener() {
        return this.ColumnItem;
    }

    public double TableColumn() {
        return this.GenericTableView;
    }

    public int AlbumOverviewViewController() {
        return this.ITableItemClickedListener;
    }

    public int AlbumSearchViewController() {
        return this.TableColumn;
    }

    public int ArtistAlbumsViewController() {
        return this.AlbumOverviewViewController;
    }

    public String ArtistSearchViewController() {
        return this.AlbumSearchViewController;
    }

    @Override
    public Type GenericSearchPanel() {
        return new TypeToken<ResponseModel<TrackMeasurementModel>>(){
            private static final long serialVersionUID = 1L;
        }.getType();
    }
}

