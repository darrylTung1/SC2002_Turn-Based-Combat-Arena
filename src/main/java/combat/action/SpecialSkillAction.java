package combat.action;

import combat.engine.BattleContext;
import combat.model.Combatant;
import combat.model.Player;

// Execute player's class-specific special ability [Cooldown: 3 turns incl. current round]

public class SpecialSkillAction implements Action {
    private Combatant target;

    public void setTarget(Combatant target) {
        this.target = target;
    }

    @Override
    public void execute(Combatant actor, BattleContext context) {
        // LSP: fail explicitly rather than silently doing nothing for non-Player actors.
        if (!(actor instanceof Player player))
            throw new IllegalStateException("SpecialSkillAction requires a Player actor");

        player.executeSpecialSkill(target, context.getAliveEnemies());
        player.setSpecialSkillCooldown(3);
    }

    @Override
    public String getName() {
        return "Special Skill";
    }

    @Override
    public boolean isAvailable(Combatant actor, BattleContext context) {
        return (actor instanceof Player player) && player.isSpecialSkillReady();
    }

    @Override
    public Combatant resolveTarget(BattleContext context) {
        return target;
    }

    public Combatant getTarget() {
        return target;
    }
}
