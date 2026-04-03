package combat.action;

import combat.engine.BattleContext;
import combat.model.Combatant;
import combat.model.Player;

/**
 * Action to execute the player's class-specific special ability.
 * Cooldown: 3 turns (including current round).
 */
public class SpecialSkillAction implements Action {
    private Combatant target;

    public void setTarget(Combatant target) {
        this.target = target;
    }

    @Override
    public void execute(Combatant actor, BattleContext context) {
        if (!(actor instanceof Player player)) return;

        player.executeSpecialSkill(target, context.getAliveEnemies());
        player.setSpecialSkillCooldown(3); // 3 turns including current
    }

    @Override
    public String getName() {
        return "Special Skill";
    }

    @Override
    public boolean isAvailable(Combatant actor, BattleContext context) {
        return (actor instanceof Player player) && player.isSpecialSkillReady();
    }

    public Combatant getTarget() {
        return target;
    }
}
