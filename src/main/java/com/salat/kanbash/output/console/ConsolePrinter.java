package com.salat.kanbash.output.console;

import com.salat.kanbash.content.Column;
import com.salat.kanbash.content.ContentAdapter;
import com.salat.kanbash.output.Printer;
import com.salat.kanbash.output.common.Numeration;
import com.salat.kanbash.workspace.WorkspaceAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ConsolePrinter implements Printer {
    private static final String WELCOME_MESSAGE = """
            WELCOME TO KANBASH. Kanban methodology via CLI
            Board has 3 columns: SHOULD DO, IN PROGRESS and DONE
            Supported commands:
            - new 'task' - creates new task in SHOULD DO column
            - pick 'index' - picks i'th task from SHOULD DO and move it to IN PROGRESS
            - done 'index' - picks i'th task from IN PROGRESS and move it to DONE
            - clear 'something' - clears specified column or all columns
            - remove 'something' 'index' - removes specified task from column
             edit 'something' 'index' 'task' - edits specified task in column
            - numeration 'number' or 'hyphen' - changes numeration
            - exit - for exit
            MAKE YOUR PRODUCTIVITY GREAT AGAIN
            """;

    private static final String delimiter = "----------------------------------------"; // 40 symbols

    private final ContentAdapter contentAdapter;
    private final WorkspaceAdapter workspaceAdapter;

    public ConsolePrinter(ContentAdapter contentAdapter, WorkspaceAdapter workspaceAdapter) {
        this.contentAdapter = contentAdapter;
        this.workspaceAdapter = workspaceAdapter;
    }

    @Override
    public void print() {
        printWithFormat();
    }

    @Override
    public void printWelcomePage() {
        System.out.println(WELCOME_MESSAGE);
    }

    private void printWithFormat() {
        List<String> shouldDoForOutput = format(contentAdapter.getTasks(Column.SHOULD_DO));
        List<String> inProgressForOutput = format(contentAdapter.getTasks(Column.IN_PROGRESS));
        List<String> doneForOutput = format(contentAdapter.getTasks(Column.DONE));

        String shouldDoHeading = "----------------SHOULD-DO---------------";
        String inProgressHeading = "---------------IN-PROGRESS--------------";
        String doneHeading = "------------------DONE------------------";
        String space = "                                        ";

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

        if (workspaceAdapter.getNumeration() == Numeration.NUMBER) {
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
            while (line.length() < delimiter.length()) {
                line.append(" ");
            }
            taskLines.add(line.toString());
        }

        return taskLines;
    }
}
