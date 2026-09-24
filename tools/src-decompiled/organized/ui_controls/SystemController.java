/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JOptionPane;
import sdfgjkljljoftrytrszgijpokjprs.LevelLoudnessComposer;
import sdfgjkljljoftrytrszgijpokjprs.VolumeControl;
import sdfgjkljljoftrytrszgijpokjprs.LoudnessModule;
import sdfgjkljljoftrytrszgijpokjprs.ReportingController;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.ComposedModule;
import sdfgjkljljoftrytrszgijpokjprs.NoAudioOutput;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerEventListener;
import sdfgjkljljoftrytrszgijpokjprs.AudioOutput;
import sdfgjkljljoftrytrszgijpokjprs.Mp3AudioCodec;
import sdfgjkljljoftrytrszgijpokjprs.IMetaInformationListener;
import sdfgjkljljoftrytrszgijpokjprs.SystemStateMachine;
import sdfgjkljljoftrytrszgijpokjprs.FastAnalyzer;
import sdfgjkljljoftrytrszgijpokjprs.ISystemSettings;
import sdfgjkljljoftrytrszgijpokjprs.SpectrumModule;
import sdfgjkljljoftrytrszgijpokjprs.IAudioFileCodec;
import sdfgjkljljoftrytrszgijpokjprs.IAudioInputStream;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.AudioSampleModel;
import sdfgjkljljoftrytrszgijpokjprs.IAnalyzerStrategy;
import sdfgjkljljoftrytrszgijpokjprs.RealtimeAnalyzer;
import sdfgjkljljoftrytrszgijpokjprs.ComputationController;
import sdfgjkljljoftrytrszgijpokjprs.IComputationListener;
import sdfgjkljljoftrytrszgijpokjprs.IVolumeValueListener;
import sdfgjkljljoftrytrszgijpokjprs.ISystemErrorListener;
import sdfgjkljljoftrytrszgijpokjprs.StereoMeterModule;
import sdfgjkljljoftrytrszgijpokjprs.IMixerSettings;
import sdfgjkljljoftrytrszgijpokjprs.WavAudioCodec;
import sdfgjkljljoftrytrszgijpokjprs.FlacAudioCodec;
import sdfgjkljljoftrytrszgijpokjprs.BitDepthCutOffFrequencyModule;
import sdfgjkljljoftrytrszgijpokjprs.BatchController;
import sdfgjkljljoftrytrszgijpokjprs.IWritableAudioPlugin;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;
import sdfgjkljljoftrytrszgijpokjprs.LevelsModule;
import sdfgjkljljoftrytrszgijpokjprs.NoAudioCodecException;
import sdfgjkljljoftrytrszgijpokjprs.ChecksumModule;
import sdfgjkljljoftrytrszgijpokjprs.ITrackLoadedListener;
import sdfgjkljljoftrytrszgijpokjprs.DsdAudioCodec;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerControl;
import sdfgjkljljoftrytrszgijpokjprs.M4aAudioCodecSwitch;
import sdfgjkljljoftrytrszgijpokjprs.IAnalyzerStartListener;
import sdfgjkljljoftrytrszgijpokjprs.AiffAudioCodec;

