package combat.model;

import combat.strategy.BasicAttackOnlyStrategy;

// Goblin enemy type.
public class Goblin extends Enemy {
    private static final int MAX_HP = 55;
    private static final int ATTACK = 35;
    private static final int DEFENSE = 15;
    private static final int SPEED = 25;

    public Goblin(String name) {
        super(name, MAX_HP, ATTACK, DEFENSE, SPEED, new BasicAttackOnlyStrategy());
    }

    public Goblin() {
        this("Goblin");
    }
}
