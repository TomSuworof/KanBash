import entities.client.Client;
import entities.content.ContentAdapter;
import entities.content.JsonFileContentAdapter;
import entities.helpers.input.gui.GUICommander;
import entities.helpers.output.gui.BoardPanel;
import entities.helpers.output.gui.GUIPrinter;
import entities.helpers.output.gui.MenuBar;

public class MainGUI {
    public static void main(String[] args) {
        ContentAdapter contentAdapter = new JsonFileContentAdapter();

        MenuBar menuBar = new MenuBar();
        BoardPanel boardPanel = new BoardPanel();

        Client client = new GUICommander(
                contentAdapter,
                menuBar,
                boardPanel,
                new GUIPrinter(
                        contentAdapter,
                        menuBar,
                        boardPanel
                )
        );
        client.start();
    }
}
