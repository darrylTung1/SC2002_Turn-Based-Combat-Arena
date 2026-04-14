package combat.item;

import combat.engine.BattleContext;
import combat.model.Combatant;
import combat.model.Player;

/**
 * Power Stone — triggers the player's special skill once WITHOUT starting or changing the cooldown timer.
 */
public class PowerStone implements Item {

    @Override
    public void use(Player user, BattleContext context) {
        int savedCooldown = user.getSpecialSkillCooldown();
        user.executeSpecialSkill(
                context.getSelectedTarget(),
                context.getAliveEnemies()
        );
        user.setSpecialSkillCooldown(savedCooldown);
    }

    @Override
    public String getName() {
        return "Power Stone";
    }

    @Override
    public String getDescription() {
        return "Triggers special skill without affecting cooldown";
    }

    @Override
    public boolean requiresTarget() {
        return true;
    }

    @Override
    public Combatant resolveTarget(Player user, BattleContext context) {
        return context.getSelectedTarget();
    }
    @Override
    public Item createFresh() {
        return new PowerStone();
    }
}
