/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TemporaryResourceManager
extends TimerTask {
    private final Path DSP;
    private final long FFT = 60000L;
    private ConcurrentLinkedQueue<DSP> responseView;

    public TemporaryResourceManager(Path path) {
        this.DSP = path;
        this.responseView = new ConcurrentLinkedQueue();
        Timer timer = new Timer();
        timer.schedule((TimerTask)this, 60000L, 60000L);
    }

    public static Path DSP(String string) throws IOException {
        return Files.createTempDirectory(string, new FileAttribute[0]);
    }

    public synchronized void DSP() {
        long l = System.currentTimeMillis();
        for (DSP rHAjVyBgPhqkQKsOvJMPMYn2 : this.responseView) {
            long l2 = rHAjVyBgPhqkQKsOvJMPMYn2.FFT() + 60000L;
            if (l2 >= l && rHAjVyBgPhqkQKsOvJMPMYn2.DSP().exists()) continue;
            rHAjVyBgPhqkQKsOvJMPMYn2.DSP().delete();
            this.responseView.remove(rHAjVyBgPhqkQKsOvJMPMYn2);
        }
    }

    @Override
    public void run() {
        this.DSP();
    }

    private class DSP {
        private final File DSP;
        private final long FFT;

        public File DSP() {
            return this.DSP;
        }

        public long FFT() {
            return this.FFT;
        }

        public boolean equals(Object object) {
            if (object instanceof DSP) {
                DSP rHAjVyBgPhqkQKsOvJMPMYn2 = (DSP)object;
                return rHAjVyBgPhqkQKsOvJMPMYn2.hashCode() == this.hashCode();
            }
            return false;
        }

        public int hashCode() {
            int n = 7;
            n = 47 * n + Objects.hashCode(this.DSP.getAbsolutePath());
            return n;
        }
    }
}

