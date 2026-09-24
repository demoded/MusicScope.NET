/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.concurrent.Callable;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public abstract class LraApiCallable<T>
implements Callable<T> {
    protected static final HashMap<String, String> DSP = new HashMap(0);

    protected String DSP(String string) {
        String string2 = string;
        for (String string3 : DSP.keySet()) {
            string2 = string2.replaceAll(string3, DSP.get(string3));
        }
        string2 = Normalizer.normalize(string2, Normalizer.Form.NFD);
        string2 = string2.replaceAll("[^\\x00-\\x7F]", "");
        return string2;
    }

    static {
        DSP.put("\u00df", "ss");
        DSP.put("\u00d6", "Oe");
        DSP.put("\u00dc", "Ue");
        DSP.put("\u00c4", "Ae");
        DSP.put("\u00f6", "oe");
        DSP.put("\u00fc", "ue");
        DSP.put("\u00e4", "ae");
    }

    protected static class DSP
    implements HostnameVerifier {
        protected DSP() {
        }

        @Override
        public boolean verify(String string, SSLSession sSLSession) {
            return true;
        }
    }

    protected static class FFT
    implements X509TrustManager {
        protected FFT() {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        @Override
        public void checkClientTrusted(X509Certificate[] x509CertificateArray, String string) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509CertificateArray, String string) throws CertificateException {
        }
    }
}

