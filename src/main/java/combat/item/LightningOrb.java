package combat.item;

import combat.effect.ParalysisEffect;
import combat.effect.StunEffect;
import combat.engine.BattleContext;
import combat.model.Combatant;
import combat.model.Player;

/**
 * Lightning Orb — deals AoE lightning damage to all enemies.
 * Normal enemies take 20 damage.
 * Stunned enemies take 30 damage.
 * Applies Paralysis (defense reduction) for 2 turns.
 */
public class LightningOrb implements Item {
    private static final int NORMAL_DAMAGE = 20;
    private static final int BONUS_DAMAGE = 30;

    @Override
    public void use(Player user, BattleContext context) {
        System.out.println("Lightning Orb releases a chain lightning blast!");

        for (Combatant enemy : context.getAliveEnemies()) {
            int oldHp = enemy.getHp();

            int damage = enemy.hasEffect(StunEffect.class)
                    ? BONUS_DAMAGE
                    : NORMAL_DAMAGE;

            int finalDamage = Math.max(0, damage - enemy.getDefense());
            enemy.takeDamage(enemy.applyDamageModifiers(finalDamage));
            int newHp = enemy.getHp();

            System.out.println(enemy.getName() + ": HP " + oldHp + " -> " + newHp);

            if (damage == BONUS_DAMAGE) {
                System.out.println("Bonus lightning damage applied to stunned target!");
            }

            if (enemy.isAlive() && !enemy.hasEffect(ParalysisEffect.class)) {
                enemy.addStatusEffect(new ParalysisEffect(2));
                System.out.println(enemy.getName() + " is PARALYZED! DEF reduced.");
            }
        }
    }

    @Override
    public String getName() {
        return "Lightning Orb";
    }

    @Override
    public String getDescription() {
        return "AoE lightning: 20 dmg, or 30 to stunned enemies; applies Paralysis (-10 DEF)";
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