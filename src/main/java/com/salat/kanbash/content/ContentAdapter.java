package com.salat.kanbash.content;

import com.salat.kanbash.output.common.Numeration;
import com.salat.kanbash.output.gui.Theme;

import java.util.List;
import java.util.Optional;

public interface ContentAdapter {
    Numeration getNumeration();

    void setNumeration(Numeration numeration);

    List<String> getTasks(Column column);

    Optional<String> getTask(Column column, int index);

    Theme getTheme();

    void addTask(Column column, String taskText);

    void moveTask(Column oldColumn, int index, Column newColumn) throws IndexOutOfBoundsException;

    void clear(Column column);

    void removeTask(Column column, int index);

    void editTask(Column column, int index, String message);

    void setTheme(Theme theme);
}
