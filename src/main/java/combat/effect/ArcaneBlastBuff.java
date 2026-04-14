package combat.effect;

import combat.model.Combatant;

/**
 * Arcane Blast buff — tracks ATK bonus gained from Arcane Blast kills.
 * Each kill adds +10 ATK, persisting until end of level.
 * Duration is effectively infinite (Integer.MAX_VALUE); manually expired via expire().
 */
public class ArcaneBlastBuff extends StatusEffect {
    private int bonusAttack;

    public ArcaneBlastBuff(int bonusAttack) {
        super(Integer.MAX_VALUE);
        this.bonusAttack = bonusAttack;
    }

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

    public void expire() {
        remainingDuration = 0;
    }

    @Override
    public String getName() {
        return "Arcane Blast (+" + bonusAttack + " ATK)";
    }
}
