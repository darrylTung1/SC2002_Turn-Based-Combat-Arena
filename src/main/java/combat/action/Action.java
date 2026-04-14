package combat.action;

import combat.engine.BattleContext;
import combat.model.Combatant;

// Interface for all combat actions.

public interface Action {

    void execute(Combatant actor, BattleContext context);

    String getName();

    // Check if this action can be performed by the actor in the current context.
    boolean isAvailable(Combatant actor, BattleContext context);

    // Resolve which combatant this action targets, for display purposes.
    // Returns null if the action has no distinct target (e.g. self-buffs).
    Combatant resolveTarget(BattleContext context);
}
