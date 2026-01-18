package com.salat.kanbash;

import com.salat.kanbash.input.Client;
import com.salat.kanbash.content.ContentAdapter;
import com.salat.kanbash.content.JsonFileContentAdapter;
import com.salat.kanbash.input.gui.GUICommander;
import com.salat.kanbash.output.gui.BoardPanel;
import com.salat.kanbash.output.gui.GUIPrinter;
import com.salat.kanbash.output.gui.MenuBar;

import javax.swing.*;
import javax.swing.undo.UndoManager;

public class MainGUI {
    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "KanBash");

        UndoManager undoManager = new UndoManager();

        ContentAdapter contentAdapter = new JsonFileContentAdapter();

        MenuBar menuBar = new MenuBar();
        BoardPanel boardPanel = new BoardPanel();

        Client client = new GUICommander(
                contentAdapter,
                undoManager,
                menuBar,
                boardPanel,
                new GUIPrinter(
                        contentAdapter,
                        menuBar,
                        boardPanel
                )
        );

        SwingUtilities.invokeLater(client::start);
    }
}
