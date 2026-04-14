package combat.engine;

import combat.action.Action;
import combat.effect.StunEffect;
import combat.model.Combatant;
import combat.model.Enemy;
import combat.model.Player;
import combat.strategy.TurnOrderStrategy;
import combat.ui.GameUI;

import java.util.List;

/**
 * Executes one complete round of battle.
 * SRP: Handles round-level flow only.
 * OCP: Does not hard-code concrete Action or Item types.
 */
public class RoundExecutor {
    private final TurnOrderStrategy turnOrderStrategy;
    private final GameUI ui;

    public RoundExecutor(TurnOrderStrategy turnOrderStrategy, GameUI ui) {
        this.turnOrderStrategy = turnOrderStrategy;
        this.ui = ui;
    }

    public void execute(BattleContext context) {
        List<Combatant> turnOrder = turnOrderStrategy.determineTurnOrder(
                context.getAliveCombatants()
        );

        for (Combatant combatant : turnOrder) {
            if (!combatant.isAlive()) {
                continue;
            }

            if (combatant.hasEffect(StunEffect.class)) {
                ui.displayStunned(combatant);

                if (combatant instanceof Player player) {
                    player.decrementCooldown();
                }
                continue;
            }

            executeTurn(combatant, context);

            if (context.isPlayerDefeated() || context.allEnemiesDefeated()) {
                break;
            }
        }

        for (Combatant combatant : turnOrder) {
            if (combatant.isAlive()) {
                combatant.tickEffects();
            }
        }
    }

    private void executeTurn(Combatant combatant, BattleContext context) {
        Action action = chooseAction(combatant, context);
        Combatant target = action.resolveTarget(context);

        int oldHp = target != null ? target.getHp() : 0;

        action.execute(combatant, context);

        if (combatant instanceof Player player) {
            player.decrementCooldown();
        }

        int newHp = target != null ? target.getHp() : 0;
        ui.displayActionResult(combatant, action, context, target, oldHp, newHp);
    }

    private Action chooseAction(Combatant combatant, BattleContext context) {
        if (combatant instanceof Player player) {
            return ui.getPlayerAction(player, context);
        }

        if (combatant instanceof Enemy enemy) {
            return enemy.getActionStrategy().chooseAction(enemy, context);
        }

        throw new IllegalStateException("Unknown combatant type: " + combatant.getClass().getName());
    }
}