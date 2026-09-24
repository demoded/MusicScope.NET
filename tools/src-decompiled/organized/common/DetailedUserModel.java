/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.DetailedAlbumModel;
import 83nnfii93jksoiow9.CountModel;
import 83nnfii93jksoiow9.ResourceCache;
import 83nnfii93jksoiow9.ICorrespondingResourceLoader;
import 83nnfii93jksoiow9.ResourceLoader;
import 83nnfii93jksoiow9.UserModel;
import 83nnfii93jksoiow9.TableColumn;
import 83nnfii93jksoiow9.ResponseModel;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DetailedUserModel
implements ICorrespondingResourceLoader {
    protected static final String INavigationControlListener = "users/%s/trackMeasurements/count/";
    protected static final String LraControl = "users/%s/albumMeasurements/count/";
    protected final UserModel NavigationControl;
    protected int GenericSearchController = Integer.MIN_VALUE;
    protected int GenericSearchField = Integer.MIN_VALUE;

    public DetailedUserModel(UserModel userModel, boolean loadCorrespondingResource) {
        this.NavigationControl = userModel;
        if (loadCorrespondingResource) {
            this.GenericSearchPanel();
        }
    }

    public UserModel INavigationControlListener() {
        return this.NavigationControl;
    }

    public int LraControl() {
        return this.NavigationControl.GenericTableView();
    }

    protected void NavigationControl() {
        if (this.GenericSearchController == Integer.MIN_VALUE) {
            try {
                ResourceCache cache = ResourceCache.LraControl();
                ResourceLoader resourceLoader = new ResourceLoader(cache);
                resourceLoader.INavigationControlListener(String.format(INavigationControlListener, this.NavigationControl.GenericTableView()));
                ResponseModel<CountModel> responseModel = resourceLoader.INavigationControlListener(CountModel.class);
                if (responseModel.INavigationControlListener().size() > 0) {
                    this.GenericSearchController = responseModel.INavigationControlListener().get(0).INavigationControlListener();
                }
            }
            catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
                Logger.getLogger(DetailedAlbumModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    protected void GenericSearchController() {
        if (this.GenericSearchField == Integer.MIN_VALUE) {
            try {
                ResourceCache cache = ResourceCache.LraControl();
                ResourceLoader resourceLoader = new ResourceLoader(cache);
                resourceLoader.INavigationControlListener(String.format(LraControl, this.NavigationControl.GenericTableView()));
                ResponseModel<CountModel> responseModel = resourceLoader.INavigationControlListener(CountModel.class);
                if (responseModel.INavigationControlListener().size() > 0) {
                    this.GenericSearchField = responseModel.INavigationControlListener().get(0).INavigationControlListener();
                }
            }
            catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
                Logger.getLogger(DetailedAlbumModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static class INavigationControlListener
    extends LraControl {
        public INavigationControlListener(UserModel userModel, boolean loadCorrespondingResource) {
            super(userModel, loadCorrespondingResource);
        }

        @TableColumn(INavigationControlListener="Uploaded Tracks", LraControl=300, NavigationControl=100)
        public int GenericSearchField() {
            return this.GenericSearchField;
        }

        @Override
        public void GenericSearchPanel() {
            super.GenericSearchController();
            this.NavigationControl();
        }
    }

    public static class LraControl
    extends DetailedUserModel {
        public LraControl(UserModel userModel, boolean loadCorrespondingResource) {
            super(userModel, loadCorrespondingResource);
        }

        @TableColumn(INavigationControlListener="Name", LraControl=100, NavigationControl=400)
        public String SearchItem() {
            if (this.NavigationControl.LraControl().isEmpty()) {
                return "-";
            }
            return this.NavigationControl.LraControl();
        }

        @TableColumn(INavigationControlListener="Uploaded Albums", LraControl=200, NavigationControl=50)
        public int SearchItemPanel() {
            return this.GenericSearchField;
        }

        @Override
        public void GenericSearchPanel() {
            this.GenericSearchController();
        }
    }
}

