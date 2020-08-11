package entities.helpers.input;

import entities.content.ContentAdapter;

public class Writer {
    public static void newTask(String message) {
        // adding new task in column 'do'
        ContentAdapter.addShouldDo(message);
    }

    public static void pick(String message) {
        // move i'th task to 'inProgress'
        try {
            ContentAdapter.moveShouldDoToInProgress(Integer.parseInt(message));
        } catch (IndexOutOfBoundsException indexEx) {
            System.out.println("ERROR. You do not have so much tasks in SHOULD DO");
        } catch (NumberFormatException numberEx) {
            System.out.println("ERROR. It was not a number");
        }
    }

    public static void done(String message) {
        // move i'th task tp 'done'
        try {
            ContentAdapter.moveInProgressToDone(Integer.parseInt(message));
        } catch (IndexOutOfBoundsException indexEx) {
            System.out.println("ERROR. You do not have so much tasks in IN PROGRESS");
        }catch (NumberFormatException numberEx) {
            System.out.println("ERROR. It was not a number");
        }
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