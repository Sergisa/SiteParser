package com.company;

import com.company.ui.CustomTheme;
import mdlaf.MaterialLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    static MainForm form;
    public static void main(String[] args) {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Raleway-Medium.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Raleway-Regular.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Raleway-SemiBold.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Raleway-Thin.ttf")));
            UIManager.setLookAndFeel(new MaterialLookAndFeel(new CustomTheme()));
        } catch (IOException | FontFormatException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                form = new MainForm();
            }
        });
    }
}
