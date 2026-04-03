package combat.item;

import combat.engine.BattleContext;
import combat.model.Player;

/**
 * Interface for all usable items.
 * OCP: New items can be added by implementing this interface.
 * ISP: Minimal interface — only what items need.
 */
public interface Item {

    /**
     * Use this item. Item is consumed after use.
     * @param user    the player using the item
     * @param context the current battle state
     */
    void use(Player user, BattleContext context);

    /**
     * @return display name of this item
     */
    String getName();

    /**
     * @return description of what this item does
     */
    String getDescription();
}
