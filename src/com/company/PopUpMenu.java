package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class PopUpMenu extends JPopupMenu {
    JMenuItem anItem;
    Component triggerElement;
    MenuActionListener actionListener;

    public PopUpMenu() {
        anItem = new JMenuItem("Удалить");
        anItem.addActionListener(this::removeAction);
        add(anItem);
    }

    public PopUpMenu(Component component) {
        this();
        triggerElement = component;
    }

    private void removeAction(ActionEvent actionEvent) {
        if (actionListener != null) {
            actionListener.action();
        }

    }

    public void setActionListener(MenuActionListener actionListener) {
        this.actionListener = actionListener;
    }
}