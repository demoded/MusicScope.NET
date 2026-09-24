/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import sdfgjkljljoftrytrszgijpokjprs.AudioExtension;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormat;
import sdfgjkljljoftrytrszgijpokjprs.IPlayerStateListener;
import sdfgjkljljoftrytrszgijpokjprs.BitsPerSample;
import sdfgjkljljoftrytrszgijpokjprs.IMetaInformationListener;
import sdfgjkljljoftrytrszgijpokjprs.SystemStateMachine;
import sdfgjkljljoftrytrszgijpokjprs.MetaInfomationCopy;
import sdfgjkljljoftrytrszgijpokjprs.AudioFormatCode;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.PlayerState;
import sdfgjkljljoftrytrszgijpokjprs.PlayerEvent;

public class FormatControl
extends JComponent
implements MouseListener,
Runnable,
IPlayerStateListener,
IMetaInformationListener {
    private static final long serialVersionUID = 1L;
    private int responseView;
    private int AdditionalMetadataValue;
    private IAudioMetaInformation AudioFileExtension = new MetaInfomationCopy(0, BitsPerSample.AudioFileExtension, 0, 0, AudioFormatCode.responseView, 0L, 0L, "", 0, AudioExtension.AacMetaDataModel, 0L, 0L, null);
    boolean DSP = false;
    boolean FFT = false;

    @Override
    public void run() {
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n = 120;
        int n2 = 37;
        int n3 = 40;
        if (this.AudioFileExtension != null) {
            if (this.AudioFileExtension.MetaInfomationCopy().FFT() == AudioFormat.DSP) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("PCM", 0, n3);
            if (this.AudioFileExtension.MetaInfomationCopy().FFT() == AudioFormat.FFT) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("DSD", n - fontMetrics.stringWidth("DSD"), n3);
            graphics2D.setColor(Color.decode("#666666"));
            graphics2D.drawLine(0, n3 + 7, n, n3 + 7);
            if (this.AdditionalMetadataValue == 1) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("1", 0, n3 + 25);
            if (this.AdditionalMetadataValue == 16) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("16", 45 - fontMetrics.stringWidth("16"), n3 + 25);
            if (this.AdditionalMetadataValue == 24) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("24", 83 - fontMetrics.stringWidth("24"), n3 + 25);
            if (this.AdditionalMetadataValue == 32) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("32", n - fontMetrics.stringWidth("32"), n3 + 25);
            graphics2D.setColor(Color.decode("#666666"));
            graphics2D.drawLine(0, 32 + n3, n, 32 + n3);
            if (this.responseView == 44100) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("44.1", n2 - fontMetrics.stringWidth("44.1"), n3 + 50);
            if (this.responseView == 48000 || this.responseView == 48048) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("48", n - fontMetrics.stringWidth("48"), n3 + 50);
            if (this.responseView == 88200) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("88.2", n2 - fontMetrics.stringWidth("88.2"), n3 + 75);
            if (this.responseView == 96000) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("96", n - fontMetrics.stringWidth("96"), n3 + 75);
            if (this.responseView == 176400) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("176.4", n2 - fontMetrics.stringWidth("176.4"), n3 + 100);
            if (this.responseView == 192000) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("192", n - fontMetrics.stringWidth("192"), n3 + 100);
            if (this.responseView == 352800) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("352.8", n2 - fontMetrics.stringWidth("352.8"), n3 + 125);
            if (this.responseView == 384000) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("384", n - fontMetrics.stringWidth("384"), n3 + 125);
            if (this.responseView == 2822400) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("64", n2 - 20 - fontMetrics.stringWidth("64"), n3 + 150);
            if (this.responseView == 5644800) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            if (this.responseView == 5644800) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("128", 49 - fontMetrics.stringWidth("128"), n3 + 150);
            if (this.responseView == 11289600) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("256", 84 - fontMetrics.stringWidth("256"), n3 + 150);
            if (this.responseView == 22579200) {
                graphics2D.setColor(Color.decode("#EEEEEE"));
            } else {
                graphics2D.setColor(Color.decode("#666666"));
            }
            graphics2D.drawString("512", n - fontMetrics.stringWidth("512"), n3 + 150);
            graphics2D.setColor(Color.decode("#666666"));
            graphics2D.drawLine(0, n3 + 157, n, n3 + 157);
            if (!this.DSP && !this.FFT) {
                if (this.AudioFileExtension.MetaInfomationCopy() == AudioExtension.responseView) {
                    graphics2D.setColor(Color.decode("#EEEEEE"));
                } else {
                    graphics2D.setColor(Color.decode("#666666"));
                }
                graphics2D.drawString("WAV", 0, n3 + 175);
                if (this.AudioFileExtension.MetaInfomationCopy() == AudioExtension.IBaseAudioCodec) {
                    graphics2D.setColor(Color.decode("#EEEEEE"));
                } else {
                    graphics2D.setColor(Color.decode("#666666"));
                }
                graphics2D.drawString("AIFF", 0, n3 + 200);
                if (this.AudioFileExtension.MetaInfomationCopy() == AudioExtension.AudioFileExtension) {
                    graphics2D.setColor(Color.decode("#EEEEEE"));
                } else {
                    graphics2D.setColor(Color.decode("#666666"));
                }
                graphics2D.drawString("FLAC", 0, n3 + 225);
                if (this.AudioFileExtension.MetaInfomationCopy() == AudioExtension.DSP) {
                    graphics2D.setColor(Color.decode("#EEEEEE"));
                } else {
                    graphics2D.setColor(Color.decode("#666666"));
                }
                graphics2D.drawString("ALAC", 0, n3 + 250);
                if (this.AudioFileExtension.MetaInfomationCopy() == AudioExtension.IAudioFileCodec) {
                    graphics2D.setColor(Color.decode("#EEEEEE"));
                } else {
                    graphics2D.setColor(Color.decode("#666666"));
                }
                graphics2D.drawString("DSF", n - fontMetrics.stringWidth("DSF"), n3 + 175);
                if (this.AudioFileExtension.MetaInfomationCopy() == AudioExtension.IAudioInputStream) {
                    graphics2D.setColor(Color.decode("#EEEEEE"));
                } else {
                    graphics2D.setColor(Color.decode("#666666"));
                }
                graphics2D.drawString("DFF", n - fontMetrics.stringWidth("DFF"), n3 + 200);
                if (this.AudioFileExtension.MetaInfomationCopy() == AudioExtension.IAudioMetaInformation) {
                    graphics2D.setColor(Color.decode("#EEEEEE"));
                } else {
                    graphics2D.setColor(Color.decode("#666666"));
                }
                graphics2D.drawString("MP3", n - fontMetrics.stringWidth("MP3"), n3 + 225);
                if (this.AudioFileExtension.MetaInfomationCopy() == AudioExtension.AdditionalMetadataValue) {
                    graphics2D.setColor(Color.decode("#EEEEEE"));
                } else {
                    graphics2D.setColor(Color.decode("#666666"));
                }
                graphics2D.drawString("BWF", n - fontMetrics.stringWidth("BWF"), n3 + 250);
            } else {
                graphics2D.setColor(Color.decode("#EE0000"));
                if (this.FFT) {
                    graphics2D.drawString("VST/AU INPUT", 60 - fontMetrics.stringWidth("VST/AU INPUT") / 2, n3 + 200);
                    graphics2D.setColor(Color.decode("#00EE00"));
                    graphics2D.drawString("RESET", 60 - fontMetrics.stringWidth("RESET") / 2, n3 + 250);
                }
                if (this.DSP) {
                    graphics2D.drawString("AUDIO INPUT", 60 - fontMetrics.stringWidth("AUDIO INPUT") / 2, n3 + 200);
                    graphics2D.setColor(Color.decode("#00EE00"));
                    graphics2D.drawString("RESET", 60 - fontMetrics.stringWidth("RESET") / 2, n3 + 250);
                }
            }
        }
    }

    @Override
    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA2) {
        this.responseView = yGjBevanihqaxYKnUNtrNeA2.DSP();
        this.AdditionalMetadataValue = yGjBevanihqaxYKnUNtrNeA2.FFT().DSP();
        this.AudioFileExtension = yGjBevanihqaxYKnUNtrNeA2;
        this.DSP = yGjBevanihqaxYKnUNtrNeA2.MetaInfomationCopy() == AudioExtension.MetaInfomationCopy;
        this.FFT = yGjBevanihqaxYKnUNtrNeA2.MetaInfomationCopy() == AudioExtension.AacAudioCodec;
        this.repaint();
    }

    @Override
    public void DSP(PlayerState zjoyaRSokkGYDwXHPKTBIiX) {
        if (zjoyaRSokkGYDwXHPKTBIiX == PlayerState.DSP) {
            this.FFT = false;
            this.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == this && (this.FFT || this.DSP)) {
            int n = mouseEvent.getX();
            int n2 = mouseEvent.getY();
            if (n > 0 && n < 300 && n2 > 280 && n2 < 300) {
                SystemStateMachine.DSP().DSP(PlayerEvent.AudioFileExtension);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }
}

