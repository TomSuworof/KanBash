package com.salat.kanbash.workspace;

import com.google.gson.Gson;
import com.salat.kanbash.output.common.Numeration;
import com.salat.kanbash.output.gui.Theme;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonFileWorkspaceAdapter implements WorkspaceAdapter {
    private static final String contentFileName = "workspace.json";

    private File workspaceFile;
    private Workspace workspace;

    public JsonFileWorkspaceAdapter() {
        try {
            workspaceFile = new File(contentFileName);
            workspaceFile.createNewFile(); // internally checks if file already exists

            FileReader fileReader = new FileReader(workspaceFile);
            workspace = new Gson().fromJson(fileReader, Workspace.class);
            fileReader.close();

            if (workspace == null) {
                workspace = new Workspace();
                update();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Numeration getNumeration() {
        return workspace.numeration;
    }

    @Override
    public Theme getTheme() {
        return workspace.theme;
    }

    @Override
    public WindowState getLastWindowState() {
        return workspace.lastWindowState;
    }

    @Override
    public void setNumeration(Numeration numeration) {
        workspace.numeration = numeration;
        update();
    }

    @Override
    public void setTheme(Theme theme) {
        workspace.theme = theme;
        update();
    }

    @Override
    public void setLastWindowState(WindowState windowState) {
        workspace.lastWindowState = windowState;
        update();
    }

    private void update() {
        String jsonString = new Gson().toJson(workspace);
        try (FileWriter fileWriter = new FileWriter(workspaceFile, false)) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
