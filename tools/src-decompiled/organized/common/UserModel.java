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

@ResourcePath(INavigationControlListener="users/")
public class UserModel
extends BaseModel {
    @SerializedName(value="instanceKey")
    private String INavigationControlListener;
    @SerializedName(value="name")
    private String LraControl;

    public String INavigationControlListener() {
        return this.INavigationControlListener;
    }

    public void INavigationControlListener(String instanceKey) {
        this.INavigationControlListener = instanceKey;
    }

    public String LraControl() {
        return this.LraControl;
    }

    public void LraControl(String name) {
        this.LraControl = name;
    }

    @Override
    public Type GenericSearchPanel() {
        return new TypeToken<ResponseModel<UserModel>>(){
            private static final long serialVersionUID = 1L;
        }.getType();
    }
}

