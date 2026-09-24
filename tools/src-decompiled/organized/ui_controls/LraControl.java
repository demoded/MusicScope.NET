/*
 * Decompiled with CFR 0.152.
 */
package 83nnfii93jksoiow9;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

public class LraControl
extends JPanel {
    private static final int INavigationControlListener = 5;
    private static final double LraControl = 0.11;
    private static final Color NavigationControl = Color.GREEN;
    private static final Color GenericSearchController = Color.RED;
    private boolean GenericSearchField;
    private JPanel GenericSearchPanel;
    private JLabel SearchItem;
    private JPanel SearchItemPanel;
    private JPanel TabCloseListener;

    public LraControl() {
        this.LraControl();
        this.repaint();
    }

    public boolean INavigationControlListener() {
        return this.GenericSearchField;
    }

    public void INavigationControlListener(boolean intersamplePeaks) {
        this.GenericSearchField = intersamplePeaks;
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.INavigationControlListener(g, GenericSearchController);
    }

    public void INavigationControlListener(Graphics g, Color color) {
        Dimension dimension = this.GenericSearchPanel.getSize();
        int xCenter = (int)dimension.getWidth() / 2;
        int yCenter = (int)dimension.getHeight() / 2;
        int width = (int)dimension.getWidth() - 5;
        int height = (int)dimension.getHeight() - 5;
        int boaderSize = (int)(0.11 * (double)width);
        Graphics2D graphics = (Graphics2D)g.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(color);
        Ellipse2D.Double circle = new Ellipse2D.Double(xCenter - width / 2, yCenter - height / 2, width, height);
        graphics.fill(circle);
        graphics.setColor(this.SearchItemPanel.getBackground());
        Ellipse2D.Double innerCircle = new Ellipse2D.Double(xCenter - width / 2 + boaderSize, yCenter - height / 2 + boaderSize, width - boaderSize * 2, height - boaderSize * 2);
        graphics.fill(innerCircle);
        graphics.dispose();
    }

    private void LraControl() {
        this.SearchItemPanel = new JPanel();
        this.TabCloseListener = new JPanel();
        this.SearchItem = new JLabel();
        this.GenericSearchPanel = new JPanel();
        this.SearchItemPanel.setLayout(new OverlayLayout(this.SearchItemPanel));
        this.TabCloseListener.setFocusable(false);
        this.TabCloseListener.setLayout(new GridBagLayout());
        this.SearchItem.setFont(new Font("Tahoma", 0, 24));
        this.SearchItem.setForeground(new Color(102, 102, 102));
        this.SearchItem.setText("00");
        this.TabCloseListener.add((Component)this.SearchItem, new GridBagConstraints());
        this.SearchItemPanel.add(this.TabCloseListener);
        GroupLayout graphicPanelLayout = new GroupLayout(this.GenericSearchPanel);
        this.GenericSearchPanel.setLayout(graphicPanelLayout);
        graphicPanelLayout.setHorizontalGroup(graphicPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 200, Short.MAX_VALUE));
        graphicPanelLayout.setVerticalGroup(graphicPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 200, Short.MAX_VALUE));
        this.SearchItemPanel.add(this.GenericSearchPanel);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(0, 0, 0).addComponent(this.SearchItemPanel, -1, -1, Short.MAX_VALUE).addGap(0, 0, 0)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.SearchItemPanel, -1, -1, Short.MAX_VALUE));
    }
}

