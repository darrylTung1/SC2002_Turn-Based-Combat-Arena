package combat.level;

import combat.model.Enemy;
import combat.model.Goblin;
import combat.model.Wolf;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates level configuration: difficulty, enemy spawns.
 * SRP: Only responsible for level setup, not battle logic.
 */
public class Level {
    private final Difficulty difficulty;
    private final int levelNumber;

    public Level(Difficulty difficulty, int levelNumber) {
        this.difficulty = difficulty;
        this.levelNumber = levelNumber;
    }

    /**
     * Create the initial wave of enemies for this level.
     */
    public List<Enemy> getInitialSpawn() {
        List<Enemy> enemies = new ArrayList<>();
        switch (difficulty) {
            case EASY -> {
                enemies.add(new Goblin("Goblin A"));
                enemies.add(new Goblin("Goblin B"));
                enemies.add(new Goblin("Goblin C"));
            }
            case MEDIUM -> {
                enemies.add(new Goblin("Goblin"));
                enemies.add(new Wolf("Wolf"));
            }
            case HARD -> {
                enemies.add(new Goblin("Goblin A"));
                enemies.add(new Goblin("Goblin B"));
            }
        }
        return enemies;
    }

    /**
     * Create the backup wave of enemies (if applicable).
     */
    public List<Enemy> getBackupSpawn() {
        List<Enemy> backup = new ArrayList<>();
        switch (difficulty) {
            case EASY -> { /* No backup */ }
            case MEDIUM -> {
                backup.add(new Wolf("Wolf A"));
                backup.add(new Wolf("Wolf B"));
            }
            case HARD -> {
                backup.add(new Goblin("Goblin C"));
                backup.add(new Wolf("Wolf A"));
                backup.add(new Wolf("Wolf B"));
            }
        }
        return backup;
    }

    public boolean hasBackupSpawn() {
        return difficulty == Difficulty.MEDIUM || difficulty == Difficulty.HARD;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
