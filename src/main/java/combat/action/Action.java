package combat.action;

import combat.engine.BattleContext;
import combat.model.Combatant;

/**
 * Interface for all combat actions.
 * OCP: New actions can be added by implementing this interface
 * without modifying BattleEngine.
 * DIP: BattleEngine depends on this abstraction, not concrete actions.
 */
public interface Action {

    /**
     * Execute this action.
     * @param actor      the combatant performing the action
     * @param context    the current battle state
     */
    void execute(Combatant actor, BattleContext context);

    /**
     * @return display name of this action for the UI
     */
    String getName();

    /**
     * Check if this action can be performed by the actor in the current context.
     * @param actor   the combatant attempting the action
     * @param context the current battle state
     * @return true if the action is available
     */
    boolean isAvailable(Combatant actor, BattleContext context);
}
