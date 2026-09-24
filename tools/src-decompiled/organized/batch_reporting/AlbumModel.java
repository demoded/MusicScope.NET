/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.reflect.TypeToken
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.ForeignKey;
import 83nnfii93jksoiow9.GenericSearchField;
import 83nnfii93jksoiow9.RelationshipField;
import 83nnfii93jksoiow9.BaseModel;
import 83nnfii93jksoiow9.CodecModel;
import 83nnfii93jksoiow9.LabelModel;
import 83nnfii93jksoiow9.TableColumn;
import 83nnfii93jksoiow9.ResponseModel;
import 83nnfii93jksoiow9.BitDepthModel;
import 83nnfii93jksoiow9.SourceModel;
import 83nnfii93jksoiow9.ResourcePath;
import 83nnfii93jksoiow9.SampleRateModel;
import 83nnfii93jksoiow9.ExcludeField;
import com.google.common.reflect.TypeToken;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Type;

@ResourcePath(INavigationControlListener="albums/")
public class AlbumModel
extends BaseModel {
    @ForeignKey(INavigationControlListener=LabelModel.class, LraControl="label")
    @SerializedName(value="labelId")
    private Integer INavigationControlListener;
    @ExcludeField
    @RelationshipField(INavigationControlListener="label")
    private LabelModel LraControl;
    @ForeignKey(INavigationControlListener=CodecModel.class, LraControl="codec")
    @SerializedName(value="codecId")
    private Integer NavigationControl;
    @ExcludeField
    @RelationshipField(INavigationControlListener="codec")
    private CodecModel GenericSearchController;
    @ForeignKey(INavigationControlListener=SampleRateModel.class, LraControl="sampleRate")
    @SerializedName(value="sampleRateId")
    private Integer GenericSearchField;
    @ExcludeField
    @RelationshipField(INavigationControlListener="sampleRate")
    private SampleRateModel GenericSearchPanel;
    @ForeignKey(INavigationControlListener=BitDepthModel.class, LraControl="bitDepth")
    @SerializedName(value="bitDepthId")
    private Integer SearchItem;
    @ExcludeField
    @RelationshipField(INavigationControlListener="bitDepth")
    private BitDepthModel SearchItemPanel;
    @ForeignKey(INavigationControlListener=SourceModel.class, LraControl="source")
    @SerializedName(value="sourceId")
    private Integer TabCloseListener;
    @ExcludeField
    @RelationshipField(INavigationControlListener="source")
    private SourceModel TabController;
    @SerializedName(value="name")
    private String TabHeaderPanel;
    @SerializedName(value="releaseYear")
    private Integer TabbedSearchView;
    @SerializedName(value="numberTracks")
    private Integer AbstractTableView;
    @SerializedName(value="totalTime")
    private Integer AnnotatedTableView;

    public Integer INavigationControlListener() {
        return this.INavigationControlListener;
    }

    public LabelModel LraControl() {
        return this.LraControl;
    }

    public Integer NavigationControl() {
        return this.NavigationControl;
    }

    public CodecModel GenericSearchController() {
        return this.GenericSearchController;
    }

    public Integer GenericSearchField() {
        return this.GenericSearchField;
    }

    public SampleRateModel SearchItem() {
        return this.GenericSearchPanel;
    }

    public Integer SearchItemPanel() {
        return this.SearchItem;
    }

    public BitDepthModel TabCloseListener() {
        return this.SearchItemPanel;
    }

    public Integer TabController() {
        return this.TabCloseListener;
    }

    public SourceModel TabHeaderPanel() {
        return this.TabController;
    }

    @TableColumn(INavigationControlListener="Album")
    public String TabbedSearchView() {
        return this.TabHeaderPanel;
    }

    @TableColumn(INavigationControlListener="Release Year")
    public Integer AbstractTableView() {
        return this.TabbedSearchView;
    }

    @TableColumn(INavigationControlListener="Number Tracks")
    public Integer AnnotatedTableView() {
        return this.AbstractTableView;
    }

    public Integer ColumnItem() {
        return this.AnnotatedTableView;
    }

    @GenericSearchField(INavigationControlListener="Release Year")
    public void INavigationControlListener(Integer releaseYear) {
        this.TabbedSearchView = releaseYear;
    }

    @GenericSearchField(INavigationControlListener="Name")
    public void INavigationControlListener(String name) {
        this.TabHeaderPanel = name;
    }

    @GenericSearchField(INavigationControlListener="Number of Tracks")
    public void LraControl(Integer numberTracks) {
        this.AbstractTableView = numberTracks;
    }

    @Override
    public Type GenericSearchPanel() {
        return new TypeToken<ResponseModel<AlbumModel>>(){
            private static final long serialVersionUID = 1L;
        }.getType();
    }
}

