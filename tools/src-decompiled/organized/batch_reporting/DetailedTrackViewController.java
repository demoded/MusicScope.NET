/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.DetailedTrackView;
import 83nnfii93jksoiow9.DetailedTrackModel;
import 83nnfii93jksoiow9.DetailedAlbumModel;
import 83nnfii93jksoiow9.BaseViewController;
import 83nnfii93jksoiow9.ResourceCache;
import 83nnfii93jksoiow9.ResourceLoader;
import 83nnfii93jksoiow9.ResponseModel;
import 83nnfii93jksoiow9.AlbumModel;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DetailedTrackViewController
extends BaseViewController<DetailedTrackView> {
    private DetailedTrackModel.INavigationControlListener INavigationControlListener;
    private DetailedAlbumModel.LraControl LraControl;

    public DetailedTrackViewController() {
        this.LraControl(new DetailedTrackView());
    }

    public DetailedTrackViewController(DetailedTrackModel.INavigationControlListener detailedTrackModel) {
        this();
        this.INavigationControlListener = detailedTrackModel;
        AlbumModel albumModel = new AlbumModel();
        albumModel.INavigationControlListener(detailedTrackModel.LraControl());
        this.LraControl = this.INavigationControlListener(albumModel);
        this.INavigationControlListener();
    }

    public DetailedTrackViewController(DetailedTrackModel.INavigationControlListener detailedTrackModel, DetailedAlbumModel.LraControl detailedAlbumModel) {
        this();
        this.INavigationControlListener = detailedTrackModel;
        this.LraControl = detailedAlbumModel;
    }

    @Override
    public void INavigationControlListener() {
        ((DetailedTrackView)this.GenericSearchController()).INavigationControlListener(this.INavigationControlListener);
        ((DetailedTrackView)this.GenericSearchController()).INavigationControlListener(this.LraControl);
    }

    private DetailedAlbumModel.LraControl INavigationControlListener(AlbumModel albumModel) {
        try {
            ResourceCache cache = ResourceCache.LraControl();
            ResourceLoader resourceLoader = new ResourceLoader(cache);
            HashMap<String, String> albumFilter = ResourceLoader.LraControl(albumModel);
            ResponseModel<AlbumModel> responseModel = resourceLoader.INavigationControlListener(AlbumModel.class, albumFilter, "-id", 0, 1);
            return new DetailedAlbumModel.LraControl(responseModel.INavigationControlListener().get(0), true);
        }
        catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(DetailedTrackViewController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}

