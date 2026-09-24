/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.Files
 */
package sdfgjkljljoftrytrszgijpokjprs;

import com.google.common.io.Files;
import com.xivero.hraa.Main;
import java.awt.FileDialog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import sdfgjkljljoftrytrszgijpokjprs.OverallValueComposer;
import sdfgjkljljoftrytrszgijpokjprs.DragAndDropListener;
import sdfgjkljljoftrytrszgijpokjprs.ReportingController;
import sdfgjkljljoftrytrszgijpokjprs.MetadataException;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.BatchListRenderer;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerEventListener;
import sdfgjkljljoftrytrszgijpokjprs.IBatchListSizeListener;
import sdfgjkljljoftrytrszgijpokjprs.AlbumUploadItem;
import sdfgjkljljoftrytrszgijpokjprs.SystemStateMachine;
import sdfgjkljljoftrytrszgijpokjprs.FFT;
import sdfgjkljljoftrytrszgijpokjprs.IUpdatedCallback;
import sdfgjkljljoftrytrszgijpokjprs.IBatchFrameEvents;
import sdfgjkljljoftrytrszgijpokjprs.UploadRunnable;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.ITableReorderable;
import sdfgjkljljoftrytrszgijpokjprs.BatchItem;
import sdfgjkljljoftrytrszgijpokjprs.OverallValueModel;
import sdfgjkljljoftrytrszgijpokjprs.ILevelMeterControlMidSideSwitch;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;
import sdfgjkljljoftrytrszgijpokjprs.UploadController;
import sdfgjkljljoftrytrszgijpokjprs.FolderValueComposer;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerControl;
import sdfgjkljljoftrytrszgijpokjprs.BatchListFrame;

