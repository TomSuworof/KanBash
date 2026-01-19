package com.salat.kanbash.input.gui;

import com.salat.kanbash.content.Column;
import com.salat.kanbash.content.ContentAdapter;
import com.salat.kanbash.input.Client;
import com.salat.kanbash.output.Printer;
import com.salat.kanbash.output.common.Numeration;
import com.salat.kanbash.output.gui.BoardPanel;
import com.salat.kanbash.output.gui.MenuBar;
import com.salat.kanbash.output.gui.Theme;

import javax.swing.*;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public record GUICommander(
        ContentAdapter contentAdapter,
        UndoManager undoManager,
        MenuBar menuBar,
        BoardPanel boardPanel,
        Printer printer
) implements Client {

    @Override
    public void start() {
        JFrame mainFrame = new JFrame("KanBash");
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                rememberLocation(mainFrame);
            }

            @Override
            public void componentResized(ComponentEvent e) {
                rememberLocation(mainFrame);
            }
        });

        mainFrame.setJMenuBar(menuBar);
        initMenu();

        mainFrame.getContentPane().add(boardPanel);
        initBoardPanel();

        resetLocation(mainFrame);

        printer.print();
        mainFrame.setVisible(true);
//        printer.printWelcomePage();
    }

    private void initMenu() {
        menuBar.addMenuBarListener(new MenuBar.MenuBarListener() {
            @Override
            public void onExit() {
                System.exit(0);
            }

            @Override
            public void onUndo() {
                if (undoManager.canUndo()) {
                    undoManager.undo();
                }
            }

            @Override
            public void onNumerationChanged(Numeration numeration) {
                contentAdapter.setNumeration(numeration);
                printer.print();
            }

            @Override
            public void onThemeChanged(Theme newTheme) {
                contentAdapter.setTheme(newTheme);
                printer.print();
            }
        });
    }

    private void initBoardPanel() {
        boardPanel.addBoardListener(new BoardPanel.BoardListener() {
            @Override
            public void onNewTask(Column column, String taskText) {
                contentAdapter.addTask(column, taskText, contentAdapter.getTasks(column).size());
                printer.print();

                undoManager.addEdit(new AbstractUndoableEdit() {
                    @Override
                    public void undo() throws CannotUndoException {
                        contentAdapter.removeTask(column, contentAdapter.getTasks(column).size());
                        printer.print();
                    }
                });
            }

            @Override
            public void onMove(Column oldColumn, int oldIndex, Column newColumn) {
                contentAdapter.moveTask(oldColumn, oldIndex, newColumn, contentAdapter.getTasks(newColumn).size());
                printer.print();

                undoManager.addEdit(new AbstractUndoableEdit() {
                    @Override
                    public void undo() throws CannotUndoException {
                        contentAdapter.moveTask(newColumn, contentAdapter.getTasks(newColumn).size() - 1, oldColumn, oldIndex);
                        printer.print();
                    }
                });
            }

            @Override
            public void onClear(Column column) {
                var oldTasks = new ArrayList<>(contentAdapter.getTasks(column));

                contentAdapter.clear(column);
                printer.print();

                undoManager.addEdit(new AbstractUndoableEdit() {
                    @Override
                    public void undo() throws CannotUndoException {
                        for (var oldTask : oldTasks) {
                            contentAdapter.addTask(column, oldTask, contentAdapter.getTasks(column).size());
                        }
                        printer.print();
                    }
                });
            }

            @Override
            public void onRemove(Column column, int index) {
                var oldTask = contentAdapter.getTask(column, index).orElseThrow();

                contentAdapter.removeTask(column, index);
                printer.print();

                undoManager.addEdit(new AbstractUndoableEdit() {
                    @Override
                    public void undo() throws CannotUndoException {
                        contentAdapter.addTask(column, oldTask, index);
                        printer.print();
                    }
                });
            }

            @Override
            public void onEdit(Column column, int index, String taskText) {
                var oldTask = contentAdapter.getTask(column, index).orElseThrow();

                contentAdapter.editTask(column, index, taskText);
                printer.print();

                undoManager.addEdit(new AbstractUndoableEdit() {
                    @Override
                    public void undo() throws CannotUndoException {
                        contentAdapter.editTask(column, index, oldTask);
                        printer.print();
                    }
                });
            }
        });
    }

    private void resetLocation(JFrame mainFrame) {
        var lastWindowLocation = contentAdapter.getLastWindowLocation();
        if (lastWindowLocation == null) {
            return;
        }
        mainFrame.setBounds(lastWindowLocation);
    }

    private void rememberLocation(JFrame mainFrame) {
        if (!mainFrame.isVisible()) {
            return;
        }
        contentAdapter.setLastWindowLocation(mainFrame.getBounds());
    }
}
