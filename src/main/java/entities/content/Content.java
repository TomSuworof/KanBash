package entities.content;

import java.util.ArrayList;
import java.util.List;

public class Content {
    public Boolean isNumerationUsed = false;

    public final List<String> shouldDoTasks = new ArrayList<>();
    public final List<String> inProgressTasks = new ArrayList<>();
    public final List<String> doneTasks = new ArrayList<>();
}
