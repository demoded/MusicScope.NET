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

@ResourcePath(INavigationControlListener="artists/")
public class ArtistModel
extends BaseModel {
    @SerializedName(value="name")
    private String INavigationControlListener;

    public String INavigationControlListener() {
        return this.INavigationControlListener;
    }

    public void INavigationControlListener(String name) {
        this.INavigationControlListener = name;
    }

    @Override
    public Type GenericSearchPanel() {
        return new TypeToken<ResponseModel<ArtistModel>>(){
            private static final long serialVersionUID = 1L;
        }.getType();
    }
}

