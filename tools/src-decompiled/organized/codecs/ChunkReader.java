/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdfgjkljljoftrytrszgijpokjprs.OutOfChunkRangeException;
import sdfgjkljljoftrytrszgijpokjprs.IChunkReader;

public class ChunkReader
implements IChunkReader {
    private final RandomAccessFile DSP;
    private final long FFT;
    private final long responseView;

    public ChunkReader(File file) throws FileNotFoundException, IOException {
        this.DSP = new RandomAccessFile(file, "r");
        this.FFT = 0L;
        this.responseView = this.DSP.length();
    }

    public ChunkReader(ChunkReader rBEDoDrXEedwSjzkgSRdKqV, long l) {
        this.DSP = rBEDoDrXEedwSjzkgSRdKqV.IAudioInputStream();
        this.FFT = rBEDoDrXEedwSjzkgSRdKqV.AdditionalMetadataValue();
        this.responseView = l;
    }

    public long DSP() {
        return this.responseView;
    }

    public long FFT() {
        return this.DSP() - this.AudioFileExtension();
    }

    public boolean responseView() {
        return this.FFT() > 1L;
    }

    public void DSP(long l) {
        try {
            long l2 = l;
            l2 = l2 < 0L ? 0L : l2;
            l2 = l2 >= this.DSP.length() ? this.DSP.length() - 1L : l2;
            this.DSP.seek(l2);
        }
        catch (IOException iOException) {
            Logger.getLogger(ChunkReader.class.getName()).log(Level.SEVERE, null, iOException);
        }
    }

    @Override
    public ByteBuffer DSP(int n) throws OutOfChunkRangeException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(n);
        ByteBuffer byteBuffer2 = this.FFT(n);
        for (int i = byteBuffer2.limit() - 1; i >= 0; --i) {
            byteBuffer.put(byteBuffer2.get(i));
        }
        byteBuffer.rewind();
        return byteBuffer;
    }

    @Override
    public ByteBuffer FFT(int n) throws OutOfChunkRangeException {
        try {
            if (this.DSP.getFilePointer() + (long)n <= this.FFT + this.responseView) {
                byte[] byArray = new byte[n];
                this.DSP.readFully(byArray);
                return ByteBuffer.wrap(byArray);
            }
            throw new OutOfChunkRangeException(this.FFT, this.responseView, n);
        }
        catch (IOException iOException) {
            Logger.getLogger(ChunkReader.class.getName()).log(Level.SEVERE, null, iOException);
            return ByteBuffer.allocate(n);
        }
    }

    @Override
    public long AdditionalMetadataValue() {
        try {
            return this.DSP.getFilePointer();
        }
        catch (IOException iOException) {
            Logger.getLogger(ChunkReader.class.getName()).log(Level.SEVERE, null, iOException);
            return -1L;
        }
    }

    public long AudioFileExtension() {
        try {
            long l = this.DSP.getFilePointer();
            return l - this.FFT;
        }
        catch (IOException iOException) {
            Logger.getLogger(ChunkReader.class.getName()).log(Level.SEVERE, null, iOException);
            return -1L;
        }
    }

    private RandomAccessFile IAudioInputStream() {
        return this.DSP;
    }

    public void IAudioFileCodec() {
        if (this.DSP != null) {
            try {
                this.DSP.close();
            }
            catch (IOException iOException) {
                Logger.getLogger(ChunkReader.class.getName()).log(Level.SEVERE, null, iOException);
            }
        }
    }
}

