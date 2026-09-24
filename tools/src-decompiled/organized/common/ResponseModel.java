/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.BaseModel;
import 83nnfii93jksoiow9.MessageModel;
import 83nnfii93jksoiow9.ErrorModel;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResponseModel<T extends BaseModel> {
    @SerializedName(value="error")
    private boolean INavigationControlListener;
    @SerializedName(value="data")
    private ArrayList<T> LraControl;
    @SerializedName(value="messages")
    private ArrayList<MessageModel> NavigationControl;
    @SerializedName(value="errors")
    private ArrayList<ErrorModel> GenericSearchController;

    public List<T> INavigationControlListener() {
        if (this.LraControl == null) {
            return new ArrayList(0);
        }
        return Collections.unmodifiableList(this.LraControl);
    }

    public boolean LraControl() {
        return this.INavigationControlListener;
    }

    public List<MessageModel> NavigationControl() {
        return Collections.unmodifiableList(this.NavigationControl);
    }

    public List<ErrorModel> GenericSearchController() {
        return Collections.unmodifiableList(this.GenericSearchController);
    }
}

