import entities.client.Client;
import entities.content.ContentAdapter;
import entities.content.JsonFileContentAdapter;
import entities.helpers.input.console.ConsoleCommander;
import entities.helpers.output.console.ConsolePrinter;

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