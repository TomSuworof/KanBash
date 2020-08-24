package entities.client;
import entities.helpers.input.Parser;
import entities.helpers.output.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    private static Parser commander;
    private static Printer printer;

    public Client(Parser commander, Printer printer) {
        Client.commander = commander;
        Client.printer = printer;
    }

    public void work() {
        printer.printWelcomePage();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            do {
                printer.print();
                input = bufferedReader.readLine().trim();
                commander.parse(input);
            } while (!input.equals("exit"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}