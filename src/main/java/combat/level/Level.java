package combat.level;

import combat.model.Enemy;

import java.util.List;

// Encapsulates level configuration: difficulty, enemy spawns
    // SRP: Only responsible for level setup, not battle logic
    // OCP fix: spawn logic has moved into Difficulty - no switch statements here
    
public class Level {
    private final Difficulty difficulty;
    private final int levelNumber;

    public Level(Difficulty difficulty, int levelNumber) {
        this.difficulty = difficulty;
        this.levelNumber = levelNumber;
    }

    public List<Enemy> getInitialSpawn() {
        return difficulty.createInitialSpawn();
    }

    public List<Enemy> getBackupSpawn() {
        return difficulty.createBackupSpawn();
    }

    public boolean hasBackupSpawn() {
        return difficulty.hasBackupSpawn();
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
