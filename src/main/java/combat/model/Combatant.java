package combat.model;

import combat.effect.StatusEffect;
import java.util.ArrayList;
import java.util.List;

// Abstract base class for all entities in combat

// SRP: Holds combatant state and manages status effects.
// LSP: Player and Enemy are interchangeable as Combatant.

public abstract class Combatant {
    private final String name;
    private int hp;
    private final int maxHp;
    private int attack;
    private final int baseAttack;
    private int defense;
    private final int baseDefense;
    private final int speed;
    private final List<StatusEffect> statusEffects;

    protected Combatant(String name, int maxHp, int attack, int defense, int speed) {
        this.name = name;
        this.hp = maxHp;
        this.maxHp = maxHp;
        this.baseAttack = attack;
        this.attack = attack;
        this.baseDefense = defense;
        this.defense = defense;
        this.speed = speed;
        this.statusEffects = new ArrayList<>();
    }

    // HP Management 

    // HP no lower than 0
    // Apply raw damage to this combatant
    // Damage formulat (ATK-DEF) calculated by Actino
    // SRP: Combatant manages state; Action calculates damage
    public void takeDamage(int rawDamage) {
        this.hp = Math.max(0, this.hp - Math.max(0, rawDamage));
    }

    public void heal(int amount) {
        this.hp = Math.min(this.maxHp, this.hp + amount);
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    // Status Effect Management
    public void addStatusEffect(StatusEffect effect) {
        this.statusEffects.add(effect);
    }

    public void removeExpiredEffects() {
        statusEffects.removeIf(StatusEffect::isExpired);
    }

    public void tickEffects() {
        for (StatusEffect effect : statusEffects) {
            effect.tick(this);
        }
        removeExpiredEffects();
    }

    public boolean hasEffect(Class<? extends StatusEffect> effectType) {
        return statusEffects.stream().anyMatch(effectType::isInstance);
    }

    public <T extends StatusEffect> T getEffect(Class<T> effectType) {
        return statusEffects.stream()
                .filter(effectType::isInstance)
                .map(effectType::cast)
                .findFirst()
                .orElse(null);
    }

    public List<StatusEffect> getStatusEffects() {
        return new ArrayList<>(statusEffects);
    }

    public int applyDamageModifiers(int damage) {
        for (StatusEffect effect : statusEffects) {
            damage = effect.modifyIncomingDamage(damage);
        }
        return damage;
    }

    // Stat Modification
    public void modifyAttack(int amount) {
        this.attack += amount;
    }

    public void modifyDefense(int amount) {
        this.defense += amount;
    }

    public void resetDefense() {
        this.defense = baseDefense;
    }

    public void resetAttack() {
        this.attack = baseAttack;
    }

    // Getters
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public int getBaseAttack() { return baseAttack; }
    public int getDefense() { return defense; }
    public int getBaseDefense() { return baseDefense; }
    public int getSpeed() { return speed; }

    @Override
    public String toString() {
        return name + " [HP: " + hp + "/" + maxHp + " | ATK: " + attack
                + " | DEF: " + defense + " | SPD: " + speed + "]";
    }
}
