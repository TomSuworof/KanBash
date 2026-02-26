package com.salat.kanbash;

import com.salat.kanbash.input.Client;
import com.salat.kanbash.content.ContentAdapter;
import com.salat.kanbash.content.JsonFileContentAdapter;
import com.salat.kanbash.input.gui.GUICommander;
import com.salat.kanbash.output.gui.BoardPanel;
import com.salat.kanbash.output.gui.GUIPrinter;
import com.salat.kanbash.output.gui.MenuBar;
import com.salat.kanbash.output.gui.WindowStateManager;
import com.salat.kanbash.workspace.JsonFileWorkspaceAdapter;
import com.salat.kanbash.workspace.WorkspaceAdapter;

import javax.swing.*;
import javax.swing.undo.UndoManager;

public class MainGUI {
    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "KanBash");

        UndoManager undoManager = new UndoManager();

        ContentAdapter contentAdapter = new JsonFileContentAdapter();

        WorkspaceAdapter workspaceAdapter = new JsonFileWorkspaceAdapter();

        MenuBar menuBar = new MenuBar();
        BoardPanel boardPanel = new BoardPanel();

        WindowStateManager windowStateManager = new WindowStateManager(workspaceAdapter);

        Client client = new GUICommander(
                contentAdapter,
                workspaceAdapter,
                undoManager,
                menuBar,
                boardPanel,
                windowStateManager,
                new GUIPrinter(
                        contentAdapter,
                        workspaceAdapter,
                        menuBar,
                        boardPanel
                )
        );

        SwingUtilities.invokeLater(client::start);
    }
}
