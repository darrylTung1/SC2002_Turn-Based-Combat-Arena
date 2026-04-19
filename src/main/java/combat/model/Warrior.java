package combat.model;

import combat.effect.StunEffect;
import java.util.List;

// Warrior player class.
 // Special Skill: Shield Bash — deals BasicAttack damage to target, stuns for 2 turns.

public class Warrior extends Player {
    private static final int MAX_HP = 260;
    private static final int ATTACK = 40;
    private static final int DEFENSE = 20;
    private static final int SPEED = 30;

    public Warrior() {
        super("Warrior", MAX_HP, ATTACK, DEFENSE, SPEED);
    }

    @Override
    public void executeSpecialSkill(Combatant target, List<Combatant> allEnemies) {
        int damage = this.calculateDamageTo(target);
        target.takeDamage(damage);

        // Stun target: unable to act for current turn + next turn (2 turns total)
        if (target.isAlive()) {
            target.addStatusEffect(new StunEffect(2));
        }
    }

    @Override
    public String getSpecialSkillName() {
        return "Shield Bash";
    }
    @Override
    public String getSpecialSkillDescription() {
        return "Deal BasicAttack damage to selected enemy. Enemy cannot act for the current and next turn.";
    }

    @Override
    public String getSpecialSkillDescription() {
        return "Deal BasicAttack damage to selected enemy. Enemy cannot act for the current and next turn.";
    }

    @Override
    public Player createFresh() {
        return new Warrior();
    }
}
