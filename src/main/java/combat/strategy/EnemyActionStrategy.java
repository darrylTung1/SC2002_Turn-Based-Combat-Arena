package combat.strategy;

import combat.action.Action;
import combat.engine.BattleContext;
import combat.model.Enemy;

// Strategy interface for enemy action selection
    // OCP: New enemy behaviours can be added without modifying Enemy class
    // Currently only BasicAttack, but designed for future extensibility

public interface EnemyActionStrategy {
    // enemy -> the enemy acting
    // context -> the current battle state
    // returns the configured action to execute
    Action chooseAction(Enemy enemy, BattleContext context);
}
