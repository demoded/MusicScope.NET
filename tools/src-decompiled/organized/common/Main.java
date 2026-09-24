/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.Files
 *  com.xivero.appleHandler.IHandlerCallback
 *  com.xivero.appleHandler.IPlugin
 */
package com.xivero.hraa;

import com.google.common.io.Files;
import com.xivero.appleHandler.IHandlerCallback;
import com.xivero.appleHandler.IPlugin;
import com.xivero.hraa.gui.frame.DSP;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdfgjkljljoftrytrszgijpokjprs.OverallValueComposer;
import sdfgjkljljoftrytrszgijpokjprs.DragAndDropListener;
import sdfgjkljljoftrytrszgijpokjprs.ReportingController;
import sdfgjkljljoftrytrszgijpokjprs.PlayerControl;
import sdfgjkljljoftrytrszgijpokjprs.UserAction;
import sdfgjkljljoftrytrszgijpokjprs.ReportSaver;
import sdfgjkljljoftrytrszgijpokjprs.LevelMeterControl;
import sdfgjkljljoftrytrszgijpokjprs.SwitchHeaderControl;
import sdfgjkljljoftrytrszgijpokjprs.SpectrumControl;
import sdfgjkljljoftrytrszgijpokjprs.SystemController;
import sdfgjkljljoftrytrszgijpokjprs.NumericalControl;
import sdfgjkljljoftrytrszgijpokjprs.SimpleImageComposer;
import sdfgjkljljoftrytrszgijpokjprs.FormatControl;
import sdfgjkljljoftrytrszgijpokjprs.SimpleModelComposer;
import sdfgjkljljoftrytrszgijpokjprs.MusicScopeCloudViewer;
import sdfgjkljljoftrytrszgijpokjprs.OperatingSystem;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;
import sdfgjkljljoftrytrszgijpokjprs.SimpleVersionScheme;
import sdfgjkljljoftrytrszgijpokjprs.Version;
import sdfgjkljljoftrytrszgijpokjprs.BatchController;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;
import sdfgjkljljoftrytrszgijpokjprs.SimpleValueComposer;
import sdfgjkljljoftrytrszgijpokjprs.WaterfallControl;
import sdfgjkljljoftrytrszgijpokjprs.TemporaryResourceManager;
import sdfgjkljljoftrytrszgijpokjprs.NetworkController;
import sdfgjkljljoftrytrszgijpokjprs.SettingsPanel;
import sdfgjkljljoftrytrszgijpokjprs.StereoMeterControl;
import sdfgjkljljoftrytrszgijpokjprs.InfoControl;
import sdfgjkljljoftrytrszgijpokjprs.IUserActionCallback;
import sdfgjkljljoftrytrszgijpokjprs.TimeControl;
import sdfgjkljljoftrytrszgijpokjprs.UploadController;
import sdfgjkljljoftrytrszgijpokjprs.IReportSaveCallback;
import sdfgjkljljoftrytrszgijpokjprs.CircleControl;

public class Main {
    public static final Version DSP;
    private static boolean FFT;
    private static SystemController responseView;
    private static DragAndDropListener AdditionalMetadataValue;
    private static DSP AudioFileExtension;
    private static final SimpleValueComposer IAudioFileCodec;
    private static final SimpleModelComposer IAudioInputStream;
    private static final OverallValueComposer IAudioMetaInformation;
    private static final ReportingController IBaseAudioCodec;
    private static final ReportSaver MetaInfomationCopy;
    private static final TemporaryResourceManager AacAudioCodec;
    private static Path AacMetaDataModel;

