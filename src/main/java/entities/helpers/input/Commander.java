package entities.helpers.input;

public interface Commander {

    void newTask(String message);

    void pick(String index);

    void done(String index);

    void clear(String message);

    void remove(String message);

    void setNumeration(String message);
}
