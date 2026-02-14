package com.salat.kanbash.content;

import com.salat.kanbash.output.common.Numeration;
import com.salat.kanbash.output.gui.Theme;

import java.util.ArrayList;
import java.util.List;

public class Content {
    public Numeration numeration = Numeration.HYPHEN;
    public Theme theme = Theme.SYSTEM;

    public WindowState lastWindowState = null;

    public final List<String> shouldDoTasks = new ArrayList<>();
    public final List<String> inProgressTasks = new ArrayList<>();
    public final List<String> doneTasks = new ArrayList<>();
}
