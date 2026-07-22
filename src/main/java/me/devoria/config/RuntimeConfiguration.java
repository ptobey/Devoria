package me.devoria.config;

import org.bukkit.configuration.ConfigurationSection;

/** Typed settings consumed after startup validation succeeds. */
public record RuntimeConfiguration(
        boolean worldRulesEnabled,
        boolean mobSpawning,
        boolean fireTick,
        int randomTickSpeed,
        boolean mobGriefing,
        boolean modelEngineEnabled,
        SpellCastConfiguration spellCasts) {

    public static RuntimeConfiguration from(ConfigurationSection config) {
        return new RuntimeConfiguration(
                config.getBoolean("world-rules.enabled"),
                config.getBoolean("world-rules.do-mob-spawning"),
                config.getBoolean("world-rules.do-fire-tick"),
                config.getInt("world-rules.random-tick-speed"),
                config.getBoolean("world-rules.mob-griefing"),
                config.getBoolean("integrations.model-engine.enabled"),
                SpellCastConfiguration.from(config));
    }
}
