package com.salat.kanbash;

import com.salat.kanbash.content.ContentAdapter;
import com.salat.kanbash.content.JsonFileContentAdapter;
import com.salat.kanbash.input.Client;
import com.salat.kanbash.input.console.ConsoleCommander;
import com.salat.kanbash.output.console.ConsolePrinter;
import com.salat.kanbash.workspace.JsonFileWorkspaceAdapter;
import com.salat.kanbash.workspace.WorkspaceAdapter;

public class MainConsole {
    public static void main(String[] args) {
        ContentAdapter contentAdapter = new JsonFileContentAdapter();
        contentAdapter.load();

        WorkspaceAdapter workspaceAdapter = new JsonFileWorkspaceAdapter();
        workspaceAdapter.load();

        Client client = new ConsoleCommander(
                contentAdapter,
                workspaceAdapter,
                new ConsolePrinter(contentAdapter, workspaceAdapter)
        );
        client.start();
    }
}