public class SystemController
implements IPlayerEventListener,
IVolumeValueListener,
IPlayerControl {
    private static final Object DSP = new Object();
    private static final Object FFT = new Object();
    private static final Object responseView = new Object();
    private static SystemController AdditionalMetadataValue;
    private RealtimeAnalyzer AudioFileExtension;
    private final FastAnalyzer IAudioFileCodec;
    private final LevelsModule IAudioInputStream = new LevelsModule();
    private final SpectrumModule IAudioMetaInformation = new SpectrumModule();
    private final StereoMeterModule IBaseAudioCodec = new StereoMeterModule();
    private final LoudnessModule MetaInfomationCopy = new LoudnessModule();
    private final ChecksumModule AacAudioCodec = new ChecksumModule();
    private final BitDepthCutOffFrequencyModule AacMetaDataModel = new BitDepthCutOffFrequencyModule();
    private final ComposedModule BufferedAacReader;
    private final ArrayList<IMetaInformationListener> AiffAudioCodec;
    private final ArrayList<ISystemErrorListener> AiffMetaDataModel;
    private final ArrayList<ITrackLoadedListener> AlacAudioCodec;
    private final ArrayList<Object> AlacMetaDataModel;
    private final ArrayList<IAnalyzerStartListener> BufferedAlacReader;
    private final sdfgjkljljoftrytrszgijpokjprs.DSP AlacContextModel = new sdfgjkljljoftrytrszgijpokjprs.DSP();
    private final ComputationController AlacDecoderUtils;
    private final SystemStateMachine AlacFile;
    private IAnalyzerStrategy AlacInputStream;
    private DSP AlacUtils;
    private Thread ChunkInfo;
    private boolean DemuxResT = false;
    private VolumeControl DemuxUtils;
    private IMixerSettings LeadingZeros;
    private AudioOutput MyStream;
    private Object QTMovieT;
    private String SampleDuration;
    private IAudioMetaInformation SampleInfo;
    private IAudioMetaInformation StreamUtils;
    private SourceDataLine DffChunkReaderAdapter;
    private Thread DsdAudioCodec;

    private SystemController() {
        this.AlacContextModel.DSP((IAudioFileCodec)new WavAudioCodec());
        this.AlacContextModel.DSP((IAudioFileCodec)new M4aAudioCodecSwitch());
        this.AlacContextModel.DSP((IAudioFileCodec)new FlacAudioCodec());
        this.AlacContextModel.DSP((IAudioFileCodec)new AiffAudioCodec());
        this.AlacContextModel.DSP((IAudioFileCodec)new Mp3AudioCodec());
        this.AlacContextModel.DSP((IAudioFileCodec)new DsdAudioCodec(352800));
        this.AlacInputStream = null;
        this.AlacFile = SystemStateMachine.DSP();
        this.AiffAudioCodec = new ArrayList(0);
        this.AiffMetaDataModel = new ArrayList(0);
        this.AlacAudioCodec = new ArrayList(0);
        this.AlacMetaDataModel = new ArrayList(0);
        this.BufferedAlacReader = new ArrayList(0);
        this.BufferedAacReader = new LevelLoudnessComposer(this.IAudioInputStream, this.MetaInfomationCopy);
        this.AlacDecoderUtils = new ComputationController();
        this.AlacDecoderUtils.DSP(this.IAudioMetaInformation);
        this.AlacDecoderUtils.DSP(this.IBaseAudioCodec);
        this.AlacDecoderUtils.DSP(this.BufferedAacReader);
        this.AlacDecoderUtils.DSP(this.AacAudioCodec);
        this.AlacDecoderUtils.DSP(this.AacMetaDataModel);
        this.AudioFileExtension = new RealtimeAnalyzer(this.AlacDecoderUtils, this.MyStream, null);
        this.IAudioFileCodec = new FastAnalyzer(this.AlacDecoderUtils);
        this.DSP(this.IAudioFileCodec);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static SystemController IAudioFileCodec() {
        if (AdditionalMetadataValue == null) {
            Object object = responseView;
            synchronized (object) {
                if (AdditionalMetadataValue == null) {
                    AdditionalMetadataValue = new SystemController();
                    AdditionalMetadataValue.BufferedAacReader();
                }
            }
        }
        return AdditionalMetadataValue;
    }

    private void BufferedAacReader() {
        this.AlacFile.DSP(AdditionalMetadataValue, PlayerEvent.values());
        this.DSP(this.IAudioInputStream);
        this.DSP(this.IAudioMetaInformation);
        this.DSP(this.IBaseAudioCodec);
        this.DSP(this.MetaInfomationCopy);
        this.DSP(this.IAudioInputStream, PlayerState.values());
        this.DSP(this.IAudioMetaInformation, PlayerState.values());
        this.DSP(this.IBaseAudioCodec, PlayerState.values());
        this.DSP(this.MetaInfomationCopy, PlayerState.values());
        this.DSP(this.AacMetaDataModel, PlayerState.values());
        this.DSP(this.IAudioInputStream, PlayerEvent.IAudioFileCodec, PlayerEvent.AudioFileExtension);
        this.DSP(this.IAudioMetaInformation, PlayerEvent.AudioFileExtension);
        this.DSP(this.IBaseAudioCodec, PlayerEvent.AudioFileExtension);
        this.DSP(this.MetaInfomationCopy, PlayerEvent.AudioFileExtension);
        this.DSP(this.AacMetaDataModel, PlayerEvent.values());
        this.DSP(this.IAudioMetaInformation);
        this.DSP(this.IAudioInputStream);
        this.DSP(this.MetaInfomationCopy);
        this.DSP(this.IBaseAudioCodec);
        ReportingController fiypGaFYdzscEBybnfTNrMU = ReportingController.DSP();
        fiypGaFYdzscEBybnfTNrMU.DSP(this.IAudioInputStream, 1);
        fiypGaFYdzscEBybnfTNrMU.DSP(this.MetaInfomationCopy, 2);
        fiypGaFYdzscEBybnfTNrMU.DSP(this.AacAudioCodec, 3);
    }

    public LevelsModule IAudioInputStream() {
        return this.IAudioInputStream;
    }

    public SpectrumModule IAudioMetaInformation() {
        return this.IAudioMetaInformation;
    }

    public BitDepthCutOffFrequencyModule IBaseAudioCodec() {
        return this.AacMetaDataModel;
    }

    public synchronized void DSP(IComputationListener<?> ekxmGzMKSyHUczkNwMvschV2, ComputationController.DSP ... rHAjVyBgPhqkQKsOvJMPMYnArray) {
        if (this.AlacDecoderUtils != null) {
            this.AlacDecoderUtils.DSP(ekxmGzMKSyHUczkNwMvschV2, rHAjVyBgPhqkQKsOvJMPMYnArray);
        }
    }

    public final synchronized void DSP(IMetaInformationListener mhVrMnbsUmIgwNYekMCIkde) {
        if (this.AiffAudioCodec != null && !this.AiffAudioCodec.contains(mhVrMnbsUmIgwNYekMCIkde)) {
            this.AiffAudioCodec.add(mhVrMnbsUmIgwNYekMCIkde);
        }
    }

    public final synchronized void FFT(IMetaInformationListener mhVrMnbsUmIgwNYekMCIkde) {
        if (this.AiffAudioCodec != null) {
            this.AiffAudioCodec.remove(mhVrMnbsUmIgwNYekMCIkde);
        }
    }

    public final synchronized void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA2) {
        if (this.AiffAudioCodec != null) {
            for (IMetaInformationListener mhVrMnbsUmIgwNYekMCIkde : this.AiffAudioCodec) {
                mhVrMnbsUmIgwNYekMCIkde.DSP(yGjBevanihqaxYKnUNtrNeA, yGjBevanihqaxYKnUNtrNeA2);
            }
        }
    }

    private synchronized void FFT(String string) {
        if (this.AiffMetaDataModel != null) {
            for (ISystemErrorListener gTFnKQGZNjRkBqwPpskYCUd2 : this.AiffMetaDataModel) {
                gTFnKQGZNjRkBqwPpskYCUd2.DSP(string);
            }
        }
    }

    public synchronized void DSP(ITrackLoadedListener rLTzSWcNicpRLKKlkUbbehc2) {
        if (this.AlacAudioCodec != null && !this.AlacAudioCodec.contains(rLTzSWcNicpRLKKlkUbbehc2)) {
            this.AlacAudioCodec.add(rLTzSWcNicpRLKKlkUbbehc2);
        }
    }

    public synchronized void DSP(String string) {
        if (this.AlacAudioCodec != null) {
            for (ITrackLoadedListener rLTzSWcNicpRLKKlkUbbehc2 : this.AlacAudioCodec) {
                rLTzSWcNicpRLKKlkUbbehc2.DSP(string);
            }
        }
    }

    public synchronized void DSP(IAnalyzerStartListener uzZiPWteQonnOTzvIEnZmpw2) {
        if (this.BufferedAlacReader != null && !this.BufferedAlacReader.contains(uzZiPWteQonnOTzvIEnZmpw2)) {
            this.BufferedAlacReader.add(uzZiPWteQonnOTzvIEnZmpw2);
        }
    }

    public synchronized void MetaInfomationCopy() {
        if (this.BufferedAlacReader != null) {
            for (IAnalyzerStartListener uzZiPWteQonnOTzvIEnZmpw2 : this.BufferedAlacReader) {
                uzZiPWteQonnOTzvIEnZmpw2.AdditionalMetadataValue();
            }
        }
    }

    public final void DSP(IPlayerStateListener hAfXhvYubJkskjSMlFLLdgY, PlayerState ... zjoyaRSokkGYDwXHPKTBIiXArray) {
        if (this.AlacFile != null) {
            this.AlacFile.DSP(hAfXhvYubJkskjSMlFLLdgY, zjoyaRSokkGYDwXHPKTBIiXArray);
        }
    }

    public final void FFT(IPlayerStateListener hAfXhvYubJkskjSMlFLLdgY, PlayerState ... zjoyaRSokkGYDwXHPKTBIiXArray) {
        if (this.AlacFile != null) {
            this.AlacFile.FFT(hAfXhvYubJkskjSMlFLLdgY, zjoyaRSokkGYDwXHPKTBIiXArray);
        }
    }

    public void DSP(IPlayerEventListener kXzLGKHeRupGOUCLSJARWrw, PlayerEvent ... lXCOGwZXVelwEKHMMPwpSAOArray) {
        if (this.AlacFile != null) {
            this.AlacFile.DSP(kXzLGKHeRupGOUCLSJARWrw, lXCOGwZXVelwEKHMMPwpSAOArray);
        }
    }

    private void AiffAudioCodec() {
        this.DemuxResT = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void AiffMetaDataModel() {
        Object object = FFT;
        synchronized (object) {
            this.DemuxResT = false;
            FFT.notify();
        }
    }

    @Override
    public void DSP(Object object) {
        this.DSP();
        this.AlacFile.DSP(PlayerEvent.DSP, object);
    }

    @Override
    public void DSP() {
        if (this.AlacFile.FFT() != PlayerState.DSP) {
            this.QTMovieT = null;
            this.AlacFile.DSP(PlayerEvent.responseView);
            this.AlacFile.DSP(PlayerEvent.FFT);
            this.MetaInfomationCopy();
        }
        this.SampleDuration = "";
    }

    @Override
    public void FFT() {
        BatchController.DSP().responseView();
        this.AlacFile.DSP(PlayerEvent.responseView);
    }

    @Override
    public void responseView() {
        this.AlacFile.DSP(PlayerEvent.AdditionalMetadataValue);
    }

    @Override
    public void DSP(SourceDataLine sourceDataLine) {
        this.DffChunkReaderAdapter = sourceDataLine;
    }

    @Override
    public void AdditionalMetadataValue() {
        this.AlacFile.DSP(PlayerEvent.IAudioInputStream);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void AudioFileExtension() {
        Object object = DSP;
        synchronized (object) {
            if (this.MyStream == null && SystemStateMachine.DSP().FFT() != PlayerState.DSP) {
                try {
                    this.MyStream = this.LeadingZeros.responseView() ? new NoAudioOutput() : new AudioOutput(this.LeadingZeros.FFT());
                    this.MyStream.DSP(this.DemuxUtils.DSP());
                    this.MyStream.DSP(this.SampleInfo, this.StreamUtils);
                    this.DSP(this.MyStream, PlayerState.values());
                    this.DSP(this.MyStream);
                }
                catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("com/xivero/hraa/gui/control/player/Strings").getString("JOptionPane.showMessageDialog.NoSoundDeviceText"), ResourceBundle.getBundle("com/xivero/hraa/gui/control/player/Strings").getString("JOptionPane.showMessageDialog.NoSoundDeviceHeader"), -1, null);
                    this.MyStream = null;
                    return;
                }
            }
            this.AudioFileExtension = new RealtimeAnalyzer(this.AlacDecoderUtils, this.MyStream, this.AudioFileExtension);
            this.DSP(this.AudioFileExtension);
        }
        this.AlacFile.DSP(PlayerEvent.IAudioMetaInformation);
    }

    public void DSP(IWritableAudioPlugin<Future<AudioSampleModel>> lKkynTEEZVTbuSJCgDWFhnQ2) {
        if (lKkynTEEZVTbuSJCgDWFhnQ2 != null) {
            this.IAudioFileCodec.DSP(lKkynTEEZVTbuSJCgDWFhnQ2);
            this.AudioFileExtension.DSP(lKkynTEEZVTbuSJCgDWFhnQ2);
        }
    }

    public void FFT(IWritableAudioPlugin<Future<AudioSampleModel>> lKkynTEEZVTbuSJCgDWFhnQ2) {
        if (lKkynTEEZVTbuSJCgDWFhnQ2 != null) {
            this.IAudioFileCodec.FFT(lKkynTEEZVTbuSJCgDWFhnQ2);
            this.AudioFileExtension.FFT(lKkynTEEZVTbuSJCgDWFhnQ2);
        }
    }

    private void AlacAudioCodec() {
        if (this.ChunkInfo != null && !this.ChunkInfo.isAlive()) {
            this.ChunkInfo = new Thread(this.AlacUtils);
            this.ChunkInfo.setPriority(10);
            this.ChunkInfo.start();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean DSP(PlayerEvent lXCOGwZXVelwEKHMMPwpSAO2, Object object) {
        BatchController kfrVEsKUvFeBfAoSkvCCRmV2 = BatchController.DSP();
        switch (lXCOGwZXVelwEKHMMPwpSAO2) {
            case DSP: {
                try {
                    this.AiffMetaDataModel();
                    if (this.AlacContextModel.DSP(object)) {
                        this.SampleInfo = this.AlacContextModel.responseView();
                        this.StreamUtils = this.AlacContextModel.FFT();
                        this.QTMovieT = object;
                        if (this.SampleInfo != null && this.StreamUtils != null) {
                            this.AlacDecoderUtils.DSP(this.SampleInfo);
                            this.AlacDecoderUtils.FFT(this.StreamUtils);
                            this.AlacUtils = new DSP();
                            this.ChunkInfo = new Thread(this.AlacUtils);
                            this.DSP(this.SampleInfo, this.StreamUtils);
                            if (object instanceof String && !this.SampleDuration.equals(this.QTMovieT)) {
                                this.SampleDuration = (String)this.QTMovieT;
                                this.DSP((String)this.QTMovieT);
                            } else if (object instanceof IAudioInputStream) {
                                this.DSP("Audio Input Active");
                            }
                        } else {
                            this.AlacFile.DSP(PlayerEvent.FFT);
                            return false;
                        }
                        return true;
                    }
                }
                catch (NoAudioCodecException nLDIvDIdyiEiXdYwRPbKxHW2) {
                    this.FFT("NoAudioCodecException");
                }
                return false;
            }
            case IAudioInputStream: {
                this.AiffMetaDataModel();
                Object object2 = DSP;
                synchronized (object2) {
                    this.AlacInputStream = this.IAudioFileCodec;
                }
                if (this.AlacFile.FFT() == PlayerState.AdditionalMetadataValue) {
                    this.MetaInfomationCopy();
                }
                this.AlacAudioCodec();
                break;
            }
            case IAudioMetaInformation: {
                this.AiffMetaDataModel();
                Object object3 = DSP;
                synchronized (object3) {
                    this.AlacInputStream = this.AudioFileExtension;
                }
                if (this.AlacFile.FFT() == PlayerState.AdditionalMetadataValue) {
                    this.MetaInfomationCopy();
                }
                this.AlacAudioCodec();
                break;
            }
            case IAudioFileCodec: {
                if (kfrVEsKUvFeBfAoSkvCCRmV2.AdditionalMetadataValue()) {
                    if (this.ChunkInfo != null && this.ChunkInfo.isAlive()) {
                        this.ChunkInfo.interrupt();
                    }
                    this.DSP(PlayerEvent.FFT, null);
                    kfrVEsKUvFeBfAoSkvCCRmV2.AacMetaDataModel();
                    if (this.MyStream == null) break;
                    this.FFT(this.MyStream, PlayerState.values());
                    this.FFT(this.MyStream);
                    this.FFT(this.AudioFileExtension);
                    this.MyStream = null;
                    break;
                }
            }
            case responseView: {
                if (this.ChunkInfo != null && this.ChunkInfo.isAlive()) {
                    this.ChunkInfo.interrupt();
                }
                this.DSP(PlayerEvent.FFT, null);
                this.DSP(PlayerEvent.DSP, this.QTMovieT);
                Object object4 = DSP;
                synchronized (object4) {
                    this.AlacInputStream = null;
                }
                System.gc();
                if (this.MyStream == null) break;
                this.FFT(this.MyStream, PlayerState.values());
                this.FFT(this.MyStream);
                this.FFT(this.AudioFileExtension);
                this.MyStream = null;
                break;
            }
            case AdditionalMetadataValue: {
                this.AiffAudioCodec();
                break;
            }
            case FFT: {
                if (this.ChunkInfo != null && this.ChunkInfo.isAlive()) {
                    this.ChunkInfo.interrupt();
                }
                Object object5 = DSP;
                synchronized (object5) {
                    this.AlacInputStream = null;
                }
                this.AlacContextModel.DSP();
                this.AlacUtils = null;
            }
        }
        return true;
    }

    @Override
    public void DSP(int n) {
        if (this.MyStream != null) {
            this.MyStream.DSP(n);
        }
    }

    public void DSP(VolumeControl aVBqUkzZTgvQKwqvfonPEcF) {
        if (aVBqUkzZTgvQKwqvfonPEcF != null) {
            this.DemuxUtils = aVBqUkzZTgvQKwqvfonPEcF;
        }
    }

    public void DSP(IMixerSettings hbAAgnybqKnNXPUlmqPVCYX2) {
        if (hbAAgnybqKnNXPUlmqPVCYX2 != null) {
            this.LeadingZeros = hbAAgnybqKnNXPUlmqPVCYX2;
        }
    }

    public void DSP(ISystemSettings oVwxYDEnxDAUeujkyADMYmG) {
        if (oVwxYDEnxDAUeujkyADMYmG != null) {
            this.AlacDecoderUtils.DSP(oVwxYDEnxDAUeujkyADMYmG);
        }
    }

    private class DSP
    implements Runnable {
        private AudioSampleModel FFT = null;

        private DSP() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            Thread.currentThread().setName(this.getClass().getSimpleName());
            while (!Thread.currentThread().isInterrupted()) {
                this.FFT = SystemController.this.AlacContextModel.DSP(50);
                if (this.FFT == null) {
                    SystemController.this.DsdAudioCodec = new Thread(new Runnable(){

                        @Override
                        public void run() {
                            try {
                                SystemController.this.ChunkInfo.join();
                                SystemController.this.AlacFile.DSP(PlayerEvent.IAudioFileCodec);
                            }
                            catch (InterruptedException interruptedException) {
                                Logger.getLogger(SystemController.class.getName()).log(Level.SEVERE, null, interruptedException);
                            }
                        }
                    });
                    SystemController.this.DsdAudioCodec.start();
                    break;
                }
                Object object = FFT;
                synchronized (object) {
                    while (SystemController.this.DemuxResT) {
                        try {
                            FFT.wait();
                        }
                        catch (InterruptedException interruptedException) {
                            break;
                        }
                        catch (Exception exception) {
                            Logger.getLogger(SystemController.class.getName()).log(Level.SEVERE, null, exception);
                        }
                    }
                }
                object = DSP;
                synchronized (object) {
                    if (SystemController.this.AlacInputStream != null) {
                        SystemController.this.AlacInputStream.DSP(this.FFT);
                    } else {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}

