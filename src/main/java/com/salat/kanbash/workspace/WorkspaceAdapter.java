package com.salat.kanbash.workspace;

import com.salat.kanbash.output.common.Numeration;
import com.salat.kanbash.output.gui.Theme;

public interface WorkspaceAdapter {
    Numeration getNumeration();

    void setNumeration(Numeration numeration);

    WindowState getLastWindowState();

    Theme getTheme();

    void setTheme(Theme theme);

    void setLastWindowState(WindowState windowState);
}
