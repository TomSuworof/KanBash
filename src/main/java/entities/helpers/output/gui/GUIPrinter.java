package entities.helpers.output.gui;

import entities.content.ContentAdapter;
import entities.helpers.output.Printer;

import javax.swing.*;
import java.util.List;

public class GUIPrinter implements Printer {
    private final ContentAdapter contentAdapter;
    private final BoardPanel boardPanel;

    public GUIPrinter(ContentAdapter contentAdapter, BoardPanel boardPanel) {
        this.contentAdapter = contentAdapter;
        this.boardPanel = boardPanel;
    }

    @Override
    public void print() {
        List<String> shouldDoForOutput = contentAdapter.getShouldDoTasks();
        List<String> inProgressForOutput = contentAdapter.getInProgressTasks();
        List<String> doneForOutput = contentAdapter.getDoneTasks();

        boardPanel.setNumerationSet(contentAdapter.isNumerationUsed());
        boardPanel.setShouldDoTasks(shouldDoForOutput);
        boardPanel.setInProgressTasks(inProgressForOutput);
        boardPanel.setDoneTasks(doneForOutput);
    }

    @Override
    public void printWelcomePage() {
        JOptionPane.showMessageDialog(boardPanel, "MAKE YOUR PRODUCTIVITY GREAT AGAIN");
    }
}
