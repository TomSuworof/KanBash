package entities.helpers.input;

import entities.content.ContentAdapter;

public abstract class Commander {
    protected static ContentAdapter contentAdapter;

    public Commander(ContentAdapter contentAdapter) {
        Commander.contentAdapter = contentAdapter;
    }

    abstract void newTask(String message);

    abstract void pick(String index);

    abstract void done(String index);

    abstract void clear(String message);

    abstract void remove(String message);

    abstract void setNumeration(String message);
}
