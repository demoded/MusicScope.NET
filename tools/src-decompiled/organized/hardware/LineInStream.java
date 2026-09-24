/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import sdfgjkljljoftrytrszgijpokjprs.AudioInput;
import sdfgjkljljoftrytrszgijpokjprs.LineInMetaDataModel;
import sdfgjkljljoftrytrszgijpokjprs.IAudioInputStream;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;

public class LineInStream
implements IAudioInputStream {
    private AudioInput DSP = null;

    public LineInStream(AudioInput knBEVYWejFGxtgEiJtPubpZ) {
        this.DSP = knBEVYWejFGxtgEiJtPubpZ;
    }

    @Override
    public IAudioInputStream DSP() throws Exception {
        return new LineInStream(this.DSP);
    }

    @Override
    public int DSP(byte[] byArray, int n, int n2) {
        try {
            if (this.DSP != null && !this.DSP.responseView()) {
                this.DSP.DSP();
            }
            return this.DSP.DSP(byArray, n, n2);
        }
        catch (IOException | LineUnavailableException exception) {
            Logger.getLogger(LineInStream.class.getName()).log(Level.SEVERE, null, exception);
            return 0;
        }
    }

    @Override
    public IAudioMetaInformation FFT() {
        return new LineInMetaDataModel(this.DSP.AdditionalMetadataValue(), Long.MAX_VALUE);
    }

    @Override
    public IAudioMetaInformation responseView() {
        return this.FFT();
    }

    @Override
    public void AdditionalMetadataValue() {
        if (this.DSP != null) {
            try {
                this.DSP.FFT();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }
}

