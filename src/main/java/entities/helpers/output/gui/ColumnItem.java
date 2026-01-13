package entities.helpers.output.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

class ColumnItem extends JPanel {
    private final JTextArea textArea;

    public ColumnItem(int index, String value, boolean isNumerationUsed) {
        setLayout(new GridBagLayout());

        setBorder(BorderFactory.createRaisedSoftBevelBorder());

        String formattedValue = isNumerationUsed ?
                String.format("%d. %s", index, value) :
                String.format("- %s", value);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(false);
        textArea.setText(formattedValue);

        var backgroundColor = Optional.ofNullable(UIManager.getColor("Panel.background")).orElse(Color.white);
        setBackground(backgroundColor);
        textArea.setBackground(backgroundColor);

        add(textArea, new GridBagConstraints(
                0,
                0,
                1,
                1,
                1,
                1,
                GridBagConstraints.NORTHWEST,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0),
                0,
                0
        ));
    }

    void onParentResized(Component component) {
        Dimension size = component.getSize();
        // Safe paddings
        size.width -= 10;
        size.height -= 10;

        String text = textArea.getText();

        FontMetrics fontMetrics = textArea.getFontMetrics(textArea.getFont());
        int charWidth = fontMetrics.charWidth('m');
        int charHeight = fontMetrics.getHeight();
        int textWidth = fontMetrics.stringWidth(text);

        int columns = (size.width + 1) / charWidth;
        int rows = (textWidth + 1) / size.width;

        textArea.setColumns(columns);
        textArea.setRows(rows);
        textArea.setSize(new Dimension(
                columns * charWidth,
                rows * charHeight
        ));
    }

    @Override
    public void setComponentPopupMenu(JPopupMenu popup) {
        super.setComponentPopupMenu(popup);
        textArea.setComponentPopupMenu(popup);
    }
}
