package entities.helpers.output;

import entities.content.ContentAdapter;

public abstract class Printer {
    protected ContentAdapter contentAdapter;

    public Printer(ContentAdapter contentAdapter) {
        this.contentAdapter = contentAdapter;
    }

    public abstract void print();

    public abstract void printWelcomePage();
}
