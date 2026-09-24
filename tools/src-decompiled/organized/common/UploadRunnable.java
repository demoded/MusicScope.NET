/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import sdfgjkljljoftrytrszgijpokjprs.UploadException;
import sdfgjkljljoftrytrszgijpokjprs.BatchItem;
import sdfgjkljljoftrytrszgijpokjprs.BatchController;
import sdfgjkljljoftrytrszgijpokjprs.UploadController;

class UploadRunnable
implements Runnable {
    private final BatchItem DSP;
    private final BatchController FFT;

    UploadRunnable(BatchItem gDUqxotmDwrsAvlIwIEIdto2, BatchController kfrVEsKUvFeBfAoSkvCCRmV2) {
        this.DSP = gDUqxotmDwrsAvlIwIEIdto2;
        this.FFT = kfrVEsKUvFeBfAoSkvCCRmV2;
    }

    @Override
    public void run() {
        UploadController rqQjEReeYEfXFwAZlPAcXvt2 = UploadController.DSP();
        try {
            rqQjEReeYEfXFwAZlPAcXvt2.FFT();
        }
        catch (UploadException wMyLcuHrEpLWCkBbcPuIVEh) {
            this.DSP.DSP(wMyLcuHrEpLWCkBbcPuIVEh.getMessage());
        }
        this.FFT.fireTableDataChanged();
    }
}

