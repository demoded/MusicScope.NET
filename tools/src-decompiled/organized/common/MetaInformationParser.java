/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdfgjkljljoftrytrszgijpokjprs.AbstractMetaDataModel;
import sdfgjkljljoftrytrszgijpokjprs.SubChunk;
import sdfgjkljljoftrytrszgijpokjprs.ChunkAttribute;

public class MetaInformationParser<B extends AbstractMetaDataModel, R extends B> {
    private final Class<R> DSP;
    private final HashMap<String, DSP<B>> FFT;
    private final int responseView;

    public MetaInformationParser(Class<B> clazz, Class<R> clazz2) {
        this.DSP = clazz2;
        this.responseView = this.DSP(clazz);
        this.FFT = new HashMap(0);
    }

    public Set<String> DSP() {
        return this.FFT.keySet();
    }

    public long DSP(String string) {
        DSP<B> rHAjVyBgPhqkQKsOvJMPMYn2 = this.FFT.get(string);
        return ((AbstractMetaDataModel)rHAjVyBgPhqkQKsOvJMPMYn2.FFT).FFT();
    }

    public long FFT() {
        long l = 0L;
        l += (long)this.responseView;
        l += (long)this.DSP(this.DSP);
        for (String string : this.DSP()) {
            l += this.DSP(string) % 2L == 0L ? 0L : 1L;
            l += this.DSP(string);
            l += (long)this.responseView;
        }
        return l;
    }

    public void DSP(R r, RandomAccessFile randomAccessFile) throws IOException {
        Field[] fieldArray;
        int n = this.DSP(r.getClass());
        ByteBuffer byteBuffer = this.DSP(0L, this.responseView, randomAccessFile);
        ByteBuffer byteBuffer2 = this.DSP(this.responseView, n, randomAccessFile);
        r.DSP(byteBuffer);
        r.FFT(byteBuffer2);
        this.DSP(n + this.responseView, randomAccessFile);
        Class<?> clazz = r.getClass();
        for (Field field : fieldArray = clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object object = field.get(r);
                Class<?> clazz2 = object.getClass();
                SubChunk yKTWEeEoGctvADZOqBEefOZ = clazz2.getAnnotation(SubChunk.class);
                if (yKTWEeEoGctvADZOqBEefOZ == null) continue;
                String string = yKTWEeEoGctvADZOqBEefOZ.DSP();
                boolean bl = yKTWEeEoGctvADZOqBEefOZ.FFT();
                long l = yKTWEeEoGctvADZOqBEefOZ.responseView();
                DSP<B> rHAjVyBgPhqkQKsOvJMPMYn2 = this.FFT.get(string);
                if (rHAjVyBgPhqkQKsOvJMPMYn2 == null) continue;
                AbstractMetaDataModel dzeHJEjXSeqlfumFByehgcX = (AbstractMetaDataModel)object;
                rHAjVyBgPhqkQKsOvJMPMYn2.DSP.position(0);
                dzeHJEjXSeqlfumFByehgcX.DSP(rHAjVyBgPhqkQKsOvJMPMYn2.DSP);
                dzeHJEjXSeqlfumFByehgcX.DSP(((AbstractMetaDataModel)rHAjVyBgPhqkQKsOvJMPMYn2.FFT).DSP());
                if (!bl) continue;
                dzeHJEjXSeqlfumFByehgcX.FFT(this.DSP(((AbstractMetaDataModel)rHAjVyBgPhqkQKsOvJMPMYn2.FFT).DSP(), (int)Math.min(((AbstractMetaDataModel)rHAjVyBgPhqkQKsOvJMPMYn2.FFT).FFT(), l), randomAccessFile));
            }
            catch (IllegalAccessException | IllegalArgumentException | SecurityException exception) {
                Logger.getLogger(MetaInformationParser.class.getName()).log(Level.SEVERE, null, exception);
            }
        }
    }

    private ByteBuffer DSP(long l, int n, RandomAccessFile randomAccessFile) throws IOException {
        byte[] byArray = new byte[n];
        randomAccessFile.seek(l);
        int n2 = randomAccessFile.read(byArray);
        if (n2 > 0) {
            return ByteBuffer.wrap(byArray);
        }
        return null;
    }

    private void DSP(long l, RandomAccessFile randomAccessFile) throws IOException {
        ByteBuffer byteBuffer;
        long l2 = l;
        while ((byteBuffer = this.DSP(l2, this.responseView, randomAccessFile)) != null) {
            try {
                AbstractMetaDataModel dzeHJEjXSeqlfumFByehgcX = (AbstractMetaDataModel)this.DSP.newInstance();
                dzeHJEjXSeqlfumFByehgcX.DSP(byteBuffer);
                dzeHJEjXSeqlfumFByehgcX.DSP(l2 + (long)this.responseView);
                if (dzeHJEjXSeqlfumFByehgcX.FFT() <= 0L) break;
                if (!this.FFT.containsKey(new String(dzeHJEjXSeqlfumFByehgcX.responseView()))) {
                    this.FFT.put(new String(dzeHJEjXSeqlfumFByehgcX.responseView()), new DSP<AbstractMetaDataModel>(byteBuffer, dzeHJEjXSeqlfumFByehgcX));
                }
                l2 += (long)this.responseView;
                l2 += dzeHJEjXSeqlfumFByehgcX.FFT();
                l2 += dzeHJEjXSeqlfumFByehgcX.FFT() % 2L == 0L ? 0L : 1L;
            }
            catch (IllegalAccessException | InstantiationException reflectiveOperationException) {
                Logger.getLogger(MetaInformationParser.class.getName()).log(Level.SEVERE, null, reflectiveOperationException);
            }
        }
    }

    public int DSP(Class<?> clazz) {
        Field[] fieldArray;
        int n = 0;
        for (Field field : fieldArray = clazz.getDeclaredFields()) {
            ChunkAttribute zDSaXekyHHNqfExESErZUQI = field.getAnnotation(ChunkAttribute.class);
            if (zDSaXekyHHNqfExESErZUQI == null) continue;
            n += zDSaXekyHHNqfExESErZUQI.DSP();
        }
        return n;
    }

    private class DSP<B> {
        protected final ByteBuffer DSP;
        protected final B FFT;

        DSP(ByteBuffer byteBuffer, B b) {
            this.DSP = byteBuffer;
            this.FFT = b;
        }
    }
}

