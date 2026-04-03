package combat.ui;

import combat.action.Action;
import combat.engine.BattleContext;
import combat.item.Item;
import combat.level.Difficulty;
import combat.model.Combatant;
import combat.model.Enemy;
import combat.model.Player;

import java.util.List;

/**
 * Interface for the game's user interface.
 * DIP: BattleEngine depends on this abstraction, not CLIView.
 * ISP: Methods are focused on UI concerns only.
 * Allows future replacement with GUI without changing engine.
 */
public interface GameUI {

    // --- Setup Screens ---
    Player selectPlayer();
    List<Item> selectItems();
    Difficulty selectDifficulty();

    // --- Battle Display ---
    void displayBattleStart(Player player, List<Enemy> enemies);
    void displayRoundStart(int roundNumber);
    void displayRoundEnd(BattleContext context);
    void displayStunned(Combatant combatant);
    void displayBackupSpawn(List<Enemy> backup);
    void displayActionResult(Combatant actor, Action action, BattleContext context);

    // --- Player Input ---
    Action getPlayerAction(Player player, BattleContext context);
    Combatant selectTarget(List<Combatant> targets);
    Item selectItem(List<Item> items);

    // --- End Screens ---
    void displayVictory(BattleContext context);
    void displayDefeat(BattleContext context);

    /**
     * @return true if the player wants to replay
     */
    boolean promptReplay();

    /**
     * @return true if the player wants to start a new game (vs exit)
     */
    boolean promptNewGame();
}
