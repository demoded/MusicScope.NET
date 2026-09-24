/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import sdfgjkljljoftrytrszgijpokjprs.DragAndDropListener;
import sdfgjkljljoftrytrszgijpokjprs.PlayerControl;
import sdfgjkljljoftrytrszgijpokjprs.ITableReorderable;
import sdfgjkljljoftrytrszgijpokjprs.BatchController;
import sdfgjkljljoftrytrszgijpokjprs.FilenameUtils;

public class TabelItemOrderTransferHandler
extends TransferHandler {
    private static final long serialVersionUID = 1L;
    private final DataFlavor FFT = new ActivationDataFlavor(Integer.class, "application/x-java-jvm-local-objectref", "IntegerRowIndex");
    private JTable responseView = null;
    private final DragAndDropListener AdditionalMetadataValue;

    public TabelItemOrderTransferHandler(JTable jTable, DragAndDropListener btSxiFJlPwYFcLNMFEsrKYC) {
        this.responseView = jTable;
        this.AdditionalMetadataValue = btSxiFJlPwYFcLNMFEsrKYC;
    }

    @Override
    protected Transferable createTransferable(JComponent jComponent) {
        assert (jComponent == this.responseView);
        return new DataHandler(this.responseView.getSelectedRow(), this.FFT.getMimeType());
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport transferSupport) {
        return true;
    }

    @Override
    public int getSourceActions(JComponent jComponent) {
        return 3;
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport transferSupport) {
        Transferable transferable = transferSupport.getTransferable();
        boolean bl = false;
        JTable jTable = (JTable)transferSupport.getComponent();
        JTable.DropLocation dropLocation = (JTable.DropLocation)transferSupport.getDropLocation();
        int n = dropLocation.getRow();
        int n2 = this.responseView.getModel().getRowCount();
        if (n < 0 || n > n2) {
            n = n2;
        }
        if (transferSupport.isDataFlavorSupported(this.FFT)) {
            jTable.setCursor(Cursor.getPredefinedCursor(0));
            try {
                int n3 = (Integer)transferSupport.getTransferable().getTransferData(this.FFT);
                if (n3 != -1 && n3 != n) {
                    ((ITableReorderable)((Object)this.responseView.getModel())).DSP(n3, n);
                    if (n > n3) {
                        --n;
                    }
                    jTable.getSelectionModel().addSelectionInterval(n, n);
                    return true;
                }
            }
            catch (UnsupportedFlavorException | IOException exception) {}
        } else if (transferSupport.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            try {
                List list = (List)transferable.getTransferData(DataFlavor.javaFileListFlavor);
                if (list instanceof List && list.size() > 0) {
                    for (Object e : list) {
                        if (!(e instanceof File) || !((File)e).isDirectory()) continue;
                        bl = true;
                        break;
                    }
                    for (Object e : list) {
                        if (!(e instanceof File)) continue;
                        if (bl) {
                            this.AdditionalMetadataValue.DSP((File)e);
                            continue;
                        }
                        if (!Arrays.asList(PlayerControl.DSP).contains(FilenameUtils.DSP(((File)e).getAbsolutePath()))) continue;
                        BatchController kfrVEsKUvFeBfAoSkvCCRmV2 = BatchController.DSP();
                        kfrVEsKUvFeBfAoSkvCCRmV2.DSP(((File)e).getAbsolutePath(), n);
                        kfrVEsKUvFeBfAoSkvCCRmV2.DSP(true);
                    }
                    if (bl) {
                        this.AdditionalMetadataValue.DSP();
                    }
                    return true;
                }
            }
            catch (UnsupportedFlavorException | IOException exception) {
                // empty catch block
            }
        }
        return false;
    }

    @Override
    protected void exportDone(JComponent jComponent, Transferable transferable, int n) {
        if (n == 2) {
            this.responseView.setCursor(Cursor.getPredefinedCursor(0));
        }
    }
}

