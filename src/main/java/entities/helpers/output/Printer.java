package entities.helpers.output;

import entities.content.ContentAdapter;
import entities.content.GsonContentAdapter;

public abstract class Printer {
    protected static ContentAdapter contentAdapter;


    public Printer(ContentAdapter contentAdapter) {
        this.contentAdapter = contentAdapter;
    }

    public abstract void print();

    public abstract void printWelcomePage();
}
