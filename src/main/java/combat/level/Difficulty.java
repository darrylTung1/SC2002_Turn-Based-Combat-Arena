package combat.level;

import combat.model.Enemy;
import combat.model.Goblin;
import combat.model.Wolf;
import java.util.List;

/**
 * Difficulty levels.
 * OCP: each difficulty owns its own spawn configuration via abstract methods.
 * Adding a new difficulty only requires adding a new enum constant — no switch statements elsewhere need to change.
 */
public enum Difficulty {

    EASY("Easy") {
        @Override
        public List<Enemy> createInitialSpawn() {
            return List.of(new Goblin("Goblin A"), new Goblin("Goblin B"), new Goblin("Goblin C"));
        }
        @Override
        public List<Enemy> createBackupSpawn() {
            return List.of();
        }
        @Override
        public boolean hasBackupSpawn() {
            return false;
        }
    },

    MEDIUM("Medium") {
        @Override
        public List<Enemy> createInitialSpawn() {
            return List.of(new Goblin("Goblin"), new Wolf("Wolf"));
        }
        @Override
        public List<Enemy> createBackupSpawn() {
            return List.of(new Wolf("Wolf A"), new Wolf("Wolf B"));
        }
        @Override
        public boolean hasBackupSpawn() {
            return true;
        }
    },

    HARD("Hard") {
        @Override
        public List<Enemy> createInitialSpawn() {
            return List.of(new Goblin("Goblin A"), new Goblin("Goblin B"));
        }
        @Override
        public List<Enemy> createBackupSpawn() {
            return List.of(new Goblin("Goblin C"), new Wolf("Wolf A"), new Wolf("Wolf B"));
        }
        @Override
        public boolean hasBackupSpawn() {
            return true;
        }
    };

    private final String displayName;

    Difficulty(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public abstract List<Enemy> createInitialSpawn();
    public abstract List<Enemy> createBackupSpawn();
    public abstract boolean hasBackupSpawn();
}
