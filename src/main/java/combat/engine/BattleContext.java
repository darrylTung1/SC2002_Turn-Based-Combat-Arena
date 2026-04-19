package combat.engine;

import combat.model.Combatant;
import combat.model.Enemy;
import combat.model.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Holds current battle state
// Passed to Actions and Items so they can query/modify battle state without coupling to BattleEngine internals
    // SRP: Only manages battle state, no logic

public class BattleContext {
    private final Player player;
    private final List<Enemy> enemies;
    private int currentRound;
    private Combatant selectedTarget;

    public BattleContext(Player player, List<Enemy> enemies) {
        this.player = player;
        this.enemies = new ArrayList<>(enemies);
        this.currentRound = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getAllEnemies() {
        return new ArrayList<>(enemies);
    }

    public List<Combatant> getAliveEnemies() {
        return enemies.stream()
                .filter(Combatant::isAlive)
                .collect(Collectors.toList());
    }

    public List<Combatant> getAliveCombatants() {
        List<Combatant> alive = new ArrayList<>();
        if (player.isAlive()) alive.add(player);
        alive.addAll(getAliveEnemies());
        return alive;
    }

    public void addEnemies(List<Enemy> newEnemies) {
        enemies.addAll(newEnemies);
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void incrementRound() {
        currentRound++;
    }

    public Combatant getSelectedTarget() {
        return selectedTarget;
    }

    public void setSelectedTarget(Combatant target) {
        this.selectedTarget = target;
    }

    public boolean allEnemiesDefeated() {
        return enemies.stream().noneMatch(Combatant::isAlive);
    }

    public boolean isPlayerDefeated() {
        return !player.isAlive();
    }
}
