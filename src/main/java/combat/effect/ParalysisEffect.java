package combat.effect;

import combat.model.Combatant;

/**
 * Paralysis effect — reduces defense by 10 for 2 turns.
 */
public class ParalysisEffect extends StatusEffect {
    private static final int DEFENSE_REDUCTION = 5;

    public ParalysisEffect(int duration) {
        super(duration);
    }

    @Override
    public void onApply(Combatant target) {
        target.modifyDefense(-DEFENSE_REDUCTION);
    }

    @Override
    public void onExpire(Combatant target) {
        target.modifyDefense(DEFENSE_REDUCTION);
    }

    @Override
    public String getName() {
        return "Paralysis";
    }
}