/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.UserSearchView;
import 83nnfii93jksoiow9.UserAlbumsViewController;
import 83nnfii93jksoiow9.ITableItemClickedListener;
import 83nnfii93jksoiow9.ResourceCache;
import 83nnfii93jksoiow9.GenericTableView;
import 83nnfii93jksoiow9.SingleExecutor;
import 83nnfii93jksoiow9.ResourceLoader;
import 83nnfii93jksoiow9.UserModel;
import 83nnfii93jksoiow9.IExecutorStateChangedListener;
import 83nnfii93jksoiow9.DetailedUserModel;
import 83nnfii93jksoiow9.ResponseModel;
import 83nnfii93jksoiow9.BaseSearchViewController;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserSearchViewController
extends BaseSearchViewController<UserSearchView> {
    private static final int GenericSearchController = 20;
    private final GenericTableView<DetailedUserModel.LraControl> GenericSearchField = new GenericTableView<DetailedUserModel.LraControl>(DetailedUserModel.LraControl.class);
    private final SingleExecutor GenericSearchPanel;
    private final LraControl SearchItem;

    public UserSearchViewController() {
        this.GenericSearchField.INavigationControlListener(new NavigationControl(this));
        this.GenericSearchField.INavigationControlListener(new INavigationControlListener());
        this.GenericSearchPanel = SingleExecutor.INavigationControlListener();
        this.SearchItem = new LraControl(this.GenericSearchField);
        this.GenericSearchPanel.INavigationControlListener(this.SearchItem);
        this.LraControl(new UserSearchView());
        ((UserSearchView)this.GenericSearchController()).INavigationControlListener(this.GenericSearchField);
        ((UserSearchView)this.GenericSearchController()).INavigationControlListener(this);
    }

    @Override
    public void INavigationControlListener(String value) {
        ((UserSearchView)this.GenericSearchController()).INavigationControlListener(value);
    }

    @Override
    public void INavigationControlListener() {
        this.GenericSearchField.NavigationControl();
        this.LraControl("");
    }

    @Override
    private void LraControl(String filter) {
        UserSearchView view = (UserSearchView)this.GenericSearchController();
        UserModel userFilterModel = new UserModel();
        userFilterModel.LraControl("%" + view.INavigationControlListener() + "%");
        ResourceLoader resourceLoader = new ResourceLoader(ResourceCache.LraControl());
        HashMap<String, String> filterMap = ResourceLoader.LraControl(userFilterModel);
        String filterString = this.INavigationControlListener(filterMap, "AND", "LIKE");
        if (!filter.trim().isEmpty()) {
            filterString = filter + " AND " + filterString;
        }
        try {
            ResponseModel<UserModel> responseModel = resourceLoader.INavigationControlListener(UserModel.class, filterString, "-id", 0, 20);
            for (final UserModel userModel : responseModel.INavigationControlListener()) {
                this.GenericSearchPanel.INavigationControlListener(new Thread(new Runnable(){

                    @Override
                    public void run() {
                        DetailedUserModel.LraControl detailedUserModel = new DetailedUserModel.LraControl(userModel, true);
                        UserSearchViewController.this.GenericSearchField.INavigationControlListener(detailedUserModel);
                    }
                }));
            }
        }
        catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserSearchViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String LraControl() {
        return "Search: " + ((UserSearchView)this.GenericSearchController()).INavigationControlListener();
    }

    public void NavigationControl() {
        this.LraControl("id < " + this.SearchItemPanel());
        this.LraControl("id > " + this.SearchItem());
    }

    private int SearchItem() {
        int id = 0;
        for (DetailedUserModel.LraControl row : this.GenericSearchField.LraControl()) {
            if (row.LraControl() <= id) continue;
            id = row.LraControl();
        }
        return id;
    }

    private int SearchItemPanel() {
        int id = Integer.MAX_VALUE;
        for (DetailedUserModel.LraControl row : this.GenericSearchField.LraControl()) {
            if (row.LraControl() >= id) continue;
            id = row.LraControl();
        }
        return id;
    }

    private static class LraControl
    implements IExecutorStateChangedListener {
        private final GenericTableView<?> INavigationControlListener;

        private LraControl(GenericTableView<?> tableView) {
            this.INavigationControlListener = tableView;
        }

        @Override
        public void INavigationControlListener(IExecutorStateChangedListener.INavigationControlListener state) {
            switch (state) {
                case INavigationControlListener: {
                    this.INavigationControlListener.INavigationControlListener("Loading...");
                    this.INavigationControlListener.INavigationControlListener(true);
                    break;
                }
                default: {
                    this.INavigationControlListener.INavigationControlListener("No Results");
                    this.INavigationControlListener.INavigationControlListener(this.INavigationControlListener.INavigationControlListener() <= 0);
                }
            }
        }
    }

    private static class INavigationControlListener
    implements Comparator<DetailedUserModel.LraControl> {
        private INavigationControlListener() {
        }

        public int INavigationControlListener(DetailedUserModel.LraControl o1, DetailedUserModel.LraControl o2) {
            return Integer.compare(o2.LraControl(), o1.LraControl());
        }

        @Override
        public /* synthetic */ int compare(Object x0, Object x1) {
            return this.INavigationControlListener((DetailedUserModel.LraControl)x0, (DetailedUserModel.LraControl)x1);
        }
    }

    private static class NavigationControl
    implements ITableItemClickedListener<DetailedUserModel.LraControl> {
        private final UserSearchViewController INavigationControlListener;

        private NavigationControl(UserSearchViewController controller) {
            this.INavigationControlListener = controller;
        }

        @Override
        public void INavigationControlListener(DetailedUserModel.LraControl item, String columnName, Object value) {
            UserAlbumsViewController userAlbumsViewController = new UserAlbumsViewController(item);
            this.INavigationControlListener.INavigationControlListener(userAlbumsViewController);
            userAlbumsViewController.INavigationControlListener();
        }
    }
}

