package combat.item;

import combat.effect.ParalysisEffect;
import combat.effect.StunEffect;
import combat.engine.BattleContext;
import combat.model.Combatant;
import combat.model.Player;

// Lightning Orb - deals AoE lightning damage to all enemies
    // Normal enemies take 30 damage
    // Stunned enemies take 40 damage
// Applies Paralysis (defense reduction) for 2 turns

public class LightningOrb implements Item {
    private static final int NORMAL_DAMAGE = 30;
    private static final int BONUS_DAMAGE = 40;

    @Override
    public void use(Player user, BattleContext context) {
        for (Combatant enemy : context.getAliveEnemies()) {
            int damage = enemy.hasEffect(StunEffect.class) ? BONUS_DAMAGE : NORMAL_DAMAGE;
            int finalDamage = Math.max(0, damage - enemy.getDefense());
            enemy.takeDamage(enemy.applyDamageModifiers(finalDamage));

            if (enemy.isAlive()) {
                enemy.addStatusEffect(new ParalysisEffect(2));
            }
        }
    }

    @Override
    public String getName() {
        return "Lightning Orb";
    }

    @Override
    public String getDescription() {
        return "AoE lightning: 30 dmg, or 40 to stunned enemies; applies Paralysis (-5 DEF)";
    }

    @Override
    public boolean requiresTarget() {
        return false;
    }

    @Override
    public Combatant resolveTarget(Player user, BattleContext context) {
        return null;
    }
}