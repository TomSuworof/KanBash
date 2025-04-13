package entities.helpers.input;

import entities.content.ContentAdapter;

public class ConsoleCommander extends Commander implements Parser {

    public ConsoleCommander(ContentAdapter contentAdapter) {
        super(contentAdapter);
    }

    public void parse(String input) {
        String command = input.split(" ")[0];
        String message = input.substring(command.length()).trim();
        switch (command) {
            case "new":
                newTask(message);
                break;
            case "pick":
                pick(message);
                break;
            case "done":
                done(message);
                break;
            case "clear":
                clear(message);
                break;
            case "remove":
                remove(message);
                break;
            case "numeration":
                setNumeration(message);
            case "exit":
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

    public void newTask(String message) {
        // adding new task in column 'do'
        contentAdapter.addShouldDoTask(message);
    }

    public void pick(String index) {
        // move i'th task to 'inProgress'
        try {
            contentAdapter.moveShouldDoToInProgress(Integer.parseInt(index));
        } catch (IndexOutOfBoundsException indexEx) {
            System.out.println("ERROR. You do not have so much tasks in SHOULD DO");
        } catch (NumberFormatException numberEx) {
            System.out.println("ERROR. It was not a number");
        }
    }

    public void done(String index) {
        // move i'th task tp 'done'
        try {
            contentAdapter.moveInProgressToDone(Integer.parseInt(index));
        } catch (IndexOutOfBoundsException indexEx) {
            System.out.println("ERROR. You do not have so much tasks in IN PROGRESS");
        } catch (NumberFormatException numberEx) {
            System.out.println("ERROR. It was not a number");
        }
    }

    public void clear(String message) {
        // clear certain column or all of them
        contentAdapter.clearSomething(message);
    }

    public void remove(String message) {
        // allow to remove certain element from columns
        try {
            contentAdapter.removeTask(message);
        } catch (NumberFormatException e) {
            System.out.println("ERROR. It was not a number");
        }
    }

    public void setNumeration(String message) {
        switch (message) {
            case "number":
                contentAdapter.setNumerationUsage(true);
                break;
            case "hyphen":
                contentAdapter.setNumerationUsage(false);
                break;
            default:
                break;
        }
    }
}