/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jaudiotagger.audio.AudioFileFilter
 */
package sdfgjkljljoftrytrszgijpokjprs;

import com.xivero.hraa.Main;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jaudiotagger.audio.AudioFileFilter;
import sdfgjkljljoftrytrszgijpokjprs.MetadataException;
import sdfgjkljljoftrytrszgijpokjprs.TrackMeasurementModel;
import sdfgjkljljoftrytrszgijpokjprs.ArtistModel;
import sdfgjkljljoftrytrszgijpokjprs.AlbumUploadItem;
import sdfgjkljljoftrytrszgijpokjprs.FFT;
import sdfgjkljljoftrytrszgijpokjprs.IErrorCallback;
import sdfgjkljljoftrytrszgijpokjprs.UploadException;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.AlbumMeasurementModel;
import sdfgjkljljoftrytrszgijpokjprs.UploadCallable;
import sdfgjkljljoftrytrszgijpokjprs.UploadResponseModel;
import sdfgjkljljoftrytrszgijpokjprs.Settings;
import sdfgjkljljoftrytrszgijpokjprs.AlbumModel;
import sdfgjkljljoftrytrszgijpokjprs.OverallValueModel;
import sdfgjkljljoftrytrszgijpokjprs.TrackModel;
import sdfgjkljljoftrytrszgijpokjprs.AdditionalMetadataValue;
import sdfgjkljljoftrytrszgijpokjprs.IUpdatableState;
import sdfgjkljljoftrytrszgijpokjprs.KeyMigrationCallable;

public class UploadController {
    private static final Object DSP = new Object();
    private static final Object FFT = new Object();
    private static UploadController responseView;
    private final int AdditionalMetadataValue = 120;
    private final String AudioFileExtension = "MusicScope " + Main.DSP.toString();
    private final ExecutorService IAudioFileCodec = Executors.newSingleThreadExecutor();
    private final ArrayList<AlbumUploadItem> IAudioInputStream = new ArrayList(0);
    private String IAudioMetaInformation;
    private String IBaseAudioCodec;
    private String MetaInfomationCopy;

