package me.devoria.spells;

/** Validated resource cost and cooldown for one spell slot. */
public record SpellCastRule(int manaCost, int cooldownMillis) {
    public static final int MAX_MANA_COST = 10_000;
    public static final int MAX_COOLDOWN_MILLIS = 3_600_000;

    public SpellCastRule {
        if (manaCost < 0 || manaCost > MAX_MANA_COST) {
            throw new IllegalArgumentException("manaCost must be between 0 and "
                    + MAX_MANA_COST);
        }
        if (cooldownMillis < 0 || cooldownMillis > MAX_COOLDOWN_MILLIS) {
            throw new IllegalArgumentException("cooldownMillis must be between 0 and "
                    + MAX_COOLDOWN_MILLIS);
        }
    }
}
