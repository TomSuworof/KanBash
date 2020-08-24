import entities.client.Client;
import entities.content.GsonContentAdapter;
import entities.helpers.input.ConsoleCommander;
import entities.helpers.output.ConsolePrinter;

public class KanBash {
    public static void main(String[] args) {
        Client client = new Client(
                new ConsoleCommander(new GsonContentAdapter()),
                new ConsolePrinter(new GsonContentAdapter()));
        client.work();
    }
}