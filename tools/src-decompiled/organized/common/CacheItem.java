/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import java.util.GregorianCalendar;

public class CacheItem<T> {
    private T INavigationControlListener;
    private long LraControl;

    public CacheItem(T cacheObject) {
        this.INavigationControlListener = cacheObject;
        this.LraControl = GregorianCalendar.getInstance().getTimeInMillis();
    }

    public T INavigationControlListener() {
        return this.INavigationControlListener;
    }

    public long LraControl() {
        return this.LraControl;
    }
}

