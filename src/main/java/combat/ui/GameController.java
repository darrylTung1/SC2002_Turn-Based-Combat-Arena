package combat.ui;

import combat.engine.BattleEngine;
import combat.engine.BattleResult;
import combat.item.Item;
import combat.level.Difficulty;
import combat.level.Level;
import combat.model.Player;
import combat.strategy.TurnOrderStrategy;
import java.util.List;

/**
 * Controller that orchestrates the game lifecycle.
 * Connects UI <-> Engine. Handles replay/new-game loop.
 * SRP: Only game flow orchestration.
 * LSP: uses Player.createFresh() instead of instanceof Warrior/Wizard to clone the player.
 */
public class GameController {
    private final GameUI ui;
    private final TurnOrderStrategy turnOrderStrategy;

    public GameController(GameUI ui, TurnOrderStrategy turnOrderStrategy) {
        this.ui = ui;
        this.turnOrderStrategy = turnOrderStrategy;
    }

    public void run() {
        boolean running = true;

        while (running) {
            Player originalPlayer = ui.selectPlayer();
            List<Item> items = ui.selectItems();
            Difficulty difficulty = ui.selectDifficulty();

            int levelNumber = switch (difficulty) {
                case EASY -> 1;
                case MEDIUM -> 2;
                case HARD -> 3;
            };

            boolean inCurrentSetup = true;

            while (running && inCurrentSetup) {
                Player battlePlayer = originalPlayer.createFresh();
                items.forEach(battlePlayer::addItem);

                Level level = new Level(difficulty, levelNumber);
                BattleEngine engine = new BattleEngine(turnOrderStrategy, ui);

                BattleResult result = engine.startBattle(battlePlayer, level);

                if (result == BattleResult.DEFEAT) {
                    PostBattleChoice choice = ui.promptReplay();
                    switch (choice) {
                        case REPLAY -> { /* same settings, restart battle */ }
                        case NEW_GAME -> inCurrentSetup = false;
                        case EXIT -> {
                            running = false;
                            inCurrentSetup = false;
                        }
                    }
                } else {
                    // Victory — game ends immediately
                    running = false;
                    inCurrentSetup = false;
                }
            }
        }

        System.out.println("Thanks for playing!");
    }
}
