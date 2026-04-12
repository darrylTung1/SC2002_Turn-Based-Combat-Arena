package combat.action;

import combat.engine.BattleContext;
import combat.effect.DefendBuff;
import combat.model.Combatant;

// Defend action

// Increases defense by 10 for current and next round [2 turns]

public class DefendAction extends TargetAction {

    @Override
    public void execute(Combatant actor, BattleContext context) {
        actor.addStatusEffect(new DefendBuff(2)); // Current + next round
        actor.modifyDefense(10);
    }

    @Override
    public String getName() {
        return "Defend";
    }

    @Override
    public boolean isAvailable(Combatant actor, BattleContext context) {
        return true; // Always available
    }
}
