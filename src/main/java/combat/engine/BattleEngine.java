package combat.engine;

import combat.action.Action;
import combat.action.*  ;
import combat.item.Item;
import combat.item.Potion;
import combat.item.PowerStone;
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
    public BattleResult startBattle(Player player, Level level){
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
            return BattleResult.DEFEAT;
        } else {
            ui.displayVictory(context);
            return BattleResult.VICTORY;
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
                // combatant.tickEffects();
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
            // combatant.tickEffects();

            // Check for game-ending condition after each action
            if (context.isPlayerDefeated() || context.allEnemiesDefeated()) {
                break;
            }
        }

        for (Combatant combatant : turnOrder) {
            if (!combatant.isAlive()) continue;
            combatant.tickEffects();
        }
    }

    private void executePlayerTurn(Player player) {
        Action action = ui.getPlayerAction(player, context);
        Combatant target = null;
        if (action instanceof BasicAttack basicAttack) {
            target = basicAttack.getTarget();
        }
        else if (action instanceof SpecialSkillAction skill) {
            target = skill.getTarget();
        }
        else if (action instanceof UseItemAction useItem) {
            Item item = useItem.getSelectedItem();

            if (item instanceof Potion) {
                target = player;
            } else if (item instanceof PowerStone) {
                target = context.getSelectedTarget();
            }
        }
        else {
            target = player;
        }
        int oldHp = target != null ? target.getHp() : 0;
        action.execute(player, context);
        player.decrementCooldown();
        int newHp = target != null ? target.getHp() : 0;
        ui.displayActionResult(player, action, context, target, oldHp, newHp);
    }

    private void executeEnemyTurn(Enemy enemy) {
        Action action = enemy.getActionStrategy().chooseAction(enemy, context);
        Combatant target = null;
        if (action instanceof BasicAttack basicAttack) {
            target = basicAttack.getTarget();
        }
        else if (action instanceof SpecialSkillAction skill) {
            target = skill.getTarget();
        }
        else if (action instanceof UseItemAction useItem) {
            Item item = useItem.getSelectedItem();

            if (item instanceof Potion) {
                target = enemy;
            } else if (item instanceof PowerStone) {
                target = context.getSelectedTarget();
            }
        }
        else {
            target = enemy;
        }
        int oldHp = target != null ? target.getHp() : 0;
        action.execute(enemy, context);
        int newHp = target != null ? target.getHp() : 0;
        ui.displayActionResult(enemy, action, context, target, oldHp, newHp);
    }
}
