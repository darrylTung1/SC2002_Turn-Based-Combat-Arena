package combat.model;

import combat.strategy.BasicAttackOnlyStrategy;

/**
 * Wolf enemy type.
 */
public class Wolf extends Enemy {
    private static final int MAX_HP = 40;
    private static final int ATTACK = 45;
    private static final int DEFENSE = 5;
    private static final int SPEED = 35;

    public Wolf(String name) {
        super(name, MAX_HP, ATTACK, DEFENSE, SPEED, new BasicAttackOnlyStrategy());
    }

    public Wolf() {
        this("Wolf");
    }
}
