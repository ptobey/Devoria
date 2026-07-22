# Human / Imanity vertical slice

## Purpose

The first playable slice is one complete, replayable path: a new player selects
**Imanity → Human**, receives a starter weapon, casts all four Human spells,
defeats one modeled Huntsman mob, earns and equips owned loot, and retains that
state through reconnect and server restart.

This is the acceptance contract for implementation issues and staging. Other
factions and affinities, quests, economy, trading, crafting, and broad balance
work are outside this slice.

## Fixed player-facing mapping

| Slot | Input | Human spell |
|---|---|---|
| Base | `R-L-R` | Heroic Strike |
| Utility | `R-R-L` | Adventurer's Aura |
| Heavy | `R-L-L` | Energy Burst |
| Movement | `R-R-R` | Dash |

The first `R` enters spell mode; the following two clicks select the slot. Help
and failure messages must use these inputs consistently.

## VS-01 — Onboarding and selection

- A player without a profile is clearly prompted to choose a faction and can
  open the selector without operator permission.
- Selecting Imanity opens its affinity view. Selecting Human shows a
  confirmation with faction, affinity, four spells, inputs, starter reward, and
  a clear save action.
- Confirming stores exactly `IMANITY/HUMAN`, installs the four-slot mapping
  above, and grants one valid Human starter weapon.
- Selection and grant are idempotent across double clicks, GUI reopen, reconnect,
  retries, and server restart.
- If inventory is full, selection succeeds, a pending grant is persisted, and
  the player receives instructions for claiming it later. The item is not
  silently dropped or lost.
- Cancel or close leaves prior state unchanged. Invalid and "Coming Soon"
  choices cannot alter a profile.

## VS-02 — Starter weapon and spell mode

- The starter has a stable namespaced ID and schema version, readable name and
  lore, valid base damage, and an authoritative spell-capable marker.
- Enabling spell mode while holding it shows current/max mana and click-sequence
  progress. A vanilla or non-spell weapon cannot cast.
- Cooldown, insufficient-mana, invalid-state, successful-cast, and three-second
  inactivity outcomes have distinct, actionable feedback.
- Death, logout, world change, weapon change, or disabling spell mode safely
  cancels an unfinished sequence.

## VS-03 — Human spells

Spell values are validated content/config values. Acceptance asserts the active
configuration, not hidden constants in listeners.

### Heroic Strike

- Produces a readable forward slash.
- Hits only valid targets in configured range.
- Credits the caster and applies configured damage at most once per target.

### Adventurer's Aura

- Applies configured speed and strength for the configured duration.
- Shows start and end feedback.
- Does not stack or refresh outside its explicit cooldown policy.

### Energy Burst

- Telegraphs and emits the configured short burst around or in front of the
  caster.
- Hits only valid targets and applies configured total damage at most once per
  target.

### Dash

- Moves in the facing direction and stops safely at collisions or invalid
  terrain without clipping through solid blocks.
- Applies configured landing or impact damage at most once to valid nearby
  targets.

### Rules shared by every spell

- A successful cast atomically deducts mana and starts one independent cooldown.
- Mana never becomes negative and regenerates only to max mana at the configured
  rate. Cooldowns are visible in seconds.
- Cooldown, insufficient mana, invalid target/state, or interrupted input causes
  no effect, damage, charge, or new cooldown.
- The caster cannot damage themselves. One documented friendly-fire setting is
  applied consistently.
- Effects and tasks end when participants die, log out, unload, or the plugin
  disables. Particle overlap cannot multiply damage.

## VS-04 — Modeled Huntsman

- `mobs/huntsman.yml` has a stable ID, display name, base entity, ModelEngine ID,
  max health, attack damage/interval, XP, and loot-table ID.
- Missing, malformed, or out-of-range fields are rejected with file/key-specific
  diagnostics before an encounter begins.
- An authorized admin can summon a Huntsman at a safe target location. It has a
  readable name/health state, uses the configured model, attacks players, takes
  starter and spell damage, and dies exactly once.
- Melee and spell damage are credited to the correct player. Contribution cannot
  be inflated by overkill or repeated death processing.
- Without a compatible ModelEngine runtime, Devoria still starts in degraded
  mode; diagnostics explain the loss, summon fails without a stray base entity,
  and no player sees a stack trace.

## VS-05 — Loot, ownership, identification, and equipment

- Huntsman references a validated loot table instead of hard-coded type/level
  paths.
- In deterministic test content, every player contributing at least 15% receives
  exactly the configured personal roll. Ineligible players receive no reward.
- Drops carry stable item ID/schema version, rolled values, owner UUID while
  bound, and identification state.
- Only the owner can collect a bound drop. Denial leaves it recoverable.
- Identification preserves ID, roll, rarity, owner, quantity, and state; retrying
  cannot reroll or duplicate it.
