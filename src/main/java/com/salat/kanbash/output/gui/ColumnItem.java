package com.salat.kanbash.output.gui;

import com.salat.kanbash.output.common.Numeration;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

class ColumnItem extends JPanel {
    private final JTextPane textPane;

    public ColumnItem(int index, String value, Numeration numeration) {
        setLayout(new BorderLayout());

        setBorder(BorderFactory.createRaisedSoftBevelBorder());

        String formattedValue = switch (numeration) {
            case NUMBER -> String.format("%d. %s", index, value);
            case HYPHEN -> String.format("- %s", value);
        };

        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setText(formattedValue);

        var backgroundColor = Optional.ofNullable(UIManager.getColor("Panel.background")).orElse(Color.white);
        setBackground(backgroundColor);
        textPane.setBackground(backgroundColor);

        add(textPane, BorderLayout.CENTER);
    }

    @Override
    public void setComponentPopupMenu(JPopupMenu popup) {
        super.setComponentPopupMenu(popup);
        textPane.setComponentPopupMenu(popup);
    }
}
