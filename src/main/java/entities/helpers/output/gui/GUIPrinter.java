package entities.helpers.output.gui;

import entities.content.Column;
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
        List<String> shouldDoForOutput = contentAdapter.getTasks(Column.SHOULD_DO);
        List<String> inProgressForOutput = contentAdapter.getTasks(Column.IN_PROGRESS);
        List<String> doneForOutput = contentAdapter.getTasks(Column.DONE);

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