    private UploadController() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static UploadController DSP() {
        if (responseView == null) {
            Object object = DSP;
            synchronized (object) {
                if (responseView == null) {
                    responseView = new UploadController();
                }
            }
        }
        return responseView;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public AlbumUploadItem DSP(OverallValueModel hLDzMMEJpHepjqnDHxBExAn2, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA) throws MetadataException {
        final ArrayList arrayList = new ArrayList(0);
        this.DSP(yGjBevanihqaxYKnUNtrNeA, new IErrorCallback(){

            @Override
            public void DSP(String string) {
                arrayList.add(string);
            }
        });
        if (!arrayList.isEmpty()) {
            MetadataException gHZgLGQAyrKtRkmFCeaixlU = new MetadataException("Metadata not valid");
            for (String string : arrayList) {
                gHZgLGQAyrKtRkmFCeaixlU.DSP(string);
            }
            throw gHZgLGQAyrKtRkmFCeaixlU;
        }
        Object object = FFT;
        synchronized (object) {
            AlbumUploadItem lzCAJZEadLaXnGAVreeEXhK = this.FFT(hLDzMMEJpHepjqnDHxBExAn2, yGjBevanihqaxYKnUNtrNeA);
            if (lzCAJZEadLaXnGAVreeEXhK == null) {
                lzCAJZEadLaXnGAVreeEXhK = this.responseView(hLDzMMEJpHepjqnDHxBExAn2, yGjBevanihqaxYKnUNtrNeA);
            }
            if (lzCAJZEadLaXnGAVreeEXhK.FFT() || this.DSP(lzCAJZEadLaXnGAVreeEXhK)) {
                return lzCAJZEadLaXnGAVreeEXhK;
            }
            this.DSP(lzCAJZEadLaXnGAVreeEXhK, hLDzMMEJpHepjqnDHxBExAn2, yGjBevanihqaxYKnUNtrNeA);
            this.FFT(lzCAJZEadLaXnGAVreeEXhK);
            return lzCAJZEadLaXnGAVreeEXhK;
        }
    }

    public void FFT() throws UploadException {
        this.AdditionalMetadataValue();
        for (AlbumUploadItem lzCAJZEadLaXnGAVreeEXhK : this.IAudioInputStream) {
            if (lzCAJZEadLaXnGAVreeEXhK.FFT() || lzCAJZEadLaXnGAVreeEXhK.AudioFileExtension() || !this.DSP(lzCAJZEadLaXnGAVreeEXhK)) continue;
            this.DSP(lzCAJZEadLaXnGAVreeEXhK, this.IAudioMetaInformation, this.MetaInfomationCopy);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void responseView() {
        Object object = FFT;
        synchronized (object) {
            this.IAudioInputStream.clear();
        }
    }

    private AlbumUploadItem FFT(OverallValueModel hLDzMMEJpHepjqnDHxBExAn2, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA) {
        if (yGjBevanihqaxYKnUNtrNeA.BufferedAacReader() != null && hLDzMMEJpHepjqnDHxBExAn2 != null) {
            FFT qPeIwmpzLZIktKLXAJOQHcO = yGjBevanihqaxYKnUNtrNeA.BufferedAacReader();
            for (AlbumUploadItem lzCAJZEadLaXnGAVreeEXhK : this.IAudioInputStream) {
                if (lzCAJZEadLaXnGAVreeEXhK.FFT() || this.DSP(lzCAJZEadLaXnGAVreeEXhK) || !qPeIwmpzLZIktKLXAJOQHcO.AdditionalMetadataValue().FFT()) continue;
                AlbumModel fikLFfybOLBBPFzDSaJuDxU2 = lzCAJZEadLaXnGAVreeEXhK.DSP();
                boolean bl = true;
                bl &= fikLFfybOLBBPFzDSaJuDxU2.DSP().trim().toLowerCase().equals(qPeIwmpzLZIktKLXAJOQHcO.AdditionalMetadataValue().DSP().trim().toLowerCase());
                bl &= fikLFfybOLBBPFzDSaJuDxU2.AdditionalMetadataValue() == ((Integer)hLDzMMEJpHepjqnDHxBExAn2.AdditionalMetadataValue().DSP()).intValue();
                bl &= fikLFfybOLBBPFzDSaJuDxU2.responseView() == ((Integer)hLDzMMEJpHepjqnDHxBExAn2.responseView().DSP()).intValue();
                bl &= fikLFfybOLBBPFzDSaJuDxU2.FFT().equals(hLDzMMEJpHepjqnDHxBExAn2.AlacDecoderUtils());
                if (qPeIwmpzLZIktKLXAJOQHcO.IAudioInputStream().FFT()) {
                    bl &= fikLFfybOLBBPFzDSaJuDxU2.AudioFileExtension() == qPeIwmpzLZIktKLXAJOQHcO.IAudioInputStream().DSP().intValue();
                }
                if (qPeIwmpzLZIktKLXAJOQHcO.AacAudioCodec().FFT() && lzCAJZEadLaXnGAVreeEXhK.IAudioInputStream().FFT()) {
                    bl &= lzCAJZEadLaXnGAVreeEXhK.IAudioInputStream().DSP().intValue() == qPeIwmpzLZIktKLXAJOQHcO.AacAudioCodec().DSP().intValue();
                }
                if (!bl) continue;
                return lzCAJZEadLaXnGAVreeEXhK;
            }
        }
        return null;
    }

    private AlbumUploadItem responseView(OverallValueModel hLDzMMEJpHepjqnDHxBExAn2, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA) {
        FFT qPeIwmpzLZIktKLXAJOQHcO = yGjBevanihqaxYKnUNtrNeA.BufferedAacReader();
        AlbumUploadItem lzCAJZEadLaXnGAVreeEXhK = new AlbumUploadItem(new AlbumModel());
        lzCAJZEadLaXnGAVreeEXhK.DSP(hLDzMMEJpHepjqnDHxBExAn2.AlacUtils().getParent());
        lzCAJZEadLaXnGAVreeEXhK.DSP().responseView((Integer)hLDzMMEJpHepjqnDHxBExAn2.AdditionalMetadataValue().DSP());
        lzCAJZEadLaXnGAVreeEXhK.DSP().FFT((Integer)hLDzMMEJpHepjqnDHxBExAn2.responseView().DSP());
        lzCAJZEadLaXnGAVreeEXhK.DSP().FFT(hLDzMMEJpHepjqnDHxBExAn2.AlacDecoderUtils());
        if (qPeIwmpzLZIktKLXAJOQHcO.responseView().FFT()) {
            lzCAJZEadLaXnGAVreeEXhK.DSP().responseView(qPeIwmpzLZIktKLXAJOQHcO.responseView().DSP());
        }
        if (qPeIwmpzLZIktKLXAJOQHcO.AdditionalMetadataValue().FFT()) {
            lzCAJZEadLaXnGAVreeEXhK.DSP().DSP(qPeIwmpzLZIktKLXAJOQHcO.AdditionalMetadataValue().DSP());
        }
        if (qPeIwmpzLZIktKLXAJOQHcO.IAudioInputStream().FFT()) {
            lzCAJZEadLaXnGAVreeEXhK.DSP().AdditionalMetadataValue(qPeIwmpzLZIktKLXAJOQHcO.IAudioInputStream().DSP());
        } else {
            qPeIwmpzLZIktKLXAJOQHcO.IAudioInputStream(new AdditionalMetadataValue<Integer>(this.responseView(lzCAJZEadLaXnGAVreeEXhK)));
            lzCAJZEadLaXnGAVreeEXhK.DSP().AdditionalMetadataValue(qPeIwmpzLZIktKLXAJOQHcO.IAudioInputStream().DSP());
        }
        if (qPeIwmpzLZIktKLXAJOQHcO.IBaseAudioCodec().FFT()) {
            lzCAJZEadLaXnGAVreeEXhK.DSP().AudioFileExtension(qPeIwmpzLZIktKLXAJOQHcO.IBaseAudioCodec().DSP());
        }
        lzCAJZEadLaXnGAVreeEXhK.DSP(qPeIwmpzLZIktKLXAJOQHcO.MetaInfomationCopy());
        lzCAJZEadLaXnGAVreeEXhK.FFT(qPeIwmpzLZIktKLXAJOQHcO.AacAudioCodec());
        lzCAJZEadLaXnGAVreeEXhK.DSP().AdditionalMetadataValue(this.AudioFileExtension);
        this.IAudioInputStream.add(lzCAJZEadLaXnGAVreeEXhK);
        return lzCAJZEadLaXnGAVreeEXhK;
    }

    private void DSP(AlbumUploadItem lzCAJZEadLaXnGAVreeEXhK, OverallValueModel hLDzMMEJpHepjqnDHxBExAn2, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA) {
        ArtistModel iAOlbhzKtGpcbDBaJetftIG;
        FFT qPeIwmpzLZIktKLXAJOQHcO = yGjBevanihqaxYKnUNtrNeA.BufferedAacReader();
        TrackMeasurementModel hYnHthBzCblaVyhdrHDqDcm = new TrackMeasurementModel();
        hYnHthBzCblaVyhdrHDqDcm.DSP(hLDzMMEJpHepjqnDHxBExAn2.ChunkInfo());
        hYnHthBzCblaVyhdrHDqDcm.IAudioInputStream((Double)hLDzMMEJpHepjqnDHxBExAn2.AacMetaDataModel().DSP());
        hYnHthBzCblaVyhdrHDqDcm.AudioFileExtension((Double)hLDzMMEJpHepjqnDHxBExAn2.IAudioInputStream().DSP());
        hYnHthBzCblaVyhdrHDqDcm.AdditionalMetadataValue((Double)hLDzMMEJpHepjqnDHxBExAn2.IAudioMetaInformation().DSP());
        hYnHthBzCblaVyhdrHDqDcm.AacAudioCodec((Double)hLDzMMEJpHepjqnDHxBExAn2.AiffMetaDataModel().DSP());
        hYnHthBzCblaVyhdrHDqDcm.AacMetaDataModel((Double)hLDzMMEJpHepjqnDHxBExAn2.AlacAudioCodec().DSP());
        hYnHthBzCblaVyhdrHDqDcm.responseView((Double)hLDzMMEJpHepjqnDHxBExAn2.AudioFileExtension().DSP());
        hYnHthBzCblaVyhdrHDqDcm.FFT((Double)hLDzMMEJpHepjqnDHxBExAn2.IAudioFileCodec().DSP());
        hYnHthBzCblaVyhdrHDqDcm.IBaseAudioCodec((Double)hLDzMMEJpHepjqnDHxBExAn2.BufferedAacReader().DSP());
        hYnHthBzCblaVyhdrHDqDcm.MetaInfomationCopy((Double)hLDzMMEJpHepjqnDHxBExAn2.AiffAudioCodec().DSP());
        hYnHthBzCblaVyhdrHDqDcm.DSP((Double)hLDzMMEJpHepjqnDHxBExAn2.AacAudioCodec().DSP());
        hYnHthBzCblaVyhdrHDqDcm.IAudioInputStream((Double)hLDzMMEJpHepjqnDHxBExAn2.AacMetaDataModel().DSP());
        hYnHthBzCblaVyhdrHDqDcm.IAudioMetaInformation((Double)hLDzMMEJpHepjqnDHxBExAn2.AlacInputStream().DSP());
        hYnHthBzCblaVyhdrHDqDcm.IAudioFileCodec((Double)hLDzMMEJpHepjqnDHxBExAn2.AlacFile().DSP());
        hYnHthBzCblaVyhdrHDqDcm.BufferedAacReader((Double)hLDzMMEJpHepjqnDHxBExAn2.IBaseAudioCodec().DSP());
        hYnHthBzCblaVyhdrHDqDcm.AiffAudioCodec((Double)hLDzMMEJpHepjqnDHxBExAn2.MetaInfomationCopy().DSP());
        hYnHthBzCblaVyhdrHDqDcm.DSP((Integer)hLDzMMEJpHepjqnDHxBExAn2.AlacContextModel().DSP());
        if (hLDzMMEJpHepjqnDHxBExAn2.AlacMetaDataModel() != null) {
            hYnHthBzCblaVyhdrHDqDcm.FFT((Integer)hLDzMMEJpHepjqnDHxBExAn2.AlacMetaDataModel().DSP());
        }
        if (hLDzMMEJpHepjqnDHxBExAn2.AlacMetaDataModel() != null) {
            hYnHthBzCblaVyhdrHDqDcm.responseView((Integer)hLDzMMEJpHepjqnDHxBExAn2.BufferedAlacReader().DSP());
        }
        TrackModel iAZFzvHVmGVFKKhFOLoTZwF2 = new TrackModel();
        iAZFzvHVmGVFKKhFOLoTZwF2.DSP(yGjBevanihqaxYKnUNtrNeA.IAudioFileCodec());
        if (qPeIwmpzLZIktKLXAJOQHcO.FFT().FFT()) {
            iAZFzvHVmGVFKKhFOLoTZwF2.FFT(qPeIwmpzLZIktKLXAJOQHcO.FFT().DSP());
        }
        if (qPeIwmpzLZIktKLXAJOQHcO.DSP().FFT()) {
            iAZFzvHVmGVFKKhFOLoTZwF2.DSP(qPeIwmpzLZIktKLXAJOQHcO.DSP().DSP());
        }
        if (qPeIwmpzLZIktKLXAJOQHcO.IAudioMetaInformation().FFT()) {
            iAZFzvHVmGVFKKhFOLoTZwF2.DSP(qPeIwmpzLZIktKLXAJOQHcO.IAudioMetaInformation().DSP());
        }
        if (qPeIwmpzLZIktKLXAJOQHcO.IBaseAudioCodec().FFT()) {
            iAZFzvHVmGVFKKhFOLoTZwF2.FFT(qPeIwmpzLZIktKLXAJOQHcO.IBaseAudioCodec().DSP());
        }
        if (qPeIwmpzLZIktKLXAJOQHcO.AacMetaDataModel().FFT()) {
            iAZFzvHVmGVFKKhFOLoTZwF2.responseView(qPeIwmpzLZIktKLXAJOQHcO.AacMetaDataModel().DSP());
        }
        iAZFzvHVmGVFKKhFOLoTZwF2.DSP(hYnHthBzCblaVyhdrHDqDcm);
        lzCAJZEadLaXnGAVreeEXhK.DSP().DSP(iAZFzvHVmGVFKKhFOLoTZwF2);
        if (qPeIwmpzLZIktKLXAJOQHcO.AudioFileExtension() != null && qPeIwmpzLZIktKLXAJOQHcO.AudioFileExtension().FFT()) {
            for (String string : qPeIwmpzLZIktKLXAJOQHcO.AudioFileExtension().DSP()) {
                iAOlbhzKtGpcbDBaJetftIG = new ArtistModel();
                iAOlbhzKtGpcbDBaJetftIG.DSP(string);
                iAZFzvHVmGVFKKhFOLoTZwF2.DSP(iAOlbhzKtGpcbDBaJetftIG);
            }
        }
        if (qPeIwmpzLZIktKLXAJOQHcO.IAudioFileCodec() != null && qPeIwmpzLZIktKLXAJOQHcO.IAudioFileCodec().FFT()) {
            for (String string : qPeIwmpzLZIktKLXAJOQHcO.IAudioFileCodec().DSP()) {
                iAOlbhzKtGpcbDBaJetftIG = new ArtistModel();
                iAOlbhzKtGpcbDBaJetftIG.DSP(string);
                lzCAJZEadLaXnGAVreeEXhK.DSP().DSP(iAOlbhzKtGpcbDBaJetftIG);
            }
        }
    }

    private boolean DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IErrorCallback rKofDIHPRKJLQsiMCEyWXpA) {
        boolean bl = true;
        if (yGjBevanihqaxYKnUNtrNeA.BufferedAacReader() != null) {
            FFT qPeIwmpzLZIktKLXAJOQHcO = yGjBevanihqaxYKnUNtrNeA.BufferedAacReader();
            if (!qPeIwmpzLZIktKLXAJOQHcO.AdditionalMetadataValue().FFT()) {
                if (rKofDIHPRKJLQsiMCEyWXpA != null) {
                    rKofDIHPRKJLQsiMCEyWXpA.DSP("No Album Name");
                }
                bl = false;
            }
            if (!qPeIwmpzLZIktKLXAJOQHcO.DSP().FFT()) {
                if (rKofDIHPRKJLQsiMCEyWXpA != null) {
                    rKofDIHPRKJLQsiMCEyWXpA.DSP("No Trackname");
                }
                bl = false;
            }
        } else {
            if (rKofDIHPRKJLQsiMCEyWXpA != null) {
                rKofDIHPRKJLQsiMCEyWXpA.DSP("No Metadata!");
            }
            bl = false;
        }
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void DSP(AlbumUploadItem lzCAJZEadLaXnGAVreeEXhK, String string, String string2) throws UploadException {
        Object object = DSP;
        synchronized (object) {
            UploadCallable dXJPyPaUBgexSaefEizNJCt2 = new UploadCallable(lzCAJZEadLaXnGAVreeEXhK, string, string2);
            Future<UploadResponseModel> future = this.IAudioFileCodec.submit(dXJPyPaUBgexSaefEizNJCt2);
            try {
                UploadResponseModel dXwHeIuXrPXwwYsOazDjvwg2 = future.get(120L, TimeUnit.SECONDS);
                if (dXwHeIuXrPXwwYsOazDjvwg2 == null || dXwHeIuXrPXwwYsOazDjvwg2.DSP()) {
                    throw new UploadException("Upload failed", dXwHeIuXrPXwwYsOazDjvwg2);
                }
            }
            catch (InterruptedException | ExecutionException exception) {
                Logger.getLogger(UploadController.class.getName()).log(Level.SEVERE, null, exception);
            }
            catch (TimeoutException timeoutException) {
                for (IUpdatableState nszTqDkxYiaGpuKyqsPMpEP2 : lzCAJZEadLaXnGAVreeEXhK.IAudioFileCodec()) {
                    nszTqDkxYiaGpuKyqsPMpEP2.DSP("Upload failed: Timeout");
                }
                throw new UploadException("Upload failed: Timeout");
            }
        }
    }

    private boolean DSP(AlbumUploadItem lzCAJZEadLaXnGAVreeEXhK) {
        if (lzCAJZEadLaXnGAVreeEXhK.FFT()) {
            return false;
        }
        AlbumModel fikLFfybOLBBPFzDSaJuDxU2 = lzCAJZEadLaXnGAVreeEXhK.DSP();
        boolean bl = true;
        return bl &= fikLFfybOLBBPFzDSaJuDxU2.AudioFileExtension() == fikLFfybOLBBPFzDSaJuDxU2.IAudioFileCodec().size() || this.responseView(lzCAJZEadLaXnGAVreeEXhK) == fikLFfybOLBBPFzDSaJuDxU2.IAudioFileCodec().size();
    }

    private void FFT(AlbumUploadItem lzCAJZEadLaXnGAVreeEXhK) {
        int n = 0;
        double d = 0.0;
        boolean bl = true;
        for (TrackModel iAZFzvHVmGVFKKhFOLoTZwF2 : lzCAJZEadLaXnGAVreeEXhK.DSP().IAudioFileCodec()) {
            double d2 = iAZFzvHVmGVFKKhFOLoTZwF2.FFT().DSP();
            d += Math.pow(10.0, d2 / 20.0);
            bl &= iAZFzvHVmGVFKKhFOLoTZwF2.FFT().FFT() == 0;
            bl &= iAZFzvHVmGVFKKhFOLoTZwF2.FFT().responseView() == 0;
            n = (int)((long)n + iAZFzvHVmGVFKKhFOLoTZwF2.DSP());
        }
        d = 20.0 * Math.log10(d / (double)lzCAJZEadLaXnGAVreeEXhK.DSP().IAudioFileCodec().size());
        AlbumMeasurementModel cxgXPZvqmsDtmYhggfCRwiz2 = new AlbumMeasurementModel();
        cxgXPZvqmsDtmYhggfCRwiz2.DSP(d);
        cxgXPZvqmsDtmYhggfCRwiz2.DSP(bl);
        lzCAJZEadLaXnGAVreeEXhK.DSP().DSP(cxgXPZvqmsDtmYhggfCRwiz2);
        lzCAJZEadLaXnGAVreeEXhK.DSP().DSP(n);
    }

    private int responseView(AlbumUploadItem lzCAJZEadLaXnGAVreeEXhK) {
        File file = lzCAJZEadLaXnGAVreeEXhK.AdditionalMetadataValue().toFile();
        File[] fileArray = file.listFiles((FileFilter)new AudioFileFilter(false));
        return fileArray.length;
    }

    private boolean AdditionalMetadataValue() {
        if (!(this.IAudioMetaInformation == null || this.IAudioMetaInformation.isEmpty() || this.IBaseAudioCodec == null || this.IBaseAudioCodec.isEmpty() || Boolean.valueOf(Settings.DSP().DSP("LegacyKeyMigrated")).booleanValue())) {
            KeyMigrationCallable uFWXZinbbQlkgrlMUMYpWLl2 = new KeyMigrationCallable(this.IAudioMetaInformation, this.IBaseAudioCodec);
            Future<UploadResponseModel> future = this.IAudioFileCodec.submit(uFWXZinbbQlkgrlMUMYpWLl2);
            try {
                UploadResponseModel dXwHeIuXrPXwwYsOazDjvwg2 = future.get(120L, TimeUnit.SECONDS);
                if (dXwHeIuXrPXwwYsOazDjvwg2 == null || dXwHeIuXrPXwwYsOazDjvwg2.DSP()) {
                    Settings.DSP().DSP("LegacyKeyMigrated", Boolean.FALSE.toString());
                    return false;
                }
                if (dXwHeIuXrPXwwYsOazDjvwg2.FFT() != null) {
                    List<Integer> list = dXwHeIuXrPXwwYsOazDjvwg2.responseView();
                    boolean bl = list.contains(15200);
                    boolean bl2 = list.contains(15208);
                    if (bl || bl2) {
                        Settings.DSP().DSP("LegacyKeyMigrated", Boolean.TRUE.toString());
                        return true;
                    }
                }
            }
            catch (InterruptedException | ExecutionException | TimeoutException exception) {
                Settings.DSP().DSP("LegacyKeyMigrated", Boolean.FALSE.toString());
            }
        }
        return false;
    }
}

