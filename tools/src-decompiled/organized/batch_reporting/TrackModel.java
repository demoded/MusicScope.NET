/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.reflect.TypeToken
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.ForeignKey;
import 83nnfii93jksoiow9.ComposerModel;
import 83nnfii93jksoiow9.RelationshipField;
import 83nnfii93jksoiow9.BaseModel;
import 83nnfii93jksoiow9.ResponseModel;
import 83nnfii93jksoiow9.ResourcePath;
import 83nnfii93jksoiow9.GenreModel;
import 83nnfii93jksoiow9.ExcludeField;
import com.google.common.reflect.TypeToken;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Type;

@ResourcePath(INavigationControlListener="tracks/")
public class TrackModel
extends BaseModel {
    @ForeignKey(INavigationControlListener=GenreModel.class, LraControl="genre")
    @SerializedName(value="genreId")
    private Integer INavigationControlListener;
    @ExcludeField
    @RelationshipField(INavigationControlListener="genre")
    private GenreModel LraControl;
    @ForeignKey(INavigationControlListener=GenreModel.class, LraControl="composer")
    @SerializedName(value="composerId")
    private Integer NavigationControl;
    @ExcludeField
    @RelationshipField(INavigationControlListener="composer")
    private ComposerModel GenericSearchController;
    @SerializedName(value="albumId")
    private Integer GenericSearchField;
    @SerializedName(value="name")
    private String GenericSearchPanel;
    @SerializedName(value="releaseYear")
    private Integer SearchItem;
    @SerializedName(value="number")
    private Integer SearchItemPanel;
    @SerializedName(value="duration")
    private Integer TabCloseListener;

    public int INavigationControlListener() {
        return this.INavigationControlListener;
    }

    public GenreModel LraControl() {
        return this.LraControl;
    }

    public Integer NavigationControl() {
        return this.NavigationControl;
    }

    public ComposerModel GenericSearchController() {
        return this.GenericSearchController;
    }

    public int GenericSearchField() {
        return this.GenericSearchField;
    }

    public String SearchItem() {
        return this.GenericSearchPanel;
    }

    public void INavigationControlListener(String name) {
        this.GenericSearchPanel = name;
    }

    public Integer SearchItemPanel() {
        return this.SearchItem;
    }

    public Integer TabCloseListener() {
        return this.SearchItemPanel;
    }

    public Integer TabController() {
        return this.TabCloseListener;
    }

    @Override
    public Type GenericSearchPanel() {
        return new TypeToken<ResponseModel<TrackModel>>(){
            private static final long serialVersionUID = 1L;
        }.getType();
    }
}

