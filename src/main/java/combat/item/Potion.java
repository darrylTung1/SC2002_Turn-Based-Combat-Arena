package combat.item;

import combat.engine.BattleContext;
import combat.model.Player;

/**
 * Potion — heals 100 HP, capped at max HP.
 */
public class Potion implements Item {
    private static final int HEAL_AMOUNT = 100;

    @Override
    public void use(Player user, BattleContext context) {
        user.heal(HEAL_AMOUNT);
    }

    @Override
    public String getName() {
        return "Potion";
    }

    @Override
    public String getDescription() {
        return "Heals " + HEAL_AMOUNT + " HP (capped at max HP)";
    }
<<<<<<< HEAD
=======

    @Override
    public boolean requiresTarget() {
        return false;
    }

    @Override
    public Combatant resolveTarget(Player user, BattleContext context) {
        return user;
    }
    @Override
    public Item createFresh() {
        return new Potion();
    }
>>>>>>> 5b4d3ffc3eab76116ed5985045465bb7708ffa3f
}
