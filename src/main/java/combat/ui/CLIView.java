package combat.ui;

import combat.action.*;
import combat.engine.BattleContext;
import combat.item.Item;
import combat.item.Potion;
import combat.item.PowerStone;
import combat.item.SmokeBomb;
import combat.level.Difficulty;
import combat.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Command-line implementation of GameUI.
 * SRP: Handles only display and input — no game logic.
 */
public class CLIView implements GameUI {
    private final Scanner scanner;

    public CLIView() {
        this.scanner = new Scanner(System.in);
    }

    // SETUP SCREENS

    @Override
    public Player selectPlayer() {
        System.out.println("=== SELECT YOUR CHARACTER ===");
        System.out.println("1. Warrior - HP:260 ATK:40 DEF:20 SPD:30 | Shield Bash");
        System.out.println("2. Wizard  - HP:200 ATK:50 DEF:10 SPD:20 | Arcane Blast");
        System.out.print("Choose (1-2): ");
        
        int choice = readInt(1,2);
        return choice == 1 ? warrior : wizard;
    }

    @Override
    public List<Item> selectItems() {
        System.out.println("\n=== SELECT 2 ITEMS (duplicates allowed) ===");
        System.out.println("1. Potion      - Heals 100 HP");
        System.out.println("2. Power Stone - Free special skill use");
        System.out.println("3. Smoke Bomb  - Enemy attacks deal 0 damage (2 turns)");

        List<Item> items = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            System.out.print("Item " + i + " (1-3): ");
            items.add(createItem(choice));
        }
        return items;
    }

    @Override
    public Difficulty selectDifficulty() {
        System.out.println("\n=== ENEMIES ===");
        System.out.println(new Goblin());
        System.out.println(new Wolf());

        System.out.println("\n=== SELECT DIFFICULTY ===");
        System.out.println("1. Easy   - 3 Goblins");
        System.out.println("2. Medium - 1 Goblin + 1 Wolf | Backup: 2 Wolves");
        System.out.println("3. Hard   - 2 Goblins | Backup: 1 Goblin + 2 Wolves");
        System.out.print("Choose (1-3): ");

        int choice = readInt(1, 3);
        return switch (choice) {
            case 1 -> Difficulty.EASY;
            case 2 -> Difficulty.MEDIUM;
            case 3 -> Difficulty.HARD;
            default -> Difficulty.EASY;
        };
    }

    // BATTLE DISPLAY

    @Override
    public void displayBattleStart(Player player, List<Enemy> enemies) {
        System.out.println("\n========================================");
        System.out.println("        BATTLE START!");
        System.out.println("========================================");
        System.out.println("Player: " + player);
        System.out.println("Enemies:");
        for (Enemy e : enemies) {
            System.out.println("  - " + e);
        }
        System.out.println("========================================\n");
    }

    @Override
    public void displayRoundStart(int roundNumber) {
        System.out.println("\n--- Round " + roundNumber + " ---");
    }

    @Override
    public void displayRoundEnd(BattleContext context) {
        System.out.println("\n[End of Round " + context.getCurrentRound() + "]");
        System.out.println("  " + context.getPlayer());
        for (var enemy : context.getAllEnemies()) {
            String status = enemy.isAlive() ? enemy.toString() : enemy.getName() + " [ELIMINATED]";
            System.out.println("  " + status);
        }
    }

    @Override
    public void displayStunned(Combatant combatant) {
        System.out.println(combatant.getName() + " is STUNNED! Turn skipped.");
    }

    @Override
    public void displayBackupSpawn(List<Enemy> backup) {
        System.out.println("\n!!! BACKUP SPAWN !!!");
        for (Enemy e : backup) {
            System.out.println("  " + e.getName() + " has entered the battle!");
        }
    }

    @Override
    public void displayActionResult(Combatant actor, Action action, BattleContext context, Combatant target, int oldHp, int newHp) {
        System.out.println(actor.getName() + " used " + action.getName() + ".");
        if (target != null) {
            System.out.println(target.getName() + ": HP " + oldHp + " -> " + newHp);
        }
    }

    // PLAYER INPUT

    @Override
    public Action getPlayerAction(Player player, BattleContext context) {
        System.out.println("\n" + player.getName() + "'s turn! HP: " + player.getHp() + "/" + player.getMaxHp());
        System.out.println("1. Basic Attack");
        System.out.println("2. Defend");
        System.out.println("3. Use Item");

        int maxOption = 3;
        if (player.isSpecialSkillReady()) {
            System.out.println("4. " + player.getSpecialSkillName() + " (Special Skill)");
            maxOption = 4;
        } else {
            System.out.println("4. " + player.getSpecialSkillName()
            + " (Cooldown: " + player.getSpecialSkillCooldown() + ")");
        }

        System.out.print("Choose action: ");
        int choice = readInt(1, maxOption);

        return switch (choice) {
            case 1 -> {
                BasicAttack attack = new BasicAttack();
                attack.setTarget(selectTarget(context.getAliveEnemies()));
                yield attack;
            }
            case 2 -> new DefendAction();
            case 3 -> {
                Item item = selectItemWithBack(player.getItems());
                if (item == null) {
                    yield getPlayerAction(player, context);
                }
                UseItemAction useItem = new UseItemAction();
                useItem.setSelectedItem(item);
                if (item.requiresTarget()) {
                    context.setSelectedTarget(selectTarget(context.getAliveEnemies()));
                }
                yield useItem;
            }
            case 4 -> {
                if (!player.isSpecialSkillReady()) {
                    System.out.println("Skill on cooldown! Choose again.");
                    yield getPlayerAction(player, context);
                }
                SpecialSkillAction skill = new SpecialSkillAction();
                skill.setTarget(selectTarget(context.getAliveEnemies()));
                yield skill;
            }
            default -> {yield getPlayerAction(player, context);}
        };
    }

    @Override
    public Combatant selectTarget(List<Combatant> targets) {
        System.out.println("Select target:");
        for (int i = 0; i < targets.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + targets.get(i));
        }
        System.out.print("Target: ");
        int choice = readInt(1, targets.size());
        return targets.get(choice - 1);
    }

    @Override
    public Item selectItem(List<Item> items) {
        System.out.println("Select item:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + items.get(i).getName()
                    + " - " + items.get(i).getDescription());
        }
        System.out.print("Item: ");
        int choice = readInt(1, items.size());
        return items.get(choice - 1);
    }

    // END SCREENS

    @Override
    public void displayVictory(BattleContext context) {
        System.out.println("\n========================================");
        System.out.println("  VICTORY!");
        System.out.println("  Congratulations, you have defeated all your enemies.");
        System.out.println("  Remaining HP: " + context.getPlayer().getHp()
                + "/" + context.getPlayer().getMaxHp());
        System.out.println("  Total Rounds: " + context.getCurrentRound());
        System.out.println("========================================");
    }

    @Override
    public void displayDefeat(BattleContext context) {
        long enemiesRemaining = context.getAliveEnemies().size();
        System.out.println("\n========================================");
        System.out.println("  DEFEAT");
        System.out.println("  Defeated. Don't give up, try again!");
        System.out.println("  Enemies remaining: " + enemiesRemaining);
        System.out.println("  Total Rounds Survived: " + context.getCurrentRound());
        System.out.println("========================================");
    }

    @Override
    public PostBattleChoice promptReplay() {
        System.out.println("\n1. Replay with same settings");
        System.out.println("2. New game");
        System.out.println("3. Exit");
        System.out.print("Choose: ");

        int choice = readInt(1, 3);
        return switch (choice) {
            case 1 -> PostBattleChoice.REPLAY;
            case 2 -> PostBattleChoice.NEW_GAME;
            case 3 -> PostBattleChoice.EXIT;
            default -> PostBattleChoice.EXIT;
        };
    }

    // =============================================
    // HELPERS

    private int readInt(int min, int max) {
        while (true) {
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.print("Invalid. Enter " + min + "-" + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a number: ");
            }
        }
    }

    private Item selectItemWithBack(List<Item> items) {
        if (items.isEmpty()) {
            System.out.println("No items available.");
            System.out.println("  1. Back");
            System.out.print("Choose: ");
            readInt(1, 1);
            return null;
        }
        System.out.println("Select item:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + items.get(i).getName()
                    + " - " + items.get(i).getDescription());
        }
        System.out.println("  " + (items.size() + 1) + ". Back");
        System.out.print("Item: ");
        int choice = readInt(1, items.size() + 1);
        if (choice == items.size() + 1) return null;
        return items.get(choice - 1);
    }
}
