package com.salat.kanbash.output.gui;

import com.salat.kanbash.content.Column;
import com.salat.kanbash.content.ContentAdapter;
import com.salat.kanbash.output.Printer;
import com.salat.kanbash.output.common.Numeration;
import com.salat.kanbash.workspace.WorkspaceAdapter;

import javax.swing.*;
import java.util.List;

public record GUIPrinter(
        ContentAdapter contentAdapter,
        WorkspaceAdapter workspaceAdapter,
        MenuBar menuBar,
        BoardPanel boardPanel
) implements Printer {

    @Override
    public void print() {
        Numeration numeration = workspaceAdapter.getNumeration();
        Theme theme = workspaceAdapter.getTheme();

        List<String> shouldDoForOutput = contentAdapter.getTasks(Column.SHOULD_DO);
        List<String> inProgressForOutput = contentAdapter.getTasks(Column.IN_PROGRESS);
        List<String> doneForOutput = contentAdapter.getTasks(Column.DONE);

        menuBar.setNumeration(numeration);
        menuBar.setTheme(theme);

        boardPanel.setNumeration(numeration);
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
