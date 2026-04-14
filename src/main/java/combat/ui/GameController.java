package combat.ui;

import combat.engine.BattleEngine;
import combat.engine.BattleResult;
import combat.item.Item;
import combat.level.Difficulty;
import combat.level.Level;
import combat.model.Player;
import combat.strategy.TurnOrderStrategy;
<<<<<<< HEAD

=======
>>>>>>> e96d39b (refactor status effect hierarchy and damage handling)
import java.util.List;

/**
 * Controller that orchestrates the game lifecycle.
<<<<<<< HEAD
 * Connects UI <-> Engine. Handles replay/new-game loop.
=======
 * Connects UI  <-> Engine. Handles replay/new-game loop.
>>>>>>> e96d39b (refactor status effect hierarchy and damage handling)
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
<<<<<<< HEAD
=======
                // LSP fix: no instanceof — every Player subclass knows how to clone itself.
>>>>>>> e96d39b (refactor status effect hierarchy and damage handling)
                Player battlePlayer = originalPlayer.createFresh();
                items.forEach(battlePlayer::addItem);

                Level level = new Level(difficulty, levelNumber);
                BattleEngine engine = new BattleEngine(turnOrderStrategy, ui);

                BattleResult result = engine.startBattle(battlePlayer, level);

                if (result == BattleResult.DEFEAT) {
                    PostBattleChoice choice = ui.promptReplay();
                    switch (choice) {
<<<<<<< HEAD
                        case REPLAY -> {
                            // Replay with same settings
                        }
=======
                        case REPLAY -> { /* same settings, restart battle */ }
>>>>>>> e96d39b (refactor status effect hierarchy and damage handling)
                        case NEW_GAME -> inCurrentSetup = false;
                        case EXIT -> {
                            running = false;
                            inCurrentSetup = false;
                        }
                    }
                } else {
                    running = false;
                    inCurrentSetup = false;
                }
            }
        }

        System.out.println("Thanks for playing!");
    }
<<<<<<< HEAD

    private List<Item> createFreshItems(List<Item> templates) {
        return templates.stream()
                .map(Item::createFresh)
                .toList();
    }
}
=======
}
>>>>>>> e96d39b (refactor status effect hierarchy and damage handling)
