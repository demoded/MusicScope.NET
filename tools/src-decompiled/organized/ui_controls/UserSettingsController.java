/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.ws.rs.client.Entity
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.RequestBuilder;
import 83nnfii93jksoiow9.LraRequest;
import 83nnfii93jksoiow9.BaseViewController;
import 83nnfii93jksoiow9.ResourceCache;
import 83nnfii93jksoiow9.GenericTableView;
import 83nnfii93jksoiow9.MessageModel;
import 83nnfii93jksoiow9.ResourceLoader;
import 83nnfii93jksoiow9.UserModel;
import 83nnfii93jksoiow9.ResponseModel;
import 83nnfii93jksoiow9.UserSettingsView;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.ws.rs.client.Entity;

public class UserSettingsController
extends BaseViewController<UserSettingsView> {
    private static final String INavigationControlListener = "users/%s/";
    private final String LraControl;
    private UserModel GenericSearchController;

    public UserSettingsController(String userId) {
        this.LraControl = userId;
        this.LraControl(new UserSettingsView());
        ((UserSettingsView)this.GenericSearchController()).INavigationControlListener(this);
    }

    @Override
    public void INavigationControlListener() {
        try {
            UserModel userFilterModel = new UserModel();
            userFilterModel.INavigationControlListener(this.LraControl);
            HashMap<String, String> userFilter = ResourceLoader.LraControl(userFilterModel);
            ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
            ResponseModel<UserModel> responseModel = resourceLoader.INavigationControlListener(UserModel.class, userFilter, "", 0, 1);
            this.GenericSearchController = responseModel.INavigationControlListener().get(0);
            ((UserSettingsView)this.GenericSearchController()).INavigationControlListener(this.GenericSearchController.LraControl());
        }
        catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(GenericTableView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void LraControl() {
        String name = ((UserSettingsView)this.GenericSearchController()).INavigationControlListener();
        try {
            RequestBuilder requestBuilder = LraRequest.INavigationControlListener().INavigationControlListener(String.format(INavigationControlListener, this.GenericSearchController.GenericTableView(), name)).INavigationControlListener("name", name);
            String response = (String)requestBuilder.NavigationControl().put(Entity.text((Object)""), String.class);
            Type typeToken = new UserModel().GenericSearchPanel();
            ResponseModel model = (ResponseModel)new Gson().fromJson(response, typeToken);
            if (this.INavigationControlListener(model, 14409)) {
                JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("com/xivero/musicscopecloud/PropertiesBundle_de_DE").getString("MessageBox.UsernameAlreadyExists"), ResourceBundle.getBundle("com/xivero/musicscopecloud/PropertiesBundle_de_DE").getString("MessageBox.Title"), 1);
            } else {
                ResourceCache.LraControl().INavigationControlListener();
                this.GenericSearchField().navigateBack();
            }
        }
        catch (KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean INavigationControlListener(ResponseModel<?> response, int code) {
        for (MessageModel message : response.NavigationControl()) {
            if (message.LraControl() != code) continue;
            return true;
        }
        return false;
    }
}

