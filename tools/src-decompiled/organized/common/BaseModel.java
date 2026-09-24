/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.reflect.TypeToken
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.ResponseModel;
import com.google.common.reflect.TypeToken;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Type;

public abstract class BaseModel {
    @SerializedName(value="id")
    private Integer INavigationControlListener;

    public int GenericTableView() {
        return this.INavigationControlListener;
    }

    public void INavigationControlListener(int id) {
        this.INavigationControlListener = id;
    }

    public Type GenericSearchPanel() {
        return new TypeToken<ResponseModel<BaseModel>>(){
            private static final long serialVersionUID = 1L;
        }.getType();
    }
}

