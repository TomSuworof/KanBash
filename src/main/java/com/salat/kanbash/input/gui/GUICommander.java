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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public record GUICommander(
        ContentAdapter contentAdapter,
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
            }

            @Override
            public void onMove(Column oldColumn, int oldIndex, Column newColumn) {
                contentAdapter.moveTask(oldColumn, oldIndex, newColumn, contentAdapter.getTasks(newColumn).size());
                printer.print();
            }

            @Override
            public void onClear(Column column) {
                contentAdapter.clear(column);
                printer.print();
            }

            @Override
            public void onRemove(Column column, int index) {
                contentAdapter.removeTask(column, index);
                printer.print();
            }

            @Override
            public void onEdit(Column column, int index, String taskText) {
                contentAdapter.editTask(column, index, taskText);
                printer.print();
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
