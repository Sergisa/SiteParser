package com.company.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class CustomButton extends JButton {
    private Rectangle viewRect = new Rectangle();
    private Rectangle iconRect = new Rectangle();
    private Rectangle textRect = new Rectangle();
    private final int radius = 4;

    public CustomButton(String text) {
        super(text);
        setForeground(Color.WHITE);
        setBackground(Color.blue);
        setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
    }

    @Override
    public void paint(Graphics g) {
        //super.paint(g);


        final AbstractButton b = (AbstractButton) this;
        ButtonModel m = b.getModel();

        FontMetrics fm = g.getFontMetrics();

        Insets i = getInsets();

        viewRect.x = i.left;
        viewRect.y = i.top;
        viewRect.width = b.getWidth() - (i.right + viewRect.x);
        viewRect.height = b.getHeight() - (i.bottom + viewRect.y);

        textRect.x = textRect.y = textRect.width = textRect.height = 0;
        iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;

        Font f = getFont();

        // layout the text and icon
        String text = SwingUtilities.layoutCompoundLabel(this, fm, b.getText(), b.getIcon(),
                b.getVerticalAlignment(), b.getHorizontalAlignment(),
                b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
                viewRect, iconRect, textRect,
                b.getText() == null ? 0 : b.getIconTextGap());

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        View v = (View) getClientProperty(BasicHTML.propertyKey);
        g2d.setFont(f);

        paintBackground(b, g2d, 0, 0, b.getWidth(), b.getHeight());

        if (v != null)
            v.paint(g2d, textRect);
        else
            paintButtonText(g2d, b, textRect, text);

        // Paint the Icon
        if (b.getIcon() != null)
            paintIcon(g2d, this, iconRect);
    }

    private void paintGradientBackground(AbstractButton b, Graphics2D g2d, int x, int y, int width, int height, Color startColor, Color endColor) {
        LinearGradientPaint gradient = new LinearGradientPaint(x, y, x, y + height,
                new float[]{0.0f, 1.0f},
                new Color[]{startColor, endColor});

        g2d.setPaint(gradient);
        g2d.fillRoundRect(x, y, width, height, (int) (radius * 2), (int) (radius * 2));

    }

    private void paintBackground(AbstractButton b, Graphics2D g2d, int x, int y, int width, int height) {
        if (getModel().isPressed()) {
            g2d.setColor(getBackground().darker());
        } else {
            g2d.setColor(getBackground());
        }
        g2d.fillRoundRect(x, y, width, height, (int) (radius * 2), (int) (radius * 2));

    }

    protected void paintIcon(Graphics g, JComponent c, Rectangle iconRect) {
        Graphics2D graphics = (Graphics2D) g.create();
        AbstractButton b = (AbstractButton) c;
        Icon icon = b.getIcon();
        icon.paintIcon(b, graphics, iconRect.x, iconRect.y);
        graphics.dispose();
    }

    private void paintButtonText(Graphics g, AbstractButton button,
                                 Rectangle textRect, String text) {
        paintText(g, button, textRect, text, button.getDisplayedMnemonicIndex(), getFont(), getForeground(), null, null);
    }

    private static void paintText(Graphics g, JComponent comp, Rectangle textRect,
                                  String text, int mnemonicIndex, Font font, Color color, Rectangle clip,
                                  AffineTransform transform) {
        if ((text == null) || (text.isEmpty()))
            return;

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setFont(font);
        g2d.setColor(color);
        if (clip != null)
            g2d.clip(clip);
        if (transform != null)
            g2d.transform(transform);
        BasicGraphicsUtils.drawStringUnderlineCharAt(g2d, text, mnemonicIndex,
                textRect.x, textRect.y + g2d.getFontMetrics().getAscent());
        g2d.dispose();
    }

}
