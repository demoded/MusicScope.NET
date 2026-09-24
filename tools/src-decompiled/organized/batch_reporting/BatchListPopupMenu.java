/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import sdfgjkljljoftrytrszgijpokjprs.IListPopupItemActionListener;

public class BatchListPopupMenu
extends JPopupMenu {
    private static final long serialVersionUID = 1L;
    private int DSP = -1;
    private final JMenuItem FFT = new JMenuItem(ResourceBundle.getBundle("com/xivero/hraa/gui/frame/batch/Bundle").getString("playMenuItem.text"), null);
    private final JMenuItem responseView;
    private final JMenuItem AdditionalMetadataValue;
    private IListPopupItemActionListener AudioFileExtension;
    private IListPopupItemActionListener IAudioFileCodec;
    private IListPopupItemActionListener IAudioInputStream;

    public BatchListPopupMenu() {
        this.FFT.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                BatchListPopupMenu.this.DSP(BatchListPopupMenu.this.AudioFileExtension, actionEvent, BatchListPopupMenu.this.DSP);
            }
        });
        this.add(this.FFT);
        this.responseView = new JMenuItem(ResourceBundle.getBundle("com/xivero/hraa/gui/frame/batch/Bundle").getString("analyzeMenuItem.text"));
        this.responseView.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                BatchListPopupMenu.this.DSP(BatchListPopupMenu.this.IAudioFileCodec, actionEvent, BatchListPopupMenu.this.DSP);
            }
        });
        this.add(this.responseView);
        this.AdditionalMetadataValue = new JMenuItem(ResourceBundle.getBundle("com/xivero/hraa/gui/frame/batch/Bundle").getString("deleteMenuItem.text"));
        this.AdditionalMetadataValue.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                BatchListPopupMenu.this.DSP(BatchListPopupMenu.this.IAudioInputStream, actionEvent, BatchListPopupMenu.this.DSP);
            }
        });
        this.add(this.AdditionalMetadataValue);
    }

    public void DSP(int n) {
        this.DSP = n;
    }

    public void DSP(IListPopupItemActionListener bDsdbFOYtFCuggqxpEwhmVU) {
        if (bDsdbFOYtFCuggqxpEwhmVU != null) {
            this.AudioFileExtension = bDsdbFOYtFCuggqxpEwhmVU;
        }
    }

    public void FFT(IListPopupItemActionListener bDsdbFOYtFCuggqxpEwhmVU) {
        if (bDsdbFOYtFCuggqxpEwhmVU != null) {
            this.IAudioInputStream = bDsdbFOYtFCuggqxpEwhmVU;
        }
    }

    public void responseView(IListPopupItemActionListener bDsdbFOYtFCuggqxpEwhmVU) {
        if (bDsdbFOYtFCuggqxpEwhmVU != null) {
            this.IAudioFileCodec = bDsdbFOYtFCuggqxpEwhmVU;
        }
    }

    private void DSP(IListPopupItemActionListener bDsdbFOYtFCuggqxpEwhmVU, ActionEvent actionEvent, int n) {
        if (bDsdbFOYtFCuggqxpEwhmVU != null) {
            bDsdbFOYtFCuggqxpEwhmVU.DSP(actionEvent, n);
        }
    }
}

