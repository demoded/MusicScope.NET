/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  javax.ws.rs.ProcessingException
 *  javax.ws.rs.client.Client
 *  javax.ws.rs.client.ClientBuilder
 *  javax.ws.rs.client.Entity
 *  javax.ws.rs.client.Invocation$Builder
 *  javax.ws.rs.client.WebTarget
 *  javax.ws.rs.core.MediaType
 */
package sdfgjkljljoftrytrszgijpokjprs;

import com.google.gson.Gson;
import java.security.SecureRandom;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import sdfgjkljljoftrytrszgijpokjprs.LraApiCallable;
import sdfgjkljljoftrytrszgijpokjprs.UploadResponseModel;

public class KeyMigrationCallable
extends LraApiCallable<UploadResponseModel> {
    private final int FFT = 3;
    private final int responseView = 10000;
    private final int AdditionalMetadataValue = 60000;
    private final String AudioFileExtension;
    private final String IAudioFileCodec;

    public KeyMigrationCallable(String string, String string2) {
        this.AudioFileExtension = string != null ? string : "";
        this.IAudioFileCodec = string2 != null ? string2 : "";
    }

    public UploadResponseModel DSP() throws Exception {
        DSP rHAjVyBgPhqkQKsOvJMPMYn2 = new DSP(this.AudioFileExtension, this.IAudioFileCodec);
        String string = new Gson().toJson((Object)rHAjVyBgPhqkQKsOvJMPMYn2);
        SSLContext sSLContext = SSLContext.getInstance("TLS");
        sSLContext.init(null, new TrustManager[]{new LraApiCallable.FFT()}, new SecureRandom());
        Client client = ClientBuilder.newBuilder().sslContext(sSLContext).hostnameVerifier((HostnameVerifier)new LraApiCallable.DSP()).build();
        UploadResponseModel dXwHeIuXrPXwwYsOazDjvwg2 = null;
        for (int i = 0; i < 3; ++i) {
            try {
                WebTarget webTarget = client.target("https://api.xivero.com:8097/").path("lra/v1.0/userMigration/");
                Invocation.Builder builder = webTarget.request(new MediaType[]{MediaType.APPLICATION_JSON_TYPE});
                builder.property("jersey.config.client.connectTimeout", (Object)10000);
                builder.property("jersey.config.client.readTimeout", (Object)60000);
                builder.header("apiKey", (Object)"dU83jmI032MW#Jd93dfsuj");
                builder.header("data", (Object)this.DSP(string));
                String string2 = (String)builder.put(Entity.entity((Object)"", (MediaType)MediaType.TEXT_PLAIN_TYPE), String.class);
                dXwHeIuXrPXwwYsOazDjvwg2 = (UploadResponseModel)new Gson().fromJson(string2, UploadResponseModel.class);
                if (dXwHeIuXrPXwwYsOazDjvwg2 == null || dXwHeIuXrPXwwYsOazDjvwg2.DSP()) continue;
                break;
            }
            catch (ProcessingException processingException) {
                // empty catch block
            }
        }
        client.close();
        return dXwHeIuXrPXwwYsOazDjvwg2;
    }

    @Override
    public /* synthetic */ Object call() throws Exception {
        return this.DSP();
    }

    private class DSP {
        private final String FFT;
        private final String responseView;

        public DSP(String string, String string2) {
            this.FFT = string;
            this.responseView = string2;
        }
    }
}

