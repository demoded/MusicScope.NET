/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import sdfgjkljljoftrytrszgijpokjprs.ComputationSubject;
import sdfgjkljljoftrytrszgijpokjprs.AbstractComputationModule;
import sdfgjkljljoftrytrszgijpokjprs.ComputationObjects;
import sdfgjkljljoftrytrszgijpokjprs.ChecksumModel;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;
import sdfgjkljljoftrytrszgijpokjprs.IValueReporting;
import sdfgjkljljoftrytrszgijpokjprs.ITrackLoadedListener;

@ComputationSubject(DSP=ComputationController.DSP.Checksum)
public class ChecksumModule
extends AbstractComputationModule<ChecksumModel>
implements IValueReporting<ChecksumModel>,
ITrackLoadedListener {
    private MessageDigest DSP;
    private ChecksumModel FFT;

    public ChecksumModule() {
        try {
            this.DSP = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Logger.getLogger(ChecksumModule.class.getName()).log(Level.SEVERE, null, noSuchAlgorithmException);
        }
        this.FFT = new ChecksumModel();
    }

    public ChecksumModel responseView() throws Exception {
        Thread.currentThread().setName(this.getClass().getSimpleName());
        byte[] byArray = new byte[8];
        ComputationObjects pYJBgzPCdrZnekQfrnwIPxJ = this.DSP();
        AudioSampleModel zvOTVUaKFTNpNSbbMYZfoXf = pYJBgzPCdrZnekQfrnwIPxJ.responseView();
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        for (int i = 0; i < zvOTVUaKFTNpNSbbMYZfoXf.AdditionalMetadataValue(); ++i) {
            double d = zvOTVUaKFTNpNSbbMYZfoXf.responseView()[i];
            ByteBuffer.wrap(byArray).putDouble(d);
            this.DSP.update(byArray);
            double d2 = zvOTVUaKFTNpNSbbMYZfoXf.FFT()[i];
            ByteBuffer.wrap(byArray).putDouble(d2);
            this.DSP.update(byArray);
        }
        this.FFT.DSP(this.DSP(this.DSP.digest()));
        return this.FFT;
    }

    public ChecksumModel AdditionalMetadataValue() {
        return this.FFT;
    }

    @Override
    public void DSP(String string) {
        try {
            this.DSP = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Logger.getLogger(ChecksumModule.class.getName()).log(Level.SEVERE, null, noSuchAlgorithmException);
        }
        this.FFT = new ChecksumModel();
    }

    private String DSP(byte[] byArray) {
        return new HexBinaryAdapter().marshal(byArray);
    }

    @Override
    public /* synthetic */ Object FFT() throws Exception {
        return this.responseView();
    }

    @Override
    public /* synthetic */ Object AudioFileExtension() {
        return this.AdditionalMetadataValue();
    }
}

