package entities.helpers.output;

import java.util.ArrayList;

public class Formatter {
    public static ArrayList<String> format(ArrayList<String> tasks) {
        ArrayList<String> taskLines = new ArrayList<>();

        for (String task : tasks) {
            String[] words = task.split(" ");
//            for (String word : words) {
//                System.out.println(word);
//            }
            String line = "";
            for (String word : words) {
                if (line.length() < 40) {
                    line += " ";
                }
                if ((line + word).length() <= 40) {
                    line += word;
                } else {
                   taskLines.add(line);
                   line = " " + word;
                }
            }
        }

        return taskLines;
    }
}
