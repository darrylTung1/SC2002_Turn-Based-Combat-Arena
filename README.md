# SC2002 Turn-Based Combat Arena

A turn-based combat game built in Java for SC2002.

## Diagrams

- **UML Class Diagram:** https://lucid.app/lucidchart/3fd8394d-cad6-49ed-84bd-b97cbef9c7b7/edit?viewport_loc=566%2C-277%2C5372%2C2583%2CHWEp-vi-RSFO&invitationId=inv_2a694528-77c5-4057-8a57-f8af177ce038
- **Sequence Diagram:** https://lucid.app/lucidchart/c6f00b8b-079c-4e8b-b875-04763bd4a92e/edit?viewport_loc=-170%2C1352%2C2811%2C1351%2CHWEp-vi-RSFO&invitationId=inv_0e3f4e8b-c006-45b4-9dc1-3b4db8749bcb

## How To Run

1. Set up a Java project (IntelliJ/Eclipse/VS Code) with `src/main/java` as the source root
2. All files use `package combat.*` — make sure your project structure matches
3. Run `combat.App` to start

## Key Rules From The Spec

- Damage = `max(0, ATK - DEF)`, HP clamped at 0
- Stun = 2 turns (turn applied + next turn)
- Defend = +10 DEF for 2 turns (current + next)
- Cooldown = 3 turns starting from use, decrements per combatant turn only
- Backup spawn triggers after ALL initial enemies are defeated
- HP cannot go below 0
- No draw — game always ends in win or loss

## Items

- **Potion**: Heal 100 HP — `New HP = min(Current HP + 100, Max HP)`
- **Power Stone**: Triggers special skill once without starting or changing the cooldown timer (free extra use)
- **Smoke Bomb**: Enemy attacks deal 0 damage for the current turn and the next turn
- Two single-use items chosen at game start; duplicates allowed
