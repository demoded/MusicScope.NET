/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.ICache;
import 83nnfii93jksoiow9.BaseModel;
import 83nnfii93jksoiow9.CacheItem;
import 83nnfii93jksoiow9.ResponseModel;
import java.util.GregorianCalendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class ResourceCache
implements ICache<String, ResponseModel<? extends BaseModel>> {
    private static final int INavigationControlListener = 120000;
    private static final Object LraControl = new Object();
    private static ResourceCache NavigationControl;
    private final ConcurrentHashMap<String, CacheItem<Future<ResponseModel<? extends BaseModel>>>> GenericSearchController = new ConcurrentHashMap(0);

    private ResourceCache() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ResourceCache LraControl() {
        Object object = LraControl;
        synchronized (object) {
            if (NavigationControl == null) {
                NavigationControl = new ResourceCache();
            }
        }
        return NavigationControl;
    }

    @Override
    public synchronized void INavigationControlListener(String key, Future<ResponseModel<? extends BaseModel>> item) {
        if (key != null && !key.isEmpty() && item != null) {
            this.GenericSearchController.put(key, new CacheItem<Future<ResponseModel<? extends BaseModel>>>(item));
        }
    }

    @Override
    public synchronized boolean INavigationControlListener(String key) {
        if (key == null) {
            return false;
        }
        return this.GenericSearchController.containsKey(key) && this.INavigationControlListener(this.GenericSearchController.get(key));
    }

    @Override
    public synchronized Future<ResponseModel<? extends BaseModel>> LraControl(String key) {
        if (key != null && !key.isEmpty()) {
            return this.GenericSearchController.get(key).INavigationControlListener();
        }
        return null;
    }

    @Override
    public synchronized Future<ResponseModel<? extends BaseModel>> NavigationControl(String key) {
        if (key != null && !key.isEmpty()) {
            return this.GenericSearchController.remove(key).INavigationControlListener();
        }
        return null;
    }

    @Override
    public synchronized void INavigationControlListener() {
        this.GenericSearchController.clear();
    }

    @Override
    private boolean INavigationControlListener(CacheItem<?> item) {
        return item.LraControl() + 120000L > GregorianCalendar.getInstance().getTimeInMillis();
    }
}

