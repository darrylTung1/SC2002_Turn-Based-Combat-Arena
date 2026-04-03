package combat.effect;

import combat.model.Combatant;

/**
 * Stun effect — affected entity cannot take actions.
 * Duration: 2 turns (current turn + next turn).
 */
public class StunEffect implements StatusEffect {
    private int remainingDuration;

    public StunEffect(int duration) {
        this.remainingDuration = duration;
    }

    @Override
    public void tick(Combatant target) {
        remainingDuration--;
    }

    @Override
    public void onApply(Combatant target) {
        // No stat changes, just prevents action
    }

    @Override
    public void onExpire(Combatant target) {
        // No cleanup needed
    }

    @Override
    public boolean isExpired() {
        return remainingDuration <= 0;
    }

    @Override
    public int getRemainingDuration() {
        return remainingDuration;
    }

    @Override
    public String getName() {
        return "Stunned";
    }
}
