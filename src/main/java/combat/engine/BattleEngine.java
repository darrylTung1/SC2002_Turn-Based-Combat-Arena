package combat.engine;

import combat.action.Action;
import combat.effect.StatusEffect;
import combat.level.Level;
import combat.model.Combatant;
import combat.model.Enemy;
import combat.model.Player;
import combat.strategy.TurnOrderStrategy;
import combat.ui.BattleUI;
import java.util.List;

// Core battle management engine 
    // DIP: Depends on abstractions (TurnOrderStrategy, Action, BattleUI) - not concrete classes
    // SRP: Manages battle flow only - no UI creation, no entity creation
    // OCP: instanceof chains on Action/Item types removed; target resolution delegated to Action.resolveTarget()

public class BattleEngine {
    private final TurnOrderStrategy turnOrderStrategy;
    private final BattleUI ui;

    public BattleEngine(TurnOrderStrategy turnOrderStrategy, BattleUI ui) {
        this.turnOrderStrategy = turnOrderStrategy;
        this.ui = ui;
    }

    public BattleResult startBattle(Player player, Level level) {
        BattleContext context = new BattleContext(player, level.getInitialSpawn());
        boolean backupSpawned = false;

        ui.displayBattleStart(player, context.getAllEnemies());

        while (!context.allEnemiesDefeated() && !context.isPlayerDefeated()) {
            context.incrementRound();
            ui.displayRoundStart(context.getCurrentRound());

            executeRound(context);

            if (!backupSpawned && context.allEnemiesDefeated() && level.hasBackupSpawn()) {
                List<Enemy> backup = level.getBackupSpawn();
                context.addEnemies(backup);
                backupSpawned = true;
                ui.displayBackupSpawn(backup);
            }

            ui.displayRoundEnd(context);
        }

        if (context.isPlayerDefeated()) {
            ui.displayDefeat(context);
            return BattleResult.DEFEAT;
        } else {
            ui.displayVictory(context);
            return BattleResult.VICTORY;
        }
    }

    private void executeRound(BattleContext context) {
        List<Combatant> turnOrder = turnOrderStrategy.determineTurnOrder(
                context.getAliveCombatants()
        );

        for (Combatant combatant : turnOrder) {
            if (!combatant.isAlive()) continue;

            boolean actionPrevented = combatant.getStatusEffects().stream()
                    .filter(StatusEffect::preventsAction)
                    .findFirst()
                    .map(effect -> { ui.displayActionSkipped(combatant, effect.getName()); return true; })
                    .orElse(false);
            if (actionPrevented) continue;

            executeTurn(combatant, context);

            if (context.isPlayerDefeated() || context.allEnemiesDefeated()) break;
        }

        for (Combatant combatant : turnOrder) {
            if (combatant.isAlive()) combatant.tickEffects();
        }
    }

    // Execute a single combatant's turn
        // OCP: uses Action.resolveTarget() so no instanceof chains are needed when new Action types are added

    private void executeTurn(Combatant combatant, BattleContext context) {
        Action action = chooseAction(combatant, context);

        List<Combatant> combatants = context.getAliveCombatants();
        int[] hpBefore = combatants.stream().mapToInt(Combatant::getHp).toArray();

        if (combatant instanceof Player player) player.decrementCooldown();

        action.execute(combatant, context);

        ui.displayActionResult(combatant, action, context, combatants, hpBefore);
    }

    // Choose the action for given combatant
    // [DIP issue] The instanceof dispatch here is unavoidable without adding UI coupling to Combatant.
        // -> Isolated to this single method to minimise impact
        
    private Action chooseAction(Combatant combatant, BattleContext context) {
        return switch (combatant) {
            case Player player -> ui.getPlayerAction(player, context);
            case Enemy enemy -> enemy.getActionStrategy().chooseAction(enemy, context);
            case null -> throw new IllegalStateException("combatant is null");
            default -> throw new IllegalStateException("Unknown combatant type: " + combatant.getClass());
        };
    }
}
