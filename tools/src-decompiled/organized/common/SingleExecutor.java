/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.IExecutorStateChangedListener;
import 83nnfii93jksoiow9.CountedExecutor;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SingleExecutor {
    private static final Object INavigationControlListener = new Object();
    private static SingleExecutor LraControl;
    private final CountedExecutor NavigationControl = new CountedExecutor(Executors.newCachedThreadPool());

    private SingleExecutor() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static SingleExecutor INavigationControlListener() {
        Object object = INavigationControlListener;
        synchronized (object) {
            if (LraControl == null) {
                LraControl = new SingleExecutor();
            }
        }
        return LraControl;
    }

    public void INavigationControlListener(IExecutorStateChangedListener listener) {
        this.NavigationControl.INavigationControlListener(listener);
    }

    public void LraControl(IExecutorStateChangedListener listener) {
        this.NavigationControl.LraControl(listener);
    }

    public <T> Future<T> INavigationControlListener(Callable<T> task) {
        return this.NavigationControl.INavigationControlListener(task);
    }

    public Future<?> INavigationControlListener(Runnable task) {
        return this.NavigationControl.INavigationControlListener(task);
    }
}

