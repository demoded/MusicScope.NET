/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import sdfgjkljljoftrytrszgijpokjprs.ISocketDataListener;
import sdfgjkljljoftrytrszgijpokjprs.SocketReader;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerControl;

public class NetworkConnection
implements ISocketDataListener {
    private Socket DSP;
    private final SocketReader FFT;
    private final Thread responseView;
    private boolean AdditionalMetadataValue;
    private double AudioFileExtension = -1.0;
    private final BlockingQueue<DSP> IAudioFileCodec;

    public NetworkConnection(Socket socket, IPlayerControl tSwCDjQKHwQVvbvetpZIATg2) throws IOException {
        this.DSP = socket;
        this.FFT = new SocketReader(socket, this, tSwCDjQKHwQVvbvetpZIATg2);
        this.responseView = new Thread(this.FFT);
        this.IAudioFileCodec = new ArrayBlockingQueue<DSP>(10);
        this.responseView.start();
    }

    public boolean FFT() {
        return this.AudioFileExtension > 0.0 && this.DSP != null && !this.DSP.isClosed();
    }

    @Override
    public void DSP(double d) {
        this.AudioFileExtension = d;
    }

    @Override
    public void DSP(byte[] byArray) {
        if (this.AdditionalMetadataValue) {
            this.IAudioFileCodec.offer(new DSP(byArray, byArray.length));
        }
    }

    public void responseView() throws IOException {
        this.AdditionalMetadataValue = false;
    }

    public String toString() {
        return this.DSP.getLocalAddress().getCanonicalHostName();
    }

    @Override
    public void DSP() {
        this.DSP = null;
    }

    private class DSP {
        private final byte[] FFT;
        private final int responseView;

        public DSP(byte[] byArray, int n) {
            this.FFT = byArray;
            this.responseView = n;
        }
    }
}

