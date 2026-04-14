package combat.action;

import combat.engine.BattleContext;
import combat.effect.DefendBuff;
import combat.model.Combatant;

// Defend action — applies a DefendBuff that increases defense by 10 for 2 turns.

public class DefendAction implements Action {

    @Override
    public void execute(Combatant actor, BattleContext context) {
        actor.addStatusEffect(new DefendBuff(2));
    }

    @Override
    public String getName() {
        return "Defend";
    }

    @Override
    public boolean isAvailable(Combatant actor, BattleContext context) {
        return true;
    }

    @Override
    public Combatant resolveTarget(BattleContext context) {
        return null; // Self-effect, no distinct target
    }
}
