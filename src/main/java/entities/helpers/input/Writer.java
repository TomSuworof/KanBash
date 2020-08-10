package entities.helpers.input;

import com.google.gson.Gson;
import entities.content.Content;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private static Content content;

    public Writer() {
        try (FileReader fileReader = new FileReader("src\\main\\resources\\content.json")) {
            content = new Gson().fromJson(fileReader, Content.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newTask(String message) {
        // adding new task in column 'do'
        content.addShouldDo(message);
    }

    public void pick(int index) {
        // move i'th task to 'inProgress'
        content.moveShouldDoToInProgress(index);
    }

    public void done(int index) {
        // move i'th task tp 'done'
        content.moveInProgressToDone(index);
    }

    public void clear(String message) {
        // clear certain column or all of them
        content.removeSomething(message);
    }

    public void updateBase() {
        String jsonString = new Gson().toJson(content);
        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\content.json", false)) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
