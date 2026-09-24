/*
 * Decompiled with CFR 0.152.
 */
package com.xivero.hraa.gui.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ScrollbarStyle
extends BasicScrollBarUI {
    @Override
    public Dimension getPreferredSize(JComponent jComponent) {
        return new Dimension(7, Integer.MAX_VALUE);
    }

    @Override
    protected void paintThumb(Graphics graphics, JComponent jComponent, Rectangle rectangle) {
        graphics.setColor(Color.gray);
        graphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    @Override
    protected void paintTrack(Graphics graphics, JComponent jComponent, Rectangle rectangle) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    @Override
    protected JButton createDecreaseButton(int n) {
        return this.DSP();
    }

    @Override
    protected JButton createIncreaseButton(int n) {
        return this.DSP();
    }

    private JButton DSP() {
        JButton jButton = new JButton();
        jButton.setPreferredSize(new Dimension(0, 0));
        jButton.setMinimumSize(new Dimension(0, 0));
        jButton.setMaximumSize(new Dimension(0, 0));
        return jButton;
    }
}

