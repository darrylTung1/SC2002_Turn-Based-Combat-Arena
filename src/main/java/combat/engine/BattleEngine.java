package combat.engine;
import combat.model.Enemy;
import combat.model.Player;
import combat.strategy.TurnOrderStrategy;
import combat.ui.GameUI;
import combat.level.Level;

import java.util.List;

/**
 * Core battle management engine.
 * DIP: Depends on abstractions (TurnOrderStrategy, Action, GameUI) — not concrete classes.
 * SRP: Manages battle flow only. Does not handle UI or entity creation.
 */
public class BattleEngine {
	private final RoundExecutor roundExecutor;
	private final GameUI ui;
	private BattleContext context;
	private boolean backupSpawned;
    public BattleEngine(TurnOrderStrategy turnOrderStrategy, GameUI ui) {
        this.roundExecutor = new RoundExecutor(turnOrderStrategy, ui);
        this.ui = ui;
        this.backupSpawned = false;
    }

    /**
     * Run the entire battle for a given level.
     * @param player the player combatant
     * @param level  the level configuration
     */
    public BattleResult startBattle(Player player, Level level){
        // Initialize context with initial spawn
        context = new BattleContext(player, level.getInitialSpawn());
        backupSpawned = false;

        ui.displayBattleStart(player, context.getAllEnemies());

        // Main game loop
        while (!context.allEnemiesDefeated() && !context.isPlayerDefeated()) {
            context.incrementRound();
            ui.displayRoundStart(context.getCurrentRound());

            roundExecutor.execute(context);
            // Check for backup spawn
            if (!backupSpawned && context.allEnemiesDefeated() && level.hasBackupSpawn()) {
                List<Enemy> backup = level.getBackupSpawn();
                context.addEnemies(backup);
                backupSpawned = true;
                ui.displayBackupSpawn(backup);
            }

            ui.displayRoundEnd(context);
        }

        // Display result
        if (context.isPlayerDefeated()) {
            ui.displayDefeat(context);
            return BattleResult.DEFEAT;
        } else {
            ui.displayVictory(context);
            return BattleResult.VICTORY;
        }
    }
}
