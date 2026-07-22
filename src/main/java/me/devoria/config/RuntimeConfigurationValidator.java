package me.devoria.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import me.devoria.spells.SpellCastRule;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Validates operator-controlled runtime settings before Devoria registers any
 * listeners or commands.
 */
public final class RuntimeConfigurationValidator {
    private static final int MAX_RANDOM_TICK_SPEED = 4096;
    private static final String DATABASE_USERNAME_ENV = "DEVORIA_DB_USERNAME";
    private static final String DATABASE_PASSWORD_ENV = "DEVORIA_DB_PASSWORD";

    private RuntimeConfigurationValidator() {
    }

    public static List<String> validate(ConfigurationSection config,
            Map<String, String> environment) {
        List<String> errors = new ArrayList<>();

        requireBoolean(config, "world-rules.enabled", errors);
        requireBoolean(config, "world-rules.do-mob-spawning", errors);
        requireBoolean(config, "world-rules.do-fire-tick", errors);
        requireBoolean(config, "world-rules.mob-griefing", errors);
        requireBoolean(config, "integrations.model-engine.enabled", errors);
        requireBoolean(config, "database.enabled", errors);

        validateSpellRules(config, errors);

        if (!config.isInt("world-rules.random-tick-speed")) {
            errors.add("world-rules.random-tick-speed must be an integer");
        } else if (config.getInt("world-rules.random-tick-speed") < 0
                || config.getInt("world-rules.random-tick-speed")
                        > MAX_RANDOM_TICK_SPEED) {
            errors.add("world-rules.random-tick-speed must be between 0 and "
                    + MAX_RANDOM_TICK_SPEED);
        }

        if (config.isBoolean("database.enabled")
                && config.getBoolean("database.enabled")) {
            validateDatabase(config, environment, errors);
        }

        return List.copyOf(errors);
    }

    private static void validateSpellRules(ConfigurationSection config,
            List<String> errors) {
        for (String slot : List.of("base", "utility", "heavy", "movement")) {
            requireBoundedInteger(config, "spells." + slot + ".mana-cost",
                    SpellCastRule.MAX_MANA_COST, errors);
            requireBoundedInteger(config, "spells." + slot + ".cooldown-millis",
                    SpellCastRule.MAX_COOLDOWN_MILLIS, errors);
        }
    }

    private static void requireBoundedInteger(ConfigurationSection config,
            String path, int maximum, List<String> errors) {
        if (!config.isInt(path)) {
            errors.add(path + " must be an integer");
        } else if (config.getInt(path) < 0 || config.getInt(path) > maximum) {
            errors.add(path + " must be between 0 and " + maximum);
        }
    }

    private static void validateDatabase(ConfigurationSection config,
            Map<String, String> environment, List<String> errors) {
        String url = requireNonBlankString(config, "database.url", errors);
        if (url != null) {
            String normalizedUrl = url.toLowerCase(Locale.ROOT);
            if (!normalizedUrl.startsWith("jdbc:mysql://")) {
                errors.add("database.url must use the jdbc:mysql scheme");
            }
            if (normalizedUrl.contains("usessl=false")
                    || normalizedUrl.contains("sslmode=disabled")) {
                errors.add("database.url must not disable TLS");
            }
        }

        requireCredential(config, environment, DATABASE_USERNAME_ENV,
                "database.username", errors);
        requireCredential(config, environment, DATABASE_PASSWORD_ENV,
                "database.password", errors);
    }

    private static void requireCredential(ConfigurationSection config,
            Map<String, String> environment, String environmentName,
            String configPath, List<String> errors) {
        String environmentValue = environment.get(environmentName);
        if (environmentValue != null && !environmentValue.isBlank()) {
            return;
        }

        if (!config.isString(configPath)
                || config.getString(configPath, "").isBlank()) {
            errors.add(configPath + " or " + environmentName + " must be configured");
        }
    }

    private static String requireNonBlankString(ConfigurationSection config,
            String path, List<String> errors) {
        if (!config.isString(path)) {
            errors.add(path + " must be a string");
            return null;
        }

        String value = config.getString(path, "").trim();
        if (value.isBlank()) {
            errors.add(path + " must not be blank");
            return null;
        }
        return value;
    }

    private static void requireBoolean(ConfigurationSection config, String path,
            List<String> errors) {
        if (!config.isBoolean(path)) {
            errors.add(path + " must be true or false");
        }
    }
}
