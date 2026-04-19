package combat.effect;

import combat.model.Combatant;

// Defend buff -> increases defense by 10
    // Duration: current round + next round [2 turns]

public class DefendBuff extends StatusEffect {
    private static final int DEFENSE_BONUS = 10;

    public DefendBuff(int duration) {
        super(duration);
    }

    @Override
    public void onApply(Combatant target) {
        target.modifyDefense(DEFENSE_BONUS);
    }

    @Override
    public void onExpire(Combatant target) {
        target.modifyDefense(-DEFENSE_BONUS);
    }

    @Override
    public String getName() {
        return "Defending";
    }
}
