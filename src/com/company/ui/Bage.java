package com.company.ui;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Bage extends JLabel {
    private final int radius = 4;

    public Bage(String text) {
        super(text);
        setBorder(new EmptyBorder(new Insets(4, 7, 4, 7)));
        setForeground(CustomColor.brown);
        setFont(new Font("Raleway Medium", Font.PLAIN, 14));
        setBackground(CustomColor.beige);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Graphics2D g2 = (Graphics2D) g.create();
        Color bgSaved = getBackground();
        paintBackground(this, g2, 0, 0, getWidth(), getHeight());
        setBackground(null);
        super.paint(g2);
        setBackground(bgSaved);
    }

    private void paintBackground(JLabel b, Graphics2D g2d, int x, int y, int width, int height) {
        g2d.setColor(b.getBackground());
        g2d.fillRoundRect(x, y, width, height, (int) (getCornerRadius(b.getFont()) * radius),
                (int) (getCornerRadius(b.getFont()) * radius));
    }

    private float getCornerRadius(Font f) {
        return StyleUtil.getAdjustedSize(f.getSize(), 2, 6, 1, false);
    }
}
