package com.salat.kanbash.input.console;

import com.salat.kanbash.input.Client;
import com.salat.kanbash.content.Column;
import com.salat.kanbash.content.ContentAdapter;
import com.salat.kanbash.content.Numeration;
import com.salat.kanbash.output.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleCommander implements Client {
    private final Printer printer;
    private final ContentAdapter contentAdapter;
    private boolean toExit = false;

    public ConsoleCommander(ContentAdapter contentAdapter, Printer printer) {
        this.contentAdapter = contentAdapter;
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
                contentAdapter.addTask(Column.SHOULD_DO, message);
                break;
            case "pick":
                try {
                    contentAdapter.moveTask(Column.SHOULD_DO, Integer.parseInt(message), Column.IN_PROGRESS);
                } catch (NumberFormatException e) {
                    System.out.println("ERROR. It was not a number");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("ERROR. You do not have so much tasks in SHOULD DO");
                }
                break;
            case "done":
                try {
                    contentAdapter.moveTask(Column.IN_PROGRESS, Integer.parseInt(message), Column.DONE);
                } catch (NumberFormatException e) {
                    System.out.println("ERROR. It was not a number");
                } catch (IndexOutOfBoundsException indexEx) {
                    System.out.println("ERROR. You do not have so much tasks in IN PROGRESS");
                }
                break;
            case "clear":
                contentAdapter.clear(columnFromName(message));
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
                    contentAdapter.removeTask(column, index);
                } catch (NumberFormatException e) {
                    System.out.println("ERROR. It was not a number");
                }
                break;
            case "edit":
                try {
                    String[] messageParts = message.split(" ");
                    Column column;
                    int index;
                    int indexOfTaskText;
                    {
                        // Identifying column
                        try {
                            String columnName = messageParts[0];
                            column = columnFromName(columnName);
                            index = Integer.parseInt(messageParts[1]);
                            indexOfTaskText = 2;
                        } catch (IllegalArgumentException e) {
                            // column is not in first word, trying first+second
                            String columName = messageParts[0] + messageParts[1];
                            column = columnFromName(columName);
                            index = Integer.parseInt(messageParts[2]);
                            indexOfTaskText = 3;
                        }
                    }
                    if (column == Column.ALL) {
                        throw new IllegalArgumentException("Cannot edit in all columns");
                    }
                    StringBuilder taskText = new StringBuilder();
                    for (int i = indexOfTaskText; i < messageParts.length; i++) {
                        taskText.append(messageParts[i]);
                    }
                    contentAdapter.editTask(column, index, taskText.toString());
                } catch (NumberFormatException e) {
                    System.out.println("ERROR. It was not a number");
                }
                break;
            case "numeration":
                switch (numerationFromName(message)) {
                    case NUMBER:
                        contentAdapter.setNumerationUsage(true);
                        break;
                    case HYPHEN:
                        contentAdapter.setNumerationUsage(false);
                        break;
                    default:
                        break;
                }
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
                                "- edit 'name_of_column' 'index' 'message'\n" +
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
                                "- edit 'name_of_column' 'index' 'message'\n" +
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