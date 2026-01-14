package com.salat.kanbash.output.gui;

import com.salat.kanbash.output.common.Numeration;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MenuBar extends JMenuBar {
    private final ButtonGroup numerationGroup;
    private final ButtonGroup themeGroup;

    private final List<MenuBarListener> listeners = new ArrayList<>();

    public MenuBar() {
        JMenu viewMenu = new JMenu("View");

        numerationGroup = new ButtonGroup();
        JMenu numerationMenu = new JMenu("Numeration");
        for (var numeration : Numeration.values()) {
            JRadioButtonMenuItem numerationButtonItem = new JRadioButtonMenuItem(numeration.displayName);
            numerationButtonItem.addActionListener(
                    e -> listeners.forEach(
                            l -> l.onNumerationChanged(numeration)
                    )
            );
            numerationGroup.add(numerationButtonItem);
            numerationMenu.add(numerationButtonItem);
        }
        viewMenu.add(numerationMenu);

        themeGroup = new ButtonGroup();
        JMenu themeMenu = new JMenu("Theme");
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

    public void setNumeration(Numeration numeration) {
        numerationGroup.getElements().asIterator().forEachRemaining(b -> {
            if (b.getText().equals(numeration.displayName)) {
                b.setSelected(true);
            }
        });
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
        void onNumerationChanged(Numeration numeration);

        void onThemeChanged(Theme newTheme);
    }
}
