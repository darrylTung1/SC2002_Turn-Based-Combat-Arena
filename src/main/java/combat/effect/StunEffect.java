package combat.effect;

/**
 * Stun effect — affected entity cannot take actions.
 * Duration: 2 turns (current turn + next turn).
 */
public class StunEffect extends StatusEffect {

    public StunEffect(int duration) {
        super(duration);
    }

    @Override
    public boolean preventsAction() {
        return true;
    }

    @Override
    public String getName() {
        return "Stunned";
    }
}
