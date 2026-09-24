/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.IExecutorStateChangedListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CountedExecutor
implements IExecutorStateChangedListener {
    private static final int INavigationControlListener = 100;
    private ExecutorService LraControl;
    private ConcurrentLinkedQueue<Future<?>> NavigationControl;
    private Timer GenericSearchController;
    private TimerTask GenericSearchField;
    private ConcurrentLinkedQueue<IExecutorStateChangedListener> GenericSearchPanel;
    private IExecutorStateChangedListener.INavigationControlListener SearchItem;

    public CountedExecutor(ExecutorService executor) {
        this.LraControl = executor;
        this.NavigationControl = new ConcurrentLinkedQueue();
        this.GenericSearchController = new Timer("Executor Waiter");
        this.GenericSearchPanel = new ConcurrentLinkedQueue();
        this.GenericSearchPanel.add(this);
    }

    public <T> Future<T> INavigationControlListener(Callable<T> task) {
        Future<T> future = this.LraControl.submit(task);
        this.NavigationControl.add(future);
        this.LraControl();
        return future;
    }

    public Future<?> INavigationControlListener(Runnable task) {
        Future<?> future = this.LraControl.submit(task);
        this.NavigationControl.add(future);
        this.LraControl();
        return future;
    }

    private void LraControl() {
        if (this.GenericSearchField == null) {
            this.GenericSearchField = new INavigationControlListener(this.NavigationControl, this.GenericSearchPanel);
            this.GenericSearchController.schedule(this.GenericSearchField, 100L, 100L);
        }
    }

    public IExecutorStateChangedListener.INavigationControlListener INavigationControlListener() {
        return this.SearchItem;
    }

    public void INavigationControlListener(IExecutorStateChangedListener listener) {
        if (!this.GenericSearchPanel.contains(listener)) {
            this.GenericSearchPanel.add(listener);
        }
    }

    public void LraControl(IExecutorStateChangedListener listener) {
        if (listener != null) {
            this.GenericSearchPanel.remove(listener);
        }
    }

    @Override
    public void INavigationControlListener(IExecutorStateChangedListener.INavigationControlListener state) {
        this.SearchItem = state;
    }

    private static class INavigationControlListener
    extends TimerTask {
        private final ConcurrentLinkedQueue<Future<?>> INavigationControlListener;
        private final ConcurrentLinkedQueue<IExecutorStateChangedListener> LraControl;
        private IExecutorStateChangedListener.INavigationControlListener NavigationControl = IExecutorStateChangedListener.INavigationControlListener.LraControl;

        private INavigationControlListener(ConcurrentLinkedQueue<Future<?>> futures, ConcurrentLinkedQueue<IExecutorStateChangedListener> changedListeners) {
            this.INavigationControlListener = futures;
            this.LraControl = changedListeners;
        }

        @Override
        public void run() {
            ArrayList removeList = new ArrayList(0);
            if (this.INavigationControlListener.isEmpty()) {
                this.INavigationControlListener(IExecutorStateChangedListener.INavigationControlListener.LraControl);
            } else {
                this.INavigationControlListener(IExecutorStateChangedListener.INavigationControlListener.INavigationControlListener);
            }
            for (Future<?> future : this.INavigationControlListener) {
                if (!future.isDone() && !future.isCancelled()) continue;
                removeList.add(future);
            }
            this.INavigationControlListener.removeAll(removeList);
        }

        private void INavigationControlListener(IExecutorStateChangedListener.INavigationControlListener state) {
            if (this.NavigationControl != state) {
                for (IExecutorStateChangedListener listener : this.LraControl) {
                    listener.INavigationControlListener(state);
                }
            }
            this.NavigationControl = state;
        }
    }
}

