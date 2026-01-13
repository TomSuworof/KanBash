package entities.helpers.output.gui;

import entities.content.Column;
import entities.content.ContentAdapter;
import entities.helpers.output.Printer;

import javax.swing.*;
import java.util.List;

public record GUIPrinter(
        ContentAdapter contentAdapter,
        MenuBar menuBar,
        BoardPanel boardPanel
) implements Printer {

    @Override
    public void print() {
        Theme theme = contentAdapter.getTheme().orElse(Theme.SYSTEM);
        List<String> shouldDoForOutput = contentAdapter.getTasks(Column.SHOULD_DO);
        List<String> inProgressForOutput = contentAdapter.getTasks(Column.IN_PROGRESS);
        List<String> doneForOutput = contentAdapter.getTasks(Column.DONE);

        menuBar.setTheme(theme);
        boardPanel.setNumerationSet(contentAdapter.isNumerationUsed());
        boardPanel.setShouldDoTasks(shouldDoForOutput);
        boardPanel.setInProgressTasks(inProgressForOutput);
        boardPanel.setDoneTasks(doneForOutput);

        SwingUtilities.invokeLater(
                () -> SwingUtilities.updateComponentTreeUI(SwingUtilities.getRoot(boardPanel))
        );
    }

    @Override
    public void printWelcomePage() {
        JOptionPane.showMessageDialog(boardPanel, "MAKE YOUR PRODUCTIVITY GREAT AGAIN");
    }
}
