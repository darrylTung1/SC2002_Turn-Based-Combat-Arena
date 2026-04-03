# SC2002 Turn-Based Combat Arena — Group Briefing

## What's Ready

I've set up the full project architecture. src file contains all the code

Here's the package structure:

```
combat/
├── App.java                    ← Entry point
├── model/                      ← Combatants (all done)
│   ├── Combatant.java          ← Abstract base
│   ├── Player.java             ← Abstract player
│   ├── Enemy.java              ← Abstract enemy
│   ├── Warrior.java            ← Shield Bash implemented
│   ├── Wizard.java             ← Arcane Blast implemented
│   ├── Goblin.java, Wolf.java
├── action/                     ← Combat actions (all done)
│   ├── Action.java             ← Interface
│   ├── BasicAttack.java        ← Damage formula done
│   ├── DefendAction.java
│   ├── UseItemAction.java
│   └── SpecialSkillAction.java
├── effect/                     ← Status effects (all done)
│   ├── StatusEffect.java       ← Interface
│   ├── StunEffect.java
│   ├── DefendBuff.java
│   ├── SmokeBombEffect.java
│   └── ArcaneBlastBuff.java
├── item/                       ← Items (all done)
│   ├── Item.java               ← Interface
│   ├── Potion.java, PowerStone.java, SmokeBomb.java
├── strategy/                   ← Strategy pattern (all done)
│   ├── TurnOrderStrategy.java, SpeedBasedTurnOrder.java
│   └── EnemyActionStrategy.java, BasicAttackOnlyStrategy.java
├── engine/                     ← Battle management (needs testing)
│   ├── BattleEngine.java       ← Game loop written, needs debugging
│   └── BattleContext.java      ← Done
├── level/                      ← Level config (done)
│   ├── Level.java, Difficulty.java
└── ui/                         ← CLI (needs polish)
    ├── GameUI.java             ← Interface
    ├── CLIView.java            ← Screens exist, needs formatting
    └── GameController.java     ← Replay loop simplified
```


### Battle Engine + Game Loop
**Focus: Make the game actually run end-to-end.**

`BattleEngine.java` has the full game loop written but hasn't been tested. Your job:

1. Get it to compile and run from `App.main()`
2. Verify the round flow: turn order → stun check → action → tick effects → check win/lose
3. Key edge cases to test:
   - Stunned combatant's cooldown still decrements (line in `executeRound`)
   - Backup spawn triggers AFTER initial wave is fully defeated, not mid-round
   - Turn order recalculates after backup spawn (new wolves have SPD 35)
   - When a combatant dies mid-round, they don't get another turn
4. Verify against **Appendix A Easy scenario** (Warrior vs 3 Goblins, 11 rounds, end HP 150/260)
5. Fix the `GameController.java` replay logic — currently `promptReplay()` needs to handle three options: replay same settings, new game, or exit

**Key formula to remember:**
```
damage = max(0, attacker.getAttack() - target.getDefense())
target.takeDamage(damage)  // HP = max(0, HP - damage)
```

### Combatants + Actions
**Focus: Verify special skills and action edge cases.**

The classes are written. Your job:

1. Verify `Warrior.executeSpecialSkill()`:
   - Deals `max(0, ATK - DEF)` damage to ONE target
   - Applies `StunEffect(2)` — target skips current turn + next turn
   - Cooldown set to 3, decrements only on the player's turn
2. Verify `Wizard.executeSpecialSkill()`:
   - Deals `max(0, ATK - DEF)` damage to ALL alive enemies
   - Each kill adds +10 ATK via `ArcaneBlastBuff` (stacks across uses)
   - ATK bonus persists until end of level and applies to ALL attacks
3. Verify against **Appendix A Medium scenario (Wizard)**: Round 1 Arcane Blast kills Wolf (ATK 50→60), Round 3 Power Stone triggers Arcane Blast again (ATK 60→80 after 2 kills)
4. Verify `SpecialSkillAction` cooldown: starts at 3 on use, decrements each player turn, ready at 0
5. Verify `DefendAction`: +10 DEF for 2 turns (current + next), removed when `DefendBuff` expires

### Items + Effects + CLI Screens
**Focus: Polish the CLI to match the spec exactly.**

Items and effects are written. Your job:

1. Verify items:
   - `Potion`: heals 100, capped at max HP → `min(currentHP + 100, maxHP)`
   - `PowerStone`: triggers special skill WITHOUT affecting cooldown timer
   - `SmokeBomb`: enemy attacks deal 0 damage for 2 turns (current + next)
2. Verify `SmokeBombEffect` is checked in `BasicAttack.execute()` via `context.isSmokeBombActive()`
3. **Main task — CLI formatting.** The spec requires specific screens:

**Loading screen must show:**
- List of player classes with stats
- Player selection
- Item selection (2 items, duplicates allowed)
- Difficulty selection with enemy counts

**During gameplay must show:**
- Round number
- Backup spawn announcement
- Status effects on each combatant
- Action menu with cooldown display
- Damage dealt, HP changes
- Alive/eliminated status for all combatants at end of each round

**End screen must show:**
- Victory: "Congratulations..." + remaining HP + total rounds
- Defeat: "Defeated. Don't give up..." + enemies remaining + rounds survived
- Three options: replay same settings, new game, exit

The current `displayActionResult()` in `CLIView.java` has a TODO — it needs to print actual damage numbers and HP changes.


## How To Get Started

1. Set up a Java project (IntelliJ/Eclipse/VS Code) with `src/main/java` as the source root
2. All files use `package combat.*` — make sure your project structure matches
3. Run `combat.App` to start
4. It will probably crash on the first run — that's expected, work through the errors

## Key Rules From The Spec

- Damage = `max(0, ATK - DEF)`, HP clamped at 0
- Stun = 2 turns (turn applied + next turn)
- Defend = +10 DEF for 2 turns (current + next)
- Smoke Bomb = 0 damage for 2 turns (current + next)
- Cooldown = 3 turns starting from use, decrements per combatant turn only
- Power Stone does NOT affect cooldown
- Backup spawn triggers after ALL initial enemies are defeated
- HP cannot go below 0
- No draw — game always ends in win or loss


## Timeline

Everyone finish their parts by 10 Apr
Test on 11 Apr

**GitHub**: Everyone push to their own branch, merge into main after testing. Commit history shows individual contributions.
