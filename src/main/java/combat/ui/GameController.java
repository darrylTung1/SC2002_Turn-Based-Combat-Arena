package combat.ui;

import combat.engine.BattleEngine;
import combat.item.Item;
import combat.level.Difficulty;
import combat.level.Level;
import combat.model.Player;
import combat.strategy.SpeedBasedTurnOrder;

import java.util.List;

/**
 * Controller that orchestrates the game lifecycle.
 * Connects UI ↔ Engine. Handles replay/new-game loop.
 * SRP: Only game flow orchestration.
 */
public class GameController {
    private final GameUI ui;

    public GameController(GameUI ui) {
        this.ui = ui;
    }

    public void run() {
        boolean running = true;

        while (running) {
            // Setup phase
            Player player = ui.selectPlayer();
            List<Item> items = ui.selectItems();
            items.forEach(player::addItem);
            Difficulty difficulty = ui.selectDifficulty();

            // Create level
            int levelNumber = switch (difficulty) {
                case EASY -> 1;
                case MEDIUM -> 2;
                case HARD -> 3;
            };
            Level level = new Level(difficulty, levelNumber);

            // Create engine with strategy injection (DIP)
            BattleEngine engine = new BattleEngine(
                    new SpeedBasedTurnOrder(),
                    ui
            );

            // Run battle
            engine.startBattle(player, level);

            // Post-game
            if (!ui.promptReplay()) {
                running = ui.promptNewGame();
            }
        }

        System.out.println("Thanks for playing!");
    }
}
