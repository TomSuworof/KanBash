package entities.client;

import entities.helpers.output.Printer;
import entities.helpers.input.Writer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    public void work() {
        System.out.println(Printer.WelcomePage);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            do {
                Printer.print();
                input = bufferedReader.readLine().trim();
                String command = input.split(" ")[0];
                String message = input.substring(command.length()).trim();
                switch (command) {
                    case "new":
                        Writer.newTask(message);
                        break;
                    case "pick":
                        Writer.pick(Integer.parseInt(message));
                        break;
                    case "done":
                        Writer.done(Integer.parseInt(message));
                        break;
                    case "clear":
                        Writer.clear(message);
                        break;
                    case "exit":
                        break;
                    default:
                        System.out.println(
                                "I do not know this command\n" +
                                 "Please use these commands:\n" +
                                 "- new + 'message'\n" +
                                 "- pick + 'index'\n" +
                                 "- done + 'index'\n" +
                                 "- clear + 'name_of_column' or 'all'\n" +
                                 "- exit - for exit"
                        );
                }
            } while (!input.equals("exit"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}