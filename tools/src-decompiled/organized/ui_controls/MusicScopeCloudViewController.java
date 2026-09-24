/*
 * Decompiled with CFR 0.152.
 */
package com.xivero.musicscopecloud;

import 83nnfii93jksoiow9.TrackModel;
import 83nnfii93jksoiow9.INavigationControlListener;
import 83nnfii93jksoiow9.INavigationListener;
import 83nnfii93jksoiow9.ArtistModel;
import 83nnfii93jksoiow9.BaseModel;
import 83nnfii93jksoiow9.BaseViewController;
import 83nnfii93jksoiow9.TabController;
import 83nnfii93jksoiow9.UserSettingsController;
import 83nnfii93jksoiow9.MainFrame;
import 83nnfii93jksoiow9.UserSearchViewController;
import 83nnfii93jksoiow9.AlbumSearchViewController;
import 83nnfii93jksoiow9.AlbumOverviewViewController;
import 83nnfii93jksoiow9.UserModel;
import 83nnfii93jksoiow9.BaseSearchViewController;
import 83nnfii93jksoiow9.AlbumModel;
import 83nnfii93jksoiow9.ViewManager;
import 83nnfii93jksoiow9.TrackSearchViewCrontroller;
import 83nnfii93jksoiow9.ArtistSearchViewController;
import java.awt.Image;
import java.util.List;
import javax.swing.JPanel;

public class MusicScopeCloudViewController
extends ViewManager
implements INavigationControlListener,
INavigationListener {
    private final MainFrame INavigationControlListener = new MainFrame();
    private TabController LraControl;
    private String NavigationControl;

    public MusicScopeCloudViewController() {
        this.INavigationControlListener.INavigationControlListener(false);
        this.INavigationControlListener.INavigationControlListener("Album", AlbumModel.class);
        this.INavigationControlListener.INavigationControlListener("Artist", ArtistModel.class);
        this.INavigationControlListener.INavigationControlListener("Track", TrackModel.class);
        this.INavigationControlListener.INavigationControlListener("User", UserModel.class);
        this.INavigationControlListener.INavigationControlListener(this);
        this.addNavigationListener(this);
        this.LraControl = new TabController();
    }

    public void load() {
        this.loadView(new AlbumOverviewViewController());
        this.loadContent();
    }

    public synchronized void setVisible(boolean visible) {
        this.INavigationControlListener.setVisible(visible);
        this.notifyAll();
    }

    public boolean isVisible() {
        return this.INavigationControlListener.isVisible();
    }

    public void setUserKey(String userKey) {
        this.NavigationControl = userKey;
    }

    public void setIcons(List<? extends Image> icons) {
        this.INavigationControlListener.INavigationControlListener(icons);
    }

    public void setTitle(String title) {
        this.INavigationControlListener.setTitle(title);
    }

    @Override
    public void viewChanged(BaseViewController<?> viewController) {
        this.INavigationControlListener.INavigationControlListener((JPanel)viewController.GenericSearchController());
        this.INavigationControlListener.INavigationControlListener(this.hasPreviousView());
    }

    @Override
    public void backNavigationAction() {
        this.navigateBack();
        this.INavigationControlListener.INavigationControlListener(this.hasPreviousView());
    }

    @Override
    public void homeNavigationAction() {
        this.navigateFirstView();
        this.INavigationControlListener.INavigationControlListener(this.hasPreviousView());
    }

    @Override
    public void searchNavigationAction(String value, Class<? extends BaseModel> modelClass) {
        BaseSearchViewController searchViewController = null;
        if (modelClass == AlbumModel.class) {
            searchViewController = new AlbumSearchViewController();
        } else if (modelClass == TrackModel.class) {
            searchViewController = new TrackSearchViewCrontroller();
        } else if (modelClass == UserModel.class) {
            searchViewController = new UserSearchViewController();
        } else if (modelClass == ArtistModel.class) {
            searchViewController = new ArtistSearchViewController();
        }
        if (searchViewController != null) {
            searchViewController.INavigationControlListener(value);
            this.LraControl.INavigationControlListener(searchViewController.LraControl(), searchViewController);
            this.loadView(this.LraControl);
            searchViewController.INavigationControlListener();
        }
    }

    @Override
    public void userSettingsAction() {
        UserSettingsController userSettingsController = new UserSettingsController(this.NavigationControl);
        this.loadView(userSettingsController);
        userSettingsController.INavigationControlListener();
    }
}

