package entities.client;
import entities.helpers.input.Writer;
import entities.helpers.output.Printer;
import entities.helpers.input.ConsoleWriter;
import entities.helpers.output.ConsolePrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    private static final Writer writer = new ConsoleWriter();
    private static final Printer printer = new ConsolePrinter();

    public void work() {
        printer.printWelcomePage();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            do {
                printer.print();
                input = bufferedReader.readLine().trim();
                String command = input.split(" ")[0];
                String message = input.substring(command.length()).trim();
                switch (command) {
                    case "new":
                        writer.newTask(message);
                        break;
                    case "pick":
                        writer.pick(message);
                        break;
                    case "done":
                        writer.done(message);
                        break;
                    case "clear":
                        writer.clear(message);
                        break;
                    case "remove":
                        writer.remove(message);
                        break;
                    case "numeration":
                        writer.setNumeration(message);
                    case "exit":
                        break;
                    default:
                        System.out.println(
                                "I do not know this command\n" +
                                 "Please use these commands:\n" +
                                 "- new 'message'\n" +
                                 "- pick 'index'\n" +
                                 "- done 'index'\n" +
                                 "- clear 'name_of_column' or 'all'\n" +
                                 "- remove 'name_of_column' 'index'\n" +
                                 "- numeration 'number' or 'hyphen'\n" +
                                 "- exit"
                        );
                }
            } while (!input.equals("exit"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}