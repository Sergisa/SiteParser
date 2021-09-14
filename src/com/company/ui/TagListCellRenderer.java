package com.company.ui;

import com.company.Tag;

import javax.swing.*;
import java.awt.*;

public class TagListCellRenderer extends JPanel implements ListCellRenderer<Tag> {
    private final JLabel tagNameLabel;
    private final JLabel tagLinkLabel;
    private final int linePagging = 6;

    public TagListCellRenderer() {
        setBorder(BorderFactory.createEmptyBorder(linePagging, linePagging, linePagging, linePagging));
        setLayout(new FlowLayout(FlowLayout.LEFT, 9, 1));
        //setLayout(new BorderLayout(9, 1));
        tagNameLabel = new JLabel();
        tagNameLabel.setFont(new Font("Montserrat Regular", Font.ITALIC | Font.BOLD, 12));

        tagLinkLabel = new JLabel();
        tagLinkLabel.setFont(new Font("Montserrat Regular", Font.PLAIN, 12));


        add(tagNameLabel);
        add(tagLinkLabel);
        //setBackground(Color.BLUE);
        //setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Tag> list, Tag tag, int index, boolean isSelected, boolean cellHasFocus) {
        tagNameLabel.setText(tag.getName());
        tagLinkLabel.setText(tag.getUrl().toString());

        if (tag.getName().equals(Tag.Type.SCRIPT.tagName)) {
            tagNameLabel.setForeground(new Color(0xB23CFD));
        } else if (tag.getName().equals(Tag.Type.LINK.tagName)) {
            tagNameLabel.setForeground(new Color(0x00B74A));
        }

        if (isSelected) {
            setBackground(new Color(0xBDBDBD));
        } else {
            setBackground(null);
        }
        setForeground(Color.black);
        return this;
    }
}
