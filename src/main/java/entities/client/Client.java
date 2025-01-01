package entities.client;
import entities.helpers.input.Parser;
import entities.helpers.output.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    private final Parser commander;
    private final Printer printer;

    public Client(Parser commander, Printer printer) {
        this.commander = commander;
        this.printer = printer;
    }

    public void start() {
        printer.printWelcomePage();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            do {
                printer.print();
                input = bufferedReader.readLine().trim();
                commander.parse(input);
            } while (!input.equals("exit"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}