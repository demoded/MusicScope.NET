/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.GenericSearchField;
import 83nnfii93jksoiow9.GenericSearchPanel;
import 83nnfii93jksoiow9.SearchItem;
import 83nnfii93jksoiow9.SearchItemPanel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenericSearchController<T> {
    private final Class<T> INavigationControlListener;
    private final GenericSearchPanel LraControl;
    private final List<SearchItem> NavigationControl;

    public GenericSearchController(Class<T> searchClass, GenericSearchPanel searchPanel) {
        this.INavigationControlListener = searchClass;
        this.LraControl = searchPanel;
        this.NavigationControl = this.INavigationControlListener(searchClass);
        this.INavigationControlListener(this.NavigationControl);
    }

    public T INavigationControlListener() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        HashMap<String, String> searchValues = this.LraControl();
        T model = this.INavigationControlListener.newInstance();
        for (SearchItem item : this.NavigationControl) {
            if (!searchValues.containsKey(item.INavigationControlListener())) continue;
            if (!item.LraControl().isAccessible()) {
                item.LraControl().setAccessible(true);
            }
            item.LraControl().invoke(model, searchValues.get(item.INavigationControlListener()));
        }
        return model;
    }

    private void INavigationControlListener(List<SearchItem> items) {
        for (SearchItem item : items) {
            this.LraControl.INavigationControlListener(new SearchItemPanel(item.INavigationControlListener()));
        }
    }

    private HashMap<String, String> LraControl() {
        HashMap<String, String> valueMap = new HashMap<String, String>(0);
        for (SearchItemPanel item : this.LraControl.INavigationControlListener()) {
            valueMap.put(item.INavigationControlListener(), item.LraControl());
        }
        return valueMap;
    }

    private List<SearchItem> INavigationControlListener(Class<T> searchClass) {
        Method[] methods;
        ArrayList<SearchItem> searchItems = new ArrayList<SearchItem>(0);
        for (Method method : methods = searchClass.getDeclaredMethods()) {
            GenericSearchField searchField = method.getAnnotation(GenericSearchField.class);
            if (searchField == null) continue;
            SearchItem searchItem = new SearchItem();
            searchItem.INavigationControlListener(searchField.INavigationControlListener());
            searchItem.INavigationControlListener(method);
            searchItems.add(searchItem);
        }
        return searchItems;
    }
}

