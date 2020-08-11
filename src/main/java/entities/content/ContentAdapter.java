package entities.content;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 * this adapter allows us to use content, that will be automatically updated
 */

public class ContentAdapter {
    private static class Content {
        private ArrayList<String> shouldDo;
        private ArrayList<String> inProgress;
        private ArrayList<String> done;
    }

    private static final String fileName = "src\\main\\resources\\contentMy.json";
    private static Content content;

    private static Content getContent() {
        try (FileReader fileReader = new FileReader(fileName)) {
            content = new Gson().fromJson(fileReader, Content.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    public static ArrayList<String> getShouldDo() {
        return getContent().shouldDo;
    }
    public static ArrayList<String> getDone() {
        return getContent().done;
    }
    public static ArrayList<String> getInProgress() {
        return getContent().inProgress;
    }

    public static void addShouldDo(String message) {
        content = getContent();
        content.shouldDo.add("- " + message);
        update();
    }
    public static void moveShouldDoToInProgress(int index) {
        content = getContent();
        content.inProgress.add(0, content.shouldDo.get(index));
        content.shouldDo.remove(index);
        update();
    }
    public static void moveInProgressToDone(int index) {
        content = getContent();
        content.done.add(0, content.inProgress.get(index));
        content.inProgress.remove(index);
        update();
    }
    public static void clearSomething(String message) {
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
            case "all":
                content.shouldDo.clear();
                content.inProgress.clear();
                content.done.clear();
            default:
                break;
        }
        update();
    }
    public static void removeTask(String message) throws NumberFormatException {
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

    private static void update() {
        String jsonString = new Gson().toJson(content);
        try (FileWriter fileWriter = new FileWriter(fileName, false)) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
