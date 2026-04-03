package combat.model;

import combat.strategy.EnemyActionStrategy;

/**
 * Abstract base class for enemy combatants.
 * DIP: Enemy depends on EnemyActionStrategy abstraction,
 * allowing future behavior variations without modifying Enemy.
 */
public abstract class Enemy extends Combatant {
    private final EnemyActionStrategy actionStrategy;

    protected Enemy(String name, int maxHp, int attack, int defense, int speed,
                    EnemyActionStrategy actionStrategy) {
        super(name, maxHp, attack, defense, speed);
        this.actionStrategy = actionStrategy;
    }

    public EnemyActionStrategy getActionStrategy() {
        return actionStrategy;
    }
}
