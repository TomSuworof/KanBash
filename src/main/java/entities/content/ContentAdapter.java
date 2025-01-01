package entities.content;

import java.util.List;

public abstract class ContentAdapter {
    public abstract Boolean isNumerationUsed();
    public abstract void setNumerationUsage(boolean isNumeration);

    public abstract List<String> getShouldDoTasks();
    public abstract List<String> getDoneTasks();
    public abstract List<String> getInProgressTasks();

    public abstract void addShouldDoTask(String message);
    public abstract void moveShouldDoToInProgress(int index) throws IndexOutOfBoundsException;
    public abstract void moveInProgressToDone(int index) throws IndexOutOfBoundsException;
    public abstract void clearSomething(String message);
    public abstract void removeTask(String message);
    
}
