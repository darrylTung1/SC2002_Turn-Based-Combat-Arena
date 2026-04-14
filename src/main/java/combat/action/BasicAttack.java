package combat.action;

import combat.effect.AttackBlockingEffect;
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

        if (target.hasEffect(AttackBlockingEffect.class)) return;

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

    @Override
    public Combatant resolveTarget(BattleContext context) {
        return target;
    }

    public Combatant getTarget() {
        return target;
    }
}
