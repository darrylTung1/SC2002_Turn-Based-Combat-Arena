package combat.effect;

import combat.model.Combatant;

/**
 * Arcane Blast buff — tracks ATK bonus gained from Arcane Blast kills.
 * Each kill adds +10 ATK, persisting until end of level.
 * Duration is effectively infinite (set very high, removed on level end).
 */
public class ArcaneBlastBuff implements StatusEffect {
    private int bonusAttack;
    private boolean expired;

    public ArcaneBlastBuff(int bonusAttack) {
        this.bonusAttack = bonusAttack;
        this.expired = false;
    }

    /**
     * Stack additional ATK bonus from subsequent Arcane Blast kills.
     */
    public void addBonus(int additional, Combatant target) {
        this.bonusAttack += additional;
        target.modifyAttack(additional);
    }

    public int getBonusAttack() {
        return bonusAttack;
    }

    @Override
    public void tick(Combatant target) {
        // Does not expire from ticking — lasts until end of level
    }

    @Override
    public void onApply(Combatant target) {
        target.modifyAttack(bonusAttack);
    }

    @Override
    public void onExpire(Combatant target) {
        target.modifyAttack(-bonusAttack);
    }

    /**
     * Call this at end of level to remove the buff.
     */
    public void expire() {
        this.expired = true;
    }

    @Override
    public boolean isExpired() {
        return expired;
    }

    @Override
    public int getRemainingDuration() {
        return expired ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public String getName() {
        return "Arcane Blast (+" + bonusAttack + " ATK)";
    }
}
