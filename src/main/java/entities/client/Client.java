package entities.client;
import entities.helpers.input.Parser;
import entities.helpers.output.Printer;
import entities.helpers.input.ConsoleCommander;
import entities.helpers.output.ConsolePrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    private static final Parser commander = new ConsoleCommander();
    private static final Printer printer = new ConsolePrinter();

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