import com.salat.kanbash.input.Client;
import com.salat.kanbash.content.ContentAdapter;
import com.salat.kanbash.content.JsonFileContentAdapter;
import com.salat.kanbash.input.gui.GUICommander;
import com.salat.kanbash.output.gui.BoardPanel;
import com.salat.kanbash.output.gui.GUIPrinter;
import com.salat.kanbash.output.gui.MenuBar;

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
