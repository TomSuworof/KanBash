package entities.helpers.output;

import entities.content.ContentAdapter;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ConsolePrinter extends Printer {
    private static final String WELCOME_MESSAGE = "" +
            "WELCOME TO KANBASH. Kanban methodology via CLI\n" +
            "Board has 3 columns: SHOULD DO, IN PROGRESS and DONE\n" +
            "Supported commands:\n" +
            "- new 'task' - creates new task in SHOULD DO column\n" +
            "- pick 'index' - picks i'th task from SHOULD DO and move it to IN PROGRESS\n" +
            "- done 'index' - picks i'th task from IN PROGRESS and move it to DONE\n" +
            "- clear 'something' - clears specified column or all columns\n" +
            "- remove 'something' 'index' - removes specified task from column\n" +
            "- numeration 'number' or 'hyphen' - changes numeration\n" +
            "- exit - for exit\n" +
            "MAKE YOUR PRODUCTIVITY GREAT AGAIN\n";

    private static final String delimiter = "----------------------------------------"; // 40 symbols

    public ConsolePrinter(ContentAdapter contentAdapter) {
        super(contentAdapter);
    }

    public void print() {
        printWithFormat();
    }
    public void printWelcomePage() {
        System.out.println(WELCOME_MESSAGE);
    }

    private void printWithFormat() {
        List<String> shouldDoForOutput   = format(contentAdapter.getShouldDoTasks());
        List<String> inProgressForOutput = format(contentAdapter.getInProgressTasks());
        List<String> doneForOutput       = format(contentAdapter.getDoneTasks());

        String shouldDoHeading   = "----------------SHOULD-DO---------------";
        String inProgressHeading = "---------------IN-PROGRESS--------------";
        String doneHeading       = "------------------DONE------------------";
        String space             = "                                        ";

        System.out.println(shouldDoHeading + "+" + inProgressHeading + "+" + doneHeading);

        int[] limits = {
                shouldDoForOutput.size(),
                inProgressForOutput.size(),
                doneForOutput.size()
        };
        Arrays.sort(limits);

        for (int i = 0; i < limits[2]; i++) {
            try {
                System.out.print(shouldDoForOutput.get(i));
            } catch (IndexOutOfBoundsException e) {
                System.out.print(space);
            }
            System.out.print("|");
            try {
                System.out.print(inProgressForOutput.get(i));
            } catch (IndexOutOfBoundsException e) {
                System.out.print(space);
            }
            System.out.print("|");
            try {
                System.out.println(doneForOutput.get(i));
            } catch (IndexOutOfBoundsException e) {
                System.out.println(space);
            }
        }
    }

    private List<String> format(List<String> tasks) {
        List<String> newTasks;

        if (contentAdapter.isNumerationUsed()) {
            AtomicInteger index = new AtomicInteger();
            newTasks = tasks.stream().map(task -> index.getAndIncrement() + ". " + task).collect(Collectors.toList());
        } else {
            newTasks = tasks.stream().map(task -> "- " + task).collect(Collectors.toList());
        }

        List<String> taskLines = new ArrayList<>();

        for (String task : newTasks) {
            String[] words = task.split(" ");
            StringBuilder line = new StringBuilder();
            for (String word : words) {
                if ((line + word).length() <= delimiter.length()) {
                    line.append(word);
                } else {
                    while (line.length() < delimiter.length()) {
                        line.append(" ");
                    }
                    taskLines.add(line.toString());
                    line = new StringBuilder(word + " ");
                    continue;
                }
                if (line.length() < delimiter.length()) {
                    line.append(" ");
                }
            }
            while(line.length() < delimiter.length()) {
                line.append(" ");
            }
            taskLines.add(line.toString());
        }

        return taskLines;
    }
}
