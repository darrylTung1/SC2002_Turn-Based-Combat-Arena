package combat;

import combat.strategy.SpeedBasedTurnOrder;
import combat.ui.CLIView;
import combat.ui.GameController;

// Application entry point
// Composition root: concrete dependencies (CLIView, SpeedBasedTurnOrder) are wired here and injected - nothing downstream needs to instantiate them

public class App {
    public static void main(String[] args) {
        GameController controller = new GameController(new CLIView(), new SpeedBasedTurnOrder());
        controller.run();
    }
}
