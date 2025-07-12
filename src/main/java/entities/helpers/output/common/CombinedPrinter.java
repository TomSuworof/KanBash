package entities.helpers.output.common;

import entities.helpers.output.Printer;

import java.util.List;

public class CombinedPrinter implements Printer {
    private final List<Printer> delegates;

    public CombinedPrinter(List<Printer> delegates) {
        this.delegates = delegates;
    }

    @Override
    public void print() {
        delegates.forEach(Printer::print);
    }

    @Override
    public void printWelcomePage() {
        delegates.forEach(Printer::printWelcomePage);
    }
}
