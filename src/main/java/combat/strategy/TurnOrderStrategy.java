package combat.strategy;

import combat.model.Combatant;
import java.util.List;

// Strategy interface for determining turn order
    // OCP: New turn order strategies can be added without modifying BattleEngine
    // DIP: BattleEngine depends on this abstraction
    
public interface TurnOrderStrategy {

    /**
     * Determine the order in which combatants act this round.
     * @param combatants all alive combatants
     * @return ordered list of combatants (first = acts first)
     */
    List<Combatant> determineTurnOrder(List<Combatant> combatants);
}
