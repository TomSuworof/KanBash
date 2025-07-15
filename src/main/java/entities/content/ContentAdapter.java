package entities.content;

import java.util.List;

public interface ContentAdapter {
    boolean isNumerationUsed();

    void setNumerationUsage(boolean isNumeration);

    List<String> getShouldDoTasks();

    List<String> getDoneTasks();

    List<String> getInProgressTasks();

    void addShouldDoTask(String message);

    void moveShouldDoToInProgress(int index) throws IndexOutOfBoundsException;

    void moveInProgressToDone(int index) throws IndexOutOfBoundsException;

    void clearSomething(Column column);

    void removeTask(Column column, int index);

    void editTask(Column column, int index, String message);
}
