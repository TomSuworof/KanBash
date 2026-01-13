package com.salat.kanbash.input.gui;

import com.salat.kanbash.input.Client;
import com.salat.kanbash.content.Column;
import com.salat.kanbash.content.ContentAdapter;
import com.salat.kanbash.content.Numeration;
import com.salat.kanbash.output.Printer;
import com.salat.kanbash.output.gui.BoardPanel;

import com.salat.kanbash.output.gui.MenuBar;

import javax.swing.*;
import java.awt.*;

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

        mainFrame.setJMenuBar(menuBar);
        initMenu();

        mainFrame.getContentPane().add(boardPanel);
        initBoardPanel();

        resolveMonitors(mainFrame);

        mainFrame.setVisible(true);
//        printer.printWelcomePage();
        printer.print();
    }

    private void initMenu() {
        menuBar.addMenuBarListener(newTheme -> {
            contentAdapter.setTheme(newTheme);
            printer.print();
        });
    }

    private void initBoardPanel() {
        boardPanel.addBoardListener(new BoardPanel.BoardListener() {
            @Override
            public void onNewTask(Column column, String taskText) {
                contentAdapter.addTask(column, taskText);
                printer.print();
            }

            @Override
            public void onMove(Column oldColumn, int index, Column newColumn) {
                contentAdapter.moveTask(oldColumn, index, newColumn);
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

            @Override
            public void onSetNumeration(Numeration numeration) {
                contentAdapter.setNumerationUsage(numeration == Numeration.NUMBER);
                printer.print();
            }
        });
    }

    private void resolveMonitors(JFrame mainFrame) {
        boolean preferSecondaryMonitor = true;
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        var monitors = env.getScreenDevices();

        if (preferSecondaryMonitor && monitors.length > 1) {
            var secondaryMonitor = monitors[1];
            var secondaryMonitorBounds = secondaryMonitor.getDefaultConfiguration().getBounds();
            mainFrame.setLocation(
                    secondaryMonitorBounds.x + secondaryMonitorBounds.width / 4,
                    secondaryMonitorBounds.y + secondaryMonitorBounds.height / 4
            );
        }
    }
}