    public static void main(String[] stringArray) throws IOException {
        stringArray.clone();
        if (OperatingSystem.responseView()) {
            try {
                File file = new File("lib/AppleAbout.jar");
                URL[] uRLArray = new URL[]{file.toURI().toURL()};
                URLClassLoader uRLClassLoader = URLClassLoader.newInstance(uRLArray);
                Class<?> clazz = uRLClassLoader.loadClass("com.xivero.appleHandler.HandlerPlugin");
                IPlugin iPlugin = (IPlugin)clazz.newInstance();
                iPlugin.addHandler(new IHandlerCallback(){

                    public void aboutHandlerCall() {
                        AudioFileExtension.AacAudioCodec().setVisible(true);
                    }
                });
            }
            catch (ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException exception) {
                // empty catch block
            }
        }
        Main.DSP(new String[0]);
        AudioFileExtension.AlacMetaDataModel();
    }

    public static void DSP(String ... stringArray) {
        if (stringArray != null && stringArray.length > 0) {
            stringArray.clone();
        }
        AudioFileExtension = new DSP();
        MusicScopeCloudViewer.DSP();
        UploadController.DSP();
        responseView = SystemController.IAudioFileCodec();
        responseView.DSP(AudioFileExtension.AiffMetaDataModel());
        AdditionalMetadataValue = new DragAndDropListener(responseView);
        NetworkController.DSP().DSP(responseView);
        BatchController kfrVEsKUvFeBfAoSkvCCRmV2 = BatchController.DSP();
        kfrVEsKUvFeBfAoSkvCCRmV2.DSP(responseView);
        AudioFileExtension.DSP(AdditionalMetadataValue);
        AudioFileExtension.DSP(responseView);
        PlayerControl gGiRamzaArNdRQDukShvdNU = AudioFileExtension.DSP();
        gGiRamzaArNdRQDukShvdNU.DSP(responseView);
        responseView.DSP(gGiRamzaArNdRQDukShvdNU, PlayerState.values());
        responseView.DSP(gGiRamzaArNdRQDukShvdNU);
        responseView.DSP(AudioFileExtension.BufferedAacReader());
        responseView.DSP(AudioFileExtension.AiffAudioCodec());
        StereoMeterControl ntMSlcredseGwvbyesPSAQf2 = AudioFileExtension.FFT();
        responseView.DSP(ntMSlcredseGwvbyesPSAQf2, ComputationController.DSP.AdditionalMetadataValue);
        responseView.DSP(ntMSlcredseGwvbyesPSAQf2);
        responseView.DSP(ntMSlcredseGwvbyesPSAQf2, PlayerState.values());
        responseView.DSP(ntMSlcredseGwvbyesPSAQf2, PlayerEvent.AudioFileExtension);
        InfoControl nyfwxCZoaPuQlzOUeBNsFaO2 = AudioFileExtension.MetaInfomationCopy();
        kfrVEsKUvFeBfAoSkvCCRmV2.DSP(nyfwxCZoaPuQlzOUeBNsFaO2);
        responseView.DSP(nyfwxCZoaPuQlzOUeBNsFaO2);
        responseView.DSP(nyfwxCZoaPuQlzOUeBNsFaO2, PlayerEvent.values());
        responseView.DSP(nyfwxCZoaPuQlzOUeBNsFaO2, PlayerState.values());
        LevelMeterControl ipeEJbKSCPCYelngcqwUQgF = AudioFileExtension.responseView();
        responseView.DSP(ipeEJbKSCPCYelngcqwUQgF, ComputationController.DSP.AudioFileExtension);
        responseView.DSP(ipeEJbKSCPCYelngcqwUQgF);
        responseView.DSP(ipeEJbKSCPCYelngcqwUQgF, PlayerState.values());
        responseView.DSP(ipeEJbKSCPCYelngcqwUQgF, PlayerEvent.AudioFileExtension);
        responseView.DSP(ipeEJbKSCPCYelngcqwUQgF);
        responseView.DSP(ipeEJbKSCPCYelngcqwUQgF);
        SpectrumControl nCABabPjyneGQZVXMqQJJlz = AudioFileExtension.AdditionalMetadataValue();
        nCABabPjyneGQZVXMqQJJlz.DSP(responseView.IAudioMetaInformation());
        responseView.DSP(nCABabPjyneGQZVXMqQJJlz, ComputationController.DSP.DSP);
        responseView.DSP(nCABabPjyneGQZVXMqQJJlz);
        responseView.DSP(nCABabPjyneGQZVXMqQJJlz);
        responseView.DSP(nCABabPjyneGQZVXMqQJJlz, PlayerState.values());
        responseView.DSP(nCABabPjyneGQZVXMqQJJlz, PlayerEvent.AudioFileExtension);
        responseView.DSP(nCABabPjyneGQZVXMqQJJlz);
        WaterfallControl miBFKRJVXkTtMTMpbtygQBp2 = AudioFileExtension.AudioFileExtension();
        responseView.DSP(miBFKRJVXkTtMTMpbtygQBp2, ComputationController.DSP.DSP, ComputationController.DSP.IAudioInputStream);
        responseView.DSP(miBFKRJVXkTtMTMpbtygQBp2);
        responseView.DSP(miBFKRJVXkTtMTMpbtygQBp2);
        responseView.DSP(miBFKRJVXkTtMTMpbtygQBp2, PlayerState.values());
        responseView.DSP(miBFKRJVXkTtMTMpbtygQBp2, PlayerEvent.values());
        responseView.DSP(miBFKRJVXkTtMTMpbtygQBp2);
        NumericalControl tSILPPfdcnHrmGfZNXMsPlw = AudioFileExtension.IAudioInputStream();
        responseView.DSP(tSILPPfdcnHrmGfZNXMsPlw, ComputationController.DSP.AudioFileExtension, ComputationController.DSP.IAudioInputStream);
        responseView.DSP(tSILPPfdcnHrmGfZNXMsPlw);
        responseView.DSP(tSILPPfdcnHrmGfZNXMsPlw, PlayerState.values());
        responseView.DSP(tSILPPfdcnHrmGfZNXMsPlw, PlayerEvent.IAudioFileCodec, PlayerEvent.AudioFileExtension);
        responseView.DSP(tSILPPfdcnHrmGfZNXMsPlw);
        responseView.DSP(tSILPPfdcnHrmGfZNXMsPlw);
        FormatControl xZEXahJHCGCBNASefxRYhTY = AudioFileExtension.IAudioFileCodec();
        xZEXahJHCGCBNASefxRYhTY.addMouseListener(xZEXahJHCGCBNASefxRYhTY);
        responseView.DSP(xZEXahJHCGCBNASefxRYhTY);
        responseView.DSP(xZEXahJHCGCBNASefxRYhTY, PlayerState.DSP);
        CircleControl uRfbqkKHjLdhIvxtcBLiaka2 = AudioFileExtension.IAudioMetaInformation();
        responseView.DSP(uRfbqkKHjLdhIvxtcBLiaka2);
        responseView.DSP(uRfbqkKHjLdhIvxtcBLiaka2, ComputationController.DSP.AudioFileExtension);
        responseView.DSP(uRfbqkKHjLdhIvxtcBLiaka2);
        responseView.DSP(uRfbqkKHjLdhIvxtcBLiaka2, PlayerState.values());
        responseView.DSP(uRfbqkKHjLdhIvxtcBLiaka2, PlayerEvent.AudioFileExtension);
        responseView.DSP(uRfbqkKHjLdhIvxtcBLiaka2);
        uRfbqkKHjLdhIvxtcBLiaka2.addMouseListener(uRfbqkKHjLdhIvxtcBLiaka2);
        uRfbqkKHjLdhIvxtcBLiaka2.addMouseMotionListener(uRfbqkKHjLdhIvxtcBLiaka2);
        TimeControl rVznADxHDiUGKUcgCFACfzg2 = AudioFileExtension.IBaseAudioCodec();
        rVznADxHDiUGKUcgCFACfzg2.DSP(responseView);
        responseView.DSP(rVznADxHDiUGKUcgCFACfzg2, ComputationController.DSP.AudioFileExtension);
        responseView.DSP(rVznADxHDiUGKUcgCFACfzg2);
        responseView.DSP(rVznADxHDiUGKUcgCFACfzg2);
        responseView.DSP(rVznADxHDiUGKUcgCFACfzg2);
        responseView.DSP(rVznADxHDiUGKUcgCFACfzg2, PlayerState.values());
        responseView.DSP(rVznADxHDiUGKUcgCFACfzg2, PlayerEvent.IAudioFileCodec, PlayerEvent.AudioFileExtension);
        SettingsPanel nkSLFlumvAIqqGMcVFgItWJ2 = AudioFileExtension.AacMetaDataModel();
        nkSLFlumvAIqqGMcVFgItWJ2.DSP(responseView);
        SwitchHeaderControl lRWbrwPJNJMrzLYayAJxsLY = AudioFileExtension.AlacAudioCodec();
        lRWbrwPJNJMrzLYayAJxsLY.DSP(tSILPPfdcnHrmGfZNXMsPlw);
        responseView.DSP(IAudioFileCodec);
        responseView.DSP(IAudioFileCodec);
        responseView.DSP(IAudioInputStream);
        responseView.DSP(IAudioInputStream);
        responseView.DSP(IAudioMetaInformation);
        responseView.DSP(IAudioMetaInformation);
        nCABabPjyneGQZVXMqQJJlz.addMouseListener(miBFKRJVXkTtMTMpbtygQBp2);
        nCABabPjyneGQZVXMqQJJlz.addMouseListener(nCABabPjyneGQZVXMqQJJlz);
        nCABabPjyneGQZVXMqQJJlz.addMouseMotionListener(miBFKRJVXkTtMTMpbtygQBp2);
        nCABabPjyneGQZVXMqQJJlz.addMouseMotionListener(nCABabPjyneGQZVXMqQJJlz);
        nCABabPjyneGQZVXMqQJJlz.DSP(miBFKRJVXkTtMTMpbtygQBp2);
        ReportingController.DSP().DSP(nCABabPjyneGQZVXMqQJJlz, Integer.MAX_VALUE);
        miBFKRJVXkTtMTMpbtygQBp2.addMouseListener(nCABabPjyneGQZVXMqQJJlz);
        miBFKRJVXkTtMTMpbtygQBp2.addMouseMotionListener(nCABabPjyneGQZVXMqQJJlz);
        miBFKRJVXkTtMTMpbtygQBp2.addMouseListener(miBFKRJVXkTtMTMpbtygQBp2);
        miBFKRJVXkTtMTMpbtygQBp2.addMouseMotionListener(miBFKRJVXkTtMTMpbtygQBp2);
        miBFKRJVXkTtMTMpbtygQBp2.addMouseMotionListener(uRfbqkKHjLdhIvxtcBLiaka2);
        miBFKRJVXkTtMTMpbtygQBp2.addMouseListener(uRfbqkKHjLdhIvxtcBLiaka2);
        miBFKRJVXkTtMTMpbtygQBp2.DSP(responseView.IBaseAudioCodec());
        ReportingController.DSP().DSP(miBFKRJVXkTtMTMpbtygQBp2, Integer.MIN_VALUE);
        ipeEJbKSCPCYelngcqwUQgF.addMouseListener(ipeEJbKSCPCYelngcqwUQgF);
        ipeEJbKSCPCYelngcqwUQgF.addMouseListener(tSILPPfdcnHrmGfZNXMsPlw);
        ipeEJbKSCPCYelngcqwUQgF.addMouseListener(uRfbqkKHjLdhIvxtcBLiaka2);
        ipeEJbKSCPCYelngcqwUQgF.addMouseListener(ntMSlcredseGwvbyesPSAQf2);
        ipeEJbKSCPCYelngcqwUQgF.addMouseListener(nCABabPjyneGQZVXMqQJJlz);
        ipeEJbKSCPCYelngcqwUQgF.addMouseListener(miBFKRJVXkTtMTMpbtygQBp2);
        ipeEJbKSCPCYelngcqwUQgF.DSP(responseView.IAudioInputStream());
        ipeEJbKSCPCYelngcqwUQgF.DSP(IAudioFileCodec);
        ipeEJbKSCPCYelngcqwUQgF.DSP(BatchController.DSP());
        tSILPPfdcnHrmGfZNXMsPlw.addMouseListener(ipeEJbKSCPCYelngcqwUQgF);
        tSILPPfdcnHrmGfZNXMsPlw.addMouseListener(tSILPPfdcnHrmGfZNXMsPlw);
        responseView.DSP(kfrVEsKUvFeBfAoSkvCCRmV2, PlayerState.values());
        responseView.DSP(kfrVEsKUvFeBfAoSkvCCRmV2, PlayerEvent.values());
        Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
        rVznADxHDiUGKUcgCFACfzg2.DSP(new IReportSaveCallback(){

            @Override
            public void DSP(File file) {
                final File file2 = file;
                if (!FFT) {
                    MetaInfomationCopy.DSP(file.getAbsolutePath() + "_report");
                } else {
                    File file3 = new File(MetaInfomationCopy.responseView());
                    MetaInfomationCopy.DSP(file3.getParent() + File.separatorChar + file.getName() + "_report");
                }
                MetaInfomationCopy.DSP(new IUserActionCallback(){

                    @Override
                    public void DSP(UserAction gUmcHlGuXLMlTCxtghDBJCl) {
                        MetaInfomationCopy.setVisible(false);
                        try {
                            if (!Files.equal((File)file2.getParentFile(), (File)new File(MetaInfomationCopy.responseView()).getParentFile())) {
                                FFT = true;
                            }
                        }
                        catch (IOException iOException) {
                            // empty catch block
                        }
                        if (gUmcHlGuXLMlTCxtghDBJCl == UserAction.DSP) {
                            Main.DSP(file2.getName(), MetaInfomationCopy.responseView(), MetaInfomationCopy.FFT(), MetaInfomationCopy.DSP());
                        }
                    }
                });
                MetaInfomationCopy.setVisible(true);
            }
        });
    }

