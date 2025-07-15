package entities.helpers.input.gui;

import entities.client.Client;
import entities.content.Column;
import entities.content.ContentAdapter;
import entities.content.Numeration;
import entities.helpers.common.DefaultCommander;
import entities.helpers.output.Printer;
import entities.helpers.output.gui.BoardPanel;

import javax.swing.*;
import java.awt.*;

public class GUICommander extends DefaultCommander implements Client {
    private final BoardPanel boardPanel;
    private final Printer printer;

    public GUICommander(ContentAdapter contentAdapter, BoardPanel boardPanel, Printer printer) {
        super(contentAdapter);
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
            public void onNewTask(String taskText) {
                GUICommander.this.newTask(taskText);
                printer.print();
            }

            @Override
            public void onPick(int index) {
                GUICommander.this.pick(index);
                printer.print();
            }

            @Override
            public void onDone(int index) {
                GUICommander.this.done(index);
                printer.print();
            }

            @Override
            public void onClear(Column column) {
                GUICommander.this.clear(column);
                printer.print();
            }

            @Override
            public void onRemove(Column column, int index) {
                GUICommander.this.remove(column, index);
                printer.print();
            }

            @Override
            public void onEdit(Column column, int index, String taskText) {
                GUICommander.this.edit(column, index, taskText);
                printer.print();
            }

            @Override
            public void onSetNumeration(Numeration numeration) {
                GUICommander.this.setNumeration(numeration);
                printer.print();
            }
        });
    }
}
