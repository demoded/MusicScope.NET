/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.DetailedAlbumModel;
import 83nnfii93jksoiow9.CountModel;
import 83nnfii93jksoiow9.ResourceCache;
import 83nnfii93jksoiow9.ICorrespondingResourceLoader;
import 83nnfii93jksoiow9.AlbumMeasurementModel;
import 83nnfii93jksoiow9.ResourceLoader;
import 83nnfii93jksoiow9.UserModel;
import 83nnfii93jksoiow9.TableColumn;
import 83nnfii93jksoiow9.ResponseModel;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DetailedAlbumMesurementModel
implements ICorrespondingResourceLoader {
    protected final String INavigationControlListener = "albumMeasurements/%s/users/count/";
    protected final String LraControl = "albumMeasurements/%s/users/";
    protected final AlbumMeasurementModel NavigationControl;
    protected int GenericSearchController;
    protected UserModel GenericSearchField;

    public DetailedAlbumMesurementModel(AlbumMeasurementModel measurementModel) {
        this.NavigationControl = measurementModel;
    }

    public AlbumMeasurementModel INavigationControlListener() {
        return this.NavigationControl;
    }

    public int LraControl() {
        return this.GenericSearchController;
    }

    protected void NavigationControl() {
        if (this.NavigationControl != null) {
            try {
                ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
                resourceLoader.INavigationControlListener(String.format("albumMeasurements/%s/users/count/", this.NavigationControl.GenericTableView()));
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
        if (this.NavigationControl != null) {
            try {
                ResourceCache cache = ResourceCache.LraControl();
                ResourceLoader resourceLoader = new ResourceLoader(cache);
                resourceLoader.INavigationControlListener(String.format("albumMeasurements/%s/users/", this.NavigationControl.GenericTableView()));
                ResponseModel<UserModel> responseModel = resourceLoader.INavigationControlListener(UserModel.class, 1);
                if (responseModel.INavigationControlListener().size() > 0) {
                    this.GenericSearchField = responseModel.INavigationControlListener().get(0);
                }
            }
            catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
                Logger.getLogger(DetailedAlbumModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public UserModel GenericSearchField() {
        return this.GenericSearchField;
    }

    @Override
    public void GenericSearchPanel() {
        this.NavigationControl();
        this.GenericSearchController();
    }

    public static class INavigationControlListener
    extends DetailedAlbumMesurementModel {
        public INavigationControlListener(AlbumMeasurementModel measurementModel) {
            super(measurementModel);
        }

        @Override
        @TableColumn(INavigationControlListener="Verifications")
        public int LraControl() {
            return this.GenericSearchController;
        }

        @TableColumn(INavigationControlListener="LRA")
        public double SearchItem() {
            return this.NavigationControl.NavigationControl();
        }
    }
}