    public static void DSP(String string, String string2, boolean bl, boolean bl2) {
        Main.DSP(string, string2, bl, bl2, false);
    }

    public static void DSP(String string, String string2, boolean bl, boolean bl2, boolean bl3) {
        if (bl) {
            IBaseAudioCodec.DSP(new SimpleImageComposer(string), string2 + ".png");
        }
        if (bl2) {
            IBaseAudioCodec.DSP(IAudioFileCodec, string2 + ".txt");
        }
        if (bl3) {
            IBaseAudioCodec.DSP(IAudioMetaInformation);
        }
    }

    public static synchronized Path DSP() {
        if (AacMetaDataModel == null) {
            try {
                AacMetaDataModel = TemporaryResourceManager.DSP("_msf");
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return AacMetaDataModel;
    }

    static {
        FFT = false;
        IBaseAudioCodec = ReportingController.DSP();
        MetaInfomationCopy = new ReportSaver((Frame)AudioFileExtension, true);
        IAudioFileCodec = new SimpleValueComposer();
        IAudioInputStream = new SimpleModelComposer();
        IAudioMetaInformation = OverallValueComposer.DSP();
        AacAudioCodec = new TemporaryResourceManager(Main.DSP());
        DSP = new Version(new SimpleVersionScheme());
        DSP.DSP(0, 2);
        DSP.DSP(1, 1);
        DSP.DSP(2, 0);
        DSP.DSP(3, 180724);
    }
}

