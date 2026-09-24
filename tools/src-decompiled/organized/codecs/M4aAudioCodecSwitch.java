/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.util.ResourceBundle;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import sdfgjkljljoftrytrszgijpokjprs.AacAudioCodec;
import sdfgjkljljoftrytrszgijpokjprs.IAudioFileCodec;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.AudioFileExtension;
import sdfgjkljljoftrytrszgijpokjprs.AlacAudioCodec;

@AudioFileExtension(DSP={"m4a"})
public class M4aAudioCodecSwitch
implements IAudioFileCodec {
    private final AlacAudioCodec DSP = new AlacAudioCodec();
    private final AacAudioCodec FFT = new AacAudioCodec();
    private JDialog responseView;

    @Override
    public IAudioFileCodec DSP(String string) {
        try {
            return this.DSP.DSP(string);
        }
        catch (Exception exception) {
            try {
                IAudioFileCodec vZagBOcdslnIaKEkojULJvx = this.FFT.DSP(string);
                if (vZagBOcdslnIaKEkojULJvx != null) {
                    this.DSP();
                }
            }
            catch (Exception exception2) {
                // empty catch block
            }
            return null;
        }
    }

    @Override
    public int DSP(byte[] byArray, int n, int n2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IAudioMetaInformation FFT() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IAudioMetaInformation responseView() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void AdditionalMetadataValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void DSP() {
        if (this.responseView == null || !this.responseView.isVisible()) {
            JOptionPane jOptionPane = new JOptionPane(ResourceBundle.getBundle("com/xivero/hraa/audio/codec/m4a/Bundle").getString("JOptionPane.AACCodecExcepion.text"));
            this.responseView = jOptionPane.createDialog(ResourceBundle.getBundle("com/xivero/hraa/audio/codec/m4a/Bundle").getString("JOptionPane.AACCodecExcepion.header"));
            this.responseView.setModal(false);
            this.responseView.setAlwaysOnTop(true);
            this.responseView.setVisible(true);
        }
    }
}

