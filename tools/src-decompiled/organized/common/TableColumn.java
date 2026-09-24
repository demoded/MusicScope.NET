/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface TableColumn {
    public String INavigationControlListener();

    public int LraControl() default 0x7FFFFFFF;

    public int NavigationControl() default 0;

    public String GenericSearchController() default "-";
}

