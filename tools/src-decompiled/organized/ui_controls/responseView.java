/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jaudiotagger.audio.AudioFile
 *  org.jaudiotagger.audio.AudioFileIO
 *  org.jaudiotagger.audio.exceptions.CannotReadException
 *  org.jaudiotagger.audio.exceptions.InvalidAudioFrameException
 *  org.jaudiotagger.audio.exceptions.ReadOnlyFileException
 *  org.jaudiotagger.tag.FieldKey
 *  org.jaudiotagger.tag.KeyNotFoundException
 *  org.jaudiotagger.tag.Tag
 *  org.jaudiotagger.tag.TagException
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.File;
import java.io.IOException;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import sdfgjkljljoftrytrszgijpokjprs.FFT;
import sdfgjkljljoftrytrszgijpokjprs.AdditionalMetadataValue;

public class responseView {
    public static FFT DSP(String string) {
        if (string.isEmpty()) {
            return null;
        }
        return responseView.DSP(new File(string));
    }

    public static FFT DSP(File file) {
        FFT qPeIwmpzLZIktKLXAJOQHcO = new FFT();
        try {
            int n;
            Object object;
            AudioFile audioFile = AudioFileIO.read((File)file);
            Tag tag = audioFile.getTag();
            if (tag == null) {
                return qPeIwmpzLZIktKLXAJOQHcO;
            }
            if (tag.hasField(FieldKey.ALBUM_ARTIST)) {
                object = tag.getAll(FieldKey.ALBUM_ARTIST);
                for (n = 0; n < object.size(); ++n) {
                    object.set(n, ((String)object.get(n)).trim());
                }
                qPeIwmpzLZIktKLXAJOQHcO.IAudioFileCodec(new AdditionalMetadataValue<String[]>(object.toArray(new String[object.size()])));
            }
            if (tag.hasField(FieldKey.ALBUM)) {
                object = tag.getFirst(FieldKey.ALBUM);
                qPeIwmpzLZIktKLXAJOQHcO.AdditionalMetadataValue(new AdditionalMetadataValue<String>(((String)object).trim()));
            }
            if (tag.hasField(FieldKey.DISC_NO)) {
                try {
                    object = tag.getFirst(FieldKey.DISC_NO);
                    qPeIwmpzLZIktKLXAJOQHcO.AacAudioCodec(new AdditionalMetadataValue<Integer>(Integer.parseInt((String)object)));
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
            if (tag.hasField(FieldKey.GENRE)) {
                object = tag.getFirst(FieldKey.GENRE);
                qPeIwmpzLZIktKLXAJOQHcO.FFT(new AdditionalMetadataValue<String>(((String)object).trim()));
            }
            if (tag.hasField("LABEL")) {
                object = tag.getFirst("LABEL");
                qPeIwmpzLZIktKLXAJOQHcO.responseView(new AdditionalMetadataValue<String>(((String)object).trim()));
            }
            if (tag.hasField(FieldKey.PRODUCER) && !qPeIwmpzLZIktKLXAJOQHcO.responseView().FFT()) {
                object = tag.getFirst(FieldKey.PRODUCER);
                qPeIwmpzLZIktKLXAJOQHcO.responseView(new AdditionalMetadataValue<String>(((String)object).trim()));
            }
            if (tag.hasField(FieldKey.DISC_TOTAL)) {
                try {
                    object = tag.getFirst(FieldKey.DISC_TOTAL);
                    qPeIwmpzLZIktKLXAJOQHcO.MetaInfomationCopy(new AdditionalMetadataValue<Integer>(Integer.parseInt((String)object)));
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
            if (tag.hasField(FieldKey.TRACK_TOTAL)) {
                try {
                    object = tag.getFirst(FieldKey.TRACK_TOTAL);
                    qPeIwmpzLZIktKLXAJOQHcO.IAudioInputStream(new AdditionalMetadataValue<Integer>(Integer.parseInt((String)object)));
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
            if (tag.hasField(FieldKey.YEAR)) {
                try {
                    object = tag.getFirst(FieldKey.YEAR);
                    qPeIwmpzLZIktKLXAJOQHcO.IBaseAudioCodec(new AdditionalMetadataValue<Integer>(Integer.parseInt((String)object)));
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
            if (tag.hasField(FieldKey.ARTIST)) {
                object = tag.getAll(FieldKey.ARTIST);
                for (n = 0; n < object.size(); ++n) {
                    object.set(n, ((String)object.get(n)).trim());
                }
                qPeIwmpzLZIktKLXAJOQHcO.AudioFileExtension(new AdditionalMetadataValue<String[]>(object.toArray(new String[object.size()])));
            }
            if (tag.hasField(FieldKey.TITLE)) {
                object = tag.getFirst(FieldKey.TITLE);
                qPeIwmpzLZIktKLXAJOQHcO.DSP(new AdditionalMetadataValue<String>(((String)object).trim()));
            }
            if (tag.hasField(FieldKey.TRACK)) {
                try {
                    object = tag.getFirst(FieldKey.TRACK);
                    qPeIwmpzLZIktKLXAJOQHcO.IAudioMetaInformation(new AdditionalMetadataValue<Integer>(Integer.parseInt((String)object)));
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
            if (tag.hasField(FieldKey.COMPOSER)) {
                object = tag.getFirst(FieldKey.COMPOSER);
                qPeIwmpzLZIktKLXAJOQHcO.AacMetaDataModel(new AdditionalMetadataValue<String>(((String)object).trim()));
            }
        }
        catch (IOException | CannotReadException | InvalidAudioFrameException | ReadOnlyFileException | KeyNotFoundException | TagException throwable) {
            return null;
        }
        return qPeIwmpzLZIktKLXAJOQHcO;
    }
}

