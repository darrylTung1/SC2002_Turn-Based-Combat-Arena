package combat.effect;

import combat.model.Combatant;

// Interface for all status effects applied to combatants.
// OCP: new effects can be added without modifying existing code.
public interface StatusEffect {

    // Called at the end of each turn to tick down duration.
    void tick(Combatant target);

    void onApply(Combatant target);

    void onExpire(Combatant target);

    boolean isExpired();

    int getRemainingDuration();

    String getName();
}
