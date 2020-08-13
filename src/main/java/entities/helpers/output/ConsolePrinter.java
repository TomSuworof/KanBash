package entities.helpers.output;

import entities.content.ContentAdapter;
import entities.content.GsonContentAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ConsolePrinter implements Printer {

    private static final ContentAdapter contentAdapter = new GsonContentAdapter();

    private static final String WelcomePage = "" +
            "WELCOME TO KANBASH. Here we have kanban methodology via CLI\n" +
            "We have 3 columns: SHOULD DO, IN PROGRESS and DONE\n" +
            "commands supported:\n" +
            "- new 'task' - creates new task in SHOULD DO\n" +
            "- pick 'index' - pick I'th command from SHOULD DO and move it to IN PROGRESS\n" +
            "- done 'index' - pick one task from IN PROGRESS and move it to DONE\n" +
            "- clear 'something' - allows to clear certain column or all\n" +
            "- remove 'something' 'index' - allows to remove certain element from column\n" +
            "- numeration 'number' or 'hyphen' - allows to change numeration of tasks\n" +
            "- exit - for exit\n" +
            "MAKE YOUR PRODUCTIVITY GREAT AGAIN\n";

    private static final String delimiter = "----------------------------------------"; // 40 symbols

    public void print() {
//        printSimple();
        printWithFormat();
    }
    public void printWelcomePage() {
        System.out.println(WelcomePage);
    }
    private void printSimple() {
        System.out.println("SHOULD DO");
        contentAdapter.getShouldDo().forEach(System.out::println);
        System.out.println(delimiter);

        System.out.println("IN PROGRESS");
        contentAdapter.getInProgress().forEach(System.out::println);
        System.out.println(delimiter);

        System.out.println("DONE");
        contentAdapter.getDone().forEach(System.out::println);
        System.out.println(delimiter);
    }
    private void printWithFormat() {
        ArrayList<String> shouldDoForOutput   = format(contentAdapter.getShouldDo());
        ArrayList<String> inProgressForOutput = format(contentAdapter.getInProgress());
        ArrayList<String> doneForOutput       = format(contentAdapter.getDone());

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
            } catch (IndexOutOfBoundsException index0) {
                System.out.print(space);
            }
            System.out.print("|");
            try {
                System.out.print(inProgressForOutput.get(i));
            } catch (IndexOutOfBoundsException index1) {
                System.out.print(space);
            }
            System.out.print("|");
            try {
                System.out.println(doneForOutput.get(i));
            } catch (IndexOutOfBoundsException index2) {
                System.out.println(space);
            }
        }
    }

    private ArrayList<String> format(ArrayList<String> tasks) {
        boolean isNumeration = contentAdapter.getNumeration();

        ArrayList<String> tasksNew;

        if (isNumeration) {
            AtomicInteger index = new AtomicInteger();
            tasksNew = (ArrayList<String>) tasks.stream().map(task -> task = index.getAndIncrement() + ". " + task).collect(Collectors.toList());
        } else {
            tasksNew = (ArrayList<String>) tasks.stream().map(task -> task = "- " + task).collect(Collectors.toList());
        }

        ArrayList<String> taskLines = new ArrayList<>();

        for (String task : tasksNew) {
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
