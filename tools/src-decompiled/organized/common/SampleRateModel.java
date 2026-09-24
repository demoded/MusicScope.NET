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

@ResourcePath(INavigationControlListener="sampleRates/")
public class SampleRateModel
extends BaseModel {
    @SerializedName(value="sampleRate")
    private Integer INavigationControlListener;
    @SerializedName(value="description")
    private String LraControl;

    public int INavigationControlListener() {
        return this.INavigationControlListener;
    }

    public String LraControl() {
        return this.LraControl;
    }

    @Override
    public Type GenericSearchPanel() {
        return new TypeToken<ResponseModel<SampleRateModel>>(){
            private static final long serialVersionUID = 1L;
        }.getType();
    }
}

