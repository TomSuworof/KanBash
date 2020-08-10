package entities.helpers.output;

import com.google.gson.Gson;
import entities.content.Content;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
    Content content;

    public Printer() {
        try (FileReader fileReader = new FileReader("src\\main\\resources\\content.json")) {
            content = new Gson().fromJson(fileReader, Content.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        printSimple();
//        printWithFormat();
    }

    private void printSimple() {
        System.out.println("SHOULD DO");
        content.getShouldDo().forEach(System.out::println);
        System.out.println(delimiter);

        System.out.println("IN PROGRESS");
        content.getInProgress().forEach(System.out::println);
        System.out.println(delimiter);

        System.out.println("DONE");
        content.getDone().forEach(System.out::println);
        System.out.println(delimiter);

    }

    private void printWithFormat() {
        ArrayList<String> shouldDoForOutput   = Formatter.format(content.getShouldDo());
        ArrayList<String> inProgressForOutput = Formatter.format(content.getInProgress());
        ArrayList<String> doneForOutput       = Formatter.format(content.getDone());

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
