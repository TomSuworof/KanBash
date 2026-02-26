package com.salat.kanbash.content;

import java.util.List;
import java.util.Optional;

public interface ContentAdapter {
    List<String> getTasks(Column column);

    Optional<String> getTask(Column column, int index);

    void addTask(Column column, String taskText, int index);

    void moveTask(Column oldColumn, int oldIndex, Column newColumn, int newIndex) throws IndexOutOfBoundsException;

    void clear(Column column);

    void removeTask(Column column, int index);

    void editTask(Column column, int index, String message);
}
