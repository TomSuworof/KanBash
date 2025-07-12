package entities.helpers.input.console;

import entities.client.Client;
import entities.content.Column;
import entities.content.ContentAdapter;
import entities.content.Numeration;
import entities.helpers.common.DefaultCommander;
import entities.helpers.output.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleCommander extends DefaultCommander implements Client {
    private final Printer printer;
    private boolean toExit = false;

    public ConsoleCommander(ContentAdapter contentAdapter, Printer printer) {
        super(contentAdapter);
        this.printer = printer;
    }

    @Override
    public void start() {
        printer.printWelcomePage();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            do {
                printer.print();
                input = bufferedReader.readLine().trim();
                parse(input);
            } while (!toExit());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parse(String input) {
        String command = input.split(" ")[0];
        String message = input.substring(command.length()).trim();
        switch (command) {
            case "new":
                newTask(message);
                break;
            case "pick":
                try {
                    pick(Integer.parseInt(message));
                } catch (NumberFormatException e) {
                    System.out.println("ERROR. It was not a number");
                }
                break;
            case "done":
                try {
                    done(Integer.parseInt(message));
                } catch (NumberFormatException e) {
                    System.out.println("ERROR. It was not a number");
                }
                break;
            case "clear":
                clear(columnFromName(message));
                break;
            case "remove":
                try {
                    String[] messageParts = message.split(" ");
                    int index = Integer.parseInt(messageParts[messageParts.length - 1]);
                    String columnName = message.substring(0, message.length() - String.valueOf(index).length() - 1);
                    Column column = columnFromName(columnName);
                    if (column == Column.ALL) {
                        throw new IllegalArgumentException("Cannot remove from all columns");
                    }
                    remove(column, index);
                } catch (NumberFormatException e) {
                    System.out.println("ERROR. It was not a number");
                }
                break;
            case "numeration":
                setNumeration(numerationFromName(message));
            case "exit":
                toExit = true;
                break;
            case "commands":
            case "info":
            case "help":
                System.out.println(
                        "Commands:\n" +
                                "- new 'message'\n" +
                                "- pick 'index'\n" +
                                "- done 'index'\n" +
                                "- clear 'name_of_column' or 'all'\n" +
                                "- remove 'name_of_column' 'index'\n" +
                                "- numeration 'number' or 'hyphen'\n" +
                                "- exit"
                );
                break;
            default:
                System.out.println(
                        "Unknown command\n" +
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
    }

    public boolean toExit() {
        return toExit;
    }

    private static Column columnFromName(String columnName) {
        return switch (columnName.toUpperCase()) {
            case "SHOULD DO" -> Column.SHOULD_DO;
            case "IN PROGRESS" -> Column.IN_PROGRESS;
            case "DONE" -> Column.DONE;
            case "ALL" -> Column.ALL;
            default -> throw new IllegalArgumentException("Unknown column");
        };
    }

    private static Numeration numerationFromName(String numerationName) {
        return switch (numerationName.toLowerCase()) {
            case "hyphen" -> Numeration.HYPHEN;
            case "number" -> Numeration.NUMBER;
            default -> throw new IllegalArgumentException("Unknown numeration");
        };
    }
}