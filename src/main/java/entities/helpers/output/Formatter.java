package entities.helpers.output;

import java.util.ArrayList;

public class Formatter {
    public static ArrayList<String> format(ArrayList<String> tasks) {
        ArrayList<String> taskLines = new ArrayList<>();

        for (String task : tasks) {
            String[] words = task.split(" ");
            StringBuilder line = new StringBuilder();
            for (String word : words) {
                if ((line + word).length() <= 40) {
                    line.append(word);
                } else {
                    while (line.length() < 40) {
                        line.append(" ");
                    }
                    taskLines.add(line.toString());
                    line = new StringBuilder(word + " ");
                    continue;
                }
                if (line.length() < 40) {
                    line.append(" ");
                }
            }
            while(line.length() < 40) {
                line.append(" ");
            }
            taskLines.add(line.toString());
        }

        return taskLines;
    }
}
