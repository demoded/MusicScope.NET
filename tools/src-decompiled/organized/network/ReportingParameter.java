/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface ReportingParameter {
    public String DSP();

    public String FFT() default "";

    public String[] responseView() default {""};

    public boolean AdditionalMetadataValue() default true;

    public boolean AudioFileExtension() default false;
}

