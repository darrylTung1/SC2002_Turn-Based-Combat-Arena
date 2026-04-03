package combat.effect;

import combat.model.Combatant;

/**
 * Smoke Bomb effect — enemy attacks deal 0 damage.
 * Duration: current turn + next turn (2 turns).
 */
public class SmokeBombEffect implements StatusEffect {
    private int remainingDuration;

    public SmokeBombEffect(int duration) {
        this.remainingDuration = duration;
    }

    @Override
    public void tick(Combatant target) {
        remainingDuration--;
    }

    @Override
    public void onApply(Combatant target) {
        // Effect is checked in BattleContext.isSmokeBombActive()
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
        return "Smoke Bomb";
    }
}