public class BatchController
extends AbstractTableModel
implements IPlayerStateListener,
IPlayerEventListener,
IUpdatedCallback,
IBatchFrameEvents,
ITableReorderable,
ILevelMeterControlMidSideSwitch {
    private static final long serialVersionUID = 1L;
    private static final Object DSP = new Object();
    private static final DragAndDropListener FFT = new DragAndDropListener(null);
    private static BatchController responseView = null;
    private static BatchListFrame AdditionalMetadataValue;
    private final String[] AudioFileExtension;
    private final Class<?>[] IAudioFileCodec = new Class[]{String.class, Boolean.class, Boolean.class, String.class};
    private final boolean[] IAudioInputStream = new boolean[]{false, true, true, false};
    private final FileDialog IAudioMetaInformation;
    private final LinkedList<BatchItem> IBaseAudioCodec;
    private final ArrayList<IBatchListSizeListener> MetaInfomationCopy;
    private boolean AacAudioCodec;
    private IPlayerControl AacMetaDataModel;
    private PlayerState BufferedAacReader;
    private PlayerEvent AiffAudioCodec;

    private BatchController() {
        this.AudioFileExtension = new String[]{ResourceBundle.getBundle("com/xivero/hraa/gui/frame/batch/Bundle").getString("columnName.Track"), ResourceBundle.getBundle("com/xivero/hraa/gui/frame/batch/Bundle").getString("columnName.ImageBoolean"), ResourceBundle.getBundle("com/xivero/hraa/gui/frame/batch/Bundle").getString("columnName.ValueBoolean"), "State"};
        this.IBaseAudioCodec = new LinkedList();
        this.MetaInfomationCopy = new ArrayList(0);
        AdditionalMetadataValue = new BatchListFrame(FFT);
        this.IAudioMetaInformation = new FileDialog(AdditionalMetadataValue);
        this.IAudioMetaInformation.setTitle(ResourceBundle.getBundle("com/xivero/hraa/gui/frame/batch/Bundle").getString("BatchListFrame.jCheckBoxOverallReport.text"));
        this.IAudioMetaInformation.setMultipleMode(false);
        this.IAudioMetaInformation.setMode(1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static BatchController DSP() {
        if (responseView == null) {
            Object object = DSP;
            synchronized (object) {
                if (responseView == null) {
                    responseView = new BatchController();
                    AdditionalMetadataValue.DSP(responseView);
                    BatchListRenderer kPkxKNlhkRWAWNuwGFPqFGo = new BatchListRenderer();
                    AdditionalMetadataValue.DSP(String.class, kPkxKNlhkRWAWNuwGFPqFGo);
                    AdditionalMetadataValue.DSP(responseView);
                    AdditionalMetadataValue.DSP(FFT);
                }
            }
        }
        return responseView;
    }

    public void DSP(boolean bl) {
        AdditionalMetadataValue.setVisible(bl);
        AdditionalMetadataValue.setState(0);
    }

    public void DSP(IPlayerControl tSwCDjQKHwQVvbvetpZIATg2) {
        this.AacMetaDataModel = tSwCDjQKHwQVvbvetpZIATg2;
    }

    public boolean FFT() {
        return this.IBaseAudioCodec.size() > 0;
    }

    public void DSP(String string, int n) {
        String string2 = Files.getNameWithoutExtension((String)string) + "." + Files.getFileExtension((String)string);
        BatchItem gDUqxotmDwrsAvlIwIEIdto2 = new BatchItem(string, string2, "", false, false, true, true);
        gDUqxotmDwrsAvlIwIEIdto2.DSP(AdditionalMetadataValue.DSP());
        gDUqxotmDwrsAvlIwIEIdto2.FFT(AdditionalMetadataValue.FFT());
        this.IBaseAudioCodec.add(n, gDUqxotmDwrsAvlIwIEIdto2);
        this.fireTableDataChanged();
    }

    public void DSP(String string, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA) {
        FFT qPeIwmpzLZIktKLXAJOQHcO = yGjBevanihqaxYKnUNtrNeA.BufferedAacReader();
        String string2 = qPeIwmpzLZIktKLXAJOQHcO != null && qPeIwmpzLZIktKLXAJOQHcO.DSP() != null && qPeIwmpzLZIktKLXAJOQHcO.DSP().FFT() ? qPeIwmpzLZIktKLXAJOQHcO.DSP().DSP() : Files.getNameWithoutExtension((String)string) + "." + Files.getFileExtension((String)string);
        BatchItem gDUqxotmDwrsAvlIwIEIdto2 = new BatchItem(string, string2, "", false, false, true, true);
        gDUqxotmDwrsAvlIwIEIdto2.DSP(yGjBevanihqaxYKnUNtrNeA);
        this.DSP(gDUqxotmDwrsAvlIwIEIdto2);
    }

    public void DSP(String ... stringArray) {
        for (String string : stringArray) {
            String string2 = Files.getNameWithoutExtension((String)string) + "." + Files.getFileExtension((String)string);
            BatchItem gDUqxotmDwrsAvlIwIEIdto2 = new BatchItem(string, string2, "", false, false, true, true);
            gDUqxotmDwrsAvlIwIEIdto2.DSP(this);
            this.DSP(gDUqxotmDwrsAvlIwIEIdto2);
        }
    }

    public void DSP(BatchItem ... gDUqxotmDwrsAvlIwIEIdtoArray) {
        for (BatchItem gDUqxotmDwrsAvlIwIEIdto2 : gDUqxotmDwrsAvlIwIEIdtoArray) {
            gDUqxotmDwrsAvlIwIEIdto2.DSP(AdditionalMetadataValue.DSP());
            gDUqxotmDwrsAvlIwIEIdto2.FFT(AdditionalMetadataValue.FFT());
            this.IBaseAudioCodec.add(gDUqxotmDwrsAvlIwIEIdto2);
        }
        if (SystemStateMachine.DSP().FFT() == PlayerState.DSP && this.AacMetaDataModel != null) {
            this.AacMetaDataModel.DSP(this.AudioFileExtension().DSP());
        }
        if (this.MetaInfomationCopy != null) {
            for (IBatchListSizeListener lgGoKotvGVSrulzbcftkqoi : this.MetaInfomationCopy) {
                lgGoKotvGVSrulzbcftkqoi.DSP(this.IBaseAudioCodec.size());
            }
        }
        AdditionalMetadataValue.FFT(false);
        this.fireTableDataChanged();
    }

    public void responseView() {
        this.BufferedAacReader = PlayerState.AdditionalMetadataValue;
    }

    public boolean AdditionalMetadataValue() {
        if (!AdditionalMetadataValue.responseView()) {
            return this.IBaseAudioCodec.size() > this.IAudioMetaInformation() + 1;
        }
        return this.AiffAudioCodec();
    }

    public BatchItem AudioFileExtension() {
        if (this.AdditionalMetadataValue()) {
            if (!AdditionalMetadataValue.responseView()) {
                this.AudioFileExtension(this.IAudioMetaInformation() + 1);
                this.fireTableDataChanged();
                return this.IBaseAudioCodec.get(this.IAudioMetaInformation());
            }
            BatchItem gDUqxotmDwrsAvlIwIEIdto2 = this.AiffMetaDataModel();
            this.AudioFileExtension(this.IBaseAudioCodec.indexOf(gDUqxotmDwrsAvlIwIEIdto2));
            this.fireTableDataChanged();
            return gDUqxotmDwrsAvlIwIEIdto2;
        }
        return null;
    }

    public BatchItem DSP(int n) {
        if (this.IBaseAudioCodec.size() > n) {
            return this.IBaseAudioCodec.get(n);
        }
        return null;
    }

    public LinkedList<BatchItem> IAudioFileCodec() {
        return this.IBaseAudioCodec;
    }

    public void responseView(boolean bl) {
        for (BatchItem gDUqxotmDwrsAvlIwIEIdto2 : this.IBaseAudioCodec) {
            gDUqxotmDwrsAvlIwIEIdto2.FFT(bl);
        }
        this.fireTableDataChanged();
    }

    public void AdditionalMetadataValue(boolean bl) {
        for (BatchItem gDUqxotmDwrsAvlIwIEIdto2 : this.IBaseAudioCodec) {
            gDUqxotmDwrsAvlIwIEIdto2.DSP(bl);
        }
        this.fireTableDataChanged();
    }

    public BatchItem IAudioInputStream() {
        int n = this.IAudioMetaInformation();
        if (this.IBaseAudioCodec.size() > n && n != -1) {
            return this.IBaseAudioCodec.get(n);
        }
        return null;
    }

    public int IAudioMetaInformation() {
        if (this.IBaseAudioCodec.size() > 0) {
            for (int i = 0; i < this.IBaseAudioCodec.size(); ++i) {
                if (!this.IBaseAudioCodec.get(i).IAudioInputStream()) continue;
                return i;
            }
        }
        return -1;
    }

    public void DSP(IBatchListSizeListener lgGoKotvGVSrulzbcftkqoi) {
        if (!this.MetaInfomationCopy.contains(lgGoKotvGVSrulzbcftkqoi)) {
            this.MetaInfomationCopy.add(lgGoKotvGVSrulzbcftkqoi);
        }
    }

    @Override
    public String getColumnName(int n) {
        return this.AudioFileExtension[n];
    }

    @Override
    public Class<?> getColumnClass(int n) {
        return this.IAudioFileCodec[n];
    }

    @Override
    public boolean isCellEditable(int n, int n2) {
        return this.IAudioInputStream[n2];
    }

    @Override
    public int getRowCount() {
        return this.IBaseAudioCodec.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int n, int n2) {
        return this.IBaseAudioCodec.get(n).DSP(n2);
    }

    @Override
    public void setValueAt(Object object, int n, int n2) {
        this.IBaseAudioCodec.get(n).DSP(object, n2);
    }

    @Override
    public void FFT(int n) {
        this.AudioFileExtension(n);
        this.AacMetaDataModel.DSP(this.IBaseAudioCodec.get(n).DSP());
        this.AacMetaDataModel.AudioFileExtension();
        this.fireTableDataChanged();
    }

    @Override
    public void IBaseAudioCodec() {
    }

    public void MetaInfomationCopy() {
        for (BatchItem gDUqxotmDwrsAvlIwIEIdto2 : this.IBaseAudioCodec) {
            gDUqxotmDwrsAvlIwIEIdto2.DSP((OverallValueModel)null);
        }
    }

    @Override
    public void AacAudioCodec() {
        AdditionalMetadataValue.FFT(true);
        this.IBaseAudioCodec.clear();
        if (this.AacMetaDataModel != null) {
            this.AacMetaDataModel.FFT();
            this.AacMetaDataModel.DSP();
        }
        if (this.MetaInfomationCopy != null) {
            for (IBatchListSizeListener lgGoKotvGVSrulzbcftkqoi : this.MetaInfomationCopy) {
                lgGoKotvGVSrulzbcftkqoi.DSP(this.IBaseAudioCodec.size());
            }
        }
        this.fireTableDataChanged();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void DSP(PlayerState zjoyaRSokkGYDwXHPKTBIiX) {
        switch (zjoyaRSokkGYDwXHPKTBIiX) {
            case AdditionalMetadataValue: {
                Object object;
                Object object3;
                Object object4;
                if (!this.FFT() || this.BufferedAacReader == PlayerState.AdditionalMetadataValue || this.AiffAudioCodec != PlayerEvent.IAudioFileCodec) break;
                BatchItem gDUqxotmDwrsAvlIwIEIdto2 = BatchController.DSP().IAudioInputStream();
                if (gDUqxotmDwrsAvlIwIEIdto2 != null) {
                    Main.DSP(gDUqxotmDwrsAvlIwIEIdto2.FFT(), gDUqxotmDwrsAvlIwIEIdto2.DSP() + "_report", gDUqxotmDwrsAvlIwIEIdto2.AdditionalMetadataValue(), gDUqxotmDwrsAvlIwIEIdto2.AudioFileExtension(), gDUqxotmDwrsAvlIwIEIdto2.IAudioMetaInformation() == null);
                    if (AdditionalMetadataValue.AdditionalMetadataValue()) {
                        object4 = UploadController.DSP();
                        try {
                            object3 = ((UploadController)object4).DSP(gDUqxotmDwrsAvlIwIEIdto2.IAudioMetaInformation(), gDUqxotmDwrsAvlIwIEIdto2.IAudioFileCodec());
                            ((AlbumUploadItem)object3).DSP(gDUqxotmDwrsAvlIwIEIdto2);
                            if (!((AlbumUploadItem)object3).FFT()) {
                                gDUqxotmDwrsAvlIwIEIdto2.DSP("Waiting for completion");
                                this.fireTableDataChanged();
                                Thread object22 = new Thread(new UploadRunnable(gDUqxotmDwrsAvlIwIEIdto2, this));
                                object22.start();
                            }
                        }
                        catch (MetadataException gHZgLGQAyrKtRkmFCeaixlU) {
                            Logger.getLogger(ReportingController.class.getName()).log(Level.SEVERE, null, gHZgLGQAyrKtRkmFCeaixlU);
                            StringBuilder stringBuilder = new StringBuilder(0);
                            object = gHZgLGQAyrKtRkmFCeaixlU.DSP().iterator();
                            while (object.hasNext()) {
                                stringBuilder.append((String)object.next());
                                if (!object.hasNext()) continue;
                                stringBuilder.append(", ");
                            }
                            gDUqxotmDwrsAvlIwIEIdto2.DSP(gHZgLGQAyrKtRkmFCeaixlU.getMessage() + " | " + stringBuilder);
                            this.fireTableDataChanged();
                        }
                    }
                }
                if (!this.AdditionalMetadataValue() && AdditionalMetadataValue.responseView()) {
                    this.IAudioMetaInformation.setFile("report.txt");
                    this.IAudioMetaInformation.setVisible(true);
                    object4 = this.IAudioMetaInformation.getFiles();
                    if (((File[])object4).length == 1) {
                        void var5_11;
                        object3 = Files.getFileExtension((String)object4[0].getAbsolutePath());
                        String string = object4[0].getAbsolutePath();
                        if (((String)object3).isEmpty() || !((String)object3).equals("txt")) {
                            String string2 = string + ".txt";
                        }
                        ReportingController.DSP().DSP(OverallValueComposer.DSP(), (String)var5_11);
                    }
                    AdditionalMetadataValue.DSP(false);
                    this.AacMetaDataModel.FFT();
                    this.AacMetaDataModel.DSP(this.IAudioInputStream().DSP());
                }
                if (!this.AdditionalMetadataValue() && AdditionalMetadataValue.AudioFileExtension()) {
                    object4 = FolderValueComposer.DSP(BatchController.DSP());
                    for (Path path : ((HashMap)object4).keySet()) {
                        object = new File(path.toFile(), "MusicScope-Report.txt");
                        try {
                            if (((File)object).exists()) {
                                ((File)object).delete();
                            }
                            ((File)object).createNewFile();
                            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter((File)object));
                            bufferedWriter.append(((StringBuilder)((HashMap)object4).get(path)).toString());
                            bufferedWriter.flush();
                            bufferedWriter.close();
                        }
                        catch (IOException iOException) {
                            Logger.getLogger(ReportingController.class.getName()).log(Level.SEVERE, null, iOException);
                        }
                    }
                }
                if (!this.AacAudioCodec) break;
                this.AacAudioCodec = false;
                gDUqxotmDwrsAvlIwIEIdto2 = this.AudioFileExtension();
                if (gDUqxotmDwrsAvlIwIEIdto2 == null) break;
                this.AacMetaDataModel.DSP(gDUqxotmDwrsAvlIwIEIdto2.DSP());
                if (this.BufferedAacReader == PlayerState.responseView) {
                    this.AacMetaDataModel.AdditionalMetadataValue();
                    break;
                }
                if (this.BufferedAacReader != PlayerState.FFT) break;
                this.AacMetaDataModel.AudioFileExtension();
                break;
            }
            case responseView: 
            case FFT: {
                this.BufferedAacReader = zjoyaRSokkGYDwXHPKTBIiX;
            }
        }
    }

    public void AacMetaDataModel() {
        this.AacAudioCodec = true;
    }

    @Override
    public boolean DSP(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2, Object object) {
        this.AiffAudioCodec = lXCOGwZXVelwEKHMMPwpSAO2;
        return true;
    }

    @Override
    public void responseView(int n) {
        if (this.IBaseAudioCodec.size() > n) {
            if (this.IAudioMetaInformation() == n) {
                this.AacMetaDataModel.FFT();
                this.AacMetaDataModel.DSP();
            }
            this.IBaseAudioCodec.remove(n);
            this.fireTableDataChanged();
        }
    }

    @Override
    public void AdditionalMetadataValue(int n) {
        this.AudioFileExtension(n);
        this.AacMetaDataModel.DSP(this.IBaseAudioCodec.get(n).DSP());
        this.AacMetaDataModel.AdditionalMetadataValue();
        this.fireTableDataChanged();
    }

    private void AudioFileExtension(int n) {
        for (int i = 0; i < this.IBaseAudioCodec.size(); ++i) {
            this.IBaseAudioCodec.get(i).responseView(i == n);
        }
    }

    @Override
    public void DSP(int n, int n2) {
        int n3 = n2 > n ? n2 - 1 : n2;
        BatchItem gDUqxotmDwrsAvlIwIEIdto2 = this.IBaseAudioCodec.get(n);
        this.IBaseAudioCodec.remove(n);
        this.IBaseAudioCodec.add(n3, gDUqxotmDwrsAvlIwIEIdto2);
        this.fireTableDataChanged();
    }

    private boolean AiffAudioCodec() {
        for (BatchItem gDUqxotmDwrsAvlIwIEIdto2 : this.IBaseAudioCodec) {
            if (gDUqxotmDwrsAvlIwIEIdto2.IAudioMetaInformation() != null) continue;
            return true;
        }
        return false;
    }

    private BatchItem AiffMetaDataModel() {
        for (BatchItem gDUqxotmDwrsAvlIwIEIdto2 : this.IBaseAudioCodec) {
            if (gDUqxotmDwrsAvlIwIEIdto2.IAudioMetaInformation() != null) continue;
            return gDUqxotmDwrsAvlIwIEIdto2;
        }
        return null;
    }

    @Override
    public void FFT(boolean bl) {
        this.MetaInfomationCopy();
    }

    @Override
    public void BufferedAacReader() {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                BatchController.this.fireTableDataChanged();
            }
        });
    }
}

