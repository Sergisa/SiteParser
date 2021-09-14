package com.company.ui;

import mdlaf.themes.MaterialLiteTheme;
import mdlaf.utils.MaterialBorders;

import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;

public class CustomTheme extends MaterialLiteTheme {

    @Override
    public int getArcButton() {
        return 7;
    }

    @Override
    public ColorUIResource getBackgroundTextField() {
        return null;
    }

    @Override
    public BorderUIResource getBorderList() {
        return MaterialBorders.LIGHT_SHADOW_BORDER;
    }
}
