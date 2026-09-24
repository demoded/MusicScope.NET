/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.DetailedTrackModel;
import 83nnfii93jksoiow9.DetailedAlbumModel;
import 83nnfii93jksoiow9.DetailedTrackViewController;
import 83nnfii93jksoiow9.TrackModel;
import 83nnfii93jksoiow9.DetailedAlbumView;
import 83nnfii93jksoiow9.ITableItemClickedListener;
import 83nnfii93jksoiow9.BaseViewController;
import 83nnfii93jksoiow9.SingleExecutor;
import 83nnfii93jksoiow9.AlbumModel;

public class DetailedAlbumViewController
extends BaseViewController<DetailedAlbumView> {
    private final SingleExecutor INavigationControlListener;
    private AlbumModel LraControl;
    private DetailedAlbumModel.LraControl GenericSearchController;

    public DetailedAlbumViewController(AlbumModel albumModel) {
        this.LraControl = albumModel;
        this.INavigationControlListener = SingleExecutor.INavigationControlListener();
        this.LraControl(new DetailedAlbumView());
        ((DetailedAlbumView)this.GenericSearchController()).INavigationControlListener(new INavigationControlListener(this));
    }

    public DetailedAlbumViewController(DetailedAlbumModel detailedAlbumModel) {
        this(detailedAlbumModel.INavigationControlListener());
        this.GenericSearchController = new DetailedAlbumModel.LraControl(detailedAlbumModel, false);
    }

    public DetailedAlbumModel.LraControl LraControl() {
        return this.GenericSearchController;
    }

    @Override
    public void INavigationControlListener() {
        ((DetailedAlbumView)this.GenericSearchController()).INavigationControlListener(this.GenericSearchController);
        this.GenericSearchController.GenericSearchPanel();
        for (final TrackModel track : this.GenericSearchController.LraControl()) {
            this.INavigationControlListener.INavigationControlListener(new Thread(new Runnable(){

                @Override
                public void run() {
                    DetailedTrackModel.INavigationControlListener detailedTrack = new DetailedTrackModel.INavigationControlListener(track, true);
                    ((DetailedAlbumView)DetailedAlbumViewController.this.GenericSearchController()).INavigationControlListener(detailedTrack);
                }
            }));
        }
    }

    private static class INavigationControlListener
    implements ITableItemClickedListener<DetailedTrackModel.INavigationControlListener> {
        private final DetailedAlbumViewController INavigationControlListener;

        private INavigationControlListener(DetailedAlbumViewController controller) {
            this.INavigationControlListener = controller;
        }

        @Override
        public void INavigationControlListener(DetailedTrackModel.INavigationControlListener item, String columnName, Object value) {
            DetailedTrackViewController detailedTrackViewController = new DetailedTrackViewController(item, this.INavigationControlListener.LraControl());
            this.INavigationControlListener.INavigationControlListener(detailedTrackViewController);
            detailedTrackViewController.INavigationControlListener();
        }
    }
}

