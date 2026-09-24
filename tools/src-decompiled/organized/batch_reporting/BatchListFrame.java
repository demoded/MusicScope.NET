/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import com.xivero.hraa.gui.frame.DSP;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.Box;
import javax.swing.DropMode;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.OverlayLayout;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import sdfgjkljljoftrytrszgijpokjprs.IListPopupItemActionListener;
import sdfgjkljljoftrytrszgijpokjprs.DragAndDropListener;
import sdfgjkljljoftrytrszgijpokjprs.BatchListPopupMenu;
import sdfgjkljljoftrytrszgijpokjprs.IBatchFrameEvents;
import sdfgjkljljoftrytrszgijpokjprs.BatchController;
import sdfgjkljljoftrytrszgijpokjprs.UploadController;
import sdfgjkljljoftrytrszgijpokjprs.TabelItemOrderTransferHandler;

public class BatchListFrame
extends JFrame {
    private static final long serialVersionUID = 1L;
    private IBatchFrameEvents DSP;
    private final BatchListPopupMenu FFT;
    private final DragAndDropListener responseView;
    private Box.Filler AdditionalMetadataValue;
    private Box.Filler AudioFileExtension;
    private Box.Filler IAudioFileCodec;
    private Box.Filler IAudioInputStream;
    private JButton IAudioMetaInformation;
    private JCheckBox IBaseAudioCodec;
    private JCheckBox MetaInfomationCopy;
    private JCheckBox AacAudioCodec;
    private JCheckBox AacMetaDataModel;
    private JCheckBox BufferedAacReader;
    private JLabel AiffAudioCodec;
    private JPanel AiffMetaDataModel;
    private JPanel AlacAudioCodec;
    private JPanel AlacMetaDataModel;
    private JPanel BufferedAlacReader;
    private JScrollPane AlacContextModel;
    private JTable AlacDecoderUtils;

    public BatchListFrame() {
        this((DragAndDropListener)null);
    }

    public BatchListFrame(DragAndDropListener btSxiFJlPwYFcLNMFEsrKYC) {
        this.IAudioFileCodec();
        this.responseView = btSxiFJlPwYFcLNMFEsrKYC;
        ArrayList<Image> arrayList = new ArrayList<Image>(0);
        for (int i = 16; i <= 128; i *= 2) {
            arrayList.add(new ImageIcon(DSP.class.getResource("icon" + i + ".png")).getImage());
        }
        this.setIconImages(arrayList);
        this.setLocationRelativeTo(null);
        this.FFT(true);
        this.AlacDecoderUtils.setTransferHandler(new TabelItemOrderTransferHandler(this.AlacDecoderUtils, btSxiFJlPwYFcLNMFEsrKYC));
        this.FFT = new BatchListPopupMenu();
        this.FFT.FFT(new IListPopupItemActionListener(){

            @Override
            public void DSP(ActionEvent actionEvent, int n) {
                if (BatchListFrame.this.DSP != null) {
                    BatchListFrame.this.DSP.responseView(n);
                }
            }
        });
        this.FFT.DSP(new IListPopupItemActionListener(){

            @Override
            public void DSP(ActionEvent actionEvent, int n) {
                if (BatchListFrame.this.DSP != null) {
                    BatchListFrame.this.DSP.FFT(n);
                }
            }
        });
        this.FFT.responseView(new IListPopupItemActionListener(){

            @Override
            public void DSP(ActionEvent actionEvent, int n) {
                if (BatchListFrame.this.DSP != null) {
                    BatchListFrame.this.DSP.AdditionalMetadataValue(n);
                }
            }
        });
    }

    public boolean DSP() {
        return this.IBaseAudioCodec.isSelected();
    }

    public boolean FFT() {
        return this.MetaInfomationCopy.isSelected();
    }

    public boolean responseView() {
        return this.BufferedAacReader.isSelected();
    }

    public boolean AdditionalMetadataValue() {
        return this.AacMetaDataModel.isSelected();
    }

    public boolean AudioFileExtension() {
        return this.AacAudioCodec.isSelected();
    }

    public void DSP(boolean bl) {
        this.BufferedAacReader.setSelected(bl);
    }

    public void DSP(IBatchFrameEvents vJpWNAZCGdriIMYsJsOuMPD) {
        this.DSP = vJpWNAZCGdriIMYsJsOuMPD;
    }

    public void DSP(AbstractTableModel abstractTableModel) {
        this.AlacDecoderUtils.setModel(abstractTableModel);
        this.AlacDecoderUtils.getColumnModel().getColumn(1).setMaxWidth(50);
        this.AlacDecoderUtils.getColumnModel().getColumn(1).setMinWidth(50);
        this.AlacDecoderUtils.getColumnModel().getColumn(2).setMaxWidth(50);
        this.AlacDecoderUtils.getColumnModel().getColumn(2).setMinWidth(50);
        this.AlacDecoderUtils.getColumnModel().getColumn(2).setWidth(70);
    }

    public void DSP(Class<?> clazz, TableCellRenderer tableCellRenderer) {
        this.AlacDecoderUtils.setDefaultRenderer(clazz, tableCellRenderer);
    }

    public void DSP(DropTargetListener dropTargetListener) {
        DropTarget dropTarget = new DropTarget(this, dropTargetListener);
        this.setDropTarget(dropTarget);
    }

    public final void FFT(boolean bl) {
        this.BufferedAlacReader.setVisible(bl);
        this.AlacDecoderUtils.setVisible(!bl);
        this.AlacContextModel.setVisible(!bl);
    }

    private void IAudioFileCodec() {
        this.AiffMetaDataModel = new JPanel();
        this.AlacMetaDataModel = new JPanel();
        this.BufferedAlacReader = new JPanel();
        this.AiffAudioCodec = new JLabel();
        this.AlacContextModel = new JScrollPane();
        this.AlacDecoderUtils = new JTable();
        this.AlacAudioCodec = new JPanel();
        this.IBaseAudioCodec = new JCheckBox();
        this.MetaInfomationCopy = new JCheckBox();
        this.AdditionalMetadataValue = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, Short.MAX_VALUE));
        this.AudioFileExtension = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, Short.MAX_VALUE));
        this.IAudioFileCodec = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, Short.MAX_VALUE));
        this.BufferedAacReader = new JCheckBox();
        this.AacAudioCodec = new JCheckBox();
        this.AacMetaDataModel = new JCheckBox();
        this.IAudioInputStream = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, Short.MAX_VALUE));
        this.IAudioMetaInformation = new JButton();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com/xivero/hraa/gui/frame/batch/Bundle");
        this.setTitle(resourceBundle.getString("BatchListFrame.title"));
        this.setCursor(new Cursor(0));
        this.setMinimumSize(new Dimension(558, 395));
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                BatchListFrame.this.DSP(windowEvent);
            }
        });
        this.AlacMetaDataModel.setLayout(new OverlayLayout(this.AlacMetaDataModel));
        this.AiffAudioCodec.setHorizontalAlignment(0);
        this.AiffAudioCodec.setText(resourceBundle.getString("BatchListFrame.jLabel1.text"));
        GroupLayout groupLayout = new GroupLayout(this.BufferedAlacReader);
        this.BufferedAlacReader.setLayout(groupLayout);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AiffAudioCodec, -1, 735, Short.MAX_VALUE));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AiffAudioCodec, -1, 380, Short.MAX_VALUE));
        this.AlacMetaDataModel.add(this.BufferedAlacReader);
        this.AlacDecoderUtils.setAutoCreateRowSorter(true);
        this.AlacDecoderUtils.setModel(new DefaultTableModel(new Object[0][], new String[0]));
        this.AlacDecoderUtils.setDragEnabled(true);
        this.AlacDecoderUtils.setDropMode(DropMode.INSERT_ROWS);
        this.AlacDecoderUtils.setName("");
        this.AlacDecoderUtils.setSelectionMode(0);
        this.AlacDecoderUtils.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                BatchListFrame.this.DSP(mouseEvent);
            }
        });
        this.AlacContextModel.setViewportView(this.AlacDecoderUtils);
        this.AlacMetaDataModel.add(this.AlacContextModel);
        this.AlacAudioCodec.setLayout(new GridLayout(2, 5));
        this.IBaseAudioCodec.setText(resourceBundle.getString("BatchListFrame.jCheckBoxAllImages.text"));
        this.IBaseAudioCodec.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                BatchListFrame.this.DSP(actionEvent);
            }
        });
        this.AlacAudioCodec.add(this.IBaseAudioCodec);
        this.MetaInfomationCopy.setText(resourceBundle.getString("BatchListFrame.jCheckBoxAllReports.text"));
        this.MetaInfomationCopy.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                BatchListFrame.this.FFT(actionEvent);
            }
        });
        this.AlacAudioCodec.add(this.MetaInfomationCopy);
        this.AlacAudioCodec.add(this.AdditionalMetadataValue);
        this.AlacAudioCodec.add(this.AudioFileExtension);
        this.AlacAudioCodec.add(this.IAudioFileCodec);
        this.BufferedAacReader.setText(resourceBundle.getString("BatchListFrame.jCheckBoxOverallReport.text"));
        this.BufferedAacReader.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                BatchListFrame.this.AdditionalMetadataValue(actionEvent);
            }
        });
        this.AlacAudioCodec.add(this.BufferedAacReader);
        this.AacAudioCodec.setText(resourceBundle.getString("BatchListFrame.jCheckBoxFolderReport.text"));
        this.AacAudioCodec.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                BatchListFrame.this.IAudioFileCodec(actionEvent);
            }
        });
        this.AlacAudioCodec.add(this.AacAudioCodec);
        this.AacMetaDataModel.setText(resourceBundle.getString("BatchListFrame.jCheckBoxLraUpload.text"));
        this.AacMetaDataModel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                BatchListFrame.this.AudioFileExtension(actionEvent);
            }
        });
        this.AlacAudioCodec.add(this.AacMetaDataModel);
        this.AlacAudioCodec.add(this.IAudioInputStream);
        this.IAudioMetaInformation.setText(resourceBundle.getString("BatchListFrame.jButtonClearList.text"));
        this.IAudioMetaInformation.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                BatchListFrame.this.responseView(actionEvent);
            }
        });
        this.AlacAudioCodec.add(this.IAudioMetaInformation);
        GroupLayout groupLayout2 = new GroupLayout(this.AiffMetaDataModel);
        this.AiffMetaDataModel.setLayout(groupLayout2);
        groupLayout2.setHorizontalGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout2.createSequentialGroup().addContainerGap().addGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AlacAudioCodec, -1, -1, Short.MAX_VALUE).addComponent(this.AlacMetaDataModel, -1, -1, Short.MAX_VALUE)).addContainerGap()));
        groupLayout2.setVerticalGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, groupLayout2.createSequentialGroup().addContainerGap().addComponent(this.AlacMetaDataModel, -1, -1, Short.MAX_VALUE).addGap(11, 11, 11).addComponent(this.AlacAudioCodec, -2, 50, -2).addContainerGap()));
        GroupLayout groupLayout3 = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(groupLayout3);
        groupLayout3.setHorizontalGroup(groupLayout3.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AiffMetaDataModel, -1, -1, Short.MAX_VALUE));
        groupLayout3.setVerticalGroup(groupLayout3.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout3.createSequentialGroup().addComponent(this.AiffMetaDataModel, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0)));
        this.pack();
    }

    private void DSP(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case 1: {
                if (mouseEvent.getClickCount() != 2 || !(mouseEvent.getSource() instanceof JTable)) break;
                JTable jTable = (JTable)mouseEvent.getSource();
                int n = jTable.rowAtPoint(mouseEvent.getPoint());
                if (this.DSP == null) break;
                this.DSP.FFT(n);
                break;
            }
            case 3: {
                if (!(mouseEvent.getSource() instanceof JTable)) break;
                JTable jTable = (JTable)mouseEvent.getSource();
                this.FFT.DSP(jTable.rowAtPoint(mouseEvent.getPoint()));
                this.FFT.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
            }
        }
    }

    private void DSP(ActionEvent actionEvent) {
        BatchController.DSP().AdditionalMetadataValue(this.IBaseAudioCodec.isSelected());
    }

    private void FFT(ActionEvent actionEvent) {
        BatchController.DSP().responseView(this.MetaInfomationCopy.isSelected());
    }

    private void DSP(WindowEvent windowEvent) {
        if (this.DSP != null) {
            this.DSP.IBaseAudioCodec();
        }
    }

    private void responseView(ActionEvent actionEvent) {
        UploadController.DSP().responseView();
        if (this.DSP != null) {
            this.DSP.AacAudioCodec();
            this.BufferedAacReader.setSelected(false);
        }
    }

    private void AdditionalMetadataValue(ActionEvent actionEvent) {
        if (this.DSP == null || this.BufferedAacReader.isSelected()) {
            // empty if block
        }
    }

    private void AudioFileExtension(ActionEvent actionEvent) {
    }

    private void IAudioFileCodec(ActionEvent actionEvent) {
    }

    @Override
    public void setVisible(boolean bl) {
        if (BatchController.DSP().AdditionalMetadataValue()) {
            this.BufferedAlacReader.setVisible(false);
        }
        super.setVisible(bl);
    }
}

