package entities.helpers.output;

import entities.content.ContentAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class Printer {

    public static String WelcomePage = "" +
            "WELCOME TO KANBASH. Here we have kanban methodology via CLI\n" +
            "We have 3 columns: SHOULD DO, IN PROGRESS and DONE\n" +
            "commands supported:\n" +
            "- new 'task' - creates new task in SHOULD DO\n" +
            "- pick 'index' - pick I'th command from SHOULD DO and move it to IN PROGRESS\n" +
            "- done 'index' - pick one task from IN PROGRESS and move it to DONE\n" +
            "- clear 'something' - allows to clear certain column or all\n" +
            "- exit - for exit" +
            "MAKE YOUR PRODUCTIVITY GREAT AGAIN\n";

    private static final String delimiter = "----------------------------------------"; // 40 symbols

    public static void print() {
        printSimple();
//        printWithFormat();
    }

    private static void printSimple() {
        System.out.println("SHOULD DO");
        ContentAdapter.getShouldDo().forEach(System.out::println);
        System.out.println(delimiter);

        System.out.println("IN PROGRESS");
        ContentAdapter.getInProgress().forEach(System.out::println);
        System.out.println(delimiter);

        System.out.println("DONE");
        ContentAdapter.getDone().forEach(System.out::println);
        System.out.println(delimiter);

    }

    private void printWithFormat() {
        ArrayList<String> shouldDoForOutput   = Formatter.format(ContentAdapter.getShouldDo());
        ArrayList<String> inProgressForOutput = Formatter.format(ContentAdapter.getInProgress());
        ArrayList<String> doneForOutput       = Formatter.format(ContentAdapter.getDone());

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

//        for (int i = 0; i < limits[2]; i++) {
//            System.out.println(
//                    (shouldDoForOutput.get(i)   == null ? space : shouldDoForOutput.get(i))   + "|" +
//                    (inProgressForOutput.get(i) == null ? space : inProgressForOutput.get(i)) + "|" +
//                    (doneForOutput.get(i)       == null ? space : doneForOutput.get(i))
//            );
//        }

//        for (String line : shouldDoForOutput) {
//            System.out.println(line);
//        }

    }
}
