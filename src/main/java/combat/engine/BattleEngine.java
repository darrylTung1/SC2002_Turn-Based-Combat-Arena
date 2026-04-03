package combat.engine;

import combat.action.Action;
import combat.effect.StunEffect;
import combat.model.Combatant;
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
    private final TurnOrderStrategy turnOrderStrategy;
    private final GameUI ui;
    private BattleContext context;
    private boolean backupSpawned;

    public BattleEngine(TurnOrderStrategy turnOrderStrategy, GameUI ui) {
        this.turnOrderStrategy = turnOrderStrategy;
        this.ui = ui;
        this.backupSpawned = false;
    }

    /**
     * Run the entire battle for a given level.
     * @param player the player combatant
     * @param level  the level configuration
     */
    public void startBattle(Player player, Level level) {
        // Initialize context with initial spawn
        context = new BattleContext(player, level.getInitialSpawn());
        backupSpawned = false;

        ui.displayBattleStart(player, context.getAllEnemies());

        // Main game loop
        while (!context.allEnemiesDefeated() && !context.isPlayerDefeated()) {
            context.incrementRound();
            ui.displayRoundStart(context.getCurrentRound());

            executeRound();

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
        } else {
            ui.displayVictory(context);
        }
    }

    private void executeRound() {
        List<Combatant> turnOrder = turnOrderStrategy.determineTurnOrder(
                context.getAliveCombatants()
        );

        for (Combatant combatant : turnOrder) {
            if (!combatant.isAlive()) continue;

            // Check stun
            if (combatant.hasEffect(StunEffect.class)) {
                ui.displayStunned(combatant);
                combatant.tickEffects();
                // Decrement cooldown even if stunned (turn still counted)
                if (combatant instanceof Player player) {
                    player.decrementCooldown();
                }
                continue;
            }

            // Execute turn
            if (combatant instanceof Player player) {
                executePlayerTurn(player);
            } else if (combatant instanceof Enemy enemy) {
                executeEnemyTurn(enemy);
            }

            // Tick status effects at end of turn
            combatant.tickEffects();

            // Check for game-ending condition after each action
            if (context.isPlayerDefeated() || context.allEnemiesDefeated()) {
                break;
            }
        }
    }

    private void executePlayerTurn(Player player) {
        Action action = ui.getPlayerAction(player, context);
        action.execute(player, context);
        player.decrementCooldown();
        ui.displayActionResult(player, action, context);
    }

    private void executeEnemyTurn(Enemy enemy) {
        Action action = enemy.getActionStrategy().chooseAction(enemy, context);
        action.execute(enemy, context);
        ui.displayActionResult(enemy, action, context);
    }
}
