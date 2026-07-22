package me.devoria.spells;

public enum SpellType {
    BASE("Base Spell", "base"),
    UTIL("Utility Spell", "utility"),
    HEAVY("Heavy Spell", "heavy"),
    MOVEMENT("Movement Spell", "movement");

    private final String cooldownKey;
    private final String displayName;

    SpellType(String cooldownKey, String displayName) {
        this.cooldownKey = cooldownKey;
        this.displayName = displayName;
    }

    public String cooldownKey() {
        return cooldownKey;
    }

    public String displayName() {
        return displayName;
    }
}
