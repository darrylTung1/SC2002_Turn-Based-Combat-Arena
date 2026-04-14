package combat.model;

import combat.effect.StunEffect;
import java.util.List;

// Warrior player class
// Special Skill: Shield Bash — deals BasicAttack damage to target, stuns for 2 turns

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
        int damage = Math.max(0, this.getAttack() - target.getDefense());
        target.takeDamage(damage);

        if (target.isAlive()) {
            StunEffect stun = new StunEffect(2);
            target.addStatusEffect(stun);
            stun.onApply(target);
        }
    }

    @Override
    public String getSpecialSkillName() {
        return "Shield Bash";
    }
<<<<<<< HEAD

=======
>>>>>>> 5b4d3ffc3eab76116ed5985045465bb7708ffa3f
    @Override
    public Player createFresh() {
        return new Warrior();
    }
}
