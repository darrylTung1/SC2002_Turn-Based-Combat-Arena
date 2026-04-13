package combat.action;

import combat.engine.BattleContext;
import combat.model.Combatant;

// Basic attack action

// Formula: Damage = max(0, Attacker ATK - Target DEF)

// HP cannot go below 0

public class BasicAttack implements Action {
    private Combatant target;

    public BasicAttack() {}

    public void setTarget(Combatant target) {
        this.target = target;
    }

    @Override
    public void execute(Combatant actor, BattleContext context) {
        if (target == null || !target.isAlive()) return;

        // Check if SmokeBomb effect is active on the target (player) -> if SmokeBomb is active then enemy attacks do 0 damage.
        if (context.isSmokeBombActive() && actor instanceof combat.model.Enemy) {
            // 0 damage due to smoke bomb
            return;
        }

        int damage = Math.max(0, actor.getAttack() - target.getDefense());
        target.takeDamage(damage);
    }

    @Override
    public String getName() {
        return "Basic Attack";
    }

    @Override
    public boolean isAvailable(Combatant actor, BattleContext context) {
        return true;
    }

    public Combatant getTarget() {
        return target;
    }
}
