package combat.effect;

import combat.model.Combatant;

/**
 * Defend buff — increases defense by 10.
 * Duration: current round + next round (2 turns).
 */
public class DefendBuff implements StatusEffect {
    private int remainingDuration;
    private static final int DEFENSE_BONUS = 10;

    public DefendBuff(int duration) {
        this.remainingDuration = duration;
    }

    @Override
    public void tick(Combatant target) {
        remainingDuration--;
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
    public boolean isExpired() {
        return remainingDuration <= 0;
    }

    @Override
    public int getRemainingDuration() {
        return remainingDuration;
    }

    @Override
    public String getName() {
        return "Defending";
    }
}
