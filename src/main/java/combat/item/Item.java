package combat.item;

import combat.engine.BattleContext;
import combat.model.Combatant;
import combat.model.Player;

// Interface for all usable items
    // OCP: New items can be added by implementing this interface
    // ISP: Minimal interface - only what items need
    
public interface Item {

    // Use this item. Item is consumed after use
    void use(Player user, BattleContext context);

    String getName();

    String getDescription();

    // Whether this item requires the player to select a target before use
    boolean requiresTarget();

    // Resolve which combatant this item will affect, for display purposes
    Combatant resolveTarget(Player user, BattleContext context);
}
