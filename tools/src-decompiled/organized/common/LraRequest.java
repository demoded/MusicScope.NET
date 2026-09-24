/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.RequestBuilder;
import 83nnfii93jksoiow9.Parameter;

public class LraRequest
extends RequestBuilder {
    private final int SearchItem = 10000;
    private final int SearchItemPanel = 60000;
    private final String TabCloseListener = "https://api.xivero.com:8097/lra/%s/";
    private final String TabController = "v1.0";
    private final String TabHeaderPanel = "dU83jmI032MW#Jd93dfsuj";

    public LraRequest() {
        this.INavigationControlListener = 10000;
        this.LraControl = 60000;
        this.NavigationControl = String.format("https://api.xivero.com:8097/lra/%s/", "v1.0");
        this.GenericSearchPanel.add(new Parameter("apiKey", "dU83jmI032MW#Jd93dfsuj"));
    }

    public static RequestBuilder INavigationControlListener() {
        return new LraRequest();
    }
}

