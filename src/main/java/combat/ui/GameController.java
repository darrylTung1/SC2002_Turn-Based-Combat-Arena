package combat.ui;

import combat.engine.BattleEngine;
import combat.engine.BattleResult;
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
        	Player selectedPlayer = ui.selectPlayer();
        	List<Item> selectedItems = ui.selectItems();
        	Difficulty difficulty = ui.selectDifficulty();

            int levelNumber = switch (difficulty) {
                case EASY -> 1;
                case MEDIUM -> 2;
                case HARD -> 3;
            };

            boolean inCurrentSetup = true;

            while (running && inCurrentSetup) {
            	Player battlePlayer = createFreshPlayer(selectedPlayer);
            	selectedItems.forEach(battlePlayer::addItem);
                Level level = new Level(difficulty, levelNumber);
                BattleEngine engine = new BattleEngine(
                        new SpeedBasedTurnOrder(),
                        ui
                );

                BattleResult result = engine.startBattle(battlePlayer, level);

                if (result == BattleResult.DEFEAT) {
                    PostBattleChoice choice = ui.promptReplay();

                    switch (choice) {
                        case REPLAY -> {
                            // same settings, battle restarts
                        }
                        case NEW_GAME -> inCurrentSetup = false;
                        case EXIT -> {
                            running = false;
                            inCurrentSetup = false;
                        }
                    }
                } else {
                    // Win -> exit directly
                    running = false;
                    inCurrentSetup = false;
                }
            }
        }

        System.out.println("Thanks for playing!");
    }
    
    
    /* Recreate a fresh player instance of the same class for replay.*/
    private Player createFreshPlayer(Player template) {
        try {
            return template.getClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Unable to recreate player of type: " + template.getClass().getSimpleName(), e
            );
        }
    }
}
