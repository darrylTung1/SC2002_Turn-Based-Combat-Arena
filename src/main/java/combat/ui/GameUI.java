package combat.ui;

import combat.item.Item;
import combat.level.Difficulty;
import combat.model.Player;
import java.util.List;

/**
 * Full game UI interface: extends BattleUI with setup and post-battle screens.
 * ISP: BattleEngine only depends on BattleUI. GameController uses this full interface.
 * DIP: Both BattleEngine and GameController depend on these abstractions, not CLIView.
 */
public interface GameUI extends BattleUI {

    // Setup Screens
    Player selectPlayer();
    List<Item> selectItems();
    Difficulty selectDifficulty();

    // Post-Battle
    PostBattleChoice promptReplay();
}
