/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import com.xivero.hraa.Main;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.OverlayLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import sdfgjkljljoftrytrszgijpokjprs.LicenseBox;
import sdfgjkljljoftrytrszgijpokjprs.OperatingSystem;
import sdfgjkljljoftrytrszgijpokjprs.SimpleVersionScheme;
import sdfgjkljljoftrytrszgijpokjprs.Version;

public class AboutBox
extends JDialog {
    private static final long serialVersionUID = 1L;
    private final ResourceBundle DSP = ResourceBundle.getBundle("com/xivero/hraa/gui/frame/about/Bundle");
    private Thread FFT;
    private Frame responseView;
    private LicenseBox AdditionalMetadataValue;
    private JButton AudioFileExtension;
    private JPanel IAudioFileCodec;
    private JPanel IAudioInputStream;
    private JScrollPane IAudioMetaInformation;
    private JScrollPane IBaseAudioCodec;
    private JTextPane MetaInfomationCopy;
    private JTextPane AacAudioCodec;

    public AboutBox(Frame frame, boolean bl) {
        super(frame, bl);
        this.DSP();
        this.responseView = frame;
        this.AacAudioCodec.setText(String.format(this.DSP.getString("AboutBox.jTextPaneVersion.check"), Main.DSP.FFT(2)));
        this.setLocationRelativeTo(null);
    }

    private void DSP() {
        this.IAudioFileCodec = new JPanel();
        this.AudioFileExtension = new JButton();
        this.IBaseAudioCodec = new JScrollPane();
        this.AacAudioCodec = new JTextPane();
        this.IAudioMetaInformation = new JScrollPane();
        this.MetaInfomationCopy = new JTextPane();
        this.IAudioInputStream = new JPanel();
        this.setDefaultCloseOperation(2);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com/xivero/hraa/gui/frame/about/Bundle");
        this.setTitle(resourceBundle.getString("AboutBox.title"));
        this.setMaximumSize(new Dimension(558, 303));
        this.setMinimumSize(new Dimension(558, 303));
        this.setResizable(false);
        this.AudioFileExtension.setText(resourceBundle.getString("AboutBox.jButtonClose.text"));
        this.AudioFileExtension.setFocusable(false);
        this.AudioFileExtension.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AboutBox.this.DSP(actionEvent);
            }
        });
        this.IBaseAudioCodec.setBorder(null);
        this.IBaseAudioCodec.setHorizontalScrollBarPolicy(31);
        this.IBaseAudioCodec.setVerticalScrollBarPolicy(21);
        this.AacAudioCodec.setEditable(false);
        this.AacAudioCodec.setBackground(new Color(240, 240, 240));
        this.AacAudioCodec.setBorder(null);
        this.AacAudioCodec.setContentType("text/html");
        this.AacAudioCodec.addHyperlinkListener(new HyperlinkListener(){

            @Override
            public void hyperlinkUpdate(HyperlinkEvent hyperlinkEvent) {
                AboutBox.this.FFT(hyperlinkEvent);
            }
        });
        this.IBaseAudioCodec.setViewportView(this.AacAudioCodec);
        this.IAudioMetaInformation.setBorder(null);
        this.IAudioMetaInformation.setHorizontalScrollBarPolicy(31);
        this.IAudioMetaInformation.setVerticalScrollBarPolicy(21);
        this.MetaInfomationCopy.setEditable(false);
        this.MetaInfomationCopy.setBackground(new Color(240, 240, 240));
        this.MetaInfomationCopy.setBorder(null);
        this.MetaInfomationCopy.setContentType("text/html");
        this.MetaInfomationCopy.setText(resourceBundle.getString("AboutBox.jTextAbout.text"));
        this.MetaInfomationCopy.setFocusable(false);
        this.MetaInfomationCopy.addHyperlinkListener(new HyperlinkListener(){

            @Override
            public void hyperlinkUpdate(HyperlinkEvent hyperlinkEvent) {
                AboutBox.this.DSP(hyperlinkEvent);
            }
        });
        this.IAudioMetaInformation.setViewportView(this.MetaInfomationCopy);
        this.IAudioInputStream.setLayout(new OverlayLayout(this.IAudioInputStream));
        GroupLayout groupLayout = new GroupLayout(this.IAudioFileCodec);
        this.IAudioFileCodec.setLayout(groupLayout);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGap(16, 16, 16).addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.IAudioMetaInformation, -1, 528, Short.MAX_VALUE).addComponent(this.IBaseAudioCodec).addGroup(groupLayout.createSequentialGroup().addComponent(this.IAudioInputStream, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.AudioFileExtension, -2, 106, -2))).addGap(14, 14, 14)));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, groupLayout.createSequentialGroup().addGap(14, 14, 14).addComponent(this.IBaseAudioCodec, -2, 21, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.IAudioMetaInformation, -1, 246, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.AudioFileExtension).addComponent(this.IAudioInputStream, -2, -1, -2)).addContainerGap()));
        GroupLayout groupLayout2 = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(groupLayout2);
        groupLayout2.setHorizontalGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.IAudioFileCodec, -1, -1, Short.MAX_VALUE));
        groupLayout2.setVerticalGroup(groupLayout2.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.IAudioFileCodec, -1, -1, Short.MAX_VALUE));
        this.pack();
    }

    private void DSP(ActionEvent actionEvent) {
        this.setVisible(false);
    }

    private void DSP(HyperlinkEvent hyperlinkEvent) {
        if (hyperlinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            if (hyperlinkEvent.getURL().toString().equals("http://show.license/")) {
                this.FFT();
            } else {
                Desktop desktop;
                Desktop desktop2 = desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        desktop.browse(hyperlinkEvent.getURL().toURI());
                    }
                    catch (IOException | URISyntaxException exception) {
                        // empty catch block
                    }
                }
            }
        }
    }

    private void FFT() {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                if (OperatingSystem.FFT()) {
                    try {
                        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    }
                    catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exception) {
                        Logger.getLogger(AboutBox.class.getName()).log(Level.SEVERE, null, exception);
                    }
                }
                AboutBox.this.AdditionalMetadataValue = new LicenseBox(AboutBox.this.responseView, true);
                AboutBox.this.AdditionalMetadataValue.setLocationRelativeTo(null);
                AboutBox.this.AdditionalMetadataValue.setVisible(true);
            }
        });
    }

    private void FFT(HyperlinkEvent hyperlinkEvent) {
        if (hyperlinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            if (hyperlinkEvent.getDescription().equals("VERSION")) {
                this.AacAudioCodec.setText(String.format(this.DSP.getString("AboutBox.jTextPaneVersion.checking"), Main.DSP.FFT(2)));
                if (this.FFT == null || this.FFT != null && !this.FFT.isAlive()) {
                    this.FFT = new Thread(new Runnable(){

                        @Override
                        public void run() {
                            try {
                                URL uRL = new URL("https://www.xivero.com/downloads/MusicScope-Version.txt");
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uRL.openStream()));
                                String string = bufferedReader.readLine();
                                Version kTbZASHDopQBPRkAiVONWbv2 = new Version(string, "\\.", new SimpleVersionScheme());
                                if (Main.DSP.DSP(kTbZASHDopQBPRkAiVONWbv2)) {
                                    AboutBox.this.AacAudioCodec.setText(String.format(AboutBox.this.DSP.getString("AboutBox.jTextPaneVersion.newVersion"), Main.DSP.FFT(2), kTbZASHDopQBPRkAiVONWbv2.FFT(2)));
                                } else {
                                    AboutBox.this.AacAudioCodec.setText(String.format(AboutBox.this.DSP.getString("AboutBox.jTextPaneVersion.sameVersion"), Main.DSP.FFT(2)));
                                }
                            }
                            catch (MalformedURLException malformedURLException) {
                                Logger.getLogger(AboutBox.class.getName()).log(Level.SEVERE, null, malformedURLException);
                            }
                            catch (IOException iOException) {
                                Logger.getLogger(AboutBox.class.getName()).log(Level.SEVERE, null, iOException);
                            }
                        }
                    });
                    this.FFT.start();
                }
            } else {
                this.DSP(hyperlinkEvent);
            }
        }
    }

    @Override
    public void setVisible(boolean bl) {
        super.setVisible(bl);
    }
}