- Equip/hold applies each valid contribution exactly once; replacement or
  unequip removes it exactly once. Vanilla or invalid items inject no stats.
- Full inventory, simultaneous pickup, death, reconnect, and restart do not
  duplicate or lose earned items.
- Permission-gated admin inspection exposes ID, owner, roll, and schema version.

## VS-06 — Persistence and recovery

Persist faction, affinity, the four-slot loadout, max/current mana,
starter-granted-or-pending state, cooldown expiry policy, and plugin-owned item
identity/ownership data.

- Save confirmed selection/grant and save on quit and plugin disable. Load before
  gameplay becomes available on join.
- Reconnect and clean restart preserve selection, loadout, documented mana
  policy, starter/loot, identification, ownership, and effective equipped stats.
- A failed save is visible to operators, retains the last valid file, and keeps
  unsaved in-memory state available for retry.
- A malformed profile is backed up or quarantined and follows a documented
  recovery path instead of silently erasing progress.
- Writes are atomic and replayed join/quit/disable events remain idempotent.
- Persist absolute cooldown expiry timestamps so relog/restart cannot bypass a
  cooldown.

The current filesystem recovery path accepts strict UTF-8 profiles up to 64 KiB
and moves malformed, oversized, or unsupported JSON beside the original as
`<uuid>.json.invalid-<timestamp>` before a default profile may be saved. Existing
recovery files are never replaced. If Devoria cannot move the invalid file, it
keeps the player's replacement state in memory, blocks writes to that path, and
retries preservation on later saves. Operators should retain the quarantine file
for diagnosis or manual migration; restoring it requires stopping the server and
validating its UUID and schema first.

## VS-07 — Admin and operator behavior

Permission-gated tooling must allow an admin to:

1. inspect faction, affinity, loadout, mana, starter status, equipped
   contributions, and persistence health;
2. recover/grant the starter without duplication;
3. summon Huntsman or receive a precise content/integration error;
4. inspect or grant a content item safely;
5. reload content/config atomically, retaining the last valid registry when new
   content is invalid; and
6. view Paper/Devoria versions, persistence, ModelEngine, Human kit, Huntsman,
   loot, and item registry diagnostics.

Console use works wherever no player location or held item is inherently needed.
Unauthorized calls disclose no sensitive state and make no changes.

## Required failure matrix

Every implementation issue must define player feedback, operator feedback, state
changes, and retry/recovery for relevant cases:

- active cooldown, insufficient mana, input timeout, spell mode disabled, wrong
  held item, caster death/logout/world change;
- full inventory, duplicate starter claim, non-owner pickup;
- corrupt profile or failed save;
- missing/malformed item, loot, or mob definition;
- missing/incompatible ModelEngine or unsafe summon location; and
- unauthorized admin action or invalid atomic reload.

Expected failures must not expose stack traces or leave partial state, orphan
entities, or orphan scheduler tasks.

## Two-player, 30-minute release gate

Run from a clean staging data folder with production-like configuration, the
licensed or official demo ModelEngine runtime and model installed, deterministic
test loot, two non-op players A/B, and one admin.

| Time | Required exercise |
|---|---|
| 0–5 min | Both select Imanity → Human; verify one starter and input help each. |
| 5–12 min | Each casts all four spells; exercise cooldown, mana, timeout, wrong-item, self-damage, and friendly-fire rules. |
| 12–18 min | Kill at least three Huntsmen, including shared eligible credit and one below-threshold player; verify XP, ownership, and single death reward. |
| 18–22 min | Test cross-pickup denial, identification, equip deltas, full-inventory recovery, and duplicate starter prevention. |
| 22–25 min | A reconnects; verify all persisted state. Then cleanly restart the server. |
| 25–28 min | Both reconnect, verify state again, cast, and complete another Huntsman kill. |
| 28–30 min | Admin runs diagnostics, inspect, and reload; reject an invalid fixture while current content remains usable. |

Pass only when every scenario succeeds with no duplication, data loss, uncaught
exception, severe error, repeated warning, orphan entity/task, unauthorized
admin access, or post-restart inconsistency. Record environment and performance;
the gate requires TPS at least 19.5 and mean MSPT below 50. A crash, profile
reset, invalid item, exploit, or failed post-restart cast blocks release.

## Suggested issue order

1. Content schema and registry validation for Human, Huntsman, loot, and items.
2. Profile schema, atomic persistence, migration, and recovery.
3. Confirmed selection and idempotent/pending starter grant.
4. Mana, cooldown, cast transaction, and feedback.
5. One issue per Human spell on shared target/damage/task rules.
6. Huntsman spawn, model, combat, and contribution.
7. Personal loot, ownership, identification, and equip recalculation.
8. Admin inspection, recovery, reload, and diagnostics.
9. Automated scenarios and the documented staging playtest.
