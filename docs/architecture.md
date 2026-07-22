# Architecture

## Runtime baseline

Devoria currently targets Java 17 and Paper 1.19.3. ModelEngine is an expected
production dependency for the complete model-backed mob experience. The plugin
can start without it so CI, development, diagnostics, and recovery remain
possible; that mode is intentionally feature-reduced and visibly reported.

The current codebase is a recovered prototype. Existing packages do not yet
represent the final service boundaries, and `ItemUtils` remains the largest
known concentration of unrelated responsibilities.

## Current flow

```text
Paper events and commands
        |
        v
listeners / commands / GUIs
        |
        +--> player profiles and cooldowns
        +--> item parsing, rolling, rendering, and equipment stats
        +--> combat, spells, loot, and mobs
        +--> JSON files and disabled legacy database integration
        +--> ModelEngine adapter for model-backed mob behavior
```

Runtime adapters currently call utility classes directly. Recovery work should
move decisions into testable services without attempting a single large rewrite.

## Target dependency direction

```text
Bukkit adapters (commands, listeners, schedulers, GUIs)
        |
        v
Application services (players, items, combat, spells, mobs, loot)
        |
        v
Pure domain values and rules
        |
        v
Ports (profile repository, content registry, clock, random source)
        ^
        |
Infrastructure adapters (JSON, SQLite/MySQL, Paper, ModelEngine)
```

Dependencies point inward. Domain rules must not depend on Paper, ModelEngine,
files, database drivers, wall-clock time, or global randomness. Adapters convert
external values into validated domain inputs and translate results back into
server effects.

## Boundary responsibilities

### Player profiles

- Own faction, affinity, spell loadout, progression, and durable player state.
- Save atomically through a repository interface.
- Keep database and filesystem work off the server tick.
- Version stored schemas and provide explicit migrations.

### Items

- Separate content definitions, random rolls, persistent item identity, display
  rendering, and equipped-stat aggregation.
- Use namespaced persistent data for authoritative fields.
- Treat lore and names as presentation only.
- Validate ranges, enum values, materials, and schema versions at load time.

### Combat

- Represent damage types and defenses with typed immutable values.
- Keep range rolling, percentage stacking, rounding, and mitigation pure.
- Return a structured result used by health changes, indicators, logs, and loot.
- Never rely on positional lists or unchecked Bukkit metadata.

### Spells

- Separate input combinations from selection, mana cost, cooldown policy, and
  Paper effects.
- Inject clocks and random sources for deterministic tests.
- Define failure results for unavailable targets, mana, cooldowns, and optional
  integrations.

### Mobs and loot

- Load versioned, validated definitions through registries.
- Keep ModelEngine-specific types inside `integrations.modelengine`.
- Require ModelEngine for full production acceptance while retaining a clear
  degraded diagnostic mode.
- Separate encounter behavior, reward eligibility, ownership, and item rolling.

### Persistence

- JSON profile storage is the recovered local baseline.
- The existing JDBC utility is legacy and disabled by default.
- Future storage implementations must sit behind repository interfaces, use
  migrations, pool production connections, enable TLS, and perform I/O
  asynchronously with safe server-thread handoff.

## Runtime invariants

- Default startup does not mutate world gamerules.
- Missing optional infrastructure produces clear diagnostics, not class-loading
  failures or silent feature loss.
- Full production readiness includes a compatible ModelEngine runtime.
- Player saves are atomic and bounded serialized input is rejected.
- Custom listeners do not override unrelated vanilla entities or items.
- Admin-capable commands are permission-gated and console-safe where practical.
- The Maven build and Paper lifecycle must remain reproducible in CI.

## Incremental migration order

1. Extract and test pure range, cooldown, selection, aggregation, and combat
   rules.
2. Introduce typed item and damage values alongside the legacy format.
3. Add versioned persistent item data and compatibility readers.
4. Introduce player and content repository interfaces.
5. Move blocking persistence behind asynchronous infrastructure adapters.
6. Complete the Human vertical slice through these boundaries.
7. Remove legacy localized-name metadata and static utility paths only after
   stored-data migrations and runtime coverage exist.

Large rewrites or direct merges from prototype branches are deliberately avoided.
Retained prototype concepts must be reimplemented through these boundaries.
