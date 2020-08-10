import entities.client.Client;
import entities.helpers.output.Formatter;

import java.util.ArrayList;
import java.util.Arrays;

public class KanBash {
    public static void main(String[] args) {
        Client client = new Client();
        client.work();

//        Content content = new Gson().fromJson(new FileReader("src\\main\\resources\\content.json"), Content.class);
//
//        content.getShouldDo().forEach(System.out::println);
//
//        ArrayList<String> test = new ArrayList<>();
//        test.add("- it is a very long text for testing, how Formatter can help us to keep our task easy-to-read and informative");
//        test.add(" - another very long text. just for double-checking, how our algorithm works");
//        ArrayList<String> output = Formatter.format(test);
//        System.out.println("----------------------------------------");
//
//        for (String line : output) {
//            System.out.println(line);
//        } // todo
    }
}

/*
todo - make columns instead of blocks
    - turn it to app
 */