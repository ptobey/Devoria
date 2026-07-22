package me.devoria.combat;

public enum DamageType {
    PHYSICAL("damage"),
    EARTH("earthDamage"),
    FIRE("fireDamage"),
    ARCANE("arcaneDamage"),
    LIGHT("lightDamage"),
    DARK("darkDamage"),
    WIND("windDamage"),
    ELECTRIC("thunderDamage");

    private final String legacyKey;

    DamageType(String legacyKey) {
        this.legacyKey = legacyKey;
    }

    public String legacyKey() {
        return legacyKey;
    }
}
