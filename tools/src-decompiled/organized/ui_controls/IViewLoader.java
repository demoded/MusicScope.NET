/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.BaseViewController;
import javax.swing.JPanel;

public interface IViewLoader {
    public <T extends BaseViewController<? extends JPanel>> void loadView(T var1);

    public void loadContent();

    public void navigateBack();
}

