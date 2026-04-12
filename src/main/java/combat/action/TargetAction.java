package combat.action;

import combat.engine.BattleContext;
import combat.model.Combatant;

// Abstract class for target action
public abstract class TargetAction implements Action {
    private Combatant target;

    public void setTarget(Combatant target) {
        this.target = target;
    }

    public Combatant getTarget() {
        return target;
    }

    @Override public abstract void execute(Combatant actor, BattleContext context);
    @Override public abstract String getName();
    @Override public abstract boolean isAvailable(Combatant actor, BattleContext context);
}
