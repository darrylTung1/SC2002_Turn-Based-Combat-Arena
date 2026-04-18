package combat.action;

import combat.engine.BattleContext;
import combat.item.Item;
import combat.model.Combatant;
import combat.model.Player;

// Use item from player's inventory; Item consumed after use.

public class UseItemAction implements Action {
    private Item selectedItem;

    public void setSelectedItem(Item item) {
        this.selectedItem = item;
    }

    @Override
    public void execute(Combatant actor, BattleContext context) {
        // LSP: fail explicitly rather than silently doing nothing for non-Player actors.
        if (!(actor instanceof Player player))
            throw new IllegalStateException("UseItemAction requires a Player actor");
        if (selectedItem == null) return;

        selectedItem.use(player, context);
        player.removeItem(selectedItem);
    }

    @Override
    public String getName() {
        return selectedItem != null ? "Use " + selectedItem.getName() : "Use Item";
    }

    @Override
    public boolean isAvailable(Combatant actor, BattleContext context) {
        return (actor instanceof Player player) && player.hasItems();
    }

    @Override
    public Combatant resolveTarget(BattleContext context) {
        if (selectedItem == null) return null;
        // Delegate target resolution to the item — OCP: no instanceof chains here.
        return selectedItem.resolveTarget(context.getPlayer(), context);
    }

    public Item getSelectedItem() {
        return selectedItem;
    }
}
