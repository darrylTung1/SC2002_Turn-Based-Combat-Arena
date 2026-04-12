package combat.action;

import combat.engine.BattleContext;
import combat.model.Combatant;

// Interface for all combat actions. 

public interface Action {

    // Execute this action. 
    // actor: combatant performing the action
    // context: current battle state 
    void execute(Combatant actor, BattleContext context);

    // return display name of this action 
    String getName();

    // Check if this action can be performed by the actor in the current context.
    // actor: the combatant attempting the action
    // context: the current battle state
    
    boolean isAvailable(Combatant actor, BattleContext context);
    // returns true if the action is available
}
