package combat.effect;

import combat.model.Combatant;

/**
 * Abstract base class for all status effects applied to combatants.
 * OCP: new effects can be added without modifying existing code.
 * Holds remainingDuration and provides shared tick/expiry logic.
 * Subclasses implement onApply(), onExpire(), and getName().
 */
public abstract class StatusEffect {
    protected int remainingDuration;

    protected StatusEffect(int duration) {
        this.remainingDuration = duration;
    }

    public void tick(Combatant target) {
        remainingDuration--;
    }

    public abstract void onApply(Combatant target);

    public abstract void onExpire(Combatant target);

    public boolean isExpired() {
        return remainingDuration <= 0;
    }

    public int getRemainingDuration() {
        return remainingDuration;
    }

    public abstract String getName();

    /**
     * Modify incoming damage to the combatant carrying this effect.
     * Default is pass-through — override to intercept damage (e.g. SmokeBombEffect returns 0).
     */
    public int modifyIncomingDamage(int damage) {
        return damage;
    }
}
