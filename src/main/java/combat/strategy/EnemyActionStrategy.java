package combat.strategy;

import combat.action.Action;
import combat.engine.BattleContext;
import combat.model.Enemy;

/**
 * Strategy interface for enemy action selection.
 * OCP: New enemy behaviors can be added without modifying Enemy class.
 * Currently only BasicAttack, but designed for future extensibility.
 */
public interface EnemyActionStrategy {

    /**
     * Choose and configure the action for this enemy's turn.
     * @param enemy   the enemy acting
     * @param context the current battle state
     * @return the configured action to execute
     */
    Action chooseAction(Enemy enemy, BattleContext context);
}
