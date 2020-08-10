package entities.content;

import java.util.ArrayList;

public final class Content {
    private ArrayList<String> shouldDo;
    private ArrayList<String> inProgress;
    private ArrayList<String> done;

    public ArrayList<String> getShouldDo() {
        return shouldDo;
    }
    public ArrayList<String> getDone() {
        return done;
    }
    public ArrayList<String> getInProgress() {
        return inProgress;
    }

    public void addShouldDo(String message) {
        shouldDo.add("- " + message);
    }
    public void moveShouldDoToInProgress(int index) {
        inProgress.add(0, shouldDo.get(index));
        shouldDo.remove(index);
    }
    public void moveInProgressToDone(int index) {
        done.add(0, inProgress.get(index));
        inProgress.remove(index);
    }

    public void removeSomething(String nameOfColumn) {
        switch (nameOfColumn) {
            case "SHOULD DO":
                shouldDo.clear();
                break;
            case "IN PROGRESS":
                inProgress.clear();
                break;
            case "DONE":
                done.clear();
                break;
            case "all":
                shouldDo.clear();
                inProgress.clear();
                done.clear();
            default:
                break;
        }
    }
}
