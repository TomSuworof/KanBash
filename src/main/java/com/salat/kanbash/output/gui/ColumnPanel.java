package com.salat.kanbash.output.gui;

import com.salat.kanbash.output.common.Numeration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ColumnPanel extends JPanel {
    public interface ColumnAction {
        String getActionName();

        void execute();
    }

    public interface ColumnItemAction {
        String getActionName();

        void execute(int index);

        default boolean isPrimaryAction() {
            return false;
        }
    }

    private static class ColumnItemsList extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            // Hack to keep column width equal to viewport and resize child components
            int width = getParent().getSize().width;
            int height = super.getPreferredSize().height;
            return new Dimension(width, height);
        }
    }

    private final ColumnItemsList columnItemsList;
    private final List<? extends ColumnItemAction> columnItemActions;

    private Numeration numeration = Numeration.HYPHEN;

    public ColumnPanel(
            String name,
            List<? extends ColumnAction> columnActions,
            List<? extends ColumnItemAction> columnItemActions
    ) {
        this.columnItemActions = columnItemActions;
        setLayout(new BorderLayout());

        JLabel columnNameLabel = new JLabel(name);
        columnNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        add(columnNameLabel, BorderLayout.NORTH);

        JPopupMenu menu = new JPopupMenu();
        for (var columnAction : columnActions) {
            JMenuItem menuItem = new JMenuItem(columnAction.getActionName());
            menuItem.addActionListener(e -> columnAction.execute());
            menu.add(menuItem);
        }
        columnNameLabel.setComponentPopupMenu(menu);

        columnItemsList = new ColumnItemsList();
        columnItemsList.setLayout(new GridBagLayout());
        JScrollPane columnItemsScroll = new JScrollPane(columnItemsList);
        columnItemsScroll.getVerticalScrollBar().setUnitIncrement(10);
        add(columnItemsScroll, BorderLayout.CENTER);
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

            itemPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                        columnItemActions.stream()
                                .filter(ColumnItemAction::isPrimaryAction)
                                .findFirst()
                                .ifPresent(action -> action.execute(index));
                    }
                }
            });

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
    }
}
