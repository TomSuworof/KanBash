package com.salat.kanbash;

import com.salat.kanbash.input.Client;
import com.salat.kanbash.content.ContentAdapter;
import com.salat.kanbash.content.JsonFileContentAdapter;
import com.salat.kanbash.input.console.ConsoleCommander;
import com.salat.kanbash.output.console.ConsolePrinter;

public class MainConsole {
    public static void main(String[] args) {
        ContentAdapter contentAdapter = new JsonFileContentAdapter();

        Client client = new ConsoleCommander(
                contentAdapter,
                new ConsolePrinter(contentAdapter)
        );
        client.start();
    }
}