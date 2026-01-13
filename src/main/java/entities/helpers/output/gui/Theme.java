package entities.helpers.output.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.util.Objects;

public enum Theme {
    SYSTEM(
            "System",
            () -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    ),
    CROSS_PLATFORM(
            "Cross-platform (metal)",
            () -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    ),
    INTELLIJ_LIGHT(
            "IntelliJ Light",
            FlatIntelliJLaf::setup
    ),
    INTELLIJ_DARK(
            "IntelliJ Darcula",
            FlatDarculaLaf::setup
    );

    public final String displayName;
    public final Runnable applyFunction;

    Theme(String displayName, Runnable applyFunction) {
        this.displayName = displayName;
        this.applyFunction = applyFunction;
    }

    public static Theme byName(String name) {
        for (var theme : values()) {
            if (Objects.equals(name, theme.name())) {
                return theme;
            }
        }
        return null;
    }
}
