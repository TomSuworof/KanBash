import entities.client.Client;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        client.work();

//        Content content = new Gson().fromJson(new FileReader("src\\main\\resources\\content.json"), Content.class);
//
//        content.getShouldDo().forEach(System.out::println);
    }
}

/*
todo - create 'welcome' page
    - make columns instead of blocks
    - turn it to app
 */