package com.salat.kanbash.output.gui;

import com.salat.kanbash.content.Column;
import com.salat.kanbash.output.common.Numeration;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardPanel extends JPanel {
    private final ColumnPanel shouldDoPanel;
    private final ColumnPanel inProgressPanel;
    private final ColumnPanel donePanel;

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
            if (taskText == null) {
                return;
            }
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
        setLayout(new GridLayout(1, 3, 0, 0));

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

        add(shouldDoPanel);
        add(inProgressPanel);
        add(donePanel);
    }

    public void setShouldDoTasks(List<String> tasks) {
        shouldDoPanel.setItems(tasks);

        JPanel specialAddPanel = new JPanel(new BorderLayout());
        JButton button = new JButton("New task");
        button.setBorderPainted(false);
        button.setBackground(Optional.ofNullable(UIManager.getColor("Button.background")).orElse(new Color(0xeeeeee)));
        specialAddPanel.setBorder(BorderFactory.createDashedBorder(
                Color.GRAY,
                1,
                5,
                2,
                true
        ));
        button.addActionListener(e -> {
            String taskText = JOptionPane.showInputDialog(this, "Write task text");
            listeners.forEach(l -> l.onNewTask(Column.SHOULD_DO, taskText));
        });

        specialAddPanel.add(button, BorderLayout.CENTER);
        shouldDoPanel.setSpecialItemAddButton(specialAddPanel);
    }

    public void setInProgressTasks(List<String> tasks) {
        inProgressPanel.setItems(tasks);
    }

    public void setDoneTasks(List<String> tasks) {
        donePanel.setItems(tasks);
    }

    public void setNumeration(Numeration numeration) {
        shouldDoPanel.setNumeration(numeration);
        inProgressPanel.setNumeration(numeration);
        donePanel.setNumeration(numeration);
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
    }
}
