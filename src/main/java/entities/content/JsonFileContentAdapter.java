package entities.content;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonFileContentAdapter implements ContentAdapter {
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

    @Override
    public boolean isNumerationUsed() {
        return content.isNumerationUsed;
    }

    @Override
    public void setNumerationUsage(boolean useNumeration) {
        content.isNumerationUsed = useNumeration;
        update();
    }

    @Override
    public List<String> getShouldDoTasks() {
        return content.shouldDoTasks;
    }

    @Override
    public List<String> getDoneTasks() {
        return content.doneTasks;
    }

    @Override
    public List<String> getInProgressTasks() {
        return content.inProgressTasks;
    }

    @Override
    public void addShouldDoTask(String message) {
        content.shouldDoTasks.add(message);
        update();
    }

    @Override
    public void moveShouldDoToInProgress(int index) throws IndexOutOfBoundsException {
        content.inProgressTasks.add(0, content.shouldDoTasks.get(index));
        content.shouldDoTasks.remove(index);
        update();
    }

    @Override
    public void moveInProgressToDone(int index) throws IndexOutOfBoundsException {
        content.doneTasks.add(0, content.inProgressTasks.get(index));
        content.inProgressTasks.remove(index);
        update();
    }

    @Override
    public void clearSomething(Column column) {
        switch (column) {
            case SHOULD_DO:
                content.shouldDoTasks.clear();
                break;
            case IN_PROGRESS:
                content.inProgressTasks.clear();
                break;
            case DONE:
                content.doneTasks.clear();
                break;
            case ALL:
                content.shouldDoTasks.clear();
                content.inProgressTasks.clear();
                content.doneTasks.clear();
            default:
                break;
        }
        update();
    }

    @Override
    public void removeTask(Column column, int index) throws NumberFormatException {
        try {
            switch (column) {
                case SHOULD_DO:
                    content.shouldDoTasks.remove(index);
                    break;
                case IN_PROGRESS:
                    content.inProgressTasks.remove(index);
                    break;
                case DONE:
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

    @Override
    public void editTask(Column column, int index, String message) {
        try {
            switch (column) {
                case SHOULD_DO:
                    content.shouldDoTasks.set(index, message);
                    break;
                case IN_PROGRESS:
                    content.inProgressTasks.set(index, message);
                    break;
                case DONE:
                    content.doneTasks.set(index, message);
                    break;
                default:
                    break;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("ERROR. You do not have tasks with this number");
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
