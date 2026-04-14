package combat.item;

import combat.effect.SmokeBombEffect;
import combat.engine.BattleContext;
import combat.model.Combatant;
import combat.model.Player;

/**
 * Smoke Bomb — enemy attacks deal 0 damage for current turn + next turn.
 */
public class SmokeBomb implements Item {

    @Override
    public void use(Player user, BattleContext context) {
        user.addStatusEffect(new SmokeBombEffect(2));
    }

    @Override
    public String getName() {
        return "Smoke Bomb";
    }

    @Override
    public String getDescription() {
        return "Enemy attacks deal 0 damage this turn and next turn";
    }

    @Override
    public boolean requiresTarget() {
        return false;
    }

    @Override
    public Combatant resolveTarget(Player user, BattleContext context) {
        return user;
    }
}
