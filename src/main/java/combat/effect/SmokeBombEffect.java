package combat.effect;

// Smoke Bomb effect -> incoming attacks deal 0 damage
    // Duration: current turn + next turn (2 turns)
    // Overrides modifyIncomingDamage() to block all damage
    
public class SmokeBombEffect extends StatusEffect {

    public SmokeBombEffect(int duration) {
        super(duration);
    }

    @Override
    public int modifyIncomingDamage(int damage) {
        return 0;
    }

    @Override
    public String getName() {
        return "Smoke Bomb";
    }
}
