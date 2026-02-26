package com.salat.kanbash.content;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public List<String> getTasks(Column column) {
        return switch (column) {
            case SHOULD_DO -> Collections.unmodifiableList(content.shouldDoTasks);
            case IN_PROGRESS -> Collections.unmodifiableList(content.inProgressTasks);
            case DONE -> Collections.unmodifiableList(content.doneTasks);
            default -> throw new IllegalArgumentException("Cannot return all tasks");
        };
    }

    @Override
    public Optional<String> getTask(Column column, int index) {
        var tasks = getTasks(column);
        if (index >= tasks.size()) {
            return Optional.empty();
        } else {
            return Optional.of(tasks.get(index));
        }
    }

    @Override
    public void addTask(Column column, String taskText, int index) {
        switch (column) {
            case SHOULD_DO:
                content.shouldDoTasks.add(index, taskText);
                break;
            case IN_PROGRESS:
                content.inProgressTasks.add(index, taskText);
                break;
            case DONE:
                content.doneTasks.add(index, taskText);
                break;
            default:
                break;
        }
        update();
    }

    @Override
    public void moveTask(Column oldColumn, int oldIndex, Column newColumn, int newIndex) throws IndexOutOfBoundsException {
        String task = getTask(oldColumn, oldIndex).orElseThrow(IllegalArgumentException::new);
        this.removeTask(oldColumn, oldIndex);
        this.addTask(newColumn, task, newIndex);
    }

    @Override
    public void clear(Column column) {
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
        update();
    }

    @Override
    public void editTask(Column column, int index, String message) {
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
