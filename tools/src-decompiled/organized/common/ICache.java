/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import java.util.concurrent.Future;

public interface ICache<K, T> {
    public void INavigationControlListener(K var1, Future<T> var2);

    public boolean INavigationControlListener(K var1);

    public Future<T> LraControl(K var1);

    public Future<T> NavigationControl(K var1);

    public void INavigationControlListener();
}

