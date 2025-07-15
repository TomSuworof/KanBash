package entities.helpers.input;

import entities.content.Column;
import entities.content.Numeration;

public interface Commander {
    void newTask(String taskText);

    void pick(int index);

    void done(int index);

    void clear(Column column);

    void remove(Column column, int index);

    void edit(Column column, int index, String taskText);

    void setNumeration(Numeration numeration);
}
