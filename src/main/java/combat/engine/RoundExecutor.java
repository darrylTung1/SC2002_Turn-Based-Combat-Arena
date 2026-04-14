package combat.engine;

import combat.action.Action;
import combat.action.BasicAttack;
import combat.action.SpecialSkillAction;
import combat.action.UseItemAction;
import combat.effect.StunEffect;
import combat.item.Item;
import combat.item.Potion;
import combat.item.PowerStone;
import combat.model.Combatant;
import combat.model.Enemy;
import combat.model.Player;
import combat.strategy.TurnOrderStrategy;
import combat.ui.GameUI;

import java.util.List;

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
            if (!combatant.isAlive()) continue;

            if (combatant.hasEffect(StunEffect.class)) {
                ui.displayStunned(combatant);
                if (combatant instanceof Player player) {
                    player.decrementCooldown();
                }
                continue;
            }

            if (combatant instanceof Player player) {
                executePlayerTurn(player, context);
            } else if (combatant instanceof Enemy enemy) {
                executeEnemyTurn(enemy, context);
            }

            if (context.isPlayerDefeated() || context.allEnemiesDefeated()) {
                break;
            }
        }

        for (Combatant combatant : turnOrder) {
            if (!combatant.isAlive()) continue;
            combatant.tickEffects();
        }
    }

    private void executePlayerTurn(Player player, BattleContext context) {
        Action action = ui.getPlayerAction(player, context);
        Combatant target = null;

        if (action instanceof BasicAttack basicAttack) {
            target = basicAttack.getTarget();
        } else if (action instanceof SpecialSkillAction skill) {
            target = skill.getTarget();
        } else if (action instanceof UseItemAction useItem) {
            Item item = useItem.getSelectedItem();
            if (item instanceof Potion) {
                target = player;
            } else if (item instanceof PowerStone) {
                target = context.getSelectedTarget();
            }
        } else {
            target = player;
        }

        int oldHp = target != null ? target.getHp() : 0;
        action.execute(player, context);
        player.decrementCooldown();
        int newHp = target != null ? target.getHp() : 0;
        ui.displayActionResult(player, action, context, target, oldHp, newHp);
    }

    private void executeEnemyTurn(Enemy enemy, BattleContext context) {
        Action action = enemy.getActionStrategy().chooseAction(enemy, context);
        Combatant target = null;

        if (action instanceof BasicAttack basicAttack) {
            target = basicAttack.getTarget();
        } else if (action instanceof SpecialSkillAction skill) {
            target = skill.getTarget();
        } else if (action instanceof UseItemAction useItem) {
            Item item = useItem.getSelectedItem();
            if (item instanceof Potion) {
                target = enemy;
            } else if (item instanceof PowerStone) {
                target = context.getSelectedTarget();
            }
        } else {
            target = enemy;
        }

        int oldHp = target != null ? target.getHp() : 0;
        action.execute(enemy, context);
        int newHp = target != null ? target.getHp() : 0;
        ui.displayActionResult(enemy, action, context, target, oldHp, newHp);
    }
}