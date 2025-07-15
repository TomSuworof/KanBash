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

    public BoardPanel() {
        setLayout(new GridBagLayout());

        var clearShouldDoAction = new ColumnPanel.ColumnAction() {
            @Override
            public String getActionName() {
                return "Clear";
            }

            @Override
            public void execute() {
                listeners.forEach(l -> l.onClear(Column.SHOULD_DO));
            }
        };
        var clearInProgressAction = new ColumnPanel.ColumnAction() {
            @Override
            public String getActionName() {
                return "Clear";
            }

            @Override
            public void execute() {
                listeners.forEach(l -> l.onClear(Column.IN_PROGRESS));
            }
        };
        var clearDoneAction = new ColumnPanel.ColumnAction() {
            @Override
            public String getActionName() {
                return "Clear";
            }

            @Override
            public void execute() {
                listeners.forEach(l -> l.onClear(Column.DONE));
            }
        };

        var pickAction = new ColumnPanel.ColumnItemAction() {
            @Override
            public String getActionName() {
                return "Pick";
            }

            @Override
            public void execute(int index) {
                listeners.forEach(l -> l.onPick(index));
            }
        };
        var doneAction = new ColumnPanel.ColumnItemAction() {
            @Override
            public String getActionName() {
                return "Done";
            }

            @Override
            public void execute(int index) {
                listeners.forEach(l -> l.onDone(index));
            }
        };

        var editShouldDoAction = new ColumnPanel.ColumnItemAction() {
            @Override
            public String getActionName() {
                return "Edit";
            }

            @Override
            public void execute(int index) {
                String taskText = JOptionPane.showInputDialog("New task text");
                listeners.forEach(l -> l.onEdit(Column.SHOULD_DO, index, taskText));
            }
        };
        var editInProgressAction = new ColumnPanel.ColumnItemAction() {
            @Override
            public String getActionName() {
                return "Edit";
            }

            @Override
            public void execute(int index) {
                String taskText = JOptionPane.showInputDialog("New task text");
                listeners.forEach(l -> l.onEdit(Column.IN_PROGRESS, index, taskText));
            }
        };
        var editDoneAction = new ColumnPanel.ColumnItemAction() {
            @Override
            public String getActionName() {
                return "Edit";
            }

            @Override
            public void execute(int index) {
                String taskText = JOptionPane.showInputDialog("New task text");
                listeners.forEach(l -> l.onEdit(Column.DONE, index, taskText));
            }
        };

        var removeShouldDoAction = new ColumnPanel.ColumnItemAction() {
            @Override
            public String getActionName() {
                return "Remove";
            }

            @Override
            public void execute(int index) {
                listeners.forEach(l -> l.onRemove(Column.SHOULD_DO, index));
            }
        };
        var removeInProgressAction = new ColumnPanel.ColumnItemAction() {
            @Override
            public String getActionName() {
                return "Remove";
            }

            @Override
            public void execute(int index) {
                listeners.forEach(l -> l.onRemove(Column.IN_PROGRESS, index));
            }
        };
        var removeDoneAction = new ColumnPanel.ColumnItemAction() {
            @Override
            public String getActionName() {
                return "Remove";
            }

            @Override
            public void execute(int index) {
                listeners.forEach(l -> l.onRemove(Column.DONE, index));
            }
        };

        shouldDoPanel = new ColumnPanel(
                "SHOULD-DO",
                List.of(clearShouldDoAction),
                List.of(pickAction, editShouldDoAction, removeShouldDoAction)
        );
        inProgressPanel = new ColumnPanel(
                "IN-PROGRESS",
                List.of(clearInProgressAction),
                List.of(doneAction, editInProgressAction, removeInProgressAction)
        );
        donePanel = new ColumnPanel(
                "DONE",
                List.of(clearDoneAction),
                List.of(editDoneAction, removeDoneAction)
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
            listeners.forEach(l -> l.onNewTask(taskText));
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
        void onNewTask(String taskText);

        void onPick(int index);

        void onDone(int index);

        void onClear(Column column);

        void onRemove(Column column, int index);

        void onEdit(Column column, int index, String taskText);

        void onSetNumeration(Numeration numeration);
    }
}
