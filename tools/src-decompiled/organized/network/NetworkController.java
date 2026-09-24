/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import sdfgjkljljoftrytrszgijpokjprs.NetworkConnection;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerControl;
import sdfgjkljljoftrytrszgijpokjprs.IConnectionsListener;

public class NetworkController {
    private static final Object DSP = new Object();
    private static NetworkController FFT;
    private int responseView = 1;
    private int AdditionalMetadataValue;
    private InetAddress AudioFileExtension;
    private final LinkedBlockingQueue<NetworkConnection> IAudioFileCodec = new LinkedBlockingQueue();
    private ServerSocket IAudioInputStream;
    private IConnectionsListener IAudioMetaInformation;
    private Thread IBaseAudioCodec;
    private Timer MetaInfomationCopy;
    private IPlayerControl AacAudioCodec = null;

    private NetworkController() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NetworkController DSP() {
        if (FFT == null) {
            Object object = DSP;
            synchronized (object) {
                if (FFT == null) {
                    FFT = new NetworkController();
                }
            }
        }
        return FFT;
    }

    public synchronized void FFT() throws IOException {
        if (this.IBaseAudioCodec == null || this.IBaseAudioCodec != null && !this.IBaseAudioCodec.isAlive()) {
            this.IAudioFileCodec.clear();
            this.IAudioInputStream = new ServerSocket(this.AdditionalMetadataValue, this.responseView, this.AudioFileExtension);
            this.IAudioInputStream.setReuseAddress(true);
            this.IBaseAudioCodec = new Thread(new Runnable(){

                @Override
                public void run() {
                    while (!NetworkController.this.IAudioInputStream.isClosed() && !Thread.currentThread().isInterrupted()) {
                        try {
                            Socket socket = NetworkController.this.IAudioInputStream.accept();
                            if (NetworkController.this.IAudioFileCodec.size() <= NetworkController.this.responseView) {
                                socket.setKeepAlive(true);
                                NetworkController.this.IAudioFileCodec.add(new NetworkConnection(socket, NetworkController.this.AacAudioCodec));
                                if (NetworkController.this.IAudioMetaInformation == null) continue;
                                NetworkController.this.IAudioMetaInformation.DSP();
                                continue;
                            }
                            socket.close();
                        }
                        catch (IOException iOException) {}
                    }
                }
            });
            this.MetaInfomationCopy = new Timer();
            this.MetaInfomationCopy.schedule(new TimerTask(){

                @Override
                public void run() {
                    for (NetworkConnection rnvNtSEHUfpSqfWkQXegJHH2 : NetworkController.this.IAudioFileCodec) {
                        if (rnvNtSEHUfpSqfWkQXegJHH2.FFT()) continue;
                        NetworkController.this.IAudioFileCodec.remove(rnvNtSEHUfpSqfWkQXegJHH2);
                        if (NetworkController.this.IAudioMetaInformation == null) continue;
                        NetworkController.this.IAudioMetaInformation.DSP();
                    }
                }
            }, 1000L, 1000L);
            this.IBaseAudioCodec.start();
        }
    }

    public synchronized void responseView() {
        if (this.IBaseAudioCodec != null && this.IBaseAudioCodec.isAlive()) {
            this.MetaInfomationCopy.cancel();
            this.MetaInfomationCopy = null;
            this.IBaseAudioCodec.interrupt();
            try {
                for (NetworkConnection rnvNtSEHUfpSqfWkQXegJHH2 : this.IAudioFileCodec) {
                    rnvNtSEHUfpSqfWkQXegJHH2.responseView();
                }
                this.IAudioInputStream.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
            this.IBaseAudioCodec = null;
        }
    }

    public void AdditionalMetadataValue() {
        try {
            if (this.IBaseAudioCodec != null) {
                this.responseView();
                this.FFT();
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void DSP(IConnectionsListener vrHTMcQBPuMwPqWnJJpGyXF2) {
        this.IAudioMetaInformation = vrHTMcQBPuMwPqWnJJpGyXF2;
    }

    public void DSP(int n) {
        if (this.AdditionalMetadataValue != n) {
            this.AdditionalMetadataValue = n;
            this.AdditionalMetadataValue();
        }
    }

    public void DSP(IPlayerControl tSwCDjQKHwQVvbvetpZIATg2) {
        this.AacAudioCodec = tSwCDjQKHwQVvbvetpZIATg2;
    }
}

