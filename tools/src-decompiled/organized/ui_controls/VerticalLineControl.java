/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

public class VerticalLineControl
extends JComponent {
    private static final long serialVersionUID = 1L;

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics2D.setColor(Color.decode("#009000"));
        graphics2D.setStroke(new BasicStroke(1.0f));
        graphics2D.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
    }
}

