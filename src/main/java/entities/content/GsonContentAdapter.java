package entities.content;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 * this adapter allows us to use content, that will be automatically updated
 */

public class GsonContentAdapter implements ContentAdapter {
    private static class Content {
        private ArrayList<String> shouldDo;
        private ArrayList<String> inProgress;
        private ArrayList<String> done;
        private boolean isNumeration = false;
    }

    private static final String fileName = "contentMy.json";
    private static String path = ClassLoader.getSystemResource(fileName).toString();
    static {
        if (path.startsWith("jar:")) {
            path = fileName;
        }
        if (path.startsWith("file:")) {
            path = path.substring(6);
        }

    }
    private static Content content;
    private Content getContent() {
        try (FileReader fileReader = new FileReader(path)) {
            content = new Gson().fromJson(fileReader, Content.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    public boolean getNumeration() {
        return getContent().isNumeration;
    }
    public void setNumeration(boolean isNumeration) {
        content = getContent();
        content.isNumeration = isNumeration;
        update();
    }

    public ArrayList<String> getShouldDo() {
        return getContent().shouldDo;
    }
    public ArrayList<String> getDone() {
        return getContent().done;
    }
    public ArrayList<String> getInProgress() {
        return getContent().inProgress;
    }

    public void addShouldDo(String message) {
        content = getContent();
        content.shouldDo.add(message);
        update();
    }
    public void moveShouldDoToInProgress(int index) throws IndexOutOfBoundsException{
        content = getContent();
        content.inProgress.add(0, content.shouldDo.get(index));
        content.shouldDo.remove(index);
        update();
    }
    public void moveInProgressToDone(int index) throws IndexOutOfBoundsException {
        content = getContent();
        content.done.add(0, content.inProgress.get(index));
        content.inProgress.remove(index);
        update();
    }
    public void clearSomething(String message) {
        content = getContent();
        switch (message.toUpperCase()) {
            case "SHOULD DO":
                content.shouldDo.clear();
                break;
            case "IN PROGRESS":
                content.inProgress.clear();
                break;
            case "DONE":
                content.done.clear();
                break;
            case "ALL":
                content.shouldDo.clear();
                content.inProgress.clear();
                content.done.clear();
            default:
                break;
        }
        update();
    }
    public void removeTask(String message) throws NumberFormatException {
        content = getContent();
        int index = Integer.parseInt(message.split(" ")[message.split(" ").length - 1]);
        String nameOfColumn = message.substring(0, message.length() - String.valueOf(index).length() - 1);
        try {
            switch (nameOfColumn.toUpperCase()) {
                case "SHOULD DO":
                    content.shouldDo.remove(index);
                    break;
                case "IN PROGRESS":
                    content.inProgress.remove(index);
                    break;
                case "DONE":
                    content.done.remove(index);
                    break;
                default:
                    break;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("ERROR. You do not have so mush tasks");
        }
        update();
    }

    private void update() {
        String jsonString = new Gson().toJson(content);
        try (FileWriter fileWriter = new FileWriter(path, false)) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
