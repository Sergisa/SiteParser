package com.company.ui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

public class CustomInput extends JTextField {
    private String placeholder = "";

    public CustomInput() {
        setBorder(new MyBorder());
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    private static void drawRoundedBorder(Graphics2D g2, int x, int y, int w, int h,
                                          Color borderColor, int arc, boolean boxShadow, Color boxShadowColor) {
        if (boxShadow) {
            g2.setStroke(new BasicStroke(1.2f));
            g2.setColor(boxShadowColor);
            g2.drawRoundRect(x + 3, y + 3, w - 6, h - 6, arc, arc);

            g2.setColor(new Color(boxShadowColor.getRed(), boxShadowColor.getGreen(), boxShadowColor.getBlue(), 80));
            g2.drawRoundRect(x + 2, y + 2, w - 4, h - 4, arc, arc);

            g2.setColor(new Color(boxShadowColor.getRed(), boxShadowColor.getGreen(), boxShadowColor.getBlue(), 40));
            g2.drawRoundRect(x + 1, y + 1, w - 2, h - 2, arc, arc);

            g2.setColor(new Color(boxShadowColor.getRed(), boxShadowColor.getGreen(), boxShadowColor.getBlue(), 20));
            g2.drawRoundRect(x, y, w - 1, h - 1, arc, arc);
        } else {
            g2.setStroke(new BasicStroke(1.0f));
            g2.setColor(borderColor);
            g2.drawRoundRect(x + 1, y + 1, w - 2, h - 2, arc, arc);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //super.paintBackground(g);

        Insets s = getInsets();

        if (getText().isEmpty() && !hasFocus()) {
            JLabel lbl = new JLabel(" " + getPlaceholder());
            lbl.setFont(new Font(getFont().getFontName(), Font.ITALIC, getFont().getSize() - 1));
            lbl.setForeground(Color.gray);
            lbl.setBackground(getBackground());

            SwingUtilities.paintComponent(g, lbl, this, s.left, s.top, getWidth() - s.left - s.right, getHeight() - s.top - s.bottom);
        }
    }

    private class MyBorder extends AbstractBorder {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(8, 12, 8, 12);
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            drawRoundedBorder(g2d,
                    x + 1,
                    y + 1,
                    width - 4,
                    height - 4,
                    null,
                    (int) getCornerRadius() * 4,
                    false,
                    null
            );

            g2d.dispose();
        }

        private float getCornerRadius() {
            return StyleUtil.getAdjustedSize(getFont().getSize(), 2, 6, 1, false);
        }
    }
}
