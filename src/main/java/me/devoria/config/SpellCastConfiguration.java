package me.devoria.config;

import me.devoria.spells.SpellCastRule;
import me.devoria.spells.SpellType;
import org.bukkit.configuration.ConfigurationSection;

/** Typed cast rules for the four click-combo spell slots. */
public record SpellCastConfiguration(
        SpellCastRule base,
        SpellCastRule utility,
        SpellCastRule heavy,
        SpellCastRule movement) {

    public SpellCastRule ruleFor(SpellType type) {
        return switch (type) {
            case BASE -> base;
            case UTIL -> utility;
            case HEAVY -> heavy;
            case MOVEMENT -> movement;
        };
    }

    public static SpellCastConfiguration from(ConfigurationSection config) {
        return new SpellCastConfiguration(
                rule(config, "base"),
                rule(config, "utility"),
                rule(config, "heavy"),
                rule(config, "movement"));
    }

    private static SpellCastRule rule(ConfigurationSection config, String slot) {
        String prefix = "spells." + slot;
        return new SpellCastRule(
                config.getInt(prefix + ".mana-cost"),
                config.getInt(prefix + ".cooldown-millis"));
    }
}
