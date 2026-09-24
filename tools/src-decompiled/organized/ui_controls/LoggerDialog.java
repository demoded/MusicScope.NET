/*
 * Decompiled with CFR 0.152.
 */
package com.xivero.logging;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LoggerDialog
extends JDialog {
    private JEditorPane DSP;
    private JScrollPane FFT;

    public LoggerDialog(Frame frame, boolean bl) {
        super(frame, bl);
        this.DSP();
    }

    private void DSP() {
        this.FFT = new JScrollPane();
        this.DSP = new JEditorPane();
        this.setDefaultCloseOperation(2);
        this.FFT.setViewportView(this.DSP);
        GroupLayout groupLayout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(groupLayout);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.FFT, -1, 400, Short.MAX_VALUE));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.FFT, -1, 300, Short.MAX_VALUE));
        this.pack();
    }

    public static void main(String[] stringArray) {
        try {
            for (UIManager.LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels()) {
                if (!"Nimbus".equals(lookAndFeelInfo.getName())) continue;
                UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
                break;
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
            Logger.getLogger(LoggerDialog.class.getName()).log(Level.SEVERE, null, classNotFoundException);
        }
        catch (InstantiationException instantiationException) {
            Logger.getLogger(LoggerDialog.class.getName()).log(Level.SEVERE, null, instantiationException);
        }
        catch (IllegalAccessException illegalAccessException) {
            Logger.getLogger(LoggerDialog.class.getName()).log(Level.SEVERE, null, illegalAccessException);
        }
        catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
            Logger.getLogger(LoggerDialog.class.getName()).log(Level.SEVERE, null, unsupportedLookAndFeelException);
        }
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                LoggerDialog loggerDialog = new LoggerDialog((Frame)new JFrame(), true);
                loggerDialog.addWindowListener(new WindowAdapter(){

                    @Override
                    public void windowClosing(WindowEvent windowEvent) {
                        System.exit(0);
                    }
                });
                loggerDialog.setVisible(true);
            }
        });
    }
}

