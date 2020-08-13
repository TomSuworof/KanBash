package entities.content;

import java.util.ArrayList;

public interface ContentAdapter {
    ArrayList<String> getShouldDo();
    ArrayList<String> getDone();
    ArrayList<String> getInProgress();

    void addShouldDo(String message);
    void moveShouldDoToInProgress(int index) throws IndexOutOfBoundsException;
    void moveInProgressToDone(int index) throws IndexOutOfBoundsException;
    void clearSomething(String message);
    void removeTask(String message);
}
