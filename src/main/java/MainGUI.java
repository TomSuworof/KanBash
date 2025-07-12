import entities.client.Client;
import entities.content.ContentAdapter;
import entities.content.JsonFileContentAdapter;
import entities.helpers.input.gui.GUICommander;
import entities.helpers.output.gui.BoardPanel;
import entities.helpers.output.gui.GUIPrinter;

public class MainGUI {
    public static void main(String[] args) {
        ContentAdapter contentAdapter = new JsonFileContentAdapter();

        BoardPanel boardPanel = new BoardPanel();

        Client client = new GUICommander(
                contentAdapter,
                boardPanel,
                new GUIPrinter(
                        contentAdapter,
                        boardPanel
                )
        );
        client.start();
    }
}
