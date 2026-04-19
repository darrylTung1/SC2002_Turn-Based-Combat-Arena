package combat.strategy;

import combat.action.Action;
import combat.action.BasicAttack;
import combat.engine.BattleContext;
import combat.model.Enemy;

// Default enemy strategy: always perform BasicAttack on the player

public class BasicAttackOnlyStrategy implements EnemyActionStrategy {

    @Override
    public Action chooseAction(Enemy enemy, BattleContext context) {
        BasicAttack attack = new BasicAttack();
        attack.setTarget(context.getPlayer());
        return attack;
    }
}
