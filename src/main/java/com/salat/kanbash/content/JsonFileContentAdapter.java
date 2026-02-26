package com.salat.kanbash.content;

import com.salat.kanbash.storageadapters.JsonFileAdapter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JsonFileContentAdapter extends JsonFileAdapter<Content> implements ContentAdapter {
    public JsonFileContentAdapter() {
        super("content.json", Content.class, Content::new);
    }

    @Override
    public List<String> getTasks(Column column) {
        return switch (column) {
            case SHOULD_DO -> Collections.unmodifiableList(data.shouldDoTasks);
            case IN_PROGRESS -> Collections.unmodifiableList(data.inProgressTasks);
            case DONE -> Collections.unmodifiableList(data.doneTasks);
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
                data.shouldDoTasks.add(index, taskText);
                break;
            case IN_PROGRESS:
                data.inProgressTasks.add(index, taskText);
                break;
            case DONE:
                data.doneTasks.add(index, taskText);
                break;
            default:
                break;
        }
        store();
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
                data.shouldDoTasks.clear();
                break;
            case IN_PROGRESS:
                data.inProgressTasks.clear();
                break;
            case DONE:
                data.doneTasks.clear();
                break;
            case ALL:
                data.shouldDoTasks.clear();
                data.inProgressTasks.clear();
                data.doneTasks.clear();
            default:
                break;
        }
        store();
    }

    @Override
    public void removeTask(Column column, int index) throws NumberFormatException {
        switch (column) {
            case SHOULD_DO:
                data.shouldDoTasks.remove(index);
                break;
            case IN_PROGRESS:
                data.inProgressTasks.remove(index);
                break;
            case DONE:
                data.doneTasks.remove(index);
                break;
            default:
                break;
        }
        store();
    }

    @Override
    public void editTask(Column column, int index, String message) {
        switch (column) {
            case SHOULD_DO:
                data.shouldDoTasks.set(index, message);
                break;
            case IN_PROGRESS:
                data.inProgressTasks.set(index, message);
                break;
            case DONE:
                data.doneTasks.set(index, message);
                break;
            default:
                break;
        }
        store();
    }
}
