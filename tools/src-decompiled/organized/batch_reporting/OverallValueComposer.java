/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import com.xivero.hraa.Main;
import java.io.File;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdfgjkljljoftrytrszgijpokjprs.ReportingController;
import sdfgjkljljoftrytrszgijpokjprs.IMetaInformationListener;
import sdfgjkljljoftrytrszgijpokjprs.ValueModel;
import sdfgjkljljoftrytrszgijpokjprs.IAudioMetaInformation;
import sdfgjkljljoftrytrszgijpokjprs.ReportingParameter;
import sdfgjkljljoftrytrszgijpokjprs.BatchItem;
import sdfgjkljljoftrytrszgijpokjprs.OverallValueModel;
import sdfgjkljljoftrytrszgijpokjprs.BatchController;
import sdfgjkljljoftrytrszgijpokjprs.SimpleValueComposer;
import sdfgjkljljoftrytrszgijpokjprs.IValueReporting;
import sdfgjkljljoftrytrszgijpokjprs.IValueCollector;
import sdfgjkljljoftrytrszgijpokjprs.ITrackLoadedListener;

public class OverallValueComposer
implements IMetaInformationListener,
IValueCollector,
ITrackLoadedListener {
    private static final OverallValueComposer DSP = new OverallValueComposer();
    private static final int[] FFT = new int[]{-32, 6, 3, 11, 13, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 9};
    private static final boolean[] responseView = new boolean[]{true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
    private final Locale AdditionalMetadataValue = new Locale("en", "us");
    private IAudioMetaInformation AudioFileExtension;
    private final BatchController IAudioFileCodec = BatchController.DSP();
    private File IAudioInputStream;

    protected OverallValueComposer() {
    }

    public static OverallValueComposer DSP() {
        return DSP;
    }

    @Override
    public void DSP(ReportingController fiypGaFYdzscEBybnfTNrMU) {
        OverallValueModel hLDzMMEJpHepjqnDHxBExAn2 = new OverallValueModel();
        ArrayList<AbstractMap.SimpleEntry<String, Object>> arrayList = this.responseView(fiypGaFYdzscEBybnfTNrMU);
        hLDzMMEJpHepjqnDHxBExAn2.DSP(this.IAudioInputStream.getAbsoluteFile().toPath());
        hLDzMMEJpHepjqnDHxBExAn2.DSP(this.DSP(32, this.IAudioInputStream.getName()));
        hLDzMMEJpHepjqnDHxBExAn2.responseView(this.AudioFileExtension.MetaInfomationCopy().DSP());
        hLDzMMEJpHepjqnDHxBExAn2.FFT(this.AudioFileExtension.MetaInfomationCopy().FFT().name());
        hLDzMMEJpHepjqnDHxBExAn2.FFT(this.AudioFileExtension.FFT().DSP());
        hLDzMMEJpHepjqnDHxBExAn2.DSP(this.AudioFileExtension.DSP());
        for (AbstractMap.SimpleEntry<String, Object> simpleEntry : arrayList) {
            ValueModel rOJuPbsRZYkDSnyfZISOUMD = (ValueModel)simpleEntry.getValue();
            switch (simpleEntry.getKey()) {
                case "TPL Left:": {
                    hLDzMMEJpHepjqnDHxBExAn2.DSP(rOJuPbsRZYkDSnyfZISOUMD);
                    break;
                }
                case "TPL Right:": {
                    hLDzMMEJpHepjqnDHxBExAn2.FFT(rOJuPbsRZYkDSnyfZISOUMD);
                    break;
                }
                case "TPL Mid:": {
                    hLDzMMEJpHepjqnDHxBExAn2.IBaseAudioCodec(rOJuPbsRZYkDSnyfZISOUMD);
                    break;
                }
                case "TPL Side:": {
                    hLDzMMEJpHepjqnDHxBExAn2.MetaInfomationCopy(rOJuPbsRZYkDSnyfZISOUMD);
                    break;
                }
                case "RMS Left:": {
                    hLDzMMEJpHepjqnDHxBExAn2.responseView(rOJuPbsRZYkDSnyfZISOUMD);
                    break;
                }
                case "RMS Right:": {
                    hLDzMMEJpHepjqnDHxBExAn2.AdditionalMetadataValue(rOJuPbsRZYkDSnyfZISOUMD);
                    break;
                }
                case "RMS Mid:": {
                    hLDzMMEJpHepjqnDHxBExAn2.AacAudioCodec(rOJuPbsRZYkDSnyfZISOUMD);
                    break;
                }
                case "RMS Side:": {
                    hLDzMMEJpHepjqnDHxBExAn2.AacMetaDataModel(rOJuPbsRZYkDSnyfZISOUMD);
                    break;
                }
                case "CREST Avg.:": {
                    hLDzMMEJpHepjqnDHxBExAn2.AudioFileExtension(rOJuPbsRZYkDSnyfZISOUMD);
                    break;
                }
                case "PLR Avg.:": {
                    hLDzMMEJpHepjqnDHxBExAn2.IAudioFileCodec(rOJuPbsRZYkDSnyfZISOUMD);
                    break;
                }
                case "Loudness Range:": {
                    hLDzMMEJpHepjqnDHxBExAn2.IAudioInputStream(rOJuPbsRZYkDSnyfZISOUMD);
                    break;
                }
                case "Integrated Loudness:": {
                    hLDzMMEJpHepjqnDHxBExAn2.IAudioMetaInformation(rOJuPbsRZYkDSnyfZISOUMD);
                    break;
                }
                case "Cut-Off Frequency:": {
                    hLDzMMEJpHepjqnDHxBExAn2.AiffMetaDataModel(rOJuPbsRZYkDSnyfZISOUMD);
                    break;
                }
                case "Max. M-Loudness:": {
                    hLDzMMEJpHepjqnDHxBExAn2.AlacAudioCodec(rOJuPbsRZYkDSnyfZISOUMD);
                    break;
                }
                case "Max. S-Loudness:": {
                    hLDzMMEJpHepjqnDHxBExAn2.AlacMetaDataModel(rOJuPbsRZYkDSnyfZISOUMD);
                    break;
                }
                case "IS L/M:": {
                    hLDzMMEJpHepjqnDHxBExAn2.BufferedAacReader(rOJuPbsRZYkDSnyfZISOUMD);
                    break;
                }
                case "IS R/S:": {
                    hLDzMMEJpHepjqnDHxBExAn2.AiffAudioCodec(rOJuPbsRZYkDSnyfZISOUMD);
                    break;
                }
                case "HASH:": {
                    hLDzMMEJpHepjqnDHxBExAn2.AdditionalMetadataValue((String)rOJuPbsRZYkDSnyfZISOUMD.DSP());
                }
            }
        }
        this.IAudioFileCodec.IAudioInputStream().DSP(hLDzMMEJpHepjqnDHxBExAn2);
    }

    @Override
    public StringBuilder FFT(ReportingController fiypGaFYdzscEBybnfTNrMU) {
        StringBuilder stringBuilder = new StringBuilder(0);
        boolean[] blArray = responseView;
        stringBuilder.append(String.format(this.AdditionalMetadataValue, "Report generated by the MusicScope %s - www.xivero.com", Main.DSP.FFT(2))).append(System.lineSeparator());
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(this.DSP(blArray, " | ", "Track", "Format", "Bit", "Sample rate", "Cut-Off Freq.", "TPL Left", "TPL Right", "TPL Mid", "TPL Side", "RMS Left", "RMS Right", "RMS Mid", "RMS Side", "CREST Avg.", "PLR Avg.", "I-Loudness", "LRA"));
        stringBuilder.append(System.lineSeparator());
        for (BatchItem gDUqxotmDwrsAvlIwIEIdto2 : this.IAudioFileCodec.IAudioFileCodec()) {
            String string;
            OverallValueModel hLDzMMEJpHepjqnDHxBExAn2 = gDUqxotmDwrsAvlIwIEIdto2.IAudioMetaInformation();
            if ((Integer)hLDzMMEJpHepjqnDHxBExAn2.AlacContextModel().DSP() != -1) {
                double d = (Integer)hLDzMMEJpHepjqnDHxBExAn2.AlacContextModel().DSP() / 1000;
                string = String.format(this.AdditionalMetadataValue, "%s %s", d, "kHz");
            } else {
                string = "--- kHz";
            }
            stringBuilder.append(this.DSP(blArray, "   ", hLDzMMEJpHepjqnDHxBExAn2.DSP(), hLDzMMEJpHepjqnDHxBExAn2.FFT(), String.format(this.AdditionalMetadataValue, "%s", hLDzMMEJpHepjqnDHxBExAn2.AdditionalMetadataValue().DSP()), String.format(this.AdditionalMetadataValue, "%s %s", hLDzMMEJpHepjqnDHxBExAn2.responseView().DSP(), hLDzMMEJpHepjqnDHxBExAn2.responseView().FFT()), String.format(this.AdditionalMetadataValue, "%s", string), String.format(this.AdditionalMetadataValue, "%5.1f %s", hLDzMMEJpHepjqnDHxBExAn2.AudioFileExtension().DSP(), hLDzMMEJpHepjqnDHxBExAn2.AudioFileExtension().FFT()), String.format(this.AdditionalMetadataValue, "%5.1f %s", hLDzMMEJpHepjqnDHxBExAn2.IAudioFileCodec().DSP(), hLDzMMEJpHepjqnDHxBExAn2.IAudioFileCodec().FFT()), String.format(this.AdditionalMetadataValue, "%5.1f %s", hLDzMMEJpHepjqnDHxBExAn2.BufferedAacReader().DSP(), hLDzMMEJpHepjqnDHxBExAn2.BufferedAacReader().FFT()), String.format(this.AdditionalMetadataValue, "%5.1f %s", hLDzMMEJpHepjqnDHxBExAn2.AiffAudioCodec().DSP(), hLDzMMEJpHepjqnDHxBExAn2.AiffAudioCodec().FFT()), String.format(this.AdditionalMetadataValue, "%5.1f %s", hLDzMMEJpHepjqnDHxBExAn2.IAudioInputStream().DSP(), hLDzMMEJpHepjqnDHxBExAn2.IAudioInputStream().FFT()), String.format(this.AdditionalMetadataValue, "%5.1f %s", hLDzMMEJpHepjqnDHxBExAn2.IAudioMetaInformation().DSP(), hLDzMMEJpHepjqnDHxBExAn2.IAudioMetaInformation().FFT()), String.format(this.AdditionalMetadataValue, "%5.1f %s", hLDzMMEJpHepjqnDHxBExAn2.AiffMetaDataModel().DSP(), hLDzMMEJpHepjqnDHxBExAn2.AiffMetaDataModel().FFT()), String.format(this.AdditionalMetadataValue, "%5.1f %s", hLDzMMEJpHepjqnDHxBExAn2.AlacAudioCodec().DSP(), hLDzMMEJpHepjqnDHxBExAn2.AlacAudioCodec().FFT()), String.format(this.AdditionalMetadataValue, "%5.1f %s", hLDzMMEJpHepjqnDHxBExAn2.IBaseAudioCodec().DSP(), hLDzMMEJpHepjqnDHxBExAn2.IBaseAudioCodec().FFT()), String.format(this.AdditionalMetadataValue, "%5.1f %s", hLDzMMEJpHepjqnDHxBExAn2.MetaInfomationCopy().DSP(), hLDzMMEJpHepjqnDHxBExAn2.MetaInfomationCopy().FFT()), String.format(this.AdditionalMetadataValue, "%5.1f %s", hLDzMMEJpHepjqnDHxBExAn2.AacMetaDataModel().DSP(), hLDzMMEJpHepjqnDHxBExAn2.AacMetaDataModel().FFT()), String.format(this.AdditionalMetadataValue, "%5.1f %s", hLDzMMEJpHepjqnDHxBExAn2.AacAudioCodec().DSP(), hLDzMMEJpHepjqnDHxBExAn2.AacAudioCodec().FFT())));
            stringBuilder.append(System.lineSeparator());
        }
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(String.format(this.AdditionalMetadataValue, "%s: %5.1f %s", "Playlist Loudness Range", this.FFT(), "dB"));
        stringBuilder.append(System.lineSeparator());
        return stringBuilder;
    }

    @Override
    public void DSP(IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA, IAudioMetaInformation yGjBevanihqaxYKnUNtrNeA2) {
        this.AudioFileExtension = yGjBevanihqaxYKnUNtrNeA2;
    }

    @Override
    public void DSP(String string) {
        this.IAudioInputStream = new File(string);
    }

    private ArrayList<AbstractMap.SimpleEntry<String, Object>> responseView(ReportingController fiypGaFYdzscEBybnfTNrMU) {
        ArrayList<AbstractMap.SimpleEntry<String, Object>> arrayList = new ArrayList<AbstractMap.SimpleEntry<String, Object>>(0);
        for (IValueReporting<?> oJKdRGEwipDKnLCbDZfxxHL2 : fiypGaFYdzscEBybnfTNrMU.FFT()) {
            Object object;
            String string;
            String string2;
            ReportingParameter fegnCROFfntWhihpSdONLyP2;
            Object obj = oJKdRGEwipDKnLCbDZfxxHL2.AudioFileExtension();
            if (obj == null) continue;
            Class<?> clazz = obj.getClass();
            Field[] fieldArray = clazz.getDeclaredFields();
            Method[] methodArray = clazz.getDeclaredMethods();
            for (Field field : fieldArray) {
                fegnCROFfntWhihpSdONLyP2 = field.getAnnotation(ReportingParameter.class);
                if (fegnCROFfntWhihpSdONLyP2 == null) continue;
                string2 = fegnCROFfntWhihpSdONLyP2.DSP();
                string = fegnCROFfntWhihpSdONLyP2.FFT();
                object = fegnCROFfntWhihpSdONLyP2.responseView();
                field.setAccessible(true);
                if (Arrays.asList(object).contains(this.AudioFileExtension.MetaInfomationCopy().FFT().toString())) continue;
                try {
                    Object object2 = field.get(obj);
                    arrayList.add(new AbstractMap.SimpleEntry<String, ValueModel>(string2, new ValueModel(object2, string)));
                }
                catch (IllegalAccessException | IllegalArgumentException exception) {
                    // empty catch block
                }
            }
            for (AccessibleObject accessibleObject : methodArray) {
                fegnCROFfntWhihpSdONLyP2 = ((Method)accessibleObject).getAnnotation(ReportingParameter.class);
                if (fegnCROFfntWhihpSdONLyP2 == null) continue;
                string2 = fegnCROFfntWhihpSdONLyP2.DSP();
                string = fegnCROFfntWhihpSdONLyP2.FFT();
                accessibleObject.setAccessible(true);
                try {
                    object = ((Method)accessibleObject).invoke(obj, new Object[0]);
                    arrayList.add(new AbstractMap.SimpleEntry<String, ValueModel>(string2, new ValueModel(object, string)));
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
                    Logger.getLogger(SimpleValueComposer.class.getName()).log(Level.SEVERE, null, exception);
                }
            }
            arrayList.add(new AbstractMap.SimpleEntry<String, ValueModel>("", new ValueModel("", "")));
        }
        return arrayList;
    }

    private String DSP(boolean[] blArray, String string, Object ... objectArray) {
        String string2 = "";
        for (int i = 0; i < blArray.length; ++i) {
            if (!blArray[i]) continue;
            string2 = string2 + String.format("%" + FFT[i] + "s", objectArray[i]);
            string2 = string2 + (i == blArray.length - 1 ? "" : string);
        }
        return string2;
    }

    private double FFT() {
        double d = 0.0;
        for (BatchItem gDUqxotmDwrsAvlIwIEIdto2 : this.IAudioFileCodec.IAudioFileCodec()) {
            double d2 = (Double)gDUqxotmDwrsAvlIwIEIdto2.IAudioMetaInformation().AacAudioCodec().DSP();
            d += Math.pow(10.0, d2 / 20.0);
        }
        return 20.0 * Math.log10(d / (double)this.IAudioFileCodec.IAudioFileCodec().size());
    }

    private String DSP(int n, String string) {
        if (string.length() > n) {
            String string2 = string.substring(0, n / 2 - 3);
            String string3 = string.substring(string.length() - n / 2, string.length());
            return string2 + "..." + string3;
        }
        return string;
    }
}

