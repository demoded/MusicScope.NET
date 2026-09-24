/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.CountModel;
import 83nnfii93jksoiow9.ArtistModel;
import 83nnfii93jksoiow9.ResourceCache;
import 83nnfii93jksoiow9.ICorrespondingResourceLoader;
import 83nnfii93jksoiow9.ResourceLoader;
import 83nnfii93jksoiow9.TableColumn;
import 83nnfii93jksoiow9.ResponseModel;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DetailedArtistModel
implements ICorrespondingResourceLoader {
    protected static final String INavigationControlListener = "artists/%s/tracks/count/";
    protected static final String LraControl = "artists/%s/albums/count/";
    protected ArtistModel NavigationControl;
    protected int GenericSearchController = Integer.MIN_VALUE;
    protected int GenericSearchField = Integer.MIN_VALUE;

    public DetailedArtistModel(ArtistModel artistModel, boolean loadCorrespondingResource) {
        this.NavigationControl = artistModel;
        if (loadCorrespondingResource) {
            this.GenericSearchPanel();
        }
    }

    public ArtistModel INavigationControlListener() {
        return this.NavigationControl;
    }

    protected void LraControl() {
        if (this.GenericSearchController == Integer.MIN_VALUE) {
            try {
                ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
                resourceLoader.INavigationControlListener(String.format(INavigationControlListener, this.NavigationControl.GenericTableView()));
                ResponseModel<CountModel> responseModel = resourceLoader.INavigationControlListener(CountModel.class);
                if (responseModel.INavigationControlListener().size() > 0) {
                    this.GenericSearchController = responseModel.INavigationControlListener().get(0).INavigationControlListener();
                }
            }
            catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
                Logger.getLogger(DetailedArtistModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    protected void NavigationControl() {
        if (this.GenericSearchField == Integer.MIN_VALUE) {
            try {
                ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
                resourceLoader.INavigationControlListener(String.format(LraControl, this.NavigationControl.GenericTableView()));
                ResponseModel<CountModel> responseModel = resourceLoader.INavigationControlListener(CountModel.class);
                if (responseModel.INavigationControlListener().size() > 0) {
                    this.GenericSearchField = responseModel.INavigationControlListener().get(0).INavigationControlListener();
                }
            }
            catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
                Logger.getLogger(DetailedArtistModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static class INavigationControlListener
    extends DetailedArtistModel {
        public INavigationControlListener(ArtistModel artistModel, boolean loadCorrespondingResource) {
            super(artistModel, loadCorrespondingResource);
        }

        @TableColumn(INavigationControlListener="Name", LraControl=100, NavigationControl=400)
        public String GenericSearchController() {
            if (this.NavigationControl.INavigationControlListener().isEmpty()) {
                return "-";
            }
            return this.NavigationControl.INavigationControlListener();
        }

        @TableColumn(INavigationControlListener="Albums", LraControl=200, NavigationControl=50)
        public int GenericSearchField() {
            return this.GenericSearchField;
        }

        @Override
        public void GenericSearchPanel() {
            this.NavigationControl();
        }
    }
}

