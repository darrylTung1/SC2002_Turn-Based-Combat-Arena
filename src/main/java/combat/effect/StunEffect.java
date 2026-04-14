package combat.effect;

import combat.model.Combatant;

/**
 * Stun effect — affected entity cannot take actions.
 * Duration: 2 turns (current turn + next turn).
 */
public class StunEffect extends StatusEffect {

    public StunEffect(int duration) {
        super(duration);
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
    public String getName() {
        return "Stunned";
    }
}
