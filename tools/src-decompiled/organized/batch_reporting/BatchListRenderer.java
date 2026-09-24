/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import sdfgjkljljoftrytrszgijpokjprs.BatchController;

public class BatchListRenderer
extends DefaultTableCellRenderer {
    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable jTable, Object object, boolean bl, boolean bl2, int n, int n2) {
        Component component = super.getTableCellRendererComponent(jTable, object, bl, bl2, n, n2);
        component.setBackground(Color.WHITE);
        if (object instanceof String) {
            Font font = component.getFont();
            if (BatchController.DSP().DSP(n).IAudioInputStream()) {
                component.setForeground(Color.decode("#55b9ff").darker());
                component.setFont(font.deriveFont(font.getStyle() | 1));
            } else {
                component.setForeground(Color.BLACK);
                component.setFont(font.deriveFont(font.getStyle() & 0xFFFFFFFE));
            }
        }
        return component;
    }
}

