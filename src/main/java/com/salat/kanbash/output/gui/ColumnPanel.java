package com.salat.kanbash.output.gui;

import com.salat.kanbash.output.common.Numeration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Arrays;
import java.util.List;

public class ColumnPanel extends JPanel {
    public interface ColumnAction {
        String getActionName();

        void execute();
    }

    public interface ColumnItemAction {
        String getActionName();

        void execute(int index);
    }

    private final JPanel columnItemsList;
    private final List<? extends ColumnItemAction> columnItemActions;

    private Numeration numeration = Numeration.HYPHEN;

    public ColumnPanel(
            String name,
            List<? extends ColumnAction> columnActions,
            List<? extends ColumnItemAction> columnItemActions
    ) {
        this.columnItemActions = columnItemActions;
        setLayout(new GridBagLayout());

        JLabel columnNameLabel = new JLabel(name);
        add(columnNameLabel, new GridBagConstraints(
                        0,
                        0,
                        1,
                        1,
                        1,
                        0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, 0, 0),
                        0,
                        0
                )
        );

        JPopupMenu menu = new JPopupMenu();
        for (var columnAction : columnActions) {
            JMenuItem menuItem = new JMenuItem(columnAction.getActionName());
            menuItem.addActionListener(e -> columnAction.execute());
            menu.add(menuItem);
        }
        columnNameLabel.setComponentPopupMenu(menu);

        columnItemsList = new JPanel(new GridBagLayout());
        JScrollPane columnItemsScroll = new JScrollPane(columnItemsList);
        columnItemsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        columnItemsScroll.getVerticalScrollBar().setUnitIncrement(10);
        add(columnItemsScroll, new GridBagConstraints(
                0,
                1,
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

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Arrays.stream(columnItemsList.getComponents())
                        .filter(c -> c instanceof ColumnItem)
                        .map(c -> (ColumnItem) c)
                        .forEach(c -> c.onParentResized(e.getComponent()));
            }
        });
    }

    public void setNumeration(Numeration numeration) {
        this.numeration = numeration;
    }

    public void setSpecialItemAddButton(JPanel panel) {
        columnItemsList.add(panel, new GridBagConstraints(
                0,
                columnItemsList.getComponents().length + 1,
                1,
                1,
                1,
                0,
                GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 5, 5),
                0,
                0
        ));
    }

    public void setItems(List<String> items) {
        columnItemsList.removeAll();

        GridBagConstraints gbc = new GridBagConstraints(
                0,
                1,
                1,
                1,
                1,
                0,
                GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 5, 5),
                0,
                0
        );

        for (int i = 0; i < items.size(); i++) {
            final String item = items.get(i);
            final int index = i;
            ColumnItem itemPanel = new ColumnItem(index, item, numeration);

            JPopupMenu menu = new JPopupMenu();
            for (var columnItemAction : columnItemActions) {
                JMenuItem menuItem = new JMenuItem(columnItemAction.getActionName());
                menuItem.addActionListener(e -> columnItemAction.execute(index));
                menu.add(menuItem);
            }
            itemPanel.setComponentPopupMenu(menu);

            if (index == items.size() - 1) {
                gbc.weighty = 1; // last element would "press" others
            }
            columnItemsList.add(itemPanel, gbc);
            gbc.gridy++;
        }
        SwingUtilities.invokeLater(columnItemsList::updateUI);
    }
}
