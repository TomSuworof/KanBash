package entities.helpers.common;

import entities.content.Column;
import entities.content.ContentAdapter;
import entities.content.Numeration;
import entities.helpers.input.Commander;

public class DefaultCommander implements Commander {
    private final ContentAdapter contentAdapter;

    public DefaultCommander(ContentAdapter contentAdapter) {
        this.contentAdapter = contentAdapter;
    }

    @Override
    public void newTask(String taskText) {
        // adding new task in column 'do'
        contentAdapter.addShouldDoTask(taskText);
    }

    @Override
    public void pick(int index) {
        // move i'th task to 'inProgress'
        try {
            contentAdapter.moveShouldDoToInProgress(index);
        } catch (IndexOutOfBoundsException indexEx) {
            System.out.println("ERROR. You do not have so much tasks in SHOULD DO");
        }
    }

    @Override
    public void done(int index) {
        // move i'th task tp 'done'
        try {
            contentAdapter.moveInProgressToDone(index);
        } catch (IndexOutOfBoundsException indexEx) {
            System.out.println("ERROR. You do not have so much tasks in IN PROGRESS");
        }
    }

    @Override
    public void clear(Column column) {
        // clear certain column or all of them
        contentAdapter.clearSomething(column);
    }

    @Override
    public void remove(Column column, int index) {
        // allow to remove certain element from columns
        contentAdapter.removeTask(column, index);
    }

    @Override
    public void setNumeration(Numeration numeration) {
        switch (numeration) {
            case NUMBER:
                contentAdapter.setNumerationUsage(true);
                break;
            case HYPHEN:
                contentAdapter.setNumerationUsage(false);
                break;
            default:
                break;
        }
    }
}
