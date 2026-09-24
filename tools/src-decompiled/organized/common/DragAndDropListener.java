/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import sdfgjkljljoftrytrszgijpokjprs.ILoadableCallback;
import sdfgjkljljoftrytrszgijpokjprs.IFinishedCallback;
import sdfgjkljljoftrytrszgijpokjprs.Mp3AudioCodec;
import sdfgjkljljoftrytrszgijpokjprs.FileLoader;
import sdfgjkljljoftrytrszgijpokjprs.ICommitableStorage;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.OperatingSystem;
import sdfgjkljljoftrytrszgijpokjprs.WavAudioCodec;
import sdfgjkljljoftrytrszgijpokjprs.FlacAudioCodec;
import sdfgjkljljoftrytrszgijpokjprs.AlacAudioCodec;
import sdfgjkljljoftrytrszgijpokjprs.BatchController;
import sdfgjkljljoftrytrszgijpokjprs.LoadingDialog;
import sdfgjkljljoftrytrszgijpokjprs.DsdAudioCodec;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerControl;
import sdfgjkljljoftrytrszgijpokjprs.M4aAudioCodecSwitch;
import sdfgjkljljoftrytrszgijpokjprs.AiffAudioCodec;

public class DragAndDropListener
implements DropTargetListener,
ILoadableCallback<DSP>,
IFinishedCallback,
ICommitableStorage<DSP> {
    private final FileLoader<DSP> DSP;
    private final LoadingDialog FFT;
    private final IPlayerControl responseView;
    private final sdfgjkljljoftrytrszgijpokjprs.DSP AdditionalMetadataValue;
    private ArrayList<DSP> AudioFileExtension;
    private boolean IAudioFileCodec = true;

    public DragAndDropListener(IPlayerControl tSwCDjQKHwQVvbvetpZIATg2) {
        this.responseView = tSwCDjQKHwQVvbvetpZIATg2;
        this.AdditionalMetadataValue = new sdfgjkljljoftrytrszgijpokjprs.DSP(new WavAudioCodec(), new AiffAudioCodec(), new Mp3AudioCodec(), new M4aAudioCodecSwitch(), new FlacAudioCodec(), new AlacAudioCodec(), new DsdAudioCodec(352800));
        this.FFT = new LoadingDialog(null, true);
        this.DSP = new FileLoader<DSP>(this, this.FFT);
        this.FFT.DSP(this.DSP);
        this.AudioFileExtension = new ArrayList(0);
        this.responseView();
    }

    private void responseView() {
        this.DSP.DSP(this);
    }

    @Override
    public void dragEnter(DropTargetDragEvent dropTargetDragEvent) {
    }

    @Override
    public void dragOver(DropTargetDragEvent dropTargetDragEvent) {
        if (!OperatingSystem.responseView()) {
            block7: {
                if (!this.IAudioFileCodec) {
                    dropTargetDragEvent.rejectDrag();
                }
                dropTargetDragEvent.acceptDrag(0x40000000);
                Transferable transferable = dropTargetDragEvent.getTransferable();
                if (dropTargetDragEvent.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    try {
                        List list = (List)transferable.getTransferData(DataFlavor.javaFileListFlavor);
                        if (!(list instanceof List)) break block7;
                        if (list.size() == 1) {
                            for (Object e : list) {
                                if (!(e instanceof File)) continue;
                                return;
                            }
                            break block7;
                        }
                        return;
                    }
                    catch (UnsupportedFlavorException | IOException exception) {
                        // empty catch block
                    }
                }
            }
            dropTargetDragEvent.rejectDrag();
        }
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dropTargetDragEvent) {
    }

    @Override
    public void dragExit(DropTargetEvent dropTargetEvent) {
    }

    @Override
    public void drop(DropTargetDropEvent dropTargetDropEvent) {
        dropTargetDropEvent.acceptDrop(2);
        Transferable transferable = dropTargetDropEvent.getTransferable();
        if (dropTargetDropEvent.isDataFlavorSupported(DataFlavor.javaFileListFlavor) && this.IAudioFileCodec) {
            try {
                List list = (List)transferable.getTransferData(DataFlavor.javaFileListFlavor);
                if (list instanceof List && list.size() > 0) {
                    for (Object e : list) {
                        if (!(e instanceof File)) continue;
                        this.DSP((File)e);
                    }
                }
            }
            catch (UnsupportedFlavorException | IOException exception) {
                // empty catch block
            }
        }
        this.DSP();
    }

    public void DSP() {
        this.AudioFileExtension.clear();
        this.DSP.responseView().FFT();
        this.DSP.DSP();
    }

    @Override
    public void DSP(File file) {
        this.DSP.DSP(file, true, this);
    }

    @Override
    public synchronized void DSP(DSP rHAjVyBgPhqkQKsOvJMPMYn2) {
        if (rHAjVyBgPhqkQKsOvJMPMYn2.DSP() != null) {
            this.AudioFileExtension.add(rHAjVyBgPhqkQKsOvJMPMYn2);
        }
    }

    public synchronized DSP DSP(File file, Path path) throws Exception {
        DSP rHAjVyBgPhqkQKsOvJMPMYn2 = null;
        if (this.AdditionalMetadataValue.DSP(file.getAbsolutePath())) {
            rHAjVyBgPhqkQKsOvJMPMYn2 = new DSP(file, this.AdditionalMetadataValue.FFT());
        }
        this.AdditionalMetadataValue.AdditionalMetadataValue();
        return rHAjVyBgPhqkQKsOvJMPMYn2;
    }

    @Override
    public void FFT() {
        BatchController kfrVEsKUvFeBfAoSkvCCRmV2 = BatchController.DSP();
        for (DSP rHAjVyBgPhqkQKsOvJMPMYn2 : this.AudioFileExtension) {
            if (this.responseView == null || kfrVEsKUvFeBfAoSkvCCRmV2.FFT() || this.AudioFileExtension.size() > 1) {
                kfrVEsKUvFeBfAoSkvCCRmV2.DSP(rHAjVyBgPhqkQKsOvJMPMYn2.DSP().getAbsolutePath(), rHAjVyBgPhqkQKsOvJMPMYn2.FFT());
                kfrVEsKUvFeBfAoSkvCCRmV2.DSP(true);
                continue;
            }
            if (this.responseView == null) continue;
            this.responseView.DSP(rHAjVyBgPhqkQKsOvJMPMYn2.DSP().getAbsolutePath());
        }
    }

    @Override
    public /* synthetic */ Object FFT(File file, Path path) throws Exception {
        return this.DSP(file, path);
    }

    public static class DSP {
        private final File DSP;
        private final IAudioMetaInformation FFT;

        public DSP(File file, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA) {
            this.DSP = file;
            this.FFT = yGjBevanihqaxYKnUNtrNeA;
        }

        public File DSP() {
            return this.DSP;
        }

        public IAudioMetaInformation FFT() {
            return this.FFT;
        }
    }
}

