/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.BaseViewController;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JPanel;

public abstract class BaseSearchViewController<T extends JPanel>
extends BaseViewController<T> {
    public static final String INavigationControlListener = "Loading...";
    public static final String LraControl = "No Results";

    public abstract void INavigationControlListener(String var1);

    @Override
    public abstract void INavigationControlListener();

    public abstract String LraControl();

    protected String INavigationControlListener(HashMap<String, String> filterMap, String itemLink, String keyValueLink) {
        StringBuilder builder = new StringBuilder(0);
        Iterator<String> it = filterMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            builder.append(key).append(" ").append(keyValueLink).append(" '").append(filterMap.get(key)).append("'");
            if (!it.hasNext()) continue;
            builder.append(" ").append(itemLink).append(" ");
        }
        return builder.toString();
    }
}

