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
import sdfgjkljljoftrytrszgijpokjprs.AlbumUploadItem;
import sdfgjkljljoftrytrszgijpokjprs.LraApiCallable;
import sdfgjkljljoftrytrszgijpokjprs.UploadResponseModel;
import sdfgjkljljoftrytrszgijpokjprs.AlbumModel;
import sdfgjkljljoftrytrszgijpokjprs.JsonAlbum;
import sdfgjkljljoftrytrszgijpokjprs.IUpdatableState;

class UploadCallable
extends LraApiCallable<UploadResponseModel> {
    private final int FFT = 3;
    private final int responseView = 10000;
    private final int AdditionalMetadataValue = 60000;
    private final AlbumUploadItem AudioFileExtension;
    private final String IAudioFileCodec;
    private final String IAudioInputStream;

    UploadCallable(AlbumUploadItem lzCAJZEadLaXnGAVreeEXhK, String string, String string2) {
        this.AudioFileExtension = lzCAJZEadLaXnGAVreeEXhK;
        this.IAudioFileCodec = string;
        this.IAudioInputStream = string2;
    }

    public UploadResponseModel DSP() throws Exception {
        String string = "Uploading...";
        this.AudioFileExtension.FFT(true);
        this.FFT(string);
        String string2 = this.DSP(this.AudioFileExtension.DSP());
        SSLContext sSLContext = SSLContext.getInstance("TLS");
        sSLContext.init(null, new TrustManager[]{new LraApiCallable.FFT()}, new SecureRandom());
        Client client = ClientBuilder.newBuilder().sslContext(sSLContext).hostnameVerifier((HostnameVerifier)new LraApiCallable.DSP()).build();
        UploadResponseModel dXwHeIuXrPXwwYsOazDjvwg2 = null;
        for (int i = 0; i < 3; ++i) {
            try {
                String string3;
                WebTarget processingException = client.target("https://api.xivero.com:8097/").path("lra/v1.0/albumsView/" + this.IAudioFileCodec + "/");
                Invocation.Builder builder = processingException.request(new MediaType[]{MediaType.APPLICATION_JSON_TYPE});
                builder.property("jersey.config.client.connectTimeout", (Object)10000);
                builder.property("jersey.config.client.readTimeout", (Object)60000);
                builder.header("apiKey", (Object)"dU83jmI032MW#Jd93dfsuj");
                builder.header("data", (Object)this.DSP(string2));
                if (this.IAudioInputStream != null && !this.IAudioInputStream.isEmpty()) {
                    builder.header("recoveryKey", (Object)this.IAudioInputStream);
                }
                if ((dXwHeIuXrPXwwYsOazDjvwg2 = (UploadResponseModel)new Gson().fromJson(string3 = (String)builder.post(Entity.entity((Object)"", (MediaType)MediaType.TEXT_PLAIN_TYPE), String.class), UploadResponseModel.class)) == null) continue;
                this.AudioFileExtension.DSP(dXwHeIuXrPXwwYsOazDjvwg2);
                this.AudioFileExtension.DSP(!dXwHeIuXrPXwwYsOazDjvwg2.DSP());
                string = "Upload finished";
                break;
            }
            catch (ProcessingException processingException) {
                string = "Upload timeout";
            }
        }
        if (this.AudioFileExtension.responseView().DSP()) {
            string = "Metadata not valid";
        }
        for (IUpdatableState nszTqDkxYiaGpuKyqsPMpEP2 : this.AudioFileExtension.IAudioFileCodec()) {
            nszTqDkxYiaGpuKyqsPMpEP2.DSP(string);
        }
        client.close();
        this.AudioFileExtension.FFT(false);
        return dXwHeIuXrPXwwYsOazDjvwg2;
    }

    private String DSP(AlbumModel fikLFfybOLBBPFzDSaJuDxU2) {
        return new Gson().toJson((Object)new JsonAlbum(fikLFfybOLBBPFzDSaJuDxU2));
    }

    private void FFT(String string) {
        for (IUpdatableState nszTqDkxYiaGpuKyqsPMpEP2 : this.AudioFileExtension.IAudioFileCodec()) {
            nszTqDkxYiaGpuKyqsPMpEP2.DSP(string);
        }
    }

    @Override
    public /* synthetic */ Object call() throws Exception {
        return this.DSP();
    }
}

