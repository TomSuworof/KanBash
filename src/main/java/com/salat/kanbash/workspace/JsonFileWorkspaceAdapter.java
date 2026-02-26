package com.salat.kanbash.workspace;

import com.salat.kanbash.output.common.Numeration;
import com.salat.kanbash.output.gui.Theme;
import com.salat.kanbash.storageadapters.JsonFileAdapter;

public class JsonFileWorkspaceAdapter extends JsonFileAdapter<Workspace> implements WorkspaceAdapter {
    public JsonFileWorkspaceAdapter() {
        super("workspace.json", Workspace.class, Workspace::new);
    }

    @Override
    public Numeration getNumeration() {
        return data.numeration;
    }

    @Override
    public Theme getTheme() {
        return data.theme;
    }

    @Override
    public WindowState getLastWindowState() {
        return data.lastWindowState;
    }

    @Override
    public void setNumeration(Numeration numeration) {
        data.numeration = numeration;
        store();
    }

    @Override
    public void setTheme(Theme theme) {
        data.theme = theme;
        store();
    }

    @Override
    public void setLastWindowState(WindowState windowState) {
        data.lastWindowState = windowState;
        store();
    }
}
