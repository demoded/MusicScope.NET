/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

class HostnameManager
implements HostnameVerifier {
    HostnameManager() {
    }

    @Override
    public boolean verify(String string, SSLSession ssls) {
        return true;
    }
}

