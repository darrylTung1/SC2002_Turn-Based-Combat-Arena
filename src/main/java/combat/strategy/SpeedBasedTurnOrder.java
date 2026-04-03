package combat.strategy;

import combat.model.Combatant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Turn order determined by speed stat (higher speed goes first).
 */
public class SpeedBasedTurnOrder implements TurnOrderStrategy {

    @Override
    public List<Combatant> determineTurnOrder(List<Combatant> combatants) {
        List<Combatant> ordered = new ArrayList<>(combatants);
        ordered.sort(Comparator.comparingInt(Combatant::getSpeed).reversed());
        return ordered;
    }
}
