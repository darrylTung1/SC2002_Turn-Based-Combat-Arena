package combat;

import combat.ui.CLIView;
import combat.ui.GameController;

/**
 * Application entry point.
 */
public class App {
    public static void main(String[] args) {
        GameController controller = new GameController(new CLIView());
        controller.run();
    }
}
