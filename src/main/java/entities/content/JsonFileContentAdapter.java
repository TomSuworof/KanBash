package entities.content;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonFileContentAdapter extends ContentAdapter {
    private static final String contentFileName = "content.json";

    private File contenFile;
    private Content content;

    public JsonFileContentAdapter() {
        try {
            contenFile = new File(contentFileName);
            contenFile.createNewFile(); // internally checks if file already exists

            FileReader fileReader = new FileReader(contenFile);
            content = new Gson().fromJson(fileReader, Content.class);
            fileReader.close();

            if (content == null) {
                content = new Content();
                update();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean isNumerationUsed() {
        return content.isNumerationUsed;
    }
    public void setNumerationUsage(boolean useNumeration) {
        content.isNumerationUsed = useNumeration;
        update();
    }

    public List<String> getShouldDoTasks() {
        return content.shouldDoTasks;
    }
    public List<String> getDoneTasks() {
        return content.doneTasks;
    }
    public List<String> getInProgressTasks() {
        return content.inProgressTasks;
    }

    public void addShouldDoTask(String message) {
        content.shouldDoTasks.add(message);
        update();
    }
    public void moveShouldDoToInProgress(int index) throws IndexOutOfBoundsException{
        content.inProgressTasks.add(0, content.shouldDoTasks.get(index));
        content.shouldDoTasks.remove(index);
        update();
    }
    public void moveInProgressToDone(int index) throws IndexOutOfBoundsException {
        content.doneTasks.add(0, content.inProgressTasks.get(index));
        content.inProgressTasks.remove(index);
        update();
    }
    public void clearSomething(String message) {
        switch (message.toUpperCase()) {
            case "SHOULD DO":
                content.shouldDoTasks.clear();
                break;
            case "IN PROGRESS":
                content.inProgressTasks.clear();
                break;
            case "DONE":
                content.doneTasks.clear();
                break;
            case "ALL":
                content.shouldDoTasks.clear();
                content.inProgressTasks.clear();
                content.doneTasks.clear();
            default:
                break;
        }
        update();
    }
    public void removeTask(String message) throws NumberFormatException {
        String[] messageParts = message.split(" ");
        int index = Integer.parseInt(messageParts[messageParts.length - 1]);
        String columnName = message.substring(0, message.length() - String.valueOf(index).length() - 1);
        try {
            switch (columnName.toUpperCase()) {
                case "SHOULD DO":
                    content.shouldDoTasks.remove(index);
                    break;
                case "IN PROGRESS":
                    content.inProgressTasks.remove(index);
                    break;
                case "DONE":
                    content.doneTasks.remove(index);
                    break;
                default:
                    break;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("ERROR. You do not have task with this number");
        }
        update();
    }

    private void update() {
        String jsonString = new Gson().toJson(content);
        try (FileWriter fileWriter = new FileWriter(contenFile, false)) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
