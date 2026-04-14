package combat.effect;

import combat.model.Combatant;

/**
 * Smoke Bomb effect — enemy attacks deal 0 damage.
 * Duration: current turn + next turn (2 turns).
 * The blocking logic lives in BattleContext.isActorBlocked(), which BasicAttack queries.
 */
// Implements AttackBlockingEffect so BasicAttack knows to deal 0 damage to this combatant.
public class SmokeBombEffect implements AttackBlockingEffect {
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
        // No stat changes needed
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
