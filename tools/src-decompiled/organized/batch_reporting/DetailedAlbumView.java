/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.DetailedTrackModel;
import 83nnfii93jksoiow9.DetailedAlbumModel;
import 83nnfii93jksoiow9.ITableItemClickedListener;
import 83nnfii93jksoiow9.GenericTableView;
import 83nnfii93jksoiow9.SingleExecutor;
import 83nnfii93jksoiow9.IExecutorStateChangedListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.Comparator;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;

public class DetailedAlbumView
extends JPanel
implements IExecutorStateChangedListener {
    private final GenericTableView<DetailedTrackModel.INavigationControlListener> INavigationControlListener;
    private DetailedAlbumModel.LraControl LraControl;
    private JPanel NavigationControl;
    private JLabel GenericSearchController;
    private JPanel GenericSearchField;
    private JLabel GenericSearchPanel;
    private JLabel SearchItem;
    private JPanel SearchItemPanel;
    private JTextArea TabCloseListener;
    private JLabel TabController;
    private JLabel TabHeaderPanel;
    private JLabel TabbedSearchView;
    private JLabel AbstractTableView;
    private JLabel AnnotatedTableView;
    private JLabel ColumnItem;
    private Box.Filler GenericTableView;
    private Box.Filler ITableItemClickedListener;
    private Box.Filler TableColumn;
    private Box.Filler AlbumOverviewViewController;
    private Box.Filler AlbumSearchViewController;
    private Box.Filler ArtistAlbumsViewController;
    private Box.Filler ArtistSearchViewController;
    private JPanel BaseSearchViewController;
    private JPanel BaseViewController;
    private JPanel DetailedAlbumViewController;
    private JScrollPane DetailedTrackViewController;
    private JSeparator ILoadingCompletedCallback;
    private JSeparator INavigationListener;
    private JPanel IViewLoader;
    private JLabel TrackSearchViewCrontroller;
    private JLabel UserAlbumsViewController;
    private JLabel UserSearchViewController;
    private JLabel UserSettingsController;
    private JLabel ViewManager;
    private JLabel MainFrame;
    private JLabel Icon;
    private JLabel AlbumOverviewView;
    private JPanel AlbumSearchView;
    private JLabel ArtistAlbumsView;
    private JLabel ArtistSearchView;
    private JPanel DetailedAlbumView;
    private JPanel DetailedTrackView;
    private JLabel ILoadMoreActionListener;
    private JLabel ISearchActionListener;
    private JPanel TrackSearchView;
    private JLabel UserAlbumsView;
    private JPanel UserSearchView;
    private JLabel UserSettingsView;
    private JLabel CountedExecutor;
    private JPanel IExecutorStateChangedListener;

    public DetailedAlbumView() {
        this.LraControl();
        this.INavigationControlListener = new GenericTableView<DetailedTrackModel.INavigationControlListener>(DetailedTrackModel.INavigationControlListener.class);
        this.INavigationControlListener.INavigationControlListener(new INavigationControlListener());
        this.UserSearchView.setLayout(new BorderLayout());
        this.UserSearchView.add(this.INavigationControlListener, "Center");
        SingleExecutor.INavigationControlListener().INavigationControlListener(this);
    }

    public boolean INavigationControlListener(ITableItemClickedListener<DetailedTrackModel.INavigationControlListener> listener) {
        if (listener != null) {
            return this.INavigationControlListener.INavigationControlListener(listener);
        }
        return false;
    }

    public void INavigationControlListener(DetailedAlbumModel.LraControl detailedAlbumModel) {
        this.LraControl = detailedAlbumModel;
        this.INavigationControlListener();
    }

    public void INavigationControlListener() {
        this.GenericSearchPanel.setText(this.LraControl.AnnotatedTableView());
        this.TabCloseListener.setText(this.LraControl.ColumnItem());
        this.AlbumOverviewView.setText(this.INavigationControlListener(this.LraControl.TableColumn()).toString());
        this.UserAlbumsViewController.setText(String.format("%s dB", this.LraControl.GenericTableView()));
        this.UserSettingsController.setText(this.INavigationControlListener(this.LraControl.ITableItemClickedListener()).toString());
        this.ISearchActionListener.setText(this.LraControl.AbstractTableView());
        this.AbstractTableView.setText(this.LraControl.TabHeaderPanel());
        this.TabHeaderPanel.setText(String.format("%s Bit", this.LraControl.TabController()));
        this.ArtistSearchView.setText(String.format("%s Hz", this.LraControl.TabbedSearchView()));
        this.MainFrame.setText("" + this.LraControl.TabCloseListener());
        this.ColumnItem.setText("" + this.LraControl.NavigationControl());
        this.CountedExecutor.setText(this.LraControl.GenericSearchController());
    }

    public synchronized void INavigationControlListener(DetailedTrackModel.INavigationControlListener track) {
        this.INavigationControlListener.INavigationControlListener(track);
    }

    private void LraControl() {
        this.AlbumSearchView = new JPanel();
        this.BaseViewController = new JPanel();
        this.DetailedTrackView = new JPanel();
        this.GenericSearchController = new JLabel();
        this.ArtistSearchViewController = new Box.Filler(new Dimension(25, 0), new Dimension(25, 0), new Dimension(25, Short.MAX_VALUE));
        this.GenericSearchPanel = new JLabel();
        this.BaseSearchViewController = new JPanel();
        this.GenericSearchField = new JPanel();
        this.NavigationControl = new JPanel();
        this.IViewLoader = new JPanel();
        this.ViewManager = new JLabel();
        this.Icon = new JLabel();
        this.TrackSearchViewCrontroller = new JLabel();
        this.ITableItemClickedListener = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(Short.MAX_VALUE, 15));
        this.UserSearchViewController = new JLabel();
        this.ILoadMoreActionListener = new JLabel();
        this.GenericTableView = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(Short.MAX_VALUE, 15));
        this.TabbedSearchView = new JLabel();
        this.TabController = new JLabel();
        this.ArtistAlbumsView = new JLabel();
        this.AlbumSearchViewController = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(Short.MAX_VALUE, 15));
        this.AnnotatedTableView = new JLabel();
        this.UserSettingsView = new JLabel();
        this.IExecutorStateChangedListener = new JPanel();
        this.MainFrame = new JLabel();
        this.AlbumOverviewView = new JLabel();
        this.UserAlbumsViewController = new JLabel();
        this.TableColumn = new Box.Filler(new Dimension(0, 15), new Dimension(0, 0), new Dimension(Short.MAX_VALUE, 15));
        this.UserSettingsController = new JLabel();
        this.ISearchActionListener = new JLabel();
        this.AlbumOverviewViewController = new Box.Filler(new Dimension(0, 15), new Dimension(0, 0), new Dimension(Short.MAX_VALUE, 15));
        this.AbstractTableView = new JLabel();
        this.TabHeaderPanel = new JLabel();
        this.ArtistSearchView = new JLabel();
        this.ArtistAlbumsViewController = new Box.Filler(new Dimension(0, 15), new Dimension(0, 0), new Dimension(Short.MAX_VALUE, 15));
        this.ColumnItem = new JLabel();
        this.CountedExecutor = new JLabel();
        this.DetailedAlbumView = new JPanel();
        this.ILoadingCompletedCallback = new JSeparator();
        this.SearchItemPanel = new JPanel();
        this.SearchItem = new JLabel();
        this.DetailedTrackViewController = new JScrollPane();
        this.TabCloseListener = new JTextArea();
        this.TrackSearchView = new JPanel();
        this.UserSearchView = new JPanel();
        this.UserAlbumsView = new JLabel();
        this.DetailedAlbumViewController = new JPanel();
        this.INavigationListener = new JSeparator();
        this.DetailedTrackView.setLayout(new BoxLayout(this.DetailedTrackView, 0));
        this.GenericSearchController.setText("Album:");
        this.DetailedTrackView.add(this.GenericSearchController);
        this.DetailedTrackView.add(this.ArtistSearchViewController);
        this.GenericSearchPanel.setMaximumSize(new Dimension(5000, 18));
        this.GenericSearchPanel.setMinimumSize(new Dimension(100, 18));
        this.DetailedTrackView.add(this.GenericSearchPanel);
        this.IViewLoader.setMinimumSize(new Dimension(175, 180));
        this.IViewLoader.setPreferredSize(new Dimension(175, 126));
        this.IViewLoader.setLayout(new BoxLayout(this.IViewLoader, 1));
        this.ViewManager.setText("Label:");
        this.ViewManager.setMaximumSize(new Dimension(5000, 18));
        this.ViewManager.setMinimumSize(new Dimension(100, 18));
        this.IViewLoader.add(this.ViewManager);
        this.Icon.setText("Release Year:");
        this.Icon.setMaximumSize(new Dimension(5000, 18));
        this.Icon.setMinimumSize(new Dimension(100, 18));
        this.IViewLoader.add(this.Icon);
        this.TrackSearchViewCrontroller.setText("LRA:");
        this.TrackSearchViewCrontroller.setMaximumSize(new Dimension(5000, 18));
        this.TrackSearchViewCrontroller.setMinimumSize(new Dimension(100, 18));
        this.IViewLoader.add(this.TrackSearchViewCrontroller);
        this.IViewLoader.add(this.ITableItemClickedListener);
        this.UserSearchViewController.setText("Number of Tracks:");
        this.UserSearchViewController.setMaximumSize(new Dimension(5000, 18));
        this.UserSearchViewController.setMinimumSize(new Dimension(100, 18));
        this.IViewLoader.add(this.UserSearchViewController);
        this.ILoadMoreActionListener.setText("Totaltime:");
        this.ILoadMoreActionListener.setMaximumSize(new Dimension(5000, 18));
        this.ILoadMoreActionListener.setMinimumSize(new Dimension(100, 18));
        this.IViewLoader.add(this.ILoadMoreActionListener);
        this.IViewLoader.add(this.GenericTableView);
        this.TabbedSearchView.setText("Codec:");
        this.TabbedSearchView.setMaximumSize(new Dimension(5000, 18));
        this.TabbedSearchView.setMinimumSize(new Dimension(100, 18));
        this.IViewLoader.add(this.TabbedSearchView);
        this.TabController.setText("Bit depth:");
        this.TabController.setMaximumSize(new Dimension(5000, 18));
        this.TabController.setMinimumSize(new Dimension(100, 18));
        this.IViewLoader.add(this.TabController);
        this.ArtistAlbumsView.setText("Sample Rate:");
        this.ArtistAlbumsView.setMaximumSize(new Dimension(5000, 18));
        this.ArtistAlbumsView.setMinimumSize(new Dimension(100, 18));
        this.IViewLoader.add(this.ArtistAlbumsView);
        this.IViewLoader.add(this.AlbumSearchViewController);
        this.AnnotatedTableView.setText("Number of Confirmations:");
        this.AnnotatedTableView.setMaximumSize(new Dimension(5000, 18));
        this.AnnotatedTableView.setMinimumSize(new Dimension(100, 18));
        this.IViewLoader.add(this.AnnotatedTableView);
        this.UserSettingsView.setText("First uploaded by:");
        this.UserSettingsView.setMaximumSize(new Dimension(5000, 18));
        this.UserSettingsView.setMinimumSize(new Dimension(100, 18));
        this.IViewLoader.add(this.UserSettingsView);
        this.IExecutorStateChangedListener.setMaximumSize(new Dimension(Short.MAX_VALUE, 5555));
        this.IExecutorStateChangedListener.setLayout(new BoxLayout(this.IExecutorStateChangedListener, 1));
        this.MainFrame.setMaximumSize(new Dimension(5000, 18));
        this.MainFrame.setMinimumSize(new Dimension(100, 18));
        this.IExecutorStateChangedListener.add(this.MainFrame);
        this.AlbumOverviewView.setMaximumSize(new Dimension(5000, 18));
        this.AlbumOverviewView.setMinimumSize(new Dimension(100, 18));
        this.IExecutorStateChangedListener.add(this.AlbumOverviewView);
        this.UserAlbumsViewController.setMaximumSize(new Dimension(5000, 18));
        this.UserAlbumsViewController.setMinimumSize(new Dimension(100, 18));
        this.IExecutorStateChangedListener.add(this.UserAlbumsViewController);
        this.IExecutorStateChangedListener.add(this.TableColumn);
        this.UserSettingsController.setMaximumSize(new Dimension(5000, 18));
        this.UserSettingsController.setMinimumSize(new Dimension(100, 18));
        this.IExecutorStateChangedListener.add(this.UserSettingsController);
        this.ISearchActionListener.setMaximumSize(new Dimension(5000, 18));
        this.ISearchActionListener.setMinimumSize(new Dimension(100, 18));
        this.IExecutorStateChangedListener.add(this.ISearchActionListener);
        this.IExecutorStateChangedListener.add(this.AlbumOverviewViewController);
        this.AbstractTableView.setMaximumSize(new Dimension(5000, 18));
        this.AbstractTableView.setMinimumSize(new Dimension(100, 18));
        this.IExecutorStateChangedListener.add(this.AbstractTableView);
        this.TabHeaderPanel.setMaximumSize(new Dimension(5000, 18));
        this.TabHeaderPanel.setMinimumSize(new Dimension(100, 18));
        this.IExecutorStateChangedListener.add(this.TabHeaderPanel);
        this.ArtistSearchView.setMaximumSize(new Dimension(5000, 18));
        this.ArtistSearchView.setMinimumSize(new Dimension(100, 18));
        this.IExecutorStateChangedListener.add(this.ArtistSearchView);
        this.IExecutorStateChangedListener.add(this.ArtistAlbumsViewController);
        this.ColumnItem.setMaximumSize(new Dimension(5000, 18));
        this.ColumnItem.setMinimumSize(new Dimension(100, 18));
        this.IExecutorStateChangedListener.add(this.ColumnItem);
        this.CountedExecutor.setMaximumSize(new Dimension(5000, 18));
        this.CountedExecutor.setMinimumSize(new Dimension(100, 18));
        this.IExecutorStateChangedListener.add(this.CountedExecutor);
        GroupLayout albumInfoPanelLayout = new GroupLayout(this.NavigationControl);
        this.NavigationControl.setLayout(albumInfoPanelLayout);
        albumInfoPanelLayout.setHorizontalGroup(albumInfoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(albumInfoPanelLayout.createSequentialGroup().addComponent(this.IViewLoader, -2, 190, -2).addGap(15, 15, 15).addComponent(this.IExecutorStateChangedListener, -1, 144, Short.MAX_VALUE)));
        albumInfoPanelLayout.setVerticalGroup(albumInfoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.IExecutorStateChangedListener, -2, 245, -2).addComponent(this.IViewLoader, -2, 245, -2));
        GroupLayout albumPanelLayout = new GroupLayout(this.GenericSearchField);
        this.GenericSearchField.setLayout(albumPanelLayout);
        albumPanelLayout.setHorizontalGroup(albumPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.NavigationControl, -1, -1, Short.MAX_VALUE));
        albumPanelLayout.setVerticalGroup(albumPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.NavigationControl, -2, -1, -2));
        this.ILoadingCompletedCallback.setOrientation(1);
        GroupLayout separatorPanelLayout = new GroupLayout(this.DetailedAlbumView);
        this.DetailedAlbumView.setLayout(separatorPanelLayout);
        separatorPanelLayout.setHorizontalGroup(separatorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(separatorPanelLayout.createSequentialGroup().addGap(25, 25, 25).addComponent(this.ILoadingCompletedCallback, -2, -1, -2).addContainerGap(25, Short.MAX_VALUE)));
        separatorPanelLayout.setVerticalGroup(separatorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(separatorPanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.ILoadingCompletedCallback).addGap(0, 0, 0)));
        this.SearchItem.setText("Artists:");
        this.SearchItem.setMaximumSize(new Dimension(5000, 18));
        this.SearchItem.setMinimumSize(new Dimension(100, 18));
        this.DetailedTrackViewController.setBorder(null);
        this.TabCloseListener.setEditable(false);
        this.TabCloseListener.setBackground(this.BaseViewController.getBackground());
        this.TabCloseListener.setColumns(20);
        this.TabCloseListener.setFont(this.SearchItem.getFont());
        this.TabCloseListener.setLineWrap(true);
        this.TabCloseListener.setRows(5);
        this.TabCloseListener.setWrapStyleWord(true);
        this.TabCloseListener.setMargin(new Insets(2, 0, 2, 10));
        this.DetailedTrackViewController.setViewportView(this.TabCloseListener);
        GroupLayout artistPanelLayout = new GroupLayout(this.SearchItemPanel);
        this.SearchItemPanel.setLayout(artistPanelLayout);
        artistPanelLayout.setHorizontalGroup(artistPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, artistPanelLayout.createSequentialGroup().addGap(0, 0, 0).addGroup(artistPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.SearchItem, -1, -1, Short.MAX_VALUE).addComponent(this.DetailedTrackViewController, -1, 205, Short.MAX_VALUE)).addGap(0, 0, 0)));
        artistPanelLayout.setVerticalGroup(artistPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(artistPanelLayout.createSequentialGroup().addComponent(this.SearchItem, -2, 18, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.DetailedTrackViewController)));
        GroupLayout infoPanelLayout = new GroupLayout(this.BaseSearchViewController);
        this.BaseSearchViewController.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(infoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(infoPanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.GenericSearchField, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0).addComponent(this.DetailedAlbumView, -2, -1, -2).addGap(0, 0, 0).addComponent(this.SearchItemPanel, -1, -1, Short.MAX_VALUE).addContainerGap()));
        infoPanelLayout.setVerticalGroup(infoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(infoPanelLayout.createSequentialGroup().addGap(0, 0, 0).addGroup(infoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.DetailedAlbumView, -1, -1, Short.MAX_VALUE).addComponent(this.SearchItemPanel, -1, -1, Short.MAX_VALUE).addComponent(this.GenericSearchField, -2, -1, -2)).addGap(0, 0, 0)));
        this.UserSearchView.setBorder(BorderFactory.createEtchedBorder());
        GroupLayout trackTablePanelLayout = new GroupLayout(this.UserSearchView);
        this.UserSearchView.setLayout(trackTablePanelLayout);
        trackTablePanelLayout.setHorizontalGroup(trackTablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        trackTablePanelLayout.setVerticalGroup(trackTablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 142, Short.MAX_VALUE));
        GroupLayout trackPanelLayout = new GroupLayout(this.TrackSearchView);
        this.TrackSearchView.setLayout(trackPanelLayout);
        trackPanelLayout.setHorizontalGroup(trackPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(trackPanelLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.UserSearchView, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0)));
        trackPanelLayout.setVerticalGroup(trackPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, trackPanelLayout.createSequentialGroup().addComponent(this.UserSearchView, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0)));
        this.UserAlbumsView.setText("Tracks:");
        GroupLayout jPanel1Layout = new GroupLayout(this.DetailedAlbumViewController);
        this.DetailedAlbumViewController.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.INavigationListener));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.INavigationListener, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
        GroupLayout innerRootPanelLayout = new GroupLayout(this.BaseViewController);
        this.BaseViewController.setLayout(innerRootPanelLayout);
        innerRootPanelLayout.setHorizontalGroup(innerRootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.TrackSearchView, -1, -1, Short.MAX_VALUE).addComponent(this.BaseSearchViewController, -1, -1, Short.MAX_VALUE).addComponent(this.DetailedTrackView, -1, -1, Short.MAX_VALUE).addGroup(innerRootPanelLayout.createSequentialGroup().addComponent(this.UserAlbumsView).addGap(0, 0, Short.MAX_VALUE)).addComponent(this.DetailedAlbumViewController, -1, -1, Short.MAX_VALUE));
        innerRootPanelLayout.setVerticalGroup(innerRootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(innerRootPanelLayout.createSequentialGroup().addComponent(this.DetailedTrackView, -2, -1, -2).addGap(0, 0, 0).addComponent(this.DetailedAlbumViewController, -2, -1, -2).addGap(0, 0, 0).addComponent(this.BaseSearchViewController, -2, -1, -2).addGap(25, 25, 25).addComponent(this.UserAlbumsView).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.TrackSearchView, -1, -1, Short.MAX_VALUE)));
        GroupLayout rootPanelLayout = new GroupLayout(this.AlbumSearchView);
        this.AlbumSearchView.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rootPanelLayout.createSequentialGroup().addContainerGap().addComponent(this.BaseViewController, -1, -1, Short.MAX_VALUE).addContainerGap()));
        rootPanelLayout.setVerticalGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rootPanelLayout.createSequentialGroup().addContainerGap().addComponent(this.BaseViewController, -1, -1, Short.MAX_VALUE).addContainerGap()));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AlbumSearchView, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AlbumSearchView, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE));
    }

    @Override
    public void INavigationControlListener(IExecutorStateChangedListener.INavigationControlListener state) {
        this.INavigationControlListener.INavigationControlListener(state == IExecutorStateChangedListener.INavigationControlListener.INavigationControlListener);
    }

    private Object INavigationControlListener(Object object) {
        if (object != null) {
            return object;
        }
        return "";
    }

    private static class INavigationControlListener
    implements Comparator<DetailedTrackModel.INavigationControlListener> {
        private INavigationControlListener() {
        }

        public int INavigationControlListener(DetailedTrackModel.INavigationControlListener o1, DetailedTrackModel.INavigationControlListener o2) {
            if (o1 != null && o1.TabHeaderPanel() != null && o2 != null && o2.TabHeaderPanel() != null) {
                return Integer.compare(o1.TabHeaderPanel(), o2.TabHeaderPanel());
            }
            return 0;
        }

        @Override
        public /* synthetic */ int compare(Object x0, Object x1) {
            return this.INavigationControlListener((DetailedTrackModel.INavigationControlListener)x0, (DetailedTrackModel.INavigationControlListener)x1);
        }
    }
}

