package combat.ui;

import combat.action.Action;
import combat.engine.BattleContext;
import combat.item.Item;
import combat.model.Combatant;
import combat.model.Enemy;
import combat.model.Player;
import java.util.List;


/**
 * Interface for battle-phase UI only.
 * ISP: BattleEngine depends on this narrower interface instead of the full GameUI, so it is not forced to depend on setup/end-screen methods it never uses.
/**
 * Interface for battle-phase UI only.
 * ISP: BattleEngine depends on this narrower interface instead of the full GameUI,
 * so it is not forced to depend on setup/end-screen methods it never uses.
 */
public interface BattleUI {

    // Battle Display
    void displayBattleStart(Player player, List<Enemy> enemies);
    void displayRoundStart(int roundNumber);
    void displayRoundEnd(BattleContext context);
    void displayActionSkipped(Combatant combatant, String reason);
    void displayBackupSpawn(List<Enemy> backup);
    void displayActionResult(Combatant actor, Action action, BattleContext context, List<Combatant> combatants, int[] hpBefore);

    // Player Input
    Action getPlayerAction(Player player, BattleContext context);
    Combatant selectTarget(List<Combatant> targets);
    Item selectItem(List<Item> items);

    // End Screens
    void displayVictory(BattleContext context);
    void displayDefeat(BattleContext context);
}
