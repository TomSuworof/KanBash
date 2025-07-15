package entities.content;

import java.util.List;
import java.util.Optional;

public interface ContentAdapter {
    boolean isNumerationUsed();

    void setNumerationUsage(boolean isNumeration);

    List<String> getTasks(Column column);

    Optional<String> getTask(Column column, int index);

    void addTask(Column column, String taskText);

    void moveTask(Column oldColumn, int index, Column newColumn) throws IndexOutOfBoundsException;

    void clear(Column column);

    void removeTask(Column column, int index);

    void editTask(Column column, int index, String message);
}
