package combat.effect;

import combat.model.Combatant;

/**
 * Interface for all status effects applied to combatants.
 * OCP: New effects can be added without modifying existing code.
 */
public interface StatusEffect {

    /**
     * Called each turn to apply ongoing effect logic.
     * @param target the combatant this effect is applied to
     */
    void tick(Combatant target);

    /**
     * Called when the effect is first applied.
     * @param target the combatant receiving the effect
     */
    void onApply(Combatant target);

    /**
     * Called when the effect expires and is removed.
     * @param target the combatant losing the effect
     */
    void onExpire(Combatant target);

    /**
     * @return true if this effect has expired and should be removed
     */
    boolean isExpired();

    /**
     * @return the remaining duration in turns
     */
    int getRemainingDuration();

    /**
     * @return display name of this effect
     */
    String getName();
}
