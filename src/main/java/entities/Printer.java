package entities;

import com.google.gson.Gson;
import entities.content.Content;

import java.io.FileReader;
import java.io.IOException;

public class Printer {
    private static final String delimiter = "-------------------------------------";
    Content content;

    public Printer() {
        try (FileReader fileReader = new FileReader("src\\main\\resources\\content.json")) {
            content = new Gson().fromJson(fileReader, Content.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
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
}
