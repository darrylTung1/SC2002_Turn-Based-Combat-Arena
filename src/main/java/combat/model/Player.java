package combat.model;

import combat.item.Item;
import java.util.ArrayList;
import java.util.List;

// Abstract base class for player-controlled combatants.

// Adds item inventory and special skill cooldown management.

public abstract class Player extends Combatant {
    private final List<Item> items;
    private int specialSkillCooldown;

    protected Player(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
        this.items = new ArrayList<>();
        this.specialSkillCooldown = 0;
    }

    // Item Management
    public void addItem(Item item) {
        this.items.add(item);
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public boolean hasItems() {
        return !items.isEmpty();
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    // --- Cooldown Management ---
    public int getSpecialSkillCooldown() {
        return specialSkillCooldown;
    }

    public void setSpecialSkillCooldown(int cooldown) {
        this.specialSkillCooldown = cooldown;
    }

    public void decrementCooldown() {
        if (specialSkillCooldown > 0) {
            specialSkillCooldown--;
        }
    }

    public boolean isSpecialSkillReady() {
        return specialSkillCooldown == 0;
    }

    // Execute the class-specific special skill. -> Subclasses must implement their unique ability
    public abstract void executeSpecialSkill(Combatant target, List<Combatant> allEnemies);

    // Returns the name of the special skill
    public abstract String getSpecialSkillName();
    
    public abstract Player createFresh();
}
