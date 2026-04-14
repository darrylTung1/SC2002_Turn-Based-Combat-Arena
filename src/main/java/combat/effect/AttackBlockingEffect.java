package combat.effect;

// Marker interface for status effects that block incoming attacks.
// Any effect implementing this will cause BasicAttack to deal 0 damage to the affected combatant.
// OCP: new blocking effects just implement this — no other code changes needed.
public interface AttackBlockingEffect extends StatusEffect {
}
