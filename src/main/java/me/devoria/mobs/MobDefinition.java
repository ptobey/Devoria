package me.devoria.mobs;

import java.util.Map;

/**
 * Validated values required to create one configured Devoria mob.
 */
public record MobDefinition(
        String name,
        String model,
        String entityType,
        int maxHealth,
        int damage,
        int experience) {

    public static MobDefinition from(Map<String, String> values) {
        String name = requireText(values, "name");
        String model = requireText(values, "model");
        if (!model.matches("[A-Za-z0-9_-]+")) {
            throw new IllegalArgumentException(
                    "model must contain only letters, numbers, underscores, or hyphens");
        }

        return new MobDefinition(
                name,
                model,
                requireText(values, "type"),
                requirePositiveInteger(values, "max_health"),
                requireNonNegativeInteger(values, "damage"),
                requireNonNegativeInteger(values, "xp"));
    }

    private static String requireText(Map<String, String> values, String key) {
        String value = values.get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(key + " must not be blank");
        }
        return value.trim();
    }

    private static int requirePositiveInteger(Map<String, String> values, String key) {
        int value = requireInteger(values, key);
        if (value <= 0) {
            throw new IllegalArgumentException(key + " must be greater than zero");
        }
        return value;
    }

    private static int requireNonNegativeInteger(Map<String, String> values, String key) {
        int value = requireInteger(values, key);
        if (value < 0) {
            throw new IllegalArgumentException(key + " must not be negative");
        }
        return value;
    }

    private static int requireInteger(Map<String, String> values, String key) {
        String value = requireText(values, key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(key + " must be an integer", exception);
        }
    }
}
