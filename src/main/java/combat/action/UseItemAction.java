package combat.action;

import combat.engine.BattleContext;
import combat.item.Item;
import combat.model.Combatant;
import combat.model.Player;

/**
 * Action to use an item from the player's inventory.
 * Item is consumed after use.
 */
public class UseItemAction implements Action {
    private Item selectedItem;

    public void setSelectedItem(Item item) {
        this.selectedItem = item;
    }

    @Override
    public void execute(Combatant actor, BattleContext context) {
        if (!(actor instanceof Player player)) return;
        if (selectedItem == null) return;

        selectedItem.use(player, context);
        player.removeItem(selectedItem);
    }

    @Override
    public String getName() {
        return "Use Item";
    }

    @Override
    public boolean isAvailable(Combatant actor, BattleContext context) {
        return (actor instanceof Player player) && player.hasItems();
    }

    public Item getSelectedItem() {
        return selectedItem;
    }
}
