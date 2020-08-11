package entities.helpers.input;

import entities.content.ContentAdapter;

public class Writer {
    public static void newTask(String message) {
        // adding new task in column 'do'
        ContentAdapter.addShouldDo(message);
    }

    public static void pick(int index) {
        // move i'th task to 'inProgress'
        ContentAdapter.moveShouldDoToInProgress(index);
    }

    public static void done(int index) {
        // move i'th task tp 'done'
        ContentAdapter.moveInProgressToDone(index);
    }

    public static void clear(String message) {
        // clear certain column or all of them
        ContentAdapter.clearSomething(message);
    }

    public static void remove(String message) {
        // allow to remove certain element from columns
        try {
            ContentAdapter.removeTask(message);
        } catch (NumberFormatException e) {
            System.out.println("ERROR. It was not a number");
        }
    }
}
