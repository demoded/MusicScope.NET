/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.ISocketDataListener;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerControl;

public class SocketReader
implements Runnable {
    private static final int DSP = 1000 * BitsPerSample.IAudioFileCodec.FFT();
    private final Socket FFT;
    private final BufferedInputStream responseView;
    private final ISocketDataListener AdditionalMetadataValue;
    private final IPlayerControl AudioFileExtension;
    private char IAudioFileCodec;
    private int IAudioInputStream;
    private final byte[] IAudioMetaInformation = new byte[DSP];
    private final byte[] IBaseAudioCodec = new byte[5];
    private ByteBuffer MetaInfomationCopy;

    public SocketReader(Socket socket, ISocketDataListener lSAvoQmvVEpPeurnfJpgDzj, IPlayerControl tSwCDjQKHwQVvbvetpZIATg2) throws IOException {
        this.FFT = socket;
        this.AdditionalMetadataValue = lSAvoQmvVEpPeurnfJpgDzj;
        this.AudioFileExtension = tSwCDjQKHwQVvbvetpZIATg2;
        this.responseView = new BufferedInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        try {
            while (!this.FFT.isClosed()) {
                Thread.currentThread().setPriority(10);
                this.responseView.read(this.IBaseAudioCodec, 0, 1);
                this.responseView.read(this.IBaseAudioCodec, 1, 4);
                this.MetaInfomationCopy = ByteBuffer.wrap(this.IBaseAudioCodec);
                this.IAudioFileCodec = (char)this.MetaInfomationCopy.order(ByteOrder.LITTLE_ENDIAN).get();
                this.IAudioInputStream = this.MetaInfomationCopy.order(ByteOrder.LITTLE_ENDIAN).getInt();
                if (this.IAudioFileCodec == 'E') {
                    this.responseView.close();
                    this.FFT.close();
                    if (this.AudioFileExtension == null) continue;
                    this.AudioFileExtension.DSP();
                    continue;
                }
                int n = this.IAudioInputStream = this.IAudioInputStream < 0 ? 1 : this.IAudioInputStream;
                while (this.IAudioInputStream > 0) {
                    int n2 = this.responseView.read(this.IAudioMetaInformation, 0, this.IAudioInputStream > DSP ? DSP : this.IAudioInputStream);
                    if (n2 <= 0) continue;
                    this.IAudioInputStream -= n2;
                    if (this.AdditionalMetadataValue == null) continue;
                    switch (this.IAudioFileCodec) {
                        case 'I': {
                            this.MetaInfomationCopy = ByteBuffer.wrap(this.IAudioMetaInformation, 0, n2);
                            this.AdditionalMetadataValue.DSP(this.MetaInfomationCopy.order(ByteOrder.LITTLE_ENDIAN).getDouble());
                            this.MetaInfomationCopy.clear();
                            this.MetaInfomationCopy = null;
                            break;
                        }
                        case 'S': {
                            this.AdditionalMetadataValue.DSP(Arrays.copyOfRange(this.IAudioMetaInformation, 0, n2));
                        }
                    }
                }
            }
        }
        catch (IOException iOException) {
        }
        finally {
            try {
                this.responseView.close();
                this.FFT.close();
                this.AdditionalMetadataValue.DSP();
            }
            catch (IOException iOException) {}
        }
    }
}

