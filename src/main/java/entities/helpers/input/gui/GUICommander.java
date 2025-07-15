package entities.helpers.input.gui;

import entities.client.Client;
import entities.content.Column;
import entities.content.ContentAdapter;
import entities.content.Numeration;
import entities.helpers.output.Printer;
import entities.helpers.output.gui.BoardPanel;

import javax.swing.*;
import java.awt.*;

public class GUICommander implements Client {
    private final ContentAdapter contentAdapter;
    private final BoardPanel boardPanel;
    private final Printer printer;

    public GUICommander(ContentAdapter contentAdapter, BoardPanel boardPanel, Printer printer) {
        this.contentAdapter = contentAdapter;
        this.boardPanel = boardPanel;
        this.printer = printer;
    }

    @Override
    public void start() {
        JFrame mainFrame = new JFrame("KanBash");
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainFrame.getContentPane().add(boardPanel);

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

        init();
        mainFrame.setVisible(true);
//        printer.printWelcomePage();
        printer.print();
    }

    public void init() {
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
}
