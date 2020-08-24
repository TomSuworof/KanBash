package entities.content;

import java.util.ArrayList;

public abstract class ContentAdapter {
    protected static class Content {
        protected ArrayList<String> shouldDo;
        protected ArrayList<String> inProgress;
        protected ArrayList<String> done;
        protected boolean isNumeration = false;
    }

    protected static final String fileName = "contentMy.json";
    protected static String path = ClassLoader.getSystemResource(fileName).toString();
    static {
        if (path.startsWith("jar:")) {
            path = fileName;
        }
        if (path.startsWith("file:")) {
            path = path.substring(6);
        }

    }
    protected static Content content;

    public abstract ArrayList<String> getShouldDo();
    public abstract ArrayList<String> getDone();
    public abstract ArrayList<String> getInProgress();
    public abstract boolean getNumeration();

    public abstract void addShouldDo(String message);
    public abstract void moveShouldDoToInProgress(int index) throws IndexOutOfBoundsException;
    public abstract void moveInProgressToDone(int index) throws IndexOutOfBoundsException;
    public abstract void clearSomething(String message);
    public abstract void removeTask(String message);
    public abstract void setNumeration(boolean isNumeration);
}
