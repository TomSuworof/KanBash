package com.salat.kanbash.output.gui;

import com.salat.kanbash.output.common.Numeration;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

        textPane.setCaret(new NoSelectCaret());

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

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        super.addMouseListener(l);
        textPane.addMouseListener(l);
    }
}

class NoSelectCaret extends DefaultCaret {
    public NoSelectCaret() {
        setUpdatePolicy(NEVER_UPDATE);
    }

    @Override
    protected void moveCaret(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }
}
