# Legacy branch salvage audit

This audit records the disposition of the three pre-recovery branches that
remain on the remote. It was performed against `master` on 2026-07-22. The
branches are historical inputs, not merge candidates.

## Decision summary

| Branch | Ahead / behind `master` | Decision | Reason |
| --- | ---: | --- | --- |
| `raven-sort` | 0 / 64 | Retire | Its tip is already contained in `master`; PR #2 merged the work. |
| `raven-merge-spell-plugin` | 0 / 54 | Retire | Its tip is already contained in `master`; PRs #3 and #4 merged the work. |
| `pto-new-item-stats-creation` | 6 / 30 | Salvage concepts only | It contains useful combat-stat design work, but its implementation conflicts with the recovered runtime and persistence direction. |

No branch should be merged wholesale. No commit from these branches should be
cherry-picked without being rewritten against the current typed, tested
architecture.

## Fully integrated branches

### `raven-sort`

- Tip: `f6eda065e06dc9165ca171b70c98e0237e0183c0`
- Last activity: 2022-12-07
- Unique commits: none
- Integration evidence: [PR #2](https://github.com/ptobey/Devoria/pull/2)

The branch is an ancestor of `master`. It has no source or history that is not
already reachable from the default branch and can be deleted after repository
branch cleanup is approved.

### `raven-merge-spell-plugin`

- Tip: `20c91893c4190dc55f84c3b2e999316f1d8727db`
- Last activity: 2022-12-13
- Unique commits: none
- Integration evidence: [PR #3](https://github.com/ptobey/Devoria/pull/3) and [PR #4](https://github.com/ptobey/Devoria/pull/4)

The branch is an ancestor of `master`. It has no unique source to recover and
can be deleted after repository branch cleanup is approved.

## Item-stat prototype

### `pto-new-item-stats-creation`

- Tip: `2c456d3f2ba6f1c748ae04aa032cd7bd42beb01e`
- Last activity: 2023-05-13
- Merge base: `b25f6edd220a094cd7295783624b8a218334bf29`
- Unique commits: six
- Change size from its merge base: 1,066 insertions and 125 deletions across five Java files
- Pull request: none

The branch explores a larger equipment and combat model:

- physical, earth, fire, arcane, light, dark, wind, and electric base damage;
- per-element percentage bonuses;
- strength, defense, spirit, dexterity, and endurance attributes;
- XP, loot-chance, loot-quality, coin-chance, and coin-quality modifiers;
- aggregation across weapon and armor slots; and
- mob defense metadata and multi-type damage indicators.

These are product concepts worth retaining. The code is not suitable for direct
integration because it:

- adds more than one thousand lines to the already monolithic `ItemUtils`;
- continues to store authoritative data in localized item-name strings;
- uses positional string lists for damage values;
- contains inconsistent keys such as `thunderhDamagePercent`;
- records several reward modifiers that have no implemented behavior;
- assumes entity metadata exists and cancels damage before validating it;
- catches broad exceptions or silently ignores failures; and
- has no tests for rolling, aggregation, mitigation, or malformed data.

### Commit disposition

| Commit | Prototype intent | Disposition |
| --- | --- | --- |
| `13c902b` | Add elemental base-damage ranges | Rewrite as a typed damage model with deterministic range tests. |
| `24e6a9d` | Add elemental percentage bonuses | Rewrite after stacking and rounding rules are specified. |
| `c3977ed` | Repair prototype damage behavior | Do not cherry-pick; cover the intended cases in new combat tests. |
| `cc7bb1e` | Add additional character statistics | Retain the stat names as design input; specify semantics before implementation. |
| `17ec6ef` | Add XP, loot, coin, and element fields | Revisit after progression and loot contracts exist; most fields are inert. |
| `2c456d3` | Apply strength and defense | Rewrite as pure attack and mitigation functions with bounds and tests. |

## Salvage backlog

1. Define a versioned `DamageType` taxonomy and decide whether “electric” or
   “thunder” is the canonical name.
2. Define immutable combat inputs and outputs instead of positional lists and
   metadata strings.
3. Specify range rolling, percentage stacking, rounding, minimum damage, and
   defense bounds with worked examples.
4. Extract pure equipment aggregation, attack, and mitigation functions and
   cover them with unit tests before connecting Bukkit events.
5. Define a versioned item schema using namespaced persistent data before adding
   new item statistics.
6. Specify strength, defense, spirit, dexterity, and endurance in the Human
   vertical slice; omit any stat without player-visible behavior.
7. Defer XP, loot, and coin modifiers until progression, loot ownership, and
   economy services have explicit contracts.
8. Add ModelEngine-backed mob defense and health-bar behavior only through the
   existing integration boundary.

Until this backlog is implemented, keep `pto-new-item-stats-creation` as a
read-only historical reference. Once each retained concept has an issue or
specification, the branch can be retired without losing design intent.
