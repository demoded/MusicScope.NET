/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.ws.rs.client.Client
 *  javax.ws.rs.client.ClientBuilder
 *  javax.ws.rs.client.Invocation$Builder
 *  javax.ws.rs.client.WebTarget
 *  javax.ws.rs.core.MediaType
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.HostnameManager;
import 83nnfii93jksoiow9.BaseModel;
import 83nnfii93jksoiow9.Parameter;
import 83nnfii93jksoiow9.ResponseModel;
import 83nnfii93jksoiow9.X509TrustAllManager;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class RequestBuilder {
    protected int INavigationControlListener = 10000;
    protected int LraControl = 60000;
    protected String NavigationControl;
    protected String GenericSearchController;
    protected final ArrayList<Parameter> GenericSearchField = new ArrayList(0);
    protected final ArrayList<Parameter> GenericSearchPanel = new ArrayList(0);
    private Client SearchItem;
    private URI SearchItemPanel;

    protected RequestBuilder() {
    }

    public static RequestBuilder LraControl() {
        return new RequestBuilder();
    }

    public RequestBuilder INavigationControlListener(String resourcePath) {
        this.GenericSearchController = resourcePath;
        return this;
    }

    public RequestBuilder LraControl(String url) {
        this.NavigationControl = url;
        return this;
    }

    public RequestBuilder INavigationControlListener(int ms) {
        this.LraControl = ms;
        return this;
    }

    public RequestBuilder LraControl(int ms) {
        this.INavigationControlListener = ms;
        return this;
    }

    public RequestBuilder INavigationControlListener(String key, String value) {
        if (value != null && !value.isEmpty() && key != null && !key.isEmpty()) {
            this.GenericSearchField.add(new Parameter(key, value));
        }
        return this;
    }

    public RequestBuilder LraControl(String key, String value) {
        if (value != null && !value.isEmpty() && key != null && !key.isEmpty()) {
            this.GenericSearchPanel.add(new Parameter(key, value));
        }
        return this;
    }

    public RequestBuilder INavigationControlListener(Client client) {
        this.SearchItem = client;
        return this;
    }

    public Invocation.Builder NavigationControl() throws KeyManagementException, NoSuchAlgorithmException {
        return this.INavigationControlListener(this.GenericSearchController, this.GenericSearchField, this.GenericSearchPanel);
    }

    public Client GenericSearchController() {
        return this.SearchItem;
    }

    public void GenericSearchField() {
        this.SearchItem.close();
    }

    public URI GenericSearchPanel() {
        return this.SearchItemPanel;
    }

    protected void INavigationControlListener(URI uri) {
        this.SearchItemPanel = uri;
    }

    protected String SearchItem() {
        return this.NavigationControl;
    }

    protected String SearchItemPanel() {
        return this.SearchItem() + this.GenericSearchController;
    }

    protected Invocation.Builder TabCloseListener() throws KeyManagementException, NoSuchAlgorithmException {
        return this.INavigationControlListener(new ArrayList<Parameter>(0));
    }

    protected Invocation.Builder INavigationControlListener(ArrayList<Parameter> queryParameters) throws KeyManagementException, NoSuchAlgorithmException {
        return this.INavigationControlListener(null, queryParameters, new ArrayList<Parameter>(0));
    }

    protected Invocation.Builder NavigationControl(String resourcePath) throws KeyManagementException, NoSuchAlgorithmException {
        return this.INavigationControlListener(resourcePath, new ArrayList<Parameter>(0), new ArrayList<Parameter>(0));
    }

    protected Invocation.Builder INavigationControlListener(String resourcePath, ArrayList<Parameter> queryParameters, ArrayList<Parameter> headers) throws KeyManagementException, NoSuchAlgorithmException {
        if (this.SearchItem == null) {
            this.SearchItem = this.TabController();
        }
        return this.INavigationControlListener(this.SearchItem, resourcePath, queryParameters, headers);
    }

    protected <T extends BaseModel> ResponseModel<T> INavigationControlListener(Type type, String jsonResponse) {
        return (ResponseModel)new Gson().fromJson(jsonResponse, type);
    }

    protected Client TabController() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(null, new TrustManager[]{new X509TrustAllManager()}, new SecureRandom());
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        clientBuilder.sslContext(sslcontext);
        clientBuilder.hostnameVerifier((HostnameVerifier)new HostnameManager());
        return clientBuilder.build();
    }

    protected Invocation.Builder INavigationControlListener(Client client, String resourcePath, ArrayList<Parameter> queryParameters, ArrayList<Parameter> headers) {
        WebTarget target = client.target(this.SearchItem()).path(resourcePath);
        for (Parameter parameter : queryParameters) {
            target = target.queryParam(parameter.INavigationControlListener(), new Object[]{parameter.LraControl()});
        }
        Invocation.Builder request = target.request(new MediaType[]{MediaType.APPLICATION_JSON_TYPE});
        request.property("jersey.config.client.connectTimeout", (Object)this.INavigationControlListener);
        request.property("jersey.config.client.readTimeout", (Object)this.LraControl);
        for (Parameter parameter : headers) {
            request.header(parameter.INavigationControlListener(), (Object)parameter.LraControl());
        }
        this.INavigationControlListener(target.getUri());
        return request;
    }
}

