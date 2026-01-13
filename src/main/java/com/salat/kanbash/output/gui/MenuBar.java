package com.salat.kanbash.output.gui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MenuBar extends JMenuBar {
    private final ButtonGroup themeGroup;

    private final List<MenuBarListener> listeners = new ArrayList<>();

    public MenuBar() {
        JMenu viewMenu = new JMenu("View");
        JMenu themeMenu = new JMenu("Theme");

        themeGroup = new ButtonGroup();
        for (var theme : Theme.values()) {
            JRadioButtonMenuItem themeButtonItem = new JRadioButtonMenuItem(theme.displayName);

            themeButtonItem.addActionListener(
                    e -> listeners.forEach(
                            l -> l.onThemeChanged(theme)
                    )
            );

            themeGroup.add(themeButtonItem);
            themeMenu.add(themeButtonItem);
        }
        viewMenu.add(themeMenu);
        add(viewMenu);
    }

    public void setTheme(Theme theme) {
        themeGroup.getElements().asIterator().forEachRemaining(b -> {
            if (b.getText().equals(theme.displayName)) {
                b.setSelected(true);
            }
        });

        theme.applyFunction.run();
    }

    public void addMenuBarListener(MenuBarListener listener) {
        listeners.add(listener);
    }

    public interface MenuBarListener {
        void onThemeChanged(Theme newTheme);
    }
}
