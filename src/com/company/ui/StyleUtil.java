package com.company.ui;

import java.awt.*;

public class StyleUtil {
    static int getComponentFontSize(int ptSize) {
        // TODO: Headless.
        int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
        return (int) Math.round(ptSize * screenRes / 72.0);
    }

    static float getAdjustedSize(int fontSize, float baseSize, int forEachBase, float toAdjustBy) {
        int delta = fontSize - 11;

        if (delta <= 0)
            return baseSize;

        return baseSize + delta * toAdjustBy / forEachBase;
    }

    public static int getAdjustedSize(int fontSize, int baseSize,
                                      int forEachBase, int toAdjustBy, boolean toRoundAsEven) {
        int delta = fontSize - 11;

        if (delta <= 0)
            return baseSize;

        int result = baseSize + delta * toAdjustBy / forEachBase;

        if (toRoundAsEven && (result % 2 != 0))
            result--;

        return result;
    }
}
