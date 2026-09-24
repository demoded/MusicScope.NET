/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.reflect.TypeToken
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.ForeignKey;
import 83nnfii93jksoiow9.RelationshipField;
import 83nnfii93jksoiow9.BaseModel;
import 83nnfii93jksoiow9.LabelModel;
import 83nnfii93jksoiow9.ResponseModel;
import 83nnfii93jksoiow9.AlbumModel;
import 83nnfii93jksoiow9.ResourcePath;
import 83nnfii93jksoiow9.ExcludeField;
import com.google.common.reflect.TypeToken;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Type;

@ResourcePath(INavigationControlListener="albumMeasurements/")
public class AlbumMeasurementModel
extends BaseModel {
    @ForeignKey(INavigationControlListener=LabelModel.class, LraControl="album")
    @SerializedName(value="albumId")
    private Integer INavigationControlListener;
    @ExcludeField
    @RelationshipField(INavigationControlListener="album")
    private AlbumModel LraControl;
    @SerializedName(value="lra")
    private Double NavigationControl;
    @SerializedName(value="noIsp")
    private Boolean GenericSearchController;
    @SerializedName(value="timestamp")
    private Integer GenericSearchField;

    public Integer INavigationControlListener() {
        return this.INavigationControlListener;
    }

    public AlbumModel LraControl() {
        return this.LraControl;
    }

    public void INavigationControlListener(Integer albumId) {
        this.INavigationControlListener = albumId;
    }

    public Double NavigationControl() {
        return this.NavigationControl;
    }

    public void INavigationControlListener(Double lra) {
        this.NavigationControl = lra;
    }

    public Boolean GenericSearchController() {
        return this.GenericSearchController;
    }

    public void INavigationControlListener(Boolean noIsp) {
        this.GenericSearchController = noIsp;
    }

    public Integer GenericSearchField() {
        return this.GenericSearchField;
    }

    public void LraControl(Integer timestamp) {
        this.GenericSearchField = timestamp;
    }

    @Override
    public Type GenericSearchPanel() {
        return new TypeToken<ResponseModel<AlbumMeasurementModel>>(){
            private static final long serialVersionUID = 1L;
        }.getType();
    }
}

