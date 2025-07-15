package entities.helpers.output.gui;

import entities.content.Column;
import entities.content.Numeration;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoardPanel extends JPanel {
    private final ColumnPanel shouldDoPanel;
    private final ColumnPanel inProgressPanel;
    private final ColumnPanel donePanel;

    private final JRadioButton hyphen;
    private final JRadioButton number;

    private final List<BoardListener> listeners = new ArrayList<>();

    private class ClearAction implements ColumnPanel.ColumnAction {
        private final Column column;

        public ClearAction(Column column) {
            this.column = column;
        }

        @Override
        public String getActionName() {
            return "Clear";
        }

        @Override
        public void execute() {
            listeners.forEach(l -> l.onClear(column));
        }
    }

    private class MoveAction implements ColumnPanel.ColumnItemAction {
        private final String actionName;
        private final Column oldColumn;
        private final Column newColumn;

        public MoveAction(String actionName, Column oldColumn, Column newColumn) {
            this.actionName = actionName;
            this.oldColumn = oldColumn;
            this.newColumn = newColumn;
        }

        @Override
        public String getActionName() {
            return actionName;
        }

        @Override
        public void execute(int index) {
            listeners.forEach(l -> l.onMove(oldColumn, index, newColumn));
        }
    }

    private class EditAction implements ColumnPanel.ColumnItemAction {
        private final Column column;

        public EditAction(Column column) {
            this.column = column;
        }

        @Override
        public String getActionName() {
            return "Edit";
        }

        @Override
        public void execute(int index) {
            String taskText = JOptionPane.showInputDialog("New task text");
            listeners.forEach(l -> l.onEdit(column, index, taskText));
        }
    }

    private class RemoveAction implements ColumnPanel.ColumnItemAction {
        private final Column column;

        public RemoveAction(Column column) {
            this.column = column;
        }

        @Override
        public String getActionName() {
            return "Remove";
        }

        @Override
        public void execute(int index) {
            listeners.forEach(l -> l.onRemove(column, index));
        }
    }

    public BoardPanel() {
        setLayout(new GridBagLayout());

        shouldDoPanel = new ColumnPanel(
                "SHOULD-DO",
                List.of(new ClearAction(Column.SHOULD_DO)),
                List.of(
                        new MoveAction(
                                "Pick",
                                Column.SHOULD_DO,
                                Column.IN_PROGRESS
                        ),
                        new EditAction(Column.SHOULD_DO),
                        new RemoveAction(Column.SHOULD_DO)
                )
        );
        inProgressPanel = new ColumnPanel(
                "IN-PROGRESS",
                List.of(new ClearAction(Column.IN_PROGRESS)),
                List.of(
                        new MoveAction(
                                "Done",
                                Column.IN_PROGRESS,
                                Column.DONE
                        ),
                        new EditAction(Column.IN_PROGRESS),
                        new RemoveAction(Column.IN_PROGRESS)
                )
        );
        donePanel = new ColumnPanel(
                "DONE",
                List.of(new ClearAction(Column.DONE)),
                List.of(
                        new EditAction(Column.DONE),
                        new RemoveAction(Column.DONE)
                )
        );

        JPanel columnPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints(
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
        );

        gbc.gridx = 0;
        columnPanel.add(shouldDoPanel, gbc);

        gbc.gridx = 1;
        columnPanel.add(inProgressPanel, gbc);

        gbc.gridx = 2;
        columnPanel.add(donePanel, gbc);

        add(columnPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        JPanel bottomPanel = new JPanel(new GridBagLayout());

        JButton newTaskButton = new JButton("New task");
        newTaskButton.addActionListener(e -> {
            String taskText = JOptionPane.showInputDialog(this, "Write task text");
            listeners.forEach(l -> l.onNewTask(Column.SHOULD_DO, taskText));
        });
        bottomPanel.add(newTaskButton, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        JPanel numerationSelectionPanel = new JPanel();
        JLabel numerationLabel = new JLabel("Numeration: ");
        ButtonGroup numerationSelector = new ButtonGroup();
        hyphen = new JRadioButton("Hyphen");
        hyphen.addActionListener(e -> listeners.forEach(l -> l.onSetNumeration(Numeration.HYPHEN)));
        number = new JRadioButton("Number");
        number.addActionListener(e -> listeners.forEach(l -> l.onSetNumeration(Numeration.NUMBER)));
        numerationSelector.add(hyphen);
        numerationSelector.add(number);
        numerationSelectionPanel.add(numerationLabel);
        numerationSelectionPanel.add(hyphen);
        numerationSelectionPanel.add(number);
        bottomPanel.add(numerationSelectionPanel, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        add(bottomPanel, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

    public void setShouldDoTasks(List<String> tasks) {
        shouldDoPanel.setItems(tasks);
    }

    public void setInProgressTasks(List<String> tasks) {
        inProgressPanel.setItems(tasks);
    }

    public void setDoneTasks(List<String> tasks) {
        donePanel.setItems(tasks);
    }

    public void setNumerationSet(boolean isNumerationUsed) {
        shouldDoPanel.setNumerationUsed(isNumerationUsed);
        inProgressPanel.setNumerationUsed(isNumerationUsed);
        donePanel.setNumerationUsed(isNumerationUsed);

        if (isNumerationUsed) {
            number.setSelected(true);
        } else {
            hyphen.setSelected(true);
        }
    }

    public void addBoardListener(BoardListener listener) {
        listeners.add(listener);
    }

    public interface BoardListener {
        void onNewTask(Column column, String taskText);

        void onMove(Column oldColumn, int index, Column newColumn);

        void onClear(Column column);

        void onRemove(Column column, int index);

        void onEdit(Column column, int index, String taskText);

        void onSetNumeration(Numeration numeration);
    }
}
