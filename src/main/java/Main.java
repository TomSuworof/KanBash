import entities.client.Client;
import entities.content.ContentAdapter;
import entities.content.JsonFileContentAdapter;
import entities.helpers.input.ConsoleCommander;
import entities.helpers.output.ConsolePrinter;

public class Main {
    public static void main(String[] args) {
        ContentAdapter contentAdapter = new JsonFileContentAdapter();

        Client client = new Client(
                new ConsoleCommander(contentAdapter),
                new ConsolePrinter(contentAdapter)
        );
        client.start();
    }
}