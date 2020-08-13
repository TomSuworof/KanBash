package entities.helpers.input;

import entities.content.ContentAdapter;
import entities.content.GsonContentAdapter;

public class ConsoleWriter implements  Writer {

    private static final ContentAdapter contentAdapter = new GsonContentAdapter();

    public void newTask(String message) {
        // adding new task in column 'do'
        contentAdapter.addShouldDo(message);
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
        }catch (NumberFormatException numberEx) {
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
                contentAdapter.setNumeration(true);
                break;
            case "hyphen":
                contentAdapter.setNumeration(false);
                break;
            default:
                break;
        }
    }
}