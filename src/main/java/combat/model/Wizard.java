package combat.model;

import combat.effect.ArcaneBlastBuff;
import java.util.List;

// Wizard player class.
 // Special Skill: Arcane Blast — deals BasicAttack damage to ALL enemies.
 // Each kill adds +10 ATK lasting until end of level (tracked via ArcaneBlastBuff).

public class Wizard extends Player {
    private static final int MAX_HP = 200;
    private static final int ATTACK = 50;
    private static final int DEFENSE = 10;
    private static final int SPEED = 20;
    private static final int KILL_BONUS = 10;

    public Wizard() {
        super("Wizard", MAX_HP, ATTACK, DEFENSE, SPEED);
    }

    @Override
    public void executeSpecialSkill(Combatant target, List<Combatant> allEnemies) {
        int killCount = 0;
        for (Combatant enemy : allEnemies) {
            if (enemy.isAlive()) {
                int damage = this.calculateDamageTo(enemy);
                enemy.takeDamage(damage);
                if (!enemy.isAlive()) {
                    killCount++;
                }
            }
        }
        // +10 ATK per kill, tracked via ArcaneBlastBuff status effect
        if (killCount > 0) {
            int bonus = killCount * KILL_BONUS;
            ArcaneBlastBuff existing = getEffect(ArcaneBlastBuff.class);
            if (existing != null) {
                existing.addBonus(bonus, this);
            } else {
                addStatusEffect(new ArcaneBlastBuff(bonus));
            }
        }
    }

    @Override
    public String getSpecialSkillName() {
        return "Arcane Blast";
    }
    @Override
    public String getSpecialSkillDescription() {
        return "Deal BasicAttack damage to ALL enemies. Each kill adds +" + KILL_BONUS + " ATK until end of level.";
    }

    @Override
    public String getSpecialSkillDescription() {
        return "Deal BasicAttack damage to ALL enemies. Each kill adds +" + KILL_BONUS + " ATK until end of level.";
    }

    @Override
    public Player createFresh() {
        return new Wizard();
    }
}
